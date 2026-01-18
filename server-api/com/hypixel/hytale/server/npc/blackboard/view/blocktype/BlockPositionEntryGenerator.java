/*     */ package com.hypixel.hytale.server.npc.blackboard.view.blocktype;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSectionReference;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionData;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.IBlockPositionData;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntConsumer;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.function.IntConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockPositionEntryGenerator {
/*  24 */   private final FoundBlockConsumer foundBlockConsumer = new FoundBlockConsumer();
/*  25 */   private final IntSet internalIdHolder = (IntSet)new IntOpenHashSet();
/*     */   
/*     */   @Nonnull
/*     */   public BlockPositionProvider generate(short changeCounter, int sectionIndex, @Nonnull BlockChunk chunk, IntList unifiedBlocksOfInterest, @Nonnull BitSet searchedBlockSets) {
/*  29 */     BlockSection section = chunk.getSectionAtIndex(sectionIndex);
/*     */ 
/*     */     
/*  32 */     if (section.isSolidAir()) return new BlockPositionProvider(searchedBlockSets, null, changeCounter);
/*     */     
/*  34 */     if (!section.containsAny(unifiedBlocksOfInterest)) return new BlockPositionProvider(searchedBlockSets, null, changeCounter);
/*     */     
/*  36 */     ChunkSectionReference chunkSectionPointer = new ChunkSectionReference(chunk, section, sectionIndex);
/*     */     
/*  38 */     this.foundBlockConsumer.init(chunkSectionPointer, searchedBlockSets);
/*     */     
/*  40 */     section.find(unifiedBlocksOfInterest, this.internalIdHolder, (IntConsumer)this.foundBlockConsumer);
/*  41 */     this.internalIdHolder.clear();
/*     */     
/*  43 */     Int2ObjectOpenHashMap<List<IBlockPositionData>> blockData = this.foundBlockConsumer.getBlockData();
/*  44 */     this.foundBlockConsumer.release();
/*     */     
/*  46 */     return new BlockPositionProvider(searchedBlockSets, blockData, changeCounter);
/*     */   }
/*     */   
/*     */   private static class FoundBlockConsumer implements IntConsumer {
/*  50 */     private final Int2IntMap blockSetCounts = (Int2IntMap)new Int2IntOpenHashMap();
/*     */     @Nullable
/*     */     private ChunkSectionReference sectionPointer;
/*     */     @Nullable
/*     */     private BitSet searchedBlockSets;
/*     */     private int maxBlockType;
/*     */     @Nullable
/*     */     private Int2ObjectOpenHashMap<List<IBlockPositionData>> blockData;
/*     */     
/*     */     public void init(ChunkSectionReference sectionPointer, BitSet searchedBlockSets) {
/*  60 */       this.sectionPointer = sectionPointer;
/*  61 */       this.searchedBlockSets = searchedBlockSets;
/*  62 */       this.maxBlockType = NPCPlugin.get().getMaxBlackboardBlockCountPerType();
/*  63 */       this.blockData = new Int2ObjectOpenHashMap();
/*     */     }
/*     */     
/*     */     public void release() {
/*  67 */       this.blockSetCounts.clear();
/*  68 */       this.sectionPointer = null;
/*  69 */       this.searchedBlockSets = null;
/*  70 */       this.blockData = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(int blockIndex) {
/*  75 */       BlockSetModule blockSetModule = BlockSetModule.getInstance();
/*  76 */       int type = this.sectionPointer.getSection().get(blockIndex);
/*     */       
/*  78 */       BlockPositionData data = null;
/*     */       
/*     */       int i;
/*  81 */       for (i = this.searchedBlockSets.nextSetBit(0); i >= 0; i = this.searchedBlockSets.nextSetBit(i + 1)) {
/*     */         
/*  83 */         if (blockSetModule.blockInSet(i, type)) {
/*  84 */           ObjectArrayList<BlockPositionData> objectArrayList; List<IBlockPositionData> entry = (List<IBlockPositionData>)this.blockData.getOrDefault(i, null);
/*  85 */           if (entry == null) {
/*  86 */             objectArrayList = new ObjectArrayList();
/*  87 */             this.blockData.put(i, objectArrayList);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  92 */           int count = this.blockSetCounts.getOrDefault(i, 0);
/*  93 */           if (count < this.maxBlockType) {
/*  94 */             if (data == null) data = new BlockPositionData(blockIndex, this.sectionPointer, type); 
/*  95 */             objectArrayList.add(data);
/*     */           } else {
/*  97 */             int j = RandomExtra.randomRange(count + 1);
/*  98 */             if (j < this.maxBlockType) {
/*  99 */               if (data == null) data = new BlockPositionData(blockIndex, this.sectionPointer, type); 
/* 100 */               objectArrayList.set(j, data);
/*     */             } 
/*     */           } 
/* 103 */           this.blockSetCounts.put(i, count + 1);
/*     */         } 
/*     */         
/* 106 */         if (i == Integer.MAX_VALUE) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public Int2ObjectOpenHashMap<List<IBlockPositionData>> getBlockData() {
/* 113 */       this.blockData.trim();
/* 114 */       return this.blockData;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\blocktype\BlockPositionEntryGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */