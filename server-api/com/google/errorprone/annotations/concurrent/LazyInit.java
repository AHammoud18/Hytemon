package com.google.errorprone.annotations.concurrent;

import com.google.errorprone.annotations.IncompatibleModifiers;
import com.google.errorprone.annotations.Modifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@IncompatibleModifiers(modifier = {Modifier.FINAL})
public @interface LazyInit {}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\concurrent\LazyInit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */