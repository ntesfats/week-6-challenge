package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="employee_table")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name = "username")
    @Size(min= 3)
    private String username;

    @Column(name = "email")
    @NotEmpty
    @NotNull
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "enabled")
    private boolean enabled;

    @Column (name = "full_name")
    @NotEmpty
    @NotNull
    private String fullName;

    @Column(name = "job_title")
    @NotEmpty
    @NotNull
    private String jobTitle;
    @JoinTable(name = "depart_id")
    private Department department;

    public Employee() {
    }
    public Employee(@Size(min=3) String username,
                    @NotEmpty @NotNull String email,
                    @NotEmpty @NotNull String password,
                    @NotEmpty @NotNull String fullName,
                    @NotEmpty @NotNull String jobTitle,
                    @NotEmpty @NotNull Department department, boolean enabled) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.department = department;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
//        this.password = password;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void clearPassword() {
        this.password = "";
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
