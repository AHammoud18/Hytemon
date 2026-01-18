package org.bouncycastle.pqc.jcajce.provider.mayo;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.pqc.crypto.mayo.MayoKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoKeyPairGenerator;
import org.bouncycastle.pqc.crypto.mayo.MayoParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.MayoParameterSpec;
import org.bouncycastle.util.Strings;

public class MayoKeyPairGeneratorSpi extends KeyPairGenerator {
  private static Map parameters = new HashMap<>();
  
  MayoKeyGenerationParameters param;
  
  private MayoParameters mayoParameters;
  
  MayoKeyPairGenerator engine = new MayoKeyPairGenerator();
  
  SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
  
  boolean initialised = false;
  
  public MayoKeyPairGeneratorSpi() {
    super("Mayo");
  }
  
  protected MayoKeyPairGeneratorSpi(MayoParameters paramMayoParameters) {
    super(paramMayoParameters.getName());
    this.mayoParameters = paramMayoParameters;
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom) {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    String str = getNameFromParams(paramAlgorithmParameterSpec);
    if (str != null) {
      this.param = new MayoKeyGenerationParameters(paramSecureRandom, (MayoParameters)parameters.get(str));
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } else {
      throw new InvalidAlgorithmParameterException("invalid ParameterSpec: " + paramAlgorithmParameterSpec);
    } 
  }
  
  private static String getNameFromParams(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    if (paramAlgorithmParameterSpec instanceof MayoParameterSpec) {
      MayoParameterSpec mayoParameterSpec = (MayoParameterSpec)paramAlgorithmParameterSpec;
      return mayoParameterSpec.getName();
    } 
    return Strings.toLowerCase(SpecUtil.getNameFrom(paramAlgorithmParameterSpec));
  }
  
  public KeyPair generateKeyPair() {
    if (!this.initialised) {
      this.param = new MayoKeyGenerationParameters(this.random, MayoParameters.mayo1);
      this.engine.init((KeyGenerationParameters)this.param);
      this.initialised = true;
    } 
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.engine.generateKeyPair();
    MayoPublicKeyParameters mayoPublicKeyParameters = (MayoPublicKeyParameters)asymmetricCipherKeyPair.getPublic();
    MayoPrivateKeyParameters mayoPrivateKeyParameters = (MayoPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate();
    return new KeyPair(new BCMayoPublicKey(mayoPublicKeyParameters), new BCMayoPrivateKey(mayoPrivateKeyParameters));
  }
  
  static {
    parameters.put("MAYO_1", MayoParameters.mayo1);
    parameters.put("MAYO_2", MayoParameters.mayo2);
    parameters.put("MAYO_3", MayoParameters.mayo3);
    parameters.put("MAYO_5", MayoParameters.mayo5);
    parameters.put(MayoParameterSpec.mayo1.getName(), MayoParameters.mayo1);
    parameters.put(MayoParameterSpec.mayo2.getName(), MayoParameters.mayo2);
    parameters.put(MayoParameterSpec.mayo3.getName(), MayoParameters.mayo3);
    parameters.put(MayoParameterSpec.mayo5.getName(), MayoParameters.mayo5);
  }
  
  public static class Mayo1 extends MayoKeyPairGeneratorSpi {
    public Mayo1() {
      super(MayoParameters.mayo1);
    }
  }
  
  public static class Mayo2 extends MayoKeyPairGeneratorSpi {
    public Mayo2() {
      super(MayoParameters.mayo2);
    }
  }
  
  public static class Mayo3 extends MayoKeyPairGeneratorSpi {
    public Mayo3() {
      super(MayoParameters.mayo3);
    }
  }
  
  public static class Mayo5 extends MayoKeyPairGeneratorSpi {
    public Mayo5() {
      super(MayoParameters.mayo5);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\mayo\MayoKeyPairGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */