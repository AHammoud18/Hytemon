/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.LoadOperationsFromAssetOperation;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.BrushOperation;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.GlobalBrushOperation;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class ScriptedBrushAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ScriptedBrushAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ScriptedBrushAsset> CODEC;
/*     */   private static DefaultAssetMap<String, ScriptedBrushAsset> ASSET_MAP;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   static {
/*  50 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ScriptedBrushAsset.class, ScriptedBrushAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, asset -> asset.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Operations", (Codec)new ArrayCodec((Codec)BrushOperation.OPERATION_CODEC, x$0 -> new BrushOperation[x$0])), (asset, operations) -> { asset.operations = (List<BrushOperation>)new ObjectArrayList(); if (operations != null) Collections.addAll(asset.operations, operations);  }asset -> (asset.operations != null) ? asset.operations.<BrushOperation>toArray(new BrushOperation[0]) : new BrushOperation[0]).documentation("The list of brush operations to execute sequentially").add()).documentation("A scripted brush asset containing multiple brush operations that will be executed sequentially")).build();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static DefaultAssetMap<String, ScriptedBrushAsset> getAssetMap() {
/*  56 */     if (ASSET_MAP == null) {
/*  57 */       ASSET_MAP = (DefaultAssetMap<String, ScriptedBrushAsset>)AssetRegistry.getAssetStore(ScriptedBrushAsset.class).getAssetMap();
/*     */     }
/*  59 */     return ASSET_MAP;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ScriptedBrushAsset get(@Nonnull String id) {
/*  64 */     return (ScriptedBrushAsset)getAssetMap().getAsset(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected List<BrushOperation> operations = (List<BrushOperation>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getId() {
/*  77 */     return this.id;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<BrushOperation> getOperations() {
/*  82 */     return this.operations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIntoExecutor(@Nonnull BrushConfigCommandExecutor executor) {
/*  92 */     executor.getSequentialOperations().clear();
/*  93 */     executor.getGlobalOperations().clear();
/*     */     
/*  95 */     for (BrushOperation operation : this.operations) {
/*     */       
/*  97 */       if (operation instanceof LoadOperationsFromAssetOperation) {
/*  98 */         LoadOperationsFromAssetOperation loadOp = (LoadOperationsFromAssetOperation)operation;
/*  99 */         ScriptedBrushAsset targetAsset = get(loadOp.getAssetId());
/*     */         
/* 101 */         if (targetAsset != null)
/*     */         {
/* 103 */           for (BrushOperation targetOp : targetAsset.getOperations()) {
/*     */             
/* 105 */             if (targetOp instanceof GlobalBrushOperation) {
/* 106 */               executor.getGlobalOperations().put(targetOp.getName().toLowerCase(), (GlobalBrushOperation)targetOp); continue;
/* 107 */             }  if (targetOp instanceof SequenceBrushOperation)
/* 108 */               executor.getSequentialOperations().add((SequenceBrushOperation)targetOp); 
/*     */           } 
/*     */         }
/*     */         continue;
/*     */       } 
/* 113 */       if (operation instanceof GlobalBrushOperation) {
/* 114 */         executor.getGlobalOperations().put(operation.getName().toLowerCase(), (GlobalBrushOperation)operation); continue;
/* 115 */       }  if (operation instanceof SequenceBrushOperation)
/* 116 */         executor.getSequentialOperations().add((SequenceBrushOperation)operation); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\ScriptedBrushAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */