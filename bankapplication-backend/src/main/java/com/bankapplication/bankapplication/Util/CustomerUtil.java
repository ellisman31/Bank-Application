package com.bankapplication.bankapplication.Util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class CustomerUtil {

    public Timestamp registrationDate() {
        Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTimeInMillis());
    }

    public BigDecimal defaultBalance() {
        return new BigDecimal(0);
    }

}
