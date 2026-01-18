/*     */ package com.google.common.flogger.parser;
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
/*     */ public final class ParseException
/*     */   extends RuntimeException
/*     */ {
/*     */   private static final String ELLIPSIS = "...";
/*     */   private static final int SNIPPET_LENGTH = 5;
/*     */   
/*     */   public static ParseException withBounds(String errorMessage, String logMessage, int start, int end) {
/*  45 */     return new ParseException(msg(errorMessage, logMessage, start, end), logMessage);
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
/*     */   public static ParseException atPosition(String errorMessage, String logMessage, int position) {
/*  57 */     return new ParseException(msg(errorMessage, logMessage, position, position + 1), logMessage);
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
/*     */   public static ParseException withStartPosition(String errorMessage, String logMessage, int start) {
/*  71 */     return new ParseException(msg(errorMessage, logMessage, start, -1), logMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParseException generic(String errorMessage, String logMessage) {
/*  81 */     return new ParseException(errorMessage, logMessage);
/*     */   }
/*     */   
/*     */   private ParseException(String errorMessage, String logMessage) {
/*  85 */     super(errorMessage);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String msg(String errorMessage, String logMessage, int errorStart, int errorEnd) {
/*  90 */     if (errorEnd < 0) {
/*  91 */       errorEnd = logMessage.length();
/*     */     }
/*  93 */     StringBuilder out = (new StringBuilder(errorMessage)).append(": ");
/*  94 */     if (errorStart > 5 + "...".length()) {
/*  95 */       out.append("...").append(logMessage, errorStart - 5, errorStart);
/*     */     } else {
/*  97 */       out.append(logMessage, 0, errorStart);
/*     */     } 
/*  99 */     out.append('[').append(logMessage.substring(errorStart, errorEnd)).append(']');
/* 100 */     if (logMessage.length() - errorEnd > 5 + "...".length()) {
/* 101 */       out.append(logMessage, errorEnd, errorEnd + 5).append("...");
/*     */     } else {
/* 103 */       out.append(logMessage, errorEnd, logMessage.length());
/*     */     } 
/* 105 */     return out.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Throwable fillInStackTrace() {
/* 113 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\ParseException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */