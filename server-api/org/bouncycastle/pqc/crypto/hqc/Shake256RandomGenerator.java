package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.crypto.digests.SHAKEDigest;

class Shake256RandomGenerator {
  private final SHAKEDigest digest = new SHAKEDigest(256);
  
  public Shake256RandomGenerator(byte[] paramArrayOfbyte, byte paramByte) {
    this.digest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    this.digest.update(paramByte);
  }
  
  public Shake256RandomGenerator(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
    this.digest.update(paramArrayOfbyte, paramInt1, paramInt2);
    this.digest.update(paramByte);
  }
  
  public void init(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
    this.digest.reset();
    this.digest.update(paramArrayOfbyte, paramInt1, paramInt2);
    this.digest.update(paramByte);
  }
  
  public void nextBytes(byte[] paramArrayOfbyte) {
    this.digest.doOutput(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void nextBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.digest.doOutput(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void xofGetBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramInt & 0x7;
    int j = paramInt - i;
    this.digest.doOutput(paramArrayOfbyte, 0, j);
    if (i != 0) {
      byte[] arrayOfByte = new byte[8];
      this.digest.doOutput(arrayOfByte, 0, 8);
      System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, j, i);
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\hqc\Shake256RandomGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */