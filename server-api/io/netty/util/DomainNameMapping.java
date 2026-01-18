/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.IDN;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Locale;
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
/*     */ @Deprecated
/*     */ public class DomainNameMapping<V>
/*     */   implements Mapping<String, V>
/*     */ {
/*     */   final V defaultValue;
/*     */   private final Map<String, V> map;
/*     */   private final Map<String, V> unmodifiableMap;
/*     */   
/*     */   @Deprecated
/*     */   public DomainNameMapping(V defaultValue) {
/*  54 */     this(4, defaultValue);
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
/*     */   public DomainNameMapping(int initialCapacity, V defaultValue) {
/*  67 */     this(new LinkedHashMap<>(initialCapacity), defaultValue);
/*     */   }
/*     */   
/*     */   DomainNameMapping(Map<String, V> map, V defaultValue) {
/*  71 */     this.defaultValue = (V)ObjectUtil.checkNotNull(defaultValue, "defaultValue");
/*  72 */     this.map = map;
/*  73 */     this
/*  74 */       .unmodifiableMap = (map != null) ? Collections.<String, V>unmodifiableMap(map) : null;
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
/*     */   @Deprecated
/*     */   public DomainNameMapping<V> add(String hostname, V output) {
/*  91 */     this.map.put(normalizeHostname((String)ObjectUtil.checkNotNull(hostname, "hostname")), (V)ObjectUtil.checkNotNull(output, "output"));
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean matches(String template, String hostName) {
/*  99 */     if (template.startsWith("*.")) {
/* 100 */       return (template.regionMatches(2, hostName, 0, hostName.length()) || 
/* 101 */         StringUtil.commonSuffixOfLength(hostName, template, template.length() - 1));
/*     */     }
/* 103 */     return template.equals(hostName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String normalizeHostname(String hostname) {
/* 110 */     if (needsNormalization(hostname)) {
/* 111 */       hostname = IDN.toASCII(hostname, 1);
/*     */     }
/* 113 */     return hostname.toLowerCase(Locale.US);
/*     */   }
/*     */   
/*     */   private static boolean needsNormalization(String hostname) {
/* 117 */     int length = hostname.length();
/* 118 */     for (int i = 0; i < length; i++) {
/* 119 */       int c = hostname.charAt(i);
/* 120 */       if (c > 127) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public V map(String hostname) {
/* 129 */     if (hostname != null) {
/* 130 */       hostname = normalizeHostname(hostname);
/*     */       
/* 132 */       for (Map.Entry<String, V> entry : this.map.entrySet()) {
/* 133 */         if (matches(entry.getKey(), hostname)) {
/* 134 */           return entry.getValue();
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, V> asMap() {
/* 145 */     return this.unmodifiableMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     return StringUtil.simpleClassName(this) + "(default: " + this.defaultValue + ", map: " + this.map + ')';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\DomainNameMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */