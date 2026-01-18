/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;
/*    */ import com.hypixel.hytale.server.core.util.AuthUtil;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
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
/*    */ public class UnbanCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/*    */   private final HytaleBanProvider banProvider;
/*    */   @Nonnull
/* 30 */   private final RequiredArg<String> usernameArg = withRequiredArg("username", "server.commands.unban.username.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnbanCommand(@Nonnull HytaleBanProvider banProvider) {
/* 38 */     super("unban", "server.commands.unban.desc");
/* 39 */     setUnavailableInSingleplayer(true);
/* 40 */     this.banProvider = banProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 46 */     String username = (String)this.usernameArg.get(context);
/*    */     
/* 48 */     return AuthUtil.lookupUuid(username).thenAccept(uuid -> {
/*    */           if (!this.banProvider.hasBan(uuid)) {
/*    */             context.sendMessage(Message.translation("server.modules.unban.playerNotBanned").param("name", username));
/*    */           } else {
/*    */             this.banProvider.modify(());
/*    */ 
/*    */             
/*    */             context.sendMessage(Message.translation("server.modules.unban.success").param("name", username));
/*    */           } 
/* 57 */         }).exceptionally(ex -> {
/*    */           context.sendMessage(Message.translation("server.modules.ban.lookupFailed").param("name", username));
/*    */           ex.printStackTrace();
/*    */           return null;
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\UnbanCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */