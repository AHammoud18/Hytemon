/*    */ package com.hypixel.hytale.server.core.event.events.permissions;
/*    */ 
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
/*    */ public class GroupAdded
/*    */   extends PlayerPermissionChangeEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final String groupName;
/*    */   
/*    */   public GroupAdded(@Nonnull UUID playerUuid, @Nonnull String groupName) {
/* 85 */     super(playerUuid);
/* 86 */     this.groupName = groupName;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getGroupName() {
/* 91 */     return this.groupName;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\permissions\PlayerPermissionChangeEvent$GroupAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */