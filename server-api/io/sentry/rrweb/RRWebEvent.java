/*    */ package io.sentry.rrweb;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.ObjectReader;
/*    */ import io.sentry.ObjectWriter;
/*    */ import io.sentry.util.Objects;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public abstract class RRWebEvent {
/*    */   @NotNull
/*    */   private RRWebEventType type;
/*    */   private long timestamp;
/*    */   
/*    */   protected RRWebEvent(@NotNull RRWebEventType type) {
/* 16 */     this.type = type;
/* 17 */     this.timestamp = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   protected RRWebEvent() {
/* 21 */     this(RRWebEventType.Custom);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public RRWebEventType getType() {
/* 26 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(@NotNull RRWebEventType type) {
/* 30 */     this.type = type;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 34 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public void setTimestamp(long timestamp) {
/* 38 */     this.timestamp = timestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 43 */     if (this == o) return true; 
/* 44 */     if (!(o instanceof RRWebEvent)) return false; 
/* 45 */     RRWebEvent that = (RRWebEvent)o;
/* 46 */     return (this.timestamp == that.timestamp && this.type == that.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     return Objects.hash(new Object[] { this.type, Long.valueOf(this.timestamp) });
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class JsonKeys
/*    */   {
/*    */     public static final String TYPE = "type";
/*    */     
/*    */     public static final String TIMESTAMP = "timestamp";
/*    */     
/*    */     public static final String TAG = "tag";
/*    */   }
/*    */   
/*    */   public static final class Serializer
/*    */   {
/*    */     public void serialize(@NotNull RRWebEvent baseEvent, @NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 67 */       writer.name("type").value(logger, baseEvent.type);
/* 68 */       writer.name("timestamp").value(baseEvent.timestamp);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class Deserializer
/*    */   {
/*    */     public boolean deserializeValue(@NotNull RRWebEvent baseEvent, @NotNull String nextName, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 80 */       switch (nextName) {
/*    */         case "type":
/* 82 */           baseEvent.type = 
/* 83 */             (RRWebEventType)Objects.requireNonNull(reader
/* 84 */               .nextOrNull(logger, new RRWebEventType.Deserializer()), "");
/* 85 */           return true;
/*    */         case "timestamp":
/* 87 */           baseEvent.timestamp = reader.nextLong();
/* 88 */           return true;
/*    */       } 
/* 90 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */