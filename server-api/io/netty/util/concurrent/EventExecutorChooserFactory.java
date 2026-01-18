package io.netty.util.concurrent;

import java.util.List;

public interface EventExecutorChooserFactory {
  EventExecutorChooser newChooser(EventExecutor[] paramArrayOfEventExecutor);
  
  public static interface ObservableEventExecutorChooser extends EventExecutorChooser {
    int activeExecutorCount();
    
    List<AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric> executorUtilizations();
  }
  
  public static interface EventExecutorChooser {
    EventExecutor next();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\EventExecutorChooserFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */