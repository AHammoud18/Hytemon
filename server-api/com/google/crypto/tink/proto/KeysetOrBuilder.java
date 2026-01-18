package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface KeysetOrBuilder extends MessageOrBuilder {
  int getPrimaryKeyId();
  
  List<Keyset.Key> getKeyList();
  
  Keyset.Key getKey(int paramInt);
  
  int getKeyCount();
  
  List<? extends Keyset.KeyOrBuilder> getKeyOrBuilderList();
  
  Keyset.KeyOrBuilder getKeyOrBuilder(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeysetOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */