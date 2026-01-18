/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ItemReticle;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ class ItemReticle
/*     */   implements NetworkSerializable<ItemReticle>
/*     */ {
/*     */   public static final BuilderCodec<ItemReticle> CODEC;
/*     */   protected boolean hideBase;
/*     */   protected String[] parts;
/*     */   
/*     */   static {
/* 177 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemReticle.class, ItemReticle::new).append(new KeyedCodec("HideBase", (Codec)Codec.BOOLEAN), (itemReticle, o) -> itemReticle.hideBase = o.booleanValue(), itemReticle -> Boolean.valueOf(itemReticle.hideBase)).documentation("Specifies whether the base reticle should be hidden while the configured parts are shown.").add()).append(new KeyedCodec("Parts", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (itemReticle, o) -> itemReticle.parts = o, itemReticle -> itemReticle.parts).documentation("A list of reticle parts that should be displayed for this configuration.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)CommonAssetValidator.UI_RETICLE_PARTS_ARRAY).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemReticle(boolean hideBase, String[] parts) {
/* 183 */     this.hideBase = hideBase;
/* 184 */     this.parts = parts;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemReticle() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemReticle toPacket() {
/* 193 */     ItemReticle packet = new ItemReticle();
/* 194 */     packet.hideBase = this.hideBase;
/* 195 */     packet.parts = this.parts;
/* 196 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 202 */     return "ItemReticle{, hideBase=" + this.hideBase + ", parts=" + 
/*     */       
/* 204 */       Arrays.toString((Object[])this.parts) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemReticleConfig$ItemReticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */