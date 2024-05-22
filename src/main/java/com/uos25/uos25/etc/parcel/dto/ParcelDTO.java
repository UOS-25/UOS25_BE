package com.uos25.uos25.etc.parcel.dto;

import com.uos25.uos25.etc.parcel.entity.Parcel;
import com.uos25.uos25.etc.parcel.entity.ParcelState;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

public class ParcelDTO {

    @Getter
    @AllArgsConstructor
    public static class ParcelRegistryRequest {
        private String fromAddress;
        private String toAddress;
        private String fromPhoneNumber;
        private String toPhoneNumber;
        private int weight;
        private String goods;
    }

    @Getter
    @AllArgsConstructor
    public static class ParcelInfoResponse {
        private Long parcelaId
        private String fromAddress;
        private String toAddress;
        private String fromPhoneNumber;
        private String toPhoneNumber;
        private int weight;
        private String goods;

        public static ParcelInfoResponse toDTO(Parcel parcel) {
            return new ParcelInfoResponse(parcel.getId(),parcel.getFromAddress(), parcel.getToAddress(), parcel.getFromPhoneNumber(),
                    parcel.getToPhoneNumber(), parcel.getWeight(), parcel.getGoods());
        }
    }


    @Getter
    @AllArgsConstructor
    @Jacksonized
    @Builder
    public static class ParcelStateRequest {
        private ParcelState parcelState;

    }

    @Getter
    @AllArgsConstructor
    public static class ParcelInfoResponses {
        List<ParcelInfoResponse> responses;

        public static ParcelInfoResponses toDTO(List<Parcel> parcels) {
            List<ParcelInfoResponse> responses = new ArrayList<>();
            parcels.forEach(parcel -> responses.add(ParcelInfoResponse.toDTO(parcel)));

            return new ParcelInfoResponses(responses);
        }
    }
}
