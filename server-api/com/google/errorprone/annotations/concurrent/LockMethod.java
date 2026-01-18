package com.google.errorprone.annotations.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Deprecated
public @interface LockMethod {
  String[] value();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\concurrent\LockMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */