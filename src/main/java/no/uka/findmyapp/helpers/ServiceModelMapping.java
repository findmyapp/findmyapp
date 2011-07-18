package no.uka.findmyapp.helpers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface ServiceModelMapping {
		Class returnType();
		boolean isList() default false;
}
