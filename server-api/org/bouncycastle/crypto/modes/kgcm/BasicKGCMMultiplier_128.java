package org.bouncycastle.crypto.modes.kgcm;

public class BasicKGCMMultiplier_128 implements KGCMMultiplier {
  private final long[] H = new long[2];
  
  public void init(long[] paramArrayOflong) {
    KGCMUtil_128.copy(paramArrayOflong, this.H);
  }
  
  public void multiplyH(long[] paramArrayOflong) {
    KGCMUtil_128.multiply(paramArrayOflong, this.H, paramArrayOflong);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\modes\kgcm\BasicKGCMMultiplier_128.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */