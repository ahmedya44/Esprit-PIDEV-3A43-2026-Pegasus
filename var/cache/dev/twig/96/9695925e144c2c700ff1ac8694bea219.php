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

/* pdf/ticket.html.twig */
class __TwigTemplate_a5463230821bf43445c989093a7c8732 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "pdf/ticket.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "pdf/ticket.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"fr\">
<head>
    <meta charset=\"UTF-8\">
    <title>Ticket - Commande #";
        // line 5
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 5, $this->source); })()), "id", [], "any", false, false, false, 5), "html", null, true);
        yield "</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'DejaVu Sans', Arial, sans-serif; font-size: 12px; color: #333; margin: 20px; }
        .header { text-align: center; border-bottom: 2px solid #ffbe33; padding-bottom: 15px; margin-bottom: 25px; }
        .header h1 { margin: 0; font-size: 24px; color: #222; }
        .header .slogan { color: #666; font-size: 11px; margin-top: 5px; }
        .info-block { margin-bottom: 20px; }
        .info-block strong { display: inline-block; width: 120px; color: #555; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #f5f5f5; font-weight: bold; }
        .text-right { text-align: right; }
        .total-row { font-weight: bold; font-size: 14px; background: #fff8e6; }
        .footer { margin-top: 30px; padding-top: 15px; border-top: 1px solid #ddd; font-size: 10px; color: #888; text-align: center; }
        .statut { display: inline-block; padding: 4px 10px; border-radius: 4px; font-weight: bold; }
        .statut.payee { background: #d4edda; color: #155724; }
        .statut.validee { background: #cce5ff; color: #004085; }
    </style>
</head>
<body>
    <div class=\"header\">
        <h1>Pegasus</h1>
        <p class=\"slogan\">Ticket de caisse / Facture</p>
    </div>

    <div class=\"info-block\">
        <strong>N° commande :</strong> #";
        // line 32
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 32, $this->source); })()), "id", [], "any", false, false, false, 32), "html", null, true);
        yield "<br>
        <strong>Date :</strong> ";
        // line 33
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 33, $this->source); })()), "dateCommande", [], "any", false, false, false, 33), "d/m/Y H:i"), "html", null, true);
        yield "<br>
        <strong>Statut :</strong> <span class=\"statut ";
        // line 34
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 34, $this->source); })()), "statut", [], "any", false, false, false, 34), "html", null, true);
        yield "\">";
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 34, $this->source); })()), "statut", [], "any", false, false, false, 34), "html", null, true);
        yield "</span>
    </div>

    <table>
        <thead>
            <tr>
                <th>Produit</th>
                <th class=\"text-right\">Qté</th>
                <th class=\"text-right\">Prix unitaire</th>
                <th class=\"text-right\">Sous-total</th>
            </tr>
        </thead>
        <tbody>
            ";
        // line 47
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable((isset($context["lignes"]) || array_key_exists("lignes", $context) ? $context["lignes"] : (function () { throw new RuntimeError('Variable "lignes" does not exist.', 47, $this->source); })()));
        foreach ($context['_seq'] as $context["_key"] => $context["ligne"]) {
            // line 48
            yield "            <tr>
                <td>";
            // line 49
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "produit", [], "any", false, false, false, 49), "nom", [], "any", false, false, false, 49), "html", null, true);
            yield "</td>
                <td class=\"text-right\">";
            // line 50
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "quantite", [], "any", false, false, false, 50), "html", null, true);
            yield "</td>
                <td class=\"text-right\">";
            // line 51
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatNumber(CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "prixUnitaire", [], "any", false, false, false, 51), 2, ",", " "), "html", null, true);
            yield " €</td>
                <td class=\"text-right\">";
            // line 52
            yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatNumber((CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "quantite", [], "any", false, false, false, 52) * CoreExtension::getAttribute($this->env, $this->source, $context["ligne"], "prixUnitaire", [], "any", false, false, false, 52)), 2, ",", " "), "html", null, true);
            yield " €</td>
            </tr>
            ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['ligne'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 55
        yield "        </tbody>
        <tfoot>
            <tr class=\"total-row\">
                <td colspan=\"3\" class=\"text-right\">Total TTC</td>
                <td class=\"text-right\">";
        // line 59
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Twig\Extension\CoreExtension']->formatNumber(CoreExtension::getAttribute($this->env, $this->source, (isset($context["commande"]) || array_key_exists("commande", $context) ? $context["commande"] : (function () { throw new RuntimeError('Variable "commande" does not exist.', 59, $this->source); })()), "total", [], "any", false, false, false, 59), 2, ",", " "), "html", null, true);
        yield " €</td>
            </tr>
        </tfoot>
    </table>

    <div class=\"footer\">
        Merci pour votre achat. Pegasus — Ce document fait office de ticket de caisse / facture.
    </div>
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
        return "pdf/ticket.html.twig";
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
        return array (  144 => 59,  138 => 55,  129 => 52,  125 => 51,  121 => 50,  117 => 49,  114 => 48,  110 => 47,  92 => 34,  88 => 33,  84 => 32,  54 => 5,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"fr\">
<head>
    <meta charset=\"UTF-8\">
    <title>Ticket - Commande #{{ commande.id }}</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'DejaVu Sans', Arial, sans-serif; font-size: 12px; color: #333; margin: 20px; }
        .header { text-align: center; border-bottom: 2px solid #ffbe33; padding-bottom: 15px; margin-bottom: 25px; }
        .header h1 { margin: 0; font-size: 24px; color: #222; }
        .header .slogan { color: #666; font-size: 11px; margin-top: 5px; }
        .info-block { margin-bottom: 20px; }
        .info-block strong { display: inline-block; width: 120px; color: #555; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #f5f5f5; font-weight: bold; }
        .text-right { text-align: right; }
        .total-row { font-weight: bold; font-size: 14px; background: #fff8e6; }
        .footer { margin-top: 30px; padding-top: 15px; border-top: 1px solid #ddd; font-size: 10px; color: #888; text-align: center; }
        .statut { display: inline-block; padding: 4px 10px; border-radius: 4px; font-weight: bold; }
        .statut.payee { background: #d4edda; color: #155724; }
        .statut.validee { background: #cce5ff; color: #004085; }
    </style>
</head>
<body>
    <div class=\"header\">
        <h1>Pegasus</h1>
        <p class=\"slogan\">Ticket de caisse / Facture</p>
    </div>

    <div class=\"info-block\">
        <strong>N° commande :</strong> #{{ commande.id }}<br>
        <strong>Date :</strong> {{ commande.dateCommande|date('d/m/Y H:i') }}<br>
        <strong>Statut :</strong> <span class=\"statut {{ commande.statut }}\">{{ commande.statut }}</span>
    </div>

    <table>
        <thead>
            <tr>
                <th>Produit</th>
                <th class=\"text-right\">Qté</th>
                <th class=\"text-right\">Prix unitaire</th>
                <th class=\"text-right\">Sous-total</th>
            </tr>
        </thead>
        <tbody>
            {% for ligne in lignes %}
            <tr>
                <td>{{ ligne.produit.nom }}</td>
                <td class=\"text-right\">{{ ligne.quantite }}</td>
                <td class=\"text-right\">{{ ligne.prixUnitaire|number_format(2, ',', ' ') }} €</td>
                <td class=\"text-right\">{{ (ligne.quantite * ligne.prixUnitaire)|number_format(2, ',', ' ') }} €</td>
            </tr>
            {% endfor %}
        </tbody>
        <tfoot>
            <tr class=\"total-row\">
                <td colspan=\"3\" class=\"text-right\">Total TTC</td>
                <td class=\"text-right\">{{ commande.total|number_format(2, ',', ' ') }} €</td>
            </tr>
        </tfoot>
    </table>

    <div class=\"footer\">
        Merci pour votre achat. Pegasus — Ce document fait office de ticket de caisse / facture.
    </div>
</body>
</html>
", "pdf/ticket.html.twig", "C:\\Users\\ranim\\Downloads\\Pegasus-produit\\Pegasus-produit\\templates\\pdf\\ticket.html.twig");
    }
}
