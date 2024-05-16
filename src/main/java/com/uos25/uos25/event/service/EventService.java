package com.uos25.uos25.event.service;


import com.uos25.uos25.event.dto.EventDTO.EventCreateRequest;
import com.uos25.uos25.event.dto.EventDTO.EventInfoResponses;
import com.uos25.uos25.event.entity.Event;
import com.uos25.uos25.event.entity.EventType;
import com.uos25.uos25.event.repository.EventRepository;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.service.ProductsService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final ProductsService productsService;
    private final StoreService storeService;

    public void createEvent(EventCreateRequest request, Long storeId) {
        Products product = productsService.getProductByName(request.getProductName());
        Store store = storeService.getStoreById(storeId);

        Event event = Event.builder()
                .products(product)
                .discount(request.getDiscount())
                .type(EventType.valueOf(request.getType()))
                .store(store)
                .build();

        eventRepository.save(event);
    }

    public EventInfoResponses getAllEventInfo(Long storeId) {
        return null;
    }

}
