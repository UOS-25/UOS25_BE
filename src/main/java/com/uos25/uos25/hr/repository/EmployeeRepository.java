package com.uos25.uos25.hr.repository;

import com.uos25.uos25.hr.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByStoreId(Long storeId);
}
