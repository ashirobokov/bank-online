package ru.ashirobokov.payment.abs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentAbsApplication {
    public static void main (String[] args) {
        SpringApplication.run(PaymentAbsApplication.class, args);
        log.info("PaymentABSApplication started ... ");
    }

}
