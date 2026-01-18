/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.ModelAttachment;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticAssetValidator;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticType;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelAttachment
/*     */   implements NetworkSerializable<ModelAttachment>
/*     */ {
/*     */   public static final BuilderCodec<ModelAttachment> CODEC;
/*     */   protected String model;
/*     */   protected String texture;
/*     */   protected String gradientSet;
/*     */   protected String gradientId;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelAttachment.class, ModelAttachment::new).append(new KeyedCodec("Model", (Codec)Codec.STRING), (modelAttachment, s) -> modelAttachment.model = s, modelAttachment -> modelAttachment.model).addValidator((Validator)CommonAssetValidator.MODEL_CHARACTER_ATTACHMENT).add()).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (modelAttachment, s) -> modelAttachment.texture = s, modelAttachment -> modelAttachment.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_CHARACTER_ATTACHMENT).add()).append(new KeyedCodec("GradientSet", (Codec)Codec.STRING), (modelAttachment, s) -> modelAttachment.gradientSet = s, modelAttachment -> modelAttachment.gradientSet).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Dropdown("GradientSets"))).addValidator((Validator)new CosmeticAssetValidator(CosmeticType.GRADIENT_SETS)).add()).append(new KeyedCodec("GradientId", (Codec)Codec.STRING), (modelAttachment, s) -> modelAttachment.gradientId = s, modelAttachment -> modelAttachment.gradientId).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Dropdown("GradientIds"))).add()).addField(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (modelAttachment, aDouble) -> modelAttachment.weight = aDouble.doubleValue(), modelAttachment -> Double.valueOf(modelAttachment.weight))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected double weight = 1.0D;
/*     */   
/*     */   public ModelAttachment(String model, String texture, String gradientSet, String gradientId, double weight) {
/*  60 */     this.model = model;
/*  61 */     this.texture = texture;
/*  62 */     this.gradientSet = gradientSet;
/*  63 */     this.gradientId = gradientId;
/*  64 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/*  71 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getTexture() {
/*  75 */     return this.texture;
/*     */   }
/*     */   
/*     */   public String getGradientId() {
/*  79 */     return this.gradientId;
/*     */   }
/*     */   
/*     */   public String getGradientSet() {
/*  83 */     return this.gradientSet;
/*     */   }
/*     */   
/*     */   public double getWeight() {
/*  87 */     return this.weight;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ModelAttachment toPacket() {
/*  93 */     ModelAttachment packet = new ModelAttachment();
/*  94 */     packet.model = this.model;
/*  95 */     packet.texture = this.texture;
/*  96 */     packet.gradientSet = this.gradientSet;
/*  97 */     packet.gradientId = this.gradientId;
/*  98 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 104 */     return "ModelAttachment{model='" + this.model + "', texture='" + this.texture + "', gradientSet='" + this.gradientSet + "', gradientId='" + this.gradientId + "', weight=" + this.weight + "}";
/*     */   }
/*     */   
/*     */   protected ModelAttachment() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\ModelAttachment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */