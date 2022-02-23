package com.bankapplication.bankapplication.Util;

import com.bankapplication.bankapplication.Service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@Component
@NoArgsConstructor
public class Util {

    private Random random = new Random();

    public Timestamp currentDate() {
        Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTimeInMillis());
    }

    public BigDecimal defaultBalance() {
        return new BigDecimal(0);
    }

    public String getBankAccountNumber(UserService userService) {

        long firstPartBankAccountNumber = 0L;
        long secondPartBankAccountNumber = 0L;
        while(true) {
            long randomBankNumber = random.nextLong();
            if (randomBankNumber > 0 && String.valueOf(randomBankNumber).length() == 15) {
                firstPartBankAccountNumber = randomBankNumber;
                break;
            }
        }

        while(true) {
            long randomBankNumber = random.nextLong();
            if (randomBankNumber > 0 && String.valueOf(randomBankNumber).length() == 15) {
                secondPartBankAccountNumber = randomBankNumber;
                break;
            }
        }

        String bankAccountNumber = "113-" + firstPartBankAccountNumber + "-" + secondPartBankAccountNumber;
        if (userService.findUserBankAccountNumber(bankAccountNumber) == null) {
            return bankAccountNumber;
        }
        else {
            return getBankAccountNumber(userService);
        }

    }

}
