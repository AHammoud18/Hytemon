/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class DomainNameMappingBuilder<V>
/*     */ {
/*     */   private final V defaultValue;
/*     */   private final Map<String, V> map;
/*     */   
/*     */   public DomainNameMappingBuilder(V defaultValue) {
/*  45 */     this(4, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainNameMappingBuilder(int initialCapacity, V defaultValue) {
/*  56 */     this.defaultValue = (V)ObjectUtil.checkNotNull(defaultValue, "defaultValue");
/*  57 */     this.map = new LinkedHashMap<>(initialCapacity);
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
/*     */   public DomainNameMappingBuilder<V> add(String hostname, V output) {
/*  73 */     this.map.put((String)ObjectUtil.checkNotNull(hostname, "hostname"), (V)ObjectUtil.checkNotNull(output, "output"));
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainNameMapping<V> build() {
/*  84 */     return new ImmutableDomainNameMapping<>(this.defaultValue, this.map);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableDomainNameMapping<V>
/*     */     extends DomainNameMapping<V>
/*     */   {
/*     */     private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
/*     */     
/*     */     private static final String REPR_MAP_OPENING = ", map: {";
/*     */     
/*     */     private static final String REPR_MAP_CLOSING = "})";
/*     */     
/*  97 */     private static final int REPR_CONST_PART_LENGTH = "ImmutableDomainNameMapping(default: "
/*  98 */       .length() + ", map: {".length() + "})".length();
/*     */     
/*     */     private final String[] domainNamePatterns;
/*     */     
/*     */     private final V[] values;
/*     */     private final Map<String, V> map;
/*     */     
/*     */     private ImmutableDomainNameMapping(V defaultValue, Map<String, V> map) {
/* 106 */       super((Map<String, V>)null, defaultValue);
/*     */       
/* 108 */       Set<Map.Entry<String, V>> mappings = map.entrySet();
/* 109 */       int numberOfMappings = mappings.size();
/* 110 */       this.domainNamePatterns = new String[numberOfMappings];
/* 111 */       this.values = (V[])new Object[numberOfMappings];
/*     */       
/* 113 */       Map<String, V> mapCopy = new LinkedHashMap<>(map.size());
/* 114 */       int index = 0;
/* 115 */       for (Map.Entry<String, V> mapping : mappings) {
/* 116 */         String hostname = normalizeHostname(mapping.getKey());
/* 117 */         V value = mapping.getValue();
/* 118 */         this.domainNamePatterns[index] = hostname;
/* 119 */         this.values[index] = value;
/* 120 */         mapCopy.put(hostname, value);
/* 121 */         index++;
/*     */       } 
/*     */       
/* 124 */       this.map = Collections.unmodifiableMap(mapCopy);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DomainNameMapping<V> add(String hostname, V output) {
/* 130 */       throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V map(String hostname) {
/* 136 */       if (hostname != null) {
/* 137 */         hostname = normalizeHostname(hostname);
/*     */         
/* 139 */         int length = this.domainNamePatterns.length;
/* 140 */         for (int index = 0; index < length; index++) {
/* 141 */           if (matches(this.domainNamePatterns[index], hostname)) {
/* 142 */             return this.values[index];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       return this.defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, V> asMap() {
/* 152 */       return this.map;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 157 */       String defaultValueStr = this.defaultValue.toString();
/*     */       
/* 159 */       int numberOfMappings = this.domainNamePatterns.length;
/* 160 */       if (numberOfMappings == 0) {
/* 161 */         return "ImmutableDomainNameMapping(default: " + defaultValueStr + ", map: {" + "})";
/*     */       }
/*     */       
/* 164 */       String pattern0 = this.domainNamePatterns[0];
/* 165 */       String value0 = this.values[0].toString();
/* 166 */       int oneMappingLength = pattern0.length() + value0.length() + 3;
/* 167 */       int estimatedBufferSize = estimateBufferSize(defaultValueStr.length(), numberOfMappings, oneMappingLength);
/*     */ 
/*     */       
/* 170 */       StringBuilder sb = (new StringBuilder(estimatedBufferSize)).append("ImmutableDomainNameMapping(default: ").append(defaultValueStr).append(", map: {");
/*     */       
/* 172 */       appendMapping(sb, pattern0, value0);
/* 173 */       for (int index = 1; index < numberOfMappings; index++) {
/* 174 */         sb.append(", ");
/* 175 */         appendMapping(sb, index);
/*     */       } 
/*     */       
/* 178 */       return sb.append("})").toString();
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
/*     */     private static int estimateBufferSize(int defaultValueLength, int numberOfMappings, int estimatedMappingLength) {
/* 194 */       return REPR_CONST_PART_LENGTH + defaultValueLength + (int)((estimatedMappingLength * numberOfMappings) * 1.1D);
/*     */     }
/*     */ 
/*     */     
/*     */     private StringBuilder appendMapping(StringBuilder sb, int mappingIndex) {
/* 199 */       return appendMapping(sb, this.domainNamePatterns[mappingIndex], this.values[mappingIndex].toString());
/*     */     }
/*     */     
/*     */     private static StringBuilder appendMapping(StringBuilder sb, String domainNamePattern, String value) {
/* 203 */       return sb.append(domainNamePattern).append('=').append(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\DomainNameMappingBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */