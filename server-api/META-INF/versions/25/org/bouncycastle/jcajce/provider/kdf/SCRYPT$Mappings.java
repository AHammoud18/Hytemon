package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf;

import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public class Mappings extends AlgorithmProvider {
  public void configure(ConfigurableProvider paramConfigurableProvider) {
    paramConfigurableProvider.addAlgorithm("KDF.SCRYPT", "org.bouncycastle.jcajce.provider.kdf.scrypt.ScryptSpi$ScryptWithUTF8");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\SCRYPT$Mappings.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */