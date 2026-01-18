/*    */ package com.hypixel.hytale.server.core.modules.singleplayer.commands;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.serveraccess.Access;
/*    */ import com.hypixel.hytale.server.core.Constants;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
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
/*    */ public abstract class PlayCommandBase
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/*    */   private final SingleplayerModule singleplayerModule;
/*    */   @Nonnull
/*    */   private final Access commandAccess;
/*    */   @Nonnull
/* 37 */   private final OptionalArg<Boolean> enabledArg = withOptionalArg("enabled", "server.commands.play.enabled.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PlayCommandBase(@Nonnull String name, @Nonnull String description, @Nonnull SingleplayerModule singleplayerModule, @Nonnull Access commandAccess) {
/* 48 */     super(name, description);
/* 49 */     this.singleplayerModule = singleplayerModule;
/* 50 */     this.commandAccess = commandAccess;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 57 */     if (!Constants.SINGLEPLAYER) {
/* 58 */       context.sendMessage(Message.translation("server.commands.play.singleplayerOnly")
/* 59 */           .param("commandAccess", this.commandAccess.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     Access access = SingleplayerModule.get().getAccess();
/*    */     
/* 65 */     if (this.enabledArg.provided(context)) {
/* 66 */       boolean enabled = ((Boolean)this.enabledArg.get(context)).booleanValue();
/*    */       
/* 68 */       if (!enabled && access == this.commandAccess) {
/* 69 */         this.singleplayerModule.requestServerAccess(Access.Private);
/* 70 */         context.sendMessage(Message.translation("server.commands.play.accessDisabled")
/* 71 */             .param("commandAccess", this.commandAccess.toString()));
/* 72 */       } else if (enabled && access != this.commandAccess) {
/* 73 */         this.singleplayerModule.requestServerAccess(this.commandAccess);
/* 74 */         context.sendMessage(Message.translation("server.commands.play.accessEnabled")
/* 75 */             .param("commandAccess", this.commandAccess.toString()));
/*    */       } else {
/* 77 */         context.sendMessage(Message.translation("server.commands.play.accessAlreadyToggled")
/* 78 */             .param("commandAccess", this.commandAccess.toString())
/* 79 */             .param("enabled", MessageFormat.enabled(enabled)));
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 85 */     if (access == this.commandAccess) {
/* 86 */       this.singleplayerModule.requestServerAccess(Access.Private);
/* 87 */       context.sendMessage(Message.translation("server.commands.play.accessDisabled").param("commandAccess", this.commandAccess.toString()));
/*    */     } else {
/* 89 */       this.singleplayerModule.requestServerAccess(this.commandAccess);
/* 90 */       context.sendMessage(Message.translation("server.commands.play.accessEnabled").param("commandAccess", this.commandAccess.toString()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\commands\PlayCommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */