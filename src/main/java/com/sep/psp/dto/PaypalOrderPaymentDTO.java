package com.sep.psp.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaypalOrderPaymentDTO {

    private String userEmail;
    private String order;
    private String orderDescription;
    private String price;
    private String currency;
    private String agencyPib;
    private String date = LocalDate.now().toString();

    @Override
    public String toString() {
        return "PaypalOrderPaymentDTO{" +
                "userEmail='" + userEmail + '\'' +
                ", order='" + order + '\'' +
                ", orderDescription='" + orderDescription + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", agencyPib='" + agencyPib + '\'' +
                ", date=" + date +
                '}';
    }
}
