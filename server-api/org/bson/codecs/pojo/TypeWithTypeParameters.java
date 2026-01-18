package org.bson.codecs.pojo;

import java.util.List;

public interface TypeWithTypeParameters<T> {
  Class<T> getType();
  
  List<? extends TypeWithTypeParameters<?>> getTypeParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\TypeWithTypeParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */