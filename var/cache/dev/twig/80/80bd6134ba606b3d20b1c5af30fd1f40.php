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

/* front/gallery.html.twig */
class __TwigTemplate_3f0cab2af0787bf8b07c87620a8c7bb1 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/gallery.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/gallery.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html>

<head>
  <!-- Basic -->
  <meta charset=\"utf-8\" />
  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
  <!-- Mobile Metas -->
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
  <!-- Site Metas -->
  <meta name=\"keywords\" content=\"\" />
  <meta name=\"description\" content=\"\" />
  <meta name=\"author\" content=\"\" />
  <link rel=\"shortcut icon\" href=\"";
        // line 14
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/favicon.png"), "html", null, true);
        yield "\" type=\"\">

  <title> Feane </title>

  <!-- bootstrap core css -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"";
        // line 19
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/bootstrap.css"), "html", null, true);
        yield "\" />

  <!--owl slider stylesheet -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css\" />
  <!-- nice select  -->
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css\" integrity=\"sha512-CruCP+TD3yXzlvvijET8wV5WxxEh5H8P4cmz0RFbKK6FlZ2sYl3AEsKlLPHbniXKSrDdFewhbmBK5skbdsASbQ==\" crossorigin=\"anonymous\" />
  <!-- font awesome style -->
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\" integrity=\"sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />
  <link href=\"";
        // line 27
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/font-awesome.min.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />

  <!-- Custom styles for this template -->
  <link href=\"";
        // line 30
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/style.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"";
        // line 32
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/responsive.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />

  <style>
    .action-buttons {
      position: absolute;
      top: 10px;
      right: 10px;
      z-index: 10;
    }
    .action-buttons .btn {
      padding: 8px 12px;
      margin-left: 5px;
      border-radius: 4px;
      font-size: 14px;
      font-weight: bold;
      box-shadow: 0 2px 4px rgba(0,0,0,0.2);
    }
    .action-buttons .btn-primary {
      background-color: #007bff;
      border-color: #007bff;
      color: white;
    }
    .action-buttons .btn-danger {
      background-color: #dc3545;
      border-color: #dc3545;
      color: white;
    }
    .action-buttons .btn:hover {
      transform: scale(1.1);
      transition: transform 0.2s;
    }
    .card {
      position: relative;
    }
  </style>

</head>

<body class=\"sub_page\">

  <div class=\"hero_area\">
    <div class=\"bg-box\">
      <img src=\"";
        // line 74
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/hero-bg.jpg"), "html", null, true);
        yield "\" alt=\"\">
    </div>
    <!-- header section strats -->
    <header class=\"header_section\">
      <div class=\"container\">
        <nav class=\"navbar navbar-expand-lg custom_nav-container \">
          <a class=\"navbar-brand\" href=\"";
        // line 80
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_home");
        yield "\">
            <span>
              Feane
            </span>
          </a>

          <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">
            <span class=\"\"> </span>
          </button>

          <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
            <ul class=\"navbar-nav  mx-auto \">
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 93
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_home");
        yield "\">Home </a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 96
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_menu");
        yield "\">Menu</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 99
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_about");
        yield "\">About</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 102
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_book");
        yield "\">Book Table</a>
              </li>
              <li class=\"nav-item active\">
                <a class=\"nav-link\" href=\"";
        // line 105
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">Galerie <span class=\"sr-only\">(current)</span></a>
              </li>
            </ul>
            <div class=\"user_option\">
              <a href=\"\" class=\"user_link\">
                <i class=\"fa fa-user\" aria-hidden=\"true\"></i>
              </a>
              <a class=\"cart_link\" href=\"#\">
                <svg version=\"1.1\" id=\"Capa_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" viewBox=\"0 0 456.029 456.029\" style=\"enable-background:new 0 0 456.029 456.029;\" xml:space=\"preserve\">
                  <g>
                    <g>
                      <path d=\"M345.6,338.862c-29.184,0-53.248,23.552-53.248,53.248c0,29.184,23.552,53.248,53.248,53.248
                   c29.184,0,53.248-23.552,53.248-53.248C398.336,362.926,374.784,338.862,345.6,338.862z\" />
                    </g>
                  </g>
                  <g>
                    <g>
                      <path d=\"M439.296,84.91c-1.024,0-2.56-0.512-4.096-0.512H112.64l-5.12-34.304C104.448,27.566,84.992,10.67,61.952,10.67H20.48
                   C9.216,10.67,0,19.886,0,31.15c0,11.264,9.216,20.48,20.48,20.48h41.472c2.56,0,4.608,2.048,5.12,4.608l31.744,216.064
                   c4.096,27.136,27.648,47.616,55.296,47.616h212.992c26.624,0,49.664-18.944,55.296-45.056l33.28-166.4
                   C457.728,97.71,450.56,86.958,439.296,84.91z\" />
                    </g>
                  </g>
                  <g>
                    <g>
                      <path d=\"M215.04,389.55c-1.024-28.16-24.576-50.688-52.736-50.688c-29.696,1.536-52.224,26.112-51.2,55.296
                   c1.024,28.16,24.064,50.688,52.224,50.688h1.024C193.536,443.31,216.576,418.734,215.04,389.55z\" />
                    </g>
                  </g>
                </svg>
              </a>
              <form class=\"form-inline\">
                <button class=\"btn  my-2 my-sm-0 nav_search-btn\" type=\"submit\">
                  <i class=\"fa fa-search\" aria-hidden=\"true\"></i>
                </button>
              </form>
              <a href=\"\" class=\"order_online\">
                Order Online
              </a>
            </div>
          </div>
        </nav>
      </div>
    </header>
    <!-- end header section -->
  </div>

  <section class=\"food_section layout_padding\">
    <div class=\"container\">
      <div class=\"heading_container heading_center\">
        <h2>
          Galerie
        </h2>
      </div>

      <!-- Interface de recherche et tri -->
      <div class=\"row mb-4\">
        <div class=\"col-md-6\">
          <form method=\"GET\" action=\"";
        // line 163
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">
            <div class=\"input-group\">
              <input type=\"text\" name=\"search\" class=\"form-control\" placeholder=\"Rechercher une œuvre...\" value=\"";
        // line 165
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 165, $this->source); })()), "html", null, true);
        yield "\">
              <button type=\"submit\" class=\"btn btn-primary\">
                <i class=\"fa fa-search\"></i>
              </button>
            </div>
          </form>
        </div>
        <div class=\"col-md-6\">
          <form method=\"GET\" action=\"";
        // line 173
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">
            <select name=\"sort\" class=\"form-select\" onchange=\"this.form.submit()\">
              <option value=\"recent\" ";
        // line 175
        if (((isset($context["sortBy"]) || array_key_exists("sortBy", $context) ? $context["sortBy"] : (function () { throw new RuntimeError('Variable "sortBy" does not exist.', 175, $this->source); })()) == "recent")) {
            yield "selected";
        }
        yield ">🕐 Plus récent</option>
              <option value=\"oldest\" ";
        // line 176
        if (((isset($context["sortBy"]) || array_key_exists("sortBy", $context) ? $context["sortBy"] : (function () { throw new RuntimeError('Variable "sortBy" does not exist.', 176, $this->source); })()) == "oldest")) {
            yield "selected";
        }
        yield ">🕑 Plus ancien</option>
            </select>
            ";
        // line 178
        if ((($tmp = (isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 178, $this->source); })())) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 179
            yield "              <input type=\"hidden\" name=\"search\" value=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 179, $this->source); })()), "html", null, true);
            yield "\">
            ";
        }
        // line 181
        yield "          </form>
        </div>
      </div>

      ";
        // line 185
        if (((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 185, $this->source); })()) || ((isset($context["sortBy"]) || array_key_exists("sortBy", $context) ? $context["sortBy"] : (function () { throw new RuntimeError('Variable "sortBy" does not exist.', 185, $this->source); })()) != "recent"))) {
            // line 186
            yield "        <div class=\"alert alert-info\">
          <i class=\"fa fa-info-circle\"></i> 
          ";
            // line 188
            if ((($tmp = (isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 188, $this->source); })())) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                yield "Recherche pour \"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 188, $this->source); })()), "html", null, true);
                yield "\"";
            }
            yield " 
          ";
            // line 189
            if (((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 189, $this->source); })()) && ((isset($context["sortBy"]) || array_key_exists("sortBy", $context) ? $context["sortBy"] : (function () { throw new RuntimeError('Variable "sortBy" does not exist.', 189, $this->source); })()) != "recent"))) {
                yield " - ";
            }
            // line 190
            yield "          ";
            if (((isset($context["sortBy"]) || array_key_exists("sortBy", $context) ? $context["sortBy"] : (function () { throw new RuntimeError('Variable "sortBy" does not exist.', 190, $this->source); })()) == "oldest")) {
                yield "Plus ancien";
            }
            // line 191
            yield "          <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
            yield "\" class=\"float-end\">✖ Effacer</a>
        </div>
      ";
        }
        // line 194
        yield "
      ";
        // line 195
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 195, $this->source); })()), "session", [], "any", false, false, false, 195), "flashbag", [], "any", false, false, false, 195), "all", [], "method", false, false, false, 195));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 196
            yield "        ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 197
                yield "          <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
            ";
                // line 198
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
                yield "
            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">
              <span aria-hidden=\"true\">&times;</span>
            </button>
          </div>
        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 204
            yield "      ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 205
        yield "
      <div class=\"text-right mb-4\">
        <a class=\"btn btn-warning\" href=\"";
        // line 207
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery_new");
        yield "\">Ajouter une oeuvre</a>
      </div>

      <div class=\"row\">
        ";
        // line 211
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["arts"]) || array_key_exists("arts", $context) ? $context["arts"] : (function () { throw new RuntimeError('Variable "arts" does not exist.', 211, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["art"]) {
            // line 212
            yield "          <div class=\"col-md-4 mb-4\">
            <div class=\"card h-100\">
              <div class=\"action-buttons\">
                <a href=\"";
            // line 215
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("art_detail", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 215)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-info\" title=\"Voir détails\">
                  <i class=\"fa fa-eye\"></i>
                </a>
                <a href=\"";
            // line 218
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 218)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-primary\" title=\"Modifier\">
                  <i class=\"fa fa-edit\"></i>
                </a>
                <form method=\"post\" action=\"";
            // line 221
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery_delete", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 221)]), "html", null, true);
            yield "\" style=\"display: inline;\">
                  <input type=\"hidden\" name=\"_token\" value=\"";
            // line 222
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("delete" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 222))), "html", null, true);
            yield "\">
                  <button type=\"submit\" class=\"btn btn-sm btn-danger\" onclick=\"return confirm('Supprimer cette publication ?')\" title=\"Supprimer\">
                    <i class=\"fa fa-trash\"></i>
                  </button>
                </form>
              </div>
              <a href=\"";
            // line 228
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("art_detail", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 228)]), "html", null, true);
            yield "\">
                <img class=\"card-img-top\" src=\"";
            // line 229
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "imageUrl", [], "any", false, false, false, 229), "html", null, true);
            yield "\" alt=\"\">
              </a>
              <div class=\"card-body\">
                <h5 class=\"card-title\">";
            // line 232
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 232), "html", null, true);
            yield "</h5>
                <p class=\"card-text\">";
            // line 233
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 233), "html", null, true);
            yield "</p>
              </div>
              <div class=\"card-footer d-flex justify-content-between align-items-center\">
                <small class=\"text-muted\">";
            // line 236
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 236)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 236), "Y-m-d H:i"), "html", null, true)) : (""));
            yield "</small>
                <div class=\"view-section\">
                  <i class=\"fas fa-eye\"></i>
                  <span class=\"view-count\" data-art-id=\"";
            // line 239
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 239), "html", null, true);
            yield "\">0</span> vues
                </div>
              </div>
            </div>
          </div>
        ";
            $context['_iterated'] = true;
        }
        // line 244
        if (!$context['_iterated']) {
            // line 245
            yield "          <div class=\"col-12\">
            <p>Aucune oeuvre publiée.</p>
          </div>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['art'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 249
        yield "      </div>
    </div>
  </section>

  <!-- footer section -->
  <footer class=\"footer_section\">
    <div class=\"container\">
      <div class=\"row\">
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_contact\">
            <h4>
              Contact Us
            </h4>
            <div class=\"contact_link_box\">
              <a href=\"\">
                <i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i>
                <span>
                  Location
                </span>
              </a>
              <a href=\"\">
                <i class=\"fa fa-phone\" aria-hidden=\"true\"></i>
                <span>
                  Call +01 1234567890
                </span>
              </a>
              <a href=\"\">
                <i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>
                <span>
                  demo@gmail.com
                </span>
              </a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_detail\">
            <a href=\"\" class=\"footer-logo\">
              Feane
            </a>
            <p>
              Necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with
            </p>
            <div class=\"footer_social\">
              <a href=\"\">
                <i class=\"fa fa-facebook\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-linkedin\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-instagram\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-pinterest\" aria-hidden=\"true\"></i>
              </a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <h4>
            Opening Hours
          </h4>
          <p>
            Everyday
          </p>
          <p>
            10.00 Am -10.00 Pm
          </p>
        </div>
      </div>
      <div class=\"footer-info\">
        <p>
          &copy; <span id=\"displayYear\"></span> All Rights Reserved By
          <a href=\"https://html.design/\">Free Html Templates</a><br><br>
          &copy; <span id=\"displayYear\"></span> Distributed By
          <a href=\"https://themewagon.com/\" target=\"_blank\">ThemeWagon</a>
        </p>
      </div>
    </div>
  </footer>
  <!-- footer section -->

  <!-- jQery -->
  <script src=\"";
        // line 336
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/jquery-3.4.1.min.js"), "html", null, true);
        yield "\"></script>
  <!-- popper js -->
  <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\">
  </script>
  <!-- bootstrap js -->
  <script src=\"";
        // line 341
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/bootstrap.js"), "html", null, true);
        yield "\"></script>
  <!-- owl slider -->
  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js\">
  </script>
  <!-- isotope js -->
  <script src=\"https://unpkg.com/isotope-layout@3.0.4/dist/isotope.pkgd.min.js\"></script>
  <!-- nice select -->
  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js\"></script>
  <!-- custom js -->
  <script src=\"";
        // line 350
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/custom.js"), "html", null, true);
        yield "\"></script>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Charger seulement les compteurs de vues (pas d'enregistrement)
      document.querySelectorAll('.view-count').forEach(function(viewElement) {
        const artId = viewElement.getAttribute('data-art-id');
        loadViewCount(artId, viewElement);
      });
    });

    async function loadViewCount(artId, element) {
      try {
        const response = await fetch('/api/art/' + artId + '/views');
        const data = await response.json();
        
        if (response.ok) {
          element.textContent = data.viewsCount;
        }
      } catch (error) {
        console.error('Erreur lors du chargement des vues:', error);
      }
    }
  </script>
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
        return "front/gallery.html.twig";
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
        return array (  563 => 350,  551 => 341,  543 => 336,  454 => 249,  445 => 245,  443 => 244,  433 => 239,  427 => 236,  421 => 233,  417 => 232,  411 => 229,  407 => 228,  398 => 222,  394 => 221,  388 => 218,  382 => 215,  377 => 212,  372 => 211,  365 => 207,  361 => 205,  355 => 204,  343 => 198,  338 => 197,  333 => 196,  329 => 195,  326 => 194,  319 => 191,  314 => 190,  310 => 189,  302 => 188,  298 => 186,  296 => 185,  290 => 181,  284 => 179,  282 => 178,  275 => 176,  269 => 175,  264 => 173,  253 => 165,  248 => 163,  187 => 105,  181 => 102,  175 => 99,  169 => 96,  163 => 93,  147 => 80,  138 => 74,  93 => 32,  88 => 30,  82 => 27,  71 => 19,  63 => 14,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html>

<head>
  <!-- Basic -->
  <meta charset=\"utf-8\" />
  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
  <!-- Mobile Metas -->
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
  <!-- Site Metas -->
  <meta name=\"keywords\" content=\"\" />
  <meta name=\"description\" content=\"\" />
  <meta name=\"author\" content=\"\" />
  <link rel=\"shortcut icon\" href=\"{{ asset('front/images/favicon.png') }}\" type=\"\">

  <title> Feane </title>

  <!-- bootstrap core css -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"{{ asset('front/css/bootstrap.css') }}\" />

  <!--owl slider stylesheet -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css\" />
  <!-- nice select  -->
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css\" integrity=\"sha512-CruCP+TD3yXzlvvijET8wV5WxxEh5H8P4cmz0RFbKK6FlZ2sYl3AEsKlLPHbniXKSrDdFewhbmBK5skbdsASbQ==\" crossorigin=\"anonymous\" />
  <!-- font awesome style -->
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\" integrity=\"sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />
  <link href=\"{{ asset('front/css/font-awesome.min.css') }}\" rel=\"stylesheet\" />

  <!-- Custom styles for this template -->
  <link href=\"{{ asset('front/css/style.css') }}\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"{{ asset('front/css/responsive.css') }}\" rel=\"stylesheet\" />

  <style>
    .action-buttons {
      position: absolute;
      top: 10px;
      right: 10px;
      z-index: 10;
    }
    .action-buttons .btn {
      padding: 8px 12px;
      margin-left: 5px;
      border-radius: 4px;
      font-size: 14px;
      font-weight: bold;
      box-shadow: 0 2px 4px rgba(0,0,0,0.2);
    }
    .action-buttons .btn-primary {
      background-color: #007bff;
      border-color: #007bff;
      color: white;
    }
    .action-buttons .btn-danger {
      background-color: #dc3545;
      border-color: #dc3545;
      color: white;
    }
    .action-buttons .btn:hover {
      transform: scale(1.1);
      transition: transform 0.2s;
    }
    .card {
      position: relative;
    }
  </style>

</head>

<body class=\"sub_page\">

  <div class=\"hero_area\">
    <div class=\"bg-box\">
      <img src=\"{{ asset('front/images/hero-bg.jpg') }}\" alt=\"\">
    </div>
    <!-- header section strats -->
    <header class=\"header_section\">
      <div class=\"container\">
        <nav class=\"navbar navbar-expand-lg custom_nav-container \">
          <a class=\"navbar-brand\" href=\"{{ path('front_home') }}\">
            <span>
              Feane
            </span>
          </a>

          <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">
            <span class=\"\"> </span>
          </button>

          <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
            <ul class=\"navbar-nav  mx-auto \">
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"{{ path('front_home') }}\">Home </a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"{{ path('front_menu') }}\">Menu</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"{{ path('front_about') }}\">About</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"{{ path('front_book') }}\">Book Table</a>
              </li>
              <li class=\"nav-item active\">
                <a class=\"nav-link\" href=\"{{ path('front_gallery') }}\">Galerie <span class=\"sr-only\">(current)</span></a>
              </li>
            </ul>
            <div class=\"user_option\">
              <a href=\"\" class=\"user_link\">
                <i class=\"fa fa-user\" aria-hidden=\"true\"></i>
              </a>
              <a class=\"cart_link\" href=\"#\">
                <svg version=\"1.1\" id=\"Capa_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" viewBox=\"0 0 456.029 456.029\" style=\"enable-background:new 0 0 456.029 456.029;\" xml:space=\"preserve\">
                  <g>
                    <g>
                      <path d=\"M345.6,338.862c-29.184,0-53.248,23.552-53.248,53.248c0,29.184,23.552,53.248,53.248,53.248
                   c29.184,0,53.248-23.552,53.248-53.248C398.336,362.926,374.784,338.862,345.6,338.862z\" />
                    </g>
                  </g>
                  <g>
                    <g>
                      <path d=\"M439.296,84.91c-1.024,0-2.56-0.512-4.096-0.512H112.64l-5.12-34.304C104.448,27.566,84.992,10.67,61.952,10.67H20.48
                   C9.216,10.67,0,19.886,0,31.15c0,11.264,9.216,20.48,20.48,20.48h41.472c2.56,0,4.608,2.048,5.12,4.608l31.744,216.064
                   c4.096,27.136,27.648,47.616,55.296,47.616h212.992c26.624,0,49.664-18.944,55.296-45.056l33.28-166.4
                   C457.728,97.71,450.56,86.958,439.296,84.91z\" />
                    </g>
                  </g>
                  <g>
                    <g>
                      <path d=\"M215.04,389.55c-1.024-28.16-24.576-50.688-52.736-50.688c-29.696,1.536-52.224,26.112-51.2,55.296
                   c1.024,28.16,24.064,50.688,52.224,50.688h1.024C193.536,443.31,216.576,418.734,215.04,389.55z\" />
                    </g>
                  </g>
                </svg>
              </a>
              <form class=\"form-inline\">
                <button class=\"btn  my-2 my-sm-0 nav_search-btn\" type=\"submit\">
                  <i class=\"fa fa-search\" aria-hidden=\"true\"></i>
                </button>
              </form>
              <a href=\"\" class=\"order_online\">
                Order Online
              </a>
            </div>
          </div>
        </nav>
      </div>
    </header>
    <!-- end header section -->
  </div>

  <section class=\"food_section layout_padding\">
    <div class=\"container\">
      <div class=\"heading_container heading_center\">
        <h2>
          Galerie
        </h2>
      </div>

      <!-- Interface de recherche et tri -->
      <div class=\"row mb-4\">
        <div class=\"col-md-6\">
          <form method=\"GET\" action=\"{{ path('front_gallery') }}\">
            <div class=\"input-group\">
              <input type=\"text\" name=\"search\" class=\"form-control\" placeholder=\"Rechercher une œuvre...\" value=\"{{ search }}\">
              <button type=\"submit\" class=\"btn btn-primary\">
                <i class=\"fa fa-search\"></i>
              </button>
            </div>
          </form>
        </div>
        <div class=\"col-md-6\">
          <form method=\"GET\" action=\"{{ path('front_gallery') }}\">
            <select name=\"sort\" class=\"form-select\" onchange=\"this.form.submit()\">
              <option value=\"recent\" {% if sortBy == 'recent' %}selected{% endif %}>🕐 Plus récent</option>
              <option value=\"oldest\" {% if sortBy == 'oldest' %}selected{% endif %}>🕑 Plus ancien</option>
            </select>
            {% if search %}
              <input type=\"hidden\" name=\"search\" value=\"{{ search }}\">
            {% endif %}
          </form>
        </div>
      </div>

      {% if search or sortBy != 'recent' %}
        <div class=\"alert alert-info\">
          <i class=\"fa fa-info-circle\"></i> 
          {% if search %}Recherche pour \"{{ search }}\"{% endif %} 
          {% if search and sortBy != 'recent' %} - {% endif %}
          {% if sortBy == 'oldest' %}Plus ancien{% endif %}
          <a href=\"{{ path('front_gallery') }}\" class=\"float-end\">✖ Effacer</a>
        </div>
      {% endif %}

      {% for type, messages in app.session.flashbag.all() %}
        {% for message in messages %}
          <div class=\"alert alert-{{ type }} alert-dismissible fade show\" role=\"alert\">
            {{ message }}
            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">
              <span aria-hidden=\"true\">&times;</span>
            </button>
          </div>
        {% endfor %}
      {% endfor %}

      <div class=\"text-right mb-4\">
        <a class=\"btn btn-warning\" href=\"{{ path('front_gallery_new') }}\">Ajouter une oeuvre</a>
      </div>

      <div class=\"row\">
        {% for art in arts %}
          <div class=\"col-md-4 mb-4\">
            <div class=\"card h-100\">
              <div class=\"action-buttons\">
                <a href=\"{{ path('art_detail', {'id': art.id}) }}\" class=\"btn btn-sm btn-info\" title=\"Voir détails\">
                  <i class=\"fa fa-eye\"></i>
                </a>
                <a href=\"{{ path('front_gallery_edit', {'id': art.id}) }}\" class=\"btn btn-sm btn-primary\" title=\"Modifier\">
                  <i class=\"fa fa-edit\"></i>
                </a>
                <form method=\"post\" action=\"{{ path('front_gallery_delete', {'id': art.id}) }}\" style=\"display: inline;\">
                  <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('delete' ~ art.id) }}\">
                  <button type=\"submit\" class=\"btn btn-sm btn-danger\" onclick=\"return confirm('Supprimer cette publication ?')\" title=\"Supprimer\">
                    <i class=\"fa fa-trash\"></i>
                  </button>
                </form>
              </div>
              <a href=\"{{ path('art_detail', {'id': art.id}) }}\">
                <img class=\"card-img-top\" src=\"{{ art.imageUrl }}\" alt=\"\">
              </a>
              <div class=\"card-body\">
                <h5 class=\"card-title\">{{ art.title }}</h5>
                <p class=\"card-text\">{{ art.description }}</p>
              </div>
              <div class=\"card-footer d-flex justify-content-between align-items-center\">
                <small class=\"text-muted\">{{ art.createdAt ? art.createdAt|date('Y-m-d H:i') : '' }}</small>
                <div class=\"view-section\">
                  <i class=\"fas fa-eye\"></i>
                  <span class=\"view-count\" data-art-id=\"{{ art.id }}\">0</span> vues
                </div>
              </div>
            </div>
          </div>
        {% else %}
          <div class=\"col-12\">
            <p>Aucune oeuvre publiée.</p>
          </div>
        {% endfor %}
      </div>
    </div>
  </section>

  <!-- footer section -->
  <footer class=\"footer_section\">
    <div class=\"container\">
      <div class=\"row\">
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_contact\">
            <h4>
              Contact Us
            </h4>
            <div class=\"contact_link_box\">
              <a href=\"\">
                <i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i>
                <span>
                  Location
                </span>
              </a>
              <a href=\"\">
                <i class=\"fa fa-phone\" aria-hidden=\"true\"></i>
                <span>
                  Call +01 1234567890
                </span>
              </a>
              <a href=\"\">
                <i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>
                <span>
                  demo@gmail.com
                </span>
              </a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_detail\">
            <a href=\"\" class=\"footer-logo\">
              Feane
            </a>
            <p>
              Necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with
            </p>
            <div class=\"footer_social\">
              <a href=\"\">
                <i class=\"fa fa-facebook\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-linkedin\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-instagram\" aria-hidden=\"true\"></i>
              </a>
              <a href=\"\">
                <i class=\"fa fa-pinterest\" aria-hidden=\"true\"></i>
              </a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <h4>
            Opening Hours
          </h4>
          <p>
            Everyday
          </p>
          <p>
            10.00 Am -10.00 Pm
          </p>
        </div>
      </div>
      <div class=\"footer-info\">
        <p>
          &copy; <span id=\"displayYear\"></span> All Rights Reserved By
          <a href=\"https://html.design/\">Free Html Templates</a><br><br>
          &copy; <span id=\"displayYear\"></span> Distributed By
          <a href=\"https://themewagon.com/\" target=\"_blank\">ThemeWagon</a>
        </p>
      </div>
    </div>
  </footer>
  <!-- footer section -->

  <!-- jQery -->
  <script src=\"{{ asset('front/js/jquery-3.4.1.min.js') }}\"></script>
  <!-- popper js -->
  <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\">
  </script>
  <!-- bootstrap js -->
  <script src=\"{{ asset('front/js/bootstrap.js') }}\"></script>
  <!-- owl slider -->
  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js\">
  </script>
  <!-- isotope js -->
  <script src=\"https://unpkg.com/isotope-layout@3.0.4/dist/isotope.pkgd.min.js\"></script>
  <!-- nice select -->
  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js\"></script>
  <!-- custom js -->
  <script src=\"{{ asset('front/js/custom.js') }}\"></script>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Charger seulement les compteurs de vues (pas d'enregistrement)
      document.querySelectorAll('.view-count').forEach(function(viewElement) {
        const artId = viewElement.getAttribute('data-art-id');
        loadViewCount(artId, viewElement);
      });
    });

    async function loadViewCount(artId, element) {
      try {
        const response = await fetch('/api/art/' + artId + '/views');
        const data = await response.json();
        
        if (response.ok) {
          element.textContent = data.viewsCount;
        }
      } catch (error) {
        console.error('Erreur lors du chargement des vues:', error);
      }
    }
  </script>
", "front/gallery.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\front\\gallery.html.twig");
    }
}
