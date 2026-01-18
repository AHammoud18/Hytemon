/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.AsciiString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum HttpStatusClass
/*     */ {
/*  28 */   INFORMATIONAL(100, 200, "Informational"),
/*     */ 
/*     */ 
/*     */   
/*  32 */   SUCCESS(200, 300, "Success"),
/*     */ 
/*     */ 
/*     */   
/*  36 */   REDIRECTION(300, 400, "Redirection"),
/*     */ 
/*     */ 
/*     */   
/*  40 */   CLIENT_ERROR(400, 500, "Client Error"),
/*     */ 
/*     */ 
/*     */   
/*  44 */   SERVER_ERROR(500, 600, "Server Error"),
/*     */ 
/*     */ 
/*     */   
/*  48 */   UNKNOWN(0, 0, "Unknown Status")
/*     */   {
/*     */     public boolean contains(int code) {
/*  51 */       return (code < 100 || code >= 600);
/*     */     } };
/*     */   
/*     */   static {
/*  55 */     statusArray = new HttpStatusClass[6];
/*     */     
/*  57 */     statusArray[1] = INFORMATIONAL;
/*  58 */     statusArray[2] = SUCCESS;
/*  59 */     statusArray[3] = REDIRECTION;
/*  60 */     statusArray[4] = CLIENT_ERROR;
/*  61 */     statusArray[5] = SERVER_ERROR;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final HttpStatusClass[] statusArray;
/*     */ 
/*     */   
/*     */   private final int min;
/*     */ 
/*     */   
/*     */   private final int max;
/*     */ 
/*     */   
/*     */   private final AsciiString defaultReasonPhrase;
/*     */ 
/*     */   
/*     */   private static int fast_div100(int dividend) {
/*  79 */     return (int)(dividend * 1374389535L >> 37L);
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
/*     */   private static int digit(char c) {
/*  96 */     return c - 48;
/*     */   }
/*     */   
/*     */   private static boolean isDigit(char c) {
/* 100 */     return (c >= '0' && c <= '9');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpStatusClass(int min, int max, String defaultReasonPhrase) {
/* 108 */     this.min = min;
/* 109 */     this.max = max;
/* 110 */     this.defaultReasonPhrase = AsciiString.cached(defaultReasonPhrase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int code) {
/* 117 */     return (code >= this.min && code < this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AsciiString defaultReasonPhrase() {
/* 124 */     return this.defaultReasonPhrase;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpStatusClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */