package ru.ashirobokov.payment.selector.flow;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentSelectorProcessor {

    String INPUT = "input";

    @Input
    SubscribableChannel input();

    @Output
    MessageChannel account();

    @Output
    MessageChannel card();

    @Output
    MessageChannel phone();

}
