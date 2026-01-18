package META-INF.versions.21.org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.MLKEM;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class Mappings extends AsymmetricAlgorithmProvider {
  public void configure(ConfigurableProvider paramConfigurableProvider) {
    paramConfigurableProvider.addAlgorithm("KeyFactory.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.MLKEM", "ML-KEM");
    addKeyFactoryAlgorithm(paramConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM512());
    addKeyFactoryAlgorithm(paramConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM768());
    addKeyFactoryAlgorithm(paramConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM1024());
    paramConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.MLKEM", "ML-KEM");
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
    paramConfigurableProvider.addAlgorithm("KeyGenerator.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi");
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
    MLKEMKeyFactorySpi mLKEMKeyFactorySpi = new MLKEMKeyFactorySpi();
    paramConfigurableProvider.addAlgorithm("Cipher.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$Base");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.MLKEM", "ML-KEM");
    addCipherAlgorithm(paramConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
    addCipherAlgorithm(paramConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
    addCipherAlgorithm(paramConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
    paramConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_512, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
    paramConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_768, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
    paramConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_1024, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
    paramConfigurableProvider.addAlgorithm("KEM.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMSpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(NISTObjectIdentifiers.id_alg_ml_kem_512), "ML-KEM");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(NISTObjectIdentifiers.id_alg_ml_kem_768), "ML-KEM");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(NISTObjectIdentifiers.id_alg_ml_kem_1024), "ML-KEM");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\jcajce\provider\asymmetric\MLKEM$Mappings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */