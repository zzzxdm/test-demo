package com.zzz.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/9/22
 * @Version V1.0
 */
@Slf4j
public class CronUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String time2Cron(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            throw new RuntimeException("时间字符串不能为空！");
        }
        boolean legalTime;
        Date date;
        if (timeStr.length() == 10) {
            date = parseDate(timeStr, YYYY_MM_DD);
        } else if (timeStr.length() == 19) {
            date = parseDate(timeStr, YYYY_MM_DD_HH_MM_SS);
        } else {
            throw new RuntimeException("时间字符串长度不符！");
        }
        legalTime = date == null ? false : true;
        if (legalTime) {
            return transTime2Cron(timeStr);
        } else {
            throw new RuntimeException("时间格式错误！");
        }
    }

    private static String transTime2Cron(String timeStr) {
        // cron表达式格式 秒  分  时  日  月  周  年
        if (timeStr.length() == 10) {
            String[] dateArr = timeStr.split("-");
            return String.format("* * * %s %s ? %s", dateArr[2], dateArr[1], dateArr[0]);
        } else {
            String[] strArr = timeStr.split(" ");
            String[] dateArr = strArr[0].split("-");
            String[] timeArr = strArr[1].split(":");
            return String.format("%s %s %s %s %s ? %s", timeArr[2], timeArr[1], timeArr[0], dateArr[2], dateArr[1], dateArr[0]);
        }
    }


    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("date parse error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(time2Cron("2022-09-22"));
        System.out.println(time2Cron("2022-09-22 11:30:00"));
        System.out.println(time2Cron("2022-09-22-11:30:00"));
    }

}
