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
class __TwigTemplate_2f4bb699fe4268497b360b6aadfbf320 extends Template
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
            'stylesheets' => [$this, 'block_stylesheets'],
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
    public function block_stylesheets(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_5a27a8ba21ca79b61932376b2fa922d2 = $this->extensions["Symfony\\Bundle\\WebProfilerBundle\\Twig\\WebProfilerExtension"];
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "stylesheets"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "stylesheets"));

        // line 6
        yield "  ";
        yield from $this->yieldParentBlock("stylesheets", $context, $blocks);
        yield "
  ";
        // line 7
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 7, $this->source); })()), "user", [], "any", false, false, false, 7) &&  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE"))) {
            // line 8
            yield "  <style>
    .ai-chatbot-wrap { position: fixed; bottom: 24px; right: 24px; z-index: 9999; font-family: inherit; }
    .ai-chatbot-btn { width: 60px; height: 60px; border-radius: 50%; border: none; cursor: pointer; display: flex; align-items: center; justify-content: center; background: linear-gradient(145deg, #d4a574 0%, #c4956a 35%, #8b6914 100%); color: #fff; box-shadow: 0 6px 24px rgba(139,105,20,0.35), 0 0 0 1px rgba(255,255,255,0.15) inset; transition: transform 0.35s cubic-bezier(0.34,1.56,0.64,1), box-shadow 0.35s ease; }
    .ai-chatbot-btn:hover { transform: scale(1.08); box-shadow: 0 10px 32px rgba(139,105,20,0.45), 0 0 0 1px rgba(255,255,255,0.2) inset; }
    .ai-chatbot-btn svg { width: 28px; height: 28px; filter: drop-shadow(0 1px 2px rgba(0,0,0,0.2)); }
    @keyframes ai-float-breathing { 0%, 100% { transform: translateY(0); box-shadow: 0 6px 24px rgba(139,105,20,0.35); } 50% { transform: translateY(-4px); box-shadow: 0 10px 28px rgba(139,105,20,0.45); } }
    .ai-chatbot-btn.pulse { animation: ai-float-breathing 3s ease-in-out infinite; }
    .ai-chat-window { position: absolute; bottom: 76px; right: 0; width: 380px; max-width: calc(100vw - 48px); height: 480px; max-height: calc(100vh - 140px); border-radius: 20px; background: linear-gradient(165deg, rgba(255,248,240,0.94) 0%, rgba(253,245,230,0.92) 100%); backdrop-filter: blur(16px); -webkit-backdrop-filter: blur(16px); box-shadow: 0 20px 50px rgba(101,67,33,0.18), 0 0 0 1px rgba(255,255,255,0.6); display: none; flex-direction: column; overflow: hidden; opacity: 0; transform: translateY(24px) scale(0.92); transition: opacity 0.35s cubic-bezier(0.34,1.56,0.64,1), transform 0.35s cubic-bezier(0.34,1.56,0.64,1); }
    .ai-chat-window.open { display: flex; opacity: 1; transform: translateY(0) scale(1); }
    .ai-chat-header { padding: 14px 18px; background: linear-gradient(135deg, #c4956a 0%, #a67c52 50%, #8b6914 100%); color: #fff; font-weight: 700; font-size: 15px; display: flex; align-items: center; justify-content: space-between; border-radius: 20px 20px 0 0; text-shadow: 0 1px 2px rgba(0,0,0,0.15); }
    .ai-chat-close { width: 32px; height: 32px; border: none; background: rgba(255,255,255,0.2); color: #fff; border-radius: 50%; cursor: pointer; display: flex; align-items: center; justify-content: center; font-size: 18px; line-height: 1; transition: background 0.2s, transform 0.2s; }
    .ai-chat-close:hover { background: rgba(255,255,255,0.35); transform: scale(1.05); }
    .ai-chat-messages { flex: 1; overflow-y: auto; padding: 16px; display: flex; flex-direction: column; gap: 12px; background: linear-gradient(180deg, transparent 0%, rgba(253,245,230,0.3) 100%); }
    .ai-msg { max-width: 85%; padding: 12px 16px; border-radius: 18px; font-size: 14px; line-height: 1.5; word-wrap: break-word; animation: ai-msg-fade 0.35s ease; }
    @keyframes ai-msg-fade { from { opacity: 0; transform: translateY(8px); } to { opacity: 1; transform: translateY(0); } }
    .ai-msg.user { align-self: flex-end; background: rgba(245,242,238,0.95); color: #3d3428; border: 1px solid rgba(139,105,20,0.12); border-bottom-right-radius: 6px; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
    .ai-msg.assistant { align-self: flex-start; background: linear-gradient(135deg, rgba(212,165,116,0.25) 0%, rgba(196,149,106,0.2) 50%, rgba(139,105,20,0.12) 100%); color: #2c2418; border: 1px solid rgba(139,105,20,0.1); border-bottom-left-radius: 6px; box-shadow: 0 2px 12px rgba(101,67,33,0.08); }
    .ai-msg.typing { display: flex; gap: 6px; padding: 16px 20px; align-items: center; }
    .ai-typing-dot { width: 8px; height: 8px; border-radius: 50%; background: linear-gradient(145deg, #a67c52, #8b6914); animation: ai-typing-bounce 0.6s ease-in-out infinite alternate; }
    .ai-typing-dot:nth-child(2) { animation-delay: 0.15s; } .ai-typing-dot:nth-child(3) { animation-delay: 0.3s; }
    @keyframes ai-typing-bounce { from { transform: translateY(0); opacity: 0.6; } to { transform: translateY(-8px); opacity: 1; } }
    .ai-suggestions { padding: 0 16px 10px; display: flex; flex-wrap: wrap; gap: 8px; }
    .ai-suggest-btn { padding: 8px 14px; border-radius: 20px; border: 1px solid rgba(139,105,20,0.25); background: rgba(253,245,230,0.8); color: #5c4a32; font-size: 12px; cursor: pointer; transition: background 0.2s, border-color 0.2s, transform 0.2s; }
    .ai-suggest-btn:hover { background: rgba(212,165,116,0.25); border-color: rgba(139,105,20,0.4); transform: translateY(-1px); }
    .ai-chat-input-wrap { padding: 12px 16px 16px; border-top: 1px solid rgba(139,105,20,0.1); display: flex; gap: 10px; align-items: center; background: rgba(255,248,240,0.5); }
    .ai-chat-input { flex: 1; padding: 12px 16px; border: 1px solid rgba(139,105,20,0.2); border-radius: 24px; font-size: 14px; outline: none; background: rgba(255,255,255,0.9); transition: border-color 0.2s, box-shadow 0.2s; }
    .ai-chat-input:focus { border-color: #a67c52; box-shadow: 0 0 0 3px rgba(166,124,82,0.2); }
    .ai-chat-send { width: 44px; height: 44px; border-radius: 50%; border: none; background: linear-gradient(145deg, #c4956a 0%, #a67c52 100%); color: #fff; cursor: pointer; display: flex; align-items: center; justify-content: center; transition: opacity 0.2s, transform 0.2s; box-shadow: 0 4px 12px rgba(139,105,20,0.25); }
    .ai-chat-send:hover:not(:disabled) { transform: scale(1.06); box-shadow: 0 6px 16px rgba(139,105,20,0.35); }
    .ai-chat-send:disabled { opacity: 0.6; cursor: not-allowed; }
    @media (max-width: 480px) { .ai-chatbot-wrap { bottom: 16px; right: 16px; } .ai-chat-window { width: calc(100vw - 32px); right: -8px; height: 420px; border-radius: 16px; } }
  </style>
  ";
        }
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 43
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

        // line 44
        yield "<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"heading_container heading_center\">
      <h2>Nos Produits</h2>
    </div>

    <!-- Barre de recherche et filtres -->
    <div class=\"row mt-4 mb-4\">
      <div class=\"col-12\">
        ";
        // line 53
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 54
            yield "          <div class=\"mb-3 text-right\">
            <a href=\"";
            // line 55
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_new");
            yield "\" class=\"btn btn-primary\" style=\"background-color: #ffbe33; border: none; font-weight: bold; color: #000;\">
              <i class=\"fa fa-plus\"></i> Ajouter un produit
            </a>
          </div>
        ";
        }
        // line 60
        yield "        <form method=\"GET\" action=\"";
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
        yield "\">
          <div class=\"row g-2\">
            <div class=\"col-md-5\">
              <input type=\"text\" name=\"search\" value=\"";
        // line 63
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 63, $this->source); })()), "html", null, true);
        yield "\" 
                     class=\"form-control\" placeholder=\"Rechercher un produit...\">
            </div>
            <div class=\"col-md-3\">
              <select name=\"categorie\" class=\"form-control\">
                <option value=\"\">Toutes les catégories</option>
                ";
        // line 69
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["categories"]) || array_key_exists("categories", $context) ? $context["categories"] : (function () { throw new RuntimeError('Variable "categories" does not exist.', 69, $this->source); })()));
        foreach ($context['_seq'] as $context["_key"] => $context["categorie"]) {
            // line 70
            yield "                  <option value=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "id", [], "any", false, false, false, 70), "html", null, true);
            yield "\" ";
            if (((isset($context["categorieId"]) || array_key_exists("categorieId", $context) ? $context["categorieId"] : (function () { throw new RuntimeError('Variable "categorieId" does not exist.', 70, $this->source); })()) == (CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "id", [], "any", false, false, false, 70) . ""))) {
                yield "selected";
            }
            yield ">
                    ";
            // line 71
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["categorie"], "nom", [], "any", false, false, false, 71), "html", null, true);
            yield "
                  </option>
                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['categorie'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 74
        yield "              </select>
            </div>
            <div class=\"col-md-3\">
              <select name=\"tri\" class=\"form-control\">
                <option value=\"\">Trier par...</option>
                <option value=\"prix_asc\" ";
        // line 79
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 79, $this->source); })()) == "prix_asc")) {
            yield "selected";
        }
        yield ">Prix croissant</option>
                <option value=\"prix_desc\" ";
        // line 80
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 80, $this->source); })()) == "prix_desc")) {
            yield "selected";
        }
        yield ">Prix décroissant</option>
                <option value=\"nom_asc\" ";
        // line 81
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 81, $this->source); })()) == "nom_asc")) {
            yield "selected";
        }
        yield ">Nom A-Z</option>
                <option value=\"nom_desc\" ";
        // line 82
        if (((isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 82, $this->source); })()) == "nom_desc")) {
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
        // line 91
        if ((((isset($context["search"]) || array_key_exists("search", $context) ? $context["search"] : (function () { throw new RuntimeError('Variable "search" does not exist.', 91, $this->source); })()) || (isset($context["categorieId"]) || array_key_exists("categorieId", $context) ? $context["categorieId"] : (function () { throw new RuntimeError('Variable "categorieId" does not exist.', 91, $this->source); })())) || (isset($context["tri"]) || array_key_exists("tri", $context) ? $context["tri"] : (function () { throw new RuntimeError('Variable "tri" does not exist.', 91, $this->source); })()))) {
            // line 92
            yield "            <div class=\"mt-2\">
              <a href=\"";
            // line 93
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn btn-sm btn-secondary\">
                Réinitialiser les filtres
              </a>
            </div>
          ";
        }
        // line 98
        yield "        </form>
      </div>
    </div>

    <!-- Liste des produits -->
    <div class=\"row grid\">
      ";
        // line 104
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["pagination"]) || array_key_exists("pagination", $context) ? $context["pagination"] : (function () { throw new RuntimeError('Variable "pagination" does not exist.', 104, $this->source); })()));
        $context['_iterated'] = false;
        foreach ($context['_seq'] as $context["_key"] => $context["produit"]) {
            // line 105
            yield "        <div class=\"col-sm-6 col-lg-4\">
          <div class=\"box\">
            <div>
              <div class=\"img-box\">
                ";
            // line 109
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 109)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 110
                yield "                  <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "image", [], "any", false, false, false, 110))), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 110), "html", null, true);
                yield "\">
                ";
            } else {
                // line 112
                yield "                  <img src=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
                yield "\" alt=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 112), "html", null, true);
                yield "\">
                ";
            }
            // line 114
            yield "              </div>
              <div class=\"detail-box\">
                <h5>";
            // line 116
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "nom", [], "any", false, false, false, 116), "html", null, true);
            yield "</h5>
                <p>";
            // line 117
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(Twig\Extension\CoreExtension::slice($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 117), 0, 80), "html", null, true);
            if ((Twig\Extension\CoreExtension::length($this->env->getCharset(), CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "description", [], "any", false, false, false, 117)) > 80)) {
                yield "...";
            }
            yield "</p>
                ";
            // line 118
            if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 118)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 119
                yield "                  <span class=\"badge\" style=\"background-color: #ffbe33; color: #000; font-size: 0.75rem;\">
                    ";
                // line 120
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "categorie", [], "any", false, false, false, 120), "nom", [], "any", false, false, false, 120), "html", null, true);
                yield "
                  </span>
                ";
            }
            // line 123
            yield "                <div class=\"options\">
                  <h6>";
            // line 124
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "prix", [], "any", false, false, false, 124), "html", null, true);
            yield " €</h6>
                  <div class=\"d-flex align-items-center\">
                    <a href=\"";
            // line 126
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_show", ["id" => CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 126)]), "html", null, true);
            yield "\" class=\"mr-2\" title=\"Voir le produit\">
                      <i class=\"fa fa-eye\"></i>
                    </a>
                    ";
            // line 129
            if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 130
                yield "                      <a href=\"javascript:void(0);\" 
                         class=\"btn-favorite\" 
                         data-id=\"";
                // line 132
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 132), "html", null, true);
                yield "\" 
                         title=\"Ajouter aux favoris\">
                        <i class=\"fa ";
                // line 134
                yield ((CoreExtension::inFilter(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 134), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 134, $this->source); })()), "session", [], "any", false, false, false, 134), "get", ["favoris", []], "method", false, false, false, 134))) ? ("fa-heart") : ("fa-heart-o"));
                yield "\" 
                           style=\"color: ";
                // line 135
                yield ((CoreExtension::inFilter(CoreExtension::getAttribute($this->env, $this->source, $context["produit"], "id", [], "any", false, false, false, 135), CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 135, $this->source); })()), "session", [], "any", false, false, false, 135), "get", ["favoris", []], "method", false, false, false, 135))) ? ("#dc3545") : ("#000"));
                yield "; font-size: 1.2rem;\"></i>
                      </a>
                    ";
            }
            // line 138
            yield "                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      ";
            $context['_iterated'] = true;
        }
        // line 144
        if (!$context['_iterated']) {
            // line 145
            yield "        <div class=\"col-12 text-center\">
          <p>Aucun produit trouvé.</p>
          <a href=\"";
            // line 147
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
            yield "\" class=\"btn1\">Voir tous les produits</a>
        </div>
      ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['produit'], $context['_parent'], $context['_iterated']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 150
        yield "    </div>

    <div class=\"d-flex justify-content-center mt-4\">
      ";
        // line 153
        yield $this->env->getRuntime('Knp\Bundle\PaginatorBundle\Twig\Extension\PaginationRuntime')->render($this->env, (isset($context["pagination"]) || array_key_exists("pagination", $context) ? $context["pagination"] : (function () { throw new RuntimeError('Variable "pagination" does not exist.', 153, $this->source); })()));
        yield "
    </div>

  </div>
</section>

";
        // line 160
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 160, $this->source); })()), "user", [], "any", false, false, false, 160) &&  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE"))) {
            // line 161
            yield "<div class=\"ai-chatbot-wrap\" id=\"aiChatbotWrap\">
  <div class=\"ai-chat-window\" id=\"aiChatWindow\" aria-hidden=\"true\">
    <div class=\"ai-chat-header\">
      <span><i class=\"fa fa-comments\"></i> Conseiller produit</span>
      <button type=\"button\" class=\"ai-chat-close\" id=\"aiChatClose\" aria-label=\"Fermer\">&times;</button>
    </div>
    <div class=\"ai-chat-messages\" id=\"aiChatMessages\">
      <div class=\"ai-msg assistant\">Posez une question sur le catalogue ou ouvrez une fiche produit pour des conseils ciblés.</div>
    </div>
    <div class=\"ai-suggestions\" id=\"aiSuggestions\">
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"What kind of products do you have?\">Quels produits proposez-vous ?</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Help me choose a product\">Aidez-moi à choisir</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Is it worth the price?\">Rapport qualité-prix ?</button>
    </div>
    <div class=\"ai-chat-input-wrap\">
      <input type=\"text\" class=\"ai-chat-input\" id=\"aiChatInput\" placeholder=\"Votre question...\" autocomplete=\"off\">
      <button type=\"button\" class=\"ai-chat-send\" id=\"aiChatSend\" aria-label=\"Envoyer\">
        <i class=\"fa fa-paper-plane\"></i>
      </button>
    </div>
  </div>
  <button type=\"button\" class=\"ai-chatbot-btn pulse\" id=\"aiChatbotBtn\" aria-label=\"Ouvrir le conseiller produit\">
    <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z\"/></svg>
  </button>
</div>
";
        }
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 189
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

        // line 190
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

    ";
        // line 256
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 256, $this->source); })()), "user", [], "any", false, false, false, 256) &&  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE"))) {
            // line 257
            yield "    (function() {
      var productName = '';
      var productDescription = '';
      var advisorUrl = ";
            // line 260
            yield json_encode($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_ai_product_advisor"));
            yield ";
      var btn = document.getElementById('aiChatbotBtn');
      var windowEl = document.getElementById('aiChatWindow');
      var messagesEl = document.getElementById('aiChatMessages');
      var suggestionsEl = document.getElementById('aiSuggestions');
      var inputEl = document.getElementById('aiChatInput');
      var sendBtn = document.getElementById('aiChatSend');
      var closeBtn = document.getElementById('aiChatClose');
      function scrollToBottom() { if (messagesEl) messagesEl.scrollTop = messagesEl.scrollHeight; }
      function addMessage(text, role) {
        var div = document.createElement('div');
        div.className = 'ai-msg ' + role;
        div.textContent = text;
        messagesEl.appendChild(div);
        scrollToBottom();
      }
      function addTypingIndicator() {
        var div = document.createElement('div');
        div.className = 'ai-msg assistant typing';
        div.id = 'aiTypingIndicator';
        div.innerHTML = '<span class=\"ai-typing-dot\"></span><span class=\"ai-typing-dot\"></span><span class=\"ai-typing-dot\"></span>';
        messagesEl.appendChild(div);
        scrollToBottom();
      }
      function removeTypingIndicator() {
        var el = document.getElementById('aiTypingIndicator');
        if (el) el.remove();
      }
      function setLoading(loading) { sendBtn.disabled = loading; }
      function sendQuestion(question) {
        question = (question || (inputEl && inputEl.value) || '').trim();
        if (!question || !messagesEl) return;
        addMessage(question, 'user');
        inputEl.value = '';
        if (suggestionsEl) suggestionsEl.style.display = 'none';
        addTypingIndicator();
        setLoading(true);
        var body = {
          productName: (typeof productName === 'string' ? productName : ''),
          productDescription: (typeof productDescription === 'string' ? productDescription : ''),
          question: question
        };
        fetch(advisorUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json', 'X-Requested-With': 'XMLHttpRequest' },
          body: JSON.stringify(body)
        })
        .then(function(r) {
          if (!r.ok) throw new Error('Request failed');
          return r.json();
        })
        .then(function(data) {
          removeTypingIndicator();
          addMessage((data && data.answer) ? data.answer : 'Sorry, the AI assistant is temporarily unavailable. Please try again.', 'assistant');
        })
        .catch(function() {
          removeTypingIndicator();
          addMessage('Sorry, the AI assistant is temporarily unavailable. Please try again.', 'assistant');
        })
        .finally(function() { setLoading(false); scrollToBottom(); });
      }
      if (btn) btn.addEventListener('click', function() {
        windowEl.classList.add('open');
        windowEl.setAttribute('aria-hidden', 'false');
        btn.classList.remove('pulse');
        if (inputEl) inputEl.focus();
      });
      if (closeBtn) closeBtn.addEventListener('click', function() {
        windowEl.classList.remove('open');
        windowEl.setAttribute('aria-hidden', 'true');
        btn.classList.add('pulse');
      });
      if (sendBtn) sendBtn.addEventListener('click', function() { sendQuestion(); });
      if (inputEl) inputEl.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); sendQuestion(); }
      });
      if (suggestionsEl) suggestionsEl.querySelectorAll('.ai-suggest-btn').forEach(function(b) {
        b.addEventListener('click', function() { sendQuestion((b.getAttribute('data-question') || '').trim()); });
      });
      scrollToBottom();
    })();
    ";
        }
        // line 342
        yield "  </script>
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
        return array (  629 => 342,  544 => 260,  539 => 257,  537 => 256,  469 => 190,  456 => 189,  419 => 161,  417 => 160,  408 => 153,  403 => 150,  394 => 147,  390 => 145,  388 => 144,  378 => 138,  372 => 135,  368 => 134,  363 => 132,  359 => 130,  357 => 129,  351 => 126,  346 => 124,  343 => 123,  337 => 120,  334 => 119,  332 => 118,  325 => 117,  321 => 116,  317 => 114,  309 => 112,  301 => 110,  299 => 109,  293 => 105,  288 => 104,  280 => 98,  272 => 93,  269 => 92,  267 => 91,  253 => 82,  247 => 81,  241 => 80,  235 => 79,  228 => 74,  219 => 71,  210 => 70,  206 => 69,  197 => 63,  190 => 60,  182 => 55,  179 => 54,  177 => 53,  166 => 44,  153 => 43,  109 => 8,  107 => 7,  102 => 6,  89 => 5,  66 => 3,  43 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}Nos Produits - Pegasus{% endblock %}

{% block stylesheets %}
  {{ parent() }}
  {% if app.user and not is_granted('ROLE_ARTISTE') %}
  <style>
    .ai-chatbot-wrap { position: fixed; bottom: 24px; right: 24px; z-index: 9999; font-family: inherit; }
    .ai-chatbot-btn { width: 60px; height: 60px; border-radius: 50%; border: none; cursor: pointer; display: flex; align-items: center; justify-content: center; background: linear-gradient(145deg, #d4a574 0%, #c4956a 35%, #8b6914 100%); color: #fff; box-shadow: 0 6px 24px rgba(139,105,20,0.35), 0 0 0 1px rgba(255,255,255,0.15) inset; transition: transform 0.35s cubic-bezier(0.34,1.56,0.64,1), box-shadow 0.35s ease; }
    .ai-chatbot-btn:hover { transform: scale(1.08); box-shadow: 0 10px 32px rgba(139,105,20,0.45), 0 0 0 1px rgba(255,255,255,0.2) inset; }
    .ai-chatbot-btn svg { width: 28px; height: 28px; filter: drop-shadow(0 1px 2px rgba(0,0,0,0.2)); }
    @keyframes ai-float-breathing { 0%, 100% { transform: translateY(0); box-shadow: 0 6px 24px rgba(139,105,20,0.35); } 50% { transform: translateY(-4px); box-shadow: 0 10px 28px rgba(139,105,20,0.45); } }
    .ai-chatbot-btn.pulse { animation: ai-float-breathing 3s ease-in-out infinite; }
    .ai-chat-window { position: absolute; bottom: 76px; right: 0; width: 380px; max-width: calc(100vw - 48px); height: 480px; max-height: calc(100vh - 140px); border-radius: 20px; background: linear-gradient(165deg, rgba(255,248,240,0.94) 0%, rgba(253,245,230,0.92) 100%); backdrop-filter: blur(16px); -webkit-backdrop-filter: blur(16px); box-shadow: 0 20px 50px rgba(101,67,33,0.18), 0 0 0 1px rgba(255,255,255,0.6); display: none; flex-direction: column; overflow: hidden; opacity: 0; transform: translateY(24px) scale(0.92); transition: opacity 0.35s cubic-bezier(0.34,1.56,0.64,1), transform 0.35s cubic-bezier(0.34,1.56,0.64,1); }
    .ai-chat-window.open { display: flex; opacity: 1; transform: translateY(0) scale(1); }
    .ai-chat-header { padding: 14px 18px; background: linear-gradient(135deg, #c4956a 0%, #a67c52 50%, #8b6914 100%); color: #fff; font-weight: 700; font-size: 15px; display: flex; align-items: center; justify-content: space-between; border-radius: 20px 20px 0 0; text-shadow: 0 1px 2px rgba(0,0,0,0.15); }
    .ai-chat-close { width: 32px; height: 32px; border: none; background: rgba(255,255,255,0.2); color: #fff; border-radius: 50%; cursor: pointer; display: flex; align-items: center; justify-content: center; font-size: 18px; line-height: 1; transition: background 0.2s, transform 0.2s; }
    .ai-chat-close:hover { background: rgba(255,255,255,0.35); transform: scale(1.05); }
    .ai-chat-messages { flex: 1; overflow-y: auto; padding: 16px; display: flex; flex-direction: column; gap: 12px; background: linear-gradient(180deg, transparent 0%, rgba(253,245,230,0.3) 100%); }
    .ai-msg { max-width: 85%; padding: 12px 16px; border-radius: 18px; font-size: 14px; line-height: 1.5; word-wrap: break-word; animation: ai-msg-fade 0.35s ease; }
    @keyframes ai-msg-fade { from { opacity: 0; transform: translateY(8px); } to { opacity: 1; transform: translateY(0); } }
    .ai-msg.user { align-self: flex-end; background: rgba(245,242,238,0.95); color: #3d3428; border: 1px solid rgba(139,105,20,0.12); border-bottom-right-radius: 6px; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
    .ai-msg.assistant { align-self: flex-start; background: linear-gradient(135deg, rgba(212,165,116,0.25) 0%, rgba(196,149,106,0.2) 50%, rgba(139,105,20,0.12) 100%); color: #2c2418; border: 1px solid rgba(139,105,20,0.1); border-bottom-left-radius: 6px; box-shadow: 0 2px 12px rgba(101,67,33,0.08); }
    .ai-msg.typing { display: flex; gap: 6px; padding: 16px 20px; align-items: center; }
    .ai-typing-dot { width: 8px; height: 8px; border-radius: 50%; background: linear-gradient(145deg, #a67c52, #8b6914); animation: ai-typing-bounce 0.6s ease-in-out infinite alternate; }
    .ai-typing-dot:nth-child(2) { animation-delay: 0.15s; } .ai-typing-dot:nth-child(3) { animation-delay: 0.3s; }
    @keyframes ai-typing-bounce { from { transform: translateY(0); opacity: 0.6; } to { transform: translateY(-8px); opacity: 1; } }
    .ai-suggestions { padding: 0 16px 10px; display: flex; flex-wrap: wrap; gap: 8px; }
    .ai-suggest-btn { padding: 8px 14px; border-radius: 20px; border: 1px solid rgba(139,105,20,0.25); background: rgba(253,245,230,0.8); color: #5c4a32; font-size: 12px; cursor: pointer; transition: background 0.2s, border-color 0.2s, transform 0.2s; }
    .ai-suggest-btn:hover { background: rgba(212,165,116,0.25); border-color: rgba(139,105,20,0.4); transform: translateY(-1px); }
    .ai-chat-input-wrap { padding: 12px 16px 16px; border-top: 1px solid rgba(139,105,20,0.1); display: flex; gap: 10px; align-items: center; background: rgba(255,248,240,0.5); }
    .ai-chat-input { flex: 1; padding: 12px 16px; border: 1px solid rgba(139,105,20,0.2); border-radius: 24px; font-size: 14px; outline: none; background: rgba(255,255,255,0.9); transition: border-color 0.2s, box-shadow 0.2s; }
    .ai-chat-input:focus { border-color: #a67c52; box-shadow: 0 0 0 3px rgba(166,124,82,0.2); }
    .ai-chat-send { width: 44px; height: 44px; border-radius: 50%; border: none; background: linear-gradient(145deg, #c4956a 0%, #a67c52 100%); color: #fff; cursor: pointer; display: flex; align-items: center; justify-content: center; transition: opacity 0.2s, transform 0.2s; box-shadow: 0 4px 12px rgba(139,105,20,0.25); }
    .ai-chat-send:hover:not(:disabled) { transform: scale(1.06); box-shadow: 0 6px 16px rgba(139,105,20,0.35); }
    .ai-chat-send:disabled { opacity: 0.6; cursor: not-allowed; }
    @media (max-width: 480px) { .ai-chatbot-wrap { bottom: 16px; right: 16px; } .ai-chat-window { width: calc(100vw - 32px); right: -8px; height: 420px; border-radius: 16px; } }
  </style>
  {% endif %}
{% endblock %}

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
      {% for produit in pagination %}
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

    <div class=\"d-flex justify-content-center mt-4\">
      {{ knp_pagination_render(pagination) }}
    </div>

  </div>
</section>

{# AI Product Advisor - visible uniquement pour les utilisateurs simples (pas artiste, pas anonyme) #}
{% if app.user and not is_granted('ROLE_ARTISTE') %}
<div class=\"ai-chatbot-wrap\" id=\"aiChatbotWrap\">
  <div class=\"ai-chat-window\" id=\"aiChatWindow\" aria-hidden=\"true\">
    <div class=\"ai-chat-header\">
      <span><i class=\"fa fa-comments\"></i> Conseiller produit</span>
      <button type=\"button\" class=\"ai-chat-close\" id=\"aiChatClose\" aria-label=\"Fermer\">&times;</button>
    </div>
    <div class=\"ai-chat-messages\" id=\"aiChatMessages\">
      <div class=\"ai-msg assistant\">Posez une question sur le catalogue ou ouvrez une fiche produit pour des conseils ciblés.</div>
    </div>
    <div class=\"ai-suggestions\" id=\"aiSuggestions\">
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"What kind of products do you have?\">Quels produits proposez-vous ?</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Help me choose a product\">Aidez-moi à choisir</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Is it worth the price?\">Rapport qualité-prix ?</button>
    </div>
    <div class=\"ai-chat-input-wrap\">
      <input type=\"text\" class=\"ai-chat-input\" id=\"aiChatInput\" placeholder=\"Votre question...\" autocomplete=\"off\">
      <button type=\"button\" class=\"ai-chat-send\" id=\"aiChatSend\" aria-label=\"Envoyer\">
        <i class=\"fa fa-paper-plane\"></i>
      </button>
    </div>
  </div>
  <button type=\"button\" class=\"ai-chatbot-btn pulse\" id=\"aiChatbotBtn\" aria-label=\"Ouvrir le conseiller produit\">
    <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z\"/></svg>
  </button>
</div>
{% endif %}
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

    {% if app.user and not is_granted('ROLE_ARTISTE') %}
    (function() {
      var productName = '';
      var productDescription = '';
      var advisorUrl = {{ path('app_ai_product_advisor')|json_encode|raw }};
      var btn = document.getElementById('aiChatbotBtn');
      var windowEl = document.getElementById('aiChatWindow');
      var messagesEl = document.getElementById('aiChatMessages');
      var suggestionsEl = document.getElementById('aiSuggestions');
      var inputEl = document.getElementById('aiChatInput');
      var sendBtn = document.getElementById('aiChatSend');
      var closeBtn = document.getElementById('aiChatClose');
      function scrollToBottom() { if (messagesEl) messagesEl.scrollTop = messagesEl.scrollHeight; }
      function addMessage(text, role) {
        var div = document.createElement('div');
        div.className = 'ai-msg ' + role;
        div.textContent = text;
        messagesEl.appendChild(div);
        scrollToBottom();
      }
      function addTypingIndicator() {
        var div = document.createElement('div');
        div.className = 'ai-msg assistant typing';
        div.id = 'aiTypingIndicator';
        div.innerHTML = '<span class=\"ai-typing-dot\"></span><span class=\"ai-typing-dot\"></span><span class=\"ai-typing-dot\"></span>';
        messagesEl.appendChild(div);
        scrollToBottom();
      }
      function removeTypingIndicator() {
        var el = document.getElementById('aiTypingIndicator');
        if (el) el.remove();
      }
      function setLoading(loading) { sendBtn.disabled = loading; }
      function sendQuestion(question) {
        question = (question || (inputEl && inputEl.value) || '').trim();
        if (!question || !messagesEl) return;
        addMessage(question, 'user');
        inputEl.value = '';
        if (suggestionsEl) suggestionsEl.style.display = 'none';
        addTypingIndicator();
        setLoading(true);
        var body = {
          productName: (typeof productName === 'string' ? productName : ''),
          productDescription: (typeof productDescription === 'string' ? productDescription : ''),
          question: question
        };
        fetch(advisorUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json', 'X-Requested-With': 'XMLHttpRequest' },
          body: JSON.stringify(body)
        })
        .then(function(r) {
          if (!r.ok) throw new Error('Request failed');
          return r.json();
        })
        .then(function(data) {
          removeTypingIndicator();
          addMessage((data && data.answer) ? data.answer : 'Sorry, the AI assistant is temporarily unavailable. Please try again.', 'assistant');
        })
        .catch(function() {
          removeTypingIndicator();
          addMessage('Sorry, the AI assistant is temporarily unavailable. Please try again.', 'assistant');
        })
        .finally(function() { setLoading(false); scrollToBottom(); });
      }
      if (btn) btn.addEventListener('click', function() {
        windowEl.classList.add('open');
        windowEl.setAttribute('aria-hidden', 'false');
        btn.classList.remove('pulse');
        if (inputEl) inputEl.focus();
      });
      if (closeBtn) closeBtn.addEventListener('click', function() {
        windowEl.classList.remove('open');
        windowEl.setAttribute('aria-hidden', 'true');
        btn.classList.add('pulse');
      });
      if (sendBtn) sendBtn.addEventListener('click', function() { sendQuestion(); });
      if (inputEl) inputEl.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); sendQuestion(); }
      });
      if (suggestionsEl) suggestionsEl.querySelectorAll('.ai-suggest-btn').forEach(function(b) {
        b.addEventListener('click', function() { sendQuestion((b.getAttribute('data-question') || '').trim()); });
      });
      scrollToBottom();
    })();
    {% endif %}
  </script>
{% endblock %}", "produit/index.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\produit\\index.html.twig");
    }
}
