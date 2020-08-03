package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

//    Department
    @GetMapping("/department/all")
    public String allDepartments(Model model) {
        long numberOfDepartment = departmentRepository.count();

        model.addAttribute("numberOfDepartment", numberOfDepartment);
        model.addAttribute("departments", departmentRepository.findAll());
        return "allDepartment";
    }
    @GetMapping("/department/add")
    public String addDepartment(Model model){
        model.addAttribute("submit", "Add New Department");
        model.addAttribute("department", new Department());

        return "addDepartment";
    }
    @PostMapping("/department/process")
    public String processDepartment(@Valid @ModelAttribute("department") Department department,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("submit", "Add New Department");
            return "addDepartment";
        }
        departmentRepository.save(department);
        return "redirect:/department/all";
    }
    @RequestMapping("/department/details/{id}")
    public String departmentDetails(@PathVariable Long id, Model model){
        Department department =  departmentRepository.findById(id).get();
        if (department == null) {
            model.addAttribute("error", "Department does not exist");
        } else {
            model.addAttribute("department", departmentRepository.findById(id).get());
        }
        return "departmentDetail";
    }
    @RequestMapping("/department/update/{id}")
    public String updateDepartment(@PathVariable Long id, Model model){
        Department department = departmentRepository.findById(id).get();

        model.addAttribute("submit", "update");
        model.addAttribute("department",department);
        return "addDepartment";
    }





                        /employee/add
                        /employee/details/{id}
                        /employee/update/{id}
                        /employee/deactivate/{id}

                        /profile

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new Employee());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegister(@Valid @ModelAttribute("user") Employee employee,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.clearPassword();
            model.addAttribute("user", employee);
            return "register";
        }
        model.addAttribute("user", employee);
        model.addAttribute("message", "New user account created");

        employee.setEnabled(true);
        employeeRepository.save(employee);

        Role role = new Role(employee.getUsername(), "ROLE_USER");
        roleRepository.save(role);

        return "redirect:/";

    }

    @RequestMapping("/")
    public String index(){
        return"index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    @RequestMapping("/some")
    public String some(){
        return"some";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }
}
