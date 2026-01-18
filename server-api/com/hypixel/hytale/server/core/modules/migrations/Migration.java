package com.hypixel.hytale.server.core.modules.migrations;

import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;

public interface Migration {
  void run(WorldChunk paramWorldChunk);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\migrations\Migration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */