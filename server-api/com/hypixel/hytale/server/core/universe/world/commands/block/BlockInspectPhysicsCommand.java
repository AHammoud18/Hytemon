/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockInspectPhysicsCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_COMMANDS_BLOCK_INSPECT_PHYS_NO_BLOCKS = Message.translation("server.commands.block.inspectphys.noblocks");
/*    */   @Nonnull
/* 31 */   private static final Message MESSAGE_COMMANDS_BLOCK_INSPECT_PHYS_DONE = Message.translation("server.commands.block.inspectphys.done");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   private final FlagArg ALL = withFlagArg("all", "");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockInspectPhysicsCommand() {
/* 42 */     super("inspectphys", "server.commands.block.inspectphys.desc");
/* 43 */     setPermissionGroup(null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 48 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 49 */     assert transformComponent != null;
/*    */     
/* 51 */     Vector3d position = transformComponent.getPosition();
/* 52 */     boolean all = ((Boolean)this.ALL.get(context)).booleanValue();
/*    */     
/* 54 */     int x = MathUtil.floor(position.getX());
/* 55 */     int z = MathUtil.floor(position.getZ());
/* 56 */     int y = MathUtil.floor(position.getY());
/*    */     
/* 58 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 59 */     int chunkY = ChunkUtil.chunkCoordinate(y);
/* 60 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*    */     
/* 62 */     CompletableFutureUtil._catch(world.getChunkStore().getChunkSectionReferenceAsync(chunkX, chunkY, chunkZ)
/* 63 */         .thenAcceptAsync(chunk -> {
/*    */             Store<ChunkStore> chunkStore = chunk.getStore();
/*    */             BlockPhysics blockPhysics = (BlockPhysics)chunkStore.getComponent(chunk, BlockPhysics.getComponentType());
/*    */             if (blockPhysics == null) {
/*    */               playerRef.sendMessage(MESSAGE_COMMANDS_BLOCK_INSPECT_PHYS_NO_BLOCKS);
/*    */               return;
/*    */             } 
/*    */             Vector3d offset = new Vector3d(ChunkUtil.minBlock(chunkX), ChunkUtil.minBlock(chunkY), ChunkUtil.minBlock(chunkZ));
/*    */             for (int idx = 0; idx < 32768; idx++) {
/*    */               Vector3f colour;
/*    */               int supportValue = blockPhysics.get(idx);
/*    */               if (supportValue == 0 && !all)
/*    */                 continue; 
/*    */               int bx = ChunkUtil.xFromIndex(idx);
/*    */               int by = ChunkUtil.yFromIndex(idx);
/*    */               int bz = ChunkUtil.zFromIndex(idx);
/*    */               Vector3d pos = new Vector3d(bx, by, bz);
/*    */               pos.add(0.5D, 0.5D, 0.5D);
/*    */               pos.add(offset);
/*    */               if (supportValue == 15) {
/*    */                 colour = new Vector3f(0.0F, 1.0F, 0.0F);
/*    */               } else {
/*    */                 BlockType block = world.getBlockType(pos.toVector3i());
/*    */                 if (!block.hasSupport()) {
/*    */                   if (supportValue == 0)
/*    */                     continue; 
/*    */                   colour = new Vector3f(1.0F, 1.0F, 0.0F);
/*    */                 } else if (block.getMaxSupportDistance() != 0) {
/*    */                   float len = supportValue / block.getMaxSupportDistance();
/*    */                   colour = new Vector3f(len, 0.0F, 1.0F - len);
/*    */                 } else {
/*    */                   colour = new Vector3f(0.0F, 1.0F, 1.0F);
/*    */                 } 
/*    */               } 
/*    */               DebugUtils.addCube(((ChunkStore)chunkStore.getExternalData()).getWorld(), pos, colour, 1.05D, 30.0F);
/*    */               continue;
/*    */             } 
/*    */             playerRef.sendMessage(MESSAGE_COMMANDS_BLOCK_INSPECT_PHYS_DONE);
/*    */           }(Executor)world));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockInspectPhysicsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */