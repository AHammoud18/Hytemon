package META-INF.versions.9.org.bouncycastle.math.field;

import java.math.BigInteger;

public interface FiniteField {
  BigInteger getCharacteristic();
  
  int getDimension();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\math\field\FiniteField.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */