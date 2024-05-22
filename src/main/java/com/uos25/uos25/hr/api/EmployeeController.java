package com.uos25.uos25.hr.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeInfoResponse;
import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeInfoResponse.EmployeeInfoResponses;
import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeInfoResponse.EmployeeUpdateRequest;
import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeInfoResponse.ReviewUploadRequest;
import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeRegistryRequest;
import com.uos25.uos25.hr.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "직원 관리", description = "직원 관리 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/hr")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "직원 등록", description = "직원을 등록한다")
    @PostMapping
    public ResponseEntity<?> registryEmployee(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody EmployeeRegistryRequest request) {
        employeeService.registryEmployee(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "지점 모든 직원 조회", description = "모든 직원을 조회한다")
    @GetMapping("/employees")
    public ResponseEntity<EmployeeInfoResponses> getAllEmployeeInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        EmployeeInfoResponses responses = employeeService.getAllEmployeeInfo(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "특정 직원 조회", description = "특정 직원을 조회한다")
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeInfoResponse> getEmployeeInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long employeeId) {
        EmployeeInfoResponse response = employeeService.getEmployeeInfo(employeeId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "리뷰 업로드", description = "리뷰를 업로드 한다")
    @PostMapping("/employees/{employeeId}/review")
    public ResponseEntity<?> uploadReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long employeeId,
            @RequestBody ReviewUploadRequest request) {

        employeeService.uploadReview(request, employeeId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "직원 해고", description = "직원을 해고 한다")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<?> fireEmployee(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long employeeId) {

        employeeService.fireEmployee(employeeId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "직원 정보 수정", description = "직원 정보를 수정 한다")
    @PatchMapping("/employees/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long employeeId,
            @RequestBody EmployeeUpdateRequest request) {

        employeeService.updateEmployee(request, employeeId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "인건비 지불", description = "인건비를 지불 한다")
    @PostMapping("/employees/{employeeId}/payment")
    public ResponseEntity<?> uploadReview(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        employeeService.payTotalSalary(principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
