package org.bouncycastle.crypto;

public abstract class DefaultMultiBlockCipher implements MultiBlockCipher {
  public int getMultiBlockSize() {
    return getBlockSize();
  }
  
  public int processBlocks(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws DataLengthException, IllegalStateException {
    int i = 0;
    int j = getBlockSize();
    int k = paramInt2 * j;
    if (paramArrayOfbyte1 == paramArrayOfbyte2) {
      paramArrayOfbyte1 = new byte[k];
      System.arraycopy(paramArrayOfbyte2, paramInt1, paramArrayOfbyte1, 0, k);
      paramInt1 = 0;
    } 
    for (int m = 0; m != paramInt2; m++) {
      i += processBlock(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramInt3 + i);
      paramInt1 += j;
    } 
    return i;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\DefaultMultiBlockCipher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */