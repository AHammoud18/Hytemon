package org.bouncycastle.pqc.crypto.falcon;

class SamplerZ {
  static int sample(SamplerCtx paramSamplerCtx, double paramDouble1, double paramDouble2) {
    return sampler(paramSamplerCtx, paramDouble1, paramDouble2);
  }
  
  static int gaussian0_sampler(FalconRNG paramFalconRNG) {
    int[] arrayOfInt = { 
        10745844, 3068844, 3741698, 5559083, 1580863, 8248194, 2260429, 13669192, 2736639, 708981, 
        4421575, 10046180, 169348, 7122675, 4136815, 30538, 13063405, 7650655, 4132, 14505003, 
        7826148, 417, 16768101, 11363290, 31, 8444042, 8086568, 1, 12844466, 265321, 
        0, 1232676, 13644283, 0, 38047, 9111839, 0, 870, 6138264, 0, 
        14, 12545723, 0, 0, 3104126, 0, 0, 28824, 0, 0, 
        198, 0, 0, 1 };
    long l = paramFalconRNG.prng_get_u64();
    int m = paramFalconRNG.prng_get_u8() & 0xFF;
    int i = (int)l & 0xFFFFFF;
    int j = (int)(l >>> 24L) & 0xFFFFFF;
    int k = (int)(l >>> 48L) | m << 16;
    int n = 0;
    for (byte b = 0; b < arrayOfInt.length; b += 3) {
      int i1 = arrayOfInt[b + 2];
      int i2 = arrayOfInt[b + 1];
      int i3 = arrayOfInt[b];
      int i4 = i - i1 >>> 31;
      i4 = j - i2 - i4 >>> 31;
      i4 = k - i3 - i4 >>> 31;
      n += i4;
    } 
    return n;
  }
  
  private static int BerExp(FalconRNG paramFalconRNG, double paramDouble1, double paramDouble2) {
    int k;
    int i = (int)(paramDouble1 * 1.4426950408889634D);
    double d = paramDouble1 - i * 0.6931471805599453D;
    int j = i;
    j ^= (j ^ 0x3F) & -(63 - j >>> 31);
    i = j;
    long l = (FPREngine.fpr_expm_p63(d, paramDouble2) << 1L) - 1L >>> i;
    byte b = 64;
    do {
      b -= 8;
      k = (paramFalconRNG.prng_get_u8() & 0xFF) - ((int)(l >>> b) & 0xFF);
    } while (k == 0 && b > 0);
    return k >>> 31;
  }
  
  private static int sampler(SamplerCtx paramSamplerCtx, double paramDouble1, double paramDouble2) {
    SamplerCtx samplerCtx = paramSamplerCtx;
    int i = (int)FPREngine.fpr_floor(paramDouble1);
    double d1 = paramDouble1 - i;
    double d2 = paramDouble2 * paramDouble2 * 0.5D;
    double d3 = paramDouble2 * samplerCtx.sigma_min;
    while (true) {
      int j = gaussian0_sampler(samplerCtx.p);
      int m = samplerCtx.p.prng_get_u8() & 0xFF & 0x1;
      int k = m + ((m << 1) - 1) * j;
      double d = k - d1;
      d = d * d * d2;
      d -= (j * j) * 0.15086504887537272D;
      if (BerExp(samplerCtx.p, d, d3) != 0)
        return i + k; 
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\SamplerZ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */