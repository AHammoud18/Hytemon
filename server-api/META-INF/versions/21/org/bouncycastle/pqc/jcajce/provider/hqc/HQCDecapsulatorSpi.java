package META-INF.versions.21.org.bouncycastle.pqc.jcajce.provider.hqc;

import java.util.Arrays;
import java.util.Objects;
import javax.crypto.DecapsulateException;
import javax.crypto.KEMSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jcajce.spec.KEMKDFSpec;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMExtractor;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPrivateKey;
import org.bouncycastle.pqc.jcajce.provider.util.KdfUtil;

public class HQCDecapsulatorSpi implements KEMSpi.DecapsulatorSpi {
  BCHQCPrivateKey privateKey;
  
  KTSParameterSpec parameterSpec;
  
  HQCKEMExtractor kemExt;
  
  public HQCDecapsulatorSpi(BCHQCPrivateKey paramBCHQCPrivateKey, KTSParameterSpec paramKTSParameterSpec) {
    this.privateKey = paramBCHQCPrivateKey;
    this.parameterSpec = paramKTSParameterSpec;
    this.kemExt = new HQCKEMExtractor(paramBCHQCPrivateKey.getKeyParams());
  }
  
  public SecretKey engineDecapsulate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws DecapsulateException {
    Objects.checkFromToIndex(paramInt1, paramInt2, engineSecretSize());
    Objects.requireNonNull(paramString, "null algorithm");
    Objects.requireNonNull(paramArrayOfbyte, "null encapsulation");
    if (paramArrayOfbyte.length != engineEncapsulationSize())
      throw new DecapsulateException("incorrect encapsulation size"); 
    if (!this.parameterSpec.getKeyAlgorithmName().equals("Generic") && paramString.equals("Generic"))
      paramString = this.parameterSpec.getKeyAlgorithmName(); 
    if (!this.parameterSpec.getKeyAlgorithmName().equals("Generic") && !this.parameterSpec.getKeyAlgorithmName().equals(paramString))
      throw new UnsupportedOperationException(this.parameterSpec.getKeyAlgorithmName() + " does not match " + this.parameterSpec.getKeyAlgorithmName()); 
    boolean bool = (this.parameterSpec.getKdfAlgorithm() != null) ? true : false;
    byte[] arrayOfByte1 = this.kemExt.extractSecret(paramArrayOfbyte);
    byte[] arrayOfByte2 = Arrays.copyOfRange(KdfUtil.makeKeyBytes((KEMKDFSpec)this.parameterSpec, arrayOfByte1), paramInt1, paramInt2);
    return new SecretKeySpec(arrayOfByte2, paramString);
  }
  
  public int engineSecretSize() {
    return this.parameterSpec.getKeySize() / 8;
  }
  
  public int engineEncapsulationSize() {
    return this.kemExt.getEncapsulationLength();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\pqc\jcajce\provider\hqc\HQCDecapsulatorSpi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */