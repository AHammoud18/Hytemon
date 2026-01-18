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
/*    */ public class SenderTypeException
/*    */   extends CommandException
/*    */ {
/*    */   @Nonnull
/*    */   private final Class<?> senderType;
/*    */   
/*    */   public SenderTypeException(@Nonnull Class<?> senderType) {
/* 26 */     this.senderType = senderType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendTranslatedMessage(@Nonnull CommandSender sender) {
/* 31 */     sender.sendMessage(Message.translation("server.commands.errors.sender")
/* 32 */         .param("sender", this.senderType.getSimpleName())
/* 33 */         .color(Color.RED));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\exceptions\SenderTypeException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */