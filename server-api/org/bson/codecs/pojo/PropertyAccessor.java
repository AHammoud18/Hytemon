package org.bson.codecs.pojo;

public interface PropertyAccessor<T> {
  <S> T get(S paramS);
  
  <S> void set(S paramS, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */