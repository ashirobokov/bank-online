package ru.ashirobokov.payment.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentProcessorApplication {
    public static void main (String[] args) {
        SpringApplication.run(PaymentProcessorApplication.class, args);
        log.info("PaymentProcessorApplication started ... ");
    }
}
