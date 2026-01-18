/*     */ package io.netty.handler.codec.http.cookie;
/*     */ 
/*     */ import io.netty.handler.codec.DateFormatter;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public final class ServerCookieEncoder
/*     */   extends CookieEncoder
/*     */ {
/*  61 */   public static final ServerCookieEncoder STRICT = new ServerCookieEncoder(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final ServerCookieEncoder LAX = new ServerCookieEncoder(false);
/*     */   
/*     */   private ServerCookieEncoder(boolean strict) {
/*  70 */     super(strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encode(String name, String value) {
/*  81 */     return encode(new DefaultCookie(name, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encode(Cookie cookie) {
/*  91 */     String name = ((Cookie)ObjectUtil.checkNotNull(cookie, "cookie")).name();
/*  92 */     String value = (cookie.value() != null) ? cookie.value() : "";
/*     */     
/*  94 */     validateCookie(name, value);
/*     */     
/*  96 */     StringBuilder buf = CookieUtil.stringBuilder();
/*     */     
/*  98 */     if (cookie.wrap()) {
/*  99 */       CookieUtil.addQuoted(buf, name, value);
/*     */     } else {
/* 101 */       CookieUtil.add(buf, name, value);
/*     */     } 
/*     */     
/* 104 */     if (cookie.maxAge() != Long.MIN_VALUE) {
/* 105 */       CookieUtil.add(buf, "Max-Age", cookie.maxAge());
/* 106 */       Date expires = new Date(cookie.maxAge() * 1000L + System.currentTimeMillis());
/* 107 */       buf.append("Expires");
/* 108 */       buf.append('=');
/* 109 */       DateFormatter.append(expires, buf);
/* 110 */       buf.append(';');
/* 111 */       buf.append(' ');
/*     */     } 
/*     */     
/* 114 */     if (cookie.path() != null) {
/* 115 */       CookieUtil.add(buf, "Path", cookie.path());
/*     */     }
/*     */     
/* 118 */     if (cookie.domain() != null) {
/* 119 */       CookieUtil.add(buf, "Domain", cookie.domain());
/*     */     }
/* 121 */     if (cookie.isSecure()) {
/* 122 */       CookieUtil.add(buf, "Secure");
/*     */     }
/* 124 */     if (cookie.isHttpOnly()) {
/* 125 */       CookieUtil.add(buf, "HTTPOnly");
/*     */     }
/* 127 */     if (cookie instanceof DefaultCookie) {
/* 128 */       DefaultCookie c = (DefaultCookie)cookie;
/* 129 */       if (c.sameSite() != null) {
/* 130 */         CookieUtil.add(buf, "SameSite", c.sameSite().name());
/*     */       }
/* 132 */       if (c.isPartitioned()) {
/* 133 */         CookieUtil.add(buf, "Partitioned");
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return CookieUtil.stripTrailingSeparator(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> dedup(List<String> encoded, Map<String, Integer> nameToLastIndex) {
/* 147 */     boolean[] isLastInstance = new boolean[encoded.size()];
/* 148 */     for (Iterator<Integer> iterator = nameToLastIndex.values().iterator(); iterator.hasNext(); ) { int idx = ((Integer)iterator.next()).intValue();
/* 149 */       isLastInstance[idx] = true; }
/*     */     
/* 151 */     List<String> dedupd = new ArrayList<>(nameToLastIndex.size());
/* 152 */     for (int i = 0, n = encoded.size(); i < n; i++) {
/* 153 */       if (isLastInstance[i]) {
/* 154 */         dedupd.add(encoded.get(i));
/*     */       }
/*     */     } 
/* 157 */     return dedupd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> encode(Cookie... cookies) {
/*     */     int j;
/* 167 */     if (((Cookie[])ObjectUtil.checkNotNull(cookies, "cookies")).length == 0) {
/* 168 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 171 */     List<String> encoded = new ArrayList<>(cookies.length);
/* 172 */     Map<String, Integer> nameToIndex = (this.strict && cookies.length > 1) ? new HashMap<>() : null;
/* 173 */     boolean hasDupdName = false;
/* 174 */     for (int i = 0; i < cookies.length; i++) {
/* 175 */       Cookie c = cookies[i];
/* 176 */       encoded.add(encode(c));
/* 177 */       if (nameToIndex != null) {
/* 178 */         j = hasDupdName | ((nameToIndex.put(c.name(), Integer.valueOf(i)) != null) ? 1 : 0);
/*     */       }
/*     */     } 
/* 181 */     return (j != 0) ? dedup(encoded, nameToIndex) : encoded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> encode(Collection<? extends Cookie> cookies) {
/*     */     int j;
/* 191 */     if (((Collection)ObjectUtil.checkNotNull(cookies, "cookies")).isEmpty()) {
/* 192 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 195 */     List<String> encoded = new ArrayList<>(cookies.size());
/* 196 */     Map<String, Integer> nameToIndex = (this.strict && cookies.size() > 1) ? new HashMap<>() : null;
/* 197 */     int i = 0;
/* 198 */     boolean hasDupdName = false;
/* 199 */     for (Cookie c : cookies) {
/* 200 */       encoded.add(encode(c));
/* 201 */       if (nameToIndex != null) {
/* 202 */         j = hasDupdName | ((nameToIndex.put(c.name(), Integer.valueOf(i++)) != null) ? 1 : 0);
/*     */       }
/*     */     } 
/* 205 */     return (j != 0) ? dedup(encoded, nameToIndex) : encoded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> encode(Iterable<? extends Cookie> cookies) {
/*     */     int j;
/* 215 */     Iterator<? extends Cookie> cookiesIt = ((Iterable<? extends Cookie>)ObjectUtil.checkNotNull(cookies, "cookies")).iterator();
/* 216 */     if (!cookiesIt.hasNext()) {
/* 217 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 220 */     List<String> encoded = new ArrayList<>();
/* 221 */     Cookie firstCookie = cookiesIt.next();
/* 222 */     Map<String, Integer> nameToIndex = (this.strict && cookiesIt.hasNext()) ? new HashMap<>() : null;
/* 223 */     int i = 0;
/* 224 */     encoded.add(encode(firstCookie));
/* 225 */     boolean hasDupdName = (nameToIndex != null && nameToIndex.put(firstCookie.name(), Integer.valueOf(i++)) != null);
/* 226 */     while (cookiesIt.hasNext()) {
/* 227 */       Cookie c = cookiesIt.next();
/* 228 */       encoded.add(encode(c));
/* 229 */       if (nameToIndex != null) {
/* 230 */         j = hasDupdName | ((nameToIndex.put(c.name(), Integer.valueOf(i++)) != null) ? 1 : 0);
/*     */       }
/*     */     } 
/* 233 */     return (j != 0) ? dedup(encoded, nameToIndex) : encoded;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\ServerCookieEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */