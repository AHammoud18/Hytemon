package org.bouncycastle.tsp.ers;

import org.bouncycastle.operator.DigestCalculator;

public class ERSByteData extends ERSCachingData {
  private final byte[] content;
  
  public ERSByteData(byte[] paramArrayOfbyte) {
    this.content = paramArrayOfbyte;
  }
  
  protected byte[] calculateHash(DigestCalculator paramDigestCalculator, byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = ERSUtil.calculateDigest(paramDigestCalculator, this.content);
    return (paramArrayOfbyte != null) ? ERSUtil.concatPreviousHashes(paramDigestCalculator, paramArrayOfbyte, arrayOfByte) : arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\tsp\ers\ERSByteData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */