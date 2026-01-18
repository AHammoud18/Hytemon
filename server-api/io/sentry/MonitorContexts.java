/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import io.sentry.vendor.gson.stream.JsonToken;
/*    */ import java.io.IOException;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class MonitorContexts extends ConcurrentHashMap<String, Object> implements JsonSerializable {
/*    */   private static final long serialVersionUID = 3987329379811822556L;
/*    */   
/*    */   public MonitorContexts() {}
/*    */   
/*    */   public MonitorContexts(@NotNull MonitorContexts contexts) {
/* 19 */     for (Map.Entry<String, Object> entry : contexts.entrySet()) {
/* 20 */       if (entry != null) {
/* 21 */         Object value = entry.getValue();
/* 22 */         if ("trace".equals(entry.getKey()) && value instanceof SpanContext) {
/* 23 */           setTrace(new SpanContext((SpanContext)value)); continue;
/*    */         } 
/* 25 */         put(entry.getKey(), value);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private <T> T toContextType(@NotNull String key, @NotNull Class<T> clazz) {
/* 32 */     Object item = get(key);
/* 33 */     return clazz.isInstance(item) ? clazz.cast(item) : null;
/*    */   }
/*    */   @Nullable
/*    */   public SpanContext getTrace() {
/* 37 */     return toContextType("trace", SpanContext.class);
/*    */   }
/*    */   
/*    */   public void setTrace(@NotNull SpanContext traceContext) {
/* 41 */     Objects.requireNonNull(traceContext, "traceContext is required");
/* 42 */     put("trace", traceContext);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 50 */     writer.beginObject();
/*    */     
/* 52 */     List<String> sortedKeys = Collections.list(keys());
/* 53 */     Collections.sort(sortedKeys);
/* 54 */     for (String key : sortedKeys) {
/* 55 */       Object value = get(key);
/* 56 */       if (value != null) {
/* 57 */         writer.name(key).value(logger, value);
/*    */       }
/*    */     } 
/* 60 */     writer.endObject();
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<MonitorContexts>
/*    */   {
/*    */     @NotNull
/*    */     public MonitorContexts deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 68 */       MonitorContexts contexts = new MonitorContexts();
/* 69 */       reader.beginObject();
/* 70 */       while (reader.peek() == JsonToken.NAME) {
/* 71 */         String nextName = reader.nextName();
/* 72 */         switch (nextName) {
/*    */           case "trace":
/* 74 */             contexts.setTrace((new SpanContext.Deserializer()).deserialize(reader, logger));
/*    */             continue;
/*    */         } 
/* 77 */         Object object = reader.nextObjectOrNull();
/* 78 */         if (object != null) {
/* 79 */           contexts.put(nextName, object);
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 84 */       reader.endObject();
/* 85 */       return contexts;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MonitorContexts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */