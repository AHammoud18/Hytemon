package org.bson.codecs;

import org.bson.BsonValue;

public interface CollectibleCodec<T> extends Codec<T> {
  T generateIdIfAbsentFromDocument(T paramT);
  
  boolean documentHasId(T paramT);
  
  BsonValue getDocumentId(T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\CollectibleCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */