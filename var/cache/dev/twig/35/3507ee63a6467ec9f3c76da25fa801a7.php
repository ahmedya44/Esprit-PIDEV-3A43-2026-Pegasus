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

/* back/art/edit.html.twig */
class __TwigTemplate_2dc5a26d7fc728bd5856ccb8ca0ac470 extends Template
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
        $__internal_5a27a8ba21ca79b61932376b2fa922d2->enter($__internal_5a27a8ba21ca79b61932376b2fa922d2_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art/edit.html.twig"));

        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "back/art/edit.html.twig"));

        // line 1
        yield "<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Modifier une œuvre - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"";
        // line 7
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/img/kaiadmin/favicon.ico"), "html", null, true);
        yield "\" type=\"image/x-icon\" />

    <link rel=\"stylesheet\" href=\"";
        // line 9
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/bootstrap.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 10
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/plugins.min.css"), "html", null, true);
        yield "\" />
    <link rel=\"stylesheet\" href=\"";
        // line 11
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($this->extensions['Symfony\Bridge\Twig\Extension\AssetExtension']->getAssetUrl("back/css/kaiadmin.min.css"), "html", null, true);
        yield "\" />
</head>
<body>
<div class=\"wrapper\">
    <div class=\"main-panel\" style=\"width: 100%\">
        <div class=\"container\">
            <div class=\"page-inner\">
                <div class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\">
                    <div>
                        <h3 class=\"fw-bold mb-3\">Modifier une œuvre</h3>
                    </div>
                    <div class=\"ms-md-auto py-2 py-md-0\">
                        <a href=\"";
        // line 23
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("back_dashboard");
        yield "\" class=\"btn btn-secondary btn-round\">Retour</a>
                    </div>
                </div>

                ";
        // line 27
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["app"]) || array_key_exists("app", $context) ? $context["app"] : (function () { throw new RuntimeError('Variable "app" does not exist.', 27, $this->source); })()), "session", [], "any", false, false, false, 27), "flashbag", [], "any", false, false, false, 27), "all", [], "method", false, false, false, 27));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 28
            yield "                    ";
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable($context["messages"]);
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 29
                yield "                        <div class=\"alert alert-";
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["type"], "html", null, true);
                yield " alert-dismissible fade show\" role=\"alert\">
                            ";
                // line 30
                yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape($context["message"], "html", null, true);
                yield "
                            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>
                        </div>
                    ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_key'], $context['message'], $context['_parent']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 34
            yield "                ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['type'], $context['messages'], $context['_parent']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 35
        yield "
                <div class=\"card\">
                    <div class=\"card-body\">
                        ";
        // line 38
        yield         $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderBlock((isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 38, $this->source); })()), 'form_start');
        yield "
                            <div class=\"mb-3\">
                                ";
        // line 40
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 40, $this->source); })()), "title", [], "any", false, false, false, 40), 'label');
        yield "
                                ";
        // line 41
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 41, $this->source); })()), "title", [], "any", false, false, false, 41), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                                <div class=\"mt-2\">
                                    <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"";
        // line 43
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 43, $this->source); })()), "title", [], "any", false, false, false, 43), "vars", [], "any", false, false, false, 43), "id", [], "any", false, false, false, 43), "html", null, true);
        yield "\">Traduire EN→FR</button>
                                </div>
                                ";
        // line 45
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 45, $this->source); })()), "title", [], "any", false, false, false, 45), 'errors');
        yield "
                            </div>

                            <div class=\"mb-3\">
                                ";
        // line 49
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 49, $this->source); })()), "description", [], "any", false, false, false, 49), 'label');
        yield "
                                ";
        // line 50
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 50, $this->source); })()), "description", [], "any", false, false, false, 50), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                                <div class=\"mt-2\">
                                    <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"";
        // line 52
        yield $this->env->getRuntime('Twig\Runtime\EscaperRuntime')->escape(CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 52, $this->source); })()), "description", [], "any", false, false, false, 52), "vars", [], "any", false, false, false, 52), "id", [], "any", false, false, false, 52), "html", null, true);
        yield "\">Traduire EN→FR</button>
                                </div>
                                ";
        // line 54
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 54, $this->source); })()), "description", [], "any", false, false, false, 54), 'errors');
        yield "
                            </div>

                            <div class=\"mb-3\">
                                ";
        // line 58
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 58, $this->source); })()), "imageUrl", [], "any", false, false, false, 58), 'label');
        yield "
                                ";
        // line 59
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 59, $this->source); })()), "imageUrl", [], "any", false, false, false, 59), 'widget', ["attr" => ["class" => "form-control"]]);
        yield "
                                ";
        // line 60
        yield $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->searchAndRenderBlock(CoreExtension::getAttribute($this->env, $this->source, (isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 60, $this->source); })()), "imageUrl", [], "any", false, false, false, 60), 'errors');
        yield "
                            </div>

                            <button class=\"btn btn-primary\">Enregistrer</button>
                        ";
        // line 64
        yield         $this->env->getRuntime('Symfony\Component\Form\FormRenderer')->renderBlock((isset($context["form"]) || array_key_exists("form", $context) ? $context["form"] : (function () { throw new RuntimeError('Variable "form" does not exist.', 64, $this->source); })()), 'form_end');
        yield "
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    (function () {
        async function translateText(text) {
            const res = await fetch('";
        // line 75
        yield $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("api_translate");
        yield "', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ text: text, source: 'EN', target: 'FR' }),
            });

            const data = await res.json();
            if (!res.ok) {
                throw new Error(data && data.error ? data.error : 'Translation failed');
            }
            return data.translatedText || '';
        }

        document.querySelectorAll('.js-translate').forEach(function (btn) {
            btn.addEventListener('click', async function () {
                const targetId = btn.getAttribute('data-target');
                const el = document.getElementById(targetId);
                if (!el) return;

                const original = el.value || '';
                btn.disabled = true;

                try {
                    const translated = await translateText(original);
                    el.value = translated;
                } catch (e) {
                    alert(e.message || 'Erreur de traduction');
                } finally {
                    btn.disabled = false;
                }
            });
        });
    })();
