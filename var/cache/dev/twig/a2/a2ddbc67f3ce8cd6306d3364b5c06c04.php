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

/* back/art/index.html.twig */
class __TwigTemplate_a4e898bda1e815cbc5b294ce953dcaaa extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art/index.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art/index.html.twig"));

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
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_art_list");
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
                    <!-- Logo Header -->
                    <div class=\"logo-header\" data-background-color=\"dark\">
                        <a href=\"";
        // line 68
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"logo\">
                            <img src=\"";
        // line 69
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
                    <!-- End Logo Header -->
                </div>
                <!-- Navbar Header -->
                <nav class=\"navbar navbar-header navbar-expand-lg border-0\">
                    <div class=\"container-fluid\">
                        <ul class=\"navbar-nav mr-auto\">
                            <li class=\"nav-item\">
                                <a class=\"nav-link\" href=\"";
        // line 90
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\">
                                    <i class=\"fa fa-eye\"></i>
                                    Voir le site
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <!-- End Navbar -->
            </div>
            <!-- Main Content -->
            <div class=\"container\">
                <div class=\"page-inner\">
                    <div class=\"page-header\">
                        <h3 class=\"fw-bold mb-3\">Gestion des Œuvres</h3>
                        <ul class=\"breadcrumbs mb-3\">
                            <li class=\"nav-home\">
                                <a href=\"";
        // line 107
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">
                                    <i class=\"icon-home\"></i>
                                </a>
                            </li>
                            <li class=\"separator\">
                                <i class=\"icon-arrow-right\"></i>
                            </li>
                            <li class=\"nav-item\">
                                <a href=\"#\">Gestion</a>
                            </li>
                            <li class=\"separator\">
                                <i class=\"icon-arrow-right\"></i>
                            </li>
                            <li class=\"nav-item\">
                                <a href=\"#\">Œuvres</a>
                            </li>
                        </ul>
                    </div>

                    <!-- Cartes de statistiques -->
                    <div class=\"row\">
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-warning\">
                                                <i class=\"fas fa-images\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Total Œuvres</p>
                                                <h3 class=\"card-title\" id=\"total-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-success\">
                                                <i class=\"fas fa-check-circle\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Publiées</p>
                                                <h3 class=\"card-title\" id=\"published-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-info\">
                                                <i class=\"fas fa-clock\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">En attente</p>
                                                <h3 class=\"card-title\" id=\"pending-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-danger\">
                                                <i class=\"fas fa-archive\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Archivées</p>
                                                <h3 class=\"card-title\" id=\"archived-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    ";
        // line 206
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 206, $this->source); })()), "session", [], "any", false, false, false, 206), "flashbag", [], "any", false, false, false, 206), "all", [], "method", false, false, false, 206));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 207
            yield "                        ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 208
                yield "                            <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
                                ";
                // line 209
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
            // line 215
            yield "                    ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 216
        yield "
                    <div class=\"alert alert-info alert-dismissible fade show\" role=\"alert\">
                        <strong>DEBUG:</strong> Page chargée à ";
        // line 218
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate("now", "H:i:s"), "html", null, true);
        yield "
                    </div>

                    <div class=\"row\">
                        <div class=\"col-md-12\">
                            <div class=\"card\">
                                <div class=\"card-header\">
                                    <h4 class=\"card-title\">Liste complète des œuvres</h4>
                                </div>
                                <div class=\"card-body\">
                                    <!-- Formulaire de recherche et filtrage -->
                                    <div class=\"row mb-3\">
                                        <div class=\"col-md-6\">
                                            <form method=\"GET\" action=\"";
        // line 231
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">
                                                <div class=\"input-group\">
                                                    <input type=\"text\" name=\"search\" class=\"form-control\" placeholder=\"Rechercher par titre ou description...\" value=\"";
        // line 233
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 233, $this->source); })()), "html", null, true);
        yield "\">
                                                    <button type=\"submit\" class=\"btn btn-primary\">
                                                        <i class=\"fa fa-search\"></i> Rechercher
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class=\"col-md-6\">
                                            <form method=\"GET\" action=\"";
        // line 241
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">
                                                <div class=\"input-group\">
                                                    <select name=\"status\" class=\"form-select\" onchange=\"this.form.submit()\">
                                                        <option value=\"all\" ";
        // line 244
        if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 244, $this->source); })()) == "all")) {
            yield "selected";
        }
        yield ">📋 Tous les statuts</option>
                                                        <option value=\"en attente\" ";
        // line 245
        if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 245, $this->source); })()) == "en attente")) {
            yield "selected";
        }
        yield ">🟡 En attente</option>
                                                        <option value=\"active\" ";
        // line 246
        if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 246, $this->source); })()) == "active")) {
            yield "selected";
        }
        yield ">🟢 Publiés</option>
                                                        <option value=\"archived\" ";
        // line 247
        if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 247, $this->source); })()) == "archived")) {
            yield "selected";
        }
        yield ">🔵 Archivés</option>
                                                    </select>
                                                    ";
        // line 249
        if ((($tmp = (isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 249, $this->source); })())) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 250
            yield "                                                        <input type=\"hidden\" name=\"search\" value=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 250, $this->source); })()), "html", null, true);
            yield "\">
                                                    ";
        }
        // line 252
        yield "                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    
                                    ";
        // line 257
        if (((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 257, $this->source); })()) || ((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 257, $this->source); })()) != "all"))) {
            // line 258
            yield "                                        <div class=\"alert alert-info\">
                                            <i class=\"fa fa-info-circle\"></i> 
                                            ";
            // line 260
            if ((($tmp = (isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 260, $this->source); })())) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                yield "Recherche pour \"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 260, $this->source); })()), "html", null, true);
                yield "\"";
            }
            yield " 
                                            ";
            // line 261
            if (((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 261, $this->source); })()) && ((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 261, $this->source); })()) != "all"))) {
                yield " - ";
            }
            // line 262
            yield "                                            ";
            if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 262, $this->source); })()) != "all")) {
                // line 263
                yield "                                                ";
                if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 263, $this->source); })()) == "en attente")) {
                    yield "En attente";
                }
                // line 264
                yield "                                                ";
                if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 264, $this->source); })()) == "active")) {
                    yield "Publiés";
                }
                // line 265
                yield "                                                ";
                if (((isset($context["statusFilter"]) || array_key_exists("statusFilter", $context) ? $context["statusFilter"] : (function () { throw new RuntimeError('Variable "statusFilter" does not exist.', 265, $this->source); })()) == "archived")) {
                    yield "Archivés";
                }
                // line 266
                yield "                                            ";
            }
            // line 267
            yield "                                            <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
            yield "\" class=\"float-end\">✖ Effacer</a>
                                        </div>
                                    ";
        }
        // line 270
        yield "                                    <div class=\"table-responsive\">
                                        <table class=\"table table-striped\">
                                            <thead>
                                                <tr>
                                                    <th>Image</th>
                                                    <th>Titre</th>
                                                    <th>Description</th>
                                                    <th>Statut</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                ";
        // line 282
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["arts"]) || array_key_exists("arts", $context) ? $context["arts"] : (function () { throw new RuntimeError('Variable "arts" does not exist.', 282, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["art"]) {
            // line 283
            yield "                                                    <tr>
                                                        <td>
                                                            <img src=\"";
            // line 285
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "imageUrl", [], "any", false, false, false, 285), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 285), "html", null, true);
            yield "\" style=\"width: 50px; height: 50px; object-fit: cover;\">
                                                        </td>
                                                        <td><strong>";
            // line 287
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 287), "html", null, true);
            yield "</strong></td>
                                                        <td>";
            // line 288
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 288), 0, 80), "html", null, true);
            yield "...</td>
                                                        <td>
                                                            ";
            // line 290
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 290) == "active")) {
                // line 291
                yield "                                                                <span class=\"badge bg-success\">Publié</span>
                                                            ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 292
$context["art"], "status", [], "any", false, false, false, 292) == "en attente")) {
                // line 293
                yield "                                                                <span class=\"badge bg-warning\">En attente</span>
                                                            ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 294
$context["art"], "status", [], "any", false, false, false, 294) == "archived")) {
                // line 295
                yield "                                                                <span class=\"badge bg-secondary\">Archivé</span>
                                                            ";
            } else {
                // line 297
                yield "                                                                <span class=\"badge bg-dark\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 297), "html", null, true);
                yield "</span>
                                                            ";
            }
            // line 299
            yield "                                                        </td>
                                                        <td>
                                                            <span class=\"badge bg-info\">
                                                                <i class=\"fas fa-eye\"></i> 
                                                                <span class=\"admin-view-count\" data-art-id=\"";
            // line 303
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 303), "html", null, true);
            yield "\">0</span> vues
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <div class=\"d-flex gap-2\">
                                                                <a href=\"";
            // line 308
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_art_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 308)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-secondary\">
                                                                    <i class=\"fa fa-edit\"></i> Modifier
                                                                </a>
                                                                <form method=\"POST\" action=\"";
            // line 311
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_art_update_status", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 311)]), "html", null, true);
            yield "\" style=\"display: inline;\">
                                                                    <input type=\"hidden\" name=\"_token\" value=\"";
            // line 312
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("update_status" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 312))), "html", null, true);
            yield "\">
                                                                    <select name=\"status\" class=\"form-select form-select-sm\" onchange=\"this.form.submit()\">
                                                                        <option value=\"en attente\" ";
            // line 314
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 314) == "en attente")) {
                yield "selected";
            }
            yield ">🟡 En attente</option>
                                                                        <option value=\"active\" ";
            // line 315
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 315) == "active")) {
                yield "selected";
            }
            yield ">🟢 Publié</option>
                                                                        <option value=\"archived\" ";
            // line 316
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["art"], "status", [], "any", false, false, false, 316) == "archived")) {
                yield "selected";
            }
            yield ">🔵 Archivé</option>
                                                                    </select>
                                                                </form>
                                                                <form method=\"POST\" action=\"";
            // line 319
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_art_delete", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 319)]), "html", null, true);
            yield "\" style=\"display: inline;\">
                                                                    <input type=\"hidden\" name=\"_token\" value=\"";
            // line 320
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("delete" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 320))), "html", null, true);
            yield "\">
                                                                    <button type=\"submit\" class=\"btn btn-sm btn-danger\" onclick=\"return confirm('Supprimer définitivement cette œuvre ?')\">
                                                                        <i class=\"fa fa-trash\"></i> Supprimer
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                ";
            $context['_iterated'] = true;
        }
        // line 328
        if (!$context['_iterated']) {
            // line 329
            yield "                                                    <tr>
                                                        <td colspan=\"6\" class=\"text-center\">Aucune œuvre trouvée</td>
                                                    </tr>
                                                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['art'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 333
        yield "                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Footer -->
            <div class=\"footer\">
                <div class=\"container-fluid\">
                    <div class=\"row\">
                        <div class=\"col-12\">
                            <div class=\"text-center\">
                                <p> 2024 Pegasus Template. All rights reserved.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--   Core JS Files   -->
    <script src=\"";
        // line 358
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 359
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 360
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
        yield "\"></script>

    <!--  jQuery Scrollbar  -->
    <script src=\"";
        // line 363
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"), "html", null, true);
        yield "\"></script>

    <!--  Chart JS  -->
    <script src=\"";
        // line 366
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart.js/chart.min.js"), "html", null, true);
        yield "\"></script>

    <!--  jQuery Vector Maps  -->
    <script src=\"";
        // line 369
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/jsvectormap.min.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 370
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/world.js"), "html", null, true);
        yield "\"></script>

    <!--  Kaiadmin JS  -->
    <script src=\"";
        // line 373
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/kaiadmin.min.js"), "html", null, true);
        yield "\"></script>

    <!-- Kaiadmin DEMO methods, don't include it in your project! -->
    <script src=\"";
        // line 376
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/setting-demo.js"), "html", null, true);
        yield "\"></script>
    <script src=\"";
        // line 377
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/demo.js"), "html", null, true);
        yield "\"></script>

    <script>
        // Charger les statistiques au chargement de la page
        document.addEventListener('DOMContentLoaded', function() {
            loadStats();
            loadViewCounts();
            
            // Rafraîchir les stats toutes les 30 secondes
            setInterval(loadStats, 30000);
            setInterval(loadViewCounts, 30000);
        });

        async function loadStats() {
            try {
                const response = await fetch('";
        // line 392
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("api_stats");
        yield "');
                const data = await response.json();
                
                if (response.ok) {
                    document.getElementById('total-arts').textContent = data.total;
                    document.getElementById('published-arts').textContent = data.published;
                    document.getElementById('pending-arts').textContent = data.pending;
                    document.getElementById('archived-arts').textContent = data.archived;
                }
            } catch (error) {
                console.error('Erreur lors du chargement des statistiques:', error);
            }
        }

        async function loadViewCounts() {
            document.querySelectorAll('.admin-view-count').forEach(function(element) {
                const artId = element.getAttribute('data-art-id');
                
                fetch('/api/art/' + artId + '/views')
                    .then(response => response.json())
                    .then(data => {
                        if (data.viewsCount !== undefined) {
                            element.textContent = data.viewsCount;
                        }
                    })
                    .catch(error => {
                        console.error('Erreur lors du chargement des vues:', error);
                    });
            });
        }

        // Rafraîchir les vues toutes les 10 secondes
        setInterval(loadViewCounts, 10000);
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
        return "back/art/index.html.twig";
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
        return array (  680 => 392,  662 => 377,  658 => 376,  652 => 373,  646 => 370,  642 => 369,  636 => 366,  630 => 363,  624 => 360,  620 => 359,  616 => 358,  589 => 333,  580 => 329,  578 => 328,  565 => 320,  561 => 319,  553 => 316,  547 => 315,  541 => 314,  536 => 312,  532 => 311,  526 => 308,  518 => 303,  512 => 299,  506 => 297,  502 => 295,  500 => 294,  497 => 293,  495 => 292,  492 => 291,  490 => 290,  485 => 288,  481 => 287,  474 => 285,  470 => 283,  465 => 282,  451 => 270,  444 => 267,  441 => 266,  436 => 265,  431 => 264,  426 => 263,  423 => 262,  419 => 261,  411 => 260,  407 => 258,  405 => 257,  398 => 252,  392 => 250,  390 => 249,  383 => 247,  377 => 246,  371 => 245,  365 => 244,  359 => 241,  348 => 233,  343 => 231,  327 => 218,  323 => 216,  317 => 215,  305 => 209,  300 => 208,  295 => 207,  291 => 206,  189 => 107,  169 => 90,  145 => 69,  141 => 68,  122 => 52,  107 => 40,  85 => 21,  81 => 20,  70 => 12,  66 => 11,  62 => 10,  56 => 7,  48 => 1,);
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
                            <a href=\"{{ path('back_art_list') }}\">
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
                    <!-- Logo Header -->
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
                    <!-- End Logo Header -->
                </div>
                <!-- Navbar Header -->
                <nav class=\"navbar navbar-header navbar-expand-lg border-0\">
                    <div class=\"container-fluid\">
                        <ul class=\"navbar-nav mr-auto\">
                            <li class=\"nav-item\">
                                <a class=\"nav-link\" href=\"{{ path('front_gallery') }}\">
                                    <i class=\"fa fa-eye\"></i>
                                    Voir le site
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <!-- End Navbar -->
            </div>
            <!-- Main Content -->
            <div class=\"container\">
                <div class=\"page-inner\">
                    <div class=\"page-header\">
                        <h3 class=\"fw-bold mb-3\">Gestion des Œuvres</h3>
                        <ul class=\"breadcrumbs mb-3\">
                            <li class=\"nav-home\">
                                <a href=\"{{ path('back_dashboard') }}\">
                                    <i class=\"icon-home\"></i>
                                </a>
                            </li>
                            <li class=\"separator\">
                                <i class=\"icon-arrow-right\"></i>
                            </li>
                            <li class=\"nav-item\">
                                <a href=\"#\">Gestion</a>
                            </li>
                            <li class=\"separator\">
                                <i class=\"icon-arrow-right\"></i>
                            </li>
                            <li class=\"nav-item\">
                                <a href=\"#\">Œuvres</a>
                            </li>
                        </ul>
                    </div>

                    <!-- Cartes de statistiques -->
                    <div class=\"row\">
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-warning\">
                                                <i class=\"fas fa-images\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Total Œuvres</p>
                                                <h3 class=\"card-title\" id=\"total-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-success\">
                                                <i class=\"fas fa-check-circle\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Publiées</p>
                                                <h3 class=\"card-title\" id=\"published-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-info\">
                                                <i class=\"fas fa-clock\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">En attente</p>
                                                <h3 class=\"card-title\" id=\"pending-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=\"col-md-3\">
                            <div class=\"card card-stats\">
                                <div class=\"card-body\">
                                    <div class=\"row\">
                                        <div class=\"col-5\">
                                            <div class=\"icon-big text-center icon-danger\">
                                                <i class=\"fas fa-archive\"></i>
                                            </div>
                                        </div>
                                        <div class=\"col-7 d-flex align-items-center\">
                                            <div class=\"numbers\">
                                                <p class=\"card-category\">Archivées</p>
                                                <h3 class=\"card-title\" id=\"archived-arts\">-</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

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

                    <div class=\"alert alert-info alert-dismissible fade show\" role=\"alert\">
                        <strong>DEBUG:</strong> Page chargée à {{ \"now\"|date(\"H:i:s\") }}
                    </div>

                    <div class=\"row\">
                        <div class=\"col-md-12\">
                            <div class=\"card\">
                                <div class=\"card-header\">
                                    <h4 class=\"card-title\">Liste complète des œuvres</h4>
                                </div>
                                <div class=\"card-body\">
                                    <!-- Formulaire de recherche et filtrage -->
                                    <div class=\"row mb-3\">
                                        <div class=\"col-md-6\">
                                            <form method=\"GET\" action=\"{{ path('back_dashboard') }}\">
                                                <div class=\"input-group\">
                                                    <input type=\"text\" name=\"search\" class=\"form-control\" placeholder=\"Rechercher par titre ou description...\" value=\"{{ search }}\">
                                                    <button type=\"submit\" class=\"btn btn-primary\">
                                                        <i class=\"fa fa-search\"></i> Rechercher
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class=\"col-md-6\">
                                            <form method=\"GET\" action=\"{{ path('back_dashboard') }}\">
                                                <div class=\"input-group\">
                                                    <select name=\"status\" class=\"form-select\" onchange=\"this.form.submit()\">
                                                        <option value=\"all\" {% if statusFilter == 'all' %}selected{% endif %}>📋 Tous les statuts</option>
                                                        <option value=\"en attente\" {% if statusFilter == 'en attente' %}selected{% endif %}>🟡 En attente</option>
                                                        <option value=\"active\" {% if statusFilter == 'active' %}selected{% endif %}>🟢 Publiés</option>
                                                        <option value=\"archived\" {% if statusFilter == 'archived' %}selected{% endif %}>🔵 Archivés</option>
                                                    </select>
                                                    {% if search %}
                                                        <input type=\"hidden\" name=\"search\" value=\"{{ search }}\">
                                                    {% endif %}
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    
                                    {% if search or statusFilter != 'all' %}
                                        <div class=\"alert alert-info\">
                                            <i class=\"fa fa-info-circle\"></i> 
                                            {% if search %}Recherche pour \"{{ search }}\"{% endif %} 
                                            {% if search and statusFilter != 'all' %} - {% endif %}
                                            {% if statusFilter != 'all' %}
                                                {% if statusFilter == 'en attente' %}En attente{% endif %}
                                                {% if statusFilter == 'active' %}Publiés{% endif %}
                                                {% if statusFilter == 'archived' %}Archivés{% endif %}
                                            {% endif %}
                                            <a href=\"{{ path('back_dashboard') }}\" class=\"float-end\">✖ Effacer</a>
                                        </div>
                                    {% endif %}
                                    <div class=\"table-responsive\">
                                        <table class=\"table table-striped\">
                                            <thead>
                                                <tr>
                                                    <th>Image</th>
                                                    <th>Titre</th>
                                                    <th>Description</th>
                                                    <th>Statut</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {% for art in arts %}
                                                    <tr>
                                                        <td>
                                                            <img src=\"{{ art.imageUrl }}\" alt=\"{{ art.title }}\" style=\"width: 50px; height: 50px; object-fit: cover;\">
                                                        </td>
                                                        <td><strong>{{ art.title }}</strong></td>
                                                        <td>{{ art.description|slice(0, 80) }}...</td>
                                                        <td>
                                                            {% if art.status == 'active' %}
                                                                <span class=\"badge bg-success\">Publié</span>
                                                            {% elseif art.status == 'en attente' %}
                                                                <span class=\"badge bg-warning\">En attente</span>
                                                            {% elseif art.status == 'archived' %}
                                                                <span class=\"badge bg-secondary\">Archivé</span>
                                                            {% else %}
                                                                <span class=\"badge bg-dark\">{{ art.status }}</span>
                                                            {% endif %}
                                                        </td>
                                                        <td>
                                                            <span class=\"badge bg-info\">
                                                                <i class=\"fas fa-eye\"></i> 
                                                                <span class=\"admin-view-count\" data-art-id=\"{{ art.id }}\">0</span> vues
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <div class=\"d-flex gap-2\">
                                                                <a href=\"{{ path('back_art_edit', {'id': art.id}) }}\" class=\"btn btn-sm btn-secondary\">
                                                                    <i class=\"fa fa-edit\"></i> Modifier
                                                                </a>
                                                                <form method=\"POST\" action=\"{{ path('back_art_update_status', {'id': art.id}) }}\" style=\"display: inline;\">
                                                                    <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('update_status' ~ art.id) }}\">
                                                                    <select name=\"status\" class=\"form-select form-select-sm\" onchange=\"this.form.submit()\">
                                                                        <option value=\"en attente\" {% if art.status == 'en attente' %}selected{% endif %}>🟡 En attente</option>
                                                                        <option value=\"active\" {% if art.status == 'active' %}selected{% endif %}>🟢 Publié</option>
                                                                        <option value=\"archived\" {% if art.status == 'archived' %}selected{% endif %}>🔵 Archivé</option>
                                                                    </select>
                                                                </form>
                                                                <form method=\"POST\" action=\"{{ path('back_art_delete', {'id': art.id}) }}\" style=\"display: inline;\">
                                                                    <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('delete' ~ art.id) }}\">
                                                                    <button type=\"submit\" class=\"btn btn-sm btn-danger\" onclick=\"return confirm('Supprimer définitivement cette œuvre ?')\">
                                                                        <i class=\"fa fa-trash\"></i> Supprimer
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                {% else %}
                                                    <tr>
                                                        <td colspan=\"6\" class=\"text-center\">Aucune œuvre trouvée</td>
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
            <!-- Footer -->
            <div class=\"footer\">
                <div class=\"container-fluid\">
                    <div class=\"row\">
                        <div class=\"col-12\">
                            <div class=\"text-center\">
                                <p> 2024 Pegasus Template. All rights reserved.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--   Core JS Files   -->
    <script src=\"{{ asset('back/js/core/jquery-3.7.1.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/popper.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/core/bootstrap.min.js') }}\"></script>

    <!--  jQuery Scrollbar  -->
    <script src=\"{{ asset('back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js') }}\"></script>

    <!--  Chart JS  -->
    <script src=\"{{ asset('back/js/plugin/chart.js/chart.min.js') }}\"></script>

    <!--  jQuery Vector Maps  -->
    <script src=\"{{ asset('back/js/plugin/jsvectormap/jsvectormap.min.js') }}\"></script>
    <script src=\"{{ asset('back/js/plugin/jsvectormap/world.js') }}\"></script>

    <!--  Kaiadmin JS  -->
    <script src=\"{{ asset('back/js/kaiadmin.min.js') }}\"></script>

    <!-- Kaiadmin DEMO methods, don't include it in your project! -->
    <script src=\"{{ asset('back/js/setting-demo.js') }}\"></script>
    <script src=\"{{ asset('back/js/demo.js') }}\"></script>

    <script>
        // Charger les statistiques au chargement de la page
        document.addEventListener('DOMContentLoaded', function() {
            loadStats();
            loadViewCounts();
            
            // Rafraîchir les stats toutes les 30 secondes
            setInterval(loadStats, 30000);
            setInterval(loadViewCounts, 30000);
        });

        async function loadStats() {
            try {
                const response = await fetch('{{ path('api_stats') }}');
                const data = await response.json();
                
                if (response.ok) {
                    document.getElementById('total-arts').textContent = data.total;
                    document.getElementById('published-arts').textContent = data.published;
                    document.getElementById('pending-arts').textContent = data.pending;
                    document.getElementById('archived-arts').textContent = data.archived;
                }
            } catch (error) {
                console.error('Erreur lors du chargement des statistiques:', error);
            }
        }

        async function loadViewCounts() {
            document.querySelectorAll('.admin-view-count').forEach(function(element) {
                const artId = element.getAttribute('data-art-id');
                
                fetch('/api/art/' + artId + '/views')
                    .then(response => response.json())
                    .then(data => {
                        if (data.viewsCount !== undefined) {
                            element.textContent = data.viewsCount;
                        }
                    })
                    .catch(error => {
                        console.error('Erreur lors du chargement des vues:', error);
                    });
            });
        }

        // Rafraîchir les vues toutes les 10 secondes
        setInterval(loadViewCounts, 10000);
    </script>
</body>
</html>
", "back/art/index.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\back\\art\\index.html.twig");
    }
}
