package io.sentry.clientreport;

import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface IClientReportStorage {
  void addCount(ClientReportKey paramClientReportKey, Long paramLong);
  
  List<DiscardedEvent> resetCountsAndGet();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\IClientReportStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */