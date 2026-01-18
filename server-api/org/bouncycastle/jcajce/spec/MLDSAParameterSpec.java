package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.util.Strings;

public class MLDSAParameterSpec implements AlgorithmParameterSpec {
  public static final MLDSAParameterSpec ml_dsa_44 = new MLDSAParameterSpec("ML-DSA-44");
  
  public static final MLDSAParameterSpec ml_dsa_65 = new MLDSAParameterSpec("ML-DSA-65");
  
  public static final MLDSAParameterSpec ml_dsa_87 = new MLDSAParameterSpec("ML-DSA-87");
  
  public static final MLDSAParameterSpec ml_dsa_44_with_sha512 = new MLDSAParameterSpec("ML-DSA-44-WITH-SHA512");
  
  public static final MLDSAParameterSpec ml_dsa_65_with_sha512 = new MLDSAParameterSpec("ML-DSA-65-WITH-SHA512");
  
  public static final MLDSAParameterSpec ml_dsa_87_with_sha512 = new MLDSAParameterSpec("ML-DSA-87-WITH-SHA512");
  
  private static Map parameters = new HashMap<>();
  
  private final String name;
  
  private MLDSAParameterSpec(String paramString) {
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static MLDSAParameterSpec fromName(String paramString) {
    if (paramString == null)
      throw new NullPointerException("name cannot be null"); 
    MLDSAParameterSpec mLDSAParameterSpec = (MLDSAParameterSpec)parameters.get(Strings.toLowerCase(paramString));
    if (mLDSAParameterSpec == null)
      throw new IllegalArgumentException("unknown parameter name: " + paramString); 
    return mLDSAParameterSpec;
  }
  
  static {
    parameters.put("ml-dsa-44", ml_dsa_44);
    parameters.put("ml-dsa-65", ml_dsa_65);
    parameters.put("ml-dsa-87", ml_dsa_87);
    parameters.put("ml-dsa-44-with-sha512", ml_dsa_44_with_sha512);
    parameters.put("ml-dsa-65-with-sha512", ml_dsa_65_with_sha512);
    parameters.put("ml-dsa-87-with-sha512", ml_dsa_87_with_sha512);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\MLDSAParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */