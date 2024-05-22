package com.uos25.uos25.hr.dto;

import com.uos25.uos25.hr.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

public class EmployeeDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeRegistryRequest {
        private String name;
        private String gender;
        private String title;
        private int officeHours;
        private String career;
        private int salary;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeInfoResponse {
        private Long employeeId;
        private String name;
        private String gender;
        private String title;
        private int officeHours;
        private String career;
        private int salary;
        private String review;

        public static EmployeeInfoResponse toDTO(Employee employee) {
            return new EmployeeInfoResponse(employee.getId(), employee.getName(), employee.getGender(),
                    employee.getTitle(), employee.getOfficeHours(), employee.getCareer(), employee.getSalary(),
                    employee.getReview());

        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeInfoResponses {
        List<EmployeeInfoResponse> responses;

        public static EmployeeInfoResponses toDTO(List<Employee> employees) {
            List<EmployeeInfoResponse> responses = new ArrayList<>();

            employees.forEach(employee -> responses.add(EmployeeInfoResponse.toDTO(employee)));
            return new EmployeeInfoResponses(responses);
        }
    }

    @Getter
    @AllArgsConstructor
    @Jacksonized
    @Builder
    public static class ReviewUploadRequest {
        private String review;
    }

    @Getter
    @AllArgsConstructor
    @Jacksonized
    @Builder
    public static class SalaryUpdateRequest {
        private int salary;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeUpdateRequest {
        private String name;
        private String gender;
        private String title;
        private int officeHours;
        private String career;
        private int salary;
        private String review;
    }
}
