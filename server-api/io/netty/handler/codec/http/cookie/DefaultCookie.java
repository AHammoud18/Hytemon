/*     */ package io.netty.handler.codec.http.cookie;
/*     */ 
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
/*     */ public class DefaultCookie
/*     */   implements Cookie
/*     */ {
/*     */   private final String name;
/*     */   private String value;
/*     */   private boolean wrap;
/*     */   private String domain;
/*     */   private String path;
/*  35 */   private long maxAge = Long.MIN_VALUE;
/*     */   
/*     */   private boolean secure;
/*     */   
/*     */   private boolean httpOnly;
/*     */   
/*     */   private CookieHeaderNames.SameSite sameSite;
/*     */   private boolean partitioned;
/*     */   
/*     */   public DefaultCookie(String name, String value) {
/*  45 */     this.name = ObjectUtil.checkNonEmptyAfterTrim(name, "name");
/*  46 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  51 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String value() {
/*  56 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) {
/*  61 */     this.value = (String)ObjectUtil.checkNotNull(value, "value");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrap() {
/*  66 */     return this.wrap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWrap(boolean wrap) {
/*  71 */     this.wrap = wrap;
/*     */   }
/*     */ 
/*     */   
/*     */   public String domain() {
/*  76 */     return this.domain;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDomain(String domain) {
/*  81 */     this.domain = CookieUtil.validateAttributeValue("domain", domain);
/*     */   }
/*     */ 
/*     */   
/*     */   public String path() {
/*  86 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPath(String path) {
/*  91 */     this.path = CookieUtil.validateAttributeValue("path", path);
/*     */   }
/*     */ 
/*     */   
/*     */   public long maxAge() {
/*  96 */     return this.maxAge;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxAge(long maxAge) {
/* 101 */     this.maxAge = maxAge;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 106 */     return this.secure;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecure(boolean secure) {
/* 111 */     this.secure = secure;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHttpOnly() {
/* 116 */     return this.httpOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHttpOnly(boolean httpOnly) {
/* 121 */     this.httpOnly = httpOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieHeaderNames.SameSite sameSite() {
/* 131 */     return this.sameSite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSameSite(CookieHeaderNames.SameSite sameSite) {
/* 141 */     this.sameSite = sameSite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPartitioned() {
/* 150 */     return this.partitioned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPartitioned(boolean partitioned) {
/* 159 */     this.partitioned = partitioned;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 164 */     return name().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 169 */     if (this == o) {
/* 170 */       return true;
/*     */     }
/*     */     
/* 173 */     if (!(o instanceof Cookie)) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     Cookie that = (Cookie)o;
/* 178 */     if (!name().equals(that.name())) {
/* 179 */       return false;
/*     */     }
/*     */     
/* 182 */     if (path() == null) {
/* 183 */       if (that.path() != null)
/* 184 */         return false; 
/*     */     } else {
/* 186 */       if (that.path() == null)
/* 187 */         return false; 
/* 188 */       if (!path().equals(that.path())) {
/* 189 */         return false;
/*     */       }
/*     */     } 
/* 192 */     if (domain() == null) {
/* 193 */       if (that.domain() != null) {
/* 194 */         return false;
/*     */       }
/*     */     } else {
/* 197 */       return domain().equalsIgnoreCase(that.domain());
/*     */     } 
/*     */     
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Cookie c) {
/* 205 */     int v = name().compareTo(c.name());
/* 206 */     if (v != 0) {
/* 207 */       return v;
/*     */     }
/*     */     
/* 210 */     if (path() == null) {
/* 211 */       if (c.path() != null)
/* 212 */         return -1; 
/*     */     } else {
/* 214 */       if (c.path() == null) {
/* 215 */         return 1;
/*     */       }
/* 217 */       v = path().compareTo(c.path());
/* 218 */       if (v != 0) {
/* 219 */         return v;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (domain() == null) {
/* 224 */       if (c.domain() != null)
/* 225 */         return -1; 
/*     */     } else {
/* 227 */       if (c.domain() == null) {
/* 228 */         return 1;
/*     */       }
/* 230 */       v = domain().compareToIgnoreCase(c.domain());
/* 231 */       return v;
/*     */     } 
/*     */     
/* 234 */     return 0;
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
/*     */   @Deprecated
/*     */   protected String validateValue(String name, String value) {
/* 247 */     return CookieUtil.validateAttributeValue(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 255 */     StringBuilder buf = CookieUtil.stringBuilder().append(name()).append('=').append(value());
/* 256 */     if (domain() != null) {
/* 257 */       buf.append(", domain=")
/* 258 */         .append(domain());
/*     */     }
/* 260 */     if (path() != null) {
/* 261 */       buf.append(", path=")
/* 262 */         .append(path());
/*     */     }
/* 264 */     if (maxAge() >= 0L) {
/* 265 */       buf.append(", maxAge=")
/* 266 */         .append(maxAge())
/* 267 */         .append('s');
/*     */     }
/* 269 */     if (isSecure()) {
/* 270 */       buf.append(", secure");
/*     */     }
/* 272 */     if (isHttpOnly()) {
/* 273 */       buf.append(", HTTPOnly");
/*     */     }
/* 275 */     if (sameSite() != null) {
/* 276 */       buf.append(", SameSite=").append(sameSite());
/*     */     }
/* 278 */     if (isPartitioned()) {
/* 279 */       buf.append(", Partitioned");
/*     */     }
/* 281 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\DefaultCookie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */