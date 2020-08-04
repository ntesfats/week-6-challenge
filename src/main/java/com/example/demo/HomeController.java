package com.example.demo;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
//        long r = 2;
//        Department d = departmentRepository.findById(r).get();
//        d.getEmployees().size();


        return "allDepartment";
    }
    @GetMapping("/department/add")
    public String addDepartment(Model model){
        model.addAttribute("submit", "Add");
        model.addAttribute("department", new Department());

        return "addDepartment";
    }
    @PostMapping("/department/process")
    public String processDepartment(@Valid @ModelAttribute("department") Department department,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("submit", "Submit");
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

        model.addAttribute("submit", "Update");
        model.addAttribute("department",department);
        return "addDepartment";
    }

//    Employee

    @GetMapping("/employee/all")
    public String allEmployee(Model model){
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);

        return "allEmployee";
    }
    @GetMapping("/employee/add")
    public String addEmployee(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();

        model.addAttribute("employee", new Employee());
        model.addAttribute("submit", "Add");
        model.addAttribute("departments", departments);
        return "addEmployee";
    }

    @PostMapping("/employee/process")
    public String procesEmployee(@ModelAttribute Employee employee,
                                 BindingResult result, Model model) {

        if(result.hasErrors()){
            employee.clearPassword();
            model.addAttribute("submit", "Update");
            model.addAttribute("employee", employee);
            return "addEmployee";
        }

        employee.setEnabled(true);
        employeeRepository.save(employee);

        Role role = new Role(employee.getUsername(), "ROLE_USER");
        roleRepository.save(role);




        return "redirect:/employee/all";
    }
    @RequestMapping("/employee/details")
    public String employeeDetails(@RequestParam("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("employee", employee);

        return "employeeDetail";
    }
    @GetMapping("/employee/update/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id).get();
        Iterable<Department> departments = departmentRepository.findAll();

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("submit", "Update");
        return "addEmployee";
    }
    @GetMapping("/employee/deactivate/{id}")
    public String deactivate(@PathVariable long id, Model model, @RequestParam("details") boolean returnToDetailPage){

        Employee employee = employeeRepository.findById(id).get();
//       Here: I am negating whatever the isRented property holds
        employee.setEnabled(!employee.getEnabled());

        employeeRepository.save(employee);
        model.addAttribute("employee", employee);

        if (returnToDetailPage) {
            return "redirect:/employee/details?id="+id;
        } else {
            return "redirect:/employee/all";
        }

    }
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model){
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("employee", employee);
        return "profile";
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



    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }
}
