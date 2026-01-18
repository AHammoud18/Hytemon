package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface AesCtrHmacAeadKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasAesCtrKey();
  
  AesCtrKey getAesCtrKey();
  
  AesCtrKeyOrBuilder getAesCtrKeyOrBuilder();
  
  boolean hasHmacKey();
  
  HmacKey getHmacKey();
  
  HmacKeyOrBuilder getHmacKeyOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacAeadKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */