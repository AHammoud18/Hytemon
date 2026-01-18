/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Http3SettingsFrame
/*     */   extends Http3ControlStreamFrame, Iterable<Map.Entry<Long, Long>>
/*     */ {
/*     */   @Deprecated
/*  33 */   public static final long HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY = Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY.id();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  41 */   public static final long HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS = Http3SettingIdentifier.HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS.id();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  49 */   public static final long HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL = Http3SettingIdentifier.HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL.id();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  57 */   public static final long HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE = Http3SettingIdentifier.HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE.id();
/*     */   
/*     */   default Http3Settings settings() {
/*  60 */     throw new UnsupportedOperationException("Http3SettingsFrame.settings() not implemented in this version");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default long type() {
/*  66 */     return 4L;
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
/*     */   @Nullable
/*     */   default Long get(long key) {
/*  80 */     return settings().get(key);
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
/*     */   @Deprecated
/*     */   default Long getOrDefault(long key, long defaultValue) {
/*  94 */     Long val = get(key);
/*  95 */     return Long.valueOf((val == null) ? defaultValue : val.longValue());
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
/*     */   @Nullable
/*     */   default Long put(long key, Long value) {
/* 109 */     return settings().put(key, value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3SettingsFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */