<?php

namespace App\Form;

use App\Entity\CourseVideo;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\UrlType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

final class CourseVideoType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', TextType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'Video title (e.g. Setup & Tools)',
                    'class' => 'form-control',
                ],
                'trim' => true,
                'empty_data' => '',
            ])
            ->add('videoUrl', UrlType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'https://... (video link)',
                    'class' => 'form-control',
                ],
                'trim' => true,
                'empty_data' => '',
            ])
            ->add('durationSec', IntegerType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'Duration in seconds (e.g. 600)',
                    'class' => 'form-control',
                    'min' => 1,
                ],
            ])
            ->add('orderIndex', IntegerType::class, [
                'label' => false,
                'attr' => [
                    'placeholder' => 'Order (e.g. 1)',
                    'class' => 'form-control',
                    'min' => 1,
                ],
            ])
            ->add('isPreview', CheckboxType::class, [
                'label' => 'Preview video (free)',
                'required' => false,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => CourseVideo::class,
        ]);
    }
}