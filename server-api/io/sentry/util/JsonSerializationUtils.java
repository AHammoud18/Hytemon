/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.ISerializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.SentryLevel;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class JsonSerializationUtils
/*     */ {
/*  26 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   @NotNull
/*     */   public static Map<String, Object> calendarToMap(@NotNull Calendar calendar) {
/*  29 */     Map<String, Object> map = new HashMap<>();
/*     */     
/*  31 */     map.put("year", Integer.valueOf(calendar.get(1)));
/*  32 */     map.put("month", Integer.valueOf(calendar.get(2)));
/*  33 */     map.put("dayOfMonth", Integer.valueOf(calendar.get(5)));
/*  34 */     map.put("hourOfDay", Integer.valueOf(calendar.get(11)));
/*  35 */     map.put("minute", Integer.valueOf(calendar.get(12)));
/*  36 */     map.put("second", Integer.valueOf(calendar.get(13)));
/*     */     
/*  38 */     return map;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static List<Object> atomicIntegerArrayToList(@NotNull AtomicIntegerArray array) {
/*  43 */     int numberOfItems = array.length();
/*  44 */     List<Object> list = new ArrayList(numberOfItems);
/*  45 */     for (int i = 0; i < numberOfItems; i++) {
/*  46 */       list.add(Integer.valueOf(array.get(i)));
/*     */     }
/*  48 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static byte[] bytesFrom(@NotNull ISerializer serializer, @NotNull ILogger logger, @NotNull JsonSerializable serializable) {
/*     */     try {
/*  56 */       ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*  57 */       try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8));
/*     */         
/*  59 */         try { serializer.serialize(serializable, writer);
/*     */           
/*  61 */           byte[] arrayOfByte = stream.toByteArray();
/*  62 */           writer.close(); stream.close(); return arrayOfByte; } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; } 
/*  63 */     } catch (Throwable t) {
/*  64 */       logger.log(SentryLevel.ERROR, "Could not serialize serializable", t);
/*  65 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long byteSizeOf(@NotNull ISerializer serializer, @NotNull ILogger logger, @Nullable JsonSerializable serializable) {
/*  83 */     if (serializable == null) {
/*  84 */       return 0L;
/*     */     }
/*     */     try {
/*  87 */       ByteCountingWriter writer = new ByteCountingWriter();
/*  88 */       serializer.serialize(serializable, writer);
/*  89 */       return writer.getByteCount();
/*  90 */     } catch (Throwable t) {
/*  91 */       logger.log(SentryLevel.ERROR, "Could not calculate size of serializable", t);
/*  92 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ByteCountingWriter
/*     */     extends Writer
/*     */   {
/* 101 */     private long byteCount = 0L;
/*     */ 
/*     */     
/*     */     public void write(char[] cbuf, int off, int len) {
/* 105 */       for (int i = off; i < off + len; i++) {
/* 106 */         this.byteCount += utf8ByteCount(cbuf[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(int c) {
/* 112 */       this.byteCount += utf8ByteCount((char)c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(@NotNull String str, int off, int len) {
/* 117 */       for (int i = off; i < off + len; i++) {
/* 118 */         this.byteCount += utf8ByteCount(str.charAt(i));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public long getByteCount() {
/* 133 */       return this.byteCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int utf8ByteCount(char c) {
/* 143 */       if (c <= '')
/* 144 */         return 1; 
/* 145 */       if (c <= '߿')
/* 146 */         return 2; 
/* 147 */       if (Character.isSurrogate(c)) {
/* 148 */         return 2;
/*     */       }
/* 150 */       return 3;
/*     */     }
/*     */     
/*     */     private ByteCountingWriter() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\JsonSerializationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */