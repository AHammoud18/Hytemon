package org.bouncycastle.math.field;

public interface ExtensionField extends FiniteField {
  FiniteField getSubfield();
  
  int getDegree();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\math\field\ExtensionField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */