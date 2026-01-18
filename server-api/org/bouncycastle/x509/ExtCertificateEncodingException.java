package org.bouncycastle.x509;

import java.security.cert.CertificateEncodingException;

class ExtCertificateEncodingException extends CertificateEncodingException {
  Throwable cause;
  
  ExtCertificateEncodingException(String paramString, Throwable paramThrowable) {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause() {
    return this.cause;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\x509\ExtCertificateEncodingException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */