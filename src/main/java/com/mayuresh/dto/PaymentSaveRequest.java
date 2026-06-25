package com.mayuresh.dto;

import lombok.Data;

@Data
public class PaymentSaveRequest {
	private String orderId;
	private String paymentId;
	private Double amount;
	private String status;
	private String userEmail;
}
