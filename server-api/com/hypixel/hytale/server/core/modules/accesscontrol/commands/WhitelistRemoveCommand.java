/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import com.hypixel.hytale.server.core.util.AuthUtil;
/*    */ import java.util.Set;
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
/*    */ public class WhitelistRemoveCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/*    */   private final HytaleWhitelistProvider whitelistProvider;
/*    */   @Nonnull
/* 30 */   private final RequiredArg<String> usernameArg = withRequiredArg("username", "server.commands.whitelist.remove.username.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WhitelistRemoveCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 38 */     super("remove", "server.commands.whitelist.remove.desc");
/* 39 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 45 */     String username = (String)this.usernameArg.get(context);
/*    */     
/* 47 */     return AuthUtil.lookupUuid(username).thenAccept(uuid -> {
/*    */ 
/*    */           
/*    */           if (this.whitelistProvider.modify(())) {
/*    */             context.sendMessage(Message.translation("server.modules.whitelist.removalSuccess").param("uuid", uuid.toString()));
/*    */           } else {
/*    */             context.sendMessage(Message.translation("server.modules.whitelist.uuidNotWhitelisted").param("uuid", uuid.toString()));
/*    */           } 
/* 55 */         }).exceptionally(ex -> {
/*    */           context.sendMessage(Message.translation("server.modules.ban.lookupFailed").param("name", username));
/*    */           ex.printStackTrace();
/*    */           return null;
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */