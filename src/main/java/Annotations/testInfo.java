package Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface testInfo {
	String description() default "DESCRIPTION NOT GIVEN";
	String functionality() default "FUNCTIONALITY NOT GIVEN";
}
