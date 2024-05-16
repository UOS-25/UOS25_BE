package com.uos25.uos25.service.event;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.uos25.uos25.event.dto.EventDTO.EventCreateRequest;
import com.uos25.uos25.event.repository.EventRepository;
import com.uos25.uos25.event.service.EventService;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.service.ProductsService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    ProductsService productsService;

    @Mock
    EventRepository eventRepository;

    @Mock
    StoreService storeService;

    @InjectMocks
    EventService eventService;

    @Test
    @DisplayName("Event Create Test")
    void eventCreateTest() {
        EventCreateRequest request = new EventCreateRequest("DISCOUNT", "죠스바", 500);
        Products products = new Products("죠스바", "ㅁ", 500, 500);
        Store store = new Store();

        given(storeService.getStoreById(any())).willReturn(store);

        given(productsService.getProductByName(any())).willReturn(products);

        eventService.createEvent(request, 0L);

        verify(eventRepository).save(any());
    }
}