</script>

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
        return "back/art/edit.html.twig";
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
        return array (  202 => 75,  188 => 64,  181 => 60,  177 => 59,  173 => 58,  166 => 54,  161 => 52,  156 => 50,  152 => 49,  145 => 45,  140 => 43,  135 => 41,  131 => 40,  126 => 38,  121 => 35,  115 => 34,  105 => 30,  100 => 29,  95 => 28,  91 => 27,  84 => 23,  69 => 11,  65 => 10,  61 => 9,  56 => 7,  48 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />
    <title>Modifier une œuvre - Admin</title>
    <meta content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\" name=\"viewport\" />
    <link rel=\"icon\" href=\"{{ asset('back/img/kaiadmin/favicon.ico') }}\" type=\"image/x-icon\" />

    <link rel=\"stylesheet\" href=\"{{ asset('back/css/bootstrap.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/plugins.min.css') }}\" />
    <link rel=\"stylesheet\" href=\"{{ asset('back/css/kaiadmin.min.css') }}\" />
</head>
<body>
<div class=\"wrapper\">
    <div class=\"main-panel\" style=\"width: 100%\">
        <div class=\"container\">
            <div class=\"page-inner\">
                <div class=\"d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4\">
                    <div>
                        <h3 class=\"fw-bold mb-3\">Modifier une œuvre</h3>
                    </div>
                    <div class=\"ms-md-auto py-2 py-md-0\">
                        <a href=\"{{ path('back_dashboard') }}\" class=\"btn btn-secondary btn-round\">Retour</a>
                    </div>
                </div>

                {% for type, messages in app.session.flashbag.all() %}
                    {% for message in messages %}
                        <div class=\"alert alert-{{ type }} alert-dismissible fade show\" role=\"alert\">
                            {{ message }}
                            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>
                        </div>
                    {% endfor %}
                {% endfor %}

                <div class=\"card\">
                    <div class=\"card-body\">
                        {{ form_start(form) }}
                            <div class=\"mb-3\">
                                {{ form_label(form.title) }}
                                {{ form_widget(form.title, { attr: { class: 'form-control' } }) }}
                                <div class=\"mt-2\">
                                    <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"{{ form.title.vars.id }}\">Traduire EN→FR</button>
                                </div>
                                {{ form_errors(form.title) }}
                            </div>

                            <div class=\"mb-3\">
                                {{ form_label(form.description) }}
                                {{ form_widget(form.description, { attr: { class: 'form-control' } }) }}
                                <div class=\"mt-2\">
                                    <button type=\"button\" class=\"btn btn-sm btn-outline-secondary js-translate\" data-target=\"{{ form.description.vars.id }}\">Traduire EN→FR</button>
                                </div>
                                {{ form_errors(form.description) }}
                            </div>

                            <div class=\"mb-3\">
                                {{ form_label(form.imageUrl) }}
                                {{ form_widget(form.imageUrl, { attr: { class: 'form-control' } }) }}
                                {{ form_errors(form.imageUrl) }}
                            </div>

                            <button class=\"btn btn-primary\">Enregistrer</button>
                        {{ form_end(form) }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    (function () {
        async function translateText(text) {
            const res = await fetch('{{ path('api_translate') }}', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ text: text, source: 'EN', target: 'FR' }),
            });

            const data = await res.json();
            if (!res.ok) {
                throw new Error(data && data.error ? data.error : 'Translation failed');
            }
            return data.translatedText || '';
        }

        document.querySelectorAll('.js-translate').forEach(function (btn) {
            btn.addEventListener('click', async function () {
                const targetId = btn.getAttribute('data-target');
                const el = document.getElementById(targetId);
                if (!el) return;

                const original = el.value || '';
                btn.disabled = true;

                try {
                    const translated = await translateText(original);
                    el.value = translated;
                } catch (e) {
                    alert(e.message || 'Erreur de traduction');
                } finally {
                    btn.disabled = false;
                }
            });
        });
    })();
</script>

</body>
</html>
", "back/art/edit.html.twig", "C:\\Users\\amina\\Downloads\\Pegasus-template\\Pegasus-template\\templates\\back\\art\\edit.html.twig");
    }
}
