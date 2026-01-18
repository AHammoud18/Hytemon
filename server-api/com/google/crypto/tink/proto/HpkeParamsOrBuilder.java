package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface HpkeParamsOrBuilder extends MessageOrBuilder {
  int getKemValue();
  
  HpkeKem getKem();
  
  int getKdfValue();
  
  HpkeKdf getKdf();
  
  int getAeadValue();
  
  HpkeAead getAead();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeParamsOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */