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

/* front/gallery_new.html.twig */
class __TwigTemplate_8e0b4d40402b161ba4d1846b795d4b54 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/gallery_new.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/gallery_new.html.twig"));

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
  <link href=\"";
        // line 26
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/font-awesome.min.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />

  <!-- Custom styles for this template -->
  <link href=\"";
        // line 29
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/style.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"";
        // line 31
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/css/responsive.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />

</head>

<body class=\"sub_page\">

  <div class=\"hero_area\">
    <div class=\"bg-box\">
      <img src=\"";
        // line 39
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/hero-bg.jpg"), "html", null, true);
        yield "\" alt=\"\">
    </div>
    <!-- header section strats -->
    <header class=\"header_section\">
      <div class=\"container\">
        <nav class=\"navbar navbar-expand-lg custom_nav-container \">
          <a class=\"navbar-brand\" href=\"";
        // line 45
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
        // line 58
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_home");
        yield "\">Home </a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 61
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_menu");
        yield "\">Menu</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 64
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_about");
        yield "\">About</a>
              </li>
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 67
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_book");
        yield "\">Book Table</a>
              </li>
              <li class=\"nav-item active\">
                <a class=\"nav-link\" href=\"";
        // line 70
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

  <section class=\"book_section layout_padding\">
    <div class=\"container\">
      <div class=\"heading_container\">
        <h2>
          Ajouter une oeuvre
        </h2>
      </div>

      <div class=\"mb-4\">
        <a href=\"";
        // line 126
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">Retour à la galerie</a>
      </div>

      <div class=\"row\">
        <div class=\"col-md-8\">
          <div class=\"form_container\">
            ";
        // line 132
        yield         $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderBlock((isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 132, $this->source); })()), 'form_start');
        yield "
              <div class=\"form-group\">
                ";
        // line 134
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 134, $this->source); })()), "title", [], "any", false, false, false, 134), 'label');
        yield "
                ";
        // line 135
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 135, $this->source); })()), "title", [], "any", false, false, false, 135), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                <div class=\"mt-2\">
                  <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"";
        // line 137
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 137, $this->source); })()), "title", [], "any", false, false, false, 137), "vars", [], "any", false, false, false, 137), "id", [], "any", false, false, false, 137), "html", null, true);
        yield "\">Traduire EN→FR</button>
                </div>
                ";
        // line 139
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 139, $this->source); })()), "title", [], "any", false, false, false, 139), 'errors');
        yield "
              </div>
              <div class=\"form-group\">
                ";
        // line 142
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 142, $this->source); })()), "description", [], "any", false, false, false, 142), 'label');
        yield "
                ";
        // line 143
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 143, $this->source); })()), "description", [], "any", false, false, false, 143), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                <div class=\"mt-2\">
                  <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"";
        // line 145
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 145, $this->source); })()), "description", [], "any", false, false, false, 145), "vars", [], "any", false, false, false, 145), "id", [], "any", false, false, false, 145), "html", null, true);
        yield "\">Traduire EN→FR</button>
                </div>
                ";
        // line 147
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 147, $this->source); })()), "description", [], "any", false, false, false, 147), 'errors');
        yield "
              </div>
              <div class=\"form-group\">
                ";
        // line 150
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 150, $this->source); })()), "imageUrl", [], "any", false, false, false, 150), 'label');
        yield "
                ";
        // line 151
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 151, $this->source); })()), "imageUrl", [], "any", false, false, false, 151), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                ";
        // line 152
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 152, $this->source); })()), "imageUrl", [], "any", false, false, false, 152), 'errors');
        yield "
              </div>
              <div class=\"btn_box\">
                <button class=\"btn btn-warning\">Enregistrer</button>
              </div>
            ";
        // line 157
        yield         $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderBlock((isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 157, $this->source); })()), 'form_end');
        yield "
          </div>
        </div>
      </div>
    </div>
  </section>

  <script>
    (function () {
      async function translateText(text) {
        const res = await fetch('";
        // line 167
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("api_translate");
        yield "', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ text: text, source: 'EN', target: 'FR' }),
        });

        const data = await res.json();
        if (!res.ok) {
          throw new Error(data && data.error ? data.error : 'Translation failed');
        }
        return data.translatedText || '';
      }

      document.querySelectorAll('.js-translate').forEach(function (btn) {
        btn.addEventListener('click', async function () {
          const targetId = btn.getAttribute('data-target');
          const el = document.getElementById(targetId);
          if (!el) return;

          const original = el.value || '';
          btn.disabled = true;

          try {
            const translated = await translateText(original);
            el.value = translated;
          } catch (e) {
            alert(e.message || 'Erreur de traduction');
          } finally {
            btn.disabled = false;
          }
        });
      });
    })();
  </script>

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
        // line 285
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/jquery-3.4.1.min.js"), "html", null, true);
        yield "\"></script>
  <!-- popper js -->
  <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\">
  </script>
  <!-- bootstrap js -->
  <script src=\"";
        // line 290
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
        // line 299
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/js/custom.js"), "html", null, true);
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
        return "front/gallery_new.html.twig";
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
        return array (  435 => 299,  423 => 290,  415 => 285,  294 => 167,  281 => 157,  273 => 152,  269 => 151,  265 => 150,  259 => 147,  254 => 145,  249 => 143,  245 => 142,  239 => 139,  234 => 137,  229 => 135,  225 => 134,  220 => 132,  211 => 126,  152 => 70,  146 => 67,  140 => 64,  134 => 61,  128 => 58,  112 => 45,  103 => 39,  92 => 31,  87 => 29,  81 => 26,  71 => 19,  63 => 14,  48 => 1,);
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
  <link href=\"{{ asset('front/css/font-awesome.min.css') }}\" rel=\"stylesheet\" />

  <!-- Custom styles for this template -->
  <link href=\"{{ asset('front/css/style.css') }}\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"{{ asset('front/css/responsive.css') }}\" rel=\"stylesheet\" />

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

  <section class=\"book_section layout_padding\">
    <div class=\"container\">
      <div class=\"heading_container\">
        <h2>
          Ajouter une oeuvre
        </h2>
      </div>

      <div class=\"mb-4\">
        <a href=\"{{ path('front_gallery') }}\">Retour à la galerie</a>
      </div>

      <div class=\"row\">
        <div class=\"col-md-8\">
          <div class=\"form_container\">
            {{ form_start(form) }}
              <div class=\"form-group\">
                {{ form_label(form.title) }}
                {{ form_widget(form.title, { attr: { class: 'form-control' } }) }}
                <div class=\"mt-2\">
                  <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"{{ form.title.vars.id }}\">Traduire EN→FR</button>
                </div>
                {{ form_errors(form.title) }}
              </div>
              <div class=\"form-group\">
                {{ form_label(form.description) }}
                {{ form_widget(form.description, { attr: { class: 'form-control' } }) }}
                <div class=\"mt-2\">
                  <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"{{ form.description.vars.id }}\">Traduire EN→FR</button>
                </div>
                {{ form_errors(form.description) }}
              </div>
              <div class=\"form-group\">
                {{ form_label(form.imageUrl) }}
                {{ form_widget(form.imageUrl, { attr: { class: 'form-control' } }) }}
                {{ form_errors(form.imageUrl) }}
              </div>
              <div class=\"btn_box\">
                <button class=\"btn btn-warning\">Enregistrer</button>
              </div>
            {{ form_end(form) }}
          </div>
        </div>
      </div>
    </div>
  </section>

  <script>
    (function () {
      async function translateText(text) {
        const res = await fetch('{{ path('api_translate') }}', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ text: text, source: 'EN', target: 'FR' }),
        });

        const data = await res.json();
        if (!res.ok) {
          throw new Error(data && data.error ? data.error : 'Translation failed');
        }
        return data.translatedText || '';
      }

      document.querySelectorAll('.js-translate').forEach(function (btn) {
        btn.addEventListener('click', async function () {
          const targetId = btn.getAttribute('data-target');
          const el = document.getElementById(targetId);
          if (!el) return;

          const original = el.value || '';
          btn.disabled = true;

          try {
            const translated = await translateText(original);
            el.value = translated;
          } catch (e) {
            alert(e.message || 'Erreur de traduction');
          } finally {
            btn.disabled = false;
          }
        });
      });
    })();
  </script>

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

</body>

</html>
", "front/gallery_new.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\front\\gallery_new.html.twig");
    }
}
