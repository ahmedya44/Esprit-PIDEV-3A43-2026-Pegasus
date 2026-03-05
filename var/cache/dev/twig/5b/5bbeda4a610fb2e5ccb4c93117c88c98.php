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

/* panier/index.html.twig */
class __TwigTemplate_6a992467118d3f4ad7c66d05d0d15fdf extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "panier/index.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "panier/index.html.twig"));

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

        yield "Mon Panier - Pegasus";
        
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
      <h2>Mon Panier</h2>
    </div>

    ";
        // line 12
        if (((isset($context["panier"]) || array_key_exists("panier", $context) ? $context["panier"] : (function () { throw new RuntimeError('Variable "panier" does not exist.', 12, $this->source); })()) && (Twig\Extension\CoreExtension::length($this->env->getCharset(), (isset($context["lignes"]) || array_key_exists("lignes", $context) ? $context["lignes"] : (function () { throw new RuntimeError('Variable "lignes" does not exist.', 12, $this->source); })())) > 0))) {
            // line 13
            yield "      <div class=\"row mt-4\">
        <div class=\"col-md-8\">
          <table class=\"table\">
            <thead>
              <tr>
                <th>Produit</th>
                <th>Prix unitaire</th>
                <th>Quantité</th>
                <th>Sous-total</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              ";
            // line 26
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable((isset($context["lignes"]) || array_key_exists("lignes", $context) ? $context["lignes"] : (function () { throw new RuntimeError('Variable "lignes" does not exist.', 26, $this->source); })()));
            foreach ($context['_seq'] as $context["_key"] => $context["ligne"]) {
                // line 27
                yield "                <tr>
                  <td>
                    ";
                // line 29
                if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 29), "image", [], "any", false, false, false, 29)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                    // line 30
                    yield "                      <img src=\"";
                    yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 30), "image", [], "any", false, false, false, 30))), "html", null, true);
                    yield "\" alt=\"";
                    yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 30), "nom", [], "any", false, false, false, 30), "html", null, true);
                    yield "\" style=\"width: 60px; height: 60px; object-fit: cover; border-radius: 5px;\">
                    ";
                }
                // line 32
                yield "                    ";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 32), "nom", [], "any", false, false, false, 32), "html", null, true);
                yield "
                  </td>
                  <td>";
                // line 34
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "prixUnitaire", [], "any", false, false, false, 34), "html", null, true);
                yield " €</td>
                  <td>";
                // line 35
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "quantite", [], "any", false, false, false, 35), "html", null, true);
                yield "</td>
                  <td>";
                // line 36
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "prixUnitaire", [], "any", false, false, false, 36) * CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "quantite", [], "any", false, false, false, 36)), "html", null, true);
                yield " €</td>
                  <td>
                    <form method=\"POST\" action=\"";
                // line 38
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_supprimer", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "id", [], "any", false, false, false, 38)]), "html", null, true);
                yield "\">
                      <input type=\"hidden\" name=\"_token\" value=\"";
                // line 39
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken("panier"), "html", null, true);
                yield "\">
                      <button type=\"submit\" class=\"btn btn-danger btn-sm\">
                        <i class=\"fa fa-trash\"></i>
                      </button>
                    </form>
                  </td>
                </tr>
              ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_key'], $context['ligne'], $context['_parent']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 47
            yield "            </tbody>
          </table>
        </div>

        <div class=\"col-md-4\">
          <div class=\"box\" style=\"padding: 20px;\">
            <h4>Récapitulatif</h4>
            <hr>
            <div class=\"d-flex justify-content-between\">
              <span>Total :</span>
              <strong>";
            // line 57
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["panier"]) || array_key_exists("panier", $context) ? $context["panier"] : (function () { throw new RuntimeError('Variable "panier" does not exist.', 57, $this->source); })()), "total", [], "any", false, false, false, 57), "html", null, true);
            yield " €</strong>
            </div>
            <hr>
            <a href=\"";
            // line 60
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn btn-secondary w-100 mb-2\">
              Continuer mes achats
            </a>
            <form action=\"";
            // line 63
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_payment_checkout");
            yield "\" method=\"POST\">
              <button type=\"submit\" class=\"btn w-100 mb-2\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\">
                <i class=\"fa fa-credit-card\"></i> Payer avec Stripe
              </button>
            </form>
            <form method=\"POST\" action=\"";
            // line 68
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_vider");
            yield "\">
              <input type=\"hidden\" name=\"_token\" value=\"";
            // line 69
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken("panier"), "html", null, true);
            yield "\">
              <button type=\"submit\" class=\"btn btn-danger w-100\">
                Vider le panier
              </button>
            </form>
          </div>
        </div>
      </div>

    ";
        } else {
            // line 79
            yield "      <div class=\"text-center mt-5\">
        <i class=\"fa fa-shopping-cart\" style=\"font-size: 5rem; color: #ffbe33;\"></i>
        <h4 class=\"mt-3\">Votre panier est vide !</h4>
        <a href=\"";
            // line 82
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn mt-3\" style=\"background-color: #ffbe33; border: none; font-weight: bold;\">
          Voir les produits
        </a>
      </div>
    ";
        }
        // line 87
        yield "
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
        return "panier/index.html.twig";
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
        return array (  242 => 87,  234 => 82,  229 => 79,  216 => 69,  212 => 68,  204 => 63,  198 => 60,  192 => 57,  180 => 47,  166 => 39,  162 => 38,  157 => 36,  153 => 35,  149 => 34,  143 => 32,  135 => 30,  133 => 29,  129 => 27,  125 => 26,  110 => 13,  108 => 12,  100 => 6,  87 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Mon Panier - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Mon Panier</h2>
    </div>

    {% if panier and lignes|length > 0 %}
      <div class=\"row mt-4\">
        <div class=\"col-md-8\">
          <table class=\"table\">
            <thead>
              <tr>
                <th>Produit</th>
                <th>Prix unitaire</th>
                <th>Quantité</th>
                <th>Sous-total</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {% for ligne in lignes %}
                <tr>
                  <td>
                    {% if ligne.produit.image %}
                      <img src=\"{{ asset('uploads/images/produits/' ~ ligne.produit.image) }}\" alt=\"{{ ligne.produit.nom }}\" style=\"width: 60px; height: 60px; object-fit: cover; border-radius: 5px;\">
                    {% endif %}
                    {{ ligne.produit.nom }}
                  </td>
                  <td>{{ ligne.prixUnitaire }} €</td>
                  <td>{{ ligne.quantite }}</td>
                  <td>{{ ligne.prixUnitaire * ligne.quantite }} €</td>
                  <td>
                    <form method=\"POST\" action=\"{{ path('app_panier_supprimer', {'id': ligne.id}) }}\">
                      <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('panier') }}\">
                      <button type=\"submit\" class=\"btn btn-danger btn-sm\">
                        <i class=\"fa fa-trash\"></i>
                      </button>
                    </form>
                  </td>
                </tr>
              {% endfor %}
            </tbody>
          </table>
        </div>

        <div class=\"col-md-4\">
          <div class=\"box\" style=\"padding: 20px;\">
            <h4>Récapitulatif</h4>
            <hr>
            <div class=\"d-flex justify-content-between\">
              <span>Total :</span>
              <strong>{{ panier.total }} €</strong>
            </div>
            <hr>
            <a href=\"{{ path('app_produit_index') }}\" class=\"btn btn-secondary w-100 mb-2\">
              Continuer mes achats
            </a>
            <form action=\"{{ path('app_payment_checkout') }}\" method=\"POST\">
              <button type=\"submit\" class=\"btn w-100 mb-2\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\">
                <i class=\"fa fa-credit-card\"></i> Payer avec Stripe
              </button>
            </form>
            <form method=\"POST\" action=\"{{ path('app_panier_vider') }}\">
              <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('panier') }}\">
              <button type=\"submit\" class=\"btn btn-danger w-100\">
                Vider le panier
              </button>
            </form>
          </div>
        </div>
      </div>

    {% else %}
      <div class=\"text-center mt-5\">
        <i class=\"fa fa-shopping-cart\" style=\"font-size: 5rem; color: #ffbe33;\"></i>
        <h4 class=\"mt-3\">Votre panier est vide !</h4>
        <a href=\"{{ path('app_produit_index') }}\" class=\"btn mt-3\" style=\"background-color: #ffbe33; border: none; font-weight: bold;\">
          Voir les produits
        </a>
      </div>
    {% endif %}

  </div>
</section>
{% endblock %}", "panier/index.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\panier\\index.html.twig");
    }
}
