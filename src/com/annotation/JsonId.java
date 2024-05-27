package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a field as an identifier in JSON serialization and deserialization processes.
 * This annotation indicates that the annotated field is crucial for the operation, typically used
 * for ensuring the presence of key fields in JSON data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonId {
    /**
     * Indicates whether the annotated field is required. Default is {@code true}.
     *
     * @return {@code true} if the field is required; {@code false} otherwise.
     */
    boolean required() default true;
}