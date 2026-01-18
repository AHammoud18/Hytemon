package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface HmacPrfKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  HmacPrfParams getParams();
  
  HmacPrfParamsOrBuilder getParamsOrBuilder();
  
  ByteString getKeyValue();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HmacPrfKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */