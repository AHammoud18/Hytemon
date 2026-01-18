package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.pqc.crypto.mldsa.Packing;
import org.bouncycastle.pqc.crypto.mldsa.Poly;
import org.bouncycastle.pqc.crypto.mldsa.PolyVecK;
import org.bouncycastle.pqc.crypto.mldsa.PolyVecL;
import org.bouncycastle.pqc.crypto.mldsa.PolyVecMatrix;
import org.bouncycastle.pqc.crypto.mldsa.Symmetric;
import org.bouncycastle.util.Arrays;

class MLDSAEngine {
  private final SecureRandom random;
  
  final SHAKEDigest shake256Digest = new SHAKEDigest(256);
  
  public static final int DilithiumN = 256;
  
  public static final int DilithiumQ = 8380417;
  
  public static final int DilithiumQinv = 58728449;
  
  public static final int DilithiumD = 13;
  
  public static final int SeedBytes = 32;
  
  public static final int CrhBytes = 64;
  
  public static final int RndBytes = 32;
  
  public static final int TrBytes = 64;
  
  public static final int DilithiumPolyT1PackedBytes = 320;
  
  public static final int DilithiumPolyT0PackedBytes = 416;
  
  private final int DilithiumPolyVecHPackedBytes;
  
  private final int DilithiumPolyZPackedBytes;
  
  private final int DilithiumPolyW1PackedBytes;
  
  private final int DilithiumPolyEtaPackedBytes;
  
  private final int DilithiumK;
  
  private final int DilithiumL;
  
  private final int DilithiumEta;
  
  private final int DilithiumTau;
  
  private final int DilithiumBeta;
  
  private final int DilithiumGamma1;
  
  private final int DilithiumGamma2;
  
  private final int DilithiumOmega;
  
  private final int DilithiumCTilde;
  
  private final int CryptoPublicKeyBytes;
  
  private final int CryptoBytes;
  
  private final int PolyUniformGamma1NBlocks;
  
  private final Symmetric symmetric;
  
  protected Symmetric GetSymmetric() {
    return this.symmetric;
  }
  
  int getDilithiumPolyZPackedBytes() {
    return this.DilithiumPolyZPackedBytes;
  }
  
  int getDilithiumPolyW1PackedBytes() {
    return this.DilithiumPolyW1PackedBytes;
  }
  
  int getDilithiumPolyEtaPackedBytes() {
    return this.DilithiumPolyEtaPackedBytes;
  }
  
  int getDilithiumK() {
    return this.DilithiumK;
  }
  
  int getDilithiumL() {
    return this.DilithiumL;
  }
  
  int getDilithiumEta() {
    return this.DilithiumEta;
  }
  
  int getDilithiumTau() {
    return this.DilithiumTau;
  }
  
  int getDilithiumBeta() {
    return this.DilithiumBeta;
  }
  
  int getDilithiumGamma1() {
    return this.DilithiumGamma1;
  }
  
  int getDilithiumGamma2() {
    return this.DilithiumGamma2;
  }
  
  int getDilithiumOmega() {
    return this.DilithiumOmega;
  }
  
  int getDilithiumCTilde() {
    return this.DilithiumCTilde;
  }
  
  int getCryptoPublicKeyBytes() {
    return this.CryptoPublicKeyBytes;
  }
  
  int getPolyUniformGamma1NBlocks() {
    return this.PolyUniformGamma1NBlocks;
  }
  
  MLDSAEngine(int paramInt, SecureRandom paramSecureRandom) {
    switch (paramInt) {
      case 2:
        this.DilithiumK = 4;
        this.DilithiumL = 4;
        this.DilithiumEta = 2;
        this.DilithiumTau = 39;
        this.DilithiumBeta = 78;
        this.DilithiumGamma1 = 131072;
        this.DilithiumGamma2 = 95232;
        this.DilithiumOmega = 80;
        this.DilithiumPolyZPackedBytes = 576;
        this.DilithiumPolyW1PackedBytes = 192;
        this.DilithiumPolyEtaPackedBytes = 96;
        this.DilithiumCTilde = 32;
        break;
      case 3:
        this.DilithiumK = 6;
        this.DilithiumL = 5;
        this.DilithiumEta = 4;
        this.DilithiumTau = 49;
        this.DilithiumBeta = 196;
        this.DilithiumGamma1 = 524288;
        this.DilithiumGamma2 = 261888;
        this.DilithiumOmega = 55;
        this.DilithiumPolyZPackedBytes = 640;
        this.DilithiumPolyW1PackedBytes = 128;
        this.DilithiumPolyEtaPackedBytes = 128;
        this.DilithiumCTilde = 48;
        break;
      case 5:
        this.DilithiumK = 8;
        this.DilithiumL = 7;
        this.DilithiumEta = 2;
        this.DilithiumTau = 60;
        this.DilithiumBeta = 120;
        this.DilithiumGamma1 = 524288;
        this.DilithiumGamma2 = 261888;
        this.DilithiumOmega = 75;
        this.DilithiumPolyZPackedBytes = 640;
        this.DilithiumPolyW1PackedBytes = 128;
        this.DilithiumPolyEtaPackedBytes = 96;
        this.DilithiumCTilde = 64;
        break;
      default:
        throw new IllegalArgumentException("The mode " + paramInt + "is not supported by Crystals Dilithium!");
    } 
    this.symmetric = (Symmetric)new Symmetric.ShakeSymmetric();
    this.random = paramSecureRandom;
    this.DilithiumPolyVecHPackedBytes = this.DilithiumOmega + this.DilithiumK;
    this.CryptoPublicKeyBytes = 32 + this.DilithiumK * 320;
    this.CryptoBytes = this.DilithiumCTilde + this.DilithiumL * this.DilithiumPolyZPackedBytes + this.DilithiumPolyVecHPackedBytes;
    if (this.DilithiumGamma1 == 131072) {
      this.PolyUniformGamma1NBlocks = (576 + this.symmetric.stream256BlockBytes - 1) / this.symmetric.stream256BlockBytes;
    } else if (this.DilithiumGamma1 == 524288) {
      this.PolyUniformGamma1NBlocks = (640 + this.symmetric.stream256BlockBytes - 1) / this.symmetric.stream256BlockBytes;
    } else {
      throw new RuntimeException("Wrong Dilithium Gamma1!");
    } 
  }
  
  byte[][] generateKeyPairInternal(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte1 = new byte[128];
    byte[] arrayOfByte2 = new byte[64];
    byte[] arrayOfByte3 = new byte[32];
    byte[] arrayOfByte4 = new byte[64];
    byte[] arrayOfByte5 = new byte[32];
    PolyVecMatrix polyVecMatrix = new PolyVecMatrix(this);
    PolyVecL polyVecL1 = new PolyVecL(this);
    PolyVecK polyVecK1 = new PolyVecK(this);
    PolyVecK polyVecK2 = new PolyVecK(this);
    PolyVecK polyVecK3 = new PolyVecK(this);
    this.shake256Digest.update(paramArrayOfbyte, 0, 32);
    this.shake256Digest.update((byte)this.DilithiumK);
    this.shake256Digest.update((byte)this.DilithiumL);
    this.shake256Digest.doFinal(arrayOfByte1, 0, 128);
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, 32);
    System.arraycopy(arrayOfByte1, 32, arrayOfByte4, 0, 64);
    System.arraycopy(arrayOfByte1, 96, arrayOfByte5, 0, 32);
    polyVecMatrix.expandMatrix(arrayOfByte3);
    polyVecL1.uniformEta(arrayOfByte4, (short)0);
    polyVecK1.uniformEta(arrayOfByte4, (short)this.DilithiumL);
    PolyVecL polyVecL2 = new PolyVecL(this);
    polyVecL1.copyTo(polyVecL2);
    polyVecL2.polyVecNtt();
    polyVecMatrix.pointwiseMontgomery(polyVecK2, polyVecL2);
    polyVecK2.reduce();
    polyVecK2.invNttToMont();
    polyVecK2.addPolyVecK(polyVecK1);
    polyVecK2.conditionalAddQ();
    polyVecK2.power2Round(polyVecK3);
    byte[] arrayOfByte6 = Packing.packPublicKey(polyVecK2, this);
    this.shake256Digest.update(arrayOfByte3, 0, arrayOfByte3.length);
    this.shake256Digest.update(arrayOfByte6, 0, arrayOfByte6.length);
    this.shake256Digest.doFinal(arrayOfByte2, 0, 64);
    byte[][] arrayOfByte = Packing.packSecretKey(arrayOfByte3, arrayOfByte2, arrayOfByte5, polyVecK3, polyVecL1, polyVecK1, this);
    return new byte[][] { arrayOfByte[0], arrayOfByte[1], arrayOfByte[2], arrayOfByte[3], arrayOfByte[4], arrayOfByte[5], arrayOfByte6, paramArrayOfbyte };
  }
  
  byte[] deriveT1(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6) {
    PolyVecMatrix polyVecMatrix = new PolyVecMatrix(this);
    PolyVecL polyVecL1 = new PolyVecL(this);
    PolyVecK polyVecK1 = new PolyVecK(this);
    PolyVecK polyVecK2 = new PolyVecK(this);
    PolyVecK polyVecK3 = new PolyVecK(this);
    Packing.unpackSecretKey(polyVecK3, polyVecL1, polyVecK1, paramArrayOfbyte6, paramArrayOfbyte4, paramArrayOfbyte5, this);
    polyVecMatrix.expandMatrix(paramArrayOfbyte1);
    PolyVecL polyVecL2 = new PolyVecL(this);
    polyVecL1.copyTo(polyVecL2);
    polyVecL2.polyVecNtt();
    polyVecMatrix.pointwiseMontgomery(polyVecK2, polyVecL2);
    polyVecK2.reduce();
    polyVecK2.invNttToMont();
    polyVecK2.addPolyVecK(polyVecK1);
    polyVecK2.conditionalAddQ();
    polyVecK2.power2Round(polyVecK3);
    return Packing.packPublicKey(polyVecK2, this);
  }
  
  SHAKEDigest getShake256Digest() {
    return new SHAKEDigest(this.shake256Digest);
  }
  
  void initSign(byte[] paramArrayOfbyte1, boolean paramBoolean, byte[] paramArrayOfbyte2) {
    this.shake256Digest.update(paramArrayOfbyte1, 0, 64);
    absorbCtx(paramBoolean, paramArrayOfbyte2);
  }
  
  void initVerify(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, boolean paramBoolean, byte[] paramArrayOfbyte3) {
    byte[] arrayOfByte = new byte[64];
    this.shake256Digest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    this.shake256Digest.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
    this.shake256Digest.doFinal(arrayOfByte, 0, 64);
    this.shake256Digest.update(arrayOfByte, 0, 64);
    absorbCtx(paramBoolean, paramArrayOfbyte3);
  }
  
  void absorbCtx(boolean paramBoolean, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null) {
      this.shake256Digest.update(paramBoolean ? 1 : 0);
      this.shake256Digest.update((byte)paramArrayOfbyte.length);
      this.shake256Digest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    } 
  }
  
  byte[] signInternal(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6, byte[] paramArrayOfbyte7) {
    SHAKEDigest sHAKEDigest = new SHAKEDigest(this.shake256Digest);
    sHAKEDigest.update(paramArrayOfbyte1, 0, paramInt);
    return generateSignature(generateMu(sHAKEDigest), sHAKEDigest, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramArrayOfbyte5, paramArrayOfbyte6, paramArrayOfbyte7);
  }
  
  byte[] generateMu(SHAKEDigest paramSHAKEDigest) {
    byte[] arrayOfByte = new byte[64];
    paramSHAKEDigest.doFinal(arrayOfByte, 0, 64);
    return arrayOfByte;
  }
  
  byte[] generateSignature(byte[] paramArrayOfbyte1, SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6, byte[] paramArrayOfbyte7) {
    byte[] arrayOfByte1 = new byte[this.CryptoBytes];
    byte[] arrayOfByte2 = new byte[64];
    short s = 0;
    PolyVecL polyVecL1 = new PolyVecL(this);
    PolyVecL polyVecL2 = new PolyVecL(this);
    PolyVecL polyVecL3 = new PolyVecL(this);
    PolyVecK polyVecK1 = new PolyVecK(this);
    PolyVecK polyVecK2 = new PolyVecK(this);
    PolyVecK polyVecK3 = new PolyVecK(this);
    PolyVecK polyVecK4 = new PolyVecK(this);
    PolyVecK polyVecK5 = new PolyVecK(this);
    Poly poly = new Poly(this);
    PolyVecMatrix polyVecMatrix = new PolyVecMatrix(this);
    Packing.unpackSecretKey(polyVecK1, polyVecL1, polyVecK2, paramArrayOfbyte4, paramArrayOfbyte5, paramArrayOfbyte6, this);
    byte[] arrayOfByte3 = Arrays.copyOf(paramArrayOfbyte3, 128);
    System.arraycopy(paramArrayOfbyte7, 0, arrayOfByte3, 32, 32);
    System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte3, 64, 64);
    paramSHAKEDigest.update(arrayOfByte3, 0, 128);
    paramSHAKEDigest.doFinal(arrayOfByte2, 0, 64);
    polyVecMatrix.expandMatrix(paramArrayOfbyte2);
    polyVecL1.polyVecNtt();
    polyVecK2.polyVecNtt();
    polyVecK1.polyVecNtt();
    byte b = 0;
    while (b < 'Ϩ') {
      b++;
      s = (short)(s + 1);
      polyVecL2.uniformGamma1(arrayOfByte2, s);
      polyVecL2.copyTo(polyVecL3);
      polyVecL3.polyVecNtt();
      polyVecMatrix.pointwiseMontgomery(polyVecK3, polyVecL3);
      polyVecK3.reduce();
      polyVecK3.invNttToMont();
      polyVecK3.conditionalAddQ();
      polyVecK3.decompose(polyVecK4);
      polyVecK3.packW1(this, arrayOfByte1, 0);
      paramSHAKEDigest.update(paramArrayOfbyte1, 0, 64);
      paramSHAKEDigest.update(arrayOfByte1, 0, this.DilithiumK * this.DilithiumPolyW1PackedBytes);
      paramSHAKEDigest.doFinal(arrayOfByte1, 0, this.DilithiumCTilde);
      poly.challenge(arrayOfByte1, 0, this.DilithiumCTilde);
      poly.polyNtt();
      polyVecL3.pointwisePolyMontgomery(poly, polyVecL1);
      polyVecL3.invNttToMont();
      polyVecL3.addPolyVecL(polyVecL2);
      polyVecL3.reduce();
      if (polyVecL3.checkNorm(this.DilithiumGamma1 - this.DilithiumBeta))
        continue; 
      polyVecK5.pointwisePolyMontgomery(poly, polyVecK2);
      polyVecK5.invNttToMont();
      polyVecK4.subtract(polyVecK5);
      polyVecK4.reduce();
      if (polyVecK4.checkNorm(this.DilithiumGamma2 - this.DilithiumBeta))
        continue; 
      polyVecK5.pointwisePolyMontgomery(poly, polyVecK1);
      polyVecK5.invNttToMont();
      polyVecK5.reduce();
      if (polyVecK5.checkNorm(this.DilithiumGamma2))
        continue; 
      polyVecK4.addPolyVecK(polyVecK5);
      polyVecK4.conditionalAddQ();
      int i = polyVecK5.makeHint(polyVecK4, polyVecK3);
      if (i > this.DilithiumOmega)
        continue; 
      Packing.packSignature(arrayOfByte1, polyVecL3, polyVecK5, this);
      return arrayOfByte1;
    } 
    return null;
  }
  
  boolean verifyInternalMu(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = new byte[64];
    this.shake256Digest.doFinal(arrayOfByte, 0);
    return Arrays.constantTimeAreEqual(arrayOfByte, paramArrayOfbyte);
  }
  
  boolean verifyInternalMuSignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt, SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    byte[] arrayOfByte = new byte[Math.max(64 + this.DilithiumK * this.DilithiumPolyW1PackedBytes, this.DilithiumCTilde)];
    System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
    return doVerifyInternal(arrayOfByte, paramArrayOfbyte2, paramInt, paramSHAKEDigest, paramArrayOfbyte3, paramArrayOfbyte4);
  }
  
  boolean verifyInternal(byte[] paramArrayOfbyte1, int paramInt, SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    byte[] arrayOfByte = new byte[Math.max(64 + this.DilithiumK * this.DilithiumPolyW1PackedBytes, this.DilithiumCTilde)];
    paramSHAKEDigest.doFinal(arrayOfByte, 0);
    return doVerifyInternal(arrayOfByte, paramArrayOfbyte1, paramInt, paramSHAKEDigest, paramArrayOfbyte2, paramArrayOfbyte3);
  }
  
  private boolean doVerifyInternal(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt, SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    if (paramInt != this.CryptoBytes)
      return false; 
    PolyVecK polyVecK1 = new PolyVecK(this);
    PolyVecL polyVecL = new PolyVecL(this);
    if (!Packing.unpackSignature(polyVecL, polyVecK1, paramArrayOfbyte2, this))
      return false; 
    if (polyVecL.checkNorm(getDilithiumGamma1() - getDilithiumBeta()))
      return false; 
    Poly poly = new Poly(this);
    PolyVecMatrix polyVecMatrix = new PolyVecMatrix(this);
    PolyVecK polyVecK2 = new PolyVecK(this);
    PolyVecK polyVecK3 = new PolyVecK(this);
    polyVecK2 = Packing.unpackPublicKey(polyVecK2, paramArrayOfbyte4, this);
    poly.challenge(paramArrayOfbyte2, 0, this.DilithiumCTilde);
    polyVecMatrix.expandMatrix(paramArrayOfbyte3);
    polyVecL.polyVecNtt();
    polyVecMatrix.pointwiseMontgomery(polyVecK3, polyVecL);
    poly.polyNtt();
    polyVecK2.shiftLeft();
    polyVecK2.polyVecNtt();
    polyVecK2.pointwisePolyMontgomery(poly, polyVecK2);
    polyVecK3.subtract(polyVecK2);
    polyVecK3.reduce();
    polyVecK3.invNttToMont();
    polyVecK3.conditionalAddQ();
    polyVecK3.useHint(polyVecK3, polyVecK1);
    polyVecK3.packW1(this, paramArrayOfbyte1, 64);
    paramSHAKEDigest.update(paramArrayOfbyte1, 0, 64 + this.DilithiumK * this.DilithiumPolyW1PackedBytes);
    paramSHAKEDigest.doFinal(paramArrayOfbyte1, 0, this.DilithiumCTilde);
    return Arrays.constantTimeAreEqual(this.DilithiumCTilde, paramArrayOfbyte2, 0, paramArrayOfbyte1, 0);
  }
  
  byte[][] generateKeyPair() {
    byte[] arrayOfByte = new byte[32];
    this.random.nextBytes(arrayOfByte);
    return generateKeyPairInternal(arrayOfByte);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\MLDSAEngine.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */