<?php

namespace App\Form;

use App\Entity\SponsoringPack;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Positive;

class SponsoringPackType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomPack', TextType::class, [
                'label' => 'Nom du pack',
                'attr' => ['class' => 'form-control', 'placeholder' => 'Ex: Pack Gold'],
                'constraints' => [new NotBlank()],
            ])
            ->add('description', TextareaType::class, [
                'label' => 'Description',
                'attr' => ['class' => 'form-control', 'rows' => 4, 'placeholder' => 'Avantages du pack...'],
                'required' => false,
            ])
            ->add('prix', MoneyType::class, [
                'label' => 'Prix (DT)',
                'currency' => false,
                'attr' => ['class' => 'form-control', 'placeholder' => '0.00'],
                'constraints' => [new NotBlank(), new Positive()],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => SponsoringPack::class,
        ]);
    }
}
