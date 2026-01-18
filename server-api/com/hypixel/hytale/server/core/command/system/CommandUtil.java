/*    */ package com.hypixel.hytale.server.core.command.system;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.NoPermissionException;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandUtil
/*    */ {
/*    */   public static final String CONFIRM_UNSAFE_COMMAND = "confirm";
/*    */   public static final String WORLD_OPTION = "world";
/*    */   public static final String ENTITY_OPTION = "entity";
/*    */   public static final String PLAYER_OPTION = "player";
/* 20 */   public static int RECOMMEND_COUNT = 5;
/*    */   
/*    */   @Nonnull
/*    */   public static String stripCommandName(@Nonnull String rawCommand) {
/* 24 */     int indexOf = rawCommand.indexOf(' ');
/* 25 */     return (indexOf < 0) ? rawCommand : rawCommand.substring(indexOf + 1);
/*    */   }
/*    */   
/*    */   public static void requirePermission(@Nonnull PermissionHolder permissionHolder, @Nonnull String permission) {
/* 29 */     if (!permissionHolder.hasPermission(permission)) throw new NoPermissionException(permission); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */