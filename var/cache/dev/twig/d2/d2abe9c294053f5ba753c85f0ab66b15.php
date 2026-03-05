<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Extension\CoreExtension;
use Twig\Extension\SandboxExtension;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Source;
use Twig\Template;
use Twig\TemplateWrapper;

/* back/produits_attente.html.twig */
class __TwigTemplate_c0e2b20ea697dea3af4f0a1cb1a6f318 extends Template
{
    private Source $source;
    /**
     * @var array<string, Template>
     */
    private array $macros = [];

    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->source = $this->getSourceContext();

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/produits_attente.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/produits_attente.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Kaiadmin - Bootstrap 5 Admin Dashboard</title>
    <meta
      content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\"
      name=\"viewport\"
    />
    <link
      rel=\"icon\"
      href=\"";
        // line 12
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/favicon.ico"), "html", null, true);
        yield "\"
      type=\"image/x-icon\"
    />

    <!-- Fonts and icons -->
    <script src=\"";
        // line 17
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/webfont/webfont.min.js"), "html", null, true);
        yield "\"></script>
    <script>
      WebFont.load({
        google: { families: [\"Public Sans:300,400,500,600,700\"] },
        custom: {
          families: [
            \"Font Awesome 5 Solid\",
            \"Font Awesome 5 Regular\",
            \"Font Awesome 5 Brands\",
            \"simple-line-icons\",
          ],
          urls: [\"";
        // line 28
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/fonts.min.css"), "html", null, true);
        yield "\"],
        },
        active: function () {
          sessionStorage.fonts = true;
        },
      });
    </script>

    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"";
        // line 37
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 38
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/plugins.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 39
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/kaiadmin.min.css"), "html", null, true);
        yield "\" />

    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link rel=\"stylesheet\" href=\"";
        // line 42
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/demo.css"), "html", null, true);
        yield "\" />
  </head>
  <body>
    <div class=\"wrapper\">
      <!-- Sidebar -->
      <div class=\"sidebar\" data-background-color=\"dark\">
        <div class=\"sidebar-logo\">
          <!-- Logo Header -->
          <div class=\"logo-header\" data-background-color=\"dark\">
            <a href=\"index.html\" class=\"logo\">
              <img
                src=\"";
        // line 53
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\"
                alt=\"navbar brand\"
                class=\"navbar-brand\"
                height=\"20\"
              />
            </a>
            <div class=\"nav-toggle\">
              <button class=\"btn btn-toggle toggle-sidebar\">
                <i class=\"gg-menu-right\"></i>
              </button>
              <button class=\"btn btn-toggle sidenav-toggler\">
                <i class=\"gg-menu-left\"></i>
              </button>
            </div>
            <button class=\"topbar-toggler more\">
              <i class=\"gg-more-vertical-alt\"></i>
            </button>
          </div>
          <!-- End Logo Header -->
        </div>
        <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
          <div class=\"sidebar-content\">
            <ul class=\"nav nav-secondary\">
              <li class=\"nav-item active\">
                <a
                  data-bs-toggle=\"collapse\"
                  href=\"#dashboard\"
                  class=\"collapsed\"
                  aria-expanded=\"false\"
                >
                  <i class=\"fas fa-home\"></i>
                  <p>Dashboard</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse show\" id=\"dashboard\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"";
        // line 90
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_produit_attente");
        yield "\">
                        <span class=\"sub-item\">Gérer Produits</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"";
        // line 95
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_commandes_index");
        yield "\">
                        <span class=\"sub-item\">Historique des achats</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-section\">
                <span class=\"sidebar-mini-icon\">
                  <i class=\"fa fa-ellipsis-h\"></i>
                </span>
                <h4 class=\"text-section\">Components</h4>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#base\">
                  <i class=\"fas fa-layer-group\"></i>
                  <p>Base</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"base\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"components/avatars.html\">
                        <span class=\"sub-item\">Avatars</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/buttons.html\">
                        <span class=\"sub-item\">Buttons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/gridsystem.html\">
                        <span class=\"sub-item\">Grid System</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/panels.html\">
                        <span class=\"sub-item\">Panels</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/notifications.html\">
                        <span class=\"sub-item\">Notifications</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/sweetalert.html\">
                        <span class=\"sub-item\">Sweet Alert</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/font-awesome-icons.html\">
                        <span class=\"sub-item\">Font Awesome Icons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/simple-line-icons.html\">
                        <span class=\"sub-item\">Simple Line Icons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/typography.html\">
                        <span class=\"sub-item\">Typography</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
                  <i class=\"fas fa-th-list\"></i>
                  <p>Sidebar Layouts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"sidebarLayouts\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"sidebar-style-2.html\">
                        <span class=\"sub-item\">Sidebar Style 2</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"icon-menu.html\">
                        <span class=\"sub-item\">Icon Menu</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#forms\">
                  <i class=\"fas fa-pen-square\"></i>
                  <p>Forms</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"forms\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"forms/forms.html\">
                        <span class=\"sub-item\">Basic Form</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#tables\">
                  <i class=\"fas fa-table\"></i>
                  <p>Tables</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"tables\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"tables/tables.html\">
                        <span class=\"sub-item\">Basic Table</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"tables/datatables.html\">
                        <span class=\"sub-item\">Datatables</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#maps\">
                  <i class=\"fas fa-map-marker-alt\"></i>
                  <p>Maps</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"maps\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"maps/googlemaps.html\">
                        <span class=\"sub-item\">Google Maps</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"maps/jsvectormap.html\">
                        <span class=\"sub-item\">Jsvectormap</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#charts\">
                  <i class=\"far fa-chart-bar\"></i>
                  <p>Charts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"charts\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"charts/charts.html\">
                        <span class=\"sub-item\">Chart Js</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"charts/sparkline.html\">
                        <span class=\"sub-item\">Sparkline</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a href=\"widgets.html\">
                  <i class=\"fas fa-desktop\"></i>
                  <p>Widgets</p>
                  <span class=\"badge badge-success\">4</span>
                </a>
              </li>
              <li class=\"nav-item\">
                <a href=\"../../documentation/index.html\">
                  <i class=\"fas fa-file\"></i>
                  <p>Documentation</p>
                  <span class=\"badge badge-secondary\">1</span>
                </a>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#submenu\">
                  <i class=\"fas fa-bars\"></i>
                  <p>Menu Levels</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"submenu\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a data-bs-toggle=\"collapse\" href=\"#subnav1\">
                        <span class=\"sub-item\">Level 1</span>
                        <span class=\"caret\"></span>
                      </a>
                      <div class=\"collapse\" id=\"subnav1\">
                        <ul class=\"nav nav-collapse subnav\">
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </li>
                    <li>
                      <a data-bs-toggle=\"collapse\" href=\"#subnav2\">
                        <span class=\"sub-item\">Level 1</span>
                        <span class=\"caret\"></span>
                      </a>
                      <div class=\"collapse\" id=\"subnav2\">
                        <ul class=\"nav nav-collapse subnav\">
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </li>
                    <li>
                      <a href=\"#\">
                        <span class=\"sub-item\">Level 1</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- End Sidebar -->

      <div class=\"main-panel\">
        <div class=\"main-header\">
          <div class=\"main-header-logo\">
            <!-- Logo Header -->
            <div class=\"logo-header\" data-background-color=\"dark\">
              <a href=\"index.html\" class=\"logo\">
                <img
                  src=\"";
        // line 342
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\"
                  alt=\"navbar brand\"
                  class=\"navbar-brand\"
                  height=\"20\"
                />
              </a>
              <div class=\"nav-toggle\">
                <button class=\"btn btn-toggle toggle-sidebar\">
                  <i class=\"gg-menu-right\"></i>
                </button>
                <button class=\"btn btn-toggle sidenav-toggler\">
                  <i class=\"gg-menu-left\"></i>
                </button>
              </div>
              <button class=\"topbar-toggler more\">
                <i class=\"gg-more-vertical-alt\"></i>
              </button>
            </div>
            <!-- End Logo Header -->
          </div>
          <!-- Navbar Header -->
          <nav
            class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\"
          >
            <div class=\"container-fluid\">
              <nav
                class=\"navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex\"
              >
                <div class=\"input-group\">
                  <div class=\"input-group-prepend\">
                    <button type=\"submit\" class=\"btn btn-search pe-1\">
                      <i class=\"fa fa-search search-icon\"></i>
                    </button>
                  </div>
                  <input
                    type=\"text\"
                    placeholder=\"Search ...\"
                    class=\"form-control\"
                  />
                </div>
              </nav>

              <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                <li
                  class=\"nav-item topbar-icon dropdown hidden-caret d-flex d-lg-none\"
                >
                  <a
                    class=\"nav-link dropdown-toggle\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    role=\"button\"
                    aria-expanded=\"false\"
                    aria-haspopup=\"true\"
                  >
                    <i class=\"fa fa-search\"></i>
                  </a>
                  <ul class=\"dropdown-menu dropdown-search animated fadeIn\">
                    <form class=\"navbar-left navbar-form nav-search\">
                      <div class=\"input-group\">
                        <input
                          type=\"text\"
                          placeholder=\"Search ...\"
                          class=\"form-control\"
                        />
                      </div>
                    </form>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link dropdown-toggle\"
                    href=\"#\"
                    id=\"messageDropdown\"
                    role=\"button\"
                    data-bs-toggle=\"dropdown\"
                    aria-haspopup=\"true\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fa fa-envelope\"></i>
                  </a>
                  <ul
                    class=\"dropdown-menu messages-notif-box animated fadeIn\"
                    aria-labelledby=\"messageDropdown\"
                  >
                    <li>
                      <div
                        class=\"dropdown-title d-flex justify-content-between align-items-center\"
                      >
                        Messages
                        <a href=\"#\" class=\"small\">Mark all as read</a>
                      </div>
                    </li>
                    <li>
                      <div class=\"message-notif-scroll scrollbar-outer\">
                        <div class=\"notif-center\">
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"";
        // line 440
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Jimmy Denis</span>
                              <span class=\"block\"> How are you ? </span>
                              <span class=\"time\">5 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"";
        // line 453
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/chadengle.jpg"), "html", null, true);
        yield "\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Chad</span>
                              <span class=\"block\"> Ok, Thanks ! </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"";
        // line 466
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/mlane.jpg"), "html", null, true);
        yield "\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Jhon Doe</span>
                              <span class=\"block\">
                                Ready for the meeting today...
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"";
        // line 481
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/talha.jpg"), "html", null, true);
        yield "\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Talha</span>
                              <span class=\"block\"> Hi, Apa Kabar ? </span>
                              <span class=\"time\">17 minutes ago</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <a class=\"see-all\" href=\"javascript:void(0);\"
                        >See all messages<i class=\"fa fa-angle-right\"></i>
                      </a>
                    </li>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link dropdown-toggle\"
                    href=\"#\"
                    id=\"notifDropdown\"
                    role=\"button\"
                    data-bs-toggle=\"dropdown\"
                    aria-haspopup=\"true\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fa fa-bell\"></i>
                    <span class=\"notification\">4</span>
                  </a>
                  <ul
                    class=\"dropdown-menu notif-box animated fadeIn\"
                    aria-labelledby=\"notifDropdown\"
                  >
                    <li>
                      <div class=\"dropdown-title\">
                        You have 4 new notification
                      </div>
                    </li>
                    <li>
                      <div class=\"notif-scroll scrollbar-outer\">
                        <div class=\"notif-center\">
                          <a href=\"#\">
                            <div class=\"notif-icon notif-primary\">
                              <i class=\"fa fa-user-plus\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\"> New user registered </span>
                              <span class=\"time\">5 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-icon notif-success\">
                              <i class=\"fa fa-comment\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\">
                                Rahmad commented on Admin
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"";
        // line 549
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile2.jpg"), "html", null, true);
        yield "\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\">
                                Reza send messages to you
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-icon notif-danger\">
                              <i class=\"fa fa-heart\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\"> Farrah liked Admin </span>
                              <span class=\"time\">17 minutes ago</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <a class=\"see-all\" href=\"javascript:void(0);\"
                        >See all notifications<i class=\"fa fa-angle-right\"></i>
                      </a>
                    </li>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fas fa-layer-group\"></i>
                  </a>
                  <div class=\"dropdown-menu quick-actions animated fadeIn\">
                    <div class=\"quick-actions-header\">
                      <span class=\"title mb-1\">Quick Actions</span>
                      <span class=\"subtitle op-7\">Shortcuts</span>
                    </div>
                    <div class=\"quick-actions-scroll scrollbar-outer\">
                      <div class=\"quick-actions-items\">
                        <div class=\"row m-0\">
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div class=\"avatar-item bg-danger rounded-circle\">
                                <i class=\"far fa-calendar-alt\"></i>
                              </div>
                              <span class=\"text\">Calendar</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-warning rounded-circle\"
                              >
                                <i class=\"fas fa-map\"></i>
                              </div>
                              <span class=\"text\">Maps</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div class=\"avatar-item bg-info rounded-circle\">
                                <i class=\"fas fa-file-excel\"></i>
                              </div>
                              <span class=\"text\">Reports</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-success rounded-circle\"
                              >
                                <i class=\"fas fa-envelope\"></i>
                              </div>
                              <span class=\"text\">Emails</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-primary rounded-circle\"
                              >
                                <i class=\"fas fa-file-invoice-dollar\"></i>
                              </div>
                              <span class=\"text\">Invoice</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-secondary rounded-circle\"
                              >
                                <i class=\"fas fa-credit-card\"></i>
                              </div>
                              <span class=\"text\">Payments</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>

                <li class=\"nav-item topbar-user dropdown hidden-caret\">
                  <a
                    class=\"dropdown-toggle profile-pic\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    aria-expanded=\"false\"
                  >
                    <div class=\"avatar-sm\">
                      <img
                        src=\"";
        // line 667
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile.jpg"), "html", null, true);
        yield "\"
                        alt=\"...\"
                        class=\"avatar-img rounded-circle\"
                      />
                    </div>
                    <span class=\"profile-username\">
                      <span class=\"op-7\">Hi,</span>
                      <span class=\"fw-bold\">Hizrian</span>
                    </span>
                  </a>
                  <ul class=\"dropdown-menu dropdown-user animated fadeIn\">
                    <div class=\"dropdown-user-scroll scrollbar-outer\">
                      <li>
                        <div class=\"user-box\">
                          <div class=\"avatar-lg\">
                            <img
                              src=\"";
        // line 683
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile.jpg"), "html", null, true);
        yield "\"
                              alt=\"image profile\"
                              class=\"avatar-img rounded\"
                            />
                          </div>
                          <div class=\"u-text\">
                            <h4>Hizrian</h4>
                            <p class=\"text-muted\">hello@example.com</p>
                            <a
                              href=\"profile.html\"
                              class=\"btn btn-xs btn-secondary btn-sm\"
                              >View Profile</a
                            >
                          </div>
                        </div>
                      </li>
                      <li>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">My Profile</a>
                        <a class=\"dropdown-item\" href=\"#\">My Balance</a>
                        <a class=\"dropdown-item\" href=\"#\">Inbox</a>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">Account Setting</a>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">Logout</a>
                      </li>
                    </div>
                  </ul>
                </li>
              </ul>
            </div>
          </nav>
          <!-- End Navbar -->
        </div>

        <div class=\"container\">
          <div class=\"page-inner\">
            <div class=\"page-header\">
              <h4 class=\"page-title\">Produits en attente</h4>
              <ul class=\"breadcrumbs\">
                <li class=\"nav-home\">
                  <a href=\"";
        // line 724
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">
                    <i class=\"icon-home\"></i>
                  </a>
                </li>
                <li class=\"separator\">
                  <i class=\"icon-arrow-right\"></i>
                </li>
                <li class=\"nav-item\">
                  <a href=\"#\">Produits</a>
                </li>
              </ul>
            </div>
            <div class=\"page-category\">
               ";
        // line 737
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 737, $this->source); })()), "flashes", ["success"], "method", false, false, false, 737));
        foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
            // line 738
            yield "                    <div class=\"alert alert-success\">";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
            yield "</div>
               ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 740
        yield "               ";
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 740, $this->source); })()), "flashes", ["warning"], "method", false, false, false, 740));
        foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
            // line 741
            yield "                    <div class=\"alert alert-warning\">";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
            yield "</div>
               ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 743
        yield "
               <div class=\"card\">
                 <div class=\"card-header\">
                   <div class=\"card-title\">Gérer les produits</div>
                 </div>
                 <div class=\"card-body\">
                   <div class=\"table-responsive\">
                     <table class=\"table table-hover\">
                       <thead>
                         <tr>
                           <th>Image</th>
                           <th>Nom</th>
                           <th>Catégorie</th>
                           <th>Prix</th>
                           <th>Stock</th>
                           <th>Statut</th>
                           <th>Description</th>
                           <th>Actions</th>
                         </tr>
                       </thead>
                       <tbody>
                         ";
        // line 764
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["produits"]) || array_key_exists("produits", $context) ? $context["produits"] : (function () { throw new RuntimeError('Variable "produits" does not exist.', 764, $this->source); })()));
        $context['_iterated'] = false;
        $context['loop'] = [
          'parent' => $context['_parent'],
          'index0' => 0,
          'index'  => 1,
          'first'  => true,
        ];
        if (is_array($context['_seq']) || (is_object($context['_seq']) && $context['_seq'] instanceof \Countable)) {
            $length = count($context['_seq']);
            $context['loop']['revindex0'] = $length - 1;
            $context['loop']['revindex'] = $length;
            $context['loop']['length'] = $length;
            $context['loop']['last'] = 1 === $length;
        }
        foreach ($context['_seq'] as $context["_key"] => $context["produit"]) {
            // line 765
            yield "                         <tr>
                           <td>
                             ";
            // line 767
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 767)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 768
                yield "                               <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 768))), "html", null, true);
                yield "\" width=\"60\" style=\"border-radius:10px; object-fit:cover; height:60px;\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 768), "html", null, true);
                yield "\">
                             ";
            } else {
                // line 770
                yield "                               <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
                yield "\" width=\"60\" height=\"60\" style=\"border-radius:10px\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 770), "html", null, true);
                yield "\">
                             ";
            }
            // line 772
            yield "                           </td>
                           <td><strong>";
            // line 773
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 773), "html", null, true);
            yield "</strong></td>
                           <td>";
            // line 774
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 774)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 774), "nom", [], "any", false, false, false, 774), "html", null, true)) : (""));
            yield "</td>
                           <td>";
            // line 775
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "prix", [], "any", false, false, false, 775), "html", null, true);
            yield " €</td>
                           <td>";
            // line 776
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "stock", [], "any", false, false, false, 776), "html", null, true);
            yield "</td>
                           <td style=\"font-weight: bold;\">
                             ";
            // line 778
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "statut", [], "any", false, false, false, 778) == "disponible")) {
                // line 779
                yield "                               <span class=\"text-success\"><i class=\"fa fa-check-circle\"></i> Disponible</span>
                             ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 780
$context["produit"], "statut", [], "any", false, false, false, 780) == "refuse")) {
                // line 781
                yield "                               <span class=\"text-danger\"><i class=\"fa fa-times-circle\"></i> Refusé</span>
                             ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 782
$context["produit"], "statut", [], "any", false, false, false, 782) == "en_attente")) {
                // line 783
                yield "                               <span class=\"text-warning\"><i class=\"fa fa-clock\"></i> En attente</span>
                             ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 784
$context["produit"], "statut", [], "any", false, false, false, 784) == "rupture")) {
                // line 785
                yield "                               <span class=\"text-secondary\"><i class=\"fa fa-exclamation-triangle\"></i> Rupture de stock</span>
                             ";
            } else {
                // line 787
                yield "                               <span class=\"text-muted\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::capitalize($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "statut", [], "any", false, false, false, 787)), "html", null, true);
                yield "</span>
                             ";
            }
            // line 789
            yield "                           </td>
                           <td>";
            // line 790
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 790), 0, 50), "html", null, true);
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 790)) > 50)) {
                yield "...";
            }
            yield "</td>
                           <td>
                             ";
            // line 792
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "statut", [], "any", false, false, false, 792) == "en_attente")) {
                // line 793
                yield "                             <form action=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_produit_accepter", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 793)]), "html", null, true);
                yield "\" method=\"post\" class=\"d-inline\">
                                <button type=\"submit\" class=\"btn btn-success btn-sm\"><i class=\"fa fa-check\"></i> Accepter</button>
                             </form>
                             <form action=\"";
                // line 796
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_produit_refuser", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 796)]), "html", null, true);
                yield "\" method=\"post\" class=\"d-inline\" onsubmit=\"return confirm('Êtes-vous sûr de vouloir refuser ce produit ?');\">
                                <button type=\"submit\" class=\"btn btn-danger btn-sm\"><i class=\"fa fa-times\"></i> Refuser</button>
                             </form>
                             ";
            }
            // line 800
            yield "                             <a href=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 800), "from" => "admin"]), "html", null, true);
            yield "\" class=\"btn btn-secondary btn-sm mt-1 mb-1\"><i class=\"fa fa-edit\"></i> Modifier</a>
                             ";
            // line 801
            yield Twig\Extension\CoreExtension::include($this->env, $context, "produit/_delete_form.html.twig", ["button_class" => "btn btn-dark btn-sm d-inline"]);
            yield "
                           </td>
                         </tr>
                         ";
            $context['_iterated'] = true;
            ++$context['loop']['index0'];
            ++$context['loop']['index'];
            $context['loop']['first'] = false;
            if (isset($context['loop']['revindex0'], $context['loop']['revindex'])) {
                --$context['loop']['revindex0'];
                --$context['loop']['revindex'];
                $context['loop']['last'] = 0 === $context['loop']['revindex0'];
            }
        }
        // line 804
        if (!$context['_iterated']) {
            // line 805
            yield "                         <tr>
                            <td colspan=\"8\" class=\"text-center py-4\">
                                <i class=\"fa fa-info-circle fa-2x text-muted mb-2\"></i><br>
                                Aucun produit trouvé.
                            </td>
                         </tr>
                         ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['produit'], $context['_parent'], $context['_iterated'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 812
        yield "                       </tbody>
                     </table>
                   </div>
                 </div>
               </div>
            </div>
          </div>
        </div>

        <footer class=\"footer\">
          <div class=\"container-fluid d-flex justify-content-between\">
            <nav class=\"pull-left\">
              <ul class=\"nav\">
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"http://www.themekita.com\">
                    ThemeKita
                  </a>
                </li>
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"#\"> Help </a>
                </li>
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"#\"> Licenses </a>
                </li>
              </ul>
            </nav>
            <div class=\"copyright\">
              2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by
              <a href=\"http://www.themekita.com\">ThemeKita</a>
            </div>
            <div>
              Distributed by
              <a target=\"_blank\" href=\"https://themewagon.com/\">ThemeWagon</a>.
            </div>
          </div>
        </footer>
      </div>
    </div>
    <!--   Core JS Files   -->
    <script src=\"";
        // line 851
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 852
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 853
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Scrollbar -->
    <script src=\"";
        // line 856
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Chart JS -->
    <script src=\"";
        // line 859
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart.js/chart.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Sparkline -->
    <script src=\"";
        // line 862
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery.sparkline/jquery.sparkline.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Chart Circle -->
    <script src=\"";
        // line 865
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart-circle/circles.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Datatables -->
    <script src=\"";
        // line 868
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/datatables/datatables.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Bootstrap Notify -->
    <script src=\"";
        // line 871
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/bootstrap-notify/bootstrap-notify.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Vector Maps -->
    <script src=\"";
        // line 874
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/jsvectormap.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 875
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/world.js"), "html", null, true);
        yield "\"></script>

    <!-- Google Maps Plugin -->
    <script src=\"";
        // line 878
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/gmaps/gmaps.js"), "html", null, true);
        yield "\"></script>

    <!-- Sweet Alert -->
    <script src=\"";
        // line 881
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/sweetalert/sweetalert.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Kaiadmin JS -->
    <script src=\"";
        // line 884
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/kaiadmin.min.js"), "html", null, true);
        yield "\"></script>
  </body>
</html>
";
        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        yield from [];
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "back/produits_attente.html.twig";
    }

    /**
     * @codeCoverageIgnore
     */
    public function isTraitable(): bool
    {
        return false;
    }

    /**
     * @codeCoverageIgnore
     */
    public function getDebugInfo(): array
    {
        return array (  1155 => 884,  1149 => 881,  1143 => 878,  1137 => 875,  1133 => 874,  1127 => 871,  1121 => 868,  1115 => 865,  1109 => 862,  1103 => 859,  1097 => 856,  1091 => 853,  1087 => 852,  1083 => 851,  1042 => 812,  1030 => 805,  1028 => 804,  1012 => 801,  1007 => 800,  1000 => 796,  993 => 793,  991 => 792,  983 => 790,  980 => 789,  974 => 787,  970 => 785,  968 => 784,  965 => 783,  963 => 782,  960 => 781,  958 => 780,  955 => 779,  953 => 778,  948 => 776,  944 => 775,  940 => 774,  936 => 773,  933 => 772,  925 => 770,  917 => 768,  915 => 767,  911 => 765,  893 => 764,  870 => 743,  861 => 741,  856 => 740,  847 => 738,  843 => 737,  827 => 724,  783 => 683,  764 => 667,  643 => 549,  572 => 481,  554 => 466,  538 => 453,  522 => 440,  421 => 342,  171 => 95,  163 => 90,  123 => 53,  109 => 42,  103 => 39,  99 => 38,  95 => 37,  83 => 28,  69 => 17,  61 => 12,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Kaiadmin - Bootstrap 5 Admin Dashboard</title>
    <meta
      content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\"
      name=\"viewport\"
    />
    <link
      rel=\"icon\"
      href=\"{{ asset('back/img/kaiadmin/favicon.ico') }}\"
      type=\"image/x-icon\"
    />

    <!-- Fonts and icons -->
    <script src=\"{{ asset('back/js/plugin/webfont/webfont.min.js') }}\"></script>
    <script>
      WebFont.load({
        google: { families: [\"Public Sans:300,400,500,600,700\"] },
        custom: {
          families: [
            \"Font Awesome 5 Solid\",
            \"Font Awesome 5 Regular\",
            \"Font Awesome 5 Brands\",
            \"simple-line-icons\",
          ],
          urls: [\"{{ asset('back/css/fonts.min.css') }}\"],
        },
        active: function () {
          sessionStorage.fonts = true;
        },
      });
    </script>

    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/bootstrap.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/plugins.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/kaiadmin.min.css') }}\" />

    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/demo.css') }}\" />
  </head>
  <body>
    <div class=\"wrapper\">
      <!-- Sidebar -->
      <div class=\"sidebar\" data-background-color=\"dark\">
        <div class=\"sidebar-logo\">
          <!-- Logo Header -->
          <div class=\"logo-header\" data-background-color=\"dark\">
            <a href=\"index.html\" class=\"logo\">
              <img
                src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\"
                alt=\"navbar brand\"
                class=\"navbar-brand\"
                height=\"20\"
              />
            </a>
            <div class=\"nav-toggle\">
              <button class=\"btn btn-toggle toggle-sidebar\">
                <i class=\"gg-menu-right\"></i>
              </button>
              <button class=\"btn btn-toggle sidenav-toggler\">
                <i class=\"gg-menu-left\"></i>
              </button>
            </div>
            <button class=\"topbar-toggler more\">
              <i class=\"gg-more-vertical-alt\"></i>
            </button>
          </div>
          <!-- End Logo Header -->
        </div>
        <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
          <div class=\"sidebar-content\">
            <ul class=\"nav nav-secondary\">
              <li class=\"nav-item active\">
                <a
                  data-bs-toggle=\"collapse\"
                  href=\"#dashboard\"
                  class=\"collapsed\"
                  aria-expanded=\"false\"
                >
                  <i class=\"fas fa-home\"></i>
                  <p>Dashboard</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse show\" id=\"dashboard\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"{{ path('admin_produit_attente') }}\">
                        <span class=\"sub-item\">Gérer Produits</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"{{ path('admin_commandes_index') }}\">
                        <span class=\"sub-item\">Historique des achats</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-section\">
                <span class=\"sidebar-mini-icon\">
                  <i class=\"fa fa-ellipsis-h\"></i>
                </span>
                <h4 class=\"text-section\">Components</h4>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#base\">
                  <i class=\"fas fa-layer-group\"></i>
                  <p>Base</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"base\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"components/avatars.html\">
                        <span class=\"sub-item\">Avatars</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/buttons.html\">
                        <span class=\"sub-item\">Buttons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/gridsystem.html\">
                        <span class=\"sub-item\">Grid System</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/panels.html\">
                        <span class=\"sub-item\">Panels</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/notifications.html\">
                        <span class=\"sub-item\">Notifications</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/sweetalert.html\">
                        <span class=\"sub-item\">Sweet Alert</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/font-awesome-icons.html\">
                        <span class=\"sub-item\">Font Awesome Icons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/simple-line-icons.html\">
                        <span class=\"sub-item\">Simple Line Icons</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"components/typography.html\">
                        <span class=\"sub-item\">Typography</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
                  <i class=\"fas fa-th-list\"></i>
                  <p>Sidebar Layouts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"sidebarLayouts\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"sidebar-style-2.html\">
                        <span class=\"sub-item\">Sidebar Style 2</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"icon-menu.html\">
                        <span class=\"sub-item\">Icon Menu</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#forms\">
                  <i class=\"fas fa-pen-square\"></i>
                  <p>Forms</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"forms\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"forms/forms.html\">
                        <span class=\"sub-item\">Basic Form</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#tables\">
                  <i class=\"fas fa-table\"></i>
                  <p>Tables</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"tables\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"tables/tables.html\">
                        <span class=\"sub-item\">Basic Table</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"tables/datatables.html\">
                        <span class=\"sub-item\">Datatables</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#maps\">
                  <i class=\"fas fa-map-marker-alt\"></i>
                  <p>Maps</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"maps\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"maps/googlemaps.html\">
                        <span class=\"sub-item\">Google Maps</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"maps/jsvectormap.html\">
                        <span class=\"sub-item\">Jsvectormap</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#charts\">
                  <i class=\"far fa-chart-bar\"></i>
                  <p>Charts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"charts\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"charts/charts.html\">
                        <span class=\"sub-item\">Chart Js</span>
                      </a>
                    </li>
                    <li>
                      <a href=\"charts/sparkline.html\">
                        <span class=\"sub-item\">Sparkline</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <li class=\"nav-item\">
                <a href=\"widgets.html\">
                  <i class=\"fas fa-desktop\"></i>
                  <p>Widgets</p>
                  <span class=\"badge badge-success\">4</span>
                </a>
              </li>
              <li class=\"nav-item\">
                <a href=\"../../documentation/index.html\">
                  <i class=\"fas fa-file\"></i>
                  <p>Documentation</p>
                  <span class=\"badge badge-secondary\">1</span>
                </a>
              </li>
              <li class=\"nav-item\">
                <a data-bs-toggle=\"collapse\" href=\"#submenu\">
                  <i class=\"fas fa-bars\"></i>
                  <p>Menu Levels</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse\" id=\"submenu\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a data-bs-toggle=\"collapse\" href=\"#subnav1\">
                        <span class=\"sub-item\">Level 1</span>
                        <span class=\"caret\"></span>
                      </a>
                      <div class=\"collapse\" id=\"subnav1\">
                        <ul class=\"nav nav-collapse subnav\">
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </li>
                    <li>
                      <a data-bs-toggle=\"collapse\" href=\"#subnav2\">
                        <span class=\"sub-item\">Level 1</span>
                        <span class=\"caret\"></span>
                      </a>
                      <div class=\"collapse\" id=\"subnav2\">
                        <ul class=\"nav nav-collapse subnav\">
                          <li>
                            <a href=\"#\">
                              <span class=\"sub-item\">Level 2</span>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </li>
                    <li>
                      <a href=\"#\">
                        <span class=\"sub-item\">Level 1</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- End Sidebar -->

      <div class=\"main-panel\">
        <div class=\"main-header\">
          <div class=\"main-header-logo\">
            <!-- Logo Header -->
            <div class=\"logo-header\" data-background-color=\"dark\">
              <a href=\"index.html\" class=\"logo\">
                <img
                  src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\"
                  alt=\"navbar brand\"
                  class=\"navbar-brand\"
                  height=\"20\"
                />
              </a>
              <div class=\"nav-toggle\">
                <button class=\"btn btn-toggle toggle-sidebar\">
                  <i class=\"gg-menu-right\"></i>
                </button>
                <button class=\"btn btn-toggle sidenav-toggler\">
                  <i class=\"gg-menu-left\"></i>
                </button>
              </div>
              <button class=\"topbar-toggler more\">
                <i class=\"gg-more-vertical-alt\"></i>
              </button>
            </div>
            <!-- End Logo Header -->
          </div>
          <!-- Navbar Header -->
          <nav
            class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\"
          >
            <div class=\"container-fluid\">
              <nav
                class=\"navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex\"
              >
                <div class=\"input-group\">
                  <div class=\"input-group-prepend\">
                    <button type=\"submit\" class=\"btn btn-search pe-1\">
                      <i class=\"fa fa-search search-icon\"></i>
                    </button>
                  </div>
                  <input
                    type=\"text\"
                    placeholder=\"Search ...\"
                    class=\"form-control\"
                  />
                </div>
              </nav>

              <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                <li
                  class=\"nav-item topbar-icon dropdown hidden-caret d-flex d-lg-none\"
                >
                  <a
                    class=\"nav-link dropdown-toggle\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    role=\"button\"
                    aria-expanded=\"false\"
                    aria-haspopup=\"true\"
                  >
                    <i class=\"fa fa-search\"></i>
                  </a>
                  <ul class=\"dropdown-menu dropdown-search animated fadeIn\">
                    <form class=\"navbar-left navbar-form nav-search\">
                      <div class=\"input-group\">
                        <input
                          type=\"text\"
                          placeholder=\"Search ...\"
                          class=\"form-control\"
                        />
                      </div>
                    </form>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link dropdown-toggle\"
                    href=\"#\"
                    id=\"messageDropdown\"
                    role=\"button\"
                    data-bs-toggle=\"dropdown\"
                    aria-haspopup=\"true\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fa fa-envelope\"></i>
                  </a>
                  <ul
                    class=\"dropdown-menu messages-notif-box animated fadeIn\"
                    aria-labelledby=\"messageDropdown\"
                  >
                    <li>
                      <div
                        class=\"dropdown-title d-flex justify-content-between align-items-center\"
                      >
                        Messages
                        <a href=\"#\" class=\"small\">Mark all as read</a>
                      </div>
                    </li>
                    <li>
                      <div class=\"message-notif-scroll scrollbar-outer\">
                        <div class=\"notif-center\">
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"{{ asset('back/img/jm_denis.jpg') }}\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Jimmy Denis</span>
                              <span class=\"block\"> How are you ? </span>
                              <span class=\"time\">5 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"{{ asset('back/img/chadengle.jpg') }}\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Chad</span>
                              <span class=\"block\"> Ok, Thanks ! </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"{{ asset('back/img/mlane.jpg') }}\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Jhon Doe</span>
                              <span class=\"block\">
                                Ready for the meeting today...
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"{{ asset('back/img/talha.jpg') }}\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"subject\">Talha</span>
                              <span class=\"block\"> Hi, Apa Kabar ? </span>
                              <span class=\"time\">17 minutes ago</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <a class=\"see-all\" href=\"javascript:void(0);\"
                        >See all messages<i class=\"fa fa-angle-right\"></i>
                      </a>
                    </li>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link dropdown-toggle\"
                    href=\"#\"
                    id=\"notifDropdown\"
                    role=\"button\"
                    data-bs-toggle=\"dropdown\"
                    aria-haspopup=\"true\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fa fa-bell\"></i>
                    <span class=\"notification\">4</span>
                  </a>
                  <ul
                    class=\"dropdown-menu notif-box animated fadeIn\"
                    aria-labelledby=\"notifDropdown\"
                  >
                    <li>
                      <div class=\"dropdown-title\">
                        You have 4 new notification
                      </div>
                    </li>
                    <li>
                      <div class=\"notif-scroll scrollbar-outer\">
                        <div class=\"notif-center\">
                          <a href=\"#\">
                            <div class=\"notif-icon notif-primary\">
                              <i class=\"fa fa-user-plus\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\"> New user registered </span>
                              <span class=\"time\">5 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-icon notif-success\">
                              <i class=\"fa fa-comment\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\">
                                Rahmad commented on Admin
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-img\">
                              <img
                                src=\"{{ asset('back/img/profile2.jpg') }}\"
                                alt=\"Img Profile\"
                              />
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\">
                                Reza send messages to you
                              </span>
                              <span class=\"time\">12 minutes ago</span>
                            </div>
                          </a>
                          <a href=\"#\">
                            <div class=\"notif-icon notif-danger\">
                              <i class=\"fa fa-heart\"></i>
                            </div>
                            <div class=\"notif-content\">
                              <span class=\"block\"> Farrah liked Admin </span>
                              <span class=\"time\">17 minutes ago</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <a class=\"see-all\" href=\"javascript:void(0);\"
                        >See all notifications<i class=\"fa fa-angle-right\"></i>
                      </a>
                    </li>
                  </ul>
                </li>
                <li class=\"nav-item topbar-icon dropdown hidden-caret\">
                  <a
                    class=\"nav-link\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    aria-expanded=\"false\"
                  >
                    <i class=\"fas fa-layer-group\"></i>
                  </a>
                  <div class=\"dropdown-menu quick-actions animated fadeIn\">
                    <div class=\"quick-actions-header\">
                      <span class=\"title mb-1\">Quick Actions</span>
                      <span class=\"subtitle op-7\">Shortcuts</span>
                    </div>
                    <div class=\"quick-actions-scroll scrollbar-outer\">
                      <div class=\"quick-actions-items\">
                        <div class=\"row m-0\">
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div class=\"avatar-item bg-danger rounded-circle\">
                                <i class=\"far fa-calendar-alt\"></i>
                              </div>
                              <span class=\"text\">Calendar</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-warning rounded-circle\"
                              >
                                <i class=\"fas fa-map\"></i>
                              </div>
                              <span class=\"text\">Maps</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div class=\"avatar-item bg-info rounded-circle\">
                                <i class=\"fas fa-file-excel\"></i>
                              </div>
                              <span class=\"text\">Reports</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-success rounded-circle\"
                              >
                                <i class=\"fas fa-envelope\"></i>
                              </div>
                              <span class=\"text\">Emails</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-primary rounded-circle\"
                              >
                                <i class=\"fas fa-file-invoice-dollar\"></i>
                              </div>
                              <span class=\"text\">Invoice</span>
                            </div>
                          </a>
                          <a class=\"col-6 col-md-4 p-0\" href=\"#\">
                            <div class=\"quick-actions-item\">
                              <div
                                class=\"avatar-item bg-secondary rounded-circle\"
                              >
                                <i class=\"fas fa-credit-card\"></i>
                              </div>
                              <span class=\"text\">Payments</span>
                            </div>
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>

                <li class=\"nav-item topbar-user dropdown hidden-caret\">
                  <a
                    class=\"dropdown-toggle profile-pic\"
                    data-bs-toggle=\"dropdown\"
                    href=\"#\"
                    aria-expanded=\"false\"
                  >
                    <div class=\"avatar-sm\">
                      <img
                        src=\"{{ asset('back/img/profile.jpg') }}\"
                        alt=\"...\"
                        class=\"avatar-img rounded-circle\"
                      />
                    </div>
                    <span class=\"profile-username\">
                      <span class=\"op-7\">Hi,</span>
                      <span class=\"fw-bold\">Hizrian</span>
                    </span>
                  </a>
                  <ul class=\"dropdown-menu dropdown-user animated fadeIn\">
                    <div class=\"dropdown-user-scroll scrollbar-outer\">
                      <li>
                        <div class=\"user-box\">
                          <div class=\"avatar-lg\">
                            <img
                              src=\"{{ asset('back/img/profile.jpg') }}\"
                              alt=\"image profile\"
                              class=\"avatar-img rounded\"
                            />
                          </div>
                          <div class=\"u-text\">
                            <h4>Hizrian</h4>
                            <p class=\"text-muted\">hello@example.com</p>
                            <a
                              href=\"profile.html\"
                              class=\"btn btn-xs btn-secondary btn-sm\"
                              >View Profile</a
                            >
                          </div>
                        </div>
                      </li>
                      <li>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">My Profile</a>
                        <a class=\"dropdown-item\" href=\"#\">My Balance</a>
                        <a class=\"dropdown-item\" href=\"#\">Inbox</a>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">Account Setting</a>
                        <div class=\"dropdown-divider\"></div>
                        <a class=\"dropdown-item\" href=\"#\">Logout</a>
                      </li>
                    </div>
                  </ul>
                </li>
              </ul>
            </div>
          </nav>
          <!-- End Navbar -->
        </div>

        <div class=\"container\">
          <div class=\"page-inner\">
            <div class=\"page-header\">
              <h4 class=\"page-title\">Produits en attente</h4>
              <ul class=\"breadcrumbs\">
                <li class=\"nav-home\">
                  <a href=\"{{ path('back_dashboard') }}\">
                    <i class=\"icon-home\"></i>
                  </a>
                </li>
                <li class=\"separator\">
                  <i class=\"icon-arrow-right\"></i>
                </li>
                <li class=\"nav-item\">
                  <a href=\"#\">Produits</a>
                </li>
              </ul>
            </div>
            <div class=\"page-category\">
               {% for message in app.flashes('success') %}
                    <div class=\"alert alert-success\">{{ message }}</div>
               {% endfor %}
               {% for message in app.flashes('warning') %}
                    <div class=\"alert alert-warning\">{{ message }}</div>
               {% endfor %}

               <div class=\"card\">
                 <div class=\"card-header\">
                   <div class=\"card-title\">Gérer les produits</div>
                 </div>
                 <div class=\"card-body\">
                   <div class=\"table-responsive\">
                     <table class=\"table table-hover\">
                       <thead>
                         <tr>
                           <th>Image</th>
                           <th>Nom</th>
                           <th>Catégorie</th>
                           <th>Prix</th>
                           <th>Stock</th>
                           <th>Statut</th>
                           <th>Description</th>
                           <th>Actions</th>
                         </tr>
                       </thead>
                       <tbody>
                         {% for produit in produits %}
                         <tr>
                           <td>
                             {% if produit.image %}
                               <img src=\"{{ asset('uploads/images/produits/' ~ produit.image) }}\" width=\"60\" style=\"border-radius:10px; object-fit:cover; height:60px;\" alt=\"{{ produit.nom }}\">
                             {% else %}
                               <img src=\"{{ asset('front/images/f1.png') }}\" width=\"60\" height=\"60\" style=\"border-radius:10px\" alt=\"{{ produit.nom }}\">
                             {% endif %}
                           </td>
                           <td><strong>{{ produit.nom }}</strong></td>
                           <td>{{ produit.categorie ? produit.categorie.nom : '' }}</td>
                           <td>{{ produit.prix }} €</td>
                           <td>{{ produit.stock }}</td>
                           <td style=\"font-weight: bold;\">
                             {% if produit.statut == 'disponible' %}
                               <span class=\"text-success\"><i class=\"fa fa-check-circle\"></i> Disponible</span>
                             {% elseif produit.statut == 'refuse' %}
                               <span class=\"text-danger\"><i class=\"fa fa-times-circle\"></i> Refusé</span>
                             {% elseif produit.statut == 'en_attente' %}
                               <span class=\"text-warning\"><i class=\"fa fa-clock\"></i> En attente</span>
                             {% elseif produit.statut == 'rupture' %}
                               <span class=\"text-secondary\"><i class=\"fa fa-exclamation-triangle\"></i> Rupture de stock</span>
                             {% else %}
                               <span class=\"text-muted\">{{ produit.statut|capitalize }}</span>
                             {% endif %}
                           </td>
                           <td>{{ produit.description|slice(0, 50) }}{% if produit.description|length > 50 %}...{% endif %}</td>
                           <td>
                             {% if produit.statut == 'en_attente' %}
                             <form action=\"{{ path('admin_produit_accepter', {'id': produit.id}) }}\" method=\"post\" class=\"d-inline\">
                                <button type=\"submit\" class=\"btn btn-success btn-sm\"><i class=\"fa fa-check\"></i> Accepter</button>
                             </form>
                             <form action=\"{{ path('admin_produit_refuser', {'id': produit.id}) }}\" method=\"post\" class=\"d-inline\" onsubmit=\"return confirm('Êtes-vous sûr de vouloir refuser ce produit ?');\">
                                <button type=\"submit\" class=\"btn btn-danger btn-sm\"><i class=\"fa fa-times\"></i> Refuser</button>
                             </form>
                             {% endif %}
                             <a href=\"{{ path('app_produit_edit', {'id': produit.id, 'from': 'admin'}) }}\" class=\"btn btn-secondary btn-sm mt-1 mb-1\"><i class=\"fa fa-edit\"></i> Modifier</a>
                             {{ include('produit/_delete_form.html.twig', {'button_class': 'btn btn-dark btn-sm d-inline'}) }}
                           </td>
                         </tr>
                         {% else %}
                         <tr>
                            <td colspan=\"8\" class=\"text-center py-4\">
                                <i class=\"fa fa-info-circle fa-2x text-muted mb-2\"></i><br>
                                Aucun produit trouvé.
                            </td>
                         </tr>
                         {% endfor %}
                       </tbody>
                     </table>
                   </div>
                 </div>
               </div>
            </div>
          </div>
        </div>

        <footer class=\"footer\">
          <div class=\"container-fluid d-flex justify-content-between\">
            <nav class=\"pull-left\">
              <ul class=\"nav\">
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"http://www.themekita.com\">
                    ThemeKita
                  </a>
                </li>
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"#\"> Help </a>
                </li>
                <li class=\"nav-item\">
                  <a class=\"nav-link\" href=\"#\"> Licenses </a>
                </li>
              </ul>
            </nav>
            <div class=\"copyright\">
              2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by
              <a href=\"http://www.themekita.com\">ThemeKita</a>
            </div>
            <div>
              Distributed by
              <a target=\"_blank\" href=\"https://themewagon.com/\">ThemeWagon</a>.
            </div>
          </div>
        </footer>
      </div>
    </div>
    <!--   Core JS Files   -->
    <script src=\"{{ asset('back/js/core/jquery-3.7.1.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/popper.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/bootstrap.min.js') }}\"></script>

    <!-- jQuery Scrollbar -->
    <script src=\"{{ asset('back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js') }}\"></script>

    <!-- Chart JS -->
    <script src=\"{{ asset('back/js/plugin/chart.js/chart.min.js') }}\"></script>

    <!-- jQuery Sparkline -->
    <script src=\"{{ asset('back/js/plugin/jquery.sparkline/jquery.sparkline.min.js') }}\"></script>

    <!-- Chart Circle -->
    <script src=\"{{ asset('back/js/plugin/chart-circle/circles.min.js') }}\"></script>

    <!-- Datatables -->
    <script src=\"{{ asset('back/js/plugin/datatables/datatables.min.js') }}\"></script>

    <!-- Bootstrap Notify -->
    <script src=\"{{ asset('back/js/plugin/bootstrap-notify/bootstrap-notify.min.js') }}\"></script>

    <!-- jQuery Vector Maps -->
    <script src=\"{{ asset('back/js/plugin/jsvectormap/jsvectormap.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/plugin/jsvectormap/world.js') }}\"></script>

    <!-- Google Maps Plugin -->
    <script src=\"{{ asset('back/js/plugin/gmaps/gmaps.js') }}\"></script>

    <!-- Sweet Alert -->
    <script src=\"{{ asset('back/js/plugin/sweetalert/sweetalert.min.js') }}\"></script>

    <!-- Kaiadmin JS -->
    <script src=\"{{ asset('back/js/kaiadmin.min.js') }}\"></script>
  </body>
</html>
", "back/produits_attente.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\back\\produits_attente.html.twig");
    }
}
