package com.example.UrlShorter.common;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;

@Service
public class CommonUtils {
    public Date getOrUpdateExpirationDate(int day, Date date) {
        Calendar calendar = null;
        if (ObjectUtils.isEmpty(date)) {
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, 10);
        } else {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, day);
        }

        return calendar.getTime();
    }
}
