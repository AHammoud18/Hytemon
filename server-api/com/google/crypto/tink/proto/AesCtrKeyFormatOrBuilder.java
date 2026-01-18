package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface AesCtrKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  AesCtrParams getParams();
  
  AesCtrParamsOrBuilder getParamsOrBuilder();
  
  int getKeySize();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */