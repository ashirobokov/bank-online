package ru.ashirobokov.payment.validate.account.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.ashirobokov.payment.validate.account.model.Payment;

import java.util.function.Predicate;

@Slf4j
@Component
public class PaymentValidateAccount {
    private static final int ACCOUNT_NUMBER_LENGTH = 20;
    private static final String PAYMENT_CURRENCY = "RUB";
    private static final Predicate<Payment> validConditions = payment ->
            getAccountNumberLength(payment) == ACCOUNT_NUMBER_LENGTH &&
                    currencyCheck(payment);
    @Autowired
    private PaymentValidateAccountProcessor processor;

    private static final int getAccountNumberLength(Payment payment) {
        if (payment.getAccountNumber() != null) {
            return payment.getAccountNumber().length();
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

    @StreamListener(PaymentValidateAccountProcessor.INPUT)
    public void validator(Payment payment) {
        log.info(" .. PaymentValidateAccount for Payment = {}", payment);
        if (validConditions.test(payment)) {
            processor.validOut().send(message(payment));
        } else {
            log.info(" .... [Error] Payment validation failed for Payment = {}", payment);
            payment.setComment("Account Number / or Payment Currency are incorrect for payId = " + payment.getPayId());
            processor.validFail().send(message(payment));
        }
    }

}
