package META-INF.versions.9.org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.util.Strings;

public class MLKEMParameterSpec implements AlgorithmParameterSpec {
  public static final org.bouncycastle.jcajce.spec.MLKEMParameterSpec ml_kem_512 = new org.bouncycastle.jcajce.spec.MLKEMParameterSpec("ML-KEM-512");
  
  public static final org.bouncycastle.jcajce.spec.MLKEMParameterSpec ml_kem_768 = new org.bouncycastle.jcajce.spec.MLKEMParameterSpec("ML-KEM-768");
  
  public static final org.bouncycastle.jcajce.spec.MLKEMParameterSpec ml_kem_1024 = new org.bouncycastle.jcajce.spec.MLKEMParameterSpec("ML-KEM-1024");
  
  private static Map parameters = new HashMap<>();
  
  private final String name;
  
  private MLKEMParameterSpec(String paramString) {
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static org.bouncycastle.jcajce.spec.MLKEMParameterSpec fromName(String paramString) {
    if (paramString == null)
      throw new NullPointerException("name cannot be null"); 
    org.bouncycastle.jcajce.spec.MLKEMParameterSpec mLKEMParameterSpec = (org.bouncycastle.jcajce.spec.MLKEMParameterSpec)parameters.get(Strings.toLowerCase(paramString));
    if (mLKEMParameterSpec == null)
      throw new IllegalArgumentException("unknown parameter name: " + paramString); 
    return mLKEMParameterSpec;
  }
  
  static {
    parameters.put("ml-kem-512", ml_kem_512);
    parameters.put("ml-kem-768", ml_kem_768);
    parameters.put("ml-kem-1024", ml_kem_1024);
    parameters.put("kyber512", ml_kem_512);
    parameters.put("kyber768", ml_kem_768);
    parameters.put("kyber1024", ml_kem_1024);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\spec\MLKEMParameterSpec.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */