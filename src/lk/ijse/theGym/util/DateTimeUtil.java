package lk.ijse.theGym.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static String dateNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public static String yearNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(new Date());
    }

    public static int getDays(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        return yearMonthObject.lengthOfMonth();

    }

    public static String monthNow() {
        LocalDate localDate = LocalDate.now();
        return String.valueOf(localDate.getMonth());
    }

    public static String timeNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        System.out.println(dateFormat.format(new Date()));
        return dateFormat.format(new Date());
    }

    public static String formatDatePatten(LocalDate date) {
        String formattedDate = "0000-00-00";
        try {
            formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException e) {
        }
        return formattedDate;
    }
}
