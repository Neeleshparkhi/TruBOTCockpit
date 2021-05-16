package Annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface testAuthor {

	String value() default "AUTHOR NOT ASSIGNED";

}
