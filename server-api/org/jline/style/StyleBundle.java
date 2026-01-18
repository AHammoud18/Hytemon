package org.jline.style;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface StyleBundle {
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD})
  @Documented
  public static @interface DefaultStyle {
    String value();
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD})
  @Documented
  public static @interface StyleName {
    String value();
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE})
  @Documented
  public static @interface StyleGroup {
    String value();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */