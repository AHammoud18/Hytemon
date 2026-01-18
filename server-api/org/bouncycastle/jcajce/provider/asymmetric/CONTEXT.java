package org.bouncycastle.jcajce.provider.asymmetric;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.spec.ContextParameterSpec;

public class CONTEXT {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.CONTEXT$";
  
  public static class ContextAlgorithmParametersSpi extends AlgorithmParametersSpi {
    private ContextParameterSpec contextParameterSpec;
    
    protected boolean isASN1FormatString(String param1String) {
      return (param1String == null || param1String.equals("ASN.1"));
    }
    
    protected AlgorithmParameterSpec engineGetParameterSpec(Class<ContextParameterSpec> param1Class) throws InvalidParameterSpecException {
      if (param1Class == null)
        throw new NullPointerException("argument to getParameterSpec must not be null"); 
      if (param1Class != ContextParameterSpec.class)
        throw new IllegalArgumentException("argument to getParameterSpec must be ContextParameterSpec.class"); 
      return (AlgorithmParameterSpec)this.contextParameterSpec;
    }
    
    protected void engineInit(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidParameterSpecException {
      if (!(param1AlgorithmParameterSpec instanceof ContextParameterSpec))
        throw new IllegalArgumentException("argument to engineInit must be a ContextParameterSpec"); 
      this.contextParameterSpec = (ContextParameterSpec)param1AlgorithmParameterSpec;
    }
    
    protected void engineInit(byte[] param1ArrayOfbyte) throws IOException {
      throw new IllegalStateException("not implemented");
    }
    
    protected void engineInit(byte[] param1ArrayOfbyte, String param1String) throws IOException {
      throw new IllegalStateException("not implemented");
    }
    
    protected byte[] engineGetEncoded() throws IOException {
      throw new IllegalStateException("not implemented");
    }
    
    protected byte[] engineGetEncoded(String param1String) throws IOException {
      throw new IllegalStateException("not implemented");
    }
    
    protected String engineToString() {
      return "ContextParameterSpec";
    }
  }
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("AlgorithmParameters.CONTEXT", "org.bouncycastle.jcajce.provider.asymmetric.CONTEXT$ContextAlgorithmParametersSpi");
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\CONTEXT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */