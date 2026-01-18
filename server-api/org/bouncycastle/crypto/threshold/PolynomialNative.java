package org.bouncycastle.crypto.threshold;

class PolynomialNative extends Polynomial {
  private final int IRREDUCIBLE;
  
  public PolynomialNative(ShamirSecretSplitter.Algorithm paramAlgorithm) {
    switch (paramAlgorithm) {
      case AES:
        this.IRREDUCIBLE = 283;
        return;
      case RSA:
        this.IRREDUCIBLE = 285;
        return;
    } 
    throw new IllegalArgumentException("The algorithm is not correct");
  }
  
  protected byte gfMul(int paramInt1, int paramInt2) {
    int i = 0;
    while (paramInt2 > 0) {
      if ((paramInt2 & 0x1) != 0)
        i ^= paramInt1; 
      paramInt1 <<= 1;
      if ((paramInt1 & 0x100) != 0)
        paramInt1 ^= this.IRREDUCIBLE; 
      paramInt2 >>= 1;
    } 
    while (i >= 256) {
      if ((i & 0x100) != 0)
        i ^= this.IRREDUCIBLE; 
      i <<= 1;
    } 
    return (byte)(i & 0xFF);
  }
  
  protected byte gfDiv(int paramInt1, int paramInt2) {
    return gfMul(paramInt1, gfPow((byte)paramInt2, (byte)-2) & 0xFF);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\PolynomialNative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */