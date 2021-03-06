package ru.ashirobokov.payment.selector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import ru.ashirobokov.payment.selector.flow.PaymentSelectorProcessor;

@EnableBinding(PaymentSelectorProcessor.class)
@Slf4j
@SpringBootApplication
public class PaymentSelectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentSelectorApplication.class, args);
        log.info("PaymentSelectorApplication started ... ");
    }
}
