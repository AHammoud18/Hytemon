/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpVersion
/*     */   implements Comparable<HttpVersion>
/*     */ {
/*     */   static final String HTTP_1_0_STRING = "HTTP/1.0";
/*     */   static final String HTTP_1_1_STRING = "HTTP/1.1";
/*  38 */   public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);
/*     */   
/*     */   private final String protocolName;
/*     */   
/*     */   private final int majorVersion;
/*     */   private final int minorVersion;
/*     */   private final String text;
/*     */   private final boolean keepAliveDefault;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public static HttpVersion valueOf(String text) {
/*  54 */     return valueOf(text, false);
/*     */   }
/*     */   
/*     */   static HttpVersion valueOf(String text, boolean strict) {
/*  58 */     ObjectUtil.checkNotNull(text, "text");
/*     */ 
/*     */     
/*  61 */     if (text == "HTTP/1.1") {
/*  62 */       return HTTP_1_1;
/*     */     }
/*  64 */     if (text == "HTTP/1.0") {
/*  65 */       return HTTP_1_0;
/*     */     }
/*     */     
/*  68 */     text = text.trim();
/*     */     
/*  70 */     if (text.isEmpty()) {
/*  71 */       throw new IllegalArgumentException("text is empty (possibly HTTP/0.9)");
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
/*  82 */     HttpVersion version = version0(text);
/*  83 */     if (version == null) {
/*  84 */       version = new HttpVersion(text, strict, true);
/*     */     }
/*  86 */     return version;
/*     */   }
/*     */   
/*     */   private static HttpVersion version0(String text) {
/*  90 */     if ("HTTP/1.1".equals(text)) {
/*  91 */       return HTTP_1_1;
/*     */     }
/*  93 */     if ("HTTP/1.0".equals(text)) {
/*  94 */       return HTTP_1_0;
/*     */     }
/*  96 */     return null;
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
/*     */   public HttpVersion(String text, boolean keepAliveDefault) {
/* 118 */     this(text, false, keepAliveDefault);
/*     */   }
/*     */   
/*     */   HttpVersion(String text, boolean strict, boolean keepAliveDefault) {
/* 122 */     text = ObjectUtil.checkNonEmptyAfterTrim(text, "text").toUpperCase();
/*     */     
/* 124 */     if (strict) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (text.length() != 8 || !text.startsWith("HTTP/") || text.charAt(6) != '.') {
/* 130 */         throw new IllegalArgumentException("invalid version format: " + text);
/*     */       }
/* 132 */       this.protocolName = "HTTP";
/* 133 */       this.majorVersion = toDecimal(text.charAt(5));
/* 134 */       this.minorVersion = toDecimal(text.charAt(7));
/*     */     } else {
/* 136 */       int slashIndex = text.indexOf('/');
/* 137 */       int dotIndex = text.indexOf('.', slashIndex + 1);
/*     */       
/* 139 */       if (slashIndex <= 0 || dotIndex <= slashIndex + 1 || dotIndex >= text
/* 140 */         .length() - 1 || hasWhitespace(text, slashIndex)) {
/* 141 */         throw new IllegalArgumentException("invalid version format: " + text);
/*     */       }
/*     */       
/* 144 */       this.protocolName = text.substring(0, slashIndex);
/* 145 */       this.majorVersion = parseInt(text, slashIndex + 1, dotIndex);
/* 146 */       this.minorVersion = parseInt(text, dotIndex + 1, text.length());
/*     */     } 
/*     */     
/* 149 */     this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
/* 150 */     this.keepAliveDefault = keepAliveDefault;
/* 151 */     this.bytes = null;
/*     */   }
/*     */   
/*     */   private static boolean hasWhitespace(String s, int end) {
/* 155 */     for (int i = 0; i < end; i++) {
/* 156 */       if (Character.isWhitespace(s.charAt(i))) {
/* 157 */         return true;
/*     */       }
/*     */     } 
/* 160 */     return false;
/*     */   }
/*     */   
/*     */   private static int parseInt(String text, int start, int end) {
/* 164 */     int result = 0;
/* 165 */     for (int i = start; i < end; i++) {
/* 166 */       char ch = text.charAt(i);
/* 167 */       result = result * 10 + toDecimal(ch);
/*     */     } 
/* 169 */     return result;
/*     */   }
/*     */   
/*     */   private static int toDecimal(int value) {
/* 173 */     if (value < 48 || value > 57) {
/* 174 */       throw new IllegalArgumentException("Invalid version number, only 0-9 (0x30-0x39) allowed, but received a '" + (char)value + "' (0x" + 
/* 175 */           Integer.toHexString(value) + ")");
/*     */     }
/* 177 */     return value - 48;
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
/*     */   public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault) {
/* 194 */     this(protocolName, majorVersion, minorVersion, keepAliveDefault, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault, boolean bytes) {
/* 200 */     protocolName = ObjectUtil.checkNonEmptyAfterTrim(protocolName, "protocolName").toUpperCase();
/*     */     
/* 202 */     for (int i = 0; i < protocolName.length(); i++) {
/* 203 */       if (Character.isISOControl(protocolName.charAt(i)) || 
/* 204 */         Character.isWhitespace(protocolName.charAt(i))) {
/* 205 */         throw new IllegalArgumentException("invalid character in protocolName");
/*     */       }
/*     */     } 
/*     */     
/* 209 */     ObjectUtil.checkPositiveOrZero(majorVersion, "majorVersion");
/* 210 */     ObjectUtil.checkPositiveOrZero(minorVersion, "minorVersion");
/*     */     
/* 212 */     this.protocolName = protocolName;
/* 213 */     this.majorVersion = majorVersion;
/* 214 */     this.minorVersion = minorVersion;
/* 215 */     this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
/* 216 */     this.keepAliveDefault = keepAliveDefault;
/*     */     
/* 218 */     if (bytes) {
/* 219 */       this.bytes = this.text.getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 221 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String protocolName() {
/* 229 */     return this.protocolName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int majorVersion() {
/* 236 */     return this.majorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minorVersion() {
/* 243 */     return this.minorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/* 250 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeepAliveDefault() {
/* 258 */     return this.keepAliveDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return text();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 271 */     return (protocolName().hashCode() * 31 + majorVersion()) * 31 + 
/* 272 */       minorVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 277 */     if (!(o instanceof HttpVersion)) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     HttpVersion that = (HttpVersion)o;
/* 282 */     return (minorVersion() == that.minorVersion() && 
/* 283 */       majorVersion() == that.majorVersion() && 
/* 284 */       protocolName().equals(that.protocolName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(HttpVersion o) {
/* 289 */     int v = protocolName().compareTo(o.protocolName());
/* 290 */     if (v != 0) {
/* 291 */       return v;
/*     */     }
/*     */     
/* 294 */     v = majorVersion() - o.majorVersion();
/* 295 */     if (v != 0) {
/* 296 */       return v;
/*     */     }
/*     */     
/* 299 */     return minorVersion() - o.minorVersion();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 303 */     if (this.bytes == null) {
/* 304 */       buf.writeCharSequence(this.text, CharsetUtil.US_ASCII);
/*     */     } else {
/* 306 */       buf.writeBytes(this.bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */