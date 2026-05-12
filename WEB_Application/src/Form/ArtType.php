<?php

declare(strict_types=1);

namespace App\Form;

use App\Entity\Art;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type;
use Symfony\Component\Form\Extension\Core\Type\FileType;

final class ArtType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', Type\TextType::class, [
                'label' => 'Titre de l\'œuvre',
                'attr' => ['class' => 'form-control', 'id' => 'art_title'],
            ])
            ->add('description', Type\TextareaType::class, [
                'label' => 'Description de l\'œuvre',
                'attr' => ['class' => 'form-control', 'rows' => 5, 'id' => 'art_description'],
            ])
            ->add('titleEn', Type\TextType::class, [
                'label' => 'Titre (English)',
                'required' => false,
                'attr' => ['class' => 'form-control', 'id' => 'title-en', 'readonly' => true],
            ])
            ->add('descriptionEn', Type\TextareaType::class, [
                'label' => 'Description (English)',
                'required' => false,
                'attr' => ['class' => 'form-control', 'rows' => 5, 'id' => 'description-en', 'readonly' => true],
            ])
            ->add('aiGeneratedImage', Type\HiddenType::class, [
                'required' => false,
                'attr' => ['id' => 'ai-generated-image'],
            ])
            ->add('isAiGenerated', Type\CheckboxType::class, [
                'label' => 'Généré par IA',
                'required' => false,
                'attr' => ['id' => 'is-ai-generated'],
            ])
            ->add('imageFile', FileType::class, [
                'label' => 'Image de l\'œuvre',
                'required' => false,
                'attr' => ['class' => 'form-control', 'id' => 'image-file'],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Art::class,
        ]);
    }
}