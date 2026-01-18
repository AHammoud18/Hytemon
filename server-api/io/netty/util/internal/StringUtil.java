/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringUtil
/*     */ {
/*     */   public static final String EMPTY_STRING = "";
/*  32 */   public static final String NEWLINE = SystemPropertyUtil.get("line.separator", "\n");
/*     */   
/*     */   public static final char DOUBLE_QUOTE = '"';
/*     */   
/*     */   public static final char COMMA = ',';
/*     */   public static final char LINE_FEED = '\n';
/*     */   public static final char CARRIAGE_RETURN = '\r';
/*     */   public static final char TAB = '\t';
/*     */   public static final char SPACE = ' ';
/*  41 */   private static final String[] BYTE2HEX_PAD = new String[256];
/*  42 */   private static final String[] BYTE2HEX_NOPAD = new String[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  54 */     for (int i = 0; i < BYTE2HEX_PAD.length; i++) {
/*  55 */       String str = Integer.toHexString(i);
/*  56 */       BYTE2HEX_PAD[i] = (i > 15) ? str : ('0' + str);
/*  57 */       BYTE2HEX_NOPAD[i] = str;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  62 */   private static final byte[] HEX2B = new byte[65536]; static {
/*  63 */     Arrays.fill(HEX2B, (byte)-1);
/*  64 */     HEX2B[48] = 0;
/*  65 */     HEX2B[49] = 1;
/*  66 */     HEX2B[50] = 2;
/*  67 */     HEX2B[51] = 3;
/*  68 */     HEX2B[52] = 4;
/*  69 */     HEX2B[53] = 5;
/*  70 */     HEX2B[54] = 6;
/*  71 */     HEX2B[55] = 7;
/*  72 */     HEX2B[56] = 8;
/*  73 */     HEX2B[57] = 9;
/*  74 */     HEX2B[65] = 10;
/*  75 */     HEX2B[66] = 11;
/*  76 */     HEX2B[67] = 12;
/*  77 */     HEX2B[68] = 13;
/*  78 */     HEX2B[69] = 14;
/*  79 */     HEX2B[70] = 15;
/*  80 */     HEX2B[97] = 10;
/*  81 */     HEX2B[98] = 11;
/*  82 */     HEX2B[99] = 12;
/*  83 */     HEX2B[100] = 13;
/*  84 */     HEX2B[101] = 14;
/*  85 */     HEX2B[102] = 15;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
/*     */ 
/*     */   
/*     */   private static final char PACKAGE_SEPARATOR_CHAR = '.';
/*     */ 
/*     */ 
/*     */   
/*     */   public static String substringAfter(String value, char delim) {
/*  98 */     int pos = value.indexOf(delim);
/*  99 */     if (pos >= 0) {
/* 100 */       return value.substring(pos + 1);
/*     */     }
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String substringBefore(String value, char delim) {
/* 111 */     int pos = value.indexOf(delim);
/* 112 */     if (pos >= 0) {
/* 113 */       return value.substring(0, pos);
/*     */     }
/* 115 */     return null;
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
/*     */   public static boolean commonSuffixOfLength(String s, String p, int len) {
/* 127 */     return (s != null && p != null && len >= 0 && s.regionMatches(s.length() - len, p, p.length() - len, len));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byteToHexStringPadded(int value) {
/* 134 */     return BYTE2HEX_PAD[value & 0xFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T byteToHexStringPadded(T buf, int value) {
/*     */     try {
/* 142 */       buf.append(byteToHexStringPadded(value));
/* 143 */     } catch (IOException e) {
/* 144 */       PlatformDependent.throwException(e);
/*     */     } 
/* 146 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexStringPadded(byte[] src) {
/* 153 */     return toHexStringPadded(src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexStringPadded(byte[] src, int offset, int length) {
/* 160 */     return ((StringBuilder)toHexStringPadded(new StringBuilder(length << 1), src, offset, length)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src) {
/* 167 */     return toHexStringPadded(dst, src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src, int offset, int length) {
/* 174 */     int end = offset + length;
/* 175 */     for (int i = offset; i < end; i++) {
/* 176 */       byteToHexStringPadded(dst, src[i]);
/*     */     }
/* 178 */     return dst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byteToHexString(int value) {
/* 185 */     return BYTE2HEX_NOPAD[value & 0xFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T byteToHexString(T buf, int value) {
/*     */     try {
/* 193 */       buf.append(byteToHexString(value));
/* 194 */     } catch (IOException e) {
/* 195 */       PlatformDependent.throwException(e);
/*     */     } 
/* 197 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexString(byte[] src) {
/* 204 */     return toHexString(src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexString(byte[] src, int offset, int length) {
/* 211 */     return ((StringBuilder)toHexString(new StringBuilder(length << 1), src, offset, length)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexString(T dst, byte[] src) {
/* 218 */     return toHexString(dst, src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexString(T dst, byte[] src, int offset, int length) {
/* 225 */     assert length >= 0;
/* 226 */     if (length == 0) {
/* 227 */       return dst;
/*     */     }
/*     */     
/* 230 */     int end = offset + length;
/* 231 */     int endMinusOne = end - 1;
/*     */     
/*     */     int i;
/*     */     
/* 235 */     for (i = offset; i < endMinusOne && 
/* 236 */       src[i] == 0; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     byteToHexString(dst, src[i++]);
/* 242 */     int remaining = end - i;
/* 243 */     toHexStringPadded(dst, src, i, remaining);
/*     */     
/* 245 */     return dst;
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
/*     */   public static int decodeHexNibble(char c) {
/* 258 */     return HEX2B[c];
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
/*     */   public static int decodeHexNibble(byte b) {
/* 271 */     return HEX2B[b];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte decodeHexByte(CharSequence s, int pos) {
/* 278 */     int hi = decodeHexNibble(s.charAt(pos));
/* 279 */     int lo = decodeHexNibble(s.charAt(pos + 1));
/* 280 */     if (hi == -1 || lo == -1) {
/* 281 */       throw new IllegalArgumentException(String.format("invalid hex byte '%s' at index %d of '%s'", new Object[] { s
/* 282 */               .subSequence(pos, pos + 2), Integer.valueOf(pos), s }));
/*     */     }
/* 284 */     return (byte)((hi << 4) + lo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeHexDump(CharSequence hexDump, int fromIndex, int length) {
/* 295 */     if (length < 0 || (length & 0x1) != 0) {
/* 296 */       throw new IllegalArgumentException("length: " + length);
/*     */     }
/* 298 */     if (length == 0) {
/* 299 */       return EmptyArrays.EMPTY_BYTES;
/*     */     }
/* 301 */     byte[] bytes = new byte[length >>> 1];
/* 302 */     for (int i = 0; i < length; i += 2) {
/* 303 */       bytes[i >>> 1] = decodeHexByte(hexDump, fromIndex + i);
/*     */     }
/* 305 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeHexDump(CharSequence hexDump) {
/* 312 */     return decodeHexDump(hexDump, 0, hexDump.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String className(Object o) {
/* 319 */     if (o == null) {
/* 320 */       return "null_object";
/*     */     }
/* 322 */     return o.getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Object o) {
/* 330 */     if (o == null) {
/* 331 */       return "null_object";
/*     */     }
/* 333 */     return simpleClassName(o.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Class<?> clazz) {
/* 342 */     String className = ((Class)ObjectUtil.<Class<?>>checkNotNull(clazz, "clazz")).getName();
/* 343 */     int lastDotIdx = className.lastIndexOf('.');
/* 344 */     if (lastDotIdx > -1) {
/* 345 */       return className.substring(lastDotIdx + 1);
/*     */     }
/* 347 */     return className;
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
/*     */   public static CharSequence escapeCsv(CharSequence value) {
/* 359 */     return escapeCsv(value, false);
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
/*     */   public static CharSequence escapeCsv(CharSequence value, boolean trimWhiteSpace) {
/* 373 */     int start, last, length = ((CharSequence)ObjectUtil.<CharSequence>checkNotNull(value, "value")).length();
/*     */ 
/*     */     
/* 376 */     if (trimWhiteSpace) {
/* 377 */       start = indexOfFirstNonOwsChar(value, length);
/* 378 */       last = indexOfLastNonOwsChar(value, start, length);
/*     */     } else {
/* 380 */       start = 0;
/* 381 */       last = length - 1;
/*     */     } 
/* 383 */     if (start > last) {
/* 384 */       return "";
/*     */     }
/*     */     
/* 387 */     int firstUnescapedSpecial = -1;
/* 388 */     boolean quoted = false;
/* 389 */     if (isDoubleQuote(value.charAt(start))) {
/* 390 */       quoted = (isDoubleQuote(value.charAt(last)) && last > start);
/* 391 */       if (quoted) {
/* 392 */         start++;
/* 393 */         last--;
/*     */       } else {
/* 395 */         firstUnescapedSpecial = start;
/*     */       } 
/*     */     } 
/*     */     
/* 399 */     if (firstUnescapedSpecial < 0) {
/* 400 */       if (quoted) {
/* 401 */         for (int j = start; j <= last; j++) {
/* 402 */           if (isDoubleQuote(value.charAt(j))) {
/* 403 */             if (j == last || !isDoubleQuote(value.charAt(j + 1))) {
/* 404 */               firstUnescapedSpecial = j;
/*     */               break;
/*     */             } 
/* 407 */             j++;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 411 */         for (int j = start; j <= last; j++) {
/* 412 */           char c = value.charAt(j);
/* 413 */           if (c == '\n' || c == '\r' || c == ',') {
/* 414 */             firstUnescapedSpecial = j;
/*     */             break;
/*     */           } 
/* 417 */           if (isDoubleQuote(c)) {
/* 418 */             if (j == last || !isDoubleQuote(value.charAt(j + 1))) {
/* 419 */               firstUnescapedSpecial = j;
/*     */               break;
/*     */             } 
/* 422 */             j++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 427 */       if (firstUnescapedSpecial < 0)
/*     */       {
/*     */ 
/*     */         
/* 431 */         return quoted ? value.subSequence(start - 1, last + 2) : value.subSequence(start, last + 1);
/*     */       }
/*     */     } 
/*     */     
/* 435 */     StringBuilder result = new StringBuilder(last - start + 1 + 7);
/* 436 */     result.append('"').append(value, start, firstUnescapedSpecial);
/* 437 */     for (int i = firstUnescapedSpecial; i <= last; i++) {
/* 438 */       char c = value.charAt(i);
/* 439 */       if (isDoubleQuote(c)) {
/* 440 */         result.append('"');
/* 441 */         if (i < last && isDoubleQuote(value.charAt(i + 1))) {
/* 442 */           i++;
/*     */         }
/*     */       } 
/* 445 */       result.append(c);
/*     */     } 
/* 447 */     return result.append('"');
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
/*     */   public static CharSequence unescapeCsv(CharSequence value) {
/* 459 */     int length = ((CharSequence)ObjectUtil.<CharSequence>checkNotNull(value, "value")).length();
/* 460 */     if (length == 0) {
/* 461 */       return value;
/*     */     }
/* 463 */     int last = length - 1;
/* 464 */     boolean quoted = (isDoubleQuote(value.charAt(0)) && isDoubleQuote(value.charAt(last)) && length != 1);
/* 465 */     if (!quoted) {
/* 466 */       validateCsvFormat(value);
/* 467 */       return value;
/*     */     } 
/* 469 */     StringBuilder unescaped = InternalThreadLocalMap.get().stringBuilder();
/* 470 */     for (int i = 1; i < last; i++) {
/* 471 */       char current = value.charAt(i);
/* 472 */       if (current == '"') {
/* 473 */         if (isDoubleQuote(value.charAt(i + 1)) && i + 1 != last) {
/*     */ 
/*     */           
/* 476 */           i++;
/*     */         } else {
/*     */           
/* 479 */           throw newInvalidEscapedCsvFieldException(value, i);
/*     */         } 
/*     */       }
/* 482 */       unescaped.append(current);
/*     */     } 
/* 484 */     return unescaped.toString();
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
/*     */   public static List<CharSequence> unescapeCsvFields(CharSequence value) {
/* 496 */     List<CharSequence> unescaped = new ArrayList<>(2);
/* 497 */     StringBuilder current = InternalThreadLocalMap.get().stringBuilder();
/* 498 */     boolean quoted = false;
/* 499 */     int last = value.length() - 1;
/* 500 */     for (int i = 0; i <= last; i++) {
/* 501 */       char c = value.charAt(i);
/* 502 */       if (quoted) {
/* 503 */         char next; switch (c) {
/*     */           case '"':
/* 505 */             if (i == last) {
/*     */               
/* 507 */               unescaped.add(current.toString());
/* 508 */               return unescaped;
/*     */             } 
/* 510 */             next = value.charAt(++i);
/* 511 */             if (next == '"') {
/*     */               
/* 513 */               current.append('"');
/*     */               break;
/*     */             } 
/* 516 */             if (next == ',') {
/*     */               
/* 518 */               quoted = false;
/* 519 */               unescaped.add(current.toString());
/* 520 */               current.setLength(0);
/*     */               
/*     */               break;
/*     */             } 
/* 524 */             throw newInvalidEscapedCsvFieldException(value, i - 1);
/*     */           default:
/* 526 */             current.append(c); break;
/*     */         } 
/*     */       } else {
/* 529 */         switch (c) {
/*     */           
/*     */           case ',':
/* 532 */             unescaped.add(current.toString());
/* 533 */             current.setLength(0);
/*     */             break;
/*     */           case '"':
/* 536 */             if (current.length() == 0) {
/* 537 */               quoted = true;
/*     */               break;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case '\n':
/*     */           case '\r':
/* 546 */             throw newInvalidEscapedCsvFieldException(value, i);
/*     */           default:
/* 548 */             current.append(c); break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 552 */     if (quoted) {
/* 553 */       throw newInvalidEscapedCsvFieldException(value, last);
/*     */     }
/* 555 */     unescaped.add(current.toString());
/* 556 */     return unescaped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateCsvFormat(CharSequence value) {
/* 565 */     int length = value.length();
/* 566 */     for (int i = 0; i < length; i++) {
/* 567 */       switch (value.charAt(i)) {
/*     */         
/*     */         case '\n':
/*     */         case '\r':
/*     */         case '"':
/*     */         case ',':
/* 573 */           throw newInvalidEscapedCsvFieldException(value, i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence value, int index) {
/* 580 */     return new IllegalArgumentException("invalid escaped CSV field: " + value + " index: " + index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int length(String s) {
/* 587 */     return (s == null) ? 0 : s.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNullOrEmpty(String s) {
/* 594 */     return (s == null || s.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOfNonWhiteSpace(CharSequence seq, int offset) {
/* 605 */     for (; offset < seq.length(); offset++) {
/* 606 */       if (!Character.isWhitespace(seq.charAt(offset))) {
/* 607 */         return offset;
/*     */       }
/*     */     } 
/* 610 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOfWhiteSpace(CharSequence seq, int offset) {
/* 621 */     for (; offset < seq.length(); offset++) {
/* 622 */       if (Character.isWhitespace(seq.charAt(offset))) {
/* 623 */         return offset;
/*     */       }
/*     */     } 
/* 626 */     return -1;
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
/*     */   public static boolean isSurrogate(char c) {
/* 638 */     return (c >= '?' && c <= '?');
/*     */   }
/*     */   
/*     */   private static boolean isDoubleQuote(char c) {
/* 642 */     return (c == '"');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean endsWith(CharSequence s, char c) {
/* 653 */     int len = s.length();
/* 654 */     return (len > 0 && s.charAt(len - 1) == c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence trimOws(CharSequence value) {
/* 665 */     int length = value.length();
/* 666 */     if (length == 0) {
/* 667 */       return value;
/*     */     }
/* 669 */     int start = indexOfFirstNonOwsChar(value, length);
/* 670 */     int end = indexOfLastNonOwsChar(value, start, length);
/* 671 */     return (start == 0 && end == length - 1) ? value : value.subSequence(start, end + 1);
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
/*     */   public static CharSequence join(CharSequence separator, Iterable<? extends CharSequence> elements) {
/* 683 */     ObjectUtil.checkNotNull(separator, "separator");
/* 684 */     ObjectUtil.checkNotNull(elements, "elements");
/*     */     
/* 686 */     Iterator<? extends CharSequence> iterator = elements.iterator();
/* 687 */     if (!iterator.hasNext()) {
/* 688 */       return "";
/*     */     }
/*     */     
/* 691 */     CharSequence firstElement = iterator.next();
/* 692 */     if (!iterator.hasNext()) {
/* 693 */       return firstElement;
/*     */     }
/*     */     
/* 696 */     StringBuilder builder = new StringBuilder(firstElement);
/*     */     do {
/* 698 */       builder.append(separator).append(iterator.next());
/* 699 */     } while (iterator.hasNext());
/*     */     
/* 701 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfFirstNonOwsChar(CharSequence value, int length) {
/* 708 */     int i = 0;
/* 709 */     while (i < length && isOws(value.charAt(i))) {
/* 710 */       i++;
/*     */     }
/* 712 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfLastNonOwsChar(CharSequence value, int start, int length) {
/* 719 */     int i = length - 1;
/* 720 */     while (i > start && isOws(value.charAt(i))) {
/* 721 */       i--;
/*     */     }
/* 723 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean isOws(char c) {
/* 727 */     return (c == ' ' || c == '\t');
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */