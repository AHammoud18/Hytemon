/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WriterOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final Writer out;
/*     */   private final CharsetDecoder decoder;
/*  55 */   private final ByteBuffer decoderIn = ByteBuffer.allocate(256);
/*  56 */   private final CharBuffer decoderOut = CharBuffer.allocate(128);
/*     */   
/*     */   public WriterOutputStream(Writer out, Charset charset) {
/*  59 */     this(out, charset
/*     */         
/*  61 */         .newDecoder()
/*  62 */         .onMalformedInput(CodingErrorAction.REPLACE)
/*  63 */         .onUnmappableCharacter(CodingErrorAction.REPLACE));
/*     */   }
/*     */   
/*     */   public WriterOutputStream(Writer out, CharsetDecoder decoder) {
/*  67 */     this.out = out;
/*  68 */     this.decoder = decoder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  73 */     write(new byte[] { (byte)b }, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  78 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  83 */     while (len > 0) {
/*  84 */       int c = Math.min(len, this.decoderIn.remaining());
/*  85 */       this.decoderIn.put(b, off, c);
/*  86 */       processInput(false);
/*  87 */       len -= c;
/*  88 */       off += c;
/*     */     } 
/*  90 */     flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  95 */     flushOutput();
/*  96 */     this.out.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 101 */     processInput(true);
/* 102 */     flush();
/* 103 */     this.out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processInput(boolean endOfInput) throws IOException {
/*     */     CoderResult coderResult;
/* 114 */     this.decoderIn.flip();
/*     */     
/*     */     while (true) {
/* 117 */       coderResult = this.decoder.decode(this.decoderIn, this.decoderOut, endOfInput);
/* 118 */       if (coderResult.isOverflow())
/* 119 */       { flushOutput(); continue; }  break;
/* 120 */     }  if (coderResult.isUnderflow()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       this.decoderIn.compact();
/*     */       return;
/*     */     } 
/*     */     throw new IOException("Unexpected coder result");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutput() throws IOException {
/* 138 */     if (this.decoderOut.position() > 0) {
/* 139 */       this.out.write(this.decoderOut.array(), 0, this.decoderOut.position());
/* 140 */       this.decoderOut.rewind();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\WriterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */