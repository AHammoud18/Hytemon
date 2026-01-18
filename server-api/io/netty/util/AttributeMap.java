package io.netty.util;

public interface AttributeMap {
  <T> Attribute<T> attr(AttributeKey<T> paramAttributeKey);
  
  <T> boolean hasAttr(AttributeKey<T> paramAttributeKey);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\AttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */