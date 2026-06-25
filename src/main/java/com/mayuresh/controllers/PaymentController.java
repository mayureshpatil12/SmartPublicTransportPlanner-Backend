package com.mayuresh.controllers;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayuresh.dto.PaymentRequest;
import com.mayuresh.dto.PaymentSaveRequest;
import com.mayuresh.entities.Payment;
import com.mayuresh.repositiories.PaymentRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin("*")
public class PaymentController {

	@Autowired
	RazorpayClient razorpayClient;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	UniversalResponse response;

	@PostMapping("/create-order")
	public ResponseEntity<ResponseWrapper> createOrder(@RequestBody PaymentRequest paymentRequest) throws Exception
	{
		int amountInPaise=(int)(paymentRequest.getAmount()*100);

		JSONObject orderRequest=new JSONObject();
		orderRequest.put("amount", amountInPaise);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "yatrasetu_receipt");

		Order order=razorpayClient.orders.create(orderRequest);
		return response.send("Payment order created", order.toString(), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper> savePayment(@RequestBody PaymentSaveRequest paymentSaveRequest)
	{
		Payment payment=new Payment();
		payment.setOrderId(paymentSaveRequest.getOrderId());
		payment.setPaymentId(paymentSaveRequest.getPaymentId());
		payment.setAmount(paymentSaveRequest.getAmount());
		payment.setStatus(paymentSaveRequest.getStatus());
		payment.setUserEmail(paymentSaveRequest.getUserEmail());

		Payment savedPayment=paymentRepository.save(payment);
		return response.send("Payment saved", savedPayment, HttpStatus.CREATED);
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<ResponseWrapper> getPaymentsByUser(@PathVariable String email)
	{
		List<Payment> payments=paymentRepository.findByUserEmailOrderByCreatedAtDesc(email);
		return response.send("Following payments found", payments, HttpStatus.OK);
	}
}
