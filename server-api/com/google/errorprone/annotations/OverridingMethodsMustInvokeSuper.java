package com.google.errorprone.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface OverridingMethodsMustInvokeSuper {}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\OverridingMethodsMustInvokeSuper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */