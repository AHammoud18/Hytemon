/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigInteger;
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
/*     */ public final class JavaBigIntegerParser
/*     */ {
/*  75 */   private static final JavaBigIntegerFromByteArray BYTE_ARRAY_PARSER = new JavaBigIntegerFromByteArray();
/*     */   
/*  77 */   private static final JavaBigIntegerFromCharArray CHAR_ARRAY_PARSER = new JavaBigIntegerFromCharArray();
/*     */   
/*  79 */   private static final JavaBigIntegerFromCharSequence CHAR_SEQUENCE_PARSER = new JavaBigIntegerFromCharSequence();
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
/*     */   public static BigInteger parseBigInteger(CharSequence str) {
/*  96 */     return CHAR_SEQUENCE_PARSER.parseBigIntegerString(str, 0, str.length(), 10);
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
/*     */   public static BigInteger parseBigInteger(CharSequence str, int radix) {
/* 109 */     return CHAR_SEQUENCE_PARSER.parseBigIntegerString(str, 0, str.length(), radix);
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
/*     */   public static BigInteger parseBigInteger(CharSequence str, int offset, int length) {
/* 124 */     return CHAR_SEQUENCE_PARSER.parseBigIntegerString(str, offset, length, 10);
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
/*     */   public static BigInteger parseBigInteger(CharSequence str, int offset, int length, int radix) {
/* 141 */     return CHAR_SEQUENCE_PARSER.parseBigIntegerString(str, offset, length, radix);
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
/*     */   public static BigInteger parseBigInteger(byte[] str) {
/* 153 */     return BYTE_ARRAY_PARSER.parseBigIntegerString(str, 0, str.length, 10);
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
/*     */   public static BigInteger parseBigInteger(byte[] str, int radix) {
/* 166 */     return BYTE_ARRAY_PARSER.parseBigIntegerString(str, 0, str.length, radix);
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
/*     */   public static BigInteger parseBigInteger(byte[] str, int offset, int length) {
/* 185 */     return BYTE_ARRAY_PARSER.parseBigIntegerString(str, offset, length, 10);
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
/*     */   public static BigInteger parseBigInteger(byte[] str, int offset, int length, int radix) {
/* 205 */     return BYTE_ARRAY_PARSER.parseBigIntegerString(str, offset, length, radix);
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
/*     */   public static BigInteger parseBigInteger(char[] str) {
/* 217 */     return CHAR_ARRAY_PARSER.parseBigIntegerString(str, 0, str.length, 10);
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
/*     */   public static BigInteger parseBigInteger(char[] str, int radix) {
/* 230 */     return CHAR_ARRAY_PARSER.parseBigIntegerString(str, 0, str.length, radix);
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
/*     */   public static BigInteger parseBigInteger(char[] str, int offset, int length) {
/* 249 */     return CHAR_ARRAY_PARSER.parseBigIntegerString(str, offset, length, 10);
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
/*     */   public static BigInteger parseBigInteger(char[] str, int offset, int length, int radix) {
/* 269 */     return CHAR_ARRAY_PARSER.parseBigIntegerString(str, offset, length, radix);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigIntegerParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */