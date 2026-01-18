/*     */ package com.google.common.flogger.backend.system;
/*     */ 
/*     */ import com.google.common.flogger.LogSite;
/*     */ import com.google.common.flogger.backend.LogData;
/*     */ import com.google.common.flogger.backend.LogMessageFormatter;
/*     */ import com.google.common.flogger.backend.MessageUtils;
/*     */ import com.google.common.flogger.backend.Metadata;
/*     */ import com.google.common.flogger.backend.MetadataProcessor;
/*     */ import com.google.common.flogger.backend.SimpleMessageFormatter;
/*     */ import java.util.Arrays;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
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
/*     */ public abstract class AbstractLogRecord
/*     */   extends LogRecord
/*     */ {
/*  95 */   private static final Formatter jdkMessageFormatter = new Formatter()
/*     */     {
/*     */       
/*     */       public String format(LogRecord record)
/*     */       {
/* 100 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*     */   
/* 104 */   private static final Object[] NO_PARAMETERS = new Object[0];
/*     */ 
/*     */   
/*     */   private final LogData data;
/*     */ 
/*     */   
/*     */   private final MetadataProcessor metadata;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLogRecord(LogData data, Metadata scope) {
/* 115 */     super(data.getLevel(), null);
/* 116 */     this.data = data;
/* 117 */     this.metadata = MetadataProcessor.forScopeAndLogSite(scope, data.getMetadata());
/*     */ 
/*     */     
/* 120 */     LogSite logSite = data.getLogSite();
/* 121 */     setSourceClassName(logSite.getClassName());
/* 122 */     setSourceMethodName(logSite.getMethodName());
/* 123 */     setLoggerName(data.getLoggerName());
/* 124 */     setMillis(TimeUnit.NANOSECONDS.toMillis(data.getTimestampNanos()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     super.setParameters(NO_PARAMETERS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLogRecord(RuntimeException error, LogData data, Metadata scope) {
/* 139 */     this(data, scope);
/*     */     
/* 141 */     setLevel((data.getLevel().intValue() < Level.WARNING.intValue()) ? Level.WARNING : data.getLevel());
/* 142 */     setThrown(error);
/*     */     
/* 144 */     StringBuilder errorMsg = (new StringBuilder("LOGGING ERROR: ")).append(error.getMessage()).append('\n');
/* 145 */     safeAppend(data, errorMsg);
/* 146 */     setMessage(errorMsg.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LogMessageFormatter getLogMessageFormatter() {
/* 155 */     return SimpleMessageFormatter.getDefaultFormatter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setParameters(Object[] parameters) {
/* 163 */     getMessage();
/*     */     
/* 165 */     if (parameters == null) {
/* 166 */       parameters = NO_PARAMETERS;
/*     */     }
/* 168 */     super.setParameters(parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setMessage(String message) {
/* 173 */     if (message == null) {
/* 174 */       message = "";
/*     */     }
/* 176 */     super.setMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getMessage() {
/* 181 */     String cachedMessage = super.getMessage();
/* 182 */     if (cachedMessage != null) {
/* 183 */       return cachedMessage;
/*     */     }
/* 185 */     String formattedMessage = getLogMessageFormatter().format(this.data, this.metadata);
/* 186 */     super.setMessage(formattedMessage);
/* 187 */     return formattedMessage;
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
/*     */   public final StringBuilder appendFormattedMessageTo(StringBuilder buffer) {
/* 204 */     String cachedMessage = super.getMessage();
/* 205 */     if (cachedMessage == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       getLogMessageFormatter().append(this.data, this.metadata, buffer);
/* 214 */     } else if ((getParameters()).length == 0) {
/*     */ 
/*     */       
/* 217 */       buffer.append(cachedMessage);
/*     */     }
/*     */     else {
/*     */       
/* 221 */       buffer.append(jdkMessageFormatter.formatMessage(this));
/*     */     } 
/* 223 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormattedMessage() {
/* 234 */     if ((getParameters()).length == 0) {
/* 235 */       return getMessage();
/*     */     }
/* 237 */     return jdkMessageFormatter.formatMessage(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setResourceBundle(ResourceBundle bundle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setResourceBundleName(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LogRecord toMutableLogRecord() {
/* 258 */     LogRecord copy = new LogRecord(getLevel(), getFormattedMessage());
/* 259 */     copy.setParameters(NO_PARAMETERS);
/* 260 */     copy.setSourceClassName(getSourceClassName());
/* 261 */     copy.setSourceMethodName(getSourceMethodName());
/* 262 */     copy.setLoggerName(getLoggerName());
/* 263 */     copy.setMillis(getMillis());
/* 264 */     copy.setThrown(getThrown());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     copy.setThreadID(getThreadID());
/* 270 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LogData getLogData() {
/* 280 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MetadataProcessor getMetadataProcessor() {
/* 289 */     return this.metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 295 */     StringBuilder out = new StringBuilder();
/* 296 */     out.append(getClass().getSimpleName())
/* 297 */       .append(" {\n  message: ")
/* 298 */       .append(getMessage())
/* 299 */       .append("\n  arguments: ")
/* 300 */       .append((getParameters() != null) ? Arrays.<Object>asList(getParameters()) : "<none>")
/* 301 */       .append('\n');
/* 302 */     safeAppend(getLogData(), out);
/* 303 */     out.append("\n}");
/* 304 */     return out.toString();
/*     */   }
/*     */   
/*     */   private static void safeAppend(LogData data, StringBuilder out) {
/* 308 */     out.append("  original message: ");
/* 309 */     if (data.getTemplateContext() == null) {
/* 310 */       out.append(MessageUtils.safeToString(data.getLiteralArgument()));
/*     */     } else {
/*     */       
/* 313 */       out.append(data.getTemplateContext().getMessage());
/* 314 */       out.append("\n  original arguments:");
/* 315 */       for (Object arg : data.getArguments()) {
/* 316 */         out.append("\n    ").append(MessageUtils.safeToString(arg));
/*     */       }
/*     */     } 
/* 319 */     Metadata metadata = data.getMetadata();
/* 320 */     if (metadata.size() > 0) {
/* 321 */       out.append("\n  metadata:");
/* 322 */       for (int n = 0; n < metadata.size(); n++) {
/* 323 */         out.append("\n    ")
/* 324 */           .append(metadata.getKey(n).getLabel())
/* 325 */           .append(": ")
/* 326 */           .append(MessageUtils.safeToString(metadata.getValue(n)));
/*     */       }
/*     */     } 
/* 329 */     out.append("\n  level: ").append(MessageUtils.safeToString(data.getLevel()));
/* 330 */     out.append("\n  timestamp (nanos): ").append(data.getTimestampNanos());
/* 331 */     out.append("\n  class: ").append(data.getLogSite().getClassName());
/* 332 */     out.append("\n  method: ").append(data.getLogSite().getMethodName());
/* 333 */     out.append("\n  line number: ").append(data.getLogSite().getLineNumber());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\AbstractLogRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */