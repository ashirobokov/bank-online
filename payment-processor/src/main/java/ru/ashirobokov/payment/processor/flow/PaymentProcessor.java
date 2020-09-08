package ru.ashirobokov.payment.processor.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import ru.ashirobokov.payment.processor.model.Payment;

import java.util.function.Function;

@Slf4j
@Component
public class PaymentProcessor {

    @Bean
    public Function<Payment, Payment> checkBalance() {
      return payment -> {
          log.info("Payment send for balance check = {}", payment);
          return payment;
      };
    }

    @Bean
    public Function<Flux<Payment>, Tuple2<Flux<Payment>, Flux<Payment>>> checkResult() {
        return flux -> {
            Flux<Payment> connectedFlux = flux.publish().autoConnect(2);
            UnicastProcessor checkOk = UnicastProcessor.create();
            UnicastProcessor checkFail = UnicastProcessor.create();
            Flux<Payment> okFlux = connectedFlux.filter(payment -> payment.getBalance() == true).doOnNext(payment -> checkOk.onNext(payment)).log(".... balance check = true ");
            Flux<Payment> failFlux = connectedFlux.filter(payment -> payment.getBalance() == false).doOnNext(payment -> checkFail.onNext(payment)).log(".... balance check = false ");

            return Tuples.of(Flux.from(checkOk).doOnSubscribe(x -> okFlux.subscribe()), Flux.from(checkFail).doOnSubscribe(x -> failFlux.subscribe()));
        };
    }

    public Function<Payment, Payment> startPayment() {
        return payment -> {
            log.info("Payment send for pay operation = {}", payment);
            return payment;
        };

    }

    @Bean
    public Function<Flux<Payment>, Tuple2<Flux<Payment>, Flux<Payment>>> payResult() {
        return flux -> {
            Flux<Payment> connectedFlux = flux.publish().autoConnect(2);
            UnicastProcessor payOk = UnicastProcessor.create();
            UnicastProcessor payFail = UnicastProcessor.create();
            Flux<Payment> okFlux = connectedFlux.filter(payment -> payment.getPayment() == true).doOnNext(payment -> payOk.onNext(payment)).log(".... payment done ");
            Flux<Payment> failFlux = connectedFlux.filter(payment -> payment.getPayment() == false).doOnNext(payment -> payFail.onNext(payment)).log(".... payment rejected ");

            return Tuples.of(Flux.from(payOk).doOnSubscribe(x -> okFlux.subscribe()), Flux.from(payFail).doOnSubscribe(x -> failFlux.subscribe()));
        };
    }

}
