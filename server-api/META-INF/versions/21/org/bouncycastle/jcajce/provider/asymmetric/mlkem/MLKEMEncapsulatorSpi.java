package META-INF.versions.21.org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.KEM;
import javax.crypto.KEMSpi;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey;
import org.bouncycastle.jcajce.spec.KEMKDFSpec;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator;
import org.bouncycastle.pqc.jcajce.provider.util.KdfUtil;

public class MLKEMEncapsulatorSpi implements KEMSpi.EncapsulatorSpi {
  private final BCMLKEMPublicKey publicKey;
  
  private final KTSParameterSpec parameterSpec;
  
  private final MLKEMGenerator kemGen;
  
  public MLKEMEncapsulatorSpi(BCMLKEMPublicKey paramBCMLKEMPublicKey, KTSParameterSpec paramKTSParameterSpec, SecureRandom paramSecureRandom) {
    this.publicKey = paramBCMLKEMPublicKey;
    this.parameterSpec = paramKTSParameterSpec;
    this.kemGen = new MLKEMGenerator(paramSecureRandom);
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
      case "ML-KEM-512":
        return 768;
      case "ML-KEM-768":
        return 1088;
      case "ML-KEM-1024":
        return 1568;
    } 
    return -1;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\jcajce\provider\asymmetric\mlkem\MLKEMEncapsulatorSpi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */