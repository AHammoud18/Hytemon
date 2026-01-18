package com.google.errorprone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface DoNotCall {
  String value() default "";
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\DoNotCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */