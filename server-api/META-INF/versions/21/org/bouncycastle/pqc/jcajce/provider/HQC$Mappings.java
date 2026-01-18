package META-INF.versions.21.org.bouncycastle.pqc.jcajce.provider;

import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.pqc.jcajce.provider.HQC;
import org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi;

public class Mappings extends AsymmetricAlgorithmProvider {
  public void configure(ConfigurableProvider paramConfigurableProvider) {
    paramConfigurableProvider.addAlgorithm("KeyFactory.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.HQC", "HQC");
    addKeyFactoryAlgorithm(paramConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC128", BCObjectIdentifiers.hqc128, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC128());
    addKeyFactoryAlgorithm(paramConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC192", BCObjectIdentifiers.hqc192, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC192());
    addKeyFactoryAlgorithm(paramConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC256", BCObjectIdentifiers.hqc256, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC256());
    paramConfigurableProvider.addAlgorithm("KeyPairGenerator.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.HQC", "HQC");
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC128", BCObjectIdentifiers.hqc128);
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC192", BCObjectIdentifiers.hqc192);
    addKeyPairGeneratorAlgorithm(paramConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC256", BCObjectIdentifiers.hqc256);
    paramConfigurableProvider.addAlgorithm("KeyGenerator.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi");
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC128", BCObjectIdentifiers.hqc128);
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC192", BCObjectIdentifiers.hqc192);
    addKeyGeneratorAlgorithm(paramConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC256", BCObjectIdentifiers.hqc256);
    HQCKeyFactorySpi hQCKeyFactorySpi = new HQCKeyFactorySpi();
    paramConfigurableProvider.addAlgorithm("Cipher.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$Base");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.HQC", "HQC");
    addCipherAlgorithm(paramConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC128", BCObjectIdentifiers.hqc128);
    addCipherAlgorithm(paramConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC192", BCObjectIdentifiers.hqc192);
    addCipherAlgorithm(paramConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC256", BCObjectIdentifiers.hqc256);
    paramConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc128, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
    paramConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc192, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
    paramConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc256, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
    paramConfigurableProvider.addAlgorithm("KEM.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKEMSpi");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(BCObjectIdentifiers.hqc128), "HQC");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(BCObjectIdentifiers.hqc192), "HQC");
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KEM." + String.valueOf(BCObjectIdentifiers.hqc256), "HQC");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\21\org\bouncycastle\pqc\jcajce\provider\HQC$Mappings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */