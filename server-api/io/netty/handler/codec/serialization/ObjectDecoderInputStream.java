/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.StreamCorruptedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class ObjectDecoderInputStream
/*     */   extends InputStream
/*     */   implements ObjectInput
/*     */ {
/*     */   private final DataInputStream in;
/*     */   private final int maxObjectSize;
/*     */   private final ClassResolver classResolver;
/*     */   
/*     */   public ObjectDecoderInputStream(InputStream in) {
/*  57 */     this(in, (ClassLoader)null);
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
/*     */   public ObjectDecoderInputStream(InputStream in, ClassLoader classLoader) {
/*  71 */     this(in, classLoader, 1048576);
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
/*     */   public ObjectDecoderInputStream(InputStream in, int maxObjectSize) {
/*  86 */     this(in, null, maxObjectSize);
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
/*     */   public ObjectDecoderInputStream(InputStream in, ClassLoader classLoader, int maxObjectSize) {
/* 104 */     ObjectUtil.checkNotNull(in, "in");
/* 105 */     ObjectUtil.checkPositive(maxObjectSize, "maxObjectSize");
/*     */     
/* 107 */     if (in instanceof DataInputStream) {
/* 108 */       this.in = (DataInputStream)in;
/*     */     } else {
/* 110 */       this.in = new DataInputStream(in);
/*     */     } 
/* 112 */     this.classResolver = ClassResolvers.weakCachingResolver(classLoader);
/* 113 */     this.maxObjectSize = maxObjectSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readObject() throws ClassNotFoundException, IOException {
/* 118 */     int dataLen = readInt();
/* 119 */     if (dataLen <= 0) {
/* 120 */       throw new StreamCorruptedException("invalid data length: " + dataLen);
/*     */     }
/* 122 */     if (dataLen > this.maxObjectSize) {
/* 123 */       throw new StreamCorruptedException("data length too big: " + dataLen + " (max: " + this.maxObjectSize + ')');
/*     */     }
/*     */ 
/*     */     
/* 127 */     return (new CompactObjectInputStream(this.in, this.classResolver)).readObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 132 */     return this.in.available();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 137 */     this.in.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int readlimit) {
/* 143 */     this.in.mark(readlimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 148 */     return this.in.markSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 154 */     return this.in.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int read(byte[] b, int off, int len) throws IOException {
/* 159 */     return this.in.read(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int read(byte[] b) throws IOException {
/* 164 */     return this.in.read(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean readBoolean() throws IOException {
/* 169 */     return this.in.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte readByte() throws IOException {
/* 174 */     return this.in.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public final char readChar() throws IOException {
/* 179 */     return this.in.readChar();
/*     */   }
/*     */ 
/*     */   
/*     */   public final double readDouble() throws IOException {
/* 184 */     return this.in.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public final float readFloat() throws IOException {
/* 189 */     return this.in.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void readFully(byte[] b, int off, int len) throws IOException {
/* 194 */     this.in.readFully(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void readFully(byte[] b) throws IOException {
/* 199 */     this.in.readFully(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int readInt() throws IOException {
/* 204 */     return this.in.readInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final String readLine() throws IOException {
/* 213 */     return this.in.readLine();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long readLong() throws IOException {
/* 218 */     return this.in.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public final short readShort() throws IOException {
/* 223 */     return this.in.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int readUnsignedByte() throws IOException {
/* 228 */     return this.in.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int readUnsignedShort() throws IOException {
/* 233 */     return this.in.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String readUTF() throws IOException {
/* 238 */     return this.in.readUTF();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 243 */     this.in.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 248 */     return this.in.skip(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int skipBytes(int n) throws IOException {
/* 253 */     return this.in.skipBytes(n);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\ObjectDecoderInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */