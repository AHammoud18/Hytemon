package org.bouncycastle.crypto;

public interface EncapsulatedSecretExtractor {
  byte[] extractSecret(byte[] paramArrayOfbyte);
  
  int getEncapsulationLength();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\EncapsulatedSecretExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */