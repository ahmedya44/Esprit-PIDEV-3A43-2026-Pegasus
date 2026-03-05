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

/* produit/index.html.twig */
class __TwigTemplate_333b84c2a854f41aab1a4b96cac26f5e extends Template
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
            'javascripts' => [$this, 'block_javascripts'],
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/index.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "produit/index.html.twig"));

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

        yield "Nos Produits - Pegasus";
        
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
      <h2>Nos Produits</h2>
    </div>

    <!-- Barre de recherche et filtres -->
    <div class=\"row mt-4 mb-4\">
      <div class=\"col-12\">
        ";
        // line 15
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 16
            yield "          <div class=\"mb-3 text-right\">
            <a href=\"";
            // line 17
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_new");
            yield "\" class=\"btn btn-primary\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\">
              <i class=\"fa fa-plus\"></i> Ajouter un produit
            </a>
          </div>
        ";
        }
        // line 22
        yield "        <form method=\"GET\" action=\"";
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
        yield "\">
          <div class=\"row g-2\">
            <div class=\"col-md-5\">
              <input type=\"text\" name=\"search\" value=\"";
        // line 25
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 25, $this->source); })()), "html", null, true);
        yield "\" 
                     class=\"form-control\" placeholder=\"Rechercher un produit...\">
            </div>
            <div class=\"col-md-3\">
              <select name=\"categorie\" class=\"form-control\">
                <option value=\"\">Toutes les catégories</option>
                ";
        // line 31
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["categories"]) || array_key_exists("categories", $context) ? $context["categories"] : (function () { throw new RuntimeError('Variable "categories" does not exist.', 31, $this->source); })()));
        foreach ($context['_seq'] as $context["_key"] => $context["categorie"]) {
            // line 32
            yield "                  <option value=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "id", [], "any", false, false, false, 32), "html", null, true);
            yield "\" ";
            if (((isset($context["categorieId"]) || array_key_exists("categorieId", $context) ? $context["categorieId"] : (function () { throw new RuntimeError('Variable "categorieId" does not exist.', 32, $this->source); })()) == (CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "id", [], "any", false, false, false, 32) . ""))) {
                yield "selected";
            }
            yield ">
                    ";
            // line 33
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "nom", [], "any", false, false, false, 33), "html", null, true);
            yield "
                  </option>
                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['categorie'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 36
        yield "              </select>
            </div>
            <div class=\"col-md-3\">
              <select name=\"tri\" class=\"form-control\">
                <option value=\"\">Trier par...</option>
                <option value=\"prix_asc\" ";
        // line 41
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 41, $this->source); })()) == "prix_asc")) {
            yield "selected";
        }
        yield ">Prix croissant</option>
                <option value=\"prix_desc\" ";
        // line 42
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 42, $this->source); })()) == "prix_desc")) {
            yield "selected";
        }
        yield ">Prix décroissant</option>
                <option value=\"nom_asc\" ";
        // line 43
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 43, $this->source); })()) == "nom_asc")) {
            yield "selected";
        }
        yield ">Nom A-Z</option>
                <option value=\"nom_desc\" ";
        // line 44
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 44, $this->source); })()) == "nom_desc")) {
            yield "selected";
        }
        yield ">Nom Z-A</option>
              </select>
            </div>
            <div class=\"col-md-1\">
              <button type=\"submit\" class=\"btn btn-warning w-100\" style=\"background-color: #ffbe33; border:none;\">
                <i class=\"fa fa-search\"></i>
              </button>
            </div>
          </div>
          ";
        // line 53
        if ((((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 53, $this->source); })()) || (isset($context["categorieId"]) || array_key_exists("categorieId", $context) ? $context["categorieId"] : (function () { throw new RuntimeError('Variable "categorieId" does not exist.', 53, $this->source); })())) || (isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 53, $this->source); })()))) {
            // line 54
            yield "            <div class=\"mt-2\">
              <a href=\"";
            // line 55
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn btn-sm btn-secondary\">
                Réinitialiser les filtres
              </a>
            </div>
          ";
        }
        // line 60
        yield "        </form>
      </div>
    </div>

    <!-- Liste des produits -->
    <div class=\"row grid\">
      ";
        // line 66
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["produits"]) || array_key_exists("produits", $context) ? $context["produits"] : (function () { throw new RuntimeError('Variable "produits" does not exist.', 66, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["produit"]) {
            // line 67
            yield "        <div class=\"col-sm-6 col-lg-4\">
          <div class=\"box\">
            <div>
              <div class=\"img-box\">
                ";
            // line 71
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 71)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 72
                yield "                  <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 72))), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 72), "html", null, true);
                yield "\">
                ";
            } else {
                // line 74
                yield "                  <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 74), "html", null, true);
                yield "\">
                ";
            }
            // line 76
            yield "              </div>
              <div class=\"detail-box\">
                <h5>";
            // line 78
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 78), "html", null, true);
            yield "</h5>
                <p>";
            // line 79
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 79), 0, 80), "html", null, true);
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 79)) > 80)) {
                yield "...";
            }
            yield "</p>
                ";
            // line 80
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 80)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 81
                yield "                  <span class=\"badge\" style=\"background-color: #ffbe33; color: #000; font-size: 0.75rem;\">
                    ";
                // line 82
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 82), "nom", [], "any", false, false, false, 82), "html", null, true);
                yield "
                  </span>
                ";
            }
            // line 85
            yield "                <div class=\"options\">
                  <h6>";
            // line 86
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "prix", [], "any", false, false, false, 86), "html", null, true);
            yield " €</h6>
                  <div class=\"d-flex align-items-center\">
                    <a href=\"";
            // line 88
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_show", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 88)]), "html", null, true);
            yield "\" class=\"mr-2\" title=\"Voir le produit\">
                      <i class=\"fa fa-eye\"></i>
                    </a>
                    ";
            // line 91
            if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 92
                yield "                      <a href=\"javascript:void(0);\" 
                         class=\"btn-favorite\" 
                         data-id=\"";
                // line 94
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 94), "html", null, true);
                yield "\" 
                         title=\"Ajouter aux favoris\">
                        <i class=\"fa ";
                // line 96
                yield ((CoreExtension::inFilter(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 96), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 96, $this->source); })()), "session", [], "any", false, false, false, 96), "get", ["favoris", []], "method", false, false, false, 96))) ? ("fa-heart") : ("fa-heart-o"));
                yield "\" 
                           style=\"color: ";
                // line 97
                yield ((CoreExtension::inFilter(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 97), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 97, $this->source); })()), "session", [], "any", false, false, false, 97), "get", ["favoris", []], "method", false, false, false, 97))) ? ("#dc3545") : ("#000"));
                yield "; font-size: 1.2rem;\"></i>
                      </a>
                    ";
            }
            // line 100
            yield "                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      ";
            $context['_iterated'] = true;
        }
        // line 106
        if (!$context['_iterated']) {
            // line 107
            yield "        <div class=\"col-12 text-center\">
          <p>Aucun produit trouvé.</p>
          <a href=\"";
            // line 109
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn1\">Voir tous les produits</a>
        </div>
      ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['produit'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 112
        yield "    </div>

  </div>
</section>
";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 118
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_javascripts(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "javascripts"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "javascripts"));

        // line 119
        yield "  <script>
    // Recherche automatique après 500ms de pause
    document.querySelector('input[name=\"search\"]').addEventListener('input', function() {
      clearTimeout(this.timer);
      this.timer = setTimeout(function() {
        document.querySelector('form').submit();
      }, 500);
    });

    // Filtre catégorie automatique
    document.querySelector('select[name=\"categorie\"]').addEventListener('change', function() {
      document.querySelector('form').submit();
    });

    // Tri automatique
    document.querySelector('select[name=\"tri\"]').addEventListener('change', function() {
      document.querySelector('form').submit();
    });

    // Gestion des favoris via AJAX
    document.querySelectorAll('.btn-favorite').forEach(btn => {
      btn.addEventListener('click', function() {
        const id = this.dataset.id;
        const icon = this.querySelector('i');
        
        // On effectue l'appel AJAX
        fetch(`/favoris/toggle/\${id}`, {
          method: 'POST',
          headers: {
            'X-Requested-With': 'XMLHttpRequest'
          }
        })
        .then(response => response.json())
        .then(data => {
          if (data.isFavorite) {
            icon.classList.remove('fa-heart-o');
            icon.classList.add('fa-heart');
            icon.style.color = '#dc3545'; // Rouge
          } else {
            icon.classList.remove('fa-heart');
            icon.classList.add('fa-heart-o');
            icon.style.color = '#000'; // Noir
          }

          // Mise à jour du badge dans la navbar
          const badge = document.getElementById('favoris-count');
          if (data.count > 0) {
            if (badge) {
              badge.innerText = data.count;
            } else {
              // Créer le badge s'il n'existe pas encore
              const cartLink = document.querySelector('a[title=\"Mes Favoris\"]');
              const newBadge = document.createElement('span');
              newBadge.id = 'favoris-count';
              newBadge.className = 'badge badge-pill badge-danger';
              newBadge.style = 'position: absolute; top: -10px; right: -10px; font-size: 10px;';
              newBadge.innerText = data.count;
              cartLink.appendChild(newBadge);
            }
          } else if (badge) {
            badge.remove();
          }
        });
      });
    });
  </script>
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
        return "produit/index.html.twig";
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
        return array (  366 => 119,  353 => 118,  338 => 112,  329 => 109,  325 => 107,  323 => 106,  313 => 100,  307 => 97,  303 => 96,  298 => 94,  294 => 92,  292 => 91,  286 => 88,  281 => 86,  278 => 85,  272 => 82,  269 => 81,  267 => 80,  260 => 79,  256 => 78,  252 => 76,  244 => 74,  236 => 72,  234 => 71,  228 => 67,  223 => 66,  215 => 60,  207 => 55,  204 => 54,  202 => 53,  188 => 44,  182 => 43,  176 => 42,  170 => 41,  163 => 36,  154 => 33,  145 => 32,  141 => 31,  132 => 25,  125 => 22,  117 => 17,  114 => 16,  112 => 15,  101 => 6,  88 => 5,  65 => 3,  42 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Nos Produits - Pegasus{% endblock %}

{% block body %}
<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Nos Produits</h2>
    </div>

    <!-- Barre de recherche et filtres -->
    <div class=\"row mt-4 mb-4\">
      <div class=\"col-12\">
        {% if is_granted('ROLE_ARTISTE') %}
          <div class=\"mb-3 text-right\">
            <a href=\"{{ path('app_produit_new') }}\" class=\"btn btn-primary\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\">
              <i class=\"fa fa-plus\"></i> Ajouter un produit
            </a>
          </div>
        {% endif %}
        <form method=\"GET\" action=\"{{ path('app_produit_index') }}\">
          <div class=\"row g-2\">
            <div class=\"col-md-5\">
              <input type=\"text\" name=\"search\" value=\"{{ search }}\" 
                     class=\"form-control\" placeholder=\"Rechercher un produit...\">
            </div>
            <div class=\"col-md-3\">
              <select name=\"categorie\" class=\"form-control\">
                <option value=\"\">Toutes les catégories</option>
                {% for categorie in categories %}
                  <option value=\"{{ categorie.id }}\" {% if categorieId == categorie.id ~ '' %}selected{% endif %}>
                    {{ categorie.nom }}
                  </option>
                {% endfor %}
              </select>
            </div>
            <div class=\"col-md-3\">
              <select name=\"tri\" class=\"form-control\">
                <option value=\"\">Trier par...</option>
                <option value=\"prix_asc\" {% if tri == 'prix_asc' %}selected{% endif %}>Prix croissant</option>
                <option value=\"prix_desc\" {% if tri == 'prix_desc' %}selected{% endif %}>Prix décroissant</option>
                <option value=\"nom_asc\" {% if tri == 'nom_asc' %}selected{% endif %}>Nom A-Z</option>
                <option value=\"nom_desc\" {% if tri == 'nom_desc' %}selected{% endif %}>Nom Z-A</option>
              </select>
            </div>
            <div class=\"col-md-1\">
              <button type=\"submit\" class=\"btn btn-warning w-100\" style=\"background-color: #ffbe33; border:none;\">
                <i class=\"fa fa-search\"></i>
              </button>
            </div>
          </div>
          {% if search or categorieId or tri %}
            <div class=\"mt-2\">
              <a href=\"{{ path('app_produit_index') }}\" class=\"btn btn-sm btn-secondary\">
                Réinitialiser les filtres
              </a>
            </div>
          {% endif %}
        </form>
      </div>
    </div>

    <!-- Liste des produits -->
    <div class=\"row grid\">
      {% for produit in produits %}
        <div class=\"col-sm-6 col-lg-4\">
          <div class=\"box\">
            <div>
              <div class=\"img-box\">
                {% if produit.image %}
                  <img src=\"{{ asset('uploads/images/produits/' ~ produit.image) }}\" alt=\"{{ produit.nom }}\">
                {% else %}
                  <img src=\"{{ asset('front/images/f1.png') }}\" alt=\"{{ produit.nom }}\">
                {% endif %}
              </div>
              <div class=\"detail-box\">
                <h5>{{ produit.nom }}</h5>
                <p>{{ produit.description|slice(0, 80) }}{% if produit.description|length > 80 %}...{% endif %}</p>
                {% if produit.categorie %}
                  <span class=\"badge\" style=\"background-color: #ffbe33; color: #000; font-size: 0.75rem;\">
                    {{ produit.categorie.nom }}
                  </span>
                {% endif %}
                <div class=\"options\">
                  <h6>{{ produit.prix }} €</h6>
                  <div class=\"d-flex align-items-center\">
                    <a href=\"{{ path('app_produit_show', {'id': produit.id}) }}\" class=\"mr-2\" title=\"Voir le produit\">
                      <i class=\"fa fa-eye\"></i>
                    </a>
                    {% if not is_granted('ROLE_ARTISTE') %}
                      <a href=\"javascript:void(0);\" 
                         class=\"btn-favorite\" 
                         data-id=\"{{ produit.id }}\" 
                         title=\"Ajouter aux favoris\">
                        <i class=\"fa {{ produit.id in app.session.get('favoris', []) ? 'fa-heart' : 'fa-heart-o' }}\" 
                           style=\"color: {{ produit.id in app.session.get('favoris', []) ? '#dc3545' : '#000' }}; font-size: 1.2rem;\"></i>
                      </a>
                    {% endif %}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      {% else %}
        <div class=\"col-12 text-center\">
          <p>Aucun produit trouvé.</p>
          <a href=\"{{ path('app_produit_index') }}\" class=\"btn1\">Voir tous les produits</a>
        </div>
      {% endfor %}
    </div>

  </div>
</section>
{% endblock %}

{% block javascripts %}
  <script>
    // Recherche automatique après 500ms de pause
    document.querySelector('input[name=\"search\"]').addEventListener('input', function() {
      clearTimeout(this.timer);
      this.timer = setTimeout(function() {
        document.querySelector('form').submit();
      }, 500);
    });

    // Filtre catégorie automatique
    document.querySelector('select[name=\"categorie\"]').addEventListener('change', function() {
      document.querySelector('form').submit();
    });

    // Tri automatique
    document.querySelector('select[name=\"tri\"]').addEventListener('change', function() {
      document.querySelector('form').submit();
    });

    // Gestion des favoris via AJAX
    document.querySelectorAll('.btn-favorite').forEach(btn => {
      btn.addEventListener('click', function() {
        const id = this.dataset.id;
        const icon = this.querySelector('i');
        
        // On effectue l'appel AJAX
        fetch(`/favoris/toggle/\${id}`, {
          method: 'POST',
          headers: {
            'X-Requested-With': 'XMLHttpRequest'
          }
        })
        .then(response => response.json())
        .then(data => {
          if (data.isFavorite) {
            icon.classList.remove('fa-heart-o');
            icon.classList.add('fa-heart');
            icon.style.color = '#dc3545'; // Rouge
          } else {
            icon.classList.remove('fa-heart');
            icon.classList.add('fa-heart-o');
            icon.style.color = '#000'; // Noir
          }

          // Mise à jour du badge dans la navbar
          const badge = document.getElementById('favoris-count');
          if (data.count > 0) {
            if (badge) {
              badge.innerText = data.count;
            } else {
              // Créer le badge s'il n'existe pas encore
              const cartLink = document.querySelector('a[title=\"Mes Favoris\"]');
              const newBadge = document.createElement('span');
              newBadge.id = 'favoris-count';
              newBadge.className = 'badge badge-pill badge-danger';
              newBadge.style = 'position: absolute; top: -10px; right: -10px; font-size: 10px;';
              newBadge.innerText = data.count;
              cartLink.appendChild(newBadge);
            }
          } else if (badge) {
            badge.remove();
          }
        });
      });
    });
  </script>
{% endblock %}", "produit/index.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\produit\\index.html.twig");
    }
}
