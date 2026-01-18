package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.util.Arrays;

public class ContextParameterSpec implements AlgorithmParameterSpec {
  public static ContextParameterSpec EMPTY_CONTEXT_SPEC = new ContextParameterSpec(new byte[0]);
  
  private final byte[] context;
  
  public ContextParameterSpec(byte[] paramArrayOfbyte) {
    this.context = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getContext() {
    return Arrays.clone(this.context);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\ContextParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */