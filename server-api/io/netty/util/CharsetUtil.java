/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.StandardCharsets;
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
/*     */ public final class CharsetUtil
/*     */ {
/*  38 */   public static final Charset UTF_16 = StandardCharsets.UTF_16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final Charset UTF_16BE = StandardCharsets.UTF_16BE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Charset UTF_16LE = StandardCharsets.UTF_16LE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final Charset UTF_8 = StandardCharsets.UTF_8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final Charset US_ASCII = StandardCharsets.US_ASCII;
/*     */   
/*  66 */   private static final Charset[] CHARSETS = new Charset[] { UTF_16, UTF_16BE, UTF_16LE, UTF_8, ISO_8859_1, US_ASCII };
/*     */ 
/*     */   
/*     */   public static Charset[] values() {
/*  70 */     return CHARSETS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static CharsetEncoder getEncoder(Charset charset) {
/*  78 */     return encoder(charset);
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
/*     */   public static CharsetEncoder encoder(Charset charset, CodingErrorAction malformedInputAction, CodingErrorAction unmappableCharacterAction) {
/*  91 */     ObjectUtil.checkNotNull(charset, "charset");
/*  92 */     CharsetEncoder e = charset.newEncoder();
/*  93 */     e.onMalformedInput(malformedInputAction).onUnmappableCharacter(unmappableCharacterAction);
/*  94 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetEncoder encoder(Charset charset, CodingErrorAction codingErrorAction) {
/* 105 */     return encoder(charset, codingErrorAction, codingErrorAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetEncoder encoder(Charset charset) {
/* 115 */     ObjectUtil.checkNotNull(charset, "charset");
/*     */     
/* 117 */     Map<Charset, CharsetEncoder> map = InternalThreadLocalMap.get().charsetEncoderCache();
/* 118 */     CharsetEncoder e = map.get(charset);
/* 119 */     if (e != null) {
/* 120 */       e.reset().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 121 */       return e;
/*     */     } 
/*     */     
/* 124 */     e = encoder(charset, CodingErrorAction.REPLACE, CodingErrorAction.REPLACE);
/* 125 */     map.put(charset, e);
/* 126 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static CharsetDecoder getDecoder(Charset charset) {
/* 134 */     return decoder(charset);
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
/*     */   public static CharsetDecoder decoder(Charset charset, CodingErrorAction malformedInputAction, CodingErrorAction unmappableCharacterAction) {
/* 147 */     ObjectUtil.checkNotNull(charset, "charset");
/* 148 */     CharsetDecoder d = charset.newDecoder();
/* 149 */     d.onMalformedInput(malformedInputAction).onUnmappableCharacter(unmappableCharacterAction);
/* 150 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetDecoder decoder(Charset charset, CodingErrorAction codingErrorAction) {
/* 161 */     return decoder(charset, codingErrorAction, codingErrorAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetDecoder decoder(Charset charset) {
/* 171 */     ObjectUtil.checkNotNull(charset, "charset");
/*     */     
/* 173 */     Map<Charset, CharsetDecoder> map = InternalThreadLocalMap.get().charsetDecoderCache();
/* 174 */     CharsetDecoder d = map.get(charset);
/* 175 */     if (d != null) {
/* 176 */       d.reset().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 177 */       return d;
/*     */     } 
/*     */     
/* 180 */     d = decoder(charset, CodingErrorAction.REPLACE, CodingErrorAction.REPLACE);
/* 181 */     map.put(charset, d);
/* 182 */     return d;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\CharsetUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */