package org.bouncycastle.pqc.crypto.snova;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CTRModeCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.GF16;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Pack;

class SnovaEngine {
  private static final Map<Integer, byte[]> fixedAbqSet = (Map)new HashMap<>();
  
  private static final Map<Integer, byte[][]> sSet = (Map)new HashMap<>();
  
  private static final Map<Integer, int[][]> xSSet = (Map)new HashMap<>();
  
  private final SnovaParameters params;
  
  private final int l;
  
  private final int lsq;
  
  private final int m;
  
  private final int v;
  
  private final int o;
  
  private final int alpha;
  
  private final int n;
  
  final byte[][] S;
  
  final int[][] xS;
  
  public SnovaEngine(SnovaParameters paramSnovaParameters) {
    this.params = paramSnovaParameters;
    this.l = paramSnovaParameters.getL();
    this.lsq = paramSnovaParameters.getLsq();
    this.m = paramSnovaParameters.getM();
    this.v = paramSnovaParameters.getV();
    this.o = paramSnovaParameters.getO();
    this.alpha = paramSnovaParameters.getAlpha();
    this.n = paramSnovaParameters.getN();
    if (!xSSet.containsKey(Integers.valueOf(this.l))) {
      byte[][] arrayOfByte = new byte[this.l][this.lsq];
      int[][] arrayOfInt = new int[this.l][this.lsq];
      be_aI(arrayOfByte[0], 0, (byte)1);
      beTheS(arrayOfByte[1]);
      byte b;
      for (b = 2; b < this.l; b++)
        GF16Utils.gf16mMul(arrayOfByte[b - 1], arrayOfByte[1], arrayOfByte[b], this.l); 
      for (b = 0; b < this.l; b++) {
        for (byte b1 = 0; b1 < this.lsq; b1++)
          arrayOfInt[b][b1] = GF16Utils.gf16FromNibble(arrayOfByte[b][b1]); 
      } 
      sSet.put(Integers.valueOf(this.l), arrayOfByte);
      xSSet.put(Integers.valueOf(this.l), arrayOfInt);
    } 
    this.S = sSet.get(Integers.valueOf(this.l));
    this.xS = xSSet.get(Integers.valueOf(this.l));
    if (this.l < 4 && !fixedAbqSet.containsKey(Integers.valueOf(this.o))) {
      int i = this.alpha * this.l;
      int j = i * this.l;
      int k = this.o * i;
      int m = this.o * j;
      byte[] arrayOfByte1 = new byte[m << 2];
      byte[] arrayOfByte2 = new byte[m + k];
      byte[] arrayOfByte3 = new byte[k << 2];
      byte[] arrayOfByte4 = "SNOVA_ABQ".getBytes();
      SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
      sHAKEDigest.update(arrayOfByte4, 0, arrayOfByte4.length);
      sHAKEDigest.doFinal(arrayOfByte2, 0, arrayOfByte2.length);
      GF16.decode(arrayOfByte2, arrayOfByte1, m << 1);
      GF16.decode(arrayOfByte2, j, arrayOfByte3, 0, k << 1);
      byte b = 0;
      int n = 0;
      int i1;
      for (i1 = 0; b < this.o; i1 += i) {
        byte b1 = 0;
        int i2 = i1;
        int i3;
        for (i3 = n; b1 < this.alpha; i3 += this.lsq) {
          makeInvertibleByAddingAS(arrayOfByte1, i3);
          makeInvertibleByAddingAS(arrayOfByte1, m + i3);
          genAFqS(arrayOfByte3, i2, arrayOfByte1, (m << 1) + i3);
          genAFqS(arrayOfByte3, k + i2, arrayOfByte1, (m << 1) + m + i3);
          b1++;
          i2 += this.l;
        } 
        b++;
        n += j;
      } 
      fixedAbqSet.put(Integers.valueOf(this.o), arrayOfByte1);
    } 
  }
  
  private void beTheS(byte[] paramArrayOfbyte) {
    byte b = 0;
    for (int i = 0; b < this.l; i += this.l) {
      for (byte b1 = 0; b1 < this.l; b1++) {
        int j = 8 - b + b1;
        paramArrayOfbyte[i + b1] = (byte)(j & 0xF);
      } 
      b++;
    } 
    if (this.l == 5)
      paramArrayOfbyte[24] = 9; 
  }
  
  private void be_aI(byte[] paramArrayOfbyte, int paramInt, byte paramByte) {
    int i = this.l + 1;
    byte b = 0;
    while (b < this.l) {
      paramArrayOfbyte[paramInt] = paramByte;
      b++;
      paramInt += i;
    } 
  }
  
  private void genAFqSCT(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
    int[] arrayOfInt = new int[this.lsq];
    int i = this.l + 1;
    int j = GF16Utils.gf16FromNibble(paramArrayOfbyte1[paramInt]);
    int k = 0;
    int m;
    for (m = 0; k < this.l; m += i) {
      arrayOfInt[m] = j;
      k++;
    } 
    for (k = 1; k < this.l - 1; k++) {
      j = GF16Utils.gf16FromNibble(paramArrayOfbyte1[paramInt + k]);
      for (m = 0; m < this.lsq; m++)
        arrayOfInt[m] = arrayOfInt[m] ^ j * this.xS[k][m]; 
    } 
    k = GF16Utils.ctGF16IsNotZero(paramArrayOfbyte1[paramInt + this.l - 1]);
    m = k * paramArrayOfbyte1[paramInt + this.l - 1] + (1 - k) * (15 + GF16Utils.ctGF16IsNotZero(paramArrayOfbyte1[paramInt]) - paramArrayOfbyte1[paramInt]);
    j = GF16Utils.gf16FromNibble((byte)m);
    for (byte b = 0; b < this.lsq; b++) {
      arrayOfInt[b] = arrayOfInt[b] ^ j * this.xS[this.l - 1][b];
      paramArrayOfbyte2[b] = GF16Utils.gf16ToNibble(arrayOfInt[b]);
    } 
    Arrays.fill(arrayOfInt, 0);
  }
  
  private void makeInvertibleByAddingAS(byte[] paramArrayOfbyte, int paramInt) {
    if (gf16Determinant(paramArrayOfbyte, paramInt) != 0)
      return; 
    for (byte b = 1; b < 16; b++) {
      generateASMatrixTo(paramArrayOfbyte, paramInt, (byte)b);
      if (gf16Determinant(paramArrayOfbyte, paramInt) != 0)
        return; 
    } 
  }
  
  private byte gf16Determinant(byte[] paramArrayOfbyte, int paramInt) {
    switch (this.l) {
      case 2:
        return determinant2x2(paramArrayOfbyte, paramInt);
      case 3:
        return determinant3x3(paramArrayOfbyte, paramInt);
      case 4:
        return determinant4x4(paramArrayOfbyte, paramInt);
      case 5:
        return determinant5x5(paramArrayOfbyte, paramInt);
    } 
    throw new IllegalStateException();
  }
  
  private byte determinant2x2(byte[] paramArrayOfbyte, int paramInt) {
    return (byte)(GF16.mul(paramArrayOfbyte[paramInt], paramArrayOfbyte[paramInt + 3]) ^ GF16.mul(paramArrayOfbyte[paramInt + 1], paramArrayOfbyte[paramInt + 2]));
  }
  
  private byte determinant3x3(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt++];
    byte b2 = paramArrayOfbyte[paramInt++];
    byte b3 = paramArrayOfbyte[paramInt++];
    byte b4 = paramArrayOfbyte[paramInt++];
    byte b5 = paramArrayOfbyte[paramInt++];
    byte b6 = paramArrayOfbyte[paramInt++];
    byte b7 = paramArrayOfbyte[paramInt++];
    byte b8 = paramArrayOfbyte[paramInt++];
    byte b9 = paramArrayOfbyte[paramInt];
    return (byte)(GF16.mul(b1, GF16.mul(b5, b9) ^ GF16.mul(b6, b8)) ^ GF16.mul(b2, GF16.mul(b4, b9) ^ GF16.mul(b6, b7)) ^ GF16.mul(b3, GF16.mul(b4, b8) ^ GF16.mul(b5, b7)));
  }
  
  private byte determinant4x4(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt++];
    byte b2 = paramArrayOfbyte[paramInt++];
    byte b3 = paramArrayOfbyte[paramInt++];
    byte b4 = paramArrayOfbyte[paramInt++];
    byte b5 = paramArrayOfbyte[paramInt++];
    byte b6 = paramArrayOfbyte[paramInt++];
    byte b7 = paramArrayOfbyte[paramInt++];
    byte b8 = paramArrayOfbyte[paramInt++];
    byte b9 = paramArrayOfbyte[paramInt++];
    byte b10 = paramArrayOfbyte[paramInt++];
    byte b11 = paramArrayOfbyte[paramInt++];
    byte b12 = paramArrayOfbyte[paramInt++];
    byte b13 = paramArrayOfbyte[paramInt++];
    byte b14 = paramArrayOfbyte[paramInt++];
    byte b15 = paramArrayOfbyte[paramInt++];
    byte b16 = paramArrayOfbyte[paramInt];
    byte b17 = (byte)(GF16.mul(b11, b16) ^ GF16.mul(b12, b15));
    byte b18 = (byte)(GF16.mul(b10, b16) ^ GF16.mul(b12, b14));
    byte b19 = (byte)(GF16.mul(b10, b15) ^ GF16.mul(b11, b14));
    byte b20 = (byte)(GF16.mul(b9, b16) ^ GF16.mul(b12, b13));
    byte b21 = (byte)(GF16.mul(b9, b15) ^ GF16.mul(b11, b13));
    byte b22 = (byte)(GF16.mul(b9, b14) ^ GF16.mul(b10, b13));
    return (byte)(GF16.mul(b1, GF16.mul(b6, b17) ^ GF16.mul(b7, b18) ^ GF16.mul(b8, b19)) ^ GF16.mul(b2, GF16.mul(b5, b17) ^ GF16.mul(b7, b20) ^ GF16.mul(b8, b21)) ^ GF16.mul(b3, GF16.mul(b5, b18) ^ GF16.mul(b6, b20) ^ GF16.mul(b8, b22)) ^ GF16.mul(b4, GF16.mul(b5, b19) ^ GF16.mul(b6, b21) ^ GF16.mul(b7, b22)));
  }
  
  private byte determinant5x5(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt++];
    byte b2 = paramArrayOfbyte[paramInt++];
    byte b3 = paramArrayOfbyte[paramInt++];
    byte b4 = paramArrayOfbyte[paramInt++];
    byte b5 = paramArrayOfbyte[paramInt++];
    byte b6 = paramArrayOfbyte[paramInt++];
    byte b7 = paramArrayOfbyte[paramInt++];
    byte b8 = paramArrayOfbyte[paramInt++];
    byte b9 = paramArrayOfbyte[paramInt++];
    byte b10 = paramArrayOfbyte[paramInt++];
    byte b11 = paramArrayOfbyte[paramInt++];
    byte b12 = paramArrayOfbyte[paramInt++];
    byte b13 = paramArrayOfbyte[paramInt++];
    byte b14 = paramArrayOfbyte[paramInt++];
    byte b15 = paramArrayOfbyte[paramInt++];
    byte b16 = paramArrayOfbyte[paramInt++];
    byte b17 = paramArrayOfbyte[paramInt++];
    byte b18 = paramArrayOfbyte[paramInt++];
    byte b19 = paramArrayOfbyte[paramInt++];
    byte b20 = paramArrayOfbyte[paramInt++];
    byte b21 = paramArrayOfbyte[paramInt++];
    byte b22 = paramArrayOfbyte[paramInt++];
    byte b23 = paramArrayOfbyte[paramInt++];
    byte b24 = paramArrayOfbyte[paramInt++];
    byte b25 = paramArrayOfbyte[paramInt];
    byte b26 = (byte)(GF16.mul(b6, b12) ^ GF16.mul(b7, b11));
    byte b27 = (byte)(GF16.mul(b6, b13) ^ GF16.mul(b8, b11));
    byte b28 = (byte)(GF16.mul(b6, b14) ^ GF16.mul(b9, b11));
    byte b29 = (byte)(GF16.mul(b6, b15) ^ GF16.mul(b10, b11));
    byte b30 = (byte)(GF16.mul(b7, b13) ^ GF16.mul(b8, b12));
    byte b31 = (byte)(GF16.mul(b7, b14) ^ GF16.mul(b9, b12));
    byte b32 = (byte)(GF16.mul(b7, b15) ^ GF16.mul(b10, b12));
    byte b33 = (byte)(GF16.mul(b8, b14) ^ GF16.mul(b9, b13));
    byte b34 = (byte)(GF16.mul(b8, b15) ^ GF16.mul(b10, b13));
    byte b35 = (byte)(GF16.mul(b9, b15) ^ GF16.mul(b10, b14));
    null = (byte)GF16.mul(GF16.mul(b1, b30) ^ GF16.mul(b2, b27) ^ GF16.mul(b3, b26), GF16.mul(b19, b25) ^ GF16.mul(b20, b24));
    null = (byte)(null ^ GF16.mul(GF16.mul(b1, b31) ^ GF16.mul(b2, b28) ^ GF16.mul(b4, b26), GF16.mul(b18, b25) ^ GF16.mul(b20, b23)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b1, b32) ^ GF16.mul(b2, b29) ^ GF16.mul(b5, b26), GF16.mul(b18, b24) ^ GF16.mul(b19, b23)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b1, b33) ^ GF16.mul(b3, b28) ^ GF16.mul(b4, b27), GF16.mul(b17, b25) ^ GF16.mul(b20, b22)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b1, b34) ^ GF16.mul(b3, b29) ^ GF16.mul(b5, b27), GF16.mul(b17, b24) ^ GF16.mul(b19, b22)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b1, b35) ^ GF16.mul(b4, b29) ^ GF16.mul(b5, b28), GF16.mul(b17, b23) ^ GF16.mul(b18, b22)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b2, b33) ^ GF16.mul(b3, b31) ^ GF16.mul(b4, b30), GF16.mul(b16, b25) ^ GF16.mul(b20, b21)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b2, b34) ^ GF16.mul(b3, b32) ^ GF16.mul(b5, b30), GF16.mul(b16, b24) ^ GF16.mul(b19, b21)));
    null = (byte)(null ^ GF16.mul(GF16.mul(b2, b35) ^ GF16.mul(b4, b32) ^ GF16.mul(b5, b31), GF16.mul(b16, b23) ^ GF16.mul(b18, b21)));
    return (byte)(null ^ GF16.mul(GF16.mul(b3, b35) ^ GF16.mul(b4, b34) ^ GF16.mul(b5, b33), GF16.mul(b16, b22) ^ GF16.mul(b17, b21)));
  }
  
  private void generateASMatrixTo(byte[] paramArrayOfbyte, int paramInt, byte paramByte) {
    byte b = 0;
    int i;
    for (i = paramInt; b < this.l; i += this.l) {
      for (byte b1 = 0; b1 < this.l; b1++) {
        byte b2 = (byte)(8 - b + b1);
        if (this.l == 5 && b == 4 && b1 == 4)
          b2 = 9; 
        paramArrayOfbyte[i + b1] = (byte)(paramArrayOfbyte[i + b1] ^ GF16.mul(b2, paramByte));
      } 
      b++;
    } 
  }
  
  private void genAFqS(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
    be_aI(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1[paramInt1]);
    byte b;
    for (b = 1; b < this.l - 1; b++)
      gf16mScaleTo(this.S[b], paramArrayOfbyte1[paramInt1 + b], paramArrayOfbyte2, paramInt2); 
    b = (byte)((paramArrayOfbyte1[paramInt1 + this.l - 1] != 0) ? paramArrayOfbyte1[paramInt1 + this.l - 1] : (16 - paramArrayOfbyte1[paramInt1] + ((paramArrayOfbyte1[paramInt1] == 0) ? 1 : 0)));
    gf16mScaleTo(this.S[this.l - 1], b, paramArrayOfbyte2, paramInt2);
  }
  
  private void gf16mScaleTo(byte[] paramArrayOfbyte1, byte paramByte, byte[] paramArrayOfbyte2, int paramInt) {
    byte b = 0;
    int i;
    for (i = 0; b < this.l; i += this.l) {
      for (byte b1 = 0; b1 < this.l; b1++)
        paramArrayOfbyte2[i + b1 + paramInt] = (byte)(paramArrayOfbyte2[i + b1 + paramInt] ^ GF16.mul(paramArrayOfbyte1[i + b1], paramByte)); 
      b++;
    } 
  }
  
  private void genF(MapGroup2 paramMapGroup2, MapGroup1 paramMapGroup1, byte[][][] paramArrayOfbyte) {
    copy4DMatrix(paramMapGroup1.p11, paramMapGroup2.f11, this.m, this.v, this.v, this.lsq);
    copy4DMatrix(paramMapGroup1.p12, paramMapGroup2.f12, this.m, this.v, this.o, this.lsq);
    copy4DMatrix(paramMapGroup1.p21, paramMapGroup2.f21, this.m, this.o, this.v, this.lsq);
    for (byte b = 0; b < this.m; b++) {
      for (byte b1 = 0; b1 < this.v; b1++) {
        for (byte b2 = 0; b2 < this.o; b2++) {
          for (byte b3 = 0; b3 < this.v; b3++)
            GF16Utils.gf16mMulToTo(paramMapGroup1.p11[b][b1][b3], paramArrayOfbyte[b3][b2], paramMapGroup1.p11[b][b3][b1], paramMapGroup2.f12[b][b1][b2], paramMapGroup2.f21[b][b2][b1], this.l); 
        } 
      } 
    } 
  }
  
  private static void copy4DMatrix(byte[][][][] paramArrayOfbyte1, byte[][][][] paramArrayOfbyte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    for (byte b = 0; b < paramInt1; b++) {
      for (byte b1 = 0; b1 < paramInt2; b1++) {
        for (byte b2 = 0; b2 < paramInt3; b2++)
          System.arraycopy(paramArrayOfbyte1[b][b1][b2], 0, paramArrayOfbyte2[b][b1][b2], 0, paramInt4); 
      } 
    } 
  }
  
  public void genP22(byte[] paramArrayOfbyte, int paramInt, byte[][][] paramArrayOfbyte1, byte[][][][] paramArrayOfbyte2, byte[][][][] paramArrayOfbyte3) {
    int i = this.o * this.lsq;
    int j = i * this.o;
    byte[] arrayOfByte = new byte[this.m * j];
    byte b = 0;
    int k;
    for (k = 0; b < this.m; k += j) {
      byte b1 = 0;
      int m;
      for (m = k; b1 < this.o; m += i) {
        byte b2 = 0;
        int n;
        for (n = m; b2 < this.o; n += this.lsq) {
          for (byte b3 = 0; b3 < this.v; b3++)
            GF16Utils.gf16mMulTo(paramArrayOfbyte1[b3][b1], paramArrayOfbyte3[b][b3][b2], paramArrayOfbyte2[b][b1][b3], paramArrayOfbyte1[b3][b2], arrayOfByte, n, this.l); 
          b2++;
        } 
        b1++;
      } 
      b++;
    } 
    GF16.encode(arrayOfByte, paramArrayOfbyte, paramInt, arrayOfByte.length);
  }
  
  private void genSeedsAndT12(byte[][][] paramArrayOfbyte, byte[] paramArrayOfbyte1) {
    int i = this.v * this.o * this.l;
    int j = i + 1 >>> 1;
    byte[] arrayOfByte1 = new byte[j];
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    sHAKEDigest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    sHAKEDigest.doFinal(arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = new byte[i];
    GF16.decode(arrayOfByte1, arrayOfByte2, i);
    int k = 0;
    for (byte b = 0; b < this.v; b++) {
      for (byte b1 = 0; b1 < this.o; b1++) {
        genAFqSCT(arrayOfByte2, k, paramArrayOfbyte[b][b1]);
        k += this.l;
      } 
    } 
  }
  
  public void genABQP(MapGroup1 paramMapGroup1, byte[] paramArrayOfbyte) {
    int i = this.lsq * (2 * this.m * this.alpha + this.m * (this.n * this.n - this.m * this.m)) + this.l * 2 * this.m * this.alpha;
    byte[] arrayOfByte1 = new byte[this.m * this.alpha * this.l << 1];
    byte[] arrayOfByte2 = new byte[i + 1 >> 1];
    if (this.params.isPkExpandShake()) {
      long l = 0L;
      int j = 0;
      int k = arrayOfByte2.length;
      byte[] arrayOfByte = new byte[8];
      SHAKEDigest sHAKEDigest = new SHAKEDigest(128);
      while (k > 0) {
        sHAKEDigest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
        Pack.longToLittleEndian(l, arrayOfByte, 0);
        sHAKEDigest.update(arrayOfByte, 0, 8);
        int m = Math.min(k, 168);
        sHAKEDigest.doFinal(arrayOfByte2, j, m);
        j += m;
        k -= m;
        l++;
      } 
    } else {
      byte[] arrayOfByte3 = new byte[16];
      CTRModeCipher cTRModeCipher = SICBlockCipher.newInstance((BlockCipher)AESEngine.newInstance());
      cTRModeCipher.init(true, (CipherParameters)new ParametersWithIV((CipherParameters)new KeyParameter(paramArrayOfbyte), arrayOfByte3));
      int j = cTRModeCipher.getBlockSize();
      byte[] arrayOfByte4 = new byte[j];
      int k;
      for (k = 0; k + j <= arrayOfByte2.length; k += j)
        cTRModeCipher.processBlock(arrayOfByte4, 0, arrayOfByte2, k); 
      if (k < arrayOfByte2.length) {
        cTRModeCipher.processBlock(arrayOfByte4, 0, arrayOfByte4, 0);
        int m = arrayOfByte2.length - k;
        System.arraycopy(arrayOfByte4, 0, arrayOfByte2, k, m);
      } 
    } 
    if ((this.lsq & 0x1) == 0) {
      paramMapGroup1.decode(arrayOfByte2, i - arrayOfByte1.length >> 1, (this.l >= 4));
    } else {
      byte[] arrayOfByte = new byte[i - arrayOfByte1.length];
      GF16.decode(arrayOfByte2, arrayOfByte, arrayOfByte.length);
      paramMapGroup1.fill(arrayOfByte, (this.l >= 4));
    } 
    if (this.l >= 4) {
      GF16.decode(arrayOfByte2, i - arrayOfByte1.length >> 1, arrayOfByte1, 0, arrayOfByte1.length);
      int j = 0;
      int k = this.m * this.alpha * this.l;
      for (byte b = 0; b < this.m; b++) {
        for (byte b1 = 0; b1 < this.alpha; b1++) {
          makeInvertibleByAddingAS(paramMapGroup1.aAlpha[b][b1], 0);
          makeInvertibleByAddingAS(paramMapGroup1.bAlpha[b][b1], 0);
          genAFqS(arrayOfByte1, j, paramMapGroup1.qAlpha1[b][b1], 0);
          genAFqS(arrayOfByte1, k, paramMapGroup1.qAlpha2[b][b1], 0);
          j += this.l;
          k += this.l;
        } 
      } 
    } else {
      int j = this.o * this.alpha * this.lsq;
      byte[] arrayOfByte = fixedAbqSet.get(Integers.valueOf(this.o));
      MapGroup1.fillAlpha(arrayOfByte, 0, paramMapGroup1.aAlpha, this.m * j);
      MapGroup1.fillAlpha(arrayOfByte, j, paramMapGroup1.bAlpha, (this.m - 1) * j);
      MapGroup1.fillAlpha(arrayOfByte, j * 2, paramMapGroup1.qAlpha1, (this.m - 2) * j);
      MapGroup1.fillAlpha(arrayOfByte, j * 3, paramMapGroup1.qAlpha2, (this.m - 3) * j);
    } 
  }
  
  public void genMap1T12Map2(SnovaKeyElements paramSnovaKeyElements, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    genSeedsAndT12(paramSnovaKeyElements.T12, paramArrayOfbyte2);
    genABQP(paramSnovaKeyElements.map1, paramArrayOfbyte1);
    genF(paramSnovaKeyElements.map2, paramSnovaKeyElements.map1, paramSnovaKeyElements.T12);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\SnovaEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */