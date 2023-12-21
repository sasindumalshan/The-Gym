package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.bar.StoreViewBarFromController;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StoreViewFromController implements Initializable {


    public Text txtAllItems;
    public Text txtLimitedItems;
    public JFXTextField search;
    public ScrollPane scrollPane;
    public VBox vBox;
    public JFXComboBox comboCategory;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("CashiarDashBordFrom.fxml", actionEvent);
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        ArrayList<String> id = new ArrayList<>();
        if (search.getText().equals("")) {
            vBox.getChildren().clear();
            loadAllItems();
        } else {
            vBox.getChildren().clear();
            try {
                ArrayList<ItemDTO> list = ItemModel.findByLike(search.getText() + "%");
                for (ItemDTO itemDTO:list) {
                    navigation(itemDTO);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLimitedItem();
        loadAllItems();
        loadAllCategory();
        comboCategory.setValue("All Items");
        setAllItemsCount();
    }

    private void setLimitedItem() {
        try {
            txtLimitedItems.setText(ItemModel.countLimitedItem());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setAllItemsCount() {
        try {
            txtAllItems.setText(ItemModel.countByItem());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllCategory() {
        try {
            ArrayList<String> category = ItemModel.findCategory();
            comboCategory.getItems().addAll(category);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllItems() {
        vBox.getChildren().clear();
        try {
            ArrayList<ItemDTO> all = ItemModel.findAll();
            for (ItemDTO itemDTO:all) {
                navigation(itemDTO);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CategoryOnAction(ActionEvent actionEvent) {
        vBox.getChildren().clear();
        if (String.valueOf(comboCategory.getValue()).equals("All Items")) {
            loadAllItems();
        } else {
            try {
                ArrayList<ItemDTO> list = ItemModel.findByCategory(String.valueOf(comboCategory.getValue()));
                for (ItemDTO itemDTO:list) {
                    navigation(itemDTO);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void navigation(ItemDTO itemDTO) {

        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/StroeViewBarFrom.fxml"));
            Parent root = loader.load();
            StoreViewBarFromController controller = loader.getController();
            controller.setData(itemDTO);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
