package META-INF.versions.9.org.bouncycastle.jcajce.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.spec.AlgorithmParameterSpec;

public class SpecUtil {
  private static Class[] NO_PARAMS = new Class[0];
  
  private static Object[] NO_ARGS = new Object[0];
  
  public static String getNameFrom(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    return AccessController.<String>doPrivileged((PrivilegedAction<String>)new Object(paramAlgorithmParameterSpec));
  }
  
  public static byte[] getContextFrom(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    return AccessController.<byte[]>doPrivileged((PrivilegedAction<byte>)new Object(paramAlgorithmParameterSpec));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajc\\util\SpecUtil.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */