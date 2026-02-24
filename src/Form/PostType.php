<?php

namespace App\Form;

use App\Entity\Post;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Form\Type\VichImageType;

class PostType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', TextType::class, [
                'label' => 'Titre',
                'empty_data' => '',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Entrez le titre du sujet',
                ],
            ])
            ->add('content', TextareaType::class, [
                'label' => 'Contenu',
                'empty_data' => '',
                'attr' => [
                    'class' => 'form-control js-ckeditor',
                    'rows' => 6,
                    'placeholder' => 'Entrez votre message',
                ],
            ])
            ->add('imageFile', VichImageType::class, [
                'label' => 'Image (optionnel)',
                'required' => false,
                'download_uri' => false,
                'image_uri' => false,
                'allow_delete' => true,
                'delete_label' => 'Supprimer l\'image actuelle',
                'asset_helper' => true,
            ]);

        if ($options['is_admin'] ?? false) {
            $builder->add('status', ChoiceType::class, [
                'label' => 'Statut',
                'choices' => [
                    'Ouvert' => Post::STATUS_OPEN,
                    'Ferme' => Post::STATUS_CLOSED,
                    'Cache' => Post::STATUS_HIDDEN,
                ],
                'attr' => [
                    'class' => 'form-select',
                ],
            ]);
        }
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver
            ->setDefaults([
                'data_class' => Post::class,
                'is_admin' => false,
            ])
            ->setAllowedTypes('is_admin', 'bool');
    }
}
