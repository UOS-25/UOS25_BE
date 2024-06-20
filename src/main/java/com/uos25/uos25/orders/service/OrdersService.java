package com.uos25.uos25.orders.service;

import com.uos25.uos25.orders.dto.OrderSummaryDTO;
import com.uos25.uos25.orders.dto.OrdersDTO;
import com.uos25.uos25.orders.dto.OrdersSaveDTO;
import com.uos25.uos25.orders.entity.Orders;
import com.uos25.uos25.orders.entity.OrdersCancel;
import com.uos25.uos25.orders.repository.OrdersCancelRepository;
import com.uos25.uos25.orders.repository.OrdersRepository;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.repository.ProductsRepository;
import com.uos25.uos25.stock.service.StockService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {
    private final StoreRepository storeRepository;
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;
    private final StockService stockService;
    private final OrdersCancelRepository ordersCancelRepository;


    //발주 저장
    @Transactional
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
    @Transactional
    public List<OrdersDTO> findByOrderNumber(String orderNumber, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByOrderNumber(orderNumber);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }


    //오더 날짜로 발주 조회
    @Transactional
    public List<OrdersDTO> findByOrderDate(LocalDate orderDate, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByOrderDate(orderDate);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //상품 번호로 발주 조회
    @Transactional
    public List<OrdersDTO> findByProductCode(String productCode, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByProductCode(productCode);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //가게 코드로 발주 조회(로그인만 하면 됨)
    @Transactional
    public List<OrdersDTO> findByStoreId(Long storeId) {
        List<Orders> ordersList = ordersRepository.findByStoreId(storeId);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //확인 여부로 발주 조회
    @Transactional
    public List<OrdersDTO> findByConfirm(boolean confirmValue, Long storeId) {
        List<Orders> ordersList = ordersRepository.findByConfirm(confirmValue);
        return ordersList.stream()
                .filter(order -> order.getStore().getId().equals(storeId))
                .map(OrdersDTO::toOrdersDTO)
                .collect(Collectors.toList());
    }

    //발주 취소
    @Transactional
    public void cancelByOrderId(Long orderId, String reason, Long storeId) {
        Optional<Orders> orders = ordersRepository.findById(orderId);
        if (orders.isPresent() && orders.get().getStore().getId() == storeId) {
            OrdersCancel ordersCancel = new OrdersCancel();
            ordersCancel.setStore(storeRepository.findById(storeId).get());
            ordersCancel.setOrders(orders.get());
            ordersCancel.setLocalDate(LocalDate.now());
            ordersCancel.setReason(reason);
            ordersCancelRepository.save(ordersCancel);
            orders.get().setConfirm(true);
            ordersRepository.save(orders.get());
        }
    }


    //발주 컨펌
    //TODO 이미 1인데 1오거나 0인데 0오면 에러 띄우는거 하긴 해야됨
    //이거로 반품이라고 생각해도 될듯?
    @Transactional
    public void orderConfirm(String orderNumber, String productCode, boolean confirm, Long storeId,
                             LocalDate orderDate) {
        Orders orders = ordersRepository.findByOrderNumberAndProductProductCodeAndStoreIdAndOrderDate(orderNumber,
                productCode, storeId, orderDate);
        orders.setConfirm(confirm);
//        System.out.println("뿌앵");
        if (confirm) {
            stockService.updateStockCounts(storeId, productCode, orders.getCounts());
        } else {
            stockService.updateStockCounts(storeId, productCode, -1 * orders.getCounts());
        }
        ordersRepository.save(orders);
    }

    @Transactional
    public List<OrderSummaryDTO> findOrderSummariesByStoreId(Long storeId) {
        List<Orders> ordersList = ordersRepository.findByStoreId(storeId);

        // orderNumber로 그룹화하고 첫 번째 Order를 가져옵니다.
        Map<String, Orders> orderMap = ordersList.stream()
                .collect(Collectors.toMap(
                        Orders::getOrderNumber,
                        order -> order,
                        (existing, replacement) -> existing
                ));

        // OrderSummaryDTO 리스트로 변환합니다.
        return orderMap.values().stream()
                .map(order -> new OrderSummaryDTO(order.getOrderNumber(), order.getOrderDate()))
                .collect(Collectors.toList());
    }

}
