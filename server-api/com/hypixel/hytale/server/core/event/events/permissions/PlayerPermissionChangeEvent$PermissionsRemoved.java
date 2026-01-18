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
/*    */ public class PermissionsRemoved
/*    */   extends PlayerPermissionChangeEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final Set<String> removedPermissions;
/*    */   
/*    */   public PermissionsRemoved(@Nonnull UUID playerUuid, @Nonnull Set<String> removedPermissions) {
/* 66 */     super(playerUuid);
/* 67 */     this.removedPermissions = removedPermissions;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<String> getRemovedPermissions() {
/* 72 */     return Collections.unmodifiableSet(this.removedPermissions);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\PlayerPermissionChangeEvent$PermissionsRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */