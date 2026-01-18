/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum SentryLevel
/*    */   implements JsonSerializable {
/*  9 */   DEBUG,
/* 10 */   INFO,
/* 11 */   WARNING,
/* 12 */   ERROR,
/* 13 */   FATAL;
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 18 */     writer.value(name().toLowerCase(Locale.ROOT));
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SentryLevel>
/*    */   {
/*    */     @NotNull
/*    */     public SentryLevel deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 26 */       return SentryLevel.valueOf(reader.nextString().toUpperCase(Locale.ROOT));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */