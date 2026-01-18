/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.LogContext;
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ 
/*     */ public final class SimpleMessageFormatter
/*     */ {
/*  54 */   private static final Set<MetadataKey<?>> DEFAULT_KEYS_TO_IGNORE = Collections.singleton(LogContext.Key.LOG_CAUSE);
/*     */ 
/*     */   
/*  57 */   private static final MetadataHandler<MetadataKey.KeyValueHandler> DEFAULT_HANDLER = MetadataKeyValueHandlers.getDefaultHandler(DEFAULT_KEYS_TO_IGNORE);
/*     */   
/*  59 */   private static final LogMessageFormatter DEFAULT_FORMATTER = new LogMessageFormatter()
/*     */     {
/*     */       
/*     */       public StringBuilder append(LogData logData, MetadataProcessor metadata, StringBuilder out)
/*     */       {
/*  64 */         return SimpleMessageFormatter.appendFormatted(logData, metadata, SimpleMessageFormatter.DEFAULT_HANDLER, out);
/*     */       }
/*     */ 
/*     */       
/*     */       public String format(LogData logData, MetadataProcessor metadata) {
/*  69 */         return SimpleMessageFormatter.format(logData, metadata);
/*     */       }
/*     */     };
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
/*     */   
/*     */   public static LogMessageFormatter getDefaultFormatter() {
/*  88 */     return DEFAULT_FORMATTER;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringBuilder appendFormatted(LogData logData, MetadataProcessor metadata, MetadataHandler<MetadataKey.KeyValueHandler> metadataHandler, StringBuilder buffer) {
/* 112 */     BaseMessageFormatter.appendFormattedMessage(logData, buffer);
/* 113 */     return appendContext(metadata, metadataHandler, buffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringBuilder appendContext(MetadataProcessor metadataProcessor, MetadataHandler<MetadataKey.KeyValueHandler> metadataHandler, StringBuilder buffer) {
/* 136 */     KeyValueFormatter kvf = new KeyValueFormatter("[CONTEXT ", " ]", buffer);
/* 137 */     metadataProcessor.process(metadataHandler, kvf);
/* 138 */     kvf.done();
/* 139 */     return buffer;
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
/*     */   public static String getLiteralLogMessage(LogData logData) {
/* 155 */     return MessageUtils.safeToString(logData.getLiteralArgument());
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
/*     */   public static boolean mustBeFormatted(LogData logData, MetadataProcessor metadata, Set<MetadataKey<?>> keysToIgnore) {
/* 183 */     return (logData.getTemplateContext() != null || metadata
/* 184 */       .keyCount() > keysToIgnore.size() || 
/* 185 */       !keysToIgnore.containsAll(metadata.keySet()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String format(LogData logData, MetadataProcessor metadata) {
/* 194 */     return mustBeFormatted(logData, metadata, DEFAULT_KEYS_TO_IGNORE) ? 
/* 195 */       appendFormatted(logData, metadata, DEFAULT_HANDLER, new StringBuilder()).toString() : 
/* 196 */       getLiteralLogMessage(logData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void format(LogData logData, SimpleLogHandler receiver) {
/* 205 */     MetadataProcessor metadata = MetadataProcessor.forScopeAndLogSite(Metadata.empty(), logData.getMetadata());
/* 206 */     receiver.handleFormattedLogMessage(logData
/* 207 */         .getLevel(), 
/* 208 */         format(logData, metadata), metadata
/* 209 */         .<Throwable>getSingleValue(LogContext.Key.LOG_CAUSE));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   static void format(LogData logData, SimpleLogHandler receiver, Option option) {
/*     */     String message;
/*     */     StringBuilder buffer;
/*     */     Throwable cause;
/* 235 */     switch (option) {
/*     */       case WITH_LOG_SITE:
/* 237 */         buffer = new StringBuilder();
/* 238 */         if (MessageUtils.appendLogSite(logData.getLogSite(), buffer)) {
/* 239 */           buffer.append(" ");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 247 */         message = appendFormatted(logData, MetadataProcessor.forScopeAndLogSite(Metadata.empty(), logData.getMetadata()), DEFAULT_HANDLER, buffer).toString();
/* 248 */         cause = logData.getMetadata().<Throwable>findValue(LogContext.Key.LOG_CAUSE);
/* 249 */         receiver.handleFormattedLogMessage(logData.getLevel(), message, cause);
/*     */         break;
/*     */       
/*     */       case DEFAULT:
/* 253 */         format(logData, receiver);
/*     */         break;
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
/*     */   
/*     */   @Deprecated
/*     */   enum Option
/*     */   {
/* 275 */     DEFAULT,
/*     */     
/* 277 */     WITH_LOG_SITE;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static interface SimpleLogHandler {
/*     */     void handleFormattedLogMessage(Level param1Level, String param1String, @NullableDecl Throwable param1Throwable);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\SimpleMessageFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */