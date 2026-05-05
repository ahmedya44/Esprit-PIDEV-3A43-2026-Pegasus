-- Augmenter la taille de la colonne image_url pour accepter les URLs longues
ALTER TABLE artwork.art MODIFY COLUMN image_url VARCHAR(1000);

-- Vérifier la structure de la table
DESCRIBE artwork.art;
