package META-INF.versions.9.org.bouncycastle.pqc.crypto.mlkem;

import java.security.SecureRandom;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMIndCpa;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.PolyVec;
import org.bouncycastle.pqc.crypto.mlkem.Symmetric;
import org.bouncycastle.util.Arrays;

class MLKEMEngine {
  private SecureRandom random;
  
  private final MLKEMIndCpa indCpa;
  
  public static final int KyberN = 256;
  
  public static final int KyberQ = 3329;
  
  public static final int KyberQinv = 62209;
  
  public static final int KyberSymBytes = 32;
  
  private static final int KyberSharedSecretBytes = 32;
  
  public static final int KyberPolyBytes = 384;
  
  private static final int KyberEta2 = 2;
  
  private static final int KyberIndCpaMsgBytes = 32;
  
  private final int KyberK;
  
  private final int KyberPolyVecBytes;
  
  private final int KyberPolyCompressedBytes;
  
  private final int KyberPolyVecCompressedBytes;
  
  private final int KyberEta1;
  
  private final int KyberIndCpaPublicKeyBytes;
  
  private final int KyberIndCpaSecretKeyBytes;
  
  private final int KyberIndCpaBytes;
  
  private final int KyberPublicKeyBytes;
  
  private final int KyberSecretKeyBytes;
  
  private final int KyberCipherTextBytes;
  
  private final int CryptoBytes;
  
  private final int CryptoSecretKeyBytes;
  
  private final int CryptoPublicKeyBytes;
  
  private final int CryptoCipherTextBytes;
  
  private final int sessionKeyLength;
  
  private final Symmetric symmetric;
  
  public Symmetric getSymmetric() {
    return this.symmetric;
  }
  
  public static int getKyberEta2() {
    return 2;
  }
  
  public static int getKyberIndCpaMsgBytes() {
    return 32;
  }
  
  public int getCryptoCipherTextBytes() {
    return this.CryptoCipherTextBytes;
  }
  
  public int getCryptoPublicKeyBytes() {
    return this.CryptoPublicKeyBytes;
  }
  
  public int getCryptoSecretKeyBytes() {
    return this.CryptoSecretKeyBytes;
  }
  
  public int getCryptoBytes() {
    return this.CryptoBytes;
  }
  
  public int getKyberCipherTextBytes() {
    return this.KyberCipherTextBytes;
  }
  
  public int getKyberSecretKeyBytes() {
    return this.KyberSecretKeyBytes;
  }
  
  public int getKyberIndCpaPublicKeyBytes() {
    return this.KyberIndCpaPublicKeyBytes;
  }
  
  public int getKyberIndCpaSecretKeyBytes() {
    return this.KyberIndCpaSecretKeyBytes;
  }
  
  public int getKyberIndCpaBytes() {
    return this.KyberIndCpaBytes;
  }
  
  public int getKyberPublicKeyBytes() {
    return this.KyberPublicKeyBytes;
  }
  
  public int getKyberPolyCompressedBytes() {
    return this.KyberPolyCompressedBytes;
  }
  
  public int getKyberK() {
    return this.KyberK;
  }
  
  public int getKyberPolyVecBytes() {
    return this.KyberPolyVecBytes;
  }
  
  public int getKyberPolyVecCompressedBytes() {
    return this.KyberPolyVecCompressedBytes;
  }
  
  public int getKyberEta1() {
    return this.KyberEta1;
  }
  
  public MLKEMEngine(int paramInt) {
    this.KyberK = paramInt;
    switch (paramInt) {
      case 2:
        this.KyberEta1 = 3;
        this.KyberPolyCompressedBytes = 128;
        this.KyberPolyVecCompressedBytes = paramInt * 320;
        this.sessionKeyLength = 32;
        break;
      case 3:
        this.KyberEta1 = 2;
        this.KyberPolyCompressedBytes = 128;
        this.KyberPolyVecCompressedBytes = paramInt * 320;
        this.sessionKeyLength = 32;
        break;
      case 4:
        this.KyberEta1 = 2;
        this.KyberPolyCompressedBytes = 160;
        this.KyberPolyVecCompressedBytes = paramInt * 352;
        this.sessionKeyLength = 32;
        break;
      default:
        throw new IllegalArgumentException("K: " + paramInt + " is not supported for Crystals Kyber");
    } 
    this.KyberPolyVecBytes = paramInt * 384;
    this.KyberIndCpaPublicKeyBytes = this.KyberPolyVecBytes + 32;
    this.KyberIndCpaSecretKeyBytes = this.KyberPolyVecBytes;
    this.KyberIndCpaBytes = this.KyberPolyVecCompressedBytes + this.KyberPolyCompressedBytes;
    this.KyberPublicKeyBytes = this.KyberIndCpaPublicKeyBytes;
    this.KyberSecretKeyBytes = this.KyberIndCpaSecretKeyBytes + this.KyberIndCpaPublicKeyBytes + 64;
    this.KyberCipherTextBytes = this.KyberIndCpaBytes;
    this.CryptoBytes = 32;
    this.CryptoSecretKeyBytes = this.KyberSecretKeyBytes;
    this.CryptoPublicKeyBytes = this.KyberPublicKeyBytes;
    this.CryptoCipherTextBytes = this.KyberCipherTextBytes;
    this.symmetric = (Symmetric)new Symmetric.ShakeSymmetric();
    this.indCpa = new MLKEMIndCpa(this);
  }
  
  public void init(SecureRandom paramSecureRandom) {
    this.random = paramSecureRandom;
  }
  
  boolean checkModulus(byte[] paramArrayOfbyte) {
    return (PolyVec.checkModulus(this, paramArrayOfbyte) < 0);
  }
  
  public byte[][] generateKemKeyPair() {
    byte[] arrayOfByte1 = new byte[32];
    byte[] arrayOfByte2 = new byte[32];
    this.random.nextBytes(arrayOfByte1);
    this.random.nextBytes(arrayOfByte2);
    return generateKemKeyPairInternal(arrayOfByte1, arrayOfByte2);
  }
  
  public byte[][] generateKemKeyPairInternal(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    byte[][] arrayOfByte = this.indCpa.generateKeyPair(paramArrayOfbyte1);
    byte[] arrayOfByte1 = new byte[this.KyberIndCpaSecretKeyBytes];
    System.arraycopy(arrayOfByte[1], 0, arrayOfByte1, 0, this.KyberIndCpaSecretKeyBytes);
    byte[] arrayOfByte2 = new byte[32];
    this.symmetric.hash_h(arrayOfByte2, arrayOfByte[0], 0);
    byte[] arrayOfByte3 = new byte[this.KyberIndCpaPublicKeyBytes];
    System.arraycopy(arrayOfByte[0], 0, arrayOfByte3, 0, this.KyberIndCpaPublicKeyBytes);
    return new byte[][] { Arrays.copyOfRange(arrayOfByte3, 0, arrayOfByte3.length - 32), Arrays.copyOfRange(arrayOfByte3, arrayOfByte3.length - 32, arrayOfByte3.length), arrayOfByte1, arrayOfByte2, paramArrayOfbyte2, Arrays.concatenate(paramArrayOfbyte1, paramArrayOfbyte2) };
  }
  
  byte[][] kemEncrypt(MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters, byte[] paramArrayOfbyte) {
    byte[] arrayOfByte1 = paramMLKEMPublicKeyParameters.getEncoded();
    byte[] arrayOfByte2 = new byte[64];
    byte[] arrayOfByte3 = new byte[64];
    System.arraycopy(paramArrayOfbyte, 0, arrayOfByte2, 0, 32);
    this.symmetric.hash_h(arrayOfByte2, arrayOfByte1, 32);
    this.symmetric.hash_g(arrayOfByte3, arrayOfByte2);
    byte[] arrayOfByte4 = this.indCpa.encrypt(arrayOfByte1, Arrays.copyOfRange(arrayOfByte2, 0, 32), Arrays.copyOfRange(arrayOfByte3, 32, arrayOfByte3.length));
    byte[] arrayOfByte5 = new byte[this.sessionKeyLength];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte5, 0, arrayOfByte5.length);
    byte[][] arrayOfByte = new byte[2][];
    arrayOfByte[0] = arrayOfByte5;
    arrayOfByte[1] = arrayOfByte4;
    return arrayOfByte;
  }
  
  byte[] kemDecrypt(MLKEMPrivateKeyParameters paramMLKEMPrivateKeyParameters, byte[] paramArrayOfbyte) {
    byte[] arrayOfByte1 = paramMLKEMPrivateKeyParameters.getEncoded();
    byte[] arrayOfByte2 = new byte[64];
    byte[] arrayOfByte3 = new byte[64];
    byte[] arrayOfByte4 = Arrays.copyOfRange(arrayOfByte1, this.KyberIndCpaSecretKeyBytes, arrayOfByte1.length);
    System.arraycopy(this.indCpa.decrypt(arrayOfByte1, paramArrayOfbyte), 0, arrayOfByte2, 0, 32);
    System.arraycopy(arrayOfByte1, this.KyberSecretKeyBytes - 64, arrayOfByte2, 32, 32);
    this.symmetric.hash_g(arrayOfByte3, arrayOfByte2);
    byte[] arrayOfByte5 = new byte[32 + this.KyberCipherTextBytes];
    System.arraycopy(arrayOfByte1, this.KyberSecretKeyBytes - 32, arrayOfByte5, 0, 32);
    System.arraycopy(paramArrayOfbyte, 0, arrayOfByte5, 32, this.KyberCipherTextBytes);
    this.symmetric.kdf(arrayOfByte5, arrayOfByte5);
    byte[] arrayOfByte6 = this.indCpa.encrypt(arrayOfByte4, Arrays.copyOfRange(arrayOfByte2, 0, 32), Arrays.copyOfRange(arrayOfByte3, 32, arrayOfByte3.length));
    int i = constantTimeZeroOnEqual(paramArrayOfbyte, arrayOfByte6);
    cmov(arrayOfByte3, arrayOfByte5, 32, i);
    return Arrays.copyOfRange(arrayOfByte3, 0, this.sessionKeyLength);
  }
  
  private void cmov(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) {
    int i = 0 - paramInt2 >> 24;
    for (int j = 0; j != paramInt1; j++)
      paramArrayOfbyte1[j] = (byte)(paramArrayOfbyte2[j] & i | paramArrayOfbyte1[j] & (i ^ 0xFFFFFFFF)); 
  }
  
  private int constantTimeZeroOnEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    int i = paramArrayOfbyte2.length ^ paramArrayOfbyte1.length;
    for (byte b = 0; b != paramArrayOfbyte2.length; b++)
      i |= paramArrayOfbyte1[b] ^ paramArrayOfbyte2[b]; 
    return i & 0xFF;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mlkem\MLKEMEngine.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */