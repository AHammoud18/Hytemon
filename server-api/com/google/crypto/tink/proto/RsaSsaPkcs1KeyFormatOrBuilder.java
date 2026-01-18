package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RsaSsaPkcs1KeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  RsaSsaPkcs1Params getParams();
  
  RsaSsaPkcs1ParamsOrBuilder getParamsOrBuilder();
  
  int getModulusSizeInBits();
  
  ByteString getPublicExponent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1KeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */