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

/* commande/historique.html.twig */
class __TwigTemplate_bccff54d715d83864ca52790166619ab extends Template
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
            'body' => [$this, 'block_body'],
        ];
    }

    protected function doGetParent(array $context): bool|string|Template|TemplateWrapper
    {
        // line 1
        return "base_front.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "commande/historique.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "commande/historique.html.twig"));

        $this->parent = $this->load("base_front.html.twig", 1);
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

        yield "Historique de mes commandes - Pegasus";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 5
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

        // line 6
        yield "<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Historique de mes achats</h2>
    </div>

    <div class=\"row justify-content-center\" style=\"margin-top: 20px;\">
      <div class=\"col-md-10\">
        <div class=\"table-responsive\">
          <table class=\"table table-bordered table-hover\">
            <thead style=\"background-color: #222831; color: white;\">
              <tr>
                <th>N° Commande</th>
                <th>Date</th>
                <th>Total</th>
                <th>Statut</th>
                <th>Détails</th>
              </tr>
            </thead>
            <tbody>
              ";
        // line 26
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["commandes"]) || array_key_exists("commandes", $context) ? $context["commandes"] : (function () { throw new RuntimeError('Variable "commandes" does not exist.', 26, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["commande"]) {
            // line 27
            yield "                <tr>
                  <td style=\"vertical-align: middle;\"><strong>#";
            // line 28
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "id", [], "any", false, false, false, 28), "html", null, true);
            yield "</strong></td>
                  <td style=\"vertical-align: middle;\">";
            // line 29
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "dateCommande", [], "any", false, false, false, 29)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "dateCommande", [], "any", false, false, false, 29), "d/m/Y H:i"), "html", null, true)) : (""));
            yield "</td>
                  <td style=\"vertical-align: middle; font-weight: bold; color: #ffbe33;\">";
            // line 30
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "total", [], "any", false, false, false, 30), "html", null, true);
            yield " €</td>
                  <td style=\"vertical-align: middle; font-weight: bold;\">
                    ";
            // line 32
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 32) == "validee")) {
                // line 33
                yield "                      <span style=\"color: green;\"><i class=\"fa fa-check-circle\"></i> Validée</span>
                    ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 34
$context["commande"], "statut", [], "any", false, false, false, 34) == "en_attente")) {
                // line 35
                yield "                      <span style=\"color: orange;\"><i class=\"fa fa-clock-o\"></i> En attente</span>
                    ";
            } else {
                // line 37
                yield "                      <span style=\"color: gray;\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::capitalize($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "statut", [], "any", false, false, false, 37)), "html", null, true);
                yield "</span>
                    ";
            }
            // line 39
            yield "                  </td>
                  <td style=\"vertical-align: middle;\">
                    <a href=\"";
            // line 41
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_commande_confirmation", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "id", [], "any", false, false, false, 41)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-info\" style=\"color: white;\">
                      <i class=\"fa fa-eye\"></i> Voir
                    </a>
                    <a href=\"";
            // line 44
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_commande_ticket", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["commande"], "id", [], "any", false, false, false, 44)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-success\" title=\"Télécharger le ticket PDF\">
                      <i class=\"fa fa-file-pdf-o\"></i> PDF
                    </a>
                  </td>
                </tr>
              ";
            $context['_iterated'] = true;
        }
        // line 49
        if (!$context['_iterated']) {
            // line 50
            yield "                <tr>
                  <td colspan=\"5\" class=\"text-center\">Vous n'avez passé aucune commande.</td>
                </tr>
              ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['commande'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 54
        yield "            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</section>
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
        return "commande/historique.html.twig";
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
        return array (  191 => 54,  182 => 50,  180 => 49,  170 => 44,  164 => 41,  160 => 39,  154 => 37,  150 => 35,  148 => 34,  145 => 33,  143 => 32,  138 => 30,  134 => 29,  130 => 28,  127 => 27,  122 => 26,  100 => 6,  87 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Historique de mes commandes - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Historique de mes achats</h2>
    </div>

    <div class=\"row justify-content-center\" style=\"margin-top: 20px;\">
      <div class=\"col-md-10\">
        <div class=\"table-responsive\">
          <table class=\"table table-bordered table-hover\">
            <thead style=\"background-color: #222831; color: white;\">
              <tr>
                <th>N° Commande</th>
                <th>Date</th>
                <th>Total</th>
                <th>Statut</th>
                <th>Détails</th>
              </tr>
            </thead>
            <tbody>
              {% for commande in commandes %}
                <tr>
                  <td style=\"vertical-align: middle;\"><strong>#{{ commande.id }}</strong></td>
                  <td style=\"vertical-align: middle;\">{{ commande.dateCommande ? commande.dateCommande|date('d/m/Y H:i') : '' }}</td>
                  <td style=\"vertical-align: middle; font-weight: bold; color: #ffbe33;\">{{ commande.total }} €</td>
                  <td style=\"vertical-align: middle; font-weight: bold;\">
                    {% if commande.statut == 'validee' %}
                      <span style=\"color: green;\"><i class=\"fa fa-check-circle\"></i> Validée</span>
                    {% elseif commande.statut == 'en_attente' %}
                      <span style=\"color: orange;\"><i class=\"fa fa-clock-o\"></i> En attente</span>
                    {% else %}
                      <span style=\"color: gray;\">{{ commande.statut|capitalize }}</span>
                    {% endif %}
                  </td>
                  <td style=\"vertical-align: middle;\">
                    <a href=\"{{ path('app_commande_confirmation', {'id': commande.id}) }}\" class=\"btn btn-sm btn-info\" style=\"color: white;\">
                      <i class=\"fa fa-eye\"></i> Voir
                    </a>
                    <a href=\"{{ path('app_commande_ticket', { id: commande.id }) }}\" class=\"btn btn-sm btn-success\" title=\"Télécharger le ticket PDF\">
                      <i class=\"fa fa-file-pdf-o\"></i> PDF
                    </a>
                  </td>
                </tr>
              {% else %}
                <tr>
                  <td colspan=\"5\" class=\"text-center\">Vous n'avez passé aucune commande.</td>
                </tr>
              {% endfor %}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</section>
{% endblock %}
", "commande/historique.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\commande\\historique.html.twig");
    }
}
