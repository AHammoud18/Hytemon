package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtRsaSsaPssPrivateKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasPublicKey();
  
  JwtRsaSsaPssPublicKey getPublicKey();
  
  JwtRsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder();
  
  ByteString getD();
  
  ByteString getP();
  
  ByteString getQ();
  
  ByteString getDp();
  
  ByteString getDq();
  
  ByteString getCrt();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssPrivateKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */