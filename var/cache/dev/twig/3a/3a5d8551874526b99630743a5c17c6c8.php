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

/* back/art-archives.html.twig */
class __TwigTemplate_30d26a5c908737c93177ec2c4f02c2e8 extends Template
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

        $this->blocks = [
            'title' => [$this, 'block_title'],
            'content' => [$this, 'block_content'],
        ];
    }

    protected function doGetParent(array $context): bool|string|Template|TemplateWrapper
    {
        // line 1
        return "back/index.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-archives.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art-archives.html.twig"));

        $this->parent = $this->load("back/index.html.twig", 1);
        yield from $this->parent->unwrap()->yield($context, array_merge($this->blocks, $blocks));
        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

    }

    // line 3
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

        yield "Publications Archivées";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 5
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_content(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "content"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "content"));

        // line 6
        yield "<div class=\"content-wrapper\">
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
        // line 16
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\">Dashboard</a></li>
        <li class=\"breadcrumb-item\"><a href=\"";
        // line 17
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_page", ["path" => "art/index"]);
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
        // line 32
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 32, $this->source); })()), "session", [], "any", false, false, false, 32), "flashbag", [], "any", false, false, false, 32), "all", [], "method", false, false, false, 32));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 33
            yield "            ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 34
                yield "              <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
                ";
                // line 35
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
            // line 41
            yield "          ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 42
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
        // line 56
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["arts"]) || array_key_exists("arts", $context) ? $context["arts"] : (function () { throw new RuntimeError('Variable "arts" does not exist.', 56, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["art"]) {
            // line 57
            yield "                  <tr>
                    <td>
                      <img src=\"";
            // line 59
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "imageUrl", [], "any", false, false, false, 59), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 59), "html", null, true);
            yield "\" style=\"width: 50px; height: 50px; object-fit: cover;\">
                    </td>
                    <td>";
            // line 61
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "title", [], "any", false, false, false, 61), "html", null, true);
            yield "</td>
                    <td>";
            // line 62
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["art"], "description", [], "any", false, false, false, 62), 0, 50), "html", null, true);
            yield "...</td>
                    <td>
                      <span class=\"badge bg-secondary\">Archivé</span>
                    </td>
                    <td>";
            // line 66
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 66)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["art"], "createdAt", [], "any", false, false, false, 66), "d/m/Y H:i"), "html", null, true)) : (""));
            yield "</td>
                    <td>
                      <!-- Bouton récupérer -->
                      <form method=\"post\" action=\"";
            // line 69
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_restore", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 69)]), "html", null, true);
            yield "\" style=\"display: inline-block; margin-right: 5px;\">
                        <input type=\"hidden\" name=\"_token\" value=\"";
            // line 70
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("restore" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 70))), "html", null, true);
            yield "\">
                        <button type=\"submit\" class=\"btn btn-success btn-sm\" style=\"border-radius: 6px; padding: 4px 12px; font-size: 13px;\" title=\"Récupérer\">
                          <i class=\"fas fa-undo\"></i> Récupérer
                        </button>
                      </form>
                      
                      <!-- Bouton supprimer définitivement -->
                      <form method=\"post\" action=\"";
            // line 77
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("admin_art_delete", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 77)]), "html", null, true);
            yield "\" style=\"display: inline-block;\">
                        <input type=\"hidden\" name=\"_token\" value=\"";
            // line 78
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken(("delete" . CoreExtension::getAttribute($this->env, $this->source, $context["art"], "id", [], "any", false, false, false, 78))), "html", null, true);
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
        // line 85
        if (!$context['_iterated']) {
            // line 86
            yield "                  <tr>
                    <td colspan=\"6\" class=\"text-center\">Aucune publication archivée</td>
                  </tr>
                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['art'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 90
        yield "              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "back/art-archives.html.twig";
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
        return array (  256 => 90,  247 => 86,  245 => 85,  233 => 78,  229 => 77,  219 => 70,  215 => 69,  209 => 66,  202 => 62,  198 => 61,  191 => 59,  187 => 57,  182 => 56,  166 => 42,  160 => 41,  148 => 35,  143 => 34,  138 => 33,  134 => 32,  116 => 17,  112 => 16,  100 => 6,  87 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'back/index.html.twig' %}

{% block title %}Publications Archivées{% endblock %}

{% block content %}
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
        <li class=\"breadcrumb-item\"><a href=\"{{ path('back_dashboard') }}\">Dashboard</a></li>
        <li class=\"breadcrumb-item\"><a href=\"{{ path('back_page', {'path': 'art/index'}) }}\">Gestion</a></li>
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
{% endblock %}
", "back/art-archives.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\back\\art-archives.html.twig");
    }
}
