/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class Utf8
/*      */ {
/*   62 */   private static final Processor processor = (UnsafeProcessor.isAvailable() && !Android.isOnAndroidDevice()) ? 
/*   63 */     new UnsafeProcessor() : 
/*   64 */     new SafeProcessor();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long ASCII_MASK_LONG = -9187201950435737472L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MAX_BYTES_PER_CHAR = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int COMPLETE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MALFORMED = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isValidUtf8(byte[] bytes) {
/*  125 */     return processor.isValidUtf8(bytes, 0, bytes.length);
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
/*      */   static boolean isValidUtf8(byte[] bytes, int index, int limit) {
/*  137 */     return processor.isValidUtf8(bytes, index, limit);
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
/*      */   static int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
/*  154 */     return processor.partialIsValidUtf8(state, bytes, index, limit);
/*      */   }
/*      */   
/*      */   private static int incompleteStateFor(int byte1) {
/*  158 */     return (byte1 > -12) ? -1 : byte1;
/*      */   }
/*      */   
/*      */   private static int incompleteStateFor(int byte1, int byte2) {
/*  162 */     return (byte1 > -12 || byte2 > -65) ? -1 : (byte1 ^ byte2 << 8);
/*      */   }
/*      */   
/*      */   private static int incompleteStateFor(int byte1, int byte2, int byte3) {
/*  166 */     return (byte1 > -12 || byte2 > -65 || byte3 > -65) ? 
/*  167 */       -1 : (
/*  168 */       byte1 ^ byte2 << 8 ^ byte3 << 16);
/*      */   }
/*      */   
/*      */   private static int incompleteStateFor(byte[] bytes, int index, int limit) {
/*  172 */     int byte1 = bytes[index - 1];
/*  173 */     switch (limit - index) {
/*      */       case 0:
/*  175 */         return incompleteStateFor(byte1);
/*      */       case 1:
/*  177 */         return incompleteStateFor(byte1, bytes[index]);
/*      */       case 2:
/*  179 */         return incompleteStateFor(byte1, bytes[index], bytes[index + 1]);
/*      */     } 
/*  181 */     throw new AssertionError();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int incompleteStateFor(ByteBuffer buffer, int byte1, int index, int remaining) {
/*  187 */     switch (remaining) {
/*      */       case 0:
/*  189 */         return incompleteStateFor(byte1);
/*      */       case 1:
/*  191 */         return incompleteStateFor(byte1, buffer.get(index));
/*      */       case 2:
/*  193 */         return incompleteStateFor(byte1, buffer.get(index), buffer.get(index + 1));
/*      */     } 
/*  195 */     throw new AssertionError();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnpairedSurrogateException
/*      */     extends IllegalArgumentException
/*      */   {
/*      */     UnpairedSurrogateException(int index, int length) {
/*  205 */       super("Unpaired surrogate at index " + index + " of " + length);
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
/*      */   static int encodedLength(String string) {
/*  219 */     int utf16Length = string.length();
/*  220 */     int utf8Length = utf16Length;
/*  221 */     int i = 0;
/*      */ 
/*      */     
/*  224 */     while (i < utf16Length && string.charAt(i) < '') {
/*  225 */       i++;
/*      */     }
/*      */ 
/*      */     
/*  229 */     for (; i < utf16Length; i++) {
/*  230 */       char c = string.charAt(i);
/*  231 */       if (c < 'ࠀ') {
/*  232 */         utf8Length += 127 - c >>> 31;
/*      */       } else {
/*  234 */         utf8Length += encodedLengthGeneral(string, i);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  239 */     if (utf8Length < utf16Length)
/*      */     {
/*  241 */       throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (utf8Length + 4294967296L));
/*      */     }
/*      */     
/*  244 */     return utf8Length;
/*      */   }
/*      */   
/*      */   private static int encodedLengthGeneral(String string, int start) {
/*  248 */     int utf16Length = string.length();
/*  249 */     int utf8Length = 0;
/*  250 */     for (int i = start; i < utf16Length; i++) {
/*  251 */       char c = string.charAt(i);
/*  252 */       if (c < 'ࠀ') {
/*  253 */         utf8Length += 127 - c >>> 31;
/*      */       } else {
/*  255 */         utf8Length += 2;
/*      */         
/*  257 */         if ('?' <= c && c <= '?') {
/*      */           
/*  259 */           int cp = Character.codePointAt(string, i);
/*  260 */           if (cp < 65536) {
/*  261 */             throw new UnpairedSurrogateException(i, utf16Length);
/*      */           }
/*  263 */           i++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  267 */     return utf8Length;
/*      */   }
/*      */   
/*      */   static int encode(String in, byte[] out, int offset, int length) {
/*  271 */     return processor.encodeUtf8(in, out, offset, length);
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
/*      */   static boolean isValidUtf8(ByteBuffer buffer) {
/*  285 */     return processor.isValidUtf8(buffer, buffer.position(), buffer.remaining());
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
/*      */   static int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
/*  298 */     return processor.partialIsValidUtf8(state, buffer, index, limit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String decodeUtf8(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
/*  308 */     return processor.decodeUtf8(buffer, index, size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
/*  318 */     return processor.decodeUtf8(bytes, index, size);
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
/*      */   static void encodeUtf8(String in, ByteBuffer out) {
/*  332 */     processor.encodeUtf8(in, out);
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
/*      */   private static int estimateConsecutiveAscii(ByteBuffer buffer, int index, int limit) {
/*  347 */     int i = index;
/*  348 */     int lim = limit - 7;
/*      */ 
/*      */ 
/*      */     
/*  352 */     for (; i < lim && (buffer.getLong(i) & 0x8080808080808080L) == 0L; i += 8);
/*  353 */     return i - index;
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
/*      */   static abstract class Processor
/*      */   {
/*      */     final boolean isValidUtf8(byte[] bytes, int index, int limit) {
/*  368 */       return (partialIsValidUtf8(0, bytes, index, limit) == 0);
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
/*      */     abstract int partialIsValidUtf8(int param1Int1, byte[] param1ArrayOfbyte, int param1Int2, int param1Int3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isValidUtf8(ByteBuffer buffer, int index, int limit) {
/*  395 */       return (partialIsValidUtf8(0, buffer, index, limit) == 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
/*  406 */       if (buffer.hasArray()) {
/*  407 */         int offset = buffer.arrayOffset();
/*  408 */         return partialIsValidUtf8(state, buffer.array(), offset + index, offset + limit);
/*  409 */       }  if (buffer.isDirect()) {
/*  410 */         return partialIsValidUtf8Direct(state, buffer, index, limit);
/*      */       }
/*  412 */       return partialIsValidUtf8Default(state, buffer, index, limit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int partialIsValidUtf8Direct(int param1Int1, ByteBuffer param1ByteBuffer, int param1Int2, int param1Int3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int partialIsValidUtf8Default(int state, ByteBuffer buffer, int index, int limit) {
/*  426 */       if (state != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  434 */         if (index >= limit) {
/*  435 */           return state;
/*      */         }
/*      */         
/*  438 */         byte byte1 = (byte)state;
/*      */         
/*  440 */         if (byte1 < -32) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  445 */           if (byte1 < -62 || buffer
/*      */             
/*  447 */             .get(index++) > -65) {
/*  448 */             return -1;
/*      */           }
/*  450 */         } else if (byte1 < -16) {
/*      */ 
/*      */ 
/*      */           
/*  454 */           byte byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*  455 */           if (byte2 == 0) {
/*  456 */             byte2 = buffer.get(index++);
/*  457 */             if (index >= limit) {
/*  458 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } 
/*  461 */           if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || buffer
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  467 */             .get(index++) > -65) {
/*  468 */             return -1;
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  474 */           byte byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*  475 */           byte byte3 = 0;
/*  476 */           if (byte2 == 0) {
/*  477 */             byte2 = buffer.get(index++);
/*  478 */             if (index >= limit) {
/*  479 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } else {
/*  482 */             byte3 = (byte)(state >> 16);
/*      */           } 
/*  484 */           if (byte3 == 0) {
/*  485 */             byte3 = buffer.get(index++);
/*  486 */             if (index >= limit) {
/*  487 */               return Utf8.incompleteStateFor(byte1, byte2, byte3);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  495 */           if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || byte3 > -65 || buffer
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  504 */             .get(index++) > -65) {
/*  505 */             return -1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  511 */       return partialIsValidUtf8(buffer, index, limit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int partialIsValidUtf8(ByteBuffer buffer, int index, int limit) {
/*  519 */       index += Utf8.estimateConsecutiveAscii(buffer, index, limit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true)
/*  527 */       { if (index >= limit)
/*  528 */           return 0; 
/*      */         int byte1;
/*  530 */         if ((byte1 = buffer.get(index++)) < 0)
/*      */         
/*      */         { 
/*  533 */           if (byte1 < -32) {
/*      */             
/*  535 */             if (index >= limit)
/*      */             {
/*  537 */               return byte1;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  542 */             if (byte1 < -62 || buffer.get(index) > -65) {
/*  543 */               return -1;
/*      */             }
/*  545 */             index++; continue;
/*  546 */           }  if (byte1 < -16) {
/*      */             
/*  548 */             if (index >= limit - 1)
/*      */             {
/*  550 */               return Utf8.incompleteStateFor(buffer, byte1, index, limit - index);
/*      */             }
/*      */             
/*  553 */             byte b = buffer.get(index++);
/*  554 */             if (b > -65 || (byte1 == -32 && b < -96) || (byte1 == -19 && b >= -96) || buffer
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  560 */               .get(index) > -65) {
/*  561 */               return -1;
/*      */             }
/*  563 */             index++;
/*      */             continue;
/*      */           } 
/*  566 */           if (index >= limit - 2)
/*      */           {
/*  568 */             return Utf8.incompleteStateFor(buffer, byte1, index, limit - index);
/*      */           }
/*      */ 
/*      */           
/*  572 */           int byte2 = buffer.get(index++);
/*  573 */           if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || buffer
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  580 */             .get(index++) > -65 || buffer
/*      */             
/*  582 */             .get(index++) > -65)
/*  583 */             break;  }  }  return -1;
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
/*      */     abstract String decodeUtf8(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws InvalidProtocolBufferException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final String decodeUtf8(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
/*  604 */       if (buffer.hasArray()) {
/*  605 */         int offset = buffer.arrayOffset();
/*  606 */         return decodeUtf8(buffer.array(), offset + index, size);
/*  607 */       }  if (buffer.isDirect()) {
/*  608 */         return decodeUtf8Direct(buffer, index, size);
/*      */       }
/*  610 */       return decodeUtf8Default(buffer, index, size);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract String decodeUtf8Direct(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2) throws InvalidProtocolBufferException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final String decodeUtf8Default(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
/*  624 */       if ((index | size | buffer.limit() - index - size) < 0) {
/*  625 */         throw new ArrayIndexOutOfBoundsException(
/*  626 */             String.format("buffer limit=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(size) }));
/*      */       }
/*      */       
/*  629 */       int offset = index;
/*  630 */       int limit = offset + size;
/*      */ 
/*      */ 
/*      */       
/*  634 */       char[] resultArr = new char[size];
/*  635 */       int resultPos = 0;
/*      */ 
/*      */ 
/*      */       
/*  639 */       while (offset < limit) {
/*  640 */         byte b = buffer.get(offset);
/*  641 */         if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */           break;
/*      */         }
/*  644 */         offset++;
/*  645 */         Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */       } 
/*      */       
/*  648 */       while (offset < limit) {
/*  649 */         byte byte1 = buffer.get(offset++);
/*  650 */         if (Utf8.DecodeUtil.isOneByte(byte1)) {
/*  651 */           Utf8.DecodeUtil.handleOneByte(byte1, resultArr, resultPos++);
/*      */ 
/*      */           
/*  654 */           while (offset < limit) {
/*  655 */             byte b = buffer.get(offset);
/*  656 */             if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */               break;
/*      */             }
/*  659 */             offset++;
/*  660 */             Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */           }  continue;
/*  662 */         }  if (Utf8.DecodeUtil.isTwoBytes(byte1)) {
/*  663 */           if (offset >= limit) {
/*  664 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/*  666 */           Utf8.DecodeUtil.handleTwoBytes(byte1, buffer
/*  667 */               .get(offset++), resultArr, resultPos++); continue;
/*  668 */         }  if (Utf8.DecodeUtil.isThreeBytes(byte1)) {
/*  669 */           if (offset >= limit - 1) {
/*  670 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/*  672 */           Utf8.DecodeUtil.handleThreeBytes(byte1, buffer
/*      */               
/*  674 */               .get(offset++), buffer
/*  675 */               .get(offset++), resultArr, resultPos++);
/*      */           
/*      */           continue;
/*      */         } 
/*  679 */         if (offset >= limit - 2) {
/*  680 */           throw InvalidProtocolBufferException.invalidUtf8();
/*      */         }
/*  682 */         Utf8.DecodeUtil.handleFourBytes(byte1, buffer
/*      */             
/*  684 */             .get(offset++), buffer
/*  685 */             .get(offset++), buffer
/*  686 */             .get(offset++), resultArr, resultPos++);
/*      */ 
/*      */ 
/*      */         
/*  690 */         resultPos++;
/*      */       } 
/*      */ 
/*      */       
/*  694 */       return new String(resultArr, 0, resultPos);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int encodeUtf8(String param1String, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void encodeUtf8(String in, ByteBuffer out) {
/*  747 */       if (out.hasArray()) {
/*  748 */         int offset = out.arrayOffset();
/*  749 */         int endIndex = Utf8.encode(in, out.array(), offset + out.position(), out.remaining());
/*  750 */         Java8Compatibility.position(out, endIndex - offset);
/*  751 */       } else if (out.isDirect()) {
/*  752 */         encodeUtf8Direct(in, out);
/*      */       } else {
/*  754 */         encodeUtf8Default(in, out);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void encodeUtf8Direct(String param1String, ByteBuffer param1ByteBuffer);
/*      */ 
/*      */ 
/*      */     
/*      */     final void encodeUtf8Default(String in, ByteBuffer out) {
/*  766 */       int inLength = in.length();
/*  767 */       int outIx = out.position();
/*  768 */       int inIx = 0;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*      */         char c;
/*      */ 
/*      */         
/*  776 */         for (; inIx < inLength && (c = in.charAt(inIx)) < ''; inIx++) {
/*  777 */           out.put(outIx + inIx, (byte)c);
/*      */         }
/*  779 */         if (inIx == inLength) {
/*      */           
/*  781 */           Java8Compatibility.position(out, outIx + inIx);
/*      */           
/*      */           return;
/*      */         } 
/*  785 */         outIx += inIx;
/*  786 */         for (; inIx < inLength; inIx++, outIx++) {
/*  787 */           c = in.charAt(inIx);
/*  788 */           if (c < '') {
/*      */             
/*  790 */             out.put(outIx, (byte)c);
/*  791 */           } else if (c < 'ࠀ') {
/*      */ 
/*      */ 
/*      */             
/*  795 */             out.put(outIx++, (byte)(0xC0 | c >>> 6));
/*  796 */             out.put(outIx, (byte)(0x80 | 0x3F & c));
/*  797 */           } else if (c < '?' || '?' < c) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  802 */             out.put(outIx++, (byte)(0xE0 | c >>> 12));
/*  803 */             out.put(outIx++, (byte)(0x80 | 0x3F & c >>> 6));
/*  804 */             out.put(outIx, (byte)(0x80 | 0x3F & c));
/*      */           } else {
/*      */             char low;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  811 */             if (inIx + 1 == inLength || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
/*  812 */               throw new Utf8.UnpairedSurrogateException(inIx, inLength);
/*      */             }
/*      */             
/*  815 */             int codePoint = Character.toCodePoint(c, low);
/*  816 */             out.put(outIx++, (byte)(0xF0 | codePoint >>> 18));
/*  817 */             out.put(outIx++, (byte)(0x80 | 0x3F & codePoint >>> 12));
/*  818 */             out.put(outIx++, (byte)(0x80 | 0x3F & codePoint >>> 6));
/*  819 */             out.put(outIx, (byte)(0x80 | 0x3F & codePoint));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  824 */         Java8Compatibility.position(out, outIx);
/*  825 */       } catch (IndexOutOfBoundsException e) {
/*      */         
/*  827 */         throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SafeProcessor
/*      */     extends Processor
/*      */   {
/*      */     int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
/*  837 */       if (state != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  845 */         if (index >= limit) {
/*  846 */           return state;
/*      */         }
/*  848 */         int byte1 = (byte)state;
/*      */         
/*  850 */         if (byte1 < -32) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  855 */           if (byte1 < -62 || bytes[index++] > -65)
/*      */           {
/*      */             
/*  858 */             return -1;
/*      */           }
/*  860 */         } else if (byte1 < -16) {
/*      */ 
/*      */ 
/*      */           
/*  864 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*  865 */           if (byte2 == 0) {
/*  866 */             byte2 = bytes[index++];
/*  867 */             if (index >= limit) {
/*  868 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } 
/*  871 */           if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || bytes[index++] > -65)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  878 */             return -1;
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  884 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*  885 */           int byte3 = 0;
/*  886 */           if (byte2 == 0) {
/*  887 */             byte2 = bytes[index++];
/*  888 */             if (index >= limit) {
/*  889 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } else {
/*  892 */             byte3 = (byte)(state >> 16);
/*      */           } 
/*  894 */           if (byte3 == 0) {
/*  895 */             byte3 = bytes[index++];
/*  896 */             if (index >= limit) {
/*  897 */               return Utf8.incompleteStateFor(byte1, byte2, byte3);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  905 */           if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || byte3 > -65 || bytes[index++] > -65)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  915 */             return -1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  920 */       return partialIsValidUtf8(bytes, index, limit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
/*  926 */       return partialIsValidUtf8Default(state, buffer, index, limit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
/*  932 */       if ((index | size | bytes.length - index - size) < 0) {
/*  933 */         throw new ArrayIndexOutOfBoundsException(
/*  934 */             String.format("buffer length=%d, index=%d, size=%d", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(index), Integer.valueOf(size) }));
/*      */       }
/*      */       
/*  937 */       int offset = index;
/*  938 */       int limit = offset + size;
/*      */ 
/*      */ 
/*      */       
/*  942 */       char[] resultArr = new char[size];
/*  943 */       int resultPos = 0;
/*      */ 
/*      */ 
/*      */       
/*  947 */       while (offset < limit) {
/*  948 */         byte b = bytes[offset];
/*  949 */         if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */           break;
/*      */         }
/*  952 */         offset++;
/*  953 */         Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */       } 
/*      */       
/*  956 */       while (offset < limit) {
/*  957 */         byte byte1 = bytes[offset++];
/*  958 */         if (Utf8.DecodeUtil.isOneByte(byte1)) {
/*  959 */           Utf8.DecodeUtil.handleOneByte(byte1, resultArr, resultPos++);
/*      */ 
/*      */           
/*  962 */           while (offset < limit) {
/*  963 */             byte b = bytes[offset];
/*  964 */             if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */               break;
/*      */             }
/*  967 */             offset++;
/*  968 */             Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */           }  continue;
/*  970 */         }  if (Utf8.DecodeUtil.isTwoBytes(byte1)) {
/*  971 */           if (offset >= limit) {
/*  972 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/*  974 */           Utf8.DecodeUtil.handleTwoBytes(byte1, bytes[offset++], resultArr, resultPos++); continue;
/*  975 */         }  if (Utf8.DecodeUtil.isThreeBytes(byte1)) {
/*  976 */           if (offset >= limit - 1) {
/*  977 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/*  979 */           Utf8.DecodeUtil.handleThreeBytes(byte1, bytes[offset++], bytes[offset++], resultArr, resultPos++);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  986 */         if (offset >= limit - 2) {
/*  987 */           throw InvalidProtocolBufferException.invalidUtf8();
/*      */         }
/*  989 */         Utf8.DecodeUtil.handleFourBytes(byte1, bytes[offset++], bytes[offset++], bytes[offset++], resultArr, resultPos++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  997 */         resultPos++;
/*      */       } 
/*      */ 
/*      */       
/* 1001 */       return new String(resultArr, 0, resultPos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String decodeUtf8Direct(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
/* 1008 */       return decodeUtf8Default(buffer, index, size);
/*      */     }
/*      */ 
/*      */     
/*      */     int encodeUtf8(String in, byte[] out, int offset, int length) {
/* 1013 */       int utf16Length = in.length();
/* 1014 */       int j = offset;
/* 1015 */       int i = 0;
/* 1016 */       int limit = offset + length;
/*      */       
/*      */       char c;
/* 1019 */       for (; i < utf16Length && i + j < limit && (c = in.charAt(i)) < ''; i++) {
/* 1020 */         out[j + i] = (byte)c;
/*      */       }
/* 1022 */       if (i == utf16Length) {
/* 1023 */         return j + utf16Length;
/*      */       }
/* 1025 */       j += i;
/* 1026 */       for (; i < utf16Length; i++) {
/* 1027 */         c = in.charAt(i);
/* 1028 */         if (c < '' && j < limit) {
/* 1029 */           out[j++] = (byte)c;
/* 1030 */         } else if (c < 'ࠀ' && j <= limit - 2) {
/* 1031 */           out[j++] = (byte)(0x3C0 | c >>> 6);
/* 1032 */           out[j++] = (byte)(0x80 | 0x3F & c);
/* 1033 */         } else if ((c < '?' || '?' < c) && j <= limit - 3) {
/*      */           
/* 1035 */           out[j++] = (byte)(0x1E0 | c >>> 12);
/* 1036 */           out[j++] = (byte)(0x80 | 0x3F & c >>> 6);
/* 1037 */           out[j++] = (byte)(0x80 | 0x3F & c);
/* 1038 */         } else if (j <= limit - 4) {
/*      */           char low;
/*      */ 
/*      */           
/* 1042 */           if (i + 1 == in.length() || !Character.isSurrogatePair(c, low = in.charAt(++i))) {
/* 1043 */             throw new Utf8.UnpairedSurrogateException(i - 1, utf16Length);
/*      */           }
/* 1045 */           int codePoint = Character.toCodePoint(c, low);
/* 1046 */           out[j++] = (byte)(0xF0 | codePoint >>> 18);
/* 1047 */           out[j++] = (byte)(0x80 | 0x3F & codePoint >>> 12);
/* 1048 */           out[j++] = (byte)(0x80 | 0x3F & codePoint >>> 6);
/* 1049 */           out[j++] = (byte)(0x80 | 0x3F & codePoint);
/*      */         }
/*      */         else {
/*      */           
/* 1053 */           if ('?' <= c && c <= '?' && (i + 1 == in
/* 1054 */             .length() || !Character.isSurrogatePair(c, in.charAt(i + 1)))) {
/* 1055 */             throw new Utf8.UnpairedSurrogateException(i, utf16Length);
/*      */           }
/* 1057 */           throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */         } 
/*      */       } 
/*      */       
/* 1061 */       return j;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void encodeUtf8Direct(String in, ByteBuffer out) {
/* 1067 */       encodeUtf8Default(in, out);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static int partialIsValidUtf8(byte[] bytes, int index, int limit) {
/* 1073 */       while (index < limit && bytes[index] >= 0) {
/* 1074 */         index++;
/*      */       }
/*      */       
/* 1077 */       return (index >= limit) ? 0 : partialIsValidUtf8NonAscii(bytes, index, limit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int partialIsValidUtf8NonAscii(byte[] bytes, int index, int limit) {
/*      */       while (true) {
/* 1087 */         if (index >= limit)
/* 1088 */           return 0; 
/*      */         int byte1;
/* 1090 */         if ((byte1 = bytes[index++]) < 0) {
/*      */           
/* 1092 */           if (byte1 < -32) {
/*      */ 
/*      */             
/* 1095 */             if (index >= limit)
/*      */             {
/* 1097 */               return byte1;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1102 */             if (byte1 < -62 || bytes[index++] > -65)
/* 1103 */               return -1;  continue;
/*      */           } 
/* 1105 */           if (byte1 < -16) {
/*      */ 
/*      */             
/* 1108 */             if (index >= limit - 1)
/* 1109 */               return Utf8.incompleteStateFor(bytes, index, limit); 
/*      */             int i;
/* 1111 */             if ((i = bytes[index++]) > -65 || (byte1 == -32 && i < -96) || (byte1 == -19 && i >= -96) || bytes[index++] > -65)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1118 */               return -1;
/*      */             }
/*      */             
/*      */             continue;
/*      */           } 
/* 1123 */           if (index >= limit - 2)
/* 1124 */             return Utf8.incompleteStateFor(bytes, index, limit); 
/*      */           int byte2;
/* 1126 */           if ((byte2 = bytes[index++]) > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || bytes[index++] > -65 || bytes[index++] > -65) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1136 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class UnsafeProcessor
/*      */     extends Processor
/*      */   {
/*      */     static boolean isAvailable() {
/* 1147 */       return (UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
/* 1153 */       if ((index | limit | bytes.length - limit) < 0) {
/* 1154 */         throw new ArrayIndexOutOfBoundsException(
/* 1155 */             String.format("Array length=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(index), Integer.valueOf(limit) }));
/*      */       }
/* 1157 */       long offset = index;
/* 1158 */       long offsetLimit = limit;
/* 1159 */       if (state != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1167 */         if (offset >= offsetLimit) {
/* 1168 */           return state;
/*      */         }
/* 1170 */         int byte1 = (byte)state;
/*      */         
/* 1172 */         if (byte1 < -32) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1177 */           if (byte1 < -62 || 
/*      */             
/* 1179 */             UnsafeUtil.getByte(bytes, offset++) > -65) {
/* 1180 */             return -1;
/*      */           }
/* 1182 */         } else if (byte1 < -16) {
/*      */ 
/*      */ 
/*      */           
/* 1186 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*      */           
/* 1188 */           byte2 = UnsafeUtil.getByte(bytes, offset++);
/* 1189 */           if (byte2 == 0 && offset >= offsetLimit) {
/* 1190 */             return Utf8.incompleteStateFor(byte1, byte2);
/*      */           }
/*      */           
/* 1193 */           if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1199 */             UnsafeUtil.getByte(bytes, offset++) > -65) {
/* 1200 */             return -1;
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1206 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/* 1207 */           int byte3 = 0;
/* 1208 */           if (byte2 == 0) {
/* 1209 */             byte2 = UnsafeUtil.getByte(bytes, offset++);
/* 1210 */             if (offset >= offsetLimit) {
/* 1211 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } else {
/* 1214 */             byte3 = (byte)(state >> 16);
/*      */           } 
/*      */           
/* 1217 */           byte3 = UnsafeUtil.getByte(bytes, offset++);
/* 1218 */           if (byte3 == 0 && offset >= offsetLimit) {
/* 1219 */             return Utf8.incompleteStateFor(byte1, byte2, byte3);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1227 */           if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || byte3 > -65 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1236 */             UnsafeUtil.getByte(bytes, offset++) > -65) {
/* 1237 */             return -1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1242 */       return partialIsValidUtf8(bytes, offset, (int)(offsetLimit - offset));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
/* 1249 */       if ((index | limit | buffer.limit() - limit) < 0) {
/* 1250 */         throw new ArrayIndexOutOfBoundsException(
/* 1251 */             String.format("buffer limit=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(limit) }));
/*      */       }
/* 1253 */       long address = UnsafeUtil.addressOffset(buffer) + index;
/* 1254 */       long addressLimit = address + (limit - index);
/* 1255 */       if (state != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1263 */         if (address >= addressLimit) {
/* 1264 */           return state;
/*      */         }
/*      */         
/* 1267 */         int byte1 = (byte)state;
/*      */         
/* 1269 */         if (byte1 < -32) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1274 */           if (byte1 < -62 || 
/*      */             
/* 1276 */             UnsafeUtil.getByte(address++) > -65) {
/* 1277 */             return -1;
/*      */           }
/* 1279 */         } else if (byte1 < -16) {
/*      */ 
/*      */ 
/*      */           
/* 1283 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/*      */           
/* 1285 */           byte2 = UnsafeUtil.getByte(address++);
/* 1286 */           if (byte2 == 0 && address >= addressLimit) {
/* 1287 */             return Utf8.incompleteStateFor(byte1, byte2);
/*      */           }
/*      */           
/* 1290 */           if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1296 */             UnsafeUtil.getByte(address++) > -65) {
/* 1297 */             return -1;
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1303 */           int byte2 = (byte)(state >> 8 ^ 0xFFFFFFFF);
/* 1304 */           int byte3 = 0;
/* 1305 */           if (byte2 == 0) {
/* 1306 */             byte2 = UnsafeUtil.getByte(address++);
/* 1307 */             if (address >= addressLimit) {
/* 1308 */               return Utf8.incompleteStateFor(byte1, byte2);
/*      */             }
/*      */           } else {
/* 1311 */             byte3 = (byte)(state >> 16);
/*      */           } 
/*      */           
/* 1314 */           byte3 = UnsafeUtil.getByte(address++);
/* 1315 */           if (byte3 == 0 && address >= addressLimit) {
/* 1316 */             return Utf8.incompleteStateFor(byte1, byte2, byte3);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1324 */           if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || byte3 > -65 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1333 */             UnsafeUtil.getByte(address++) > -65) {
/* 1334 */             return -1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1339 */       return partialIsValidUtf8(address, (int)(addressLimit - address));
/*      */     }
/*      */ 
/*      */     
/*      */     String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
/* 1344 */       String s = new String(bytes, index, size, Internal.UTF_8);
/*      */ 
/*      */ 
/*      */       
/* 1348 */       if (s.indexOf('�') < 0) {
/* 1349 */         return s;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1357 */       if (Arrays.equals(s
/* 1358 */           .getBytes(Internal.UTF_8), Arrays.copyOfRange(bytes, index, index + size))) {
/* 1359 */         return s;
/*      */       }
/*      */       
/* 1362 */       throw InvalidProtocolBufferException.invalidUtf8();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String decodeUtf8Direct(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
/* 1369 */       if ((index | size | buffer.limit() - index - size) < 0) {
/* 1370 */         throw new ArrayIndexOutOfBoundsException(
/* 1371 */             String.format("buffer limit=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(size) }));
/*      */       }
/* 1373 */       long address = UnsafeUtil.addressOffset(buffer) + index;
/* 1374 */       long addressLimit = address + size;
/*      */ 
/*      */ 
/*      */       
/* 1378 */       char[] resultArr = new char[size];
/* 1379 */       int resultPos = 0;
/*      */ 
/*      */ 
/*      */       
/* 1383 */       while (address < addressLimit) {
/* 1384 */         byte b = UnsafeUtil.getByte(address);
/* 1385 */         if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */           break;
/*      */         }
/* 1388 */         address++;
/* 1389 */         Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */       } 
/*      */       
/* 1392 */       while (address < addressLimit) {
/* 1393 */         byte byte1 = UnsafeUtil.getByte(address++);
/* 1394 */         if (Utf8.DecodeUtil.isOneByte(byte1)) {
/* 1395 */           Utf8.DecodeUtil.handleOneByte(byte1, resultArr, resultPos++);
/*      */ 
/*      */           
/* 1398 */           while (address < addressLimit) {
/* 1399 */             byte b = UnsafeUtil.getByte(address);
/* 1400 */             if (!Utf8.DecodeUtil.isOneByte(b)) {
/*      */               break;
/*      */             }
/* 1403 */             address++;
/* 1404 */             Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos++);
/*      */           }  continue;
/* 1406 */         }  if (Utf8.DecodeUtil.isTwoBytes(byte1)) {
/* 1407 */           if (address >= addressLimit) {
/* 1408 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/* 1410 */           Utf8.DecodeUtil.handleTwoBytes(byte1, 
/* 1411 */               UnsafeUtil.getByte(address++), resultArr, resultPos++); continue;
/* 1412 */         }  if (Utf8.DecodeUtil.isThreeBytes(byte1)) {
/* 1413 */           if (address >= addressLimit - 1L) {
/* 1414 */             throw InvalidProtocolBufferException.invalidUtf8();
/*      */           }
/* 1416 */           Utf8.DecodeUtil.handleThreeBytes(byte1, 
/*      */               
/* 1418 */               UnsafeUtil.getByte(address++), 
/* 1419 */               UnsafeUtil.getByte(address++), resultArr, resultPos++);
/*      */           
/*      */           continue;
/*      */         } 
/* 1423 */         if (address >= addressLimit - 2L) {
/* 1424 */           throw InvalidProtocolBufferException.invalidUtf8();
/*      */         }
/* 1426 */         Utf8.DecodeUtil.handleFourBytes(byte1, 
/*      */             
/* 1428 */             UnsafeUtil.getByte(address++), 
/* 1429 */             UnsafeUtil.getByte(address++), 
/* 1430 */             UnsafeUtil.getByte(address++), resultArr, resultPos++);
/*      */ 
/*      */ 
/*      */         
/* 1434 */         resultPos++;
/*      */       } 
/*      */ 
/*      */       
/* 1438 */       return new String(resultArr, 0, resultPos);
/*      */     }
/*      */ 
/*      */     
/*      */     int encodeUtf8(String in, byte[] out, int offset, int length) {
/* 1443 */       long outIx = offset;
/* 1444 */       long outLimit = outIx + length;
/* 1445 */       int inLimit = in.length();
/* 1446 */       if (inLimit > length || out.length - length < offset)
/*      */       {
/* 1448 */         throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1454 */       int inIx = 0; char c;
/* 1455 */       for (; inIx < inLimit && (c = in.charAt(inIx)) < ''; inIx++) {
/* 1456 */         UnsafeUtil.putByte(out, outIx++, (byte)c);
/*      */       }
/* 1458 */       if (inIx == inLimit)
/*      */       {
/* 1460 */         return (int)outIx;
/*      */       }
/*      */       
/* 1463 */       for (; inIx < inLimit; inIx++) {
/* 1464 */         c = in.charAt(inIx);
/*      */         
/* 1466 */         UnsafeUtil.putByte(out, outIx++, (byte)c);
/*      */         
/* 1468 */         UnsafeUtil.putByte(out, outIx++, (byte)(0x3C0 | c >>> 6));
/* 1469 */         UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & c));
/*      */ 
/*      */         
/* 1472 */         UnsafeUtil.putByte(out, outIx++, (byte)(0x1E0 | c >>> 12));
/* 1473 */         UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & c >>> 6));
/* 1474 */         UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & c));
/* 1475 */         if (outIx <= outLimit - 4L) {
/*      */           char low;
/*      */ 
/*      */           
/* 1479 */           if (inIx + 1 == inLimit || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
/* 1480 */             throw new Utf8.UnpairedSurrogateException(inIx - 1, inLimit);
/*      */           }
/* 1482 */           int codePoint = Character.toCodePoint(c, low);
/* 1483 */           UnsafeUtil.putByte(out, outIx++, (byte)(0xF0 | codePoint >>> 18));
/* 1484 */           UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & codePoint >>> 12));
/* 1485 */           UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & codePoint >>> 6));
/* 1486 */           UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | 0x3F & codePoint));
/*      */         } else {
/* 1488 */           if ('?' <= c && c <= '?' && (inIx + 1 == inLimit || 
/* 1489 */             !Character.isSurrogatePair(c, in.charAt(inIx + 1))))
/*      */           {
/* 1491 */             throw new Utf8.UnpairedSurrogateException(inIx, inLimit);
/*      */           }
/*      */           
/* 1494 */           throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1500 */       return (int)outIx;
/*      */     }
/*      */ 
/*      */     
/*      */     void encodeUtf8Direct(String in, ByteBuffer out) {
/* 1505 */       long address = UnsafeUtil.addressOffset(out);
/* 1506 */       long outIx = address + out.position();
/* 1507 */       long outLimit = address + out.limit();
/* 1508 */       int inLimit = in.length();
/* 1509 */       if (inLimit > outLimit - outIx)
/*      */       {
/* 1511 */         throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1517 */       int inIx = 0; char c;
/* 1518 */       for (; inIx < inLimit && (c = in.charAt(inIx)) < ''; inIx++) {
/* 1519 */         UnsafeUtil.putByte(outIx++, (byte)c);
/*      */       }
/* 1521 */       if (inIx == inLimit) {
/*      */         
/* 1523 */         Java8Compatibility.position(out, (int)(outIx - address));
/*      */         
/*      */         return;
/*      */       } 
/* 1527 */       for (; inIx < inLimit; inIx++) {
/* 1528 */         c = in.charAt(inIx);
/*      */         
/* 1530 */         UnsafeUtil.putByte(outIx++, (byte)c);
/*      */         
/* 1532 */         UnsafeUtil.putByte(outIx++, (byte)(0x3C0 | c >>> 6));
/* 1533 */         UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & c));
/*      */ 
/*      */         
/* 1536 */         UnsafeUtil.putByte(outIx++, (byte)(0x1E0 | c >>> 12));
/* 1537 */         UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & c >>> 6));
/* 1538 */         UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & c));
/* 1539 */         if (outIx <= outLimit - 4L) {
/*      */           char low;
/*      */ 
/*      */           
/* 1543 */           if (inIx + 1 == inLimit || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
/* 1544 */             throw new Utf8.UnpairedSurrogateException(inIx - 1, inLimit);
/*      */           }
/* 1546 */           int codePoint = Character.toCodePoint(c, low);
/* 1547 */           UnsafeUtil.putByte(outIx++, (byte)(0xF0 | codePoint >>> 18));
/* 1548 */           UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & codePoint >>> 12));
/* 1549 */           UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & codePoint >>> 6));
/* 1550 */           UnsafeUtil.putByte(outIx++, (byte)(0x80 | 0x3F & codePoint));
/*      */         } else {
/* 1552 */           if ('?' <= c && c <= '?' && (inIx + 1 == inLimit || 
/* 1553 */             !Character.isSurrogatePair(c, in.charAt(inIx + 1))))
/*      */           {
/* 1555 */             throw new Utf8.UnpairedSurrogateException(inIx, inLimit);
/*      */           }
/*      */           
/* 1558 */           throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1564 */       Java8Compatibility.position(out, (int)(outIx - address));
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
/*      */     private static int unsafeEstimateConsecutiveAscii(byte[] bytes, long offset, int maxChars) {
/* 1579 */       if (maxChars < 16)
/*      */       {
/* 1581 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1589 */       int unaligned = 8 - ((int)offset & 0x7);
/*      */       int i;
/* 1591 */       for (i = 0; i < unaligned; i++) {
/* 1592 */         if (UnsafeUtil.getByte(bytes, offset++) < 0) {
/* 1593 */           return i;
/*      */         }
/*      */       } 
/*      */       
/* 1597 */       for (; i + 8 <= maxChars && (
/* 1598 */         UnsafeUtil.getLong(bytes, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + offset) & 0x8080808080808080L) == 0L; i += 8)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1603 */         offset += 8L;
/*      */       }
/*      */       
/* 1606 */       for (; i < maxChars; i++) {
/* 1607 */         if (UnsafeUtil.getByte(bytes, offset++) < 0) {
/* 1608 */           return i;
/*      */         }
/*      */       } 
/* 1611 */       return maxChars;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int unsafeEstimateConsecutiveAscii(long address, int maxChars) {
/* 1619 */       int remaining = maxChars;
/* 1620 */       if (remaining < 16)
/*      */       {
/* 1622 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1628 */       int unaligned = (int)(-address & 0x7L);
/* 1629 */       for (int j = unaligned; j > 0; j--) {
/* 1630 */         if (UnsafeUtil.getByte(address++) < 0) {
/* 1631 */           return unaligned - j;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1638 */       remaining -= unaligned;
/*      */       
/* 1640 */       while (remaining >= 8 && (UnsafeUtil.getLong(address) & 0x8080808080808080L) == 0L) {
/* 1641 */         address += 8L; remaining -= 8;
/* 1642 */       }  return maxChars - remaining;
/*      */     }
/*      */ 
/*      */     
/*      */     private static int partialIsValidUtf8(byte[] bytes, long offset, int remaining) {
/* 1647 */       int skipped = unsafeEstimateConsecutiveAscii(bytes, offset, remaining);
/* 1648 */       remaining -= skipped;
/* 1649 */       offset += skipped;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true)
/* 1655 */       { int byte1 = 0;
/* 1656 */         for (; remaining > 0 && (byte1 = UnsafeUtil.getByte(bytes, offset++)) >= 0; remaining--);
/* 1657 */         if (remaining == 0) {
/* 1658 */           return 0;
/*      */         }
/* 1660 */         remaining--;
/*      */ 
/*      */         
/* 1663 */         if (byte1 < -32) {
/*      */           
/* 1665 */           if (remaining == 0)
/*      */           {
/* 1667 */             return byte1;
/*      */           }
/* 1669 */           remaining--;
/*      */ 
/*      */ 
/*      */           
/* 1673 */           if (byte1 < -62 || UnsafeUtil.getByte(bytes, offset++) > -65)
/* 1674 */             return -1;  continue;
/*      */         } 
/* 1676 */         if (byte1 < -16) {
/*      */           
/* 1678 */           if (remaining < 2)
/*      */           {
/* 1680 */             return unsafeIncompleteStateFor(bytes, byte1, offset, remaining);
/*      */           }
/* 1682 */           remaining -= 2;
/*      */           
/*      */           int i;
/* 1685 */           if ((i = UnsafeUtil.getByte(bytes, offset++)) > -65 || (byte1 == -32 && i < -96) || (byte1 == -19 && i >= -96) || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1691 */             UnsafeUtil.getByte(bytes, offset++) > -65) {
/* 1692 */             return -1;
/*      */           }
/*      */           continue;
/*      */         } 
/* 1696 */         if (remaining < 3)
/*      */         {
/* 1698 */           return unsafeIncompleteStateFor(bytes, byte1, offset, remaining);
/*      */         }
/* 1700 */         remaining -= 3;
/*      */         
/*      */         int byte2;
/* 1703 */         if ((byte2 = UnsafeUtil.getByte(bytes, offset++)) > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1710 */           UnsafeUtil.getByte(bytes, offset++) > -65 || 
/*      */           
/* 1712 */           UnsafeUtil.getByte(bytes, offset++) > -65)
/* 1713 */           break;  }  return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int partialIsValidUtf8(long address, int remaining) {
/* 1721 */       int skipped = unsafeEstimateConsecutiveAscii(address, remaining);
/* 1722 */       address += skipped;
/* 1723 */       remaining -= skipped;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true)
/* 1729 */       { int byte1 = 0;
/* 1730 */         for (; remaining > 0 && (byte1 = UnsafeUtil.getByte(address++)) >= 0; remaining--);
/* 1731 */         if (remaining == 0) {
/* 1732 */           return 0;
/*      */         }
/* 1734 */         remaining--;
/*      */         
/* 1736 */         if (byte1 < -32) {
/*      */ 
/*      */           
/* 1739 */           if (remaining == 0)
/*      */           {
/* 1741 */             return byte1;
/*      */           }
/* 1743 */           remaining--;
/*      */ 
/*      */ 
/*      */           
/* 1747 */           if (byte1 < -62 || UnsafeUtil.getByte(address++) > -65)
/* 1748 */             return -1;  continue;
/*      */         } 
/* 1750 */         if (byte1 < -16) {
/*      */ 
/*      */           
/* 1753 */           if (remaining < 2)
/*      */           {
/* 1755 */             return unsafeIncompleteStateFor(address, byte1, remaining);
/*      */           }
/* 1757 */           remaining -= 2;
/*      */           
/* 1759 */           byte b = UnsafeUtil.getByte(address++);
/* 1760 */           if (b > -65 || (byte1 == -32 && b < -96) || (byte1 == -19 && b >= -96) || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1766 */             UnsafeUtil.getByte(address++) > -65) {
/* 1767 */             return -1;
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/* 1772 */         if (remaining < 3)
/*      */         {
/* 1774 */           return unsafeIncompleteStateFor(address, byte1, remaining);
/*      */         }
/* 1776 */         remaining -= 3;
/*      */         
/* 1778 */         byte byte2 = UnsafeUtil.getByte(address++);
/* 1779 */         if (byte2 > -65 || (byte1 << 28) + byte2 - -112 >> 30 != 0 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1786 */           UnsafeUtil.getByte(address++) > -65 || 
/*      */           
/* 1788 */           UnsafeUtil.getByte(address++) > -65)
/* 1789 */           break;  }  return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int unsafeIncompleteStateFor(byte[] bytes, int byte1, long offset, int remaining) {
/* 1797 */       switch (remaining) {
/*      */         case 0:
/* 1799 */           return Utf8.incompleteStateFor(byte1);
/*      */         case 1:
/* 1801 */           return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(bytes, offset));
/*      */         case 2:
/* 1803 */           return Utf8.incompleteStateFor(byte1, 
/* 1804 */               UnsafeUtil.getByte(bytes, offset), UnsafeUtil.getByte(bytes, offset + 1L));
/*      */       } 
/* 1806 */       throw new AssertionError();
/*      */     }
/*      */ 
/*      */     
/*      */     private static int unsafeIncompleteStateFor(long address, int byte1, int remaining) {
/* 1811 */       switch (remaining) {
/*      */         case 0:
/* 1813 */           return Utf8.incompleteStateFor(byte1);
/*      */         case 1:
/* 1815 */           return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(address));
/*      */         case 2:
/* 1817 */           return Utf8.incompleteStateFor(byte1, 
/* 1818 */               UnsafeUtil.getByte(address), UnsafeUtil.getByte(address + 1L));
/*      */       } 
/* 1820 */       throw new AssertionError();
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
/*      */   private static class DecodeUtil
/*      */   {
/*      */     private static boolean isOneByte(byte b) {
/* 1834 */       return (b >= 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static boolean isTwoBytes(byte b) {
/* 1844 */       return (b < -32);
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
/*      */     private static boolean isThreeBytes(byte b) {
/* 1856 */       return (b < -16);
/*      */     }
/*      */     
/*      */     private static void handleOneByte(byte byte1, char[] resultArr, int resultPos) {
/* 1860 */       resultArr[resultPos] = (char)byte1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void handleTwoBytes(byte byte1, byte byte2, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
/* 1867 */       if (byte1 < -62 || isNotTrailingByte(byte2)) {
/* 1868 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/* 1870 */       resultArr[resultPos] = (char)((byte1 & 0x1F) << 6 | trailingByteValue(byte2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void handleThreeBytes(byte byte1, byte byte2, byte byte3, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
/* 1876 */       if (isNotTrailingByte(byte2) || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1881 */         isNotTrailingByte(byte3)) {
/* 1882 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/* 1884 */       resultArr[resultPos] = 
/*      */         
/* 1886 */         (char)((byte1 & 0xF) << 12 | trailingByteValue(byte2) << 6 | trailingByteValue(byte3));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void handleFourBytes(byte byte1, byte byte2, byte byte3, byte byte4, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
/* 1892 */       if (isNotTrailingByte(byte2) || (byte1 << 28) + byte2 - -112 >> 30 != 0 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1901 */         isNotTrailingByte(byte3) || 
/* 1902 */         isNotTrailingByte(byte4)) {
/* 1903 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1909 */       int codepoint = (byte1 & 0x7) << 18 | trailingByteValue(byte2) << 12 | trailingByteValue(byte3) << 6 | trailingByteValue(byte4);
/* 1910 */       resultArr[resultPos] = highSurrogate(codepoint);
/* 1911 */       resultArr[resultPos + 1] = lowSurrogate(codepoint);
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isNotTrailingByte(byte b) {
/* 1916 */       return (b > -65);
/*      */     }
/*      */ 
/*      */     
/*      */     private static int trailingByteValue(byte b) {
/* 1921 */       return b & 0x3F;
/*      */     }
/*      */     
/*      */     private static char highSurrogate(int codePoint) {
/* 1925 */       return (char)(55232 + (codePoint >>> 10));
/*      */     }
/*      */ 
/*      */     
/*      */     private static char lowSurrogate(int codePoint) {
/* 1930 */       return (char)(56320 + (codePoint & 0x3FF));
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Utf8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */