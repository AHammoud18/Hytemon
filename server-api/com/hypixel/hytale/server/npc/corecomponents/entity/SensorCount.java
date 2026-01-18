/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorCount;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorCount extends SensorBase {
/*    */   protected final int minCount;
/*    */   protected final int maxCount;
/*    */   protected final double minRange;
/*    */   protected final double maxRange;
/*    */   protected final int[] includeGroups;
/*    */   protected final int[] excludeGroups;
/*    */   protected boolean findPlayers;
/*    */   protected final boolean haveIncludeGroups;
/*    */   protected final boolean haveExcludeGroups;
/*    */   
/*    */   public SensorCount(@Nonnull BuilderSensorCount builderSensorCount, @Nonnull BuilderSupport support) {
/* 29 */     super((BuilderSensorBase)builderSensorCount);
/* 30 */     int[] count = builderSensorCount.getCount(support);
/* 31 */     this.minCount = count[0];
/* 32 */     this.maxCount = count[1];
/* 33 */     double[] range = builderSensorCount.getRange(support);
/* 34 */     this.minRange = range[0];
/* 35 */     this.maxRange = range[1];
/* 36 */     this.includeGroups = builderSensorCount.getIncludeGroups();
/* 37 */     this.excludeGroups = builderSensorCount.getExcludeGroups();
/* 38 */     this.haveIncludeGroups = (this.includeGroups != null && this.includeGroups.length > 0);
/* 39 */     this.haveExcludeGroups = (this.excludeGroups != null && this.excludeGroups.length > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(@Nonnull Role role) {
/* 44 */     if (this.haveIncludeGroups) {
/* 45 */       this.findPlayers = groupListHasPlayer(this.includeGroups);
/*    */     } else {
/* 47 */       this.findPlayers = (!this.haveExcludeGroups || !groupListHasPlayer(this.excludeGroups));
/*    */     } 
/*    */     
/* 50 */     role.getPositionCache().requireEntityDistanceSorted(this.maxRange);
/* 51 */     if (this.findPlayers) role.getPositionCache().requirePlayerDistanceSorted(this.maxRange);
/*    */   
/*    */   }
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 56 */     return (super.matches(ref, role, dt, store) && role.getPositionCache().isEntityCountInRange(this.minRange, this.maxRange, this.minCount, this.maxCount, this.findPlayers, role, SensorCount::filterNPC, this, (ComponentAccessor)store));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   protected static boolean groupListHasPlayer(@Nonnull int[] groups) {
/* 65 */     for (int group : groups) {
/* 66 */       if (WorldSupport.hasTagInGroup(group, BuilderManager.getPlayerGroupID())) return true; 
/*    */     } 
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean filterNPC(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 72 */     int roleIndex = role.getRoleIndex();
/* 73 */     return ((!this.haveIncludeGroups || WorldSupport.isGroupMember(roleIndex, ref, this.includeGroups, componentAccessor)) && (!this.haveExcludeGroups || 
/* 74 */       !WorldSupport.isGroupMember(roleIndex, ref, this.excludeGroups, componentAccessor)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorCount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */