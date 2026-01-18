package com.google.crypto.tink.proto;

import com.google.protobuf.MessageOrBuilder;

public interface EciesAeadHkdfParamsOrBuilder extends MessageOrBuilder {
  boolean hasKemParams();
  
  EciesHkdfKemParams getKemParams();
  
  EciesHkdfKemParamsOrBuilder getKemParamsOrBuilder();
  
  boolean hasDemParams();
  
  EciesAeadDemParams getDemParams();
  
  EciesAeadDemParamsOrBuilder getDemParamsOrBuilder();
  
  int getEcPointFormatValue();
  
  EcPointFormat getEcPointFormat();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfParamsOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */