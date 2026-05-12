package com.pegasus.services;

import java.util.Random;

public class QuotesService {
    
    private static final String[] ART_QUOTES = {
        "L'art est une menthe qui s'évapore dans l'infini. - Pablo Picasso",
        "La peinture est la poésie qui se voit. - Paul Valéry",
        "Chaque artiste était d'abord un amateur. - Ralph Waldo Emerson",
        "L'art ne fait que traduire la nature. - Auguste Rodin",
        "La simplicité est la sophistication suprême. - Leonardo da Vinci",
        "La couleur est la touche, l'âme de l'art. - Paul Gauguin",
        "L'art est le plus grand organe de la communication humaine. - Robert Motherwell",
        "La peinture est une musique pour les yeux. - Wassily Kandinsky",
        "L'art ne reproduit pas le visible, il rend visible. - Paul Klee",
        "La peinture est facile quand on ne sait pas comment peindre. - Edgar Degas",
        "L'art est le mensonge qui nous aide à découvrir la vérité. - Pablo Picasso",
        "La beauté est dans les yeux de celui qui regarde. - Oscar Wilde",
        "L'art est la signature de la civilisation. - Beverly Sills",
        "La peinture est la poésie silencieuse. - Leonardo da Vinci",
        "L'art est l'expression la plus intense de la vie. - Frida Kahlo"
    };
    
    private Random random = new Random();
    
    /**
     * Obtenir une citation d'artiste aléatoire
     */
    public String getRandomArtQuote() {
        return ART_QUOTES[random.nextInt(ART_QUOTES.length)];
    }
    
    /**
     * Obtenir une citation spécifique par index
     */
    public String getQuoteByIndex(int index) {
        if (index >= 0 && index < ART_QUOTES.length) {
            return ART_QUOTES[index];
        }
        return getRandomArtQuote();
    }
    
    /**
     * Obtenir le nombre total de citations disponibles
     */
    public int getTotalQuotes() {
        return ART_QUOTES.length;
    }
    
    /**
     * Obtenir une citation avec formatage pour l'interface
     */
    public String getFormattedQuote() {
        String quote = getRandomArtQuote();
        return "💬 " + quote;
    }
    
    /**
     * Vérifier si le service est disponible (toujours true pour cette API locale)
     */
    public boolean isAvailable() {
        return true;
    }
}
