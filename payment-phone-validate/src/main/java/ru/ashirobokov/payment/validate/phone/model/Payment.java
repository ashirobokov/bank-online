package ru.ashirobokov.payment.validate.phone.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    private Long payId;
    private String userId;
    private String personName;
    private String cardNumber;
    private String phoneNumber;
    private String companyName;
    private Long inn;
    private Long bankBik;
    private String corrAccountNumber;
    private String accountNumber;
    private String currency;
    private BigDecimal sum;
    private String paymentType;
    private Boolean balance;
    private Boolean payment;
    private String comment;
}
