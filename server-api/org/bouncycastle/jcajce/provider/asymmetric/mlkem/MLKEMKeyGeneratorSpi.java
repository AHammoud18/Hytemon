package org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.bouncycastle.jcajce.spec.KEMKDFSpec;
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMExtractor;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.jcajce.provider.util.KdfUtil;
import org.bouncycastle.util.Arrays;

public class MLKEMKeyGeneratorSpi extends KeyGeneratorSpi {
  private KEMGenerateSpec genSpec;
  
  private SecureRandom random;
  
  private KEMExtractSpec extSpec;
  
  private MLKEMParameters kyberParameters;
  
  public MLKEMKeyGeneratorSpi() {
    this(null);
  }
  
  protected MLKEMKeyGeneratorSpi(MLKEMParameters paramMLKEMParameters) {
    this.kyberParameters = paramMLKEMParameters;
  }
  
  protected void engineInit(SecureRandom paramSecureRandom) {
    throw new UnsupportedOperationException("Operation not supported");
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    this.random = paramSecureRandom;
    if (paramAlgorithmParameterSpec instanceof KEMGenerateSpec) {
      this.genSpec = (KEMGenerateSpec)paramAlgorithmParameterSpec;
      this.extSpec = null;
      if (this.kyberParameters != null) {
        String str = MLKEMParameterSpec.fromName(this.kyberParameters.getName()).getName();
        if (!str.equals(this.genSpec.getPublicKey().getAlgorithm()))
          throw new InvalidAlgorithmParameterException("key generator locked to " + str); 
      } 
    } else if (paramAlgorithmParameterSpec instanceof KEMExtractSpec) {
      this.genSpec = null;
      this.extSpec = (KEMExtractSpec)paramAlgorithmParameterSpec;
      if (this.kyberParameters != null) {
        String str = MLKEMParameterSpec.fromName(this.kyberParameters.getName()).getName();
        if (!str.equals(this.extSpec.getPrivateKey().getAlgorithm()))
          throw new InvalidAlgorithmParameterException("key generator locked to " + str); 
      } 
    } else {
      throw new InvalidAlgorithmParameterException("unknown spec");
    } 
  }
  
  protected void engineInit(int paramInt, SecureRandom paramSecureRandom) {
    throw new UnsupportedOperationException("Operation not supported");
  }
  
  protected SecretKey engineGenerateKey() {
    if (this.genSpec != null) {
      BCMLKEMPublicKey bCMLKEMPublicKey = (BCMLKEMPublicKey)this.genSpec.getPublicKey();
      MLKEMGenerator mLKEMGenerator = new MLKEMGenerator(this.random);
      SecretWithEncapsulation secretWithEncapsulation = mLKEMGenerator.generateEncapsulated((AsymmetricKeyParameter)bCMLKEMPublicKey.getKeyParams());
      byte[] arrayOfByte4 = secretWithEncapsulation.getSecret();
      byte[] arrayOfByte5 = KdfUtil.makeKeyBytes((KEMKDFSpec)this.genSpec, arrayOfByte4);
      Arrays.clear(arrayOfByte4);
      SecretKeyWithEncapsulation secretKeyWithEncapsulation1 = new SecretKeyWithEncapsulation(new SecretKeySpec(arrayOfByte5, this.genSpec.getKeyAlgorithmName()), secretWithEncapsulation.getEncapsulation());
      try {
        secretWithEncapsulation.destroy();
      } catch (DestroyFailedException destroyFailedException) {
        throw new IllegalStateException("key cleanup failed");
      } 
      return (SecretKey)secretKeyWithEncapsulation1;
    } 
    BCMLKEMPrivateKey bCMLKEMPrivateKey = (BCMLKEMPrivateKey)this.extSpec.getPrivateKey();
    MLKEMExtractor mLKEMExtractor = new MLKEMExtractor(bCMLKEMPrivateKey.getKeyParams());
    byte[] arrayOfByte1 = this.extSpec.getEncapsulation();
    byte[] arrayOfByte2 = mLKEMExtractor.extractSecret(arrayOfByte1);
    byte[] arrayOfByte3 = KdfUtil.makeKeyBytes((KEMKDFSpec)this.extSpec, arrayOfByte2);
    Arrays.clear(arrayOfByte2);
    SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(arrayOfByte3, this.extSpec.getKeyAlgorithmName()), arrayOfByte1);
    Arrays.clear(arrayOfByte3);
    return (SecretKey)secretKeyWithEncapsulation;
  }
  
  public static class MLKEM1024 extends MLKEMKeyGeneratorSpi {
    public MLKEM1024() {
      super(MLKEMParameters.ml_kem_1024);
    }
  }
  
  public static class MLKEM512 extends MLKEMKeyGeneratorSpi {
    public MLKEM512() {
      super(MLKEMParameters.ml_kem_512);
    }
  }
  
  public static class MLKEM768 extends MLKEMKeyGeneratorSpi {
    public MLKEM768() {
      super(MLKEMParameters.ml_kem_768);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mlkem\MLKEMKeyGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */