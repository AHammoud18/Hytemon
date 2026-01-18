package org.bouncycastle.jcajce.provider.util;

import javax.crypto.BadPaddingException;

public class BadBlockException extends BadPaddingException {
  private final Throwable cause;
  
  public BadBlockException(String paramString, Throwable paramThrowable) {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause() {
    return this.cause;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provide\\util\BadBlockException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */