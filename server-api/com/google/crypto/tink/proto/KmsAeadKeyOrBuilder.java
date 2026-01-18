package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface KmsAeadKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  KmsAeadKeyFormat getParams();
  
  KmsAeadKeyFormatOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsAeadKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */