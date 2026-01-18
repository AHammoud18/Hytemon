/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigDecimal;
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
/*     */ public final class JavaBigDecimalParser
/*     */ {
/*  98 */   private static final JavaBigDecimalFromByteArray BYTE_ARRAY_PARSER = new JavaBigDecimalFromByteArray();
/*     */   
/* 100 */   private static final JavaBigDecimalFromCharArray CHAR_ARRAY_PARSER = new JavaBigDecimalFromCharArray();
/*     */   
/* 102 */   private static final JavaBigDecimalFromCharSequence CHAR_SEQUENCE_PARSER = new JavaBigDecimalFromCharSequence();
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
/*     */   public static BigDecimal parseBigDecimal(CharSequence str) throws NumberFormatException {
/* 120 */     return parseBigDecimal(str, 0, str.length());
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
/*     */   public static BigDecimal parseBigDecimal(CharSequence str, int offset, int length) throws NumberFormatException {
/* 136 */     return CHAR_SEQUENCE_PARSER.parseBigDecimalString(str, offset, length);
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
/*     */   public static BigDecimal parseBigDecimal(byte[] str) throws NumberFormatException {
/* 149 */     return parseBigDecimal(str, 0, str.length);
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
/*     */   public static BigDecimal parseBigDecimal(byte[] str, int offset, int length) throws NumberFormatException {
/* 166 */     return BYTE_ARRAY_PARSER.parseBigDecimalString(str, offset, length);
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
/*     */   public static BigDecimal parseBigDecimal(char[] str) throws NumberFormatException {
/* 178 */     return parseBigDecimal(str, 0, str.length);
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
/*     */   public static BigDecimal parseBigDecimal(char[] str, int offset, int length) throws NumberFormatException {
/* 197 */     return CHAR_ARRAY_PARSER.parseBigDecimalString(str, offset, length);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigDecimalParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */