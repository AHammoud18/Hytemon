package io.netty.handler.ssl;

import java.security.cert.Certificate;
import java.util.Map;
import javax.net.ssl.SSLException;

interface OpenSslInternalSession extends OpenSslSession {
  void prepareHandshake();
  
  OpenSslSessionId sessionId();
  
  void setLocalCertificate(Certificate[] paramArrayOfCertificate);
  
  void setSessionDetails(long paramLong1, long paramLong2, OpenSslSessionId paramOpenSslSessionId, Map<String, Object> paramMap);
  
  Map<String, Object> keyValueStorage();
  
  void setLastAccessedTime(long paramLong);
  
  void tryExpandApplicationBufferSize(int paramInt);
  
  void handshakeFinished(byte[] paramArrayOfbyte1, String paramString1, String paramString2, byte[] paramArrayOfbyte2, byte[][] paramArrayOfbyte, long paramLong1, long paramLong2) throws SSLException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslInternalSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */