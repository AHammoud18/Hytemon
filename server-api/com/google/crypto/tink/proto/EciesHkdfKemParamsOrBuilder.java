package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface EciesHkdfKemParamsOrBuilder extends MessageOrBuilder {
  int getCurveTypeValue();
  
  EllipticCurveType getCurveType();
  
  int getHkdfHashTypeValue();
  
  HashType getHkdfHashType();
  
  ByteString getHkdfSalt();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesHkdfKemParamsOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */