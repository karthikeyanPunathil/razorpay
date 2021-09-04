package com.sayone.razorpay.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sayone.razorpay.dto.OrderRequestDTO;
import com.sayone.razorpay.model.PaymentOrder;
import com.sayone.razorpay.repository.PaymentRepository;
import com.sayone.razorpay.util.Signature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    PaymentRepository paymentOrderRepository;



    private static final String apiKey = "****************";

    private static final String secret = "***********************";


    @PostMapping("/orders")
    public String createPayemtOrder(@RequestBody OrderRequestDTO orderReq) {

        Random rand = new Random();

        String orderId = null;
        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", orderReq.getAmount());
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "transaction_order"+rand.nextInt(1000));
            Order order = razorpay.Orders.create(orderRequest);
            orderId=order.get("id");

        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }

        return orderId;
    }



    //@GetMapping("/create-signature")
    public String createSignature(String oderId, String secret) throws SignatureException {

        String generated_signature = Signature.calculateRFC2104HMAC(oderId, secret);

        return generated_signature;





    }
    @GetMapping("verify-signature")
    public String verifySignature(@RequestParam String orderId, @RequestParam String paymentId,
                                  @RequestParam String razorpaySignature, @RequestParam(required = false) Long customerId,
                                  @RequestParam(required = false) Long subscriptionId, @RequestParam(required = false) Long companyId,
                                  @RequestParam(required = false) Long appointmentId) throws SignatureException {

        String result = null;

        try {
            String generated_signature = createSignature(orderId + "|" + paymentId, secret);


            if (generated_signature .equals(razorpaySignature) )
            {

                PaymentOrder orderDetails = new PaymentOrder();

                if(customerId!=null) {
                    orderDetails.setOrderId(orderId);
                    orderDetails.setPaymentId(paymentId);
                    orderDetails.setRazorpaySignature(razorpaySignature);

                    paymentOrderRepository.save(orderDetails);
                }
                result ="success";
            }
            else
                result ="failed";

        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return result;



    }



    private String hmac_sha256(String string, String secret) {
        // TODO Auto-generated method stub
        return null;
    }


}
