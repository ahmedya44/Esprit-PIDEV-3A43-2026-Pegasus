<?php

declare(strict_types=1);

namespace App\Form;

use App\Entity\QuizChoice;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;

final class QuizChoiceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('label', TextType::class, [
                'constraints' => [
                    new NotBlank(message: 'Choice label is required.'),
                ],
                'attr' => [
                    'placeholder' => 'Choice label (e.g., "Paris")',
                ],
            ])
            ->add('isCorrect', CheckboxType::class, [
                'required' => false,
                'label' => 'Correct?',
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => QuizChoice::class,
        ]);
    }
}