package com.ngoctuan.ecommerce.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String fromDateToString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}
