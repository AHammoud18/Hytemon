/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Iterator;
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
/*     */ public final class DefaultHttp3SettingsFrame
/*     */   implements Http3SettingsFrame
/*     */ {
/*     */   private final Http3Settings settings;
/*     */   
/*     */   public DefaultHttp3SettingsFrame(Http3Settings settings) {
/*  34 */     this.settings = (Http3Settings)ObjectUtil.checkNotNull(settings, "settings");
/*     */   }
/*     */   
/*     */   public DefaultHttp3SettingsFrame() {
/*  38 */     this.settings = new Http3Settings();
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Settings settings() {
/*  43 */     return this.settings;
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
/*     */   @Nullable
/*     */   public Long get(long key) {
/*  58 */     return this.settings.get(key);
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
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public Long put(long key, Long value) {
/*  74 */     return this.settings.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<Long, Long>> iterator() {
/*  79 */     return this.settings.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  84 */     return this.settings.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  89 */     if (this == o) {
/*  90 */       return true;
/*     */     }
/*  92 */     if (o == null || getClass() != o.getClass()) {
/*  93 */       return false;
/*     */     }
/*  95 */     DefaultHttp3SettingsFrame that = (DefaultHttp3SettingsFrame)o;
/*  96 */     return that.settings.equals(this.settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return StringUtil.simpleClassName(this) + "(settings=" + this.settings + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultHttp3SettingsFrame copyOf(Http3SettingsFrame settingsFrame) {
/* 111 */     DefaultHttp3SettingsFrame copy = new DefaultHttp3SettingsFrame();
/* 112 */     if (settingsFrame instanceof DefaultHttp3SettingsFrame) {
/* 113 */       copy.settings.putAll(((DefaultHttp3SettingsFrame)settingsFrame).settings);
/*     */     } else {
/* 115 */       for (Map.Entry<Long, Long> entry : (Iterable<Map.Entry<Long, Long>>)settingsFrame) {
/* 116 */         copy.put(((Long)entry.getKey()).longValue(), entry.getValue());
/*     */       }
/*     */     } 
/* 119 */     return copy;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3SettingsFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */