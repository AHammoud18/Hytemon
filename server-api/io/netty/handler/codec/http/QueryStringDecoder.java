/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueryStringDecoder
/*     */ {
/*     */   private static final int DEFAULT_MAX_PARAMS = 1024;
/*     */   private final Charset charset;
/*     */   private final String uri;
/*     */   private final int maxParams;
/*     */   private final boolean semicolonIsNormalChar;
/*     */   private final boolean htmlQueryDecoding;
/*     */   private int pathEndIdx;
/*     */   private String path;
/*     */   private Map<String, List<String>> params;
/*     */   
/*     */   public QueryStringDecoder(String uri) {
/*  81 */     this(builder(), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, boolean hasPath) {
/*  89 */     this(builder().hasPath(hasPath), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset) {
/*  97 */     this(builder().charset(charset), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset, boolean hasPath) {
/* 105 */     this(builder().hasPath(hasPath).charset(charset), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset, boolean hasPath, int maxParams) {
/* 113 */     this(builder().hasPath(hasPath).charset(charset).maxParams(maxParams), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset, boolean hasPath, int maxParams, boolean semicolonIsNormalChar) {
/* 122 */     this(
/* 123 */         builder()
/* 124 */         .hasPath(hasPath)
/* 125 */         .charset(charset)
/* 126 */         .maxParams(maxParams)
/* 127 */         .semicolonIsNormalChar(semicolonIsNormalChar), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri) {
/* 136 */     this(builder(), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri, Charset charset) {
/* 144 */     this(builder().charset(charset), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri, Charset charset, int maxParams) {
/* 152 */     this(builder().charset(charset).maxParams(maxParams), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri, Charset charset, int maxParams, boolean semicolonIsNormalChar) {
/* 160 */     this(builder().charset(charset).maxParams(maxParams).semicolonIsNormalChar(semicolonIsNormalChar), uri);
/*     */   }
/*     */   
/*     */   private QueryStringDecoder(Builder builder, String uri) {
/* 164 */     this.uri = (String)ObjectUtil.checkNotNull(uri, "uri");
/* 165 */     this.charset = (Charset)ObjectUtil.checkNotNull(builder.charset, "charset");
/* 166 */     this.maxParams = ObjectUtil.checkPositive(builder.maxParams, "maxParams");
/* 167 */     this.semicolonIsNormalChar = builder.semicolonIsNormalChar;
/* 168 */     this.htmlQueryDecoding = builder.htmlQueryDecoding;
/*     */ 
/*     */     
/* 171 */     this.pathEndIdx = builder.hasPath ? -1 : 0;
/*     */   }
/*     */   
/*     */   private QueryStringDecoder(Builder builder, URI uri) {
/* 175 */     String rawPath = uri.getRawPath();
/* 176 */     if (rawPath == null) {
/* 177 */       rawPath = "";
/*     */     }
/* 179 */     String rawQuery = uri.getRawQuery();
/*     */     
/* 181 */     this.uri = (rawQuery == null) ? rawPath : (rawPath + '?' + rawQuery);
/* 182 */     this.charset = (Charset)ObjectUtil.checkNotNull(builder.charset, "charset");
/* 183 */     this.maxParams = ObjectUtil.checkPositive(builder.maxParams, "maxParams");
/* 184 */     this.semicolonIsNormalChar = builder.semicolonIsNormalChar;
/* 185 */     this.htmlQueryDecoding = builder.htmlQueryDecoding;
/* 186 */     this.pathEndIdx = rawPath.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return uri();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uri() {
/* 198 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String path() {
/* 205 */     if (this.path == null) {
/* 206 */       this.path = decodeComponent(this.uri, 0, pathEndIdx(), this.charset, false);
/*     */     }
/* 208 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> parameters() {
/* 215 */     if (this.params == null) {
/* 216 */       this.params = decodeParams(this.uri, pathEndIdx(), this.charset, this.maxParams);
/*     */     }
/* 218 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String rawPath() {
/* 225 */     return this.uri.substring(0, pathEndIdx());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String rawQuery() {
/* 232 */     int start = pathEndIdx() + 1;
/* 233 */     return (start < this.uri.length()) ? this.uri.substring(start) : "";
/*     */   }
/*     */   
/*     */   private int pathEndIdx() {
/* 237 */     if (this.pathEndIdx == -1) {
/* 238 */       this.pathEndIdx = findPathEndIndex(this.uri);
/*     */     }
/* 240 */     return this.pathEndIdx;
/*     */   }
/*     */   
/*     */   private Map<String, List<String>> decodeParams(String s, int from, Charset charset, int paramsLimit) {
/* 244 */     int len = s.length();
/* 245 */     if (from >= len) {
/* 246 */       return Collections.emptyMap();
/*     */     }
/* 248 */     if (s.charAt(from) == '?') {
/* 249 */       from++;
/*     */     }
/* 251 */     Map<String, List<String>> params = new LinkedHashMap<>();
/* 252 */     int nameStart = from;
/* 253 */     int valueStart = -1;
/*     */     
/*     */     int i;
/* 256 */     for (i = from; i < len; i++) {
/* 257 */       switch (s.charAt(i)) {
/*     */         case '=':
/* 259 */           if (nameStart == i) {
/* 260 */             nameStart = i + 1; break;
/* 261 */           }  if (valueStart < nameStart) {
/* 262 */             valueStart = i + 1;
/*     */           }
/*     */           break;
/*     */         case ';':
/* 266 */           if (this.semicolonIsNormalChar) {
/*     */             break;
/*     */           }
/*     */ 
/*     */         
/*     */         case '&':
/* 272 */           paramsLimit--;
/* 273 */           if (addParam(s, nameStart, valueStart, i, params, charset) && paramsLimit == 0) {
/* 274 */             return params;
/*     */           }
/*     */           
/* 277 */           nameStart = i + 1;
/*     */           break;
/*     */         
/*     */         case '#':
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 285 */     addParam(s, nameStart, valueStart, i, params, charset);
/* 286 */     return params;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addParam(String s, int nameStart, int valueStart, int valueEnd, Map<String, List<String>> params, Charset charset) {
/* 291 */     if (nameStart >= valueEnd) {
/* 292 */       return false;
/*     */     }
/* 294 */     if (valueStart <= nameStart) {
/* 295 */       valueStart = valueEnd + 1;
/*     */     }
/* 297 */     String name = decodeComponent(s, nameStart, valueStart - 1, charset, this.htmlQueryDecoding);
/* 298 */     String value = decodeComponent(s, valueStart, valueEnd, charset, this.htmlQueryDecoding);
/* 299 */     List<String> values = params.get(name);
/* 300 */     if (values == null) {
/* 301 */       values = new ArrayList<>(1);
/* 302 */       params.put(name, values);
/*     */     } 
/* 304 */     values.add(value);
/* 305 */     return true;
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
/*     */   public static String decodeComponent(String s) {
/* 320 */     return decodeComponent(s, HttpConstants.DEFAULT_CHARSET);
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
/*     */   public static String decodeComponent(String s, Charset charset) {
/* 346 */     if (s == null) {
/* 347 */       return "";
/*     */     }
/* 349 */     return decodeComponent(s, 0, s.length(), charset, true);
/*     */   }
/*     */   
/*     */   private static String decodeComponent(String s, int from, int toExcluded, Charset charset, boolean plusToSpace) {
/* 353 */     int len = toExcluded - from;
/* 354 */     if (len <= 0) {
/* 355 */       return "";
/*     */     }
/* 357 */     int firstEscaped = -1;
/* 358 */     for (int i = from; i < toExcluded; i++) {
/* 359 */       char c = s.charAt(i);
/* 360 */       if (c == '%' || (c == '+' && plusToSpace)) {
/* 361 */         firstEscaped = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 365 */     if (firstEscaped == -1) {
/* 366 */       return s.substring(from, toExcluded);
/*     */     }
/*     */ 
/*     */     
/* 370 */     int decodedCapacity = (toExcluded - firstEscaped) / 3;
/* 371 */     byte[] buf = PlatformDependent.allocateUninitializedArray(decodedCapacity);
/*     */ 
/*     */     
/* 374 */     StringBuilder strBuf = new StringBuilder(len);
/* 375 */     strBuf.append(s, from, firstEscaped);
/*     */     
/* 377 */     for (int j = firstEscaped; j < toExcluded; j++) {
/* 378 */       char c = s.charAt(j);
/* 379 */       if (c != '%') {
/* 380 */         strBuf.append((c != '+' || !plusToSpace) ? c : 32);
/*     */       }
/*     */       else {
/*     */         
/* 384 */         int bufIdx = 0;
/*     */         do {
/* 386 */           if (j + 3 > toExcluded) {
/* 387 */             throw new IllegalArgumentException("unterminated escape sequence at index " + j + " of: " + s);
/*     */           }
/* 389 */           buf[bufIdx++] = StringUtil.decodeHexByte(s, j + 1);
/* 390 */           j += 3;
/* 391 */         } while (j < toExcluded && s.charAt(j) == '%');
/* 392 */         j--;
/*     */         
/* 394 */         strBuf.append(new String(buf, 0, bufIdx, charset));
/*     */       } 
/* 396 */     }  return strBuf.toString();
/*     */   }
/*     */   
/*     */   private static int findPathEndIndex(String uri) {
/* 400 */     int len = uri.length();
/* 401 */     for (int i = 0; i < len; i++) {
/* 402 */       char c = uri.charAt(i);
/* 403 */       if (c == '?' || c == '#') {
/* 404 */         return i;
/*     */       }
/*     */     } 
/* 407 */     return len;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 411 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     private boolean hasPath = true;
/* 416 */     private int maxParams = 1024;
/*     */     private boolean semicolonIsNormalChar;
/* 418 */     private Charset charset = HttpConstants.DEFAULT_CHARSET;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean htmlQueryDecoding = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder hasPath(boolean hasPath) {
/* 432 */       this.hasPath = hasPath;
/* 433 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxParams(int maxParams) {
/* 443 */       this.maxParams = maxParams;
/* 444 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder semicolonIsNormalChar(boolean semicolonIsNormalChar) {
/* 455 */       this.semicolonIsNormalChar = semicolonIsNormalChar;
/* 456 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder charset(Charset charset) {
/* 466 */       this.charset = charset;
/* 467 */       return this;
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
/*     */     public Builder htmlQueryDecoding(boolean htmlQueryDecoding) {
/* 482 */       this.htmlQueryDecoding = htmlQueryDecoding;
/* 483 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QueryStringDecoder build(String uri) {
/* 493 */       return new QueryStringDecoder(this, uri);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QueryStringDecoder build(URI uri) {
/* 504 */       return new QueryStringDecoder(this, uri);
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\QueryStringDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */