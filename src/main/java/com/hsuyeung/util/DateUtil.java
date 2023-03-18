package com.hsuyeung.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期相关工具类
 *
 * @author hsuyeung
 * @date 2022/02/24
 */
public final class DateUtil {
    /**
     * 将 LocalDateTime 转为符合 RFC822 标准的字符串
     *
     * @param time   时间
     * @param locale 语言，该参数会影响输出的字符串的语言
     * @return 类似 Sun, 05 Mar 23 12:54:54 GMT 这样的字符串
     */
    public static String formatLocalDateTimeToRFC822String(LocalDateTime time, Locale locale) {
        // RFC822 标准要求时区必须使用 GMT 表示，所以需要将系统时区的值转为 GMT 时区的值
        time = toUtc(time);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneId.of("GMT"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", locale);
        return zonedDateTime.format(formatter);
    }

    /**
     * 将 time 从 fromZone 时区的值转到 toZone 时区的值
     *
     * @param time     时间
     * @param fromZone 被转换的时区
     * @param toZone   转换为的时区
     * @return 转换后的时间
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId fromZone, ZoneId toZone) {
        return time.atZone(fromZone).withZoneSameInstant(toZone).toLocalDateTime();
    }

    /**
     * 将 time 从系统默认时区的值转到 toZone 时区的值
     *
     * @param time   时间
     * @param toZone 转换为的时区
     * @return 转换后的时间
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId toZone) {
        return DateUtil.toZone(time, ZoneId.systemDefault(), toZone);
    }

    /**
     * 将 time 从 fromZone 时区的值转到 {@link ZoneOffset#UTC} 时区的值
     *
     * @param time     时间
     * @param fromZone 被转换的时区
     * @return 转换后的时间
     */
    public static LocalDateTime toUtc(LocalDateTime time, ZoneId fromZone) {
        return DateUtil.toZone(time, fromZone, ZoneOffset.UTC);
    }

    /**
     * 将 time 从系统默认时区的值转到 {@link ZoneOffset#UTC} 时区的值
     *
     * @param time 时间
     * @return 转换后的时间
     */
    public static LocalDateTime toUtc(LocalDateTime time) {
        return DateUtil.toUtc(time, ZoneId.systemDefault());
    }

    private DateUtil() {
    }
}
