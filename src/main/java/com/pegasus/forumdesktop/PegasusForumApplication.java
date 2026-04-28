package com.pegasus.forumdesktop;

import com.pegasus.forumdesktop.controller.ForumController;
import com.pegasus.forumdesktop.dao.CommentDao;
import com.pegasus.forumdesktop.dao.PostDao;
import com.pegasus.forumdesktop.dao.RatingDao;
import com.pegasus.forumdesktop.dao.TranslationDao;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.service.AuthService;
import com.pegasus.forumdesktop.service.AiAutocompleteClient;
import com.pegasus.forumdesktop.service.ForumService;
import com.pegasus.forumdesktop.service.GifSearchClient;
import com.pegasus.forumdesktop.service.ModerationService;
import com.pegasus.forumdesktop.service.TranslationApiClient;
import com.pegasus.forumdesktop.view.ForumView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PegasusForumApplication extends Application {
    @Override
    public void start(Stage stage) {
        UserDao userDao = new UserDao();
        PostDao postDao = new PostDao(userDao);
        CommentDao commentDao = new CommentDao(userDao);
        RatingDao ratingDao = new RatingDao();
        TranslationDao translationDao = new TranslationDao();
        ForumService forumService = new ForumService(
            postDao,
            commentDao,
            ratingDao,
            translationDao,
            userDao,
            new ModerationService(),
            new TranslationApiClient(),
            new AiAutocompleteClient(),
            new GifSearchClient()
        );
        AuthService authService = new AuthService(userDao);
        ForumView view = new ForumView();

        new ForumController(view, authService, forumService);

        Scene scene = new Scene(view.getRoot(), 1320, 820);
        var css = getClass().getResource("/com/pegasus/forumdesktop/app.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("Pegasus Forum - JavaFX MVC");
        stage.setMinWidth(1120);
        stage.setMinHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
