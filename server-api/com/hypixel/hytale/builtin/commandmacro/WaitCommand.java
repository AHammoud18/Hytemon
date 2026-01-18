/*    */ package com.hypixel.hytale.builtin.commandmacro;
/*    */ 
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WaitCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   private static final long MILLISECONDS_TO_SECONDS_MULTIPLIER = 1000L;
/*    */   public static final Runnable EMPTY_RUNNABLE = () -> {
/*    */     
/*    */     };
/* 23 */   private final RequiredArg<Float> timeArg = (RequiredArg<Float>)((RequiredArg)withRequiredArg("time", "server.commands.wait.arg.time", (ArgumentType)ArgTypes.FLOAT)
/* 24 */     .addValidator(Validators.greaterThan(Float.valueOf(0.0F)))).addValidator(Validators.lessThan(Float.valueOf(1000.0F)));
/*    */   
/* 26 */   private final FlagArg printArg = withFlagArg("print", "server.commands.wait.arg.print");
/*    */   
/*    */   public WaitCommand() {
/* 29 */     super("wait", "server.commands.wait.desc");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 34 */     CommandSender sender = context.sender();
/* 35 */     Runnable runnable = ((Boolean)this.printArg.get(context)).booleanValue() ? (() -> sender.sendMessage(Message.translation("server.commands.wait.complete"))) : EMPTY_RUNNABLE;
/*    */     
/* 37 */     return CompletableFuture.runAsync(runnable, CompletableFuture.delayedExecutor((long)(((Float)this.timeArg.get(context)).floatValue() * 1000.0F), TimeUnit.MILLISECONDS));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\WaitCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */