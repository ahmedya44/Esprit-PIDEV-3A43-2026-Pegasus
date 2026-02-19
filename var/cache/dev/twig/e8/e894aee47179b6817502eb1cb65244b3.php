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

/* back/art-archived.html.twig */
class __TwigTemplate_0454e661e50681864a982ba0e4d440ff extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-archived.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-archived.html.twig"));

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
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/images/favicon.png"), "html", null, true);
        yield "\" type=\"\">

  <title> Publications Archivées</title>

  <!-- bootstrap core css -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"";
        // line 19
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.css"), "html", null, true);
        yield "\" />

  <!-- font awesome style -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"";
        // line 22
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/font-awesome.min.css"), "html", null, true);
        yield "\" />

  <!-- Custom styles for this template -->
  <link href=\"";
        // line 25
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/style.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"";
        // line 27
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/responsive.css"), "html", null, true);
        yield "\" rel=\"stylesheet\" />

</head>

<body>
  <div class=\"container-scroller\">
    <!-- sidebar -->
    <nav class=\"sidebar\">
      <div class=\"sidebar-item-wrapper\">
        <div class=\"nav-logo\">
          <a class=\"sidebar-brand\" href=\"";
        // line 37
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_index");
        yield "\">
            <span>Admin Panel</span>
          </a>
        </div>
        <ul class=\"nav\">
          <li class=\"nav-item\">
            <a class=\"nav-link\" href=\"";
        // line 43
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_index");
        yield "\">
              <i class=\"fa fa-home\"></i>
              <span class=\"nav-title\">Dashboard</span>
            </a>
          </li>
          <li class=\"nav-section\">
            <span class=\"sidebar-mini-icon\">
              <i class=\"fa fa-ellipsis-h\"></i>
            </span>
            <h4 class=\"text-section\">Gestion</h4>
          </li>
          <li class=\"nav-item\">
            <a href=\"";
        // line 55
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_index");
        yield "\">
              <i class=\"fas fa-image\"></i>
              <p>Gestion des œuvres</p>
            </a>
          </li>
          <li class=\"nav-item active\">
            <a href=\"";
        // line 61
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_archived");
        yield "\">
              <i class=\"fas fa-archive\"></i>
              <p>Publications archivées</p>
            </a>
          </li>
        </ul>
      </div>
    </nav>
    <!-- end sidebar -->

    <!-- main content -->
    <div class=\"container-fluid page-body-wrapper\">
      <!-- top bar -->
      <nav class=\"navbar navbar-expand-lg navbar-light\">
        <div class=\"container-fluid\">
          <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">
            <span class=\"navbar-toggler-icon\"></span>
          </button>

          <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
            <ul class=\"navbar-nav mr-auto\">
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"";
        // line 83
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">
                  <i class=\"fa fa-eye\"></i>
                  Voir le site
                </a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- end top bar -->

      <!-- page content -->
      <div class=\"content-wrapper\">
        <div class=\"page-header\">
          <h3 class=\"page-title\">
            <span class=\"page-title-icon bg-gradient-primary text-white mr-2\">
              <i class=\"fas fa-archive\"></i>
            </span>
            Publications Archivées
          </h3>
          <nav aria-label=\"breadcrumb\">
            <ol class=\"breadcrumb\">
              <li class=\"breadcrumb-item\"><a href=\"";
        // line 105
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_index");
        yield "\">Gestion</a></li>
              <li class=\"breadcrumb-item active\" aria-current=\"page\">Archives</li>
            </ol>
          </nav>
        </div>

        <div class=\"row\">
          <div class=\"col-12 grid-margin stretch-card\">
            <div class=\"card\">
              <div class=\"card-body\">
                <h4 class=\"card-title\">Liste des publications archivées</h4>
                <p class=\"card-description\">
                  Les publications archivées ne sont pas visibles dans la galerie. Vous pouvez les récupérer ou les supprimer définitivement.
                </p>

                ";
        // line 120
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 120, $this->source); })()), "session", [], "any", false, false, false, 120), "flashbag", [], "any", false, false, false, 120), "all", [], "method", false, false, false, 120));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 121
            yield "                  ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 122
                yield "                    <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
                      ";
                // line 123
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
            // line 129
            yield "                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 130
        yield "
                <div class=\"table-responsive\">
                  <table class=\"table table-hover\">
                    <thead>
                      <tr>
                        <th>Image</th>
                        <th>Titre</th>
                        <th>Description</th>
                        <th>Statut</th>
                        <th>Date</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      ";
        // line 144
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["arts"]) || array_key_exists("arts", $context) ? $context["arts"] : (function () { throw new RuntimeError('Variable "arts" does not exist.', 144, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["art"]) {
            // line 145
            yield "                        <tr>
                          <td>
                            <img src=\"";
            // line 147
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "imageUrl", [], "any", false, false, false, 147), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 147), "html", null, true);
            yield "\" style=\"width: 50px; height: 50px; object-fit: cover;\">
                          </td>
                          <td>";
            // line 149
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 149), "html", null, true);
            yield "</td>
                          <td>";
            // line 150
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 150), 0, 50), "html", null, true);
            yield "...</td>
                          <td>
                            <span class=\"badge bg-secondary\">Archivé</span>
                          </td>
                          <td>";
            // line 154
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 154)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 154), "d/m/Y H:i"), "html", null, true)) : (""));
            yield "</td>
                          <td>
                                                            <!-- Bouton récupérer -->
                                                            <form method=\"post\" action=\"";
            // line 157
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_restore", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 157)]), "html", null, true);
            yield "\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"";
            // line 158
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("restore" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 158))), "html", null, true);
            yield "\">
                                                                <button type=\"submit\" class=\"btn btn-success btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Récupérer\">
                                                                    <i class=\"fas fa-undo\"></i> Récupérer
                                                                </button>
                                                            </form>
                                                            
                                                            <!-- Bouton supprimer définitivement -->
                                                            <form method=\"post\" action=\"";
            // line 165
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_delete", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 165)]), "html", null, true);
            yield "\" style=\"display: inline-block;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"";
            // line 166
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("delete" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 166))), "html", null, true);
            yield "\">
                                                                <button type=\"submit\" class=\"btn btn-danger btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Supprimer définitivement\" onclick=\"return confirm('Supprimer définitivement cette œuvre ? Cette action est irréversible.')\">
                                                                    <i class=\"fas fa-trash\"></i> Supprimer
                                                                </button>
                                                            </form>
                                                        </td>
                        </tr>
                      ";
            $context['_iterated'] = true;
        }
        // line 173
        if (!$context['_iterated']) {
            // line 174
            yield "                        <tr>
                          <td colspan=\"6\" class=\"text-center\">Aucune publication archivée</td>
                        </tr>
                      ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['art'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 178
        yield "                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- end page content -->
    </div>
    <!-- end main content -->
  </div>

  <!-- container-scroller -->

  <!-- plugins:js -->
  <script src=\"";
        // line 194
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/vendors/js/vendor.bundle.base.js"), "html", null, true);
        yield "\"></script>
  <!-- endinject -->

  <!-- Plugin js for this page -->
  <!-- End plugin js for this page -->

  <!-- Custom js for this page-->
  <script src=\"";
        // line 201
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/off-canvas.js"), "html", null, true);
        yield "\"></script>
  <script src=\"";
        // line 202
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/hoverable-collapse.js"), "html", null, true);
        yield "\"></script>
  <script src=\"";
        // line 203
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/misc.js"), "html", null, true);
        yield "\"></script>
  <!-- end custom js for this page-->

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
        return "back/art-archived.html.twig";
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
        return array (  360 => 203,  356 => 202,  352 => 201,  342 => 194,  324 => 178,  315 => 174,  313 => 173,  301 => 166,  297 => 165,  287 => 158,  283 => 157,  277 => 154,  270 => 150,  266 => 149,  259 => 147,  255 => 145,  250 => 144,  234 => 130,  228 => 129,  216 => 123,  211 => 122,  206 => 121,  202 => 120,  184 => 105,  159 => 83,  134 => 61,  125 => 55,  110 => 43,  101 => 37,  88 => 27,  83 => 25,  77 => 22,  71 => 19,  63 => 14,  48 => 1,);
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
  <link rel=\"shortcut icon\" href=\"{{ asset('back/images/favicon.png') }}\" type=\"\">

  <title> Publications Archivées</title>

  <!-- bootstrap core css -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"{{ asset('back/css/bootstrap.css') }}\" />

  <!-- font awesome style -->
  <link rel=\"stylesheet\" type=\"text/css\" href=\"{{ asset('back/css/font-awesome.min.css') }}\" />

  <!-- Custom styles for this template -->
  <link href=\"{{ asset('back/css/style.css') }}\" rel=\"stylesheet\" />
  <!-- responsive style -->
  <link href=\"{{ asset('back/css/responsive.css') }}\" rel=\"stylesheet\" />

</head>

<body>
  <div class=\"container-scroller\">
    <!-- sidebar -->
    <nav class=\"sidebar\">
      <div class=\"sidebar-item-wrapper\">
        <div class=\"nav-logo\">
          <a class=\"sidebar-brand\" href=\"{{ path('admin_art_index') }}\">
            <span>Admin Panel</span>
          </a>
        </div>
        <ul class=\"nav\">
          <li class=\"nav-item\">
            <a class=\"nav-link\" href=\"{{ path('admin_art_index') }}\">
              <i class=\"fa fa-home\"></i>
              <span class=\"nav-title\">Dashboard</span>
            </a>
          </li>
          <li class=\"nav-section\">
            <span class=\"sidebar-mini-icon\">
              <i class=\"fa fa-ellipsis-h\"></i>
            </span>
            <h4 class=\"text-section\">Gestion</h4>
          </li>
          <li class=\"nav-item\">
            <a href=\"{{ path('admin_art_index') }}\">
              <i class=\"fas fa-image\"></i>
              <p>Gestion des œuvres</p>
            </a>
          </li>
          <li class=\"nav-item active\">
            <a href=\"{{ path('admin_art_archived') }}\">
              <i class=\"fas fa-archive\"></i>
              <p>Publications archivées</p>
            </a>
          </li>
        </ul>
      </div>
    </nav>
    <!-- end sidebar -->

    <!-- main content -->
    <div class=\"container-fluid page-body-wrapper\">
      <!-- top bar -->
      <nav class=\"navbar navbar-expand-lg navbar-light\">
        <div class=\"container-fluid\">
          <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">
            <span class=\"navbar-toggler-icon\"></span>
          </button>

          <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">
            <ul class=\"navbar-nav mr-auto\">
              <li class=\"nav-item\">
                <a class=\"nav-link\" href=\"{{ path('front_gallery') }}\">
                  <i class=\"fa fa-eye\"></i>
                  Voir le site
                </a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- end top bar -->

      <!-- page content -->
      <div class=\"content-wrapper\">
        <div class=\"page-header\">
          <h3 class=\"page-title\">
            <span class=\"page-title-icon bg-gradient-primary text-white mr-2\">
              <i class=\"fas fa-archive\"></i>
            </span>
            Publications Archivées
          </h3>
          <nav aria-label=\"breadcrumb\">
            <ol class=\"breadcrumb\">
              <li class=\"breadcrumb-item\"><a href=\"{{ path('admin_art_index') }}\">Gestion</a></li>
              <li class=\"breadcrumb-item active\" aria-current=\"page\">Archives</li>
            </ol>
          </nav>
        </div>

        <div class=\"row\">
          <div class=\"col-12 grid-margin stretch-card\">
            <div class=\"card\">
              <div class=\"card-body\">
                <h4 class=\"card-title\">Liste des publications archivées</h4>
                <p class=\"card-description\">
                  Les publications archivées ne sont pas visibles dans la galerie. Vous pouvez les récupérer ou les supprimer définitivement.
                </p>

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

                <div class=\"table-responsive\">
                  <table class=\"table table-hover\">
                    <thead>
                      <tr>
                        <th>Image</th>
                        <th>Titre</th>
                        <th>Description</th>
                        <th>Statut</th>
                        <th>Date</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {% for art in arts %}
                        <tr>
                          <td>
                            <img src=\"{{ art.imageUrl }}\" alt=\"{{ art.title }}\" style=\"width: 50px; height: 50px; object-fit: cover;\">
                          </td>
                          <td>{{ art.title }}</td>
                          <td>{{ art.description|slice(0, 50) }}...</td>
                          <td>
                            <span class=\"badge bg-secondary\">Archivé</span>
                          </td>
                          <td>{{ art.createdAt ? art.createdAt|date('d/m/Y H:i') : '' }}</td>
                          <td>
                                                            <!-- Bouton récupérer -->
                                                            <form method=\"post\" action=\"{{ path('admin_art_restore', {'id': art.id}) }}\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('restore' ~ art.id) }}\">
                                                                <button type=\"submit\" class=\"btn btn-success btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Récupérer\">
                                                                    <i class=\"fas fa-undo\"></i> Récupérer
                                                                </button>
                                                            </form>
                                                            
                                                            <!-- Bouton supprimer définitivement -->
                                                            <form method=\"post\" action=\"{{ path('admin_art_delete', {'id': art.id}) }}\" style=\"display: inline-block;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('delete' ~ art.id) }}\">
                                                                <button type=\"submit\" class=\"btn btn-danger btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Supprimer définitivement\" onclick=\"return confirm('Supprimer définitivement cette œuvre ? Cette action est irréversible.')\">
                                                                    <i class=\"fas fa-trash\"></i> Supprimer
                                                                </button>
                                                            </form>
                                                        </td>
                        </tr>
                      {% else %}
                        <tr>
                          <td colspan=\"6\" class=\"text-center\">Aucune publication archivée</td>
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
      <!-- end page content -->
    </div>
    <!-- end main content -->
  </div>

  <!-- container-scroller -->

  <!-- plugins:js -->
  <script src=\"{{ asset('back/vendors/js/vendor.bundle.base.js') }}\"></script>
  <!-- endinject -->

  <!-- Plugin js for this page -->
  <!-- End plugin js for this page -->

  <!-- Custom js for this page-->
  <script src=\"{{ asset('back/js/off-canvas.js') }}\"></script>
  <script src=\"{{ asset('back/js/hoverable-collapse.js') }}\"></script>
  <script src=\"{{ asset('back/js/misc.js') }}\"></script>
  <!-- end custom js for this page-->

</body>

</html>
", "back/art-archived.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\back\\art-archived.html.twig");
    }
}
