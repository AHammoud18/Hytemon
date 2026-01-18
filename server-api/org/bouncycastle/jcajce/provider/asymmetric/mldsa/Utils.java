package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;

class Utils {
  private static Map parameters = new HashMap<>();
  
  static MLDSAParameters getParameters(String paramString) {
    return (MLDSAParameters)parameters.get(paramString);
  }
  
  static {
    parameters.put(MLDSAParameterSpec.ml_dsa_44.getName(), MLDSAParameters.ml_dsa_44);
    parameters.put(MLDSAParameterSpec.ml_dsa_65.getName(), MLDSAParameters.ml_dsa_65);
    parameters.put(MLDSAParameterSpec.ml_dsa_87.getName(), MLDSAParameters.ml_dsa_87);
    parameters.put(MLDSAParameterSpec.ml_dsa_44_with_sha512.getName(), MLDSAParameters.ml_dsa_44_with_sha512);
    parameters.put(MLDSAParameterSpec.ml_dsa_65_with_sha512.getName(), MLDSAParameters.ml_dsa_65_with_sha512);
    parameters.put(MLDSAParameterSpec.ml_dsa_87_with_sha512.getName(), MLDSAParameters.ml_dsa_87_with_sha512);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */