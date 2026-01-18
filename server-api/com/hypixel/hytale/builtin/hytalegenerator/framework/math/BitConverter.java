/*     */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BitConverter
/*     */ {
/*     */   public static void main(String[] args) {
/*  12 */     System.out.println("LONG TEST:"); int i;
/*  13 */     for (i = -4; i < 10; i++) {
/*  14 */       System.out.println();
/*  15 */       System.out.print("INPUT [" + i + "] -> BINARY -> [");
/*  16 */       boolean[] output = toBitArray(i);
/*  17 */       for (boolean bit : output)
/*  18 */         System.out.print(bit ? "1" : "0"); 
/*  19 */       System.out.print("] -> DECIMAL -> [" + toLong(output) + "]");
/*     */     } 
/*  21 */     System.out.println();
/*  22 */     System.out.println("INT TEST:");
/*  23 */     for (i = -4; i < 10; i++) {
/*  24 */       System.out.println();
/*  25 */       System.out.print("INPUT [" + i + "] -> BINARY -> [");
/*  26 */       boolean[] output = toBitArray(i);
/*  27 */       for (boolean bit : output)
/*  28 */         System.out.print(bit ? "1" : "0"); 
/*  29 */       System.out.print("] -> DECIMAL -> [" + toInt(output) + "]");
/*     */     } 
/*  31 */     System.out.println();
/*  32 */     System.out.println("BYTE TEST:");
/*  33 */     for (i = -4; i < 10; i++) {
/*  34 */       System.out.println();
/*  35 */       System.out.print("INPUT [" + i + "] -> BINARY -> [");
/*  36 */       boolean[] output = toBitArray((byte)i);
/*  37 */       for (boolean bit : output)
/*  38 */         System.out.print(bit ? "1" : "0"); 
/*  39 */       System.out.print("] -> DECIMAL -> [" + toByte(output) + "]");
/*     */     } 
/*  41 */     System.out.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] toBitArray(long number) {
/*  49 */     byte PRECISION = 64;
/*  50 */     boolean[] bits = new boolean[64];
/*  51 */     long position = 1L; byte i;
/*  52 */     for (i = 63; i >= 0; i = (byte)(i - 1)) {
/*  53 */       bits[i] = ((number & position) != 0L);
/*  54 */       position <<= 1L;
/*     */     } 
/*  56 */     return bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] toBitArray(int number) {
/*  64 */     byte PRECISION = 32;
/*  65 */     boolean[] bits = new boolean[32];
/*  66 */     int position = 1; byte i;
/*  67 */     for (i = 31; i >= 0; i = (byte)(i - 1)) {
/*  68 */       bits[i] = ((number & position) != 0);
/*  69 */       position <<= 1;
/*     */     } 
/*  71 */     return bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] toBitArray(byte number) {
/*  79 */     byte PRECISION = 8;
/*  80 */     boolean[] bits = new boolean[8];
/*  81 */     byte position = 1; byte i;
/*  82 */     for (i = 7; i >= 0; i = (byte)(i - 1)) {
/*  83 */       bits[i] = ((number & position) != 0);
/*  84 */       position = (byte)(position << 1);
/*     */     } 
/*  86 */     return bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long toLong(@Nonnull boolean[] bits) {
/*  94 */     byte PRECISION = 64;
/*  95 */     if (bits.length != 64) {
/*  96 */       throw new IllegalArgumentException("array must have length 64");
/*     */     }
/*  98 */     long position = 1L;
/*  99 */     long number = 0L; byte i;
/* 100 */     for (i = 63; i >= 0; i = (byte)(i - 1)) {
/* 101 */       if (bits[i])
/* 102 */         number += position; 
/* 103 */       position <<= 1L;
/*     */     } 
/* 105 */     return number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toInt(@Nonnull boolean[] bits) {
/* 113 */     byte PRECISION = 32;
/* 114 */     if (bits.length != 32) {
/* 115 */       throw new IllegalArgumentException("array must have length 32");
/*     */     }
/* 117 */     int position = 1;
/* 118 */     int number = 0; byte i;
/* 119 */     for (i = 31; i >= 0; i = (byte)(i - 1)) {
/* 120 */       if (bits[i])
/* 121 */         number += position; 
/* 122 */       position <<= 1;
/*     */     } 
/* 124 */     return number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toByte(@Nonnull boolean[] bits) {
/* 132 */     byte PRECISION = 8;
/* 133 */     if (bits.length != 8) {
/* 134 */       throw new IllegalArgumentException("array must have length 8");
/*     */     }
/* 136 */     byte position = 1;
/* 137 */     byte number = 0; byte i;
/* 138 */     for (i = 7; i >= 0; i = (byte)(i - 1)) {
/* 139 */       if (bits[i])
/* 140 */         number = (byte)(number + position); 
/* 141 */       position = (byte)(position << 1);
/*     */     } 
/* 143 */     return number;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\BitConverter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */