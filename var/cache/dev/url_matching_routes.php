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
        '/admin/art/archives-data' => [[['_route' => 'art_archives_data', '_controller' => 'App\\Controller\\ArtArchiveController::getArchivedArts'], null, ['GET' => 0], null, false, false, null]],
        '/admin' => [[['_route' => 'back_dashboard', '_controller' => 'App\\Controller\\BackController::dashboard'], null, ['GET' => 0], null, false, false, null]],
        '/admin/art' => [[['_route' => 'back_art_list', '_controller' => 'App\\Controller\\BackController::artList'], null, ['GET' => 0], null, false, false, null]],
        '/admin/art-archives' => [[['_route' => 'back_art_archives', '_controller' => 'App\\Controller\\BackController::artArchives'], null, ['GET' => 0], null, false, false, null]],
        '/' => [[['_route' => 'front_home', '_controller' => 'App\\Controller\\FrontController::home'], null, ['GET' => 0], null, false, false, null]],
        '/menu' => [[['_route' => 'front_menu', '_controller' => 'App\\Controller\\FrontController::menu'], null, ['GET' => 0], null, false, false, null]],
        '/about' => [[['_route' => 'front_about', '_controller' => 'App\\Controller\\FrontController::about'], null, ['GET' => 0], null, false, false, null]],
        '/book' => [[['_route' => 'front_book', '_controller' => 'App\\Controller\\FrontController::book'], null, ['GET' => 0], null, false, false, null]],
        '/gallery' => [[['_route' => 'front_gallery', '_controller' => 'App\\Controller\\GalleryController::index'], null, ['GET' => 0], null, false, false, null]],
        '/gallery/new' => [[['_route' => 'front_gallery_new', '_controller' => 'App\\Controller\\GalleryController::new'], null, ['GET' => 0, 'POST' => 1], null, false, false, null]],
        '/api/stats' => [[['_route' => 'api_stats', '_controller' => 'App\\Controller\\StatsController::getStats'], null, ['GET' => 0], null, false, false, null]],
        '/api/translate' => [[['_route' => 'api_translate', '_controller' => 'App\\Controller\\TranslateController::translate'], null, ['POST' => 0], null, false, false, null]],
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
                .'|/a(?'
                    .'|dmin/(?'
                        .'|art/([^/]++)/(?'
                            .'|restore(*:238)'
                            .'|archive(*:253)'
                            .'|delete(?'
                                .'|(*:270)'
                            .')'
                            .'|update\\-status(?'
                                .'|(*:296)'
                            .')'
                            .'|edit(*:309)'
                        .')'
                        .'|([A-Za-z0-9_\\-/]+)\\.html(*:342)'
                    .')'
                    .'|rt/([^/]++)(?'
                        .'|(*:365)'
                        .'|/(?'
                            .'|like(*:381)'
                            .'|view(*:393)'
                        .')'
                    .')'
                    .'|pi/art/([^/]++)/views(*:424)'
                .')'
                .'|/gallery/([^/]++)/(?'
                    .'|edit(*:458)'
                    .'|delete(*:472)'
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
        238 => [[['_route' => 'admin_art_restore', '_controller' => 'App\\Controller\\ArtArchiveController::restore'], ['id'], ['POST' => 0], null, false, false, null]],
        253 => [[['_route' => 'admin_art_archive', '_controller' => 'App\\Controller\\ArtArchiveController::archive'], ['id'], ['POST' => 0], null, false, false, null]],
        270 => [
            [['_route' => 'admin_art_delete', '_controller' => 'App\\Controller\\ArtArchiveController::delete'], ['id'], ['POST' => 0], null, false, false, null],
            [['_route' => 'back_art_delete', '_controller' => 'App\\Controller\\BackController::delete'], ['id'], ['POST' => 0], null, false, false, null],
        ],
        296 => [
            [['_route' => 'admin_art_update_status', '_controller' => 'App\\Controller\\ArtArchiveController::updateStatus'], ['id'], ['POST' => 0], null, false, false, null],
            [['_route' => 'back_art_update_status', '_controller' => 'App\\Controller\\BackController::updateStatus'], ['id'], ['POST' => 0], null, false, false, null],
        ],
        309 => [[['_route' => 'back_art_edit', '_controller' => 'App\\Controller\\BackController::edit'], ['id'], ['GET' => 0, 'POST' => 1], null, false, false, null]],
        342 => [[['_route' => 'back_page', '_controller' => 'App\\Controller\\BackController::page'], ['path'], ['GET' => 0], null, false, false, null]],
        365 => [[['_route' => 'art_detail', '_controller' => 'App\\Controller\\ArtDetailController::show'], ['id'], ['GET' => 0], null, false, true, null]],
        381 => [[['_route' => 'art_like', '_controller' => 'App\\Controller\\LikeController::toggleLike'], ['id'], ['POST' => 0], null, false, false, null]],
        393 => [[['_route' => 'art_view', '_controller' => 'App\\Controller\\ViewController::addView'], ['id'], ['POST' => 0], null, false, false, null]],
        424 => [[['_route' => 'api_art_views', '_controller' => 'App\\Controller\\ViewController::getViewsCount'], ['id'], ['GET' => 0], null, false, false, null]],
        458 => [[['_route' => 'front_gallery_edit', '_controller' => 'App\\Controller\\GalleryController::edit'], ['id'], ['GET' => 0, 'POST' => 1], null, false, false, null]],
        472 => [
            [['_route' => 'front_gallery_delete', '_controller' => 'App\\Controller\\GalleryController::delete'], ['id'], ['POST' => 0], null, false, false, null],
            [null, null, null, null, false, false, 0],
        ],
    ],
    null, // $checkCondition
];
