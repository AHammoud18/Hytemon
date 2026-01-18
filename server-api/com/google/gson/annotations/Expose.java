package com.google.gson.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Expose {
  boolean serialize() default true;
  
  boolean deserialize() default true;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\annotations\Expose.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */