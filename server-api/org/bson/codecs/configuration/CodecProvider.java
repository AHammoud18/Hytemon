package org.bson.codecs.configuration;

import org.bson.codecs.Codec;

public interface CodecProvider {
  <T> Codec<T> get(Class<T> paramClass, CodecRegistry paramCodecRegistry);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\configuration\CodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */