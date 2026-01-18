/*     */ package com.hypixel.hytale.math.vector.relative;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3l;
/*     */ import java.util.Objects;
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
/*     */ public class RelativeVector3l
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<RelativeVector3l> CODEC;
/*     */   private Vector3l vector;
/*     */   private boolean relative;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RelativeVector3l.class, RelativeVector3l::new).append(new KeyedCodec("Vector", (Codec)Vector3l.CODEC), (o, i) -> o.vector = i, RelativeVector3l::getVector).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Relative", (Codec)Codec.BOOLEAN), (o, i) -> o.relative = i.booleanValue(), RelativeVector3l::isRelative).addValidator(Validators.nonNull()).add()).build();
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
/*     */   public RelativeVector3l(@Nonnull Vector3l vector, boolean relative) {
/*  57 */     this.vector = vector;
/*  58 */     this.relative = relative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RelativeVector3l() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l getVector() {
/*  72 */     return this.vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/*  79 */     return this.relative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l resolve(@Nonnull Vector3l vector) {
/*  90 */     return this.relative ? vector.clone().add(vector) : vector.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  95 */     if (this == o) return true; 
/*  96 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  98 */     RelativeVector3l that = (RelativeVector3l)o;
/*     */     
/* 100 */     if (this.relative != that.relative) return false; 
/* 101 */     return Objects.equals(this.vector, that.vector);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int result = (this.vector != null) ? this.vector.hashCode() : 0;
/* 107 */     result = 31 * result + (this.relative ? 1 : 0);
/* 108 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 114 */     return "RelativeVector3l{vector=" + String.valueOf(this.vector) + ", relative=" + this.relative + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\relative\RelativeVector3l.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */