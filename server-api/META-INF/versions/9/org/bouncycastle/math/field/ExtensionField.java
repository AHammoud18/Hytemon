package META-INF.versions.9.org.bouncycastle.math.field;

import org.bouncycastle.math.field.FiniteField;

public interface ExtensionField extends FiniteField {
  FiniteField getSubfield();
  
  int getDegree();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\math\field\ExtensionField.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */