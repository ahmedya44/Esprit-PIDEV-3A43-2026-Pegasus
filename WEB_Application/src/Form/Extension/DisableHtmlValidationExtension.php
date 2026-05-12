<?php

namespace App\Form\Extension;

use Symfony\Component\Form\AbstractTypeExtension;
use Symfony\Component\Form\Extension\Core\Type\FormType;
use Symfony\Component\Form\FormInterface;
use Symfony\Component\Form\FormView;

class DisableHtmlValidationExtension extends AbstractTypeExtension
{
    public static function getExtendedTypes(): iterable
    {
        return [FormType::class];
    }

    public function buildView(FormView $view, FormInterface $form, array $options): void
    {
        if (!$form->isRoot()) {
            return;
        }

        if (!isset($view->vars['attr']['novalidate'])) {
            $view->vars['attr']['novalidate'] = 'novalidate';
        }
    }
}
