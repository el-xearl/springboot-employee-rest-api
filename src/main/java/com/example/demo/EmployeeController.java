package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//REST controller for managing employees
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired 
    private EmployeeDAO employeeDAO;

    //Get endpoint to fetch all employees
    @GetMapping("/")
    public Employees getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    //Post endpoint to add a new employee
    @PostMapping("/")
    public ResponseEntity<Object>
        addEmployee(@RequestBody Employee employee) {

            //Generate ID for the new employee
            Integer id = employeeDAO.getAllEmployees()
                .getEmployeeList().size() + 1;
            employee.setId(id);

            //Add employee to the list
            employeeDAO.addEmployee(employee);

            //Build location URI for the newly created employee
            URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                     .path("/{id}")
                     .buildAndExpand(employee.getId())
                     .toUri();

                return ResponseEntity.created(location).build();
        }
            
}
