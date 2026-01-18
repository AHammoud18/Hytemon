package org.bouncycastle.jcajce.spec;

import java.security.PrivateKey;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.util.Arrays;

public class KEMExtractSpec extends KEMKDFSpec implements AlgorithmParameterSpec {
  private static final byte[] EMPTY_OTHER_INFO = new byte[0];
  
  private static AlgorithmIdentifier DefKdf = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, (ASN1Encodable)new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
  
  private final PrivateKey privateKey;
  
  private final byte[] encapsulation;
  
  private KEMExtractSpec(PrivateKey paramPrivateKey, byte[] paramArrayOfbyte1, String paramString, int paramInt, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte2) {
    super(paramAlgorithmIdentifier, paramArrayOfbyte2, paramString, paramInt);
    this.privateKey = paramPrivateKey;
    this.encapsulation = Arrays.clone(paramArrayOfbyte1);
  }
  
  public KEMExtractSpec(PrivateKey paramPrivateKey, byte[] paramArrayOfbyte, String paramString) {
    this(paramPrivateKey, paramArrayOfbyte, paramString, 256);
  }
  
  public KEMExtractSpec(PrivateKey paramPrivateKey, byte[] paramArrayOfbyte, String paramString, int paramInt) {
    this(paramPrivateKey, paramArrayOfbyte, paramString, paramInt, DefKdf, EMPTY_OTHER_INFO);
  }
  
  public byte[] getEncapsulation() {
    return Arrays.clone(this.encapsulation);
  }
  
  public PrivateKey getPrivateKey() {
    return this.privateKey;
  }
  
  public static final class Builder {
    private final PrivateKey privateKey;
    
    private final byte[] encapsulation;
    
    private final String algorithmName;
    
    private final int keySizeInBits;
    
    private AlgorithmIdentifier kdfAlgorithm;
    
    private byte[] otherInfo;
    
    public Builder(PrivateKey param1PrivateKey, byte[] param1ArrayOfbyte, String param1String, int param1Int) {
      this.privateKey = param1PrivateKey;
      this.encapsulation = Arrays.clone(param1ArrayOfbyte);
      this.algorithmName = param1String;
      this.keySizeInBits = param1Int;
      this.kdfAlgorithm = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, (ASN1Encodable)new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
      this.otherInfo = KEMExtractSpec.EMPTY_OTHER_INFO;
    }
    
    public Builder withNoKdf() {
      this.kdfAlgorithm = null;
      return this;
    }
    
    public Builder withKdfAlgorithm(AlgorithmIdentifier param1AlgorithmIdentifier) {
      this.kdfAlgorithm = param1AlgorithmIdentifier;
      return this;
    }
    
    public Builder withOtherInfo(byte[] param1ArrayOfbyte) {
      this.otherInfo = (param1ArrayOfbyte == null) ? KEMExtractSpec.EMPTY_OTHER_INFO : Arrays.clone(param1ArrayOfbyte);
      return this;
    }
    
    public KEMExtractSpec build() {
      return new KEMExtractSpec(this.privateKey, this.encapsulation, this.algorithmName, this.keySizeInBits, this.kdfAlgorithm, this.otherInfo);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\KEMExtractSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */