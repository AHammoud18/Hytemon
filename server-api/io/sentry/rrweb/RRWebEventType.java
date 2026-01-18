/*    */ package io.sentry.rrweb;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.JsonDeserializer;
/*    */ import io.sentry.JsonSerializable;
/*    */ import io.sentry.ObjectReader;
/*    */ import io.sentry.ObjectWriter;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum RRWebEventType implements JsonSerializable {
/* 12 */   DomContentLoaded,
/* 13 */   Load,
/* 14 */   FullSnapshot,
/* 15 */   IncrementalSnapshot,
/* 16 */   Meta,
/* 17 */   Custom,
/* 18 */   Plugin;
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 23 */     writer.value(ordinal());
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<RRWebEventType> {
/*    */     @NotNull
/*    */     public RRWebEventType deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 30 */       return RRWebEventType.values()[reader.nextInt()];
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebEventType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */