package ru.ashirobokov.payment.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentReceiverApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentReceiverApplication.class, args);
        log.info("PaymentReceiverApplication started ... ");
    }
}
