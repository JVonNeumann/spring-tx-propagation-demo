package com.github.springtxpropagation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtxpropagation.dao.EmployeeDao;
import com.github.springtxpropagation.model.Employee;
import com.github.springtxpropagation.service.DepartmentService;
import com.github.springtxpropagation.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	DepartmentService departmentService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEmpByRequired(String name) {
		Employee employee = new Employee();
		employee.setDeptId(1);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptByRequired("jishubu");
		throw new RuntimeException("addEmpByRequired exception");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEmpBySupports(String name) {
		Employee employee = new Employee();
		employee.setDeptId(2);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptBySupports("jishubu");
		throw new RuntimeException("addEmpBySupports exception");
	}

	@Override
	// @Transactional(propagation = Propagation.REQUIRED,rollbackFor =
	// Exception.class)
	public void addEmpByMandatory(String name) {
		System.out.println("aaaaaa");
		Employee employee = new Employee();
		employee.setDeptId(3);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptByMandatory("jishubu");
		//throw new RuntimeException("addEmpByMandatory exception");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addEmpByRequiresNew(String name) {
		Employee employee = new Employee();
		employee.setDeptId(4);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptByRequiresNew("jishubu");
		throw new RuntimeException("addEmpByRequiresNew exception");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEmpByNotSupported(String name) {
		Employee employee = new Employee();
		employee.setDeptId(5);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptByNotSupported("jishubu");
		throw new RuntimeException("addEmpByNotSupported exception");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEmpByNever(String name) {
		Employee employee = new Employee();
		employee.setDeptId(6);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
		departmentService.addDeptByNever("jishubu");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEmpByNested(String name) {
		Employee employee = new Employee();
		employee.setDeptId(7);
		employee.setName(name);
		employee.setAddress("邯郸");
		employeeDao.insertSelective(employee);
//		try {
//			departmentService.addDeptByNested("jishubu");
//		} catch (Exception e) {
//		}
		departmentService.addDeptByNested("jishubu");
//		throw new RuntimeException("addEmpByNested exception");
	}
}
