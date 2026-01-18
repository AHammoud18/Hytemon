package META-INF.versions.9.org.bouncycastle.util;

public class Objects {
  public static boolean areEqual(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (null != paramObject1 && null != paramObject2 && paramObject1.equals(paramObject2)));
  }
  
  public static int hashCode(Object paramObject) {
    return (null == paramObject) ? 0 : paramObject.hashCode();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastl\\util\Objects.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */