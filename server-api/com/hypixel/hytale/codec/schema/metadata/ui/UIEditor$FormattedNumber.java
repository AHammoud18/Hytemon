/*     */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*     */ public class FormattedNumber
/*     */   implements UIEditor.EditorComponent
/*     */ {
/*     */   public static final BuilderCodec<FormattedNumber> CODEC;
/*     */   private Double step;
/*     */   private String suffix;
/*     */   private Integer maxDecimalPlaces;
/*     */   
/*     */   static {
/* 105 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FormattedNumber.class, FormattedNumber::new).addField(new KeyedCodec("step", (Codec)Codec.DOUBLE, false, true), (o, i) -> o.step = i, o -> o.step)).addField(new KeyedCodec("suffix", (Codec)Codec.STRING, false, true), (o, i) -> o.suffix = i, o -> o.suffix)).addField(new KeyedCodec("maxDecimalPlaces", (Codec)Codec.INTEGER, false, true), (o, i) -> o.maxDecimalPlaces = i, o -> o.maxDecimalPlaces)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FormattedNumber(Double step, String suffix, Integer maxDecimalPlaces) {
/* 112 */     this.step = step;
/* 113 */     this.suffix = suffix;
/* 114 */     this.maxDecimalPlaces = maxDecimalPlaces;
/*     */   }
/*     */ 
/*     */   
/*     */   public FormattedNumber() {}
/*     */   
/*     */   @Nonnull
/*     */   public FormattedNumber setStep(Double step) {
/* 122 */     this.step = step;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FormattedNumber setSuffix(String suffix) {
/* 128 */     this.suffix = suffix;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FormattedNumber setMaxDecimalPlaces(Integer maxDecimalPlaces) {
/* 134 */     this.maxDecimalPlaces = maxDecimalPlaces;
/* 135 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIEditor$FormattedNumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */