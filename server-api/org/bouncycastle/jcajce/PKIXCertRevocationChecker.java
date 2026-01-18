package org.bouncycastle.jcajce;

import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;

public interface PKIXCertRevocationChecker {
  void setParameter(String paramString, Object paramObject);
  
  void initialize(PKIXCertRevocationCheckerParameters paramPKIXCertRevocationCheckerParameters) throws CertPathValidatorException;
  
  void check(Certificate paramCertificate) throws CertPathValidatorException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\PKIXCertRevocationChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */