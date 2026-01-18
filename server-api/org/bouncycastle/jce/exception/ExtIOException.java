package org.bouncycastle.jce.exception;

import java.io.IOException;

public class ExtIOException extends IOException implements ExtException {
  private Throwable cause;
  
  public ExtIOException(String paramString, Throwable paramThrowable) {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause() {
    return this.cause;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\exception\ExtIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */