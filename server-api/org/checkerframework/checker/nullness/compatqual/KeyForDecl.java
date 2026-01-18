package org.checkerframework.checker.nullness.compatqual;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyForDecl {
  String[] value();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\checkerframework\checker\nullness\compatqual\KeyForDecl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */