package ru.ashirobokov.payment.validate.card.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.validate.card.model.Payment;

import java.util.function.Predicate;

@Slf4j
@Component
public class PaymentValidateCard {
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final String PAYMENT_CURRENCY = "RUB";
    private static final Predicate<Payment> validConditions = payment ->
            getCardNumberLength(payment) == CARD_NUMBER_LENGTH &&
                    currencyCheck(payment);
    @Autowired
    private PaymentValidateCardProcessor processor;

    private static final int getCardNumberLength(Payment payment) {
        if (payment.getCardNumber() != null) {
            return payment.getCardNumber().replaceAll("\\s+", "").length();
        } else {
            return 0;
        }
    }

    private static final boolean currencyCheck(Payment payment) {
        return payment.getCurrency().equals(PAYMENT_CURRENCY);
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

    @StreamListener(PaymentValidateCardProcessor.INPUT)
    public void validator(Payment payment) {
        log.info(" .. Payment Validate Card for Payment = {}", payment);
        if (validConditions.test(payment)) {
            processor.validOut().send(message(payment));
        } else {
            log.info(" .... [Error] Payment validation failed for Payment = {}", payment);
            payment.setComment("Card Number / or Payment Currency are incorrect for payId = " + payment.getPayId());
            processor.validFail().send(message(payment));
        }
    }

}
