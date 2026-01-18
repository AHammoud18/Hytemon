package io.netty.util;

public interface Constant<T extends Constant<T>> extends Comparable<T> {
  int id();
  
  String name();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */