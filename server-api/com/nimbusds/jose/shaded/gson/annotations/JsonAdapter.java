package com.nimbusds.jose.shaded.gson.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JsonAdapter {
  Class<?> value();
  
  boolean nullSafe() default true;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\annotations\JsonAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */