/*    */ package io.sentry.util;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class Pair<A, B> {
/*    */   @Nullable
/*    */   private final A first;
/*    */   
/*    */   public Pair(@Nullable A first, @Nullable B second) {
/* 12 */     this.first = first;
/* 13 */     this.second = second;
/*    */   } @Nullable
/*    */   private final B second; @Nullable
/*    */   public A getFirst() {
/* 17 */     return this.first;
/*    */   }
/*    */   @Nullable
/*    */   public B getSecond() {
/* 21 */     return this.second;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */