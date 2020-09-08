package ru.ashirobokov.payment.validate.account.flow;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentValidateAccountProcessor {

    String INPUT = "input";

    @Input
    SubscribableChannel input();

    @Output
    MessageChannel validOut();

    @Output
    MessageChannel validFail();

}
