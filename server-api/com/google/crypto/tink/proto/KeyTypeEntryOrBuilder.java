package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

@Deprecated
public interface KeyTypeEntryOrBuilder extends MessageOrBuilder {
  String getPrimitiveName();
  
  ByteString getPrimitiveNameBytes();
  
  String getTypeUrl();
  
  ByteString getTypeUrlBytes();
  
  int getKeyManagerVersion();
  
  boolean getNewKeyAllowed();
  
  String getCatalogueName();
  
  ByteString getCatalogueNameBytes();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyTypeEntryOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */