package ru.ashirobokov.payment.abs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.abs.model.Payment;

import java.util.function.Function;

@Slf4j
@Component
public class PaymentAbsStub {

    @Bean
    public Function<Payment, Payment> balance() {
        return payment -> {
            payment.setBalance(true);
            log.info("Balance check for {} done ", payment);
            return payment;
        };
    }

    @Bean
    public Function<Payment, Payment> payment() {
        return payment -> {
            payment.setPayment(true);
            payment.setComment("Payment successfully done");
            log.info("Payment operation for {} done ", payment);
            return payment;
        };
    }

}
