package com.google.errorprone.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.lang.model.element.Modifier;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE})
public @interface IncompatibleModifiers {
  @Deprecated
  Modifier[] value() default {};
  
  Modifier[] modifier() default {};
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\IncompatibleModifiers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */