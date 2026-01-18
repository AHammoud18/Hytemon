package io.netty.handler.ssl;

import java.util.List;
import java.util.Set;

public interface CipherSuiteFilter {
  String[] filterCipherSuites(Iterable<String> paramIterable, List<String> paramList, Set<String> paramSet);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\CipherSuiteFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */