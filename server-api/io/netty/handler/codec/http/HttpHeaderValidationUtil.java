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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpHeaderValidationUtil
/*     */ {
/*     */   public static boolean isConnectionHeader(CharSequence name, boolean ignoreTeHeader) {
/*  63 */     int len = name.length();
/*  64 */     switch (len) { case 2:
/*  65 */         return ignoreTeHeader ? false : AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.TE);
/*  66 */       case 7: return AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.UPGRADE);
/*  67 */       case 10: return (AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.CONNECTION) || 
/*  68 */           AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.KEEP_ALIVE));
/*  69 */       case 16: return AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.PROXY_CONNECTION);
/*  70 */       case 17: return AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.TRANSFER_ENCODING); }
/*     */     
/*  72 */     return false;
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
/*     */   public static boolean isTeNotTrailers(CharSequence name, CharSequence value) {
/*  90 */     if (name.length() == 2) {
/*  91 */       return (AsciiString.contentEqualsIgnoreCase(name, (CharSequence)HttpHeaderNames.TE) && 
/*  92 */         !AsciiString.contentEqualsIgnoreCase(value, (CharSequence)HttpHeaderValues.TRAILERS));
/*     */     }
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int validateValidHeaderValue(CharSequence value) {
/* 105 */     int length = value.length();
/* 106 */     if (length == 0) {
/* 107 */       return -1;
/*     */     }
/* 109 */     if (value instanceof AsciiString) {
/* 110 */       return verifyValidHeaderValueAsciiString((AsciiString)value);
/*     */     }
/* 112 */     return verifyValidHeaderValueCharSequence(value);
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
/*     */   private static int verifyValidHeaderValueAsciiString(AsciiString value) {
/* 125 */     byte[] array = value.array();
/* 126 */     int start = value.arrayOffset();
/* 127 */     int b = array[start] & 0xFF;
/* 128 */     if (b < 33 || b == 127) {
/* 129 */       return 0;
/*     */     }
/* 131 */     int end = start + value.length();
/* 132 */     for (int i = start + 1; i < end; i++) {
/* 133 */       b = array[i] & 0xFF;
/* 134 */       if ((b < 32 && b != 9) || b == 127) {
/* 135 */         return i - start;
/*     */       }
/*     */     } 
/* 138 */     return -1;
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
/*     */   private static int verifyValidHeaderValueCharSequence(CharSequence value) {
/* 151 */     int b = value.charAt(0);
/* 152 */     if (b < 33 || b == 127) {
/* 153 */       return 0;
/*     */     }
/* 155 */     int length = value.length();
/* 156 */     for (int i = 1; i < length; i++) {
/* 157 */       b = value.charAt(i);
/* 158 */       if ((b < 32 && b != 9) || b == 127) {
/* 159 */         return i;
/*     */       }
/*     */     } 
/* 162 */     return -1;
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
/*     */   public static int validateToken(CharSequence token) {
/* 179 */     return HttpUtil.validateToken(token);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpHeaderValidationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */