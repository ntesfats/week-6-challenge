package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void run(String... args) {

        Department softwareDeparment = new Department("Software Developer Dep.");
        Department administratorDepartment = new Department("Admin");

        departmentRepository.save(softwareDeparment);
        departmentRepository.save(administratorDepartment);

        Employee employee1 = new Employee("mc", "mc@domain.com", "mc",
                "Bart Simpson", "Front Developer", softwareDeparment, true);

        Employee employee2 = new Employee("nahom", "nahom@gmail.com", "nahom",
                "Nahom Tesfatsion", "Java Developer", softwareDeparment, true);

        Employee employee3 = new Employee("admin", "admin@gmail.com", "admin",
                "Bob smith", "CEO", administratorDepartment, true);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
//
        Role employee1Role = new Role("mc", "ROLE_USER");
        Role employee2Role = new Role("nahom", "ROLE_USER");
        Role employee3Role = new Role("admin", "ROLE_ADMIN");
//
        roleRepository.save(employee1Role);
        roleRepository.save(employee2Role);
        roleRepository.save(employee3Role);

    }


}
