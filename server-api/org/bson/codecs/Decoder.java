package org.bson.codecs;

import org.bson.BsonReader;

public interface Decoder<T> {
  T decode(BsonReader paramBsonReader, DecoderContext paramDecoderContext);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */