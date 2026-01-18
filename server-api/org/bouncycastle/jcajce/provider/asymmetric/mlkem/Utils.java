package org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;

class Utils {
  private static Map parameters = new HashMap<>();
  
  static MLKEMParameters getParameters(String paramString) {
    return (MLKEMParameters)parameters.get(paramString);
  }
  
  static {
    parameters.put(MLKEMParameterSpec.ml_kem_512.getName(), MLKEMParameters.ml_kem_512);
    parameters.put(MLKEMParameterSpec.ml_kem_768.getName(), MLKEMParameters.ml_kem_768);
    parameters.put(MLKEMParameterSpec.ml_kem_1024.getName(), MLKEMParameters.ml_kem_1024);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mlkem\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */