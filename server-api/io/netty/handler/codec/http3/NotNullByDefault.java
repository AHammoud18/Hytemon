package io.netty.handler.codec.http3;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.meta.TypeQualifierDefault;
import org.jetbrains.annotations.NotNull;

@Documented
@TypeQualifierDefault({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PACKAGE})
@NotNull
@interface NotNullByDefault {}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\NotNullByDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */