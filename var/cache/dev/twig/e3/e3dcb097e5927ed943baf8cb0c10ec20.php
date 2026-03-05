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

/* base_front.html.twig */
class __TwigTemplate_978144425ad7a3c2ae435c7c532d0e44 extends Template
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
            'title' => [$this, 'block_title'],
            'stylesheets' => [$this, 'block_stylesheets'],
            'body' => [$this, 'block_body'],
            'javascripts' => [$this, 'block_javascripts'],
        ];
    }

    protected function doDisplay(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "base_front.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "base_front.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html>
<head>
  <meta charset=\"utf-8\" />
  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
  <link rel=\"shortcut icon\" href=\"";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/favicon.png"), "html", null, true);
        yield "\">
  <title>";
        // line 8
        yield from $this->unwrap()->yieldBlock('title', $context, $blocks);
        yield "</title>

  <link rel=\"stylesheet\" href=\"";
        // line 10
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/bootstrap.css"), "html", null, true);
        yield "\" />
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css\" />
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css\" />
  <link href=\"";
        // line 13
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/font-awesome.min.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <link href=\"";
        // line 14
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/style.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <link href=\"";
        // line 15
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/responsive.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <link href=\"";
        // line 16
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/front-enhance.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  ";
        // line 17
        yield from $this->unwrap()->yieldBlock('stylesheets', $context, $blocks);
        // line 18
        yield "  <style>
    /* Force navbar links to always be visible and yellow */
    .custom_nav-container .navbar-nav .nav-link {
      color: #ffbe33 !important;
      opacity: 1 !important;
    }
    .custom_nav-container .navbar-nav .nav-link:hover,
    .custom_nav-container .navbar-nav .nav-item.active .nav-link {
      color: white !important;
    }
  </style>
</head>
<body>

  <!-- header -->
  <header class=\"header_section\">
    <div class=\"container\">
      <nav class=\"navbar navbar-expand-lg custom_nav-container\">
        <a class=\"navbar-brand\" href=\"";
        // line 36
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
        yield "\">
          <span>Pegasus</span>
          ";
        // line 38
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 39
            yield "            <span style=\"font-size: 11px; background: #ffbe33; color: #000; padding: 2px 8px; border-radius: 10px; margin-left: 8px; font-weight: 700; vertical-align: middle; letter-spacing: 1px;\">ARTISTE</span>
          ";
        }
        // line 41
        yield "        </a>
        <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\">
          <span></span>
        </button>
        <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
          <ul class=\"navbar-nav mx-auto\">

            ";
        // line 48
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 49
            yield "            ";
            // line 50
            yield "            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"";
            // line 51
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" style=\"color: white !important; opacity: 1;\">
                <i class=\"fa fa-th-large\" style=\"color: #ffbe33;\"></i>
                &nbsp;Mes produits
              </a>
            </li>
            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"";
            // line 57
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_mes_produits");
            yield "\" style=\"color: white !important; opacity: 1;\">
                <i class=\"fa fa-bar-chart\" style=\"color: #ffbe33;\"></i>
                &nbsp;Statuts des produits
              </a>
            </li>
            ";
        } else {
            // line 63
            yield "            ";
            // line 64
            yield "            <li class=\"nav-item active\">
              <a class=\"nav-link\" href=\"";
            // line 65
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\">Accueil</a>
            </li>
            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"";
            // line 68
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\">Produits</a>
            </li>
            ";
        }
        // line 71
        yield "
          </ul>

          <div class=\"user_option\">
            ";
        // line 75
        if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 75, $this->source); })()), "user", [], "any", false, false, false, 75)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 76
            yield "              ";
            if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 77
                yield "                <span style=\"color: #ffbe33; margin-right: 10px; font-size: 13px; font-weight: 600;\">
                  <i class=\"fa fa-paint-brush\"></i>
                  ";
                // line 79
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 79, $this->source); })()), "user", [], "any", false, false, false, 79), "userIdentifier", [], "any", false, false, false, 79), "html", null, true);
                yield "
                </span>
              ";
            } else {
                // line 82
                yield "                <span class=\"mr-3\" style=\"color: white;\">
                  <i class=\"fa fa-user-circle-o\"></i>
                  ";
                // line 84
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 84, $this->source); })()), "user", [], "any", false, false, false, 84), "userIdentifier", [], "any", false, false, false, 84), "html", null, true);
                yield "
                </span>
              ";
            }
            // line 87
            yield "              <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_logout");
            yield "\" class=\"user_link\" title=\"Se déconnecter\">
                <i class=\"fa fa-sign-out\" aria-hidden=\"true\"></i>
              </a>
            ";
        } else {
            // line 91
            yield "              <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_login");
            yield "\" class=\"user_link\" title=\"Se connecter\">
                <i class=\"fa fa-user\" aria-hidden=\"true\"></i>
              </a>
            ";
        }
        // line 95
        yield "
            ";
        // line 96
        if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 97
            yield "              <a class=\"cart_link\" href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_favoris_index");
            yield "\" title=\"Mes Favoris\" style=\"margin-left: 15px; position: relative;\">
                <i class=\"fa fa-heart\" aria-hidden=\"true\" style=\"color: #ffbe33;\"></i>
                ";
            // line 99
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 99, $this->source); })()), "session", [], "any", false, false, false, 99), "get", ["favoris"], "method", false, false, false, 99)) > 0)) {
                // line 100
                yield "                  <span class=\"badge badge-pill badge-danger\" id=\"favoris-count\" style=\"position: absolute; top: -10px; right: -10px; font-size: 10px;\">
                    ";
                // line 101
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 101, $this->source); })()), "session", [], "any", false, false, false, 101), "get", ["favoris"], "method", false, false, false, 101)), "html", null, true);
                yield "
                  </span>
                ";
            }
            // line 104
            yield "              </a>
              <a class=\"cart_link\" href=\"";
            // line 105
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_commande_historique");
            yield "\" title=\"Historique d'achats\" style=\"margin-left: 15px;\">
                <i class=\"fa fa-history\" aria-hidden=\"true\"></i>
              </a>
              <a class=\"cart_link\" href=\"";
            // line 108
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_index");
            yield "\" title=\"Mon Panier\">
                <i class=\"fa fa-shopping-cart\" aria-hidden=\"true\"></i>
              </a>
            ";
        }
        // line 112
        yield "          </div>
        </div>
      </nav>
    </div>
  </header>
  <!-- end header -->

  <div class=\"container\" style=\"margin-top: 30px; margin-bottom: 30px;\">
    ";
        // line 120
        yield from $this->unwrap()->yieldBlock('body', $context, $blocks);
        // line 121
        yield "  </div>

  <!-- footer -->
  <footer class=\"footer_section\">
    <div class=\"container\">
      <div class=\"row\">
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_contact\">
            <h4>Contact Us</h4>
            <div class=\"contact_link_box\">
              <a href=\"\"><i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i><span> Location</span></a>
              <a href=\"\"><i class=\"fa fa-phone\" aria-hidden=\"true\"></i><span> Call +01 1234567890</span></a>
              <a href=\"\"><i class=\"fa fa-envelope\" aria-hidden=\"true\"></i><span> pegasus@gmail.com</span></a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_detail\">
            <a href=\"\" class=\"footer-logo\">Pegasus</a>
            <p>Plateforme artistique pour découvrir et acheter des œuvres d'art uniques.</p>
            <div class=\"footer_social\">
              <a href=\"\"><i class=\"fa fa-facebook\" aria-hidden=\"true\"></i></a>
              <a href=\"\"><i class=\"fa fa-twitter\" aria-hidden=\"true\"></i></a>
              <a href=\"\"><i class=\"fa fa-instagram\" aria-hidden=\"true\"></i></a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <h4>Horaires</h4>
          <p>Tous les jours</p>
          <p>24h/24 - 7j/7</p>
        </div>
      </div>
      <div class=\"footer-info\">
        <p>&copy; 2026 Pegasus - Tous droits réservés</p>
      </div>
    </div>
  </footer>
  <!-- end footer -->

  <script src=\"";
        // line 161
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/jquery-3.4.1.min.js"), "html", null, true);
        yield "\"></script>
  <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"></script>
  <script src=\"";
        // line 163
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/bootstrap.js"), "html", null, true);
        yield "\"></script>
  <script src=\"";
        // line 164
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/custom.js"), "html", null, true);
        yield "\"></script>
  ";
        // line 165
        yield from $this->unwrap()->yieldBlock('javascripts', $context, $blocks);
        // line 166
        yield "</body>
</html>";
        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        yield from [];
    }

    // line 8
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_title(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "title"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "title"));

        yield "Pegasus";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 17
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_stylesheets(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "stylesheets"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "stylesheets"));

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 120
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_body(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "body"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "body"));

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 165
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_javascripts(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "javascripts"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "javascripts"));

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "base_front.html.twig";
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
        return array (  408 => 165,  386 => 120,  364 => 17,  341 => 8,  329 => 166,  327 => 165,  323 => 164,  319 => 163,  314 => 161,  272 => 121,  270 => 120,  260 => 112,  253 => 108,  247 => 105,  244 => 104,  238 => 101,  235 => 100,  233 => 99,  227 => 97,  225 => 96,  222 => 95,  214 => 91,  206 => 87,  200 => 84,  196 => 82,  190 => 79,  186 => 77,  183 => 76,  181 => 75,  175 => 71,  169 => 68,  163 => 65,  160 => 64,  158 => 63,  149 => 57,  140 => 51,  137 => 50,  135 => 49,  133 => 48,  124 => 41,  120 => 39,  118 => 38,  113 => 36,  93 => 18,  91 => 17,  87 => 16,  83 => 15,  79 => 14,  75 => 13,  69 => 10,  64 => 8,  60 => 7,  52 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html>
<head>
  <meta charset=\"utf-8\" />
  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
  <link rel=\"shortcut icon\" href=\"{{ asset('front/images/favicon.png') }}\">
  <title>{% block title %}Pegasus{% endblock %}</title>

  <link rel=\"stylesheet\" href=\"{{ asset('front/css/bootstrap.css') }}\" />
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css\" />
  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css\" />
  <link href=\"{{ asset('front/css/font-awesome.min.css') }}\" rel=\"stylesheet\" />
  <link href=\"{{ asset('front/css/style.css') }}\" rel=\"stylesheet\" />
  <link href=\"{{ asset('front/css/responsive.css') }}\" rel=\"stylesheet\" />
  <link href=\"{{ asset('front/css/front-enhance.css') }}\" rel=\"stylesheet\" />
  {% block stylesheets %}{% endblock %}
  <style>
    /* Force navbar links to always be visible and yellow */
    .custom_nav-container .navbar-nav .nav-link {
      color: #ffbe33 !important;
      opacity: 1 !important;
    }
    .custom_nav-container .navbar-nav .nav-link:hover,
    .custom_nav-container .navbar-nav .nav-item.active .nav-link {
      color: white !important;
    }
  </style>
</head>
<body>

  <!-- header -->
  <header class=\"header_section\">
    <div class=\"container\">
      <nav class=\"navbar navbar-expand-lg custom_nav-container\">
        <a class=\"navbar-brand\" href=\"{{ path('app_produit_index') }}\">
          <span>Pegasus</span>
          {% if is_granted('ROLE_ARTISTE') %}
            <span style=\"font-size: 11px; background: #ffbe33; color: #000; padding: 2px 8px; border-radius: 10px; margin-left: 8px; font-weight: 700; vertical-align: middle; letter-spacing: 1px;\">ARTISTE</span>
          {% endif %}
        </a>
        <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\">
          <span></span>
        </button>
        <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
          <ul class=\"navbar-nav mx-auto\">

            {% if is_granted('ROLE_ARTISTE') %}
            {# ---- NAVBAR ARTISTE ---- #}
            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"{{ path('app_produit_index') }}\" style=\"color: white !important; opacity: 1;\">
                <i class=\"fa fa-th-large\" style=\"color: #ffbe33;\"></i>
                &nbsp;Mes produits
              </a>
            </li>
            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"{{ path('app_produit_mes_produits') }}\" style=\"color: white !important; opacity: 1;\">
                <i class=\"fa fa-bar-chart\" style=\"color: #ffbe33;\"></i>
                &nbsp;Statuts des produits
              </a>
            </li>
            {% else %}
            {# ---- NAVBAR USER NORMAL ---- #}
            <li class=\"nav-item active\">
              <a class=\"nav-link\" href=\"{{ path('app_produit_index') }}\">Accueil</a>
            </li>
            <li class=\"nav-item\">
              <a class=\"nav-link\" href=\"{{ path('app_produit_index') }}\">Produits</a>
            </li>
            {% endif %}

          </ul>

          <div class=\"user_option\">
            {% if app.user %}
              {% if is_granted('ROLE_ARTISTE') %}
                <span style=\"color: #ffbe33; margin-right: 10px; font-size: 13px; font-weight: 600;\">
                  <i class=\"fa fa-paint-brush\"></i>
                  {{ app.user.userIdentifier }}
                </span>
              {% else %}
                <span class=\"mr-3\" style=\"color: white;\">
                  <i class=\"fa fa-user-circle-o\"></i>
                  {{ app.user.userIdentifier }}
                </span>
              {% endif %}
              <a href=\"{{ path('app_logout') }}\" class=\"user_link\" title=\"Se déconnecter\">
                <i class=\"fa fa-sign-out\" aria-hidden=\"true\"></i>
              </a>
            {% else %}
              <a href=\"{{ path('app_login') }}\" class=\"user_link\" title=\"Se connecter\">
                <i class=\"fa fa-user\" aria-hidden=\"true\"></i>
              </a>
            {% endif %}

            {% if not is_granted('ROLE_ARTISTE') %}
              <a class=\"cart_link\" href=\"{{ path('app_favoris_index') }}\" title=\"Mes Favoris\" style=\"margin-left: 15px; position: relative;\">
                <i class=\"fa fa-heart\" aria-hidden=\"true\" style=\"color: #ffbe33;\"></i>
                {% if app.session.get('favoris')|length > 0 %}
                  <span class=\"badge badge-pill badge-danger\" id=\"favoris-count\" style=\"position: absolute; top: -10px; right: -10px; font-size: 10px;\">
                    {{ app.session.get('favoris')|length }}
                  </span>
                {% endif %}
              </a>
              <a class=\"cart_link\" href=\"{{ path('app_commande_historique') }}\" title=\"Historique d'achats\" style=\"margin-left: 15px;\">
                <i class=\"fa fa-history\" aria-hidden=\"true\"></i>
              </a>
              <a class=\"cart_link\" href=\"{{ path('app_panier_index') }}\" title=\"Mon Panier\">
                <i class=\"fa fa-shopping-cart\" aria-hidden=\"true\"></i>
              </a>
            {% endif %}
          </div>
        </div>
      </nav>
    </div>
  </header>
  <!-- end header -->

  <div class=\"container\" style=\"margin-top: 30px; margin-bottom: 30px;\">
    {% block body %}{% endblock %}
  </div>

  <!-- footer -->
  <footer class=\"footer_section\">
    <div class=\"container\">
      <div class=\"row\">
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_contact\">
            <h4>Contact Us</h4>
            <div class=\"contact_link_box\">
              <a href=\"\"><i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i><span> Location</span></a>
              <a href=\"\"><i class=\"fa fa-phone\" aria-hidden=\"true\"></i><span> Call +01 1234567890</span></a>
              <a href=\"\"><i class=\"fa fa-envelope\" aria-hidden=\"true\"></i><span> pegasus@gmail.com</span></a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <div class=\"footer_detail\">
            <a href=\"\" class=\"footer-logo\">Pegasus</a>
            <p>Plateforme artistique pour découvrir et acheter des œuvres d'art uniques.</p>
            <div class=\"footer_social\">
              <a href=\"\"><i class=\"fa fa-facebook\" aria-hidden=\"true\"></i></a>
              <a href=\"\"><i class=\"fa fa-twitter\" aria-hidden=\"true\"></i></a>
              <a href=\"\"><i class=\"fa fa-instagram\" aria-hidden=\"true\"></i></a>
            </div>
          </div>
        </div>
        <div class=\"col-md-4 footer-col\">
          <h4>Horaires</h4>
          <p>Tous les jours</p>
          <p>24h/24 - 7j/7</p>
        </div>
      </div>
      <div class=\"footer-info\">
        <p>&copy; 2026 Pegasus - Tous droits réservés</p>
      </div>
    </div>
  </footer>
  <!-- end footer -->

  <script src=\"{{ asset('front/js/jquery-3.4.1.min.js') }}\"></script>
  <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"></script>
  <script src=\"{{ asset('front/js/bootstrap.js') }}\"></script>
  <script src=\"{{ asset('front/js/custom.js') }}\"></script>
  {% block javascripts %}{% endblock %}
</body>
</html>", "base_front.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\base_front.html.twig");
    }
}
