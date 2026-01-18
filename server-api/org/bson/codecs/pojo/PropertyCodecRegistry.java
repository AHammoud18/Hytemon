package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

public interface PropertyCodecRegistry {
  <T> Codec<T> get(TypeWithTypeParameters<T> paramTypeWithTypeParameters);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyCodecRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */