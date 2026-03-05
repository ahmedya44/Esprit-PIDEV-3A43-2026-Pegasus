<?php

declare(strict_types=1);

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\UrlType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Email;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;

final class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email', EmailType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Please enter an email address']),
                    new Email(['message' => 'Please enter a valid email address']),
                ],
            ])
            ->add('username', TextType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Please enter a username']),
                    new Length([
                        'min' => 3,
                        'minMessage' => 'Username must be at least {{ limit }} characters',
                        'max' => 180,
                        'maxMessage' => 'Username cannot be longer than {{ limit }} characters',
                    ]),
                ],
            ])
            ->add('plainPassword', RepeatedType::class, [
                'type' => PasswordType::class,
                'invalid_message' => 'Passwords do not match.',
                'mapped' => false,
                'first_options' => [
                    'constraints' => [
                        new NotBlank(['message' => 'Please enter a password']),
                        new Length([
                            'min' => 6,
                            'minMessage' => 'Password must be at least {{ limit }} characters',
                        ]),
                    ],
                ],
                'second_options' => [],
            ])
            ->add('phone', TextType::class, [
                'required' => false,
            ])
        ;

        switch ($options['registration_role']) {
            case 'admin':
                $builder->add('birthDate', DateType::class, [
                    'required' => false,
                    'widget' => 'single_text',
                ]);
                break;
            case 'artiste':
                $builder
                    ->add('birthDate', DateType::class, [
                        'required' => false,
                        'widget' => 'single_text',
                    ])
                    ->add('bio', TextareaType::class, ['required' => false])
                    ->add('styles', TextType::class, ['required' => false])
                    ->add('facebook', UrlType::class, ['required' => false])
                    ->add('instagram', UrlType::class, ['required' => false])
                    ->add('portfolioUrl', UrlType::class, ['required' => false]);
                break;
            case 'sponsor':
                $builder
                    ->add('companyName', TextType::class, [
                        'constraints' => [
                            new NotBlank(['message' => 'Please enter a company name']),
                        ],
                    ])
                    ->add('website', UrlType::class, ['required' => false])
                    ->add('address', TextType::class, ['required' => false])
                    ->add('description', TextareaType::class, ['required' => false]);
                break;
            case 'normal':
                $builder->add('birthDate', DateType::class, [
                    'required' => false,
                    'widget' => 'single_text',
                ]);
                break;
        }
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
            'registration_role' => 'normal',
        ]);

        $resolver->setAllowedValues('registration_role', ['admin', 'artiste', 'sponsor', 'normal']);
    }
}
