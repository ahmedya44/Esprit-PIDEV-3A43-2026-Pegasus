-- Ajouter la colonne likes à la table artwork
ALTER TABLE artwork.art ADD COLUMN likes INT DEFAULT 0;

-- Vérifier la structure de la table
DESCRIBE artwork.art;
