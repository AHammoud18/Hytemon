/*    */ package it.unimi.dsi.fastutil.booleans;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.PrimitiveIterator;
/*    */ import java.util.function.Consumer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface BooleanIterator
/*    */   extends PrimitiveIterator<Boolean, BooleanConsumer>
/*    */ {
/*    */   @Deprecated
/*    */   default Boolean next() {
/* 46 */     return Boolean.valueOf(nextBoolean());
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
/*    */   default void forEachRemaining(BooleanConsumer action) {
/* 62 */     Objects.requireNonNull(action);
/* 63 */     while (hasNext()) {
/* 64 */       action.accept(nextBoolean());
/*    */     }
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
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Boolean> action) {
/* 78 */     Objects.requireNonNull(action); forEachRemaining((action instanceof BooleanConsumer) ? (BooleanConsumer)action : action::accept);
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
/*    */   default int skip(int n) {
/* 93 */     if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 94 */     int i = n;
/* 95 */     for (; i-- != 0 && hasNext(); nextBoolean());
/* 96 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   boolean nextBoolean();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */