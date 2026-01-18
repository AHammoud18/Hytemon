package com.hypixel.hytale.server.core.modules.collision;

@FunctionalInterface
public interface CollisionFilter<D, T> {
  boolean test(T paramT, int paramInt, D paramD, CollisionConfig paramCollisionConfig);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */