/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask.BlockMaskAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.DirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.StaticDirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.OriginScannerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.ColumnProp;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
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
/*     */ public class ColumnPropAsset
/*     */   extends PropAsset
/*     */ {
/*     */   public static final BuilderCodec<ColumnPropAsset> CODEC;
/*     */   
/*     */   static {
/*  48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ColumnPropAsset.class, ColumnPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("ColumnBlocks", (Codec)new ArrayCodec((Codec)ColumnBlock.CODEC, x$0 -> new ColumnBlock[x$0]), true), (asset, v) -> asset.columnBlocks = v, asset -> asset.columnBlocks).add()).append(new KeyedCodec("BlockMask", (Codec)BlockMaskAsset.CODEC, false), (asset, v) -> asset.blockMaskAsset = v, asset -> asset.blockMaskAsset).add()).append(new KeyedCodec("Directionality", (Codec)DirectionalityAsset.CODEC, true), (asset, v) -> asset.directionalityAsset = v, asset -> asset.directionalityAsset).add()).append(new KeyedCodec("Scanner", (Codec)ScannerAsset.CODEC, true), (asset, v) -> asset.scannerAsset = v, asset -> asset.scannerAsset).add()).build();
/*     */   }
/*  50 */   private ColumnBlock[] columnBlocks = new ColumnBlock[0];
/*  51 */   private BlockMaskAsset blockMaskAsset = new BlockMaskAsset();
/*  52 */   private DirectionalityAsset directionalityAsset = (DirectionalityAsset)new StaticDirectionalityAsset();
/*  53 */   private ScannerAsset scannerAsset = (ScannerAsset)new OriginScannerAsset();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Prop build(@Nonnull PropAsset.Argument argument) {
/*  58 */     if (skip()) return Prop.noProp(); 
/*  59 */     if (this.directionalityAsset == null) {
/*  60 */       return Prop.noProp();
/*     */     }
/*  62 */     ArrayList<Integer> blockPositions = new ArrayList<>();
/*  63 */     ArrayList<Material> blockTypes = new ArrayList<>();
/*  64 */     for (int i = 0; i < this.columnBlocks.length; i++) {
/*  65 */       blockPositions.add(Integer.valueOf((this.columnBlocks[i]).y));
/*  66 */       blockTypes.add((this.columnBlocks[i]).materialAsset.build(argument.materialCache));
/*     */     } 
/*     */     
/*  69 */     BlockMask blockMask = null;
/*  70 */     if (this.blockMaskAsset != null) {
/*  71 */       blockMask = this.blockMaskAsset.build(argument.materialCache);
/*     */     } else {
/*  73 */       blockMask = new BlockMask();
/*     */     } 
/*  75 */     Directionality directionality = this.directionalityAsset.build(DirectionalityAsset.argumentFrom(argument));
/*  76 */     Scanner scanner = this.scannerAsset.build(ScannerAsset.argumentFrom(argument));
/*     */     
/*  78 */     return (Prop)new ColumnProp(blockPositions, blockTypes, blockMask, scanner, directionality, argument.materialCache);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  83 */     this.blockMaskAsset.cleanUp();
/*  84 */     this.directionalityAsset.cleanUp();
/*  85 */     this.scannerAsset.cleanUp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ColumnBlock
/*     */     implements JsonAssetWithMap<String, DefaultAssetMap<String, ColumnBlock>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, ColumnBlock> CODEC;
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
/* 108 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ColumnBlock.class, ColumnBlock::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Y", (Codec)Codec.INTEGER, true), (t, y) -> t.y = y.intValue(), t -> Integer.valueOf(t.y)).add()).append(new KeyedCodec("Material", (Codec)MaterialAsset.CODEC, true), (asset, value) -> asset.materialAsset = value, asset -> asset.materialAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 113 */     private int y = 1;
/* 114 */     private MaterialAsset materialAsset = new MaterialAsset("Empty", "Empty");
/*     */ 
/*     */     
/*     */     public String getId() {
/* 118 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\ColumnPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */