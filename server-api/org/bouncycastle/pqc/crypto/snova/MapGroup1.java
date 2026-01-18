package org.bouncycastle.pqc.crypto.snova;

import org.bouncycastle.util.GF16;

class MapGroup1 {
  public final byte[][][][] p11;
  
  public final byte[][][][] p12;
  
  public final byte[][][][] p21;
  
  public final byte[][][] aAlpha;
  
  public final byte[][][] bAlpha;
  
  public final byte[][][] qAlpha1;
  
  public final byte[][][] qAlpha2;
  
  public MapGroup1(SnovaParameters paramSnovaParameters) {
    int i = paramSnovaParameters.getM();
    int j = paramSnovaParameters.getV();
    int k = paramSnovaParameters.getO();
    int m = paramSnovaParameters.getAlpha();
    int n = paramSnovaParameters.getLsq();
    this.p11 = new byte[i][j][j][n];
    this.p12 = new byte[i][j][k][n];
    this.p21 = new byte[i][k][j][n];
    this.aAlpha = new byte[i][m][n];
    this.bAlpha = new byte[i][m][n];
    this.qAlpha1 = new byte[i][m][n];
    this.qAlpha2 = new byte[i][m][n];
  }
  
  void decode(byte[] paramArrayOfbyte, int paramInt, boolean paramBoolean) {
    int i = decodeP(paramArrayOfbyte, 0, this.p11, paramInt);
    i += decodeP(paramArrayOfbyte, i, this.p12, paramInt - i);
    i += decodeP(paramArrayOfbyte, i, this.p21, paramInt - i);
    if (paramBoolean) {
      i += decodeAlpha(paramArrayOfbyte, i, this.aAlpha, paramInt - i);
      i += decodeAlpha(paramArrayOfbyte, i, this.bAlpha, paramInt - i);
      i += decodeAlpha(paramArrayOfbyte, i, this.qAlpha1, paramInt - i);
      decodeAlpha(paramArrayOfbyte, i, this.qAlpha2, paramInt - i);
    } 
  }
  
  static int decodeP(byte[] paramArrayOfbyte, int paramInt1, byte[][][][] paramArrayOfbyte1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramArrayOfbyte1.length; b++)
      i += decodeAlpha(paramArrayOfbyte, paramInt1 + i, paramArrayOfbyte1[b], paramInt2); 
    return i;
  }
  
  private static int decodeAlpha(byte[] paramArrayOfbyte, int paramInt1, byte[][][] paramArrayOfbyte1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramArrayOfbyte1.length; b++)
      i += decodeArray(paramArrayOfbyte, paramInt1 + i, paramArrayOfbyte1[b], paramInt2 - i); 
    return i;
  }
  
  static int decodeArray(byte[] paramArrayOfbyte, int paramInt1, byte[][] paramArrayOfbyte1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
      int j = Math.min((paramArrayOfbyte1[b]).length, paramInt2 << 1);
      GF16.decode(paramArrayOfbyte, paramInt1 + i, paramArrayOfbyte1[b], 0, j);
      j = j + 1 >> 1;
      i += j;
      paramInt2 -= j;
    } 
    return i;
  }
  
  void fill(byte[] paramArrayOfbyte, boolean paramBoolean) {
    int i = fillP(paramArrayOfbyte, 0, this.p11, paramArrayOfbyte.length);
    i += fillP(paramArrayOfbyte, i, this.p12, paramArrayOfbyte.length - i);
    i += fillP(paramArrayOfbyte, i, this.p21, paramArrayOfbyte.length - i);
    if (paramBoolean) {
      i += fillAlpha(paramArrayOfbyte, i, this.aAlpha, paramArrayOfbyte.length - i);
      i += fillAlpha(paramArrayOfbyte, i, this.bAlpha, paramArrayOfbyte.length - i);
      i += fillAlpha(paramArrayOfbyte, i, this.qAlpha1, paramArrayOfbyte.length - i);
      fillAlpha(paramArrayOfbyte, i, this.qAlpha2, paramArrayOfbyte.length - i);
    } 
  }
  
  static int fillP(byte[] paramArrayOfbyte, int paramInt1, byte[][][][] paramArrayOfbyte1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramArrayOfbyte1.length; b++)
      i += fillAlpha(paramArrayOfbyte, paramInt1 + i, paramArrayOfbyte1[b], paramInt2 - i); 
    return i;
  }
  
  static int fillAlpha(byte[] paramArrayOfbyte, int paramInt1, byte[][][] paramArrayOfbyte1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
      for (byte b1 = 0; b1 < (paramArrayOfbyte1[b]).length; b1++) {
        int j = Math.min((paramArrayOfbyte1[b][b1]).length, paramInt2 - i);
        System.arraycopy(paramArrayOfbyte, paramInt1 + i, paramArrayOfbyte1[b][b1], 0, j);
        i += j;
      } 
    } 
    return i;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\MapGroup1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */