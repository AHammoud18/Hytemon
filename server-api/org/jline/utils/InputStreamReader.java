/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.MalformedInputException;
/*     */ import java.nio.charset.UnmappableCharacterException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputStreamReader
/*     */   extends Reader
/*     */ {
/*     */   private InputStream in;
/*     */   private static final int BUFFER_SIZE = 4;
/*     */   private boolean endOfInput = false;
/*     */   CharsetDecoder decoder;
/*  78 */   ByteBuffer bytes = ByteBuffer.allocate(4);
/*     */   
/*  80 */   char pending = Character.MAX_VALUE;
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
/*     */   public InputStreamReader(InputStream in) {
/*  92 */     super(in);
/*  93 */     this.in = in;
/*  94 */     this
/*     */ 
/*     */       
/*  97 */       .decoder = Charset.defaultCharset().newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  98 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, String enc) throws UnsupportedEncodingException {
/* 117 */     super(in);
/* 118 */     if (enc == null) {
/* 119 */       throw new NullPointerException();
/*     */     }
/* 121 */     this.in = in;
/*     */     try {
/* 123 */       this
/*     */ 
/*     */         
/* 126 */         .decoder = Charset.forName(enc).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 127 */     } catch (IllegalArgumentException e) {
/* 128 */       throw (UnsupportedEncodingException)(new UnsupportedEncodingException(enc)).initCause(e);
/*     */     } 
/* 130 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, CharsetDecoder dec) {
/* 143 */     super(in);
/* 144 */     dec.averageCharsPerByte();
/* 145 */     this.in = in;
/* 146 */     this.decoder = dec;
/* 147 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, Charset charset) {
/* 160 */     super(in);
/* 161 */     this.in = in;
/* 162 */     this
/*     */       
/* 164 */       .decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 165 */     this.bytes.limit(0);
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
/*     */   public void close() throws IOException {
/* 177 */     synchronized (this.lock) {
/* 178 */       this.decoder = null;
/* 179 */       if (this.in != null) {
/* 180 */         this.in.close();
/* 181 */         this.in = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 194 */     if (!isOpen()) {
/* 195 */       return null;
/*     */     }
/* 197 */     return this.decoder.charset().name();
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
/*     */   public int read() throws IOException {
/* 214 */     synchronized (this.lock) {
/* 215 */       if (!isOpen()) {
/* 216 */         throw new ClosedException("InputStreamReader is closed.");
/*     */       }
/*     */       
/* 219 */       if (this.pending != Character.MAX_VALUE) {
/* 220 */         char c = this.pending;
/* 221 */         this.pending = Character.MAX_VALUE;
/* 222 */         return c;
/*     */       } 
/* 224 */       char[] buf = new char[2];
/* 225 */       int nb = read(buf, 0, 2);
/* 226 */       if (nb == 2) {
/* 227 */         this.pending = buf[1];
/*     */       }
/* 229 */       if (nb > 0) {
/* 230 */         return buf[0];
/*     */       }
/* 232 */       return -1;
/*     */     } 
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
/*     */   public int read(char[] buf, int offset, int length) throws IOException {
/* 263 */     synchronized (this.lock) {
/* 264 */       if (!isOpen()) {
/* 265 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/* 267 */       if (offset < 0 || offset > buf.length - length || length < 0) {
/* 268 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 270 */       if (length == 0) {
/* 271 */         return 0;
/*     */       }
/*     */       
/* 274 */       CharBuffer out = CharBuffer.wrap(buf, offset, length);
/* 275 */       CoderResult result = CoderResult.UNDERFLOW;
/*     */ 
/*     */ 
/*     */       
/* 279 */       boolean needInput = !this.bytes.hasRemaining();
/*     */       
/* 281 */       while (out.position() == offset) {
/*     */         
/* 283 */         if (needInput) {
/*     */           try {
/* 285 */             if (this.in.available() == 0 && out.position() > offset) {
/*     */               break;
/*     */             }
/*     */           }
/* 289 */           catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */           
/* 293 */           int off = this.bytes.arrayOffset() + this.bytes.limit();
/* 294 */           int was_red = this.in.read(this.bytes.array(), off, 1);
/*     */           
/* 296 */           if (was_red == -1) {
/* 297 */             this.endOfInput = true; break;
/*     */           } 
/* 299 */           if (was_red == 0) {
/*     */             break;
/*     */           }
/* 302 */           this.bytes.limit(this.bytes.limit() + was_red);
/*     */         } 
/*     */ 
/*     */         
/* 306 */         result = this.decoder.decode(this.bytes, out, false);
/*     */         
/* 308 */         if (result.isUnderflow()) {
/*     */           
/* 310 */           if (this.bytes.limit() == this.bytes.capacity()) {
/* 311 */             this.bytes.compact();
/* 312 */             this.bytes.limit(this.bytes.position());
/* 313 */             this.bytes.position(0);
/*     */           } 
/* 315 */           needInput = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 321 */       if (result == CoderResult.UNDERFLOW && this.endOfInput) {
/* 322 */         result = this.decoder.decode(this.bytes, out, true);
/* 323 */         this.decoder.flush(out);
/* 324 */         this.decoder.reset();
/*     */       } 
/* 326 */       if (result.isMalformed())
/* 327 */         throw new MalformedInputException(result.length()); 
/* 328 */       if (result.isUnmappable()) {
/* 329 */         throw new UnmappableCharacterException(result.length());
/*     */       }
/*     */       
/* 332 */       return (out.position() - offset == 0) ? -1 : (out.position() - offset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpen() {
/* 341 */     return (this.in != null);
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
/*     */   public boolean ready() throws IOException {
/* 359 */     synchronized (this.lock) {
/* 360 */       if (this.in == null) {
/* 361 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/*     */       try {
/* 364 */         return (this.bytes.hasRemaining() || this.in.available() > 0);
/* 365 */       } catch (IOException e) {
/* 366 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\InputStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */