<?php

namespace App\Form;

use App\Entity\Artiste;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\EnumType;
use App\Enum\AccountStatus;

class ArtisteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email')
            ->add('roles')
            ->add('password')
            ->add('username')
            ->add('phone')
            ->add('avatarUrl')
            ->add('createdAt', null, [
                'widget' => 'single_text',
            ])
            ->add('birthDate', null, [
                'widget' => 'single_text',
            ])
            ->add('status', EnumType::class, [
                'class' => AccountStatus::class,
            ])
            ->add('bio')
            ->add('styles')
            ->add('facebook')
            ->add('instagram')
            ->add('portfolioUrl')
            ->add('verified')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Artiste::class,
        ]);
    }
}
