package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ChaCha20Poly1305KeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  ByteString getKeyValue();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\ChaCha20Poly1305KeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */