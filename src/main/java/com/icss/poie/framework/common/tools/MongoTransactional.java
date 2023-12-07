package com.icss.poie.framework.common.tools;


import org.springframework.core.annotation.AliasFor;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Mongo事务注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})//字段注解
@Retention(RetentionPolicy.RUNTIME)//在运行期保留注解信息
@Documented		//在生成javac时显示该注解的信息
@Inherited		//标明注解可以被使用它的子类继承
@Retryable(
        value = UncategorizedMongoDbException.class,
        exceptionExpression = "#{message.contains('WriteConflict error')}",
        maxAttempts = 128,
        backoff = @Backoff(delay = 500))
@Transactional(
        transactionManager = "mongoTransactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Exception.class,
        timeout = 120)
public @interface MongoTransactional {

    /**
     * PROPAGATION_REQUIRED -- 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br/>
     * PROPAGATION_SUPPORTS -- 支持当前事务，如果当前没有事务，就以非事务方式执行。<br/>
     * PROPAGATION_MANDATORY -- 支持当前事务，如果当前没有事务，就抛出异常。<br/>
     * PROPAGATION_REQUIRES_NEW -- 新建事务，如果当前存在事务，把当前事务挂起。<br/>
     * PROPAGATION_NOT_SUPPORTED -- 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br/>
     * PROPAGATION_NEVER -- 以非事务方式执行，如果当前存在事务，则抛出异常。<br/>
     * PROPAGATION_NESTED -- 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。<br/>
     * 前六个策略类似于EJB CMT，第七个（PROPAGATION_NESTED）是Spring所提供的一个特殊变量。
     * @return
     */
    @AliasFor(annotation = Transactional.class, attribute = "propagation")
    Propagation propagation() default Propagation.REQUIRED;
}
