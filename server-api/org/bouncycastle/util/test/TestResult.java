package org.bouncycastle.util.test;

public interface TestResult {
  boolean isSuccessful();
  
  Throwable getException();
  
  String toString();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastl\\util\test\TestResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */