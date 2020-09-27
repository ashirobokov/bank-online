package ru.ashirobokov.payment.abs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.abs.model.Payment;

import java.math.BigDecimal;
import java.util.function.Function;

@Slf4j
@Component
public class PaymentAbsStub {
    private final BigDecimal LIMIT_MOBILE_PHONE = new BigDecimal("100.00");
    private final BigDecimal LIMIT_BANK_PAYMENT = new BigDecimal("5000.00");

    @Bean
    public Function<Payment, Payment> balance() {
        return payment -> {
            if (balanceCheck(payment)) {
                payment.setComment("Balance validation is OK for payId = " + payment.getPayId());
                payment.setBalance(true);
            } else {
                payment.setComment("Payment [for payId = " + payment.getPayId() + "] exceeds the required limit");
                payment.setBalance(false);
            }
            log.info("Balance check for {} done ", payment.getPayId());
            return payment;
        };
    }

    @Bean
    public Function<Payment, Payment> payment() {
        return payment -> {
            if (operationCheck(payment)) {
                log.info("Payment operation for {} done ", payment.getPayId());
                payment.setComment("Payment for payId = " + payment.getPayId() + " successfully done");
                payment.setPayment(true);
            } else {
                log.info("Payment operation for {} rejected by Banking System ", payment.getPayId());
                payment.setComment("Payment for payId = " + payment.getPayId() + " rejected by Banking System ");
                payment.setPayment(false);
            }

            return payment;
        };
    }

    private boolean balanceCheck(Payment payment) {
        if (payment.getPaymentType().equals("mobile_phone")) {
            return payment.getSum().compareTo(LIMIT_MOBILE_PHONE) < 0 ? true : false;
        } else {
            return payment.getSum().compareTo(LIMIT_BANK_PAYMENT) < 0 ? true : false;
        }
    }

    private boolean operationCheck(Payment payment) {
        return payment.getPayId() % 5 != 0 ? true : false;
    }
}
