/*      */ package io.netty.util;
/*      */ 
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.InternalThreadLocalMap;
/*      */ import io.netty.util.internal.MathUtil;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class AsciiString
/*      */   implements CharSequence, Comparable<CharSequence>
/*      */ {
/*   48 */   public static final AsciiString EMPTY_STRING = cached("");
/*      */ 
/*      */ 
/*      */   
/*      */   private static final char MAX_CHAR_VALUE = 'ÿ';
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte[] value;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int offset;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int length;
/*      */ 
/*      */   
/*      */   private int hash;
/*      */ 
/*      */   
/*      */   private String string;
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(byte[] value) {
/*   79 */     this(value, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(byte[] value, boolean copy) {
/*   87 */     this(value, 0, value.length, copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(byte[] value, int start, int length, boolean copy) {
/*   96 */     if (copy) {
/*   97 */       byte[] rangedCopy = new byte[length];
/*   98 */       System.arraycopy(value, start, rangedCopy, 0, rangedCopy.length);
/*   99 */       this.value = rangedCopy;
/*  100 */       this.offset = 0;
/*      */     } else {
/*  102 */       if (MathUtil.isOutOfBounds(start, length, value.length)) {
/*  103 */         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value.length + ')');
/*      */       }
/*      */       
/*  106 */       this.value = value;
/*  107 */       this.offset = start;
/*      */     } 
/*  109 */     this.length = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(ByteBuffer value) {
/*  117 */     this(value, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(ByteBuffer value, boolean copy) {
/*  127 */     this(value, value.position(), value.remaining(), copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(ByteBuffer value, int start, int length, boolean copy) {
/*  137 */     if (MathUtil.isOutOfBounds(start, length, value.capacity())) {
/*  138 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.capacity(" + value
/*  139 */           .capacity() + ')');
/*      */     }
/*      */     
/*  142 */     if (value.hasArray()) {
/*  143 */       if (copy) {
/*  144 */         int bufferOffset = value.arrayOffset() + start;
/*  145 */         this.value = Arrays.copyOfRange(value.array(), bufferOffset, bufferOffset + length);
/*  146 */         this.offset = 0;
/*      */       } else {
/*  148 */         this.value = value.array();
/*  149 */         this.offset = start;
/*      */       } 
/*      */     } else {
/*  152 */       this.value = PlatformDependent.allocateUninitializedArray(length);
/*  153 */       int oldPos = value.position();
/*  154 */       value.get(this.value, 0, length);
/*  155 */       value.position(oldPos);
/*  156 */       this.offset = 0;
/*      */     } 
/*  158 */     this.length = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(char[] value) {
/*  165 */     this(value, 0, value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(char[] value, int start, int length) {
/*  173 */     if (MathUtil.isOutOfBounds(start, length, value.length)) {
/*  174 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value.length + ')');
/*      */     }
/*      */ 
/*      */     
/*  178 */     this.value = PlatformDependent.allocateUninitializedArray(length);
/*  179 */     for (int i = 0, j = start; i < length; i++, j++) {
/*  180 */       this.value[i] = c2b(value[j]);
/*      */     }
/*  182 */     this.offset = 0;
/*  183 */     this.length = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(char[] value, Charset charset) {
/*  190 */     this(value, charset, 0, value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(char[] value, Charset charset, int start, int length) {
/*  198 */     CharBuffer cbuf = CharBuffer.wrap(value, start, length);
/*  199 */     CharsetEncoder encoder = CharsetUtil.encoder(charset);
/*  200 */     ByteBuffer nativeBuffer = ByteBuffer.allocate((int)(encoder.maxBytesPerChar() * length));
/*  201 */     encoder.encode(cbuf, nativeBuffer, true);
/*  202 */     int bufferOffset = nativeBuffer.arrayOffset();
/*  203 */     this.value = Arrays.copyOfRange(nativeBuffer.array(), bufferOffset, bufferOffset + nativeBuffer.position());
/*  204 */     this.offset = 0;
/*  205 */     this.length = this.value.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(CharSequence value) {
/*  212 */     this(value, 0, value.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(CharSequence value, int start, int length) {
/*  220 */     if (MathUtil.isOutOfBounds(start, length, value.length())) {
/*  221 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value
/*  222 */           .length() + ')');
/*      */     }
/*      */     
/*  225 */     this.value = PlatformDependent.allocateUninitializedArray(length);
/*  226 */     for (int i = 0, j = start; i < length; i++, j++) {
/*  227 */       this.value[i] = c2b(value.charAt(j));
/*      */     }
/*  229 */     this.offset = 0;
/*  230 */     this.length = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(CharSequence value, Charset charset) {
/*  237 */     this(value, charset, 0, value.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString(CharSequence value, Charset charset, int start, int length) {
/*  245 */     CharBuffer cbuf = CharBuffer.wrap(value, start, start + length);
/*  246 */     CharsetEncoder encoder = CharsetUtil.encoder(charset);
/*  247 */     ByteBuffer nativeBuffer = ByteBuffer.allocate((int)(encoder.maxBytesPerChar() * length));
/*  248 */     encoder.encode(cbuf, nativeBuffer, true);
/*  249 */     int offset = nativeBuffer.arrayOffset();
/*  250 */     this.value = Arrays.copyOfRange(nativeBuffer.array(), offset, offset + nativeBuffer.position());
/*  251 */     this.offset = 0;
/*  252 */     this.length = this.value.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor visitor) throws Exception {
/*  262 */     return forEachByte0(0, length(), visitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteProcessor visitor) throws Exception {
/*  273 */     if (MathUtil.isOutOfBounds(index, length, length())) {
/*  274 */       throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= length(" + 
/*  275 */           length() + ')');
/*      */     }
/*  277 */     return forEachByte0(index, length, visitor);
/*      */   }
/*      */   
/*      */   private int forEachByte0(int index, int length, ByteProcessor visitor) throws Exception {
/*  281 */     int len = this.offset + index + length;
/*  282 */     for (int i = this.offset + index; i < len; i++) {
/*  283 */       if (!visitor.process(this.value[i])) {
/*  284 */         return i - this.offset;
/*      */       }
/*      */     } 
/*  287 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor visitor) throws Exception {
/*  297 */     return forEachByteDesc0(0, length(), visitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteProcessor visitor) throws Exception {
/*  308 */     if (MathUtil.isOutOfBounds(index, length, length())) {
/*  309 */       throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= length(" + 
/*  310 */           length() + ')');
/*      */     }
/*  312 */     return forEachByteDesc0(index, length, visitor);
/*      */   }
/*      */   
/*      */   private int forEachByteDesc0(int index, int length, ByteProcessor visitor) throws Exception {
/*  316 */     int end = this.offset + index;
/*  317 */     for (int i = this.offset + index + length - 1; i >= end; i--) {
/*  318 */       if (!visitor.process(this.value[i])) {
/*  319 */         return i - this.offset;
/*      */       }
/*      */     } 
/*  322 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte byteAt(int index) {
/*  328 */     if (index < 0 || index >= this.length) {
/*  329 */       throw new IndexOutOfBoundsException("index: " + index + " must be in the range [0," + this.length + ")");
/*      */     }
/*      */     
/*  332 */     if (PlatformDependent.hasUnsafe()) {
/*  333 */       return PlatformDependent.getByte(this.value, index + this.offset);
/*      */     }
/*  335 */     return this.value[index + this.offset];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  342 */     return (this.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  350 */     return this.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void arrayChanged() {
/*  358 */     this.string = null;
/*  359 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  370 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  379 */     return this.offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntireArrayUsed() {
/*  387 */     return (this.offset == 0 && this.length == this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*  394 */     return toByteArray(0, length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray(int start, int end) {
/*  402 */     return Arrays.copyOfRange(this.value, start + this.offset, end + this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(int srcIdx, byte[] dst, int dstIdx, int length) {
/*  414 */     if (MathUtil.isOutOfBounds(srcIdx, length, length())) {
/*  415 */       throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + 
/*  416 */           length() + ')');
/*      */     }
/*      */     
/*  419 */     System.arraycopy(this.value, srcIdx + this.offset, ObjectUtil.checkNotNull(dst, "dst"), dstIdx, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public char charAt(int index) {
/*  424 */     return b2c(byteAt(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(CharSequence cs) {
/*  434 */     return (indexOf(cs) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(CharSequence string) {
/*  452 */     if (this == string) {
/*  453 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  457 */     int length1 = length();
/*  458 */     int length2 = string.length();
/*  459 */     int minLength = Math.min(length1, length2);
/*  460 */     for (int i = 0, j = arrayOffset(); i < minLength; i++, j++) {
/*  461 */       int result = b2c(this.value[j]) - string.charAt(i);
/*  462 */       if (result != 0) {
/*  463 */         return result;
/*      */       }
/*      */     } 
/*      */     
/*  467 */     return length1 - length2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString concat(CharSequence string) {
/*  477 */     int thisLen = length();
/*  478 */     int thatLen = string.length();
/*  479 */     if (thatLen == 0) {
/*  480 */       return this;
/*      */     }
/*      */     
/*  483 */     if (string instanceof AsciiString) {
/*  484 */       AsciiString that = (AsciiString)string;
/*  485 */       if (isEmpty()) {
/*  486 */         return that;
/*      */       }
/*      */       
/*  489 */       byte[] arrayOfByte = PlatformDependent.allocateUninitializedArray(thisLen + thatLen);
/*  490 */       System.arraycopy(this.value, arrayOffset(), arrayOfByte, 0, thisLen);
/*  491 */       System.arraycopy(that.value, that.arrayOffset(), arrayOfByte, thisLen, thatLen);
/*  492 */       return new AsciiString(arrayOfByte, false);
/*      */     } 
/*      */     
/*  495 */     if (isEmpty()) {
/*  496 */       return new AsciiString(string);
/*      */     }
/*      */     
/*  499 */     byte[] newValue = PlatformDependent.allocateUninitializedArray(thisLen + thatLen);
/*  500 */     System.arraycopy(this.value, arrayOffset(), newValue, 0, thisLen);
/*  501 */     for (int i = thisLen, j = 0; i < newValue.length; i++, j++) {
/*  502 */       newValue[i] = c2b(string.charAt(j));
/*      */     }
/*      */     
/*  505 */     return new AsciiString(newValue, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean endsWith(CharSequence suffix) {
/*  516 */     int suffixLen = suffix.length();
/*  517 */     return regionMatches(length() - suffixLen, suffix, 0, suffixLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contentEqualsIgnoreCase(CharSequence string) {
/*  528 */     if (this == string) {
/*  529 */       return true;
/*      */     }
/*      */     
/*  532 */     if (string == null || string.length() != length()) {
/*  533 */       return false;
/*      */     }
/*      */     
/*  536 */     if (string instanceof AsciiString) {
/*  537 */       AsciiString other = (AsciiString)string;
/*  538 */       byte[] arrayOfByte = this.value;
/*  539 */       if (this.offset == 0 && other.offset == 0 && this.length == arrayOfByte.length) {
/*  540 */         byte[] otherValue = other.value;
/*  541 */         for (int k = 0; k < arrayOfByte.length; k++) {
/*  542 */           if (!equalsIgnoreCase(arrayOfByte[k], otherValue[k])) {
/*  543 */             return false;
/*      */           }
/*      */         } 
/*  546 */         return true;
/*      */       } 
/*  548 */       return misalignedEqualsIgnoreCase(other);
/*      */     } 
/*      */     
/*  551 */     byte[] value = this.value;
/*  552 */     for (int i = this.offset, j = 0; j < string.length(); i++, j++) {
/*  553 */       if (!equalsIgnoreCase(b2c(value[i]), string.charAt(j))) {
/*  554 */         return false;
/*      */       }
/*      */     } 
/*  557 */     return true;
/*      */   }
/*      */   
/*      */   private boolean misalignedEqualsIgnoreCase(AsciiString other) {
/*  561 */     byte[] value = this.value;
/*  562 */     byte[] otherValue = other.value;
/*  563 */     for (int i = this.offset, j = other.offset, end = this.offset + this.length; i < end; i++, j++) {
/*  564 */       if (!equalsIgnoreCase(value[i], otherValue[j])) {
/*  565 */         return false;
/*      */       }
/*      */     } 
/*  568 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray() {
/*  577 */     return toCharArray(0, length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray(int start, int end) {
/*  586 */     int length = end - start;
/*  587 */     if (length == 0) {
/*  588 */       return EmptyArrays.EMPTY_CHARS;
/*      */     }
/*      */     
/*  591 */     if (MathUtil.isOutOfBounds(start, length, length())) {
/*  592 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + 
/*  593 */           length() + ')');
/*      */     }
/*      */     
/*  596 */     char[] buffer = new char[length];
/*  597 */     for (int i = 0, j = start + arrayOffset(); i < length; i++, j++) {
/*  598 */       buffer[i] = b2c(this.value[j]);
/*      */     }
/*  600 */     return buffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(int srcIdx, char[] dst, int dstIdx, int length) {
/*  612 */     ObjectUtil.checkNotNull(dst, "dst");
/*      */     
/*  614 */     if (MathUtil.isOutOfBounds(srcIdx, length, length())) {
/*  615 */       throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + 
/*  616 */           length() + ')');
/*      */     }
/*      */     
/*  619 */     int dstEnd = dstIdx + length;
/*  620 */     for (int i = dstIdx, j = srcIdx + arrayOffset(); i < dstEnd; i++, j++) {
/*  621 */       dst[i] = b2c(this.value[j]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString subSequence(int start) {
/*  632 */     return subSequence(start, length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString subSequence(int start, int end) {
/*  644 */     return subSequence(start, end, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString subSequence(int start, int end, boolean copy) {
/*  657 */     if (MathUtil.isOutOfBounds(start, end - start, length())) {
/*  658 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= end (" + end + ") <= length(" + 
/*  659 */           length() + ')');
/*      */     }
/*      */     
/*  662 */     if (start == 0 && end == length()) {
/*  663 */       return this;
/*      */     }
/*      */     
/*  666 */     if (end == start) {
/*  667 */       return EMPTY_STRING;
/*      */     }
/*      */     
/*  670 */     return new AsciiString(this.value, start + this.offset, end - start, copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(CharSequence string) {
/*  683 */     return indexOf(string, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(CharSequence subString, int start) {
/*  697 */     int subCount = subString.length();
/*  698 */     if (start < 0) {
/*  699 */       start = 0;
/*      */     }
/*  701 */     if (subCount <= 0) {
/*  702 */       return (start < this.length) ? start : this.length;
/*      */     }
/*  704 */     if (subCount > this.length - start) {
/*  705 */       return -1;
/*      */     }
/*      */     
/*  708 */     char firstChar = subString.charAt(0);
/*  709 */     if (firstChar > 'ÿ') {
/*  710 */       return -1;
/*      */     }
/*  712 */     byte firstCharAsByte = c2b0(firstChar);
/*  713 */     int len = this.offset + this.length - subCount;
/*  714 */     for (int i = start + this.offset; i <= len; i++) {
/*  715 */       if (this.value[i] == firstCharAsByte) {
/*  716 */         int o1 = i, o2 = 0;
/*  717 */         while (++o2 < subCount && b2c(this.value[++o1]) == subString.charAt(o2));
/*      */ 
/*      */         
/*  720 */         if (o2 == subCount) {
/*  721 */           return i - this.offset;
/*      */         }
/*      */       } 
/*      */     } 
/*  725 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch, int start) {
/*  738 */     if (ch > 'ÿ') {
/*  739 */       return -1;
/*      */     }
/*      */     
/*  742 */     if (start < 0) {
/*  743 */       start = 0;
/*      */     }
/*      */     
/*  746 */     byte chAsByte = c2b0(ch);
/*  747 */     int len = this.offset + this.length;
/*  748 */     for (int i = start + this.offset; i < len; i++) {
/*  749 */       if (this.value[i] == chAsByte) {
/*  750 */         return i - this.offset;
/*      */       }
/*      */     } 
/*  753 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(CharSequence string) {
/*  767 */     return lastIndexOf(string, this.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(CharSequence subString, int start) {
/*  781 */     int subCount = subString.length();
/*  782 */     start = Math.min(start, this.length - subCount);
/*  783 */     if (start < 0) {
/*  784 */       return -1;
/*      */     }
/*  786 */     if (subCount == 0) {
/*  787 */       return start;
/*      */     }
/*      */     
/*  790 */     char firstChar = subString.charAt(0);
/*  791 */     if (firstChar > 'ÿ') {
/*  792 */       return -1;
/*      */     }
/*  794 */     byte firstCharAsByte = c2b0(firstChar);
/*  795 */     for (int i = this.offset + start; i >= this.offset; i--) {
/*  796 */       if (this.value[i] == firstCharAsByte) {
/*  797 */         int o1 = i, o2 = 0;
/*  798 */         while (++o2 < subCount && b2c(this.value[++o1]) == subString.charAt(o2));
/*      */ 
/*      */         
/*  801 */         if (o2 == subCount) {
/*  802 */           return i - this.offset;
/*      */         }
/*      */       } 
/*      */     } 
/*  806 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean regionMatches(int thisStart, CharSequence string, int start, int length) {
/*  821 */     ObjectUtil.checkNotNull(string, "string");
/*      */     
/*  823 */     if (start < 0 || string.length() - start < length) {
/*  824 */       return false;
/*      */     }
/*      */     
/*  827 */     int thisLen = length();
/*  828 */     if (thisStart < 0 || thisLen - thisStart < length) {
/*  829 */       return false;
/*      */     }
/*      */     
/*  832 */     if (length <= 0) {
/*  833 */       return true;
/*      */     }
/*      */     
/*  836 */     if (string instanceof AsciiString) {
/*  837 */       AsciiString asciiString = (AsciiString)string;
/*  838 */       return PlatformDependent.equals(this.value, thisStart + this.offset, asciiString.value, start + asciiString.offset, length);
/*      */     } 
/*      */     
/*  841 */     int thatEnd = start + length;
/*  842 */     for (int i = start, j = thisStart + arrayOffset(); i < thatEnd; i++, j++) {
/*  843 */       if (b2c(this.value[j]) != string.charAt(i)) {
/*  844 */         return false;
/*      */       }
/*      */     } 
/*  847 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean regionMatches(boolean ignoreCase, int thisStart, CharSequence string, int start, int length) {
/*  863 */     if (!ignoreCase) {
/*  864 */       return regionMatches(thisStart, string, start, length);
/*      */     }
/*      */     
/*  867 */     ObjectUtil.checkNotNull(string, "string");
/*      */     
/*  869 */     int thisLen = length();
/*  870 */     if (thisStart < 0 || length > thisLen - thisStart) {
/*  871 */       return false;
/*      */     }
/*  873 */     if (start < 0 || length > string.length() - start) {
/*  874 */       return false;
/*      */     }
/*      */     
/*  877 */     thisStart += arrayOffset();
/*  878 */     int thisEnd = thisStart + length;
/*  879 */     if (string instanceof AsciiString) {
/*  880 */       AsciiString asciiString = (AsciiString)string;
/*  881 */       byte[] value = this.value;
/*  882 */       byte[] otherValue = asciiString.value;
/*  883 */       start += asciiString.offset;
/*  884 */       while (thisStart < thisEnd) {
/*  885 */         if (!equalsIgnoreCase(value[thisStart++], otherValue[start++])) {
/*  886 */           return false;
/*      */         }
/*      */       } 
/*  889 */       return true;
/*      */     } 
/*  891 */     while (thisStart < thisEnd) {
/*  892 */       if (!equalsIgnoreCase(b2c(this.value[thisStart++]), string.charAt(start++))) {
/*  893 */         return false;
/*      */       }
/*      */     } 
/*  896 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString replace(char oldChar, char newChar) {
/*  907 */     if (oldChar > 'ÿ') {
/*  908 */       return this;
/*      */     }
/*      */     
/*  911 */     byte oldCharAsByte = c2b0(oldChar);
/*  912 */     byte newCharAsByte = c2b(newChar);
/*  913 */     int len = this.offset + this.length;
/*  914 */     for (int i = this.offset; i < len; i++) {
/*  915 */       if (this.value[i] == oldCharAsByte) {
/*  916 */         byte[] buffer = PlatformDependent.allocateUninitializedArray(length());
/*  917 */         System.arraycopy(this.value, this.offset, buffer, 0, i - this.offset);
/*  918 */         buffer[i - this.offset] = newCharAsByte;
/*  919 */         i++;
/*  920 */         for (; i < len; i++) {
/*  921 */           byte oldValue = this.value[i];
/*  922 */           buffer[i - this.offset] = (oldValue != oldCharAsByte) ? oldValue : newCharAsByte;
/*      */         } 
/*  924 */         return new AsciiString(buffer, false);
/*      */       } 
/*      */     } 
/*  927 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(CharSequence prefix) {
/*  938 */     return startsWith(prefix, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(CharSequence prefix, int start) {
/*  952 */     return regionMatches(start, prefix, 0, prefix.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString toLowerCase() {
/*  961 */     return AsciiStringUtil.toLowerCase(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString toUpperCase() {
/*  970 */     return AsciiStringUtil.toUpperCase(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence trim(CharSequence c) {
/*  981 */     if (c instanceof AsciiString) {
/*  982 */       return ((AsciiString)c).trim();
/*      */     }
/*  984 */     if (c instanceof String) {
/*  985 */       return ((String)c).trim();
/*      */     }
/*  987 */     int start = 0, last = c.length() - 1;
/*  988 */     int end = last;
/*  989 */     while (start <= end && c.charAt(start) <= ' ') {
/*  990 */       start++;
/*      */     }
/*  992 */     while (end >= start && c.charAt(end) <= ' ') {
/*  993 */       end--;
/*      */     }
/*  995 */     if (start == 0 && end == last) {
/*  996 */       return c;
/*      */     }
/*  998 */     return c.subSequence(start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString trim() {
/* 1008 */     int start = arrayOffset(), last = arrayOffset() + length() - 1;
/* 1009 */     int end = last;
/* 1010 */     while (start <= end && this.value[start] <= 32) {
/* 1011 */       start++;
/*      */     }
/* 1013 */     while (end >= start && this.value[end] <= 32) {
/* 1014 */       end--;
/*      */     }
/* 1016 */     if (start == 0 && end == last) {
/* 1017 */       return this;
/*      */     }
/* 1019 */     return new AsciiString(this.value, start, end - start + 1, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contentEquals(CharSequence a) {
/* 1029 */     if (this == a) {
/* 1030 */       return true;
/*      */     }
/*      */     
/* 1033 */     if (a == null || a.length() != length()) {
/* 1034 */       return false;
/*      */     }
/* 1036 */     if (a instanceof AsciiString) {
/* 1037 */       return equals(a);
/*      */     }
/*      */     
/* 1040 */     for (int i = arrayOffset(), j = 0; j < a.length(); i++, j++) {
/* 1041 */       if (b2c(this.value[i]) != a.charAt(j)) {
/* 1042 */         return false;
/*      */       }
/*      */     } 
/* 1045 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matches(String expr) {
/* 1057 */     return Pattern.matches(expr, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString[] split(String expr, int max) {
/* 1072 */     return toAsciiStringArray(Pattern.compile(expr).split(this, max));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AsciiString[] split(char delim) {
/* 1079 */     List<AsciiString> res = InternalThreadLocalMap.get().arrayList();
/*      */     
/* 1081 */     int start = 0;
/* 1082 */     int length = length(); int i;
/* 1083 */     for (i = start; i < length; i++) {
/* 1084 */       if (charAt(i) == delim) {
/* 1085 */         if (start == i) {
/* 1086 */           res.add(EMPTY_STRING);
/*      */         } else {
/* 1088 */           res.add(new AsciiString(this.value, start + arrayOffset(), i - start, false));
/*      */         } 
/* 1090 */         start = i + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 1094 */     if (start == 0) {
/* 1095 */       res.add(this);
/*      */     }
/* 1097 */     else if (start != length) {
/*      */       
/* 1099 */       res.add(new AsciiString(this.value, start + arrayOffset(), length - start, false));
/*      */     } else {
/*      */       
/* 1102 */       for (i = res.size() - 1; i >= 0 && (
/* 1103 */         (AsciiString)res.get(i)).isEmpty(); i--) {
/* 1104 */         res.remove(i);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     return res.<AsciiString>toArray(EmptyArrays.EMPTY_ASCII_STRINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1122 */     int h = this.hash;
/* 1123 */     if (h == 0) {
/* 1124 */       h = PlatformDependent.hashCodeAscii(this.value, this.offset, this.length);
/* 1125 */       this.hash = h;
/*      */     } 
/* 1127 */     return h;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1132 */     if (obj == null || obj.getClass() != AsciiString.class) {
/* 1133 */       return false;
/*      */     }
/* 1135 */     if (this == obj) {
/* 1136 */       return true;
/*      */     }
/*      */     
/* 1139 */     AsciiString other = (AsciiString)obj;
/* 1140 */     return (length() == other.length() && 
/* 1141 */       hashCode() == other.hashCode() && 
/* 1142 */       PlatformDependent.equals(array(), arrayOffset(), other.array(), other.arrayOffset(), length()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1151 */     String cache = this.string;
/* 1152 */     if (cache == null) {
/* 1153 */       cache = toString(0);
/* 1154 */       this.string = cache;
/*      */     } 
/* 1156 */     return cache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int start) {
/* 1164 */     return toString(start, length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int start, int end) {
/* 1171 */     int length = end - start;
/* 1172 */     if (length == 0) {
/* 1173 */       return "";
/*      */     }
/*      */     
/* 1176 */     if (MathUtil.isOutOfBounds(start, length, length())) {
/* 1177 */       throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + 
/* 1178 */           length() + ')');
/*      */     }
/*      */ 
/*      */     
/* 1182 */     String str = new String(this.value, 0, start + this.offset, length);
/* 1183 */     return str;
/*      */   }
/*      */   
/*      */   public boolean parseBoolean() {
/* 1187 */     return (this.length >= 1 && this.value[this.offset] != 0);
/*      */   }
/*      */   
/*      */   public char parseChar() {
/* 1191 */     return parseChar(0);
/*      */   }
/*      */   
/*      */   public char parseChar(int start) {
/* 1195 */     if (start + 1 >= length()) {
/* 1196 */       throw new IndexOutOfBoundsException("2 bytes required to convert to character. index " + start + " would go out of bounds.");
/*      */     }
/*      */     
/* 1199 */     int startWithOffset = start + this.offset;
/* 1200 */     return (char)(b2c(this.value[startWithOffset]) << 8 | b2c(this.value[startWithOffset + 1]));
/*      */   }
/*      */   
/*      */   public short parseShort() {
/* 1204 */     return parseShort(0, length(), 10);
/*      */   }
/*      */   
/*      */   public short parseShort(int radix) {
/* 1208 */     return parseShort(0, length(), radix);
/*      */   }
/*      */   
/*      */   public short parseShort(int start, int end) {
/* 1212 */     return parseShort(start, end, 10);
/*      */   }
/*      */   
/*      */   public short parseShort(int start, int end, int radix) {
/* 1216 */     int intValue = parseInt(start, end, radix);
/* 1217 */     short result = (short)intValue;
/* 1218 */     if (result != intValue) {
/* 1219 */       throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */     }
/* 1221 */     return result;
/*      */   }
/*      */   
/*      */   public int parseInt() {
/* 1225 */     return parseInt(0, length(), 10);
/*      */   }
/*      */   
/*      */   public int parseInt(int radix) {
/* 1229 */     return parseInt(0, length(), radix);
/*      */   }
/*      */   
/*      */   public int parseInt(int start, int end) {
/* 1233 */     return parseInt(start, end, 10);
/*      */   }
/*      */   
/*      */   public int parseInt(int start, int end, int radix) {
/* 1237 */     if (radix < 2 || radix > 36) {
/* 1238 */       throw new NumberFormatException();
/*      */     }
/*      */     
/* 1241 */     if (start == end) {
/* 1242 */       throw new NumberFormatException();
/*      */     }
/*      */     
/* 1245 */     int i = start;
/* 1246 */     boolean negative = (byteAt(i) == 45);
/* 1247 */     if (negative && ++i == end) {
/* 1248 */       throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */     }
/*      */     
/* 1251 */     return parseInt(i, end, radix, negative);
/*      */   }
/*      */   
/*      */   private int parseInt(int start, int end, int radix, boolean negative) {
/* 1255 */     int max = Integer.MIN_VALUE / radix;
/* 1256 */     int result = 0;
/* 1257 */     int currOffset = start;
/* 1258 */     while (currOffset < end) {
/* 1259 */       int digit = Character.digit((char)(this.value[currOffset++ + this.offset] & 0xFF), radix);
/* 1260 */       if (digit == -1) {
/* 1261 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1263 */       if (max > result) {
/* 1264 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1266 */       int next = result * radix - digit;
/* 1267 */       if (next > result) {
/* 1268 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1270 */       result = next;
/*      */     } 
/* 1272 */     if (!negative) {
/* 1273 */       result = -result;
/* 1274 */       if (result < 0) {
/* 1275 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/*      */     } 
/* 1278 */     return result;
/*      */   }
/*      */   
/*      */   public long parseLong() {
/* 1282 */     return parseLong(0, length(), 10);
/*      */   }
/*      */   
/*      */   public long parseLong(int radix) {
/* 1286 */     return parseLong(0, length(), radix);
/*      */   }
/*      */   
/*      */   public long parseLong(int start, int end) {
/* 1290 */     return parseLong(start, end, 10);
/*      */   }
/*      */   
/*      */   public long parseLong(int start, int end, int radix) {
/* 1294 */     if (radix < 2 || radix > 36) {
/* 1295 */       throw new NumberFormatException();
/*      */     }
/*      */     
/* 1298 */     if (start == end) {
/* 1299 */       throw new NumberFormatException();
/*      */     }
/*      */     
/* 1302 */     int i = start;
/* 1303 */     boolean negative = (byteAt(i) == 45);
/* 1304 */     if (negative && ++i == end) {
/* 1305 */       throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */     }
/*      */     
/* 1308 */     return parseLong(i, end, radix, negative);
/*      */   }
/*      */   
/*      */   private long parseLong(int start, int end, int radix, boolean negative) {
/* 1312 */     long max = Long.MIN_VALUE / radix;
/* 1313 */     long result = 0L;
/* 1314 */     int currOffset = start;
/* 1315 */     while (currOffset < end) {
/* 1316 */       int digit = Character.digit((char)(this.value[currOffset++ + this.offset] & 0xFF), radix);
/* 1317 */       if (digit == -1) {
/* 1318 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1320 */       if (max > result) {
/* 1321 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1323 */       long next = result * radix - digit;
/* 1324 */       if (next > result) {
/* 1325 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/* 1327 */       result = next;
/*      */     } 
/* 1329 */     if (!negative) {
/* 1330 */       result = -result;
/* 1331 */       if (result < 0L) {
/* 1332 */         throw new NumberFormatException(subSequence(start, end, false).toString());
/*      */       }
/*      */     } 
/* 1335 */     return result;
/*      */   }
/*      */   
/*      */   public float parseFloat() {
/* 1339 */     return parseFloat(0, length());
/*      */   }
/*      */   
/*      */   public float parseFloat(int start, int end) {
/* 1343 */     return Float.parseFloat(toString(start, end));
/*      */   }
/*      */   
/*      */   public double parseDouble() {
/* 1347 */     return parseDouble(0, length());
/*      */   }
/*      */   
/*      */   public double parseDouble(int start, int end) {
/* 1351 */     return Double.parseDouble(toString(start, end));
/*      */   }
/*      */   
/* 1354 */   public static final HashingStrategy<CharSequence> CASE_INSENSITIVE_HASHER = new HashingStrategy<CharSequence>()
/*      */     {
/*      */       public int hashCode(CharSequence o)
/*      */       {
/* 1358 */         return AsciiString.hashCode(o);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(CharSequence a, CharSequence b) {
/* 1363 */         return AsciiString.contentEqualsIgnoreCase(a, b);
/*      */       }
/*      */     };
/*      */   
/* 1367 */   public static final HashingStrategy<CharSequence> CASE_SENSITIVE_HASHER = new HashingStrategy<CharSequence>()
/*      */     {
/*      */       public int hashCode(CharSequence o)
/*      */       {
/* 1371 */         return AsciiString.hashCode(o);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(CharSequence a, CharSequence b) {
/* 1376 */         return AsciiString.contentEquals(a, b);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AsciiString of(CharSequence string) {
/* 1385 */     return (string instanceof AsciiString) ? (AsciiString)string : new AsciiString(string);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AsciiString cached(String string) {
/* 1395 */     AsciiString asciiString = new AsciiString(string);
/* 1396 */     asciiString.string = string;
/* 1397 */     return asciiString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hashCode(CharSequence value) {
/* 1406 */     if (value == null) {
/* 1407 */       return 0;
/*      */     }
/* 1409 */     if (value instanceof AsciiString) {
/* 1410 */       return value.hashCode();
/*      */     }
/*      */     
/* 1413 */     return PlatformDependent.hashCodeAscii(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(CharSequence a, CharSequence b) {
/* 1420 */     return contains(a, b, DefaultCharEqualityComparator.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean containsIgnoreCase(CharSequence a, CharSequence b) {
/* 1427 */     return contains(a, b, AsciiCaseInsensitiveCharEqualityComparator.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contentEqualsIgnoreCase(CharSequence a, CharSequence b) {
/* 1435 */     if (a == null || b == null) {
/* 1436 */       return (a == b);
/*      */     }
/*      */     
/* 1439 */     if (a instanceof AsciiString) {
/* 1440 */       return ((AsciiString)a).contentEqualsIgnoreCase(b);
/*      */     }
/* 1442 */     if (b instanceof AsciiString) {
/* 1443 */       return ((AsciiString)b).contentEqualsIgnoreCase(a);
/*      */     }
/*      */     
/* 1446 */     if (a.length() != b.length()) {
/* 1447 */       return false;
/*      */     }
/* 1449 */     for (int i = 0; i < a.length(); i++) {
/* 1450 */       if (!equalsIgnoreCase(a.charAt(i), b.charAt(i))) {
/* 1451 */         return false;
/*      */       }
/*      */     } 
/* 1454 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean containsContentEqualsIgnoreCase(Collection<CharSequence> collection, CharSequence value) {
/* 1467 */     for (CharSequence v : collection) {
/* 1468 */       if (contentEqualsIgnoreCase(value, v)) {
/* 1469 */         return true;
/*      */       }
/*      */     } 
/* 1472 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean containsAllContentEqualsIgnoreCase(Collection<CharSequence> a, Collection<CharSequence> b) {
/* 1485 */     for (CharSequence v : b) {
/* 1486 */       if (!containsContentEqualsIgnoreCase(a, v)) {
/* 1487 */         return false;
/*      */       }
/*      */     } 
/* 1490 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contentEquals(CharSequence a, CharSequence b) {
/* 1497 */     if (a == null || b == null) {
/* 1498 */       return (a == b);
/*      */     }
/*      */     
/* 1501 */     if (a instanceof AsciiString) {
/* 1502 */       return ((AsciiString)a).contentEquals(b);
/*      */     }
/*      */     
/* 1505 */     if (b instanceof AsciiString) {
/* 1506 */       return ((AsciiString)b).contentEquals(a);
/*      */     }
/*      */     
/* 1509 */     if (a.length() != b.length()) {
/* 1510 */       return false;
/*      */     }
/* 1512 */     for (int i = 0; i < a.length(); i++) {
/* 1513 */       if (a.charAt(i) != b.charAt(i)) {
/* 1514 */         return false;
/*      */       }
/*      */     } 
/* 1517 */     return true;
/*      */   }
/*      */   
/*      */   private static AsciiString[] toAsciiStringArray(String[] jdkResult) {
/* 1521 */     AsciiString[] res = new AsciiString[jdkResult.length];
/* 1522 */     for (int i = 0; i < jdkResult.length; i++) {
/* 1523 */       res[i] = new AsciiString(jdkResult[i]);
/*      */     }
/* 1525 */     return res;
/*      */   }
/*      */   
/*      */   private static interface CharEqualityComparator {
/*      */     boolean equals(char param1Char1, char param1Char2);
/*      */   }
/*      */   
/*      */   private static final class DefaultCharEqualityComparator implements CharEqualityComparator {
/* 1533 */     static final DefaultCharEqualityComparator INSTANCE = new DefaultCharEqualityComparator();
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(char a, char b) {
/* 1538 */       return (a == b);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class AsciiCaseInsensitiveCharEqualityComparator
/*      */     implements CharEqualityComparator {
/* 1544 */     static final AsciiCaseInsensitiveCharEqualityComparator INSTANCE = new AsciiCaseInsensitiveCharEqualityComparator();
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(char a, char b) {
/* 1549 */       return AsciiString.equalsIgnoreCase(a, b);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class GeneralCaseInsensitiveCharEqualityComparator
/*      */     implements CharEqualityComparator {
/* 1555 */     static final GeneralCaseInsensitiveCharEqualityComparator INSTANCE = new GeneralCaseInsensitiveCharEqualityComparator();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(char a, char b) {
/* 1561 */       return (Character.toUpperCase(a) == Character.toUpperCase(b) || 
/* 1562 */         Character.toLowerCase(a) == Character.toLowerCase(b));
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean contains(CharSequence a, CharSequence b, CharEqualityComparator cmp) {
/* 1567 */     if (a == null || b == null || a.length() < b.length()) {
/* 1568 */       return false;
/*      */     }
/* 1570 */     if (b.length() == 0) {
/* 1571 */       return true;
/*      */     }
/* 1573 */     int bStart = 0;
/* 1574 */     for (int i = 0; i < a.length(); i++) {
/* 1575 */       if (cmp.equals(b.charAt(bStart), a.charAt(i))) {
/*      */         
/* 1577 */         if (++bStart == b.length())
/* 1578 */           return true; 
/*      */       } else {
/* 1580 */         if (a.length() - i < b.length())
/*      */         {
/* 1582 */           return false;
/*      */         }
/* 1584 */         bStart = 0;
/*      */       } 
/*      */     } 
/* 1587 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean regionMatchesCharSequences(CharSequence cs, int csStart, CharSequence string, int start, int length, CharEqualityComparator charEqualityComparator) {
/* 1594 */     if (csStart < 0 || length > cs.length() - csStart) {
/* 1595 */       return false;
/*      */     }
/* 1597 */     if (start < 0 || length > string.length() - start) {
/* 1598 */       return false;
/*      */     }
/*      */     
/* 1601 */     int csIndex = csStart;
/* 1602 */     int csEnd = csIndex + length;
/* 1603 */     int stringIndex = start;
/*      */     
/* 1605 */     while (csIndex < csEnd) {
/* 1606 */       char c1 = cs.charAt(csIndex++);
/* 1607 */       char c2 = string.charAt(stringIndex++);
/*      */       
/* 1609 */       if (!charEqualityComparator.equals(c1, c2)) {
/* 1610 */         return false;
/*      */       }
/*      */     } 
/* 1613 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean regionMatches(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
/* 1628 */     if (cs == null || string == null) {
/* 1629 */       return false;
/*      */     }
/*      */     
/* 1632 */     if (cs instanceof String && string instanceof String) {
/* 1633 */       return ((String)cs).regionMatches(ignoreCase, csStart, (String)string, start, length);
/*      */     }
/*      */     
/* 1636 */     if (cs instanceof AsciiString) {
/* 1637 */       return ((AsciiString)cs).regionMatches(ignoreCase, csStart, string, start, length);
/*      */     }
/*      */     
/* 1640 */     return regionMatchesCharSequences(cs, csStart, string, start, length, 
/* 1641 */         ignoreCase ? GeneralCaseInsensitiveCharEqualityComparator.INSTANCE : 
/* 1642 */         DefaultCharEqualityComparator.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean regionMatchesAscii(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
/* 1657 */     if (cs == null || string == null) {
/* 1658 */       return false;
/*      */     }
/*      */     
/* 1661 */     if (!ignoreCase && cs instanceof String && string instanceof String)
/*      */     {
/*      */ 
/*      */       
/* 1665 */       return ((String)cs).regionMatches(false, csStart, (String)string, start, length);
/*      */     }
/*      */     
/* 1668 */     if (cs instanceof AsciiString) {
/* 1669 */       return ((AsciiString)cs).regionMatches(ignoreCase, csStart, string, start, length);
/*      */     }
/*      */     
/* 1672 */     return regionMatchesCharSequences(cs, csStart, string, start, length, 
/* 1673 */         ignoreCase ? AsciiCaseInsensitiveCharEqualityComparator.INSTANCE : 
/* 1674 */         DefaultCharEqualityComparator.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 1708 */     if (str == null || searchStr == null) {
/* 1709 */       return -1;
/*      */     }
/* 1711 */     if (startPos < 0) {
/* 1712 */       startPos = 0;
/*      */     }
/* 1714 */     int searchStrLen = searchStr.length();
/* 1715 */     int endLimit = str.length() - searchStrLen + 1;
/* 1716 */     if (startPos > endLimit) {
/* 1717 */       return -1;
/*      */     }
/* 1719 */     if (searchStrLen == 0) {
/* 1720 */       return startPos;
/*      */     }
/* 1722 */     for (int i = startPos; i < endLimit; i++) {
/* 1723 */       if (regionMatches(str, true, i, searchStr, 0, searchStrLen)) {
/* 1724 */         return i;
/*      */       }
/*      */     } 
/* 1727 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOfIgnoreCaseAscii(CharSequence str, CharSequence searchStr, int startPos) {
/* 1761 */     if (str == null || searchStr == null) {
/* 1762 */       return -1;
/*      */     }
/* 1764 */     if (startPos < 0) {
/* 1765 */       startPos = 0;
/*      */     }
/* 1767 */     int searchStrLen = searchStr.length();
/* 1768 */     int endLimit = str.length() - searchStrLen + 1;
/* 1769 */     if (startPos > endLimit) {
/* 1770 */       return -1;
/*      */     }
/* 1772 */     if (searchStrLen == 0) {
/* 1773 */       return startPos;
/*      */     }
/* 1775 */     for (int i = startPos; i < endLimit; i++) {
/* 1776 */       if (regionMatchesAscii(str, true, i, searchStr, 0, searchStrLen)) {
/* 1777 */         return i;
/*      */       }
/*      */     } 
/* 1780 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(CharSequence cs, char searchChar, int start) {
/* 1795 */     if (cs instanceof String)
/* 1796 */       return ((String)cs).indexOf(searchChar, start); 
/* 1797 */     if (cs instanceof AsciiString) {
/* 1798 */       return ((AsciiString)cs).indexOf(searchChar, start);
/*      */     }
/* 1800 */     if (cs == null) {
/* 1801 */       return -1;
/*      */     }
/* 1803 */     int sz = cs.length();
/* 1804 */     for (int i = (start < 0) ? 0 : start; i < sz; i++) {
/* 1805 */       if (cs.charAt(i) == searchChar) {
/* 1806 */         return i;
/*      */       }
/*      */     } 
/* 1809 */     return -1;
/*      */   }
/*      */   
/*      */   private static boolean equalsIgnoreCase(byte a, byte b) {
/* 1813 */     return (a == b || AsciiStringUtil.toLowerCase(a) == AsciiStringUtil.toLowerCase(b));
/*      */   }
/*      */   
/*      */   private static boolean equalsIgnoreCase(char a, char b) {
/* 1817 */     return (a == b || toLowerCase(a) == toLowerCase(b));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char toLowerCase(char c) {
/* 1827 */     return isUpperCase(c) ? (char)(c + 32) : c;
/*      */   }
/*      */   
/*      */   private static byte toUpperCase(byte b) {
/* 1831 */     return AsciiStringUtil.toUpperCase(b);
/*      */   }
/*      */   
/*      */   public static boolean isUpperCase(byte value) {
/* 1835 */     return AsciiStringUtil.isUpperCase(value);
/*      */   }
/*      */   
/*      */   public static boolean isUpperCase(char value) {
/* 1839 */     return (value >= 'A' && value <= 'Z');
/*      */   }
/*      */   
/*      */   public static byte c2b(char c) {
/* 1843 */     return (byte)((c > 'ÿ') ? 63 : c);
/*      */   }
/*      */   
/*      */   private static byte c2b0(char c) {
/* 1847 */     return (byte)c;
/*      */   }
/*      */   
/*      */   public static char b2c(byte b) {
/* 1851 */     return (char)(b & 0xFF);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\AsciiString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */