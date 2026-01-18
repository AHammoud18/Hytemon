/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterNPCGroup;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterNPCGroup
/*    */   extends EntityFilterBase
/*    */ {
/*    */   public static final int COST = 200;
/*    */   protected final int[] includeGroups;
/*    */   protected final int[] excludeGroups;
/*    */   
/*    */   public EntityFilterNPCGroup(@Nonnull BuilderEntityFilterNPCGroup builder, @Nonnull BuilderSupport support) {
/* 22 */     this.includeGroups = builder.getIncludeGroups(support);
/* 23 */     this.excludeGroups = builder.getExcludeGroups(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 28 */     if (this.includeGroups != null && this.includeGroups.length > 0 && 
/* 29 */       !WorldSupport.isGroupMember(role.getRoleIndex(), targetRef, this.includeGroups, (ComponentAccessor)store)) return false;
/*    */ 
/*    */     
/* 32 */     if (this.excludeGroups != null && this.excludeGroups.length > 0) {
/* 33 */       return !WorldSupport.isGroupMember(role.getRoleIndex(), targetRef, this.excludeGroups, (ComponentAccessor)store);
/*    */     }
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 41 */     return 200;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterNPCGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */