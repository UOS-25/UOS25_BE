package com.uos25.uos25.orders.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.orders.dto.OrderSummaryDTO;
import com.uos25.uos25.orders.dto.OrdersDTO;
import com.uos25.uos25.orders.dto.OrdersDeleteDTO;
import com.uos25.uos25.orders.dto.OrdersSaveDTO;
import com.uos25.uos25.orders.service.OrdersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Orders", description = "Orders API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    //발주 저장
    @PostMapping("/save")
    public ResponseEntity<?> saveOrder(@RequestBody Map<String, Object> requestBody, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        OrdersSaveDTO ordersSaveDTO = new OrdersSaveDTO();
        ordersSaveDTO.setOrderNumber((String) requestBody.get("orderNumber"));

        @SuppressWarnings("unchecked")
        Map<String, Integer> productsMap = (Map<String, Integer>) requestBody.get("productsMap");

        ordersService.saveOrders(ordersSaveDTO, principalDetails.getId(), productsMap);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/board")
    public  ResponseEntity<?> findOrderSummariesByStoreId(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<OrderSummaryDTO> orderSummaryDTOList = ordersService.findOrderSummariesByStoreId(principalDetails.getId());
        return ResponseEntity.ok(orderSummaryDTOList);
    }
    //오더 번호로 발주 조회
    @GetMapping("/findByOrderNumber")
    public ResponseEntity<?> findByOrderNumber(@RequestParam String orderNumber, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<OrdersDTO> ordersDTOList = ordersService.findByOrderNumber(orderNumber, principalDetails.getId());
        System.out.println(orderNumber);
        return ResponseEntity.ok().body(ordersDTOList);
    }

    //오더 날짜로 발주 조회
    @GetMapping("/findByOrderDate")
    public ResponseEntity<?> findByOrderDate(@RequestParam String orderDate, @AuthenticationPrincipal PrincipalDetails principalDetails){
        LocalDate date = LocalDate.parse(orderDate);
        List<OrdersDTO> ordersDTOList = ordersService.findByOrderDate(date, principalDetails.getId());
        return ResponseEntity.ok().body(ordersDTOList);
    }

    //상품 번호로 발주 조회
    @GetMapping("/findByProductCode")
    public ResponseEntity<?> findByProductCode(@RequestParam String productCode, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<OrdersDTO> ordersDTOList = ordersService.findByProductCode(productCode, principalDetails.getId());
        return ResponseEntity.ok().body(ordersDTOList);
    }

    //가게 코드로 발주 조회(로그인만 하면 됨)
    @GetMapping("/findByStoreId")
    public ResponseEntity<?> findByStoreId(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<OrdersDTO> ordersDTOList = ordersService.findByStoreId(principalDetails.getId());
        return ResponseEntity.ok().body(ordersDTOList);
    }

    //확인 여부로 발주 조회
    @GetMapping("/findByConfirm")
    public ResponseEntity<?> findByConfirm(@RequestParam boolean confirmValue, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<OrdersDTO> ordersDTOList = ordersService.findByConfirm(confirmValue, principalDetails.getId());
        return ResponseEntity.ok().body(ordersDTOList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteByProductProductCodeAndStoreIdAndOrderNumber(@RequestBody OrdersDeleteDTO ordersDeleteDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        ordersService.cancelByOrderId(ordersDeleteDTO.getOrderId(), ordersDeleteDTO.getReason(), principalDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> orderConfirm(@RequestParam String orderNumber, @RequestParam String productCode, @RequestParam boolean confirm, @RequestParam String orderDate,@AuthenticationPrincipal PrincipalDetails principalDetails){
        LocalDate date = LocalDate.parse(orderDate);
        ordersService.orderConfirm(orderNumber, productCode, confirm, principalDetails.getId(), date);
      return ResponseEntity.ok().build();
    }


}
