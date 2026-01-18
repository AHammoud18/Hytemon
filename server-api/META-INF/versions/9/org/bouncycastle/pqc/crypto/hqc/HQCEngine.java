package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import java.security.SecureRandom;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.crypto.hqc.GF2PolynomialCalculator;
import org.bouncycastle.pqc.crypto.hqc.ReedMuller;
import org.bouncycastle.pqc.crypto.hqc.ReedSolomon;
import org.bouncycastle.pqc.crypto.hqc.Shake256RandomGenerator;
import org.bouncycastle.pqc.crypto.hqc.Utils;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Longs;
import org.bouncycastle.util.Pack;

class HQCEngine {
  private final int n;
  
  private final int n1;
  
  private final int k;
  
  private final int delta;
  
  private final int w;
  
  private final int wr;
  
  private final int g;
  
  private final int fft;
  
  private final int mulParam;
  
  private static final int SEED_BYTES = 32;
  
  private final int N_BYTE;
  
  private final int N_BYTE_64;
  
  private final int K_BYTE;
  
  private final int N1N2_BYTE_64;
  
  private final int N1N2_BYTE;
  
  private static final int SALT_SIZE_BYTES = 16;
  
  private final int[] generatorPoly;
  
  private final int N_MU;
  
  private final int pkSize;
  
  private final GF2PolynomialCalculator gf;
  
  private final long rejectionThreshold;
  
  public HQCEngine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int[] paramArrayOfint) {
    this.n = paramInt1;
    this.k = paramInt4;
    this.delta = paramInt6;
    this.w = paramInt7;
    this.wr = paramInt8;
    this.n1 = paramInt2;
    this.generatorPoly = paramArrayOfint;
    this.g = paramInt5;
    this.fft = paramInt9;
    this.N_MU = paramInt10;
    this.pkSize = paramInt11;
    this.mulParam = paramInt3 >> 7;
    this.N_BYTE = Utils.getByteSizeFromBitSize(paramInt1);
    this.K_BYTE = paramInt4;
    this.N_BYTE_64 = Utils.getByte64SizeFromBitSize(paramInt1);
    this.N1N2_BYTE_64 = Utils.getByte64SizeFromBitSize(paramInt2 * paramInt3);
    this.N1N2_BYTE = Utils.getByteSizeFromBitSize(paramInt2 * paramInt3);
    long l = (1L << (paramInt1 & 0x3F)) - 1L;
    this.gf = new GF2PolynomialCalculator(this.N_BYTE_64, paramInt1, l);
    this.rejectionThreshold = 16777216L / paramInt1 * paramInt1;
  }
  
  public void genKeyPair(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, SecureRandom paramSecureRandom) {
    byte[] arrayOfByte1 = new byte[32];
    byte[] arrayOfByte2 = new byte[64];
    long[] arrayOfLong1 = new long[this.N_BYTE_64];
    long[] arrayOfLong2 = new long[this.N_BYTE_64];
    long[] arrayOfLong3 = new long[this.N_BYTE_64];
    paramSecureRandom.nextBytes(arrayOfByte1);
    Shake256RandomGenerator shake256RandomGenerator = new Shake256RandomGenerator(arrayOfByte1, (byte)1);
    System.arraycopy(arrayOfByte1, 0, paramArrayOfbyte2, this.pkSize + 32 + this.K_BYTE, 32);
    shake256RandomGenerator.nextBytes(arrayOfByte1);
    shake256RandomGenerator.nextBytes(paramArrayOfbyte2, this.pkSize + 32, this.K_BYTE);
    hashHI(arrayOfByte2, 512, arrayOfByte1, arrayOfByte1.length, (byte)2);
    shake256RandomGenerator.init(arrayOfByte2, 0, 32, (byte)1);
    vectSampleFixedWeight1(arrayOfLong2, shake256RandomGenerator, this.w);
    vectSampleFixedWeight1(arrayOfLong1, shake256RandomGenerator, this.w);
    System.arraycopy(arrayOfByte2, 32, paramArrayOfbyte1, 0, 32);
    shake256RandomGenerator.init(arrayOfByte2, 32, 32, (byte)1);
    vectSetRandom(shake256RandomGenerator, arrayOfLong3);
    this.gf.vectMul(arrayOfLong3, arrayOfLong2, arrayOfLong3);
    Longs.xorTo(this.N_BYTE_64, arrayOfLong1, 0, arrayOfLong3, 0);
    Utils.fromLongArrayToByteArray(paramArrayOfbyte1, 32, paramArrayOfbyte1.length - 32, arrayOfLong3);
    System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte2, this.pkSize, 32);
    System.arraycopy(paramArrayOfbyte1, 0, paramArrayOfbyte2, 0, this.pkSize);
    Arrays.clear(arrayOfByte2);
    Arrays.clear(arrayOfLong1);
    Arrays.clear(arrayOfLong2);
    Arrays.clear(arrayOfLong3);
  }
  
  public void encaps(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, SecureRandom paramSecureRandom) {
    byte[] arrayOfByte1 = new byte[this.K_BYTE];
    byte[] arrayOfByte2 = new byte[32];
    long[] arrayOfLong1 = new long[this.N_BYTE_64];
    long[] arrayOfLong2 = new long[this.N1N2_BYTE_64];
    paramSecureRandom.nextBytes(arrayOfByte1);
    paramSecureRandom.nextBytes(paramArrayOfbyte5);
    hashHI(arrayOfByte2, 256, paramArrayOfbyte4, paramArrayOfbyte4.length, (byte)1);
    hashGJ(paramArrayOfbyte3, 512, arrayOfByte2, arrayOfByte1, 0, arrayOfByte1.length, paramArrayOfbyte5, 0, 16, (byte)0);
    pkeEncrypt(arrayOfLong1, arrayOfLong2, paramArrayOfbyte4, arrayOfByte1, paramArrayOfbyte3, 32);
    Utils.fromLongArrayToByteArray(paramArrayOfbyte1, arrayOfLong1);
    Utils.fromLongArrayToByteArray(paramArrayOfbyte2, arrayOfLong2);
    Arrays.clear(arrayOfLong1);
    Arrays.clear(arrayOfLong2);
    Arrays.clear(arrayOfByte1);
    Arrays.clear(arrayOfByte2);
  }
  
  public int decaps(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    long[] arrayOfLong1 = new long[this.N_BYTE_64];
    long[] arrayOfLong2 = new long[this.N_BYTE_64];
    long[] arrayOfLong3 = new long[this.N_BYTE_64];
    long[] arrayOfLong4 = new long[this.N_BYTE_64];
    byte[] arrayOfByte1 = new byte[32];
    byte[] arrayOfByte2 = new byte[64];
    byte[] arrayOfByte3 = new byte[this.k];
    byte[] arrayOfByte4 = new byte[32];
    byte[] arrayOfByte5 = new byte[this.n1];
    Shake256RandomGenerator shake256RandomGenerator = new Shake256RandomGenerator(paramArrayOfbyte3, this.pkSize, 32, (byte)1);
    vectSampleFixedWeight1(arrayOfLong4, shake256RandomGenerator, this.w);
    Utils.fromByteArrayToLongArray(arrayOfLong1, paramArrayOfbyte2, 0, this.N_BYTE);
    Utils.fromByteArrayToLongArray(arrayOfLong2, paramArrayOfbyte2, this.N_BYTE, this.N1N2_BYTE);
    this.gf.vectMul(arrayOfLong3, arrayOfLong4, arrayOfLong1);
    vectTruncate(arrayOfLong3);
    Longs.xorTo(this.N_BYTE_64, arrayOfLong2, 0, arrayOfLong3, 0);
    ReedMuller.decode(arrayOfByte5, arrayOfLong3, this.n1, this.mulParam);
    ReedSolomon.decode(arrayOfByte3, arrayOfByte5, this.n1, this.fft, this.delta, this.k, this.g);
    byte b1 = 0;
    hashHI(arrayOfByte1, 256, paramArrayOfbyte3, this.pkSize, (byte)1);
    hashGJ(arrayOfByte2, 512, arrayOfByte1, arrayOfByte3, 0, arrayOfByte3.length, paramArrayOfbyte2, this.N_BYTE + this.N1N2_BYTE, 16, (byte)0);
    System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte1, 0, 32);
    Arrays.fill(arrayOfLong4, 0L);
    pkeEncrypt(arrayOfLong3, arrayOfLong4, paramArrayOfbyte3, arrayOfByte3, arrayOfByte2, 32);
    hashGJ(arrayOfByte4, 256, arrayOfByte1, paramArrayOfbyte3, this.pkSize + 32, this.K_BYTE, paramArrayOfbyte2, 0, paramArrayOfbyte2.length, (byte)3);
    if (!Arrays.constantTimeAreEqual(this.N_BYTE_64, arrayOfLong1, 0, arrayOfLong3, 0))
      b1 = 1; 
    if (!Arrays.constantTimeAreEqual(this.N_BYTE_64, arrayOfLong2, 0, arrayOfLong4, 0))
      b1 = 1; 
    b1--;
    for (byte b2 = 0; b2 < this.K_BYTE; b2++)
      paramArrayOfbyte1[b2] = (byte)((paramArrayOfbyte1[b2] & b1 ^ arrayOfByte4[b2] & (b1 ^ 0xFFFFFFFF)) & 0xFF); 
    Arrays.clear(arrayOfLong1);
    Arrays.clear(arrayOfLong2);
    Arrays.clear(arrayOfLong3);
    Arrays.clear(arrayOfLong4);
    Arrays.clear(arrayOfByte1);
    Arrays.clear(arrayOfByte2);
    Arrays.clear(arrayOfByte3);
    Arrays.clear(arrayOfByte4);
    Arrays.clear(arrayOfByte5);
    return -b1;
  }
  
  private void pkeEncrypt(long[] paramArrayOflong1, long[] paramArrayOflong2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) {
    long[] arrayOfLong1 = new long[this.N_BYTE_64];
    long[] arrayOfLong2 = new long[this.N_BYTE_64];
    byte[] arrayOfByte = new byte[this.n1];
    ReedSolomon.encode(arrayOfByte, paramArrayOfbyte2, this.n1, this.k, this.g, this.generatorPoly);
    ReedMuller.encode(paramArrayOflong2, arrayOfByte, this.n1, this.mulParam);
    Shake256RandomGenerator shake256RandomGenerator = new Shake256RandomGenerator(paramArrayOfbyte1, 0, 32, (byte)1);
    vectSetRandom(shake256RandomGenerator, arrayOfLong2);
    shake256RandomGenerator.init(paramArrayOfbyte3, paramInt, 32, (byte)1);
    vectSampleFixedWeights2(shake256RandomGenerator, arrayOfLong1, this.wr);
    this.gf.vectMul(paramArrayOflong1, arrayOfLong1, arrayOfLong2);
    Utils.fromByteArrayToLongArray(arrayOfLong2, paramArrayOfbyte1, 32, this.pkSize - 32);
    this.gf.vectMul(arrayOfLong2, arrayOfLong1, arrayOfLong2);
    vectSampleFixedWeights2(shake256RandomGenerator, arrayOfLong1, this.wr);
    Longs.xorTo(this.N_BYTE_64, arrayOfLong1, 0, arrayOfLong2, 0);
    vectTruncate(arrayOfLong2);
    Longs.xorTo(this.N1N2_BYTE_64, arrayOfLong2, 0, paramArrayOflong2, 0);
    vectSampleFixedWeights2(shake256RandomGenerator, arrayOfLong2, this.wr);
    Longs.xorTo(this.N_BYTE_64, arrayOfLong2, 0, paramArrayOflong1, 0);
    Arrays.clear(arrayOfLong1);
    Arrays.clear(arrayOfLong2);
    Arrays.clear(arrayOfByte);
  }
  
  private int barrettReduce(int paramInt) {
    long l = paramInt * this.N_MU >>> 32L;
    int i = paramInt - (int)(l * this.n);
    i -= -(i - this.n >>> 31 ^ 0x1) & this.n;
    return i;
  }
  
  private void generateRandomSupport(int[] paramArrayOfint, int paramInt, Shake256RandomGenerator paramShake256RandomGenerator) {
    int i = 3 * paramInt;
    byte[] arrayOfByte = new byte[i];
    int j = i;
    byte b = 0;
    while (b < paramInt) {
      if (j == i) {
        paramShake256RandomGenerator.xofGetBytes(arrayOfByte, i);
        j = 0;
      } 
      int k = (arrayOfByte[j++] & 0xFF) << 16 | (arrayOfByte[j++] & 0xFF) << 8 | arrayOfByte[j++] & 0xFF;
      if (k >= this.rejectionThreshold)
        continue; 
      k = barrettReduce(k);
      boolean bool = false;
      for (byte b1 = 0; b1 < b; b1++) {
        if (paramArrayOfint[b1] == k) {
          bool = true;
          break;
        } 
      } 
      if (!bool)
        paramArrayOfint[b++] = k; 
    } 
  }
  
  private void writeSupportToVector(long[] paramArrayOflong, int[] paramArrayOfint, int paramInt) {
    int[] arrayOfInt = new int[this.wr];
    long[] arrayOfLong = new long[this.wr];
    byte b;
    for (b = 0; b < paramInt; b++) {
      arrayOfInt[b] = paramArrayOfint[b] >>> 6;
      arrayOfLong[b] = 1L << (paramArrayOfint[b] & 0x3F);
    } 
    for (b = 0; b < paramArrayOflong.length; b++) {
      long l = 0L;
      for (byte b1 = 0; b1 < paramInt; b1++) {
        int i = b - arrayOfInt[b1];
        l |= arrayOfLong[b1] & -(0x1 ^ (i | -i) >>> 31);
      } 
      paramArrayOflong[b] = l;
    } 
  }
  
  public void vectSampleFixedWeight1(long[] paramArrayOflong, Shake256RandomGenerator paramShake256RandomGenerator, int paramInt) {
    int[] arrayOfInt = new int[this.wr];
    generateRandomSupport(arrayOfInt, paramInt, paramShake256RandomGenerator);
    writeSupportToVector(paramArrayOflong, arrayOfInt, paramInt);
  }
  
  private static void hashHI(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, byte paramByte) {
    SHA3Digest sHA3Digest = new SHA3Digest(paramInt1);
    sHA3Digest.update(paramArrayOfbyte2, 0, paramInt2);
    sHA3Digest.update(paramByte);
    sHA3Digest.doFinal(paramArrayOfbyte1, 0);
  }
  
  private void hashGJ(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, byte[] paramArrayOfbyte4, int paramInt4, int paramInt5, byte paramByte) {
    SHA3Digest sHA3Digest = new SHA3Digest(paramInt1);
    sHA3Digest.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
    sHA3Digest.update(paramArrayOfbyte3, paramInt2, paramInt3);
    sHA3Digest.update(paramArrayOfbyte4, paramInt4, paramInt5);
    sHA3Digest.update(paramByte);
    sHA3Digest.doFinal(paramArrayOfbyte1, 0);
  }
  
  private void vectSetRandom(Shake256RandomGenerator paramShake256RandomGenerator, long[] paramArrayOflong) {
    byte[] arrayOfByte = new byte[paramArrayOflong.length << 3];
    paramShake256RandomGenerator.xofGetBytes(arrayOfByte, this.N_BYTE);
    Pack.littleEndianToLong(arrayOfByte, 0, paramArrayOflong);
    paramArrayOflong[this.N_BYTE_64 - 1] = paramArrayOflong[this.N_BYTE_64 - 1] & Utils.bitMask(this.n, 64L);
  }
  
  private void vectSampleFixedWeights2(Shake256RandomGenerator paramShake256RandomGenerator, long[] paramArrayOflong, int paramInt) {
    int[] arrayOfInt = new int[this.wr];
    byte[] arrayOfByte = new byte[this.wr << 2];
    paramShake256RandomGenerator.xofGetBytes(arrayOfByte, arrayOfByte.length);
    Pack.littleEndianToInt(arrayOfByte, 0, arrayOfInt);
    int i;
    for (i = 0; i < paramInt; i++)
      arrayOfInt[i] = i + (int)((arrayOfInt[i] & 0xFFFFFFFFL) * (this.n - i) >> 32L); 
    i = paramInt - 1;
    while (i-- > 0) {
      int j = 0;
      for (int k = i + 1; k < paramInt; k++)
        j |= compareU32(arrayOfInt[k], arrayOfInt[i]); 
      j = -j;
      arrayOfInt[i] = j & i ^ (j ^ 0xFFFFFFFF) & arrayOfInt[i];
    } 
    writeSupportToVector(paramArrayOflong, arrayOfInt, paramInt);
  }
  
  private static int compareU32(int paramInt1, int paramInt2) {
    return 0x1 ^ (paramInt1 - paramInt2 | paramInt2 - paramInt1) >>> 31;
  }
  
  private void vectTruncate(long[] paramArrayOflong) {
    Arrays.fill(paramArrayOflong, this.N1N2_BYTE_64, this.n + 63 >> 6, 0L);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\HQCEngine.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */