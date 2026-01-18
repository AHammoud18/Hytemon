package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RsaSsaPssKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  RsaSsaPssParams getParams();
  
  RsaSsaPssParamsOrBuilder getParamsOrBuilder();
  
  int getModulusSizeInBits();
  
  ByteString getPublicExponent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPssKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */