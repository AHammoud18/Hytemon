/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.util.collection.LongObjectHashMap;
/*     */ import io.netty.util.collection.LongObjectMap;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Http3Settings
/*     */   implements Iterable<Map.Entry<Long, Long>>
/*     */ {
/*     */   private final LongObjectMap<Long> settings;
/*  50 */   private static final Long TRUE = Long.valueOf(1L);
/*  51 */   private static final Long FALSE = Long.valueOf(0L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings() {
/*  57 */     this.settings = (LongObjectMap<Long>)new LongObjectHashMap((Http3SettingIdentifier.values()).length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Http3Settings(int initialCapacity) {
/*  66 */     this.settings = (LongObjectMap<Long>)new LongObjectHashMap(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Http3Settings(int initialCapacity, float loadFactor) {
/*  76 */     this.settings = (LongObjectMap<Long>)new LongObjectHashMap(initialCapacity, loadFactor);
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
/*     */   @Nullable
/*     */   public Long put(long key, Long value) {
/*  94 */     if (Http3CodecUtils.isReservedHttp2Setting(key)) {
/*  95 */       throw new IllegalArgumentException("Setting is reserved for HTTP/2: " + key);
/*     */     }
/*     */     
/*  98 */     Http3SettingIdentifier identifier = Http3SettingIdentifier.fromId(key);
/*     */ 
/*     */     
/* 101 */     if (identifier == null) {
/* 102 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 106 */     verifyStandardSetting(identifier, value);
/*     */     
/* 108 */     return (Long)this.settings.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long get(long key) {
/* 119 */     return (Long)this.settings.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long qpackMaxTableCapacity() {
/* 129 */     return get(Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY.id());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings qpackMaxTableCapacity(long value) {
/* 139 */     put(Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY.id(), Long.valueOf(value));
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long maxFieldSectionSize() {
/* 150 */     return get(Http3SettingIdentifier.HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE.id());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings maxFieldSectionSize(long value) {
/* 160 */     put(Http3SettingIdentifier.HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE.id(), Long.valueOf(value));
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long qpackBlockedStreams() {
/* 171 */     return get(Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS.id());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings qpackBlockedStreams(long value) {
/* 181 */     put(Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS.id(), Long.valueOf(value));
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean connectProtocolEnabled() {
/* 192 */     Long value = get(Http3SettingIdentifier.HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL.id());
/* 193 */     return (value == null) ? null : Boolean.valueOf(TRUE.equals(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings enableConnectProtocol(boolean enabled) {
/* 203 */     put(Http3SettingIdentifier.HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL.id(), enabled ? TRUE : FALSE);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean h3DatagramEnabled() {
/* 214 */     Long value = get(Http3SettingIdentifier.HTTP3_SETTINGS_H3_DATAGRAM.id());
/* 215 */     return (value == null) ? null : Boolean.valueOf(TRUE.equals(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings enableH3Datagram(boolean enabled) {
/* 225 */     put(Http3SettingIdentifier.HTTP3_SETTINGS_H3_DATAGRAM.id(), enabled ? TRUE : FALSE);
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Settings putAll(Http3Settings http3Settings) {
/* 236 */     ObjectUtil.checkNotNull(http3Settings, "http3Settings");
/* 237 */     this.settings.putAll((Map)http3Settings.settings);
/* 238 */     return this;
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
/*     */   public static Http3Settings defaultSettings() {
/* 254 */     return (new Http3Settings())
/* 255 */       .qpackMaxTableCapacity(0L)
/* 256 */       .qpackBlockedStreams(0L)
/* 257 */       .maxFieldSectionSize(Long.MAX_VALUE)
/* 258 */       .enableConnectProtocol(false)
/* 259 */       .enableH3Datagram(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<Long, Long>> iterator() {
/* 270 */     final Iterator<LongObjectMap.PrimitiveEntry<Long>> it = this.settings.entries().iterator();
/* 271 */     return new Iterator<Map.Entry<Long, Long>>()
/*     */       {
/*     */         public boolean hasNext() {
/* 274 */           return it.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public Map.Entry<Long, Long> next() {
/* 279 */           LongObjectMap.PrimitiveEntry<Long> entry = it.next();
/* 280 */           return new AbstractMap.SimpleImmutableEntry<>(Long.valueOf(entry.key()), (Long)entry.value());
/*     */         }
/*     */       };
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
/*     */   public boolean equals(Object o) {
/* 294 */     if (this == o) {
/* 295 */       return true;
/*     */     }
/* 297 */     if (!(o instanceof Http3Settings)) {
/* 298 */       return false;
/*     */     }
/* 300 */     Http3Settings that = (Http3Settings)o;
/* 301 */     return this.settings.equals(that.settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 311 */     return this.settings.hashCode();
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
/*     */   public String toString() {
/* 324 */     StringBuilder sb = new StringBuilder("Http3Settings{");
/* 325 */     boolean first = true;
/* 326 */     for (LongObjectMap.PrimitiveEntry<Long> e : (Iterable<LongObjectMap.PrimitiveEntry<Long>>)this.settings.entries()) {
/* 327 */       if (!first) {
/* 328 */         sb.append(", ");
/*     */       }
/* 330 */       first = false;
/* 331 */       sb.append("0x").append(Long.toHexString(e.key())).append('=').append(e.value());
/*     */     } 
/* 333 */     return sb.append('}').toString();
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
/*     */   private static void verifyStandardSetting(Http3SettingIdentifier identifier, Long value) {
/* 345 */     ObjectUtil.checkNotNull(value, "value");
/* 346 */     ObjectUtil.checkNotNull(identifier, "identifier");
/*     */     
/* 348 */     switch (identifier) {
/*     */       case HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY:
/*     */       case HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS:
/*     */       case HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE:
/* 352 */         if (value.longValue() < 0L) {
/* 353 */           throw new IllegalArgumentException("Setting 0x" + Long.toHexString(identifier.id()) + " invalid: " + value + " (must be >= 0)");
/*     */         }
/*     */         return;
/*     */       
/*     */       case HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL:
/*     */       case HTTP3_SETTINGS_H3_DATAGRAM:
/* 359 */         if (value.longValue() != 0L && value.longValue() != 1L) {
/* 360 */           throw new IllegalArgumentException("Invalid: " + value + "for " + 
/*     */               
/* 362 */               Http3SettingIdentifier.valueOf(String.valueOf(identifier)) + " (expected 0 or 1)");
/*     */         }
/*     */         return;
/*     */     } 
/*     */     
/* 367 */     if (value.longValue() < 0L)
/* 368 */       throw new IllegalArgumentException("Setting 0x" + 
/* 369 */           Long.toHexString(identifier.id()) + " invalid: " + value); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3Settings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */