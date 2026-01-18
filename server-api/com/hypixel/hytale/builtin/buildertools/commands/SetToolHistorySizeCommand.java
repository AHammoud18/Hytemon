/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.validator.RangeValidator;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SetToolHistorySizeCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 19 */   private final RequiredArg<Integer> historyLengthArg = (RequiredArg<Integer>)
/* 20 */     withRequiredArg("historyLength", "server.commands.settoolhistorysize.historyLength.desc", (ArgumentType)ArgTypes.INTEGER)
/* 21 */     .addValidator((Validator)new RangeValidator(Integer.valueOf(10), Integer.valueOf(250), true));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SetToolHistorySizeCommand() {
/* 27 */     super("setToolHistorySize", "server.commands.settoolhistorysize.desc");
/* 28 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     BuilderToolsPlugin.get().setToolHistorySize(((Integer)this.historyLengthArg.get(context)).intValue());
/* 34 */     context.sendMessage(Message.translation("server.commands.settoolhistorysize.set")
/* 35 */         .param("size", ((Integer)this.historyLengthArg.get(context)).intValue()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\SetToolHistorySizeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */