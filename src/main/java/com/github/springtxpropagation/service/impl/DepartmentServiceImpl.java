package com.github.springtxpropagation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtxpropagation.dao.DepartmentDao;
import com.github.springtxpropagation.model.Department;
import com.github.springtxpropagation.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentDao departmentDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addDeptByRequired(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		throw new RuntimeException("addDeptByRequired exception");
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public void addDeptBySupports(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		throw new RuntimeException("addDeptBySupports exception");
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public void addDeptByMandatory(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		//throw new RuntimeException("addDeptByMandatory exception");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addDeptByRequiresNew(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		// throw new RuntimeException("addDeptByNotSupported exception");
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
	public void addDeptByNotSupported(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		throw new RuntimeException("addDeptByNotSupported exception");
	}

	@Override
	@Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
	public void addDeptByNever(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
	}

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public void addDeptByNested(String name) {
		Department department = new Department();
		department.setName(name);
		departmentDao.insertSelective(department);
		throw new RuntimeException("addDeptByNested exception");
	}
}
