/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CraftingBench
/*     */   extends Bench
/*     */ {
/*     */   public static final BuilderCodec<CraftingBench> CODEC;
/*     */   protected BenchCategory[] categories;
/*     */   
/*     */   static {
/*  24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CraftingBench.class, CraftingBench::new, Bench.BASE_CODEC).append(new KeyedCodec("Categories", (Codec)new ArrayCodec((Codec)BenchCategory.CODEC, x$0 -> new BenchCategory[x$0])), (bench, s) -> bench.categories = s, bench -> bench.categories).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public BenchCategory[] getCategories() {
/*  29 */     return this.categories;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  34 */     if (this == o) return true; 
/*  35 */     if (o == null || getClass() != o.getClass()) return false; 
/*  36 */     if (!super.equals(o)) return false;
/*     */     
/*  38 */     CraftingBench that = (CraftingBench)o;
/*     */ 
/*     */     
/*  41 */     return Arrays.equals((Object[])this.categories, (Object[])that.categories);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  46 */     int result = super.hashCode();
/*  47 */     result = 31 * result + Arrays.hashCode((Object[])this.categories);
/*  48 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BenchCategory
/*     */   {
/*     */     public static final BuilderCodec<BenchCategory> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String id;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String name;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String icon;
/*     */ 
/*     */ 
/*     */     
/*     */     protected CraftingBench.BenchItemCategory[] itemCategories;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  77 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchCategory.class, BenchCategory::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.id = s, benchCategory -> benchCategory.id)).append(new KeyedCodec("Name", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.name = s, benchCategory -> benchCategory.name).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.benchCategories.{id}"))).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (benchCategory, s) -> benchCategory.icon = s, benchCategory -> benchCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_CRAFTING).add()).addField(new KeyedCodec("ItemCategories", (Codec)new ArrayCodec((Codec)CraftingBench.BenchItemCategory.CODEC, x$0 -> new CraftingBench.BenchItemCategory[x$0])), (benchCategory, s) -> benchCategory.itemCategories = s, benchCategory -> benchCategory.itemCategories)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BenchCategory(String id, String name, String icon, CraftingBench.BenchItemCategory[] itemCategories) {
/*  85 */       this.id = id;
/*  86 */       this.name = name;
/*  87 */       this.icon = icon;
/*  88 */       this.itemCategories = itemCategories;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BenchCategory() {}
/*     */     
/*     */     public String getId() {
/*  95 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  99 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getIcon() {
/* 103 */       return this.icon;
/*     */     }
/*     */     
/*     */     public CraftingBench.BenchItemCategory[] getItemCategories() {
/* 107 */       return this.itemCategories;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 113 */       return "BenchCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', itemCategories='" + 
/*     */ 
/*     */ 
/*     */         
/* 117 */         Arrays.toString((Object[])this.itemCategories) + "'}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BenchItemCategory
/*     */   {
/*     */     public static final BuilderCodec<BenchItemCategory> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String icon;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String diagram;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 158 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchItemCategory.class, BenchItemCategory::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.id = s, benchItemCategory -> benchItemCategory.id)).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.name = s, benchItemCategory -> benchItemCategory.name)).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.icon = s, benchItemCategory -> benchItemCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_CRAFTING).add()).append(new KeyedCodec("Diagram", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.diagram = s, benchItemCategory -> benchItemCategory.diagram).addValidator((Validator)CommonAssetValidator.UI_CRAFTING_DIAGRAM).add()).addField(new KeyedCodec("Slots", (Codec)Codec.INTEGER), (benchItemCategory, s) -> benchItemCategory.slots = s.intValue(), benchItemCategory -> Integer.valueOf(benchItemCategory.slots))).addField(new KeyedCodec("SpecialSlot", (Codec)Codec.BOOLEAN), (benchItemCategory, s) -> benchItemCategory.specialSlot = s.booleanValue(), benchItemCategory -> Boolean.valueOf(benchItemCategory.specialSlot))).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     protected int slots = 1;
/*     */     protected boolean specialSlot = true;
/*     */     
/*     */     public BenchItemCategory(String id, String name, String icon, String diagram, int slots, boolean specialSlot) {
/* 168 */       this.id = id;
/* 169 */       this.name = name;
/* 170 */       this.icon = icon;
/* 171 */       this.diagram = diagram;
/* 172 */       this.slots = slots;
/* 173 */       this.specialSlot = specialSlot;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getId() {
/* 180 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 184 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getIcon() {
/* 188 */       return this.icon;
/*     */     }
/*     */     
/*     */     public String getDiagram() {
/* 192 */       return this.diagram;
/*     */     }
/*     */     
/*     */     public int getSlots() {
/* 196 */       return this.slots;
/*     */     }
/*     */     
/*     */     public boolean isSpecialSlot() {
/* 200 */       return this.specialSlot;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 206 */       return "BenchItemCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', diagram='" + this.diagram + "', slots='" + this.slots + "', specialSlot='" + this.specialSlot + "'}";
/*     */     }
/*     */     
/*     */     protected BenchItemCategory() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\CraftingBench.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */