package org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class SLHDSA {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Pure");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Pure");
      param1ConfigurableProvider.addAlgorithm("KeyFactory.HASH-SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Hash");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.HASH-SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Hash");
      SLHDSAKeyFactorySpi.Hash hash = new SLHDSAKeyFactorySpi.Hash();
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_128s", NISTObjectIdentifiers.id_slh_dsa_sha2_128s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_128f", NISTObjectIdentifiers.id_slh_dsa_sha2_128f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_192s", NISTObjectIdentifiers.id_slh_dsa_sha2_192s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_192f", NISTObjectIdentifiers.id_slh_dsa_sha2_192f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_256s", NISTObjectIdentifiers.id_slh_dsa_sha2_256s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Sha2_256f", NISTObjectIdentifiers.id_slh_dsa_sha2_256f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_128s", NISTObjectIdentifiers.id_slh_dsa_shake_128s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_128f", NISTObjectIdentifiers.id_slh_dsa_shake_128f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_192s", NISTObjectIdentifiers.id_slh_dsa_shake_192s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_192f", NISTObjectIdentifiers.id_slh_dsa_shake_192f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_256s", NISTObjectIdentifiers.id_slh_dsa_shake_256s, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$Shake_256f", NISTObjectIdentifiers.id_slh_dsa_shake_256f, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128S-WITH-SHA256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_128s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128s_with_sha256, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128F-WITH-SHA256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_128f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128f_with_sha256, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192S-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_192s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192s_with_sha512, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192F-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_192f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192f_with_sha512, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256S-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_256s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256s_with_sha512, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256F-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashSha2_256f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256f_with_sha512, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128S-WITH-SHAKE128", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_128s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_128s_with_shake128, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128F-WITH-SHAKE128", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_128f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_128f_with_shake128, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192S-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_192s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_192s_with_shake256, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192F-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_192f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_192f_with_shake256, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256S-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_256s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_256s_with_shake256, (AsymmetricKeyInfoConverter)hash);
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256F-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyFactorySpi$HashShake_256f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_256f_with_shake256, (AsymmetricKeyInfoConverter)hash);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_128s", NISTObjectIdentifiers.id_slh_dsa_sha2_128s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_128f", NISTObjectIdentifiers.id_slh_dsa_sha2_128f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_192s", NISTObjectIdentifiers.id_slh_dsa_sha2_192s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_192f", NISTObjectIdentifiers.id_slh_dsa_sha2_192f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_256s", NISTObjectIdentifiers.id_slh_dsa_sha2_256s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Sha2_256f", NISTObjectIdentifiers.id_slh_dsa_sha2_256f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_128s", NISTObjectIdentifiers.id_slh_dsa_shake_128s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_128f", NISTObjectIdentifiers.id_slh_dsa_shake_128f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_192s", NISTObjectIdentifiers.id_slh_dsa_shake_192s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_192f", NISTObjectIdentifiers.id_slh_dsa_shake_192f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256S", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_256s", NISTObjectIdentifiers.id_slh_dsa_shake_256s);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256F", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$Shake_256f", NISTObjectIdentifiers.id_slh_dsa_shake_256f);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128S-WITH-SHA256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_128s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128s_with_sha256);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-128F-WITH-SHA256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_128f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128f_with_sha256);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192S-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_192s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192s_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-192F-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_192f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192f_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256S-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_256s", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256s_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHA2-256F-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashSha2_256f", NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256f_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128S-WITH-SHAKE128", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_128s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_128s_with_shake128);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-128F-WITH-SHAKE128", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_128f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_128f_with_shake128);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192S-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_192s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_192s_with_shake256);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-192F-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_192f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_192f_with_shake256);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256S-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_256s", NISTObjectIdentifiers.id_hash_slh_dsa_shake_256s_with_shake256);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "SLH-DSA-SHAKE-256F-WITH-SHAKE256", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SLHDSAKeyPairGeneratorSpi$HashShake_256f", NISTObjectIdentifiers.id_hash_slh_dsa_shake_256f_with_shake256);
      String[] arrayOfString1 = { 
          "SLH-DSA-SHA2-128S", "SLH-DSA-SHA2-128F", "SLH-DSA-SHA2-192S", "SLH-DSA-SHA2-192F", "SLH-DSA-SHA2-256S", "SLH-DSA-SHA2-256F", "SLH-DSA-SHAKE-128S", "SLH-DSA-SHAKE-128F", "SLH-DSA-SHAKE-192S", "SLH-DSA-SHAKE-192F", 
          "SLH-DSA-SHAKE-256S", "SLH-DSA-SHAKE-256F" };
      String[] arrayOfString2 = { 
          "SLH-DSA-SHA2-128S-WITH-SHA256", "SLH-DSA-SHA2-128F-WITH-SHA256", "SLH-DSA-SHA2-192S-WITH-SHA512", "SLH-DSA-SHA2-192F-WITH-SHA512", "SLH-DSA-SHA2-256S-WITH-SHA512", "SLH-DSA-SHA2-256F-WITH-SHA512", "SLH-DSA-SHAKE-128S-WITH-SHAKE128", "SLH-DSA-SHAKE-128F-WITH-SHAKE128", "SLH-DSA-SHAKE-192S-WITH-SHAKE256", "SLH-DSA-SHAKE-192F-WITH-SHAKE256", 
          "SLH-DSA-SHAKE-256S-WITH-SHAKE256", "SLH-DSA-SHAKE-256F-WITH-SHAKE256" };
      addSignatureAlgorithm(param1ConfigurableProvider, "SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.SignatureSpi$Direct", (ASN1ObjectIdentifier)null);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SLHDSA", "SLH-DSA");
      addSignatureAlgorithm(param1ConfigurableProvider, "HASH-SLH-DSA", "org.bouncycastle.jcajce.provider.asymmetric.slhdsa.HashSignatureSpi$Direct", (ASN1ObjectIdentifier)null);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.HASHWITHSLHDSA", "HASH-SLH-DSA");
      byte b1;
      for (b1 = 0; b1 != arrayOfString1.length; b1++)
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + arrayOfString1[b1], "SLH-DSA"); 
      for (b1 = 0; b1 != arrayOfString2.length; b1++)
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + arrayOfString2[b1], "HASH-SLH-DSA"); 
      ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = { 
          NISTObjectIdentifiers.id_slh_dsa_sha2_128s, NISTObjectIdentifiers.id_slh_dsa_sha2_128f, NISTObjectIdentifiers.id_slh_dsa_sha2_192s, NISTObjectIdentifiers.id_slh_dsa_sha2_192f, NISTObjectIdentifiers.id_slh_dsa_sha2_256s, NISTObjectIdentifiers.id_slh_dsa_sha2_256f, NISTObjectIdentifiers.id_slh_dsa_shake_128s, NISTObjectIdentifiers.id_slh_dsa_shake_128f, NISTObjectIdentifiers.id_slh_dsa_shake_192s, NISTObjectIdentifiers.id_slh_dsa_shake_192f, 
          NISTObjectIdentifiers.id_slh_dsa_shake_256s, NISTObjectIdentifiers.id_slh_dsa_shake_256f };
      byte b2;
      for (b2 = 0; b2 != arrayOfASN1ObjectIdentifier.length; b2++) {
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + arrayOfASN1ObjectIdentifier[b2], "SLH-DSA");
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + arrayOfASN1ObjectIdentifier[b2], "SLH-DSA");
      } 
      arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[] { 
          NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128s_with_sha256, NISTObjectIdentifiers.id_hash_slh_dsa_sha2_128f_with_sha256, NISTObjectIdentifiers.id_hash_slh_dsa_shake_128s_with_shake128, NISTObjectIdentifiers.id_hash_slh_dsa_shake_128f_with_shake128, NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192s_with_sha512, NISTObjectIdentifiers.id_hash_slh_dsa_sha2_192f_with_sha512, NISTObjectIdentifiers.id_hash_slh_dsa_shake_192s_with_shake256, NISTObjectIdentifiers.id_hash_slh_dsa_shake_192f_with_shake256, NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256s_with_sha512, NISTObjectIdentifiers.id_hash_slh_dsa_sha2_256f_with_sha512, 
          NISTObjectIdentifiers.id_hash_slh_dsa_shake_256s_with_shake256, NISTObjectIdentifiers.id_hash_slh_dsa_shake_256f_with_shake256 };
      for (b2 = 0; b2 != arrayOfASN1ObjectIdentifier.length; b2++) {
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + arrayOfASN1ObjectIdentifier[b2], "HASH-SLH-DSA");
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + arrayOfASN1ObjectIdentifier[b2], "HASH-SLH-DSA");
      } 
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\SLHDSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */