/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.BitSet;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DateFormatter
/*     */ {
/*  50 */   private static final BitSet DELIMITERS = new BitSet();
/*     */   static {
/*  52 */     DELIMITERS.set(9); char c;
/*  53 */     for (c = ' '; c <= '/'; c = (char)(c + 1)) {
/*  54 */       DELIMITERS.set(c);
/*     */     }
/*  56 */     for (c = ';'; c <= '@'; c = (char)(c + 1)) {
/*  57 */       DELIMITERS.set(c);
/*     */     }
/*  59 */     for (c = '['; c <= '`'; c = (char)(c + 1)) {
/*  60 */       DELIMITERS.set(c);
/*     */     }
/*  62 */     for (c = '{'; c <= '~'; c = (char)(c + 1)) {
/*  63 */       DELIMITERS.set(c);
/*     */     }
/*     */   }
/*     */   
/*  67 */   private static final String[] DAY_OF_WEEK_TO_SHORT_NAME = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
/*     */ 
/*     */   
/*  70 */   private static final String[] CALENDAR_MONTH_TO_SHORT_NAME = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
/*     */ 
/*     */   
/*  73 */   private static final FastThreadLocal<DateFormatter> INSTANCES = new FastThreadLocal<DateFormatter>()
/*     */     {
/*     */       protected DateFormatter initialValue()
/*     */       {
/*  77 */         return new DateFormatter();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date parseHttpDate(CharSequence txt) {
/*  87 */     return parseHttpDate(txt, 0, txt.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date parseHttpDate(CharSequence txt, int start, int end) {
/*  98 */     int length = end - start;
/*  99 */     if (length == 0)
/* 100 */       return null; 
/* 101 */     if (length < 0)
/* 102 */       throw new IllegalArgumentException("Can't have end < start"); 
/* 103 */     if (length > 64) {
/* 104 */       throw new IllegalArgumentException("Can't parse more than 64 chars, looks like a user error or a malformed header");
/*     */     }
/*     */     
/* 107 */     return formatter().parse0((CharSequence)ObjectUtil.checkNotNull(txt, "txt"), start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String format(Date date) {
/* 116 */     return formatter().format0((Date)ObjectUtil.checkNotNull(date, "date"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringBuilder append(Date date, StringBuilder sb) {
/* 126 */     return formatter().append0((Date)ObjectUtil.checkNotNull(date, "date"), (StringBuilder)ObjectUtil.checkNotNull(sb, "sb"));
/*     */   }
/*     */   
/*     */   private static DateFormatter formatter() {
/* 130 */     DateFormatter formatter = (DateFormatter)INSTANCES.get();
/* 131 */     formatter.reset();
/* 132 */     return formatter;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDelim(char c) {
/* 137 */     return DELIMITERS.get(c);
/*     */   }
/*     */   
/*     */   private static boolean isDigit(char c) {
/* 141 */     return (c >= '0' && c <= '9');
/*     */   }
/*     */   
/*     */   private static int getNumericalValue(char c) {
/* 145 */     return c - 48;
/*     */   }
/*     */   
/* 148 */   private final GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
/* 149 */   private final StringBuilder sb = new StringBuilder(29);
/*     */   private boolean timeFound;
/*     */   private int hours;
/*     */   private int minutes;
/*     */   private int seconds;
/*     */   private boolean dayOfMonthFound;
/*     */   private int dayOfMonth;
/*     */   private boolean monthFound;
/*     */   private int month;
/*     */   private boolean yearFound;
/*     */   private int year;
/*     */   
/*     */   private DateFormatter() {
/* 162 */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/* 166 */     this.timeFound = false;
/* 167 */     this.hours = -1;
/* 168 */     this.minutes = -1;
/* 169 */     this.seconds = -1;
/* 170 */     this.dayOfMonthFound = false;
/* 171 */     this.dayOfMonth = -1;
/* 172 */     this.monthFound = false;
/* 173 */     this.month = -1;
/* 174 */     this.yearFound = false;
/* 175 */     this.year = -1;
/* 176 */     this.cal.clear();
/* 177 */     this.sb.setLength(0);
/*     */   }
/*     */   
/*     */   private boolean tryParseTime(CharSequence txt, int tokenStart, int tokenEnd) {
/* 181 */     int len = tokenEnd - tokenStart;
/*     */ 
/*     */     
/* 184 */     if (len < 5 || len > 8) {
/* 185 */       return false;
/*     */     }
/*     */     
/* 188 */     int localHours = -1;
/* 189 */     int localMinutes = -1;
/* 190 */     int localSeconds = -1;
/* 191 */     int currentPartNumber = 0;
/* 192 */     int currentPartValue = 0;
/* 193 */     int numDigits = 0;
/*     */     
/* 195 */     for (int i = tokenStart; i < tokenEnd; i++) {
/* 196 */       char c = txt.charAt(i);
/* 197 */       if (isDigit(c)) {
/* 198 */         currentPartValue = currentPartValue * 10 + getNumericalValue(c);
/* 199 */         if (++numDigits > 2) {
/* 200 */           return false;
/*     */         }
/* 202 */       } else if (c == ':') {
/* 203 */         if (numDigits == 0)
/*     */         {
/* 205 */           return false;
/*     */         }
/* 207 */         switch (currentPartNumber) {
/*     */           
/*     */           case 0:
/* 210 */             localHours = currentPartValue;
/*     */             break;
/*     */           
/*     */           case 1:
/* 214 */             localMinutes = currentPartValue;
/*     */             break;
/*     */           
/*     */           default:
/* 218 */             return false;
/*     */         } 
/* 220 */         currentPartValue = 0;
/* 221 */         currentPartNumber++;
/* 222 */         numDigits = 0;
/*     */       } else {
/*     */         
/* 225 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     if (numDigits > 0)
/*     */     {
/* 231 */       localSeconds = currentPartValue;
/*     */     }
/*     */     
/* 234 */     if (localHours >= 0 && localMinutes >= 0 && localSeconds >= 0) {
/* 235 */       this.hours = localHours;
/* 236 */       this.minutes = localMinutes;
/* 237 */       this.seconds = localSeconds;
/* 238 */       return true;
/*     */     } 
/*     */     
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   private boolean tryParseDayOfMonth(CharSequence txt, int tokenStart, int tokenEnd) {
/* 245 */     int len = tokenEnd - tokenStart;
/*     */     
/* 247 */     if (len == 1) {
/* 248 */       char c0 = txt.charAt(tokenStart);
/* 249 */       if (isDigit(c0)) {
/* 250 */         this.dayOfMonth = getNumericalValue(c0);
/* 251 */         return true;
/*     */       }
/*     */     
/* 254 */     } else if (len == 2) {
/* 255 */       char c0 = txt.charAt(tokenStart);
/* 256 */       char c1 = txt.charAt(tokenStart + 1);
/* 257 */       if (isDigit(c0) && isDigit(c1)) {
/* 258 */         this.dayOfMonth = getNumericalValue(c0) * 10 + getNumericalValue(c1);
/* 259 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     return false;
/*     */   }
/*     */   
/*     */   private boolean tryParseMonth(CharSequence txt, int tokenStart, int tokenEnd) {
/* 267 */     int len = tokenEnd - tokenStart;
/*     */     
/* 269 */     if (len != 3) {
/* 270 */       return false;
/*     */     }
/*     */     
/* 273 */     char monthChar1 = AsciiString.toLowerCase(txt.charAt(tokenStart));
/* 274 */     char monthChar2 = AsciiString.toLowerCase(txt.charAt(tokenStart + 1));
/* 275 */     char monthChar3 = AsciiString.toLowerCase(txt.charAt(tokenStart + 2));
/*     */     
/* 277 */     if (monthChar1 == 'j' && monthChar2 == 'a' && monthChar3 == 'n') {
/* 278 */       this.month = 0;
/* 279 */     } else if (monthChar1 == 'f' && monthChar2 == 'e' && monthChar3 == 'b') {
/* 280 */       this.month = 1;
/* 281 */     } else if (monthChar1 == 'm' && monthChar2 == 'a' && monthChar3 == 'r') {
/* 282 */       this.month = 2;
/* 283 */     } else if (monthChar1 == 'a' && monthChar2 == 'p' && monthChar3 == 'r') {
/* 284 */       this.month = 3;
/* 285 */     } else if (monthChar1 == 'm' && monthChar2 == 'a' && monthChar3 == 'y') {
/* 286 */       this.month = 4;
/* 287 */     } else if (monthChar1 == 'j' && monthChar2 == 'u' && monthChar3 == 'n') {
/* 288 */       this.month = 5;
/* 289 */     } else if (monthChar1 == 'j' && monthChar2 == 'u' && monthChar3 == 'l') {
/* 290 */       this.month = 6;
/* 291 */     } else if (monthChar1 == 'a' && monthChar2 == 'u' && monthChar3 == 'g') {
/* 292 */       this.month = 7;
/* 293 */     } else if (monthChar1 == 's' && monthChar2 == 'e' && monthChar3 == 'p') {
/* 294 */       this.month = 8;
/* 295 */     } else if (monthChar1 == 'o' && monthChar2 == 'c' && monthChar3 == 't') {
/* 296 */       this.month = 9;
/* 297 */     } else if (monthChar1 == 'n' && monthChar2 == 'o' && monthChar3 == 'v') {
/* 298 */       this.month = 10;
/* 299 */     } else if (monthChar1 == 'd' && monthChar2 == 'e' && monthChar3 == 'c') {
/* 300 */       this.month = 11;
/*     */     } else {
/* 302 */       return false;
/*     */     } 
/*     */     
/* 305 */     return true;
/*     */   }
/*     */   
/*     */   private boolean tryParseYear(CharSequence txt, int tokenStart, int tokenEnd) {
/* 309 */     int len = tokenEnd - tokenStart;
/*     */     
/* 311 */     if (len == 2) {
/* 312 */       char c0 = txt.charAt(tokenStart);
/* 313 */       char c1 = txt.charAt(tokenStart + 1);
/* 314 */       if (isDigit(c0) && isDigit(c1)) {
/* 315 */         this.year = getNumericalValue(c0) * 10 + getNumericalValue(c1);
/* 316 */         return true;
/*     */       }
/*     */     
/* 319 */     } else if (len == 4) {
/* 320 */       char c0 = txt.charAt(tokenStart);
/* 321 */       char c1 = txt.charAt(tokenStart + 1);
/* 322 */       char c2 = txt.charAt(tokenStart + 2);
/* 323 */       char c3 = txt.charAt(tokenStart + 3);
/* 324 */       if (isDigit(c0) && isDigit(c1) && isDigit(c2) && isDigit(c3)) {
/* 325 */         this
/*     */ 
/*     */           
/* 328 */           .year = getNumericalValue(c0) * 1000 + getNumericalValue(c1) * 100 + getNumericalValue(c2) * 10 + getNumericalValue(c3);
/* 329 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean parseToken(CharSequence txt, int tokenStart, int tokenEnd) {
/* 338 */     if (!this.timeFound) {
/* 339 */       this.timeFound = tryParseTime(txt, tokenStart, tokenEnd);
/* 340 */       if (this.timeFound) {
/* 341 */         return (this.dayOfMonthFound && this.monthFound && this.yearFound);
/*     */       }
/*     */     } 
/*     */     
/* 345 */     if (!this.dayOfMonthFound) {
/* 346 */       this.dayOfMonthFound = tryParseDayOfMonth(txt, tokenStart, tokenEnd);
/* 347 */       if (this.dayOfMonthFound) {
/* 348 */         return (this.timeFound && this.monthFound && this.yearFound);
/*     */       }
/*     */     } 
/*     */     
/* 352 */     if (!this.monthFound) {
/* 353 */       this.monthFound = tryParseMonth(txt, tokenStart, tokenEnd);
/* 354 */       if (this.monthFound) {
/* 355 */         return (this.timeFound && this.dayOfMonthFound && this.yearFound);
/*     */       }
/*     */     } 
/*     */     
/* 359 */     if (!this.yearFound) {
/* 360 */       this.yearFound = tryParseYear(txt, tokenStart, tokenEnd);
/*     */     }
/* 362 */     return (this.timeFound && this.dayOfMonthFound && this.monthFound && this.yearFound);
/*     */   }
/*     */   
/*     */   private Date parse0(CharSequence txt, int start, int end) {
/* 366 */     boolean allPartsFound = parse1(txt, start, end);
/* 367 */     return (allPartsFound && normalizeAndValidate()) ? computeDate() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean parse1(CharSequence txt, int start, int end) {
/* 372 */     int tokenStart = -1;
/*     */     
/* 374 */     for (int i = start; i < end; i++) {
/* 375 */       char c = txt.charAt(i);
/*     */       
/* 377 */       if (isDelim(c)) {
/* 378 */         if (tokenStart != -1) {
/*     */           
/* 380 */           if (parseToken(txt, tokenStart, i)) {
/* 381 */             return true;
/*     */           }
/* 383 */           tokenStart = -1;
/*     */         } 
/* 385 */       } else if (tokenStart == -1) {
/*     */         
/* 387 */         tokenStart = i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 392 */     return (tokenStart != -1 && parseToken(txt, tokenStart, txt.length()));
/*     */   }
/*     */   
/*     */   private boolean normalizeAndValidate() {
/* 396 */     if (this.dayOfMonth < 1 || this.dayOfMonth > 31 || this.hours > 23 || this.minutes > 59 || this.seconds > 59)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 401 */       return false;
/*     */     }
/*     */     
/* 404 */     if (this.year >= 70 && this.year <= 99) {
/* 405 */       this.year += 1900;
/* 406 */     } else if (this.year >= 0 && this.year < 70) {
/* 407 */       this.year += 2000;
/* 408 */     } else if (this.year < 1601) {
/*     */       
/* 410 */       return false;
/*     */     } 
/* 412 */     return true;
/*     */   }
/*     */   
/*     */   private Date computeDate() {
/* 416 */     this.cal.set(5, this.dayOfMonth);
/* 417 */     this.cal.set(2, this.month);
/* 418 */     this.cal.set(1, this.year);
/* 419 */     this.cal.set(11, this.hours);
/* 420 */     this.cal.set(12, this.minutes);
/* 421 */     this.cal.set(13, this.seconds);
/* 422 */     return this.cal.getTime();
/*     */   }
/*     */   
/*     */   private String format0(Date date) {
/* 426 */     append0(date, this.sb);
/* 427 */     return this.sb.toString();
/*     */   }
/*     */   
/*     */   private StringBuilder append0(Date date, StringBuilder sb) {
/* 431 */     this.cal.setTime(date);
/*     */     
/* 433 */     sb.append(DAY_OF_WEEK_TO_SHORT_NAME[this.cal.get(7) - 1]).append(", ");
/* 434 */     appendZeroLeftPadded(this.cal.get(5), sb).append(' ');
/* 435 */     sb.append(CALENDAR_MONTH_TO_SHORT_NAME[this.cal.get(2)]).append(' ');
/* 436 */     sb.append(this.cal.get(1)).append(' ');
/* 437 */     appendZeroLeftPadded(this.cal.get(11), sb).append(':');
/* 438 */     appendZeroLeftPadded(this.cal.get(12), sb).append(':');
/* 439 */     return appendZeroLeftPadded(this.cal.get(13), sb).append(" GMT");
/*     */   }
/*     */   
/*     */   private static StringBuilder appendZeroLeftPadded(int value, StringBuilder sb) {
/* 443 */     if (value < 10) {
/* 444 */       sb.append('0');
/*     */     }
/* 446 */     return sb.append(value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\DateFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */