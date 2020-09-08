package ru.ashirobokov.payment.validate.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentValidateAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentValidateAccountApplication.class, args);
        log.info("PaymentValidateAccountApplication started ... ");
    }
}
