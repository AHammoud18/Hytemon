package org.bouncycastle.pqc.crypto.falcon;

import java.security.SecureRandom;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.util.Arrays;

class FalconNIST {
  final int NONCELEN;
  
  final int LOGN;
  
  private final int N;
  
  private final SecureRandom rand;
  
  private final int CRYPTO_SECRETKEYBYTES;
  
  private final int CRYPTO_PUBLICKEYBYTES;
  
  final int CRYPTO_BYTES;
  
  FalconNIST(int paramInt1, int paramInt2, SecureRandom paramSecureRandom) {
    this.rand = paramSecureRandom;
    this.LOGN = paramInt1;
    this.NONCELEN = paramInt2;
    this.N = 1 << paramInt1;
    this.CRYPTO_PUBLICKEYBYTES = 1 + 14 * this.N / 8;
    if (paramInt1 == 10) {
      this.CRYPTO_SECRETKEYBYTES = 2305;
      this.CRYPTO_BYTES = 1330;
    } else if (paramInt1 == 9 || paramInt1 == 8) {
      this.CRYPTO_SECRETKEYBYTES = 1 + 6 * this.N * 2 / 8 + this.N;
      this.CRYPTO_BYTES = 690;
    } else if (paramInt1 == 7 || paramInt1 == 6) {
      this.CRYPTO_SECRETKEYBYTES = 1 + 7 * this.N * 2 / 8 + this.N;
      this.CRYPTO_BYTES = 690;
    } else {
      this.CRYPTO_SECRETKEYBYTES = 1 + this.N * 2 + this.N;
      this.CRYPTO_BYTES = 690;
    } 
  }
  
  byte[][] crypto_sign_keypair(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    byte[] arrayOfByte1 = new byte[this.N];
    byte[] arrayOfByte2 = new byte[this.N];
    byte[] arrayOfByte3 = new byte[this.N];
    short[] arrayOfShort = new short[this.N];
    byte[] arrayOfByte4 = new byte[48];
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    this.rand.nextBytes(arrayOfByte4);
    sHAKEDigest.update(arrayOfByte4, 0, arrayOfByte4.length);
    FalconKeyGen.keygen(sHAKEDigest, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfShort, this.LOGN);
    paramArrayOfbyte2[0] = (byte)(80 + this.LOGN);
    int i = 1;
    int j = FalconCodec.trim_i8_encode(paramArrayOfbyte2, i, this.CRYPTO_SECRETKEYBYTES - i, arrayOfByte1, this.LOGN, FalconCodec.max_fg_bits[this.LOGN]);
    if (j == 0)
      throw new IllegalStateException("f encode failed"); 
    byte[] arrayOfByte5 = Arrays.copyOfRange(paramArrayOfbyte2, i, i + j);
    i += j;
    j = FalconCodec.trim_i8_encode(paramArrayOfbyte2, i, this.CRYPTO_SECRETKEYBYTES - i, arrayOfByte2, this.LOGN, FalconCodec.max_fg_bits[this.LOGN]);
    if (j == 0)
      throw new IllegalStateException("g encode failed"); 
    byte[] arrayOfByte6 = Arrays.copyOfRange(paramArrayOfbyte2, i, i + j);
    i += j;
    j = FalconCodec.trim_i8_encode(paramArrayOfbyte2, i, this.CRYPTO_SECRETKEYBYTES - i, arrayOfByte3, this.LOGN, FalconCodec.max_FG_bits[this.LOGN]);
    if (j == 0)
      throw new IllegalStateException("F encode failed"); 
    byte[] arrayOfByte7 = Arrays.copyOfRange(paramArrayOfbyte2, i, i + j);
    i += j;
    if (i != this.CRYPTO_SECRETKEYBYTES)
      throw new IllegalStateException("secret key encoding failed"); 
    paramArrayOfbyte1[0] = (byte)this.LOGN;
    j = FalconCodec.modq_encode(paramArrayOfbyte1, this.CRYPTO_PUBLICKEYBYTES - 1, arrayOfShort, this.LOGN);
    if (j != this.CRYPTO_PUBLICKEYBYTES - 1)
      throw new IllegalStateException("public key encoding failed"); 
    return new byte[][] { Arrays.copyOfRange(paramArrayOfbyte1, 1, paramArrayOfbyte1.length), arrayOfByte5, arrayOfByte6, arrayOfByte7 };
  }
  
