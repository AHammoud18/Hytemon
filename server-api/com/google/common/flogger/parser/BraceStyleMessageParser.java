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
/*     */ public abstract class BraceStyleMessageParser
/*     */   extends MessageParser
/*     */ {
/*     */   private static final char BRACE_STYLE_SEPARATOR = ',';
/*     */   
/*     */   abstract void parseBraceFormatTerm(MessageBuilder<?> paramMessageBuilder, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4) throws ParseException;
/*     */   
/*     */   public final void unescape(StringBuilder out, String message, int start, int end) {
/*  75 */     unescapeBraceFormat(out, message, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> void parseImpl(MessageBuilder<T> builder) throws ParseException {
/*  80 */     String message = builder.getMessage();
/*  81 */     int pos = nextBraceFormatTerm(message, 0);
/*  82 */     for (; pos >= 0; 
/*  83 */       pos = nextBraceFormatTerm(message, pos)) {
/*     */       
/*  85 */       int termStart = pos++;
/*     */       
/*  87 */       int indexStart = termStart + 1;
/*     */ 
/*     */ 
/*     */       
/*  91 */       int index = 0; while (true) {
/*     */         char c; int trailingPartStart;
/*  93 */         if (pos < message.length()) {
/*     */           
/*  95 */           c = message.charAt(pos++);
/*  96 */           int digit = (char)(c - 48);
/*  97 */           if (digit < 10) {
/*  98 */             index = 10 * index + digit;
/*  99 */             if (index < 1000000) {
/*     */               continue;
/*     */             }
/* 102 */             throw ParseException.withBounds("index too large", message, indexStart, pos);
/*     */           } 
/*     */         } else {
/*     */           
/* 106 */           throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */         } 
/*     */ 
/*     */         
/* 110 */         int indexLen = pos - 1 - indexStart;
/* 111 */         if (indexLen == 0)
/*     */         {
/* 113 */           throw ParseException.withBounds("missing index", message, termStart, pos);
/*     */         }
/*     */         
/* 116 */         if (message.charAt(indexStart) == '0' && indexLen > 1) {
/* 117 */           throw ParseException.withBounds("index has leading zero", message, indexStart, pos - 1);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 122 */         if (c == '}') {
/*     */           
/* 124 */           trailingPartStart = -1;
/* 125 */         } else if (c == ',') {
/* 126 */           trailingPartStart = pos;
/*     */           do {
/* 128 */             if (pos == message.length()) {
/* 129 */               throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */             }
/* 131 */           } while (message.charAt(pos++) != '}');
/*     */         } else {
/*     */           
/* 134 */           throw ParseException.withBounds("malformed index", message, termStart + 1, pos);
/*     */         } 
/*     */ 
/*     */         
/* 138 */         parseBraceFormatTerm(builder, index, message, termStart, trailingPartStart, pos);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int nextBraceFormatTerm(String message, int pos) throws ParseException {
/* 149 */     label19: while (pos < message.length()) {
/* 150 */       char c = message.charAt(pos++);
/* 151 */       if (c == '{')
/*     */       {
/* 153 */         return pos - 1;
/*     */       }
/* 155 */       if (c != '\'') {
/*     */         continue;
/*     */       }
/*     */       
/* 159 */       if (pos == message.length()) {
/* 160 */         throw ParseException.withStartPosition("trailing single quote", message, pos - 1);
/*     */       }
/* 162 */       if (message.charAt(pos++) == '\'') {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 167 */       int quote = pos - 2;
/*     */       
/*     */       while (true) {
/* 170 */         if (pos == message.length()) {
/* 171 */           throw ParseException.withStartPosition("unmatched single quote", message, quote);
/*     */         }
/* 173 */         if (message.charAt(pos++) == '\'')
/*     */           continue label19; 
/*     */       } 
/* 176 */     }  return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void unescapeBraceFormat(StringBuilder out, String message, int start, int end) {
/* 185 */     int pos = start;
/* 186 */     boolean isQuoted = false;
/* 187 */     while (pos < end) {
/* 188 */       char c = message.charAt(pos++);
/*     */       
/* 190 */       if (c != '\\' && c != '\'') {
/*     */         continue;
/*     */       }
/* 193 */       int quoteStart = pos - 1;
/* 194 */       if (c == '\\') {
/*     */         
/* 196 */         c = message.charAt(pos++);
/* 197 */         if (c != '\'') {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 202 */       out.append(message, start, quoteStart);
/* 203 */       start = pos;
/* 204 */       if (pos == end) {
/*     */         break;
/*     */       }
/* 207 */       if (isQuoted) {
/* 208 */         isQuoted = false; continue;
/* 209 */       }  if (message.charAt(pos) != '\'') {
/* 210 */         isQuoted = true;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 215 */       pos++;
/*     */     } 
/*     */ 
/*     */     
/* 219 */     if (start < end)
/* 220 */       out.append(message, start, end); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\BraceStyleMessageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */