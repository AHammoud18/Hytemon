/*    */ package com.hypixel.hytale.server.core.event.events.permissions;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PermissionsAdded
/*    */   extends PlayerPermissionChangeEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final Set<String> addedPermissions;
/*    */   
/*    */   public PermissionsAdded(@Nonnull UUID playerUuid, @Nonnull Set<String> addedPermissions) {
/* 47 */     super(playerUuid);
/* 48 */     this.addedPermissions = addedPermissions;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<String> getAddedPermissions() {
/* 53 */     return Collections.unmodifiableSet(this.addedPermissions);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\PlayerPermissionChangeEvent$PermissionsAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */