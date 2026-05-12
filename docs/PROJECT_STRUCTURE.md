# Project structure

This is the active Symfony application. The old nested `Pegasus-template/` app has been removed from the working tree so there is only one real application root.

## Main folders

- `bin/` - Symfony console and PHPUnit entry points.
- `config/` - Symfony bundles, packages, services, and routes.
- `src/` - PHP application code: controllers, entities, forms, repositories, and services.
- `templates/` - Twig views.
- `public/` - Web entry point, uploaded files, and front/back static assets.
- `migrations/` - Doctrine migration files.
- `tests/` - Test bootstrap and test cases.
- `translations/` - Translation files.
- `var/` and `vendor/` - Generated/runtime folders ignored by Git.

## Twig layout convention

- `templates/front/layout.html.twig` is the main public/frontoffice shell.
- `templates/back/layout.html.twig` is the main admin/backoffice shell.
- `templates/base.html.twig`, `templates/base_front.html.twig`, and `templates/admin/base.html.twig` are compatibility aliases for older pages. New templates should extend `front/layout.html.twig` or `back/layout.html.twig` directly.

## Notes

- Do not recreate nested app folders such as `Pegasus-template/bin`, `Pegasus-template/config`, or `Pegasus-template/templates`.
- Keep generated installers such as `composer.phar` and `composer-setup.php` out of the project root unless they are needed temporarily.
