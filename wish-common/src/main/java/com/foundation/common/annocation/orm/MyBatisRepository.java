package com.foundation.common.annocation.orm;

import java.lang.annotation.*;

/**
 * Retention为runtime级别的运行周期 将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用
 * Target.type用于描述类、接口(包括注解类型) 或enum声明
 * 标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * @author fqh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisRepository {
    String value() default "";
}