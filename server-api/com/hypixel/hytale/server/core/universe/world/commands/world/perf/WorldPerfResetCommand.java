/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world.perf;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPerfResetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_WORLD_PERF_RESET_ALL = Message.translation("server.commands.world.perf.reset.all");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private final FlagArg allFlag = withFlagArg("all", "server.commands.world.perf.reset.all.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPerfResetCommand() {
/* 31 */     super("reset", "server.commands.world.perf.reset.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@NonNullDecl CommandContext context, @NonNullDecl World world, @NonNullDecl Store<EntityStore> store) {
/* 36 */     if (this.allFlag.provided(context)) {
/* 37 */       Universe.get().getWorlds().forEach((name, w) -> w.clearMetrics());
/* 38 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_PERF_RESET_ALL);
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     world.clearMetrics();
/* 43 */     context.sendMessage(Message.translation("server.commands.world.perf.reset")
/* 44 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\perf\WorldPerfResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */