/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityFilterLineOfSight
/*    */   extends EntityFilterBase
/*    */ {
/*    */   public static final int COST = 400;
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 19 */     return role.getPositionCache().hasLineOfSight(ref, targetRef, (ComponentAccessor)store);
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 24 */     return 400;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterLineOfSight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */