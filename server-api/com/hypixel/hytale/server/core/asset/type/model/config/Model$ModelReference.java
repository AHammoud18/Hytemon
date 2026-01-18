/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelReference
/*     */ {
/*     */   public static final BuilderCodec<ModelReference> CODEC;
/*     */   
/*     */   static {
/* 546 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelReference.class, ModelReference::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (modelReference, s) -> modelReference.modelAssetId = s, modelReference -> modelReference.modelAssetId)).addField(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (modelReference, aDouble) -> modelReference.scale = aDouble.floatValue(), modelReference -> Double.valueOf(modelReference.scale))).addField(new KeyedCodec("RandomAttachments", (Codec)MapCodec.STRING_HASH_MAP_CODEC), (modelReference, stringStringMap) -> modelReference.randomAttachmentIds = stringStringMap, modelReference -> modelReference.randomAttachmentIds)).addField(new KeyedCodec("Static", (Codec)Codec.BOOLEAN), (modelReference, b) -> modelReference.staticModel = b.booleanValue(), modelReference -> Boolean.valueOf(modelReference.staticModel))).build();
/*     */   }
/* 548 */   public static final ModelReference DEFAULT_PLAYER_MODEL = new ModelReference("Player", -1.0F, null, false);
/*     */   
/*     */   private String modelAssetId;
/*     */   private float scale;
/*     */   private Map<String, String> randomAttachmentIds;
/*     */   private boolean staticModel;
/*     */   
/*     */   public ModelReference(String modelAssetId, float scale, Map<String, String> randomAttachmentIds) {
/* 556 */     this(modelAssetId, scale, randomAttachmentIds, false);
/*     */   }
/*     */   
/*     */   public ModelReference(String modelAssetId, float scale, Map<String, String> randomAttachmentIds, boolean staticModel) {
/* 560 */     this.modelAssetId = modelAssetId;
/* 561 */     this.scale = scale;
/* 562 */     this.randomAttachmentIds = randomAttachmentIds;
/* 563 */     this.staticModel = staticModel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelReference() {}
/*     */   
/*     */   public String getModelAssetId() {
/* 570 */     return this.modelAssetId;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 574 */     return this.scale;
/*     */   }
/*     */   
/*     */   public Map<String, String> getRandomAttachmentIds() {
/* 578 */     return this.randomAttachmentIds;
/*     */   }
/*     */   
/*     */   public boolean isStaticModel() {
/* 582 */     return this.staticModel;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Model toModel() {
/* 587 */     if (this.modelAssetId == null) return null;
/*     */     
/* 589 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.modelAssetId);
/* 590 */     if (modelAsset == null) modelAsset = ModelAsset.DEBUG;
/*     */     
/* 592 */     return Model.createScaledModel(modelAsset, this.scale, this.randomAttachmentIds, null, this.staticModel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 597 */     if (this == o) return true; 
/* 598 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 600 */     ModelReference that = (ModelReference)o;
/*     */     
/* 602 */     if (Float.compare(that.scale, this.scale) != 0) return false; 
/* 603 */     if (this.staticModel != that.staticModel) return false; 
/* 604 */     if (!Objects.equals(this.modelAssetId, that.modelAssetId)) return false; 
/* 605 */     return Objects.equals(this.randomAttachmentIds, that.randomAttachmentIds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 610 */     int result = (this.modelAssetId != null) ? this.modelAssetId.hashCode() : 0;
/* 611 */     result = 31 * result + ((this.scale != 0.0F) ? Float.floatToIntBits(this.scale) : 0);
/* 612 */     result = 31 * result + ((this.randomAttachmentIds != null) ? this.randomAttachmentIds.hashCode() : 0);
/* 613 */     result = 31 * result + (this.staticModel ? 1 : 0);
/* 614 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 620 */     return "ModelReference{modelAssetId='" + this.modelAssetId + "', scale=" + this.scale + ", randomAttachmentIds=" + String.valueOf(this.randomAttachmentIds) + ", staticModel=" + this.staticModel + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\Model$ModelReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */