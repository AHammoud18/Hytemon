/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.ChangeActiveSlotInteraction;
/*    */ import java.util.Collections;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
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
/*    */ public class UnarmedInteractions
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, UnarmedInteractions>>
/*    */ {
/*    */   public static final String DEFAULT_UNARMED_ID = "Empty";
/*    */   public static final AssetBuilderCodec<String, UnarmedInteractions> CODEC;
/*    */   private static DefaultAssetMap<String, UnarmedInteractions> ASSET_MAP;
/*    */   protected AssetExtraInfo.Data data;
/*    */   protected String id;
/*    */   protected Map<InteractionType, String> interactions;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(UnarmedInteractions.class, UnarmedInteractions::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC, false)), (item, v) -> item.interactions = v, item -> item.interactions, (item, parent) -> item.interactions = parent.interactions).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).add()).afterDecode(o -> { if (o.interactions == null) o.interactions = new EnumMap<>(InteractionType.class);  o.interactions.putIfAbsent(InteractionType.SwapFrom, ChangeActiveSlotInteraction.DEFAULT_ROOT.getId()); o.interactions = Collections.unmodifiableMap(o.interactions); })).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DefaultAssetMap<String, UnarmedInteractions> getAssetMap() {
/* 49 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<String, UnarmedInteractions>)AssetRegistry.getAssetStore(UnarmedInteractions.class).getAssetMap(); 
/* 50 */     return ASSET_MAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 60 */     return this.id;
/*    */   }
/*    */   
/*    */   public Map<InteractionType, String> getInteractions() {
/* 64 */     return this.interactions;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "UnarmedInteractions{id=" + this.id + ", interactions=" + String.valueOf(this.interactions) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\UnarmedInteractions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */