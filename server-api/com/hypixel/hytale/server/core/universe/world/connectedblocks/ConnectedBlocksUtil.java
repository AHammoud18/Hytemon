/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntPair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Queue;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectedBlocksUtil
/*     */ {
/*     */   private static final int MAX_UPDATE_DEPTH = 3;
/*     */   
/*     */   public static void setConnectedBlockAndNotifyNeighbors(int blockTypeId, @Nonnull RotationTuple blockTypeRotation, @Nonnull Vector3i placementNormal, @Nonnull Vector3i blockPosition, @Nonnull WorldChunk worldChunkComponent, @Nonnull BlockChunk blockChunkComponent) {
/*  31 */     Vector3i coordinate = new Vector3i(blockPosition);
/*  32 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockTypeId);
/*     */     
/*  34 */     if (blockType == null)
/*     */       return; 
/*  36 */     BlockSection sectionAtY = blockChunkComponent.getSectionAtBlockY(blockPosition.y);
/*  37 */     int filler = sectionAtY.getFiller(blockPosition.x, blockPosition.y, blockPosition.z);
/*     */ 
/*     */     
/*  40 */     int settings = 132;
/*  41 */     if (blockType.getConnectedBlockRuleSet() != null && filler == 0) {
/*  42 */       int rotationIndex = blockTypeRotation.index();
/*  43 */       Optional<ConnectedBlockResult> foundPattern = getDesiredConnectedBlockType(worldChunkComponent.getWorld(), coordinate, blockType, rotationIndex, placementNormal, true);
/*     */       
/*  45 */       if (foundPattern.isPresent() && (!((ConnectedBlockResult)foundPattern.get()).blockTypeKey().equals(blockType.getId()) || ((ConnectedBlockResult)foundPattern.get()).rotationIndex != rotationIndex)) {
/*     */         
/*  47 */         ConnectedBlockResult result = foundPattern.get();
/*  48 */         int id = BlockType.getAssetMap().getIndex(result.blockTypeKey());
/*  49 */         int rotation = result.rotationIndex();
/*  50 */         worldChunkComponent.setBlock(coordinate.x, coordinate.y, coordinate.z, id, (BlockType)BlockType.getAssetMap().getAsset(id), rotation, 0, settings);
/*     */       } 
/*     */     } 
/*     */     
/*  54 */     updateNeighborsWithDepth(worldChunkComponent, coordinate, placementNormal, settings);
/*     */   }
/*     */   
/*     */   private static void updateNeighborsWithDepth(@Nonnull WorldChunk worldChunkComponent, @Nonnull Vector3i startCoordinate, @Nonnull Vector3i placementNormal, int settings) {
/*     */     static final class QueueEntry extends Record {
/*     */       private final Vector3i coordinate;
/*     */       private final int depth;
/*     */       
/*     */       QueueEntry(Vector3i coordinate, int depth) {
/*  63 */         this.coordinate = coordinate; this.depth = depth; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #63	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #63	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry; } public final boolean equals(Object o) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #63	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lcom/hypixel/hytale/server/core/universe/world/connectedblocks/ConnectedBlocksUtil$1QueueEntry;
/*  63 */         //   0	8	1	o	Ljava/lang/Object; } public Vector3i coordinate() { return this.coordinate; } public int depth() { return this.depth; }
/*     */     
/*     */     };
/*  66 */     Queue<QueueEntry> queue = new ArrayDeque<>();
/*  67 */     ObjectOpenHashSet<Vector3i> objectOpenHashSet = new ObjectOpenHashSet();
/*  68 */     queue.add(new QueueEntry(new Vector3i(startCoordinate), 0));
/*     */     
/*  70 */     while (!queue.isEmpty()) {
/*  71 */       QueueEntry entry = queue.poll();
/*  72 */       Vector3i coordinate = entry.coordinate;
/*  73 */       int depth = entry.depth;
/*     */       
/*  75 */       Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*  76 */       notifyNeighborsAndCollectChanges(worldChunkComponent
/*  77 */           .getWorld(), coordinate, (Map<Vector3i, ConnectedBlockResult>)object2ObjectOpenHashMap, placementNormal);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  83 */       for (Map.Entry<Vector3i, ConnectedBlockResult> result : (Iterable<Map.Entry<Vector3i, ConnectedBlockResult>>)object2ObjectOpenHashMap.entrySet()) {
/*  84 */         Vector3i location = result.getKey();
/*  85 */         ConnectedBlockResult connectedBlockResult = result.getValue();
/*     */         
/*  87 */         if (!objectOpenHashSet.add(location.clone()))
/*     */           continue; 
/*  89 */         if (location.x == coordinate.x && location.y == coordinate.y && location.z == coordinate.z)
/*     */           continue; 
/*  91 */         WorldChunk newWorldChunk = worldChunkComponent;
/*  92 */         long chunkIndex = ChunkUtil.indexChunkFromBlock(location.x, location.z);
/*  93 */         if (chunkIndex != newWorldChunk.getIndex()) {
/*  94 */           newWorldChunk = worldChunkComponent.getWorld().getChunkIfLoaded(chunkIndex);
/*  95 */           if (newWorldChunk == null)
/*     */             continue; 
/*     */         } 
/*  98 */         int blockId = BlockType.getAssetMap().getIndex(connectedBlockResult.blockTypeKey());
/*  99 */         BlockType block = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 100 */         newWorldChunk.setBlock(location.x, location.y, location.z, blockId, block, connectedBlockResult.rotationIndex(), 0, settings);
/*     */         
/* 102 */         for (Map.Entry<Vector3i, ObjectIntPair<String>> additionalEntry : connectedBlockResult.getAdditionalConnectedBlocks().entrySet()) {
/* 103 */           Vector3i offset = additionalEntry.getKey();
/* 104 */           ObjectIntPair<String> blockData = additionalEntry.getValue();
/* 105 */           Vector3i additionalLocation = (new Vector3i(location)).add(offset);
/*     */           
/* 107 */           WorldChunk additionalChunk = newWorldChunk;
/* 108 */           long additionalChunkIndex = ChunkUtil.indexChunkFromBlock(additionalLocation.x, additionalLocation.z);
/* 109 */           if (additionalChunkIndex != newWorldChunk.getIndex()) {
/* 110 */             additionalChunk = worldChunkComponent.getWorld().getChunkIfLoaded(additionalChunkIndex);
/* 111 */             if (additionalChunk == null)
/*     */               continue; 
/*     */           } 
/* 114 */           int additionalBlockId = BlockType.getAssetMap().getIndex(blockData.first());
/* 115 */           BlockType additionalBlock = (BlockType)BlockType.getAssetMap().getAsset(additionalBlockId);
/* 116 */           if (additionalBlock != null) {
/* 117 */             additionalChunk.setBlock(additionalLocation.x, additionalLocation.y, additionalLocation.z, additionalBlockId, additionalBlock, blockData.rightInt(), 0, settings);
/*     */           }
/*     */         } 
/*     */         
/* 121 */         if (depth + 1 < 3) {
/* 122 */           queue.add(new QueueEntry(location.clone(), depth + 1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifyNeighborsAndCollectChanges(@Nonnull World world, @Nonnull Vector3i origin, @Nonnull Map<Vector3i, ConnectedBlockResult> desiredChanges, Vector3i placementNormal) {
/* 132 */     Vector3i coordinate = origin.clone();
/*     */     
/* 134 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(origin.x, origin.z);
/* 135 */     WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
/*     */     
/* 137 */     for (int x1 = -1; x1 <= 1; x1++) {
/* 138 */       for (int z1 = -1; z1 <= 1; z1++) {
/* 139 */         for (int y1 = -1; y1 <= 1; y1++) {
/* 140 */           if (x1 != 0 || y1 != 0 || z1 != 0) {
/*     */             
/* 142 */             coordinate.assign(origin).add(x1, y1, z1);
/*     */             
/* 144 */             if (coordinate.y >= 0 && coordinate.y < 320)
/*     */             {
/* 146 */               if (!desiredChanges.containsKey(coordinate)) {
/*     */                 
/* 148 */                 long neighborChunkIndex = ChunkUtil.indexChunkFromBlock(coordinate.x, coordinate.z);
/* 149 */                 if (neighborChunkIndex != chunkIndex) {
/* 150 */                   chunkIndex = neighborChunkIndex;
/* 151 */                   chunk = world.getChunkIfLoaded(neighborChunkIndex);
/*     */                 } 
/* 153 */                 if (chunk != null) {
/*     */                   
/* 155 */                   BlockChunk blockChunk = chunk.getBlockChunk();
/* 156 */                   if (blockChunk != null) {
/*     */                     
/* 158 */                     BlockSection blockSection = blockChunk.getSectionAtBlockY(coordinate.y);
/* 159 */                     if (blockSection != null) {
/*     */                       
/* 161 */                       int neighborBlockId = blockSection.get(coordinate.x, coordinate.y, coordinate.z);
/* 162 */                       BlockType neighborBlockType = (BlockType)BlockType.getAssetMap().getAsset(neighborBlockId);
/* 163 */                       if (neighborBlockType != null) {
/*     */                         
/* 165 */                         ConnectedBlockRuleSet ruleSet = neighborBlockType.getConnectedBlockRuleSet();
/*     */                         
/* 167 */                         if (ruleSet != null && 
/* 168 */                           !ruleSet.onlyUpdateOnPlacement()) {
/*     */                           
/* 170 */                           int filler = blockSection.getFiller(coordinate.x, coordinate.y, coordinate.z);
/* 171 */                           int existingRotation = blockSection.getRotationIndex(coordinate.x, coordinate.y, coordinate.z);
/*     */                           
/* 173 */                           if (filler != 0) {
/* 174 */                             int originX = coordinate.x - FillerBlockUtil.unpackX(filler);
/* 175 */                             int originY = coordinate.y - FillerBlockUtil.unpackY(filler);
/* 176 */                             int originZ = coordinate.z - FillerBlockUtil.unpackZ(filler);
/*     */                             
/* 178 */                             coordinate.assign(originX, originY, originZ);
/*     */                           } 
/*     */                           
/* 181 */                           Optional<ConnectedBlockResult> output = getDesiredConnectedBlockType(world, coordinate, neighborBlockType, existingRotation, placementNormal, false);
/*     */                           
/* 183 */                           if (output.isPresent() && (
/* 184 */                             !neighborBlockType.getId().equals(((ConnectedBlockResult)output.get()).blockTypeKey()) || ((ConnectedBlockResult)output.get()).rotationIndex != existingRotation))
/* 185 */                             desiredChanges.put(coordinate.clone(), output.get()); 
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Optional<ConnectedBlockResult> getDesiredConnectedBlockType(@Nonnull World world, @Nonnull Vector3i coordinate, @Nonnull BlockType currentBlockType, int currentRotation, @Nonnull Vector3i placementNormal, boolean isPlacement) {
/* 200 */     ConnectedBlockRuleSet ruleSet = currentBlockType.getConnectedBlockRuleSet();
/* 201 */     if (ruleSet == null) return Optional.empty();
/*     */     
/* 203 */     return ruleSet.getConnectedBlockType(world, coordinate, currentBlockType, currentRotation, placementNormal, isPlacement);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ConnectedBlockResult
/*     */   {
/*     */     private final String blockTypeKey;
/*     */     
/*     */     private final int rotationIndex;
/* 212 */     private final Map<Vector3i, ObjectIntPair<String>> additionalConnectedBlocks = (Map<Vector3i, ObjectIntPair<String>>)new Object2ObjectOpenHashMap();
/*     */     
/*     */     public ConnectedBlockResult(@Nonnull String blockTypeKey, int rotationIndex) {
/* 215 */       this.blockTypeKey = blockTypeKey;
/* 216 */       this.rotationIndex = rotationIndex;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String blockTypeKey() {
/* 221 */       return this.blockTypeKey;
/*     */     }
/*     */     
/*     */     public int rotationIndex() {
/* 225 */       return this.rotationIndex;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Map<Vector3i, ObjectIntPair<String>> getAdditionalConnectedBlocks() {
/* 230 */       return this.additionalConnectedBlocks;
/*     */     }
/*     */     
/*     */     public void addAdditionalBlock(@Nonnull Vector3i offset, @Nonnull String blockTypeKey, int rotationIndex) {
/* 234 */       this.additionalConnectedBlocks.put(offset, ObjectIntPair.of(blockTypeKey, rotationIndex));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 239 */       if (obj == this) return true; 
/* 240 */       if (obj == null || obj.getClass() != getClass()) return false; 
/* 241 */       ConnectedBlockResult that = (ConnectedBlockResult)obj;
/* 242 */       return (Objects.equals(this.blockTypeKey, that.blockTypeKey) && this.rotationIndex == that.rotationIndex && 
/*     */         
/* 244 */         Objects.equals(this.additionalConnectedBlocks, that.additionalConnectedBlocks));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 249 */       return Objects.hash(new Object[] { this.blockTypeKey, Integer.valueOf(this.rotationIndex), this.additionalConnectedBlocks });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 254 */       return "ConnectedBlockResult[blockTypeKey=" + this.blockTypeKey + ", rotationIndex=" + this.rotationIndex + ", additionalConnectedBlocks=" + String.valueOf(this.additionalConnectedBlocks) + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlocksUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */