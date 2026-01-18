package org.bouncycastle.est.jcajce;

import javax.net.ssl.SSLSocketFactory;

public interface SSLSocketFactoryCreator {
  SSLSocketFactory createFactory() throws Exception;
  
  boolean isTrusted();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\est\jcajce\SSLSocketFactoryCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */