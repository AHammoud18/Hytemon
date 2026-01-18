/*    */ package com.hypixel.hytale.server.core.universe.world.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetTickingCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final RequiredArg<Boolean> tickingArg = withRequiredArg("ticking", "server.commands.setticking.ticking.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SetTickingCommand() {
/* 30 */     super("setticking", "server.commands.setticking.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 37 */     boolean isTicking = ((Boolean)this.tickingArg.get(context)).booleanValue();
/* 38 */     world.setTicking(isTicking);
/* 39 */     context.sendMessage(Message.translation("server.universe.settick.info")
/* 40 */         .param("status", isTicking)
/* 41 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\SetTickingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */