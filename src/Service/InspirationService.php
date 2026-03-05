<?php

declare(strict_types=1);

namespace App\Service;

class InspirationService
{
    /**
     * @var list<string>
     */
    private array $inspirations;

    public function __construct()
    {
        $this->inspirations = [
            "La créativité est l'intelligence qui s'amuse.",
            "Chaque œuvre est une fenêtre sur l'âme de l'artiste.",
            "L'art transforme le visible en invisible.",
            "La beauté sauve le monde, mais l'art le rend vivant.",
            "Votre imagination est votre seule limite.",
            "Le pinceau est l'extension de l'esprit créatif.",
            "Dans chaque coup de pinceau réside une émotion.",
            "L'art ne reproduit pas le visible, il rend visible.",
            "La peinture est la poésie qui se voit.",
            "Créer, c'est résister à la destruction.",
            "L'artiste ne voit pas les choses comme elles sont, il les voit autrement.",
            "La couleur est un moyen d'exercer une influence directe sur l'âme.",
            "L'art est la plus intense manifestation de l'idéal.",
            "Chaque tableau est une confession.",
            "La vraie peinture est poésie silencieuse.",
            "L'art est le langage de l'âme.",
            "La créativité demande courage.",
            "L'inspiration existe, mais elle doit vous trouver en train de travailler.",
            "Le talent, c'est d'avoir envie de faire quelque chose.",
            "L'art est la vie elle-même.",
            "Peindre, c'est peindre la lumière.",
            "La simplicité est la sophistication suprême.",
            "L'art commence là où s'arrête la nature.",
            "La beauté est dans les yeux de celui qui regarde.",
            "Créer, c'est donner une forme à ses rêves.",
            "L'art est une révolte permanente contre la réalité.",
            "La peinture est une poésie visuelle.",
            "Chaque artiste a sa propre lumière.",
            "L'art est le miroir de l'humanité.",
            "La création est un acte d'amour.",
            "L'imagination crée la réalité.",
            "L'art est l'éternité du moment.",
            "Peindre, c'est capturer l'invisible.",
            "La couleur est le clavier, les yeux sont l'harmonie.",
            "L'art ne ment jamais.",
            "Chaque coup de pinceau raconte une histoire.",
            "La beauté est une promesse de bonheur.",
            "L'art est la plus haute forme d'espérance.",
            "Créer, c'est laisser une trace de son passage.",
            "L'inspiration frappe à la porte, il faut ouvrir.",
            "L'art est la vérité embellie.",
            "La peinture est une conversation silencieuse.",
            "Chaque œuvre est un univers à explorer.",
            "L'artiste peint avec son âme.",
            "La couleur exprime ce que les mots ne peuvent dire.",
            "L'art est la liberté de l'esprit.",
            "Créer, c'est danser avec l'infini.",
            "La peinture est musique pour les yeux.",
            "L'art rend la vie plus belle.",
            "Chaque tableau est une porte vers l'imaginaire.",
            "L'artiste voit l'invisible dans le visible.",
            "La création est un voyage intérieur.",
            "L'art est l'écho de l'âme humaine."
        ];
    }

    public function getDailyInspiration(): string
    {
        $dayOfYear = (int)date('z'); // 0-365
        return $this->inspirations[$dayOfYear % count($this->inspirations)];
    }

    public function getRandomInspiration(): string
    {
        return $this->inspirations[array_rand($this->inspirations)];
    }

    /**
     * @return list<string>
     */
    public function getAllInspirations(): array
    {
        return $this->inspirations;
    }
}
