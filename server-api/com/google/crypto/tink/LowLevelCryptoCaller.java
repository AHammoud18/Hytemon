package com.google.crypto.tink;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
public @interface LowLevelCryptoCaller {}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\LowLevelCryptoCaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */