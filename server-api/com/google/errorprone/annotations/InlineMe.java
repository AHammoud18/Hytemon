package com.google.errorprone.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface InlineMe {
  String replacement();
  
  String[] imports() default {};
  
  String[] staticImports() default {};
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\errorprone\annotations\InlineMe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */