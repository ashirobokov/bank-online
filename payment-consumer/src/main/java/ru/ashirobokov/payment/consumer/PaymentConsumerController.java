package ru.ashirobokov.payment.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import ru.ashirobokov.payment.consumer.model.Payment;

import java.util.function.Consumer;

@Slf4j
@RestController
public class PaymentConsumerController {

    private final EmitterProcessor<Payment> successProcessor = EmitterProcessor.create();
    private final EmitterProcessor<Payment> validationFailedProcessor = EmitterProcessor.create();
    private final EmitterProcessor<Payment> paymentRejectedProcessor = EmitterProcessor.create();

    @GetMapping(value = "/payment_success", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Payment> sendPaymentSucceed() {
        return this.successProcessor;
    }

    @GetMapping(value = "/validation_failed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Payment> sendValidationFailed() {
        return this.validationFailedProcessor;
    }

    @GetMapping(value = "/payment_rejected", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Payment> sendPaymentRejected() {
        return this.paymentRejectedProcessor;
    }

    @Bean
    public Consumer<Flux<Payment>> getterSuccess() {
        return recordFlux ->
                recordFlux
                        .doOnNext(value -> successProcessor.onNext(value))
//                        .doOnNext(this.streamProcessor::onNext)
                        .doOnNext(value -> log.info(" .. PaymentConsumerSuccess value = {}", value))
                        .subscribe();
    }

    @Bean
    public Consumer<Flux<Payment>> getterFail() {
        return recordFlux ->
                recordFlux
                        .doOnNext(value -> validationFailedProcessor.onNext(value))
//                        .doOnNext(this.streamProcessor::onNext)
                        .doOnNext(value -> log.info(" .. PaymentConsumerFail value = {}", value))
                        .subscribe();
    }

    @Bean
    public Consumer<Flux<Payment>> getterReject() {
        return recordFlux ->
                recordFlux
                        .doOnNext(value -> paymentRejectedProcessor.onNext(value))
                        .log(" .. Payment Rejected")
                        .subscribe();
    }

}
