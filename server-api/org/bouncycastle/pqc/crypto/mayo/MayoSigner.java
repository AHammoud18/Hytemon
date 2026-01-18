package org.bouncycastle.pqc.crypto.mayo;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Bytes;
import org.bouncycastle.util.GF16;
import org.bouncycastle.util.Longs;
import org.bouncycastle.util.Pack;

public class MayoSigner implements MessageSigner {
  private SecureRandom random;
  
  private MayoParameters params;
  
  private MayoPublicKeyParameters pubKey;
  
  private MayoPrivateKeyParameters privKey;
  
  private static final int F_TAIL_LEN = 4;
  
  private static final long EVEN_BYTES = 71777214294589695L;
  
  private static final long EVEN_2BYTES = 281470681808895L;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    if (paramBoolean) {
      this.pubKey = null;
      if (paramCipherParameters instanceof ParametersWithRandom) {
        ParametersWithRandom parametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.privKey = (MayoPrivateKeyParameters)parametersWithRandom.getParameters();
        this.random = parametersWithRandom.getRandom();
      } else {
        this.privKey = (MayoPrivateKeyParameters)paramCipherParameters;
        this.random = CryptoServicesRegistrar.getSecureRandom();
      } 
      this.params = this.privKey.getParameters();
    } else {
      this.pubKey = (MayoPublicKeyParameters)paramCipherParameters;
      this.params = this.pubKey.getParameters();
      this.privKey = null;
      this.random = null;
    } 
  }
  
  public byte[] generateSignature(byte[] paramArrayOfbyte) {
    int i = this.params.getK();
    int j = this.params.getV();
    int k = this.params.getO();
    int m = this.params.getN();
    int n = this.params.getM();
    int i1 = this.params.getVBytes();
    int i2 = this.params.getOBytes();
    int i3 = this.params.getSaltBytes();
    int i4 = this.params.getMVecLimbs();
    int i5 = this.params.getP1Limbs();
    int i6 = this.params.getPkSeedBytes();
    int i7 = this.params.getDigestBytes();
    int i8 = this.params.getSkSeedBytes();
    byte[] arrayOfByte1 = new byte[this.params.getMBytes()];
    byte[] arrayOfByte2 = new byte[n];
    byte[] arrayOfByte3 = new byte[n];
    byte[] arrayOfByte4 = new byte[i3];
    byte[] arrayOfByte5 = new byte[i * i1 + this.params.getRBytes()];
    byte[] arrayOfByte6 = new byte[j * i];
    int i9 = i * k;
    int i10 = i * m;
    byte[] arrayOfByte7 = new byte[(n + 7) / 8 * 8 * (i9 + 1)];
    byte[] arrayOfByte8 = new byte[i10];
    byte[] arrayOfByte9 = new byte[i9 + 1];
    byte[] arrayOfByte10 = new byte[i10];
    byte[] arrayOfByte11 = new byte[i7 + i3 + i8 + 1];
    byte[] arrayOfByte12 = new byte[this.params.getSigBytes()];
    long[] arrayOfLong1 = new long[i5 + this.params.getP2Limbs()];
    byte[] arrayOfByte13 = new byte[j * k];
    long[] arrayOfLong2 = new long[i9 * i4];
    long[] arrayOfLong3 = new long[i * i * i4];
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    try {
      byte[] arrayOfByte14 = this.privKey.getSeedSk();
      int i11 = i6 + i2;
      byte[] arrayOfByte15 = new byte[i11];
      sHAKEDigest.update(arrayOfByte14, 0, arrayOfByte14.length);
      sHAKEDigest.doFinal(arrayOfByte15, 0, i11);
      GF16.decode(arrayOfByte15, i6, arrayOfByte13, 0, arrayOfByte13.length);
      Utils.expandP1P2(this.params, arrayOfLong1, arrayOfByte15);
      int i12 = 0;
      int i13 = k * i4;
      int i14 = 0;
      int i15 = 0;
      int i16;
      for (i16 = 0; i14 < j; i16 += i13) {
        int i20 = i14;
        int i21 = i15;
        int i22;
        for (i22 = i16; i20 < j; i22 += i13) {
          if (i20 == i14) {
            i12 += i4;
          } else {
            byte b1 = 0;
            int i23;
            for (i23 = i5; b1 < k; i23 += i4) {
              GF16Utils.mVecMulAdd(i4, arrayOfLong1, i12, arrayOfByte13[i21 + b1], arrayOfLong1, i16 + i23);
              GF16Utils.mVecMulAdd(i4, arrayOfLong1, i12, arrayOfByte13[i15 + b1], arrayOfLong1, i22 + i23);
              b1++;
            } 
            i12 += i4;
          } 
          i20++;
          i21 += k;
        } 
        i14++;
        i15 += k;
      } 
      Arrays.fill(arrayOfByte15, (byte)0);
      sHAKEDigest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
      sHAKEDigest.doFinal(arrayOfByte11, 0, i7);
      this.random.nextBytes(arrayOfByte4);
      System.arraycopy(arrayOfByte4, 0, arrayOfByte11, i7, arrayOfByte4.length);
      System.arraycopy(arrayOfByte14, 0, arrayOfByte11, i7 + i3, i8);
      sHAKEDigest.update(arrayOfByte11, 0, i7 + i3 + i8);
      sHAKEDigest.doFinal(arrayOfByte4, 0, i3);
      System.arraycopy(arrayOfByte4, 0, arrayOfByte11, i7, i3);
      sHAKEDigest.update(arrayOfByte11, 0, i7 + i3);
      sHAKEDigest.doFinal(arrayOfByte1, 0, this.params.getMBytes());
      GF16.decode(arrayOfByte1, arrayOfByte2, n);
      i14 = j * i * i4;
      long[] arrayOfLong = new long[i14];
      byte[] arrayOfByte16 = new byte[j];
      byte b;
      for (b = 0; b <= 'ÿ'; b++) {
        arrayOfByte11[arrayOfByte11.length - 1] = (byte)b;
        sHAKEDigest.update(arrayOfByte11, 0, arrayOfByte11.length);
        sHAKEDigest.doFinal(arrayOfByte5, 0, arrayOfByte5.length);
        for (byte b1 = 0; b1 < i; b1++)
          GF16.decode(arrayOfByte5, b1 * i1, arrayOfByte6, b1 * j, j); 
        GF16Utils.mulAddMatXMMat(i4, arrayOfByte6, arrayOfLong1, i5, arrayOfLong2, i, j, k);
        GF16Utils.mulAddMUpperTriangularMatXMatTrans(i4, arrayOfLong1, arrayOfByte6, arrayOfLong, j, i);
        GF16Utils.mulAddMatXMMat(i4, arrayOfByte6, arrayOfLong, arrayOfLong3, i, j);
        computeRHS(arrayOfLong3, arrayOfByte2, arrayOfByte3);
        computeA(arrayOfLong2, arrayOfByte7);
        GF16.decode(arrayOfByte5, i * i1, arrayOfByte9, 0, i9);
        if (sampleSolution(arrayOfByte7, arrayOfByte3, arrayOfByte9, arrayOfByte8))
          break; 
        Arrays.fill(arrayOfLong2, 0L);
        Arrays.fill(arrayOfLong3, 0L);
      } 
      b = 0;
      int i17 = 0;
      int i18 = 0;
      int i19;
      for (i19 = 0; b < i; i19 += j) {
        GF16Utils.matMul(arrayOfByte13, arrayOfByte8, i17, arrayOfByte16, k, j);
        Bytes.xor(j, arrayOfByte6, i19, arrayOfByte16, arrayOfByte10, i18);
        System.arraycopy(arrayOfByte8, i17, arrayOfByte10, i18 + j, k);
        b++;
        i17 += k;
        i18 += m;
      } 
      GF16.encode(arrayOfByte10, arrayOfByte12, i10);
      System.arraycopy(arrayOfByte4, 0, arrayOfByte12, arrayOfByte12.length - i3, i3);
      return Arrays.concatenate(arrayOfByte12, paramArrayOfbyte);
    } finally {
      Arrays.fill(arrayOfByte1, (byte)0);
      Arrays.fill(arrayOfByte2, (byte)0);
      Arrays.fill(arrayOfByte3, (byte)0);
      Arrays.fill(arrayOfByte4, (byte)0);
      Arrays.fill(arrayOfByte5, (byte)0);
      Arrays.fill(arrayOfByte6, (byte)0);
      Arrays.fill(arrayOfByte7, (byte)0);
      Arrays.fill(arrayOfByte8, (byte)0);
      Arrays.fill(arrayOfByte9, (byte)0);
      Arrays.fill(arrayOfByte10, (byte)0);
      Arrays.fill(arrayOfByte11, (byte)0);
    } 
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    int i = this.params.getM();
    int j = this.params.getN();
    int k = this.params.getK();
    int m = k * j;
    int n = this.params.getP1Limbs();
    int i1 = this.params.getP2Limbs();
    int i2 = this.params.getP3Limbs();
    int i3 = this.params.getMBytes();
    int i4 = this.params.getSigBytes();
    int i5 = this.params.getDigestBytes();
    int i6 = this.params.getSaltBytes();
    int i7 = this.params.getMVecLimbs();
    byte[] arrayOfByte1 = new byte[i3];
    byte[] arrayOfByte2 = new byte[i];
    byte[] arrayOfByte3 = new byte[i << 1];
    byte[] arrayOfByte4 = new byte[m];
    long[] arrayOfLong1 = new long[n + i1 + i2];
    byte[] arrayOfByte5 = new byte[i5 + i6];
    byte[] arrayOfByte6 = this.pubKey.getEncoded();
    Utils.expandP1P2(this.params, arrayOfLong1, arrayOfByte6);
    Utils.unpackMVecs(arrayOfByte6, this.params.getPkSeedBytes(), arrayOfLong1, n + i1, i2 / i7, i);
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    sHAKEDigest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    sHAKEDigest.doFinal(arrayOfByte5, 0, i5);
    sHAKEDigest.update(arrayOfByte5, 0, i5);
    sHAKEDigest.update(paramArrayOfbyte2, i4 - i6, i6);
    sHAKEDigest.doFinal(arrayOfByte1, 0, i3);
    GF16.decode(arrayOfByte1, arrayOfByte2, i);
    GF16.decode(paramArrayOfbyte2, arrayOfByte4, m);
    long[] arrayOfLong2 = new long[k * k * i7];
    long[] arrayOfLong3 = new long[m * i7];
    mayoGenericMCalculatePS(this.params, arrayOfLong1, n, n + i1, arrayOfByte4, this.params.getV(), this.params.getO(), k, arrayOfLong3);
    mayoGenericMCalculateSPS(arrayOfLong3, arrayOfByte4, i7, k, j, arrayOfLong2);
    byte[] arrayOfByte7 = new byte[i];
    computeRHS(arrayOfLong2, arrayOfByte7, arrayOfByte3);
    return Arrays.constantTimeAreEqual(i, arrayOfByte3, 0, arrayOfByte2, 0);
  }
  
  void computeRHS(long[] paramArrayOflong, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    int i = this.params.getM();
    int j = this.params.getMVecLimbs();
    int k = this.params.getK();
    int[] arrayOfInt = this.params.getFTail();
    int m = (i - 1 & 0xF) << 2;
    if ((i & 0xF) != 0) {
      long l = (1L << (i & 0xF) << 2) - 1L;
      int i4 = k * k;
      byte b = 0;
      int i5;
      for (i5 = j - 1; b < i4; i5 += j) {
        paramArrayOflong[i5] = paramArrayOflong[i5] & l;
        b++;
      } 
    } 
    long[] arrayOfLong = new long[j];
    byte[] arrayOfByte = new byte[j << 3];
    int n = k * j;
    int i1 = k - 1;
    int i2 = i1 * j;
    int i3;
    for (i3 = i2 * k; i1 >= 0; i3 -= n) {
      int i4 = i1;
      int i5 = i2;
      int i6;
      for (i6 = i3; i4 < k; i6 += n) {
        int i7 = (int)(arrayOfLong[j - 1] >>> m & 0xFL);
        arrayOfLong[j - 1] = arrayOfLong[j - 1] << 4L;
        int i8;
        for (i8 = j - 2; i8 >= 0; i8--) {
          arrayOfLong[i8 + 1] = arrayOfLong[i8 + 1] ^ arrayOfLong[i8] >>> 60L;
          arrayOfLong[i8] = arrayOfLong[i8] << 4L;
        } 
        Pack.longToLittleEndian(arrayOfLong, arrayOfByte, 0);
        for (i8 = 0; i8 < 4; i8++) {
          int i10 = arrayOfInt[i8];
          if (i10 != 0) {
            long l = GF16.mul(i7, i10);
            if ((i8 & 0x1) == 0) {
              arrayOfByte[i8 >> 1] = (byte)(arrayOfByte[i8 >> 1] ^ (byte)(int)(l & 0xFL));
            } else {
              arrayOfByte[i8 >> 1] = (byte)(arrayOfByte[i8 >> 1] ^ (byte)(int)((l & 0xFL) << 4L));
            } 
          } 
        } 
        Pack.littleEndianToLong(arrayOfByte, 0, arrayOfLong);
        i8 = i3 + i5;
        int i9 = i6 + i2;
        boolean bool = (i1 == i4) ? true : false;
        for (byte b = 0; b < j; b++) {
          long l = paramArrayOflong[i8 + b];
          if (!bool)
            l ^= paramArrayOflong[i9 + b]; 
          arrayOfLong[b] = arrayOfLong[b] ^ l;
        } 
        i4++;
        i5 += j;
      } 
      i1--;
      i2 -= j;
    } 
    Pack.longToLittleEndian(arrayOfLong, arrayOfByte, 0);
    for (i1 = 0; i1 < i; i1 += 2) {
      i2 = i1 >> 1;
      paramArrayOfbyte2[i1] = (byte)(paramArrayOfbyte1[i1] ^ arrayOfByte[i2] & 0xF);
      paramArrayOfbyte2[i1 + 1] = (byte)(paramArrayOfbyte1[i1 + 1] ^ arrayOfByte[i2] >>> 4 & 0xF);
    } 
  }
  
  void computeA(long[] paramArrayOflong, byte[] paramArrayOfbyte) {
    int i = this.params.getK();
    int j = this.params.getO();
    int k = this.params.getM();
    int m = this.params.getMVecLimbs();
    int n = this.params.getACols();
    int[] arrayOfInt = this.params.getFTail();
    byte b1 = 0;
    int i1 = 0;
    int i2 = k + 7 >>> 3;
    int i3 = j * i;
    int i4 = j * m;
    int i5 = i3 + 15 >> 4 << 4;
    long[] arrayOfLong = new long[i5 * i2 << 4];
    if ((k & 0xF) != 0) {
      long l = 1L << (k & 0xF) << 2;
      l--;
      byte b = 0;
      int i8;
      for (i8 = m - 1; b < i3; i8 += m) {
        paramArrayOflong[i8] = paramArrayOflong[i8] & l;
        b++;
      } 
    } 
    byte b2 = 0;
    int i6 = 0;
    int i7;
    for (i7 = 0; b2 < i; i7 += i4) {
      int i8 = i - 1;
      int i9 = i8 * i4;
      int i10;
      for (i10 = i8 * j; i8 >= b2; i10 -= j) {
        byte b = 0;
        int i11;
        for (i11 = 0; b < j; i11 += m) {
          byte b3 = 0;
          int i12;
          for (i12 = 0; b3 < m; i12 += i5) {
            long l = paramArrayOflong[i9 + b3 + i11];
            int i13 = i6 + b + i1 + i12;
            arrayOfLong[i13] = arrayOfLong[i13] ^ l << b1;
            if (b1)
              arrayOfLong[i13 + i5] = arrayOfLong[i13 + i5] ^ l >>> 64 - b1; 
            b3++;
          } 
          b++;
        } 
        if (b2 != i8) {
          b = 0;
          for (i11 = 0; b < j; i11 += m) {
            byte b3 = 0;
            int i12;
            for (i12 = 0; b3 < m; i12 += i5) {
              long l = paramArrayOflong[i7 + b3 + i11];
              int i13 = i10 + b + i1 + i12;
              arrayOfLong[i13] = arrayOfLong[i13] ^ l << b1;
              if (b1 > 0)
                arrayOfLong[i13 + i5] = arrayOfLong[i13 + i5] ^ l >>> 64 - b1; 
              b3++;
            } 
            b++;
          } 
        } 
        b1 += 4;
        if (b1 == 64) {
          i1 += i5;
          b1 = 0;
        } 
        i8--;
        i9 -= i4;
      } 
      b2++;
      i6 += j;
    } 
    for (b2 = 0; b2 < i5 * (k + ((i + 1) * i >> 1) + 15 >>> 4); b2 += 16)
      transpose16x16Nibbles(arrayOfLong, b2); 
    byte[] arrayOfByte1 = new byte[16];
    i6 = 0;
    i7 = 0;
    while (i6 < 4) {
      int i8 = arrayOfInt[i6];
      arrayOfByte1[i7++] = (byte)GF16.mul(i8, 1);
      arrayOfByte1[i7++] = (byte)GF16.mul(i8, 2);
      arrayOfByte1[i7++] = (byte)GF16.mul(i8, 4);
      arrayOfByte1[i7++] = (byte)GF16.mul(i8, 8);
      i6++;
    } 
    for (i6 = 0; i6 < i5; i6 += 16) {
      for (i7 = k; i7 < k + ((i + 1) * i >>> 1); i7++) {
        int i8 = (i7 >>> 4) * i5 + i6 + (i7 & 0xF);
        long l1 = arrayOfLong[i8] & 0x1111111111111111L;
        long l2 = arrayOfLong[i8] >>> 1L & 0x1111111111111111L;
        long l3 = arrayOfLong[i8] >>> 2L & 0x1111111111111111L;
        long l4 = arrayOfLong[i8] >>> 3L & 0x1111111111111111L;
        byte b3 = 0;
        for (byte b4 = 0; b3 < 4; b4 += 4) {
          int i9 = i7 + b3 - k;
          int i10 = (i9 >> 4) * i5 + i6 + (i9 & 0xF);
          arrayOfLong[i10] = arrayOfLong[i10] ^ l1 * arrayOfByte1[b4] ^ l2 * arrayOfByte1[b4 + 1] ^ l3 * arrayOfByte1[b4 + 2] ^ l4 * arrayOfByte1[b4 + 3];
          b3++;
        } 
      } 
    } 
    byte[] arrayOfByte2 = Pack.longToLittleEndian(arrayOfLong);
    for (i7 = 0; i7 < k; i7 += 16) {
      for (byte b = 0; b < n - 1; b += 16) {
        for (byte b3 = 0; b3 + i7 < k; b3++)
          GF16.decode(arrayOfByte2, (i7 * i5 >> 4) + b + b3 << 3, paramArrayOfbyte, (i7 + b3) * n + b, Math.min(16, n - 1 - b)); 
      } 
    } 
  }
  
  private static void transpose16x16Nibbles(long[] paramArrayOflong, int paramInt) {
    byte b;
    for (b = 0; b < 16; b += 2) {
      int j = paramInt + b;
      int k = j + 1;
      long l = (paramArrayOflong[j] >>> 4L ^ paramArrayOflong[k]) & 0xF0F0F0F0F0F0F0FL;
      paramArrayOflong[j] = paramArrayOflong[j] ^ l << 4L;
      paramArrayOflong[k] = paramArrayOflong[k] ^ l;
    } 
    b = 0;
    int i = paramInt;
    while (b < 16) {
      long l1 = (paramArrayOflong[i] >>> 8L ^ paramArrayOflong[i + 2]) & 0xFF00FF00FF00FFL;
      long l2 = (paramArrayOflong[i + 1] >>> 8L ^ paramArrayOflong[i + 3]) & 0xFF00FF00FF00FFL;
      paramArrayOflong[i++] = paramArrayOflong[i++] ^ l1 << 8L;
      paramArrayOflong[i++] = paramArrayOflong[i++] ^ l2 << 8L;
      paramArrayOflong[i++] = paramArrayOflong[i++] ^ l1;
      paramArrayOflong[i++] = paramArrayOflong[i++] ^ l2;
      b += 4;
    } 
    for (b = 0; b < 4; b++) {
      i = paramInt + b;
      long l1 = (paramArrayOflong[i] >>> 16L ^ paramArrayOflong[i + 4]) & 0xFFFF0000FFFFL;
      long l2 = (paramArrayOflong[i + 8] >>> 16L ^ paramArrayOflong[i + 12]) & 0xFFFF0000FFFFL;
      paramArrayOflong[i] = paramArrayOflong[i] ^ l1 << 16L;
      paramArrayOflong[i + 8] = paramArrayOflong[i + 8] ^ l2 << 16L;
      paramArrayOflong[i + 4] = paramArrayOflong[i + 4] ^ l1;
      paramArrayOflong[i + 12] = paramArrayOflong[i + 12] ^ l2;
    } 
    for (b = 0; b < 8; b++) {
      i = paramInt + b;
      long l = (paramArrayOflong[i] >>> 32L ^ paramArrayOflong[i + 8]) & 0xFFFFFFFFL;
      paramArrayOflong[i] = paramArrayOflong[i] ^ l << 32L;
      paramArrayOflong[i + 8] = paramArrayOflong[i + 8] ^ l;
    } 
  }
  
  boolean sampleSolution(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    int i = this.params.getK();
    int j = this.params.getO();
    int k = this.params.getM();
    int m = this.params.getACols();
    int n = i * j;
    System.arraycopy(paramArrayOfbyte3, 0, paramArrayOfbyte4, 0, n);
    byte[] arrayOfByte = new byte[k];
    GF16Utils.matMul(paramArrayOfbyte1, paramArrayOfbyte3, 0, arrayOfByte, n + 1, k);
    int i1 = 0;
    int i2;
    for (i2 = n; i1 < k; i2 += n + 1) {
      paramArrayOfbyte1[i2] = (byte)(paramArrayOfbyte2[i1] ^ arrayOfByte[i1]);
      i1++;
    } 
    ef(paramArrayOfbyte1, k, m);
    i1 = 0;
    i2 = 0;
    int i3;
    for (i3 = (k - 1) * m; i2 < m - 1; i3++) {
      i1 |= (paramArrayOfbyte1[i3] != 0) ? 1 : 0;
      i2++;
    } 
    if (i1 == 0)
      return false; 
    i2 = k - 1;
    for (i3 = i2 * m; i2 >= 0; i3 -= m) {
      byte b = 0;
      int i4 = Math.min(i2 + 32 / (k - i2), n);
      for (int i5 = i2; i5 <= i4; i5++) {
        byte b1 = (byte)(-(paramArrayOfbyte1[i3 + i5] & 0xFF) >> 31);
        byte b2 = (byte)(b1 & (b ^ 0xFFFFFFFF) & paramArrayOfbyte1[i3 + m - 1]);
        paramArrayOfbyte4[i5] = (byte)(paramArrayOfbyte4[i5] ^ b2);
        byte b3 = 0;
        int i6 = i5;
        int i7;
        for (i7 = m - 1; b3 < i2; i7 += m << 3) {
          long l = 0L;
          byte b4 = 0;
          int i8;
          for (i8 = 0; b4 < 8; i8 += m) {
            l ^= (paramArrayOfbyte1[i6 + i8] & 0xFF) << b4 << 3;
            b4++;
          } 
          l = GF16Utils.mulFx8(b2, l);
          b4 = 0;
          for (i8 = 0; b4 < 8; i8 += m) {
            paramArrayOfbyte1[i7 + i8] = (byte)(paramArrayOfbyte1[i7 + i8] ^ (byte)(int)(l >> b4 << 3 & 0xFL));
            b4++;
          } 
          b3 += 8;
          i6 += m << 3;
        } 
        b = (byte)(b | b1);
      } 
      i2--;
    } 
    return true;
  }
  
  void ef(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = paramInt2 + 15 >> 4;
    long[] arrayOfLong1 = new long[i];
    long[] arrayOfLong2 = new long[i];
    long[] arrayOfLong3 = new long[paramInt1 * i];
    int j = this.params.getO() * this.params.getK() + 16;
    byte[] arrayOfByte = new byte[j >> 1];
    int k = j >> 4;
    byte b = 0;
    int m = 0;
    int n;
    for (n = 0; b < paramInt1; n += i) {
      for (byte b1 = 0; b1 < i; b1++) {
        long l = 0L;
        for (byte b2 = 0; b2 < 16; b2++) {
          int i2 = (b1 << 4) + b2;
          if (i2 < paramInt2)
            l |= (paramArrayOfbyte[m + i2] & 0xFL) << b2 << 2; 
        } 
        arrayOfLong3[b1 + n] = l;
      } 
      b++;
      m += paramInt2;
    } 
    b = 0;
    for (m = 0; m < paramInt2; m++) {
      n = Math.max(0, m + paramInt1 - paramInt2);
      int i2 = Math.min(paramInt1 - 1, m);
      Arrays.clear(arrayOfLong1);
      Arrays.clear(arrayOfLong2);
      int i3 = 0;
      long l = -1L;
      int i4 = Math.min(paramInt1 - 1, i2 + 32);
      int i5 = n;
      int i6;
      for (i6 = n * i; i5 <= i4; i6 += i) {
        long l1 = ctCompare64(i5, b) ^ 0xFFFFFFFFFFFFFFFFL;
        long l2 = b - i5 >> 63L;
        for (byte b1 = 0; b1 < i; b1++)
          arrayOfLong1[b1] = arrayOfLong1[b1] ^ (l1 | l2 & l) & arrayOfLong3[i6 + b1]; 
        i3 = (int)(arrayOfLong1[m >>> 4] >>> (m & 0xF) << 2 & 0xFL);
        l = -(i3) >> 63L ^ 0xFFFFFFFFFFFFFFFFL;
        i5++;
      } 
      vecMulAddU64(i, arrayOfLong1, GF16.inv((byte)i3), arrayOfLong2);
      i5 = n;
      for (i6 = n * i; i5 <= i2; i6 += i) {
        long l1 = (ctCompare64(i5, b) ^ 0xFFFFFFFFFFFFFFFFL) & (l ^ 0xFFFFFFFFFFFFFFFFL);
        long l2 = l1 ^ 0xFFFFFFFFFFFFFFFFL;
        byte b1 = 0;
        for (int i7 = i6; b1 < i; i7++) {
          arrayOfLong3[i7] = l2 & arrayOfLong3[i7] | l1 & arrayOfLong2[b1];
          b1++;
        } 
        i5++;
      } 
      i5 = n;
      for (i6 = n * i; i5 < paramInt1; i6 += i) {
        int i7 = (i5 > b) ? -1 : 0;
        int i8 = (int)(arrayOfLong3[i6 + (m >>> 4)] >>> (m & 0xF) << 2 & 0xFL);
        vecMulAddU64(i, arrayOfLong2, (byte)(i7 & i8), arrayOfLong3, i6);
        i5++;
      } 
      if (i3 != 0)
        b++; 
    } 
    m = 0;
    n = 0;
    int i1;
    for (i1 = 0; n < paramInt1; i1 += i) {
      Pack.longToLittleEndian(arrayOfLong3, i1, k, arrayOfByte, 0);
      GF16.decode(arrayOfByte, 0, paramArrayOfbyte, m, paramInt2);
      m += paramInt2;
      n++;
    } 
  }
  
  private static long ctCompare64(int paramInt1, int paramInt2) {
    return -((paramInt1 ^ paramInt2)) >> 63L;
  }
  
  private static void vecMulAddU64(int paramInt, long[] paramArrayOflong1, byte paramByte, long[] paramArrayOflong2) {
    int i = mulTable(paramByte & 0xFF);
    for (byte b = 0; b < paramInt; b++) {
      long l = (paramArrayOflong1[b] & 0x1111111111111111L) * (i & 0xFF) ^ (paramArrayOflong1[b] >>> 1L & 0x1111111111111111L) * (i >>> 8 & 0xF) ^ (paramArrayOflong1[b] >>> 2L & 0x1111111111111111L) * (i >>> 16 & 0xF) ^ (paramArrayOflong1[b] >>> 3L & 0x1111111111111111L) * (i >>> 24 & 0xF);
      paramArrayOflong2[b] = paramArrayOflong2[b] ^ l;
    } 
  }
  
  private static void vecMulAddU64(int paramInt1, long[] paramArrayOflong1, byte paramByte, long[] paramArrayOflong2, int paramInt2) {
    int i = mulTable(paramByte & 0xFF);
    for (byte b = 0; b < paramInt1; b++) {
      long l = (paramArrayOflong1[b] & 0x1111111111111111L) * (i & 0xFF) ^ (paramArrayOflong1[b] >>> 1L & 0x1111111111111111L) * (i >>> 8 & 0xF) ^ (paramArrayOflong1[b] >>> 2L & 0x1111111111111111L) * (i >>> 16 & 0xF) ^ (paramArrayOflong1[b] >>> 3L & 0x1111111111111111L) * (i >>> 24 & 0xF);
      paramArrayOflong2[paramInt2 + b] = paramArrayOflong2[paramInt2 + b] ^ l;
    } 
  }
  
  private static int mulTable(int paramInt) {
    int i = paramInt * 134480385;
    int j = i & 0xF0F0F0F0;
    return i ^ j >>> 4 ^ j >>> 3;
  }
  
  private static void mayoGenericMCalculatePS(MayoParameters paramMayoParameters, long[] paramArrayOflong1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4, int paramInt5, long[] paramArrayOflong2) {
    int i = paramInt4 + paramInt3;
    int j = paramMayoParameters.getMVecLimbs();
    long[] arrayOfLong = new long[j * paramMayoParameters.getK() * paramMayoParameters.getN() * j << 4];
    int k = paramInt4 * j;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2;
    for (i2 = 0; n < paramInt3; i2 += k) {
      int i3;
      for (i3 = n; i3 < paramInt3; i3++) {
        byte b = 0;
        int i5;
        for (i5 = 0; b < paramInt5; i5 += i) {
          Longs.xorTo(j, paramArrayOflong1, m, arrayOfLong, ((i1 + b << 4) + (paramArrayOfbyte[i5 + i3] & 0xFF)) * j);
          b++;
        } 
        m += j;
      } 
      i3 = 0;
      int i4;
      for (i4 = i2; i3 < paramInt4; i4 += j) {
        byte b = 0;
        int i5;
        for (i5 = 0; b < paramInt5; i5 += i) {
          Longs.xorTo(j, paramArrayOflong1, paramInt1 + i4, arrayOfLong, ((i1 + b << 4) + (paramArrayOfbyte[i5 + i3 + paramInt3] & 0xFF)) * j);
          b++;
        } 
        i3++;
      } 
      n++;
      i1 += paramInt5;
    } 
    m = 0;
    n = paramInt3;
    for (i1 = paramInt3 * paramInt5; n < i; i1 += paramInt5) {
      for (i2 = n; i2 < i; i2++) {
        byte b = 0;
        int i3;
        for (i3 = 0; b < paramInt5; i3 += i) {
          Longs.xorTo(j, paramArrayOflong1, paramInt2 + m, arrayOfLong, ((i1 + b << 4) + (paramArrayOfbyte[i3 + i2] & 0xFF)) * j);
          b++;
        } 
        m += j;
      } 
      n++;
    } 
    mVecMultiplyBins(j, i * paramInt5, arrayOfLong, paramArrayOflong2);
  }
  
  private static void mayoGenericMCalculateSPS(long[] paramArrayOflong1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, long[] paramArrayOflong2) {
    int i = paramInt2 * paramInt2;
    int j = paramInt1 * i << 4;
    long[] arrayOfLong = new long[j];
    int k = paramInt2 * paramInt1;
    byte b = 0;
    int m = 0;
    int n;
    for (n = 0; b < paramInt2; n += k << 4) {
      byte b1 = 0;
      int i1;
      for (i1 = 0; b1 < paramInt3; i1 += k) {
        int i2 = (paramArrayOfbyte[m + b1] & 0xFF) * paramInt1 + n;
        byte b2 = 0;
        int i3;
        for (i3 = 0; b2 < paramInt2; i3 += paramInt1) {
          Longs.xorTo(paramInt1, paramArrayOflong1, i1 + i3, arrayOfLong, i2 + (i3 << 4));
          b2++;
        } 
        b1++;
      } 
      b++;
      m += paramInt3;
    } 
    mVecMultiplyBins(paramInt1, i, arrayOfLong, paramArrayOflong2);
  }
  
  private static void mVecMultiplyBins(int paramInt1, int paramInt2, long[] paramArrayOflong1, long[] paramArrayOflong2) {
    int i = paramInt1 + paramInt1;
    int j = i + paramInt1;
    int k = j + paramInt1;
    int m = k + paramInt1;
    int n = m + paramInt1;
    int i1 = n + paramInt1;
    int i2 = i1 + paramInt1;
    int i3 = i2 + paramInt1;
    int i4 = i3 + paramInt1;
    int i5 = i4 + paramInt1;
    int i6 = i5 + paramInt1;
    int i7 = i6 + paramInt1;
    int i8 = i7 + paramInt1;
    int i9 = i8 + paramInt1;
    byte b = 0;
    int i10;
    for (i10 = 0; b < paramInt2; i10 += paramInt1 << 4) {
      byte b1 = 0;
      for (int i11 = i10; b1 < paramInt1; i11++) {
        long l2 = paramArrayOflong1[i11 + m];
        long l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i4] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        long l1 = paramArrayOflong1[i11 + i5];
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + i6] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i1] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + n] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i8] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + j] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i9] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + i2] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i7] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + k] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + i3] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        l1 = paramArrayOflong1[i11 + i] ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        l3 = l2 & 0x1111111111111111L;
        l2 = paramArrayOflong1[i11 + paramInt1] ^ (l2 & 0xEEEEEEEEEEEEEEEEL) >>> 1L ^ (l3 << 3L) + l3;
        l3 = (l1 & 0x8888888888888888L) >>> 3L;
        paramArrayOflong2[(i10 >> 4) + b1] = l2 ^ (l1 & 0x7777777777777777L) << 1L ^ (l3 << 1L) + l3;
        b1++;
      } 
      b++;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\MayoSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */