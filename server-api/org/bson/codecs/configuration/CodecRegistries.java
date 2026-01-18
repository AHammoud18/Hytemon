/*     */ package org.bson.codecs.configuration;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.internal.ProvidersCodecRegistry;
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
/*     */ public final class CodecRegistries
/*     */ {
/*     */   public static CodecRegistry fromCodecs(Codec<?>... codecs) {
/*  43 */     return fromCodecs(Arrays.asList(codecs));
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
/*     */   public static CodecRegistry fromCodecs(List<? extends Codec<?>> codecs) {
/*  56 */     return fromProviders(new CodecProvider[] { new MapOfCodecsProvider(codecs) });
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
/*     */   public static CodecRegistry fromProviders(CodecProvider... providers) {
/*  72 */     return fromProviders(Arrays.asList(providers));
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
/*     */   public static CodecRegistry fromProviders(List<? extends CodecProvider> providers) {
/*  88 */     return (CodecRegistry)new ProvidersCodecRegistry(providers);
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
/*     */   public static CodecRegistry fromRegistries(CodecRegistry... registries) {
/* 107 */     return fromRegistries(Arrays.asList(registries));
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
/*     */   public static CodecRegistry fromRegistries(List<? extends CodecRegistry> registries) {
/* 126 */     return (CodecRegistry)new ProvidersCodecRegistry(registries);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\configuration\CodecRegistries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */