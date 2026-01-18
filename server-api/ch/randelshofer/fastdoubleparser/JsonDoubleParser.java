/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonDoubleParser
/*     */ {
/*  47 */   private static final JsonDoubleBitsFromByteArray BYTE_ARRAY_PARSER = new JsonDoubleBitsFromByteArray();
/*     */   
/*  49 */   private static final JsonDoubleBitsFromCharArray CHAR_ARRAY_PARSER = new JsonDoubleBitsFromCharArray();
/*     */   
/*  51 */   private static final JsonDoubleBitsFromCharSequence CHAR_SEQUENCE_PARSER = new JsonDoubleBitsFromCharSequence();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(CharSequence str) throws NumberFormatException {
/*  70 */     return parseDouble(str, 0, str.length());
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
/*     */   public static double parseDouble(CharSequence str, int offset, int length) throws NumberFormatException {
/*  86 */     long bitPattern = CHAR_SEQUENCE_PARSER.parseNumber(str, offset, length);
/*  87 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/*  88 */     return Double.longBitsToDouble(bitPattern);
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
/*     */   public static double parseDouble(byte[] str) throws NumberFormatException {
/* 103 */     return parseDouble(str, 0, str.length);
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
/*     */   public static double parseDouble(byte[] str, int offset, int length) throws NumberFormatException {
/* 120 */     long bitPattern = BYTE_ARRAY_PARSER.parseNumber(str, offset, length);
/* 121 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 122 */     return Double.longBitsToDouble(bitPattern);
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
/*     */   public static double parseDouble(char[] str) throws NumberFormatException {
/* 134 */     return parseDouble(str, 0, str.length);
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
/*     */   public static double parseDouble(char[] str, int offset, int length) throws NumberFormatException {
/* 152 */     long bitPattern = CHAR_ARRAY_PARSER.parseNumber(str, offset, length);
/* 153 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 154 */     return Double.longBitsToDouble(bitPattern);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JsonDoubleParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */