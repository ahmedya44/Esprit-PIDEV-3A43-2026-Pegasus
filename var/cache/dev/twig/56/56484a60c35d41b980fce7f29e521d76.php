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

/* produit/show.html.twig */
class __TwigTemplate_4a21316e6d3bd414312e561daa0e78b9 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/show.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/show.html.twig"));

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

        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 3, $this->source); })()), "nom", [], "any", false, false, false, 3), "html", null, true);
        yield " - Pegasus";
        
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
    <div class=\"row\">
      <div class=\"col-md-6\">
        <div class=\"img-box\">
          ";
        // line 11
        if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 11, $this->source); })()), "image", [], "any", false, false, false, 11)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 12
            yield "            <img src=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 12, $this->source); })()), "image", [], "any", false, false, false, 12))), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 12, $this->source); })()), "nom", [], "any", false, false, false, 12), "html", null, true);
            yield "\" style=\"width: 100%; border-radius: 10px;\">
          ";
        } else {
            // line 14
            yield "            <img src=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 14, $this->source); })()), "nom", [], "any", false, false, false, 14), "html", null, true);
            yield "\" style=\"width: 100%; border-radius: 10px;\">
          ";
        }
        // line 16
        yield "        </div>
      </div>
      <div class=\"col-md-6\">
        <div class=\"detail-box\">
          <h2>";
        // line 20
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 20, $this->source); })()), "nom", [], "any", false, false, false, 20), "html", null, true);
        yield "</h2>
          <p>";
        // line 21
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 21, $this->source); })()), "description", [], "any", false, false, false, 21), "html", null, true);
        yield "</p>
          <h4 style=\"color: #ffbe33;\">";
        // line 22
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 22, $this->source); })()), "prix", [], "any", false, false, false, 22), "html", null, true);
        yield " €</h4>
          <p>Stock : ";
        // line 23
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 23, $this->source); })()), "stock", [], "any", false, false, false, 23), "html", null, true);
        yield "</p>
          ";
        // line 24
        if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 24, $this->source); })()), "categorie", [], "any", false, false, false, 24)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 25
            yield "            <p>Catégorie : ";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 25, $this->source); })()), "categorie", [], "any", false, false, false, 25), "nom", [], "any", false, false, false, 25), "html", null, true);
            yield "</p>
          ";
        }
        // line 27
        yield "          <p>
            Statut : 
            ";
        // line 29
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 29, $this->source); })()), "statut", [], "any", false, false, false, 29) == "disponible")) {
            // line 30
            yield "              <span style=\"color: green; font-weight: bold;\">Disponible</span>
            ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 31
(isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 31, $this->source); })()), "statut", [], "any", false, false, false, 31) == "rupture")) {
            // line 32
            yield "              <span style=\"color: red; font-weight: bold;\">Rupture de stock</span>
            ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 33
(isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 33, $this->source); })()), "statut", [], "any", false, false, false, 33) == "bientot")) {
            // line 34
            yield "              <span style=\"color: orange; font-weight: bold;\">Bientôt disponible</span>
            ";
        } else {
            // line 36
            yield "              <span style=\"color: grey; font-weight: bold;\">Archivé</span>
            ";
        }
        // line 38
        yield "          </p>

          ";
        // line 40
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 40, $this->source); })()), "statut", [], "any", false, false, false, 40) == "disponible")) {
            // line 41
            yield "            ";
            if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 42
                yield "              <form method=\"POST\" action=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_ajouter", ["id" => CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 42, $this->source); })()), "id", [], "any", false, false, false, 42)]), "html", null, true);
                yield "\" style=\"margin-top: 20px;\">
                <input type=\"hidden\" name=\"_token\" value=\"";
                // line 43
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken("panier"), "html", null, true);
                yield "\">
                <div class=\"d-flex align-items-center\" style=\"gap: 10px;\">
                  <input type=\"number\" name=\"quantite\" value=\"1\" min=\"1\" max=\"";
                // line 45
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 45, $this->source); })()), "stock", [], "any", false, false, false, 45), "html", null, true);
                yield "\" 
                         class=\"form-control\" style=\"width: 80px;\">
                  <button type=\"submit\" class=\"btn\" style=\"background-color: #ffbe33; border: none; font-weight: bold; padding: 10px 20px;\">
                    <i class=\"fa fa-shopping-cart\"></i> Ajouter au panier
                  </button>
                </div>
              </form>
            ";
            }
            // line 53
            yield "          ";
        }
        // line 54
        yield "
          <div style=\"margin-top: 20px;\">
            <a href=\"";
        // line 56
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
        yield "\" class=\"btn1\">Retour à la liste</a>
            ";
        // line 57
        if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 58
            yield "              <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_index");
            yield "\" class=\"btn1\" style=\"margin-left: 10px;\">
                <i class=\"fa fa-shopping-cart\"></i> Voir le panier
              </a>
            ";
        }
        // line 62
        yield "          </div>

          <div style=\"margin-top: 10px;\">
            ";
        // line 65
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 66
            yield "              <a href=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 66, $this->source); })()), "id", [], "any", false, false, false, 66)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-secondary\">Modifier</a>
              ";
            // line 67
            yield Twig\Extension\CoreExtension::include($this->env, $context, "produit/_delete_form.html.twig");
            yield "
            ";
        }
        // line 69
        yield "          </div>
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
        return "produit/show.html.twig";
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
        return array (  248 => 69,  243 => 67,  238 => 66,  236 => 65,  231 => 62,  223 => 58,  221 => 57,  217 => 56,  213 => 54,  210 => 53,  199 => 45,  194 => 43,  189 => 42,  186 => 41,  184 => 40,  180 => 38,  176 => 36,  172 => 34,  170 => 33,  167 => 32,  165 => 31,  162 => 30,  160 => 29,  156 => 27,  150 => 25,  148 => 24,  144 => 23,  140 => 22,  136 => 21,  132 => 20,  126 => 16,  118 => 14,  110 => 12,  108 => 11,  101 => 6,  88 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}{{ produit.nom }} - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"row\">
      <div class=\"col-md-6\">
        <div class=\"img-box\">
          {% if produit.image %}
            <img src=\"{{ asset('uploads/images/produits/' ~ produit.image) }}\" alt=\"{{ produit.nom }}\" style=\"width: 100%; border-radius: 10px;\">
          {% else %}
            <img src=\"{{ asset('front/images/f1.png') }}\" alt=\"{{ produit.nom }}\" style=\"width: 100%; border-radius: 10px;\">
          {% endif %}
        </div>
      </div>
      <div class=\"col-md-6\">
        <div class=\"detail-box\">
          <h2>{{ produit.nom }}</h2>
          <p>{{ produit.description }}</p>
          <h4 style=\"color: #ffbe33;\">{{ produit.prix }} €</h4>
          <p>Stock : {{ produit.stock }}</p>
          {% if produit.categorie %}
            <p>Catégorie : {{ produit.categorie.nom }}</p>
          {% endif %}
          <p>
            Statut : 
            {% if produit.statut == 'disponible' %}
              <span style=\"color: green; font-weight: bold;\">Disponible</span>
            {% elseif produit.statut == 'rupture' %}
              <span style=\"color: red; font-weight: bold;\">Rupture de stock</span>
            {% elseif produit.statut == 'bientot' %}
              <span style=\"color: orange; font-weight: bold;\">Bientôt disponible</span>
            {% else %}
              <span style=\"color: grey; font-weight: bold;\">Archivé</span>
            {% endif %}
          </p>

          {% if produit.statut == 'disponible' %}
            {% if not is_granted('ROLE_ARTISTE') %}
              <form method=\"POST\" action=\"{{ path('app_panier_ajouter', {'id': produit.id}) }}\" style=\"margin-top: 20px;\">
                <input type=\"hidden\" name=\"_token\" value=\"{{ csrf_token('panier') }}\">
                <div class=\"d-flex align-items-center\" style=\"gap: 10px;\">
                  <input type=\"number\" name=\"quantite\" value=\"1\" min=\"1\" max=\"{{ produit.stock }}\" 
                         class=\"form-control\" style=\"width: 80px;\">
                  <button type=\"submit\" class=\"btn\" style=\"background-color: #ffbe33; border: none; font-weight: bold; padding: 10px 20px;\">
                    <i class=\"fa fa-shopping-cart\"></i> Ajouter au panier
                  </button>
                </div>
              </form>
            {% endif %}
          {% endif %}

          <div style=\"margin-top: 20px;\">
            <a href=\"{{ path('app_produit_index') }}\" class=\"btn1\">Retour à la liste</a>
            {% if not is_granted('ROLE_ARTISTE') %}
              <a href=\"{{ path('app_panier_index') }}\" class=\"btn1\" style=\"margin-left: 10px;\">
                <i class=\"fa fa-shopping-cart\"></i> Voir le panier
              </a>
            {% endif %}
          </div>

          <div style=\"margin-top: 10px;\">
            {% if is_granted('ROLE_ARTISTE') %}
              <a href=\"{{ path('app_produit_edit', {'id': produit.id}) }}\" class=\"btn btn-sm btn-secondary\">Modifier</a>
              {{ include('produit/_delete_form.html.twig') }}
            {% endif %}
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
{% endblock %}", "produit/show.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\produit\\show.html.twig");
    }
}
