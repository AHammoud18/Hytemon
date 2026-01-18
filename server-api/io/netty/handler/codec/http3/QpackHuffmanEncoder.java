/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QpackHuffmanEncoder
/*     */ {
/*     */   private final int[] codes;
/*     */   private final byte[] lengths;
/*  27 */   private final EncodedLengthProcessor encodedLengthProcessor = new EncodedLengthProcessor();
/*  28 */   private final EncodeProcessor encodeProcessor = new EncodeProcessor();
/*     */   
/*     */   QpackHuffmanEncoder() {
/*  31 */     this(QpackUtil.HUFFMAN_CODES, QpackUtil.HUFFMAN_CODE_LENGTHS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QpackHuffmanEncoder(int[] codes, byte[] lengths) {
/*  41 */     this.codes = codes;
/*  42 */     this.lengths = lengths;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(ByteBuf out, CharSequence data) {
/*  52 */     ObjectUtil.checkNotNull(out, "out");
/*  53 */     if (data instanceof AsciiString) {
/*  54 */       AsciiString string = (AsciiString)data;
/*     */       try {
/*  56 */         this.encodeProcessor.out = out;
/*  57 */         string.forEachByte(this.encodeProcessor);
/*  58 */       } catch (Exception e) {
/*  59 */         throw new IllegalStateException(e);
/*     */       } finally {
/*  61 */         this.encodeProcessor.end();
/*     */       } 
/*     */     } else {
/*  64 */       encodeSlowPath(out, data);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeSlowPath(ByteBuf out, CharSequence data) {
/*  69 */     long current = 0L;
/*  70 */     int n = 0;
/*     */     
/*  72 */     for (int i = 0; i < data.length(); i++) {
/*  73 */       int b = data.charAt(i) & 0xFF;
/*  74 */       int code = this.codes[b];
/*  75 */       int nbits = this.lengths[b];
/*     */       
/*  77 */       current <<= nbits;
/*  78 */       current |= code;
/*  79 */       n += nbits;
/*     */       
/*  81 */       while (n >= 8) {
/*  82 */         n -= 8;
/*  83 */         out.writeByte((int)(current >> n));
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if (n > 0) {
/*  88 */       current <<= 8 - n;
/*  89 */       current |= (255 >>> n);
/*  90 */       out.writeByte((int)current);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getEncodedLength(CharSequence data) {
/* 101 */     if (data instanceof AsciiString) {
/* 102 */       AsciiString string = (AsciiString)data;
/*     */       try {
/* 104 */         this.encodedLengthProcessor.reset();
/* 105 */         string.forEachByte(this.encodedLengthProcessor);
/* 106 */         return this.encodedLengthProcessor.length();
/* 107 */       } catch (Exception e) {
/* 108 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } 
/* 111 */     return getEncodedLengthSlowPath(data);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEncodedLengthSlowPath(CharSequence data) {
/* 116 */     long len = 0L;
/* 117 */     for (int i = 0; i < data.length(); i++) {
/* 118 */       len += this.lengths[data.charAt(i) & 0xFF];
/*     */     }
/* 120 */     return (int)(len + 7L >> 3L);
/*     */   }
/*     */   
/*     */   private final class EncodeProcessor
/*     */     implements ByteProcessor
/*     */   {
/*     */     ByteBuf out;
/*     */     private long current;
/*     */     
/*     */     public boolean process(byte value) {
/* 130 */       int b = value & 0xFF;
/* 131 */       int nbits = QpackHuffmanEncoder.this.lengths[b];
/*     */       
/* 133 */       this.current <<= nbits;
/* 134 */       this.current |= QpackHuffmanEncoder.this.codes[b];
/* 135 */       this.n += nbits;
/*     */       
/* 137 */       while (this.n >= 8) {
/* 138 */         this.n -= 8;
/* 139 */         this.out.writeByte((int)(this.current >> this.n));
/*     */       } 
/* 141 */       return true;
/*     */     } private int n;
/*     */     private EncodeProcessor() {}
/*     */     void end() {
/*     */       try {
/* 146 */         if (this.n > 0) {
/* 147 */           this.current <<= 8 - this.n;
/* 148 */           this.current |= (255 >>> this.n);
/* 149 */           this.out.writeByte((int)this.current);
/*     */         } 
/*     */       } finally {
/* 152 */         this.out = null;
/* 153 */         this.current = 0L;
/* 154 */         this.n = 0;
/*     */       } 
/*     */     } }
/*     */   
/*     */   private final class EncodedLengthProcessor implements ByteProcessor {
/*     */     private long len;
/*     */     
/*     */     private EncodedLengthProcessor() {}
/*     */     
/*     */     public boolean process(byte value) {
/* 164 */       this.len += QpackHuffmanEncoder.this.lengths[value & 0xFF];
/* 165 */       return true;
/*     */     }
/*     */     
/*     */     void reset() {
/* 169 */       this.len = 0L;
/*     */     }
/*     */     
/*     */     int length() {
/* 173 */       return (int)(this.len + 7L >> 3L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackHuffmanEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */