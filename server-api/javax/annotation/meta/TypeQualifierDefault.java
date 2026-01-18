package javax.annotation.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeQualifierDefault {
  ElementType[] value() default {};
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\javax\annotation\meta\TypeQualifierDefault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */