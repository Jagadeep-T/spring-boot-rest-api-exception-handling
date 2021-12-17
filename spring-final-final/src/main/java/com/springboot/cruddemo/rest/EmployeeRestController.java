package com.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.response.ErrorMessages;
import com.springboot.cruddemo.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll() {
        return new ResponseEntity<List<Employee>>(employeeService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<Employee> getEmployee(@RequestBody Employee emp) throws EmployeeServiceException {
    	int id =emp.getId();
        Employee theEmployee = employeeService.findById(id);
        if (theEmployee == null || theEmployee.getId() <= 0) {
            throw new EmployeeServiceException("Employee with id " + id + " does not exist");
        }
        return new ResponseEntity<Employee>(theEmployee, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee theEmployee) throws Exception {
    	if(theEmployee.getDepartmentName().isEmpty())throw new EmployeeServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    	theEmployee.setId(0);
        employeeService.save(theEmployee);
        return new ResponseEntity<Employee>(theEmployee, HttpStatus.OK);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee theEmployee) {
        employeeService.save(theEmployee);
        return new ResponseEntity<Employee>(theEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public String deleteEmployee (@RequestBody Employee emp) {
    	int id = emp.getId();
        Employee theEmployee = employeeService.findById(id);
        if (theEmployee == null) {
            throw new RuntimeException("Employee with id " + id + " does not exist");
        }
        employeeService.deleteById(id);
        return "{\"employee_id\" : \"" + id + "\","
                + "\"info\" : \" Employee has been successfully deleted\"}";
    }
}
