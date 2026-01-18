/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
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
/*     */ public class NonBlocking
/*     */ {
/*     */   public static NonBlockingPumpReader nonBlockingPumpReader() {
/*  50 */     return new NonBlockingPumpReader();
/*     */   }
/*     */   
/*     */   public static NonBlockingPumpReader nonBlockingPumpReader(int size) {
/*  54 */     return new NonBlockingPumpReader(size);
/*     */   }
/*     */   
/*     */   public static NonBlockingPumpInputStream nonBlockingPumpInputStream() {
/*  58 */     return new NonBlockingPumpInputStream();
/*     */   }
/*     */   
/*     */   public static NonBlockingPumpInputStream nonBlockingPumpInputStream(int size) {
/*  62 */     return new NonBlockingPumpInputStream(size);
/*     */   }
/*     */   
/*     */   public static NonBlockingInputStream nonBlockingStream(NonBlockingReader reader, Charset encoding) {
/*  66 */     return new NonBlockingReaderInputStream(reader, encoding);
/*     */   }
/*     */   
/*     */   public static NonBlockingInputStream nonBlocking(String name, InputStream inputStream) {
/*  70 */     if (inputStream instanceof NonBlockingInputStream) {
/*  71 */       return (NonBlockingInputStream)inputStream;
/*     */     }
/*  73 */     return new NonBlockingInputStreamImpl(name, inputStream);
/*     */   }
/*     */   
/*     */   public static NonBlockingReader nonBlocking(String name, Reader reader) {
/*  77 */     if (reader instanceof NonBlockingReader) {
/*  78 */       return (NonBlockingReader)reader;
/*     */     }
/*  80 */     return new NonBlockingReaderImpl(name, reader);
/*     */   }
/*     */   
/*     */   public static NonBlockingReader nonBlocking(String name, InputStream inputStream, Charset encoding) {
/*  84 */     return new NonBlockingInputStreamReader(nonBlocking(name, inputStream), encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NonBlockingReaderInputStream
/*     */     extends NonBlockingInputStream
/*     */   {
/*     */     private final NonBlockingReader reader;
/*     */     
/*     */     private final CharsetEncoder encoder;
/*     */     
/*     */     private final ByteBuffer bytes;
/*     */     
/*     */     private final CharBuffer chars;
/*     */     
/*     */     private NonBlockingReaderInputStream(NonBlockingReader reader, Charset charset) {
/* 100 */       this.reader = reader;
/* 101 */       this
/*     */         
/* 103 */         .encoder = charset.newEncoder().onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);
/* 104 */       this.bytes = ByteBuffer.allocate(4);
/* 105 */       this.chars = CharBuffer.allocate(2);
/*     */       
/* 107 */       this.bytes.limit(0);
/* 108 */       this.chars.limit(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() {
/* 113 */       return (int)(this.reader.available() * this.encoder.averageBytesPerChar()) + this.bytes.remaining();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 118 */       this.reader.close();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(long timeout, boolean isPeek) throws IOException {
/* 123 */       Timeout t = new Timeout(timeout);
/* 124 */       while (!this.bytes.hasRemaining() && !t.elapsed()) {
/* 125 */         int c = this.reader.read(t.timeout());
/* 126 */         if (c == -1) {
/* 127 */           return -1;
/*     */         }
/* 129 */         if (c >= 0) {
/* 130 */           if (!this.chars.hasRemaining()) {
/* 131 */             this.chars.position(0);
/* 132 */             this.chars.limit(0);
/*     */           } 
/* 134 */           int l = this.chars.limit();
/* 135 */           this.chars.array()[this.chars.arrayOffset() + l] = (char)c;
/* 136 */           this.chars.limit(l + 1);
/* 137 */           this.bytes.clear();
/* 138 */           this.encoder.encode(this.chars, this.bytes, false);
/* 139 */           this.bytes.flip();
/*     */         } 
/*     */       } 
/* 142 */       if (this.bytes.hasRemaining()) {
/* 143 */         if (isPeek) {
/* 144 */           return this.bytes.get(this.bytes.position());
/*     */         }
/* 146 */         return this.bytes.get();
/*     */       } 
/*     */       
/* 149 */       return -2;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NonBlockingInputStreamReader
/*     */     extends NonBlockingReader
/*     */   {
/*     */     private final NonBlockingInputStream input;
/*     */     private final CharsetDecoder decoder;
/*     */     private final ByteBuffer bytes;
/*     */     private final CharBuffer chars;
/*     */     
/*     */     public NonBlockingInputStreamReader(NonBlockingInputStream inputStream, Charset encoding) {
/* 162 */       this(inputStream, (
/*     */           
/* 164 */           (encoding != null) ? encoding : Charset.defaultCharset())
/* 165 */           .newDecoder()
/* 166 */           .onMalformedInput(CodingErrorAction.REPLACE)
/* 167 */           .onUnmappableCharacter(CodingErrorAction.REPLACE));
/*     */     }
/*     */     
/*     */     public NonBlockingInputStreamReader(NonBlockingInputStream input, CharsetDecoder decoder) {
/* 171 */       this.input = input;
/* 172 */       this.decoder = decoder;
/* 173 */       this.bytes = ByteBuffer.allocate(2048);
/* 174 */       this.chars = CharBuffer.allocate(1024);
/* 175 */       this.bytes.limit(0);
/* 176 */       this.chars.limit(0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int read(long timeout, boolean isPeek) throws IOException {
/* 181 */       Timeout t = new Timeout(timeout);
/* 182 */       while (!this.chars.hasRemaining() && !t.elapsed()) {
/* 183 */         int b = this.input.read(t.timeout());
/* 184 */         if (b == -1) {
/* 185 */           return -1;
/*     */         }
/* 187 */         if (b >= 0) {
/* 188 */           if (!this.bytes.hasRemaining()) {
/* 189 */             this.bytes.position(0);
/* 190 */             this.bytes.limit(0);
/*     */           } 
/* 192 */           int l = this.bytes.limit();
/* 193 */           this.bytes.array()[this.bytes.arrayOffset() + l] = (byte)b;
/* 194 */           this.bytes.limit(l + 1);
/* 195 */           this.chars.clear();
/* 196 */           this.decoder.decode(this.bytes, this.chars, false);
/* 197 */           this.chars.flip();
/*     */         } 
/*     */       } 
/* 200 */       if (this.chars.hasRemaining()) {
/* 201 */         if (isPeek) {
/* 202 */           return this.chars.get(this.chars.position());
/*     */         }
/* 204 */         return this.chars.get();
/*     */       } 
/*     */       
/* 207 */       return -2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int readBuffered(char[] b, int off, int len, long timeout) throws IOException {
/* 213 */       if (b == null)
/* 214 */         throw new NullPointerException(); 
/* 215 */       if (off < 0 || len < 0 || off + len < b.length)
/* 216 */         throw new IllegalArgumentException(); 
/* 217 */       if (len == 0)
/* 218 */         return 0; 
/* 219 */       if (this.chars.hasRemaining()) {
/* 220 */         int r = Math.min(len, this.chars.remaining());
/* 221 */         this.chars.get(b, off, r);
/* 222 */         return r;
/*     */       } 
/* 224 */       Timeout t = new Timeout(timeout);
/* 225 */       while (!this.chars.hasRemaining() && !t.elapsed()) {
/* 226 */         if (!this.bytes.hasRemaining()) {
/* 227 */           this.bytes.position(0);
/* 228 */           this.bytes.limit(0);
/*     */         } 
/* 230 */         int i = this.input.readBuffered(this.bytes
/* 231 */             .array(), this.bytes.limit(), this.bytes.capacity() - this.bytes.limit(), t.timeout());
/* 232 */         if (i < 0) {
/* 233 */           return i;
/*     */         }
/* 235 */         this.bytes.limit(this.bytes.limit() + i);
/* 236 */         this.chars.clear();
/* 237 */         this.decoder.decode(this.bytes, this.chars, false);
/* 238 */         this.chars.flip();
/*     */       } 
/* 240 */       int nb = Math.min(len, this.chars.remaining());
/* 241 */       this.chars.get(b, off, nb);
/* 242 */       return nb;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void shutdown() {
/* 248 */       this.input.shutdown();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 253 */       this.input.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\NonBlocking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */