package org.bouncycastle.cert.cmp;

public class CMPRuntimeException extends RuntimeException {
  private Throwable cause;
  
  public CMPRuntimeException(String paramString, Throwable paramThrowable) {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause() {
    return this.cause;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\cmp\CMPRuntimeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */