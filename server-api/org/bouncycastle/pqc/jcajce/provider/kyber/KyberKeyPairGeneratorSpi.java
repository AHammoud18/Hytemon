package org.bouncycastle.pqc.jcajce.provider.kyber;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyPairGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.util.Strings;

public class KyberKeyPairGeneratorSpi extends KeyPairGenerator {
  private static Map parameters = new HashMap<>();
  
  MLKEMKeyGenerationParameters param;
  
  MLKEMKeyPairGenerator engine = new MLKEMKeyPairGenerator();
  
  SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
  
  boolean initialised = false;
  
  private MLKEMParameters kyberParameters = null;
  
  public KyberKeyPairGeneratorSpi() {
    super("KYBER");
  }
  
  protected KyberKeyPairGeneratorSpi(MLKEMParameters paramMLKEMParameters) {
    super(Strings.toUpperCase(paramMLKEMParameters.getName()));
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom) {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    String str = getNameFromParams(paramAlgorithmParameterSpec);
    if (str != null && parameters.containsKey(str)) {
      MLKEMParameters mLKEMParameters = (MLKEMParameters)parameters.get(str);
      this.param = new MLKEMKeyGenerationParameters(paramSecureRandom, mLKEMParameters);
      if (this.kyberParameters != null && !mLKEMParameters.getName().equals(this.kyberParameters.getName()))
        throw new InvalidAlgorithmParameterException("key pair generator locked to " + Strings.toUpperCase(this.kyberParameters.getName())); 
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } else {
      throw new InvalidAlgorithmParameterException("invalid ParameterSpec: " + paramAlgorithmParameterSpec);
    } 
  }
  
  private static String getNameFromParams(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    if (paramAlgorithmParameterSpec instanceof KyberParameterSpec) {
      KyberParameterSpec kyberParameterSpec = (KyberParameterSpec)paramAlgorithmParameterSpec;
      return kyberParameterSpec.getName();
    } 
    return Strings.toLowerCase(SpecUtil.getNameFrom(paramAlgorithmParameterSpec));
  }
  
  public KeyPair generateKeyPair() {
    if (!this.initialised) {
      if (this.kyberParameters != null) {
        this.param = new MLKEMKeyGenerationParameters(this.random, this.kyberParameters);
      } else {
        this.param = new MLKEMKeyGenerationParameters(this.random, MLKEMParameters.ml_kem_1024);
      } 
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } 
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.engine.generateKeyPair();
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = (MLKEMPublicKeyParameters)asymmetricCipherKeyPair.getPublic();
    MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters = (MLKEMPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate();
    return new KeyPair((PublicKey)new BCKyberPublicKey(mLKEMPublicKeyParameters), (PrivateKey)new BCKyberPrivateKey(mLKEMPrivateKeyParameters));
  }
  
  static {
    parameters.put(KyberParameterSpec.kyber512.getName(), MLKEMParameters.ml_kem_512);
    parameters.put(KyberParameterSpec.kyber768.getName(), MLKEMParameters.ml_kem_768);
    parameters.put(KyberParameterSpec.kyber1024.getName(), MLKEMParameters.ml_kem_1024);
  }
  
  public static class Kyber1024 extends KyberKeyPairGeneratorSpi {
    public Kyber1024() {
      super(MLKEMParameters.ml_kem_1024);
    }
  }
  
  public static class Kyber512 extends KyberKeyPairGeneratorSpi {
    public Kyber512() {
      super(MLKEMParameters.ml_kem_512);
    }
  }
  
  public static class Kyber768 extends KyberKeyPairGeneratorSpi {
    public Kyber768() {
      super(MLKEMParameters.ml_kem_768);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\kyber\KyberKeyPairGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */