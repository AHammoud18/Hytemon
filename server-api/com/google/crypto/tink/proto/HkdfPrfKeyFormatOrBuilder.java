package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface HkdfPrfKeyFormatOrBuilder extends MessageOrBuilder {
  boolean hasParams();
  
  HkdfPrfParams getParams();
  
  HkdfPrfParamsOrBuilder getParamsOrBuilder();
  
  int getKeySize();
  
  int getVersion();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HkdfPrfKeyFormatOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */