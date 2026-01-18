package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface AesCtrHmacAeadKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasAesCtrKeyFormat();
  
  AesCtrKeyFormat getAesCtrKeyFormat();
  
  AesCtrKeyFormatOrBuilder getAesCtrKeyFormatOrBuilder();
  
  boolean hasHmacKeyFormat();
  
  HmacKeyFormat getHmacKeyFormat();
  
  HmacKeyFormatOrBuilder getHmacKeyFormatOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacAeadKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */