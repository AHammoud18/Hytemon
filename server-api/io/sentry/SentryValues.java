/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class SentryValues<T> {
/*    */   private final List<T> values;
/*    */   
/*    */   SentryValues(@Nullable List<T> values) {
/* 12 */     if (values == null) {
/* 13 */       values = new ArrayList<>(0);
/*    */     }
/* 15 */     this.values = new ArrayList<>(values);
/*    */   }
/*    */   @NotNull
/*    */   public List<T> getValues() {
/* 19 */     return this.values;
/*    */   }
/*    */   
/*    */   public static final class JsonKeys {
/*    */     public static final String VALUES = "values";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */