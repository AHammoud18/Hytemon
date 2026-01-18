package org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class MLKEM {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.mlkem.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.MLKEM", "ML-KEM");
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM512());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM768());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyFactorySpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024, (AsymmetricKeyInfoConverter)new MLKEMKeyFactorySpi.MLKEM1024());
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.MLKEM", "ML-KEM");
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyPairGeneratorSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
      param1ConfigurableProvider.addAlgorithm("KeyGenerator.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi");
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMKeyGeneratorSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
      MLKEMKeyFactorySpi mLKEMKeyFactorySpi = new MLKEMKeyFactorySpi();
      param1ConfigurableProvider.addAlgorithm("Cipher.ML-KEM", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$Base");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.MLKEM", "ML-KEM");
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM512", NISTObjectIdentifiers.id_alg_ml_kem_512);
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM768", NISTObjectIdentifiers.id_alg_ml_kem_768);
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.jcajce.provider.asymmetric.mlkem.MLKEMCipherSpi$MLKEM1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_512, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_768, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_alg_ml_kem_1024, (AsymmetricKeyInfoConverter)mLKEMKeyFactorySpi);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\MLKEM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */