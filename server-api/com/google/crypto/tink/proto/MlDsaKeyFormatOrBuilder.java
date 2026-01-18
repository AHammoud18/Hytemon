package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface MlDsaKeyFormatOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  MlDsaParams getParams();
  
  MlDsaParamsOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */