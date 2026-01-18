/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class DomainWildcardMappingBuilder<V>
/*     */ {
/*     */   private final V defaultValue;
/*     */   private final Map<String, V> map;
/*     */   
/*     */   public DomainWildcardMappingBuilder(V defaultValue) {
/*  40 */     this(4, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainWildcardMappingBuilder(int initialCapacity, V defaultValue) {
/*  51 */     this.defaultValue = (V)ObjectUtil.checkNotNull(defaultValue, "defaultValue");
/*  52 */     this.map = new LinkedHashMap<>(initialCapacity);
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
/*     */   public DomainWildcardMappingBuilder<V> add(String hostname, V output) {
/*  73 */     this.map.put(normalizeHostName(hostname), 
/*  74 */         (V)ObjectUtil.checkNotNull(output, "output"));
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   private String normalizeHostName(String hostname) {
/*  79 */     ObjectUtil.checkNotNull(hostname, "hostname");
/*  80 */     if (hostname.isEmpty() || hostname.charAt(0) == '.') {
/*  81 */       throw new IllegalArgumentException("Hostname '" + hostname + "' not valid");
/*     */     }
/*  83 */     hostname = ImmutableDomainWildcardMapping.normalize((String)ObjectUtil.checkNotNull(hostname, "hostname"));
/*  84 */     if (hostname.charAt(0) == '*') {
/*  85 */       if (hostname.length() < 3 || hostname.charAt(1) != '.') {
/*  86 */         throw new IllegalArgumentException("Wildcard Hostname '" + hostname + "'not valid");
/*     */       }
/*  88 */       return hostname.substring(1);
/*     */     } 
/*  90 */     return hostname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mapping<String, V> build() {
/*  98 */     return new ImmutableDomainWildcardMapping<>(this.defaultValue, this.map);
/*     */   }
/*     */   
/*     */   private static final class ImmutableDomainWildcardMapping<V>
/*     */     implements Mapping<String, V> {
/*     */     private static final String REPR_HEADER = "ImmutableDomainWildcardMapping(default: ";
/*     */     private static final String REPR_MAP_OPENING = ", map: ";
/*     */     private static final String REPR_MAP_CLOSING = ")";
/*     */     private final V defaultValue;
/*     */     private final Map<String, V> map;
/*     */     
/*     */     ImmutableDomainWildcardMapping(V defaultValue, Map<String, V> map) {
/* 110 */       this.defaultValue = defaultValue;
/* 111 */       this.map = new LinkedHashMap<>(map);
/*     */     }
/*     */ 
/*     */     
/*     */     public V map(String hostname) {
/* 116 */       if (hostname != null) {
/* 117 */         hostname = normalize(hostname);
/*     */ 
/*     */         
/* 120 */         V value = this.map.get(hostname);
/* 121 */         if (value != null) {
/* 122 */           return value;
/*     */         }
/*     */ 
/*     */         
/* 126 */         int idx = hostname.indexOf('.');
/* 127 */         if (idx != -1) {
/* 128 */           value = this.map.get(hostname.substring(idx));
/* 129 */           if (value != null) {
/* 130 */             return value;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 135 */       return this.defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     static String normalize(String hostname) {
/* 140 */       return DomainNameMapping.normalizeHostname(hostname);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 145 */       StringBuilder sb = new StringBuilder();
/* 146 */       sb.append("ImmutableDomainWildcardMapping(default: ").append(this.defaultValue).append(", map: ").append('{');
/*     */       
/* 148 */       for (Map.Entry<String, V> entry : this.map.entrySet()) {
/* 149 */         String hostname = entry.getKey();
/* 150 */         if (hostname.charAt(0) == '.') {
/* 151 */           hostname = '*' + hostname;
/*     */         }
/* 153 */         sb.append(hostname).append('=').append(entry.getValue()).append(", ");
/*     */       } 
/* 155 */       sb.setLength(sb.length() - 2);
/* 156 */       return sb.append('}').append(")").toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\DomainWildcardMappingBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */