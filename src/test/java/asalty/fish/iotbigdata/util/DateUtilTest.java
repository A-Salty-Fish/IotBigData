package asalty.fish.iotbigdata.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 15:19
 */

public class DateUtilTest {

    @Test
    public void getThisYearBeginAndEndDate() {
        System.out.println(Arrays.toString(DateUtil.getThisYearBeginAndEndDate()));
    }

    @Test
    public void getThisMonthBeginAndEndDate() {
        System.out.println(Arrays.toString(DateUtil.getThisMonthBeginAndEndDate()));
    }

    @Test
    public void getThisWeekBeginAndEndDate() {
        System.out.println(Arrays.toString(DateUtil.getThisWeekBeginAndEndDate()));
    }

    @Test
    public void getThisDayBeginAndEndDate() {
        System.out.println(Arrays.toString(DateUtil.getThisDayBeginAndEndDate()));
    }

    @Test
    public void getLast13MonthBegin() {
        System.out.println(Arrays.toString(DateUtil.getLast13MonthBegin()));
    }
}
