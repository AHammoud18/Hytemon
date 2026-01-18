/*      */ package io.sentry.vendor.gson.stream;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.util.Arrays;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Internal
/*      */ public class JsonReader
/*      */   implements Closeable
/*      */ {
/*      */   private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
/*      */   private static final int PEEKED_NONE = 0;
/*      */   private static final int PEEKED_BEGIN_OBJECT = 1;
/*      */   private static final int PEEKED_END_OBJECT = 2;
/*      */   private static final int PEEKED_BEGIN_ARRAY = 3;
/*      */   private static final int PEEKED_END_ARRAY = 4;
/*      */   private static final int PEEKED_TRUE = 5;
/*      */   private static final int PEEKED_FALSE = 6;
/*      */   private static final int PEEKED_NULL = 7;
/*      */   private static final int PEEKED_SINGLE_QUOTED = 8;
/*      */   private static final int PEEKED_DOUBLE_QUOTED = 9;
/*      */   private static final int PEEKED_UNQUOTED = 10;
/*      */   private static final int PEEKED_BUFFERED = 11;
/*      */   private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
/*      */   private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
/*      */   private static final int PEEKED_UNQUOTED_NAME = 14;
/*      */   private static final int PEEKED_LONG = 15;
/*      */   private static final int PEEKED_NUMBER = 16;
/*      */   private static final int PEEKED_EOF = 17;
/*      */   private static final int NUMBER_CHAR_NONE = 0;
/*      */   private static final int NUMBER_CHAR_SIGN = 1;
/*      */   private static final int NUMBER_CHAR_DIGIT = 2;
/*      */   private static final int NUMBER_CHAR_DECIMAL = 3;
/*      */   private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
/*      */   private static final int NUMBER_CHAR_EXP_E = 5;
/*      */   private static final int NUMBER_CHAR_EXP_SIGN = 6;
/*      */   private static final int NUMBER_CHAR_EXP_DIGIT = 7;
/*      */   private final Reader in;
/*      */   private boolean lenient = false;
/*  245 */   private final char[] buffer = new char[1024];
/*  246 */   private int pos = 0;
/*  247 */   private int limit = 0;
/*      */   
/*  249 */   private int lineNumber = 0;
/*  250 */   private int lineStart = 0;
/*      */   
/*  252 */   int peeked = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long peekedLong;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekedNumberLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String peekedString;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  276 */   private int[] stack = new int[32];
/*  277 */   private int stackSize = 0; private String[] pathNames; private int[] pathIndices;
/*      */   public JsonReader(Reader in) {
/*  279 */     this.stack[this.stackSize++] = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     this.pathNames = new String[32];
/*  291 */     this.pathIndices = new int[32];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  297 */     if (in == null) {
/*  298 */       throw new NullPointerException("in == null");
/*      */     }
/*  300 */     this.in = in;
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
/*      */   public final void setLenient(boolean lenient) {
/*  333 */     this.lenient = lenient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isLenient() {
/*  340 */     return this.lenient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginArray() throws IOException {
/*  348 */     int p = this.peeked;
/*  349 */     if (p == 0) {
/*  350 */       p = doPeek();
/*      */     }
/*  352 */     if (p == 3) {
/*  353 */       push(1);
/*  354 */       this.pathIndices[this.stackSize - 1] = 0;
/*  355 */       this.peeked = 0;
/*      */     } else {
/*  357 */       throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endArray() throws IOException {
/*  366 */     int p = this.peeked;
/*  367 */     if (p == 0) {
/*  368 */       p = doPeek();
/*      */     }
/*  370 */     if (p == 4) {
/*  371 */       this.stackSize--;
/*  372 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  373 */       this.peeked = 0;
/*      */     } else {
/*  375 */       throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginObject() throws IOException {
/*  384 */     int p = this.peeked;
/*  385 */     if (p == 0) {
/*  386 */       p = doPeek();
/*      */     }
/*  388 */     if (p == 1) {
/*  389 */       push(3);
/*  390 */       this.peeked = 0;
/*      */     } else {
/*  392 */       throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endObject() throws IOException {
/*  401 */     int p = this.peeked;
/*  402 */     if (p == 0) {
/*  403 */       p = doPeek();
/*      */     }
/*  405 */     if (p == 2) {
/*  406 */       this.stackSize--;
/*  407 */       this.pathNames[this.stackSize] = null;
/*  408 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  409 */       this.peeked = 0;
/*      */     } else {
/*  411 */       throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws IOException {
/*  419 */     int p = this.peeked;
/*  420 */     if (p == 0) {
/*  421 */       p = doPeek();
/*      */     }
/*  423 */     return (p != 2 && p != 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonToken peek() throws IOException {
/*  430 */     int p = this.peeked;
/*  431 */     if (p == 0) {
/*  432 */       p = doPeek();
/*      */     }
/*      */     
/*  435 */     switch (p) {
/*      */       case 1:
/*  437 */         return JsonToken.BEGIN_OBJECT;
/*      */       case 2:
/*  439 */         return JsonToken.END_OBJECT;
/*      */       case 3:
/*  441 */         return JsonToken.BEGIN_ARRAY;
/*      */       case 4:
/*  443 */         return JsonToken.END_ARRAY;
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*  447 */         return JsonToken.NAME;
/*      */       case 5:
/*      */       case 6:
/*  450 */         return JsonToken.BOOLEAN;
/*      */       case 7:
/*  452 */         return JsonToken.NULL;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  457 */         return JsonToken.STRING;
/*      */       case 15:
/*      */       case 16:
/*  460 */         return JsonToken.NUMBER;
/*      */       case 17:
/*  462 */         return JsonToken.END_DOCUMENT;
/*      */     } 
/*  464 */     throw new AssertionError();
/*      */   }
/*      */ 
/*      */   
/*      */   int doPeek() throws IOException {
/*  469 */     int peekStack = this.stack[this.stackSize - 1];
/*  470 */     if (peekStack == 1)
/*  471 */     { this.stack[this.stackSize - 1] = 2; }
/*  472 */     else if (peekStack == 2)
/*      */     
/*  474 */     { int i = nextNonWhitespace(true);
/*  475 */       switch (i) {
/*      */         case 93:
/*  477 */           return this.peeked = 4;
/*      */         case 59:
/*  479 */           checkLenient(); break;
/*      */         case 44:
/*      */           break;
/*      */         default:
/*  483 */           throw syntaxError("Unterminated array");
/*      */       }  }
/*  485 */     else { if (peekStack == 3 || peekStack == 5) {
/*  486 */         this.stack[this.stackSize - 1] = 4;
/*      */         
/*  488 */         if (peekStack == 5) {
/*  489 */           int j = nextNonWhitespace(true);
/*  490 */           switch (j) {
/*      */             case 125:
/*  492 */               return this.peeked = 2;
/*      */             case 59:
/*  494 */               checkLenient(); break;
/*      */             case 44:
/*      */               break;
/*      */             default:
/*  498 */               throw syntaxError("Unterminated object");
/*      */           } 
/*      */         } 
/*  501 */         int i = nextNonWhitespace(true);
/*  502 */         switch (i) {
/*      */           case 34:
/*  504 */             return this.peeked = 13;
/*      */           case 39:
/*  506 */             checkLenient();
/*  507 */             return this.peeked = 12;
/*      */           case 125:
/*  509 */             if (peekStack != 5) {
/*  510 */               return this.peeked = 2;
/*      */             }
/*  512 */             throw syntaxError("Expected name");
/*      */         } 
/*      */         
/*  515 */         checkLenient();
/*  516 */         this.pos--;
/*  517 */         if (isLiteral((char)i)) {
/*  518 */           return this.peeked = 14;
/*      */         }
/*  520 */         throw syntaxError("Expected name");
/*      */       } 
/*      */       
/*  523 */       if (peekStack == 4) {
/*  524 */         this.stack[this.stackSize - 1] = 5;
/*      */         
/*  526 */         int i = nextNonWhitespace(true);
/*  527 */         switch (i) {
/*      */           case 58:
/*      */             break;
/*      */           case 61:
/*  531 */             checkLenient();
/*  532 */             if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
/*  533 */               this.pos++;
/*      */             }
/*      */             break;
/*      */           default:
/*  537 */             throw syntaxError("Expected ':'");
/*      */         } 
/*  539 */       } else if (peekStack == 6) {
/*  540 */         if (this.lenient) {
/*  541 */           consumeNonExecutePrefix();
/*      */         }
/*  543 */         this.stack[this.stackSize - 1] = 7;
/*  544 */       } else if (peekStack == 7) {
/*  545 */         int i = nextNonWhitespace(false);
/*  546 */         if (i == -1) {
/*  547 */           return this.peeked = 17;
/*      */         }
/*  549 */         checkLenient();
/*  550 */         this.pos--;
/*      */       }
/*  552 */       else if (peekStack == 8) {
/*  553 */         throw new IllegalStateException("JsonReader is closed");
/*      */       }  }
/*      */     
/*  556 */     int c = nextNonWhitespace(true);
/*  557 */     switch (c) {
/*      */       case 93:
/*  559 */         if (peekStack == 1) {
/*  560 */           return this.peeked = 4;
/*      */         }
/*      */ 
/*      */       
/*      */       case 44:
/*      */       case 59:
/*  566 */         if (peekStack == 1 || peekStack == 2) {
/*  567 */           checkLenient();
/*  568 */           this.pos--;
/*  569 */           return this.peeked = 7;
/*      */         } 
/*  571 */         throw syntaxError("Unexpected value");
/*      */       
/*      */       case 39:
/*  574 */         checkLenient();
/*  575 */         return this.peeked = 8;
/*      */       case 34:
/*  577 */         return this.peeked = 9;
/*      */       case 91:
/*  579 */         return this.peeked = 3;
/*      */       case 123:
/*  581 */         return this.peeked = 1;
/*      */     } 
/*  583 */     this.pos--;
/*      */ 
/*      */     
/*  586 */     int result = peekKeyword();
/*  587 */     if (result != 0) {
/*  588 */       return result;
/*      */     }
/*      */     
/*  591 */     result = peekNumber();
/*  592 */     if (result != 0) {
/*  593 */       return result;
/*      */     }
/*      */     
/*  596 */     if (!isLiteral(this.buffer[this.pos])) {
/*  597 */       throw syntaxError("Expected value");
/*      */     }
/*      */     
/*  600 */     checkLenient();
/*  601 */     return this.peeked = 10;
/*      */   }
/*      */   private int peekKeyword() throws IOException {
/*      */     String keyword, keywordUpper;
/*      */     int peeking;
/*  606 */     char c = this.buffer[this.pos];
/*      */ 
/*      */ 
/*      */     
/*  610 */     if (c == 't' || c == 'T') {
/*  611 */       keyword = "true";
/*  612 */       keywordUpper = "TRUE";
/*  613 */       peeking = 5;
/*  614 */     } else if (c == 'f' || c == 'F') {
/*  615 */       keyword = "false";
/*  616 */       keywordUpper = "FALSE";
/*  617 */       peeking = 6;
/*  618 */     } else if (c == 'n' || c == 'N') {
/*  619 */       keyword = "null";
/*  620 */       keywordUpper = "NULL";
/*  621 */       peeking = 7;
/*      */     } else {
/*  623 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  627 */     int length = keyword.length();
/*  628 */     for (int i = 1; i < length; i++) {
/*  629 */       if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
/*  630 */         return 0;
/*      */       }
/*  632 */       c = this.buffer[this.pos + i];
/*  633 */       if (c != keyword.charAt(i) && c != keywordUpper.charAt(i)) {
/*  634 */         return 0;
/*      */       }
/*      */     } 
/*      */     
/*  638 */     if ((this.pos + length < this.limit || fillBuffer(length + 1)) && 
/*  639 */       isLiteral(this.buffer[this.pos + length])) {
/*  640 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  644 */     this.pos += length;
/*  645 */     return this.peeked = peeking;
/*      */   }
/*      */   
/*      */   private int peekNumber() throws IOException {
/*      */     int j;
/*  650 */     char[] buffer = this.buffer;
/*  651 */     int p = this.pos;
/*  652 */     int l = this.limit;
/*      */     
/*  654 */     long value = 0L;
/*  655 */     boolean negative = false;
/*  656 */     boolean fitsInLong = true;
/*  657 */     int last = 0;
/*      */     
/*  659 */     int i = 0;
/*      */ 
/*      */     
/*  662 */     for (;; i++) {
/*  663 */       if (p + i == l) {
/*  664 */         if (i == buffer.length)
/*      */         {
/*      */           
/*  667 */           return 0;
/*      */         }
/*  669 */         if (!fillBuffer(i + 1)) {
/*      */           break;
/*      */         }
/*  672 */         p = this.pos;
/*  673 */         l = this.limit;
/*      */       } 
/*      */       
/*  676 */       char c = buffer[p + i];
/*  677 */       switch (c) {
/*      */         case '-':
/*  679 */           if (last == 0) {
/*  680 */             negative = true;
/*  681 */             last = 1; break;
/*      */           } 
/*  683 */           if (last == 5) {
/*  684 */             last = 6;
/*      */             break;
/*      */           } 
/*  687 */           return 0;
/*      */         
/*      */         case '+':
/*  690 */           if (last == 5) {
/*  691 */             last = 6;
/*      */             break;
/*      */           } 
/*  694 */           return 0;
/*      */         
/*      */         case 'E':
/*      */         case 'e':
/*  698 */           if (last == 2 || last == 4) {
/*  699 */             last = 5;
/*      */             break;
/*      */           } 
/*  702 */           return 0;
/*      */         
/*      */         case '.':
/*  705 */           if (last == 2) {
/*  706 */             last = 3;
/*      */             break;
/*      */           } 
/*  709 */           return 0;
/*      */         
/*      */         default:
/*  712 */           if (c < '0' || c > '9') {
/*  713 */             if (!isLiteral(c)) {
/*      */               break;
/*      */             }
/*  716 */             return 0;
/*      */           } 
/*  718 */           if (last == 1 || last == 0) {
/*  719 */             value = -(c - 48);
/*  720 */             last = 2; break;
/*  721 */           }  if (last == 2) {
/*  722 */             if (value == 0L) {
/*  723 */               return 0;
/*      */             }
/*  725 */             long newValue = value * 10L - (c - 48);
/*  726 */             j = fitsInLong & ((value > -922337203685477580L || (value == -922337203685477580L && newValue < value)) ? 1 : 0);
/*      */             
/*  728 */             value = newValue; break;
/*  729 */           }  if (last == 3) {
/*  730 */             last = 4; break;
/*  731 */           }  if (last == 5 || last == 6) {
/*  732 */             last = 7;
/*      */           }
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  738 */     if (last == 2 && j != 0 && (value != Long.MIN_VALUE || negative) && (value != 0L || false == negative)) {
/*  739 */       this.peekedLong = negative ? value : -value;
/*  740 */       this.pos += i;
/*  741 */       return this.peeked = 15;
/*  742 */     }  if (last == 2 || last == 4 || last == 7) {
/*      */       
/*  744 */       this.peekedNumberLength = i;
/*  745 */       return this.peeked = 16;
/*      */     } 
/*  747 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isLiteral(char c) throws IOException {
/*  752 */     switch (c) {
/*      */       case '#':
/*      */       case '/':
/*      */       case ';':
/*      */       case '=':
/*      */       case '\\':
/*  758 */         checkLenient();
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\f':
/*      */       case '\r':
/*      */       case ' ':
/*      */       case ',':
/*      */       case ':':
/*      */       case '[':
/*      */       case ']':
/*      */       case '{':
/*      */       case '}':
/*  770 */         return false;
/*      */     } 
/*  772 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextName() throws IOException {
/*      */     String result;
/*  784 */     int p = this.peeked;
/*  785 */     if (p == 0) {
/*  786 */       p = doPeek();
/*      */     }
/*      */     
/*  789 */     if (p == 14) {
/*  790 */       result = nextUnquotedValue();
/*  791 */     } else if (p == 12) {
/*  792 */       result = nextQuotedValue('\'');
/*  793 */     } else if (p == 13) {
/*  794 */       result = nextQuotedValue('"');
/*      */     } else {
/*  796 */       throw new IllegalStateException("Expected a name but was " + peek() + locationString());
/*      */     } 
/*  798 */     this.peeked = 0;
/*  799 */     this.pathNames[this.stackSize - 1] = result;
/*  800 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextString() throws IOException {
/*      */     String result;
/*  812 */     int p = this.peeked;
/*  813 */     if (p == 0) {
/*  814 */       p = doPeek();
/*      */     }
/*      */     
/*  817 */     if (p == 10) {
/*  818 */       result = nextUnquotedValue();
/*  819 */     } else if (p == 8) {
/*  820 */       result = nextQuotedValue('\'');
/*  821 */     } else if (p == 9) {
/*  822 */       result = nextQuotedValue('"');
/*  823 */     } else if (p == 11) {
/*  824 */       result = this.peekedString;
/*  825 */       this.peekedString = null;
/*  826 */     } else if (p == 15) {
/*  827 */       result = Long.toString(this.peekedLong);
/*  828 */     } else if (p == 16) {
/*  829 */       result = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  830 */       this.pos += this.peekedNumberLength;
/*      */     } else {
/*  832 */       throw new IllegalStateException("Expected a string but was " + peek() + locationString());
/*      */     } 
/*  834 */     this.peeked = 0;
/*  835 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  836 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nextBoolean() throws IOException {
/*  847 */     int p = this.peeked;
/*  848 */     if (p == 0) {
/*  849 */       p = doPeek();
/*      */     }
/*  851 */     if (p == 5) {
/*  852 */       this.peeked = 0;
/*  853 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  854 */       return true;
/*  855 */     }  if (p == 6) {
/*  856 */       this.peeked = 0;
/*  857 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  858 */       return false;
/*      */     } 
/*  860 */     throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void nextNull() throws IOException {
/*  871 */     int p = this.peeked;
/*  872 */     if (p == 0) {
/*  873 */       p = doPeek();
/*      */     }
/*  875 */     if (p == 7) {
/*  876 */       this.peeked = 0;
/*  877 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*      */     } else {
/*  879 */       throw new IllegalStateException("Expected null but was " + peek() + locationString());
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
/*      */   public double nextDouble() throws IOException {
/*  893 */     int p = this.peeked;
/*  894 */     if (p == 0) {
/*  895 */       p = doPeek();
/*      */     }
/*      */     
/*  898 */     if (p == 15) {
/*  899 */       this.peeked = 0;
/*  900 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  901 */       return this.peekedLong;
/*      */     } 
/*      */     
/*  904 */     if (p == 16) {
/*  905 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  906 */       this.pos += this.peekedNumberLength;
/*  907 */     } else if (p == 8 || p == 9) {
/*  908 */       this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*  909 */     } else if (p == 10) {
/*  910 */       this.peekedString = nextUnquotedValue();
/*  911 */     } else if (p != 11) {
/*  912 */       throw new IllegalStateException("Expected a double but was " + peek() + locationString());
/*      */     } 
/*      */     
/*  915 */     this.peeked = 11;
/*  916 */     double result = Double.parseDouble(this.peekedString);
/*  917 */     if (!this.lenient && (Double.isNaN(result) || Double.isInfinite(result))) {
/*  918 */       throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + 
/*  919 */           locationString());
/*      */     }
/*  921 */     this.peekedString = null;
/*  922 */     this.peeked = 0;
/*  923 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  924 */     return result;
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
/*      */   public long nextLong() throws IOException {
/*  938 */     int p = this.peeked;
/*  939 */     if (p == 0) {
/*  940 */       p = doPeek();
/*      */     }
/*      */     
/*  943 */     if (p == 15) {
/*  944 */       this.peeked = 0;
/*  945 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  946 */       return this.peekedLong;
/*      */     } 
/*      */     
/*  949 */     if (p == 16) {
/*  950 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  951 */       this.pos += this.peekedNumberLength;
/*  952 */     } else if (p == 8 || p == 9 || p == 10) {
/*  953 */       if (p == 10) {
/*  954 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/*  956 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/*  959 */         long l = Long.parseLong(this.peekedString);
/*  960 */         this.peeked = 0;
/*  961 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  962 */         return l;
/*  963 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/*  967 */       throw new IllegalStateException("Expected a long but was " + peek() + locationString());
/*      */     } 
/*      */     
/*  970 */     this.peeked = 11;
/*  971 */     double asDouble = Double.parseDouble(this.peekedString);
/*  972 */     long result = (long)asDouble;
/*  973 */     if (result != asDouble) {
/*  974 */       throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
/*      */     }
/*  976 */     this.peekedString = null;
/*  977 */     this.peeked = 0;
/*  978 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  979 */     return result;
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
/*      */   private String nextQuotedValue(char quote) throws IOException {
/*  994 */     char[] buffer = this.buffer;
/*  995 */     StringBuilder builder = null;
/*      */     while (true) {
/*  997 */       int p = this.pos;
/*  998 */       int l = this.limit;
/*      */       
/* 1000 */       int start = p;
/* 1001 */       while (p < l) {
/* 1002 */         int c = buffer[p++];
/*      */         
/* 1004 */         if (c == quote) {
/* 1005 */           this.pos = p;
/* 1006 */           int len = p - start - 1;
/* 1007 */           if (builder == null) {
/* 1008 */             return new String(buffer, start, len);
/*      */           }
/* 1010 */           builder.append(buffer, start, len);
/* 1011 */           return builder.toString();
/*      */         } 
/* 1013 */         if (c == 92) {
/* 1014 */           this.pos = p;
/* 1015 */           int len = p - start - 1;
/* 1016 */           if (builder == null) {
/* 1017 */             int estimatedLength = (len + 1) * 2;
/* 1018 */             builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */           } 
/* 1020 */           builder.append(buffer, start, len);
/* 1021 */           builder.append(readEscapeCharacter());
/* 1022 */           p = this.pos;
/* 1023 */           l = this.limit;
/* 1024 */           start = p; continue;
/* 1025 */         }  if (c == 10) {
/* 1026 */           this.lineNumber++;
/* 1027 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/*      */       
/* 1031 */       if (builder == null) {
/* 1032 */         int estimatedLength = (p - start) * 2;
/* 1033 */         builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */       } 
/* 1035 */       builder.append(buffer, start, p - start);
/* 1036 */       this.pos = p;
/* 1037 */       if (!fillBuffer(1)) {
/* 1038 */         throw syntaxError("Unterminated string");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String nextUnquotedValue() throws IOException {
/* 1048 */     StringBuilder builder = null;
/* 1049 */     int i = 0;
/*      */ 
/*      */     
/*      */     label34: while (true) {
/* 1053 */       for (; this.pos + i < this.limit; i++)
/* 1054 */       { switch (this.buffer[this.pos + i])
/*      */         { case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1060 */             checkLenient(); break label34;
/*      */           case '\t': break label34;
/*      */           case '\n': break label34;
/*      */           case '\f': break label34;
/*      */           case '\r': break label34;
/*      */           case ' ': break label34;
/*      */           case ',':
/*      */             break label34;
/*      */           case ':':
/*      */             break label34;
/*      */           case '[':
/*      */             break label34;
/*      */           case ']':
/*      */             break label34;
/*      */           case '{':
/*      */             break label34;
/*      */           case '}':
/* 1077 */             break label34; }  }  if (i < this.buffer.length) {
/* 1078 */         if (fillBuffer(i + 1)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1086 */       if (builder == null) {
/* 1087 */         builder = new StringBuilder(Math.max(i, 16));
/*      */       }
/* 1089 */       builder.append(this.buffer, this.pos, i);
/* 1090 */       this.pos += i;
/* 1091 */       i = 0;
/* 1092 */       if (!fillBuffer(1)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1097 */     String result = (null == builder) ? new String(this.buffer, this.pos, i) : builder.append(this.buffer, this.pos, i).toString();
/* 1098 */     this.pos += i;
/* 1099 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipQuotedValue(char quote) throws IOException {
/* 1104 */     char[] buffer = this.buffer;
/*      */     while (true) {
/* 1106 */       int p = this.pos;
/* 1107 */       int l = this.limit;
/*      */       
/* 1109 */       while (p < l) {
/* 1110 */         int c = buffer[p++];
/* 1111 */         if (c == quote) {
/* 1112 */           this.pos = p; return;
/*      */         } 
/* 1114 */         if (c == 92) {
/* 1115 */           this.pos = p;
/* 1116 */           readEscapeCharacter();
/* 1117 */           p = this.pos;
/* 1118 */           l = this.limit; continue;
/* 1119 */         }  if (c == 10) {
/* 1120 */           this.lineNumber++;
/* 1121 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/* 1124 */       this.pos = p;
/* 1125 */       if (!fillBuffer(1))
/* 1126 */         throw syntaxError("Unterminated string"); 
/*      */     } 
/*      */   }
/*      */   private void skipUnquotedValue() throws IOException {
/*      */     do {
/* 1131 */       int i = 0;
/* 1132 */       for (; this.pos + i < this.limit; i++) {
/* 1133 */         switch (this.buffer[this.pos + i]) {
/*      */           case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1139 */             checkLenient();
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */           case ',':
/*      */           case ':':
/*      */           case '[':
/*      */           case ']':
/*      */           case '{':
/*      */           case '}':
/* 1151 */             this.pos += i;
/*      */             return;
/*      */         } 
/*      */       } 
/* 1155 */       this.pos += i;
/* 1156 */     } while (fillBuffer(1));
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
/*      */   public int nextInt() throws IOException {
/* 1170 */     int p = this.peeked;
/* 1171 */     if (p == 0) {
/* 1172 */       p = doPeek();
/*      */     }
/*      */ 
/*      */     
/* 1176 */     if (p == 15) {
/* 1177 */       int i = (int)this.peekedLong;
/* 1178 */       if (this.peekedLong != i) {
/* 1179 */         throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
/*      */       }
/* 1181 */       this.peeked = 0;
/* 1182 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1183 */       return i;
/*      */     } 
/*      */     
/* 1186 */     if (p == 16) {
/* 1187 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/* 1188 */       this.pos += this.peekedNumberLength;
/* 1189 */     } else if (p == 8 || p == 9 || p == 10) {
/* 1190 */       if (p == 10) {
/* 1191 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/* 1193 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/* 1196 */         int i = Integer.parseInt(this.peekedString);
/* 1197 */         this.peeked = 0;
/* 1198 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1199 */         return i;
/* 1200 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/* 1204 */       throw new IllegalStateException("Expected an int but was " + peek() + locationString());
/*      */     } 
/*      */     
/* 1207 */     this.peeked = 11;
/* 1208 */     double asDouble = Double.parseDouble(this.peekedString);
/* 1209 */     int result = (int)asDouble;
/* 1210 */     if (result != asDouble) {
/* 1211 */       throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
/*      */     }
/* 1213 */     this.peekedString = null;
/* 1214 */     this.peeked = 0;
/* 1215 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1216 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1223 */     this.peeked = 0;
/* 1224 */     this.stack[0] = 8;
/* 1225 */     this.stackSize = 1;
/* 1226 */     this.in.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipValue() throws IOException {
/* 1235 */     int count = 0;
/*      */     do {
/* 1237 */       int p = this.peeked;
/* 1238 */       if (p == 0) {
/* 1239 */         p = doPeek();
/*      */       }
/*      */       
/* 1242 */       if (p == 3) {
/* 1243 */         push(1);
/* 1244 */         count++;
/* 1245 */       } else if (p == 1) {
/* 1246 */         push(3);
/* 1247 */         count++;
/* 1248 */       } else if (p == 4) {
/* 1249 */         this.stackSize--;
/* 1250 */         count--;
/* 1251 */       } else if (p == 2) {
/* 1252 */         this.stackSize--;
/* 1253 */         count--;
/* 1254 */       } else if (p == 14 || p == 10) {
/* 1255 */         skipUnquotedValue();
/* 1256 */       } else if (p == 8 || p == 12) {
/* 1257 */         skipQuotedValue('\'');
/* 1258 */       } else if (p == 9 || p == 13) {
/* 1259 */         skipQuotedValue('"');
/* 1260 */       } else if (p == 16) {
/* 1261 */         this.pos += this.peekedNumberLength;
/*      */       } 
/* 1263 */       this.peeked = 0;
/* 1264 */     } while (count != 0);
/*      */     
/* 1266 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1267 */     this.pathNames[this.stackSize - 1] = "null";
/*      */   }
/*      */   
/*      */   private void push(int newTop) {
/* 1271 */     if (this.stackSize == this.stack.length) {
/* 1272 */       int newLength = this.stackSize * 2;
/* 1273 */       this.stack = Arrays.copyOf(this.stack, newLength);
/* 1274 */       this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
/* 1275 */       this.pathNames = Arrays.<String>copyOf(this.pathNames, newLength);
/*      */     } 
/* 1277 */     this.stack[this.stackSize++] = newTop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillBuffer(int minimum) throws IOException {
/* 1286 */     char[] buffer = this.buffer;
/* 1287 */     this.lineStart -= this.pos;
/* 1288 */     if (this.limit != this.pos) {
/* 1289 */       this.limit -= this.pos;
/* 1290 */       System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
/*      */     } else {
/* 1292 */       this.limit = 0;
/*      */     } 
/*      */     
/* 1295 */     this.pos = 0;
/*      */     int total;
/* 1297 */     while ((total = this.in.read(buffer, this.limit, buffer.length - this.limit)) != -1) {
/* 1298 */       this.limit += total;
/*      */ 
/*      */       
/* 1301 */       if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == '﻿') {
/* 1302 */         this.pos++;
/* 1303 */         this.lineStart++;
/* 1304 */         minimum++;
/*      */       } 
/*      */       
/* 1307 */       if (this.limit >= minimum) {
/* 1308 */         return true;
/*      */       }
/*      */     } 
/* 1311 */     return false;
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
/*      */   private int nextNonWhitespace(boolean throwOnEof) throws IOException {
/* 1329 */     char[] buffer = this.buffer;
/* 1330 */     int p = this.pos;
/* 1331 */     int l = this.limit;
/*      */     while (true) {
/* 1333 */       if (p == l) {
/* 1334 */         this.pos = p;
/* 1335 */         if (!fillBuffer(1)) {
/*      */           break;
/*      */         }
/* 1338 */         p = this.pos;
/* 1339 */         l = this.limit;
/*      */       } 
/*      */       
/* 1342 */       int c = buffer[p++];
/* 1343 */       if (c == 10) {
/* 1344 */         this.lineNumber++;
/* 1345 */         this.lineStart = p; continue;
/*      */       } 
/* 1347 */       if (c == 32 || c == 13 || c == 9) {
/*      */         continue;
/*      */       }
/*      */       
/* 1351 */       if (c == 47) {
/* 1352 */         this.pos = p;
/* 1353 */         if (p == l) {
/* 1354 */           this.pos--;
/* 1355 */           boolean charsLoaded = fillBuffer(2);
/* 1356 */           this.pos++;
/* 1357 */           if (!charsLoaded) {
/* 1358 */             return c;
/*      */           }
/*      */         } 
/*      */         
/* 1362 */         checkLenient();
/* 1363 */         char peek = buffer[this.pos];
/* 1364 */         switch (peek) {
/*      */           
/*      */           case '*':
/* 1367 */             this.pos++;
/* 1368 */             if (!skipTo("*/")) {
/* 1369 */               throw syntaxError("Unterminated comment");
/*      */             }
/* 1371 */             p = this.pos + 2;
/* 1372 */             l = this.limit;
/*      */             continue;
/*      */ 
/*      */           
/*      */           case '/':
/* 1377 */             this.pos++;
/* 1378 */             skipToEndOfLine();
/* 1379 */             p = this.pos;
/* 1380 */             l = this.limit;
/*      */             continue;
/*      */         } 
/*      */         
/* 1384 */         return c;
/*      */       } 
/* 1386 */       if (c == 35) {
/* 1387 */         this.pos = p;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1393 */         checkLenient();
/* 1394 */         skipToEndOfLine();
/* 1395 */         p = this.pos;
/* 1396 */         l = this.limit; continue;
/*      */       } 
/* 1398 */       this.pos = p;
/* 1399 */       return c;
/*      */     } 
/*      */     
/* 1402 */     if (throwOnEof) {
/* 1403 */       throw new EOFException("End of input" + locationString());
/*      */     }
/* 1405 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLenient() throws IOException {
/* 1410 */     if (!this.lenient) {
/* 1411 */       throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipToEndOfLine() throws IOException {
/* 1421 */     while (this.pos < this.limit || fillBuffer(1)) {
/* 1422 */       char c = this.buffer[this.pos++];
/* 1423 */       if (c == '\n') {
/* 1424 */         this.lineNumber++;
/* 1425 */         this.lineStart = this.pos; break;
/*      */       } 
/* 1427 */       if (c == '\r') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipTo(String toFind) throws IOException {
/* 1437 */     int length = toFind.length();
/*      */     
/* 1439 */     for (; this.pos + length <= this.limit || fillBuffer(length); this.pos++) {
/* 1440 */       if (this.buffer[this.pos] == '\n') {
/* 1441 */         this.lineNumber++;
/* 1442 */         this.lineStart = this.pos + 1;
/*      */       } else {
/*      */         
/* 1445 */         int c = 0; while (true) { if (c < length) {
/* 1446 */             if (this.buffer[this.pos + c] != toFind.charAt(c))
/*      */               break;  c++;
/*      */             continue;
/*      */           } 
/* 1450 */           return true; } 
/*      */       } 
/* 1452 */     }  return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1456 */     return getClass().getSimpleName() + locationString();
/*      */   }
/*      */   
/*      */   String locationString() {
/* 1460 */     int line = this.lineNumber + 1;
/* 1461 */     int column = this.pos - this.lineStart + 1;
/* 1462 */     return " at line " + line + " column " + column + " path " + getPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/* 1470 */     StringBuilder result = (new StringBuilder()).append('$');
/* 1471 */     for (int i = 0, size = this.stackSize; i < size; i++) {
/* 1472 */       switch (this.stack[i]) {
/*      */         case 1:
/*      */         case 2:
/* 1475 */           result.append('[').append(this.pathIndices[i]).append(']');
/*      */           break;
/*      */         
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/* 1481 */           result.append('.');
/* 1482 */           if (this.pathNames[i] != null) {
/* 1483 */             result.append(this.pathNames[i]);
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1493 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char readEscapeCharacter() throws IOException {
/*      */     char result;
/*      */     int i, end;
/* 1506 */     if (this.pos == this.limit && !fillBuffer(1)) {
/* 1507 */       throw syntaxError("Unterminated escape sequence");
/*      */     }
/*      */     
/* 1510 */     char escaped = this.buffer[this.pos++];
/* 1511 */     switch (escaped) {
/*      */       case 'u':
/* 1513 */         if (this.pos + 4 > this.limit && !fillBuffer(4)) {
/* 1514 */           throw syntaxError("Unterminated escape sequence");
/*      */         }
/*      */         
/* 1517 */         result = Character.MIN_VALUE;
/* 1518 */         for (i = this.pos, end = i + 4; i < end; i++) {
/* 1519 */           char c = this.buffer[i];
/* 1520 */           result = (char)(result << 4);
/* 1521 */           if (c >= '0' && c <= '9') {
/* 1522 */             result = (char)(result + c - 48);
/* 1523 */           } else if (c >= 'a' && c <= 'f') {
/* 1524 */             result = (char)(result + c - 97 + 10);
/* 1525 */           } else if (c >= 'A' && c <= 'F') {
/* 1526 */             result = (char)(result + c - 65 + 10);
/*      */           } else {
/* 1528 */             throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
/*      */           } 
/*      */         } 
/* 1531 */         this.pos += 4;
/* 1532 */         return result;
/*      */       
/*      */       case 't':
/* 1535 */         return '\t';
/*      */       
/*      */       case 'b':
/* 1538 */         return '\b';
/*      */       
/*      */       case 'n':
/* 1541 */         return '\n';
/*      */       
/*      */       case 'r':
/* 1544 */         return '\r';
/*      */       
/*      */       case 'f':
/* 1547 */         return '\f';
/*      */       
/*      */       case '\n':
/* 1550 */         this.lineNumber++;
/* 1551 */         this.lineStart = this.pos;
/*      */ 
/*      */       
/*      */       case '"':
/*      */       case '\'':
/*      */       case '/':
/*      */       case '\\':
/* 1558 */         return escaped;
/*      */     } 
/*      */     
/* 1561 */     throw syntaxError("Invalid escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IOException syntaxError(String message) throws IOException {
/* 1570 */     throw new MalformedJsonException(message + locationString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeNonExecutePrefix() throws IOException {
/* 1578 */     nextNonWhitespace(true);
/*      */ 
/*      */     
/* 1581 */     int p = --this.pos;
/* 1582 */     if (p + 5 > this.limit && !fillBuffer(5)) {
/*      */       return;
/*      */     }
/*      */     
/* 1586 */     char[] buf = this.buffer;
/* 1587 */     if (buf[p] != ')' || buf[p + 1] != ']' || buf[p + 2] != '}' || buf[p + 3] != '\'' || buf[p + 4] != '\n') {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1592 */     this.pos += 5;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\vendor\gson\stream\JsonReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */