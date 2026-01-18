/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BenchCategory
/*     */ {
/*     */   public static final BuilderCodec<BenchCategory> CODEC;
/*     */   protected String id;
/*     */   protected String name;
/*     */   protected String icon;
/*     */   protected CraftingBench.BenchItemCategory[] itemCategories;
/*     */   
/*     */   static {
/*  77 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchCategory.class, BenchCategory::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.id = s, benchCategory -> benchCategory.id)).append(new KeyedCodec("Name", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.name = s, benchCategory -> benchCategory.name).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.benchCategories.{id}"))).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.icon = s, benchCategory -> benchCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_CRAFTING).add()).addField(new KeyedCodec("ItemCategories", (Codec)new ArrayCodec((Codec)CraftingBench.BenchItemCategory.CODEC, x$0 -> new CraftingBench.BenchItemCategory[x$0])), (benchCategory, s) -> benchCategory.itemCategories = s, benchCategory -> benchCategory.itemCategories)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BenchCategory(String id, String name, String icon, CraftingBench.BenchItemCategory[] itemCategories) {
/*  85 */     this.id = id;
/*  86 */     this.name = name;
/*  87 */     this.icon = icon;
/*  88 */     this.itemCategories = itemCategories;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BenchCategory() {}
/*     */   
/*     */   public String getId() {
/*  95 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  99 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 103 */     return this.icon;
/*     */   }
/*     */   
/*     */   public CraftingBench.BenchItemCategory[] getItemCategories() {
/* 107 */     return this.itemCategories;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 113 */     return "BenchCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', itemCategories='" + 
/*     */ 
/*     */ 
/*     */       
/* 117 */       Arrays.toString((Object[])this.itemCategories) + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\CraftingBench$BenchCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */