package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.crypto.digests.SHAKEDigest;

class FalconSign {
  void smallints_to_fpr(double[] paramArrayOfdouble, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    int i = 1 << paramInt2;
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble[paramInt1 + b] = paramArrayOfbyte[b]; 
  }
  
  void ffSampling_fft_dyntree(SamplerCtx paramSamplerCtx, double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3, double[] paramArrayOfdouble4, int paramInt4, double[] paramArrayOfdouble5, int paramInt5, int paramInt6, int paramInt7, double[] paramArrayOfdouble6, int paramInt8) {
    if (paramInt7 == 0) {
      double d = paramArrayOfdouble3[paramInt3];
      d = Math.sqrt(d) * FPREngine.fpr_inv_sigma[paramInt6];
      paramArrayOfdouble1[paramInt1] = SamplerZ.sample(paramSamplerCtx, paramArrayOfdouble1[paramInt1], d);
      paramArrayOfdouble2[paramInt2] = SamplerZ.sample(paramSamplerCtx, paramArrayOfdouble2[paramInt2], d);
      return;
    } 
    int i = 1 << paramInt7;
    int j = i >> 1;
    FalconFFT.poly_LDL_fft(paramArrayOfdouble3, paramInt3, paramArrayOfdouble4, paramInt4, paramArrayOfdouble5, paramInt5, paramInt7);
    FalconFFT.poly_split_fft(paramArrayOfdouble6, paramInt8, paramArrayOfdouble6, paramInt8 + j, paramArrayOfdouble3, paramInt3, paramInt7);
    System.arraycopy(paramArrayOfdouble6, paramInt8, paramArrayOfdouble3, paramInt3, i);
    FalconFFT.poly_split_fft(paramArrayOfdouble6, paramInt8, paramArrayOfdouble6, paramInt8 + j, paramArrayOfdouble5, paramInt5, paramInt7);
    System.arraycopy(paramArrayOfdouble6, paramInt8, paramArrayOfdouble5, paramInt5, i);
    System.arraycopy(paramArrayOfdouble4, paramInt4, paramArrayOfdouble6, paramInt8, i);
    System.arraycopy(paramArrayOfdouble3, paramInt3, paramArrayOfdouble4, paramInt4, j);
    System.arraycopy(paramArrayOfdouble5, paramInt5, paramArrayOfdouble4, paramInt4 + j, j);
    int m = paramInt8 + i;
    FalconFFT.poly_split_fft(paramArrayOfdouble6, m, paramArrayOfdouble6, m + j, paramArrayOfdouble2, paramInt2, paramInt7);
    ffSampling_fft_dyntree(paramSamplerCtx, paramArrayOfdouble6, m, paramArrayOfdouble6, m + j, paramArrayOfdouble5, paramInt5, paramArrayOfdouble5, paramInt5 + j, paramArrayOfdouble4, paramInt4 + j, paramInt6, paramInt7 - 1, paramArrayOfdouble6, m + i);
    FalconFFT.poly_merge_fft(paramArrayOfdouble6, paramInt8 + (i << 1), paramArrayOfdouble6, m, paramArrayOfdouble6, m + j, paramInt7);
    System.arraycopy(paramArrayOfdouble2, paramInt2, paramArrayOfdouble6, m, i);
    FalconFFT.poly_sub(paramArrayOfdouble6, m, paramArrayOfdouble6, paramInt8 + (i << 1), paramInt7);
    System.arraycopy(paramArrayOfdouble6, paramInt8 + (i << 1), paramArrayOfdouble2, paramInt2, i);
    FalconFFT.poly_mul_fft(paramArrayOfdouble6, paramInt8, paramArrayOfdouble6, m, paramInt7);
    FalconFFT.poly_add(paramArrayOfdouble1, paramInt1, paramArrayOfdouble6, paramInt8, paramInt7);
    int k = paramInt8;
    FalconFFT.poly_split_fft(paramArrayOfdouble6, k, paramArrayOfdouble6, k + j, paramArrayOfdouble1, paramInt1, paramInt7);
    ffSampling_fft_dyntree(paramSamplerCtx, paramArrayOfdouble6, k, paramArrayOfdouble6, k + j, paramArrayOfdouble3, paramInt3, paramArrayOfdouble3, paramInt3 + j, paramArrayOfdouble4, paramInt4, paramInt6, paramInt7 - 1, paramArrayOfdouble6, k + i);
    FalconFFT.poly_merge_fft(paramArrayOfdouble1, paramInt1, paramArrayOfdouble6, k, paramArrayOfdouble6, k + j, paramInt7);
  }
  
  int do_sign_dyn(SamplerCtx paramSamplerCtx, short[] paramArrayOfshort1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, short[] paramArrayOfshort2, int paramInt1, double[] paramArrayOfdouble, int paramInt2) {
    int i = 1 << paramInt1;
    int i1 = paramInt2;
    int i2 = i1 + i;
    int i3 = i2 + i;
    int i4 = i3 + i;
    smallints_to_fpr(paramArrayOfdouble, i2, paramArrayOfbyte1, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i1, paramArrayOfbyte2, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i4, paramArrayOfbyte3, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i3, paramArrayOfbyte4, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i2, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i1, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i4, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i3, paramInt1);
    FalconFFT.poly_neg(paramArrayOfdouble, i2, paramInt1);
    FalconFFT.poly_neg(paramArrayOfdouble, i4, paramInt1);
    int j = i4 + i;
    int k = j + i;
    System.arraycopy(paramArrayOfdouble, i2, paramArrayOfdouble, j, i);
    FalconFFT.poly_mulselfadj_fft(paramArrayOfdouble, j, paramInt1);
    System.arraycopy(paramArrayOfdouble, i1, paramArrayOfdouble, k, i);
    FalconFFT.poly_muladj_fft(paramArrayOfdouble, k, paramArrayOfdouble, i3, paramInt1);
    FalconFFT.poly_mulselfadj_fft(paramArrayOfdouble, i1, paramInt1);
    FalconFFT.poly_add(paramArrayOfdouble, i1, paramArrayOfdouble, j, paramInt1);
    System.arraycopy(paramArrayOfdouble, i2, paramArrayOfdouble, j, i);
    FalconFFT.poly_muladj_fft(paramArrayOfdouble, i2, paramArrayOfdouble, i4, paramInt1);
    FalconFFT.poly_add(paramArrayOfdouble, i2, paramArrayOfdouble, k, paramInt1);
    FalconFFT.poly_mulselfadj_fft(paramArrayOfdouble, i3, paramInt1);
    System.arraycopy(paramArrayOfdouble, i4, paramArrayOfdouble, k, i);
    FalconFFT.poly_mulselfadj_fft(paramArrayOfdouble, k, paramInt1);
    FalconFFT.poly_add(paramArrayOfdouble, i3, paramArrayOfdouble, k, paramInt1);
    int i5 = i1;
    int i6 = i2;
    int i7 = i3;
    i2 = j;
    j = i2 + i;
    k = j + i;
    byte b;
    for (b = 0; b < i; b++)
      paramArrayOfdouble[j + b] = paramArrayOfshort2[b]; 
    FalconFFT.FFT(paramArrayOfdouble, j, paramInt1);
    double d = 8.137358613394092E-5D;
    System.arraycopy(paramArrayOfdouble, j, paramArrayOfdouble, k, i);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, k, paramArrayOfdouble, i2, paramInt1);
    FalconFFT.poly_mulconst(paramArrayOfdouble, k, -d, paramInt1);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, j, paramArrayOfdouble, i4, paramInt1);
    FalconFFT.poly_mulconst(paramArrayOfdouble, j, d, paramInt1);
    System.arraycopy(paramArrayOfdouble, j, paramArrayOfdouble, i4, 2 * i);
    j = i7 + i;
    k = j + i;
    ffSampling_fft_dyntree(paramSamplerCtx, paramArrayOfdouble, j, paramArrayOfdouble, k, paramArrayOfdouble, i5, paramArrayOfdouble, i6, paramArrayOfdouble, i7, paramInt1, paramInt1, paramArrayOfdouble, k + i);
    i2 = i1 + i;
    i3 = i2 + i;
    i4 = i3 + i;
    System.arraycopy(paramArrayOfdouble, j, paramArrayOfdouble, i4 + i, i * 2);
    j = i4 + i;
    k = j + i;
    smallints_to_fpr(paramArrayOfdouble, i2, paramArrayOfbyte1, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i1, paramArrayOfbyte2, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i4, paramArrayOfbyte3, paramInt1);
    smallints_to_fpr(paramArrayOfdouble, i3, paramArrayOfbyte4, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i2, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i1, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i4, paramInt1);
    FalconFFT.FFT(paramArrayOfdouble, i3, paramInt1);
    FalconFFT.poly_neg(paramArrayOfdouble, i2, paramInt1);
    FalconFFT.poly_neg(paramArrayOfdouble, i4, paramInt1);
    int m = k + i;
    int n = m + i;
    System.arraycopy(paramArrayOfdouble, j, paramArrayOfdouble, m, i);
    System.arraycopy(paramArrayOfdouble, k, paramArrayOfdouble, n, i);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, m, paramArrayOfdouble, i1, paramInt1);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, n, paramArrayOfdouble, i3, paramInt1);
    FalconFFT.poly_add(paramArrayOfdouble, m, paramArrayOfdouble, n, paramInt1);
    System.arraycopy(paramArrayOfdouble, j, paramArrayOfdouble, n, i);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, n, paramArrayOfdouble, i2, paramInt1);
    System.arraycopy(paramArrayOfdouble, m, paramArrayOfdouble, j, i);
    FalconFFT.poly_mul_fft(paramArrayOfdouble, k, paramArrayOfdouble, i4, paramInt1);
    FalconFFT.poly_add(paramArrayOfdouble, k, paramArrayOfdouble, n, paramInt1);
    FalconFFT.iFFT(paramArrayOfdouble, j, paramInt1);
    FalconFFT.iFFT(paramArrayOfdouble, k, paramInt1);
    int i8 = 0;
    int i9 = 0;
    for (b = 0; b < i; b++) {
      int i10 = (paramArrayOfshort2[b] & 0xFFFF) - (int)FPREngine.fpr_rint(paramArrayOfdouble[j + b]);
      i8 += i10 * i10;
      i9 |= i8;
    } 
    i8 |= -(i9 >>> 31);
    short[] arrayOfShort = new short[i];
    for (b = 0; b < i; b++)
      arrayOfShort[b] = (short)(int)-FPREngine.fpr_rint(paramArrayOfdouble[k + b]); 
    if (FalconCommon.is_short_half(i8, arrayOfShort, paramInt1) != 0) {
      System.arraycopy(arrayOfShort, 0, paramArrayOfshort1, 0, i);
      return 1;
    } 
    return 0;
  }
  
  void sign_dyn(short[] paramArrayOfshort1, SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, short[] paramArrayOfshort2, int paramInt, double[] paramArrayOfdouble) {
    SamplerCtx samplerCtx;
    boolean bool = false;
    do {
      samplerCtx = new SamplerCtx();
      samplerCtx.sigma_min = FPREngine.fpr_sigma_min[paramInt];
      samplerCtx.p.prng_init(paramSHAKEDigest);
    } while (do_sign_dyn(samplerCtx, paramArrayOfshort1, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramArrayOfshort2, paramInt, paramArrayOfdouble, bool) == 0);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */