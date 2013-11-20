package bbc.iplayer.spring.mashery.iodocs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The name to use for the resource in Mashery I/O Docs.
 * <p/>
 * If not present, the class and method names are used to create a automatic name.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IoDocsResourceName {
    String value();
}
