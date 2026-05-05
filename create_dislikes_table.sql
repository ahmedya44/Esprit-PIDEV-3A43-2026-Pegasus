-- Table pour les dislikes d'œuvres d'art
CREATE TABLE IF NOT EXISTS art_dislike (
    id INT AUTO_INCREMENT PRIMARY KEY,
    art_id INT NOT NULL,
    session_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE,
    UNIQUE KEY unique_art_session (art_id, session_id)
);

-- Index pour optimiser les performances
CREATE INDEX idx_dislike_art_id ON art_dislike(art_id);
CREATE INDEX idx_dislike_session_id ON art_dislike(session_id);

-- Ajouter la colonne dislikes à la table art si elle n'existe pas
ALTER TABLE art ADD COLUMN IF NOT EXISTS dislikes INT DEFAULT 0;

-- Mettre à jour les dislikes existants
UPDATE art SET dislikes = 0 WHERE dislikes IS NULL;
