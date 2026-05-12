# Java -> Symfony Parity Matrix

This document is the execution checklist to align the Symfony web app with the Java desktop app.

Legend:
- `MATCHED`: implemented in Symfony with close equivalent
- `PARTIAL`: exists but behavior/coverage likely differs
- `MISSING`: no clear equivalent found in Symfony

## 1) Domain Entity Parity

| Java Entity | Symfony Entity | Status | Action |
|---|---|---|---|
| `User` | `User` | PARTIAL | Verify field parity (status, provider IDs, verification flags, role metadata). |
| `Admin` | `Admin` | MATCHED | Confirm admin-specific permissions/routes parity. |
| `Artiste` | `Artiste` | MATCHED | Check verification and profile fields parity. |
| `NormalUser` | `NormalUser` | MATCHED | Confirm role conversion and profile workflow parity. |
| `Sponsor` | `Sponsor` | MATCHED | Verify sponsor verification/request lifecycle. |
| `RoleRequest` | (none obvious) | MISSING/PARTIAL | Add dedicated entity + workflow if currently embedded elsewhere. |
| `Art` | `Art` | MATCHED | Confirm likes/dislikes/comments counters and metadata parity. |
| `ArtFavoris` | `ArtFavoris` | MATCHED | Add stricter typing and parity checks with Java behavior. |
| `ArtView` | `ArtView` | MATCHED | Confirm unique/view counting rules match Java. |
| `ArtLike` | (no dedicated entity obvious) | MISSING/PARTIAL | Implement explicit like/dislike model if Java has separate table/service. |
| `Evenement` | `Evenement` | MATCHED | Align status transitions and role-specific access. |
| `Participation` | `Participation` | MATCHED | Align participation lifecycle and constraints. |
| `SponsoringPack` | `SponsoringPack` | MATCHED | Align pricing/state and reservation lifecycle. |
| `Course` | `Course` | MATCHED | Align moderation/category/status semantics. |
| `CourseSection` | `CourseSection` | MATCHED | Confirm ordering and visibility logic parity. |
| `CourseVideo` | `CourseVideo` | MATCHED | Align completion/progress metadata with Java if needed. |
| `Quiz` | `Quiz` | MATCHED | Align publishing and scoring behavior. |
| `QuizQuestion` | `QuizQuestion` | MATCHED | Confirm answer validation parity. |
| `QuizChoice` | `QuizChoice` | MATCHED | Confirm correctness and ordering rules. |
| `Post` (forumdesktop model) | `Post` | MATCHED | Align moderation status states and rating behavior. |
| `Comment` (forumdesktop model) | `Commentaire` | PARTIAL | Validate structure parity and moderation links. |
| `PostStatus` (enum/model) | (none obvious enum) | PARTIAL | Add enum/constants for consistent moderation states. |
| `RatingSummary` / rating model | `PostRating` | MATCHED | Align aggregation/query behavior. |
| `TranslationValue` | `Translation` | PARTIAL | Verify direction/language metadata parity. |
| `Produit` | (none) | MISSING | Implement marketplace product module (entity/repo/forms/controllers). |
| `Categorie` | Sonata category entities | PARTIAL | Decide whether to reuse Sonata classification or create dedicated product categories. |
| `Panier` | (none) | MISSING | Implement cart model. |
| `LignePanier` | (none) | MISSING | Implement cart line items. |
| `Commande` | (none) | MISSING | Implement orders. |
| `LigneCommande` | (none) | MISSING | Implement order lines. |
| `MenuItem` | (none obvious) | PARTIAL | Validate if needed in Symfony or UI-only in Java. |

## 2) Service/Business Logic Parity

| Java Service | Symfony Equivalent | Status | Action |
|---|---|---|---|
| `LoginService`, `ServiceUser` | `SecurityController`, `RegistrationController`, `UserRepository` | PARTIAL | Align full auth lifecycle: signup/signin/reset/verification/role upgrade. |
| `RoleRequestService` | (none obvious) | MISSING/PARTIAL | Create explicit role-request service + admin approval flow. |
| `ServiceArt`, `ServiceArtiste` | `GalleryController`, `ArtisteController`, repos | PARTIAL | Align create/edit/delete + filtering + moderation behavior. |
| `ServiceArtLike`, `ServiceArtDislike` | `FavoriteController` + current art flows | MISSING/PARTIAL | Add like/dislike endpoints and persistence if not present. |
| `ServiceArtFavoris` | `ArtFavorisRepository`, `FavoriteController` | MATCHED | Align duplicate prevention/race-condition handling. |
| `ServiceArtView` | `ArtViewRepository`, `ViewController` | MATCHED | Ensure counting semantics match Java. |
| `ServiceArtComment` | `CommentaireRepository` + controllers | PARTIAL | Align moderation, threading, and edit/delete policies. |
| `CourseService` | `ArtistCoursesController`, `CourseRepository` | PARTIAL | Align status workflow + moderation + stats hooks. |
| `CourseSectionService` | `CourseSectionRepository` | PARTIAL | Match section management and ordering operations. |
| `CourseVideoService` | `CourseVideoRepository` | PARTIAL | Add parity for video operations and progress. |
| `CourseCategoryClassifier` | `CourseCategoryClassifier` | MATCHED | Verify model/prompt behavior parity. |
| `LearningProgressService`, `LearningInsightsService` | (none obvious) | MISSING | Implement learner progress + insights data model and UI. |
| `CertificatePdfService` | (none obvious) | MISSING | Add course certificate generation flow if required. |
| `ServiceEvenement` | `EvenementController`, `FrontEvenementController` | PARTIAL | Align role-specific event operations. |
| `EventStatsService`, `StatsService` | `StatsController`, admin forum stats | PARTIAL | Expand to full event/course/forum/admin metrics parity. |
| `ServiceParticipation` | `ParticipantController`, `ParticipationRepository` | PARTIAL | Align constraints, validation, and export/reporting. |
| `ServiceSponsoringPack` | `SponsoringPackController`, `FrontSponsoringPackController` | PARTIAL | Align booking/payment states and admin review flows. |
| `StripeService` | (no clear Symfony Stripe service) | MISSING/PARTIAL | Add/align Stripe checkout/webhook/payment status flows. |
| `EmailService` | Symfony mailer usage | PARTIAL | Align templates and trigger points (verification/reset/notifications). |
| `ModerationService` (forum/course) | `BadWordsDetectorService`, forum admin controllers | PARTIAL | Add unified moderation decisions/status pipeline. |
| `FantasyChatbotService`, `FactsService`, `QuotesService` | `ArtChatbotService`, `MLArtChatbotService`, `AIService` | PARTIAL | Align prompt behavior/fallback providers. |
| `PinterestService`, `RecommendationService` | `InspirationService`, `StockedImageService` | PARTIAL | Align recommendation inputs and output metadata. |
| `VoiceSearchService` | (none obvious) | MISSING | Add voice search feature if required by parity scope. |
| `YouTubeVideoSummaryService` | (none obvious) | MISSING | Add summarization module if it is core to final Java behavior. |
| `SpotifyService` | (none obvious) | MISSING | Implement only if in project requirements/demo scope. |
| `GoogleAuthService` | `SecurityController` (KnpU OAuth config exists) | PARTIAL | Confirm full social login parity with Java. |
| `CloudinaryService` | (no clear dedicated service) | PARTIAL | Decide Cloudinary vs local uploads; align media handling. |

## 3) Controller/UI Flow Parity (High-Level)

| Java Flow | Symfony Equivalent | Status | Action |
|---|---|---|---|
| Front auth screens (`signin`, `signup`, `reset`, `email verification`) | `SecurityController`, `RegistrationController` + templates | PARTIAL | Align all edge-case flows and messaging. |
| Profile (`view/edit/password`) | front profile templates/controllers | PARTIAL | Align all editable fields and validations. |
| Role request + history | (no clear explicit module) | MISSING/PARTIAL | Implement dedicated role request feature. |
| Front gallery + art details | `GalleryController`, `ArtDetailController` | MATCHED/PARTIAL | Align actions (like/fav/view/comment/report). |
| Front forum module | `ForumController` + admin forum controllers | PARTIAL | Align moderation requests, status filtering, stats views. |
| Front courses list/player | `CourseFrontController`, artist course controllers | PARTIAL | Align player/progress/certificates. |
| Front events role-specific pages | `FrontEvenementController` + related controllers | PARTIAL | Align participant/sponsor/artiste variants. |
| Sponsoring pack browse + reserve | `FrontSponsoringPackController` | PARTIAL | Align reservation, payment, and status workflow. |
| Backoffice global/admin dashboard | `BackController`, `AdminController`, stats controllers | PARTIAL | Expand dashboards to Java parity KPIs and sections. |
| Admin users/users stats | `AdminController`, `NormalUserController`, `StatsController` | PARTIAL | Add explicit user stats and role request moderation pages. |
| Admin events/courses/forum stats pages | partial controllers exist | PARTIAL | Fill missing pages and chart data endpoints. |
| Marketplace backoffice pages (`products`) | none obvious | MISSING | Build admin product CRUD + stock/ordering management. |

## 4) Priority Execution Plan

### Phase 1: Foundation (Week 1)
1. Create `docs/parity_feature_checklist.md` with one row per Java feature and acceptance criteria.
2. Finalize entity parity diff and add missing schema migrations (especially `RoleRequest` and marketplace entities).
3. Fix critical static/runtime issues (typed returns, query consistency) before parity feature work.

### Phase 2: Identity + Role Governance (Week 1-2)
1. Align full auth lifecycle (signup/signin/reset/email verification/social login).
2. Implement role request + history + admin approval/rejection workflow.
3. Align route guards and role-based redirects with Java behavior.

### Phase 3: Core Modules Parity (Week 2-3)
1. Events role-specific flows and stats parity.
2. Courses management/player parity, including moderation and progress.
3. Forum moderation/request pipeline and admin stats parity.
4. Art module parity for like/dislike/favorite/view/comment.

### Phase 4: Commerce + Payments (Week 3-4)
1. Implement marketplace (`Produit`, cart, orders, order lines).
2. Align sponsoring reservation/payment states with Stripe flow.
3. Add admin product/order management and reporting.

### Phase 5: Observability + Demo Hardening (Week 4)
1. Add integration tests for critical user journeys.
2. Add parity smoke test script/checklist for demo preparation.
3. Final UX consistency pass (wording, statuses, error messages).

## 5) Immediate Action Backlog (next coding tasks)

1. Add missing role-request module in Symfony (entity, migration, repository, controller, templates, admin actions).
2. Add explicit art like/dislike persistence if currently absent.
3. Create marketplace foundation entities and migrations (`Produit`, `Panier`, `LignePanier`, `Commande`, `LigneCommande`).
4. Expand stats endpoints to match Java admin dashboards.
5. Stabilize repository typing issues flagged by PHPStan in `src/Repository` to reduce hidden bugs.

## 6) Notes

- Ignore files under `var/cache/` for implementation planning; they are generated.
- Use Java app as behavior source of truth; where web UX differs, keep behavior parity first and adapt presentation second.
- Keep migrations small and reversible to avoid breaking ongoing team work.

## UI-First Track

A dedicated UI-first execution plan is available in: docs/ui_first_parity_plan.md`r
Use this as the active implementation order, while keeping this file as the master parity matrix.

