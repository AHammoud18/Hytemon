package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface HmacPrfKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  HmacPrfParams getParams();
  
  HmacPrfParamsOrBuilder getParamsOrBuilder();
  
  int getKeySize();
  
  int getVersion();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HmacPrfKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */