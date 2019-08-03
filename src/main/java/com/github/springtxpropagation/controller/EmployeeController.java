package com.github.springtxpropagation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.springtxpropagation.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ResponseBody
    @RequestMapping("/zyc/emp/{name}")
    public String addEmp(@PathVariable("name") String name){

        employeeService.addEmpByRequired(name);

        return "ok";
    }
}
