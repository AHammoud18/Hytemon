package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ECNamedCurveGenParameterSpec implements AlgorithmParameterSpec {
  private String name;
  
  public ECNamedCurveGenParameterSpec(String paramString) {
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\spec\ECNamedCurveGenParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */