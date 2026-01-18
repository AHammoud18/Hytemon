/*    */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshInterval
/*    */ {
/*    */   public static final BuilderCodec<RefreshInterval> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RefreshInterval.class, RefreshInterval::new).append(new KeyedCodec("Days", (Codec)Codec.INTEGER), (interval, i) -> interval.days = i.intValue(), interval -> Integer.valueOf(interval.days)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).build();
/*    */   }
/* 23 */   protected int days = 1;
/*    */   
/*    */   public RefreshInterval(int days) {
/* 26 */     this.days = days;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDays() {
/* 33 */     return this.days;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "RefreshInterval{days=" + this.days + "}";
/*    */   }
/*    */   
/*    */   protected RefreshInterval() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\RefreshInterval.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */