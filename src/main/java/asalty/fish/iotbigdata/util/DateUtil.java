package asalty.fish.iotbigdata.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 14:41
 */

public class DateUtil {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String[] getThisYearBeginAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String beginDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.YEAR, 1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        return new String[]{beginDate, endDate};
    }

    public static String[] getThisMonthBeginAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String beginDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        return new String[]{beginDate, endDate};
    }

    public static String[] getThisWeekBeginAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        String beginDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        return new String[]{beginDate, endDate};
    }

    public static String[] getThisDayBeginAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        String beginDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        return new String[]{beginDate, endDate};
    }

    public static String[] getLast13MonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -12);
        String[] result = new String[14];
        for (int i = 0; i <= 13; i++) {
            result[i] = simpleDateFormat.format(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
        }
        return result;
    }
}
