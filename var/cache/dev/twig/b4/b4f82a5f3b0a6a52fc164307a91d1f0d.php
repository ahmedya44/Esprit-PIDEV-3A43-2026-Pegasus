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

/* back/components/avatars.html.twig */
class __TwigTemplate_87e63f82a22e7433da942e3921d83979 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/components/avatars.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/components/avatars.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"en\">
<head>
\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
\t<title>Avatars - Kaiadmin Bootstrap 5 Admin Dashboard</title>
\t<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
\t<link rel=\"icon\" href=\"";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/favicon.ico"), "html", null, true);
        yield "\" type=\"image/x-icon\"/>

\t<!-- Fonts and icons -->
\t<script src=\"";
        // line 10
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/webfont/webfont.min.js"), "html", null, true);
        yield "\"></script>
\t<script>
\t\tWebFont.load({
\t\t\tgoogle: {\"families\":[\"Public Sans:300,400,500,600,700\"]},
\t\t\tcustom: {\"families\":[\"Font Awesome 5 Solid\", \"Font Awesome 5 Regular\", \"Font Awesome 5 Brands\", \"simple-line-icons\"], urls: ['";
        // line 14
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/fonts.min.css"), "html", null, true);
        yield "']},
\t\t\tactive: function() {
\t\t\t\tsessionStorage.fonts = true;
\t\t\t}
\t\t});
\t</script>

\t<!-- CSS Files -->
\t<link rel=\"stylesheet\" href=\"";
        // line 22
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.min.css"), "html", null, true);
        yield "\">
\t<link rel=\"stylesheet\" href=\"";
        // line 23
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/plugins.min.css"), "html", null, true);
        yield "\">
\t<link rel=\"stylesheet\" href=\"";
        // line 24
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/kaiadmin.min.css"), "html", null, true);
        yield "\">

\t<!-- CSS Just for demo purpose, don't include it in your project -->
\t<link rel=\"stylesheet\" href=\"";
        // line 27
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/demo.css"), "html", null, true);
        yield "\">
</head>
<body>
\t<div class=\"wrapper\">
\t\t<!-- Sidebar -->
\t\t<div class=\"sidebar\" data-background-color=\"dark\">
\t\t\t<div class=\"sidebar-logo\">
\t\t\t\t<!-- Logo Header -->
\t\t\t\t<div class=\"logo-header\" data-background-color=\"dark\">

\t\t\t\t\t<a href=\"../index.html\" class=\"logo\">
\t\t\t\t\t\t<img src=\"";
        // line 38
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\">
\t\t\t\t\t</a>
\t\t\t\t\t<div class=\"nav-toggle\">
\t\t\t\t\t\t<button class=\"btn btn-toggle toggle-sidebar\">
\t\t\t\t\t\t\t<i class=\"gg-menu-right\"></i>
\t\t\t\t\t\t</button>
\t\t\t\t\t\t<button class=\"btn btn-toggle sidenav-toggler\">
\t\t\t\t\t\t\t<i class=\"gg-menu-left\"></i>
\t\t\t\t\t\t</button>
\t\t\t\t\t</div>
\t\t\t\t\t<button class=\"topbar-toggler more\">
\t\t\t\t\t\t<i class=\"gg-more-vertical-alt\"></i>
\t\t\t\t\t</button>
\t\t\t\t</div>
\t\t\t\t<!-- End Logo Header -->\t
\t\t\t</div>\t
\t\t\t<div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
\t\t\t\t<div class=\"sidebar-content\">
\t\t\t\t\t<ul class=\"nav nav-secondary\">
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#dashboard\" class=\"collapsed\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-home\"></i>
\t\t\t\t\t\t\t\t<p>Dashboard</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"dashboard\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../../demo1/index.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Dashboard 1</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-section\">
\t\t\t\t\t\t\t<span class=\"sidebar-mini-icon\">
\t\t\t\t\t\t\t\t<i class=\"fa fa-ellipsis-h\"></i>
\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t<h4 class=\"text-section\">Components</h4>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item active submenu\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#base\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-layer-group\"></i>
\t\t\t\t\t\t\t\t<p>Base</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse show\" id=\"base\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li class=\"active\">
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/avatars.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Avatars</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/buttons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Buttons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/gridsystem.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Grid System</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/panels.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Panels</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/notifications.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Notifications</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/sweetalert.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sweet Alert</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/font-awesome-icons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Font Awesome Icons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/simple-line-icons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Simple Line Icons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/typography.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Typography</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-th-list\"></i>
\t\t\t\t\t\t\t\t<p>Sidebar Layouts</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"sidebarLayouts\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../sidebar-style-2.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sidebar Style 2</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../icon-menu.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Icon Menu</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#forms\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-pen-square\"></i>
\t\t\t\t\t\t\t\t<p>Forms</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"forms\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../forms/forms.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Basic Form</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#tables\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-table\"></i>
\t\t\t\t\t\t\t\t<p>Tables</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"tables\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../tables/tables.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Basic Table</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../tables/datatables.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Datatables</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#maps\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-map-marker-alt\"></i>
\t\t\t\t\t\t\t\t<p>Maps</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"maps\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../maps/googlemaps.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Google Maps</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../maps/jsvectormap.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Jsvectormap</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#charts\">
\t\t\t\t\t\t\t\t<i class=\"far fa-chart-bar\"></i>
\t\t\t\t\t\t\t\t<p>Charts</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"charts\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../charts/charts.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Chart Js</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../charts/sparkline.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sparkline</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a href=\"../widgets.html\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-desktop\"></i>
\t\t\t\t\t\t\t\t<p>Widgets</p>
\t\t\t\t\t\t\t\t<span class=\"badge badge-success\">4</span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a href=\"../../../documentation/index.html\">
\t\t\t\t\t\t\t  <i class=\"fas fa-file\"></i>
\t\t\t\t\t\t\t  <p>Documentation</p>
\t\t\t\t\t\t\t  <span class=\"badge badge-secondary\">1</span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#submenu\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-bars\"></i>
\t\t\t\t\t\t\t\t<p>Menu Levels</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"submenu\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#subnav1\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"subnav1\">
\t\t\t\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse subnav\">
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#subnav2\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"subnav2\">
\t\t\t\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse subnav\">
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t</ul>
\t\t\t\t</div>
\t\t\t</div>
\t\t</div>
\t\t<!-- End Sidebar -->

\t\t<div class=\"main-panel\">
\t\t\t<div class=\"main-header\">
\t\t\t\t<div class=\"main-header-logo\">
\t\t\t\t\t<!-- Logo Header -->
\t\t\t\t\t<div class=\"logo-header\" data-background-color=\"dark\">

\t\t\t\t\t\t<a href=\"../index.html\" class=\"logo\">
\t\t\t\t\t\t\t<img src=\"";
        // line 313
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/logo_light.svg"), "html", null, true);
        yield "\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\">
\t\t\t\t\t\t</a>
\t\t\t\t\t\t<div class=\"nav-toggle\">
\t\t\t\t\t\t\t<button class=\"btn btn-toggle toggle-sidebar\">
\t\t\t\t\t\t\t\t<i class=\"gg-menu-right\"></i>
\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t<button class=\"btn btn-toggle sidenav-toggler\">
\t\t\t\t\t\t\t\t<i class=\"gg-menu-left\"></i>
\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<button class=\"topbar-toggler more\">
\t\t\t\t\t\t\t<i class=\"gg-more-vertical-alt\"></i>
\t\t\t\t\t\t</button>

\t\t\t\t\t</div>
\t\t\t\t\t<!-- End Logo Header -->
\t\t\t\t</div>
\t\t\t\t<!-- Navbar Header -->
\t\t\t\t<nav class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\">

\t\t\t\t\t<div class=\"container-fluid\">
\t\t\t\t\t\t<nav class=\"navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex\">
\t\t\t\t\t\t\t<div class=\"input-group\">
\t\t\t\t\t\t\t\t<div class=\"input-group-prepend\">
\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-search pe-1\">
\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-search search-icon\"></i>
\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<input type=\"text\" placeholder=\"Search ...\" class=\"form-control\">
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</nav>

\t\t\t\t\t\t<ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret d-flex d-lg-none\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" data-bs-toggle=\"dropdown\" href=\"#\" role=\"button\" aria-expanded=\"false\" aria-haspopup=\"true\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-search\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-search animated fadeIn\">
\t\t\t\t\t\t\t\t\t<form class=\"navbar-left navbar-form nav-search\">
\t\t\t\t\t\t\t\t\t\t<div class=\"input-group\">
\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" placeholder=\"Search ...\" class=\"form-control\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</form>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"messageDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-envelope\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu messages-notif-box animated fadeIn\" aria-labelledby=\"messageDropdown\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-title d-flex justify-content-between align-items-center\">
\t\t\t\t\t\t\t\t\t\t\tMessages \t\t\t\t\t\t\t\t\t
\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"small\">Mark all as read</a>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"message-notif-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-center\">
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 374
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Jimmy Denis</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHow are you ?
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">5 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 386
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/chadengle.jpg"), "html", null, true);
        yield "\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Chad</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tOk, Thanks !
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 398
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/mlane.jpg"), "html", null, true);
        yield "\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Jhon Doe</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tReady for the meeting today...
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 410
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/talha.jpg"), "html", null, true);
        yield "\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Talha</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHi, Apa Kabar ?
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">17 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a class=\"see-all\" href=\"javascript:void(0);\">See all messages<i class=\"fa fa-angle-right\"></i> </a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"notifDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-bell\"></i>
\t\t\t\t\t\t\t\t\t<span class=\"notification\">4</span>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu notif-box animated fadeIn\" aria-labelledby=\"notifDropdown\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-title\">You have 4 new notification</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"notif-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-center\">
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-primary\"> <i class=\"fa fa-user-plus\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tNew user registered
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">5 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-success\"> <i class=\"fa fa-comment\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tRahmad commented on Admin
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 460
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile2.jpg"), "html", null, true);
        yield "\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tReza send messages to you
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-danger\"> <i class=\"fa fa-heart\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFarrah liked Admin
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">17 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a class=\"see-all\" href=\"javascript:void(0);\">See all notifications<i class=\"fa fa-angle-right\"></i> </a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fas fa-layer-group\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<div class=\"dropdown-menu quick-actions animated fadeIn\">
\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-header\">
\t\t\t\t\t\t\t\t\t\t<span class=\"title mb-1\">Quick Actions</span>
\t\t\t\t\t\t\t\t\t\t<span class=\"subtitle op-7\">Shortcuts</span>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-items\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"row m-0\">
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-danger rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"far fa-calendar-alt\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Calendar</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-warning rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-map\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Maps</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-info rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-file-excel\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Reports</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-success rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-envelope\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Emails</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-primary rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-file-invoice-dollar\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Invoice</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-secondary rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-credit-card\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Payments</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t
\t\t\t\t\t\t\t<li class=\"nav-item topbar-user dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle profile-pic\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<div class=\"avatar-sm\">
\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 555
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<span class=\"profile-username\">
\t\t\t\t\t\t\t\t\t\t<span class=\"op-7\">Hi,</span> <span class=\"fw-bold\">Hizrian</span>
\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user animated fadeIn\">
\t\t\t\t\t\t\t\t\t<div class=\"dropdown-user-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"user-box\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-lg\"><img src=\"";
        // line 565
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/profile.jpg"), "html", null, true);
        yield "\" alt=\"image profile\" class=\"avatar-img rounded\"></div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"u-text\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<h4>Hizrian</h4>
\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">hello@example.com</p><a href=\"profile.html\" class=\"btn btn-xs btn-secondary btn-sm\">View Profile</a>
\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">My Profile</a>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">My Balance</a>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Inbox</a>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Account Setting</a>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Logout</a>
\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</div>
\t\t\t\t</nav>
\t\t\t\t<!-- End Navbar -->
\t\t\t</div>
\t\t\t
\t\t\t<div class=\"container\">
\t\t\t\t<div class=\"page-inner\">
\t\t\t\t\t<div class=\"page-header\">
\t\t\t\t\t\t<h3 class=\"fw-bold mb-3\">Avatars</h3>
\t\t\t\t\t\t<ul class=\"breadcrumbs mb-3\">
\t\t\t\t\t\t\t<li class=\"nav-home\">
\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t<i class=\"icon-home\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"separator\">
\t\t\t\t\t\t\t\t<i class=\"icon-arrow-right\"></i>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a href=\"#\">Base</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"separator\">
\t\t\t\t\t\t\t\t<i class=\"icon-arrow-right\"></i>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a href=\"#\">Avatars</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Sizing</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xxl\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 625
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xl\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 629
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-lg\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 633
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 637
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-sm\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 641
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xs\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 645
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Status Indicator</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-online\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 660
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-offline\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 664
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-away\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 668
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Shape</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 683
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 687
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Group</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-group\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 703
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/jm_denis.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 706
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/chadengle.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"";
        // line 709
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/mlane.jpg"), "html", null, true);
        yield "\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"avatar-title rounded-circle border border-white\">CF</span>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</div>
\t\t\t
\t\t\t<footer class=\"footer\">
\t\t\t\t<div class=\"container-fluid d-flex justify-content-between\">
\t\t\t\t\t<nav class=\"pull-left\">
\t\t\t\t\t\t<ul class=\"nav\">
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"http://www.themekita.com\">
\t\t\t\t\t\t\t\t\tThemeKita
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"#\"> Help </a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"#\"> Licenses </a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</nav>
\t\t\t\t\t<div class=\"copyright\">
\t\t\t\t\t\t2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by
\t\t\t\t\t\t<a href=\"http://www.themekita.com\">ThemeKita</a>
\t\t\t\t\t</div>
\t\t\t\t\t<div>
\t\t\t\t\t\tDistributed by
\t\t\t\t\t\t<a target=\"_blank\" href=\"https://themewagon.com/\">ThemeWagon</a>.
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</footer>
\t\t</div>
\t\t
\t\t<!-- Custom template | don't include it in your project! -->
\t\t<div class=\"custom-template\">
\t\t\t<div class=\"title\">Settings</div>
\t\t\t<div class=\"custom-content\">
\t\t\t\t<div class=\"switcher\">
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Logo Header</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\" selected changeLogoHeaderColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeLogoHeaderColor\" data-color=\"blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"purple\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"light-blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"green\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"orange\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"red\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<br/>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"purple2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"light-blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"green2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"orange2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"red2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Navbar Header</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"purple\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"light-blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"green\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"orange\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"red\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<br/>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeTopBarColor\" data-color=\"blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"purple2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"light-blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"green2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"orange2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"red2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Sidebar</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeSideBarColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeSideBarColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeSideBarColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</div>
\t\t\t<div class=\"custom-toggle\">
\t\t\t\t<i class=\"icon-settings\"></i>
\t\t\t</div>
\t\t</div>
\t\t<!-- End Custom template -->
\t</div>
\t<!--   Core JS Files   -->
\t<script src=\"";
        // line 816
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/jquery-3.7.1.min.js"), "html", null, true);
        yield "\"></script>
\t<script src=\"";
        // line 817
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/popper.min.js"), "html", null, true);
        yield "\"></script>
\t<script src=\"";
        // line 818
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/core/bootstrap.min.js"), "html", null, true);
        yield "\"></script>
\t
\t<!-- jQuery Scrollbar -->
\t<script src=\"";
        // line 821
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"), "html", null, true);
        yield "\"></script>
\t<!-- Moment JS -->
\t<script src=\"";
        // line 823
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/moment/moment.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Chart JS -->
\t<script src=\"";
        // line 826
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart.js/chart.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- jQuery Sparkline -->
\t<script src=\"";
        // line 829
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jquery.sparkline/jquery.sparkline.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Chart Circle -->
\t<script src=\"";
        // line 832
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/chart-circle/circles.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Datatables -->
\t<script src=\"";
        // line 835
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/datatables/datatables.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Bootstrap Notify -->
\t<script src=\"";
        // line 838
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/bootstrap-notify/bootstrap-notify.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- jQuery Vector Maps -->
\t<script src=\"";
        // line 841
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/jsvectormap.min.js"), "html", null, true);
        yield "\"></script>
\t<script src=\"";
        // line 842
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/jsvectormap/world.js"), "html", null, true);
        yield "\"></script>

\t<!-- Sweet Alert -->
\t<script src=\"";
        // line 845
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/plugin/sweetalert/sweetalert.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Kaiadmin JS -->
\t<script src=\"";
        // line 848
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/kaiadmin.min.js"), "html", null, true);
        yield "\"></script>

\t<!-- Kaiadmin DEMO methods, don't include it in your project! -->
\t<script src=\"";
        // line 851
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/js/setting-demo2.js"), "html", null, true);
        yield "\"></script>
</body>
</html>";
        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        yield from [];
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "back/components/avatars.html.twig";
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
        return array (  1032 => 851,  1026 => 848,  1020 => 845,  1014 => 842,  1010 => 841,  1004 => 838,  998 => 835,  992 => 832,  986 => 829,  980 => 826,  974 => 823,  969 => 821,  963 => 818,  959 => 817,  955 => 816,  845 => 709,  839 => 706,  833 => 703,  814 => 687,  807 => 683,  789 => 668,  782 => 664,  775 => 660,  757 => 645,  750 => 641,  743 => 637,  736 => 633,  729 => 629,  722 => 625,  659 => 565,  646 => 555,  548 => 460,  495 => 410,  480 => 398,  465 => 386,  450 => 374,  386 => 313,  108 => 38,  94 => 27,  88 => 24,  84 => 23,  80 => 22,  69 => 14,  62 => 10,  56 => 7,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"en\">
<head>
\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
\t<title>Avatars - Kaiadmin Bootstrap 5 Admin Dashboard</title>
\t<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
\t<link rel=\"icon\" href=\"{{ asset('back/img/kaiadmin/favicon.ico') }}\" type=\"image/x-icon\"/>

\t<!-- Fonts and icons -->
\t<script src=\"{{ asset('back/js/plugin/webfont/webfont.min.js') }}\"></script>
\t<script>
\t\tWebFont.load({
\t\t\tgoogle: {\"families\":[\"Public Sans:300,400,500,600,700\"]},
\t\t\tcustom: {\"families\":[\"Font Awesome 5 Solid\", \"Font Awesome 5 Regular\", \"Font Awesome 5 Brands\", \"simple-line-icons\"], urls: ['{{ asset('back/css/fonts.min.css') }}']},
\t\t\tactive: function() {
\t\t\t\tsessionStorage.fonts = true;
\t\t\t}
\t\t});
\t</script>

\t<!-- CSS Files -->
\t<link rel=\"stylesheet\" href=\"{{ asset('back/css/bootstrap.min.css') }}\">
\t<link rel=\"stylesheet\" href=\"{{ asset('back/css/plugins.min.css') }}\">
\t<link rel=\"stylesheet\" href=\"{{ asset('back/css/kaiadmin.min.css') }}\">

\t<!-- CSS Just for demo purpose, don't include it in your project -->
\t<link rel=\"stylesheet\" href=\"{{ asset('back/css/demo.css') }}\">
</head>
<body>
\t<div class=\"wrapper\">
\t\t<!-- Sidebar -->
\t\t<div class=\"sidebar\" data-background-color=\"dark\">
\t\t\t<div class=\"sidebar-logo\">
\t\t\t\t<!-- Logo Header -->
\t\t\t\t<div class=\"logo-header\" data-background-color=\"dark\">

\t\t\t\t\t<a href=\"../index.html\" class=\"logo\">
\t\t\t\t\t\t<img src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\">
\t\t\t\t\t</a>
\t\t\t\t\t<div class=\"nav-toggle\">
\t\t\t\t\t\t<button class=\"btn btn-toggle toggle-sidebar\">
\t\t\t\t\t\t\t<i class=\"gg-menu-right\"></i>
\t\t\t\t\t\t</button>
\t\t\t\t\t\t<button class=\"btn btn-toggle sidenav-toggler\">
\t\t\t\t\t\t\t<i class=\"gg-menu-left\"></i>
\t\t\t\t\t\t</button>
\t\t\t\t\t</div>
\t\t\t\t\t<button class=\"topbar-toggler more\">
\t\t\t\t\t\t<i class=\"gg-more-vertical-alt\"></i>
\t\t\t\t\t</button>
\t\t\t\t</div>
\t\t\t\t<!-- End Logo Header -->\t
\t\t\t</div>\t
\t\t\t<div class=\"sidebar-wrapper scrollbar scrollbar-inner\">
\t\t\t\t<div class=\"sidebar-content\">
\t\t\t\t\t<ul class=\"nav nav-secondary\">
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#dashboard\" class=\"collapsed\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-home\"></i>
\t\t\t\t\t\t\t\t<p>Dashboard</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"dashboard\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../../demo1/index.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Dashboard 1</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-section\">
\t\t\t\t\t\t\t<span class=\"sidebar-mini-icon\">
\t\t\t\t\t\t\t\t<i class=\"fa fa-ellipsis-h\"></i>
\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t<h4 class=\"text-section\">Components</h4>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item active submenu\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#base\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-layer-group\"></i>
\t\t\t\t\t\t\t\t<p>Base</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse show\" id=\"base\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li class=\"active\">
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/avatars.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Avatars</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/buttons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Buttons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/gridsystem.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Grid System</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/panels.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Panels</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/notifications.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Notifications</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/sweetalert.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sweet Alert</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/font-awesome-icons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Font Awesome Icons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/simple-line-icons.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Simple Line Icons</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../components/typography.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Typography</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#sidebarLayouts\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-th-list\"></i>
\t\t\t\t\t\t\t\t<p>Sidebar Layouts</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"sidebarLayouts\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../sidebar-style-2.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sidebar Style 2</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../icon-menu.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Icon Menu</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#forms\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-pen-square\"></i>
\t\t\t\t\t\t\t\t<p>Forms</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"forms\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../forms/forms.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Basic Form</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#tables\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-table\"></i>
\t\t\t\t\t\t\t\t<p>Tables</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"tables\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../tables/tables.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Basic Table</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../tables/datatables.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Datatables</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#maps\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-map-marker-alt\"></i>
\t\t\t\t\t\t\t\t<p>Maps</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"maps\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../maps/googlemaps.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Google Maps</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../maps/jsvectormap.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Jsvectormap</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#charts\">
\t\t\t\t\t\t\t\t<i class=\"far fa-chart-bar\"></i>
\t\t\t\t\t\t\t\t<p>Charts</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"charts\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../charts/charts.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Chart Js</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"../charts/sparkline.html\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Sparkline</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a href=\"../widgets.html\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-desktop\"></i>
\t\t\t\t\t\t\t\t<p>Widgets</p>
\t\t\t\t\t\t\t\t<span class=\"badge badge-success\">4</span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a href=\"../../../documentation/index.html\">
\t\t\t\t\t\t\t  <i class=\"fas fa-file\"></i>
\t\t\t\t\t\t\t  <p>Documentation</p>
\t\t\t\t\t\t\t  <span class=\"badge badge-secondary\">1</span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t</li>
\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#submenu\">
\t\t\t\t\t\t\t\t<i class=\"fas fa-bars\"></i>
\t\t\t\t\t\t\t\t<p>Menu Levels</p>
\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t<div class=\"collapse\" id=\"submenu\">
\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#subnav1\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"subnav1\">
\t\t\t\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse subnav\">
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a data-bs-toggle=\"collapse\" href=\"#subnav2\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"subnav2\">
\t\t\t\t\t\t\t\t\t\t\t<ul class=\"nav nav-collapse subnav\">
\t\t\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 2</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t<span class=\"sub-item\">Level 1</span>
\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</li>
\t\t\t\t\t</ul>
\t\t\t\t</div>
\t\t\t</div>
\t\t</div>
\t\t<!-- End Sidebar -->

\t\t<div class=\"main-panel\">
\t\t\t<div class=\"main-header\">
\t\t\t\t<div class=\"main-header-logo\">
\t\t\t\t\t<!-- Logo Header -->
\t\t\t\t\t<div class=\"logo-header\" data-background-color=\"dark\">

\t\t\t\t\t\t<a href=\"../index.html\" class=\"logo\">
\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/kaiadmin/logo_light.svg') }}\" alt=\"navbar brand\" class=\"navbar-brand\" height=\"20\">
\t\t\t\t\t\t</a>
\t\t\t\t\t\t<div class=\"nav-toggle\">
\t\t\t\t\t\t\t<button class=\"btn btn-toggle toggle-sidebar\">
\t\t\t\t\t\t\t\t<i class=\"gg-menu-right\"></i>
\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t<button class=\"btn btn-toggle sidenav-toggler\">
\t\t\t\t\t\t\t\t<i class=\"gg-menu-left\"></i>
\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<button class=\"topbar-toggler more\">
\t\t\t\t\t\t\t<i class=\"gg-more-vertical-alt\"></i>
\t\t\t\t\t\t</button>

\t\t\t\t\t</div>
\t\t\t\t\t<!-- End Logo Header -->
\t\t\t\t</div>
\t\t\t\t<!-- Navbar Header -->
\t\t\t\t<nav class=\"navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom\">

\t\t\t\t\t<div class=\"container-fluid\">
\t\t\t\t\t\t<nav class=\"navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex\">
\t\t\t\t\t\t\t<div class=\"input-group\">
\t\t\t\t\t\t\t\t<div class=\"input-group-prepend\">
\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-search pe-1\">
\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-search search-icon\"></i>
\t\t\t\t\t\t\t\t\t</button>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<input type=\"text\" placeholder=\"Search ...\" class=\"form-control\">
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</nav>

\t\t\t\t\t\t<ul class=\"navbar-nav topbar-nav ms-md-auto align-items-center\">
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret d-flex d-lg-none\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" data-bs-toggle=\"dropdown\" href=\"#\" role=\"button\" aria-expanded=\"false\" aria-haspopup=\"true\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-search\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-search animated fadeIn\">
\t\t\t\t\t\t\t\t\t<form class=\"navbar-left navbar-form nav-search\">
\t\t\t\t\t\t\t\t\t\t<div class=\"input-group\">
\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" placeholder=\"Search ...\" class=\"form-control\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</form>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"messageDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-envelope\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu messages-notif-box animated fadeIn\" aria-labelledby=\"messageDropdown\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-title d-flex justify-content-between align-items-center\">
\t\t\t\t\t\t\t\t\t\t\tMessages \t\t\t\t\t\t\t\t\t
\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"small\">Mark all as read</a>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"message-notif-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-center\">
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Jimmy Denis</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHow are you ?
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">5 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/chadengle.jpg') }}\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Chad</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tOk, Thanks !
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/mlane.jpg') }}\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Jhon Doe</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tReady for the meeting today...
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/talha.jpg') }}\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"subject\">Talha</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHi, Apa Kabar ?
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">17 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a class=\"see-all\" href=\"javascript:void(0);\">See all messages<i class=\"fa fa-angle-right\"></i> </a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"notifDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fa fa-bell\"></i>
\t\t\t\t\t\t\t\t\t<span class=\"notification\">4</span>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu notif-box animated fadeIn\" aria-labelledby=\"notifDropdown\">
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-title\">You have 4 new notification</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<div class=\"notif-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-center\">
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-primary\"> <i class=\"fa fa-user-plus\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tNew user registered
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">5 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-success\"> <i class=\"fa fa-comment\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tRahmad commented on Admin
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-img\"> 
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/profile2.jpg') }}\" alt=\"Img Profile\">
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tReza send messages to you
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">12 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-icon notif-danger\"> <i class=\"fa fa-heart\"></i> </div>
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"notif-content\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"block\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFarrah liked Admin
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"time\">17 minutes ago</span> 
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t<a class=\"see-all\" href=\"javascript:void(0);\">See all notifications<i class=\"fa fa-angle-right\"></i> </a>
\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item topbar-icon dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<i class=\"fas fa-layer-group\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<div class=\"dropdown-menu quick-actions animated fadeIn\">
\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-header\">
\t\t\t\t\t\t\t\t\t\t<span class=\"title mb-1\">Quick Actions</span>
\t\t\t\t\t\t\t\t\t\t<span class=\"subtitle op-7\">Shortcuts</span>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-items\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"row m-0\">
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-danger rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"far fa-calendar-alt\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Calendar</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-warning rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-map\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Maps</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-info rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-file-excel\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Reports</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-success rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-envelope\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Emails</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-primary rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-file-invoice-dollar\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Invoice</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"col-6 col-md-4 p-0\" href=\"#\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"quick-actions-item\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-item bg-secondary rounded-circle\">
\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"fas fa-credit-card\"></i>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"text\">Payments</span>
\t\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t
\t\t\t\t\t\t\t<li class=\"nav-item topbar-user dropdown hidden-caret\">
\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle profile-pic\" data-bs-toggle=\"dropdown\" href=\"#\" aria-expanded=\"false\">
\t\t\t\t\t\t\t\t\t<div class=\"avatar-sm\">
\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/profile.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t<span class=\"profile-username\">
\t\t\t\t\t\t\t\t\t\t<span class=\"op-7\">Hi,</span> <span class=\"fw-bold\">Hizrian</span>
\t\t\t\t\t\t\t\t\t</span>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user animated fadeIn\">
\t\t\t\t\t\t\t\t\t<div class=\"dropdown-user-scroll scrollbar-outer\">
\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"user-box\">
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-lg\"><img src=\"{{ asset('back/img/profile.jpg') }}\" alt=\"image profile\" class=\"avatar-img rounded\"></div>
\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"u-text\">
\t\t\t\t\t\t\t\t\t\t\t\t\t<h4>Hizrian</h4>
\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text-muted\">hello@example.com</p><a href=\"profile.html\" class=\"btn btn-xs btn-secondary btn-sm\">View Profile</a>
\t\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t\t<li>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">My Profile</a>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">My Balance</a>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Inbox</a>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Account Setting</a>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"dropdown-divider\"></div>
\t\t\t\t\t\t\t\t\t\t\t<a class=\"dropdown-item\" href=\"#\">Logout</a>
\t\t\t\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t</ul>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</div>
\t\t\t\t</nav>
\t\t\t\t<!-- End Navbar -->
\t\t\t</div>
\t\t\t
\t\t\t<div class=\"container\">
\t\t\t\t<div class=\"page-inner\">
\t\t\t\t\t<div class=\"page-header\">
\t\t\t\t\t\t<h3 class=\"fw-bold mb-3\">Avatars</h3>
\t\t\t\t\t\t<ul class=\"breadcrumbs mb-3\">
\t\t\t\t\t\t\t<li class=\"nav-home\">
\t\t\t\t\t\t\t\t<a href=\"#\">
\t\t\t\t\t\t\t\t\t<i class=\"icon-home\"></i>
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"separator\">
\t\t\t\t\t\t\t\t<i class=\"icon-arrow-right\"></i>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a href=\"#\">Base</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"separator\">
\t\t\t\t\t\t\t\t<i class=\"icon-arrow-right\"></i>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a href=\"#\">Avatars</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"row\">
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Sizing</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xxl\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xl\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-lg\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-sm\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-xs\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Status Indicator</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-online\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-offline\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar avatar-away\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Shape</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded\">
\t\t\t\t\t\t\t\t\t\t</div>

\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle\">
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t\t<div class=\"col-md-12\">
\t\t\t\t\t\t\t<div class=\"card\">
\t\t\t\t\t\t\t\t<div class=\"card-header\">
\t\t\t\t\t\t\t\t\t<h4 class=\"card-title\">Group</h4>

\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t<div class=\"card-body\">
\t\t\t\t\t\t\t\t\t<p class=\"demo\">
\t\t\t\t\t\t\t\t\t\t<div class=\"avatar-group\">
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/jm_denis.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/chadengle.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"{{ asset('back/img/mlane.jpg') }}\" alt=\"...\" class=\"avatar-img rounded-circle border border-white\">
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t\t<div class=\"avatar\">
\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"avatar-title rounded-circle border border-white\">CF</span>
\t\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t\t\t</p>
\t\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t\t</div>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</div>
\t\t\t
\t\t\t<footer class=\"footer\">
\t\t\t\t<div class=\"container-fluid d-flex justify-content-between\">
\t\t\t\t\t<nav class=\"pull-left\">
\t\t\t\t\t\t<ul class=\"nav\">
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"http://www.themekita.com\">
\t\t\t\t\t\t\t\t\tThemeKita
\t\t\t\t\t\t\t\t</a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"#\"> Help </a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t\t<li class=\"nav-item\">
\t\t\t\t\t\t\t\t<a class=\"nav-link\" href=\"#\"> Licenses </a>
\t\t\t\t\t\t\t</li>
\t\t\t\t\t\t</ul>
\t\t\t\t\t</nav>
\t\t\t\t\t<div class=\"copyright\">
\t\t\t\t\t\t2024, made with <i class=\"fa fa-heart heart text-danger\"></i> by
\t\t\t\t\t\t<a href=\"http://www.themekita.com\">ThemeKita</a>
\t\t\t\t\t</div>
\t\t\t\t\t<div>
\t\t\t\t\t\tDistributed by
\t\t\t\t\t\t<a target=\"_blank\" href=\"https://themewagon.com/\">ThemeWagon</a>.
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</footer>
\t\t</div>
\t\t
\t\t<!-- Custom template | don't include it in your project! -->
\t\t<div class=\"custom-template\">
\t\t\t<div class=\"title\">Settings</div>
\t\t\t<div class=\"custom-content\">
\t\t\t\t<div class=\"switcher\">
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Logo Header</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\" selected changeLogoHeaderColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeLogoHeaderColor\" data-color=\"blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"purple\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"light-blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"green\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"orange\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"red\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<br/>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"purple2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"light-blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"green2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"orange2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeLogoHeaderColor\" data-color=\"red2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Navbar Header</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"purple\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"light-blue\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"green\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"orange\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"red\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<br/>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeTopBarColor\" data-color=\"blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"purple2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"light-blue2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"green2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"orange2\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeTopBarColor\" data-color=\"red2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t\t<div class=\"switch-block\">
\t\t\t\t\t\t<h4>Sidebar</h4>
\t\t\t\t\t\t<div class=\"btnSwitch\">
\t\t\t\t\t\t\t<button type=\"button\" class=\"selected changeSideBarColor\" data-color=\"white\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeSideBarColor\" data-color=\"dark\"></button>
\t\t\t\t\t\t\t<button type=\"button\" class=\"changeSideBarColor\" data-color=\"dark2\"></button>
\t\t\t\t\t\t</div>
\t\t\t\t\t</div>
\t\t\t\t</div>
\t\t\t</div>
\t\t\t<div class=\"custom-toggle\">
\t\t\t\t<i class=\"icon-settings\"></i>
\t\t\t</div>
\t\t</div>
\t\t<!-- End Custom template -->
\t</div>
\t<!--   Core JS Files   -->
\t<script src=\"{{ asset('back/js/core/jquery-3.7.1.min.js') }}\"></script>
\t<script src=\"{{ asset('back/js/core/popper.min.js') }}\"></script>
\t<script src=\"{{ asset('back/js/core/bootstrap.min.js') }}\"></script>
\t
\t<!-- jQuery Scrollbar -->
\t<script src=\"{{ asset('back/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js') }}\"></script>
\t<!-- Moment JS -->
\t<script src=\"{{ asset('back/js/plugin/moment/moment.min.js') }}\"></script>

\t<!-- Chart JS -->
\t<script src=\"{{ asset('back/js/plugin/chart.js/chart.min.js') }}\"></script>

\t<!-- jQuery Sparkline -->
\t<script src=\"{{ asset('back/js/plugin/jquery.sparkline/jquery.sparkline.min.js') }}\"></script>

\t<!-- Chart Circle -->
\t<script src=\"{{ asset('back/js/plugin/chart-circle/circles.min.js') }}\"></script>

\t<!-- Datatables -->
\t<script src=\"{{ asset('back/js/plugin/datatables/datatables.min.js') }}\"></script>

\t<!-- Bootstrap Notify -->
\t<script src=\"{{ asset('back/js/plugin/bootstrap-notify/bootstrap-notify.min.js') }}\"></script>

\t<!-- jQuery Vector Maps -->
\t<script src=\"{{ asset('back/js/plugin/jsvectormap/jsvectormap.min.js') }}\"></script>
\t<script src=\"{{ asset('back/js/plugin/jsvectormap/world.js') }}\"></script>

\t<!-- Sweet Alert -->
\t<script src=\"{{ asset('back/js/plugin/sweetalert/sweetalert.min.js') }}\"></script>

\t<!-- Kaiadmin JS -->
\t<script src=\"{{ asset('back/js/kaiadmin.min.js') }}\"></script>

\t<!-- Kaiadmin DEMO methods, don't include it in your project! -->
\t<script src=\"{{ asset('back/js/setting-demo2.js') }}\"></script>
</body>
</html>", "back/components/avatars.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\back\\components\\avatars.html.twig");
    }
}
