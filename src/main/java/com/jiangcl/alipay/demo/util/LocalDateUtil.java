package com.jiangcl.alipay.demo.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangcl
 * @date 2020/9/9
 * @desc
 */
public class LocalDateUtil {

    public static void localDateFormat(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.now();
        String dateStr = localDate.format(format);
        System.out.println(dateStr);

        LocalDate parse = LocalDate.parse("2019-09-09");
        System.out.println(parse.format(format));

        LocalDate date = LocalDate.of(2018, 9, 21);
        System.out.println(date.format(format));
    }

    public static void localDateTimeFormat(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateStr);

        LocalDateTime parse = LocalDateTime.parse("2007-12-03T10:15:30");
        System.out.println(parse.format(format));

        LocalDateTime dateTime = LocalDateTime.of(2020, 8, 12, 12, 23, 42);
        System.out.println(dateTime.format(format));
    }

    public static void dateChange(){
        LocalDate localDate = LocalDate.now();

        //当月第一天
        LocalDate firstDateOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDateOfMonth);

        //当月最后一天
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDayOfMonth);

        //当月第一个周一
        LocalDate firstMonday = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstMonday);

        //当月最后一个周一
        LocalDate lastMonday = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
        System.out.println(lastMonday);

        //向前或者向后一周
        LocalDate plusOneWeek = localDate.plusWeeks(1);
        System.out.println(plusOneWeek);

        //向前或者向后一天
        LocalDate plusOneDay = localDate.plusDays(1);
        System.out.println(plusOneDay);

        //向前或者向后一个月
        LocalDate plusOneMonth = localDate.plusMonths(1);
        System.out.println(plusOneMonth);

        //向前或者向后一年
        LocalDate plusOneYear = localDate.plusYears(1);
        System.out.println(plusOneYear);
    }

    public static void localDateTimeChange(){
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
        //localDateTime的api与LocalDate类似，此处不再举例
    }

    public static void main(String[] args) {
        //localDateFormat();
        //localDateTimeFormat();
        //dateChange();
        localDateTimeChange();
        //monday("2020-08-02");
    }

    /**
     * @desc 获取指定月份到现在为止的所有星期一
     * @author jiangcl
     * @date 2020/9/9
     * @param str
     * @return void
     */
    public static void monday(String str){

        //格式化字符串
        LocalDate sourceDate = LocalDate.parse(str);
        //获取当前日期
        LocalDate nowDate = LocalDate.now();
        //创建一个集合，用于存储符合要求的日期
        List<LocalDate> dates = new ArrayList<>();
        /**
         * 获取指定月份的每一个周一
         * 若周一的日期大于当前日期则退出循环，否则继续寻找
         */
        LocalDate localDate = sourceDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        while (true){
            if(localDate.isAfter(nowDate)){
                break;
            }else {
                dates.add(localDate);
            }
            localDate = localDate.plusWeeks(1);
        }

        dates.forEach(System.out::println);

    }
}
