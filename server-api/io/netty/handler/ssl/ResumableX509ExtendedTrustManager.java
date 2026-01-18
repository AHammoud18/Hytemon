package io.netty.handler.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509TrustManager;

public interface ResumableX509ExtendedTrustManager extends X509TrustManager {
  void resumeClientTrusted(X509Certificate[] paramArrayOfX509Certificate, SSLEngine paramSSLEngine) throws CertificateException;
  
  void resumeServerTrusted(X509Certificate[] paramArrayOfX509Certificate, SSLEngine paramSSLEngine) throws CertificateException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ResumableX509ExtendedTrustManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */