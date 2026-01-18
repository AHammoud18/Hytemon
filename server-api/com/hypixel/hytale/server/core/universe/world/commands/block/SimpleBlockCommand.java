/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class SimpleBlockCommand extends AbstractWorldCommand {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_ERROR_EXCEPTION = Message.translation("server.commands.error.exception");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 27 */   private final RequiredArg<RelativeIntPosition> coordsArg = withRequiredArg("x y z", "server.commands.block.set.arg.coords", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SimpleBlockCommand(@Nonnull String name, @Nonnull String description) {
/* 36 */     super(name, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 41 */     CommandSender sender = context.sender();
/* 42 */     RelativeIntPosition coords = (RelativeIntPosition)context.get((Argument)this.coordsArg);
/*    */     
/* 44 */     Vector3i blockPos = coords.getBlockPosition(context, (ComponentAccessor)store);
/* 45 */     int x = blockPos.x;
/* 46 */     int y = blockPos.y;
/* 47 */     int z = blockPos.z;
/*    */     
/* 49 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(x, z);
/* 50 */     world.getChunkAsync(chunkIndex).thenAcceptAsync(chunk -> executeWithBlock(context, chunk, x, y, z), (Executor)world)
/* 51 */       .exceptionally(t -> {
/*    */           ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Error getting chunk for command");
/*    */           sender.sendMessage(MESSAGE_COMMANDS_ERROR_EXCEPTION);
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   protected abstract void executeWithBlock(@Nonnull CommandContext paramCommandContext, @Nonnull WorldChunk paramWorldChunk, int paramInt1, int paramInt2, int paramInt3);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\SimpleBlockCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */