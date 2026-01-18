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
/*     */ public abstract class PrintfMessageParser
/*     */   extends MessageParser
/*     */ {
/*     */   private static final String ALLOWED_NEWLINE_PATTERN = "\\n|\\r(?:\\n)?";
/*  32 */   private static final String SYSTEM_NEWLINE = getSafeSystemNewline();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getSafeSystemNewline() {
/*     */     try {
/*  39 */       String unsafeNewline = System.getProperty("line.separator");
/*  40 */       if (unsafeNewline.matches("\\n|\\r(?:\\n)?")) {
/*  41 */         return unsafeNewline;
/*     */       }
/*  43 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/*  46 */     return "\n";
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
/*     */   abstract int parsePrintfTerm(MessageBuilder<?> paramMessageBuilder, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4) throws ParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void unescape(StringBuilder out, String message, int start, int end) {
/*  93 */     unescapePrintf(out, message, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> void parseImpl(MessageBuilder<T> builder) throws ParseException {
/*  98 */     String message = builder.getMessage();
/*     */     
/* 100 */     int lastResolvedIndex = -1;
/*     */ 
/*     */     
/* 103 */     int implicitIndex = 0;
/*     */     int pos;
/* 105 */     for (pos = nextPrintfTerm(message, 0); pos >= 0; pos = nextPrintfTerm(message, pos)) {
/*     */       
/* 107 */       int termStart = pos++;
/*     */ 
/*     */       
/* 110 */       int optionsStart = pos;
/*     */ 
/*     */ 
/*     */       
/* 114 */       int index = 0; while (true) {
/*     */         char c;
/* 116 */         if (pos < message.length()) {
/* 117 */           c = message.charAt(pos++);
/*     */           
/* 119 */           int digit = (char)(c - 48);
/* 120 */           if (digit < 10) {
/* 121 */             index = 10 * index + digit;
/* 122 */             if (index < 1000000) {
/*     */               continue;
/*     */             }
/* 125 */             throw ParseException.withBounds("index too large", message, termStart, pos);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 130 */           throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */         } 
/*     */ 
/*     */         
/* 134 */         if (c == '$') {
/*     */           
/* 136 */           int indexLen = pos - 1 - optionsStart;
/* 137 */           if (indexLen == 0) {
/* 138 */             throw ParseException.withBounds("missing index", message, termStart, pos);
/*     */           }
/*     */ 
/*     */           
/* 142 */           if (message.charAt(optionsStart) == '0') {
/* 143 */             throw ParseException.withBounds("index has leading zero", message, termStart, pos);
/*     */           }
/*     */           
/* 146 */           index--;
/*     */           
/* 148 */           optionsStart = pos;
/*     */           
/* 150 */           if (pos == message.length()) {
/* 151 */             throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */           }
/* 153 */           c = message.charAt(pos++);
/* 154 */         } else if (c == '<') {
/*     */           
/* 156 */           if (lastResolvedIndex == -1) {
/* 157 */             throw ParseException.withBounds("invalid relative parameter", message, termStart, pos);
/*     */           }
/* 159 */           index = lastResolvedIndex;
/*     */           
/* 161 */           optionsStart = pos;
/*     */           
/* 163 */           if (pos == message.length()) {
/* 164 */             throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */           }
/* 166 */           c = message.charAt(pos++);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 171 */           index = implicitIndex++;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 176 */         pos = findFormatChar(message, termStart, pos - 1);
/*     */ 
/*     */ 
/*     */         
/* 180 */         pos = parsePrintfTerm(builder, index, message, termStart, optionsStart, pos);
/*     */         
/* 182 */         lastResolvedIndex = index;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int nextPrintfTerm(String message, int pos) throws ParseException {
/* 192 */     while (pos < message.length()) {
/* 193 */       if (message.charAt(pos++) != '%') {
/*     */         continue;
/*     */       }
/* 196 */       if (pos < message.length()) {
/* 197 */         char c = message.charAt(pos);
/* 198 */         if (c == '%' || c == 'n') {
/*     */           
/* 200 */           pos++;
/*     */           
/*     */           continue;
/*     */         } 
/* 204 */         return pos - 1;
/*     */       } 
/*     */       
/* 207 */       throw ParseException.withStartPosition("trailing unquoted '%' character", message, pos - 1);
/*     */     } 
/*     */     
/* 210 */     return -1;
/*     */   }
/*     */   
/*     */   private static int findFormatChar(String message, int termStart, int pos) throws ParseException {
/* 214 */     for (; pos < message.length(); pos++) {
/* 215 */       char c = message.charAt(pos);
/*     */ 
/*     */ 
/*     */       
/* 219 */       int alpha = (char)((c & 0xFFFFFFDF) - 65);
/* 220 */       if (alpha < 26) {
/* 221 */         return pos;
/*     */       }
/*     */     } 
/* 224 */     throw ParseException.withStartPosition("unterminated parameter", message, termStart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void unescapePrintf(StringBuilder out, String message, int start, int end) {
/* 233 */     int pos = start;
/* 234 */     while (pos < end) {
/* 235 */       if (message.charAt(pos++) != '%') {
/*     */         continue;
/*     */       }
/* 238 */       if (pos == end) {
/*     */         break;
/*     */       }
/*     */       
/* 242 */       char chr = message.charAt(pos);
/* 243 */       if (chr == '%') {
/*     */         
/* 245 */         out.append(message, start, pos);
/* 246 */       } else if (chr == 'n') {
/*     */         
/* 248 */         out.append(message, start, pos - 1);
/* 249 */         out.append(SYSTEM_NEWLINE);
/*     */       } else {
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 255 */       start = ++pos;
/*     */     } 
/*     */     
/* 258 */     if (start < end)
/* 259 */       out.append(message, start, end); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\PrintfMessageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */