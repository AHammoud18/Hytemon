package META-INF.versions.21.org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KEMSpi;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMDecapsulatorSpi;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMEncapsulatorSpi;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;

public class MLKEMSpi implements KEMSpi {
  public KEMSpi.EncapsulatorSpi engineNewEncapsulator(PublicKey paramPublicKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException, InvalidKeyException {
    KTSParameterSpec kTSParameterSpec;
    if (!(paramPublicKey instanceof BCMLKEMPublicKey))
      throw new InvalidKeyException("unsupported key"); 
    if (paramAlgorithmParameterSpec == null)
      kTSParameterSpec = (new KTSParameterSpec.Builder("Generic", 256)).withNoKdf().build(); 
    if (!(kTSParameterSpec instanceof KTSParameterSpec))
      throw new InvalidAlgorithmParameterException("MLKEM can only accept KTSParameterSpec"); 
    if (paramSecureRandom == null)
      paramSecureRandom = new SecureRandom(); 
    return (KEMSpi.EncapsulatorSpi)new MLKEMEncapsulatorSpi((BCMLKEMPublicKey)paramPublicKey, kTSParameterSpec, paramSecureRandom);
  }
  
  public KEMSpi.DecapsulatorSpi engineNewDecapsulator(PrivateKey paramPrivateKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException, InvalidKeyException {
    KTSParameterSpec kTSParameterSpec;
    if (!(paramPrivateKey instanceof BCMLKEMPrivateKey))
      throw new InvalidKeyException("unsupported key"); 
    if (paramAlgorithmParameterSpec == null)
      kTSParameterSpec = (new KTSParameterSpec.Builder("Generic", 256)).withNoKdf().build(); 
    if (!(kTSParameterSpec instanceof KTSParameterSpec))
      throw new InvalidAlgorithmParameterException("MLKEM can only accept KTSParameterSpec"); 
    return (KEMSpi.DecapsulatorSpi)new MLKEMDecapsulatorSpi((BCMLKEMPrivateKey)paramPrivateKey, kTSParameterSpec);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\jcajce\provider\asymmetric\mlkem\MLKEMSpi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */