-- Créer des données de test pour la galerie d'art

-- Insérer des œuvres d'art exemples
INSERT INTO art (title, description, image_url, status, artist, created_at, likes) VALUES
('Starry Night', 'Une peinture emblématique de Van Gogh montrant un ciel nocturne tourbillonnant.', 'https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=400', 'published', 'Vincent van Gogh', NOW(), 156),
('The Persistence of Memory', 'Les fameuses montres molles de Salvador Dali représentant le temps qui se déforme.', 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400', 'published', 'Salvador Dalí', NOW(), 203),
('The Great Wave', 'La vague célèbre de Hokusai avec le Mont Fuji en arrière-plan.', 'https://images.unsplash.com/photo-1579532585038-5b5bfecfd4c6?w=400', 'published', 'Katsushika Hokusai', NOW(), 178),
('Girl with a Pearl Earring', 'Portrait mystérieux dune jeune fille avec un éclairage dramatique.', 'https://images.unsplash.com/photo-1549490349-8643362247b5?w=400', 'published', 'Johannes Vermeer', NOW(), 145),
('The Scream', 'Figure emblématique de langoisse existentielle moderne.', 'https://images.unsplash.com/photo-1578321272176-b7bbc0679853?w=400', 'pending', 'Edvard Munch', NOW(), 189),
('Water Lilies', 'Série de peintures impressionnistes du jardin de Monet.', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400', 'published', 'Claude Monet', NOW(), 167),
('The Kiss', 'Célébration de lamour et de lintimité dans le style Art Nouveau.', 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400', 'published', 'Gustav Klimt', NOW(), 234),
('Guernica', 'Dénonciation puissante des horreurs de la guerre.', 'https://images.unsplash.com/photo-1536924940846-227afb31e2a5?w=400', 'published', 'Pablo Picasso', NOW(), 198);

-- Insérer des likes pour tester
INSERT INTO art_like (art_id, session_id, created_at) VALUES
(1, 'session_1', NOW()),
(2, 'session_1', NOW()),
(3, 'session_1', NOW()),
(1, 'session_2', NOW()),
(2, 'session_2', NOW()),
(4, 'session_2', NOW()),
(5, 'session_2', NOW()),
(1, 'session_3', NOW()),
(3, 'session_3', NOW()),
(6, 'session_3', NOW()),
(7, 'session_3', NOW()),
(2, 'session_4', NOW()),
(4, 'session_4', NOW()),
(8, 'session_4', NOW());

-- Mettre à jour les likes dans la table art
UPDATE art SET likes = (
    SELECT COUNT(*) FROM art_like WHERE art_like.art_id = art.id
) WHERE id IN (SELECT DISTINCT art_id FROM art_like);

-- Afficher les données créées
SELECT 'Artworks created:' as info;
SELECT id, title, artist, status, likes FROM art;

SELECT 'Likes created:' as info;
SELECT COUNT(*) as total_likes FROM art_like;
