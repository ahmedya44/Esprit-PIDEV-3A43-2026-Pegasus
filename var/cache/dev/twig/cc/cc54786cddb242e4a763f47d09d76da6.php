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

/* back/commandes.html.twig */
class __TwigTemplate_05085613359bbef32fc8ad76f4e9f2f1 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/commandes.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/commandes.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"fr\">
  <head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Historique des Achats - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/favicon.ico"), "html", null, true);
        yield "\" type=\"image/x-icon\" />

    <!-- Fonts and icons -->
    <script src=\"";
        // line 10
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/webfont/webfont.min.js"), "html", null, true);
        yield "\"></script>
    <script>
      WebFont.load({
        google: { families: [\"Public Sans:300,400,500,600,700\"] },
        custom: {
          families: [\"Font Awesome 5 Solid\", \"Font Awesome 5 Regular\", \"Font Awesome 5 Brands\", \"simple-line-icons\"],
          urls: [\"";
        // line 16
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/fonts.min.css"), "html", null, true);
        yield "\"],
        },
        active: function () { sessionStorage.fonts = true; },
      });
    </script>

    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"";
        // line 23
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 24
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/plugins.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 25
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/kaiadmin.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 26
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/demo.css"), "html", null, true);
        yield "\" />
  </head>
  <body>
    <div class=\"wrapper\">
      <!-- Sidebar -->
      <div class=\"sidebar\" data-background-color=\"dark\">
        <div class=\"sidebar-logo\">
          <div class=\"logo-header\" data-background-color=\"dark\">
            <a href=\"";
        // line 34
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"logo\">
              <img src=\"";
        // line 35
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
            </a>
            <div class=\"nav-toggle\">
              <button class=\"btn btn-toggle toggle-sidebar\"><i class=\"gg-menu-right\"></i></button>
              <button class=\"btn btn-toggle sidenav-toggler\"><i class=\"gg-menu-left\"></i></button>
            </div>
            <button class=\"topbar-toggler more\"><i class=\"gg-more-vertical-alt\"></i></button>
          </div>
        </div>
        <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
          <div class=\"sidebar-content\">
            <ul class=\"nav nav-secondary\">
              <li class=\"nav-item active\">
                <a data-bs-toggle=\"collapse\" href=\"#dashboard\" class=\"collapsed\" aria-expanded=\"false\">
                  <i class=\"fas fa-home\"></i>
                  <p>Dashboard</p>
                  <span class=\"caret\"></span>
                </a>
                <div class=\"collapse show\" id=\"dashboard\">
                  <ul class=\"nav nav-collapse\">
                    <li>
                      <a href=\"";
        // line 56
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_produit_attente");
        yield "\">
                        <span class=\"sub-item\">Gérer Produits</span>
                      </a>
                    </li>
                    <li class=\"active\">
                      <a href=\"";
        // line 61
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_commandes_index");
        yield "\">
                        <span class=\"sub-item\">Historique des achats</span>
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
            <div class=\"logo-header\" data-background-color=\"dark\">
              <a href=\"";
        // line 78
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"logo\">
                <img src=\"";
        // line 79
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
              </a>
              <div class=\"nav-toggle\">
                <button class=\"btn btn-toggle toggle-sidebar\"><i class=\"gg-menu-right\"></i></button>
                <button class=\"btn btn-toggle sidenav-toggler\"><i class=\"gg-menu-left\"></i></button>
              </div>
              <button class=\"topbar-toggler more\"><i class=\"gg-more-vertical-alt\"></i></button>
            </div>
          </div>
          <nav class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\">
            <div class=\"container-fluid\">
              <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                <li class=\"nav-item topbar-user dropdown hidden-caret\">
                  <a class=\"dropdown-toggle profile-pic\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
                    <div class=\"avatar-sm\">
                      <img src=\"";
        // line 94
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\" />
                    </div>
                    <span class=\"profile-username\">
                      <span class=\"op-7\">Hi,</span>
                      <span class=\"fw-bold\">Admin</span>
                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </nav>
        </div>

        <div class=\"container\">
          <div class=\"page-inner\">
            <div class=\"page-header\">
              <h4 class=\"page-title\">Historique des achats</h4>
              <ul class=\"breadcrumbs\">
                <li class=\"nav-home\">
                  <a href=\"";
        // line 113
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\"><i class=\"icon-home\"></i></a>
                </li>
                <li class=\"separator\"><i class=\"icon-arrow-right\"></i></li>
                <li class=\"nav-item\"><a href=\"#\">Commandes</a></li>
              </ul>
            </div>

            <div class=\"page-category\">
              ";
        // line 121
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 121, $this->source); })()), "flashes", ["success"], "method", false, false, false, 121));
        foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
            // line 122
            yield "                <div class=\"alert alert-success\">";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
            yield "</div>
              ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 124
        yield "
              <div class=\"card\">
                <div class=\"card-header\">
                  <div class=\"d-flex justify-content-between align-items-center\">
                    <div class=\"card-title\">Historique global des commandes</div>
                    <span class=\"badge badge-info\">";
        // line 129
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["pagination"]) || array_key_exists("pagination", $context) ? $context["pagination"] : (function () { throw new RuntimeError('Variable "pagination" does not exist.', 129, $this->source); })()), "totalItemCount", [], "any", false, false, false, 129), "html", null, true);
        yield " commande(s)</span>
                  </div>
                </div>
                <div class=\"card-body\">
                  <div class=\"table-responsive\">
                    <table class=\"table table-hover\">
                      <thead>
                        <tr>
                          <th>N° Commande</th>
                          <th>Date</th>
                          <th>Articles</th>
                          <th>Total</th>
                          <th>Statut</th>
                        </tr>
                      </thead>
                      <tbody>
                        ";
        // line 145
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["pagination"]) || array_key_exists("pagination", $context) ? $context["pagination"] : (function () { throw new RuntimeError('Variable "pagination" does not exist.', 145, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["commande"]) {
            // line 146
            yield "                        <tr>
                          <td><strong>#";
            // line 147
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "id", [], "any", false, false, false, 147), "html", null, true);
            yield "</strong></td>
                          <td>
                            <i class=\"fa fa-calendar text-muted\"></i>
                            ";
            // line 150
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "dateCommande", [], "any", false, false, false, 150)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "dateCommande", [], "any", false, false, false, 150), "d/m/Y à H:i"), "html", null, true)) : ("Date inconnue"));
            yield "
                          </td>
                          <td>
                            ";
            // line 153
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "ligneCommandes", [], "any", false, false, false, 153)) > 0)) {
                // line 154
                yield "                              ";
                $context['_parent'] = $context;
                $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "ligneCommandes", [], "any", false, false, false, 154));
                foreach ($context['_seq'] as $context["_key"] => $context["ligne"]) {
                    // line 155
                    yield "                                <span class=\"badge badge-light text-dark border\">
                                  ";
                    // line 156
                    yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 156), "nom", [], "any", false, false, false, 156), "html", null, true);
                    yield " × ";
                    yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "quantite", [], "any", false, false, false, 156), "html", null, true);
                    yield "
                                </span>
                              ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_key'], $context['ligne'], $context['_parent']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 159
                yield "                            ";
            } else {
                // line 160
                yield "                              <span class=\"text-muted\">—</span>
                            ";
            }
            // line 162
            yield "                          </td>
                          <td><strong class=\"text-success\">";
            // line 163
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatNumber(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "total", [], "any", false, false, false, 163), 2, ",", " "), "html", null, true);
            yield " €</strong></td>
                          <td>
                            ";
            // line 165
            if (((CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 165) == "validee") || (CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 165) == "validée"))) {
                // line 166
                yield "                              <span class=\"badge badge-success\"><i class=\"fa fa-check\"></i> Validée</span>
                            ";
            } elseif (((CoreExtension::getAttribute($this->env, $this->source,             // line 167
$context["commande"], "statut", [], "any", false, false, false, 167) == "payee") || (CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 167) == "payée"))) {
                // line 168
                yield "                              <span class=\"badge badge-primary\"><i class=\"fa fa-credit-card\"></i> Payée (Stripe)</span>
                            ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 169
$context["commande"], "statut", [], "any", false, false, false, 169) == "en_cours")) {
                // line 170
                yield "                              <span class=\"badge badge-warning\"><i class=\"fa fa-clock-o\"></i> En cours</span>
                            ";
            } elseif (((CoreExtension::getAttribute($this->env, $this->source,             // line 171
$context["commande"], "statut", [], "any", false, false, false, 171) == "annulee") || (CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 171) == "annulée"))) {
                // line 172
                yield "                              <span class=\"badge badge-danger\"><i class=\"fa fa-times\"></i> Annulée</span>
                            ";
            } else {
                // line 174
                yield "                              <span class=\"badge badge-secondary\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::capitalize($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 174)), "html", null, true);
                yield "</span>
                            ";
            }
            // line 176
            yield "                          </td>
                        </tr>
                        ";
            $context['_iterated'] = true;
        }
        // line 178
        if (!$context['_iterated']) {
            // line 179
            yield "                        <tr>
                          <td colspan=\"5\" class=\"text-center py-5\">
                            <i class=\"fa fa-shopping-cart fa-3x text-muted mb-3\"></i>
                            <p class=\"text-muted\">Aucune commande trouvée.</p>
                          </td>
                        </tr>
                        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['commande'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 186
        yield "                      </tbody>
                    </table>
                  </div>
                  <div class=\"d-flex justify-content-center mt-3\">
                    ";
        // line 190
        yield $this->env->getRuntime('Knp\Bundle\PaginatorBundle\Twig\Extension\PaginationRuntime')->render($this->env, (isset($context["pagination"]) || array_key_exists("pagination", $context) ? $context["pagination"] : (function () { throw new RuntimeError('Variable "pagination" does not exist.', 190, $this->source); })()));
        yield "
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
                <li class=\"nav-item\"><a class=\"nav-link\" href=\"http://www.themekita.com\">ThemeKita</a></li>
              </ul>
            </nav>
            <div class=\"copyright\">2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by <a href=\"http://www.themekita.com\">ThemeKita</a></div>
          </div>
        </footer>
      </div>
    </div>

    <script src=\"";
        // line 212
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 213
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 214
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 215
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 216
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
        return "back/commandes.html.twig";
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
        return array (  404 => 216,  400 => 215,  396 => 214,  392 => 213,  388 => 212,  363 => 190,  357 => 186,  345 => 179,  343 => 178,  337 => 176,  331 => 174,  327 => 172,  325 => 171,  322 => 170,  320 => 169,  317 => 168,  315 => 167,  312 => 166,  310 => 165,  305 => 163,  302 => 162,  298 => 160,  295 => 159,  284 => 156,  281 => 155,  276 => 154,  274 => 153,  268 => 150,  262 => 147,  259 => 146,  254 => 145,  235 => 129,  228 => 124,  219 => 122,  215 => 121,  204 => 113,  182 => 94,  164 => 79,  160 => 78,  140 => 61,  132 => 56,  108 => 35,  104 => 34,  93 => 26,  89 => 25,  85 => 24,  81 => 23,  71 => 16,  62 => 10,  56 => 7,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"fr\">
  <head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Historique des Achats - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"{{ asset('back/img/kaiadmin/favicon.ico') }}\" type=\"image/x-icon\" />

    <!-- Fonts and icons -->
    <script src=\"{{ asset('back/js/plugin/webfont/webfont.min.js') }}\"></script>
    <script>
      WebFont.load({
        google: { families: [\"Public Sans:300,400,500,600,700\"] },
        custom: {
          families: [\"Font Awesome 5 Solid\", \"Font Awesome 5 Regular\", \"Font Awesome 5 Brands\", \"simple-line-icons\"],
          urls: [\"{{ asset('back/css/fonts.min.css') }}\"],
        },
        active: function () { sessionStorage.fonts = true; },
      });
    </script>

    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/bootstrap.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/plugins.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/kaiadmin.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/demo.css') }}\" />
  </head>
  <body>
    <div class=\"wrapper\">
      <!-- Sidebar -->
      <div class=\"sidebar\" data-background-color=\"dark\">
        <div class=\"sidebar-logo\">
          <div class=\"logo-header\" data-background-color=\"dark\">
            <a href=\"{{ path('back_dashboard') }}\" class=\"logo\">
              <img src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
            </a>
            <div class=\"nav-toggle\">
              <button class=\"btn btn-toggle toggle-sidebar\"><i class=\"gg-menu-right\"></i></button>
              <button class=\"btn btn-toggle sidenav-toggler\"><i class=\"gg-menu-left\"></i></button>
            </div>
            <button class=\"topbar-toggler more\"><i class=\"gg-more-vertical-alt\"></i></button>
          </div>
        </div>
        <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
          <div class=\"sidebar-content\">
            <ul class=\"nav nav-secondary\">
              <li class=\"nav-item active\">
                <a data-bs-toggle=\"collapse\" href=\"#dashboard\" class=\"collapsed\" aria-expanded=\"false\">
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
                    <li class=\"active\">
                      <a href=\"{{ path('admin_commandes_index') }}\">
                        <span class=\"sub-item\">Historique des achats</span>
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
            <div class=\"logo-header\" data-background-color=\"dark\">
              <a href=\"{{ path('back_dashboard') }}\" class=\"logo\">
                <img src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
              </a>
              <div class=\"nav-toggle\">
                <button class=\"btn btn-toggle toggle-sidebar\"><i class=\"gg-menu-right\"></i></button>
                <button class=\"btn btn-toggle sidenav-toggler\"><i class=\"gg-menu-left\"></i></button>
              </div>
              <button class=\"topbar-toggler more\"><i class=\"gg-more-vertical-alt\"></i></button>
            </div>
          </div>
          <nav class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\">
            <div class=\"container-fluid\">
              <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                <li class=\"nav-item topbar-user dropdown hidden-caret\">
                  <a class=\"dropdown-toggle profile-pic\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
                    <div class=\"avatar-sm\">
                      <img src=\"{{ asset('back/img/profile.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\" />
                    </div>
                    <span class=\"profile-username\">
                      <span class=\"op-7\">Hi,</span>
                      <span class=\"fw-bold\">Admin</span>
                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </nav>
        </div>

        <div class=\"container\">
          <div class=\"page-inner\">
            <div class=\"page-header\">
              <h4 class=\"page-title\">Historique des achats</h4>
              <ul class=\"breadcrumbs\">
                <li class=\"nav-home\">
                  <a href=\"{{ path('back_dashboard') }}\"><i class=\"icon-home\"></i></a>
                </li>
                <li class=\"separator\"><i class=\"icon-arrow-right\"></i></li>
                <li class=\"nav-item\"><a href=\"#\">Commandes</a></li>
              </ul>
            </div>

            <div class=\"page-category\">
              {% for message in app.flashes('success') %}
                <div class=\"alert alert-success\">{{ message }}</div>
              {% endfor %}

              <div class=\"card\">
                <div class=\"card-header\">
                  <div class=\"d-flex justify-content-between align-items-center\">
                    <div class=\"card-title\">Historique global des commandes</div>
                    <span class=\"badge badge-info\">{{ pagination.totalItemCount }} commande(s)</span>
                  </div>
                </div>
                <div class=\"card-body\">
                  <div class=\"table-responsive\">
                    <table class=\"table table-hover\">
                      <thead>
                        <tr>
                          <th>N° Commande</th>
                          <th>Date</th>
                          <th>Articles</th>
                          <th>Total</th>
                          <th>Statut</th>
                        </tr>
                      </thead>
                      <tbody>
                        {% for commande in pagination %}
                        <tr>
                          <td><strong>#{{ commande.id }}</strong></td>
                          <td>
                            <i class=\"fa fa-calendar text-muted\"></i>
                            {{ commande.dateCommande ? commande.dateCommande|date('d/m/Y à H:i') : 'Date inconnue' }}
                          </td>
                          <td>
                            {% if commande.ligneCommandes|length > 0 %}
                              {% for ligne in commande.ligneCommandes %}
                                <span class=\"badge badge-light text-dark border\">
                                  {{ ligne.produit.nom }} × {{ ligne.quantite }}
                                </span>
                              {% endfor %}
                            {% else %}
                              <span class=\"text-muted\">—</span>
                            {% endif %}
                          </td>
                          <td><strong class=\"text-success\">{{ commande.total|number_format(2, ',', ' ') }} €</strong></td>
                          <td>
                            {% if commande.statut == 'validee' or commande.statut == 'validée' %}
                              <span class=\"badge badge-success\"><i class=\"fa fa-check\"></i> Validée</span>
                            {% elseif commande.statut == 'payee' or commande.statut == 'payée' %}
                              <span class=\"badge badge-primary\"><i class=\"fa fa-credit-card\"></i> Payée (Stripe)</span>
                            {% elseif commande.statut == 'en_cours' %}
                              <span class=\"badge badge-warning\"><i class=\"fa fa-clock-o\"></i> En cours</span>
                            {% elseif commande.statut == 'annulee' or commande.statut == 'annulée' %}
                              <span class=\"badge badge-danger\"><i class=\"fa fa-times\"></i> Annulée</span>
                            {% else %}
                              <span class=\"badge badge-secondary\">{{ commande.statut|capitalize }}</span>
                            {% endif %}
                          </td>
                        </tr>
                        {% else %}
                        <tr>
                          <td colspan=\"5\" class=\"text-center py-5\">
                            <i class=\"fa fa-shopping-cart fa-3x text-muted mb-3\"></i>
                            <p class=\"text-muted\">Aucune commande trouvée.</p>
                          </td>
                        </tr>
                        {% endfor %}
                      </tbody>
                    </table>
                  </div>
                  <div class=\"d-flex justify-content-center mt-3\">
                    {{ knp_pagination_render(pagination) }}
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
                <li class=\"nav-item\"><a class=\"nav-link\" href=\"http://www.themekita.com\">ThemeKita</a></li>
              </ul>
            </nav>
            <div class=\"copyright\">2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by <a href=\"http://www.themekita.com\">ThemeKita</a></div>
          </div>
        </footer>
      </div>
    </div>

    <script src=\"{{ asset('back/js/core/jquery-3.7.1.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/popper.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/bootstrap.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/kaiadmin.min.js') }}\"></script>
  </body>
</html>
", "back/commandes.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\back\\commandes.html.twig");
    }
}
