package com.uos25.uos25.sales.dto;

import com.uos25.uos25.sales.entity.Sales;
import com.uos25.uos25.sales.entity.SalesItem;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SalesDTO {

    private Long id; // 영수증 번호랑 판매 번호랑 동일한거로 사용할거임 ㅇㅇ(명세서엔 다르게 구분해야할 듯)
    private Long storeId;
    private LocalDate salesDate; // 판매시간
    private List<SalesItemDTO> salesItems; // 판매 항목 리스트
    private Boolean isCancelled; // 구매취소여부
    private int totalAmount; // 판매 금액
    private String type;
    private String gender; // 성별
    private String ageGroup; // 연령대

    public static class SalesMapper {

        public static SalesDTO toSalesDTO(Sales sales) {
            SalesDTO salesDTO = new SalesDTO();
            salesDTO.setId(sales.getId());
            salesDTO.setStoreId(sales.getStore().getId());
            salesDTO.setSalesDate(sales.getSalesDate());
            salesDTO.setSalesItems(toSalesItemDTOList(sales.getSalesItems())); // 변환 메서드 사용
            salesDTO.setIsCancelled(sales.getIsCancelled());
            salesDTO.setTotalAmount(sales.getTotalAmount());
            salesDTO.setType(sales.getType().name());
            salesDTO.setGender(sales.getGender());
            salesDTO.setAgeGroup(sales.getAgeGroup());
            return salesDTO;
        }

        public static SalesItemDTO toSalesItemDTO(SalesItem salesItem) {
            SalesItemDTO salesItemDTO = new SalesItemDTO();
            salesItemDTO.setProductCode(salesItem.getProduct().getProductCode());
            salesItemDTO.setCounts(salesItem.getCounts());
            return salesItemDTO;
        }

        public static List<SalesItemDTO> toSalesItemDTOList(List<SalesItem> salesItems) {
            return salesItems.stream()
                    .map(SalesMapper::toSalesItemDTO)
                    .collect(Collectors.toList());
        }

        public static List<SalesDTO> toSalesDTOList(List<Sales> salesList) {
            return salesList.stream()
                    .map(SalesMapper::toSalesDTO)
                    .collect(Collectors.toList());
        }
    }

}
