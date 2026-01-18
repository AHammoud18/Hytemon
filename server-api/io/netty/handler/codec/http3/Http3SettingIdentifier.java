/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Http3SettingIdentifier
/*     */ {
/*  39 */   HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY(1L),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE(6L),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS(7L),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   HTTP3_SETTINGS_ENABLE_CONNECT_PROTOCOL(8L),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   HTTP3_SETTINGS_H3_DATAGRAM(51L);
/*     */   private final long id;
/*     */   
/*     */   static {
/*  93 */     LOOKUP = Collections.unmodifiableMap(
/*  94 */         (Map<? extends Long, ? extends Http3SettingIdentifier>)Arrays.<Http3SettingIdentifier>stream(values()).collect(Collectors.toMap(Http3SettingIdentifier::id, Function.identity())));
/*     */   }
/*     */   private static final Map<Long, Http3SettingIdentifier> LOOKUP;
/*     */   Http3SettingIdentifier(long id) {
/*  98 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long id() {
/* 109 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Http3SettingIdentifier fromId(long id) {
/* 119 */     return LOOKUP.get(Long.valueOf(id));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3SettingIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */