package org.bouncycastle.crypto.hpke;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.crypto.agreement.BasicRawAgreement;
import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.agreement.X448Agreement;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.generators.X448KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.crypto.params.X448KeyGenerationParameters;
import org.bouncycastle.crypto.params.X448PrivateKeyParameters;
import org.bouncycastle.crypto.params.X448PublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.math.ec.rfc7748.X25519;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;

class DHKEM extends KEM {
  private AsymmetricCipherKeyPairGenerator kpGen;
  
  private RawAgreement rawAgreement;
  
  private final short kemId;
  
  private HKDF hkdf;
  
  private byte bitmask;
  
  private int Nsk;
  
  private int Nsecret;
  
  private int Nenc;
  
  ECDomainParameters domainParams;
  
  protected DHKEM(short paramShort) {
    this.kemId = paramShort;
    switch (paramShort) {
      case 16:
        this.hkdf = new HKDF((short)1);
        this.domainParams = getDomainParameters("P-256");
        this.rawAgreement = (RawAgreement)new BasicRawAgreement((BasicAgreement)new ECDHCBasicAgreement());
        this.bitmask = -1;
        this.Nsk = 32;
        this.Nsecret = 32;
        this.Nenc = 65;
        this.kpGen = (AsymmetricCipherKeyPairGenerator)new ECKeyPairGenerator();
        this.kpGen.init((KeyGenerationParameters)new ECKeyGenerationParameters(this.domainParams, getSecureRandom()));
        return;
      case 17:
        this.hkdf = new HKDF((short)2);
        this.domainParams = getDomainParameters("P-384");
        this.rawAgreement = (RawAgreement)new BasicRawAgreement((BasicAgreement)new ECDHCBasicAgreement());
        this.bitmask = -1;
        this.Nsk = 48;
        this.Nsecret = 48;
        this.Nenc = 97;
        this.kpGen = (AsymmetricCipherKeyPairGenerator)new ECKeyPairGenerator();
        this.kpGen.init((KeyGenerationParameters)new ECKeyGenerationParameters(this.domainParams, getSecureRandom()));
        return;
      case 18:
        this.hkdf = new HKDF((short)3);
        this.domainParams = getDomainParameters("P-521");
        this.rawAgreement = (RawAgreement)new BasicRawAgreement((BasicAgreement)new ECDHCBasicAgreement());
        this.bitmask = 1;
        this.Nsk = 66;
        this.Nsecret = 64;
        this.Nenc = 133;
        this.kpGen = (AsymmetricCipherKeyPairGenerator)new ECKeyPairGenerator();
        this.kpGen.init((KeyGenerationParameters)new ECKeyGenerationParameters(this.domainParams, getSecureRandom()));
        return;
      case 32:
        this.hkdf = new HKDF((short)1);
        this.rawAgreement = (RawAgreement)new X25519Agreement();
        this.Nsecret = 32;
        this.Nsk = 32;
        this.Nenc = 32;
        this.kpGen = (AsymmetricCipherKeyPairGenerator)new X25519KeyPairGenerator();
        this.kpGen.init((KeyGenerationParameters)new X25519KeyGenerationParameters(getSecureRandom()));
        return;
      case 33:
        this.hkdf = new HKDF((short)3);
        this.rawAgreement = (RawAgreement)new X448Agreement();
        this.Nsecret = 64;
        this.Nsk = 56;
        this.Nenc = 56;
        this.kpGen = (AsymmetricCipherKeyPairGenerator)new X448KeyPairGenerator();
        this.kpGen.init((KeyGenerationParameters)new X448KeyGenerationParameters(getSecureRandom()));
        return;
    } 
    throw new IllegalArgumentException("invalid kem id");
  }
  
  public byte[] SerializePublicKey(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    switch (this.kemId) {
      case 16:
      case 17:
      case 18:
        return ((ECPublicKeyParameters)paramAsymmetricKeyParameter).getQ().getEncoded(false);
      case 33:
        return ((X448PublicKeyParameters)paramAsymmetricKeyParameter).getEncoded();
      case 32:
        return ((X25519PublicKeyParameters)paramAsymmetricKeyParameter).getEncoded();
    } 
    throw new IllegalStateException("invalid kem id");
  }
  
  public byte[] SerializePrivateKey(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    byte[] arrayOfByte;
    switch (this.kemId) {
      case 16:
      case 17:
      case 18:
        return BigIntegers.asUnsignedByteArray(this.Nsk, ((ECPrivateKeyParameters)paramAsymmetricKeyParameter).getD());
      case 33:
        arrayOfByte = ((X448PrivateKeyParameters)paramAsymmetricKeyParameter).getEncoded();
        X448.clampPrivateKey(arrayOfByte);
        return arrayOfByte;
      case 32:
        arrayOfByte = ((X25519PrivateKeyParameters)paramAsymmetricKeyParameter).getEncoded();
        X25519.clampPrivateKey(arrayOfByte);
        return arrayOfByte;
    } 
    throw new IllegalStateException("invalid kem id");
  }
  
  public AsymmetricKeyParameter DeserializePublicKey(byte[] paramArrayOfbyte) {
    ECPoint eCPoint;
    if (paramArrayOfbyte == null)
      throw new NullPointerException("'pkEncoded' cannot be null"); 
    if (paramArrayOfbyte.length != this.Nenc)
      throw new IllegalArgumentException("'pkEncoded' has invalid length"); 
    switch (this.kemId) {
      case 16:
      case 17:
      case 18:
        if (paramArrayOfbyte[0] != 4)
          throw new IllegalArgumentException("'pkEncoded' has invalid format"); 
        eCPoint = this.domainParams.getCurve().decodePoint(paramArrayOfbyte);
        return (AsymmetricKeyParameter)new ECPublicKeyParameters(eCPoint, this.domainParams);
      case 33:
        return (AsymmetricKeyParameter)new X448PublicKeyParameters(paramArrayOfbyte);
      case 32:
        return (AsymmetricKeyParameter)new X25519PublicKeyParameters(paramArrayOfbyte);
    } 
    throw new IllegalStateException("invalid kem id");
  }
  
  public AsymmetricCipherKeyPair DeserializePrivateKey(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    ECPublicKeyParameters eCPublicKeyParameters;
    X448PublicKeyParameters x448PublicKeyParameters;
    X25519PublicKeyParameters x25519PublicKeyParameters;
    BigInteger bigInteger;
    ECPrivateKeyParameters eCPrivateKeyParameters;
    X448PrivateKeyParameters x448PrivateKeyParameters;
    X25519PrivateKeyParameters x25519PrivateKeyParameters;
    if (paramArrayOfbyte1 == null)
      throw new NullPointerException("'skEncoded' cannot be null"); 
    if (paramArrayOfbyte1.length != this.Nsk)
      throw new IllegalArgumentException("'skEncoded' has invalid length"); 
    AsymmetricKeyParameter asymmetricKeyParameter = null;
    if (paramArrayOfbyte2 != null)
      asymmetricKeyParameter = DeserializePublicKey(paramArrayOfbyte2); 
    switch (this.kemId) {
      case 16:
      case 17:
      case 18:
        bigInteger = new BigInteger(1, paramArrayOfbyte1);
        eCPrivateKeyParameters = new ECPrivateKeyParameters(bigInteger, this.domainParams);
        if (asymmetricKeyParameter == null) {
          ECPoint eCPoint = (new FixedPointCombMultiplier()).multiply(this.domainParams.getG(), eCPrivateKeyParameters.getD());
          eCPublicKeyParameters = new ECPublicKeyParameters(eCPoint, this.domainParams);
        } 
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)eCPublicKeyParameters, (AsymmetricKeyParameter)eCPrivateKeyParameters);
      case 33:
        x448PrivateKeyParameters = new X448PrivateKeyParameters(paramArrayOfbyte1);
        if (eCPublicKeyParameters == null)
          x448PublicKeyParameters = x448PrivateKeyParameters.generatePublicKey(); 
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)x448PublicKeyParameters, (AsymmetricKeyParameter)x448PrivateKeyParameters);
      case 32:
        x25519PrivateKeyParameters = new X25519PrivateKeyParameters(paramArrayOfbyte1);
        if (x448PublicKeyParameters == null)
          x25519PublicKeyParameters = x25519PrivateKeyParameters.generatePublicKey(); 
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)x25519PublicKeyParameters, (AsymmetricKeyParameter)x25519PrivateKeyParameters);
    } 
    throw new IllegalStateException("invalid kem id");
  }
  
  int getEncryptionSize() {
    return this.Nenc;
  }
  
  private boolean validateSk(BigInteger paramBigInteger) {
    BigInteger bigInteger = this.domainParams.getN();
    int i = bigInteger.bitLength();
    int j = i >>> 2;
    return (paramBigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || paramBigInteger.compareTo(bigInteger) >= 0) ? false : (!(WNafUtil.getNafWeight(paramBigInteger) < j));
  }
  
  public AsymmetricCipherKeyPair GeneratePrivateKey() {
    return this.kpGen.generateKeyPair();
  }
  
  public AsymmetricCipherKeyPair DeriveKeyPair(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte2;
    byte[] arrayOfByte3;
    byte b;
    X448PrivateKeyParameters x448PrivateKeyParameters;
    X25519PrivateKeyParameters x25519PrivateKeyParameters;
    byte[] arrayOfByte1 = Arrays.concatenate(Strings.toByteArray("KEM"), Pack.shortToBigEndian(this.kemId));
    switch (this.kemId) {
      case 16:
      case 17:
      case 18:
        arrayOfByte2 = this.hkdf.LabeledExtract(null, arrayOfByte1, "dkp_prk", paramArrayOfbyte);
        arrayOfByte3 = new byte[1];
        for (b = 0; b < 'Ā'; b++) {
          arrayOfByte3[0] = (byte)b;
          byte[] arrayOfByte = this.hkdf.LabeledExpand(arrayOfByte2, arrayOfByte1, "candidate", arrayOfByte3, this.Nsk);
          arrayOfByte[0] = (byte)(arrayOfByte[0] & this.bitmask);
          BigInteger bigInteger = new BigInteger(1, arrayOfByte);
          if (validateSk(bigInteger)) {
            ECPoint eCPoint = (new FixedPointCombMultiplier()).multiply(this.domainParams.getG(), bigInteger);
            ECPrivateKeyParameters eCPrivateKeyParameters = new ECPrivateKeyParameters(bigInteger, this.domainParams);
            ECPublicKeyParameters eCPublicKeyParameters = new ECPublicKeyParameters(eCPoint, this.domainParams);
            return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)eCPublicKeyParameters, (AsymmetricKeyParameter)eCPrivateKeyParameters);
          } 
        } 
        throw new IllegalStateException("DeriveKeyPairError");
      case 33:
        arrayOfByte2 = this.hkdf.LabeledExtract(null, arrayOfByte1, "dkp_prk", paramArrayOfbyte);
        arrayOfByte3 = this.hkdf.LabeledExpand(arrayOfByte2, arrayOfByte1, "sk", null, this.Nsk);
        x448PrivateKeyParameters = new X448PrivateKeyParameters(arrayOfByte3);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)x448PrivateKeyParameters.generatePublicKey(), (AsymmetricKeyParameter)x448PrivateKeyParameters);
      case 32:
        arrayOfByte2 = this.hkdf.LabeledExtract(null, arrayOfByte1, "dkp_prk", paramArrayOfbyte);
        arrayOfByte3 = this.hkdf.LabeledExpand(arrayOfByte2, arrayOfByte1, "sk", null, this.Nsk);
        x25519PrivateKeyParameters = new X25519PrivateKeyParameters(arrayOfByte3);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)x25519PrivateKeyParameters.generatePublicKey(), (AsymmetricKeyParameter)x25519PrivateKeyParameters);
    } 
    throw new IllegalStateException("invalid kem id");
  }
  
  protected byte[][] Encap(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    return Encap(paramAsymmetricKeyParameter, this.kpGen.generateKeyPair());
  }
  
  protected byte[][] Encap(AsymmetricKeyParameter paramAsymmetricKeyParameter, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair) {
    byte[][] arrayOfByte = new byte[2][];
    byte[] arrayOfByte1 = calculateRawAgreement(this.rawAgreement, paramAsymmetricCipherKeyPair.getPrivate(), paramAsymmetricKeyParameter);
    byte[] arrayOfByte2 = SerializePublicKey(paramAsymmetricCipherKeyPair.getPublic());
    byte[] arrayOfByte3 = SerializePublicKey(paramAsymmetricKeyParameter);
    byte[] arrayOfByte4 = Arrays.concatenate(arrayOfByte2, arrayOfByte3);
    byte[] arrayOfByte5 = ExtractAndExpand(arrayOfByte1, arrayOfByte4);
    arrayOfByte[0] = arrayOfByte5;
    arrayOfByte[1] = arrayOfByte2;
    return arrayOfByte;
  }
  
  protected byte[] Decap(byte[] paramArrayOfbyte, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair) {
    AsymmetricKeyParameter asymmetricKeyParameter = DeserializePublicKey(paramArrayOfbyte);
    byte[] arrayOfByte1 = calculateRawAgreement(this.rawAgreement, paramAsymmetricCipherKeyPair.getPrivate(), asymmetricKeyParameter);
    byte[] arrayOfByte2 = SerializePublicKey(paramAsymmetricCipherKeyPair.getPublic());
    byte[] arrayOfByte3 = Arrays.concatenate(paramArrayOfbyte, arrayOfByte2);
    return ExtractAndExpand(arrayOfByte1, arrayOfByte3);
  }
  
  protected byte[][] AuthEncap(AsymmetricKeyParameter paramAsymmetricKeyParameter, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair) {
    byte[][] arrayOfByte = new byte[2][];
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.kpGen.generateKeyPair();
    this.rawAgreement.init((CipherParameters)asymmetricCipherKeyPair.getPrivate());
    int i = this.rawAgreement.getAgreementSize();
    byte[] arrayOfByte1 = new byte[i * 2];
    this.rawAgreement.calculateAgreement((CipherParameters)paramAsymmetricKeyParameter, arrayOfByte1, 0);
    this.rawAgreement.init((CipherParameters)paramAsymmetricCipherKeyPair.getPrivate());
    if (i != this.rawAgreement.getAgreementSize())
      throw new IllegalStateException(); 
    this.rawAgreement.calculateAgreement((CipherParameters)paramAsymmetricKeyParameter, arrayOfByte1, i);
    byte[] arrayOfByte2 = SerializePublicKey(asymmetricCipherKeyPair.getPublic());
    byte[] arrayOfByte3 = SerializePublicKey(paramAsymmetricKeyParameter);
    byte[] arrayOfByte4 = SerializePublicKey(paramAsymmetricCipherKeyPair.getPublic());
    byte[] arrayOfByte5 = Arrays.concatenate(arrayOfByte2, arrayOfByte3, arrayOfByte4);
    byte[] arrayOfByte6 = ExtractAndExpand(arrayOfByte1, arrayOfByte5);
    arrayOfByte[0] = arrayOfByte6;
    arrayOfByte[1] = arrayOfByte2;
    return arrayOfByte;
  }
  
  protected byte[] AuthDecap(byte[] paramArrayOfbyte, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair, AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    AsymmetricKeyParameter asymmetricKeyParameter = DeserializePublicKey(paramArrayOfbyte);
    this.rawAgreement.init((CipherParameters)paramAsymmetricCipherKeyPair.getPrivate());
    int i = this.rawAgreement.getAgreementSize();
    byte[] arrayOfByte1 = new byte[i * 2];
    this.rawAgreement.calculateAgreement((CipherParameters)asymmetricKeyParameter, arrayOfByte1, 0);
    this.rawAgreement.calculateAgreement((CipherParameters)paramAsymmetricKeyParameter, arrayOfByte1, i);
    byte[] arrayOfByte2 = SerializePublicKey(paramAsymmetricCipherKeyPair.getPublic());
    byte[] arrayOfByte3 = SerializePublicKey(paramAsymmetricKeyParameter);
    byte[] arrayOfByte4 = Arrays.concatenate(paramArrayOfbyte, arrayOfByte2, arrayOfByte3);
    return ExtractAndExpand(arrayOfByte1, arrayOfByte4);
  }
  
  private byte[] ExtractAndExpand(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    byte[] arrayOfByte1 = Arrays.concatenate(Strings.toByteArray("KEM"), Pack.shortToBigEndian(this.kemId));
    byte[] arrayOfByte2 = this.hkdf.LabeledExtract(null, arrayOfByte1, "eae_prk", paramArrayOfbyte1);
    return this.hkdf.LabeledExpand(arrayOfByte2, arrayOfByte1, "shared_secret", paramArrayOfbyte2, this.Nsecret);
  }
  
  private static byte[] calculateRawAgreement(RawAgreement paramRawAgreement, AsymmetricKeyParameter paramAsymmetricKeyParameter1, AsymmetricKeyParameter paramAsymmetricKeyParameter2) {
    paramRawAgreement.init((CipherParameters)paramAsymmetricKeyParameter1);
    byte[] arrayOfByte = new byte[paramRawAgreement.getAgreementSize()];
    paramRawAgreement.calculateAgreement((CipherParameters)paramAsymmetricKeyParameter2, arrayOfByte, 0);
    return arrayOfByte;
  }
  
  private static ECDomainParameters getDomainParameters(String paramString) {
    return new ECDomainParameters(CustomNamedCurves.getByName(paramString));
  }
  
  private static SecureRandom getSecureRandom() {
    return CryptoServicesRegistrar.getSecureRandom();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\hpke\DHKEM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */