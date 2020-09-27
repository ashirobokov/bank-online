package ru.ashirobokov.payment.validate.card;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import ru.ashirobokov.payment.validate.card.flow.PaymentValidateCardProcessor;

@EnableBinding(PaymentValidateCardProcessor.class)
@Slf4j
@SpringBootApplication
public class PaymentValidateCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentValidateCardApplication.class, args);
        log.info("PaymentValidateCardApplication started ... ");
    }

}
