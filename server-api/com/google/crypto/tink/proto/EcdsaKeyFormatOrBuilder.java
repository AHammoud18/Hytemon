package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface EcdsaKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  EcdsaParams getParams();
  
  EcdsaParamsOrBuilder getParamsOrBuilder();
  
  int getVersion();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */