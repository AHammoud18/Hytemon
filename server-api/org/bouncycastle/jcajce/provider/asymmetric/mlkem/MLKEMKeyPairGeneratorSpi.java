package org.bouncycastle.jcajce.provider.asymmetric.mlkem;

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
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyPairGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.util.Strings;

public class MLKEMKeyPairGeneratorSpi extends KeyPairGenerator {
  MLKEMKeyGenerationParameters param;
  
  MLKEMKeyPairGenerator engine = new MLKEMKeyPairGenerator();
  
  SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
  
  boolean initialised = false;
  
  private MLKEMParameters mlkemParameters;
  
  public MLKEMKeyPairGeneratorSpi() {
    super("ML-KEM");
  }
  
  protected MLKEMKeyPairGeneratorSpi(MLKEMParameterSpec paramMLKEMParameterSpec) {
    super(Strings.toUpperCase(paramMLKEMParameterSpec.getName()));
    this.mlkemParameters = Utils.getParameters(paramMLKEMParameterSpec.getName());
    if (this.param == null)
      this.param = new MLKEMKeyGenerationParameters(this.random, this.mlkemParameters); 
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
      MLKEMParameters mLKEMParameters = Utils.getParameters(str);
      if (mLKEMParameters == null)
        throw new InvalidAlgorithmParameterException("unknown parameter set name: " + str); 
      if (this.mlkemParameters != null && !mLKEMParameters.getName().equals(this.mlkemParameters.getName()))
        throw new InvalidAlgorithmParameterException("key pair generator locked to " + getAlgorithm()); 
      this.param = new MLKEMKeyGenerationParameters(paramSecureRandom, mLKEMParameters);
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } else {
      throw new InvalidAlgorithmParameterException("invalid ParameterSpec: " + paramAlgorithmParameterSpec);
    } 
  }
  
  public KeyPair generateKeyPair() {
    if (!this.initialised) {
      this.param = new MLKEMKeyGenerationParameters(this.random, MLKEMParameters.ml_kem_768);
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } 
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.engine.generateKeyPair();
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = (MLKEMPublicKeyParameters)asymmetricCipherKeyPair.getPublic();
    MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters = (MLKEMPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate();
    return new KeyPair((PublicKey)new BCMLKEMPublicKey(mLKEMPublicKeyParameters), (PrivateKey)new BCMLKEMPrivateKey(mLKEMPrivateKeyParameters));
  }
  
  private static String getNameFromParams(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    if (paramAlgorithmParameterSpec instanceof MLKEMParameterSpec) {
      MLKEMParameterSpec mLKEMParameterSpec = (MLKEMParameterSpec)paramAlgorithmParameterSpec;
      return mLKEMParameterSpec.getName();
    } 
    return Strings.toUpperCase(SpecUtil.getNameFrom(paramAlgorithmParameterSpec));
  }
  
  public static class MLKEM1024 extends MLKEMKeyPairGeneratorSpi {
    public MLKEM1024() {
      super(MLKEMParameterSpec.ml_kem_1024);
    }
  }
  
  public static class MLKEM512 extends MLKEMKeyPairGeneratorSpi {
    public MLKEM512() {
      super(MLKEMParameterSpec.ml_kem_512);
    }
  }
  
  public static class MLKEM768 extends MLKEMKeyPairGeneratorSpi {
    public MLKEM768() {
      super(MLKEMParameterSpec.ml_kem_768);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mlkem\MLKEMKeyPairGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */