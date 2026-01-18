package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface KmsEnvelopeAeadKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  boolean hasParams();
  
  KmsEnvelopeAeadKeyFormat getParams();
  
  KmsEnvelopeAeadKeyFormatOrBuilder getParamsOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsEnvelopeAeadKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */