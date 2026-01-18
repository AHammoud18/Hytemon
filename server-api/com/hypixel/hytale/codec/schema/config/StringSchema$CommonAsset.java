/*     */ package com.hypixel.hytale.codec.schema.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonAsset
/*     */ {
/*     */   public static final BuilderCodec<CommonAsset> CODEC;
/*     */   private String[] requiredRoots;
/*     */   private String requiredExtension;
/*     */   private boolean isUIAsset;
/*     */   
/*     */   static {
/* 197 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CommonAsset.class, CommonAsset::new).addField(new KeyedCodec("requiredRoots", (Codec)Codec.STRING_ARRAY, false, true), (o, i) -> o.requiredRoots = i, o -> o.requiredRoots)).addField(new KeyedCodec("requiredExtension", (Codec)Codec.STRING, false, true), (o, i) -> o.requiredExtension = i, o -> o.requiredExtension)).addField(new KeyedCodec("isUIAsset", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.isUIAsset = i.booleanValue(), o -> Boolean.valueOf(o.isUIAsset))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommonAsset(String requiredExtension, boolean isUIAsset, String... requiredRoots) {
/* 204 */     this.requiredRoots = requiredRoots;
/* 205 */     this.requiredExtension = requiredExtension;
/* 206 */     this.isUIAsset = isUIAsset;
/*     */   }
/*     */ 
/*     */   
/*     */   protected CommonAsset() {}
/*     */   
/*     */   public String[] getRequiredRoots() {
/* 213 */     return this.requiredRoots;
/*     */   }
/*     */   
/*     */   public String getRequiredExtension() {
/* 217 */     return this.requiredExtension;
/*     */   }
/*     */   
/*     */   public boolean isUIAsset() {
/* 221 */     return this.isUIAsset;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\StringSchema$CommonAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */