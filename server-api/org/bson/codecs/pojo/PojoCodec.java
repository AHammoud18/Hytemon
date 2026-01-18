package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

abstract class PojoCodec<T> implements Codec<T> {
  abstract ClassModel<T> getClassModel();
  
  abstract DiscriminatorLookup getDiscriminatorLookup();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PojoCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */