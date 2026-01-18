package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface JwtHmacKeyFormatOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  int getAlgorithmValue();
  
  JwtHmacAlgorithm getAlgorithm();
  
  int getKeySize();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtHmacKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */