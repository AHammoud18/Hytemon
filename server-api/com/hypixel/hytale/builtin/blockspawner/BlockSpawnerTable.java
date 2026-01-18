/*    */ package com.hypixel.hytale.builtin.blockspawner;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.common.map.IWeightedElement;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.codec.WeightedMapCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSpawnerTable
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockSpawnerTable>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, BlockSpawnerTable> CODEC;
/*    */   private static DefaultAssetMap<String, BlockSpawnerTable> ASSET_MAP;
/*    */   protected AssetExtraInfo.Data data;
/*    */   protected String id;
/*    */   protected IWeightedMap<BlockSpawnerEntry> entries;
/*    */   
/*    */   static {
/* 46 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockSpawnerTable.class, BlockSpawnerTable::new, (Codec)Codec.STRING, (blockSpawnerTable, id) -> blockSpawnerTable.id = id, blockSpawnerTable -> blockSpawnerTable.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Entries", (Codec)new WeightedMapCodec((Codec)BlockSpawnerEntry.CODEC, (IWeightedElement[])BlockSpawnerEntry.EMPTY_ARRAY)), (blockSpawnerTable, o) -> blockSpawnerTable.entries = o, blockSpawnerTable -> blockSpawnerTable.entries, (blockSpawnerTable, parent) -> blockSpawnerTable.entries = WeightedMap.builder((Object[])BlockSpawnerEntry.EMPTY_ARRAY).putAll(parent.entries).build()).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).validator((asset, results) -> { for (BlockSpawnerEntry entry : (BlockSpawnerEntry[])asset.getEntries().internalKeys()) { if (BlockType.getAssetMap().getIndex(entry.getBlockName()) == Integer.MIN_VALUE) results.fail("BlockName \"" + entry.getBlockName() + "\" does not exist in BlockSpawnerEntry");  }  })).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DefaultAssetMap<String, BlockSpawnerTable> getAssetMap() {
/* 51 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<String, BlockSpawnerTable>)AssetRegistry.getAssetStore(BlockSpawnerTable.class).getAssetMap(); 
/* 52 */     return ASSET_MAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockSpawnerTable(String id, @Nullable IWeightedMap<BlockSpawnerEntry> entries) {
/* 61 */     this.id = id;
/* 62 */     this.entries = (entries == null) ? WeightedMap.builder((Object[])BlockSpawnerEntry.EMPTY_ARRAY).build() : entries;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockSpawnerTable() {}
/*    */ 
/*    */   
/*    */   public String getId() {
/* 70 */     return this.id;
/*    */   }
/*    */   
/*    */   public IWeightedMap<BlockSpawnerEntry> getEntries() {
/* 74 */     return this.entries;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 79 */     if (this == o) return true; 
/* 80 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 82 */     BlockSpawnerTable table = (BlockSpawnerTable)o;
/*    */     
/* 84 */     if ((this.id != null) ? !this.id.equals(table.id) : (table.id != null)) return false; 
/* 85 */     return (this.entries != null) ? this.entries.equals(table.entries) : ((table.entries == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     int result = (this.id != null) ? this.id.hashCode() : 0;
/* 91 */     result = 31 * result + ((this.entries != null) ? this.entries.hashCode() : 0);
/* 92 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 98 */     return "BlockSpawnerTable{id='" + this.id + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\BlockSpawnerTable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */