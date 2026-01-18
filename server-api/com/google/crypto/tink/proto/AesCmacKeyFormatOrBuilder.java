package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface AesCmacKeyFormatOrBuilder extends MessageOrBuilder {
  int getKeySize();
  
  boolean hasParams();
  
  AesCmacParams getParams();
  
  AesCmacParamsOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */