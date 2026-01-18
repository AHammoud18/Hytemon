/*    */ package com.hypixel.hytale.server.spawning.jobs;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCBeaconSpawnJob
/*    */   extends SpawnJob
/*    */ {
/* 20 */   protected int roleIndex = Integer.MIN_VALUE;
/*    */   
/*    */   @Nullable
/*    */   private Ref<EntityStore> player;
/*    */   
/*    */   private int spawnsThisRound;
/*    */   private int flockSize;
/*    */   @Nullable
/*    */   private FlockAsset flockAsset;
/*    */   
/*    */   public int getRoleIndex() {
/* 31 */     return this.roleIndex;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getPlayer() {
/* 36 */     return this.player;
/*    */   }
/*    */   
/*    */   public int getSpawnsThisRound() {
/* 40 */     return this.spawnsThisRound;
/*    */   }
/*    */   
/*    */   public int getFlockSize() {
/* 44 */     return this.flockSize;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public FlockAsset getFlockAsset() {
/* 49 */     return this.flockAsset;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldTerminate() {
/* 54 */     return !this.player.isValid();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean budgetAvailable() {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 64 */     super.reset();
/* 65 */     this.roleIndex = Integer.MIN_VALUE;
/* 66 */     this.flockAsset = null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ISpawnableWithModel getSpawnable() {
/* 72 */     Builder<Role> role = NPCPlugin.get().tryGetCachedValidRole(this.roleIndex);
/* 73 */     if (role == null) return null;
/*    */     
/* 75 */     if (!role.isSpawnable()) {
/* 76 */       throw new IllegalArgumentException("Spawn job: Role must be a spawnable (non-abstract) type for spawning: " + NPCPlugin.get().getName(this.roleIndex));
/*    */     }
/* 78 */     if (!(role instanceof ISpawnableWithModel)) {
/* 79 */       throw new IllegalArgumentException("Spawn job: Need ISpawnableWithModel interface for spawning: " + NPCPlugin.get().getName(this.roleIndex));
/*    */     }
/* 81 */     return (ISpawnableWithModel)role;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getSpawnableName() {
/* 87 */     return NPCPlugin.get().getName(this.roleIndex);
/*    */   }
/*    */   
/*    */   public void beginProbing(@Nonnull PlayerRef targetPlayer, int spawnsThisRound, int roleIndex, @Nullable FlockAsset flockDefinition) {
/* 91 */     beginProbing();
/* 92 */     this.player = targetPlayer.getReference();
/* 93 */     this.spawnsThisRound = spawnsThisRound;
/* 94 */     this.roleIndex = roleIndex;
/* 95 */     this.flockAsset = flockDefinition;
/* 96 */     this.flockSize = (flockDefinition != null) ? flockDefinition.pickFlockSize() : 1;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\jobs\NPCBeaconSpawnJob.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */