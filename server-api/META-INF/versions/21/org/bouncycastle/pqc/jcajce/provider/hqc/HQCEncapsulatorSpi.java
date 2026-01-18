package META-INF.versions.21.org.bouncycastle.pqc.jcajce.provider.hqc;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.KEM;
import javax.crypto.KEMSpi;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.spec.KEMKDFSpec;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMGenerator;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPublicKey;
import org.bouncycastle.pqc.jcajce.provider.util.KdfUtil;

public class HQCEncapsulatorSpi implements KEMSpi.EncapsulatorSpi {
  private final BCHQCPublicKey publicKey;
  
  private final KTSParameterSpec parameterSpec;
  
  private final HQCKEMGenerator kemGen;
  
  public HQCEncapsulatorSpi(BCHQCPublicKey paramBCHQCPublicKey, KTSParameterSpec paramKTSParameterSpec, SecureRandom paramSecureRandom) {
    this.publicKey = paramBCHQCPublicKey;
    this.parameterSpec = paramKTSParameterSpec;
    this.kemGen = new HQCKEMGenerator(paramSecureRandom);
  }
  
  public KEM.Encapsulated engineEncapsulate(int paramInt1, int paramInt2, String paramString) {
    Objects.checkFromToIndex(paramInt1, paramInt2, engineSecretSize());
    Objects.requireNonNull(paramString, "null algorithm");
    if (!this.parameterSpec.getKeyAlgorithmName().equals("Generic") && paramString.equals("Generic"))
      paramString = this.parameterSpec.getKeyAlgorithmName(); 
    if (!this.parameterSpec.getKeyAlgorithmName().equals("Generic") && !this.parameterSpec.getKeyAlgorithmName().equals(paramString))
      throw new UnsupportedOperationException(this.parameterSpec.getKeyAlgorithmName() + " does not match " + this.parameterSpec.getKeyAlgorithmName()); 
    boolean bool = (this.parameterSpec.getKdfAlgorithm() != null) ? true : false;
    SecretWithEncapsulation secretWithEncapsulation = this.kemGen.generateEncapsulated((AsymmetricKeyParameter)this.publicKey.getKeyParams());
    byte[] arrayOfByte1 = secretWithEncapsulation.getEncapsulation();
    byte[] arrayOfByte2 = secretWithEncapsulation.getSecret();
    byte[] arrayOfByte3 = Arrays.copyOfRange(KdfUtil.makeKeyBytes((KEMKDFSpec)this.parameterSpec, arrayOfByte2), paramInt1, paramInt2);
    return new KEM.Encapsulated(new SecretKeySpec(arrayOfByte3, paramString), arrayOfByte1, null);
  }
  
  public int engineSecretSize() {
    return this.parameterSpec.getKeySize() / 8;
  }
  
  public int engineEncapsulationSize() {
    switch (this.publicKey.getKeyParams().getParameters().getName()) {
      case "HQC-128":
        return 128;
      case "HQC-192":
        return 192;
      case "HQC-256":
        return 256;
    } 
    return -1;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\pqc\jcajce\provider\hqc\HQCEncapsulatorSpi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */