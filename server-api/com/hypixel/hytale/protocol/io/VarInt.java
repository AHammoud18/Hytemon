/*    */ package com.hypixel.hytale.protocol.io;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public final class VarInt
/*    */ {
/*    */   public static void write(@Nonnull ByteBuf buf, int value) {
/* 18 */     if (value < 0) {
/* 19 */       throw new IllegalArgumentException("VarInt cannot encode negative values: " + value);
/*    */     }
/* 21 */     while ((value & 0xFFFFFF80) != 0) {
/* 22 */       buf.writeByte(value & 0x7F | 0x80);
/* 23 */       value >>>= 7;
/*    */     } 
/* 25 */     buf.writeByte(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int read(@Nonnull ByteBuf buf) {
/* 33 */     int value = 0, shift = 0;
/*    */     while (true) {
/* 35 */       byte b = buf.readByte();
/* 36 */       value |= (b & Byte.MAX_VALUE) << shift;
/* 37 */       if ((b & 0x80) == 0) return value; 
/* 38 */       shift += 7;
/* 39 */       if (shift > 28) throw new ProtocolException("VarInt exceeds maximum length (5 bytes)");
/*    */     
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int peek(@Nonnull ByteBuf buf, int index) {
/* 48 */     int value = 0, shift = 0;
/* 49 */     int pos = index;
/* 50 */     while (pos < buf.writerIndex()) {
/* 51 */       byte b = buf.getByte(pos++);
/* 52 */       value |= (b & Byte.MAX_VALUE) << shift;
/* 53 */       if ((b & 0x80) == 0) return value; 
/* 54 */       shift += 7;
/* 55 */       if (shift > 28) return -1; 
/*    */     } 
/* 57 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int length(@Nonnull ByteBuf buf, int index) {
/* 65 */     int pos = index;
/* 66 */     while (pos < buf.writerIndex()) {
/* 67 */       if ((buf.getByte(pos++) & 0x80) == 0) return pos - index; 
/* 68 */       if (pos - index > 5) return -1; 
/*    */     } 
/* 70 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int size(int value) {
/* 77 */     if ((value & 0xFFFFFF80) == 0) return 1; 
/* 78 */     if ((value & 0xFFFFC000) == 0) return 2; 
/* 79 */     if ((value & 0xFFE00000) == 0) return 3; 
/* 80 */     if ((value & 0xF0000000) == 0) return 4; 
/* 81 */     return 5;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\VarInt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */