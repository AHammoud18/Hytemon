/*    */ package io.sentry.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class Objects
/*    */ {
/*    */   public static <T> T requireNonNull(@Nullable T obj, @NotNull String message) {
/* 13 */     if (obj == null) throw new IllegalArgumentException(message); 
/* 14 */     return obj;
/*    */   }
/*    */   
/*    */   public static boolean equals(@Nullable Object a, @Nullable Object b) {
/* 18 */     return (a == b || (a != null && a.equals(b)));
/*    */   }
/*    */   
/*    */   public static int hash(@Nullable Object... values) {
/* 22 */     return Arrays.hashCode(values);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\Objects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */