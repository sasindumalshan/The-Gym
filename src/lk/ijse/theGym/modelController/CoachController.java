package lk.ijse.theGym.modelController;

import lk.ijse.theGym.dto.CoachDTO;
import lk.ijse.theGym.to.Coach;
import lk.ijse.theGym.to.Customer;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachController {

    public static boolean save(Coach coach) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                coach.getCoach_id(),
                coach.getFist_name(),
                coach.getLast_name(),
                coach.getAddress_street(),
                coach.getAddress_city(),
                coach.getAddress_lene(),
                coach.getE_mail(),
                coach.getBirthday(),
                coach.getNic(),
                coach.getContact_number(),
                coach.getGender(),
                coach.getRegister_date(),
                coach.getSalary_id(),
                coach.getCoach_type()


        );
    }

    public static boolean update(Coach coach) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE coach SET fist_name=?,last_name=?,address_city=?,address_street=?,address_lene=?,e_mail=?,contact_number=?,nic=?,birthday=? WHERE coach_id=?",
                coach.getFist_name(),
                coach.getLast_name(),
                coach.getAddress_city(),
                coach.getAddress_street(),
                coach.getAddress_lene(),
                coach.getE_mail(),
                coach.getContact_number(),
                coach.getNic(),
                coach.getBirthday(),
                coach.getCoach_id()

        );
    }

    public static boolean remove(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM coach WHERE coach_id=?",text);
    }

    public static String countCoach() throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT COUNT(*) FROM coach"));
    }

    public static String countSalaryBySalaryId(String salary_id) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT COUNT(*) FROM coach WHERE salary_Id=?",salary_id));
    }

    public static boolean idExists(String id) throws SQLException, ClassNotFoundException {
        ResultSet set= CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_id=?",id);
        return set.next();
    }

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next())return resultSet.getString(1);
        return null;
    }

    public static List<CoachDTO> findCoach() throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM coach"));
    }

    private static List<CoachDTO> setDTOs(ResultSet resultSet) throws SQLException {
        List<CoachDTO> list=new ArrayList<>();
        while (resultSet.next()){
            CoachDTO dto=new CoachDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14)
            );
            list.add(dto);
        }
        return list;
    }

    public static ResultSet getLastaId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT  coach_id FROM coach ORDER BY LENGTH(coach_id),coach_id");
    }

    public static ResultSet getAllForID(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM coach WHERE coach_id=?",id);
    }

    public static ResultSet getSearchIdData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_id LIKE ?",id+"%");
    }

    public static ResultSet getSearchNameData(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE fist_name LIKE ? OR last_name LIKE ?",name+"%",name+"%");
    }

    public static ResultSet getSearchAllForID(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT *FROM coach WHERE coach_id=?",id);
    }

    public static ResultSet getALlId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_type=?","Company");
    }

    public static ResultSet getDataForId(String valueOf) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT fist_name,last_name ,nic,coach_id,salary_id FROM coach WHERE coach_id=?",valueOf);
    }
}
