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
class __TwigTemplate_e0b47184aab9482292392686aba87857 extends Template
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
    /* --- Floating AI Assistant (artistic theme) --- */
    .ai-chatbot-wrap {
      position: fixed;
      bottom: 24px;
      right: 24px;
      z-index: 9999;
      font-family: inherit;
    }
    .ai-chatbot-btn {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      border: none;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(145deg, #d4a574 0%, #c4956a 35%, #8b6914 100%);
      color: #fff;
      box-shadow: 0 6px 24px rgba(139, 105, 20, 0.35), 0 0 0 1px rgba(255,255,255,0.15) inset;
      transition: transform 0.35s cubic-bezier(0.34, 1.56, 0.64, 1), box-shadow 0.35s ease;
    }
    .ai-chatbot-btn:hover {
      transform: scale(1.08);
      box-shadow: 0 10px 32px rgba(139, 105, 20, 0.45), 0 0 0 1px rgba(255,255,255,0.2) inset;
    }
    .ai-chatbot-btn svg { width: 28px; height: 28px; filter: drop-shadow(0 1px 2px rgba(0,0,0,0.2)); }
    @keyframes ai-float-breathing {
      0%, 100% { transform: translateY(0); box-shadow: 0 6px 24px rgba(139, 105, 20, 0.35); }
      50% { transform: translateY(-4px); box-shadow: 0 10px 28px rgba(139, 105, 20, 0.45); }
    }
    .ai-chatbot-btn.pulse {
      animation: ai-float-breathing 3s ease-in-out infinite;
    }
    .ai-chat-window {
      position: absolute;
      bottom: 76px;
      right: 0;
      width: 380px;
      max-width: calc(100vw - 48px);
      height: 480px;
      max-height: calc(100vh - 140px);
      border-radius: 20px;
      background: linear-gradient(165deg, rgba(255,248,240,0.94) 0%, rgba(253,245,230,0.92) 100%);
      backdrop-filter: blur(16px);
      -webkit-backdrop-filter: blur(16px);
      box-shadow: 0 20px 50px rgba(101, 67, 33, 0.18), 0 0 0 1px rgba(255,255,255,0.6);
      display: none;
      flex-direction: column;
      overflow: hidden;
      opacity: 0;
      transform: translateY(24px) scale(0.92);
      transition: opacity 0.35s cubic-bezier(0.34, 1.56, 0.64, 1), transform 0.35s cubic-bezier(0.34, 1.56, 0.64, 1);
    }
    .ai-chat-window.open {
      display: flex;
      opacity: 1;
      transform: translateY(0) scale(1);
    }
    .ai-chat-header {
      padding: 14px 18px;
      background: linear-gradient(135deg, #c4956a 0%, #a67c52 50%, #8b6914 100%);
      color: #fff;
      font-weight: 700;
      font-size: 15px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-radius: 20px 20px 0 0;
      text-shadow: 0 1px 2px rgba(0,0,0,0.15);
    }
    .ai-chat-close {
      width: 32px; height: 32px;
      border: none;
      background: rgba(255,255,255,0.2);
      color: #fff;
      border-radius: 50%;
      cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      font-size: 18px; line-height: 1;
      transition: background 0.2s, transform 0.2s;
    }
    .ai-chat-close:hover {
      background: rgba(255,255,255,0.35);
      transform: scale(1.05);
    }
    .ai-chat-messages {
      flex: 1;
      overflow-y: auto;
      padding: 16px;
      display: flex;
      flex-direction: column;
      gap: 12px;
      background: linear-gradient(180deg, transparent 0%, rgba(253,245,230,0.3) 100%);
    }
    .ai-msg {
      max-width: 85%;
      padding: 12px 16px;
      border-radius: 18px;
      font-size: 14px;
      line-height: 1.5;
      word-wrap: break-word;
      animation: ai-msg-fade 0.35s ease;
    }
    @keyframes ai-msg-fade {
      from { opacity: 0; transform: translateY(8px); }
      to { opacity: 1; transform: translateY(0); }
    }
    .ai-msg.user {
      align-self: flex-end;
      background: rgba(245, 242, 238, 0.95);
      color: #3d3428;
      border: 1px solid rgba(139, 105, 20, 0.12);
      border-bottom-right-radius: 6px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.04);
    }
    .ai-msg.assistant {
      align-self: flex-start;
      background: linear-gradient(135deg, rgba(212, 165, 116, 0.25) 0%, rgba(196, 149, 106, 0.2) 50%, rgba(139, 105, 20, 0.12) 100%);
      color: #2c2418;
      border: 1px solid rgba(139, 105, 20, 0.1);
      border-bottom-left-radius: 6px;
      box-shadow: 0 2px 12px rgba(101, 67, 33, 0.08);
    }
    .ai-msg.typing {
      display: flex;
      gap: 6px;
      padding: 16px 20px;
      align-items: center;
    }
    .ai-typing-dot {
      width: 8px; height: 8px;
      border-radius: 50%;
      background: linear-gradient(145deg, #a67c52, #8b6914);
      animation: ai-typing-bounce 0.6s ease-in-out infinite alternate;
    }
    .ai-typing-dot:nth-child(2) { animation-delay: 0.15s; }
    .ai-typing-dot:nth-child(3) { animation-delay: 0.3s; }
    @keyframes ai-typing-bounce {
      from { transform: translateY(0); opacity: 0.6; }
      to { transform: translateY(-8px); opacity: 1; }
    }
    .ai-suggestions {
      padding: 0 16px 10px;
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
    }
    .ai-suggest-btn {
      padding: 8px 14px;
      border-radius: 20px;
      border: 1px solid rgba(139, 105, 20, 0.25);
      background: rgba(253, 245, 230, 0.8);
      color: #5c4a32;
      font-size: 12px;
      cursor: pointer;
      transition: background 0.2s, border-color 0.2s, transform 0.2s;
    }
    .ai-suggest-btn:hover {
      background: rgba(212, 165, 116, 0.25);
      border-color: rgba(139, 105, 20, 0.4);
      transform: translateY(-1px);
    }
    .ai-chat-input-wrap {
      padding: 12px 16px 16px;
      border-top: 1px solid rgba(139, 105, 20, 0.1);
      display: flex;
      gap: 10px;
      align-items: center;
      background: rgba(255,248,240,0.5);
    }
    .ai-chat-input {
      flex: 1;
      padding: 12px 16px;
      border: 1px solid rgba(139, 105, 20, 0.2);
      border-radius: 24px;
      font-size: 14px;
      outline: none;
      background: rgba(255,255,255,0.9);
      transition: border-color 0.2s, box-shadow 0.2s;
    }
    .ai-chat-input:focus {
      border-color: #a67c52;
      box-shadow: 0 0 0 3px rgba(166, 124, 82, 0.2);
    }
    .ai-chat-send {
      width: 44px; height: 44px;
      border-radius: 50%;
      border: none;
      background: linear-gradient(145deg, #c4956a 0%, #a67c52 100%);
      color: #fff;
      cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      transition: opacity 0.2s, transform 0.2s;
      box-shadow: 0 4px 12px rgba(139, 105, 20, 0.25);
    }
    .ai-chat-send:hover:not(:disabled) {
      transform: scale(1.06);
      box-shadow: 0 6px 16px rgba(139, 105, 20, 0.35);
    }
    .ai-chat-send:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }
    @media (max-width: 480px) {
      .ai-chatbot-wrap { bottom: 16px; right: 16px; }
      .ai-chat-window { width: calc(100vw - 32px); right: -8px; height: 420px; border-radius: 16px; }
    }
  </style>
  ";
        }
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->leave($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof);

        yield from [];
    }

    // line 221
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

        // line 222
        yield "<section class=\"food_section layout_padding-bottom\">
  <div class=\"container\">
    <div class=\"row\">
      <div class=\"col-md-6\">
        <div class=\"img-box\">
          ";
        // line 227
        if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 227, $this->source); })()), "image", [], "any", false, false, false, 227)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 228
            yield "            <img src=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl(("uploads/images/produits/" . CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 228, $this->source); })()), "image", [], "any", false, false, false, 228))), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 228, $this->source); })()), "nom", [], "any", false, false, false, 228), "html", null, true);
            yield "\" style=\"width: 100%; border-radius: 10px;\">
          ";
        } else {
            // line 230
            yield "            <img src=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("front/images/f1.png"), "html", null, true);
            yield "\" alt=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 230, $this->source); })()), "nom", [], "any", false, false, false, 230), "html", null, true);
            yield "\" style=\"width: 100%; border-radius: 10px;\">
          ";
        }
        // line 232
        yield "        </div>
      </div>
      <div class=\"col-md-6\">
        <div class=\"detail-box\">
          <h2>";
        // line 236
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 236, $this->source); })()), "nom", [], "any", false, false, false, 236), "html", null, true);
        yield "</h2>
          <p>";
        // line 237
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 237, $this->source); })()), "description", [], "any", false, false, false, 237), "html", null, true);
        yield "</p>
          <h4 style=\"color: #ffbe33;\">";
        // line 238
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 238, $this->source); })()), "prix", [], "any", false, false, false, 238), "html", null, true);
        yield " €</h4>
          <p>Stock : ";
        // line 239
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 239, $this->source); })()), "stock", [], "any", false, false, false, 239), "html", null, true);
        yield "</p>
          ";
        // line 240
        if ((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 240, $this->source); })()), "categorie", [], "any", false, false, false, 240)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 241
            yield "            <p>Catégorie : ";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 241, $this->source); })()), "categorie", [], "any", false, false, false, 241), "nom", [], "any", false, false, false, 241), "html", null, true);
            yield "</p>
          ";
        }
        // line 243
        yield "          <p>
            Statut : 
            ";
        // line 245
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 245, $this->source); })()), "statut", [], "any", false, false, false, 245) == "disponible")) {
            // line 246
            yield "              <span style=\"color: green; font-weight: bold;\">Disponible</span>
            ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 247
(isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 247, $this->source); })()), "statut", [], "any", false, false, false, 247) == "rupture")) {
            // line 248
            yield "              <span style=\"color: red; font-weight: bold;\">Rupture de stock</span>
            ";
        } elseif ((CoreExtension::getAttribute($this->env, $this->source,         // line 249
(isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 249, $this->source); })()), "statut", [], "any", false, false, false, 249) == "bientot")) {
            // line 250
            yield "              <span style=\"color: orange; font-weight: bold;\">Bientôt disponible</span>
            ";
        } else {
            // line 252
            yield "              <span style=\"color: grey; font-weight: bold;\">Archivé</span>
            ";
        }
        // line 254
        yield "          </p>

          ";
        // line 256
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 256, $this->source); })()), "statut", [], "any", false, false, false, 256) == "disponible")) {
            // line 257
            yield "            ";
            if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
                // line 258
                yield "              <form method=\"POST\" action=\"";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_ajouter", ["id" => CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 258, $this->source); })()), "id", [], "any", false, false, false, 258)]), "html", null, true);
                yield "\" style=\"margin-top: 20px;\">
                <input type=\"hidden\" name=\"_token\" value=\"";
                // line 259
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderCsrfToken("panier"), "html", null, true);
                yield "\">
                <div class=\"d-flex align-items-center\" style=\"gap: 10px;\">
                  <input type=\"number\" name=\"quantite\" value=\"1\" min=\"1\" max=\"";
                // line 261
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 261, $this->source); })()), "stock", [], "any", false, false, false, 261), "html", null, true);
                yield "\" 
                         class=\"form-control\" style=\"width: 80px;\">
                  <button type=\"submit\" class=\"btn\" style=\"background-color: #ffbe33; border: none; font-weight: bold; padding: 10px 20px;\">
                    <i class=\"fa fa-shopping-cart\"></i> Ajouter au panier
                  </button>
                </div>
              </form>
            ";
            }
            // line 269
            yield "          ";
        }
        // line 270
        yield "
          <div style=\"margin-top: 20px;\">
            <a href=\"";
        // line 272
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_index");
        yield "\" class=\"btn1\">Retour à la liste</a>
            ";
        // line 273
        if ((($tmp =  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 274
            yield "              <a href=\"";
            yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_panier_index");
            yield "\" class=\"btn1\" style=\"margin-left: 10px;\">
                <i class=\"fa fa-shopping-cart\"></i> Voir le panier
              </a>
            ";
        }
        // line 278
        yield "          </div>

          <div style=\"margin-top: 10px;\">
            ";
        // line 281
        if ((($tmp = $this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE")) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 282
            yield "              <a href=\"";
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_produit_edit", ["id" => CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 282, $this->source); })()), "id", [], "any", false, false, false, 282)]), "html", null, true);
            yield "\" class=\"btn btn-sm btn-secondary\">Modifier</a>
              ";
            // line 283
            yield Twig\Extension\CoreExtension::include($this->env, $context, "produit/_delete_form.html.twig");
            yield "
            ";
        }
        // line 285
        yield "          </div>
        </div>
      </div>
    </div>
  </div>
</section>

";
        // line 293
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 293, $this->source); })()), "user", [], "any", false, false, false, 293) &&  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE"))) {
            // line 294
            yield "<div class=\"ai-chatbot-wrap\" id=\"aiChatbotWrap\">
  <div class=\"ai-chat-window\" id=\"aiChatWindow\" aria-hidden=\"true\">
    <div class=\"ai-chat-header\">
      <span><i class=\"fa fa-comments\"></i> Conseiller produit</span>
      <button type=\"button\" class=\"ai-chat-close\" id=\"aiChatClose\" aria-label=\"Fermer\">&times;</button>
    </div>
    <div class=\"ai-chat-messages\" id=\"aiChatMessages\">
      <div class=\"ai-msg assistant\">Posez-moi une question sur ce produit, je vous réponds en quelques secondes.</div>
    </div>
    <div class=\"ai-suggestions\" id=\"aiSuggestions\">
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Is this product good for beginners?\">Débutant ?</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Compare this product with similar ones\">Comparer à d'autres</button>
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

    // line 322
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

        // line 323
        yield "  ";
        if ((CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 323, $this->source); })()), "user", [], "any", false, false, false, 323) &&  !$this->extensions['Symfony\Bridge\Twig\Extension\SecurityExtension']->isGranted("ROLE_ARTISTE"))) {
            // line 324
            yield "  <script>
  (function() {
    var productName = ";
            // line 326
            yield json_encode(CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 326, $this->source); })()), "nom", [], "any", false, false, false, 326));
            yield ";
    var productDescription = ";
            // line 327
            yield json_encode((((CoreExtension::getAttribute($this->env, $this->source, ($context["produit"] ?? null), "description", [], "any", true, true, false, 327) &&  !(null === CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 327, $this->source); })()), "description", [], "any", false, false, false, 327)))) ? (CoreExtension::getAttribute($this->env, $this->source, (isset($context["produit"]) || array_key_exists("produit", $context) ? $context["produit"] : (function () { throw new RuntimeError('Variable "produit" does not exist.', 327, $this->source); })()), "description", [], "any", false, false, false, 327)) : ("")));
            yield ";
    var advisorUrl = ";
            // line 328
            yield json_encode($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_ai_product_advisor"));
            yield ";

    var wrap = document.getElementById('aiChatbotWrap');
    var btn = document.getElementById('aiChatbotBtn');
    var windowEl = document.getElementById('aiChatWindow');
    var messagesEl = document.getElementById('aiChatMessages');
    var suggestionsEl = document.getElementById('aiSuggestions');
    var inputEl = document.getElementById('aiChatInput');
    var sendBtn = document.getElementById('aiChatSend');
    var closeBtn = document.getElementById('aiChatClose');

    function scrollToBottom() {
      messagesEl.scrollTop = messagesEl.scrollHeight;
    }

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

    function setLoading(loading) {
      sendBtn.disabled = loading;
      sendBtn.setAttribute('aria-busy', loading ? 'true' : 'false');
    }

    function sendQuestion(question) {
      question = (question || (inputEl && inputEl.value) || '').trim();
      if (!question) return;

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
        .finally(function() {
          setLoading(false);
          scrollToBottom();
        });
    }

    btn.addEventListener('click', function() {
      windowEl.classList.add('open');
      windowEl.setAttribute('aria-hidden', 'false');
      btn.classList.remove('pulse');
      inputEl.focus();
    });

    closeBtn.addEventListener('click', function() {
      windowEl.classList.remove('open');
      windowEl.setAttribute('aria-hidden', 'true');
      btn.classList.add('pulse');
    });

    sendBtn.addEventListener('click', function() { sendQuestion(); });

    inputEl.addEventListener('keydown', function(e) {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendQuestion();
      }
    });

    if (suggestionsEl) {
      suggestionsEl.querySelectorAll('.ai-suggest-btn').forEach(function(b) {
        b.addEventListener('click', function() {
          sendQuestion((b.getAttribute('data-question') || '').trim());
        });
      });
    }

    scrollToBottom();
  })();
  </script>
  ";
        }
        
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
        return array (  568 => 328,  564 => 327,  560 => 326,  556 => 324,  553 => 323,  540 => 322,  503 => 294,  501 => 293,  492 => 285,  487 => 283,  482 => 282,  480 => 281,  475 => 278,  467 => 274,  465 => 273,  461 => 272,  457 => 270,  454 => 269,  443 => 261,  438 => 259,  433 => 258,  430 => 257,  428 => 256,  424 => 254,  420 => 252,  416 => 250,  414 => 249,  411 => 248,  409 => 247,  406 => 246,  404 => 245,  400 => 243,  394 => 241,  392 => 240,  388 => 239,  384 => 238,  380 => 237,  376 => 236,  370 => 232,  362 => 230,  354 => 228,  352 => 227,  345 => 222,  332 => 221,  110 => 8,  108 => 7,  103 => 6,  90 => 5,  66 => 3,  43 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends 'base_front.html.twig' %}

{% block title %}{{ produit.nom }} - Pegasus{% endblock %}

{% block stylesheets %}
  {{ parent() }}
  {% if app.user and not is_granted('ROLE_ARTISTE') %}
  <style>
    /* --- Floating AI Assistant (artistic theme) --- */
    .ai-chatbot-wrap {
      position: fixed;
      bottom: 24px;
      right: 24px;
      z-index: 9999;
      font-family: inherit;
    }
    .ai-chatbot-btn {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      border: none;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(145deg, #d4a574 0%, #c4956a 35%, #8b6914 100%);
      color: #fff;
      box-shadow: 0 6px 24px rgba(139, 105, 20, 0.35), 0 0 0 1px rgba(255,255,255,0.15) inset;
      transition: transform 0.35s cubic-bezier(0.34, 1.56, 0.64, 1), box-shadow 0.35s ease;
    }
    .ai-chatbot-btn:hover {
      transform: scale(1.08);
      box-shadow: 0 10px 32px rgba(139, 105, 20, 0.45), 0 0 0 1px rgba(255,255,255,0.2) inset;
    }
    .ai-chatbot-btn svg { width: 28px; height: 28px; filter: drop-shadow(0 1px 2px rgba(0,0,0,0.2)); }
    @keyframes ai-float-breathing {
      0%, 100% { transform: translateY(0); box-shadow: 0 6px 24px rgba(139, 105, 20, 0.35); }
      50% { transform: translateY(-4px); box-shadow: 0 10px 28px rgba(139, 105, 20, 0.45); }
    }
    .ai-chatbot-btn.pulse {
      animation: ai-float-breathing 3s ease-in-out infinite;
    }
    .ai-chat-window {
      position: absolute;
      bottom: 76px;
      right: 0;
      width: 380px;
      max-width: calc(100vw - 48px);
      height: 480px;
      max-height: calc(100vh - 140px);
      border-radius: 20px;
      background: linear-gradient(165deg, rgba(255,248,240,0.94) 0%, rgba(253,245,230,0.92) 100%);
      backdrop-filter: blur(16px);
      -webkit-backdrop-filter: blur(16px);
      box-shadow: 0 20px 50px rgba(101, 67, 33, 0.18), 0 0 0 1px rgba(255,255,255,0.6);
      display: none;
      flex-direction: column;
      overflow: hidden;
      opacity: 0;
      transform: translateY(24px) scale(0.92);
      transition: opacity 0.35s cubic-bezier(0.34, 1.56, 0.64, 1), transform 0.35s cubic-bezier(0.34, 1.56, 0.64, 1);
    }
    .ai-chat-window.open {
      display: flex;
      opacity: 1;
      transform: translateY(0) scale(1);
    }
    .ai-chat-header {
      padding: 14px 18px;
      background: linear-gradient(135deg, #c4956a 0%, #a67c52 50%, #8b6914 100%);
      color: #fff;
      font-weight: 700;
      font-size: 15px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-radius: 20px 20px 0 0;
      text-shadow: 0 1px 2px rgba(0,0,0,0.15);
    }
    .ai-chat-close {
      width: 32px; height: 32px;
      border: none;
      background: rgba(255,255,255,0.2);
      color: #fff;
      border-radius: 50%;
      cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      font-size: 18px; line-height: 1;
      transition: background 0.2s, transform 0.2s;
    }
    .ai-chat-close:hover {
      background: rgba(255,255,255,0.35);
      transform: scale(1.05);
    }
    .ai-chat-messages {
      flex: 1;
      overflow-y: auto;
      padding: 16px;
      display: flex;
      flex-direction: column;
      gap: 12px;
      background: linear-gradient(180deg, transparent 0%, rgba(253,245,230,0.3) 100%);
    }
    .ai-msg {
      max-width: 85%;
      padding: 12px 16px;
      border-radius: 18px;
      font-size: 14px;
      line-height: 1.5;
      word-wrap: break-word;
      animation: ai-msg-fade 0.35s ease;
    }
    @keyframes ai-msg-fade {
      from { opacity: 0; transform: translateY(8px); }
      to { opacity: 1; transform: translateY(0); }
    }
    .ai-msg.user {
      align-self: flex-end;
      background: rgba(245, 242, 238, 0.95);
      color: #3d3428;
      border: 1px solid rgba(139, 105, 20, 0.12);
      border-bottom-right-radius: 6px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.04);
    }
    .ai-msg.assistant {
      align-self: flex-start;
      background: linear-gradient(135deg, rgba(212, 165, 116, 0.25) 0%, rgba(196, 149, 106, 0.2) 50%, rgba(139, 105, 20, 0.12) 100%);
      color: #2c2418;
      border: 1px solid rgba(139, 105, 20, 0.1);
      border-bottom-left-radius: 6px;
      box-shadow: 0 2px 12px rgba(101, 67, 33, 0.08);
    }
    .ai-msg.typing {
      display: flex;
      gap: 6px;
      padding: 16px 20px;
      align-items: center;
    }
    .ai-typing-dot {
      width: 8px; height: 8px;
      border-radius: 50%;
      background: linear-gradient(145deg, #a67c52, #8b6914);
      animation: ai-typing-bounce 0.6s ease-in-out infinite alternate;
    }
    .ai-typing-dot:nth-child(2) { animation-delay: 0.15s; }
    .ai-typing-dot:nth-child(3) { animation-delay: 0.3s; }
    @keyframes ai-typing-bounce {
      from { transform: translateY(0); opacity: 0.6; }
      to { transform: translateY(-8px); opacity: 1; }
    }
    .ai-suggestions {
      padding: 0 16px 10px;
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
    }
    .ai-suggest-btn {
      padding: 8px 14px;
      border-radius: 20px;
      border: 1px solid rgba(139, 105, 20, 0.25);
      background: rgba(253, 245, 230, 0.8);
      color: #5c4a32;
      font-size: 12px;
      cursor: pointer;
      transition: background 0.2s, border-color 0.2s, transform 0.2s;
    }
    .ai-suggest-btn:hover {
      background: rgba(212, 165, 116, 0.25);
      border-color: rgba(139, 105, 20, 0.4);
      transform: translateY(-1px);
    }
    .ai-chat-input-wrap {
      padding: 12px 16px 16px;
      border-top: 1px solid rgba(139, 105, 20, 0.1);
      display: flex;
      gap: 10px;
      align-items: center;
      background: rgba(255,248,240,0.5);
    }
    .ai-chat-input {
      flex: 1;
      padding: 12px 16px;
      border: 1px solid rgba(139, 105, 20, 0.2);
      border-radius: 24px;
      font-size: 14px;
      outline: none;
      background: rgba(255,255,255,0.9);
      transition: border-color 0.2s, box-shadow 0.2s;
    }
    .ai-chat-input:focus {
      border-color: #a67c52;
      box-shadow: 0 0 0 3px rgba(166, 124, 82, 0.2);
    }
    .ai-chat-send {
      width: 44px; height: 44px;
      border-radius: 50%;
      border: none;
      background: linear-gradient(145deg, #c4956a 0%, #a67c52 100%);
      color: #fff;
      cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      transition: opacity 0.2s, transform 0.2s;
      box-shadow: 0 4px 12px rgba(139, 105, 20, 0.25);
    }
    .ai-chat-send:hover:not(:disabled) {
      transform: scale(1.06);
      box-shadow: 0 6px 16px rgba(139, 105, 20, 0.35);
    }
    .ai-chat-send:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }
    @media (max-width: 480px) {
      .ai-chatbot-wrap { bottom: 16px; right: 16px; }
      .ai-chat-window { width: calc(100vw - 32px); right: -8px; height: 420px; border-radius: 16px; }
    }
  </style>
  {% endif %}
{% endblock %}

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

{# AI Product Advisor Chatbot - visible uniquement pour les utilisateurs simples (pas artiste, pas anonyme) #}
{% if app.user and not is_granted('ROLE_ARTISTE') %}
<div class=\"ai-chatbot-wrap\" id=\"aiChatbotWrap\">
  <div class=\"ai-chat-window\" id=\"aiChatWindow\" aria-hidden=\"true\">
    <div class=\"ai-chat-header\">
      <span><i class=\"fa fa-comments\"></i> Conseiller produit</span>
      <button type=\"button\" class=\"ai-chat-close\" id=\"aiChatClose\" aria-label=\"Fermer\">&times;</button>
    </div>
    <div class=\"ai-chat-messages\" id=\"aiChatMessages\">
      <div class=\"ai-msg assistant\">Posez-moi une question sur ce produit, je vous réponds en quelques secondes.</div>
    </div>
    <div class=\"ai-suggestions\" id=\"aiSuggestions\">
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Is this product good for beginners?\">Débutant ?</button>
      <button type=\"button\" class=\"ai-suggest-btn\" data-question=\"Compare this product with similar ones\">Comparer à d'autres</button>
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
  {% if app.user and not is_granted('ROLE_ARTISTE') %}
  <script>
  (function() {
    var productName = {{ produit.nom|json_encode|raw }};
    var productDescription = {{ (produit.description ?? '')|json_encode|raw }};
    var advisorUrl = {{ path('app_ai_product_advisor')|json_encode|raw }};

    var wrap = document.getElementById('aiChatbotWrap');
    var btn = document.getElementById('aiChatbotBtn');
    var windowEl = document.getElementById('aiChatWindow');
    var messagesEl = document.getElementById('aiChatMessages');
    var suggestionsEl = document.getElementById('aiSuggestions');
    var inputEl = document.getElementById('aiChatInput');
    var sendBtn = document.getElementById('aiChatSend');
    var closeBtn = document.getElementById('aiChatClose');

    function scrollToBottom() {
      messagesEl.scrollTop = messagesEl.scrollHeight;
    }

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

    function setLoading(loading) {
      sendBtn.disabled = loading;
      sendBtn.setAttribute('aria-busy', loading ? 'true' : 'false');
    }

    function sendQuestion(question) {
      question = (question || (inputEl && inputEl.value) || '').trim();
      if (!question) return;

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
        .finally(function() {
          setLoading(false);
          scrollToBottom();
        });
    }

    btn.addEventListener('click', function() {
      windowEl.classList.add('open');
      windowEl.setAttribute('aria-hidden', 'false');
      btn.classList.remove('pulse');
      inputEl.focus();
    });

    closeBtn.addEventListener('click', function() {
      windowEl.classList.remove('open');
      windowEl.setAttribute('aria-hidden', 'true');
      btn.classList.add('pulse');
    });

    sendBtn.addEventListener('click', function() { sendQuestion(); });

    inputEl.addEventListener('keydown', function(e) {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendQuestion();
      }
    });

    if (suggestionsEl) {
      suggestionsEl.querySelectorAll('.ai-suggest-btn').forEach(function(b) {
        b.addEventListener('click', function() {
          sendQuestion((b.getAttribute('data-question') || '').trim());
        });
      });
    }

    scrollToBottom();
  })();
  </script>
  {% endif %}
{% endblock %}", "produit/show.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\produit\\show.html.twig");
    }
}
