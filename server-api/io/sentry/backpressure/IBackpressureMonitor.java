package io.sentry.backpressure;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface IBackpressureMonitor {
  void start();
  
  int getDownsampleFactor();
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\backpressure\IBackpressureMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */