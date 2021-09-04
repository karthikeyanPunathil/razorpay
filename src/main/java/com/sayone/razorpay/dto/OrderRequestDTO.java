package com.sayone.razorpay.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String id;
    private Integer amount;
    private Integer amount_paid;
    private Integer amount_due;
    private String currency;
    private String receipt;
    private String status;
    private Integer attempts;
    //private Map notes;
    private Integer created_at;
}
