package lk.ijse.theGym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/lk/ijse/theGym/view/LoginFrom.fxml"));
        Scene scene = new Scene(rootNode);
        // stage.getIcons().addAll(new Image("/view/assest/icon/image-removebg-preview (11).png"));
        stage.setTitle("The Gym");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();

    }
}
