/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import it.unimi.dsi.fastutil.ints.IntConsumer;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class IntConditionBuilder
/*    */   implements IntConsumer {
/*    */   private final Supplier<IntSet> setSupplier;
/*    */   private final int nullValue;
/*    */   private int first;
/*    */   @Nullable
/* 16 */   private IntSet set = null;
/*    */ 
/*    */   
/*    */   public IntConditionBuilder(Supplier<IntSet> setSupplier, int nullValue) {
/* 20 */     this.setSupplier = setSupplier;
/* 21 */     this.nullValue = nullValue;
/* 22 */     this.first = nullValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(int value) {
/* 27 */     add(value);
/*    */   }
/*    */   
/*    */   public boolean add(int value) {
/* 31 */     if (value == this.first || value == this.nullValue) {
/* 32 */       return false;
/*    */     }
/* 34 */     if (this.first == this.nullValue) {
/* 35 */       this.first = value;
/* 36 */       return true;
/*    */     } 
/* 38 */     if (this.set == null) {
/* 39 */       this.set = this.setSupplier.get();
/* 40 */       this.set.add(this.first);
/*    */     } 
/* 42 */     return this.set.add(value);
/*    */   }
/*    */   
/*    */   public IIntCondition buildOrDefault(IIntCondition defaultCondition) {
/* 46 */     if (this.first == this.nullValue) return defaultCondition; 
/* 47 */     IntSet set = this.set;
/* 48 */     if (set == null) set = IntSets.singleton(this.first); 
/* 49 */     return new HashSetIntCondition(set);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\IntConditionBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */