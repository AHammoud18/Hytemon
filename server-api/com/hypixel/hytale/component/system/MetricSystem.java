package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.metrics.MetricResults;

public interface MetricSystem<ECS_TYPE> {
  MetricResults toMetricResults(Store<ECS_TYPE> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\MetricSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */