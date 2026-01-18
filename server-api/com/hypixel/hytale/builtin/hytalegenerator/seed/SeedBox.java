/*    */ package com.hypixel.hytale.builtin.hytalegenerator.seed;
/*    */ 
/*    */ import java.util.Random;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SeedBox {
/*    */   @Nonnull
/*    */   private final String key;
/*    */   
/*    */   public SeedBox(@Nonnull String key) {
/* 12 */     this.key = key;
/*    */   }
/*    */   
/*    */   public SeedBox(int key) {
/* 16 */     this.key = Integer.toString(key);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SeedBox child(@Nonnull String childKey) {
/* 21 */     return new SeedBox(this.key + this.key);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Supplier<Integer> createSupplier() {
/* 26 */     Random rand = new Random(this.key.hashCode());
/* 27 */     return () -> Integer.valueOf(rand.nextInt());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "SeedBox{value='" + this.key + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\seed\SeedBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */