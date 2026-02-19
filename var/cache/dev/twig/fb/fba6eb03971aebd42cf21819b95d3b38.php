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

/* front/art_detail.html.twig */
class __TwigTemplate_e2cb5ab2aa4cbd076c44706b446ba206 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/art_detail.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "front/art_detail.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"fr\">
<head>
    <meta charset=\"utf-8\" />
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
    <title>";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 7, $this->source); })()), "title", [], "any", false, false, false, 7), "html", null, true);
        yield " - Détail</title>
    
    <!-- Bootstrap CSS -->
    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">
    <!-- Font Awesome -->
    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css\">
    
    <style>
        .art-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .art-image {
            width: 100%;
            max-height: 600px;
            object-fit: contain;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .art-info {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .view-counter {
            background: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            display: inline-block;
            margin-bottom: 20px;
        }
        .back-btn {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 1000;
            background: rgba(0,0,0,0.7);
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 50%;
            cursor: pointer;
            transition: all 0.3s;
        }
        .back-btn:hover {
            background: rgba(0,0,0,0.9);
            transform: scale(1.1);
        }
    </style>
</head>
<body class=\"bg-light\">
    <button class=\"back-btn\" onclick=\"history.back()\">
        <i class=\"fas fa-arrow-left\"></i>
    </button>

    <div class=\"art-container\">
        <div class=\"row\">
            <div class=\"col-lg-8\">
                <img src=\"";
        // line 69
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 69, $this->source); })()), "imageUrl", [], "any", false, false, false, 69), "html", null, true);
        yield "\" alt=\"";
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 69, $this->source); })()), "title", [], "any", false, false, false, 69), "html", null, true);
        yield "\" class=\"art-image\">
            </div>
            <div class=\"col-lg-4\">
                <div class=\"art-info\">
                    <div class=\"view-counter\">
                        <i class=\"fas fa-eye\"></i> ";
        // line 74
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["totalViews"]) || array_key_exists("totalViews", $context) ? $context["totalViews"] : (function () { throw new RuntimeError('Variable "totalViews" does not exist.', 74, $this->source); })()), "html", null, true);
        yield " vues
                    </div>
                    
                    <h1 class=\"mb-4\">";
        // line 77
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 77, $this->source); })()), "title", [], "any", false, false, false, 77), "html", null, true);
        yield "</h1>
                    
                    <div class=\"mb-4\">
                        <h5>Description</h5>
                        <p>";
        // line 81
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 81, $this->source); })()), "description", [], "any", false, false, false, 81), "html", null, true);
        yield "</p>
                    </div>
                    
                    <div class=\"mb-4\">
                        <h5>Détails</h5>
                        <ul class=\"list-unstyled\">
                            <li><strong>Statut:</strong> 
                                ";
        // line 88
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 88, $this->source); })()), "status", [], "any", false, false, false, 88) == "active")) {
            // line 89
            yield "                                    <span class=\"badge bg-success\">Publié</span>
                                ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 90
(isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 90, $this->source); })()), "status", [], "any", false, false, false, 90) == "en attente")) {
            // line 91
            yield "                                    <span class=\"badge bg-warning\">En attente</span>
                                ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 92
(isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 92, $this->source); })()), "status", [], "any", false, false, false, 92) == "archived")) {
            // line 93
            yield "                                    <span class=\"badge bg-secondary\">Archivé</span>
                                ";
        } else {
            // line 95
            yield "                                    <span class=\"badge bg-dark\">";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 95, $this->source); })()), "status", [], "any", false, false, false, 95), "html", null, true);
            yield "</span>
                                ";
        }
        // line 97
        yield "                            </li>
                            <li><strong>Date de création:</strong> ";
        // line 98
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 98, $this->source); })()), "createdAt", [], "any", false, false, false, 98), "d/m/Y H:i"), "html", null, true);
        yield "</li>
                            <li><strong>ID:</strong> #";
        // line 99
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["art"]) || array_key_exists("art", $context) ? $context["art"] : (function () { throw new RuntimeError('Variable "art" does not exist.', 99, $this->source); })()), "id", [], "any", false, false, false, 99), "html", null, true);
        yield "</li>
                        </ul>
                    </div>
                    
                    ";
        // line 103
        if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), (isset($context["recentViews"]) || array_key_exists("recentViews", $context) ? $context["recentViews"] : (function () { throw new RuntimeError('Variable "recentViews" does not exist.', 103, $this->source); })())) > 0)) {
            // line 104
            yield "                    <div class=\"mb-4\">
                        <h5>Activité récente</h5>
                        <small class=\"text-muted\">
                            Dernière vue: ";
            // line 107
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["recentViews"]) || array_key_exists("recentViews", $context) ? $context["recentViews"] : (function () { throw new RuntimeError('Variable "recentViews" does not exist.', 107, $this->source); })()), 0, [], "array", false, false, false, 107), "viewedAt", [], "any", false, false, false, 107), "d/m/Y H:i"), "html", null, true);
            yield "
                        </small>
                    </div>
                    ";
        }
        // line 111
        yield "                    
                    <div class=\"d-grid gap-2\">
                        <a href=\"";
        // line 113
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("front_gallery");
        yield "\" class=\"btn btn-primary\">
                            <i class=\"fas fa-images\"></i> Retour à la galerie
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>
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
        return "front/art_detail.html.twig";
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
        return array (  208 => 113,  204 => 111,  197 => 107,  192 => 104,  190 => 103,  183 => 99,  179 => 98,  176 => 97,  170 => 95,  166 => 93,  164 => 92,  161 => 91,  159 => 90,  156 => 89,  154 => 88,  144 => 81,  137 => 77,  131 => 74,  121 => 69,  56 => 7,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"fr\">
<head>
    <meta charset=\"utf-8\" />
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />
    <title>{{ art.title }} - Détail</title>
    
    <!-- Bootstrap CSS -->
    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">
    <!-- Font Awesome -->
    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css\">
    
    <style>
        .art-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .art-image {
            width: 100%;
            max-height: 600px;
            object-fit: contain;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .art-info {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .view-counter {
            background: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            display: inline-block;
            margin-bottom: 20px;
        }
        .back-btn {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 1000;
            background: rgba(0,0,0,0.7);
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 50%;
            cursor: pointer;
            transition: all 0.3s;
        }
        .back-btn:hover {
            background: rgba(0,0,0,0.9);
            transform: scale(1.1);
        }
    </style>
</head>
<body class=\"bg-light\">
    <button class=\"back-btn\" onclick=\"history.back()\">
        <i class=\"fas fa-arrow-left\"></i>
    </button>

    <div class=\"art-container\">
        <div class=\"row\">
            <div class=\"col-lg-8\">
                <img src=\"{{ art.imageUrl }}\" alt=\"{{ art.title }}\" class=\"art-image\">
            </div>
            <div class=\"col-lg-4\">
                <div class=\"art-info\">
                    <div class=\"view-counter\">
                        <i class=\"fas fa-eye\"></i> {{ totalViews }} vues
                    </div>
                    
                    <h1 class=\"mb-4\">{{ art.title }}</h1>
                    
                    <div class=\"mb-4\">
                        <h5>Description</h5>
                        <p>{{ art.description }}</p>
                    </div>
                    
                    <div class=\"mb-4\">
                        <h5>Détails</h5>
                        <ul class=\"list-unstyled\">
                            <li><strong>Statut:</strong> 
                                {% if art.status == 'active' %}
                                    <span class=\"badge bg-success\">Publié</span>
                                {% elseif art.status == 'en attente' %}
                                    <span class=\"badge bg-warning\">En attente</span>
                                {% elseif art.status == 'archived' %}
                                    <span class=\"badge bg-secondary\">Archivé</span>
                                {% else %}
                                    <span class=\"badge bg-dark\">{{ art.status }}</span>
                                {% endif %}
                            </li>
                            <li><strong>Date de création:</strong> {{ art.createdAt|date('d/m/Y H:i') }}</li>
                            <li><strong>ID:</strong> #{{ art.id }}</li>
                        </ul>
                    </div>
                    
                    {% if recentViews|length > 0 %}
                    <div class=\"mb-4\">
                        <h5>Activité récente</h5>
                        <small class=\"text-muted\">
                            Dernière vue: {{ recentViews[0].viewedAt|date('d/m/Y H:i') }}
                        </small>
                    </div>
                    {% endif %}
                    
                    <div class=\"d-grid gap-2\">
                        <a href=\"{{ path('front_gallery') }}\" class=\"btn btn-primary\">
                            <i class=\"fas fa-images\"></i> Retour à la galerie
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>
</body>
</html>
", "front/art_detail.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\front\\art_detail.html.twig");
    }
}
