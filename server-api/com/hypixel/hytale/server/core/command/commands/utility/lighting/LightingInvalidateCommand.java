/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.lighting.ChunkLightingManager;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LightingInvalidateCommand extends AbstractWorldCommand {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_INVALIDATED_LIGHTING = Message.translation("server.commands.invalidatedlighting");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 31 */   private final FlagArg oneFlag = withFlagArg("one", "server.commands.invalidatelighting.one.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightingInvalidateCommand() {
/* 37 */     super("invalidate", "server.commands.invalidatelighting.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 42 */     ChunkLightingManager chunkLighting = world.getChunkLighting();
/* 43 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 45 */     if (((Boolean)this.oneFlag.get(context)).booleanValue()) {
/* 46 */       if (!context.isPlayer()) {
/* 47 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */         
/*    */         return;
/*    */       } 
/* 51 */       Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 52 */       if (ref == null || !ref.isValid()) {
/* 53 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */         
/*    */         return;
/*    */       } 
/* 57 */       Store<EntityStore> entityStore = ref.getStore();
/*    */       
/* 59 */       TransformComponent transformComponent = (TransformComponent)entityStore.getComponent(ref, TransformComponent.getComponentType());
/* 60 */       assert transformComponent != null;
/*    */       
/* 62 */       Vector3d position = transformComponent.getPosition();
/* 63 */       long chunkIndex = ChunkUtil.indexChunkFromBlock((int)position.getX(), (int)position.getZ());
/* 64 */       int chunkX = ChunkUtil.xOfChunkIndex(chunkIndex);
/* 65 */       int chunkZ = ChunkUtil.zOfChunkIndex(chunkIndex);
/* 66 */       Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(chunkIndex);
/*    */       
/* 68 */       if (chunkReference == null || !chunkReference.isValid()) {
/*    */ 
/*    */ 
/*    */         
/* 72 */         Message errorMessage = Message.translation("server.commands.errors.chunkNotLoaded").param("chunkX", chunkX).param("chunkZ", chunkZ).param("world", world.getName());
/* 73 */         context.sendMessage(errorMessage);
/*    */         
/*    */         return;
/*    */       } 
/* 77 */       Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 78 */       BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 79 */       assert blockChunkComponent != null;
/*    */       
/* 81 */       int chunkY = MathUtil.floor(position.getY()) >> 5;
/* 82 */       BlockSection section = blockChunkComponent.getSectionAtBlockY(chunkY);
/* 83 */       section.invalidateLocalLight();
/* 84 */       blockChunkComponent.invalidateChunkSection(chunkY);
/*    */       
/* 86 */       Vector3i chunkPosition = new Vector3i(blockChunkComponent.getX(), chunkY, blockChunkComponent.getZ());
/* 87 */       chunkLighting.addToQueue(chunkPosition);
/* 88 */       context.sendMessage(Message.translation("server.commands.invalidatelighting.success")
/* 89 */           .param("chunkPosition", chunkPosition.toString()));
/*    */     } else {
/* 91 */       chunkLighting.invalidateLoadedChunks();
/* 92 */       context.sendMessage(MESSAGE_COMMANDS_INVALIDATED_LIGHTING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingInvalidateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */