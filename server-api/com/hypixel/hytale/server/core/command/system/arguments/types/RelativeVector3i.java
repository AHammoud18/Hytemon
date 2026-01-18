/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelativeVector3i
/*     */ {
/*     */   @Nonnull
/*  19 */   public static final RelativeVector3i ZERO = new RelativeVector3i(new RelativeInteger(0, false), new RelativeInteger(0, false), new RelativeInteger(0, false));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final BuilderCodec<RelativeVector3i> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RelativeInteger x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RelativeInteger y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RelativeInteger z;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RelativeVector3i.class, RelativeVector3i::new).append(new KeyedCodec("X", (Codec)RelativeInteger.CODEC), (vec, val) -> vec.x = val, vec -> vec.x).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Y", (Codec)RelativeInteger.CODEC), (vec, val) -> vec.y = val, vec -> vec.y).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Z", (Codec)RelativeInteger.CODEC), (vec, val) -> vec.z = val, vec -> vec.z).addValidator(Validators.nonNull()).add()).build();
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
/*     */   public RelativeVector3i(RelativeInteger x, RelativeInteger y, RelativeInteger z) {
/*  62 */     this.x = x;
/*  63 */     this.y = y;
/*  64 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RelativeVector3i() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i resolve(int xBase, int yBase, int zBase) {
/*  85 */     return new Vector3i(this.x.resolve(xBase), this.y.resolve(yBase), this.z.resolve(zBase));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i resolve(@Nonnull Vector3i base) {
/*  96 */     return resolve(base.x, base.y, base.z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 102 */     return "{" + String.valueOf(this.x) + ", " + String.valueOf(this.y) + ", " + String.valueOf(this.z) + "}";
/*     */   }
/*     */   
/*     */   public boolean isRelativeX() {
/* 106 */     return this.x.isRelative();
/*     */   }
/*     */   
/*     */   public boolean isRelativeY() {
/* 110 */     return this.y.isRelative();
/*     */   }
/*     */   
/*     */   public boolean isRelativeZ() {
/* 114 */     return this.z.isRelative();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeVector3i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */