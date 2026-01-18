/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionPlaySound;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionPlaySound
/*    */   extends ActionBase {
/*    */   protected final int soundEventIndex;
/*    */   
/*    */   public ActionPlaySound(@Nonnull BuilderActionPlaySound builderActionPlaySound, @Nonnull BuilderSupport support) {
/* 24 */     super((BuilderActionBase)builderActionPlaySound);
/* 25 */     this.soundEventIndex = builderActionPlaySound.getSoundEventIndex(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 32 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 33 */     assert transformComponent != null;
/*    */     
/* 35 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 36 */     assert npcComponent != null;
/*    */     
/* 38 */     Vector3d position = transformComponent.getPosition();
/* 39 */     SoundUtil.playSoundEvent3d(ref, this.soundEventIndex, position.getX(), position.getY(), position.getZ(), false, (ComponentAccessor)store);
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionPlaySound.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */