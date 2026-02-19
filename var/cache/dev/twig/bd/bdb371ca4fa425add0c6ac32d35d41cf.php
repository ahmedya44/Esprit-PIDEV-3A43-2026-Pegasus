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

/* back/art-management.html.twig */
class __TwigTemplate_0f395b4a47d7cdf02d1c37077bb951c9 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-management.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-management.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Gestion des œuvres - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/favicon.ico"), "html", null, true);
        yield "\" type=\"image/x-icon\" />
    
    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"";
        // line 10
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 11
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/plugins.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 12
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/kaiadmin.min.css"), "html", null, true);
        yield "\" />
</head>
<body>
    <div class=\"wrapper\">
        <!-- Sidebar -->
        <div class=\"sidebar\" data-background-color=\"dark\">
            <div class=\"sidebar-logo\">
                <div class=\"logo-header\" data-background-color=\"dark\">
                    <a href=\"";
        // line 20
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"logo\">
                        <img src=\"";
        // line 21
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
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
            </div>
            <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
                <div class=\"sidebar-content\">
                    <ul class=\"nav nav-secondary\">
                        <li class=\"nav-item\">
                            <a href=\"";
        // line 40
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">
                                <i class=\"fas fa-home\"></i>
                                <p>Dashboard</p>
                            </a>
                        </li>
                        <li class=\"nav-section\">
                            <span class=\"sidebar-mini-icon\">
                                <i class=\"fa fa-ellipsis-h\"></i>
                            </span>
                            <h4 class=\"text-section\">Gestion</h4>
                        </li>
                        <li class=\"nav-item active\">
                            <a href=\"";
        // line 52
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_index");
        yield "\">
                                <i class=\"fas fa-image\"></i>
                                <p>Gestion des œuvres</p>
                            </a>
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
        // line 67
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"logo\">
                            <img src=\"";
        // line 68
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\" />
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
                </div>
                <nav class=\"navbar navbar-header navbar-expand-lg\">
                    <div class=\"container-fluid\">
                        <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                            <li class=\"nav-item\">
                                <a href=\"#\" class=\"nav-link\">
                                    <i class=\"fa fa-user\"></i>
                                    Admin
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <!-- End Navbar -->

            <div class=\"container\">
                <div class=\"page-inner\">
                    <div class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\">
                        <div>
                            <h3 class=\"fw-bold mb-3\">Gestion des œuvres</h3>
                            <h6 class=\"op-7 mb-2\">Valider, archiver ou supprimer les publications</h6>
                        </div>
                    </div>

                    ";
        // line 107
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 107, $this->source); })()), "session", [], "any", false, false, false, 107), "flashbag", [], "any", false, false, false, 107), "all", [], "method", false, false, false, 107));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 108
            yield "                        ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 109
                yield "                            <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
                                ";
                // line 110
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
                yield "
                                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>
                            </div>
                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 114
            yield "                    ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 115
        yield "
                    <div class=\"row\">
                        <div class=\"col-md-12\">
                            <div class=\"card\">
                                <div class=\"card-header\">
                                    <h4 class=\"card-title\">Liste des œuvres</h4>
                                </div>
                                <div class=\"card-body\">
                                    <div class=\"table-responsive\">
                                        <table class=\"table table-striped table-hover\">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
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
        // line 137
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["arts"]) || array_key_exists("arts", $context) ? $context["arts"] : (function () { throw new RuntimeError('Variable "arts" does not exist.', 137, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["art"]) {
            // line 138
            yield "                                                    <tr>
                                                        <td>";
            // line 139
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 139), "html", null, true);
            yield "</td>
                                                        <td>
                                                            <img src=\"";
            // line 141
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "imageUrl", [], "any", false, false, false, 141), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 141), "html", null, true);
            yield "\" style=\"width: 60px; height: 45px; object-fit: cover; border-radius: 4px;\">
                                                        </td>
                                                        <td><strong>";
            // line 143
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 143), "html", null, true);
            yield "</strong></td>
                                                        <td>";
            // line 144
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 144), 0, 80), "html", null, true);
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 144)) > 80)) {
                yield "...";
            }
            yield "</td>
                                                        <td>
                                                            ";
            // line 146
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 146) == "active")) {
                // line 147
                yield "                                                                <span class=\"badge bg-success\">Publié</span>
                                                            ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 148
$context["art"], "status", [], "any", false, false, false, 148) == "en cours de traitement")) {
                // line 149
                yield "                                                                <span class=\"badge bg-warning\">En attente</span>
                                                            ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 150
$context["art"], "status", [], "any", false, false, false, 150) == "archived")) {
                // line 151
                yield "                                                                <span class=\"badge bg-secondary\">Archivé</span>
                                                            ";
            } else {
                // line 153
                yield "                                                                <span class=\"badge bg-dark\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 153), "html", null, true);
                yield "</span>
                                                            ";
            }
            // line 155
            yield "                                                        </td>
                                                        <td>";
            // line 156
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 156)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 156), "d/m/Y H:i"), "html", null, true)) : (""));
            yield "</td>
                                                        <td>
                                                            <!-- Menu déroulant pour changer l'état (Publier/En attente) -->
                                                            <form method=\"post\" action=\"";
            // line 159
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_update_status", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 159)]), "html", null, true);
            yield "\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <select name=\"status\" class=\"form-select form-select-sm\" style=\"min-width: 120px; border-radius: 6px; border: 1px solid #dee2e6; padding: 4px 8px; font-size: 13px;\" onchange=\"this.form.submit()\">
                                                                    <option value=\"en cours de traitement\" ";
            // line 161
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 161) == "en cours de traitement")) {
                yield "selected";
            }
            yield ">🟡 En attente</option>
                                                                    <option value=\"active\" ";
            // line 162
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 162) == "active")) {
                yield "selected";
            }
            yield ">🟢 Publié</option>
                                                                </select>
                                                                <input type=\"hidden\" name=\"_token\" value=\"";
            // line 164
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("update_status" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 164))), "html", null, true);
            yield "\">
                                                            </form>
                                                            
                                                            <!-- Bouton Archiver -->
                                                            <form method=\"post\" action=\"";
            // line 168
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_archive", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 168)]), "html", null, true);
            yield "\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"";
            // line 169
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("archive" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 169))), "html", null, true);
            yield "\">
                                                                <button type=\"submit\" class=\"btn btn-info btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Archiver\" ";
            // line 170
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 170) == "archived")) {
                yield "disabled";
            }
            yield ">
                                                                    <i class=\"fas fa-archive\"></i> Archiver
                                                                </button>
                                                            </form>
                                                            
                                                            <!-- Bouton Supprimer -->
                                                            <form method=\"post\" action=\"";
            // line 176
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_delete", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 176)]), "html", null, true);
            yield "\" style=\"display: inline-block;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"";
            // line 177
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("delete" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 177))), "html", null, true);
            yield "\">
                                                                <button type=\"submit\" class=\"btn btn-danger btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Supprimer\" onclick=\"return confirm('Supprimer définitivement cette œuvre ?')\">
                                                                    <i class=\"fas fa-trash\"></i> Supprimer
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                ";
            $context['_iterated'] = true;
        }
        // line 184
        if (!$context['_iterated']) {
            // line 185
            yield "                                                    <tr>
                                                        <td colspan=\"7\" class=\"text-center\">Aucune œuvre trouvée</td>
                                                    </tr>
                                                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['art'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 189
        yield "                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- JS Files -->
    <script src=\"";
        // line 202
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 203
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 204
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
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
        return "back/art-management.html.twig";
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
        return array (  397 => 204,  393 => 203,  389 => 202,  374 => 189,  365 => 185,  363 => 184,  351 => 177,  347 => 176,  336 => 170,  332 => 169,  328 => 168,  321 => 164,  314 => 162,  308 => 161,  303 => 159,  297 => 156,  294 => 155,  288 => 153,  284 => 151,  282 => 150,  279 => 149,  277 => 148,  274 => 147,  272 => 146,  264 => 144,  260 => 143,  253 => 141,  248 => 139,  245 => 138,  240 => 137,  216 => 115,  210 => 114,  200 => 110,  195 => 109,  190 => 108,  186 => 107,  144 => 68,  140 => 67,  122 => 52,  107 => 40,  85 => 21,  81 => 20,  70 => 12,  66 => 11,  62 => 10,  56 => 7,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Gestion des œuvres - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"{{ asset('back/img/kaiadmin/favicon.ico') }}\" type=\"image/x-icon\" />
    
    <!-- CSS Files -->
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/bootstrap.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/plugins.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/kaiadmin.min.css') }}\" />
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
            </div>
            <div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
                <div class=\"sidebar-content\">
                    <ul class=\"nav nav-secondary\">
                        <li class=\"nav-item\">
                            <a href=\"{{ path('back_dashboard') }}\">
                                <i class=\"fas fa-home\"></i>
                                <p>Dashboard</p>
                            </a>
                        </li>
                        <li class=\"nav-section\">
                            <span class=\"sidebar-mini-icon\">
                                <i class=\"fa fa-ellipsis-h\"></i>
                            </span>
                            <h4 class=\"text-section\">Gestion</h4>
                        </li>
                        <li class=\"nav-item active\">
                            <a href=\"{{ path('admin_art_index') }}\">
                                <i class=\"fas fa-image\"></i>
                                <p>Gestion des œuvres</p>
                            </a>
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
                </div>
                <nav class=\"navbar navbar-header navbar-expand-lg\">
                    <div class=\"container-fluid\">
                        <ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
                            <li class=\"nav-item\">
                                <a href=\"#\" class=\"nav-link\">
                                    <i class=\"fa fa-user\"></i>
                                    Admin
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <!-- End Navbar -->

            <div class=\"container\">
                <div class=\"page-inner\">
                    <div class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\">
                        <div>
                            <h3 class=\"fw-bold mb-3\">Gestion des œuvres</h3>
                            <h6 class=\"op-7 mb-2\">Valider, archiver ou supprimer les publications</h6>
                        </div>
                    </div>

                    {% for type, messages in app.session.flashbag.all() %}
                        {% for message in messages %}
                            <div class=\"alert alert-{{ type }} alert-dismissible fade show\" role=\"alert\">
                                {{ message }}
                                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>
                            </div>
                        {% endfor %}
                    {% endfor %}

                    <div class=\"row\">
                        <div class=\"col-md-12\">
                            <div class=\"card\">
                                <div class=\"card-header\">
                                    <h4 class=\"card-title\">Liste des œuvres</h4>
                                </div>
                                <div class=\"card-body\">
                                    <div class=\"table-responsive\">
                                        <table class=\"table table-striped table-hover\">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
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
                                                        <td>{{ art.id }}</td>
                                                        <td>
                                                            <img src=\"{{ art.imageUrl }}\" alt=\"{{ art.title }}\" style=\"width: 60px; height: 45px; object-fit: cover; border-radius: 4px;\">
                                                        </td>
                                                        <td><strong>{{ art.title }}</strong></td>
                                                        <td>{{ art.description|slice(0, 80) }}{% if art.description|length > 80 %}...{% endif %}</td>
                                                        <td>
                                                            {% if art.status == 'active' %}
                                                                <span class=\"badge bg-success\">Publié</span>
                                                            {% elseif art.status == 'en cours de traitement' %}
                                                                <span class=\"badge bg-warning\">En attente</span>
                                                            {% elseif art.status == 'archived' %}
                                                                <span class=\"badge bg-secondary\">Archivé</span>
                                                            {% else %}
                                                                <span class=\"badge bg-dark\">{{ art.status }}</span>
                                                            {% endif %}
                                                        </td>
                                                        <td>{{ art.createdAt ? art.createdAt|date('d/m/Y H:i') : '' }}</td>
                                                        <td>
                                                            <!-- Menu déroulant pour changer l'état (Publier/En attente) -->
                                                            <form method=\"post\" action=\"{{ path('admin_art_update_status', {'id': art.id}) }}\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <select name=\"status\" class=\"form-select form-select-sm\" style=\"min-width: 120px; border-radius: 6px; border: 1px solid #dee2e6; padding: 4px 8px; font-size: 13px;\" onchange=\"this.form.submit()\">
                                                                    <option value=\"en cours de traitement\" {% if art.status == 'en cours de traitement' %}selected{% endif %}>🟡 En attente</option>
                                                                    <option value=\"active\" {% if art.status == 'active' %}selected{% endif %}>🟢 Publié</option>
                                                                </select>
                                                                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('update_status' ~ art.id) }}\">
                                                            </form>
                                                            
                                                            <!-- Bouton Archiver -->
                                                            <form method=\"post\" action=\"{{ path('admin_art_archive', {'id': art.id}) }}\" style=\"display: inline-block; margin-right: 5px;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('archive' ~ art.id) }}\">
                                                                <button type=\"submit\" class=\"btn btn-info btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Archiver\" {% if art.status == 'archived' %}disabled{% endif %}>
                                                                    <i class=\"fas fa-archive\"></i> Archiver
                                                                </button>
                                                            </form>
                                                            
                                                            <!-- Bouton Supprimer -->
                                                            <form method=\"post\" action=\"{{ path('admin_art_delete', {'id': art.id}) }}\" style=\"display: inline-block;\">
                                                                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('delete' ~ art.id) }}\">
                                                                <button type=\"submit\" class=\"btn btn-danger btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Supprimer\" onclick=\"return confirm('Supprimer définitivement cette œuvre ?')\">
                                                                    <i class=\"fas fa-trash\"></i> Supprimer
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                {% else %}
                                                    <tr>
                                                        <td colspan=\"7\" class=\"text-center\">Aucune œuvre trouvée</td>
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
            </div>
        </div>
    </div>

    <!-- JS Files -->
    <script src=\"{{ asset('back/js/core/jquery-3.7.1.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/popper.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/bootstrap.min.js') }}\"></script>
</body>
</html>
", "back/art-management.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\back\\art-management.html.twig");
    }
}
