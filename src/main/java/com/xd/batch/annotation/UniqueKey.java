package com.xd.batch.annotation;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UniqueKey {

    int sort() default 1;

    String group() default "";

}
