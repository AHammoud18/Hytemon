/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*  57 */   private final Map<Class<?>, Codec<?>> codecs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueCodecProvider() {
/*  63 */     addCodecs();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  69 */     return (Codec<T>)this.codecs.get(clazz);
/*     */   }
/*     */   
/*     */   private void addCodecs() {
/*  73 */     addCodec(new BinaryCodec());
/*  74 */     addCodec(new BooleanCodec());
/*  75 */     addCodec(new DateCodec());
/*  76 */     addCodec(new DoubleCodec());
/*  77 */     addCodec(new IntegerCodec());
/*  78 */     addCodec(new LongCodec());
/*  79 */     addCodec(new MinKeyCodec());
/*  80 */     addCodec(new MaxKeyCodec());
/*  81 */     addCodec(new CodeCodec());
/*  82 */     addCodec(new Decimal128Codec());
/*  83 */     addCodec(new BigDecimalCodec());
/*  84 */     addCodec(new ObjectIdCodec());
/*  85 */     addCodec(new CharacterCodec());
/*  86 */     addCodec(new StringCodec());
/*  87 */     addCodec(new SymbolCodec());
/*  88 */     addCodec(new OverridableUuidRepresentationUuidCodec());
/*     */     
/*  90 */     addCodec(new ByteCodec());
/*  91 */     addCodec(new PatternCodec());
/*  92 */     addCodec(new ShortCodec());
/*  93 */     addCodec(new ByteArrayCodec());
/*  94 */     addCodec(new FloatCodec());
/*  95 */     addCodec(new AtomicBooleanCodec());
/*  96 */     addCodec(new AtomicIntegerCodec());
/*  97 */     addCodec(new AtomicLongCodec());
/*     */   }
/*     */   
/*     */   private <T> void addCodec(Codec<T> codec) {
/* 101 */     this.codecs.put(codec.getEncoderClass(), codec);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 106 */     if (this == o) {
/* 107 */       return true;
/*     */     }
/* 109 */     if (o == null || getClass() != o.getClass()) {
/* 110 */       return false;
/*     */     }
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 118 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ValueCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */