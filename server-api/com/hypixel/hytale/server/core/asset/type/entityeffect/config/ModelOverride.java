/*    */ package com.hypixel.hytale.server.core.asset.type.entityeffect.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.AnimationSet;
/*    */ import com.hypixel.hytale.protocol.ModelOverride;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelOverride
/*    */   implements NetworkSerializable<ModelOverride>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ModelOverride> CODEC;
/*    */   @Nullable
/*    */   protected String model;
/*    */   @Nullable
/*    */   protected String texture;
/*    */   
/*    */   static {
/* 51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelOverride.class, ModelOverride::new).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING, true), (modelOverride, s) -> modelOverride.model = s, modelOverride -> modelOverride.model, (modelOverride, parent) -> modelOverride.model = parent.model).addValidator((Validator)CommonAssetValidator.MODEL_CHARACTER).add()).appendInherited(new KeyedCodec("Texture", (Codec)Codec.STRING, true), (modelOverride, s) -> modelOverride.texture = s, modelOverride -> modelOverride.texture, (modelOverride, parent) -> modelOverride.texture = parent.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_CHARACTER).add()).appendInherited(new KeyedCodec("AnimationSets", (Codec)new MapCodec((Codec)ModelAsset.AnimationSet.CODEC, java.util.HashMap::new)), (modelOverride, m) -> modelOverride.animationSetMap = m, modelOverride -> modelOverride.animationSetMap, (modelOverride, parent) -> modelOverride.animationSetMap = parent.animationSetMap).add()).build();
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
/*    */   
/*    */   @Nonnull
/* 69 */   protected Map<String, ModelAsset.AnimationSet> animationSetMap = Collections.emptyMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ModelOverride toPacket() {
/* 80 */     ModelOverride packet = new ModelOverride();
/*    */     
/* 82 */     packet.model = this.model;
/* 83 */     packet.texture = this.texture;
/*    */     
/* 85 */     if (!this.animationSetMap.isEmpty()) {
/* 86 */       Object2ObjectOpenHashMap<String, AnimationSet> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(this.animationSetMap.size());
/* 87 */       for (Map.Entry<String, ModelAsset.AnimationSet> entry : this.animationSetMap.entrySet()) {
/* 88 */         object2ObjectOpenHashMap.put(entry.getKey(), ((ModelAsset.AnimationSet)entry.getValue()).toPacket(entry.getKey()));
/*    */       }
/* 90 */       packet.animationSets = (Map)object2ObjectOpenHashMap;
/*    */     } 
/*    */     
/* 93 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 99 */     return "ModelOverride{model='" + this.model + "', texture='" + this.texture + "', animationSetMap=" + String.valueOf(this.animationSetMap) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\entityeffect\config\ModelOverride.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */