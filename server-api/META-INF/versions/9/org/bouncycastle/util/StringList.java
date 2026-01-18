package META-INF.versions.9.org.bouncycastle.util;

import org.bouncycastle.util.Iterable;

public interface StringList extends Iterable<String> {
  boolean add(String paramString);
  
  String get(int paramInt);
  
  int size();
  
  String[] toStringArray();
  
  String[] toStringArray(int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastl\\util\StringList.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */