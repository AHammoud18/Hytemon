package com.hypixel.hytale.server.npc.blackboard.view;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.blackboard.Blackboard;
import java.util.function.Consumer;

public interface IBlackboardViewManager<View extends IBlackboardView<View>> {
  View get(Ref<EntityStore> paramRef, Blackboard paramBlackboard, ComponentAccessor<EntityStore> paramComponentAccessor);
  
  View get(Vector3d paramVector3d, Blackboard paramBlackboard);
  
  View get(int paramInt1, int paramInt2, Blackboard paramBlackboard);
  
  View get(long paramLong, Blackboard paramBlackboard);
  
  View getIfExists(long paramLong);
  
  void cleanup();
  
  void onWorldRemoved();
  
  void forEachView(Consumer<View> paramConsumer);
  
  void clear();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\IBlackboardViewManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */