-- Vérifiez la structure de votre table art
USE artwork;
DESCRIBE art;

-- Vérifiez si la table existe
SHOW TABLES LIKE 'art';

-- Si la table n'existe pas, créez-la
CREATE TABLE IF NOT EXISTS art (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    status VARCHAR(50) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
