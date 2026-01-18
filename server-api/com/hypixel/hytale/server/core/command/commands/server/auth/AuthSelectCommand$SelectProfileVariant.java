/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
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
/*    */ class SelectProfileVariant
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 67 */   private final RequiredArg<String> profileArg = withRequiredArg("profile", "server.commands.auth.select.profile.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   SelectProfileVariant() {
/* 70 */     super("server.commands.auth.select.variant.desc");
/*    */   }
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/*    */     boolean success;
/* 75 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */     
/* 77 */     if (!authManager.hasPendingProfiles()) {
/* 78 */       context.sendMessage(AuthSelectCommand.MESSAGE_NO_PENDING);
/*    */       
/*    */       return;
/*    */     } 
/* 82 */     String selection = (String)this.profileArg.get(context);
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 87 */       int index = Integer.parseInt(selection);
/* 88 */       success = authManager.selectPendingProfile(index);
/* 89 */     } catch (NumberFormatException e) {
/*    */       
/* 91 */       success = authManager.selectPendingProfileByUsername(selection);
/*    */     } 
/*    */     
/* 94 */     if (success) {
/* 95 */       context.sendMessage(AuthSelectCommand.MESSAGE_SUCCESS);
/*    */     } else {
/* 97 */       context.sendMessage(AuthSelectCommand.MESSAGE_FAILED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthSelectCommand$SelectProfileVariant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */