package org.bson.codecs;

import org.bson.UuidRepresentation;

public interface OverridableUuidRepresentationCodec<T> {
  Codec<T> withUuidRepresentation(UuidRepresentation paramUuidRepresentation);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\OverridableUuidRepresentationCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */