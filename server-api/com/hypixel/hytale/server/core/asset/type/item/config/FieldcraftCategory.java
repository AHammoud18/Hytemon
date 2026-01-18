/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.ItemCategory;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class FieldcraftCategory
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, FieldcraftCategory>>, NetworkSerializable<ItemCategory>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, FieldcraftCategory> CODEC;
/*    */   private static DefaultAssetMap<String, FieldcraftCategory> ASSET_MAP;
/*    */   protected AssetExtraInfo.Data data;
/*    */   protected String id;
/*    */   protected String name;
/*    */   protected String icon;
/*    */   protected int order;
/*    */   private SoftReference<ItemCategory> cachedPacket;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(FieldcraftCategory.class, FieldcraftCategory::new, (Codec)Codec.STRING, (itemCategory, k) -> itemCategory.id = k, itemCategory -> itemCategory.id, (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (itemCategory, s) -> itemCategory.name = s, itemCategory -> itemCategory.name)).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (itemCategory, s) -> itemCategory.icon = s, itemCategory -> itemCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_CRAFTING).add()).addField(new KeyedCodec("Order", (Codec)Codec.INTEGER), (itemCategory, s) -> itemCategory.order = s.intValue(), itemCategory -> Integer.valueOf(itemCategory.order))).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DefaultAssetMap<String, FieldcraftCategory> getAssetMap() {
/* 49 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<String, FieldcraftCategory>)AssetRegistry.getAssetStore(FieldcraftCategory.class).getAssetMap(); 
/* 50 */     return ASSET_MAP;
/*    */   }
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
/*    */   @Nonnull
/*    */   public ItemCategory toPacket() {
/* 68 */     ItemCategory cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 69 */     if (cached != null) return cached;
/*    */     
/* 71 */     ItemCategory packet = new ItemCategory();
/* 72 */     packet.id = this.id;
/* 73 */     packet.icon = this.icon;
/* 74 */     packet.name = this.name;
/* 75 */     packet.order = this.order;
/*    */     
/* 77 */     this.cachedPacket = new SoftReference<>(packet);
/* 78 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 83 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 87 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 91 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 97 */     return "FieldcraftCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', order=" + this.order + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\FieldcraftCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */