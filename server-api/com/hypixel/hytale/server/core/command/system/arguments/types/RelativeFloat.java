/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class RelativeFloat
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<RelativeFloat> CODEC;
/*     */   private float value;
/*     */   private boolean isRelative;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RelativeFloat.class, RelativeFloat::new).append(new KeyedCodec("Value", (Codec)Codec.FLOAT), (o, i) -> o.value = i.floatValue(), RelativeFloat::getRawValue).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Relative", (Codec)Codec.BOOLEAN), (o, i) -> o.isRelative = i.booleanValue(), RelativeFloat::isRelative).addValidator(Validators.nonNull()).add()).build();
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
/*     */   public RelativeFloat(float value, boolean isRelative) {
/*  57 */     this.value = value;
/*  58 */     this.isRelative = isRelative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RelativeFloat() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static RelativeFloat parse(@Nonnull String input, @Nonnull ParseResult parseResult) {
/*  78 */     boolean relative = input.contains("~");
/*  79 */     input = input.replaceAll(Pattern.quote("~"), "");
/*     */     try {
/*     */       float value;
/*  82 */       if (input.isBlank()) {
/*  83 */         value = 0.0F;
/*     */       } else {
/*  85 */         value = Float.parseFloat(input);
/*     */       } 
/*  87 */       return new RelativeFloat(value, relative);
/*  88 */     } catch (Exception e) {
/*  89 */       parseResult.fail(Message.raw("Invalid float: " + input));
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRawValue() {
/*  98 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/* 105 */     return this.isRelative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float resolve(float baseValue) {
/* 115 */     return this.isRelative ? (baseValue + this.value) : this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 121 */     return (this.isRelative ? "~" : "") + (this.isRelative ? "~" : "");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeFloat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */