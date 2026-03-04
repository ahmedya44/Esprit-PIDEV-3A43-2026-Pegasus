<?php

declare(strict_types=1);

namespace App\Form;

use App\Entity\QuizQuestion;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormError;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\GreaterThanOrEqual;
use Symfony\Component\Validator\Constraints\NotBlank;

final class QuizQuestionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('questionText', TextareaType::class, [
                'constraints' => [
                    new NotBlank(message: 'Question text is required.'),
                ],
                'attr' => ['rows' => 3],
                'label' => 'Question',
            ])
            ->add('points', IntegerType::class, [
                'constraints' => [
                    new GreaterThanOrEqual(0, message: 'Points must be >= 0'),
                ],
                'label' => 'Points',
            ])
            ->add('orderIndex', IntegerType::class, [
                'constraints' => [
                    new GreaterThanOrEqual(1, message: 'Order must be >= 1'),
                ],
                'label' => 'Order',
            ])
            ->add('quizChoices', CollectionType::class, [
                'entry_type' => QuizChoiceType::class,
                'allow_add' => true,
                'allow_delete' => true,
                'by_reference' => false, // IMPORTANT so add/remove works with Doctrine
                'prototype' => true,
                'label' => 'Choices',
            ]);

        // ✅ Pro validation (diagram rules + correctness)
        $builder->addEventListener(FormEvents::POST_SUBMIT, function (FormEvent $event) {
            /** @var QuizQuestion $question */
            $question = $event->getData();
            $form = $event->getForm();

            $choices = $question->getQuizChoices();
            if ($choices->count() < 2) {
                $form->addError(new FormError('Each question must have at least 2 choices.'));
                return;
            }

            $correctCount = 0;
            foreach ($choices as $c) {
                if ($c->isCorrect()) {
                    $correctCount++;
                }
            }

            if ($correctCount < 1) {
                $form->addError(new FormError('Select at least 1 correct choice.'));
                return;
            }

            // Optional: enforce ONLY 1 correct answer (recommended)
            if ($correctCount > 1) {
                $form->addError(new FormError('Only 1 correct choice is allowed for this quiz.'));
            }
        });
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => QuizQuestion::class,
        ]);
    }
}