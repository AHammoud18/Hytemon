package org.bson.codecs.pojo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BsonDiscriminator {
  String value() default "";
  
  String key() default "_t";
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\annotations\BsonDiscriminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */