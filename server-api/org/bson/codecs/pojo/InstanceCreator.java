package org.bson.codecs.pojo;

public interface InstanceCreator<T> {
  <S> void set(S paramS, PropertyModel<S> paramPropertyModel);
  
  T getInstance();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\InstanceCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */