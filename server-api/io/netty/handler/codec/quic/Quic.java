/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class Quic
/*     */ {
/*  32 */   static final Map.Entry<ChannelOption<?>, Object>[] EMPTY_OPTION_ARRAY = (Map.Entry<ChannelOption<?>, Object>[])new Map.Entry[0];
/*     */   
/*  34 */   static final Map.Entry<AttributeKey<?>, Object>[] EMPTY_ATTRIBUTE_ARRAY = (Map.Entry<AttributeKey<?>, Object>[])new Map.Entry[0];
/*     */   
/*     */   static final int MAX_DATAGRAM_SIZE = 1350;
/*     */   
/*     */   static final int RESET_TOKEN_LEN = 16;
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */   public static final int MAX_CONN_ID_LEN = 20;
/*     */   
/*     */   static {
/*  43 */     Throwable cause = null;
/*     */     
/*     */     try {
/*  46 */       String version = Quiche.quiche_version();
/*  47 */       assert version != null;
/*  48 */     } catch (Throwable error) {
/*  49 */       cause = error;
/*     */     } 
/*     */     
/*  52 */     UNAVAILABILITY_CAUSE = cause;
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
/*     */   public static boolean isVersionSupported(int version) {
/*  68 */     return (isAvailable() && Quiche.quiche_version_is_supported(version));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailable() {
/*  77 */     return (UNAVAILABILITY_CAUSE == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureAvailability() {
/*  86 */     if (UNAVAILABILITY_CAUSE != null) {
/*  87 */       throw (Error)(new UnsatisfiedLinkError("failed to load the required native library"))
/*  88 */         .initCause(UNAVAILABILITY_CAUSE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Throwable unavailabilityCause() {
/*  99 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */   
/*     */   static Map.Entry<ChannelOption<?>, Object>[] toOptionsArray(Map<ChannelOption<?>, Object> opts) {
/* 103 */     return (Map.Entry<ChannelOption<?>, Object>[])(new HashMap<>(opts)).entrySet().toArray((Object[])EMPTY_OPTION_ARRAY);
/*     */   }
/*     */   
/*     */   static Map.Entry<AttributeKey<?>, Object>[] toAttributesArray(Map<AttributeKey<?>, Object> attributes) {
/* 107 */     return (Map.Entry<AttributeKey<?>, Object>[])(new LinkedHashMap<>(attributes)).entrySet().toArray((Object[])EMPTY_ATTRIBUTE_ARRAY);
/*     */   }
/*     */   
/*     */   private static void setAttributes(Channel channel, Map.Entry<AttributeKey<?>, Object>[] attrs) {
/* 111 */     for (Map.Entry<AttributeKey<?>, Object> e : attrs) {
/*     */       
/* 113 */       AttributeKey<Object> key = (AttributeKey<Object>)e.getKey();
/* 114 */       channel.attr(key).set(e.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setChannelOptions(Channel channel, Map.Entry<ChannelOption<?>, Object>[] options, InternalLogger logger) {
/* 120 */     for (Map.Entry<ChannelOption<?>, Object> e : options) {
/* 121 */       setChannelOption(channel, e.getKey(), e.getValue(), logger);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setChannelOption(Channel channel, ChannelOption<?> option, Object value, InternalLogger logger) {
/*     */     try {
/* 129 */       if (!channel.config().setOption(option, value)) {
/* 130 */         logger.warn("Unknown channel option '{}' for channel '{}'", option, channel);
/*     */       }
/* 132 */     } catch (Throwable t) {
/* 133 */       logger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", new Object[] { option, value, channel, t });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> void updateOptions(Map<ChannelOption<?>, Object> options, ChannelOption<T> option, @Nullable T value) {
/* 143 */     ObjectUtil.checkNotNull(option, "option");
/* 144 */     if (value == null) {
/* 145 */       options.remove(option);
/*     */     } else {
/* 147 */       options.put(option, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> void updateAttributes(Map<AttributeKey<?>, Object> attributes, AttributeKey<T> key, @Nullable T value) {
/* 156 */     ObjectUtil.checkNotNull(key, "key");
/* 157 */     if (value == null) {
/* 158 */       attributes.remove(key);
/*     */     } else {
/* 160 */       attributes.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void setupChannel(Channel ch, Map.Entry<ChannelOption<?>, Object>[] options, Map.Entry<AttributeKey<?>, Object>[] attrs, @Nullable ChannelHandler handler, InternalLogger logger) {
/* 167 */     setChannelOptions(ch, options, logger);
/* 168 */     setAttributes(ch, attrs);
/* 169 */     if (handler != null)
/* 170 */       ch.pipeline().addLast(new ChannelHandler[] { handler }); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\Quic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */