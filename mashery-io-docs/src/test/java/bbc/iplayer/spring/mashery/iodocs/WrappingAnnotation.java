package bbc.iplayer.spring.mashery.iodocs;

import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsDescription;
import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsEnum;
import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsEnumDescriptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@IoDocsEnum({"a", "b", "c"})
@IoDocsEnumDescriptions({"aDesc", "bDesc", "cDesc"})
@IoDocsDescription("Know your ABC")
public @interface WrappingAnnotation {
}
