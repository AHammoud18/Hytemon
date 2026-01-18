package org.bouncycastle.util;

public interface Selector<T> extends Cloneable {
  boolean match(T paramT);
  
  Object clone();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastl\\util\Selector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */