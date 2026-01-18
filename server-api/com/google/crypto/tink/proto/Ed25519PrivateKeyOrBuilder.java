package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface Ed25519PrivateKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  ByteString getKeyValue();
  
  boolean hasPublicKey();
  
  Ed25519PublicKey getPublicKey();
  
  Ed25519PublicKeyOrBuilder getPublicKeyOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ed25519PrivateKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */