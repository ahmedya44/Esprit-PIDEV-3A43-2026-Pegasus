<?php

namespace App\Form;

use App\Entity\Course;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\UrlType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

final class CourseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', TextType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'e.g. Web Development Masterclass',
                    'class' => 'form-control',
                ],
                'trim' => true,
                'empty_data' => '',
            ])
            ->add('description', TextareaType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'Write a short description...',
                    'rows' => 6,
                    'class' => 'form-control',
                ],
                'trim' => true,
                'empty_data' => '',
            ])
            ->add('status', ChoiceType::class, [
                'label' => false,
                'choices' => [
                    'DRAFT' => 'DRAFT',
                    'PUBLISHED' => 'PUBLISHED',
                    'HIDDEN' => 'HIDDEN',
                ],
                'attr' => [
                    'class' => 'form-select',
                ],
            ])
            ->add('thumbnailUrl', UrlType::class, [
                'label' => false,
                'required' => false,
                'attr' => [
                    'placeholder' => 'https://...',
                    'class' => 'form-control',
                ],
                'empty_data' => null,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Course::class,
        ]);
    }
}