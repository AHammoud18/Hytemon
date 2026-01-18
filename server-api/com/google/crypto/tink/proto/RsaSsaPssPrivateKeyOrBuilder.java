package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RsaSsaPssPrivateKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasPublicKey();
  
  RsaSsaPssPublicKey getPublicKey();
  
  RsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder();
  
  ByteString getD();
  
  ByteString getP();
  
  ByteString getQ();
  
  ByteString getDp();
  
  ByteString getDq();
  
  ByteString getCrt();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPssPrivateKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */