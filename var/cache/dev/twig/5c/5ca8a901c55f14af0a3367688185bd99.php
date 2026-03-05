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

/* back/sidebar-style-2.html.twig */
class __TwigTemplate_885b2715da7f021a10efee34fb3f0fa8 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/sidebar-style-2.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/sidebar-style-2.html.twig"));

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
      <div class=\"sidebar sidebar-style-2\" data-background-color=\"dark\">
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
              <li class=\"nav-item\">
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
                <div class=\"collapse\" id=\"dashboard\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"../demo1/index.html\">
                        <span class=\"sub-item\">Dashboard 1</span>
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
              <li class=\"nav-item active submenu\">
                <a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
                  <i class=\"fas fa-th-list\"></i>
                  <p>Sidebar Layouts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse show\" id=\"sidebarLayouts\">
                  <ul class=\"nav nav-collapse\">
                    <li class=\"active\">
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
        // line 337
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
        // line 435
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
        // line 448
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
        // line 461
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
        // line 476
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
        // line 544
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
        // line 662
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
        // line 678
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
            <div
              class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\"
            >
              <div>
                <h3 class=\"fw-bold mb-3\">Dashboard</h3>
                <h6 class=\"op-7 mb-2\">Free Bootstrap 5 Admin Dashboard</h6>
              </div>
              <div class=\"ms-md-auto py-2 py-md-0\">
                <a href=\"#\" class=\"btn btn-label-info btn-round me-2\">Manage</a>
                <a href=\"#\" class=\"btn btn-primary btn-round\">Add Customer</a>
              </div>
            </div>
            <div class=\"row row-card-no-pd\">
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>Todays Income</b></h6>
                        <p class=\"text-muted\">All Customs Value</p>
                      </div>
                      <h4 class=\"text-info fw-bold\">\$170</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-info w-75\"
                        role=\"progressbar\"
                        aria-valuenow=\"75\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">75%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>Total Revenue</b></h6>
                        <p class=\"text-muted\">All Customs Value</p>
                      </div>
                      <h4 class=\"text-success fw-bold\">\$120</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-success w-25\"
                        role=\"progressbar\"
                        aria-valuenow=\"25\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">25%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>New Orders</b></h6>
                        <p class=\"text-muted\">Fresh Order Amount</p>
                      </div>
                      <h4 class=\"text-danger fw-bold\">15</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-danger w-50\"
                        role=\"progressbar\"
                        aria-valuenow=\"50\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">50%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>New Users</b></h6>
                        <p class=\"text-muted\">Joined New User</p>
                      </div>
                      <h4 class=\"text-secondary fw-bold\">12</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-secondary w-25\"
                        role=\"progressbar\"
                        aria-valuenow=\"25\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">25%</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row\">
              <div class=\"col-md-8\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">User Statistics</div>
                      <div class=\"card-tools\">
                        <a
                          href=\"#\"
                          class=\"btn btn-label-success btn-round btn-sm me-2\"
                        >
                          <span class=\"btn-label\">
                            <i class=\"fa fa-pencil\"></i>
                          </span>
                          Export
                        </a>
                        <a href=\"#\" class=\"btn btn-label-info btn-round btn-sm\">
                          <span class=\"btn-label\">
                            <i class=\"fa fa-print\"></i>
                          </span>
                          Print
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"chart-container\" style=\"min-height: 375px\">
                      <canvas id=\"statisticsChart\"></canvas>
                    </div>
                    <div id=\"myChartLegend\"></div>
                  </div>
                </div>
              </div>
              <div class=\"col-md-4\">
                <div class=\"card card-primary\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">Daily Sales</div>
                      <div class=\"card-tools\">
                        <div class=\"dropdown\">
                          <button
                            class=\"btn btn-sm btn-label-light dropdown-toggle\"
                            type=\"button\"
                            id=\"dropdownMenuButton\"
                            data-bs-toggle=\"dropdown\"
                            aria-haspopup=\"true\"
                            aria-expanded=\"false\"
                          >
                            Export
                          </button>
                          <div
                            class=\"dropdown-menu\"
                            aria-labelledby=\"dropdownMenuButton\"
                          >
                            <a class=\"dropdown-item\" href=\"#\">Action</a>
                            <a class=\"dropdown-item\" href=\"#\">Another action</a>
                            <a class=\"dropdown-item\" href=\"#\"
                              >Something else here</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class=\"card-category\">March 25 - April 02</div>
                  </div>
                  <div class=\"card-body pb-0\">
                    <div class=\"mb-4 mt-2\">
                      <h1>\$4,578.58</h1>
                    </div>
                    <div class=\"pull-in\">
                      <canvas id=\"dailySalesChart\"></canvas>
                    </div>
                  </div>
                </div>
                <div class=\"card\">
                  <div class=\"card-body pb-0\">
                    <div class=\"h1 fw-bold float-end text-primary\">+5%</div>
                    <h2 class=\"mb-2\">17</h2>
                    <p class=\"text-muted\">Users online</p>
                    <div class=\"pull-in sparkline-fix\">
                      <div id=\"lineChart\"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-primary\">+5%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">17</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">Users online</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-danger\">-3%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">27</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">New Users</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart2\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-warning\">+7%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">213</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">Transactions</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart3\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div> -->
            <!-- <div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<div class=\"card-title\">Top Products</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 966
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct.svg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">CSS</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">Cascading Style Sheets</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$17</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 979
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct.svg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">J.CO Donuts</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">The Best Donuts</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$300</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 992
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct3.svg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">Ready Pro</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">Bootstrap 5 Admin Dashboard</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$350</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in\">
\t\t\t\t\t\t\t\t\t\t<canvas id=\"topProductsChart\"></canvas>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<div class=\"card-title fw-mediumbold\">Suggested People</div>
\t\t\t\t\t\t\t\t\t<div class=\"card-list\">
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1016
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Jimmy Denis</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Graphic Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1028
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/chadengle.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Chad</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">CEO Zeleaf</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1040
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/talha.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Talha</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Front End Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1052
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/mlane.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">John Doe</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Back End Developer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1064
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/talha.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Talha</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Front End Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 1076
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Jimmy Denis</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Graphic Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card card-primary bg-primary-gradient\">
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-3 b-b1 pb-2 mb-4 fw-bold\">Active user right now</h5>
\t\t\t\t\t\t\t\t\t<h1 class=\"mb-4 fw-bold\">17</h1>
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-3 b-b1 pb-2 mb-5 fw-bold\">Page view per minutes</h5>
\t\t\t\t\t\t\t\t\t<div id=\"activeUsersChart\"></div>
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-5 pb-3 mb-0 fw-bold\">Top active pages</h5>
\t\t\t\t\t\t\t\t\t<ul class=\"list-unstyled\">
\t\t\t\t\t\t\t\t\t\t<li class=\"d-flex justify-content-between pb-1 pt-1\"><small>/product/readypro/index.html</small> <span>7</span></li>
\t\t\t\t\t\t\t\t\t\t<li class=\"d-flex justify-content-between pb-1 pt-1\"><small>/product/kaiadmin/demo.html</small> <span>10</span></li>
\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div> -->
            <div class=\"row\">
              <div class=\"col-md-8\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-title\">Page visits</div>
                  </div>
                  <div class=\"card-body p-0\">
                    <div class=\"table-responsive\">
                      <!-- Projects table -->
                      <table class=\"table align-items-center mb-0\">
                        <thead class=\"thead-light\">
                          <tr>
                            <th scope=\"col\">Page name</th>
                            <th scope=\"col\">Visitors</th>
                            <th scope=\"col\">Unique users</th>
                            <th scope=\"col\">Bounce rate</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <th scope=\"row\">/kaiadmin/</th>
                            <td>4,569</td>
                            <td>340</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/index.html</th>
                            <td>3,985</td>
                            <td>319</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/charts.html</th>
                            <td>3,513</td>
                            <td>294</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              36,49%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/tables.html</th>
                            <td>2,050</td>
                            <td>147</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              50,87%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/profile.html</th>
                            <td>1,795</td>
                            <td>190</td>
                            <td>
                              <i class=\"fas fa-arrow-down text-danger me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/</th>
                            <td>4,569</td>
                            <td>340</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/index.html</th>
                            <td>3,985</td>
                            <td>319</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              46,53%
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-md-4\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-title\">Top Products</div>
                  </div>
                  <div class=\"card-body pb-0\">
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"";
        // line 1209
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct.svg"), "html", null, true);
        yield "\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">CSS</h6>
                        <small class=\"text-muted\">Cascading Style Sheets</small>
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$17</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"";
        // line 1226
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct.svg"), "html", null, true);
        yield "\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">J.CO Donuts</h6>
                        <small class=\"text-muted\">The Best Donuts</small>
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$300</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"";
        // line 1243
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/logoproduct3.svg"), "html", null, true);
        yield "\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">Ready Pro</h6>
                        <small class=\"text-muted\"
                          >Bootstrap 5 Admin Dashboard</small
                        >
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$350</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"pull-in\">
                      <canvas id=\"topProductsChart\"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row row-card-no-pd\">
              <div class=\"col-md-12\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row card-tools-still-right\">
                      <h4 class=\"card-title\">Users Geolocation</h4>
                      <div class=\"card-tools\">
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs\"
                        >
                          <span class=\"fa fa-angle-down\"></span>
                        </button>
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs btn-refresh-card\"
                        >
                          <span class=\"fa fa-sync-alt\"></span>
                        </button>
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs\"
                        >
                          <span class=\"fa fa-times\"></span>
                        </button>
                      </div>
                    </div>
                    <p class=\"card-category\">
                      Map of the distribution of users around the world
                    </p>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"row\">
                      <div class=\"col-md-6\">
                        <div class=\"table-responsive table-hover table-sales\">
                          <table class=\"table\">
                            <tbody>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1304
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/id.png"), "html", null, true);
        yield "\"
                                      alt=\"indonesia\"
                                    />
                                  </div>
                                </td>
                                <td>Indonesia</td>
                                <td class=\"text-end\">2.320</td>
                                <td class=\"text-end\">42.18%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1317
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/us.png"), "html", null, true);
        yield "\"
                                      alt=\"united states\"
                                    />
                                  </div>
                                </td>
                                <td>USA</td>
                                <td class=\"text-end\">240</td>
                                <td class=\"text-end\">4.36%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1330
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/au.png"), "html", null, true);
        yield "\"
                                      alt=\"australia\"
                                    />
                                  </div>
                                </td>
                                <td>Australia</td>
                                <td class=\"text-end\">119</td>
                                <td class=\"text-end\">2.16%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1343
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/ru.png"), "html", null, true);
        yield "\"
                                      alt=\"russia\"
                                    />
                                  </div>
                                </td>
                                <td>Russia</td>
                                <td class=\"text-end\">1.081</td>
                                <td class=\"text-end\">19.65%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1356
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/cn.png"), "html", null, true);
        yield "\"
                                      alt=\"china\"
                                    />
                                  </div>
                                </td>
                                <td>China</td>
                                <td class=\"text-end\">1.100</td>
                                <td class=\"text-end\">20%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"";
        // line 1369
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/flags/br.png"), "html", null, true);
        yield "\"
                                      alt=\"brazil\"
                                    />
                                  </div>
                                </td>
                                <td>Brasil</td>
                                <td class=\"text-end\">640</td>
                                <td class=\"text-end\">11.63%</td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      <div class=\"col-md-6\">
                        <div class=\"mapcontainer\">
                          <div
                            id=\"world-map\"
                            class=\"w-100\"
                            style=\"height: 300px\"
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row\">
              <div class=\"col-md-6\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row card-tools-still-right\">
                      <div class=\"card-title\">Recent Activity</div>
                      <div class=\"card-tools\">
                        <div class=\"dropdown\">
                          <button
                            class=\"btn btn-icon btn-clean\"
                            type=\"button\"
                            id=\"dropdownMenuButton\"
                            data-bs-toggle=\"dropdown\"
                            aria-haspopup=\"true\"
                            aria-expanded=\"false\"
                          >
                            <i class=\"fas fa-ellipsis-h\"></i>
                          </button>
                          <div
                            class=\"dropdown-menu\"
                            aria-labelledby=\"dropdownMenuButton\"
                          >
                            <a class=\"dropdown-item\" href=\"#\">Action</a>
                            <a class=\"dropdown-item\" href=\"#\">Another action</a>
                            <a class=\"dropdown-item\" href=\"#\"
                              >Something else here</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <ol class=\"activity-feed\">
                      <li class=\"feed-item feed-item-secondary\">
                        <time class=\"date\" datetime=\"9-25\">Sep 25</time>
                        <span class=\"text\"
                          >Responded to need
                          <a href=\"#\">\"Volunteer opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-success\">
                        <time class=\"date\" datetime=\"9-24\">Sep 24</time>
                        <span class=\"text\"
                          >Added an interest
                          <a href=\"#\">\"Volunteer Activities\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-info\">
                        <time class=\"date\" datetime=\"9-23\">Sep 23</time>
                        <span class=\"text\"
                          >Joined the group
                          <a href=\"single-group.php\"
                            >\"Boardsmanship Forum\"</a
                          ></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-warning\">
                        <time class=\"date\" datetime=\"9-21\">Sep 21</time>
                        <span class=\"text\"
                          >Responded to need
                          <a href=\"#\">\"In-Kind Opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-danger\">
                        <time class=\"date\" datetime=\"9-18\">Sep 18</time>
                        <span class=\"text\"
                          >Created need
                          <a href=\"#\">\"Volunteer Opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item\">
                        <time class=\"date\" datetime=\"9-17\">Sep 17</time>
                        <span class=\"text\"
                          >Attending the event
                          <a href=\"single-event.php\">\"Some New Event\"</a></span
                        >
                      </li>
                    </ol>
                  </div>
                </div>
              </div>
              <div class=\"col-md-6\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">Support Tickets</div>
                      <div class=\"card-tools\">
                        <ul
                          class=\"nav nav-pills nav-secondary nav-pills-no-bd nav-sm\"
                          id=\"pills-tab\"
                          role=\"tablist\"
                        >
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link\"
                              id=\"pills-today\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-today\"
                              role=\"tab\"
                              aria-selected=\"true\"
                              >Today</a
                            >
                          </li>
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link active\"
                              id=\"pills-week\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-week\"
                              role=\"tab\"
                              aria-selected=\"false\"
                              >Week</a
                            >
                          </li>
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link\"
                              id=\"pills-month\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-month\"
                              role=\"tab\"
                              aria-selected=\"false\"
                              >Month</a
                            >
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-online\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-info\"
                          >J</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Joko Subianto
                          <span class=\"text-warning ps-3\">pending</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I am facing some trouble with my viewport. When i
                          start my</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">8:40 PM</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-offline\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-secondary\"
                          >P</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Prabowo Widodo
                          <span class=\"text-success ps-3\">open</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I have some query regarding the license issue.</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">1 Day Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-away\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-danger\"
                          >L</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Lee Chong Wei
                          <span class=\"text-muted ps-3\">closed</span>
                        </h6>
                        <span class=\"text-muted\"
                          >Is there any update plan for RTL version near
                          future?</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Days Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-offline\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-secondary\"
                          >P</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Peter Parker
                          <span class=\"text-success ps-3\">open</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I have some query regarding the license issue.</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Day Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-away\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-danger\"
                          >L</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Logan Paul <span class=\"text-muted ps-3\">closed</span>
                        </h6>
                        <span class=\"text-muted\"
                          >Is there any update plan for RTL version near
                          future?</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Days Ago</small>
                      </div>
                    </div>
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

      <!-- Custom template | don't include it in your project! -->
      <div class=\"custom-template\">
        <div class=\"title\">Settings</div>
        <div class=\"custom-content\">
          <div class=\"switcher\">
            <div class=\"switch-block\">
              <h4>Logo Header</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"selected changeLogoHeaderColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"purple\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"light-blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"green\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"orange\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"red\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"white\"
                ></button>
                <br />
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"dark2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"purple2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"light-blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"green2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"orange2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"red2\"
                ></button>
              </div>
            </div>
            <div class=\"switch-block\">
              <h4>Navbar Header</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"purple\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"light-blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"green\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"orange\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"red\"
                ></button>
                <button
                  type=\"button\"
                  class=\"selected changeTopBarColor\"
                  data-color=\"white\"
                ></button>
                <br />
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"dark2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"purple2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"light-blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"green2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"orange2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"red2\"
                ></button>
              </div>
            </div>
            <div class=\"switch-block\">
              <h4>Sidebar</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"changeSideBarColor\"
                  data-color=\"white\"
                ></button>
                <button
                  type=\"button\"
                  class=\"selected changeSideBarColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeSideBarColor\"
                  data-color=\"dark2\"
                ></button>
              </div>
            </div>
          </div>
        </div>
        <div class=\"custom-toggle\">
          <i class=\"icon-settings\"></i>
        </div>
      </div>
      <!-- End Custom template -->
    </div>
    <!--   Core JS Files   -->
    <script src=\"";
        // line 1865
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 1866
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 1867
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Scrollbar -->
    <script src=\"";
        // line 1870
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Chart JS -->
    <script src=\"";
        // line 1873
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart.js/chart.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Sparkline -->
    <script src=\"";
        // line 1876
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery.sparkline/jquery.sparkline.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Chart Circle -->
    <script src=\"";
        // line 1879
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart-circle/circles.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Datatables -->
    <script src=\"";
        // line 1882
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/datatables/datatables.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Bootstrap Notify -->
    <script src=\"";
        // line 1885
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/bootstrap-notify/bootstrap-notify.min.js"), "html", null, true);
        yield "\"></script>

    <!-- jQuery Vector Maps -->
    <script src=\"";
        // line 1888
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/jsvectormap.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 1889
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/world.js"), "html", null, true);
        yield "\"></script>

    <!-- Sweet Alert -->
    <script src=\"";
        // line 1892
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/sweetalert/sweetalert.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Kaiadmin JS -->
    <script src=\"";
        // line 1895
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/kaiadmin.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Kaiadmin DEMO methods, don't include it in your project! -->
    <script src=\"";
        // line 1898
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/setting-demo.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 1899
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/demo.js"), "html", null, true);
        yield "\"></script>
    <script>
      \$(\"#lineChart\").sparkline([102, 109, 120, 99, 110, 105, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#177dff\",
        fillColor: \"rgba(23, 125, 255, 0.14)\",
      });

      \$(\"#lineChart2\").sparkline([99, 125, 122, 105, 110, 124, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#f3545d\",
        fillColor: \"rgba(243, 84, 93, .14)\",
      });

      \$(\"#lineChart3\").sparkline([105, 103, 123, 100, 95, 105, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#ffa534\",
        fillColor: \"rgba(255, 165, 52, .14)\",
      });
    </script>
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
        return "back/sidebar-style-2.html.twig";
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
        return array (  2092 => 1899,  2088 => 1898,  2082 => 1895,  2076 => 1892,  2070 => 1889,  2066 => 1888,  2060 => 1885,  2054 => 1882,  2048 => 1879,  2042 => 1876,  2036 => 1873,  2030 => 1870,  2024 => 1867,  2020 => 1866,  2016 => 1865,  1517 => 1369,  1501 => 1356,  1485 => 1343,  1469 => 1330,  1453 => 1317,  1437 => 1304,  1373 => 1243,  1353 => 1226,  1333 => 1209,  1197 => 1076,  1182 => 1064,  1167 => 1052,  1152 => 1040,  1137 => 1028,  1122 => 1016,  1095 => 992,  1079 => 979,  1063 => 966,  772 => 678,  753 => 662,  632 => 544,  561 => 476,  543 => 461,  527 => 448,  511 => 435,  410 => 337,  123 => 53,  109 => 42,  103 => 39,  99 => 38,  95 => 37,  83 => 28,  69 => 17,  61 => 12,  48 => 1,);
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
      <div class=\"sidebar sidebar-style-2\" data-background-color=\"dark\">
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
              <li class=\"nav-item\">
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
                <div class=\"collapse\" id=\"dashboard\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"../demo1/index.html\">
                        <span class=\"sub-item\">Dashboard 1</span>
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
              <li class=\"nav-item active submenu\">
                <a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
                  <i class=\"fas fa-th-list\"></i>
                  <p>Sidebar Layouts</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse show\" id=\"sidebarLayouts\">
                  <ul class=\"nav nav-collapse\">
                    <li class=\"active\">
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
            <div
              class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\"
            >
              <div>
                <h3 class=\"fw-bold mb-3\">Dashboard</h3>
                <h6 class=\"op-7 mb-2\">Free Bootstrap 5 Admin Dashboard</h6>
              </div>
              <div class=\"ms-md-auto py-2 py-md-0\">
                <a href=\"#\" class=\"btn btn-label-info btn-round me-2\">Manage</a>
                <a href=\"#\" class=\"btn btn-primary btn-round\">Add Customer</a>
              </div>
            </div>
            <div class=\"row row-card-no-pd\">
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>Todays Income</b></h6>
                        <p class=\"text-muted\">All Customs Value</p>
                      </div>
                      <h4 class=\"text-info fw-bold\">\$170</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-info w-75\"
                        role=\"progressbar\"
                        aria-valuenow=\"75\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">75%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>Total Revenue</b></h6>
                        <p class=\"text-muted\">All Customs Value</p>
                      </div>
                      <h4 class=\"text-success fw-bold\">\$120</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-success w-25\"
                        role=\"progressbar\"
                        aria-valuenow=\"25\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">25%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>New Orders</b></h6>
                        <p class=\"text-muted\">Fresh Order Amount</p>
                      </div>
                      <h4 class=\"text-danger fw-bold\">15</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-danger w-50\"
                        role=\"progressbar\"
                        aria-valuenow=\"50\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">50%</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-12 col-sm-6 col-md-6 col-xl-3\">
                <div class=\"card\">
                  <div class=\"card-body\">
                    <div class=\"d-flex justify-content-between\">
                      <div>
                        <h6><b>New Users</b></h6>
                        <p class=\"text-muted\">Joined New User</p>
                      </div>
                      <h4 class=\"text-secondary fw-bold\">12</h4>
                    </div>
                    <div class=\"progress progress-sm\">
                      <div
                        class=\"progress-bar bg-secondary w-25\"
                        role=\"progressbar\"
                        aria-valuenow=\"25\"
                        aria-valuemin=\"0\"
                        aria-valuemax=\"100\"
                      ></div>
                    </div>
                    <div class=\"d-flex justify-content-between mt-2\">
                      <p class=\"text-muted mb-0\">Change</p>
                      <p class=\"text-muted mb-0\">25%</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row\">
              <div class=\"col-md-8\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">User Statistics</div>
                      <div class=\"card-tools\">
                        <a
                          href=\"#\"
                          class=\"btn btn-label-success btn-round btn-sm me-2\"
                        >
                          <span class=\"btn-label\">
                            <i class=\"fa fa-pencil\"></i>
                          </span>
                          Export
                        </a>
                        <a href=\"#\" class=\"btn btn-label-info btn-round btn-sm\">
                          <span class=\"btn-label\">
                            <i class=\"fa fa-print\"></i>
                          </span>
                          Print
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"chart-container\" style=\"min-height: 375px\">
                      <canvas id=\"statisticsChart\"></canvas>
                    </div>
                    <div id=\"myChartLegend\"></div>
                  </div>
                </div>
              </div>
              <div class=\"col-md-4\">
                <div class=\"card card-primary\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">Daily Sales</div>
                      <div class=\"card-tools\">
                        <div class=\"dropdown\">
                          <button
                            class=\"btn btn-sm btn-label-light dropdown-toggle\"
                            type=\"button\"
                            id=\"dropdownMenuButton\"
                            data-bs-toggle=\"dropdown\"
                            aria-haspopup=\"true\"
                            aria-expanded=\"false\"
                          >
                            Export
                          </button>
                          <div
                            class=\"dropdown-menu\"
                            aria-labelledby=\"dropdownMenuButton\"
                          >
                            <a class=\"dropdown-item\" href=\"#\">Action</a>
                            <a class=\"dropdown-item\" href=\"#\">Another action</a>
                            <a class=\"dropdown-item\" href=\"#\"
                              >Something else here</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class=\"card-category\">March 25 - April 02</div>
                  </div>
                  <div class=\"card-body pb-0\">
                    <div class=\"mb-4 mt-2\">
                      <h1>\$4,578.58</h1>
                    </div>
                    <div class=\"pull-in\">
                      <canvas id=\"dailySalesChart\"></canvas>
                    </div>
                  </div>
                </div>
                <div class=\"card\">
                  <div class=\"card-body pb-0\">
                    <div class=\"h1 fw-bold float-end text-primary\">+5%</div>
                    <h2 class=\"mb-2\">17</h2>
                    <p class=\"text-muted\">Users online</p>
                    <div class=\"pull-in sparkline-fix\">
                      <div id=\"lineChart\"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-primary\">+5%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">17</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">Users online</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-danger\">-3%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">27</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">New Users</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart2\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"h1 fw-bold float-end text-warning\">+7%</div>
