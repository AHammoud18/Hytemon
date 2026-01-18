package org.bouncycastle.openssl;

import java.io.IOException;

public class PEMException extends IOException {
  Exception underlying;
  
  public PEMException(String paramString) {
    super(paramString);
  }
  
  public PEMException(String paramString, Exception paramException) {
    super(paramString);
    this.underlying = paramException;
  }
  
  public Exception getUnderlyingException() {
    return this.underlying;
  }
  
  public Throwable getCause() {
    return this.underlying;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\openssl\PEMException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */