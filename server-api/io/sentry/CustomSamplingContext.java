/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CustomSamplingContext
/*    */ {
/*    */   @NotNull
/* 14 */   private final Map<String, Object> data = new HashMap<>();
/*    */   
/*    */   public void set(@NotNull String key, @Nullable Object value) {
/* 17 */     Objects.requireNonNull(key, "key is required");
/* 18 */     this.data.put(key, value);
/*    */   }
/*    */   @Nullable
/*    */   public Object get(@NotNull String key) {
/* 22 */     Objects.requireNonNull(key, "key is required");
/* 23 */     return this.data.get(key);
/*    */   }
/*    */   @NotNull
/*    */   public Map<String, Object> getData() {
/* 27 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CustomSamplingContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */