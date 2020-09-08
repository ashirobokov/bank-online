package ru.ashirobokov.payment.validate.card;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentValidateCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentValidateCardApplication.class, args);
        log.info("PaymentValidateCardApplication started ... ");
    }

}
