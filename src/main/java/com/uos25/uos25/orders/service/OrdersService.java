package com.uos25.uos25.orders.service;

import com.uos25.uos25.orders.dto.OrdersDTO;
import com.uos25.uos25.orders.dto.OrdersSaveDTO;
import com.uos25.uos25.orders.entity.Orders;
import com.uos25.uos25.orders.repository.OrdersRepository;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.repository.ProductsRepository;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {
    private final StoreRepository storeRepository;
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;


    //발주 저장
    //오류처리 해야됨
    public void saveOrders(OrdersSaveDTO ordersSaveDTO, Long storeId, Map<String, Integer> productsMap) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        for (Map.Entry<String, Integer> entry : productsMap.entrySet()) {
            String productCode = entry.getKey();
            int counts = entry.getValue();

            Products product = productsRepository.findByProductCode(productCode)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Orders orders = Orders.toSaveOrders(ordersSaveDTO, store, product, counts);
            ordersRepository.save(orders);
        }
    }

    //발주 넘버로 조회
    //오류처리 해야됨.
    public List<OrdersDTO> findByOrderNumber(String orderNumber, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByOrderNumber(orderNumber);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }


    //오더 날짜로 발주 조회
    public List<OrdersDTO> findByOrderDate(LocalDate orderDate, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByOrderDate(orderDate);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //상품 번호로 발주 조회
    public List<OrdersDTO> findByProductCode(String productCode, Long storeId){
        List<Orders> ordersList = ordersRepository.findByProductCode(productCode);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //가게 코드로 발주 조회(로그인만 하면 됨)
    public List<OrdersDTO> findByStoreId(Long storeId){
        List<Orders> ordersList = ordersRepository.findByStoreId(storeId);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //확인 여부로 발주 조회
    public List<OrdersDTO> findByConfirm(boolean confirmValue, Long storeId){
        List<Orders> ordersList = ordersRepository.findByConfirm(confirmValue);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

}
