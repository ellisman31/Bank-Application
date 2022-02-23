package com.bankapplication.bankapplication.Util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class Util {

    public Timestamp currentDate() {
        Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTimeInMillis());
    }

    public BigDecimal defaultBalance() {
        return new BigDecimal(0);
    }

}
