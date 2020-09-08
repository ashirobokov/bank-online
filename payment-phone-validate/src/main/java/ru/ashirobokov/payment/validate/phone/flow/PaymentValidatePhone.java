package ru.ashirobokov.payment.validate.phone.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.validate.phone.model.Payment;

import java.util.function.Predicate;

@Slf4j
@Component
public class PaymentValidatePhone {
    private static final int PHONE_NUMBER_LENGTH = 10;
    private static final String PAYMENT_CURRENCY = "RUB";
    private static final Predicate<Payment> validConditions = payment ->
            getPhoneNumberLength(payment) == PHONE_NUMBER_LENGTH &&
                    currencyCheck(payment);
    @Autowired
    private PaymentValidatePhoneProcessor processor;

    private static final int getPhoneNumberLength(Payment payment) {
        if (payment.getPhoneNumber() != null) {
            return payment.getPhoneNumber().length();
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

    @StreamListener(PaymentValidatePhoneProcessor.INPUT)
    public void validator(Payment payment) {
        log.info(" .. PaymentValidatePhone for Payment = {}", payment);
        if (validConditions.test(payment)) {
            processor.validOut().send(message(payment));
        } else {
            log.info(" .... [Error] Payment validation failed for Payment = {}", payment);
            payment.setComment("Phone Number / or Payment Currency are incorrect for payId = " + payment.getPayId());
            processor.validFail().send(message(payment));
        }
    }

}
