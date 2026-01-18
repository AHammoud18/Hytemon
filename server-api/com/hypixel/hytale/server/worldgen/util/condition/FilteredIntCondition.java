/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilteredIntCondition
/*    */   implements IIntCondition
/*    */ {
/*    */   private final IIntCondition filter;
/*    */   private final IIntCondition condition;
/*    */   
/*    */   public FilteredIntCondition(int filterValue, IIntCondition condition) {
/* 20 */     this(new HashSetIntCondition(IntSets.singleton(filterValue)), condition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FilteredIntCondition(IIntCondition filter, IIntCondition condition) {
/* 28 */     this.filter = filter;
/* 29 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int value) {
/* 34 */     if (this.filter.eval(value)) return false; 
/* 35 */     return this.condition.eval(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "FilteredIntCondition{filter=" + String.valueOf(this.filter) + ", condition=" + String.valueOf(this.condition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\FilteredIntCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */