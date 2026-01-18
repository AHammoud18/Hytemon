/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.io.IOException;
/*    */ import org.jboss.marshalling.ByteInput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LimitingByteInput
/*    */   implements ByteInput
/*    */ {
/* 31 */   private static final TooBigObjectException EXCEPTION = new TooBigObjectException();
/*    */   
/*    */   private final ByteInput input;
/*    */   private final long limit;
/*    */   private long read;
/*    */   
/*    */   LimitingByteInput(ByteInput input, long limit) {
/* 38 */     this.input = input;
/* 39 */     this.limit = ObjectUtil.checkPositive(limit, "limit");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int available() throws IOException {
/* 49 */     return readable(this.input.available());
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 54 */     int readable = readable(1);
/* 55 */     if (readable > 0) {
/* 56 */       int b = this.input.read();
/* 57 */       this.read++;
/* 58 */       return b;
/*    */     } 
/* 60 */     throw EXCEPTION;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int read(byte[] array) throws IOException {
/* 66 */     return read(array, 0, array.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] array, int offset, int length) throws IOException {
/* 71 */     int readable = readable(length);
/* 72 */     if (readable > 0) {
/* 73 */       int i = this.input.read(array, offset, readable);
/* 74 */       this.read += i;
/* 75 */       return i;
/*    */     } 
/* 77 */     throw EXCEPTION;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long skip(long bytes) throws IOException {
/* 83 */     int readable = readable((int)bytes);
/* 84 */     if (readable > 0) {
/* 85 */       long i = this.input.skip(readable);
/* 86 */       this.read += i;
/* 87 */       return i;
/*    */     } 
/* 89 */     throw EXCEPTION;
/*    */   }
/*    */ 
/*    */   
/*    */   private int readable(int length) {
/* 94 */     return (int)Math.min(length, this.limit - this.read);
/*    */   }
/*    */   
/*    */   static final class TooBigObjectException extends IOException {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\marshalling\LimitingByteInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */