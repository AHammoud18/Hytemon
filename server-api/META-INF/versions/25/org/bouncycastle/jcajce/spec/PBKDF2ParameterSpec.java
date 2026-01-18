package META-INF.versions.25.org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2ParameterSpec extends PBEKeySpec implements AlgorithmParameterSpec {
  public PBKDF2ParameterSpec(char[] paramArrayOfchar) {
    super(paramArrayOfchar);
  }
  
  public PBKDF2ParameterSpec(char[] paramArrayOfchar, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    super(paramArrayOfchar, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public PBKDF2ParameterSpec(char[] paramArrayOfchar, byte[] paramArrayOfbyte, int paramInt) {
    super(paramArrayOfchar, paramArrayOfbyte, paramInt);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\spec\PBKDF2ParameterSpec.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */