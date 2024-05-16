package com.uos25.uos25.stock.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.stock.dto.StockDTO;
import com.uos25.uos25.stock.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Stocks", description ="Stocks API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    //제품 번호로 + 가게코드로 재고 조회
    @GetMapping("/{productCode}")
    public ResponseEntity<StockDTO> getStock(@PathVariable String productCode, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Optional<StockDTO> stockDTO = stockService.findStock(principalDetails.getId(), productCode);
        return stockDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //가게코드로 재고 조회
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocksByStore(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<StockDTO> stockDTOs = stockService.findStocksByStoreId(principalDetails.getId());
        if (stockDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stockDTOs);
    }

    // 재고 저장 -> 이전에 있던 재고 초기화 됨(주의)
    @PostMapping("/save")
    public ResponseEntity<Void> saveStock(@RequestBody StockDTO stockDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        stockDTO.setStoreId(principalDetails.getId());
        stockService.saveStock(stockDTO);
        return ResponseEntity.ok().build();
    }

    //// 재고 삭제 -> 필요 시 그냥 위의 메서드로 0으로 초기화 해줘도 됨(생각해두기)
    @DeleteMapping("/{productCode}")
    public ResponseEntity<Void> deleteStock(@PathVariable String productCode, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        stockService.deleteStock(principalDetails.getId(), productCode);
        return ResponseEntity.noContent().build();
    }

    //재고 업데이트 해주는 메서드(0이하로는 못 보내게 해둠)
    //이건 필요한지는 의문 -> 필요 없을 시에 삭제할 예정
    @PostMapping("/updateCounts")
    public ResponseEntity<Void> updateStockCounts(@RequestParam String productCode, @RequestParam int change, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        stockService.updateStockCounts(principalDetails.getId(), productCode, change);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
