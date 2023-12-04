package com.filling.framework.common.tools;


import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Mongo事务注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Retryable(value = UncategorizedMongoDbException.class, exceptionExpression = "#{message.contains('WriteConflict error')}", maxAttempts = 128, backoff = @Backoff(delay = 500))
@Transactional(transactionManager = "mongoTransactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, timeout = 120)
public @interface MongoTransactional {

}
