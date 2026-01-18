package com.google.crypto.tink.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtEcdsaPublicKeyOrBuilder extends MessageOrBuilder {
  int getVersion();
  
  int getAlgorithmValue();
  
  JwtEcdsaAlgorithm getAlgorithm();
  
  ByteString getX();
  
  ByteString getY();
  
  boolean hasCustomKid();
  
  JwtEcdsaPublicKey.CustomKid getCustomKid();
  
  JwtEcdsaPublicKey.CustomKidOrBuilder getCustomKidOrBuilder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtEcdsaPublicKeyOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */