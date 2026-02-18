<?php

namespace App\Form;

use App\Entity\Evenement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;

class EvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre', null, [
                'label' => 'Titre de l\'événement',
                'attr' => ['placeholder' => 'Entrez le titre de l\'événement']
            ])
            ->add('date', null, [
                'widget' => 'single_text',
                'label' => 'Date'
            ])
            ->add('heure', null, [
                'widget' => 'single_text',
                'label' => 'Heure'
            ])
            ->add('lieu', null, [
                'label' => 'Lieu',
                'attr' => ['placeholder' => 'Lieu de l\'événement']
            ])
            ->add('description', null, [
                'label' => 'Description',
                'attr' => ['rows' => 4, 'placeholder' => 'Description détaillée']
            ])
            ->add('image', FileType::class, [
                'label' => 'Image (Fichier)',
                'mapped' => false,
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '2048k',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                            'image/webp',
                        ],
                        'mimeTypesMessage' => 'Veuillez uploader une image valide (JPG, PNG, WEBP)',
                    ])
                ],
            ])
            ->add('capacite_max', null, [
                'label' => 'Capacité maximale',
                'attr' => ['min' => 1]
            ])
            ->add('prix', null, [
                'label' => 'Prix',
                'attr' => ['step' => '0.01']
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Evenement::class,
            'attr' => ['novalidate' => 'novalidate']
        ]);
    }
}
