package com.ankit.ems_backend.service.impl;

import com.ankit.ems_backend.dto.EmployeeDto;
import com.ankit.ems_backend.entity.Employee;
import com.ankit.ems_backend.exception.ResourceNotFoundException;
import com.ankit.ems_backend.mapper.EmployeeMapper;
import com.ankit.ems_backend.repository.EmployeeRepository;
import com.ankit.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto){

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
      Employee savedEmployee=employeeRepository.save(employee);
      return EmployeeMapper.mapToEmployeeDto(savedEmployee);
  }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
      Employee employee= employeeRepository.findById(employeeId)
            .orElseThrow(()->
                    new ResourceNotFoundException("Employee is not exist with given id "+employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
       List<Employee> employees =  employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
       Employee employee= employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee is not exist with the given id"+employeeId)
        );
       employee.setFirstName(updatedEmployee.getFirstName());
       employee.setLastName(updatedEmployee.getLastName());
       employee.setEmail(updatedEmployee.getEmail());
       Employee updatedEmployeeObj= employeeRepository.save(employee);
       return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee is not exist with the given id"+employeeId)
        );
        employeeRepository.deleteById(employeeId);
    }
}



































