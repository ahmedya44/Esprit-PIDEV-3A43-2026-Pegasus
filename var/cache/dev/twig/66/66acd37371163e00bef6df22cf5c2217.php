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

/* favoris/index.html.twig */
class __TwigTemplate_8103924cf3521f6a99b5ee98b148426f extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "favoris/index.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "favoris/index.html.twig"));

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

        yield "Mes Favoris - Pegasus";
        
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
        yield "<section class=\"food_section layout_padding\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Mes Favoris</h2>
    </div>

    <div class=\"row mt-5\">
      <div class=\"col-md-10 offset-md-1\">
        ";
        // line 14
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 14, $this->source); })()), "flashes", ["success"], "method", false, false, false, 14));
        foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
            // line 15
            yield "          <div class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">
            ";
            // line 16
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
        // line 22
        yield "
        <div class=\"card shadow-sm border-0\" style=\"border-radius: 15px;\">
          <div class=\"card-body p-0\">
            <div class=\"table-responsive\">
              <table class=\"table table-hover align-middle mb-0\">
                <thead class=\"bg-light\">
                  <tr>
                    <th class=\"border-0 px-4 py-3\">Produit</th>
                    <th class=\"border-0 py-3\">Prix</th>
                    <th class=\"border-0 py-3 text-center\">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  ";
        // line 35
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["produits"]) || array_key_exists("produits", $context) ? $context["produits"] : (function () { throw new RuntimeError('Variable "produits" does not exist.', 35, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["produit"]) {
            // line 36
            yield "                    <tr>
                      <td class=\"px-4 py-3\">
                        <div class=\"d-flex align-items-center\">
                          <div class=\"mr-3\" style=\"width: 70px; height: 70px; overflow: hidden; border-radius: 10px;\">
                            ";
            // line 40
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 40)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 41
                yield "                              <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 41))), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 41), "html", null, true);
                yield "\" style=\"width: 100%; height: 100%; object-fit: cover;\">
                            ";
            } else {
                // line 43
                yield "                              <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 43), "html", null, true);
                yield "\" style=\"width: 100%; height: 100%; object-fit: cover;\">
                            ";
            }
            // line 45
            yield "                          </div>
                          <div>
                            <h6 class=\"mb-1 font-weight-bold\">";
            // line 47
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 47), "html", null, true);
            yield "</h6>
                            <small class=\"text-muted\">";
            // line 48
            yield (((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 48)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ($this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 48), "nom", [], "any", false, false, false, 48), "html", null, true)) : ("Pas de catégorie"));
            yield "</small>
                          </div>
                        </div>
                      </td>
                      <td class=\"py-3 font-weight-bold\" style=\"color: #ffbe33;\">";
            // line 52
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "prix", [], "any", false, false, false, 52), "html", null, true);
            yield " €</td>
                      <td class=\"py-3 text-center\">
                        <div class=\"btn-group\">
                          <a href=\"";
            // line 55
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_show", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 55)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-outline-info mr-2\" title=\"Voir\">
                            <i class=\"fa fa-eye\"></i>
                          </a>
                          <form action=\"";
            // line 58
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_ajouter", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 58)]), "html", null, true);
            yield "\" method=\"post\" style=\"display:inline-block;\">
                            <button type=\"submit\" class=\"btn btn-sm btn-warning mr-2\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\" title=\"Ajouter au panier\">
                              <i class=\"fa fa-shopping-cart\"></i>
                            </button>
                          </form>
                          <form action=\"";
            // line 63
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_favoris_supprimer", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 63)]), "html", null, true);
            yield "\" method=\"post\" style=\"display:inline-block;\" onsubmit=\"return confirm('Retirer des favoris ?');\">
                            <button type=\"submit\" class=\"btn btn-sm btn-outline-danger\" title=\"Retirer des favoris\">
                              <i class=\"fa fa-trash\"></i>
                            </button>
                          </form>
                        </div>
                      </td>
                    </tr>
                  ";
            $context['_iterated'] = true;
        }
        // line 71
        if (!$context['_iterated']) {
            // line 72
            yield "                    <tr>
                      <td colspan=\"3\" class=\"text-center py-5\">
                        <div class=\"mb-3\">
                          <i class=\"fa fa-heart-o fa-4x text-muted\" style=\"opacity: 0.3;\"></i>
                        </div>
                        <h5 class=\"text-muted\">Votre liste de favoris est vide</h5>
                        <p class=\"text-muted small\">Parcourez notre catalogue pour ajouter vos produits préférés !</p>
                        <a href=\"";
            // line 79
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn btn-warning mt-3\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000; border-radius: 30px; padding: 10px 25px;\">
                          Découvrir les produits
                        </a>
                      </td>
                    </tr>
                  ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['produit'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 85
        yield "                </tbody>
              </table>
            </div>
          </div>
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
        return "favoris/index.html.twig";
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
        return array (  244 => 85,  232 => 79,  223 => 72,  221 => 71,  208 => 63,  200 => 58,  194 => 55,  188 => 52,  181 => 48,  177 => 47,  173 => 45,  165 => 43,  157 => 41,  155 => 40,  149 => 36,  144 => 35,  129 => 22,  117 => 16,  114 => 15,  110 => 14,  100 => 6,  87 => 5,  64 => 3,  41 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Mes Favoris - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Mes Favoris</h2>
    </div>

    <div class=\"row mt-5\">
      <div class=\"col-md-10 offset-md-1\">
        {% for message in app.flashes('success') %}
          <div class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">
            {{ message }}
            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">
              <span aria-hidden=\"true\">&times;</span>
            </button>
          </div>
        {% endfor %}

        <div class=\"card shadow-sm border-0\" style=\"border-radius: 15px;\">
          <div class=\"card-body p-0\">
            <div class=\"table-responsive\">
              <table class=\"table table-hover align-middle mb-0\">
                <thead class=\"bg-light\">
                  <tr>
                    <th class=\"border-0 px-4 py-3\">Produit</th>
                    <th class=\"border-0 py-3\">Prix</th>
                    <th class=\"border-0 py-3 text-center\">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {% for produit in produits %}
                    <tr>
                      <td class=\"px-4 py-3\">
                        <div class=\"d-flex align-items-center\">
                          <div class=\"mr-3\" style=\"width: 70px; height: 70px; overflow: hidden; border-radius: 10px;\">
                            {% if produit.image %}
                              <img src=\"{{ asset('uploads/images/produits/' ~ produit.image) }}\" alt=\"{{ produit.nom }}\" style=\"width: 100%; height: 100%; object-fit: cover;\">
                            {% else %}
                              <img src=\"{{ asset('front/images/f1.png') }}\" alt=\"{{ produit.nom }}\" style=\"width: 100%; height: 100%; object-fit: cover;\">
                            {% endif %}
                          </div>
                          <div>
                            <h6 class=\"mb-1 font-weight-bold\">{{ produit.nom }}</h6>
                            <small class=\"text-muted\">{{ produit.categorie ? produit.categorie.nom : 'Pas de catégorie' }}</small>
                          </div>
                        </div>
                      </td>
                      <td class=\"py-3 font-weight-bold\" style=\"color: #ffbe33;\">{{ produit.prix }} €</td>
                      <td class=\"py-3 text-center\">
                        <div class=\"btn-group\">
                          <a href=\"{{ path('app_produit_show', {'id': produit.id}) }}\" class=\"btn btn-sm btn-outline-info mr-2\" title=\"Voir\">
                            <i class=\"fa fa-eye\"></i>
                          </a>
                          <form action=\"{{ path('app_panier_ajouter', {'id': produit.id}) }}\" method=\"post\" style=\"display:inline-block;\">
                            <button type=\"submit\" class=\"btn btn-sm btn-warning mr-2\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\" title=\"Ajouter au panier\">
                              <i class=\"fa fa-shopping-cart\"></i>
                            </button>
                          </form>
                          <form action=\"{{ path('app_favoris_supprimer', {'id': produit.id}) }}\" method=\"post\" style=\"display:inline-block;\" onsubmit=\"return confirm('Retirer des favoris ?');\">
                            <button type=\"submit\" class=\"btn btn-sm btn-outline-danger\" title=\"Retirer des favoris\">
                              <i class=\"fa fa-trash\"></i>
                            </button>
                          </form>
                        </div>
                      </td>
                    </tr>
                  {% else %}
                    <tr>
                      <td colspan=\"3\" class=\"text-center py-5\">
                        <div class=\"mb-3\">
                          <i class=\"fa fa-heart-o fa-4x text-muted\" style=\"opacity: 0.3;\"></i>
                        </div>
                        <h5 class=\"text-muted\">Votre liste de favoris est vide</h5>
                        <p class=\"text-muted small\">Parcourez notre catalogue pour ajouter vos produits préférés !</p>
                        <a href=\"{{ path('app_produit_index') }}\" class=\"btn btn-warning mt-3\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000; border-radius: 30px; padding: 10px 25px;\">
                          Découvrir les produits
                        </a>
                      </td>
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
</section>
{% endblock %}
", "favoris/index.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\favoris\\index.html.twig");
    }
}
