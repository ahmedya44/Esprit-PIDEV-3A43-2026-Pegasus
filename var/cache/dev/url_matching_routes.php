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
        '/api/art-chatbot' => [[['_route' => 'api_art_chatbot', '_controller' => 'App\\Controller\\ArtChatbotController::chat'], null, ['POST' => 0], null, false, false, null]],
        '/api/chatbot-suggestions' => [[['_route' => 'api_chatbot_suggestions', '_controller' => 'App\\Controller\\ArtChatbotController::getSuggestions'], null, ['GET' => 0], null, false, false, null]],
        '/api/chatbot-info' => [[['_route' => 'api_chatbot_info', '_controller' => 'App\\Controller\\ArtChatbotController::getInfo'], null, ['GET' => 0], null, false, false, null]],
        '/admin' => [[['_route' => 'back_', '_controller' => 'App\\Controller\\BackController::dashboard'], null, null, null, false, false, null]],
        '/admin/dashboard' => [[['_route' => 'back_dashboard', '_controller' => 'App\\Controller\\BackController::adminDashboard'], null, ['GET' => 0], null, false, false, null]],
        '/api/favorites/add' => [[['_route' => 'api_favorites_add', '_controller' => 'App\\Controller\\FavoriteController::addFavorite'], null, ['POST' => 0], null, false, false, null]],
        '/api/favorites/remove' => [[['_route' => 'api_favorites_remove', '_controller' => 'App\\Controller\\FavoriteController::removeFavorite'], null, ['POST' => 0], null, false, false, null]],
        '/api/favorites' => [[['_route' => 'api_favorites_list', '_controller' => 'App\\Controller\\FavoriteController::getFavorites'], null, ['GET' => 0], null, false, false, null]],
        '/mes-favoris' => [[['_route' => 'favorites_page', '_controller' => 'App\\Controller\\FavoriteController::favoritesPage'], null, ['GET' => 0], null, false, false, null]],
        '/' => [[['_route' => 'front_home', '_controller' => 'App\\Controller\\FrontController::home'], null, ['GET' => 0], null, false, false, null]],
        '/menu' => [[['_route' => 'front_menu', '_controller' => 'App\\Controller\\FrontController::menu'], null, ['GET' => 0], null, false, false, null]],
        '/about' => [[['_route' => 'front_about', '_controller' => 'App\\Controller\\FrontController::about'], null, ['GET' => 0], null, false, false, null]],
        '/book' => [[['_route' => 'front_book', '_controller' => 'App\\Controller\\FrontController::book'], null, ['GET' => 0], null, false, false, null]],
        '/gallery' => [[['_route' => 'front_gallery', '_controller' => 'App\\Controller\\GalleryController::index'], null, ['GET' => 0], null, false, false, null]],
        '/gallery/new' => [[['_route' => 'front_gallery_new', '_controller' => 'App\\Controller\\GalleryController::new'], null, ['GET' => 0, 'POST' => 1], null, false, false, null]],
        '/api/daily-inspiration' => [[['_route' => 'api_daily_inspiration', '_controller' => 'App\\Controller\\InspirationController::getDailyInspiration'], null, ['GET' => 0], null, false, false, null]],
        '/api/random-inspiration' => [[['_route' => 'api_random_inspiration', '_controller' => 'App\\Controller\\InspirationController::getRandomInspiration'], null, ['GET' => 0], null, false, false, null]],
        '/api/ml-chatbot' => [[['_route' => 'api_ml_chatbot', '_controller' => 'App\\Controller\\MLArtChatbotController::chat'], null, ['POST' => 0], null, false, false, null]],
        '/api/ml-chatbot/learn' => [[['_route' => 'api_ml_chatbot_learn', '_controller' => 'App\\Controller\\MLArtChatbotController::learn'], null, ['POST' => 0], null, false, false, null]],
        '/api/ml-chatbot/stats' => [[['_route' => 'api_ml_chatbot_stats', '_controller' => 'App\\Controller\\MLArtChatbotController::getStats'], null, ['GET' => 0], null, false, false, null]],
        '/api/ml-chatbot/info' => [[['_route' => 'api_ml_chatbot_info', '_controller' => 'App\\Controller\\MLArtChatbotController::getInfo'], null, ['GET' => 0], null, false, false, null]],
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
                    .'|rt/([^/]++)(?'
                        .'|(*:221)'
                        .'|/view(*:234)'
                    .')'
                    .'|dmin/art/([^/]++)/(?'
                        .'|edit(*:268)'
                        .'|delete(*:282)'
                        .'|status(*:296)'
                    .')'
                    .'|pi/(?'
                        .'|favorites/check/([^/]++)(*:335)'
                        .'|art/([^/]++)/views(*:361)'
                    .')'
                .')'
                .'|/gallery/(?'
                    .'|edit/([^/]++)(*:396)'
                    .'|delete/([^/]++)(*:419)'
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
        221 => [[['_route' => 'art_detail', '_controller' => 'App\\Controller\\ArtDetailController::show'], ['id'], ['GET' => 0], null, false, true, null]],
        234 => [[['_route' => 'record_view', '_controller' => 'App\\Controller\\ViewController::recordView'], ['id'], ['POST' => 0], null, false, false, null]],
        268 => [[['_route' => 'back_art_edit', '_controller' => 'App\\Controller\\BackController::editArt'], ['id'], ['GET' => 0, 'POST' => 1], null, false, false, null]],
        282 => [[['_route' => 'back_art_delete', '_controller' => 'App\\Controller\\BackController::deleteArt'], ['id'], ['POST' => 0], null, false, false, null]],
        296 => [[['_route' => 'back_art_update_status', '_controller' => 'App\\Controller\\BackController::updateStatus'], ['id'], ['POST' => 0], null, false, false, null]],
        335 => [[['_route' => 'api_favorites_check', '_controller' => 'App\\Controller\\FavoriteController::checkFavorite'], ['artId'], ['GET' => 0], null, false, true, null]],
        361 => [[['_route' => 'get_views', '_controller' => 'App\\Controller\\ViewController::getViews'], ['id'], ['GET' => 0], null, false, false, null]],
        396 => [[['_route' => 'front_gallery_edit', '_controller' => 'App\\Controller\\GalleryController::edit'], ['id'], ['GET' => 0, 'POST' => 1], null, false, true, null]],
        419 => [
            [['_route' => 'front_gallery_delete', '_controller' => 'App\\Controller\\GalleryController::delete'], ['id'], ['POST' => 0], null, false, true, null],
            [null, null, null, null, false, false, 0],
        ],
    ],
    null, // $checkCondition
];
