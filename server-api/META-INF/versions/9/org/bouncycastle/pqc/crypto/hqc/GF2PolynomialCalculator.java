package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.util.Arrays;

class GF2PolynomialCalculator {
  private final int VEC_N_SIZE_64;
  
  private final int PARAM_N;
  
  private final long RED_MASK;
  
  GF2PolynomialCalculator(int paramInt1, int paramInt2, long paramLong) {
    this.VEC_N_SIZE_64 = paramInt1;
    this.PARAM_N = paramInt2;
    this.RED_MASK = paramLong;
  }
  
  public void vectMul(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
    long[] arrayOfLong1 = new long[this.VEC_N_SIZE_64 << 1];
    long[] arrayOfLong2 = new long[this.VEC_N_SIZE_64 << 4];
    karatsuba(arrayOfLong1, 0, paramArrayOflong2, 0, paramArrayOflong3, 0, this.VEC_N_SIZE_64, arrayOfLong2, 0);
    reduce(paramArrayOflong1, arrayOfLong1);
  }
  
  private void schoolbookMul(long[] paramArrayOflong1, int paramInt1, long[] paramArrayOflong2, int paramInt2, long[] paramArrayOflong3, int paramInt3, int paramInt4) {
    Arrays.fill(paramArrayOflong1, paramInt1, paramInt1 + (paramInt4 << 1), 0L);
    byte b = 0;
    while (b < paramInt4) {
      long l = paramArrayOflong2[b + paramInt2];
      for (byte b1 = 0; b1 < 64; b1++) {
        long l1 = -(l >> b1 & 0x1L);
        if (b1 == 0) {
          byte b2 = 0;
          int i = paramInt1;
          for (int j = paramInt3; b2 < paramInt4; j++) {
            paramArrayOflong1[i] = paramArrayOflong1[i] ^ paramArrayOflong3[j] & l1;
            b2++;
            i++;
          } 
        } else {
          int i = 64 - b1;
          byte b2 = 0;
          int j = paramInt1;
          for (int k = paramInt3; b2 < paramInt4; k++) {
            paramArrayOflong1[j++] = paramArrayOflong1[j++] ^ paramArrayOflong3[k] << b1 & l1;
            paramArrayOflong1[j] = paramArrayOflong1[j] ^ paramArrayOflong3[k] >>> i & l1;
            b2++;
          } 
        } 
      } 
      b++;
      paramInt1++;
    } 
  }
  
  private void karatsuba(long[] paramArrayOflong1, int paramInt1, long[] paramArrayOflong2, int paramInt2, long[] paramArrayOflong3, int paramInt3, int paramInt4, long[] paramArrayOflong4, int paramInt5) {
    if (paramInt4 <= 16) {
      schoolbookMul(paramArrayOflong1, paramInt1, paramArrayOflong2, paramInt2, paramArrayOflong3, paramInt3, paramInt4);
      return;
    } 
    int i = paramInt4 >> 1;
    int j = paramInt4 - i;
    int k = paramInt4 << 1;
    int m = i << 1;
    int n = j << 1;
    int i1 = paramInt5 + k;
    int i2 = i1 + k;
    int i3 = i2 + k;
    int i4 = i3 + paramInt4;
    int i5 = paramInt5 + (paramInt4 << 3);
    karatsuba(paramArrayOflong4, paramInt5, paramArrayOflong2, paramInt2, paramArrayOflong3, paramInt3, i, paramArrayOflong4, i5);
    karatsuba(paramArrayOflong4, i1, paramArrayOflong2, paramInt2 + i, paramArrayOflong3, paramInt3 + i, j, paramArrayOflong4, i5);
    byte b;
    for (b = 0; b < j; b++) {
      long l1 = (b < i) ? paramArrayOflong2[paramInt2 + b] : 0L;
      long l2 = (b < i) ? paramArrayOflong3[paramInt3 + b] : 0L;
      paramArrayOflong4[i3 + b] = l1 ^ paramArrayOflong2[paramInt2 + i + b];
      paramArrayOflong4[i4 + b] = l2 ^ paramArrayOflong3[paramInt3 + i + b];
    } 
    karatsuba(paramArrayOflong4, i2, paramArrayOflong4, i3, paramArrayOflong4, i4, j, paramArrayOflong4, i5);
    System.arraycopy(paramArrayOflong4, paramInt5, paramArrayOflong1, paramInt1, m);
    System.arraycopy(paramArrayOflong4, i1, paramArrayOflong1, paramInt1 + m, n);
    for (b = 0; b < 2 * j; b++) {
      long l1 = (b < m) ? paramArrayOflong4[paramInt5 + b] : 0L;
      long l2 = (b < n) ? paramArrayOflong4[i1 + b] : 0L;
      paramArrayOflong1[paramInt1 + i + b] = paramArrayOflong1[paramInt1 + i + b] ^ paramArrayOflong4[i2 + b] ^ l1 ^ l2;
    } 
  }
  
  private void reduce(long[] paramArrayOflong1, long[] paramArrayOflong2) {
    for (byte b = 0; b < this.VEC_N_SIZE_64; b++)
      paramArrayOflong1[b] = paramArrayOflong2[b] ^ paramArrayOflong2[b + this.VEC_N_SIZE_64 - 1] >>> (this.PARAM_N & 0x3F) ^ paramArrayOflong2[b + this.VEC_N_SIZE_64] << (int)(64L - (this.PARAM_N & 0x3FL)); 
    paramArrayOflong1[this.VEC_N_SIZE_64 - 1] = paramArrayOflong1[this.VEC_N_SIZE_64 - 1] & this.RED_MASK;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\GF2PolynomialCalculator.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */