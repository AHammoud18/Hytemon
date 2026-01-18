/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConfigurableDoubleParser
/*     */ {
/*     */   private final NumberFormatSymbols symbols;
/*     */   private ConfigurableDoubleBitsFromCharSequence charSequenceParser;
/*     */   private ConfigurableDoubleBitsFromCharArray charArrayParser;
/*     */   private final boolean ignoreCase;
/*     */   private final boolean isAllSingleCharSymbolsAscii;
/*     */   private final boolean isDigitsAscii;
/*     */   private final boolean isAscii;
/*     */   private ConfigurableDoubleBitsFromByteArrayAscii byteArrayAsciiParser;
/*     */   private ConfigurableDoubleBitsFromByteArrayUtf8 byteArrayUtf8Parser;
/*     */   
/*     */   public ConfigurableDoubleParser(NumberFormatSymbols symbols) {
/* 138 */     this(symbols, false);
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
/*     */   public ConfigurableDoubleParser(DecimalFormatSymbols symbols) {
/* 151 */     this(symbols, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigurableDoubleParser(NumberFormatSymbols symbols, boolean ignoreCase) {
/* 161 */     Objects.requireNonNull(symbols, "symbols");
/* 162 */     this.symbols = symbols;
/* 163 */     this.ignoreCase = ignoreCase;
/* 164 */     this.isAllSingleCharSymbolsAscii = NumberFormatSymbolsInfo.isMostlyAscii(symbols);
/* 165 */     this.isDigitsAscii = NumberFormatSymbolsInfo.isDigitsTokensAscii(symbols);
/* 166 */     this.isAscii = NumberFormatSymbolsInfo.isAscii(symbols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormatSymbols getNumberFormatSymbols() {
/* 175 */     return this.symbols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnoreCase() {
/* 184 */     return this.ignoreCase;
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
/*     */   public ConfigurableDoubleParser(DecimalFormatSymbols symbols, boolean ignoreCase) {
/* 200 */     this(NumberFormatSymbols.fromDecimalFormatSymbols(symbols), ignoreCase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigurableDoubleParser() {
/* 208 */     this(NumberFormatSymbols.fromDefault(), false);
/*     */   }
/*     */   
/*     */   private ConfigurableDoubleBitsFromCharArray getCharArrayParser() {
/* 212 */     if (this.charArrayParser == null) {
/* 213 */       this.charArrayParser = new ConfigurableDoubleBitsFromCharArray(this.symbols, this.ignoreCase);
/*     */     }
/*     */     
/* 216 */     return this.charArrayParser;
/*     */   }
/*     */   
/*     */   private ConfigurableDoubleBitsFromByteArrayAscii getByteArrayAsciiParser() {
/* 220 */     if (this.byteArrayAsciiParser == null) {
/* 221 */       this.byteArrayAsciiParser = new ConfigurableDoubleBitsFromByteArrayAscii(this.symbols, this.ignoreCase);
/*     */     }
/*     */     
/* 224 */     return this.byteArrayAsciiParser;
/*     */   }
/*     */   
/*     */   private ConfigurableDoubleBitsFromByteArrayUtf8 getByteArrayUtf8Parser() {
/* 228 */     if (this.byteArrayUtf8Parser == null) {
/* 229 */       this.byteArrayUtf8Parser = new ConfigurableDoubleBitsFromByteArrayUtf8(this.symbols, this.ignoreCase);
/*     */     }
/*     */     
/* 232 */     return this.byteArrayUtf8Parser;
/*     */   }
/*     */   
/*     */   private ConfigurableDoubleBitsFromCharSequence getCharSequenceParser() {
/* 236 */     if (this.charSequenceParser == null) {
/* 237 */       this.charSequenceParser = new ConfigurableDoubleBitsFromCharSequence(this.symbols, this.ignoreCase);
/*     */     }
/* 239 */     return this.charSequenceParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double parseDouble(CharSequence str) {
/* 250 */     return parseDouble(str, 0, str.length());
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
/*     */   public double parseDouble(CharSequence str, int offset, int length) {
/* 262 */     long bitPattern = getCharSequenceParser().parseFloatingPointLiteral(str, offset, length);
/* 263 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 264 */     return Double.longBitsToDouble(bitPattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double parseDouble(char[] str) {
/* 275 */     return parseDouble(str, 0, str.length);
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
/*     */   public double parseDouble(char[] str, int offset, int length) {
/* 287 */     long bitPattern = getCharArrayParser().parseFloatingPointLiteral(str, offset, length);
/* 288 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 289 */     return Double.longBitsToDouble(bitPattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double parseDouble(byte[] str) {
/* 300 */     return parseDouble(str, 0, str.length);
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
/*     */   public double parseDouble(byte[] str, int offset, int length) {
/*     */     long bitPattern;
/* 313 */     if (this.isAscii || (!this.ignoreCase && this.isAllSingleCharSymbolsAscii)) {
/* 314 */       bitPattern = getByteArrayAsciiParser().parseFloatingPointLiteral(str, offset, length);
/* 315 */     } else if (this.isDigitsAscii) {
/* 316 */       bitPattern = getByteArrayUtf8Parser().parseFloatingPointLiteral(str, offset, length);
/*     */     } else {
/* 318 */       Utf8Decoder.Result result = Utf8Decoder.decode(str, offset, length);
/* 319 */       bitPattern = getCharArrayParser().parseFloatingPointLiteral(result.chars(), 0, result.length());
/*     */     } 
/* 321 */     if (bitPattern == 9221120237041090561L) throw new NumberFormatException("illegal syntax"); 
/* 322 */     return Double.longBitsToDouble(bitPattern);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\ConfigurableDoubleParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */