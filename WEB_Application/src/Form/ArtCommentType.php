<?php

declare(strict_types=1);

namespace App\Form;

use App\Entity\ArtComment;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;

class ArtCommentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('content', TextareaType::class, [
            'label' => 'Comment',
            'constraints' => [
                new NotBlank(message: 'Comment cannot be empty.'),
                new Length(max: 2000, maxMessage: 'Comment cannot exceed 2000 characters.'),
            ],
            'attr' => ['class' => 'form-control', 'rows' => 3, 'placeholder' => 'Leave a comment…'],
        ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults(['data_class' => ArtComment::class]);
    }
}
