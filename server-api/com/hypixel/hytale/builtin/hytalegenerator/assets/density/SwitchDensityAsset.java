/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.SwitchDensity;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SwitchDensityAsset
/*     */   extends DensityAsset
/*     */ {
/*     */   public static final BuilderCodec<SwitchDensityAsset> CODEC;
/*     */   public static final String DEFAULT_STATE = "Default";
/*     */   public static final int DEFAULT_STATE_HASH = 0;
/*     */   
/*     */   static {
/*  28 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SwitchDensityAsset.class, SwitchDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("SwitchCases", (Codec)new ArrayCodec((Codec)SwitchCaseAsset.CODEC, x$0 -> new SwitchCaseAsset[x$0]), false), (t, k) -> t.switchCaseAssets = k, t -> t.switchCaseAssets).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  33 */   private SwitchCaseAsset[] switchCaseAssets = new SwitchCaseAsset[0];
/*     */   
/*     */   @Nonnull
/*     */   public Density build(@Nonnull DensityAsset.Argument argument) {
/*  37 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*     */     
/*  39 */     ArrayList<Integer> switchStates = new ArrayList<>();
/*  40 */     ArrayList<Density> densityNodes = new ArrayList<>();
/*  41 */     for (int i = 0; i < this.switchCaseAssets.length; i++) {
/*  42 */       if (this.switchCaseAssets[i] != null && (this.switchCaseAssets[i]).densityAsset != null) {
/*     */ 
/*     */         
/*  45 */         String stringState = (this.switchCaseAssets[i]).caseState;
/*  46 */         int stateHash = getHashFromState(stringState);
/*  47 */         Density densityNode = (this.switchCaseAssets[i]).densityAsset.build(argument);
/*     */         
/*  49 */         switchStates.add(Integer.valueOf(stateHash));
/*  50 */         densityNodes.add(densityNode);
/*     */       } 
/*     */     } 
/*  53 */     return (Density)new SwitchDensity(densityNodes, switchStates);
/*     */   }
/*     */   
/*     */   public static int getHashFromState(String state) {
/*  57 */     if ("Default".equals(state))
/*  58 */       return 0; 
/*  59 */     return Objects.hash(new Object[] { state });
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  64 */     cleanUpInputs();
/*  65 */     for (SwitchCaseAsset switchCaseAsset : this.switchCaseAssets) {
/*  66 */       switchCaseAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SwitchCaseAsset
/*     */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, SwitchCaseAsset>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, SwitchCaseAsset> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     private String id;
/*     */ 
/*     */ 
/*     */     
/*     */     private AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  90 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SwitchCaseAsset.class, SwitchCaseAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("CaseState", (Codec)Codec.STRING, true), (t, y) -> t.caseState = y, t -> t.caseState).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, out) -> t.densityAsset = out, t -> t.densityAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     private String caseState = "";
/*     */     
/*     */     private DensityAsset densityAsset;
/*     */     
/*     */     public String getId() {
/* 100 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanUp() {
/* 105 */       this.densityAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SwitchDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */