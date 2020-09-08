package ru.ashirobokov.payment.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsumerApplication.class, args);
        log.info("PaymentConsumerApplication started ... ");
    }

}
