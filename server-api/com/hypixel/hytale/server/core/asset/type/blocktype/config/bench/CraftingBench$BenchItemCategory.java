/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BenchItemCategory
/*     */ {
/*     */   public static final BuilderCodec<BenchItemCategory> CODEC;
/*     */   protected String id;
/*     */   protected String name;
/*     */   protected String icon;
/*     */   protected String diagram;
/*     */   
/*     */   static {
/* 158 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchItemCategory.class, BenchItemCategory::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.id = s, benchItemCategory -> benchItemCategory.id)).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.name = s, benchItemCategory -> benchItemCategory.name)).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.icon = s, benchItemCategory -> benchItemCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_CRAFTING).add()).append(new KeyedCodec("Diagram", (Codec)Codec.STRING), (benchItemCategory, s) -> benchItemCategory.diagram = s, benchItemCategory -> benchItemCategory.diagram).addValidator((Validator)CommonAssetValidator.UI_CRAFTING_DIAGRAM).add()).addField(new KeyedCodec("Slots", (Codec)Codec.INTEGER), (benchItemCategory, s) -> benchItemCategory.slots = s.intValue(), benchItemCategory -> Integer.valueOf(benchItemCategory.slots))).addField(new KeyedCodec("SpecialSlot", (Codec)Codec.BOOLEAN), (benchItemCategory, s) -> benchItemCategory.specialSlot = s.booleanValue(), benchItemCategory -> Boolean.valueOf(benchItemCategory.specialSlot))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   protected int slots = 1;
/*     */   protected boolean specialSlot = true;
/*     */   
/*     */   public BenchItemCategory(String id, String name, String icon, String diagram, int slots, boolean specialSlot) {
/* 168 */     this.id = id;
/* 169 */     this.name = name;
/* 170 */     this.icon = icon;
/* 171 */     this.diagram = diagram;
/* 172 */     this.slots = slots;
/* 173 */     this.specialSlot = specialSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 180 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 184 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 188 */     return this.icon;
/*     */   }
/*     */   
/*     */   public String getDiagram() {
/* 192 */     return this.diagram;
/*     */   }
/*     */   
/*     */   public int getSlots() {
/* 196 */     return this.slots;
/*     */   }
/*     */   
/*     */   public boolean isSpecialSlot() {
/* 200 */     return this.specialSlot;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "BenchItemCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', diagram='" + this.diagram + "', slots='" + this.slots + "', specialSlot='" + this.specialSlot + "'}";
/*     */   }
/*     */   
/*     */   protected BenchItemCategory() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\CraftingBench$BenchItemCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */