package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public class Mappings extends AlgorithmProvider {
  public void configure(ConfigurableProvider paramConfigurableProvider) {
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withUTF8");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KDF.PBKDF2WITHHMACSHA1", "PBKDF2");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KDF.PBKDF2WITHHMACSHA1ANDUTF8", "PBKDF2");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KDF." + String.valueOf(PKCSObjectIdentifiers.id_PBKDF2), "PBKDF2");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHASCII", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2with8BIT");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KDF.PBKDF2WITH8BIT", "PBKDF2WITHASCII");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KDF.PBKDF2WITHHMACSHA1AND8BIT", "PBKDF2WITHASCII");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA224", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA224");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA256", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA256");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA384", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA384");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA512", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA512");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA512-224", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA512_224");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA512-256", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA512_256");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA3-224", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA3_224");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA3-256", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA3_256");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA3-384", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA3_384");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSHA3-512", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSHA3_512");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACGOST3411", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withGOST3411");
    paramConfigurableProvider.addAlgorithm("KDF.PBKDF2WITHHMACSM3", "org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi$PBKDF2withSM3");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\PBEPBKDF2$Mappings.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */