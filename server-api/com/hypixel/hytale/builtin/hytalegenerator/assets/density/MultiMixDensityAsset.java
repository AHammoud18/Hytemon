/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiMixDensity;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiMixDensityAsset
/*     */   extends DensityAsset
/*     */ {
/*     */   public static final BuilderCodec<MultiMixDensityAsset> CODEC;
/*     */   
/*     */   static {
/*  29 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(MultiMixDensityAsset.class, MultiMixDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Keys", (Codec)new ArrayCodec((Codec)KeyAsset.CODEC, x$0 -> new KeyAsset[x$0]), true), (asset, v) -> asset.keyAssets = v, asset -> asset.keyAssets).add()).build();
/*     */   }
/*  31 */   private KeyAsset[] keyAssets = new KeyAsset[0];
/*     */   
/*     */   @Nonnull
/*     */   public Density build(@Nonnull DensityAsset.Argument argument) {
/*  35 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*     */     
/*  37 */     List<Density> densityInputs = buildInputs(argument, true);
/*     */     
/*  39 */     if (densityInputs.isEmpty()) {
/*  40 */       return (Density)new ConstantValueDensity(0.0D);
/*     */     }
/*  42 */     ArrayList<MultiMixDensity.Key> keys = new ArrayList<>(this.keyAssets.length);
/*  43 */     for (KeyAsset keyAsset : this.keyAssets) {
/*  44 */       if (keyAsset.densityIndex <= 0) {
/*  45 */         keys.add(new MultiMixDensity.Key(keyAsset.value, null));
/*     */ 
/*     */       
/*     */       }
/*  49 */       else if (keyAsset.densityIndex >= densityInputs.size() - 1) {
/*  50 */         LoggerUtil.getLogger().warning("Density Index out of bounds in MultiMix node " + keyAsset.densityIndex + ", valid range is [0, " + densityInputs.size() - 1 + "]");
/*  51 */         keys.add(new MultiMixDensity.Key(keyAsset.value, null));
/*     */       }
/*     */       else {
/*     */         
/*  55 */         Density density = densityInputs.get(keyAsset.densityIndex);
/*  56 */         keys.add(new MultiMixDensity.Key(keyAsset.value, density));
/*     */       } 
/*     */     } 
/*     */     int i;
/*  60 */     for (i = 1; i < keys.size(); ) {
/*  61 */       MultiMixDensity.Key previousKey = keys.get(i - 1);
/*  62 */       MultiMixDensity.Key currentKey = keys.get(i);
/*     */       
/*  64 */       if (previousKey.value() == currentKey.value()) {
/*  65 */         keys.remove(i);
/*     */         
/*     */         continue;
/*     */       } 
/*  69 */       i++;
/*     */     } 
/*     */ 
/*     */     
/*  73 */     for (i = 0; i < keys.size(); ) {
/*  74 */       if (((MultiMixDensity.Key)keys.get(i)).density() == null) {
/*  75 */         keys.remove(i);
/*     */         
/*     */         continue;
/*     */       } 
/*  79 */       i++;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     for (i = keys.size() - 1; i >= 0 && (
/*  84 */       (MultiMixDensity.Key)keys.get(i)).density() == null; i--) {
/*  85 */       keys.remove(i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     for (i = keys.size() - 2; i >= 0; i--) {
/*  92 */       if (((MultiMixDensity.Key)keys.get(i)).density() == null && ((MultiMixDensity.Key)keys.get(i + 1)).density() == null) {
/*  93 */         keys.remove(i);
/*     */       }
/*     */     } 
/*  96 */     if (keys.isEmpty()) {
/*  97 */       return (Density)new ConstantValueDensity(0.0D);
/*     */     }
/*     */     
/* 100 */     if (keys.size() == 1) {
/* 101 */       return ((MultiMixDensity.Key)keys.getFirst()).density();
/*     */     }
/*     */     
/* 104 */     keys.trimToSize();
/*     */     
/* 106 */     Density influenceDensity = densityInputs.getLast();
/* 107 */     return (Density)new MultiMixDensity(keys, influenceDensity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class KeyAsset
/*     */     implements JsonAssetWithMap<String, DefaultAssetMap<String, KeyAsset>>
/*     */   {
/*     */     public static final int NO_DENSITY_INDEX = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     public static final AssetBuilderCodec<String, KeyAsset> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     private String id;
/*     */ 
/*     */     
/*     */     private AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 132 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(KeyAsset.class, KeyAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Value", (Codec)Codec.DOUBLE, true), (t, value) -> t.value = value.doubleValue(), t -> Double.valueOf(t.value)).add()).append(new KeyedCodec("DensityIndex", (Codec)Codec.INTEGER, true), (t, value) -> t.densityIndex = value.intValue(), t -> Integer.valueOf(t.densityIndex)).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 137 */     private double value = 0.0D;
/* 138 */     private int densityIndex = 0;
/*     */ 
/*     */     
/*     */     public String getId() {
/* 142 */       return this.id;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 148 */     cleanUpInputs();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\MultiMixDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */