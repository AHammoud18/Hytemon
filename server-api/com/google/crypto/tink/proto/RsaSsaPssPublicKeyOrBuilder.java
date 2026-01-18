package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RsaSsaPssPublicKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  RsaSsaPssParams getParams();
  
  RsaSsaPssParamsOrBuilder getParamsOrBuilder();
  
  ByteString getN();
  
  ByteString getE();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPssPublicKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */