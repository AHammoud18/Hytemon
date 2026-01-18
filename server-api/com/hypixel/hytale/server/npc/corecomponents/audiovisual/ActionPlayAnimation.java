/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.animations.NPCAnimationSlot;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionPlayAnimation;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionPlayAnimation extends ActionBase {
/*    */   protected final NPCAnimationSlot slot;
/*    */   @Nullable
/*    */   protected String animationId;
/*    */   
/*    */   public ActionPlayAnimation(@Nonnull BuilderActionPlayAnimation builderActionPlayAnimation, @Nonnull BuilderSupport support) {
/* 24 */     super((BuilderActionBase)builderActionPlayAnimation);
/* 25 */     this.slot = builderActionPlayAnimation.getSlot();
/* 26 */     this.animationId = builderActionPlayAnimation.getAnimationId(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 31 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 33 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 34 */     assert npcComponent != null;
/*    */     
/* 36 */     npcComponent.playAnimation(ref, this.slot.getMappedSlot(), this.animationId, (ComponentAccessor)store);
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   protected void setAnimationId(String animationId) {
/* 41 */     this.animationId = animationId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionPlayAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */