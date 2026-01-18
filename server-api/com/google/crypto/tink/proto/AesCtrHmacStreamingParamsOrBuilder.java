package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface AesCtrHmacStreamingParamsOrBuilder extends MessageOrBuilder {
  int getCiphertextSegmentSize();
  
  int getDerivedKeySize();
  
  int getHkdfHashTypeValue();
  
  HashType getHkdfHashType();
  
  boolean hasHmacParams();
  
  HmacParams getHmacParams();
  
  HmacParamsOrBuilder getHmacParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacStreamingParamsOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */