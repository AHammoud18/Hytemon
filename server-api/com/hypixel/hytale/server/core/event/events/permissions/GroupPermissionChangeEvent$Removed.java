/*    */ package com.hypixel.hytale.server.core.event.events.permissions;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
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
/*    */ public class Removed
/*    */   extends GroupPermissionChangeEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final Set<String> removedPermissions;
/*    */   
/*    */   public Removed(@Nonnull String groupName, @Nonnull Set<String> removedPermissions) {
/* 86 */     super(groupName);
/* 87 */     this.removedPermissions = removedPermissions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<String> getRemovedPermissions() {
/* 95 */     return Collections.unmodifiableSet(this.removedPermissions);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\GroupPermissionChangeEvent$Removed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */