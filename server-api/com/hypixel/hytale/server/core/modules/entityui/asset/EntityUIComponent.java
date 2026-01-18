/*     */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.EntityUIComponent;
/*     */ import com.hypixel.hytale.protocol.Vector2f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import java.lang.ref.SoftReference;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class EntityUIComponent implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, EntityUIComponent>>, NetworkSerializable<EntityUIComponent> {
/*     */   public static final AssetCodecMapCodec<String, EntityUIComponent> CODEC;
/*     */   
/*     */   static {
/*  21 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)AssetBuilderCodec.abstractBuilder(EntityUIComponent.class).append(new KeyedCodec("HitboxOffset", (Codec)ProtocolCodecs.VECTOR2F), (config, v) -> config.hitboxOffset = v, config -> config.hitboxOffset).documentation("Offset from the centre of the entity's hitbox to display this component.").add()).build();
/*     */   }
/*     */   public static final BuilderCodec<EntityUIComponent> ABSTRACT_CODEC;
/*     */   protected String id;
/*     */   protected AssetExtraInfo.Data data;
/*  42 */   private Vector2f hitboxOffset = new Vector2f(0.0F, 0.0F);
/*     */   
/*     */   private transient SoftReference<EntityUIComponent> cachedPacket;
/*     */   
/*     */   private static AssetStore<String, EntityUIComponent, IndexedLookupTableAssetMap<String, EntityUIComponent>> ASSET_STORE;
/*     */   
/*     */   public static AssetStore<String, EntityUIComponent, IndexedLookupTableAssetMap<String, EntityUIComponent>> getAssetStore() {
/*  49 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(EntityUIComponent.class); 
/*  50 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, EntityUIComponent> getAssetMap() {
/*  54 */     return (IndexedLookupTableAssetMap<String, EntityUIComponent>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static EntityUIComponent getUnknownFor(String id) {
/*  62 */     return new Unknown(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  67 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public final EntityUIComponent toPacket() {
/*  73 */     EntityUIComponent cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/*  74 */     if (cached != null) return cached;
/*     */     
/*  76 */     EntityUIComponent packet = generatePacket();
/*  77 */     this.cachedPacket = new SoftReference<>(packet);
/*     */     
/*  79 */     return packet;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected EntityUIComponent generatePacket() {
/*  84 */     EntityUIComponent packet = new EntityUIComponent();
/*  85 */     packet.hitboxOffset = this.hitboxOffset;
/*  86 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  92 */     return "EntityUIComponentConfig{data=" + String.valueOf(this.data) + ", id='" + this.id + "', hitboxOffset='" + String.valueOf(this.hitboxOffset) + "'}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Unknown
/*     */     extends EntityUIComponent
/*     */   {
/*     */     public Unknown(String id) {
/* 101 */       this.id = id;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected EntityUIComponent generatePacket() {
/* 107 */       EntityUIComponent packet = super.generatePacket();
/* 108 */       packet.unknown = true;
/* 109 */       return packet;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\EntityUIComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */