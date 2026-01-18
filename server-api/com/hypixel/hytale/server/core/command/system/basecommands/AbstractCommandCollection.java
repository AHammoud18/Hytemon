/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
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
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCommandCollection
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   public AbstractCommandCollection(@Nonnull String name, @Nonnull String description) {
/* 23 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getFullUsage(@Nonnull CommandSender sender) {
/* 34 */     return super.getUsageString(sender);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 43 */     Message message = Message.translation("server.commands.help.usage").insert(":").insert("  (").insert(Message.translation("server.commands.help.useHelpOnAnySubCommand")).insert(")").insert("\n").insert(getUsageString(context.sender()));
/* 44 */     context.sender().sendMessage(message);
/* 45 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageString(@Nonnull CommandSender sender) {
/* 51 */     return getUsageShort(sender, false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractCommandCollection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */