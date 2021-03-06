package top.coos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段配置
 * 
 * @author 朱亮
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    /**
     * 外表
     * 
     * @return
     */
    Class<?> entityClass();

    /**
     * 外表字段
     * 
     * @return
     */
    String columnName() default "";

}
