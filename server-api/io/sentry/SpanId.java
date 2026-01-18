/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.LazyEvaluator;
/*    */ import java.io.IOException;
/*    */ import java.util.Objects;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class SpanId
/*    */   implements JsonSerializable
/*    */ {
/* 11 */   public static final SpanId EMPTY_ID = new SpanId("00000000-0000-0000-0000-000000000000"
/* 12 */       .replace("-", "").substring(0, 16));
/*    */   @NotNull
/*    */   private final LazyEvaluator<String> lazyValue;
/*    */   
/*    */   public SpanId(@NotNull String value) {
/* 17 */     Objects.requireNonNull(value, "value is required");
/* 18 */     this.lazyValue = new LazyEvaluator(() -> value);
/*    */   }
/*    */   
/*    */   public SpanId() {
/* 22 */     this.lazyValue = new LazyEvaluator(SentryUUID::generateSpanId);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 27 */     if (this == o) return true; 
/* 28 */     if (o == null || getClass() != o.getClass()) return false; 
/* 29 */     SpanId spanId = (SpanId)o;
/* 30 */     return ((String)this.lazyValue.getValue()).equals(spanId.lazyValue.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 35 */     return ((String)this.lazyValue.getValue()).hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return (String)this.lazyValue.getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 48 */     writer.value((String)this.lazyValue.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SpanId>
/*    */   {
/*    */     @NotNull
/*    */     public SpanId deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 57 */       return new SpanId(reader.nextString());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpanId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */