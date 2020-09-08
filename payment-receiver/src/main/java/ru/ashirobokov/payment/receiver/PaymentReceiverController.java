package ru.ashirobokov.payment.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import ru.ashirobokov.payment.receiver.model.Payment;

import java.util.function.Supplier;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class PaymentReceiverController {

    EmitterProcessor<Payment> processor = EmitterProcessor.create();

    @RequestMapping(path = "/api/v1/send", method = POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delegateToSupplier(@RequestBody Payment payment) {
        log.info(".. PaymentReceiverController get RequestBody = {}", payment);
        processor.onNext(payment);
    }

    @Bean
    public Supplier<Flux<Payment>> receiver() {
        return () -> this.processor;
    }

}
