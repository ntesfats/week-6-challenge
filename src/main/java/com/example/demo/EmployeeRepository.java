package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
    Iterable<Employee> findAllByDepartment(Department department);
}
