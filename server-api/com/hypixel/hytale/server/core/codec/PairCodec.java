/*     */ package com.hypixel.hytale.server.core.codec;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import it.unimi.dsi.fastutil.Pair;
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
/*     */ public class PairCodec
/*     */ {
/*     */   public static class IntegerPair
/*     */   {
/*     */     public static final BuilderCodec<IntegerPair> CODEC;
/*     */     private Integer left;
/*     */     private Integer right;
/*     */     
/*     */     static {
/*  35 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IntegerPair.class, IntegerPair::new).append(new KeyedCodec("Left", (Codec)Codec.INTEGER), (pair, left) -> pair.left = left, pair -> pair.left).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Right", (Codec)Codec.INTEGER), (pair, right) -> pair.right = right, pair -> pair.right).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntegerPair() {}
/*     */ 
/*     */     
/*     */     public IntegerPair(Integer left, Integer right) {
/*  44 */       this.left = left;
/*  45 */       this.right = right;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Pair<Integer, Integer> toPair() {
/*  50 */       return Pair.of(this.left, this.right);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static IntegerPair fromPair(@Nonnull Pair<Integer, Integer> pair) {
/*  55 */       return new IntegerPair((Integer)pair.left(), (Integer)pair.right());
/*     */     }
/*     */     
/*     */     public Integer getLeft() {
/*  59 */       return this.left;
/*     */     }
/*     */     
/*     */     public Integer getRight() {
/*  63 */       return this.right;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IntegerStringPair
/*     */   {
/*     */     public static final BuilderCodec<IntegerStringPair> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     private Integer left;
/*     */ 
/*     */ 
/*     */     
/*     */     private String right;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  86 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IntegerStringPair.class, IntegerStringPair::new).append(new KeyedCodec("Left", (Codec)Codec.INTEGER), (pair, left) -> pair.left = left, pair -> pair.left).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Right", (Codec)Codec.STRING), (pair, right) -> pair.right = right, pair -> pair.right).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntegerStringPair() {}
/*     */ 
/*     */     
/*     */     public IntegerStringPair(Integer left, String right) {
/*  95 */       this.left = left;
/*  96 */       this.right = right;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Pair<Integer, String> toPair() {
/* 101 */       return Pair.of(this.left, this.right);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static IntegerStringPair fromPair(@Nonnull Pair<Integer, String> pair) {
/* 106 */       return new IntegerStringPair((Integer)pair.left(), (String)pair.right());
/*     */     }
/*     */     
/*     */     public Integer getLeft() {
/* 110 */       return this.left;
/*     */     }
/*     */     
/*     */     public String getRight() {
/* 114 */       return this.right;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\codec\PairCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */