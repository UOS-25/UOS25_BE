package com.uos25.uos25.orders.dto;

import com.uos25.uos25.orders.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class OrdersDTO {


    private Long id;
    private String orderNumber;
    private Long storeId;
    private LocalDate orderDate;
    private String productCode;
    private int counts;
    private boolean confirm;

    public static OrdersDTO toOrdersDTO(Orders orders){
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(orders.getId());
        ordersDTO.setOrderNumber(orders.getOrderNumber());
        ordersDTO.setStoreId(orders.getStore().getId());
        ordersDTO.setOrderDate(orders.getOrderDate());
        ordersDTO.setProductCode(orders.getProduct().getProductCode());
        ordersDTO.setCounts(orders.getCounts());
        ordersDTO.setConfirm(ordersDTO.confirm);
        return ordersDTO;
    }


}
