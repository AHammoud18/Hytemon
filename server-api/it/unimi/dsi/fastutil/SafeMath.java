/*    */ package it.unimi.dsi.fastutil;
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
/*    */ public final class SafeMath
/*    */ {
/*    */   public static char safeIntToChar(int value) {
/* 32 */     if (value < 0 || 65535 < value) throw new IllegalArgumentException(value + " can't be represented as char"); 
/* 33 */     return (char)value;
/*    */   }
/*    */   
/*    */   public static byte safeIntToByte(int value) {
/* 37 */     if (value < -128 || 127 < value) throw new IllegalArgumentException(value + " can't be represented as byte (out of range)"); 
/* 38 */     return (byte)value;
/*    */   }
/*    */   
/*    */   public static short safeIntToShort(int value) {
/* 42 */     if (value < -32768 || 32767 < value) throw new IllegalArgumentException(value + " can't be represented as short (out of range)"); 
/* 43 */     return (short)value;
/*    */   }
/*    */   
/*    */   public static char safeLongToChar(long value) {
/* 47 */     if (value < 0L || 65535L < value) throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
/* 48 */     return (char)(int)value;
/*    */   }
/*    */   
/*    */   public static byte safeLongToByte(long value) {
/* 52 */     if (value < -128L || 127L < value) throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
/* 53 */     return (byte)(int)value;
/*    */   }
/*    */   
/*    */   public static short safeLongToShort(long value) {
/* 57 */     if (value < -32768L || 32767L < value) throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
/* 58 */     return (short)(int)value;
/*    */   }
/*    */   
/*    */   public static int safeLongToInt(long value) {
/* 62 */     if (value < -2147483648L || 2147483647L < value) throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
/* 63 */     return (int)value;
/*    */   }
/*    */   
/*    */   public static float safeDoubleToFloat(double value) {
/* 67 */     if (Double.isNaN(value)) return Float.NaN; 
/* 68 */     if (Double.isInfinite(value)) return (value < 0.0D) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY; 
/* 69 */     if (value < -3.4028234663852886E38D || 3.4028234663852886E38D < value) throw new IllegalArgumentException(value + " can't be represented as float (out of range)"); 
/* 70 */     float floatValue = (float)value;
/* 71 */     if (floatValue != value) throw new IllegalArgumentException(value + " can't be represented as float (imprecise)"); 
/* 72 */     return floatValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\SafeMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */