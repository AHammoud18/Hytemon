/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ObjectEncoderOutputStream
/*     */   extends OutputStream
/*     */   implements ObjectOutput
/*     */ {
/*     */   private final DataOutputStream out;
/*     */   private final int estimatedLength;
/*     */   
/*     */   public ObjectEncoderOutputStream(OutputStream out) {
/*  59 */     this(out, 512);
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
/*     */   public ObjectEncoderOutputStream(OutputStream out, int estimatedLength) {
/*  78 */     ObjectUtil.checkNotNull(out, "out");
/*  79 */     ObjectUtil.checkPositiveOrZero(estimatedLength, "estimatedLength");
/*     */     
/*  81 */     if (out instanceof DataOutputStream) {
/*  82 */       this.out = (DataOutputStream)out;
/*     */     } else {
/*  84 */       this.out = new DataOutputStream(out);
/*     */     } 
/*  86 */     this.estimatedLength = estimatedLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeObject(Object obj) throws IOException {
/*  91 */     ByteBuf buf = Unpooled.buffer(this.estimatedLength);
/*     */     
/*     */     try {
/*  94 */       ObjectOutputStream oout = new CompactObjectOutputStream((OutputStream)new ByteBufOutputStream(buf));
/*     */       
/*     */       try {
/*  97 */         oout.writeObject(obj);
/*  98 */         oout.flush();
/*     */       } finally {
/* 100 */         oout.close();
/*     */       } 
/*     */       
/* 103 */       int objectSize = buf.readableBytes();
/* 104 */       writeInt(objectSize);
/* 105 */       buf.getBytes(0, this, objectSize);
/*     */     } finally {
/* 107 */       buf.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/* 113 */     this.out.write(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 118 */     this.out.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 123 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public final int size() {
/* 127 */     return this.out.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 132 */     this.out.write(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 137 */     this.out.write(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeBoolean(boolean v) throws IOException {
/* 142 */     this.out.writeBoolean(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeByte(int v) throws IOException {
/* 147 */     this.out.writeByte(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeBytes(String s) throws IOException {
/* 152 */     this.out.writeBytes(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeChar(int v) throws IOException {
/* 157 */     this.out.writeChar(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeChars(String s) throws IOException {
/* 162 */     this.out.writeChars(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeDouble(double v) throws IOException {
/* 167 */     this.out.writeDouble(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeFloat(float v) throws IOException {
/* 172 */     this.out.writeFloat(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeInt(int v) throws IOException {
/* 177 */     this.out.writeInt(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeLong(long v) throws IOException {
/* 182 */     this.out.writeLong(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeShort(int v) throws IOException {
/* 187 */     this.out.writeShort(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeUTF(String str) throws IOException {
/* 192 */     this.out.writeUTF(str);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\ObjectEncoderOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */