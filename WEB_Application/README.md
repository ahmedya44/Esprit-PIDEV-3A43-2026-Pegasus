# Pegasus 🐎✨  
### Art & Fantasy Web Platform

## Overview
**Pegasus** is a creative web platform developed as part of an academic project at **Esprit School of Engineering – Tunisia** (Academic Year 2025–2026).

The platform is inspired by the universe of **art and fantasy**, aiming to create an immersive digital space where creativity, learning, and artistic collaboration meet. Pegasus allows artists to showcase their work, connect with a passionate community, and participate in various artistic experiences within a single platform.

---

## Features

### 🎨 Artistic Gallery
A space dedicated to discovering and showcasing artistic creations.

### 📚 Online Courses
Workshops and artistic training sessions accessible remotely.

### 🛍️ Marketplace
A platform for selling and promoting artworks, creative products, and artistic services.

### 🎭 Events
Creation and participation in artistic and cultural events.

### 🤝 Sponsors & Partnerships
Support and promotion for artistic initiatives and collaborations.

### 👤 User Management
Secure authentication system and user profile management.

---

## Tech Stack

### Frontend
- HTML
- CSS
- JavaScript
- Twig (Symfony template engine)

### Backend
- Symfony (PHP)

### Desktop Application
- Java
- JavaFX

### Database
- MySQL

### Development Tools
- Git
- GitHub
- Visual Studio Code

---

## Architecture

The Pegasus platform follows a **modular architecture** separating frontend, backend, and database layers.

- **Presentation Layer**  
  Implemented with HTML, CSS, JavaScript and Twig templates.

- **Application Layer**  
  Developed using the Symfony framework for handling business logic and API interactions.

- **Data Layer**  
  Managed with MySQL to store users, artworks, courses, and events.

Additionally, a **JavaFX desktop application** interacts with the system to provide extended functionalities.

---

## Contributors

Project developed by students of **Esprit School of Engineering**.

- Ahmed Ya  
- Team Members (to be completed)

---

## Academic Context

This project was developed as part of the **PI Web Development program** at **Esprit School of Engineering – Tunisia**.

Academic Year: **2025–2026**

The objective of this project is to apply software engineering principles, collaborative development, and modern web technologies to build a complete digital platform.

---

## Getting Started

### Prerequisites

Make sure you have installed:

- PHP
- Composer
- Symfony CLI
- MySQL
- Git

### Installation

Clone the repository:

```bash
git clone https://github.com/ahmedya44/Pegasus.git
```

Navigate to the project folder:

```bash
cd Pegasus
```

Install dependencies:

```bash
composer install
```

Configure the `.env` file for database access.

Run database migrations:

```bash
php bin/console doctrine:migrations:migrate
```

Start the Symfony server:

```bash
symfony server:start
```

---

## Acknowledgments

Special thanks to:

- **Esprit School of Engineering**
- Project supervisors and instructors
- All contributors involved in the development of Pegasus
