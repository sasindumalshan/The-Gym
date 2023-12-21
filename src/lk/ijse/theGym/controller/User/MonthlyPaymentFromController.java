package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.bar.MonthlyPaymentBarFromController;
import lk.ijse.theGym.model.CustomerController;
import lk.ijse.theGym.model.CustomerPaymentController;
import lk.ijse.theGym.model.PackController;
import lk.ijse.theGym.to.CustomerPayment;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;
import lk.ijse.theGym.view.data.packageDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MonthlyPaymentFromController implements Initializable {
    static String id = null;
    static String name = null;
    static String packages = null;
    static String price = null;
    static String total = null;
    public AnchorPane anchorpane;
    public ScrollPane scrollPane;
    public VBox vBox;
    public JFXTextField search;
    public JFXTextField lblId;
    public JFXTextField lblBalance;
    public Text txtName;
    public Text txtNic;
    public Text txtEmail;
    public Text txtPayment;
    public Text txtBalance;
    public JFXButton btnPay;
    public Text txtPackage1Name;
    public Text txtPackage1Price;
    public Text txtPackage2Name;
    public Text txtPackage2Price;
    public Text txtPackage3Name;
    public Text txtPackage3Price;
    public Pane panePack1;
    public Pane panePack2;
    public Pane panePack3;
    public JFXComboBox comboSelectMonth;
    public JFXComboBox comboSelectYear;
    boolean package1Selected = false;
    boolean package2Selected = false;
    boolean package3Selected = false;
    ArrayList<packageDetails> packageDetails = new ArrayList<>();
    ArrayList<String> currentYear = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> lblId.requestFocus());
        anchorpane.setStyle("-fx-background-color: transparent");
        loadTodayPayment();
        setPackageDetails();


        comboSelectMonth.setValue(DateTimeUtil.monthNow());
        comboSelectYear.setValue(DateTimeUtil.yearNow());
        if (currentYear.isEmpty()) {
            setYears();
        }
        setMonth();
    }

    private void setMonth() {
        comboSelectMonth.getItems().clear();
        comboSelectMonth.getItems().addAll(allMonth);
    }

    private void setYears() {
        try {
            ResultSet set = CustomerController.getAllYears();
            while (set.next()) {
                currentYear.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        comboSelectYear.getItems().addAll(currentYear);
    }

    private void setPackageDetails() {
        try {
            ResultSet set = PackController.getDetail();
            while (set.next()) {
                packageDetails.add(new packageDetails(
                        set.getString(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i < packageDetails.size() - 2; i++) {
            txtPackage1Name.setText(packageDetails.get(i).getPackageName());
            txtPackage2Name.setText(packageDetails.get(i + 1).getPackageName());
            txtPackage3Name.setText(packageDetails.get(i + 2).getPackageName());
            txtPackage1Price.setText(packageDetails.get(i).getPrice());
            txtPackage2Price.setText(packageDetails.get(i + 1).getPrice());
            txtPackage3Price.setText(packageDetails.get(i + 2).getPrice());
        }

    }

    private void loadTodayPayment() {
        clear();
        String[] dateSplit = DateTimeUtil.dateNow().split("-");
        setDataPayment(dateSplit[0], DateTimeUtil.monthNow());
    }

    private void setDataPayment(String year, String month) {
        try {
            ResultSet set = CustomerPaymentController.getPaymentDetails(year, month);
            while (set.next()) {
                navigation(set.getString(1), set.getString(2) + " " + set.getString(3), set.getString(4), set.getString(5));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void idOnAction(ActionEvent actionEvent) {
        if (lblId.getText().equals("")) {
            lblId.requestFocus();
        } else {
            lblBalance.requestFocus();
        }

    }

    public void balanceOnAction(ActionEvent actionEvent) {
        if (lblId.getText().equals("") & lblBalance.getText().equals("")) {
            if (lblId.getText().equals("")) {
                lblId.requestFocus();
            }
        } else {
            btnPay.fire();
        }
        if (lblId.getText().equals("")) {
            lblId.requestFocus();
        }

    }

    private int getIndex() {
        if (package1Selected | package2Selected | package3Selected) {
            System.out.println("selected");

            System.out.println("packageDetails.size() " + packageDetails.size());
            for (int i = 0; i < packageDetails.size(); i++) {
                if (package1Selected) {
                    System.out.println("package1Selected");
                    if (packageDetails.get(i).getPackageName().equals(txtPackage1Name.getText())) {
                        System.out.println("package1Selected Return");
                        return i;

                    }
                }
                if (package2Selected) {
                    System.out.println("package2Selected");
                    if (packageDetails.get(i).getPackageName().equals(txtPackage2Name.getText())) {
                        System.out.println("package2Selected Return");
                        return i;
                    }
                }
                if (package3Selected) {
                    System.out.println("package3Selected");
                    if (packageDetails.get(i).getPackageName().equals(txtPackage3Name.getText())) {
                        System.out.println("package3Selected Return");
                        return i;
                    }
                }
            }
        } else {
            String packageId = null;
            try {
                ResultSet set = CustomerController.getPackage(lblId.getText());
                if (set.next()) {
                    packageId = set.getString(1);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            for (int i = 0; i < packageDetails.size(); i++) {
                if (packageDetails.get(i).getId().equals(packageId)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void payOnAction(ActionEvent actionEvent) {

        if (lblBalance.getText().equals("") | Double.parseDouble(txtBalance.getText().equals("") ? "-1" : txtBalance.getText()) < 0) {
            Notification.notificationWARNING("Pleace Enter Balance", "your balance are not valid or empty");
        } else {
            try {
                int packageDetailsIndex = getIndex();
                System.out.println("packageDetailsIndex" + packageDetailsIndex);
                if (packageDetailsIndex > 0) {

                }
                if (package1Selected | package2Selected | package3Selected) {
                    if (CustomerController.updatePackage(lblId.getText(), packageDetails.get(packageDetailsIndex).getId())) {
                        System.out.println("updated package");
                    }
                }


                int monthIndex = -1;
                String[] split = DateTimeUtil.dateNow().split("-");
                int year = Integer.parseInt(split[0]);
                for (int j = 0; j < allMonth.length; j++) {
                    if (DateTimeUtil.monthNow().equals(allMonth[j])) {
                        monthIndex = j;
                    }
                }
                boolean addPayment = false;
                for (int j = 0; j < Integer.parseInt(packageDetails.get(packageDetailsIndex).getPackageDuration()); j++) {
                    if (monthIndex == 12) {
                        monthIndex = 0;
                        year++;
                    }
                    id = lblId.getText();
                    name = txtName.getText();
                    packages = txtPayment.getText().equals("1000") ? "monthly" : "update package";
                    price = txtPayment.getText();
                    total = txtPayment.getText();
                    if (CustomerPaymentController.addPayment(new CustomerPayment(
                            String.valueOf(Double.valueOf(txtPayment.getText()) / Double.valueOf(packageDetails.get(packageDetailsIndex).getPackageDuration())),
                            DateTimeUtil.dateNow(),
                            allMonth[monthIndex],
                            lblId.getText(),
                            String.valueOf(year)

                    ))) {
                        monthIndex++;
                        addPayment = true;
                    }
                }
                if (addPayment) {
                    Notification.notification(" Order added success", "order saved account");

//                    new Alert(Alert.AlertType.CONFIRMATION, "order Added").show();

//                    ============================================================

                    InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/Blank_A4_3.jrxml");
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("name", name);
                    hm.put("id", id);
                    hm.put("package", packages);
                    hm.put("price", price);
                    hm.put("total", total);

                    try {
                        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                        JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, new JREmptyDataSource());
                        JasperViewer.viewReport(print, false);

                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }
//                    =============================================
                    txtPayment.setText("");
                    txtEmail.setText("");
                    txtNic.setText("");
                    txtBalance.setText("");
                    txtName.setText("");
                    lblId.clear();
                    lblBalance.clear();
                    clearPackage();
                }


            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            comboSelectMonth.setValue(DateTimeUtil.monthNow());
            comboSelectYear.setValue(DateTimeUtil.yearNow());

            clear();
            loadTodayPayment();
        }

    }

    private void clearPackage() {
        if (package1Selected) {
            package1Selected = false;
            panePack1.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");

        }
        if (package2Selected) {
            package2Selected = false;
            panePack2.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");

        }
        if (package3Selected) {
            package3Selected = false;
            panePack3.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");

        }
    }


    public void idOnKeyTyped(KeyEvent keyEvent) {
        searchId();
    }

    private void searchId() {
        try {
            if (CustomerController.isIdExists(lblId.getText())) {
                System.out.println("is id exists");
                lblId.setStyle("-fx-border-color: white;-fx-text-fill: white;");
                if (CustomerPaymentController.isAlreadyPay(lblId.getText())) {
                    clearPackage();
//                    new Alert(Alert.AlertType.WARNING, "Already pay this month").show();
                    Notification.notificationWARNING("Already pay this month","payed");
                    lblId.clear();
                } else {
                    ResultSet set = CustomerController.getIdForData(lblId.getText());
                    if (set.next()) {
                        txtName.setText(set.getString(2) + " " + set.getString(3));
                        txtEmail.setText(set.getString(4));
                        txtNic.setText(set.getString(5));
                        if (package2Selected | package1Selected | package3Selected) {
                        } else {
                            txtPayment.setText(set.getString(6));
                        }

                    }
                }
            } else {
                lblId.setStyle("-fx-border-color: red;-fx-text-fill: red;");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnPay, lblBalance, lblBalance.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: #FBA23E")) {

            txtBalance.setText("");
            double payment = Double.parseDouble(txtPayment.getText());
            double balance = 0;
            if (!lblBalance.getText().equals("")) {
                balance = Double.parseDouble(lblBalance.getText());
            }
            if (balance - payment < 0) {
                lblBalance.setStyle("-fx-border-color: red;-fx-text-fill: red;");
            } else {
                lblBalance.setStyle("-fx-border-color: white;-fx-text-fill: white;");
                txtBalance.setText(String.valueOf(balance - payment));
            }
        }
    }

    public void idOnReleased(KeyEvent keyEvent) {
        txtName.setText("");
        txtPayment.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        searchId();
    }

    private void navigation(String id, String name, String fees, String date) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/MonthlyPaymentBarFrom.fxml"));
            Parent root = loader.load();
            MonthlyPaymentBarFromController controller = loader.getController();
            controller.setData(id, name, fees, date);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void package1OnMouseClick(MouseEvent mouseEvent) {
        if (package1Selected) {
            package1Selected = false;
            panePack1.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");

        } else {
            panePack1.setStyle("-fx-pref-height: 70;-fx-pref-width: 155;-fx-font-size: 25;-fx-background-color: #F79727;  -fx-effect: dropshadow( gaussian ,#F79727,50,0.1,0,0);");
            panePack2.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
            panePack3.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
            package2Selected = false;
            package3Selected = false;
            package1Selected = true;

        }
        if (package1Selected) {
            txtPayment.setText(txtPackage1Price.getText());
        } else {
            txtPayment.setText("");
        }

    }

    public void package2OnMouseClick(MouseEvent mouseEvent) {
        if (package2Selected) {
            package2Selected = false;
            panePack2.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
        } else {
            package1Selected = false;
            package3Selected = false;
            package2Selected = true;
            panePack2.setStyle("-fx-pref-height: 70;-fx-pref-width: 155;-fx-font-size: 25;-fx-background-color: #F79727;  -fx-effect: dropshadow( gaussian ,#F79727,50,0.1,0,0);");
            panePack3.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
            panePack1.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
        }

        if (package2Selected) {
            txtPayment.setText(txtPackage2Price.getText());
        } else {
            txtPayment.setText("");
        }

    }

    public void package3OnMouseClick(MouseEvent mouseEvent) {
        if (package3Selected) {
            package3Selected = false;
            panePack3.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");

        } else {
            package2Selected = false;
            package1Selected = false;
            package3Selected = true;
            panePack3.setStyle("-fx-pref-height: 70;-fx-pref-width: 155;-fx-font-size: 25;-fx-background-color: #F79727;  -fx-effect: dropshadow( gaussian ,#F79727,50,0.1,0,0);");
            panePack1.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
            panePack2.setStyle("-fx-background-color: #373C42;  -fx-shape:\"M 100 250 L 600 250 L 600 500 L 500 550 L 100 550 L 100 250   \";  -fx-border-color: white;  -fx-font-weight: bold;  -fx-font-size: 20;  -fx-text-fill: white;");
        }

        if (package3Selected) {
            txtPayment.setText(txtPackage3Price.getText());
        } else {
            txtPayment.setText("");
        }
    }

    public void selectMonthOnAction(ActionEvent actionEvent) {
        clear();
        setDataPayment(String.valueOf(comboSelectYear.getValue()), String.valueOf(comboSelectMonth.getValue()));
    }

    public void selectYearOnAction(ActionEvent actionEvent) {
        clear();
        setDataPayment(String.valueOf(comboSelectYear.getValue()), String.valueOf(comboSelectMonth.getValue()));
    }

    private void clear() {
        vBox.getChildren().clear();
    }

    public void searchOnReleased(KeyEvent keyEvent) {
        clear();
        try {
            if (search.getText().equals("")) {
                ids.clear();
                loadTodayPayment();
            } else {
                clear();
                setResult(CustomerPaymentController.getSearchNameResult(search.getText(), String.valueOf(comboSelectMonth.getValue()), String.valueOf(comboSelectYear.getValue())));
                setResult(CustomerPaymentController.getSearchIdResult(search.getText(), String.valueOf(comboSelectMonth.getValue()), String.valueOf(comboSelectYear.getValue())));
            }

            loadSearchIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadSearchIds() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < ids.size(); i++) {
            ResultSet set = CustomerPaymentController.getIdDetails(ids.get(i), String.valueOf(comboSelectYear.getValue()), String.valueOf(comboSelectMonth.getValue()));
            if (set.next()) {
                navigation(set.getString(1), set.getString(2) + " " + set.getString(3), set.getString(4), set.getString(5));
            }
        }
    }

    private void setResult(ResultSet set) throws SQLException {
        while (set.next()) {
            if (ids.isEmpty()) {
                ids.add(set.getString(1));
            } else {
                for (int i = 0; i < ids.size(); i++) {
                    if (!ids.get(i).equals(set.getString(1))) {
                        ids.add(set.getString(1));
                    }
                }
            }
        }
    }


}
