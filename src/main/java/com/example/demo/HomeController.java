package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/all-department")
    public String allDepartments(Model model) {
        long numberOfDepartment = departmentRepository.count();

        model.addAttribute("numberOfDepartment", numberOfDepartment);
        model.addAttribute("departments", departmentRepository.findAll());
        return "allDepartment";

    }

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
