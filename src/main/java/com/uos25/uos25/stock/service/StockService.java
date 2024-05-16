package com.uos25.uos25.stock.service;

import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.repository.ProductsRepository;
import com.uos25.uos25.stock.dto.StockDTO;
import com.uos25.uos25.stock.entity.Stocks;
import com.uos25.uos25.stock.entity.StoreProductId;
import com.uos25.uos25.stock.repository.StockRepository;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ProductsRepository productsRepository;
    private final StoreRepository storeRepository;

    public Optional<StockDTO> findStock(Long storeId, String productCode) {
        StoreProductId storeProductId = new StoreProductId(storeId, productCode);
        Optional<Stocks> stock = stockRepository.findById(storeProductId);
        return stock.map(this::convertToDTO);
    }

    public List<StockDTO> findStocksByStoreId(Long storeId) {
        List<Stocks> stocks = stockRepository.findByStoreId(storeId);
        return stocks.stream().map(this::convertToDTO).toList();
    }

    public void saveStock(StockDTO stockDTO) {
        Stocks stock = convertToEntity(stockDTO);
        stockRepository.save(stock);
    }

    public void deleteStock(Long storeId, String productCode) {
        StoreProductId storeProductId = new StoreProductId(storeId, productCode);
        stockRepository.deleteById(storeProductId);
    }

    //재고 -, 재고 ++ 만들어야됨. -> 여기에 판매, 반품을 같이 해버릴까나 ?
    public void updateStockCounts(Long storeId, String productCode, int change) {
        Optional<Stocks> optionalStock = stockRepository.findById(new StoreProductId(storeId, productCode));

        if (optionalStock.isPresent()) {
            Stocks stock = optionalStock.get();

            int currentCounts = stock.getCounts();
            int updatedCounts = currentCounts + change;

            // 재고는 음수가 될 수 없으므로 최소값을 0으로 설정
            if (updatedCounts < 0) {
                updatedCounts = 0;
            }

            // 변경된 재고 개수를 저장합니다.
            stock.setCounts(updatedCounts);
            stockRepository.save(stock);
        }
    }


    private StockDTO convertToDTO(Stocks stocks) {
        return new StockDTO(stocks.getId().getStoreId(), stocks.getId().getProductCode(), stocks.getCounts());
    }

    private Stocks convertToEntity(StockDTO stockDTO) {
        Store store = storeRepository.findById(stockDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid store ID"));
        Products products = productsRepository.findByProductCode(stockDTO.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product code"));

        return new Stocks(store, products, stockDTO.getCounts());
    }
}
