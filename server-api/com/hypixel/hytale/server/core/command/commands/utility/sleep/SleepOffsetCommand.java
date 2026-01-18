/*    */ package com.hypixel.hytale.server.core.command.commands.utility.sleep;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.thread.TickingThread;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SleepOffsetCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private final FlagArg percentFlag = withFlagArg("percent", "server.commands.sleepoffset.percent.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   private final OptionalArg<Integer> offsetArg = withOptionalArg("offset", "server.commands.sleepoffset.offset.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SleepOffsetCommand() {
/* 34 */     super("offset", "server.commands.sleepoffset.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 39 */     if (this.offsetArg.provided(context)) {
/*    */ 
/*    */       
/* 42 */       float oldValue = (float)TickingThread.SLEEP_OFFSET;
/* 43 */       int newValue = ((Integer)this.offsetArg.get(context)).intValue();
/* 44 */       TickingThread.SLEEP_OFFSET = newValue;
/*    */       
/* 46 */       if (((Boolean)this.percentFlag.get(context)).booleanValue()) {
/* 47 */         context.sendMessage(Message.translation("server.commands.sleepoffset.setPercent")
/* 48 */             .param("newValue", newValue)
/* 49 */             .param("oldValue", oldValue));
/*    */       } else {
/* 51 */         context.sendMessage(Message.translation("server.commands.sleepoffset.set")
/* 52 */             .param("newValue", newValue)
/* 53 */             .param("oldValue", oldValue));
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 58 */       float value = (float)TickingThread.SLEEP_OFFSET;
/* 59 */       if (((Boolean)this.percentFlag.get(context)).booleanValue()) {
/* 60 */         context.sendMessage(Message.translation("server.commands.sleepoffset.getPercent")
/* 61 */             .param("value", value));
/*    */       } else {
/* 63 */         context.sendMessage(Message.translation("server.commands.sleepoffset.getOffset")
/* 64 */             .param("value", value));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\sleep\SleepOffsetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */