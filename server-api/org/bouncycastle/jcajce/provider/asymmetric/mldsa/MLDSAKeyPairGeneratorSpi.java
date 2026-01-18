package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAKeyPairGenerator;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.util.Strings;

public class MLDSAKeyPairGeneratorSpi extends KeyPairGenerator {
  private final MLDSAParameters mldsaParameters = null;
  
  MLDSAKeyGenerationParameters param;
  
  MLDSAKeyPairGenerator engine = new MLDSAKeyPairGenerator();
  
  SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
  
  boolean initialised = false;
  
  public MLDSAKeyPairGeneratorSpi(String paramString) {
    super(paramString);
  }
  
  protected MLDSAKeyPairGeneratorSpi(MLDSAParameterSpec paramMLDSAParameterSpec) {
    super(Strings.toUpperCase(paramMLDSAParameterSpec.getName()));
    if (this.param == null)
      this.param = new MLDSAKeyGenerationParameters(this.random, this.mldsaParameters); 
    this.engine.init((KeyGenerationParameters)this.param);
    this.initialised = true;
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom) {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    try {
      initialize(paramAlgorithmParameterSpec, (new BCJcaJceHelper()).createSecureRandom("DEFAULT"));
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new IllegalStateException("unable to find DEFAULT DRBG");
    } 
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    String str = getNameFromParams(paramAlgorithmParameterSpec);
    if (str != null) {
      MLDSAParameters mLDSAParameters = Utils.getParameters(str);
      if (mLDSAParameters == null)
        throw new InvalidAlgorithmParameterException("unknown parameter set name: " + str); 
      this.param = new MLDSAKeyGenerationParameters(paramSecureRandom, mLDSAParameters);
      if (this.mldsaParameters != null && !mLDSAParameters.getName().equals(this.mldsaParameters.getName()))
        throw new InvalidAlgorithmParameterException("key pair generator locked to " + MLDSAParameterSpec.fromName(this.mldsaParameters.getName()).getName()); 
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } else {
      throw new InvalidAlgorithmParameterException("invalid ParameterSpec: " + paramAlgorithmParameterSpec);
    } 
  }
  
  public KeyPair generateKeyPair() {
    if (!this.initialised) {
      if (getAlgorithm().startsWith("HASH")) {
        this.param = new MLDSAKeyGenerationParameters(this.random, MLDSAParameters.ml_dsa_87_with_sha512);
      } else {
        this.param = new MLDSAKeyGenerationParameters(this.random, MLDSAParameters.ml_dsa_87);
      } 
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } 
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.engine.generateKeyPair();
    MLDSAPublicKeyParameters mLDSAPublicKeyParameters = (MLDSAPublicKeyParameters)asymmetricCipherKeyPair.getPublic();
    MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters = (MLDSAPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate();
    return new KeyPair((PublicKey)new BCMLDSAPublicKey(mLDSAPublicKeyParameters), (PrivateKey)new BCMLDSAPrivateKey(mLDSAPrivateKeyParameters));
  }
  
  private static String getNameFromParams(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    if (paramAlgorithmParameterSpec instanceof MLDSAParameterSpec) {
      MLDSAParameterSpec mLDSAParameterSpec = (MLDSAParameterSpec)paramAlgorithmParameterSpec;
      return mLDSAParameterSpec.getName();
    } 
    return Strings.toUpperCase(SpecUtil.getNameFrom(paramAlgorithmParameterSpec));
  }
  
  public static class Hash extends MLDSAKeyPairGeneratorSpi {
    public Hash() throws NoSuchAlgorithmException {
      super("HASH-ML-DSA");
    }
  }
  
  public static class MLDSA44 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA44() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_44);
    }
  }
  
  public static class MLDSA44withSHA512 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA44withSHA512() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_44_with_sha512);
    }
  }
  
  public static class MLDSA65 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA65() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_65);
    }
  }
  
  public static class MLDSA65withSHA512 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA65withSHA512() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_65_with_sha512);
    }
  }
  
  public static class MLDSA87 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA87() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_87);
    }
  }
  
  public static class MLDSA87withSHA512 extends MLDSAKeyPairGeneratorSpi {
    public MLDSA87withSHA512() throws NoSuchAlgorithmException {
      super(MLDSAParameterSpec.ml_dsa_87_with_sha512);
    }
  }
  
  public static class Pure extends MLDSAKeyPairGeneratorSpi {
    public Pure() throws NoSuchAlgorithmException {
      super("ML-DSA");
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\MLDSAKeyPairGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */