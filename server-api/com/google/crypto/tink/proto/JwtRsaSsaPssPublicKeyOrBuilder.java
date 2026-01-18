package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtRsaSsaPssPublicKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  int getAlgorithmValue();
  
  JwtRsaSsaPssAlgorithm getAlgorithm();
  
  ByteString getN();
  
  ByteString getE();
  
  boolean hasCustomKid();
  
  JwtRsaSsaPssPublicKey.CustomKid getCustomKid();
  
  JwtRsaSsaPssPublicKey.CustomKidOrBuilder getCustomKidOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssPublicKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */