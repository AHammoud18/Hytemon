package org.bouncycastle.crypto.threshold;

abstract class Polynomial {
  public static Polynomial newInstance(ShamirSecretSplitter.Algorithm paramAlgorithm, ShamirSecretSplitter.Mode paramMode) {
    return (Polynomial)((paramMode == ShamirSecretSplitter.Mode.Native) ? new PolynomialNative(paramAlgorithm) : new PolynomialTable(paramAlgorithm));
  }
  
  protected abstract byte gfMul(int paramInt1, int paramInt2);
  
  protected abstract byte gfDiv(int paramInt1, int paramInt2);
  
  protected byte gfPow(int paramInt, byte paramByte) {
    byte b = 1;
    for (byte b1 = 0; b1 < 8; b1++) {
      if ((paramByte & 1 << b1) != 0)
        b = gfMul(b & 0xFF, paramInt & 0xFF); 
      paramInt = gfMul(paramInt & 0xFF, paramInt & 0xFF);
    } 
    return (byte)b;
  }
  
  public byte[] gfVecMul(byte[] paramArrayOfbyte, byte[][] paramArrayOfbyte1) {
    byte[] arrayOfByte = new byte[(paramArrayOfbyte1[0]).length];
    for (byte b = 0; b < (paramArrayOfbyte1[0]).length; b++) {
      int i = 0;
      for (byte b1 = 0; b1 < paramArrayOfbyte.length; b1++)
        i ^= gfMul(paramArrayOfbyte[b1] & 0xFF, paramArrayOfbyte1[b1][b] & 0xFF); 
      arrayOfByte[b] = (byte)i;
    } 
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\Polynomial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */