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

/* produit/mes_produits.html.twig */
class __TwigTemplate_add7480a60136079a6d98f9633927405 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/mes_produits.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/mes_produits.html.twig"));

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

        yield "Statuts des produits - Pegasus";
        
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
      <h2>Statuts des produits</h2>
    </div>

    <div class=\"row justify-content-center\" style=\"margin-top: 20px;\">
      <div class=\"col-md-10\">
        <a href=\"";
        // line 14
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_new");
        yield "\" class=\"btn1\" style=\"margin-bottom: 20px; display: inline-block;\">
          <i class=\"fa fa-plus\"></i> Ajouter un nouveau produit
        </a>

        <div class=\"table-responsive\">
          <table class=\"table table-bordered table-hover\">
            <thead style=\"background-color: #222831; color: white;\">
              <tr>
                <th>Image</th>
                <th>Nom</th>
                <th>Prix</th>
                <th>Stock</th>
                <th>Statut</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              ";
        // line 31
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["produits"]) || array_key_exists("produits", $context) ? $context["produits"] : (function () { throw new RuntimeError('Variable "produits" does not exist.', 31, $this->source); })()));
        $context['_iterated'] = false;
        $context['loop'] = [
          'parent' => $context['_parent'],
          'index0' => 0,
          'index'  => 1,
          'first'  => true,
        ];
        if (is_array($context['_seq']) || (is_object($context['_seq']) && $context['_seq'] instanceof \Countable)) {
            $length = count($context['_seq']);
            $context['loop']['revindex0'] = $length - 1;
            $context['loop']['revindex'] = $length;
            $context['loop']['length'] = $length;
            $context['loop']['last'] = 1 === $length;
        }
        foreach ($context['_seq'] as $context["_key"] => $context["produit"]) {
            // line 32
            yield "                <tr>
                  <td style=\"vertical-align: middle;\">
                    ";
            // line 34
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 34)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 35
                yield "                      <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 35))), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 35), "html", null, true);
                yield "\" style=\"width: 50px; height: 50px; object-fit: cover; border-radius: 5px;\">
                    ";
            } else {
                // line 37
                yield "                      <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 37), "html", null, true);
                yield "\" style=\"width: 50px; height: 50px; object-fit: cover; border-radius: 5px;\">
                    ";
            }
            // line 39
            yield "                  </td>
                  <td style=\"vertical-align: middle;\">";
            // line 40
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 40), "html", null, true);
            yield "</td>
                  <td style=\"vertical-align: middle; font-weight: bold; color: #ffbe33;\">";
            // line 41
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "prix", [], "any", false, false, false, 41), "html", null, true);
            yield " €</td>
                  <td style=\"vertical-align: middle;\">";
            // line 42
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "stock", [], "any", false, false, false, 42), "html", null, true);
            yield "</td>
                  <td style=\"vertical-align: middle; font-weight: bold;\">
                    ";
            // line 44
            if ((CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "statut", [], "any", false, false, false, 44) == "disponible")) {
                // line 45
                yield "                      <span style=\"color: green;\"><i class=\"fa fa-check-circle\"></i> Accepté</span>
                    ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 46
$context["produit"], "statut", [], "any", false, false, false, 46) == "refuse")) {
                // line 47
                yield "                      <span style=\"color: red;\"><i class=\"fa fa-times-circle\"></i> Refusé</span>
                    ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 48
$context["produit"], "statut", [], "any", false, false, false, 48) == "en_attente")) {
                // line 49
                yield "                      <span style=\"color: orange;\"><i class=\"fa fa-clock-o\"></i> En attente</span>
                    ";
            } elseif ((CoreExtension::getAttribute($this->env, $this->source,             // line 50
$context["produit"], "statut", [], "any", false, false, false, 50) == "rupture")) {
                // line 51
                yield "                      <span style=\"color: #6c757d;\"><i class=\"fa fa-exclamation-triangle\"></i> Rupture de stock</span>
                    ";
            } else {
                // line 53
                yield "                      <span style=\"color: gray;\">";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::capitalize($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "statut", [], "any", false, false, false, 53)), "html", null, true);
                yield "</span>
                    ";
            }
            // line 55
            yield "                  </td>
                  <td style=\"vertical-align: middle;\">
                    <a href=\"";
            // line 57
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 57), "from" => "artiste"]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-secondary\" style=\"margin-right: 5px;\">
                      <i class=\"fa fa-edit\"></i> Modifier
                    </a>
                    ";
            // line 60
            yield Twig\Extension\CoreExtension::include($this->env, $context, "produit/_delete_form.html.twig", ["button_class" => "btn btn-sm btn-danger d-inline"]);
            yield "
                  </td>
                </tr>
              ";
            $context['_iterated'] = true;
            ++$context['loop']['index0'];
            ++$context['loop']['index'];
            $context['loop']['first'] = false;
            if (isset($context['loop']['revindex0'], $context['loop']['revindex'])) {
                --$context['loop']['revindex0'];
                --$context['loop']['revindex'];
                $context['loop']['last'] = 0 === $context['loop']['revindex0'];
            }
        }
        // line 63
        if (!$context['_iterated']) {
            // line 64
            yield "                <tr>
                  <td colspan=\"6\" class=\"text-center\">Vous n'avez ajouté aucun produit.</td>
                </tr>
              ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['produit'], $context['_parent'], $context['_iterated'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 68
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
        return "produit/mes_produits.html.twig";
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
        return array (  250 => 68,  241 => 64,  239 => 63,  223 => 60,  217 => 57,  213 => 55,  207 => 53,  203 => 51,  201 => 50,  198 => 49,  196 => 48,  193 => 47,  191 => 46,  188 => 45,  186 => 44,  181 => 42,  177 => 41,  173 => 40,  170 => 39,  162 => 37,  154 => 35,  152 => 34,  148 => 32,  130 => 31,  110 => 14,  100 => 6,  87 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Statuts des produits - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Statuts des produits</h2>
    </div>

    <div class=\"row justify-content-center\" style=\"margin-top: 20px;\">
      <div class=\"col-md-10\">
        <a href=\"{{ path('app_produit_new') }}\" class=\"btn1\" style=\"margin-bottom: 20px; display: inline-block;\">
          <i class=\"fa fa-plus\"></i> Ajouter un nouveau produit
        </a>

        <div class=\"table-responsive\">
          <table class=\"table table-bordered table-hover\">
            <thead style=\"background-color: #222831; color: white;\">
              <tr>
                <th>Image</th>
                <th>Nom</th>
                <th>Prix</th>
                <th>Stock</th>
                <th>Statut</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {% for produit in produits %}
                <tr>
                  <td style=\"vertical-align: middle;\">
                    {% if produit.image %}
                      <img src=\"{{ asset('uploads/images/produits/' ~ produit.image) }}\" alt=\"{{ produit.nom }}\" style=\"width: 50px; height: 50px; object-fit: cover; border-radius: 5px;\">
                    {% else %}
                      <img src=\"{{ asset('front/images/f1.png') }}\" alt=\"{{ produit.nom }}\" style=\"width: 50px; height: 50px; object-fit: cover; border-radius: 5px;\">
                    {% endif %}
                  </td>
                  <td style=\"vertical-align: middle;\">{{ produit.nom }}</td>
                  <td style=\"vertical-align: middle; font-weight: bold; color: #ffbe33;\">{{ produit.prix }} €</td>
                  <td style=\"vertical-align: middle;\">{{ produit.stock }}</td>
                  <td style=\"vertical-align: middle; font-weight: bold;\">
                    {% if produit.statut == 'disponible' %}
                      <span style=\"color: green;\"><i class=\"fa fa-check-circle\"></i> Accepté</span>
                    {% elseif produit.statut == 'refuse' %}
                      <span style=\"color: red;\"><i class=\"fa fa-times-circle\"></i> Refusé</span>
                    {% elseif produit.statut == 'en_attente' %}
                      <span style=\"color: orange;\"><i class=\"fa fa-clock-o\"></i> En attente</span>
                    {% elseif produit.statut == 'rupture' %}
                      <span style=\"color: #6c757d;\"><i class=\"fa fa-exclamation-triangle\"></i> Rupture de stock</span>
                    {% else %}
                      <span style=\"color: gray;\">{{ produit.statut|capitalize }}</span>
                    {% endif %}
                  </td>
                  <td style=\"vertical-align: middle;\">
                    <a href=\"{{ path('app_produit_edit', {'id': produit.id, 'from': 'artiste'}) }}\" class=\"btn btn-sm btn-secondary\" style=\"margin-right: 5px;\">
                      <i class=\"fa fa-edit\"></i> Modifier
                    </a>
                    {{ include('produit/_delete_form.html.twig', {'button_class': 'btn btn-sm btn-danger d-inline'}) }}
                  </td>
                </tr>
              {% else %}
                <tr>
                  <td colspan=\"6\" class=\"text-center\">Vous n'avez ajouté aucun produit.</td>
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
", "produit/mes_produits.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\produit\\mes_produits.html.twig");
    }
}
