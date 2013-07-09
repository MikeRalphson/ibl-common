package bbc.iplayer.spring.mashery.iodocs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value placed in a String parameter's field in an I/O Docs form.
 * The default value in the RequestParam takes precedence.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IoDocsDefaultString {
    String value();
}
