package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class CompositeSignatureSpec implements AlgorithmParameterSpec {
  private final boolean isPrehashMode;
  
  private final AlgorithmParameterSpec secondaryParameterSpec;
  
  public CompositeSignatureSpec(boolean paramBoolean) {
    this(paramBoolean, null);
  }
  
  public CompositeSignatureSpec(boolean paramBoolean, AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    this.isPrehashMode = paramBoolean;
    this.secondaryParameterSpec = paramAlgorithmParameterSpec;
  }
  
  public boolean isPrehashMode() {
    return this.isPrehashMode;
  }
  
  public AlgorithmParameterSpec getSecondarySpec() {
    return this.secondaryParameterSpec;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\CompositeSignatureSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */