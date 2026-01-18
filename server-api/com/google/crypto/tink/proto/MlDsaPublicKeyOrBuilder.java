package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface MlDsaPublicKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  ByteString getKeyValue();
  
  boolean hasParams();
  
  MlDsaParams getParams();
  
  MlDsaParamsOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaPublicKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */