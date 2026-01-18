package com.hypixel.hytale.server.core.asset.monitor;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public interface AssetMonitorHandler extends BiPredicate<Path, EventKind>, Consumer<Map<Path, EventKind>> {
  Object getKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\AssetMonitorHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */