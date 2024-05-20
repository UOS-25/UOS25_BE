package com.uos25.uos25.loss.service;

import com.uos25.uos25.loss.dto.LossDTO;
import com.uos25.uos25.loss.entity.Loss;
import com.uos25.uos25.loss.repository.LossRepository;
import com.uos25.uos25.products.repository.ProductsRepository;
import com.uos25.uos25.stock.service.StockService;
import com.uos25.uos25.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class LossService {

    private final LossRepository lossRepository;
    private final StoreRepository storeRepository;
    private final ProductsRepository productsRepository;
    private final StockService stockService;

    @Transactional
    public void saveLoss(LossDTO lossDTO, Long storeId){
        Loss loss = new Loss();
        loss.toLoss(lossDTO, storeRepository.findById(storeId).get(), productsRepository.findByProductCode(lossDTO.getProductsCode()).get());
        lossRepository.save(loss);
        stockService.updateStockCounts(storeId, lossDTO.getProductsCode(), -1 * loss.getCounts());
    }

}
