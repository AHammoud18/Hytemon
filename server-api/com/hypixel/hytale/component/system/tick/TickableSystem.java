package com.hypixel.hytale.component.system.tick;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.ISystem;
import javax.annotation.Nonnull;

public interface TickableSystem<ECS_TYPE> extends ISystem<ECS_TYPE> {
  void tick(float paramFloat, int paramInt, @Nonnull Store<ECS_TYPE> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\TickableSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */