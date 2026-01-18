package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ElGamalGenParameterSpec implements AlgorithmParameterSpec {
  private int primeSize;
  
  public ElGamalGenParameterSpec(int paramInt) {
    this.primeSize = paramInt;
  }
  
  public int getPrimeSize() {
    return this.primeSize;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\spec\ElGamalGenParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */