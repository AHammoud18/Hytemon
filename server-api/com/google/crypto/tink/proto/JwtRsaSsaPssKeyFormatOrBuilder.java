package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtRsaSsaPssKeyFormatOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  int getAlgorithmValue();
  
  JwtRsaSsaPssAlgorithm getAlgorithm();
  
  int getModulusSizeInBits();
  
  ByteString getPublicExponent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */