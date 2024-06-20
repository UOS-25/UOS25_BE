package com.uos25.uos25.employee.service;

import com.uos25.uos25.common.error.hr.EmployeeNotFoundException;
import com.uos25.uos25.employee.dto.EmployeeDTO.EmployeeInfoResponse;
import com.uos25.uos25.employee.dto.EmployeeDTO.EmployeeInfoResponses;
import com.uos25.uos25.employee.dto.EmployeeDTO.EmployeeRegistryRequest;
import com.uos25.uos25.employee.dto.EmployeeDTO.EmployeeUpdateRequest;
import com.uos25.uos25.employee.dto.EmployeeDTO.ReviewUploadRequest;
import com.uos25.uos25.employee.entity.Employee;
import com.uos25.uos25.employee.repository.EmployeeRepository;
import com.uos25.uos25.funds.service.FundsService;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final StoreService storeService;
    private final FundsService fundsService;

    public void registryEmployee(EmployeeRegistryRequest request, Long storeId) {
        Store store = storeService.getStoreById(storeId);

        Employee employee = Employee.builder()
                .name(request.getName())
                .career(request.getCareer())
                .title(request.getTitle())
                .gender(request.getGender())
                .officeHours(request.getOfficeHours())
                .salary(request.getSalary())
                .store(store)
                .build();

        employeeRepository.save(employee);

        fundsService.updatePersonalExpense(storeId, calculateTotalSalary(storeId));
    }

    public EmployeeInfoResponses getAllEmployeeInfo(Long storeId) {
        List<Employee> info = employeeRepository.findAllByStoreId(storeId);

        return EmployeeInfoResponses.toDTO(info);
    }

    public EmployeeInfoResponse getEmployeeInfo(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);

        return EmployeeInfoResponse.toDTO(employee);
    }


    public void uploadReview(ReviewUploadRequest request, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);

        employee.updateReview(request.getReview());

        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeUpdateRequest request, Long employeeId, Long storeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);

        employee.update(request);

        employeeRepository.save(employee);

        fundsService.updatePersonalExpense(storeId, calculateTotalSalary(storeId));
    }

    public void fireEmployee(Long employeeId, Long storeId) {
        employeeRepository.deleteById(employeeId);

        fundsService.updatePersonalExpense(storeId, calculateTotalSalary(storeId));
    }

    public void payTotalSalary(Long storeId) {
        fundsService.payPersonalExpense(storeId);
    }

    private int calculateTotalSalary(Long storeId) {
        List<Employee> employees = employeeRepository.findAllByStoreId(storeId);

        return employees.stream().mapToInt(Employee::getSalary).sum();
    }
}
