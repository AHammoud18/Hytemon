/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.InvalidMarkException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.UnsupportedCharsetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
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
/*      */ @CheckReturnValue
/*      */ public abstract class ByteString
/*      */   implements Iterable<Byte>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   static final int CONCATENATE_BY_COPY_SIZE = 128;
/*      */   static final int MIN_READ_FROM_CHUNK_SIZE = 256;
/*      */   static final int MAX_READ_FROM_CHUNK_SIZE = 8192;
/*   76 */   public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class SystemByteArrayCopier
/*      */     implements ByteArrayCopier
/*      */   {
/*      */     private SystemByteArrayCopier() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] copyFrom(byte[] bytes, int offset, int size) {
/*   98 */       byte[] copy = new byte[size];
/*   99 */       System.arraycopy(bytes, offset, copy, 0, size);
/*  100 */       return copy;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class ArraysByteArrayCopier implements ByteArrayCopier {
/*      */     private ArraysByteArrayCopier() {}
/*      */     
/*      */     public byte[] copyFrom(byte[] bytes, int offset, int size) {
/*  108 */       return Arrays.copyOfRange(bytes, offset, offset + size);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final ByteArrayCopier byteArrayCopier = Android.isOnAndroidDevice() ? new SystemByteArrayCopier() : new ArraysByteArrayCopier();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private int hash = 0;
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
/*      */   private static final int UNSIGNED_BYTE_MASK = 255;
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
/*      */   public ByteIterator iterator() {
/*  157 */     return new AbstractByteIterator() {
/*  158 */         private int position = 0;
/*  159 */         private final int limit = ByteString.this.size();
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  163 */           return (this.position < this.limit);
/*      */         }
/*      */ 
/*      */         
/*      */         public byte nextByte() {
/*  168 */           int currentPos = this.position;
/*  169 */           if (currentPos >= this.limit) {
/*  170 */             throw new NoSuchElementException();
/*      */           }
/*  172 */           this.position = currentPos + 1;
/*  173 */           return ByteString.this.internalByteAt(currentPos);
/*      */         }
/*      */       };
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
/*      */   static abstract class AbstractByteIterator
/*      */     implements ByteIterator
/*      */   {
/*      */     public final Byte next() {
/*  195 */       return Byte.valueOf(nextByte());
/*      */     }
/*      */ 
/*      */     
/*      */     public final void remove() {
/*  200 */       throw new UnsupportedOperationException();
/*      */     }
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
/*      */   public final boolean isEmpty() {
/*  217 */     return (size() == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final ByteString empty() {
/*  222 */     return EMPTY;
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
/*      */   private static int toInt(byte value) {
/*  239 */     return value & 0xFF;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int hexDigit(char c) {
/*  244 */     if (c >= '0' && c <= '9')
/*  245 */       return c - 48; 
/*  246 */     if (c >= 'A' && c <= 'F')
/*  247 */       return c - 65 + 10; 
/*  248 */     if (c >= 'a' && c <= 'f') {
/*  249 */       return c - 97 + 10;
/*      */     }
/*  251 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int extractHexDigit(String hexString, int index) {
/*  261 */     int digit = hexDigit(hexString.charAt(index));
/*  262 */     if (digit == -1) {
/*  263 */       throw new NumberFormatException("Invalid hexString " + hexString + " must only contain [0-9a-fA-F] but contained " + hexString
/*      */ 
/*      */ 
/*      */           
/*  267 */           .charAt(index) + " at index " + index);
/*      */     }
/*      */ 
/*      */     
/*  271 */     return digit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   private static final Comparator<ByteString> UNSIGNED_LEXICOGRAPHICAL_COMPARATOR = new Comparator<ByteString>()
/*      */     {
/*      */       public int compare(ByteString former, ByteString latter)
/*      */       {
/*  285 */         ByteString.ByteIterator formerBytes = former.iterator();
/*  286 */         ByteString.ByteIterator latterBytes = latter.iterator();
/*      */         
/*  288 */         while (formerBytes.hasNext() && latterBytes.hasNext()) {
/*      */           
/*  290 */           int result = Integer.compare(ByteString.toInt(formerBytes.nextByte()), ByteString.toInt(latterBytes.nextByte()));
/*  291 */           if (result != 0) {
/*  292 */             return result;
/*      */           }
/*      */         } 
/*  295 */         return Integer.compare(former.size(), latter.size());
/*      */       }
/*      */     };
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
/*      */   public static Comparator<ByteString> unsignedLexicographicalComparator() {
/*  312 */     return UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
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
/*      */   public final ByteString substring(int beginIndex) {
/*  326 */     return substring(beginIndex, size());
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
/*      */   public final boolean startsWith(ByteString prefix) {
/*  349 */     return (size() >= prefix.size() && substring(0, prefix.size()).equals(prefix));
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
/*      */   public final boolean endsWith(ByteString suffix) {
/*  361 */     return (size() >= suffix.size() && substring(size() - suffix.size()).equals(suffix));
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
/*      */   public static ByteString fromHex(@CompileTimeConstant String hexString) {
/*  374 */     if (hexString.length() % 2 != 0) {
/*  375 */       throw new NumberFormatException("Invalid hexString " + hexString + " of length " + hexString
/*  376 */           .length() + " must be even.");
/*      */     }
/*  378 */     byte[] bytes = new byte[hexString.length() / 2];
/*  379 */     for (int i = 0; i < bytes.length; i++) {
/*  380 */       int d1 = extractHexDigit(hexString, 2 * i);
/*  381 */       int d2 = extractHexDigit(hexString, 2 * i + 1);
/*  382 */       bytes[i] = (byte)(d1 << 4 | d2);
/*      */     } 
/*  384 */     return new LiteralByteString(bytes);
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
/*      */   public static ByteString copyFrom(byte[] bytes, int offset, int size) {
/*  400 */     checkRange(offset, offset + size, bytes.length);
/*  401 */     return new LiteralByteString(byteArrayCopier.copyFrom(bytes, offset, size));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteString copyFrom(byte[] bytes) {
/*  411 */     return copyFrom(bytes, 0, bytes.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteString wrap(ByteBuffer buffer) {
/*  419 */     if (buffer.hasArray()) {
/*  420 */       int offset = buffer.arrayOffset();
/*  421 */       return wrap(buffer.array(), offset + buffer.position(), buffer.remaining());
/*      */     } 
/*  423 */     return new NioByteString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteString nioByteString(ByteBuffer buffer) {
/*  429 */     return new NioByteString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteString wrap(byte[] bytes) {
/*  438 */     return new LiteralByteString(bytes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteString wrap(byte[] bytes, int offset, int length) {
/*  446 */     return new BoundedByteString(bytes, offset, length);
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
/*      */   public static ByteString copyFrom(ByteBuffer bytes, int size) {
/*  459 */     checkRange(0, size, bytes.remaining());
/*  460 */     byte[] copy = new byte[size];
/*  461 */     bytes.get(copy);
/*  462 */     return new LiteralByteString(copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteString copyFrom(ByteBuffer bytes) {
/*  472 */     return copyFrom(bytes, bytes.remaining());
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
/*      */   public static ByteString copyFrom(String text, String charsetName) throws UnsupportedEncodingException {
/*  486 */     return new LiteralByteString(text.getBytes(charsetName));
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
/*      */   public static ByteString copyFrom(String text, Charset charset) {
/*  498 */     return new LiteralByteString(text.getBytes(charset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteString copyFromUtf8(String text) {
/*  509 */     return new LiteralByteString(text.getBytes(Internal.UTF_8));
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
/*      */   public static ByteString readFrom(InputStream streamToDrain) throws IOException {
/*  533 */     return readFrom(streamToDrain, 256, 8192);
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
/*      */   public static ByteString readFrom(InputStream streamToDrain, int chunkSize) throws IOException {
/*  554 */     return readFrom(streamToDrain, chunkSize, chunkSize);
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
/*      */   public static ByteString readFrom(InputStream streamToDrain, int minChunkSize, int maxChunkSize) throws IOException {
/*  569 */     Collection<ByteString> results = new ArrayList<>();
/*      */ 
/*      */ 
/*      */     
/*  573 */     int chunkSize = minChunkSize;
/*      */     while (true) {
/*  575 */       ByteString chunk = readChunk(streamToDrain, chunkSize);
/*  576 */       if (chunk == null) {
/*      */         break;
/*      */       }
/*  579 */       results.add(chunk);
/*  580 */       chunkSize = Math.min(chunkSize * 2, maxChunkSize);
/*      */     } 
/*      */     
/*  583 */     return copyFrom(results);
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
/*      */   private static ByteString readChunk(InputStream in, int chunkSize) throws IOException {
/*  595 */     byte[] buf = new byte[chunkSize];
/*  596 */     int bytesRead = 0;
/*  597 */     while (bytesRead < chunkSize) {
/*  598 */       int count = in.read(buf, bytesRead, chunkSize - bytesRead);
/*  599 */       if (count == -1) {
/*      */         break;
/*      */       }
/*  602 */       bytesRead += count;
/*      */     } 
/*      */     
/*  605 */     if (bytesRead == 0) {
/*  606 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  610 */     return copyFrom(buf, 0, bytesRead);
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
/*      */   public final ByteString concat(ByteString other) {
/*  629 */     if (Integer.MAX_VALUE - size() < other.size()) {
/*  630 */       throw new IllegalArgumentException("ByteString would be too long: " + 
/*  631 */           size() + "+" + other.size());
/*      */     }
/*      */     
/*  634 */     return RopeByteString.concatenate(this, other);
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
/*      */   public static ByteString copyFrom(Iterable<ByteString> byteStrings) {
/*      */     int size;
/*  653 */     if (!(byteStrings instanceof Collection)) {
/*  654 */       int tempSize = 0;
/*  655 */       Iterator<ByteString> iter = byteStrings.iterator();
/*  656 */       while (iter.hasNext()) {
/*  657 */         iter.next(); tempSize++;
/*  658 */       }  size = tempSize;
/*      */     } else {
/*  660 */       size = ((Collection)byteStrings).size();
/*      */     } 
/*      */     
/*  663 */     if (size == 0) {
/*  664 */       return EMPTY;
/*      */     }
/*      */     
/*  667 */     return balancedConcat(byteStrings.iterator(), size);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ByteString balancedConcat(Iterator<ByteString> iterator, int length) {
/*      */     ByteString result;
/*  674 */     if (length < 1) {
/*  675 */       throw new IllegalArgumentException(String.format("length (%s) must be >= 1", new Object[] { Integer.valueOf(length) }));
/*      */     }
/*      */     
/*  678 */     if (length == 1) {
/*  679 */       result = iterator.next();
/*      */     } else {
/*  681 */       int halfLength = length >>> 1;
/*  682 */       ByteString left = balancedConcat(iterator, halfLength);
/*  683 */       ByteString right = balancedConcat(iterator, length - halfLength);
/*  684 */       result = left.concat(right);
/*      */     } 
/*  686 */     return result;
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
/*      */   public void copyTo(byte[] target, int offset) {
/*  703 */     copyTo(target, 0, offset, size());
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
/*      */   @Deprecated
/*      */   public final void copyTo(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
/*  719 */     checkRange(sourceOffset, sourceOffset + numberToCopy, size());
/*  720 */     checkRange(targetOffset, targetOffset + numberToCopy, target.length);
/*  721 */     if (numberToCopy > 0) {
/*  722 */       copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
/*      */     }
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
/*      */   public final byte[] toByteArray() {
/*  752 */     int size = size();
/*  753 */     if (size == 0) {
/*  754 */       return Internal.EMPTY_BYTE_ARRAY;
/*      */     }
/*  756 */     byte[] result = new byte[size];
/*  757 */     copyToInternal(result, 0, 0, size);
/*  758 */     return result;
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
/*      */   final void writeTo(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
/*  779 */     checkRange(sourceOffset, sourceOffset + numberToWrite, size());
/*  780 */     if (numberToWrite > 0) {
/*  781 */       writeToInternal(out, sourceOffset, numberToWrite);
/*      */     }
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
/*      */   public final String toString(String charsetName) throws UnsupportedEncodingException {
/*      */     try {
/*  846 */       return toString(Charset.forName(charsetName));
/*  847 */     } catch (UnsupportedCharsetException e) {
/*  848 */       UnsupportedEncodingException exception = new UnsupportedEncodingException(charsetName);
/*  849 */       exception.initCause(e);
/*  850 */       throw exception;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String toString(Charset charset) {
/*  862 */     return (size() == 0) ? "" : toStringInternal(charset);
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
/*      */   public final String toStringUtf8() {
/*  882 */     return toString(Internal.UTF_8);
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
/*      */   static abstract class LeafByteString
/*      */     extends ByteString
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
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
/*      */     protected final int getTreeDepth() {
/*  941 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final boolean isBalanced() {
/*  946 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     void writeToReverse(ByteOutput byteOutput) throws IOException {
/*  951 */       writeTo(byteOutput);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LeafByteString() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean equalsRange(ByteString param1ByteString, int param1Int1, int param1Int2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int hashCode() {
/*  975 */     int h = this.hash;
/*      */     
/*  977 */     if (h == 0) {
/*  978 */       int size = size();
/*  979 */       h = partialHash(size, 0, size);
/*  980 */       if (h == 0) {
/*  981 */         h = 1;
/*      */       }
/*  983 */       this.hash = h;
/*      */     } 
/*  985 */     return h;
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
/*      */   public static Output newOutput(int initialCapacity) {
/* 1030 */     return new Output(initialCapacity);
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
/*      */   public static Output newOutput() {
/* 1044 */     return new Output(128);
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
/*      */   public static final class Output
/*      */     extends OutputStream
/*      */   {
/* 1058 */     private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */ 
/*      */     
/*      */     private final int initialCapacity;
/*      */ 
/*      */     
/*      */     private final ArrayList<ByteString> flushedBuffers;
/*      */ 
/*      */     
/*      */     private int flushedBuffersTotalBytes;
/*      */ 
/*      */     
/*      */     private byte[] buffer;
/*      */     
/*      */     private int bufferPos;
/*      */ 
/*      */     
/*      */     Output(int initialCapacity) {
/* 1076 */       if (initialCapacity < 0) {
/* 1077 */         throw new IllegalArgumentException("Buffer size < 0");
/*      */       }
/* 1079 */       this.initialCapacity = initialCapacity;
/* 1080 */       this.flushedBuffers = new ArrayList<>();
/* 1081 */       this.buffer = new byte[initialCapacity];
/*      */     }
/*      */ 
/*      */     
/*      */     public synchronized void write(int b) {
/* 1086 */       if (this.bufferPos == this.buffer.length) {
/* 1087 */         flushFullBuffer(1);
/*      */       }
/* 1089 */       this.buffer[this.bufferPos++] = (byte)b;
/*      */     }
/*      */ 
/*      */     
/*      */     public synchronized void write(byte[] b, int offset, int length) {
/* 1094 */       if (length <= this.buffer.length - this.bufferPos) {
/*      */         
/* 1096 */         System.arraycopy(b, offset, this.buffer, this.bufferPos, length);
/* 1097 */         this.bufferPos += length;
/*      */       } else {
/*      */         
/* 1100 */         int copySize = this.buffer.length - this.bufferPos;
/* 1101 */         System.arraycopy(b, offset, this.buffer, this.bufferPos, copySize);
/* 1102 */         offset += copySize;
/* 1103 */         length -= copySize;
/*      */ 
/*      */         
/* 1106 */         flushFullBuffer(length);
/* 1107 */         System.arraycopy(b, offset, this.buffer, 0, length);
/* 1108 */         this.bufferPos = length;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized ByteString toByteString() {
/* 1121 */       flushLastBuffer();
/* 1122 */       return ByteString.copyFrom(this.flushedBuffers);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeTo(OutputStream out) throws IOException {
/*      */       ByteString[] cachedFlushBuffers;
/*      */       byte[] cachedBuffer;
/*      */       int cachedBufferPos;
/* 1136 */       synchronized (this) {
/*      */ 
/*      */         
/* 1139 */         cachedFlushBuffers = this.flushedBuffers.<ByteString>toArray(new ByteString[0]);
/* 1140 */         cachedBuffer = this.buffer;
/* 1141 */         cachedBufferPos = this.bufferPos;
/*      */       } 
/* 1143 */       for (ByteString byteString : cachedFlushBuffers) {
/* 1144 */         byteString.writeTo(out);
/*      */       }
/*      */       
/* 1147 */       out.write(Arrays.copyOf(cachedBuffer, cachedBufferPos));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized int size() {
/* 1156 */       return this.flushedBuffersTotalBytes + this.bufferPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void reset() {
/* 1164 */       this.flushedBuffers.clear();
/* 1165 */       this.flushedBuffersTotalBytes = 0;
/* 1166 */       this.bufferPos = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1171 */       return String.format("<ByteString.Output@%s size=%d>", new Object[] {
/*      */             
/* 1173 */             Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void flushFullBuffer(int minSize) {
/* 1181 */       this.flushedBuffers.add(new ByteString.LiteralByteString(this.buffer));
/* 1182 */       this.flushedBuffersTotalBytes += this.buffer.length;
/*      */ 
/*      */ 
/*      */       
/* 1186 */       int newSize = Math.max(this.initialCapacity, Math.max(minSize, this.flushedBuffersTotalBytes >>> 1));
/* 1187 */       this.buffer = new byte[newSize];
/* 1188 */       this.bufferPos = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void flushLastBuffer() {
/* 1196 */       if (this.bufferPos < this.buffer.length) {
/* 1197 */         if (this.bufferPos > 0) {
/* 1198 */           byte[] bufferCopy = Arrays.copyOf(this.buffer, this.bufferPos);
/* 1199 */           this.flushedBuffers.add(new ByteString.LiteralByteString(bufferCopy));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1204 */         this.flushedBuffers.add(new ByteString.LiteralByteString(this.buffer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1210 */         this.buffer = EMPTY_BYTE_ARRAY;
/*      */       } 
/* 1212 */       this.flushedBuffersTotalBytes += this.bufferPos;
/* 1213 */       this.bufferPos = 0;
/*      */     }
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
/*      */   static CodedBuilder newCodedBuilder(int size) {
/* 1230 */     return new CodedBuilder(size);
/*      */   }
/*      */   
/*      */   static final class CodedBuilder
/*      */   {
/*      */     private final CodedOutputStream output;
/*      */     private final byte[] buffer;
/*      */     
/*      */     private CodedBuilder(int size) {
/* 1239 */       this.buffer = new byte[size];
/* 1240 */       this.output = CodedOutputStream.newInstance(this.buffer);
/*      */     }
/*      */     
/*      */     public ByteString build() {
/* 1244 */       this.output.checkNoSpaceLeft();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1249 */       return new ByteString.LiteralByteString(this.buffer);
/*      */     }
/*      */     
/*      */     public CodedOutputStream getCodedOutput() {
/* 1253 */       return this.output;
/*      */     }
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
/*      */   protected final int peekCachedHashCode() {
/* 1283 */     return this.hash;
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
/*      */   static void checkIndex(int index, int size) {
/* 1306 */     if ((index | size - index + 1) < 0) {
/* 1307 */       if (index < 0) {
/* 1308 */         throw new ArrayIndexOutOfBoundsException("Index < 0: " + index);
/*      */       }
/* 1310 */       throw new ArrayIndexOutOfBoundsException("Index > length: " + index + ", " + size);
/*      */     } 
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
/*      */   @CanIgnoreReturnValue
/*      */   static int checkRange(int startIndex, int endIndex, int size) {
/* 1325 */     int length = endIndex - startIndex;
/* 1326 */     if ((startIndex | endIndex | length | size - endIndex) < 0) {
/* 1327 */       if (startIndex < 0) {
/* 1328 */         throw new IndexOutOfBoundsException("Beginning index: " + startIndex + " < 0");
/*      */       }
/* 1330 */       if (endIndex < startIndex) {
/* 1331 */         throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + startIndex + ", " + endIndex);
/*      */       }
/*      */ 
/*      */       
/* 1335 */       throw new IndexOutOfBoundsException("End index: " + endIndex + " >= " + size);
/*      */     } 
/* 1337 */     return length;
/*      */   }
/*      */ 
/*      */   
/*      */   public final String toString() {
/* 1342 */     return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", new Object[] {
/*      */ 
/*      */           
/* 1345 */           Integer.toHexString(System.identityHashCode(this)), 
/* 1346 */           Integer.valueOf(size()), 
/* 1347 */           truncateAndEscapeForDisplay() });
/*      */   } public abstract byte byteAt(int paramInt); abstract byte internalByteAt(int paramInt); public abstract int size(); public abstract ByteString substring(int paramInt1, int paramInt2); protected abstract void copyToInternal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3); public abstract void copyTo(ByteBuffer paramByteBuffer); public abstract void writeTo(OutputStream paramOutputStream) throws IOException; abstract void writeToInternal(OutputStream paramOutputStream, int paramInt1, int paramInt2) throws IOException; abstract void writeTo(ByteOutput paramByteOutput) throws IOException; abstract void writeToReverse(ByteOutput paramByteOutput) throws IOException;
/*      */   public abstract ByteBuffer asReadOnlyByteBuffer();
/*      */   private String truncateAndEscapeForDisplay() {
/* 1351 */     int limit = 50;
/*      */     
/* 1353 */     return (size() <= 50) ? TextFormatEscaper.escapeBytes(this) : (TextFormatEscaper.escapeBytes(substring(0, 47)) + "...");
/*      */   }
/*      */   
/*      */   public abstract List<ByteBuffer> asReadOnlyByteBufferList();
/*      */   
/*      */   protected abstract String toStringInternal(Charset paramCharset);
/*      */   
/*      */   public abstract boolean isValidUtf8();
/*      */   
/*      */   protected abstract int partialIsValidUtf8(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   public abstract boolean equals(Object paramObject);
/*      */   
/*      */   public abstract InputStream newInput();
/*      */   
/*      */   public abstract CodedInputStream newCodedInput();
/*      */   
/*      */   protected abstract int getTreeDepth();
/*      */   
/*      */   protected abstract boolean isBalanced();
/*      */   
/*      */   protected abstract int partialHash(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   private static class LiteralByteString extends LeafByteString { LiteralByteString(byte[] bytes) {
/* 1377 */       if (bytes == null) {
/* 1378 */         throw new NullPointerException();
/*      */       }
/* 1380 */       this.bytes = bytes;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 1L;
/*      */     protected final byte[] bytes;
/*      */     
/*      */     public byte byteAt(int index) {
/* 1388 */       return this.bytes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     byte internalByteAt(int index) {
/* 1393 */       return this.bytes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1398 */       return this.bytes.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final ByteString substring(int beginIndex, int endIndex) {
/* 1406 */       int length = checkRange(beginIndex, endIndex, size());
/*      */       
/* 1408 */       if (length == 0) {
/* 1409 */         return ByteString.EMPTY;
/*      */       }
/*      */       
/* 1412 */       return new ByteString.BoundedByteString(this.bytes, getOffsetIntoBytes() + beginIndex, length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
/* 1424 */       System.arraycopy(this.bytes, sourceOffset, target, targetOffset, numberToCopy);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void copyTo(ByteBuffer target) {
/* 1429 */       target.put(this.bytes, getOffsetIntoBytes(), size());
/*      */     }
/*      */ 
/*      */     
/*      */     public final ByteBuffer asReadOnlyByteBuffer() {
/* 1434 */       return ByteBuffer.wrap(this.bytes, getOffsetIntoBytes(), size()).asReadOnlyBuffer();
/*      */     }
/*      */ 
/*      */     
/*      */     public final List<ByteBuffer> asReadOnlyByteBufferList() {
/* 1439 */       return Collections.singletonList(asReadOnlyByteBuffer());
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeTo(OutputStream outputStream) throws IOException {
/* 1444 */       outputStream.write(toByteArray());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void writeToInternal(OutputStream outputStream, int sourceOffset, int numberToWrite) throws IOException {
/* 1450 */       outputStream.write(this.bytes, getOffsetIntoBytes() + sourceOffset, numberToWrite);
/*      */     }
/*      */ 
/*      */     
/*      */     final void writeTo(ByteOutput output) throws IOException {
/* 1455 */       output.writeLazy(this.bytes, getOffsetIntoBytes(), size());
/*      */     }
/*      */ 
/*      */     
/*      */     protected final String toStringInternal(Charset charset) {
/* 1460 */       return new String(this.bytes, getOffsetIntoBytes(), size(), charset);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean isValidUtf8() {
/* 1468 */       int offset = getOffsetIntoBytes();
/* 1469 */       return Utf8.isValidUtf8(this.bytes, offset, offset + size());
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int partialIsValidUtf8(int state, int offset, int length) {
/* 1474 */       int index = getOffsetIntoBytes() + offset;
/* 1475 */       return Utf8.partialIsValidUtf8(state, this.bytes, index, index + length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean equals(Object other) {
/* 1484 */       if (other == this) {
/* 1485 */         return true;
/*      */       }
/* 1487 */       if (!(other instanceof ByteString)) {
/* 1488 */         return false;
/*      */       }
/*      */       
/* 1491 */       if (size() != ((ByteString)other).size()) {
/* 1492 */         return false;
/*      */       }
/* 1494 */       if (size() == 0) {
/* 1495 */         return true;
/*      */       }
/*      */       
/* 1498 */       if (other instanceof LiteralByteString) {
/* 1499 */         LiteralByteString otherAsLiteral = (LiteralByteString)other;
/*      */ 
/*      */         
/* 1502 */         int thisHash = peekCachedHashCode();
/* 1503 */         int thatHash = otherAsLiteral.peekCachedHashCode();
/* 1504 */         if (thisHash != 0 && thatHash != 0 && thisHash != thatHash) {
/* 1505 */           return false;
/*      */         }
/*      */         
/* 1508 */         return equalsRange((LiteralByteString)other, 0, size());
/*      */       } 
/*      */       
/* 1511 */       return other.equals(this);
/*      */     }
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
/*      */     final boolean equalsRange(ByteString other, int offset, int length) {
/* 1526 */       if (length > other.size()) {
/* 1527 */         throw new IllegalArgumentException("Length too large: " + length + size());
/*      */       }
/* 1529 */       if (offset + length > other.size()) {
/* 1530 */         throw new IllegalArgumentException("Ran off end of other: " + offset + ", " + length + ", " + other
/* 1531 */             .size());
/*      */       }
/*      */       
/* 1534 */       if (other instanceof LiteralByteString) {
/* 1535 */         LiteralByteString lbsOther = (LiteralByteString)other;
/* 1536 */         byte[] thisBytes = this.bytes;
/* 1537 */         byte[] otherBytes = lbsOther.bytes;
/* 1538 */         int thisLimit = getOffsetIntoBytes() + length;
/* 1539 */         int thisIndex = getOffsetIntoBytes();
/* 1540 */         int otherIndex = lbsOther.getOffsetIntoBytes() + offset;
/* 1541 */         for (; thisIndex < thisLimit; 
/* 1542 */           thisIndex++, otherIndex++) {
/* 1543 */           if (thisBytes[thisIndex] != otherBytes[otherIndex]) {
/* 1544 */             return false;
/*      */           }
/*      */         } 
/* 1547 */         return true;
/*      */       } 
/*      */       
/* 1550 */       return other.substring(offset, offset + length).equals(substring(0, length));
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int partialHash(int h, int offset, int length) {
/* 1555 */       return Internal.partialHash(h, this.bytes, getOffsetIntoBytes() + offset, length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final InputStream newInput() {
/* 1563 */       return new ByteArrayInputStream(this.bytes, getOffsetIntoBytes(), size());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final CodedInputStream newCodedInput() {
/* 1570 */       return CodedInputStream.newInstance(this.bytes, 
/* 1571 */           getOffsetIntoBytes(), size(), true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getOffsetIntoBytes() {
/* 1583 */       return 0;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class BoundedByteString
/*      */     extends LiteralByteString
/*      */   {
/*      */     private final int bytesOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int bytesLength;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BoundedByteString(byte[] bytes, int offset, int length) {
/* 1613 */       super(bytes);
/* 1614 */       checkRange(offset, offset + length, bytes.length);
/*      */       
/* 1616 */       this.bytesOffset = offset;
/* 1617 */       this.bytesLength = length;
/*      */     }
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
/*      */     public byte byteAt(int index) {
/* 1633 */       checkIndex(index, size());
/* 1634 */       return this.bytes[this.bytesOffset + index];
/*      */     }
/*      */ 
/*      */     
/*      */     byte internalByteAt(int index) {
/* 1639 */       return this.bytes[this.bytesOffset + index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1644 */       return this.bytesLength;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int getOffsetIntoBytes() {
/* 1649 */       return this.bytesOffset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
/* 1658 */       System.arraycopy(this.bytes, 
/* 1659 */           getOffsetIntoBytes() + sourceOffset, target, targetOffset, numberToCopy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object writeReplace() {
/* 1668 */       return ByteString.wrap(toByteArray());
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException {
/* 1672 */       throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class NioByteString
/*      */     extends LeafByteString
/*      */   {
/*      */     private final ByteBuffer buffer;
/*      */ 
/*      */     
/*      */     NioByteString(ByteBuffer buffer) {
/* 1684 */       Internal.checkNotNull(buffer, "buffer");
/*      */ 
/*      */       
/* 1687 */       this.buffer = buffer.slice().order(ByteOrder.nativeOrder());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object writeReplace() {
/* 1695 */       return ByteString.copyFrom(this.buffer.slice());
/*      */     }
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException {
/* 1700 */       throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte byteAt(int index) {
/*      */       try {
/* 1708 */         return this.buffer.get(index);
/* 1709 */       } catch (ArrayIndexOutOfBoundsException e) {
/* 1710 */         throw e;
/* 1711 */       } catch (IndexOutOfBoundsException e) {
/* 1712 */         throw new ArrayIndexOutOfBoundsException(e.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte internalByteAt(int index) {
/* 1721 */       return byteAt(index);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1726 */       return this.buffer.remaining();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString substring(int beginIndex, int endIndex) {
/*      */       try {
/* 1732 */         ByteBuffer slice = slice(beginIndex, endIndex);
/* 1733 */         return new NioByteString(slice);
/* 1734 */       } catch (ArrayIndexOutOfBoundsException e) {
/* 1735 */         throw e;
/* 1736 */       } catch (IndexOutOfBoundsException e) {
/* 1737 */         throw new ArrayIndexOutOfBoundsException(e.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
/* 1744 */       ByteBuffer slice = this.buffer.slice();
/* 1745 */       Java8Compatibility.position(slice, sourceOffset);
/* 1746 */       slice.get(target, targetOffset, numberToCopy);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyTo(ByteBuffer target) {
/* 1751 */       target.put(this.buffer.slice());
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTo(OutputStream out) throws IOException {
/* 1756 */       out.write(toByteArray());
/*      */     }
/*      */ 
/*      */     
/*      */     boolean equalsRange(ByteString other, int offset, int length) {
/* 1761 */       return substring(0, length).equals(other.substring(offset, offset + length));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
/* 1766 */       if (this.buffer.hasArray()) {
/*      */ 
/*      */         
/* 1769 */         int bufferOffset = this.buffer.arrayOffset() + this.buffer.position() + sourceOffset;
/* 1770 */         out.write(this.buffer.array(), bufferOffset, numberToWrite);
/*      */         
/*      */         return;
/*      */       } 
/* 1774 */       ByteBufferWriter.write(slice(sourceOffset, sourceOffset + numberToWrite), out);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTo(ByteOutput output) throws IOException {
/* 1779 */       output.writeLazy(this.buffer.slice());
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer asReadOnlyByteBuffer() {
/* 1784 */       return this.buffer.asReadOnlyBuffer();
/*      */     }
/*      */ 
/*      */     
/*      */     public List<ByteBuffer> asReadOnlyByteBufferList() {
/* 1789 */       return Collections.singletonList(asReadOnlyByteBuffer());
/*      */     }
/*      */ 
/*      */     
/*      */     protected String toStringInternal(Charset charset) {
/*      */       byte[] bytes;
/*      */       int offset;
/*      */       int length;
/* 1797 */       if (this.buffer.hasArray()) {
/* 1798 */         bytes = this.buffer.array();
/* 1799 */         offset = this.buffer.arrayOffset() + this.buffer.position();
/* 1800 */         length = this.buffer.remaining();
/*      */       } else {
/*      */         
/* 1803 */         bytes = toByteArray();
/* 1804 */         offset = 0;
/* 1805 */         length = bytes.length;
/*      */       } 
/* 1807 */       return new String(bytes, offset, length, charset);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isValidUtf8() {
/* 1812 */       return Utf8.isValidUtf8(this.buffer);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int partialIsValidUtf8(int state, int offset, int length) {
/* 1817 */       return Utf8.partialIsValidUtf8(state, this.buffer, offset, offset + length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object other) {
/* 1823 */       if (other == this) {
/* 1824 */         return true;
/*      */       }
/* 1826 */       if (!(other instanceof ByteString)) {
/* 1827 */         return false;
/*      */       }
/* 1829 */       ByteString otherString = (ByteString)other;
/* 1830 */       if (size() != otherString.size()) {
/* 1831 */         return false;
/*      */       }
/* 1833 */       if (size() == 0) {
/* 1834 */         return true;
/*      */       }
/* 1836 */       if (other instanceof NioByteString) {
/* 1837 */         return this.buffer.equals(((NioByteString)other).buffer);
/*      */       }
/* 1839 */       if (other instanceof RopeByteString) {
/* 1840 */         return other.equals(this);
/*      */       }
/* 1842 */       return this.buffer.equals(otherString.asReadOnlyByteBuffer());
/*      */     }
/*      */ 
/*      */     
/*      */     protected int partialHash(int h, int offset, int length) {
/* 1847 */       for (int i = offset; i < offset + length; i++) {
/* 1848 */         h = h * 31 + this.buffer.get(i);
/*      */       }
/* 1850 */       return h;
/*      */     }
/*      */ 
/*      */     
/*      */     public InputStream newInput() {
/* 1855 */       return new InputStream() {
/* 1856 */           private final ByteBuffer buf = ByteString.NioByteString.this.buffer.slice();
/*      */ 
/*      */           
/*      */           public void mark(int readlimit) {
/* 1860 */             Java8Compatibility.mark(this.buf);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean markSupported() {
/* 1865 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public void reset() throws IOException {
/*      */             try {
/* 1871 */               Java8Compatibility.reset(this.buf);
/* 1872 */             } catch (InvalidMarkException e) {
/* 1873 */               throw new IOException(e);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int available() throws IOException {
/* 1879 */             return this.buf.remaining();
/*      */           }
/*      */ 
/*      */           
/*      */           public int read() throws IOException {
/* 1884 */             if (!this.buf.hasRemaining()) {
/* 1885 */               return -1;
/*      */             }
/* 1887 */             return this.buf.get() & 0xFF;
/*      */           }
/*      */ 
/*      */           
/*      */           public int read(byte[] bytes, int off, int len) throws IOException {
/* 1892 */             if (!this.buf.hasRemaining()) {
/* 1893 */               return -1;
/*      */             }
/*      */             
/* 1896 */             len = Math.min(len, this.buf.remaining());
/* 1897 */             this.buf.get(bytes, off, len);
/* 1898 */             return len;
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     public CodedInputStream newCodedInput() {
/* 1905 */       return CodedInputStream.newInstance(this.buffer, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ByteBuffer slice(int beginIndex, int endIndex) {
/* 1916 */       if (beginIndex < this.buffer.position() || endIndex > this.buffer.limit() || beginIndex > endIndex) {
/* 1917 */         throw new IllegalArgumentException(
/* 1918 */             String.format("Invalid indices [%d, %d]", new Object[] { Integer.valueOf(beginIndex), Integer.valueOf(endIndex) }));
/*      */       }
/*      */       
/* 1921 */       ByteBuffer slice = this.buffer.slice();
/* 1922 */       Java8Compatibility.position(slice, beginIndex - this.buffer.position());
/* 1923 */       Java8Compatibility.limit(slice, endIndex - this.buffer.position());
/* 1924 */       return slice;
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface ByteArrayCopier {
/*      */     byte[] copyFrom(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2);
/*      */   }
/*      */   
/*      */   public static interface ByteIterator extends Iterator<Byte> {
/*      */     byte nextByte();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ByteString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */