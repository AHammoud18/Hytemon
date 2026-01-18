package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface PrfBasedDeriverKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasPrfKey();
  
  KeyData getPrfKey();
  
  KeyDataOrBuilder getPrfKeyOrBuilder();
  
  boolean hasParams();
  
  PrfBasedDeriverParams getParams();
  
  PrfBasedDeriverParamsOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\PrfBasedDeriverKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */