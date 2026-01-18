package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.Store;
import javax.annotation.Nonnull;

public abstract class StoreSystem<ECS_TYPE> extends System<ECS_TYPE> {
  public abstract void onSystemAddedToStore(@Nonnull Store<ECS_TYPE> paramStore);
  
  public abstract void onSystemRemovedFromStore(@Nonnull Store<ECS_TYPE> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\StoreSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */