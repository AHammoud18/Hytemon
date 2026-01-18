package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface EciesAeadHkdfPublicKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  EciesAeadHkdfParams getParams();
  
  EciesAeadHkdfParamsOrBuilder getParamsOrBuilder();
  
  ByteString getX();
  
  ByteString getY();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfPublicKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */