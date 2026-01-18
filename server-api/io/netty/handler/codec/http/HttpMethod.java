/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.AsciiString;
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
/*     */ 
/*     */ 
/*     */ public class HttpMethod
/*     */   implements Comparable<HttpMethod>
/*     */ {
/*     */   private static final String GET_STRING = "GET";
/*     */   private static final String POST_STRING = "POST";
/*  39 */   public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final HttpMethod GET = new HttpMethod("GET");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final HttpMethod HEAD = new HttpMethod("HEAD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final HttpMethod POST = new HttpMethod("POST");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final HttpMethod PUT = new HttpMethod("PUT");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final HttpMethod PATCH = new HttpMethod("PATCH");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final HttpMethod DELETE = new HttpMethod("DELETE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final HttpMethod TRACE = new HttpMethod("TRACE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final HttpMethod CONNECT = new HttpMethod("CONNECT");
/*     */ 
/*     */   
/*     */   private final AsciiString name;
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpMethod valueOf(String name) {
/*  97 */     switch (name) { case "OPTIONS":
/*  98 */         return OPTIONS;
/*  99 */       case "GET": return GET;
/* 100 */       case "HEAD": return HEAD;
/* 101 */       case "POST": return POST;
/* 102 */       case "PUT": return PUT;
/* 103 */       case "PATCH": return PATCH;
/* 104 */       case "DELETE": return DELETE;
/* 105 */       case "TRACE": return TRACE;
/* 106 */       case "CONNECT": return CONNECT; }
/* 107 */      return new HttpMethod(name);
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
/*     */   public HttpMethod(String name) {
/* 121 */     name = ObjectUtil.checkNonEmptyAfterTrim(name, "name");
/* 122 */     int index = HttpUtil.validateToken(name);
/* 123 */     if (index != -1) {
/* 124 */       throw new IllegalArgumentException("Illegal character in HTTP Method: 0x" + 
/* 125 */           Integer.toHexString(name.charAt(index)));
/*     */     }
/* 127 */     this.name = AsciiString.cached(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 134 */     return this.name.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsciiString asciiName() {
/* 141 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return name().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 151 */     if (this == o) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (!(o instanceof HttpMethod)) {
/* 155 */       return false;
/*     */     }
/*     */     
/* 158 */     HttpMethod that = (HttpMethod)o;
/* 159 */     return name().equals(that.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     return this.name.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(HttpMethod o) {
/* 169 */     if (o == this) {
/* 170 */       return 0;
/*     */     }
/* 172 */     return name().compareTo(o.name());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */