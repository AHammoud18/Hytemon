/*     */ package io.netty.handler.codec.http.cookie;
/*     */ 
/*     */ import io.netty.handler.codec.DateFormatter;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClientCookieDecoder
/*     */   extends CookieDecoder
/*     */ {
/*  39 */   public static final ClientCookieDecoder STRICT = new ClientCookieDecoder(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final ClientCookieDecoder LAX = new ClientCookieDecoder(false);
/*     */   
/*     */   private ClientCookieDecoder(boolean strict) {
/*  47 */     super(strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cookie decode(String header) {
/*  56 */     int headerLen = ((String)ObjectUtil.checkNotNull(header, "header")).length();
/*     */     
/*  58 */     if (headerLen == 0) {
/*  59 */       return null;
/*     */     }
/*     */     
/*  62 */     CookieBuilder cookieBuilder = null;
/*     */     
/*  64 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/*  68 */     while (i != headerLen) {
/*     */       int nameEnd, valueBegin, valueEnd;
/*     */       
/*  71 */       char c = header.charAt(i);
/*  72 */       if (c == ',') {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  77 */       if (c == '\t' || c == '\n' || c == '\013' || c == '\f' || c == '\r' || c == ' ' || c == ';') {
/*     */         
/*  79 */         i++;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  85 */       int nameBegin = i;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/*  91 */         char curChar = header.charAt(i);
/*  92 */         if (curChar == ';') {
/*     */           
/*  94 */           nameEnd = i;
/*  95 */           valueBegin = valueEnd = -1;
/*     */           break;
/*     */         } 
/*  98 */         if (curChar == '=') {
/*     */           
/* 100 */           nameEnd = i;
/* 101 */           i++;
/* 102 */           if (i == headerLen) {
/*     */             
/* 104 */             int k = 0, j = k;
/*     */             
/*     */             break;
/*     */           } 
/* 108 */           valueBegin = i;
/*     */           
/* 110 */           int semiPos = header.indexOf(';', i);
/* 111 */           valueEnd = i = (semiPos > 0) ? semiPos : headerLen;
/*     */           break;
/*     */         } 
/* 114 */         i++;
/*     */ 
/*     */         
/* 117 */         if (i == headerLen) {
/*     */           
/* 119 */           nameEnd = headerLen;
/* 120 */           valueBegin = valueEnd = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 125 */       if (valueEnd > 0 && header.charAt(valueEnd - 1) == ',')
/*     */       {
/* 127 */         valueEnd--;
/*     */       }
/*     */       
/* 130 */       if (cookieBuilder == null) {
/*     */         
/* 132 */         DefaultCookie cookie = initCookie(header, nameBegin, nameEnd, valueBegin, valueEnd);
/*     */         
/* 134 */         if (cookie == null) {
/* 135 */           return null;
/*     */         }
/*     */         
/* 138 */         cookieBuilder = new CookieBuilder(cookie, header);
/*     */         continue;
/*     */       } 
/* 141 */       cookieBuilder.appendAttribute(nameBegin, nameEnd, valueBegin, valueEnd);
/*     */     } 
/*     */     
/* 144 */     return (cookieBuilder != null) ? cookieBuilder.cookie() : null;
/*     */   }
/*     */   
/*     */   private static class CookieBuilder
/*     */   {
/*     */     private final String header;
/*     */     private final DefaultCookie cookie;
/*     */     private String domain;
/*     */     private String path;
/* 153 */     private long maxAge = Long.MIN_VALUE;
/*     */     private int expiresStart;
/*     */     private int expiresEnd;
/*     */     private boolean secure;
/*     */     private boolean httpOnly;
/*     */     private CookieHeaderNames.SameSite sameSite;
/*     */     private boolean partitioned;
/*     */     
/*     */     CookieBuilder(DefaultCookie cookie, String header) {
/* 162 */       this.cookie = cookie;
/* 163 */       this.header = header;
/*     */     }
/*     */ 
/*     */     
/*     */     private long mergeMaxAgeAndExpires() {
/* 168 */       if (this.maxAge != Long.MIN_VALUE)
/* 169 */         return this.maxAge; 
/* 170 */       if (isValueDefined(this.expiresStart, this.expiresEnd)) {
/* 171 */         Date expiresDate = DateFormatter.parseHttpDate(this.header, this.expiresStart, this.expiresEnd);
/* 172 */         if (expiresDate != null) {
/* 173 */           long maxAgeMillis = expiresDate.getTime() - System.currentTimeMillis();
/* 174 */           return maxAgeMillis / 1000L + ((maxAgeMillis % 1000L != 0L) ? 1L : 0L);
/*     */         } 
/*     */       } 
/* 177 */       return Long.MIN_VALUE;
/*     */     }
/*     */     
/*     */     Cookie cookie() {
/* 181 */       this.cookie.setDomain(this.domain);
/* 182 */       this.cookie.setPath(this.path);
/* 183 */       this.cookie.setMaxAge(mergeMaxAgeAndExpires());
/* 184 */       this.cookie.setSecure(this.secure);
/* 185 */       this.cookie.setHttpOnly(this.httpOnly);
/* 186 */       this.cookie.setSameSite(this.sameSite);
/* 187 */       this.cookie.setPartitioned(this.partitioned);
/* 188 */       return this.cookie;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void appendAttribute(int keyStart, int keyEnd, int valueStart, int valueEnd) {
/* 205 */       int length = keyEnd - keyStart;
/*     */       
/* 207 */       if (length == 4) {
/* 208 */         parse4(keyStart, valueStart, valueEnd);
/* 209 */       } else if (length == 6) {
/* 210 */         parse6(keyStart, valueStart, valueEnd);
/* 211 */       } else if (length == 7) {
/* 212 */         parse7(keyStart, valueStart, valueEnd);
/* 213 */       } else if (length == 8) {
/* 214 */         parse8(keyStart, valueStart, valueEnd);
/* 215 */       } else if (length == 11) {
/* 216 */         parse11(keyStart);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void parse4(int nameStart, int valueStart, int valueEnd) {
/* 221 */       if (this.header.regionMatches(true, nameStart, "Path", 0, 4)) {
/* 222 */         this.path = computeValue(valueStart, valueEnd);
/*     */       }
/*     */     }
/*     */     
/*     */     private void parse6(int nameStart, int valueStart, int valueEnd) {
/* 227 */       if (this.header.regionMatches(true, nameStart, "Domain", 0, 5)) {
/* 228 */         this.domain = computeValue(valueStart, valueEnd);
/* 229 */       } else if (this.header.regionMatches(true, nameStart, "Secure", 0, 5)) {
/* 230 */         this.secure = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void setMaxAge(String value) {
/*     */       try {
/* 236 */         this.maxAge = Math.max(Long.parseLong(value), 0L);
/* 237 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void parse7(int nameStart, int valueStart, int valueEnd) {
/* 243 */       if (this.header.regionMatches(true, nameStart, "Expires", 0, 7)) {
/* 244 */         this.expiresStart = valueStart;
/* 245 */         this.expiresEnd = valueEnd;
/* 246 */       } else if (this.header.regionMatches(true, nameStart, "Max-Age", 0, 7)) {
/* 247 */         setMaxAge(computeValue(valueStart, valueEnd));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void parse8(int nameStart, int valueStart, int valueEnd) {
/* 252 */       if (this.header.regionMatches(true, nameStart, "HTTPOnly", 0, 8)) {
/* 253 */         this.httpOnly = true;
/* 254 */       } else if (this.header.regionMatches(true, nameStart, "SameSite", 0, 8)) {
/* 255 */         this.sameSite = CookieHeaderNames.SameSite.of(computeValue(valueStart, valueEnd));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void parse11(int nameStart) {
/* 260 */       if (this.header.regionMatches(true, nameStart, "Partitioned", 0, 11)) {
/* 261 */         this.partitioned = true;
/*     */       }
/*     */     }
/*     */     
/*     */     private static boolean isValueDefined(int valueStart, int valueEnd) {
/* 266 */       return (valueStart != -1 && valueStart != valueEnd);
/*     */     }
/*     */     
/*     */     private String computeValue(int valueStart, int valueEnd) {
/* 270 */       return isValueDefined(valueStart, valueEnd) ? this.header.substring(valueStart, valueEnd) : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\ClientCookieDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */