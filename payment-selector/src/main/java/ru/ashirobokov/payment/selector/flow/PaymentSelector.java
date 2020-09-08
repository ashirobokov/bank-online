package ru.ashirobokov.payment.selector.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.selector.model.Payment;

@Slf4j
@Component
public class PaymentSelector {

    @Autowired
    private PaymentSelectorProcessor processor;

    @StreamListener(PaymentSelectorProcessor.INPUT)
    public void selector(Payment payment) {
        log.info(" .. PaymentSelector get Payment = {}", payment);

        if (payment.getPaymentType().equals("account")) {
            processor.account().send(message(payment));
        } else if (payment.getPaymentType().equals("card")) {
            processor.card().send(message(payment));
        } else if (payment.getPaymentType().equals("mobile")) {
            processor.phone().send(message(payment));
        }
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

}
