package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.engines.TnepresEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.internal.asn1.gnu.GNUObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Serpent {
  public static class AlgParams extends IvAlgorithmParameters {
    protected String engineToString() {
      return "Serpent IV";
    }
  }
  
  public static class CBC extends BaseBlockCipher {
    public CBC() {
      super((BlockCipher)new CBCBlockCipher((BlockCipher)new SerpentEngine()), 128);
    }
  }
  
  public static class CBC128 extends BaseBlockCipher {
    public CBC128() {
      super(128, (BlockCipher)new CBCBlockCipher((BlockCipher)new SerpentEngine()), 128);
    }
  }
  
  public static class CBC192 extends BaseBlockCipher {
    public CBC192() {
      super(192, (BlockCipher)new CBCBlockCipher((BlockCipher)new SerpentEngine()), 128);
    }
  }
  
  public static class CBC256 extends BaseBlockCipher {
    public CBC256() {
      super(256, (BlockCipher)new CBCBlockCipher((BlockCipher)new SerpentEngine()), 128);
    }
  }
  
  public static class CFB extends BaseBlockCipher {
    public CFB() {
      super(new BufferedBlockCipher((BlockCipher)new CFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class CFB128 extends BaseBlockCipher {
    public CFB128() {
      super(128, new BufferedBlockCipher((BlockCipher)new CFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class CFB192 extends BaseBlockCipher {
    public CFB192() {
      super(192, new BufferedBlockCipher((BlockCipher)new CFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class CFB256 extends BaseBlockCipher {
    public CFB256() {
      super(256, new BufferedBlockCipher((BlockCipher)new CFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class ECB extends BaseBlockCipher {
    public ECB() {
      super(new BlockCipherProvider() {
            public BlockCipher get() {
              return (BlockCipher)new SerpentEngine();
            }
          });
    }
  }
  
  public static class ECB128 extends BaseBlockCipher {
    public ECB128() {
      super(128, new BlockCipherProvider() {
            public BlockCipher get() {
              return (BlockCipher)new SerpentEngine();
            }
          });
    }
  }
  
  public static class ECB192 extends BaseBlockCipher {
    public ECB192() {
      super(192, new BlockCipherProvider() {
            public BlockCipher get() {
              return (BlockCipher)new SerpentEngine();
            }
          });
    }
  }
  
  public static class ECB256 extends BaseBlockCipher {
    public ECB256() {
      super(256, new BlockCipherProvider() {
            public BlockCipher get() {
              return (BlockCipher)new SerpentEngine();
            }
          });
    }
  }
  
  public static class KeyGen extends BaseKeyGenerator {
    public KeyGen() {
      super("Serpent", 192, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings extends SymmetricAlgorithmProvider {
    private static final String PREFIX = Serpent.class.getName();
    
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("Cipher.Serpent", PREFIX + "$ECB");
      param1ConfigurableProvider.addAlgorithm("KeyGenerator.Serpent", PREFIX + "$KeyGen");
      param1ConfigurableProvider.addAlgorithm("AlgorithmParameters.Serpent", PREFIX + "$AlgParams");
      param1ConfigurableProvider.addAlgorithm("Cipher.Tnepres", PREFIX + "$TECB");
      param1ConfigurableProvider.addAlgorithm("KeyGenerator.Tnepres", PREFIX + "$TKeyGen");
      param1ConfigurableProvider.addAlgorithm("AlgorithmParameters.Tnepres", PREFIX + "$TAlgParams");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_ECB, PREFIX + "$ECB128");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_ECB, PREFIX + "$ECB192");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_ECB, PREFIX + "$ECB256");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CBC, PREFIX + "$CBC128");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CBC, PREFIX + "$CBC192");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CBC, PREFIX + "$CBC256");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CFB, PREFIX + "$CFB128");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CFB, PREFIX + "$CFB192");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CFB, PREFIX + "$CFB256");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_OFB, PREFIX + "$OFB128");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_OFB, PREFIX + "$OFB192");
      param1ConfigurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_OFB, PREFIX + "$OFB256");
      addGMacAlgorithm(param1ConfigurableProvider, "SERPENT", PREFIX + "$SerpentGMAC", PREFIX + "$KeyGen");
      addGMacAlgorithm(param1ConfigurableProvider, "TNEPRES", PREFIX + "$TSerpentGMAC", PREFIX + "$TKeyGen");
      addPoly1305Algorithm(param1ConfigurableProvider, "SERPENT", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
    }
  }
  
  public static class OFB extends BaseBlockCipher {
    public OFB() {
      super(new BufferedBlockCipher((BlockCipher)new OFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class OFB128 extends BaseBlockCipher {
    public OFB128() {
      super(128, new BufferedBlockCipher((BlockCipher)new OFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class OFB192 extends BaseBlockCipher {
    public OFB192() {
      super(192, new BufferedBlockCipher((BlockCipher)new OFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class OFB256 extends BaseBlockCipher {
    public OFB256() {
      super(256, new BufferedBlockCipher((BlockCipher)new OFBBlockCipher((BlockCipher)new SerpentEngine(), 128)), 128);
    }
  }
  
  public static class Poly1305 extends BaseMac {
    public Poly1305() {
      super((Mac)new org.bouncycastle.crypto.macs.Poly1305((BlockCipher)new SerpentEngine()));
    }
  }
  
  public static class Poly1305KeyGen extends BaseKeyGenerator {
    public Poly1305KeyGen() {
      super("Poly1305-Serpent", 256, (CipherKeyGenerator)new Poly1305KeyGenerator());
    }
  }
  
  public static class SerpentGMAC extends BaseMac {
    public SerpentGMAC() {
      super((Mac)new GMac(GCMBlockCipher.newInstance((BlockCipher)new SerpentEngine())));
    }
  }
  
  public static class TAlgParams extends IvAlgorithmParameters {
    protected String engineToString() {
      return "Tnepres IV";
    }
  }
  
  public static class TECB extends BaseBlockCipher {
    public TECB() {
      super(new BlockCipherProvider() {
            public BlockCipher get() {
              return (BlockCipher)new TnepresEngine();
            }
          });
    }
  }
  
  public static class TKeyGen extends BaseKeyGenerator {
    public TKeyGen() {
      super("Tnepres", 192, new CipherKeyGenerator());
    }
  }
  
  public static class TSerpentGMAC extends BaseMac {
    public TSerpentGMAC() {
      super((Mac)new GMac(GCMBlockCipher.newInstance((BlockCipher)new TnepresEngine())));
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\symmetric\Serpent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */