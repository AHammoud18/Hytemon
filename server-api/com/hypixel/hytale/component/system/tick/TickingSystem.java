package com.hypixel.hytale.component.system.tick;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.System;
import javax.annotation.Nonnull;

public abstract class TickingSystem<ECS_TYPE> extends System<ECS_TYPE> implements TickableSystem<ECS_TYPE> {
  public abstract void tick(float paramFloat, int paramInt, @Nonnull Store<ECS_TYPE> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\TickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */