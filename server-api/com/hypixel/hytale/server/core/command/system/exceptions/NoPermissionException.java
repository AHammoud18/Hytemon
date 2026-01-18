/*    */ package com.hypixel.hytale.server.core.command.system.exceptions;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import java.awt.Color;
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
/*    */ public class NoPermissionException
/*    */   extends CommandException
/*    */ {
/*    */   @Nonnull
/*    */   private final String permission;
/*    */   
/*    */   public NoPermissionException(@Nonnull String permission) {
/* 26 */     this.permission = permission;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendTranslatedMessage(@Nonnull CommandSender sender) {
/* 31 */     sender.sendMessage(Message.translation("server.commands.errors.permission")
/* 32 */         .param("permission", this.permission)
/* 33 */         .color(Color.RED));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\exceptions\NoPermissionException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */