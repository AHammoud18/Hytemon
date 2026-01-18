/*    */ package com.hypixel.hytale.server.core.modules.interaction.blocktrack;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.Object2IntMapCodec;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class BlockCounter implements Resource<ChunkStore> {
/*    */   public static ResourceType<ChunkStore, BlockCounter> getResourceType() {
/* 17 */     return InteractionModule.get().getBlockCounterResourceType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<BlockCounter> CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockCounter.class, BlockCounter::new).append(new KeyedCodec("BlockPlacementCounts", (Codec)new Object2IntMapCodec((Codec)Codec.STRING, Object2IntOpenHashMap::new, false)), (o, v) -> o.blockPlacementCounts = v, o -> o.blockPlacementCounts).add()).build();
/*    */   }
/* 29 */   private Object2IntMap<String> blockPlacementCounts = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */   
/*    */   public BlockCounter() {
/* 32 */     this.blockPlacementCounts.defaultReturnValue(0);
/*    */   }
/*    */   
/*    */   public BlockCounter(Object2IntMap<String> blockPlacementCounts) {
/* 36 */     this();
/* 37 */     this.blockPlacementCounts = blockPlacementCounts;
/*    */   }
/*    */   
/*    */   public void trackBlock(String blockName) {
/* 41 */     this.blockPlacementCounts.mergeInt(blockName, 1, Integer::sum);
/*    */   }
/*    */   
/*    */   public void untrackBlock(String blockName) {
/* 45 */     this.blockPlacementCounts.mergeInt(blockName, 0, (left, right) -> left - 1);
/*    */   }
/*    */   
/*    */   public int getBlockPlacementCount(String blockName) {
/* 49 */     return this.blockPlacementCounts.getInt(blockName);
/*    */   }
/*    */ 
/*    */   
/*    */   public Resource<ChunkStore> clone() {
/* 54 */     return new BlockCounter((Object2IntMap<String>)new Object2IntOpenHashMap(this.blockPlacementCounts));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\blocktrack\BlockCounter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */