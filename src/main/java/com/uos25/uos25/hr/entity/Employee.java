package com.uos25.uos25.hr.entity;


import com.uos25.uos25.hr.dto.EmployeeDTO.EmployeeUpdateRequest;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private String title;

    @Column
    private int officeHours;

    @Column
    private String career;

    @Column
    private int salary;

    @Column
    private String review;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Employee(String name, String gender, String title, int officeHours, String career, int salary, String review,
                    Store store) {
        this.name = name;
        this.gender = gender;
        this.title = title;
        this.officeHours = officeHours;
        this.career = career;
        this.salary = salary;
        this.review = review;
        this.store = store;
    }

    public void update(EmployeeUpdateRequest request) {
        this.name = request.getName();
        this.gender = request.getGender();
        this.title = request.getTitle();
        this.officeHours = request.getOfficeHours();
        this.career = request.getCareer();
        this.salary = request.getSalary();
        this.review = request.getReview();
    }

    public void updateReview(String review) {
        this.review = review;
    }

    public void updateSalary(int salary) {
        this.salary = salary;
    }
}
