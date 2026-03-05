<?php

namespace App\Form;

use App\Entity\Course;
use App\Entity\Quiz;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

final class QuizType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('course', EntityType::class, [
                'class' => Course::class,
                'choice_label' => 'title',
                'placeholder' => 'Select a course',
                'attr' => ['class' => 'form-select'],
            ])

            ->add('title', TextType::class, [
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Final quiz title (ex: Final Exam)',
                ],
            ])

            // ✅ correct entity property name: passingScore (NOT passScore)
            ->add('passingScore', IntegerType::class, [
                'label' => 'Passing score (%)',
                'attr' => [
                    'class' => 'form-control',
                    'min' => 0,
                    'max' => 100,
                ],
            ])

            // ✅ optional fields موجودين في entity
            ->add('timeLimitMin', IntegerType::class, [
                'label' => 'Time limit (minutes)',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'min' => 1,
                ],
            ])
            ->add('attemptLimit', IntegerType::class, [
                'label' => 'Attempt limit',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'min' => 1,
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Quiz::class,
        ]);
    }
}