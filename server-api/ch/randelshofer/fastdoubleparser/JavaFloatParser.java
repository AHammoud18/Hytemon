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
/*     */ public final class JavaFloatParser
/*     */ {
/*  33 */   private static final JavaFloatBitsFromByteArray BYTE_ARRAY_PARSER = new JavaFloatBitsFromByteArray();
/*     */   
/*  35 */   private static final JavaFloatBitsFromCharArray CHAR_ARRAY_PARSER = new JavaFloatBitsFromCharArray();
/*     */   
/*  37 */   private static final JavaFloatBitsFromCharSequence CHAR_SEQUENCE_PARSER = new JavaFloatBitsFromCharSequence();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseFloat(CharSequence str) throws NumberFormatException {
/*  56 */     return parseFloat(str, 0, str.length());
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
/*     */   public static float parseFloat(CharSequence str, int offset, int length) throws NumberFormatException {
/*  72 */     long bitPattern = CHAR_SEQUENCE_PARSER.parseFloatingPointLiteral(str, offset, length);
/*  73 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/*  74 */     return Float.intBitsToFloat((int)bitPattern);
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
/*     */   public static float parseFloat(byte[] str) throws NumberFormatException {
/*  88 */     return parseFloat(str, 0, str.length);
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
/*     */   public static float parseFloat(byte[] str, int offset, int length) throws NumberFormatException {
/* 105 */     long bitPattern = BYTE_ARRAY_PARSER.parseFloatingPointLiteral(str, offset, length);
/* 106 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 107 */     return Float.intBitsToFloat((int)bitPattern);
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
/*     */   public static float parseFloat(char[] str) throws NumberFormatException {
/* 120 */     return parseFloat(str, 0, str.length);
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
/*     */   public static float parseFloat(char[] str, int offset, int length) throws NumberFormatException {
/* 137 */     long bitPattern = CHAR_ARRAY_PARSER.parseFloatingPointLiteral(str, offset, length);
/* 138 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 139 */     return Float.intBitsToFloat((int)bitPattern);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaFloatParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */