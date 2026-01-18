/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum SentryLogLevel
/*    */   implements JsonSerializable {
/*  9 */   TRACE(1),
/* 10 */   DEBUG(5),
/* 11 */   INFO(9),
/* 12 */   WARN(13),
/* 13 */   ERROR(17),
/* 14 */   FATAL(21);
/*    */   
/*    */   private final int severityNumber;
/*    */   
/*    */   SentryLogLevel(int severityNumber) {
/* 19 */     this.severityNumber = severityNumber;
/*    */   }
/*    */   
/*    */   public int getSeverityNumber() {
/* 23 */     return this.severityNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 29 */     writer.value(name().toLowerCase(Locale.ROOT));
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SentryLogLevel>
/*    */   {
/*    */     @NotNull
/*    */     public SentryLogLevel deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 37 */       return SentryLogLevel.valueOf(reader.nextString().toUpperCase(Locale.ROOT));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLogLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */