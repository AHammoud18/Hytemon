package org.bson.codecs.pojo;

public interface IdGenerator<T> {
  T generate();
  
  Class<T> getType();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\IdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */