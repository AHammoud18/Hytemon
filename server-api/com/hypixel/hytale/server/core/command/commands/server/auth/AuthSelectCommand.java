/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.auth.SessionServiceClient;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.awt.Color;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthSelectCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_NO_PENDING = Message.translation("server.commands.auth.select.noPending").color(Color.YELLOW);
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_SUCCESS = Message.translation("server.commands.auth.select.success").color(Color.GREEN);
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_FAILED = Message.translation("server.commands.auth.select.failed").color(Color.RED);
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_AVAILABLE_PROFILES = Message.translation("server.commands.auth.select.availableProfiles").color(Color.YELLOW);
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_USAGE = Message.translation("server.commands.auth.select.usage").color(Color.GRAY);
/*    */   
/*    */   public AuthSelectCommand() {
/* 32 */     super("select", "server.commands.auth.select.desc");
/* 33 */     addUsageVariant((AbstractCommand)new SelectProfileVariant());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 38 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */     
/* 40 */     if (!authManager.hasPendingProfiles()) {
/* 41 */       context.sendMessage(MESSAGE_NO_PENDING);
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     SessionServiceClient.GameProfile[] profiles = authManager.getPendingProfiles();
/* 46 */     if (profiles != null) {
/* 47 */       context.sendMessage(MESSAGE_AVAILABLE_PROFILES);
/* 48 */       sendProfileList(context, profiles);
/* 49 */       context.sendMessage(MESSAGE_USAGE);
/*    */     } 
/*    */   }
/*    */   
/*    */   static void sendProfileList(@Nonnull CommandContext context, @Nonnull SessionServiceClient.GameProfile[] profiles) {
/* 54 */     for (int i = 0; i < profiles.length; i++) {
/* 55 */       context.sendMessage(Message.translation("server.commands.auth.select.profileItem")
/* 56 */           .param("index", i + 1)
/* 57 */           .param("username", (profiles[i]).username)
/* 58 */           .param("uuid", (profiles[i]).uuid.toString()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private static class SelectProfileVariant
/*    */     extends CommandBase
/*    */   {
/*    */     @Nonnull
/* 67 */     private final RequiredArg<String> profileArg = withRequiredArg("profile", "server.commands.auth.select.profile.desc", (ArgumentType)ArgTypes.STRING);
/*    */     
/*    */     SelectProfileVariant() {
/* 70 */       super("server.commands.auth.select.variant.desc");
/*    */     }
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/*    */       boolean success;
/* 75 */       ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */       
/* 77 */       if (!authManager.hasPendingProfiles()) {
/* 78 */         context.sendMessage(AuthSelectCommand.MESSAGE_NO_PENDING);
/*    */         
/*    */         return;
/*    */       } 
/* 82 */       String selection = (String)this.profileArg.get(context);
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 87 */         int index = Integer.parseInt(selection);
/* 88 */         success = authManager.selectPendingProfile(index);
/* 89 */       } catch (NumberFormatException e) {
/*    */         
/* 91 */         success = authManager.selectPendingProfileByUsername(selection);
/*    */       } 
/*    */       
/* 94 */       if (success) {
/* 95 */         context.sendMessage(AuthSelectCommand.MESSAGE_SUCCESS);
/*    */       } else {
/* 97 */         context.sendMessage(AuthSelectCommand.MESSAGE_FAILED);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthSelectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */