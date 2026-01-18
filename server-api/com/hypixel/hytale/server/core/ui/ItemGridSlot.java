/*     */ package com.hypixel.hytale.server.core.ui;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
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
/*     */ public class ItemGridSlot
/*     */ {
/*     */   public static final BuilderCodec<ItemGridSlot> CODEC;
/*     */   private ItemStack itemStack;
/*     */   private Value<PatchStyle> background;
/*     */   private Value<PatchStyle> overlay;
/*     */   private Value<PatchStyle> icon;
/*     */   private boolean isItemIncompatible;
/*     */   private String name;
/*     */   private String description;
/*     */   private boolean skipItemQualityBackground;
/*     */   private boolean isActivatable;
/*     */   private boolean isItemUncraftable;
/*     */   
/*     */   static {
/*  62 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemGridSlot.class, ItemGridSlot::new).addField(new KeyedCodec("ItemStack", (Codec)ItemStack.CODEC), (p, t) -> p.itemStack = t, p -> p.itemStack)).addField(new KeyedCodec("Background", ValueCodec.PATCH_STYLE), (p, t) -> p.background = t, p -> p.background)).addField(new KeyedCodec("Overlay", ValueCodec.PATCH_STYLE), (p, t) -> p.overlay = t, p -> p.overlay)).addField(new KeyedCodec("Icon", ValueCodec.PATCH_STYLE), (p, t) -> p.icon = t, p -> p.icon)).addField(new KeyedCodec("IsItemIncompatible", (Codec)Codec.BOOLEAN), (p, t) -> p.isItemIncompatible = t.booleanValue(), p -> Boolean.valueOf(p.isItemIncompatible))).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (p, t) -> p.name = t, p -> p.name)).addField(new KeyedCodec("Description", (Codec)Codec.STRING), (p, t) -> p.description = t, p -> p.description)).addField(new KeyedCodec("SkipItemQualityBackground", (Codec)Codec.BOOLEAN), (p, t) -> p.skipItemQualityBackground = t.booleanValue(), p -> Boolean.valueOf(p.skipItemQualityBackground))).addField(new KeyedCodec("IsActivatable", (Codec)Codec.BOOLEAN), (p, t) -> p.isActivatable = t.booleanValue(), p -> Boolean.valueOf(p.isActivatable))).addField(new KeyedCodec("IsItemUncraftable", (Codec)Codec.BOOLEAN), (p, t) -> p.isItemUncraftable = t.booleanValue(), p -> Boolean.valueOf(p.isItemUncraftable))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemGridSlot() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemGridSlot(ItemStack itemStack) {
/*  79 */     this.itemStack = itemStack;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setItemStack(ItemStack itemStack) {
/*  84 */     this.itemStack = itemStack;
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setBackground(Value<PatchStyle> background) {
/*  90 */     this.background = background;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setOverlay(Value<PatchStyle> overlay) {
/*  96 */     this.overlay = overlay;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setIcon(Value<PatchStyle> icon) {
/* 102 */     this.icon = icon;
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setItemIncompatible(boolean itemIncompatible) {
/* 108 */     this.isItemIncompatible = itemIncompatible;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setName(String name) {
/* 114 */     this.name = name;
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemGridSlot setDescription(String description) {
/* 120 */     this.description = description;
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isItemUncraftable() {
/* 125 */     return this.isItemUncraftable;
/*     */   }
/*     */   
/*     */   public void setItemUncraftable(boolean itemUncraftable) {
/* 129 */     this.isItemUncraftable = itemUncraftable;
/*     */   }
/*     */   
/*     */   public boolean isActivatable() {
/* 133 */     return this.isActivatable;
/*     */   }
/*     */   
/*     */   public void setActivatable(boolean activatable) {
/* 137 */     this.isActivatable = activatable;
/*     */   }
/*     */   
/*     */   public boolean isSkipItemQualityBackground() {
/* 141 */     return this.skipItemQualityBackground;
/*     */   }
/*     */   
/*     */   public void setSkipItemQualityBackground(boolean skipItemQualityBackground) {
/* 145 */     this.skipItemQualityBackground = skipItemQualityBackground;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\ItemGridSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */