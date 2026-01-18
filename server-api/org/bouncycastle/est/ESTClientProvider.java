package org.bouncycastle.est;

public interface ESTClientProvider {
  ESTClient makeClient() throws ESTException;
  
  boolean isTrusted();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\est\ESTClientProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */