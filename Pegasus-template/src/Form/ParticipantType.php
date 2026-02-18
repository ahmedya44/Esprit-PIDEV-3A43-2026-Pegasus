<?php

namespace App\Form;

use App\Entity\Evenement;
use App\Entity\Participant;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ParticipantType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', null, [
                'label' => 'Nom complet',
                'attr' => ['placeholder' => 'Nom du participant']
            ])
            ->add('email', null, [
                'label' => 'Email',
                'attr' => ['placeholder' => 'exemple@domaine.com']
            ])
            ->add('telephone', null, [
                'label' => 'Téléphone',
                'attr' => ['placeholder' => '01 02 03 04 05']
            ])
            ->add('date_inscription', null, [
                'widget' => 'single_text',
                'label' => 'Date d\'inscription'
            ])
            ->add('statut', \Symfony\Component\Form\Extension\Core\Type\ChoiceType::class, [
                'label' => 'Statut',
                'choices' => [
                    'En attente' => 'En attente',
                    'Confirmé' => 'Confirmé',
                    'Annulé' => 'Annulé',
                ],
                'attr' => ['class' => 'form-select']
            ])
            ->add('evenement', EntityType::class, [
                'class' => Evenement::class,
                'choice_label' => 'titre',
                'label' => 'Associer à un Événement'
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Participant::class,
            'attr' => ['novalidate' => 'novalidate']
        ]);
    }
}
