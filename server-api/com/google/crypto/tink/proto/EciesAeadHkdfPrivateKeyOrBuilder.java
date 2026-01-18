package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface EciesAeadHkdfPrivateKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasPublicKey();
  
  EciesAeadHkdfPublicKey getPublicKey();
  
  EciesAeadHkdfPublicKeyOrBuilder getPublicKeyOrBuilder();
  
  ByteString getKeyValue();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfPrivateKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */