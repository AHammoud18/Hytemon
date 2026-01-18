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
/*    */ public class Added
/*    */   extends GroupPermissionChangeEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final Set<String> addedPermissions;
/*    */   
/*    */   public Added(@Nonnull String groupName, @Nonnull Set<String> addedPermissions) {
/* 55 */     super(groupName);
/* 56 */     this.addedPermissions = addedPermissions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<String> getAddedPermissions() {
/* 64 */     return Collections.unmodifiableSet(this.addedPermissions);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\GroupPermissionChangeEvent$Added.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */