<?php

/**
 * This file has been auto-generated
 * by the Symfony Routing Component.
 */

return [
    false, // $matchHost
    [ // $staticRoutes
        '/_profiler' => [[['_route' => '_profiler_home', '_controller' => 'web_profiler.controller.profiler::homeAction'], null, null, null, true, false, null]],
        '/_profiler/search' => [[['_route' => '_profiler_search', '_controller' => 'web_profiler.controller.profiler::searchAction'], null, null, null, false, false, null]],
        '/_profiler/search_bar' => [[['_route' => '_profiler_search_bar', '_controller' => 'web_profiler.controller.profiler::searchBarAction'], null, null, null, false, false, null]],
        '/_profiler/phpinfo' => [[['_route' => '_profiler_phpinfo', '_controller' => 'web_profiler.controller.profiler::phpinfoAction'], null, null, null, false, false, null]],
        '/_profiler/xdebug' => [[['_route' => '_profiler_xdebug', '_controller' => 'web_profiler.controller.profiler::xdebugAction'], null, null, null, false, false, null]],
        '/_profiler/open' => [[['_route' => '_profiler_open_file', '_controller' => 'web_profiler.controller.profiler::openAction'], null, null, null, false, false, null]],
        '/ai/product-advisor' => [[['_route' => 'app_ai_product_advisor', '_controller' => 'App\\Controller\\AIController::productAdvisor'], null, ['POST' => 0], null, false, false, null]],
        '/ai/debug-gemini' => [[['_route' => 'app_ai_debug_gemini', '_controller' => 'App\\Controller\\AIController::debugGemini'], null, ['GET' => 0], null, false, false, null]],
        '/admin/commandes' => [[['_route' => 'admin_commandes_index', '_controller' => 'App\\Controller\\AdminCommandeController::index'], null, ['GET' => 0], null, true, false, null]],
        '/admin/produits-en-attente' => [[['_route' => 'admin_produit_attente', '_controller' => 'App\\Controller\\AdminProduitController::index'], null, ['GET' => 0], null, true, false, null]],
        '/admin' => [[['_route' => 'back_dashboard', '_controller' => 'App\\Controller\\BackController::dashboard'], null, ['GET' => 0], null, false, false, null]],
        '/categorie' => [[['_route' => 'app_categorie_index', '_controller' => 'App\\Controller\\CategorieController::index'], null, ['GET' => 0], null, false, false, null]],
        '/categorie/new' => [[['_route' => 'app_categorie_new', '_controller' => 'App\\Controller\\CategorieController::new'], null, ['GET' => 0, 'POST' => 1], null, false, false, null]],
        '/commande/recapitulatif' => [[['_route' => 'app_commande_recapitulatif', '_controller' => 'App\\Controller\\CommandeController::recapitulatif'], null, ['GET' => 0], null, false, false, null]],
        '/commande/confirmer' => [[['_route' => 'app_commande_confirmer', '_controller' => 'App\\Controller\\CommandeController::confirmer'], null, ['POST' => 0], null, false, false, null]],
        '/commande/historique' => [[['_route' => 'app_commande_historique', '_controller' => 'App\\Controller\\CommandeController::historique'], null, ['GET' => 0], null, false, false, null]],
        '/favoris' => [[['_route' => 'app_favoris_index', '_controller' => 'App\\Controller\\FavorisController::index'], null, ['GET' => 0], null, false, false, null]],
        '/' => [[['_route' => 'front_home', '_controller' => 'App\\Controller\\FrontController::home'], null, ['GET' => 0], null, false, false, null]],
        '/menu' => [[['_route' => 'front_menu', '_controller' => 'App\\Controller\\FrontController::menu'], null, ['GET' => 0], null, false, false, null]],
        '/produits' => [
            [['_route' => 'front_produits_legacy', '_controller' => 'App\\Controller\\FrontController::produitsLegacy'], null, ['GET' => 0], null, false, false, null],
            [['_route' => 'front_produits_legacy_slash', '_controller' => 'App\\Controller\\FrontController::produitsLegacy'], null, ['GET' => 0], null, true, false, null],
        ],
        '/about' => [[['_route' => 'front_about', '_controller' => 'App\\Controller\\FrontController::about'], null, ['GET' => 0], null, false, false, null]],
        '/book' => [[['_route' => 'front_book', '_controller' => 'App\\Controller\\FrontController::book'], null, ['GET' => 0], null, false, false, null]],
        '/panier' => [[['_route' => 'app_panier_index', '_controller' => 'App\\Controller\\PanierController::index'], null, ['GET' => 0], null, false, false, null]],
        '/panier/vider' => [[['_route' => 'app_panier_vider', '_controller' => 'App\\Controller\\PanierController::vider'], null, ['POST' => 0], null, false, false, null]],
        '/commande/checkout' => [[['_route' => 'app_payment_checkout', '_controller' => 'App\\Controller\\PaymentController::checkout'], null, ['POST' => 0], null, false, false, null]],
        '/commande/payment/success' => [[['_route' => 'app_payment_success', '_controller' => 'App\\Controller\\PaymentController::success'], null, null, null, false, false, null]],
        '/commande/payment/cancel' => [[['_route' => 'app_payment_cancel', '_controller' => 'App\\Controller\\PaymentController::cancel'], null, null, null, false, false, null]],
        '/produit' => [[['_route' => 'app_produit_index', '_controller' => 'App\\Controller\\ProduitController::index'], null, ['GET' => 0], null, false, false, null]],
        '/produit/mes-produits' => [[['_route' => 'app_produit_mes_produits', '_controller' => 'App\\Controller\\ProduitController::mesProduits'], null, ['GET' => 0], null, false, false, null]],
        '/produit/new' => [[['_route' => 'app_produit_new', '_controller' => 'App\\Controller\\ProduitController::new'], null, ['GET' => 0, 'POST' => 1], null, false, false, null]],
        '/login' => [[['_route' => 'app_login', '_controller' => 'App\\Controller\\SecurityController::login'], null, null, null, false, false, null]],
        '/logout' => [[['_route' => 'app_logout', '_controller' => 'App\\Controller\\SecurityController::logout'], null, null, null, false, false, null]],
    ],
    [ // $regexpList
        0 => '{^(?'
                .'|/_(?'
                    .'|error/(\\d+)(?:\\.([^/]++))?(*:38)'
                    .'|wdt/([^/]++)(*:57)'
                    .'|profiler/(?'
                        .'|font/([^/\\.]++)\\.woff2(*:98)'
                        .'|([^/]++)(?'
                            .'|/(?'
                                .'|search/results(*:134)'
                                .'|router(*:148)'
                                .'|exception(?'
                                    .'|(*:168)'
                                    .'|\\.css(*:181)'
                                .')'
                            .')'
                            .'|(*:191)'
                        .')'
                    .')'
                .')'
                .'|/admin/(?'
                    .'|produits\\-en\\-attente/([^/]++)/(?'
                        .'|accepter(*:254)'
                        .'|refuser(*:269)'
                    .')'
                    .'|([A-Za-z0-9_\\-/]+)\\.html(*:302)'
                .')'
                .'|/c(?'
                    .'|ategorie/([^/]++)(?'
                        .'|(*:336)'
                        .'|/edit(*:349)'
                        .'|(*:357)'
                    .')'
                    .'|ommande/(?'
                        .'|([^/]++)/ticket(*:392)'
                        .'|confirmation/([^/]++)(*:421)'
                    .')'
                .')'
                .'|/favoris/(?'
                    .'|toggle/([^/]++)(*:458)'
                    .'|supprimer/([^/]++)(*:484)'
                .')'
                .'|/p(?'
                    .'|anier/(?'
                        .'|ajouter/([^/]++)(*:523)'
                        .'|supprimer/([^/]++)(*:549)'
                    .')'
                    .'|roduit/([^/]++)(?'
                        .'|(*:576)'
                        .'|/edit(*:589)'
                        .'|(*:597)'
                    .')'
                .')'
            .')/?$}sDu',
    ],
    [ // $dynamicRoutes
        38 => [[['_route' => '_preview_error', '_controller' => 'error_controller::preview', '_format' => 'html'], ['code', '_format'], null, null, false, true, null]],
        57 => [[['_route' => '_wdt', '_controller' => 'web_profiler.controller.profiler::toolbarAction'], ['token'], null, null, false, true, null]],
        98 => [[['_route' => '_profiler_font', '_controller' => 'web_profiler.controller.profiler::fontAction'], ['fontName'], null, null, false, false, null]],
        134 => [[['_route' => '_profiler_search_results', '_controller' => 'web_profiler.controller.profiler::searchResultsAction'], ['token'], null, null, false, false, null]],
        148 => [[['_route' => '_profiler_router', '_controller' => 'web_profiler.controller.router::panelAction'], ['token'], null, null, false, false, null]],
        168 => [[['_route' => '_profiler_exception', '_controller' => 'web_profiler.controller.exception_panel::body'], ['token'], null, null, false, false, null]],
        181 => [[['_route' => '_profiler_exception_css', '_controller' => 'web_profiler.controller.exception_panel::stylesheet'], ['token'], null, null, false, false, null]],
        191 => [[['_route' => '_profiler', '_controller' => 'web_profiler.controller.profiler::panelAction'], ['token'], null, null, false, true, null]],
        254 => [[['_route' => 'admin_produit_accepter', '_controller' => 'App\\Controller\\AdminProduitController::accepter'], ['id'], ['POST' => 0], null, false, false, null]],
        269 => [[['_route' => 'admin_produit_refuser', '_controller' => 'App\\Controller\\AdminProduitController::refuser'], ['id'], ['POST' => 0], null, false, false, null]],
        302 => [[['_route' => 'back_page', '_controller' => 'App\\Controller\\BackController::page'], ['path'], ['GET' => 0], null, false, false, null]],
        336 => [[['_route' => 'app_categorie_show', '_controller' => 'App\\Controller\\CategorieController::show'], ['id'], ['GET' => 0], null, false, true, null]],
        349 => [[['_route' => 'app_categorie_edit', '_controller' => 'App\\Controller\\CategorieController::edit'], ['id'], ['GET' => 0, 'POST' => 1], null, false, false, null]],
        357 => [[['_route' => 'app_categorie_delete', '_controller' => 'App\\Controller\\CategorieController::delete'], ['id'], ['POST' => 0], null, false, true, null]],
        392 => [[['_route' => 'app_commande_ticket', '_controller' => 'App\\Controller\\CommandeController::ticket'], ['id'], ['GET' => 0], null, false, false, null]],
        421 => [[['_route' => 'app_commande_confirmation', '_controller' => 'App\\Controller\\CommandeController::confirmation'], ['id'], ['GET' => 0], null, false, true, null]],
        458 => [[['_route' => 'app_favoris_toggle', '_controller' => 'App\\Controller\\FavorisController::toggle'], ['id'], ['POST' => 0], null, false, true, null]],
        484 => [[['_route' => 'app_favoris_supprimer', '_controller' => 'App\\Controller\\FavorisController::supprimer'], ['id'], ['POST' => 0], null, false, true, null]],
        523 => [[['_route' => 'app_panier_ajouter', '_controller' => 'App\\Controller\\PanierController::ajouter'], ['id'], ['POST' => 0], null, false, true, null]],
        549 => [[['_route' => 'app_panier_supprimer', '_controller' => 'App\\Controller\\PanierController::supprimer'], ['id'], ['POST' => 0], null, false, true, null]],
        576 => [[['_route' => 'app_produit_show', '_controller' => 'App\\Controller\\ProduitController::show'], ['id'], ['GET' => 0], null, false, true, null]],
        589 => [[['_route' => 'app_produit_edit', '_controller' => 'App\\Controller\\ProduitController::edit'], ['id'], ['GET' => 0, 'POST' => 1], null, false, false, null]],
        597 => [
            [['_route' => 'app_produit_delete', '_controller' => 'App\\Controller\\ProduitController::delete'], ['id'], ['POST' => 0], null, false, true, null],
            [null, null, null, null, false, false, 0],
        ],
    ],
    null, // $checkCondition
];
