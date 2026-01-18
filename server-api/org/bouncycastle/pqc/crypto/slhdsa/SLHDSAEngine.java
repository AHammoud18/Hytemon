package org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.generators.MGF1BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.MGFParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Bytes;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

abstract class SLHDSAEngine {
  final int N;
  
  final int WOTS_W;
  
  final int WOTS_LOGW;
  
  final int WOTS_LEN;
  
  final int WOTS_LEN1;
  
  final int WOTS_LEN2;
  
  final int D;
  
  final int A;
  
  final int K;
  
  final int H;
  
  final int H_PRIME;
  
  public SLHDSAEngine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.N = paramInt1;
    if (paramInt2 == 16) {
      this.WOTS_LOGW = 4;
      this.WOTS_LEN1 = 8 * this.N / this.WOTS_LOGW;
      if (this.N <= 8) {
        this.WOTS_LEN2 = 2;
      } else if (this.N <= 136) {
        this.WOTS_LEN2 = 3;
      } else if (this.N <= 256) {
        this.WOTS_LEN2 = 4;
      } else {
        throw new IllegalArgumentException("cannot precompute SPX_WOTS_LEN2 for n outside {2, .., 256}");
      } 
    } else if (paramInt2 == 256) {
      this.WOTS_LOGW = 8;
      this.WOTS_LEN1 = 8 * this.N / this.WOTS_LOGW;
      if (this.N <= 1) {
        this.WOTS_LEN2 = 1;
      } else if (this.N <= 256) {
        this.WOTS_LEN2 = 2;
      } else {
        throw new IllegalArgumentException("cannot precompute SPX_WOTS_LEN2 for n outside {2, .., 256}");
      } 
    } else {
      throw new IllegalArgumentException("wots_w assumed 16 or 256");
    } 
    this.WOTS_W = paramInt2;
    this.WOTS_LEN = this.WOTS_LEN1 + this.WOTS_LEN2;
    this.D = paramInt3;
    this.A = paramInt4;
    this.K = paramInt5;
    this.H = paramInt6;
    this.H_PRIME = paramInt6 / paramInt3;
  }
  
  abstract void init(byte[] paramArrayOfbyte);
  
  abstract byte[] F(byte[] paramArrayOfbyte1, ADRS paramADRS, byte[] paramArrayOfbyte2);
  
  abstract byte[] H(byte[] paramArrayOfbyte1, ADRS paramADRS, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3);
  
  abstract IndexedDigest H_msg(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5);
  
  abstract byte[] T_l(byte[] paramArrayOfbyte1, ADRS paramADRS, byte[] paramArrayOfbyte2);
  
  abstract byte[] PRF(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, ADRS paramADRS);
  
  abstract byte[] PRF_msg(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4);
  
  static class Sha2Engine extends SLHDSAEngine {
    private final HMac treeHMac;
    
    private final MGF1BytesGenerator mgf1;
    
    private final byte[] hmacBuf;
    
    private final Digest msgDigest;
    
    private final byte[] msgDigestBuf;
    
    private final int bl;
    
    private final Digest sha256 = (Digest)new SHA256Digest();
    
    private final byte[] sha256Buf = new byte[this.sha256.getDigestSize()];
    
    private Memoable msgMemo;
    
    private Memoable sha256Memo;
    
    public Sha2Engine(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
      super(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
      if (param1Int1 == 16) {
        this.msgDigest = (Digest)new SHA256Digest();
        this.treeHMac = new HMac((Digest)new SHA256Digest());
        this.mgf1 = new MGF1BytesGenerator((Digest)new SHA256Digest());
        this.bl = 64;
      } else {
        this.msgDigest = (Digest)new SHA512Digest();
        this.treeHMac = new HMac((Digest)new SHA512Digest());
        this.mgf1 = new MGF1BytesGenerator((Digest)new SHA512Digest());
        this.bl = 128;
      } 
      this.hmacBuf = new byte[this.treeHMac.getMacSize()];
      this.msgDigestBuf = new byte[this.msgDigest.getDigestSize()];
    }
    
    void init(byte[] param1ArrayOfbyte) {
      byte[] arrayOfByte = new byte[this.bl];
      this.msgDigest.update(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
      this.msgDigest.update(arrayOfByte, 0, this.bl - this.N);
      this.msgMemo = ((Memoable)this.msgDigest).copy();
      this.msgDigest.reset();
      this.sha256.update(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
      this.sha256.update(arrayOfByte, 0, 64 - param1ArrayOfbyte.length);
      this.sha256Memo = ((Memoable)this.sha256).copy();
      this.sha256.reset();
    }
    
    public byte[] F(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte = compressedADRS(param1ADRS);
      ((Memoable)this.sha256).reset(this.sha256Memo);
      this.sha256.update(arrayOfByte, 0, arrayOfByte.length);
      this.sha256.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.sha256.doFinal(this.sha256Buf, 0);
      return Arrays.copyOfRange(this.sha256Buf, 0, this.N);
    }
    
    public byte[] H(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3) {
      byte[] arrayOfByte = compressedADRS(param1ADRS);
      ((Memoable)this.msgDigest).reset(this.msgMemo);
      this.msgDigest.update(arrayOfByte, 0, arrayOfByte.length);
      this.msgDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.msgDigest.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length);
      this.msgDigest.doFinal(this.msgDigestBuf, 0);
      return Arrays.copyOfRange(this.msgDigestBuf, 0, this.N);
    }
    
    IndexedDigest H_msg(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4, byte[] param1ArrayOfbyte5) {
      int i = (this.A * this.K + 7) / 8;
      int j = this.H / this.D;
      int k = this.H - j;
      int m = (j + 7) / 8;
      int n = (k + 7) / 8;
      int i1 = i + m + n;
      byte[] arrayOfByte1 = new byte[i1];
      byte[] arrayOfByte2 = new byte[this.msgDigest.getDigestSize()];
      this.msgDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.msgDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.msgDigest.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length);
      if (param1ArrayOfbyte4 != null)
        this.msgDigest.update(param1ArrayOfbyte4, 0, param1ArrayOfbyte4.length); 
      this.msgDigest.update(param1ArrayOfbyte5, 0, param1ArrayOfbyte5.length);
      this.msgDigest.doFinal(arrayOfByte2, 0);
      arrayOfByte1 = bitmask(Arrays.concatenate(param1ArrayOfbyte1, param1ArrayOfbyte2, arrayOfByte2), arrayOfByte1);
      byte[] arrayOfByte3 = new byte[8];
      System.arraycopy(arrayOfByte1, i, arrayOfByte3, 8 - n, n);
      long l = Pack.bigEndianToLong(arrayOfByte3, 0);
      l &= -1L >>> 64 - k;
      byte[] arrayOfByte4 = new byte[4];
      System.arraycopy(arrayOfByte1, i + n, arrayOfByte4, 4 - m, m);
      int i2 = Pack.bigEndianToInt(arrayOfByte4, 0);
      i2 &= -1 >>> 32 - j;
      return new IndexedDigest(l, i2, Arrays.copyOfRange(arrayOfByte1, 0, i));
    }
    
    public byte[] T_l(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte = compressedADRS(param1ADRS);
      ((Memoable)this.msgDigest).reset(this.msgMemo);
      this.msgDigest.update(arrayOfByte, 0, arrayOfByte.length);
      this.msgDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.msgDigest.doFinal(this.msgDigestBuf, 0);
      return Arrays.copyOfRange(this.msgDigestBuf, 0, this.N);
    }
    
    byte[] PRF(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, ADRS param1ADRS) {
      int i = param1ArrayOfbyte2.length;
      ((Memoable)this.sha256).reset(this.sha256Memo);
      byte[] arrayOfByte = compressedADRS(param1ADRS);
      this.sha256.update(arrayOfByte, 0, arrayOfByte.length);
      this.sha256.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.sha256.doFinal(this.sha256Buf, 0);
      return Arrays.copyOfRange(this.sha256Buf, 0, i);
    }
    
    public byte[] PRF_msg(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4) {
      this.treeHMac.init((CipherParameters)new KeyParameter(param1ArrayOfbyte1));
      this.treeHMac.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      if (param1ArrayOfbyte3 != null)
        this.treeHMac.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length); 
      this.treeHMac.update(param1ArrayOfbyte4, 0, param1ArrayOfbyte4.length);
      this.treeHMac.doFinal(this.hmacBuf, 0);
      return Arrays.copyOfRange(this.hmacBuf, 0, this.N);
    }
    
    private byte[] compressedADRS(ADRS param1ADRS) {
      byte[] arrayOfByte = new byte[22];
      System.arraycopy(param1ADRS.value, 3, arrayOfByte, 0, 1);
      System.arraycopy(param1ADRS.value, 8, arrayOfByte, 1, 8);
      System.arraycopy(param1ADRS.value, 19, arrayOfByte, 9, 1);
      System.arraycopy(param1ADRS.value, 20, arrayOfByte, 10, 12);
      return arrayOfByte;
    }
    
    protected byte[] bitmask(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte = new byte[param1ArrayOfbyte2.length];
      this.mgf1.init((DerivationParameters)new MGFParameters(param1ArrayOfbyte1));
      this.mgf1.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      Bytes.xorTo(param1ArrayOfbyte2.length, param1ArrayOfbyte2, arrayOfByte);
      return arrayOfByte;
    }
    
    protected byte[] bitmask(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3) {
      byte[] arrayOfByte = new byte[param1ArrayOfbyte2.length + param1ArrayOfbyte3.length];
      this.mgf1.init((DerivationParameters)new MGFParameters(param1ArrayOfbyte1));
      this.mgf1.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      Bytes.xorTo(param1ArrayOfbyte2.length, param1ArrayOfbyte2, arrayOfByte);
      Bytes.xorTo(param1ArrayOfbyte3.length, param1ArrayOfbyte3, 0, arrayOfByte, param1ArrayOfbyte2.length);
      return arrayOfByte;
    }
    
    protected byte[] bitmask256(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte = new byte[param1ArrayOfbyte2.length];
      MGF1BytesGenerator mGF1BytesGenerator = new MGF1BytesGenerator((Digest)new SHA256Digest());
      mGF1BytesGenerator.init((DerivationParameters)new MGFParameters(param1ArrayOfbyte1));
      mGF1BytesGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      Bytes.xorTo(param1ArrayOfbyte2.length, param1ArrayOfbyte2, arrayOfByte);
      return arrayOfByte;
    }
  }
  
  static class Shake256Engine extends SLHDSAEngine {
    private final Xof treeDigest = (Xof)new SHAKEDigest(256);
    
    private final Xof maskDigest = (Xof)new SHAKEDigest(256);
    
    public Shake256Engine(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
      super(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
    }
    
    void init(byte[] param1ArrayOfbyte) {}
    
    byte[] F(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte1 = param1ArrayOfbyte2;
      byte[] arrayOfByte2 = new byte[this.N];
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.treeDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.treeDigest.doFinal(arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    
    byte[] H(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3) {
      byte[] arrayOfByte = new byte[this.N];
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.treeDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.treeDigest.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length);
      this.treeDigest.doFinal(arrayOfByte, 0, arrayOfByte.length);
      return arrayOfByte;
    }
    
    IndexedDigest H_msg(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4, byte[] param1ArrayOfbyte5) {
      int i = (this.A * this.K + 7) / 8;
      int j = this.H / this.D;
      int k = this.H - j;
      int m = (j + 7) / 8;
      int n = (k + 7) / 8;
      int i1 = i + m + n;
      byte[] arrayOfByte1 = new byte[i1];
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      this.treeDigest.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length);
      if (param1ArrayOfbyte4 != null)
        this.treeDigest.update(param1ArrayOfbyte4, 0, param1ArrayOfbyte4.length); 
      this.treeDigest.update(param1ArrayOfbyte5, 0, param1ArrayOfbyte5.length);
      this.treeDigest.doFinal(arrayOfByte1, 0, arrayOfByte1.length);
      byte[] arrayOfByte2 = new byte[8];
      System.arraycopy(arrayOfByte1, i, arrayOfByte2, 8 - n, n);
      long l = Pack.bigEndianToLong(arrayOfByte2, 0);
      l &= -1L >>> 64 - k;
      byte[] arrayOfByte3 = new byte[4];
      System.arraycopy(arrayOfByte1, i + n, arrayOfByte3, 4 - m, m);
      int i2 = Pack.bigEndianToInt(arrayOfByte3, 0);
      i2 &= -1 >>> 32 - j;
      return new IndexedDigest(l, i2, Arrays.copyOfRange(arrayOfByte1, 0, i));
    }
    
    byte[] T_l(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte1 = param1ArrayOfbyte2;
      byte[] arrayOfByte2 = new byte[this.N];
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.treeDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.treeDigest.doFinal(arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    
    byte[] PRF(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, ADRS param1ADRS) {
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.treeDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      byte[] arrayOfByte = new byte[this.N];
      this.treeDigest.doFinal(arrayOfByte, 0, this.N);
      return arrayOfByte;
    }
    
    public byte[] PRF_msg(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4) {
      this.treeDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.treeDigest.update(param1ArrayOfbyte2, 0, param1ArrayOfbyte2.length);
      if (param1ArrayOfbyte3 != null)
        this.treeDigest.update(param1ArrayOfbyte3, 0, param1ArrayOfbyte3.length); 
      this.treeDigest.update(param1ArrayOfbyte4, 0, param1ArrayOfbyte4.length);
      byte[] arrayOfByte = new byte[this.N];
      this.treeDigest.doFinal(arrayOfByte, 0, arrayOfByte.length);
      return arrayOfByte;
    }
    
    protected byte[] bitmask(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2) {
      byte[] arrayOfByte = new byte[param1ArrayOfbyte2.length];
      this.maskDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.maskDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.maskDigest.doFinal(arrayOfByte, 0, arrayOfByte.length);
      Bytes.xorTo(param1ArrayOfbyte2.length, param1ArrayOfbyte2, arrayOfByte);
      return arrayOfByte;
    }
    
    protected byte[] bitmask(byte[] param1ArrayOfbyte1, ADRS param1ADRS, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3) {
      byte[] arrayOfByte = new byte[param1ArrayOfbyte2.length + param1ArrayOfbyte3.length];
      this.maskDigest.update(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
      this.maskDigest.update(param1ADRS.value, 0, param1ADRS.value.length);
      this.maskDigest.doFinal(arrayOfByte, 0, arrayOfByte.length);
      Bytes.xorTo(param1ArrayOfbyte2.length, param1ArrayOfbyte2, arrayOfByte);
      Bytes.xorTo(param1ArrayOfbyte3.length, param1ArrayOfbyte3, 0, arrayOfByte, param1ArrayOfbyte2.length);
      return arrayOfByte;
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */