/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkMaxSendRateCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_SEC_SET = Message.translation("server.commands.chunk.maxsendrate.sec.set");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_TICK_SET = Message.translation("server.commands.chunk.maxsendrate.tick.set");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_SUMMARY = Message.translation("server.commands.chunk.maxsendrate.summary");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final OptionalArg<Integer> secArg = withOptionalArg("sec", "server.commands.chunk.maxsendrate.sec.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final OptionalArg<Integer> tickArg = withOptionalArg("tick", "server.commands.chunk.maxsendrate.tick.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkMaxSendRateCommand() {
/* 46 */     super("maxsendrate", "server.commands.chunk.maxsendrate.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 51 */     ChunkTracker chunkTracker = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/* 52 */     assert chunkTracker != null;
/*    */     
/* 54 */     if (this.secArg.provided(context)) {
/* 55 */       int sec = ((Integer)this.secArg.get(context)).intValue();
/* 56 */       chunkTracker.setMaxChunksPerSecond(sec);
/* 57 */       context.sendMessage(MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_SEC_SET
/* 58 */           .param("value", sec));
/*    */     } 
/*    */     
/* 61 */     if (this.tickArg.provided(context)) {
/* 62 */       int tick = ((Integer)this.tickArg.get(context)).intValue();
/* 63 */       chunkTracker.setMaxChunksPerTick(tick);
/* 64 */       context.sendMessage(MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_TICK_SET
/* 65 */           .param("value", tick));
/*    */     } 
/*    */     
/* 68 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_MAXSENDRATE_SUMMARY
/* 69 */         .param("perSecond", chunkTracker.getMaxChunksPerSecond())
/* 70 */         .param("perTick", chunkTracker.getMaxChunksPerTick()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkMaxSendRateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */