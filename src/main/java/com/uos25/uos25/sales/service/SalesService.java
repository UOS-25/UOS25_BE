package com.uos25.uos25.sales.service;

import com.uos25.uos25.common.error.event.EventNotFoundException;
import com.uos25.uos25.event.entity.Event;
import com.uos25.uos25.event.entity.EventType;
import com.uos25.uos25.event.service.EventService;
import com.uos25.uos25.funds.service.FundsService;
import com.uos25.uos25.orders.dto.OrdersDTO;
import com.uos25.uos25.orders.entity.Orders;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.repository.ProductsRepository;
import com.uos25.uos25.sales.dto.SalesDTO;
import com.uos25.uos25.sales.entity.Sales;
import com.uos25.uos25.sales.entity.SalesItem;
import com.uos25.uos25.sales.entity.SalesType;
import com.uos25.uos25.sales.repository.SalesItemRepository;
import com.uos25.uos25.sales.repository.SalesRepository;
import com.uos25.uos25.stock.service.StockService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesService {

    private final StoreRepository storeRepository;
    private final ProductsRepository productsRepository;
    private final SalesRepository salesRepository;
    private final SalesItemRepository salesItemRepository;
    private final StockService stockService;
    private final FundsService fundsService;
    private final EventService eventService;

    //getIsCancelled에서 false 값을 안 받고 기본으로 설정 할 수 있으면 좋음
    @Transactional
    public Long[] saveSales(SalesDTO salesDTO) {
        Store store = storeRepository.findById(salesDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid store ID"));

        Sales sales = Sales.builder()
                .store(store)
                .salesDate(salesDTO.getSalesDate())
                .isCancelled(salesDTO.getIsCancelled())
                .totalAmount(0)
                .type(SalesType.valueOf(salesDTO.getType()))
                .gender(salesDTO.getGender())
                .ageGroup(salesDTO.getAgeGroup())
                .build();

        AtomicInteger money = new AtomicInteger(0);
        AtomicLong movieTicket = new AtomicLong(0);
        List<SalesItem> salesItems = salesDTO.getSalesItems().stream()
                .map(itemDTO -> {
                    Products product = productsRepository.findById(itemDTO.getProductCode())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid product code"));
                    SalesItem salesItem = new SalesItem(sales, product, itemDTO.getCounts()); // Sales 속성과 Product 속성 설정


                    try {
                        Event event = eventService.getEventByProductCode(product.getProductCode());
                        EventType eventType = event.getType();

                        switch (eventType) {
                            case ONE_PLUS_ONE:
                                money.addAndGet(itemDTO.getCounts() * (product.getSalePrice() - product.getOrderPrice()));
                                stockService.updateStockCounts(salesDTO.getStoreId(), product.getProductCode(), -2 * itemDTO.getCounts());
                                break;
                            case MOVIE_GIVEAWAY:
                                money.addAndGet(itemDTO.getCounts() * (product.getSalePrice() - product.getOrderPrice()));
                                stockService.updateStockCounts(salesDTO.getStoreId(), product.getProductCode(), -1 * itemDTO.getCounts());
                                movieTicket.addAndGet(itemDTO.getCounts());
                                break;
                            case DISCOUNT:
                                money.addAndGet((int) (itemDTO.getCounts() * (product.getSalePrice() * (1 - event.getDiscount() / 100.0) - product.getOrderPrice())));
                                stockService.updateStockCounts(salesDTO.getStoreId(), product.getProductCode(), -1 * itemDTO.getCounts());
                                break;
                        }
                    } catch (EventNotFoundException e) {
                        money.addAndGet(itemDTO.getCounts() * (product.getSalePrice() - product.getOrderPrice()));
                        stockService.updateStockCounts(salesDTO.getStoreId(), product.getProductCode(), -1 * itemDTO.getCounts());
                    }
                    return salesItem;
                }).collect(Collectors.toList());
        sales.setTotalAmount(money.get());
        Sales savedSales = salesRepository.save(sales);
        salesItemRepository.saveAll(salesItems);
        switch (sales.getType()){
            case CASH :
                fundsService.sales(money.get(), store.getId());
                fundsService.plusTotalFunds(money.get(), store.getId());
                break;
            default:
                fundsService.sales(money.get(), store.getId());
                break;

        }

        Long[] longs = {savedSales.getId(),  movieTicket.get()};
        return longs;
    }



    //true인데 또 취소하는거 에러띄우긴 해야됨.
    @Transactional
    public Long[] refundSales(Long id, Long storeId) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sales ID"));
        AtomicLong movieTicket = new AtomicLong(0);

        if (!sales.getIsCancelled() && sales.getStore().getId() == storeId) {
            sales.setIsCancelled(true);

//            AtomicInteger refundAmount = new AtomicInteger(0);

            sales.getSalesItems().forEach(salesItem -> {
                Products product = salesItem.getProduct();
                int counts = salesItem.getCounts();
//                refundAmount.addAndGet(counts * (product.getSalePrice() - product.getOrderPrice()));

                try {
                    Event event = eventService.getEventByProductCode(product.getProductCode());
                    EventType eventType = event.getType();

                    switch (eventType) {
                        case ONE_PLUS_ONE:
//                            refundAmount.addAndGet(counts* (product.getSalePrice() - product.getOrderPrice()));
                            stockService.updateStockCounts(sales.getStore().getId(), product.getProductCode(), 2 * counts);
                            break;
                        case MOVIE_GIVEAWAY:
//                            refundAmount.addAndGet(counts* (product.getSalePrice() - product.getOrderPrice()));
                            stockService.updateStockCounts(sales.getStore().getId(), product.getProductCode(), counts);
                            movieTicket.addAndGet(-1*counts);
                        case DISCOUNT:
//                            refundAmount.addAndGet((int) (counts * (product.getSalePrice() * (1 - event.getDiscount() / 100.0) - product.getOrderPrice())));
                            stockService.updateStockCounts(sales.getStore().getId(), product.getProductCode(), counts);
                            break;
                    }
                } catch (EventNotFoundException e) {
//                    refundAmount.addAndGet(counts* (product.getSalePrice() - product.getOrderPrice()));
                    stockService.updateStockCounts(sales.getStore().getId(), product.getProductCode(), counts);
                }
            });

            switch (sales.getType()){
                case CASH :
                    fundsService.sales(-1 * sales.getTotalAmount(), storeId);
                    fundsService.plusTotalFunds(-1 * sales.getTotalAmount(), storeId);
                    break;
                default:
                    fundsService.sales(-1 * sales.getTotalAmount(), storeId);
                    break;

            }


            salesRepository.save(sales);
        }

        Long[] longs = {sales.getId(),  movieTicket.get()};
        return longs;

    }

    @Transactional
    public List<SalesDTO> findByStoreId(Long storeId) {
        List<Sales> salesList = salesRepository.findByStoreId(storeId);
        return SalesDTO.SalesMapper.toSalesDTOList(salesList);
    }

    @Transactional
    public List<SalesDTO> findBySalesDateAndStoreId(LocalDate salesDate, Long storeId) {
        List<Sales> salesList = salesRepository.findBySalesDateAndStoreId(salesDate, storeId);
        return SalesDTO.SalesMapper.toSalesDTOList(salesList);
    }

    @Transactional
    public SalesDTO findBySalesId(Long salesId){
        Optional<Sales> sales = salesRepository.findById(salesId);
        return SalesDTO.SalesMapper.toSalesDTO(sales.get());
    }


}
