package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtHmacKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  int getAlgorithmValue();
  
  JwtHmacAlgorithm getAlgorithm();
  
  ByteString getKeyValue();
  
  boolean hasCustomKid();
  
  JwtHmacKey.CustomKid getCustomKid();
  
  JwtHmacKey.CustomKidOrBuilder getCustomKidOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtHmacKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */