package org.bouncycastle.crypto.threshold;

import java.io.IOException;

public class ShamirSplitSecret implements SplitSecret {
  private final ShamirSplitSecretShare[] secretShares;
  
  private final Polynomial poly;
  
  public ShamirSplitSecret(ShamirSecretSplitter.Algorithm paramAlgorithm, ShamirSecretSplitter.Mode paramMode, ShamirSplitSecretShare[] paramArrayOfShamirSplitSecretShare) {
    this.secretShares = paramArrayOfShamirSplitSecretShare;
    this.poly = Polynomial.newInstance(paramAlgorithm, paramMode);
  }
  
  ShamirSplitSecret(Polynomial paramPolynomial, ShamirSplitSecretShare[] paramArrayOfShamirSplitSecretShare) {
    this.secretShares = paramArrayOfShamirSplitSecretShare;
    this.poly = paramPolynomial;
  }
  
  public ShamirSplitSecretShare[] getSecretShares() {
    return this.secretShares;
  }
  
  public ShamirSplitSecret multiple(int paramInt) throws IOException {
    for (byte b = 0; b < this.secretShares.length; b++) {
      byte[] arrayOfByte = this.secretShares[b].getEncoded();
      for (byte b1 = 0; b1 < arrayOfByte.length; b1++)
        arrayOfByte[b1] = this.poly.gfMul(arrayOfByte[b1] & 0xFF, paramInt); 
      this.secretShares[b] = new ShamirSplitSecretShare(arrayOfByte, b + 1);
    } 
    return this;
  }
  
  public ShamirSplitSecret divide(int paramInt) throws IOException {
    for (byte b = 0; b < this.secretShares.length; b++) {
      byte[] arrayOfByte = this.secretShares[b].getEncoded();
      for (byte b1 = 0; b1 < arrayOfByte.length; b1++)
        arrayOfByte[b1] = this.poly.gfDiv(arrayOfByte[b1] & 0xFF, paramInt); 
      this.secretShares[b] = new ShamirSplitSecretShare(arrayOfByte, b + 1);
    } 
    return this;
  }
  
  public byte[] getSecret() throws IOException {
    int i = this.secretShares.length;
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[i - 1];
    byte[][] arrayOfByte = new byte[i][(this.secretShares[0].getEncoded()).length];
    for (byte b = 0; b < i; b++) {
      arrayOfByte[b] = this.secretShares[b].getEncoded();
      byte b1 = 0;
      byte b2;
      for (b2 = 0; b2 < i; b2++) {
        if (b2 != b) {
          b1 = (byte)(b1 + 1);
          arrayOfByte2[b1] = this.poly.gfDiv((this.secretShares[b2]).r, (this.secretShares[b]).r ^ (this.secretShares[b2]).r);
        } 
      } 
      b1 = 1;
      for (b2 = 0; b2 != arrayOfByte2.length; b2++)
        b1 = this.poly.gfMul(b1 & 0xFF, arrayOfByte2[b2] & 0xFF); 
      arrayOfByte1[b] = b1;
    } 
    return this.poly.gfVecMul(arrayOfByte1, arrayOfByte);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\ShamirSplitSecret.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */