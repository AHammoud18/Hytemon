/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockInspectFillerCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 31 */   private static final Message MESSAGE_COMMANDS_BLOCK_INSPECT_FILLER_DONE = Message.translation("server.commands.block.inspectfiller.done");
/*    */   @Nonnull
/* 33 */   private static final Message MESSAGE_COMMANDS_BLOCK_INSPECT_FILLER_NO_BLOCKS = Message.translation("server.commands.block.inspectfiller.noblocks");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockInspectFillerCommand() {
/* 39 */     super("inspectfiller", "server.commands.block.inspectfiller.desc");
/* 40 */     setPermissionGroup(null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 45 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 46 */     assert transformComponent != null;
/*    */     
/* 48 */     Vector3d position = transformComponent.getPosition();
/*    */     
/* 50 */     int x = MathUtil.floor(position.getX());
/* 51 */     int z = MathUtil.floor(position.getZ());
/* 52 */     int y = MathUtil.floor(position.getY());
/*    */     
/* 54 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 55 */     int chunkY = ChunkUtil.chunkCoordinate(y);
/* 56 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*    */     
/* 58 */     CompletableFutureUtil._catch(world.getChunkStore().getChunkSectionReferenceAsync(chunkX, chunkY, chunkZ)
/* 59 */         .thenAcceptAsync(chunk -> {
/*    */             Store<ChunkStore> chunkStore = chunk.getStore();
/*    */             BlockSection blockSection = (BlockSection)chunkStore.getComponent(chunk, BlockSection.getComponentType());
/*    */             if (blockSection == null) {
/*    */               playerRef.sendMessage(MESSAGE_COMMANDS_BLOCK_INSPECT_FILLER_NO_BLOCKS);
/*    */               return;
/*    */             } 
/*    */             BlockTypeAssetMap<String, BlockType> blockTypeMap = BlockType.getAssetMap();
/*    */             IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxMap = BlockBoundingBoxes.getAssetMap();
/*    */             Vector3d offset = new Vector3d(ChunkUtil.minBlock(chunkX), ChunkUtil.minBlock(chunkY), ChunkUtil.minBlock(chunkZ));
/*    */             for (int idx = 0; idx < 32768; idx++) {
/*    */               int blockId = blockSection.get(idx);
/*    */               BlockType blockType = (BlockType)blockTypeMap.getAsset(blockId);
/*    */               if (blockType != null) {
/*    */                 BlockBoundingBoxes hitbox = (BlockBoundingBoxes)hitboxMap.getAsset(blockType.getHitboxTypeIndex());
/*    */                 if (hitbox != null && hitbox.protrudesUnitBox()) {
/*    */                   int filler = blockSection.getFiller(idx);
/*    */                   int bx = ChunkUtil.xFromIndex(idx);
/*    */                   int by = ChunkUtil.yFromIndex(idx);
/*    */                   int bz = ChunkUtil.zFromIndex(idx);
/*    */                   Vector3d pos = new Vector3d(bx, by, bz);
/*    */                   pos.add(0.5D, 0.5D, 0.5D);
/*    */                   pos.add(offset);
/*    */                   int rotation = blockSection.getRotationIndex(idx);
/*    */                   BlockBoundingBoxes.RotatedVariantBoxes rotatedHitbox = hitbox.get(rotation);
/*    */                   int fillerX = FillerBlockUtil.unpackX(filler);
/*    */                   int fillerY = FillerBlockUtil.unpackY(filler);
/*    */                   int fillerZ = FillerBlockUtil.unpackZ(filler);
/*    */                   Box boundingBox = rotatedHitbox.getBoundingBox();
/*    */                   int minX = (int)boundingBox.min.x;
/*    */                   int minY = (int)boundingBox.min.y;
/*    */                   int minZ = (int)boundingBox.min.z;
/*    */                   if (minX - boundingBox.min.x > 0.0D)
/*    */                     minX--; 
/*    */                   if (minY - boundingBox.min.y > 0.0D)
/*    */                     minY--; 
/*    */                   if (minZ - boundingBox.min.z > 0.0D)
/*    */                     minZ--; 
/*    */                   int maxX = (int)boundingBox.max.x;
/*    */                   int maxY = (int)boundingBox.max.y;
/*    */                   int maxZ = (int)boundingBox.max.z;
/*    */                   if (boundingBox.max.x - maxX > 0.0D)
/*    */                     maxX++; 
/*    */                   if (boundingBox.max.y - maxY > 0.0D)
/*    */                     maxY++; 
/*    */                   if (boundingBox.max.z - maxZ > 0.0D)
/*    */                     maxZ++; 
/*    */                   Vector3f colour = new Vector3f();
/*    */                   colour.x = (fillerX - minX) / (maxX - minX);
/*    */                   colour.y = (fillerY - minY) / (maxY - minY);
/*    */                   colour.z = (fillerZ - minZ) / (maxZ - minZ);
/*    */                   DebugUtils.addCube(((ChunkStore)chunkStore.getExternalData()).getWorld(), pos, colour, 1.05D, 30.0F);
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */             playerRef.sendMessage(MESSAGE_COMMANDS_BLOCK_INSPECT_FILLER_DONE);
/*    */           }(Executor)world));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockInspectFillerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */