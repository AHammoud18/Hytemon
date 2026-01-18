package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface KeyDataOrBuilder extends MessageOrBuilder {
  String getTypeUrl();
  
  ByteString getTypeUrlBytes();
  
  ByteString getValue();
  
  int getKeyMaterialTypeValue();
  
  KeyData.KeyMaterialType getKeyMaterialType();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyDataOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */