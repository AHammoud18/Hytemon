/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.vendor.gson.stream.JsonToken;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.TimeZone;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public interface ObjectReader extends Closeable {
/*    */   @Nullable
/*    */   static Date dateOrNull(@Nullable String dateString, @NotNull ILogger logger) {
/* 16 */     if (dateString == null) {
/* 17 */       return null;
/*    */     }
/*    */     try {
/* 20 */       return DateUtils.getDateTime(dateString);
/* 21 */     } catch (Exception ignored) {
/*    */       try {
/* 23 */         return DateUtils.getDateTimeWithMillisPrecision(dateString);
/* 24 */       } catch (Exception e) {
/* 25 */         logger.log(SentryLevel.ERROR, "Error when deserializing millis timestamp format.", e);
/*    */ 
/*    */         
/* 28 */         return null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   void nextUnknown(ILogger paramILogger, Map<String, Object> paramMap, String paramString);
/*    */   
/*    */   @Nullable
/*    */   <T> List<T> nextListOrNull(@NotNull ILogger paramILogger, @NotNull JsonDeserializer<T> paramJsonDeserializer) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   <T> Map<String, T> nextMapOrNull(@NotNull ILogger paramILogger, @NotNull JsonDeserializer<T> paramJsonDeserializer) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   <T> Map<String, List<T>> nextMapOfListOrNull(@NotNull ILogger paramILogger, @NotNull JsonDeserializer<T> paramJsonDeserializer) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   <T> T nextOrNull(@NotNull ILogger paramILogger, @NotNull JsonDeserializer<T> paramJsonDeserializer) throws Exception;
/*    */   
/*    */   @Nullable
/*    */   Date nextDateOrNull(ILogger paramILogger) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   TimeZone nextTimeZoneOrNull(ILogger paramILogger) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Object nextObjectOrNull() throws IOException;
/*    */   
/*    */   @NotNull
/*    */   JsonToken peek() throws IOException;
/*    */   
/*    */   @NotNull
/*    */   String nextName() throws IOException;
/*    */   
/*    */   void beginObject() throws IOException;
/*    */   
/*    */   void endObject() throws IOException;
/*    */   
/*    */   void beginArray() throws IOException;
/*    */   
/*    */   void endArray() throws IOException;
/*    */   
/*    */   boolean hasNext() throws IOException;
/*    */   
/*    */   int nextInt() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Integer nextIntegerOrNull() throws IOException;
/*    */   
/*    */   long nextLong() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Long nextLongOrNull() throws IOException;
/*    */   
/*    */   String nextString() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   String nextStringOrNull() throws IOException;
/*    */   
/*    */   boolean nextBoolean() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Boolean nextBooleanOrNull() throws IOException;
/*    */   
/*    */   double nextDouble() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Double nextDoubleOrNull() throws IOException;
/*    */   
/*    */   float nextFloat() throws IOException;
/*    */   
/*    */   @Nullable
/*    */   Float nextFloatOrNull() throws IOException;
/*    */   
/*    */   void nextNull() throws IOException;
/*    */   
/*    */   void setLenient(boolean paramBoolean);
/*    */   
/*    */   void skipValue() throws IOException;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ObjectReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */