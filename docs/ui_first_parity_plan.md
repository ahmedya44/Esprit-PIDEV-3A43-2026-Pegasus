# UI-First Parity Plan (JavaFX -> Symfony Twig)

Goal: make Symfony screens feel and behave as close as possible to the Java desktop app first, then tighten backend parity behind them.

## Working Rules
- Keep existing Symfony routes/controllers whenever possible.
- Rebuild Twig templates in Java screen order, not by entity order.
- Introduce a shared design system first (`layout`, components, spacing, typography, colors).
- Use feature flags or safe fallbacks if a backend field/endpoint is not ready.

## Phase 0: UI Foundation (start here)

1. Create a single front design token file (`templates/front/layout.html.twig` + shared CSS variables).
2. Create a single backoffice design token file (`templates/back/layout.html.twig` + shared CSS variables).
3. Build reusable Twig partials/components:
   - header/nav/sidebar
   - page title + breadcrumbs
   - stats cards
   - table shell (filters/search/pagination)
   - form shell (sections + actions)
   - empty/loading/error states
4. Match Java spacing, card density, table look, button hierarchy, and status badge colors.

## Phase 1: Highest-Visibility Front Screens

Implement in this exact order:

1. `FrontLayout.fxml` -> `templates/front/layout.html.twig`
2. `home-view.fxml` -> `templates/front/index.html.twig`
3. `signin-view.fxml` + `signup-view.fxml` -> `templates/security/login.html.twig`, `templates/security/register.html.twig`
4. `profile-view.fxml` + `profile-edit-view.fxml` + `profile-password-view.fxml` -> `templates/front/profile.html.twig`, `templates/front/profile_edit.html.twig`
5. `gallery-main-view.fxml` / `gallery-view.fxml` / `gallery-final.fxml` -> `templates/front/gallery.html.twig`, `templates/front/gallery_new.html.twig`, `templates/front/gallery_edit.html.twig`
6. `art-detail-view.fxml` -> `templates/front/art_detail.html.twig`
7. `forum-view.fxml` -> `templates/forum/index.html.twig`
8. `CoursesContent.fxml` + `CoursePlayerContent.fxml` -> `templates/front/courses.html.twig`, `templates/course_front/learn.html.twig`
9. Events front set:
   - `liste-evenement-*.fxml`
   - `details-evenement-*.fxml`
   - `ajouter-evenement.fxml`
   - `modifier-evenement-view.fxml`
   -> `templates/front/evenement/*.html.twig`
10. Sponsoring flow:
   - `sponsoring-pack-view.fxml`
   - `reserver-pack-view.fxml`
   -> `templates/front/sponsoring_pack/*.html.twig`

## Phase 2: Backoffice/Admin Screens

Implement in this order:

1. `AdminLayout.fxml` -> `templates/back/layout.html.twig`
2. `AdminHomeContent.fxml` -> `templates/back/index.html.twig`
3. `AdminUsersContent.fxml` + `AdminUsersStatsContent.fxml` -> `templates/normal_user/*.html.twig` + `templates/back/participant/stats.html.twig` (or dedicated admin users stats page)
4. `AdminEventsContent.fxml` + participant/sponsor/event stats screens -> `templates/back/evenement/*.html.twig`
5. `AdminCoursesContent.fxml` + `AdminCourseStatsContent.fxml` -> `templates/back/*courses*` + `templates/back/charts/*.html.twig`
6. `AdminForumContent.fxml` + `AdminForumRequestsContent.fxml` + `AdminForumStatsContent.fxml` -> `templates/admin/forum/*.html.twig`
7. `AdminSponsoringPacksContent.fxml` -> `templates/admin/sponsoring_pack/*.html.twig`
8. `AdminQuizzesContent.fxml` + `QuizQuestionsContent.fxml` -> `templates/back/quizzes/*.html.twig`, `templates/front/quizzes/*.html.twig`
9. `AdminProductsContent.fxml` -> MISSING module in Symfony (parked until product entities are added)

## Phase 3: Screen-by-Screen Parity Checklist Template

For each page, complete this checklist:

1. Layout parity: header/sidebar/footer and page structure match Java.
2. Component parity: cards/tables/forms/buttons/badges match Java behavior.
3. Content parity: same fields, labels, helper text, and status names.
4. Interaction parity: same filters, sort, actions, confirmation patterns.
5. Validation parity: same client-facing error placement and wording.
6. Role parity: visibility/actions by role are consistent with Java.
7. Responsive parity: desktop first, then tablet/mobile adaptation.

## Immediate Sprint (UI-first, 1 week)

1. Build shared front/back design tokens and reusable Twig partials.
2. Redesign these 6 pages first:
   - `templates/front/layout.html.twig`
   - `templates/front/index.html.twig`
   - `templates/security/login.html.twig`
   - `templates/security/register.html.twig`
   - `templates/front/gallery.html.twig`
   - `templates/back/layout.html.twig`
3. Demo checkpoint: compare screenshots side-by-side with Java screens.

## Risks and Mitigation

1. Risk: UI parity blocked by missing backend data.
   - Mitigation: render placeholders and disable actions with clear labels until backend is ready.
2. Risk: too many style variants across templates.
   - Mitigation: force all pages to consume shared tokens/components before page-specific CSS.
3. Risk: duplication between `front` and `back` UI styles.
   - Mitigation: shared utility classes + module-specific themes.

## Definition of Done (UI-first)

A screen is done when:
1. Visual structure is close to Java equivalent.
2. All primary user actions are present and discoverable.
3. No broken route/action from that screen.
4. Accessibility basics pass (labels, focus states, contrast).
