/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RelativeIntegerRange
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<RelativeIntegerRange> CODEC;
/*    */   private RelativeInteger min;
/*    */   private RelativeInteger max;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RelativeIntegerRange.class, RelativeIntegerRange::new).append(new KeyedCodec("Min", (Codec)RelativeInteger.CODEC), (o, i) -> o.min = i, o -> o.min).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Max", (Codec)RelativeInteger.CODEC), (o, i) -> o.max = i, o -> o.max).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RelativeIntegerRange(@Nonnull RelativeInteger min, @Nonnull RelativeInteger max) {
/* 53 */     this.min = min;
/* 54 */     this.max = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected RelativeIntegerRange() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RelativeIntegerRange(int min, int max) {
/* 72 */     this.min = new RelativeInteger(min, false);
/* 73 */     this.max = new RelativeInteger(max, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumberInRange(int base) {
/* 83 */     if (this.min.getRawValue() == this.max.getRawValue()) {
/* 84 */       return this.min.resolve(base);
/*    */     }
/*    */     
/* 87 */     return ThreadLocalRandom.current().nextInt(this.min.resolve(base), this.max.resolve(base) + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 93 */     return "{ Minimum: " + String.valueOf(this.min) + ", Maximum: " + String.valueOf(this.max) + " }";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeIntegerRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */