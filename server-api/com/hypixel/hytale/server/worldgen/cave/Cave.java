/*    */ package com.hypixel.hytale.server.worldgen.cave;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.WorldBounds;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class Cave {
/*    */   private static final Comparator<CaveNode> ORDER;
/*    */   
/*    */   static {
/* 20 */     ORDER = Comparator.comparingInt(o -> o.getCaveNodeType().getPriority());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private Long2ObjectMap<List<CaveNode>> rawChunkNodeMap;
/*    */   
/*    */   private Long2ObjectMap<CaveNode[]> chunkNodeMap;
/*    */   
/*    */   private final CaveType caveType;
/*    */   
/*    */   @Nonnull
/*    */   private final WorldBounds bounds;
/*    */   
/*    */   private int nodeCount;
/*    */ 
/*    */   
/*    */   public Cave(CaveType caveType) {
/* 39 */     this.rawChunkNodeMap = (Long2ObjectMap<List<CaveNode>>)new Long2ObjectOpenHashMap();
/* 40 */     this.caveType = caveType;
/* 41 */     this.bounds = new WorldBounds();
/* 42 */     this.nodeCount = 0;
/*    */   }
/*    */   
/*    */   public long getNodeCount() {
/* 46 */     return this.nodeCount;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CaveType getCaveType() {
/* 51 */     return this.caveType;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public WorldBounds getBounds() {
/* 56 */     return this.bounds;
/*    */   }
/*    */   
/*    */   public void addNode(@Nonnull CaveNode element) {
/* 60 */     element.compile();
/*    */     
/* 62 */     this.bounds.include((IChunkBounds)element.getBounds());
/* 63 */     element.forEachChunk(chunk -> {
/*    */           List<CaveNode> nodes = (List<CaveNode>)this.rawChunkNodeMap.get(chunk);
/*    */           if (nodes == null) {
/*    */             this.rawChunkNodeMap.put(chunk, nodes = new ArrayList<>());
/*    */           }
/*    */           nodes.add(element);
/*    */         });
/* 70 */     this.nodeCount++;
/*    */   }
/*    */   
/*    */   public boolean contains(long chunkIndex) {
/* 74 */     return this.chunkNodeMap.containsKey(chunkIndex);
/*    */   }
/*    */   
/*    */   public CaveNode[] getCaveNodes(long chunkIndex) {
/* 78 */     return (CaveNode[])this.chunkNodeMap.get(chunkIndex);
/*    */   }
/*    */   
/*    */   public void compile() {
/* 82 */     compileNodeMap();
/*    */   }
/*    */   
/*    */   private void compileNodeMap() {
/* 86 */     this.chunkNodeMap = (Long2ObjectMap<CaveNode[]>)new Long2ObjectOpenHashMap();
/* 87 */     for (ObjectIterator<Long2ObjectMap.Entry<List<CaveNode>>> objectIterator = this.rawChunkNodeMap.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<List<CaveNode>> entry = objectIterator.next();
/* 88 */       CaveNode[] array = (CaveNode[])((List)entry.getValue()).toArray(x$0 -> new CaveNode[x$0]);
/* 89 */       ObjectArrays.mergeSort((Object[])array, ORDER);
/* 90 */       this.chunkNodeMap.put(entry.getLongKey(), array); }
/*    */     
/* 92 */     this.rawChunkNodeMap = null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 98 */     return "Cave{rawChunkNodeMap=" + String.valueOf(this.rawChunkNodeMap) + ", chunkNodeMap=" + String.valueOf(this.chunkNodeMap) + ", caveType=" + String.valueOf(this.caveType) + ", bounds=" + String.valueOf(this.bounds) + ", nodeCount=" + this.nodeCount + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\Cave.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */