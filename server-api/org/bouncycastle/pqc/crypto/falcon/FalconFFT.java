package org.bouncycastle.pqc.crypto.falcon;

class FalconFFT {
  static void FFT(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    int j = 1 << paramInt2;
    int k = j >> 1;
    int i = k;
    byte b = 1;
    int m;
    for (m = 2; b < paramInt2; m <<= 1) {
      int n = i >> 1;
      int i1 = m >> 1;
      byte b1 = 0;
      int i2;
      for (i2 = 0; b1 < i1; i2 += i) {
        int i3 = i2 + n + paramInt1;
        int i4 = m + b1 << 1;
        double d1 = FPREngine.fpr_gm_tab[i4];
        double d2 = FPREngine.fpr_gm_tab[i4 + 1];
        i4 = paramInt1 + i2;
        int i5 = i4 + k;
        int i6 = i4 + n;
        for (int i7 = i6 + k; i4 < i3; i7++) {
          double d3 = paramArrayOfdouble[i4];
          double d4 = paramArrayOfdouble[i5];
          double d7 = paramArrayOfdouble[i6];
          double d8 = paramArrayOfdouble[i7];
          double d5 = d7 * d1 - d8 * d2;
          double d6 = d7 * d2 + d8 * d1;
          paramArrayOfdouble[i4] = d3 + d5;
          paramArrayOfdouble[i5] = d4 + d6;
          paramArrayOfdouble[i6] = d3 - d5;
          paramArrayOfdouble[i7] = d4 - d6;
          i4++;
          i5++;
          i6++;
        } 
        b1++;
      } 
      i = n;
      b++;
    } 
  }
  
  static void iFFT(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    int j = 1 << paramInt2;
    int m = 1;
    int n = j;
    int k = j >> 1;
    int i;
    for (i = paramInt2; i > 1; i--) {
      int i2 = n >> 1;
      int i1 = m << 1;
      byte b = 0;
      int i3;
      for (i3 = 0; i3 < k; i3 += i1) {
        int i4 = i3 + m + paramInt1;
        int i5 = i2 + b << 1;
        double d1 = FPREngine.fpr_gm_tab[i5];
        double d2 = -FPREngine.fpr_gm_tab[i5 + 1];
        i5 = paramInt1 + i3;
        int i6 = i5 + k;
        int i7 = i5 + m;
        for (int i8 = i7 + k; i5 < i4; i8++) {
          double d3 = paramArrayOfdouble[i5];
          double d4 = paramArrayOfdouble[i6];
          double d5 = paramArrayOfdouble[i7];
          double d6 = paramArrayOfdouble[i8];
          paramArrayOfdouble[i5] = d3 + d5;
          paramArrayOfdouble[i6] = d4 + d6;
          d3 -= d5;
          d4 -= d6;
          paramArrayOfdouble[i7] = d3 * d1 - d4 * d2;
          paramArrayOfdouble[i8] = d3 * d2 + d4 * d1;
          i5++;
          i6++;
          i7++;
        } 
        b++;
      } 
      m = i1;
      n = i2;
    } 
    if (paramInt2 > 0) {
      double d = FPREngine.fpr_p2_tab[paramInt2];
      for (i = 0; i < j; i++)
        paramArrayOfdouble[paramInt1 + i] = paramArrayOfdouble[paramInt1 + i] * d; 
    } 
  }
  
  static void poly_add(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble1[paramInt1 + b] = paramArrayOfdouble1[paramInt1 + b] + paramArrayOfdouble2[paramInt2 + b]; 
  }
  
  static void poly_sub(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble1[paramInt1 + b] = paramArrayOfdouble1[paramInt1 + b] - paramArrayOfdouble2[paramInt2 + b]; 
  }
  
  static void poly_neg(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    int i = 1 << paramInt2;
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble[paramInt1 + b] = -paramArrayOfdouble[paramInt1 + b]; 
  }
  
  static void poly_adj_fft(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    int i = 1 << paramInt2;
    for (int j = i >> 1; j < i; j++)
      paramArrayOfdouble[paramInt1 + j] = -paramArrayOfdouble[paramInt1 + j]; 
  }
  
  static void poly_mul_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    int j = i >> 1;
    byte b = 0;
    int k = paramInt1;
    int m = paramInt1 + j;
    int n = paramInt2;
    while (b < j) {
      double d1 = paramArrayOfdouble1[k];
      double d2 = paramArrayOfdouble1[m];
      double d3 = paramArrayOfdouble2[n];
      double d4 = paramArrayOfdouble2[n + j];
      paramArrayOfdouble1[k] = d1 * d3 - d2 * d4;
      paramArrayOfdouble1[m] = d1 * d4 + d2 * d3;
      b++;
      k++;
      n++;
      m++;
    } 
  }
  
  static void poly_muladj_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    int j = i >> 1;
    byte b = 0;
    for (int k = paramInt1; b < j; k++) {
      double d1 = paramArrayOfdouble1[k];
      double d2 = paramArrayOfdouble1[k + j];
      double d3 = paramArrayOfdouble2[paramInt2 + b];
      double d4 = paramArrayOfdouble2[paramInt2 + b + j];
      paramArrayOfdouble1[k] = d1 * d3 + d2 * d4;
      paramArrayOfdouble1[k + j] = d2 * d3 - d1 * d4;
      b++;
    } 
  }
  
  static void poly_mulselfadj_fft(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    int i = 1 << paramInt2;
    int j = i >> 1;
    for (byte b = 0; b < j; b++) {
      double d1 = paramArrayOfdouble[paramInt1 + b];
      double d2 = paramArrayOfdouble[paramInt1 + b + j];
      paramArrayOfdouble[paramInt1 + b] = d1 * d1 + d2 * d2;
      paramArrayOfdouble[paramInt1 + b + j] = 0.0D;
    } 
  }
  
  static void poly_mulconst(double[] paramArrayOfdouble, int paramInt1, double paramDouble, int paramInt2) {
    int i = 1 << paramInt2;
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble[paramInt1 + b] = paramArrayOfdouble[paramInt1 + b] * paramDouble; 
  }
  
  static void poly_invnorm2_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3, int paramInt4) {
    int i = 1 << paramInt4;
    int j = i >> 1;
    for (byte b = 0; b < j; b++) {
      double d1 = paramArrayOfdouble2[paramInt2 + b];
      double d2 = paramArrayOfdouble2[paramInt2 + b + j];
      double d3 = paramArrayOfdouble3[paramInt3 + b];
      double d4 = paramArrayOfdouble3[paramInt3 + b + j];
      paramArrayOfdouble1[paramInt1 + b] = 1.0D / (d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
    } 
  }
  
  static void poly_add_muladj_fft(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5, int paramInt) {
    int i = 1 << paramInt;
    int j = i >> 1;
    for (byte b = 0; b < j; b++) {
      int k = b + j;
      double d1 = paramArrayOfdouble2[b];
      double d2 = paramArrayOfdouble2[k];
      double d3 = paramArrayOfdouble3[b];
      double d4 = paramArrayOfdouble3[k];
      double d5 = paramArrayOfdouble4[b];
      double d6 = paramArrayOfdouble4[k];
      double d7 = paramArrayOfdouble5[b];
      double d8 = paramArrayOfdouble5[k];
      double d9 = d1 * d5 + d2 * d6;
      double d10 = d2 * d5 - d1 * d6;
      double d11 = d3 * d7 + d4 * d8;
      double d12 = d4 * d7 - d3 * d8;
      paramArrayOfdouble1[b] = d9 + d11;
      paramArrayOfdouble1[k] = d10 + d12;
    } 
  }
  
  static void poly_mul_autoadj_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    int j = i >> 1;
    for (byte b = 0; b < j; b++) {
      paramArrayOfdouble1[paramInt1 + b] = paramArrayOfdouble1[paramInt1 + b] * paramArrayOfdouble2[paramInt2 + b];
      paramArrayOfdouble1[paramInt1 + b + j] = paramArrayOfdouble1[paramInt1 + b + j] * paramArrayOfdouble2[paramInt2 + b];
    } 
  }
  
  static void poly_div_autoadj_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
    int i = 1 << paramInt3;
    int j = i >> 1;
    for (byte b = 0; b < j; b++) {
      double d = 1.0D / paramArrayOfdouble2[paramInt2 + b];
      paramArrayOfdouble1[paramInt1 + b] = paramArrayOfdouble1[paramInt1 + b] * d;
      paramArrayOfdouble1[paramInt1 + b + j] = paramArrayOfdouble1[paramInt1 + b + j] * d;
    } 
  }
  
  static void poly_LDL_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3, int paramInt4) {
    int i = 1 << paramInt4;
    int j = i >> 1;
    byte b = 0;
    int k = j;
    int m = paramInt2;
    for (int n = paramInt2 + j; b < j; n++) {
      double d1 = paramArrayOfdouble1[paramInt1 + b];
      double d2 = paramArrayOfdouble1[paramInt1 + k];
      double d3 = paramArrayOfdouble2[m];
      double d4 = paramArrayOfdouble2[n];
      double d6 = 1.0D / (d1 * d1 + d2 * d2);
      double d5 = d1 * d6;
      d6 *= -d2;
      d1 = d3 * d5 - d4 * d6;
      d2 = d3 * d6 + d4 * d5;
      d5 = d3;
      d6 = d4;
      d3 = d1 * d5 + d2 * d6;
      d4 = d1 * -d6 + d2 * d5;
      paramArrayOfdouble3[paramInt3 + b] = paramArrayOfdouble3[paramInt3 + b] - d3;
      paramArrayOfdouble3[paramInt3 + k] = paramArrayOfdouble3[paramInt3 + k] - d4;
      paramArrayOfdouble2[m] = d1;
      paramArrayOfdouble2[n] = -d2;
      b++;
      k++;
      m++;
    } 
  }
  
  static void poly_split_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3, int paramInt4) {
    int i = 1 << paramInt4;
    int j = i >> 1;
    int k = j >> 1;
    paramArrayOfdouble1[paramInt1] = paramArrayOfdouble3[paramInt3];
    paramArrayOfdouble2[paramInt2] = paramArrayOfdouble3[paramInt3 + j];
    for (byte b = 0; b < k; b++) {
      int m = paramInt3 + (b << 1);
      double d1 = paramArrayOfdouble3[m];
      double d2 = paramArrayOfdouble3[m++ + j];
      double d3 = paramArrayOfdouble3[m];
      double d4 = paramArrayOfdouble3[m + j];
      paramArrayOfdouble1[paramInt1 + b] = (d1 + d3) * 0.5D;
      paramArrayOfdouble1[paramInt1 + b + k] = (d2 + d4) * 0.5D;
      double d5 = d1 - d3;
      double d6 = d2 - d4;
      m = b + j << 1;
      d3 = FPREngine.fpr_gm_tab[m];
      d4 = -FPREngine.fpr_gm_tab[m + 1];
      m = paramInt2 + b;
      paramArrayOfdouble2[m] = (d5 * d3 - d6 * d4) * 0.5D;
      paramArrayOfdouble2[m + k] = (d5 * d4 + d6 * d3) * 0.5D;
    } 
  }
  
  static void poly_merge_fft(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3, int paramInt4) {
    int i = 1 << paramInt4;
    int j = i >> 1;
    int k = j >> 1;
    paramArrayOfdouble1[paramInt1] = paramArrayOfdouble2[paramInt2];
    paramArrayOfdouble1[paramInt1 + j] = paramArrayOfdouble3[paramInt3];
    for (byte b = 0; b < k; b++) {
      int m = paramInt3 + b;
      double d1 = paramArrayOfdouble3[m];
      double d2 = paramArrayOfdouble3[m + k];
      m = b + j << 1;
      double d5 = FPREngine.fpr_gm_tab[m];
      double d6 = FPREngine.fpr_gm_tab[m + 1];
      double d3 = d1 * d5 - d2 * d6;
      double d4 = d1 * d6 + d2 * d5;
      m = paramInt2 + b;
      d1 = paramArrayOfdouble2[m];
      d2 = paramArrayOfdouble2[m + k];
      m = paramInt1 + (b << 1);
      paramArrayOfdouble1[m] = d1 + d3;
      paramArrayOfdouble1[m++ + j] = d2 + d4;
      paramArrayOfdouble1[m] = d1 - d3;
      paramArrayOfdouble1[m + j] = d2 - d4;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconFFT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */