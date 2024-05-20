package com.uos25.uos25.sales.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.sales.dto.SalesDTO;
import com.uos25.uos25.sales.entity.Sales;
import com.uos25.uos25.sales.service.SalesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Sales", description = "Sales API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSales(@RequestBody SalesDTO salesDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        salesDTO.setStoreId(principalDetails.getId());
        salesDTO.setSalesDate(LocalDate.now());
        Long[] arr = salesService.saveSales(salesDTO);
        return ResponseEntity.ok("삑 그리고 다음: " + arr[0] + " 영화 티켓은 과연 몇장?! " + arr[1]);
    }

    @PostMapping("/refund/{id}")
    public ResponseEntity<String> refundSales(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long[] arr = salesService.refundSales(id, principalDetails.getId());
        return ResponseEntity.ok("삑 그리고 다음: " + arr[0] + " 영화 티켓은 과연 몇장?! " + arr[1]);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<SalesDTO>> findByStoreId(@PathVariable Long storeId) {
        List<SalesDTO> salesList = salesService.findByStoreId(storeId);
        return ResponseEntity.ok(salesList);
    }

    @GetMapping("/date/{salesDate}/{storeId}")
    public ResponseEntity<List<SalesDTO>> findBySalesDateAndStoreId(@PathVariable LocalDate salesDate, @PathVariable Long storeId) {
        List<SalesDTO> salesList = salesService.findBySalesDateAndStoreId(salesDate, storeId);
        return ResponseEntity.ok(salesList);
    }

    @GetMapping("/{salesId}")
    public ResponseEntity<SalesDTO> findBySalesId(@PathVariable Long salesId) {
        SalesDTO salesDTO = salesService.findBySalesId(salesId);
        return ResponseEntity.ok(salesDTO);
    }
}
