<?php

namespace App\Form;

use App\Entity\Post;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
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
                    'data-ai-autocomplete' => '1',
                    'data-ai-field' => 'post_title',
                    'autocomplete' => 'off',
                ],
            ])
            ->add('content', TextareaType::class, [
                'label' => 'Contenu',
                'empty_data' => '',
                'attr' => [
                    'class' => 'form-control js-ckeditor',
                    'rows' => 6,
                    'placeholder' => 'Entrez votre message',
                    'data-ai-autocomplete' => '1',
                    'data-ai-field' => 'post_content',
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

        $builder->add('status', ChoiceType::class, [
            'label' => 'Visibilite du post',
            'choices' => [
                'Ouvert (visible + commentaires actifs)' => Post::STATUS_OPEN,
                'Ferme (visible + commentaires bloques)' => Post::STATUS_CLOSED,
                'Cache (non visible en front)' => Post::STATUS_HIDDEN,
            ],
            'attr' => [
                'class' => 'form-select status-3d-select',
            ],
        ]);

        $builder->add('allowedViewerIds', TextType::class, [
            'mapped' => false,
            'required' => false,
            'label' => 'IDs autorises si cache',
            'attr' => [
                'class' => 'form-control',
                'placeholder' => 'Exemple: 2, 5, 9',
            ],
            'help' => 'IDs des utilisateurs qui pourront voir ce post quand le statut est CACHE.',
        ]);

        $builder->addEventListener(FormEvents::PRE_SET_DATA, static function (FormEvent $event): void {
            $post = $event->getData();
            if (!$post instanceof Post) {
                return;
            }

            $ids = [];
            foreach ($post->getAllowedViewers() as $viewer) {
                if ($viewer->getId() !== null) {
                    $ids[] = (string) $viewer->getId();
                }
            }

            $event->getForm()->get('allowedViewerIds')->setData(implode(', ', $ids));
        });
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
