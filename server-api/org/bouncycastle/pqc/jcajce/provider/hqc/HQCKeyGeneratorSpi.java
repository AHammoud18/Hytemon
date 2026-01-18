package org.bouncycastle.pqc.jcajce.provider.hqc;

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
import org.bouncycastle.pqc.crypto.hqc.HQCKEMExtractor;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMGenerator;
import org.bouncycastle.pqc.crypto.hqc.HQCParameters;
import org.bouncycastle.pqc.jcajce.spec.HQCParameterSpec;
import org.bouncycastle.util.Arrays;

public class HQCKeyGeneratorSpi extends KeyGeneratorSpi {
  private KEMGenerateSpec genSpec;
  
  private SecureRandom random;
  
  private KEMExtractSpec extSpec;
  
  private HQCParameters hqcParameters;
  
  public HQCKeyGeneratorSpi() {
    this(null);
  }
  
  public HQCKeyGeneratorSpi(HQCParameters paramHQCParameters) {
    this.hqcParameters = paramHQCParameters;
  }
  
  protected void engineInit(SecureRandom paramSecureRandom) {
    throw new UnsupportedOperationException("Operation not supported");
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    this.random = paramSecureRandom;
    if (paramAlgorithmParameterSpec instanceof KEMGenerateSpec) {
      this.genSpec = (KEMGenerateSpec)paramAlgorithmParameterSpec;
      this.extSpec = null;
      if (this.hqcParameters != null) {
        String str = HQCParameterSpec.fromName(this.hqcParameters.getName()).getName();
        if (!str.equals(this.genSpec.getPublicKey().getAlgorithm()))
          throw new InvalidAlgorithmParameterException("key generator locked to " + str); 
      } 
    } else if (paramAlgorithmParameterSpec instanceof KEMExtractSpec) {
      this.genSpec = null;
      this.extSpec = (KEMExtractSpec)paramAlgorithmParameterSpec;
      if (this.hqcParameters != null) {
        String str = HQCParameterSpec.fromName(this.hqcParameters.getName()).getName();
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
      BCHQCPublicKey bCHQCPublicKey = (BCHQCPublicKey)this.genSpec.getPublicKey();
      HQCKEMGenerator hQCKEMGenerator = new HQCKEMGenerator(this.random);
      SecretWithEncapsulation secretWithEncapsulation = hQCKEMGenerator.generateEncapsulated((AsymmetricKeyParameter)bCHQCPublicKey.getKeyParams());
      SecretKeyWithEncapsulation secretKeyWithEncapsulation1 = new SecretKeyWithEncapsulation(new SecretKeySpec(secretWithEncapsulation.getSecret(), this.genSpec.getKeyAlgorithmName()), secretWithEncapsulation.getEncapsulation());
      try {
        secretWithEncapsulation.destroy();
      } catch (DestroyFailedException destroyFailedException) {
        throw new IllegalStateException("key cleanup failed");
      } 
      return (SecretKey)secretKeyWithEncapsulation1;
    } 
    BCHQCPrivateKey bCHQCPrivateKey = (BCHQCPrivateKey)this.extSpec.getPrivateKey();
    HQCKEMExtractor hQCKEMExtractor = new HQCKEMExtractor(bCHQCPrivateKey.getKeyParams());
    byte[] arrayOfByte1 = this.extSpec.getEncapsulation();
    byte[] arrayOfByte2 = hQCKEMExtractor.extractSecret(arrayOfByte1);
    SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(arrayOfByte2, this.extSpec.getKeyAlgorithmName()), arrayOfByte1);
    Arrays.clear(arrayOfByte2);
    return (SecretKey)secretKeyWithEncapsulation;
  }
  
  public static class HQC128 extends HQCKeyGeneratorSpi {
    public HQC128() {
      super(HQCParameters.hqc128);
    }
  }
  
  public static class HQC192 extends HQCKeyGeneratorSpi {
    public HQC192() {
      super(HQCParameters.hqc192);
    }
  }
  
  public static class HQC256 extends HQCKeyGeneratorSpi {
    public HQC256() {
      super(HQCParameters.hqc256);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\hqc\HQCKeyGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */