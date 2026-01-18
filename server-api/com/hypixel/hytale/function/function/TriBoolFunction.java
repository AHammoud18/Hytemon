package com.hypixel.hytale.function.function;

@FunctionalInterface
public interface TriBoolFunction<T, U, V, R> {
  R apply(T paramT, U paramU, V paramV, boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\function\function\TriBoolFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */