package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.util.Arrays;

public class KTSParameterSpec extends KEMKDFSpec implements AlgorithmParameterSpec {
  private final AlgorithmParameterSpec parameterSpec;
  
  protected KTSParameterSpec(String paramString, int paramInt, AlgorithmParameterSpec paramAlgorithmParameterSpec, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    super(paramAlgorithmIdentifier, paramArrayOfbyte, paramString, paramInt);
    this.parameterSpec = paramAlgorithmParameterSpec;
  }
  
  public AlgorithmParameterSpec getParameterSpec() {
    return this.parameterSpec;
  }
  
  public static final class Builder {
    private final String algorithmName;
    
    private final int keySizeInBits;
    
    private AlgorithmParameterSpec parameterSpec;
    
    private AlgorithmIdentifier kdfAlgorithm;
    
    private byte[] otherInfo;
    
    public Builder(String param1String, int param1Int) {
      this(param1String, param1Int, null);
    }
    
    public Builder(String param1String, int param1Int, byte[] param1ArrayOfbyte) {
      this.algorithmName = param1String;
      this.keySizeInBits = param1Int;
      this.kdfAlgorithm = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, (ASN1Encodable)new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
      this.otherInfo = (param1ArrayOfbyte == null) ? new byte[0] : Arrays.clone(param1ArrayOfbyte);
    }
    
    public Builder withParameterSpec(AlgorithmParameterSpec param1AlgorithmParameterSpec) {
      this.parameterSpec = param1AlgorithmParameterSpec;
      return this;
    }
    
    public Builder withNoKdf() {
      this.kdfAlgorithm = null;
      return this;
    }
    
    public Builder withKdfAlgorithm(AlgorithmIdentifier param1AlgorithmIdentifier) {
      if (param1AlgorithmIdentifier == null)
        throw new NullPointerException("kdfAlgorithm cannot be null"); 
      this.kdfAlgorithm = param1AlgorithmIdentifier;
      return this;
    }
    
    public KTSParameterSpec build() {
      return new KTSParameterSpec(this.algorithmName, this.keySizeInBits, this.parameterSpec, this.kdfAlgorithm, this.otherInfo);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\KTSParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */