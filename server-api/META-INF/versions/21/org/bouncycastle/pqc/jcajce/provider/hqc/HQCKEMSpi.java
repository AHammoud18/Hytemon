package META-INF.versions.21.org.bouncycastle.pqc.jcajce.provider.hqc;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KEMSpi;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPrivateKey;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPublicKey;
import org.bouncycastle.pqc.jcajce.provider.hqc.HQCDecapsulatorSpi;
import org.bouncycastle.pqc.jcajce.provider.hqc.HQCEncapsulatorSpi;

public class HQCKEMSpi implements KEMSpi {
  public KEMSpi.EncapsulatorSpi engineNewEncapsulator(PublicKey paramPublicKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException, InvalidKeyException {
    KTSParameterSpec kTSParameterSpec;
    if (!(paramPublicKey instanceof BCHQCPublicKey))
      throw new InvalidKeyException("unsupported key"); 
    if (paramAlgorithmParameterSpec == null)
      kTSParameterSpec = (new KTSParameterSpec.Builder("Generic", 256)).withNoKdf().build(); 
    if (!(kTSParameterSpec instanceof KTSParameterSpec))
      throw new InvalidAlgorithmParameterException("HQC can only accept KTSParameterSpec"); 
    if (paramSecureRandom == null)
      paramSecureRandom = new SecureRandom(); 
    return (KEMSpi.EncapsulatorSpi)new HQCEncapsulatorSpi((BCHQCPublicKey)paramPublicKey, kTSParameterSpec, paramSecureRandom);
  }
  
  public KEMSpi.DecapsulatorSpi engineNewDecapsulator(PrivateKey paramPrivateKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException, InvalidKeyException {
    KTSParameterSpec kTSParameterSpec;
    if (!(paramPrivateKey instanceof BCHQCPrivateKey))
      throw new InvalidKeyException("unsupported key"); 
    if (paramAlgorithmParameterSpec == null)
      kTSParameterSpec = (new KTSParameterSpec.Builder("Generic", 256)).withNoKdf().build(); 
    if (!(kTSParameterSpec instanceof KTSParameterSpec))
      throw new InvalidAlgorithmParameterException("HQC can only accept KTSParameterSpec"); 
    return (KEMSpi.DecapsulatorSpi)new HQCDecapsulatorSpi((BCHQCPrivateKey)paramPrivateKey, kTSParameterSpec);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\pqc\jcajce\provider\hqc\HQCKEMSpi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */