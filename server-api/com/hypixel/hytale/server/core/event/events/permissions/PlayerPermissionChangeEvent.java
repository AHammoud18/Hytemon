/*     */ package com.hypixel.hytale.server.core.event.events.permissions;
/*     */ 
/*     */ import com.hypixel.hytale.event.IEvent;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PlayerPermissionChangeEvent
/*     */   implements IEvent<Void>
/*     */ {
/*     */   @Nonnull
/*     */   private final UUID playerUuid;
/*     */   
/*     */   protected PlayerPermissionChangeEvent(@Nonnull UUID playerUuid) {
/*  27 */     this.playerUuid = playerUuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UUID getPlayerUuid() {
/*  35 */     return this.playerUuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PermissionsAdded
/*     */     extends PlayerPermissionChangeEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final Set<String> addedPermissions;
/*     */ 
/*     */     
/*     */     public PermissionsAdded(@Nonnull UUID playerUuid, @Nonnull Set<String> addedPermissions) {
/*  47 */       super(playerUuid);
/*  48 */       this.addedPermissions = addedPermissions;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Set<String> getAddedPermissions() {
/*  53 */       return Collections.unmodifiableSet(this.addedPermissions);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PermissionsRemoved
/*     */     extends PlayerPermissionChangeEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final Set<String> removedPermissions;
/*     */ 
/*     */     
/*     */     public PermissionsRemoved(@Nonnull UUID playerUuid, @Nonnull Set<String> removedPermissions) {
/*  66 */       super(playerUuid);
/*  67 */       this.removedPermissions = removedPermissions;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Set<String> getRemovedPermissions() {
/*  72 */       return Collections.unmodifiableSet(this.removedPermissions);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GroupAdded
/*     */     extends PlayerPermissionChangeEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final String groupName;
/*     */ 
/*     */     
/*     */     public GroupAdded(@Nonnull UUID playerUuid, @Nonnull String groupName) {
/*  85 */       super(playerUuid);
/*  86 */       this.groupName = groupName;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String getGroupName() {
/*  91 */       return this.groupName;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GroupRemoved
/*     */     extends PlayerPermissionChangeEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final String groupName;
/*     */ 
/*     */     
/*     */     public GroupRemoved(@Nonnull UUID playerUuid, @Nonnull String groupName) {
/* 104 */       super(playerUuid);
/* 105 */       this.groupName = groupName;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String getGroupName() {
/* 110 */       return this.groupName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\PlayerPermissionChangeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */