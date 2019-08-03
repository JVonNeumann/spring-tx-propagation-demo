package com.github.springtxpropagation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.springtxpropagation.service.EmployeeService;

//指定bean注入的配置文件  
@ContextConfiguration(locations = { "classpath:application-context.xml" })
// 使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)

public class TransactionPropagateApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private EmployeeService employeeService;

	/*
	 * 支持当前事务，如果当前没有事务，就新建一个事务
	 * 
	 * 1、如果ServiceA.methodA已经起了事务，这时调用ServiceB.methodB，ServiceB.
	 * methodB看到自己已经运行在ServiceA.methodA的事务内部，就不再起新的事务。这时只有外部事务并且他们是共用的，所以这时ServiceA.
	 * methodA或者ServiceB.methodB无论哪个发生异常,methodA和methodB作为一个整体都将一起回滚。  
	 * 2、如果ServiceA.methodA没有事务，ServiceB.methodB就会为自己分配一个事务。这样，在ServiceA.
	 * methodA中是没有事务控制的。只是在ServiceB.methodB内的任何地方出现异常，ServiceB.methodB将会被回滚，
	 * 不会引起ServiceA.methodA的回滚
	 */
	@Test
	public void addEmpByRequired() {
		employeeService.addEmpByRequired("laoda");
	}

	/*
	 * 支持当前事务，如果当前没有事务，就以非事务方式执行
	 * 
	 * 如果当前在事务中，即以事务的形式运行，如果当前不再一个事务中，那么就以非事务的形式运行
	 * 
	 */
	@Test
	public void addEmpBySupports() {
		employeeService.addEmpBySupports("laoer");
	}

	/*
	 * 支持当前事务，如果当前没有事务，就抛出异常。必须在一个事务中运行。也就是说，他只能被一个父事务调用。否则，他就要抛出异常
	 */
	@Test
	public void addEmpByMandatory() {
		employeeService.addEmpByMandatory("laosan");
	}

	/*
	 * 新建事务，如果当前存在事务，把当前事务挂起
	 * 
	 * 启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务,
	 * 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行
	 * 
	 * 比如我们设计ServiceA.methodA的事务级别为PROPAGATION_REQUIRED，ServiceB.
	 * methodB的事务级别为PROPAGATION_REQUIRES_NEW，那么当执行到ServiceB.methodB的时候，ServiceA.
	 * methodA所在的事务就会挂起，ServiceB.methodB会起一个新的事务，等待ServiceB.methodB的事务完成以后，他才继续执行。
	 * 他与PROPAGATION_REQUIRED
	 * 的事务区别在于事务的回滚程度了。因为ServiceB.methodB是新起一个事务，那么就是存在两个不同的事务。
	 * 
	 * 1、如果ServiceB.methodB已经提交，那么ServiceA.methodA失败回滚，ServiceB.methodB是不会回滚的。
	 * 2、如果ServiceB.methodB失败回滚，如果他抛出的异常被ServiceA.methodA的try..catch捕获并处理，ServiceA.
	 * methodA事务仍然可能提交；如果他抛出的异常未被ServiceA.methodA捕获处理，ServiceA.methodA事务将回滚
	 * 
	 * 使用场景： 不管业务逻辑的service是否有异常，Log Service都应该能够记录成功，所以Log Service的传播属性可以配为此属性
	 */
	@Test
	public void addEmpByRequiresNew() {
		employeeService.addEmpByRequiresNew("laosi");
	}

	/*
	 * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
	 * 
	 * 比如ServiceA.methodA的事务级别是PROPAGATION_REQUIRED
	 * ，而ServiceB.methodB的事务级别是PROPAGATION_NOT_SUPPORTED
	 * ，那么当执行到ServiceB.methodB时，ServiceA.methodA的事务挂起，而他以非事务的状态运行完，再继续ServiceA.
	 * methodA的事务
	 */
	@Test
	public void addEmpByNotSupported() {
		employeeService.addEmpByNotSupported("laowu");
	}

	/*
	 * 以非事务方式执行，如果当前存在事务，则抛出异常
	 * 
	 * 假设ServiceA.methodA的事务级别是PROPAGATION_REQUIRED，
	 * 而ServiceB.methodB的事务级别是PROPAGATION_NEVER ，那么ServiceB.methodB就要抛出异常了。
	 */
	@Test
	public void addEmpByNever() {
		employeeService.addEmpByNever("laoliu");
	}

	/*
	 * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作
	 * 
	 * 开始一个 "嵌套的" 事务, 它是已经存在事务的一个真正的子事务. 潜套事务开始执行时, 它将取得一个 savepoint. 如果这个嵌套事务失败,
	 * 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交
	 * 
	 * 比如我们设计ServiceA.methodA的事务级别为PROPAGATION_REQUIRED，ServiceB.
	 * methodB的事务级别为PROPAGATION_NESTED，那么当执行到ServiceB.methodB的时候，ServiceA.
	 * methodA所在的事务就会挂起，ServiceB.methodB会起一个新的子事务并设置savepoint，等待ServiceB.
	 * methodB的事务完成以后，他才继续执行。。因为ServiceB.methodB是外部事务的子事务，那么
	 * 1、如果ServiceB.methodB已经提交，那么ServiceA.methodA失败回滚，ServiceB.methodB也将回滚。
	 * 2、如果ServiceB.methodB失败回滚，如果他抛出的异常被ServiceA.methodA的try..catch捕获并处理，ServiceA.
	 * methodA事务仍然可能提交；如果他抛出的异常未被ServiceA.methodA捕获处理，ServiceA.methodA事务将回滚
	 * 
	 * 理解Nested的关键是savepoint。他与PROPAGATION_REQUIRES_NEW的区别是：
	 * PROPAGATION_REQUIRES_NEW 完全是一个新的事务,它与外部事务相互独立； 而 PROPAGATION_NESTED
	 * 则是外部事务的子事务, 如果外部事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 roll back. 
	 */
	@Test
	public void addEmpByNested() {
		employeeService.addEmpByNested("laoqi");
	}
}
