package no.uka.findmyapp.helpers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface ServiceModelMapping {
		Class returnType();
		boolean isList() default false;
}