\t\t\t\t\t\t\t\t\t<h2 class=\"mb-2\">213</h2>
\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">Transactions</p>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in sparkline-fix\">
\t\t\t\t\t\t\t\t\t\t<div id=\"lineChart3\"></div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div> -->
            <!-- <div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<div class=\"card-title\">Top Products</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body pb-0\">
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/logoproduct.svg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">CSS</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">Cascading Style Sheets</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$17</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/logoproduct.svg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">J.CO Donuts</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">The Best Donuts</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$300</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"d-flex\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/logoproduct3.svg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"flex-1 pt-1 ms-2\">
\t\t\t\t\t\t\t\t\t\t\t<h6 class=\"fw-bold mb-1\">Ready Pro</h6>
\t\t\t\t\t\t\t\t\t\t\t<small class=\"text-muted\">Bootstrap 5 Admin Dashboard</small>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"d-flex ms-auto align-items-center\">
\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text-info fw-bold\">+\$350</h4>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"separator-dashed\"></div>
\t\t\t\t\t\t\t\t\t<div class=\"pull-in\">
\t\t\t\t\t\t\t\t\t\t<canvas id=\"topProductsChart\"></canvas>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<div class=\"card-title fw-mediumbold\">Suggested People</div>
\t\t\t\t\t\t\t\t\t<div class=\"card-list\">
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Jimmy Denis</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Graphic Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/chadengle.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Chad</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">CEO Zeleaf</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/talha.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Talha</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Front End Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/mlane.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">John Doe</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Back End Developer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/talha.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Talha</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Front End Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t<div class=\"item-list\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"info-user ms-3\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"username\">Jimmy Denis</div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"status\">Graphic Designer</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-icon btn-primary btn-round btn-xs\">
\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-plus\"></i>
\t\t\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-4\">
\t\t\t\t\t\t\t<div class=\"card card-primary bg-primary-gradient\">
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-3 b-b1 pb-2 mb-4 fw-bold\">Active user right now</h5>
\t\t\t\t\t\t\t\t\t<h1 class=\"mb-4 fw-bold\">17</h1>
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-3 b-b1 pb-2 mb-5 fw-bold\">Page view per minutes</h5>
\t\t\t\t\t\t\t\t\t<div id=\"activeUsersChart\"></div>
\t\t\t\t\t\t\t\t\t<h5 class=\"mt-5 pb-3 mb-0 fw-bold\">Top active pages</h5>
\t\t\t\t\t\t\t\t\t<ul class=\"list-unstyled\">
\t\t\t\t\t\t\t\t\t\t<li class=\"d-flex justify-content-between pb-1 pt-1\"><small>/product/readypro/index.html</small> <span>7</span></li>
\t\t\t\t\t\t\t\t\t\t<li class=\"d-flex justify-content-between pb-1 pt-1\"><small>/product/kaiadmin/demo.html</small> <span>10</span></li>
\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div> -->
            <div class=\"row\">
              <div class=\"col-md-8\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-title\">Page visits</div>
                  </div>
                  <div class=\"card-body p-0\">
                    <div class=\"table-responsive\">
                      <!-- Projects table -->
                      <table class=\"table align-items-center mb-0\">
                        <thead class=\"thead-light\">
                          <tr>
                            <th scope=\"col\">Page name</th>
                            <th scope=\"col\">Visitors</th>
                            <th scope=\"col\">Unique users</th>
                            <th scope=\"col\">Bounce rate</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <th scope=\"row\">/kaiadmin/</th>
                            <td>4,569</td>
                            <td>340</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/index.html</th>
                            <td>3,985</td>
                            <td>319</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/charts.html</th>
                            <td>3,513</td>
                            <td>294</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              36,49%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/tables.html</th>
                            <td>2,050</td>
                            <td>147</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              50,87%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/profile.html</th>
                            <td>1,795</td>
                            <td>190</td>
                            <td>
                              <i class=\"fas fa-arrow-down text-danger me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/</th>
                            <td>4,569</td>
                            <td>340</td>
                            <td>
                              <i class=\"fas fa-arrow-up text-success me-3\"></i>
                              46,53%
                            </td>
                          </tr>
                          <tr>
                            <th scope=\"row\">/kaiadmin/index.html</th>
                            <td>3,985</td>
                            <td>319</td>
                            <td>
                              <i
                                class=\"fas fa-arrow-down text-warning me-3\"
                              ></i>
                              46,53%
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <div class=\"col-md-4\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-title\">Top Products</div>
                  </div>
                  <div class=\"card-body pb-0\">
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"{{ asset('back/img/logoproduct.svg') }}\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">CSS</h6>
                        <small class=\"text-muted\">Cascading Style Sheets</small>
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$17</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"{{ asset('back/img/logoproduct.svg') }}\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">J.CO Donuts</h6>
                        <small class=\"text-muted\">The Best Donuts</small>
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$300</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar\">
                        <img
                          src=\"{{ asset('back/img/logoproduct3.svg') }}\"
                          alt=\"...\"
                          class=\"avatar-img rounded-circle\"
                        />
                      </div>
                      <div class=\"flex-1 pt-1 ms-2\">
                        <h6 class=\"fw-bold mb-1\">Ready Pro</h6>
                        <small class=\"text-muted\"
                          >Bootstrap 5 Admin Dashboard</small
                        >
                      </div>
                      <div class=\"d-flex ms-auto align-items-center\">
                        <h4 class=\"text-info fw-bold\">+\$350</h4>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"pull-in\">
                      <canvas id=\"topProductsChart\"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row row-card-no-pd\">
              <div class=\"col-md-12\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row card-tools-still-right\">
                      <h4 class=\"card-title\">Users Geolocation</h4>
                      <div class=\"card-tools\">
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs\"
                        >
                          <span class=\"fa fa-angle-down\"></span>
                        </button>
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs btn-refresh-card\"
                        >
                          <span class=\"fa fa-sync-alt\"></span>
                        </button>
                        <button
                          class=\"btn btn-icon btn-link btn-primary btn-xs\"
                        >
                          <span class=\"fa fa-times\"></span>
                        </button>
                      </div>
                    </div>
                    <p class=\"card-category\">
                      Map of the distribution of users around the world
                    </p>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"row\">
                      <div class=\"col-md-6\">
                        <div class=\"table-responsive table-hover table-sales\">
                          <table class=\"table\">
                            <tbody>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/id.png') }}\"
                                      alt=\"indonesia\"
                                    />
                                  </div>
                                </td>
                                <td>Indonesia</td>
                                <td class=\"text-end\">2.320</td>
                                <td class=\"text-end\">42.18%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/us.png') }}\"
                                      alt=\"united states\"
                                    />
                                  </div>
                                </td>
                                <td>USA</td>
                                <td class=\"text-end\">240</td>
                                <td class=\"text-end\">4.36%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/au.png') }}\"
                                      alt=\"australia\"
                                    />
                                  </div>
                                </td>
                                <td>Australia</td>
                                <td class=\"text-end\">119</td>
                                <td class=\"text-end\">2.16%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/ru.png') }}\"
                                      alt=\"russia\"
                                    />
                                  </div>
                                </td>
                                <td>Russia</td>
                                <td class=\"text-end\">1.081</td>
                                <td class=\"text-end\">19.65%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/cn.png') }}\"
                                      alt=\"china\"
                                    />
                                  </div>
                                </td>
                                <td>China</td>
                                <td class=\"text-end\">1.100</td>
                                <td class=\"text-end\">20%</td>
                              </tr>
                              <tr>
                                <td>
                                  <div class=\"flag\">
                                    <img
                                      src=\"{{ asset('back/img/flags/br.png') }}\"
                                      alt=\"brazil\"
                                    />
                                  </div>
                                </td>
                                <td>Brasil</td>
                                <td class=\"text-end\">640</td>
                                <td class=\"text-end\">11.63%</td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      <div class=\"col-md-6\">
                        <div class=\"mapcontainer\">
                          <div
                            id=\"world-map\"
                            class=\"w-100\"
                            style=\"height: 300px\"
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class=\"row\">
              <div class=\"col-md-6\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row card-tools-still-right\">
                      <div class=\"card-title\">Recent Activity</div>
                      <div class=\"card-tools\">
                        <div class=\"dropdown\">
                          <button
                            class=\"btn btn-icon btn-clean\"
                            type=\"button\"
                            id=\"dropdownMenuButton\"
                            data-bs-toggle=\"dropdown\"
                            aria-haspopup=\"true\"
                            aria-expanded=\"false\"
                          >
                            <i class=\"fas fa-ellipsis-h\"></i>
                          </button>
                          <div
                            class=\"dropdown-menu\"
                            aria-labelledby=\"dropdownMenuButton\"
                          >
                            <a class=\"dropdown-item\" href=\"#\">Action</a>
                            <a class=\"dropdown-item\" href=\"#\">Another action</a>
                            <a class=\"dropdown-item\" href=\"#\"
                              >Something else here</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <ol class=\"activity-feed\">
                      <li class=\"feed-item feed-item-secondary\">
                        <time class=\"date\" datetime=\"9-25\">Sep 25</time>
                        <span class=\"text\"
                          >Responded to need
                          <a href=\"#\">\"Volunteer opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-success\">
                        <time class=\"date\" datetime=\"9-24\">Sep 24</time>
                        <span class=\"text\"
                          >Added an interest
                          <a href=\"#\">\"Volunteer Activities\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-info\">
                        <time class=\"date\" datetime=\"9-23\">Sep 23</time>
                        <span class=\"text\"
                          >Joined the group
                          <a href=\"single-group.php\"
                            >\"Boardsmanship Forum\"</a
                          ></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-warning\">
                        <time class=\"date\" datetime=\"9-21\">Sep 21</time>
                        <span class=\"text\"
                          >Responded to need
                          <a href=\"#\">\"In-Kind Opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item feed-item-danger\">
                        <time class=\"date\" datetime=\"9-18\">Sep 18</time>
                        <span class=\"text\"
                          >Created need
                          <a href=\"#\">\"Volunteer Opportunity\"</a></span
                        >
                      </li>
                      <li class=\"feed-item\">
                        <time class=\"date\" datetime=\"9-17\">Sep 17</time>
                        <span class=\"text\"
                          >Attending the event
                          <a href=\"single-event.php\">\"Some New Event\"</a></span
                        >
                      </li>
                    </ol>
                  </div>
                </div>
              </div>
              <div class=\"col-md-6\">
                <div class=\"card\">
                  <div class=\"card-header\">
                    <div class=\"card-head-row\">
                      <div class=\"card-title\">Support Tickets</div>
                      <div class=\"card-tools\">
                        <ul
                          class=\"nav nav-pills nav-secondary nav-pills-no-bd nav-sm\"
                          id=\"pills-tab\"
                          role=\"tablist\"
                        >
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link\"
                              id=\"pills-today\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-today\"
                              role=\"tab\"
                              aria-selected=\"true\"
                              >Today</a
                            >
                          </li>
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link active\"
                              id=\"pills-week\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-week\"
                              role=\"tab\"
                              aria-selected=\"false\"
                              >Week</a
                            >
                          </li>
                          <li class=\"nav-item\">
                            <a
                              class=\"nav-link\"
                              id=\"pills-month\"
                              data-bs-toggle=\"pill\"
                              href=\"#pills-month\"
                              role=\"tab\"
                              aria-selected=\"false\"
                              >Month</a
                            >
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div class=\"card-body\">
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-online\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-info\"
                          >J</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Joko Subianto
                          <span class=\"text-warning ps-3\">pending</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I am facing some trouble with my viewport. When i
                          start my</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">8:40 PM</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-offline\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-secondary\"
                          >P</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Prabowo Widodo
                          <span class=\"text-success ps-3\">open</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I have some query regarding the license issue.</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">1 Day Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-away\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-danger\"
                          >L</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Lee Chong Wei
                          <span class=\"text-muted ps-3\">closed</span>
                        </h6>
                        <span class=\"text-muted\"
                          >Is there any update plan for RTL version near
                          future?</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Days Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-offline\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-secondary\"
                          >P</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Peter Parker
                          <span class=\"text-success ps-3\">open</span>
                        </h6>
                        <span class=\"text-muted\"
                          >I have some query regarding the license issue.</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Day Ago</small>
                      </div>
                    </div>
                    <div class=\"separator-dashed\"></div>
                    <div class=\"d-flex\">
                      <div class=\"avatar avatar-away\">
                        <span
                          class=\"avatar-title rounded-circle border border-white bg-danger\"
                          >L</span
                        >
                      </div>
                      <div class=\"flex-1 ms-3 pt-1\">
                        <h6 class=\"text-uppercase fw-bold mb-1\">
                          Logan Paul <span class=\"text-muted ps-3\">closed</span>
                        </h6>
                        <span class=\"text-muted\"
                          >Is there any update plan for RTL version near
                          future?</span
                        >
                      </div>
                      <div class=\"float-end pt-1\">
                        <small class=\"text-muted\">2 Days Ago</small>
                      </div>
                    </div>
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

      <!-- Custom template | don't include it in your project! -->
      <div class=\"custom-template\">
        <div class=\"title\">Settings</div>
        <div class=\"custom-content\">
          <div class=\"switcher\">
            <div class=\"switch-block\">
              <h4>Logo Header</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"selected changeLogoHeaderColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"purple\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"light-blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"green\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"orange\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"red\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"white\"
                ></button>
                <br />
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"dark2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"purple2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"light-blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"green2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"orange2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeLogoHeaderColor\"
                  data-color=\"red2\"
                ></button>
              </div>
            </div>
            <div class=\"switch-block\">
              <h4>Navbar Header</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"purple\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"light-blue\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"green\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"orange\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"red\"
                ></button>
                <button
                  type=\"button\"
                  class=\"selected changeTopBarColor\"
                  data-color=\"white\"
                ></button>
                <br />
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"dark2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"purple2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"light-blue2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"green2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"orange2\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeTopBarColor\"
                  data-color=\"red2\"
                ></button>
              </div>
            </div>
            <div class=\"switch-block\">
              <h4>Sidebar</h4>
              <div class=\"btnSwitch\">
                <button
                  type=\"button\"
                  class=\"changeSideBarColor\"
                  data-color=\"white\"
                ></button>
                <button
                  type=\"button\"
                  class=\"selected changeSideBarColor\"
                  data-color=\"dark\"
                ></button>
                <button
                  type=\"button\"
                  class=\"changeSideBarColor\"
                  data-color=\"dark2\"
                ></button>
              </div>
            </div>
          </div>
        </div>
        <div class=\"custom-toggle\">
          <i class=\"icon-settings\"></i>
        </div>
      </div>
      <!-- End Custom template -->
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

    <!-- Sweet Alert -->
    <script src=\"{{ asset('back/js/plugin/sweetalert/sweetalert.min.js') }}\"></script>

    <!-- Kaiadmin JS -->
    <script src=\"{{ asset('back/js/kaiadmin.min.js') }}\"></script>

    <!-- Kaiadmin DEMO methods, don't include it in your project! -->
    <script src=\"{{ asset('back/js/setting-demo.js') }}\"></script>
    <script src=\"{{ asset('back/js/demo.js') }}\"></script>
    <script>
      \$(\"#lineChart\").sparkline([102, 109, 120, 99, 110, 105, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#177dff\",
        fillColor: \"rgba(23, 125, 255, 0.14)\",
      });

      \$(\"#lineChart2\").sparkline([99, 125, 122, 105, 110, 124, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#f3545d\",
        fillColor: \"rgba(243, 84, 93, .14)\",
      });

      \$(\"#lineChart3\").sparkline([105, 103, 123, 100, 95, 105, 115], {
        type: \"line\",
        height: \"70\",
        width: \"100%\",
        lineWidth: \"2\",
        lineColor: \"#ffa534\",
        fillColor: \"rgba(255, 165, 52, .14)\",
      });
    </script>
  </body>
</html>
", "back/sidebar-style-2.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\back\\sidebar-style-2.html.twig");
    }
}
