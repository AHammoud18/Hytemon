package com.google.errorprone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface InlineMeValidationDisabled {
  String value();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\InlineMeValidationDisabled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */