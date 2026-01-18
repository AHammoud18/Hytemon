/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashSetIntCondition
/*    */   implements IIntCondition
/*    */ {
/*    */   protected final IntSet set;
/*    */   
/*    */   public HashSetIntCondition(IntSet set) {
/* 16 */     this.set = set;
/*    */   }
/*    */   
/*    */   public IntSet getSet() {
/* 20 */     return this.set;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int i) {
/* 25 */     return this.set.contains(i);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "HashSetIntCondition{set=" + String.valueOf(this.set) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\HashSetIntCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */