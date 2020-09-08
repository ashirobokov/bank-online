package ru.ashirobokov.payment.validate.phone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentValidatePhoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentValidatePhoneApplication.class, args);
        log.info("PaymentValidatePhoneApplication started ... ");
    }
}
