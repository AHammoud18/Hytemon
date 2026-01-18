package org.bouncycastle.pqc.jcajce.provider.kyber;

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
import org.bouncycastle.pqc.crypto.mlkem.MLKEMExtractor;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class KyberKeyGeneratorSpi extends KeyGeneratorSpi {
  private KEMGenerateSpec genSpec;
  
  private SecureRandom random;
  
  private KEMExtractSpec extSpec;
  
  private MLKEMParameters kyberParameters;
  
  public KyberKeyGeneratorSpi() {
    this(null);
  }
  
  protected KyberKeyGeneratorSpi(MLKEMParameters paramMLKEMParameters) {
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
        String str = Strings.toUpperCase(this.kyberParameters.getName());
        if (!str.equals(this.genSpec.getPublicKey().getAlgorithm()))
          throw new InvalidAlgorithmParameterException("key generator locked to " + str); 
      } 
    } else if (paramAlgorithmParameterSpec instanceof KEMExtractSpec) {
      this.genSpec = null;
      this.extSpec = (KEMExtractSpec)paramAlgorithmParameterSpec;
      if (this.kyberParameters != null) {
        String str = Strings.toUpperCase(this.kyberParameters.getName());
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
      BCKyberPublicKey bCKyberPublicKey = (BCKyberPublicKey)this.genSpec.getPublicKey();
      MLKEMGenerator mLKEMGenerator = new MLKEMGenerator(this.random);
      SecretWithEncapsulation secretWithEncapsulation = mLKEMGenerator.generateEncapsulated((AsymmetricKeyParameter)bCKyberPublicKey.getKeyParams());
      byte[] arrayOfByte4 = secretWithEncapsulation.getSecret();
      byte[] arrayOfByte5 = Arrays.copyOfRange(arrayOfByte4, 0, (this.genSpec.getKeySize() + 7) / 8);
      Arrays.clear(arrayOfByte4);
      SecretKeyWithEncapsulation secretKeyWithEncapsulation1 = new SecretKeyWithEncapsulation(new SecretKeySpec(arrayOfByte5, this.genSpec.getKeyAlgorithmName()), secretWithEncapsulation.getEncapsulation());
      try {
        secretWithEncapsulation.destroy();
      } catch (DestroyFailedException destroyFailedException) {
        throw new IllegalStateException("key cleanup failed");
      } 
      return (SecretKey)secretKeyWithEncapsulation1;
    } 
    BCKyberPrivateKey bCKyberPrivateKey = (BCKyberPrivateKey)this.extSpec.getPrivateKey();
    MLKEMExtractor mLKEMExtractor = new MLKEMExtractor(bCKyberPrivateKey.getKeyParams());
    byte[] arrayOfByte1 = this.extSpec.getEncapsulation();
    byte[] arrayOfByte2 = mLKEMExtractor.extractSecret(arrayOfByte1);
    byte[] arrayOfByte3 = Arrays.copyOfRange(arrayOfByte2, 0, (this.extSpec.getKeySize() + 7) / 8);
    Arrays.clear(arrayOfByte2);
    SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(arrayOfByte3, this.extSpec.getKeyAlgorithmName()), arrayOfByte1);
    Arrays.clear(arrayOfByte3);
    return (SecretKey)secretKeyWithEncapsulation;
  }
  
  public static class Kyber1024 extends KyberKeyGeneratorSpi {
    public Kyber1024() {
      super(MLKEMParameters.ml_kem_1024);
    }
  }
  
  public static class Kyber512 extends KyberKeyGeneratorSpi {
    public Kyber512() {
      super(MLKEMParameters.ml_kem_512);
    }
  }
  
  public static class Kyber768 extends KyberKeyGeneratorSpi {
    public Kyber768() {
      super(MLKEMParameters.ml_kem_768);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\kyber\KyberKeyGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */