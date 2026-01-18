/*    */ package io.netty.handler.pcap;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PcapHeaders
/*    */ {
/* 37 */   private static final byte[] GLOBAL_HEADER = new byte[] { -95, -78, -61, -44, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 1 };
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
/*    */   static void writeGlobalHeader(OutputStream outputStream) throws IOException {
/* 51 */     outputStream.write(GLOBAL_HEADER);
/*    */   }
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
/*    */   static void writePacketHeader(ByteBuf byteBuf, int ts_sec, int ts_usec, int incl_len, int orig_len) {
/* 64 */     byteBuf.writeInt(ts_sec);
/* 65 */     byteBuf.writeInt(ts_usec);
/* 66 */     byteBuf.writeInt(incl_len);
/* 67 */     byteBuf.writeInt(orig_len);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\PcapHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */