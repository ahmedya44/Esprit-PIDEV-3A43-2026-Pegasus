<?php

declare(strict_types=1);

namespace App\Service;

use App\Entity\User;
use Symfony\Component\DependencyInjection\Attribute\Autowire;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;

final class AvatarService
{
    private const PRESET_DIR = 'profileCom';
    private const USER_UPLOAD_DIR = 'profilePics';
    private const STYLIZED_DIR = 'profileStylized';
    private const ALLOWED_EXTENSIONS = ['jpg', 'jpeg', 'png', 'webp'];

    public function __construct(
        #[Autowire('%kernel.project_dir%')]
        private readonly string $projectDir
    ) {
    }

    /**
     * @return list<string>
     */
    public function listPresetAvatars(): array
    {
        $directory = $this->projectDir.'/public/'.self::PRESET_DIR;
        if (!is_dir($directory)) {
            return [];
        }

        $files = scandir($directory);
        if (false === $files) {
            return [];
        }

        $avatars = [];
        foreach ($files as $file) {
            if ('.' === $file || '..' === $file) {
                continue;
            }

            $absolutePath = $directory.'/'.$file;
            if (!is_file($absolutePath)) {
                continue;
            }

            $extension = strtolower((string) pathinfo($file, PATHINFO_EXTENSION));
            if (!in_array($extension, self::ALLOWED_EXTENSIONS, true)) {
                continue;
            }

            $avatars[] = self::PRESET_DIR.'/'.$file;
        }

        sort($avatars);

        return $avatars;
    }

    public function pickRandomPresetAvatar(): ?string
    {
        $avatars = $this->listPresetAvatars();
        if ([] === $avatars) {
            return null;
        }

        return $avatars[array_rand($avatars)];
    }

    public function storeUploadedAvatar(User $user, UploadedFile $avatarFile): string
    {
        $uploadDir = $this->projectDir.'/public/'.self::USER_UPLOAD_DIR;
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0775, true);
        }

        $extension = $avatarFile->guessExtension() ?: 'jpg';
        try {
            $suffix = bin2hex(random_bytes(6));
        } catch (\Exception) {
            $suffix = uniqid('', true);
            $suffix = str_replace('.', '', $suffix);
        }

        $fileName = sprintf('user_%d_%s.%s', $user->getId(), $suffix, $extension);

        try {
            $avatarFile->move($uploadDir, $fileName);
        } catch (FileException $exception) {
            throw new \RuntimeException('Could not upload image.', 0, $exception);
        }

        return self::USER_UPLOAD_DIR.'/'.$fileName;
    }

    /**
     * @return array{absolute_path: string, public_path: string}
     */
    public function prepareStylizedOutput(User $user, string $style): array
    {
        $style = strtolower(trim($style));
        $allowedStyles = ['anime', 'comic', 'pixar'];
        if (!in_array($style, $allowedStyles, true)) {
            throw new \InvalidArgumentException('Invalid style.');
        }

        $uploadDir = $this->projectDir.'/public/'.self::STYLIZED_DIR;
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0775, true);
        }

        try {
            $suffix = bin2hex(random_bytes(6));
        } catch (\Exception) {
            $suffix = str_replace('.', '', uniqid('', true));
        }

        $fileName = sprintf('user_%d_%s_%s.jpg', $user->getId(), $style, $suffix);

        return [
            'absolute_path' => $uploadDir.'/'.$fileName,
            'public_path' => self::STYLIZED_DIR.'/'.$fileName,
        ];
    }

    public function isPublicAvatarPathAllowed(string $publicPath): bool
    {
        $publicPath = trim($publicPath);
        if ('' === $publicPath) {
            return false;
        }

        $allowedPrefixes = [
            self::PRESET_DIR.'/',
            self::USER_UPLOAD_DIR.'/',
            self::STYLIZED_DIR.'/',
        ];

        $hasAllowedPrefix = false;
        foreach ($allowedPrefixes as $prefix) {
            if (str_starts_with($publicPath, $prefix)) {
                $hasAllowedPrefix = true;
                break;
            }
        }

        if (!$hasAllowedPrefix) {
            return false;
        }

        $absolutePath = $this->projectDir.'/public/'.$publicPath;

        return is_file($absolutePath);
    }
}
