/*    */ package com.hypixel.hytale.codec.lookup;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Priority
/*    */ {
/*    */   @Nonnull
/* 11 */   public static Priority DEFAULT = new Priority(-1000);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 16 */   public static Priority NORMAL = new Priority(0);
/*    */   
/*    */   private int level;
/*    */   
/*    */   public Priority(int level) {
/* 21 */     this.level = level;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 25 */     return this.level;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Priority before() {
/* 30 */     return before(1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Priority before(int by) {
/* 35 */     return new Priority(this.level - by);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Priority after() {
/* 40 */     return after(1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Priority after(int by) {
/* 45 */     return new Priority(this.level - by);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 50 */     if (this == o) return true; 
/* 51 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 53 */     Priority priority = (Priority)o;
/*    */     
/* 55 */     return (this.level == priority.level);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return this.level;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\lookup\Priority.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */