package com.google.common.flogger;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface LazyArg<T> {
  @NullableDecl
  T evaluate();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LazyArg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */