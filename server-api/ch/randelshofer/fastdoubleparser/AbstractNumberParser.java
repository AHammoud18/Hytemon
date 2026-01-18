/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractNumberParser
/*     */ {
/*     */   public static final String ILLEGAL_OFFSET_OR_ILLEGAL_LENGTH = "offset < 0 or length > str.length";
/*     */   public static final String SYNTAX_ERROR = "illegal syntax";
/*     */   public static final long SYNTAX_ERROR_BITS = 9221120237041090561L;
/*     */   public static final String VALUE_EXCEEDS_LIMITS = "value exceeds limits";
/*     */   static final byte DECIMAL_POINT_CLASS = -4;
/*     */   static final byte OTHER_CLASS = -1;
/*  46 */   static final byte[] CHAR_TO_HEX_MAP = new byte[256];
/*     */   
/*     */   static {
/*  49 */     Arrays.fill(CHAR_TO_HEX_MAP, (byte)-1); char ch;
/*  50 */     for (ch = '0'; ch <= '9'; ch = (char)(ch + 1)) {
/*  51 */       CHAR_TO_HEX_MAP[ch] = (byte)(ch - 48);
/*     */     }
/*  53 */     for (ch = 'A'; ch <= 'F'; ch = (char)(ch + 1)) {
/*  54 */       CHAR_TO_HEX_MAP[ch] = (byte)(ch - 65 + 10);
/*     */     }
/*  56 */     for (ch = 'a'; ch <= 'f'; ch = (char)(ch + 1)) {
/*  57 */       CHAR_TO_HEX_MAP[ch] = (byte)(ch - 97 + 10);
/*     */     }
/*  59 */     CHAR_TO_HEX_MAP[46] = -4;
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
/*     */   protected static byte charAt(byte[] str, int i, int endIndex) {
/*  72 */     return (i < endIndex) ? str[i] : 0;
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
/*     */   protected static char charAt(char[] str, int i, int endIndex) {
/*  85 */     return (i < endIndex) ? str[i] : Character.MIN_VALUE;
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
/*     */   protected static char charAt(CharSequence str, int i, int endIndex) {
/*  98 */     return (i < endIndex) ? str.charAt(i) : Character.MIN_VALUE;
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
/*     */   protected static int lookupHex(byte ch) {
/* 111 */     return CHAR_TO_HEX_MAP[ch & 0xFF];
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
/*     */   protected static int lookupHex(char ch) {
/* 130 */     return (ch < '') ? CHAR_TO_HEX_MAP[ch] : -1;
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
/*     */   protected static int checkBounds(int size, int offset, int length, int maxInputLength) {
/* 144 */     if (length > maxInputLength) {
/* 145 */       throw new NumberFormatException("value exceeds limits");
/*     */     }
/* 147 */     return checkBounds(size, offset, length);
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
/*     */   protected static int checkBounds(int size, int offset, int length) {
/* 160 */     if ((offset | length | size - length - offset) < 0) {
/* 161 */       throw new IllegalArgumentException("offset < 0 or length > str.length");
/*     */     }
/* 163 */     return length + offset;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractNumberParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */