package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface EncryptedKeysetOrBuilder extends MessageOrBuilder {
  ByteString getEncryptedKeyset();
  
  boolean hasKeysetInfo();
  
  KeysetInfo getKeysetInfo();
  
  KeysetInfoOrBuilder getKeysetInfoOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EncryptedKeysetOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */