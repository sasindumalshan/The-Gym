package lk.ijse.theGym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/theGym/view/LoginFrom.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/theGym/view/Admin/AdminDashBordFrom.fxml"));
        primaryStage.setTitle("The Gym");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