  byte[] crypto_sign(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt, byte[] paramArrayOfbyte3) {
    byte[] arrayOfByte1 = new byte[this.N];
    byte[] arrayOfByte2 = new byte[this.N];
    byte[] arrayOfByte3 = new byte[this.N];
    byte[] arrayOfByte4 = new byte[this.N];
    short[] arrayOfShort1 = new short[this.N];
    short[] arrayOfShort2 = new short[this.N];
    byte[] arrayOfByte5 = new byte[48];
    byte[] arrayOfByte6 = new byte[this.NONCELEN];
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    FalconSign falconSign = new FalconSign();
    int i = 0;
    int j = FalconCodec.trim_i8_decode(arrayOfByte1, this.LOGN, FalconCodec.max_fg_bits[this.LOGN], paramArrayOfbyte3, 0, this.CRYPTO_SECRETKEYBYTES - i);
    if (j == 0)
      throw new IllegalStateException("f decode failed"); 
    i += j;
    j = FalconCodec.trim_i8_decode(arrayOfByte2, this.LOGN, FalconCodec.max_fg_bits[this.LOGN], paramArrayOfbyte3, i, this.CRYPTO_SECRETKEYBYTES - i);
    if (j == 0)
      throw new IllegalStateException("g decode failed"); 
    i += j;
    j = FalconCodec.trim_i8_decode(arrayOfByte3, this.LOGN, FalconCodec.max_FG_bits[this.LOGN], paramArrayOfbyte3, i, this.CRYPTO_SECRETKEYBYTES - i);
    if (j == 0)
      throw new IllegalArgumentException("F decode failed"); 
    i += j;
    if (i != this.CRYPTO_SECRETKEYBYTES - 1)
      throw new IllegalStateException("full key not used"); 
    if (!FalconVrfy.complete_private(arrayOfByte4, arrayOfByte1, arrayOfByte2, arrayOfByte3, this.LOGN, new short[2 * this.N]))
      throw new IllegalStateException("complete_private failed"); 
    this.rand.nextBytes(arrayOfByte6);
    sHAKEDigest.update(arrayOfByte6, 0, this.NONCELEN);
    sHAKEDigest.update(paramArrayOfbyte2, 0, paramInt);
    FalconCommon.hash_to_point_vartime(sHAKEDigest, arrayOfShort2, this.LOGN);
    this.rand.nextBytes(arrayOfByte5);
    sHAKEDigest.reset();
    sHAKEDigest.update(arrayOfByte5, 0, arrayOfByte5.length);
    falconSign.sign_dyn(arrayOfShort1, sHAKEDigest, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4, arrayOfShort2, this.LOGN, new double[10 * this.N]);
    byte[] arrayOfByte7 = new byte[this.CRYPTO_BYTES - 2 - this.NONCELEN];
    int k = FalconCodec.comp_encode(arrayOfByte7, arrayOfByte7.length, arrayOfShort1, this.LOGN);
    if (k == 0)
      throw new IllegalStateException("signature failed to generate"); 
    paramArrayOfbyte1[0] = (byte)(48 + this.LOGN);
    System.arraycopy(arrayOfByte6, 0, paramArrayOfbyte1, 1, this.NONCELEN);
    System.arraycopy(arrayOfByte7, 0, paramArrayOfbyte1, 1 + this.NONCELEN, k);
    return Arrays.copyOfRange(paramArrayOfbyte1, 0, 1 + this.NONCELEN + k);
  }
  
  int crypto_sign_open(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    short[] arrayOfShort1 = new short[this.N];
    short[] arrayOfShort2 = new short[this.N];
    short[] arrayOfShort3 = new short[this.N];
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    if (FalconCodec.modq_decode(arrayOfShort1, this.LOGN, paramArrayOfbyte4, this.CRYPTO_PUBLICKEYBYTES - 1) != this.CRYPTO_PUBLICKEYBYTES - 1)
      return -1; 
    FalconVrfy.to_ntt_monty(arrayOfShort1, this.LOGN);
    int i = paramArrayOfbyte1.length;
    int j = paramArrayOfbyte3.length;
    if (i < 1 || FalconCodec.comp_decode(arrayOfShort3, this.LOGN, paramArrayOfbyte1, i) != i)
      return -1; 
    sHAKEDigest.update(paramArrayOfbyte2, 0, this.NONCELEN);
    sHAKEDigest.update(paramArrayOfbyte3, 0, j);
    FalconCommon.hash_to_point_vartime(sHAKEDigest, arrayOfShort2, this.LOGN);
    return (FalconVrfy.verify_raw(arrayOfShort2, arrayOfShort3, arrayOfShort1, this.LOGN, new short[this.N]) == 0) ? -1 : 0;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconNIST.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */