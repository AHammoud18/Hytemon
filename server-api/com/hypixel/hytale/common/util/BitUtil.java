/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BitUtil
/*    */ {
/*    */   public static void setNibble(@Nonnull byte[] data, int idx, byte b) {
/*  8 */     int fieldIdx = idx >> 1;
/*  9 */     byte val = data[fieldIdx];
/* 10 */     b = (byte)(b & 0xF);
/* 11 */     int i = idx & 0x1;
/* 12 */     b = (byte)(b << (i ^ 0x1) << 2);
/* 13 */     val = (byte)(val & 15 << i << 2);
/* 14 */     val = (byte)(val | b);
/* 15 */     data[fieldIdx] = val;
/*    */   }
/*    */   
/*    */   public static byte getNibble(@Nonnull byte[] data, int idx) {
/* 19 */     int fieldIdx = idx >> 1;
/* 20 */     byte val = data[fieldIdx];
/* 21 */     int i = idx & 0x1;
/* 22 */     val = (byte)(val >> (i ^ 0x1) << 2);
/* 23 */     val = (byte)(val & 0xF);
/* 24 */     return val;
/*    */   }
/*    */   
/*    */   public static byte getAndSetNibble(@Nonnull byte[] data, int idx, byte b) {
/* 28 */     int fieldIdx = idx >> 1;
/* 29 */     byte val = data[fieldIdx];
/* 30 */     int i = idx & 0x1;
/*    */     
/* 32 */     byte oldVal = val;
/* 33 */     oldVal = (byte)(oldVal >> (i ^ 0x1) << 2);
/* 34 */     oldVal = (byte)(oldVal & 0xF);
/*    */     
/* 36 */     b = (byte)(b & 0xF);
/* 37 */     b = (byte)(b << (i ^ 0x1) << 2);
/*    */     
/* 39 */     val = (byte)(val & 15 << i << 2);
/* 40 */     val = (byte)(val | b);
/* 41 */     data[fieldIdx] = val;
/*    */     
/* 43 */     return oldVal;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\BitUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */