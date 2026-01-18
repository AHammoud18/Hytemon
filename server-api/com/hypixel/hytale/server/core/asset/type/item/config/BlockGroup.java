/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.protocol.BlockGroup;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Collection;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ @Deprecated
/*    */ public class BlockGroup
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockGroup>>, NetworkSerializable<BlockGroup> {
/* 22 */   private static final String[] DEFAULT_BLOCK_LIST = new String[0];
/*    */ 
/*    */ 
/*    */   
/*    */   public static final AssetCodec<String, BlockGroup> CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   private String id;
/*    */ 
/*    */   
/*    */   private AssetExtraInfo.Data data;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 38 */     CODEC = (AssetCodec<String, BlockGroup>)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockGroup.class, BlockGroup::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("Blocks", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.blocks = strings, blockSet -> blockSet.blocks)).build();
/*    */   }
/*    */ 
/*    */   
/* 42 */   private String[] blocks = DEFAULT_BLOCK_LIST;
/*    */   
/*    */   @Nullable
/*    */   public static BlockGroup findItemGroup(@Nonnull Item item) {
/* 46 */     String blockId = item.getBlockId();
/* 47 */     if (blockId == null) return null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     Collection<BlockGroup> values = ((DefaultAssetMap)AssetRegistry.getAssetStore(BlockGroup.class).getAssetMap()).getAssetMap().values();
/*    */     
/* 55 */     for (BlockGroup group : values) {
/* 56 */       if (ArrayUtil.contains((Object[])group.blocks, blockId)) {
/* 57 */         return group;
/*    */       }
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 66 */     return this.id;
/*    */   }
/*    */   
/*    */   public String get(int index) {
/* 70 */     return this.blocks[index];
/*    */   }
/*    */   
/*    */   public int size() {
/* 74 */     return this.blocks.length;
/*    */   }
/*    */   
/*    */   public int getIndex(@Nonnull Item item) {
/* 78 */     String id = item.getBlockId();
/*    */     
/* 80 */     return ArrayUtil.indexOf((Object[])this.blocks, id);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockGroup toPacket() {
/* 86 */     BlockGroup packet = new BlockGroup();
/* 87 */     packet.names = this.blocks;
/*    */     
/* 89 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\BlockGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */