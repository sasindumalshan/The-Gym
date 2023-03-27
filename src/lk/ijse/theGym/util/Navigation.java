package lk.ijse.theGym.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.theGym.controller.EmployeeDetailBar;
import lk.ijse.theGym.controller.StoreFromController;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class Navigation {
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;


    public static void swatchNavigation(String link, ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Navigation.class.getResource("/lk/ijse/theGym/view/" + link));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void swatchNavigation(String link, javafx.scene.input.MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Navigation.class.getResource("/lk/ijse/theGym/view/" + link));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void popupNavigation(String link) throws IOException {
        URL resource = Navigation.class.getResource("/lk/ijse/theGym/view/"+link);
        Parent parent = FXMLLoader.load(resource);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void close(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
         stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void close(javafx.scene.input.MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
