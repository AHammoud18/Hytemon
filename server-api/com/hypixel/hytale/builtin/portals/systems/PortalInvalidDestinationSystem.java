/*    */ package com.hypixel.hytale.builtin.portals.systems;
/*    */ import com.hypixel.hytale.builtin.portals.components.PortalDevice;
/*    */ import com.hypixel.hytale.builtin.portals.components.PortalDeviceConfig;
/*    */ import com.hypixel.hytale.builtin.portals.utils.BlockTypeUtils;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.AndQuery;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.UUID;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PortalInvalidDestinationSystem extends RefSystem<ChunkStore> {
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 27 */     if (reason != AddReason.LOAD)
/*    */       return; 
/* 29 */     World originWorld = ((ChunkStore)store.getExternalData()).getWorld();
/*    */     
/* 31 */     PortalDevice portalDevice = (PortalDevice)commandBuffer.getComponent(ref, PortalDevice.getComponentType());
/* 32 */     BlockModule.BlockStateInfo blockStateInfo = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/*    */     
/* 34 */     World destinationWorld = portalDevice.getDestinationWorld();
/* 35 */     if (destinationWorld == null) {
/* 36 */       originWorld.execute(() -> turnOffPortalBlock(originWorld, portalDevice, blockStateInfo));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 47 */     return (Query<ChunkStore>)Query.and(new Query[] { (Query)PortalDevice.getComponentType(), (Query)BlockModule.BlockStateInfo.getComponentType() });
/*    */   }
/*    */   
/*    */   public static void turnOffPortalsInWorld(World originWorld, World destinationWorld) {
/* 51 */     UUID destinationWorldUuid = destinationWorld.getWorldConfig().getUuid();
/*    */     
/* 53 */     Store<ChunkStore> store = originWorld.getChunkStore().getStore();
/* 54 */     AndQuery<ChunkStore> entityQuery = Query.and(new Query[] { (Query)PortalDevice.getComponentType(), (Query)BlockModule.BlockStateInfo.getComponentType() });
/* 55 */     store.forEachEntityParallel((Query)entityQuery, (id, archetypeChunk, commandBuffer) -> {
/*    */           PortalDevice portalDevice = (PortalDevice)archetypeChunk.getComponent(id, PortalDevice.getComponentType());
/*    */           if (!destinationWorldUuid.equals(portalDevice.getDestinationWorldUuid()))
/*    */             return; 
/*    */           BlockModule.BlockStateInfo blockStateInfo = (BlockModule.BlockStateInfo)archetypeChunk.getComponent(id, BlockModule.BlockStateInfo.getComponentType());
/*    */           originWorld.execute(());
/*    */         });
/*    */   }
/*    */   
/*    */   private static void turnOffPortalBlock(World world, PortalDevice portalDevice, BlockModule.BlockStateInfo blockStateInfo) {
/* 65 */     Ref<ChunkStore> chunkRef = blockStateInfo.getChunkRef();
/* 66 */     if (chunkRef == null || !chunkRef.isValid())
/*    */       return; 
/* 68 */     Store<ChunkStore> store = world.getChunkStore().getStore();
/* 69 */     WorldChunk worldChunk = (WorldChunk)store.getComponent(chunkRef, WorldChunk.getComponentType());
/* 70 */     if (worldChunk == null)
/*    */       return; 
/* 72 */     int index = blockStateInfo.getIndex();
/* 73 */     int x = ChunkUtil.xFromBlockInColumn(index);
/* 74 */     int y = ChunkUtil.yFromBlockInColumn(index);
/* 75 */     int z = ChunkUtil.zFromBlockInColumn(index);
/*    */     
/* 77 */     PortalDeviceConfig config = portalDevice.getConfig();
/*    */     
/* 79 */     BlockType blockType = worldChunk.getBlockType(x, y, z);
/* 80 */     BlockType offState = BlockTypeUtils.getBlockForState(blockType, config.getOffState());
/* 81 */     if (offState == null) {
/* 82 */       HytaleLogger.getLogger().at(Level.WARNING).log("Couldn't find/set off set for portal block, either " + blockType.getId() + " is misconfigured or the block changed unexpectedly");
/*    */       
/*    */       return;
/*    */     } 
/* 86 */     worldChunk.setBlockInteractionState(x, y, z, blockType, config.getOffState(), false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\PortalInvalidDestinationSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */