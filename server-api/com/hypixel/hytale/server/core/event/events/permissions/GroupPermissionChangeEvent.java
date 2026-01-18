/*    */ package com.hypixel.hytale.server.core.event.events.permissions;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
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
/*    */ public abstract class GroupPermissionChangeEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   @Nonnull
/*    */   private final String groupName;
/*    */   
/*    */   protected GroupPermissionChangeEvent(@Nonnull String groupName) {
/* 26 */     this.groupName = groupName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getGroupName() {
/* 34 */     return this.groupName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Added
/*    */     extends GroupPermissionChangeEvent
/*    */   {
/*    */     @Nonnull
/*    */     private final Set<String> addedPermissions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Added(@Nonnull String groupName, @Nonnull Set<String> addedPermissions) {
/* 55 */       super(groupName);
/* 56 */       this.addedPermissions = addedPermissions;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Set<String> getAddedPermissions() {
/* 64 */       return Collections.unmodifiableSet(this.addedPermissions);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Removed
/*    */     extends GroupPermissionChangeEvent
/*    */   {
/*    */     @Nonnull
/*    */     private final Set<String> removedPermissions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Removed(@Nonnull String groupName, @Nonnull Set<String> removedPermissions) {
/* 86 */       super(groupName);
/* 87 */       this.removedPermissions = removedPermissions;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Set<String> getRemovedPermissions() {
/* 95 */       return Collections.unmodifiableSet(this.removedPermissions);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\GroupPermissionChangeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */