/*     */ package com.hypixel.hytale.server.core.ui;
/*     */ 
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
/*     */ public class Anchor
/*     */ {
/*     */   public static final BuilderCodec<Anchor> CODEC;
/*     */   private Value<Integer> left;
/*     */   private Value<Integer> right;
/*     */   private Value<Integer> top;
/*     */   private Value<Integer> bottom;
/*     */   private Value<Integer> height;
/*     */   private Value<Integer> full;
/*     */   private Value<Integer> horizontal;
/*     */   private Value<Integer> vertical;
/*     */   private Value<Integer> width;
/*     */   private Value<Integer> minWidth;
/*     */   private Value<Integer> maxWidth;
/*     */   
/*     */   static {
/*  63 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Anchor.class, Anchor::new).addField(new KeyedCodec("Left", ValueCodec.INTEGER), (p, t) -> p.left = t, p -> p.left)).addField(new KeyedCodec("Right", ValueCodec.INTEGER), (p, t) -> p.right = t, p -> p.right)).addField(new KeyedCodec("Top", ValueCodec.INTEGER), (p, t) -> p.top = t, p -> p.top)).addField(new KeyedCodec("Bottom", ValueCodec.INTEGER), (p, t) -> p.bottom = t, p -> p.bottom)).addField(new KeyedCodec("Height", ValueCodec.INTEGER), (p, t) -> p.height = t, p -> p.height)).addField(new KeyedCodec("Full", ValueCodec.INTEGER), (p, t) -> p.full = t, p -> p.full)).addField(new KeyedCodec("Horizontal", ValueCodec.INTEGER), (p, t) -> p.horizontal = t, p -> p.horizontal)).addField(new KeyedCodec("Vertical", ValueCodec.INTEGER), (p, t) -> p.vertical = t, p -> p.vertical)).addField(new KeyedCodec("Width", ValueCodec.INTEGER), (p, t) -> p.width = t, p -> p.width)).addField(new KeyedCodec("MinWidth", ValueCodec.INTEGER), (p, t) -> p.minWidth = t, p -> p.minWidth)).addField(new KeyedCodec("MaxWidth", ValueCodec.INTEGER), (p, t) -> p.maxWidth = t, p -> p.maxWidth)).build();
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
/*     */   public void setLeft(Value<Integer> left) {
/*  81 */     this.left = left;
/*     */   }
/*     */   
/*     */   public void setRight(Value<Integer> right) {
/*  85 */     this.right = right;
/*     */   }
/*     */   
/*     */   public void setTop(Value<Integer> top) {
/*  89 */     this.top = top;
/*     */   }
/*     */   
/*     */   public void setBottom(Value<Integer> bottom) {
/*  93 */     this.bottom = bottom;
/*     */   }
/*     */   
/*     */   public void setHeight(Value<Integer> height) {
/*  97 */     this.height = height;
/*     */   }
/*     */   
/*     */   public void setFull(Value<Integer> full) {
/* 101 */     this.full = full;
/*     */   }
/*     */   
/*     */   public void setHorizontal(Value<Integer> horizontal) {
/* 105 */     this.horizontal = horizontal;
/*     */   }
/*     */   
/*     */   public void setVertical(Value<Integer> vertical) {
/* 109 */     this.vertical = vertical;
/*     */   }
/*     */   
/*     */   public void setWidth(Value<Integer> width) {
/* 113 */     this.width = width;
/*     */   }
/*     */   
/*     */   public void setMinWidth(Value<Integer> minWidth) {
/* 117 */     this.minWidth = minWidth;
/*     */   }
/*     */   
/*     */   public void setMaxWidth(Value<Integer> maxWidth) {
/* 121 */     this.maxWidth = maxWidth;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\Anchor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */