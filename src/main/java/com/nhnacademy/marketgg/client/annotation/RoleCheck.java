package com.nhnacademy.marketgg.client.annotation;

import com.nhnacademy.marketgg.client.jwt.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {

    Role accessLevel() default Role.ROLE_USER;

}
