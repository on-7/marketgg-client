package com.nhnacademy.marketgg.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 인증이 필요하지 않은 접근을 AOP 동작에서 제외시키기 위한 Annotation.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {
}
