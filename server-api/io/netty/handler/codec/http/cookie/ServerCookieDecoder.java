/*     */ package io.netty.handler.codec.http.cookie;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServerCookieDecoder
/*     */   extends CookieDecoder
/*     */ {
/*     */   private static final String RFC2965_VERSION = "$Version";
/*     */   private static final String RFC2965_PATH = "$Path";
/*     */   private static final String RFC2965_DOMAIN = "$Domain";
/*     */   private static final String RFC2965_PORT = "$Port";
/*  51 */   public static final ServerCookieDecoder STRICT = new ServerCookieDecoder(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final ServerCookieDecoder LAX = new ServerCookieDecoder(false);
/*     */   
/*     */   private ServerCookieDecoder(boolean strict) {
/*  59 */     super(strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Cookie> decodeAll(String header) {
/*  69 */     List<Cookie> cookies = new ArrayList<>();
/*  70 */     decode(cookies, header);
/*  71 */     return Collections.unmodifiableList(cookies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Cookie> decode(String header) {
/*  80 */     Set<Cookie> cookies = new TreeSet<>();
/*  81 */     decode(cookies, header);
/*  82 */     return cookies;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decode(Collection<? super Cookie> cookies, String header) {
/*  89 */     int headerLen = ((String)ObjectUtil.checkNotNull(header, "header")).length();
/*     */     
/*  91 */     if (headerLen == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  95 */     int i = 0;
/*     */     
/*  97 */     boolean rfc2965Style = false;
/*  98 */     if (header.regionMatches(true, 0, "$Version", 0, "$Version".length())) {
/*     */       
/* 100 */       i = header.indexOf(';') + 1;
/* 101 */       rfc2965Style = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     while (i != headerLen) {
/*     */       int nameEnd, valueBegin, valueEnd;
/*     */       
/* 111 */       char c = header.charAt(i);
/* 112 */       if (c == '\t' || c == '\n' || c == '\013' || c == '\f' || c == '\r' || c == ' ' || c == ',' || c == ';') {
/*     */         
/* 114 */         i++;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 120 */       int nameBegin = i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 127 */         char curChar = header.charAt(i);
/* 128 */         if (curChar == ';') {
/*     */           
/* 130 */           nameEnd = i;
/* 131 */           valueBegin = valueEnd = -1;
/*     */           break;
/*     */         } 
/* 134 */         if (curChar == '=') {
/*     */           
/* 136 */           nameEnd = i;
/* 137 */           i++;
/* 138 */           if (i == headerLen) {
/*     */             
/* 140 */             int k = 0, j = k;
/*     */             
/*     */             break;
/*     */           } 
/* 144 */           valueBegin = i;
/*     */           
/* 146 */           int semiPos = header.indexOf(';', i);
/* 147 */           valueEnd = i = (semiPos > 0) ? semiPos : headerLen;
/*     */           break;
/*     */         } 
/* 150 */         i++;
/*     */ 
/*     */         
/* 153 */         if (i == headerLen) {
/*     */           
/* 155 */           nameEnd = headerLen;
/* 156 */           valueBegin = valueEnd = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 161 */       if (rfc2965Style && (header.regionMatches(nameBegin, "$Path", 0, "$Path".length()) || header
/* 162 */         .regionMatches(nameBegin, "$Domain", 0, "$Domain".length()) || header
/* 163 */         .regionMatches(nameBegin, "$Port", 0, "$Port".length()))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 169 */       DefaultCookie cookie = initCookie(header, nameBegin, nameEnd, valueBegin, valueEnd);
/* 170 */       if (cookie != null)
/* 171 */         cookies.add(cookie); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\ServerCookieDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */