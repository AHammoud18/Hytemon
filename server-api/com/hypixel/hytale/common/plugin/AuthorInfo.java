/*     */ package com.hypixel.hytale.common.plugin;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*     */ public class AuthorInfo
/*     */ {
/*     */   @Nonnull
/*     */   public static final Codec<AuthorInfo> CODEC;
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String email;
/*     */   @Nullable
/*     */   private String url;
/*     */   
/*     */   static {
/*  35 */     CODEC = (Codec<AuthorInfo>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AuthorInfo.class, AuthorInfo::new).append(new KeyedCodec("Name", (Codec)Codec.STRING), (authorInfo, s) -> authorInfo.name = s, authorInfo -> authorInfo.name).add()).append(new KeyedCodec("Email", (Codec)Codec.STRING), (authorInfo, s) -> authorInfo.email = s, authorInfo -> authorInfo.email).add()).append(new KeyedCodec("Url", (Codec)Codec.STRING), (authorInfo, s) -> authorInfo.url = s, authorInfo -> authorInfo.url).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/*  60 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getEmail() {
/*  68 */     return this.email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getUrl() {
/*  76 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  84 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmail(@Nullable String email) {
/*  92 */     this.email = email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUrl(@Nullable String url) {
/* 100 */     this.url = url;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 106 */     return "AuthorInfo{name='" + this.name + "', email='" + this.email + "', url='" + this.url + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\plugin\AuthorInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */