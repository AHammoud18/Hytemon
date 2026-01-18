package org.checkerframework.checker.nullness.compatqual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface KeyForType {
  String[] value();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\checkerframework\checker\nullness\compatqual\KeyForType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */