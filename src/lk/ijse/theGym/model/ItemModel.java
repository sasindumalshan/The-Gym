package lk.ijse.theGym.model;


import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {

    public static boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                itemDTO.getItem_id(),
                itemDTO.getItem_name(),
                itemDTO.getCategory(),
                itemDTO.getQut(),
                itemDTO.getPrice(),
                itemDTO.getBrand(),
                itemDTO.getDescription());
    }

    public static boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET item_name=?, category=?,price=?,brand=?,description=?,qut=? WHERE item_id=?";
        return CrudUtil.crudUtil(sql,
                itemDTO.getItem_name(),
                itemDTO.getCategory(),
                itemDTO.getPrice(),
                itemDTO.getBrand(),
                itemDTO.getDescription(),
                itemDTO.getQut(),
                itemDTO.getItem_id());
    }

    public static boolean delete(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM item WHERE item_id=?", text);
    }

    public static ArrayList<ItemDTO> findAll() throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT *FROM item"));
    }

    public static ArrayList<ItemDTO> findIds() throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT item_id FROM item"));
    }

    public static ItemDTO findById(String itemCode) throws SQLException, ClassNotFoundException {
        return setDTO(CrudUtil.crudUtil("SELECT * FROM item WHERE item_id=?", itemCode));
    }

    public static ArrayList<ItemDTO> findByCategory(String category) throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM item WHERE category=?", category));
    }

    public static ArrayList<ItemDTO> findByLike(String text) throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM item WHERE item_id LIKE ? OR item_name LIKE ? OR category LIKE ? OR qut LIKE ? OR price LIKE ? OR brand LIKE ?",
                text,
                text,
                text,
                text,
                text,
                text
        ));
    }

    public static ArrayList<String> findCategory() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT DISTINCT category FROM item");
        ArrayList<String> list = new ArrayList<>();
        list.add("All Items");
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public static String countByItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM item");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static String countLimitedItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(qut) FROM item WHERE qut < 5");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static boolean isExistId(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT *from item WHERE item_id=?", id);
        return set.next();
    }

    private static ItemDTO setDTO(ResultSet resultSet) throws SQLException {
        ItemDTO itemDTO = new ItemDTO();
        if (resultSet.next()){
            itemDTO.setItem_id(resultSet.getString(1));
            itemDTO.setItem_name(resultSet.getString(2));
            itemDTO.setCategory(resultSet.getString(3));
            itemDTO.setQut(Integer.parseInt(resultSet.getString(4)));
            itemDTO.setPrice(Double.parseDouble(resultSet.getString(5)));
            itemDTO.setBrand(resultSet.getString(6));
            itemDTO.setDescription(resultSet.getString(7));
        }
        return itemDTO;
    }

    private static ArrayList<ItemDTO> setDTOs(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        while (resultSet.next()) {
            itemDTOS.add(findById(resultSet.getString(1)));
        }
        return itemDTOS;
    }

}
