# Pegasus Forum JavaFX MVC

Desktop JavaFX rebuild of the Symfony forum module.

## Architecture

- `model`: Java POJOs matching the Symfony database concepts.
- `dao`: plain JDBC repositories for the existing MySQL tables.
- `service`: validation, authentication, moderation, visibility, ownership, rating, translation rules.
- `view`: JavaFX UI controls and layout.
- `controller`: JavaFX event handling and MVC coordination.

## Database

The app uses the same Symfony database and does not create or change tables.

Default connection:

```text
jdbc:mysql://127.0.0.1:3306/pi
user: root
password: empty
```

Override with environment variables when needed:

```text
PEGASUS_DB_URL
PEGASUS_DB_USER
PEGASUS_DB_PASSWORD
```

Tables used exactly as in Symfony:

- `user`
- `forum_post`
- `forum_commentaire`
- `forum_post_rating`
- `forum_post_allowed_viewer`
- `translation`

## Features

- Login with existing Symfony users and BCrypt hashes.
- Posts CRUD with `OPEN`, `CLOSED`, and `HIDDEN` statuses.
- Hidden post access for admin, owner, and `forum_post_allowed_viewer` users.
- Comments CRUD with text or GIF URL requirement.
- One rating per user email per post, supporting 0.5 to 5.
- Search and status filtering.
- Admin comments list.
- Forum stats: count by status, total comments, top commented posts.
- Translation storage and display through the existing `translation` table.
- MyMemory translation API integration.
- Gemini autocomplete integration when `GEMINI_API_KEY` is set.
- Klipy/Giphy GIF search integration when API keys are set.
- No REST API endpoints or Symfony bundles.

## Run

```bash
mvn clean javafx:run
```

Use JDK 17+ and Maven. Keep MySQL/MariaDB running with the Symfony `pi` database loaded.

## API keys

Set these in VS Code `.vscode/launch.json`, Windows environment variables, or your terminal before running:

```text
GEMINI_API_KEY=
GEMINI_MODEL=gemma-3-4b-it
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models

GIF_PROVIDER=klipy
KLIPY_API_KEY=
KLIPY_API_URL=https://api.klipy.com/v2
KLIPY_CLIENT_KEY=pegasus_forum

GIPHY_API_KEY=
GIPHY_RATING=pg-13
```

Translation uses MyMemory and does not need a key.

## VS Code

Open this folder directly in VS Code. The `.vscode` folder includes:

- recommended Java extensions
- JDK 17 project settings
- build task: `maven: compile`
- run task: `javafx: run`
- launch config: `Run Pegasus Forum JavaFX`

On Windows, you can also run:

```powershell
.\open-in-vscode.ps1
```
