/*     */ package com.hypixel.hytale.metrics;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.CheckForNull;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Metric<T, R>
/*     */ {
/*     */   @Nullable
/*     */   private final Function<T, R> func;
/*     */   @CheckForNull
/*     */   private final Codec<R> codec;
/*     */   @CheckForNull
/*     */   private final Function<R, MetricsRegistry<R>> codecFunc;
/*     */   
/*     */   public Metric(@Nullable Function<T, R> func, @Nullable Codec<R> codec) {
/* 203 */     this.func = func;
/* 204 */     this.codec = codec;
/* 205 */     this.codecFunc = null;
/*     */   }
/*     */   
/*     */   public Metric(@Nullable Function<T, R> func, @Nullable Function<R, MetricsRegistry<R>> codecFunc) {
/* 209 */     this.func = func;
/* 210 */     this.codec = null;
/* 211 */     this.codecFunc = codecFunc;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BsonValue encode(T t, ExtraInfo extraInfo) {
/* 216 */     if (this.func == null) {
/* 217 */       assert this.codec != null;
/* 218 */       return this.codec.encode(null, extraInfo);
/*     */     } 
/*     */     
/* 221 */     R value = this.func.apply(t);
/* 222 */     return (value == null) ? null : getCodec(value).encode(value, extraInfo);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Codec<R> getCodec(R value) {
/* 227 */     if (this.codec != null) return this.codec; 
/* 228 */     assert this.codecFunc != null;
/* 229 */     return this.codecFunc.apply(value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\MetricsRegistry$Metric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */