/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemStackContainerConfig {
/* 12 */   public static final ItemStackContainerConfig DEFAULT = new ItemStackContainerConfig();
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
/*    */   public static final BuilderCodec<ItemStackContainerConfig> CODEC;
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
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemStackContainerConfig.class, ItemStackContainerConfig::new).append(new KeyedCodec("Capacity", (Codec)Codec.SHORT), (itemTool, s) -> itemTool.capacity = s.shortValue(), itemTool -> Short.valueOf(itemTool.capacity)).add()).append(new KeyedCodec("GlobalFilter", FilterType.CODEC), (itemTool, s) -> itemTool.globalFilter = s, itemTool -> itemTool.globalFilter).add()).append(new KeyedCodec("ItemTag", (Codec)Codec.STRING), (materialQuantity, s) -> materialQuantity.tag = s, materialQuantity -> materialQuantity.tag).add()).afterDecode((config, extraInfo) -> { if (config.tag != null) config.tagIndex = AssetRegistry.getOrCreateTagIndex(config.tag);  })).build();
/*    */   }
/* 37 */   protected short capacity = 0;
/* 38 */   protected FilterType globalFilter = FilterType.ALLOW_ALL;
/*    */   
/*    */   protected String tag;
/* 41 */   protected volatile int tagIndex = Integer.MIN_VALUE;
/*    */   
/*    */   public short getCapacity() {
/* 44 */     return this.capacity;
/*    */   }
/*    */   
/*    */   public FilterType getGlobalFilter() {
/* 48 */     return this.globalFilter;
/*    */   }
/*    */   
/*    */   public int getTagIndex() {
/* 52 */     return this.tagIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 58 */     return "ItemStackContainerConfig{capacity=" + this.capacity + ", globalFilter=" + String.valueOf(this.globalFilter) + ", tag='" + this.tag + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemStackContainerConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */