/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.parser.ParseException;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FormatOptions
/*     */ {
/*     */   private static final int MAX_ALLOWED_WIDTH = 999999;
/*     */   private static final int MAX_ALLOWED_PRECISION = 999999;
/*     */   private static final String FLAG_CHARS_ORDERED = " #(+,-0";
/*     */   private static final int MIN_FLAG_VALUE = 32;
/*     */   private static final int MAX_FLAG_VALUE = 48;
/*     */   private static final long ENCODED_FLAG_INDICES;
/*     */   public static final int FLAG_PREFIX_SPACE_FOR_POSITIVE_VALUES = 1;
/*     */   public static final int FLAG_SHOW_ALT_FORM = 2;
/*     */   public static final int FLAG_USE_PARENS_FOR_NEGATIVE_VALUES = 4;
/*     */   public static final int FLAG_PREFIX_PLUS_FOR_POSITIVE_VALUES = 8;
/*     */   public static final int FLAG_SHOW_GROUPING = 16;
/*     */   public static final int FLAG_LEFT_ALIGN = 32;
/*     */   public static final int FLAG_SHOW_LEADING_ZEROS = 64;
/*     */   public static final int FLAG_UPPER_CASE = 128;
/*     */   public static final int ALL_FLAGS = 255;
/*     */   public static final int UNSET = -1;
/*     */   
/*     */   static {
/*  42 */     long encoded = 0L;
/*  43 */     for (int i = 0; i < " #(+,-0".length(); i++) {
/*  44 */       long n = (" #(+,-0".charAt(i) - 32);
/*  45 */       encoded |= i + 1L << (int)(3L * n);
/*     */     } 
/*  47 */     ENCODED_FLAG_INDICES = encoded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfFlagCharacter(char c) {
/*  55 */     return (int)(ENCODED_FLAG_INDICES >>> 3 * (c - 32) & 0x7L) - 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final FormatOptions DEFAULT = new FormatOptions(0, -1, -1);
/*     */   private final int flags;
/*     */   
/*     */   public static FormatOptions getDefault() {
/* 133 */     return DEFAULT;
/*     */   }
/*     */   private final int width; private final int precision;
/*     */   
/*     */   public static FormatOptions of(int flags, int width, int precision) {
/* 138 */     if (!checkFlagConsistency(flags, (width != -1))) {
/* 139 */       throw new IllegalArgumentException("invalid flags: 0x" + Integer.toHexString(flags));
/*     */     }
/* 141 */     if ((width < 1 || width > 999999) && width != -1) {
/* 142 */       throw new IllegalArgumentException("invalid width: " + width);
/*     */     }
/* 144 */     if ((precision < 0 || precision > 999999) && precision != -1) {
/* 145 */       throw new IllegalArgumentException("invalid precision: " + precision);
/*     */     }
/* 147 */     return new FormatOptions(flags, width, precision);
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
/*     */   public static FormatOptions parse(String message, int pos, int end, boolean isUpperCase) throws ParseException {
/*     */     char c;
/* 166 */     if (pos == end && !isUpperCase) {
/* 167 */       return DEFAULT;
/*     */     }
/*     */ 
/*     */     
/* 171 */     int flags = isUpperCase ? 128 : 0;
/*     */     
/*     */     while (true) {
/* 174 */       if (pos == end) {
/* 175 */         return new FormatOptions(flags, -1, -1);
/*     */       }
/* 177 */       c = message.charAt(pos++);
/* 178 */       if (c < ' ' || c > '0') {
/*     */         break;
/*     */       }
/* 181 */       int flagIdx = indexOfFlagCharacter(c);
/* 182 */       if (flagIdx < 0) {
/* 183 */         if (c == '.')
/*     */         {
/* 185 */           return new FormatOptions(flags, -1, parsePrecision(message, pos, end));
/*     */         }
/* 187 */         throw ParseException.atPosition("invalid flag", message, pos - 1);
/*     */       } 
/* 189 */       int flagBit = 1 << flagIdx;
/* 190 */       if ((flags & flagBit) != 0) {
/* 191 */         throw ParseException.atPosition("repeated flag", message, pos - 1);
/*     */       }
/* 193 */       flags |= flagBit;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 198 */     int widthStart = pos - 1;
/* 199 */     if (c > '9') {
/* 200 */       throw ParseException.atPosition("invalid flag", message, widthStart);
/*     */     }
/* 202 */     int width = c - 48;
/*     */     while (true) {
/* 204 */       if (pos == end) {
/* 205 */         return new FormatOptions(flags, width, -1);
/*     */       }
/* 207 */       c = message.charAt(pos++);
/* 208 */       if (c == '.') {
/* 209 */         return new FormatOptions(flags, width, parsePrecision(message, pos, end));
/*     */       }
/* 211 */       int n = (char)(c - 48);
/* 212 */       if (n >= 10) {
/* 213 */         throw ParseException.atPosition("invalid width character", message, pos - 1);
/*     */       }
/* 215 */       width = width * 10 + n;
/* 216 */       if (width > 999999) {
/* 217 */         throw ParseException.withBounds("width too large", message, widthStart, end);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int parsePrecision(String message, int start, int end) throws ParseException {
/* 223 */     if (start == end) {
/* 224 */       throw ParseException.atPosition("missing precision", message, start - 1);
/*     */     }
/* 226 */     int precision = 0;
/* 227 */     for (int pos = start; pos < end; pos++) {
/* 228 */       int n = (char)(message.charAt(pos) - 48);
/* 229 */       if (n >= 10) {
/* 230 */         throw ParseException.atPosition("invalid precision character", message, pos);
/*     */       }
/* 232 */       precision = precision * 10 + n;
/* 233 */       if (precision > 999999) {
/* 234 */         throw ParseException.withBounds("precision too large", message, start, end);
/*     */       }
/*     */     } 
/*     */     
/* 238 */     if (precision == 0 && end != start + 1) {
/* 239 */       throw ParseException.withBounds("invalid precision", message, start, end);
/*     */     }
/* 241 */     return precision;
/*     */   }
/*     */ 
/*     */   
/*     */   static int parseValidFlags(String flagChars, boolean hasUpperVariant) {
/* 246 */     int flags = hasUpperVariant ? 128 : 0;
/* 247 */     for (int i = 0; i < flagChars.length(); i++) {
/* 248 */       int flagIdx = indexOfFlagCharacter(flagChars.charAt(i));
/* 249 */       if (flagIdx < 0) {
/* 250 */         throw new IllegalArgumentException("invalid flags: " + flagChars);
/*     */       }
/* 252 */       flags |= 1 << flagIdx;
/*     */     } 
/* 254 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FormatOptions(int flags, int width, int precision) {
/* 263 */     this.flags = flags;
/* 264 */     this.width = width;
/* 265 */     this.precision = precision;
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
/*     */   public FormatOptions filter(int allowedFlags, boolean allowWidth, boolean allowPrecision) {
/* 279 */     if (isDefault()) {
/* 280 */       return this;
/*     */     }
/* 282 */     int newFlags = allowedFlags & this.flags;
/* 283 */     int newWidth = allowWidth ? this.width : -1;
/* 284 */     int newPrecision = allowPrecision ? this.precision : -1;
/*     */     
/* 286 */     if (newFlags == 0 && newWidth == -1 && newPrecision == -1) {
/* 287 */       return DEFAULT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     if (newFlags == this.flags && newWidth == this.width && newPrecision == this.precision) {
/* 294 */       return this;
/*     */     }
/* 296 */     return new FormatOptions(newFlags, newWidth, newPrecision);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 302 */     return (this == getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 311 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecision() {
/* 320 */     return this.precision;
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
/*     */   public boolean validate(int allowedFlags, boolean allowPrecision) {
/* 338 */     if (isDefault()) {
/* 339 */       return true;
/*     */     }
/*     */     
/* 342 */     if ((this.flags & (allowedFlags ^ 0xFFFFFFFF)) != 0) {
/* 343 */       return false;
/*     */     }
/*     */     
/* 346 */     if (!allowPrecision && this.precision != -1) {
/* 347 */       return false;
/*     */     }
/* 349 */     return checkFlagConsistency(this.flags, (getWidth() != -1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean checkFlagConsistency(int flags, boolean hasWidth) {
/* 355 */     if ((flags & 0x9) == 9)
/*     */     {
/* 357 */       return false;
/*     */     }
/*     */     
/* 360 */     if ((flags & 0x60) == 96)
/*     */     {
/* 362 */       return false;
/*     */     }
/*     */     
/* 365 */     if ((flags & 0x60) != 0 && !hasWidth) {
/* 366 */       return false;
/*     */     }
/* 368 */     return true;
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
/*     */   public boolean areValidFor(FormatChar formatChar) {
/* 384 */     return validate(formatChar.getAllowedFlags(), formatChar.getType().supportsPrecision());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlags() {
/* 393 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldLeftAlign() {
/* 403 */     return ((this.flags & 0x20) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldShowAltForm() {
/* 413 */     return ((this.flags & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldShowLeadingZeros() {
/* 423 */     return ((this.flags & 0x40) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldPrefixPlusForPositiveValues() {
/* 433 */     return ((this.flags & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldPrefixSpaceForPositiveValues() {
/* 443 */     return ((this.flags & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldShowGrouping() {
/* 453 */     return ((this.flags & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUpperCase() {
/* 462 */     return ((this.flags & 0x80) != 0);
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
/*     */   public StringBuilder appendPrintfOptions(StringBuilder out) {
/* 474 */     if (!isDefault()) {
/*     */       
/* 476 */       int optionFlags = this.flags & 0xFFFFFF7F;
/* 477 */       for (int bit = 0; 1 << bit <= optionFlags; bit++) {
/* 478 */         if ((optionFlags & 1 << bit) != 0) {
/* 479 */           out.append(" #(+,-0".charAt(bit));
/*     */         }
/*     */       } 
/* 482 */       if (this.width != -1) {
/* 483 */         out.append(this.width);
/*     */       }
/* 485 */       if (this.precision != -1) {
/* 486 */         out.append('.').append(this.precision);
/*     */       }
/*     */     } 
/* 489 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@NullableDecl Object o) {
/* 496 */     if (o == this) {
/* 497 */       return true;
/*     */     }
/* 499 */     if (o instanceof FormatOptions) {
/* 500 */       FormatOptions other = (FormatOptions)o;
/* 501 */       return (other.flags == this.flags && other.width == this.width && other.precision == this.precision);
/*     */     } 
/* 503 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 508 */     int result = this.flags;
/* 509 */     result = 31 * result + this.width;
/* 510 */     result = 31 * result + this.precision;
/* 511 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\FormatOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */