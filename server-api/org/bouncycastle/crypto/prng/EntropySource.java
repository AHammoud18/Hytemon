package org.bouncycastle.crypto.prng;

public interface EntropySource {
  boolean isPredictionResistant();
  
  byte[] getEntropy();
  
  int entropySize();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\prng\EntropySource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */