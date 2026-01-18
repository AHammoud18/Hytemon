package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface SlhDsaParamsOrBuilder extends MessageOrBuilder {
  int getKeySize();
  
  int getHashTypeValue();
  
  SlhDsaHashType getHashType();
  
  int getSigTypeValue();
  
  SlhDsaSignatureType getSigType();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaParamsOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */