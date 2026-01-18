package META-INF.versions.9.org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.util.Strings;

public class KyberParameterSpec implements AlgorithmParameterSpec {
  public static final org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec kyber512 = new org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec(MLKEMParameters.ml_kem_512);
  
  public static final org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec kyber768 = new org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec(MLKEMParameters.ml_kem_768);
  
  public static final org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec kyber1024 = new org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec(MLKEMParameters.ml_kem_1024);
  
  private static Map parameters = new HashMap<>();
  
  private final String name;
  
  private KyberParameterSpec(MLKEMParameters paramMLKEMParameters) {
    this.name = Strings.toUpperCase(paramMLKEMParameters.getName());
  }
  
  public String getName() {
    return this.name;
  }
  
  public static org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec fromName(String paramString) {
    return (org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec)parameters.get(Strings.toLowerCase(paramString));
  }
  
  static {
    parameters.put("kyber512", kyber512);
    parameters.put("kyber768", kyber768);
    parameters.put("kyber1024", kyber1024);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\spec\KyberParameterSpec.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */