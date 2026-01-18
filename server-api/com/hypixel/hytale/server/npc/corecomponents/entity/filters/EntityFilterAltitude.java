/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterAltitude;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EntityFilterAltitude extends EntityFilterBase {
/*    */   public static final int COST = 0;
/*    */   @Nullable
/* 19 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*    */   
/*    */   protected final double[] altitudeRange;
/*    */   
/*    */   public EntityFilterAltitude(@Nonnull BuilderEntityFilterAltitude builder, @Nonnull BuilderSupport support) {
/* 24 */     this.altitudeRange = builder.getAltitudeRange(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/*    */     double heightOverGround;
/* 30 */     NPCEntity targetNpcComponent = (NPCEntity)store.getComponent(targetRef, NPC_COMPONENT_TYPE);
/* 31 */     if (targetNpcComponent != null) {
/* 32 */       MotionController targetActiveMotionController = targetNpcComponent.getRole().getActiveMotionController();
/* 33 */       heightOverGround = targetActiveMotionController.getHeightOverGround();
/*    */     } else {
/*    */       
/* 36 */       heightOverGround = 0.0D;
/*    */     } 
/* 38 */     return (heightOverGround >= this.altitudeRange[0] && heightOverGround <= this.altitudeRange[1]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 43 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterAltitude.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */