package com.hypixel.hytale.server.npc.navigation;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.movement.controllers.MotionController;

public interface AStarEvaluator {
  boolean isGoalReached(Ref<EntityStore> paramRef, AStarBase paramAStarBase, AStarNode paramAStarNode, MotionController paramMotionController, ComponentAccessor<EntityStore> paramComponentAccessor);
  
  float estimateToGoal(AStarBase paramAStarBase, Vector3d paramVector3d, MotionController paramMotionController);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\AStarEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */