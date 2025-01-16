package dad.Main;

import dad.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TellMeAStoryApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        RootController root = new RootController();
        stage.setTitle("Tell Me A Story");
        stage.setScene(new Scene(root.getRoot()));
        stage.show();

    }
}
