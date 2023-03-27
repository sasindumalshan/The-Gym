package lk.ijse.theGym.util.Alert;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;

public class MyAlert {

    public static boolean isButtonClick = false;
    public static boolean isOKSelected;
    public static boolean isNoSelected;
    public static boolean isYesSelected = false;
    private static String contText;
    private static MyAlert instance;
    public JFXButton leftBtn;
    public JFXButton rightBtn;
    public JFXButton btnCenter;
    public Text tittle;
    boolean alert;
    boolean confirmation;
    boolean no;
    boolean cancel;
    boolean yes;
    boolean oneButton;
    boolean towButton;
    boolean startThread;
    boolean ThreadStop = true;

    public MyAlert() {
        instance = this;
    }


    public MyAlert(String contText, MyButtonType myButtonType1) {
        switch (myButtonType1) {
            case CONFORMATION:towButton =true;break;
            case WARNING:oneButton=true;break;
        }
        MyAlert.contText = contText;

    }

    public static MyAlert getInstance() {
        return instance;
    }

    public void showAert() {
        try {
            Navigation.popupNavigation("Alert.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }


        setTitle();
        setButtonType();


    }

    private void setButtonType() {
        if (towButton) {
            MyAlert.getInstance().rightBtn.setText("NO");
            MyAlert.getInstance().leftBtn.setText("Yes");


        }
        if (oneButton) {
            if (cancel) {
                btnCenter.setText("OK");
            }

        }


    }

    private void setTitle() {
        System.out.println(contText);
        MyAlert.getInstance().tittle.setText(contText);
    }

    public void leftBtnOnAction(ActionEvent actionEvent) {
        isButtonClick=true;
        System.out.println("yes");
        isYesSelected=true;
        System.out.println(isYesSelected);
        close(actionEvent);
    }

    public void rightBtnOnAction(ActionEvent actionEvent) {
        isButtonClick=true;
        System.out.println("right");
        isNoSelected=true;
        close(actionEvent);
    }

    public void centeBTNOnAction(ActionEvent actionEvent) {
        isButtonClick=true;
        System.out.println("center");
       isOKSelected =true;
        close(actionEvent);
    }



    private void close(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public enum MyButtonType {
        WARNING, CONFORMATION
    }


}
