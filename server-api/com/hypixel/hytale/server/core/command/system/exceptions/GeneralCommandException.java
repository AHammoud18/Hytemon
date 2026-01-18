/*    */ package com.hypixel.hytale.server.core.command.system.exceptions;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import java.awt.Color;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class GeneralCommandException
/*    */   extends CommandException
/*    */ {
/*    */   @Nonnull
/*    */   private final Message message;
/*    */   
/*    */   public GeneralCommandException(@Nonnull Message message) {
/* 27 */     this.message = message.color(Color.RED);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendTranslatedMessage(@Nonnull CommandSender sender) {
/* 32 */     sender.sendMessage(this.message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getMessageText() {
/*    */     try {
/* 43 */       return this.message.getAnsiMessage();
/* 44 */     } catch (Exception e) {
/*    */       
/* 46 */       String rawText = this.message.getRawText();
/* 47 */       if (rawText != null) return rawText; 
/* 48 */       return this.message.getMessageId();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\exceptions\GeneralCommandException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */