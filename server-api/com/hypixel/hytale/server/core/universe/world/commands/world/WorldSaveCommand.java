/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.system.WorldConfigSaveSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WorldSaveCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_WORLD_SAVE_NO_WORLD_SPECIFIED = Message.translation("server.commands.world.save.noWorldSpecified");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_WORLD_SAVE_SAVING_ALL = Message.translation("server.commands.world.save.savingAll");
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_WORLD_SAVE_SAVING_ALL_DONE = Message.translation("server.commands.world.save.savingAllDone");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final FlagArg saveAllFlag = withFlagArg("all", "server.commands.world.save.all.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSaveCommand() {
/* 45 */     super("save", "server.commands.world.save.desc", true);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 51 */     if (((Boolean)this.saveAllFlag.get(context)).booleanValue()) {
/* 52 */       return saveAllWorlds(context);
/*    */     }
/*    */     
/* 55 */     if (!this.worldArg.provided(context)) {
/* 56 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_SAVE_NO_WORLD_SPECIFIED);
/* 57 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 60 */     World world = (World)this.worldArg.getProcessed(context);
/* 61 */     context.sendMessage(Message.translation("server.commands.world.save.saving")
/* 62 */         .param("world", world.getName()));
/*    */     
/* 64 */     return CompletableFuture.runAsync(() -> saveWorld(world), (Executor)world)
/* 65 */       .thenRun(() -> context.sendMessage(Message.translation("server.commands.world.save.savingDone").param("world", world.getName())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private CompletableFuture<Void> saveAllWorlds(@Nonnull CommandContext context) {
/* 77 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_SAVE_SAVING_ALL);
/*    */ 
/*    */ 
/*    */     
/* 81 */     CompletableFuture[] completableFutures = (CompletableFuture[])Universe.get().getWorlds().values().stream().map(world -> CompletableFuture.runAsync((), (Executor)world)).toArray(x$0 -> new CompletableFuture[x$0]);
/*    */     
/* 83 */     return 
/* 84 */       CompletableFuture.allOf((CompletableFuture<?>[])completableFutures)
/* 85 */       .thenRun(() -> context.sendMessage(MESSAGE_COMMANDS_WORLD_SAVE_SAVING_ALL_DONE));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private static CompletableFuture<Void> saveWorld(@Nonnull World world) {
/* 96 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] {
/* 97 */           WorldConfigSaveSystem.saveWorldConfigAndResources(world), 
/* 98 */           ChunkSavingSystems.saveChunksInWorld(world.getChunkStore().getStore())
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldSaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */