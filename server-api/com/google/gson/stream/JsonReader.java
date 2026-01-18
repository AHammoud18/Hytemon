/*      */ package com.google.gson.stream;
/*      */ 
/*      */ import com.google.gson.Strictness;
/*      */ import com.google.gson.internal.JsonReaderInternalAccess;
/*      */ import com.google.gson.internal.TroubleshootingGuide;
/*      */ import com.google.gson.internal.bind.JsonTreeReader;
/*      */ import java.io.Closeable;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.util.Arrays;
/*      */ import java.util.Objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  254 */   private Strictness strictness = Strictness.LEGACY_STRICT;
/*      */   
/*      */   static final int DEFAULT_NESTING_LIMIT = 255;
/*      */   
/*  258 */   private int nestingLimit = 255;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int BUFFER_SIZE = 1024;
/*      */ 
/*      */ 
/*      */   
/*  267 */   private final char[] buffer = new char[1024];
/*      */   
/*  269 */   private int pos = 0;
/*  270 */   private int limit = 0;
/*      */   
/*  272 */   private int lineNumber = 0;
/*  273 */   private int lineStart = 0;
/*      */   
/*  275 */   int peeked = 0;
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
/*      */   private int peekedNumberLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String peekedString;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   private int[] stack = new int[32];
/*      */   
/*  298 */   private int stackSize = 0; private String[] pathNames; private int[] pathIndices;
/*      */   
/*      */   public JsonReader(Reader in) {
/*  301 */     this.stack[this.stackSize++] = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  312 */     this.pathNames = new String[32];
/*  313 */     this.pathIndices = new int[32];
/*      */ 
/*      */ 
/*      */     
/*  317 */     this.in = Objects.<Reader>requireNonNull(in, "in == null");
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
/*      */   @Deprecated
/*      */   public final void setLenient(boolean lenient) {
/*  338 */     setStrictness(lenient ? Strictness.LENIENT : Strictness.LEGACY_STRICT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isLenient() {
/*  347 */     return (this.strictness == Strictness.LENIENT);
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
/*      */   public final void setStrictness(Strictness strictness) {
/*  405 */     Objects.requireNonNull(strictness);
/*  406 */     this.strictness = strictness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Strictness getStrictness() {
/*  416 */     return this.strictness;
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
/*      */   public final void setNestingLimit(int limit) {
/*  438 */     if (limit < 0) {
/*  439 */       throw new IllegalArgumentException("Invalid nesting limit: " + limit);
/*      */     }
/*  441 */     this.nestingLimit = limit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNestingLimit() {
/*  451 */     return this.nestingLimit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginArray() throws IOException {
/*  461 */     int p = this.peeked;
/*  462 */     if (p == 0) {
/*  463 */       p = doPeek();
/*      */     }
/*  465 */     if (p == 3) {
/*  466 */       push(1);
/*  467 */       this.pathIndices[this.stackSize - 1] = 0;
/*  468 */       this.peeked = 0;
/*      */     } else {
/*  470 */       throw unexpectedTokenError("BEGIN_ARRAY");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endArray() throws IOException {
/*  481 */     int p = this.peeked;
/*  482 */     if (p == 0) {
/*  483 */       p = doPeek();
/*      */     }
/*  485 */     if (p == 4) {
/*  486 */       this.stackSize--;
/*  487 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  488 */       this.peeked = 0;
/*      */     } else {
/*  490 */       throw unexpectedTokenError("END_ARRAY");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginObject() throws IOException {
/*  501 */     int p = this.peeked;
/*  502 */     if (p == 0) {
/*  503 */       p = doPeek();
/*      */     }
/*  505 */     if (p == 1) {
/*  506 */       push(3);
/*  507 */       this.peeked = 0;
/*      */     } else {
/*  509 */       throw unexpectedTokenError("BEGIN_OBJECT");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endObject() throws IOException {
/*  520 */     int p = this.peeked;
/*  521 */     if (p == 0) {
/*  522 */       p = doPeek();
/*      */     }
/*  524 */     if (p == 2) {
/*  525 */       this.stackSize--;
/*  526 */       this.pathNames[this.stackSize] = null;
/*  527 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  528 */       this.peeked = 0;
/*      */     } else {
/*  530 */       throw unexpectedTokenError("END_OBJECT");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws IOException {
/*  536 */     int p = this.peeked;
/*  537 */     if (p == 0) {
/*  538 */       p = doPeek();
/*      */     }
/*  540 */     return (p != 2 && p != 4 && p != 17);
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonToken peek() throws IOException {
/*  545 */     int p = this.peeked;
/*  546 */     if (p == 0) {
/*  547 */       p = doPeek();
/*      */     }
/*      */     
/*  550 */     switch (p) {
/*      */       case 1:
/*  552 */         return JsonToken.BEGIN_OBJECT;
/*      */       case 2:
/*  554 */         return JsonToken.END_OBJECT;
/*      */       case 3:
/*  556 */         return JsonToken.BEGIN_ARRAY;
/*      */       case 4:
/*  558 */         return JsonToken.END_ARRAY;
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*  562 */         return JsonToken.NAME;
/*      */       case 5:
/*      */       case 6:
/*  565 */         return JsonToken.BOOLEAN;
/*      */       case 7:
/*  567 */         return JsonToken.NULL;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  572 */         return JsonToken.STRING;
/*      */       case 15:
/*      */       case 16:
/*  575 */         return JsonToken.NUMBER;
/*      */       case 17:
/*  577 */         return JsonToken.END_DOCUMENT;
/*      */     } 
/*  579 */     throw new AssertionError();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int doPeek() throws IOException {
/*  585 */     int peekStack = this.stack[this.stackSize - 1];
/*  586 */     if (peekStack == 1)
/*  587 */     { this.stack[this.stackSize - 1] = 2; }
/*  588 */     else if (peekStack == 2)
/*      */     
/*  590 */     { int i = nextNonWhitespace(true);
/*  591 */       switch (i) {
/*      */         case 93:
/*  593 */           this.peeked = 4;
/*  594 */           return this.peeked;
/*      */         case 59:
/*  596 */           checkLenient(); break;
/*      */         case 44:
/*      */           break;
/*      */         default:
/*  600 */           throw syntaxError("Unterminated array");
/*      */       }  }
/*  602 */     else { if (peekStack == 3 || peekStack == 5) {
/*  603 */         this.stack[this.stackSize - 1] = 4;
/*      */         
/*  605 */         if (peekStack == 5) {
/*  606 */           int j = nextNonWhitespace(true);
/*  607 */           switch (j) {
/*      */             case 125:
/*  609 */               this.peeked = 2;
/*  610 */               return this.peeked;
/*      */             case 59:
/*  612 */               checkLenient(); break;
/*      */             case 44:
/*      */               break;
/*      */             default:
/*  616 */               throw syntaxError("Unterminated object");
/*      */           } 
/*      */         } 
/*  619 */         int i = nextNonWhitespace(true);
/*  620 */         switch (i) {
/*      */           case 34:
/*  622 */             this.peeked = 13;
/*  623 */             return this.peeked;
/*      */           case 39:
/*  625 */             checkLenient();
/*  626 */             this.peeked = 12;
/*  627 */             return this.peeked;
/*      */           case 125:
/*  629 */             if (peekStack != 5) {
/*  630 */               this.peeked = 2;
/*  631 */               return this.peeked;
/*      */             } 
/*  633 */             throw syntaxError("Expected name");
/*      */         } 
/*      */         
/*  636 */         checkLenient();
/*  637 */         this.pos--;
/*  638 */         if (isLiteral((char)i)) {
/*  639 */           this.peeked = 14;
/*  640 */           return this.peeked;
/*      */         } 
/*  642 */         throw syntaxError("Expected name");
/*      */       } 
/*      */       
/*  645 */       if (peekStack == 4) {
/*  646 */         this.stack[this.stackSize - 1] = 5;
/*      */         
/*  648 */         int i = nextNonWhitespace(true);
/*  649 */         switch (i) {
/*      */           case 58:
/*      */             break;
/*      */           case 61:
/*  653 */             checkLenient();
/*  654 */             if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
/*  655 */               this.pos++;
/*      */             }
/*      */             break;
/*      */           default:
/*  659 */             throw syntaxError("Expected ':'");
/*      */         } 
/*  661 */       } else if (peekStack == 6) {
/*  662 */         if (this.strictness == Strictness.LENIENT) {
/*  663 */           consumeNonExecutePrefix();
/*      */         }
/*  665 */         this.stack[this.stackSize - 1] = 7;
/*  666 */       } else if (peekStack == 7) {
/*  667 */         int i = nextNonWhitespace(false);
/*  668 */         if (i == -1) {
/*  669 */           this.peeked = 17;
/*  670 */           return this.peeked;
/*      */         } 
/*  672 */         checkLenient();
/*  673 */         this.pos--;
/*      */       }
/*  675 */       else if (peekStack == 8) {
/*  676 */         throw new IllegalStateException("JsonReader is closed");
/*      */       }  }
/*      */     
/*  679 */     int c = nextNonWhitespace(true);
/*  680 */     switch (c) {
/*      */       case 93:
/*  682 */         if (peekStack == 1) {
/*  683 */           this.peeked = 4;
/*  684 */           return this.peeked;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 44:
/*      */       case 59:
/*  690 */         if (peekStack == 1 || peekStack == 2) {
/*  691 */           checkLenient();
/*  692 */           this.pos--;
/*  693 */           this.peeked = 7;
/*  694 */           return this.peeked;
/*      */         } 
/*  696 */         throw syntaxError("Unexpected value");
/*      */       
/*      */       case 39:
/*  699 */         checkLenient();
/*  700 */         this.peeked = 8;
/*  701 */         return this.peeked;
/*      */       case 34:
/*  703 */         this.peeked = 9;
/*  704 */         return this.peeked;
/*      */       case 91:
/*  706 */         this.peeked = 3;
/*  707 */         return this.peeked;
/*      */       case 123:
/*  709 */         this.peeked = 1;
/*  710 */         return this.peeked;
/*      */     } 
/*  712 */     this.pos--;
/*      */ 
/*      */     
/*  715 */     int result = peekKeyword();
/*  716 */     if (result != 0) {
/*  717 */       return result;
/*      */     }
/*      */     
/*  720 */     result = peekNumber();
/*  721 */     if (result != 0) {
/*  722 */       return result;
/*      */     }
/*      */     
/*  725 */     if (!isLiteral(this.buffer[this.pos])) {
/*  726 */       throw syntaxError("Expected value");
/*      */     }
/*      */     
/*  729 */     checkLenient();
/*  730 */     this.peeked = 10;
/*  731 */     return this.peeked;
/*      */   }
/*      */   private int peekKeyword() throws IOException {
/*      */     String keyword, keywordUpper;
/*      */     int peeking;
/*  736 */     char c = this.buffer[this.pos];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  742 */     if (c == 't' || c == 'T') {
/*  743 */       keyword = "true";
/*  744 */       keywordUpper = "TRUE";
/*  745 */       peeking = 5;
/*  746 */     } else if (c == 'f' || c == 'F') {
/*  747 */       keyword = "false";
/*  748 */       keywordUpper = "FALSE";
/*  749 */       peeking = 6;
/*  750 */     } else if (c == 'n' || c == 'N') {
/*  751 */       keyword = "null";
/*  752 */       keywordUpper = "NULL";
/*  753 */       peeking = 7;
/*      */     } else {
/*  755 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  759 */     boolean allowsUpperCased = (this.strictness != Strictness.STRICT);
/*      */ 
/*      */     
/*  762 */     int length = keyword.length();
/*  763 */     for (int i = 0; i < length; i++) {
/*  764 */       if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
/*  765 */         return 0;
/*      */       }
/*  767 */       c = this.buffer[this.pos + i];
/*  768 */       boolean matched = (c == keyword.charAt(i) || (allowsUpperCased && c == keywordUpper.charAt(i)));
/*  769 */       if (!matched) {
/*  770 */         return 0;
/*      */       }
/*      */     } 
/*      */     
/*  774 */     if ((this.pos + length < this.limit || fillBuffer(length + 1)) && isLiteral(this.buffer[this.pos + length])) {
/*  775 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  779 */     this.pos += length;
/*  780 */     this.peeked = peeking;
/*  781 */     return this.peeked;
/*      */   }
/*      */   
/*      */   private int peekNumber() throws IOException {
/*      */     int j;
/*  786 */     char[] buffer = this.buffer;
/*  787 */     int p = this.pos;
/*  788 */     int l = this.limit;
/*      */     
/*  790 */     long value = 0L;
/*  791 */     boolean negative = false;
/*  792 */     boolean fitsInLong = true;
/*  793 */     int last = 0;
/*      */     
/*  795 */     int i = 0;
/*      */ 
/*      */     
/*  798 */     for (;; i++) {
/*  799 */       if (p + i == l) {
/*  800 */         if (i == buffer.length)
/*      */         {
/*      */           
/*  803 */           return 0;
/*      */         }
/*  805 */         if (!fillBuffer(i + 1)) {
/*      */           break;
/*      */         }
/*  808 */         p = this.pos;
/*  809 */         l = this.limit;
/*      */       } 
/*      */       
/*  812 */       char c = buffer[p + i];
/*  813 */       switch (c) {
/*      */         case '-':
/*  815 */           if (last == 0) {
/*  816 */             negative = true;
/*  817 */             last = 1; break;
/*      */           } 
/*  819 */           if (last == 5) {
/*  820 */             last = 6;
/*      */             break;
/*      */           } 
/*  823 */           return 0;
/*      */         
/*      */         case '+':
/*  826 */           if (last == 5) {
/*  827 */             last = 6;
/*      */             break;
/*      */           } 
/*  830 */           return 0;
/*      */         
/*      */         case 'E':
/*      */         case 'e':
/*  834 */           if (last == 2 || last == 4) {
/*  835 */             last = 5;
/*      */             break;
/*      */           } 
/*  838 */           return 0;
/*      */         
/*      */         case '.':
/*  841 */           if (last == 2) {
/*  842 */             last = 3;
/*      */             break;
/*      */           } 
/*  845 */           return 0;
/*      */         
/*      */         default:
/*  848 */           if (c < '0' || c > '9') {
/*  849 */             if (!isLiteral(c)) {
/*      */               break;
/*      */             }
/*  852 */             return 0;
/*      */           } 
/*  854 */           if (last == 1 || last == 0) {
/*  855 */             value = -(c - 48);
/*  856 */             last = 2; break;
/*  857 */           }  if (last == 2) {
/*  858 */             if (value == 0L) {
/*  859 */               return 0;
/*      */             }
/*  861 */             long newValue = value * 10L - (c - 48);
/*  862 */             j = fitsInLong & ((value > -922337203685477580L || (value == -922337203685477580L && newValue < value)) ? 1 : 0);
/*      */ 
/*      */             
/*  865 */             value = newValue; break;
/*  866 */           }  if (last == 3) {
/*  867 */             last = 4; break;
/*  868 */           }  if (last == 5 || last == 6) {
/*  869 */             last = 7;
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  877 */     if (last == 2 && j != 0 && (value != Long.MIN_VALUE || negative) && (value != 0L || !negative)) {
/*      */ 
/*      */ 
/*      */       
/*  881 */       this.peekedLong = negative ? value : -value;
/*  882 */       this.pos += i;
/*  883 */       this.peeked = 15;
/*  884 */       return this.peeked;
/*  885 */     }  if (last == 2 || last == 4 || last == 7) {
/*      */ 
/*      */       
/*  888 */       this.peekedNumberLength = i;
/*  889 */       this.peeked = 16;
/*  890 */       return this.peeked;
/*      */     } 
/*  892 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiteral(char c) throws IOException {
/*  898 */     switch (c) {
/*      */       case '#':
/*      */       case '/':
/*      */       case ';':
/*      */       case '=':
/*      */       case '\\':
/*  904 */         checkLenient();
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
/*  916 */         return false;
/*      */     } 
/*  918 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextName() throws IOException {
/*      */     String result;
/*  928 */     int p = this.peeked;
/*  929 */     if (p == 0) {
/*  930 */       p = doPeek();
/*      */     }
/*      */     
/*  933 */     if (p == 14) {
/*  934 */       result = nextUnquotedValue();
/*  935 */     } else if (p == 12) {
/*  936 */       result = nextQuotedValue('\'');
/*  937 */     } else if (p == 13) {
/*  938 */       result = nextQuotedValue('"');
/*      */     } else {
/*  940 */       throw unexpectedTokenError("a name");
/*      */     } 
/*  942 */     this.peeked = 0;
/*  943 */     this.pathNames[this.stackSize - 1] = result;
/*  944 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextString() throws IOException {
/*      */     String result;
/*  954 */     int p = this.peeked;
/*  955 */     if (p == 0) {
/*  956 */       p = doPeek();
/*      */     }
/*      */     
/*  959 */     if (p == 10) {
/*  960 */       result = nextUnquotedValue();
/*  961 */     } else if (p == 8) {
/*  962 */       result = nextQuotedValue('\'');
/*  963 */     } else if (p == 9) {
/*  964 */       result = nextQuotedValue('"');
/*  965 */     } else if (p == 11) {
/*  966 */       result = this.peekedString;
/*  967 */       this.peekedString = null;
/*  968 */     } else if (p == 15) {
/*  969 */       result = Long.toString(this.peekedLong);
/*  970 */     } else if (p == 16) {
/*  971 */       result = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  972 */       this.pos += this.peekedNumberLength;
/*      */     } else {
/*  974 */       throw unexpectedTokenError("a string");
/*      */     } 
/*  976 */     this.peeked = 0;
/*  977 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  978 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nextBoolean() throws IOException {
/*  987 */     int p = this.peeked;
/*  988 */     if (p == 0) {
/*  989 */       p = doPeek();
/*      */     }
/*  991 */     if (p == 5) {
/*  992 */       this.peeked = 0;
/*  993 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  994 */       return true;
/*  995 */     }  if (p == 6) {
/*  996 */       this.peeked = 0;
/*  997 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  998 */       return false;
/*      */     } 
/* 1000 */     throw unexpectedTokenError("a boolean");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void nextNull() throws IOException {
/* 1009 */     int p = this.peeked;
/* 1010 */     if (p == 0) {
/* 1011 */       p = doPeek();
/*      */     }
/* 1013 */     if (p == 7) {
/* 1014 */       this.peeked = 0;
/* 1015 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*      */     } else {
/* 1017 */       throw unexpectedTokenError("null");
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
/*      */   public double nextDouble() throws IOException {
/* 1032 */     int p = this.peeked;
/* 1033 */     if (p == 0) {
/* 1034 */       p = doPeek();
/*      */     }
/*      */     
/* 1037 */     if (p == 15) {
/* 1038 */       this.peeked = 0;
/* 1039 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1040 */       return this.peekedLong;
/*      */     } 
/*      */     
/* 1043 */     if (p == 16) {
/* 1044 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/* 1045 */       this.pos += this.peekedNumberLength;
/* 1046 */     } else if (p == 8 || p == 9) {
/* 1047 */       this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/* 1048 */     } else if (p == 10) {
/* 1049 */       this.peekedString = nextUnquotedValue();
/* 1050 */     } else if (p != 11) {
/* 1051 */       throw unexpectedTokenError("a double");
/*      */     } 
/*      */     
/* 1054 */     this.peeked = 11;
/* 1055 */     double result = Double.parseDouble(this.peekedString);
/* 1056 */     if (this.strictness != Strictness.LENIENT && (Double.isNaN(result) || Double.isInfinite(result))) {
/* 1057 */       throw syntaxError("JSON forbids NaN and infinities: " + result);
/*      */     }
/* 1059 */     this.peekedString = null;
/* 1060 */     this.peeked = 0;
/* 1061 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1062 */     return result;
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
/*      */   public long nextLong() throws IOException {
/* 1075 */     int p = this.peeked;
/* 1076 */     if (p == 0) {
/* 1077 */       p = doPeek();
/*      */     }
/*      */     
/* 1080 */     if (p == 15) {
/* 1081 */       this.peeked = 0;
/* 1082 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1083 */       return this.peekedLong;
/*      */     } 
/*      */     
/* 1086 */     if (p == 16) {
/* 1087 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/* 1088 */       this.pos += this.peekedNumberLength;
/* 1089 */     } else if (p == 8 || p == 9 || p == 10) {
/* 1090 */       if (p == 10) {
/* 1091 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/* 1093 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/* 1096 */         long l = Long.parseLong(this.peekedString);
/* 1097 */         this.peeked = 0;
/* 1098 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1099 */         return l;
/* 1100 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/* 1104 */       throw unexpectedTokenError("a long");
/*      */     } 
/*      */     
/* 1107 */     this.peeked = 11;
/* 1108 */     double asDouble = Double.parseDouble(this.peekedString);
/* 1109 */     long result = (long)asDouble;
/* 1110 */     if (result != asDouble) {
/* 1111 */       throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
/*      */     }
/* 1113 */     this.peekedString = null;
/* 1114 */     this.peeked = 0;
/* 1115 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1116 */     return result;
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
/*      */   private String nextQuotedValue(char quote) throws IOException {
/* 1128 */     char[] buffer = this.buffer;
/* 1129 */     StringBuilder builder = null;
/*      */     while (true) {
/* 1131 */       int p = this.pos;
/* 1132 */       int l = this.limit;
/*      */       
/* 1134 */       int start = p;
/* 1135 */       while (p < l) {
/* 1136 */         int c = buffer[p++];
/*      */ 
/*      */ 
/*      */         
/* 1140 */         if (this.strictness == Strictness.STRICT && c < 32) {
/* 1141 */           throw syntaxError("Unescaped control characters (\\u0000-\\u001F) are not allowed in strict mode");
/*      */         }
/* 1143 */         if (c == quote) {
/* 1144 */           this.pos = p;
/* 1145 */           int len = p - start - 1;
/* 1146 */           if (builder == null) {
/* 1147 */             return new String(buffer, start, len);
/*      */           }
/* 1149 */           builder.append(buffer, start, len);
/* 1150 */           return builder.toString();
/*      */         } 
/* 1152 */         if (c == 92) {
/* 1153 */           this.pos = p;
/* 1154 */           int len = p - start - 1;
/* 1155 */           if (builder == null) {
/* 1156 */             int estimatedLength = (len + 1) * 2;
/* 1157 */             builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */           } 
/* 1159 */           builder.append(buffer, start, len);
/* 1160 */           builder.append(readEscapeCharacter());
/* 1161 */           p = this.pos;
/* 1162 */           l = this.limit;
/* 1163 */           start = p; continue;
/* 1164 */         }  if (c == 10) {
/* 1165 */           this.lineNumber++;
/* 1166 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/*      */       
/* 1170 */       if (builder == null) {
/* 1171 */         int estimatedLength = (p - start) * 2;
/* 1172 */         builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */       } 
/* 1174 */       builder.append(buffer, start, p - start);
/* 1175 */       this.pos = p;
/* 1176 */       if (!fillBuffer(1)) {
/* 1177 */         throw syntaxError("Unterminated string");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String nextUnquotedValue() throws IOException {
/* 1185 */     StringBuilder builder = null;
/* 1186 */     int i = 0;
/*      */ 
/*      */     
/*      */     label34: while (true) {
/* 1190 */       for (; this.pos + i < this.limit; i++)
/* 1191 */       { switch (this.buffer[this.pos + i])
/*      */         { case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1197 */             checkLenient(); break label34;
/*      */           case '\t': break label34;
/*      */           case '\n': break label34;
/*      */           case '\f': break label34;
/*      */           case '\r':
/*      */             break label34;
/*      */           case ' ':
/*      */             break label34;
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
/* 1216 */             break label34; }  }  if (i < this.buffer.length) {
/* 1217 */         if (fillBuffer(i + 1)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1225 */       if (builder == null) {
/* 1226 */         builder = new StringBuilder(Math.max(i, 16));
/*      */       }
/* 1228 */       builder.append(this.buffer, this.pos, i);
/* 1229 */       this.pos += i;
/* 1230 */       i = 0;
/* 1231 */       if (!fillBuffer(1)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1237 */     String result = (builder == null) ? new String(this.buffer, this.pos, i) : builder.append(this.buffer, this.pos, i).toString();
/* 1238 */     this.pos += i;
/* 1239 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipQuotedValue(char quote) throws IOException {
/* 1244 */     char[] buffer = this.buffer;
/*      */     while (true) {
/* 1246 */       int p = this.pos;
/* 1247 */       int l = this.limit;
/*      */       
/* 1249 */       while (p < l) {
/* 1250 */         int c = buffer[p++];
/* 1251 */         if (c == quote) {
/* 1252 */           this.pos = p; return;
/*      */         } 
/* 1254 */         if (c == 92) {
/* 1255 */           this.pos = p;
/* 1256 */           char unused = readEscapeCharacter();
/* 1257 */           p = this.pos;
/* 1258 */           l = this.limit; continue;
/* 1259 */         }  if (c == 10) {
/* 1260 */           this.lineNumber++;
/* 1261 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/* 1264 */       this.pos = p;
/* 1265 */       if (!fillBuffer(1))
/* 1266 */         throw syntaxError("Unterminated string"); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void skipUnquotedValue() throws IOException {
/*      */     do {
/* 1272 */       int i = 0;
/* 1273 */       for (; this.pos + i < this.limit; i++) {
/* 1274 */         switch (this.buffer[this.pos + i]) {
/*      */           case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1280 */             checkLenient();
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
/* 1292 */             this.pos += i;
/*      */             return;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 1298 */       this.pos += i;
/* 1299 */     } while (fillBuffer(1));
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
/*      */   public int nextInt() throws IOException {
/* 1312 */     int p = this.peeked;
/* 1313 */     if (p == 0) {
/* 1314 */       p = doPeek();
/*      */     }
/*      */ 
/*      */     
/* 1318 */     if (p == 15) {
/* 1319 */       int i = (int)this.peekedLong;
/* 1320 */       if (this.peekedLong != i) {
/* 1321 */         throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
/*      */       }
/* 1323 */       this.peeked = 0;
/* 1324 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1325 */       return i;
/*      */     } 
/*      */     
/* 1328 */     if (p == 16) {
/* 1329 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/* 1330 */       this.pos += this.peekedNumberLength;
/* 1331 */     } else if (p == 8 || p == 9 || p == 10) {
/* 1332 */       if (p == 10) {
/* 1333 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/* 1335 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/* 1338 */         int i = Integer.parseInt(this.peekedString);
/* 1339 */         this.peeked = 0;
/* 1340 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1341 */         return i;
/* 1342 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/* 1346 */       throw unexpectedTokenError("an int");
/*      */     } 
/*      */     
/* 1349 */     this.peeked = 11;
/* 1350 */     double asDouble = Double.parseDouble(this.peekedString);
/* 1351 */     int result = (int)asDouble;
/* 1352 */     if (result != asDouble) {
/* 1353 */       throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
/*      */     }
/* 1355 */     this.peekedString = null;
/* 1356 */     this.peeked = 0;
/* 1357 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1358 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1369 */     this.peeked = 0;
/* 1370 */     this.stack[0] = 8;
/* 1371 */     this.stackSize = 1;
/* 1372 */     this.in.close();
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
/*      */   public void skipValue() throws IOException {
/* 1392 */     int count = 0;
/*      */     do {
/* 1394 */       int p = this.peeked;
/* 1395 */       if (p == 0) {
/* 1396 */         p = doPeek();
/*      */       }
/*      */       
/* 1399 */       switch (p) {
/*      */         case 3:
/* 1401 */           push(1);
/* 1402 */           count++;
/*      */           break;
/*      */         case 1:
/* 1405 */           push(3);
/* 1406 */           count++;
/*      */           break;
/*      */         case 4:
/* 1409 */           this.stackSize--;
/* 1410 */           count--;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1415 */           if (count == 0)
/*      */           {
/* 1417 */             this.pathNames[this.stackSize - 1] = null;
/*      */           }
/* 1419 */           this.stackSize--;
/* 1420 */           count--;
/*      */           break;
/*      */         case 10:
/* 1423 */           skipUnquotedValue();
/*      */           break;
/*      */         case 8:
/* 1426 */           skipQuotedValue('\'');
/*      */           break;
/*      */         case 9:
/* 1429 */           skipQuotedValue('"');
/*      */           break;
/*      */         case 14:
/* 1432 */           skipUnquotedValue();
/*      */           
/* 1434 */           if (count == 0) {
/* 1435 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 12:
/* 1439 */           skipQuotedValue('\'');
/*      */           
/* 1441 */           if (count == 0) {
/* 1442 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 13:
/* 1446 */           skipQuotedValue('"');
/*      */           
/* 1448 */           if (count == 0) {
/* 1449 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 16:
/* 1453 */           this.pos += this.peekedNumberLength;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 17:
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1462 */       this.peeked = 0;
/* 1463 */     } while (count > 0);
/*      */     
/* 1465 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void push(int newTop) throws MalformedJsonException {
/* 1470 */     if (this.stackSize - 1 >= this.nestingLimit) {
/* 1471 */       throw new MalformedJsonException("Nesting limit " + this.nestingLimit + " reached" + 
/* 1472 */           locationString());
/*      */     }
/*      */     
/* 1475 */     if (this.stackSize == this.stack.length) {
/* 1476 */       int newLength = this.stackSize * 2;
/* 1477 */       this.stack = Arrays.copyOf(this.stack, newLength);
/* 1478 */       this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
/* 1479 */       this.pathNames = Arrays.<String>copyOf(this.pathNames, newLength);
/*      */     } 
/* 1481 */     this.stack[this.stackSize++] = newTop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillBuffer(int minimum) throws IOException {
/* 1489 */     char[] buffer = this.buffer;
/* 1490 */     this.lineStart -= this.pos;
/* 1491 */     if (this.limit != this.pos) {
/* 1492 */       this.limit -= this.pos;
/* 1493 */       System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
/*      */     } else {
/* 1495 */       this.limit = 0;
/*      */     } 
/*      */     
/* 1498 */     this.pos = 0;
/*      */     int total;
/* 1500 */     while ((total = this.in.read(buffer, this.limit, buffer.length - this.limit)) != -1) {
/* 1501 */       this.limit += total;
/*      */ 
/*      */       
/* 1504 */       if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == '﻿') {
/* 1505 */         this.pos++;
/* 1506 */         this.lineStart++;
/* 1507 */         minimum++;
/*      */       } 
/*      */       
/* 1510 */       if (this.limit >= minimum) {
/* 1511 */         return true;
/*      */       }
/*      */     } 
/* 1514 */     return false;
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
/*      */   private int nextNonWhitespace(boolean throwOnEof) throws IOException {
/* 1531 */     char[] buffer = this.buffer;
/* 1532 */     int p = this.pos;
/* 1533 */     int l = this.limit;
/*      */     while (true) {
/* 1535 */       if (p == l) {
/* 1536 */         this.pos = p;
/* 1537 */         if (!fillBuffer(1)) {
/*      */           break;
/*      */         }
/* 1540 */         p = this.pos;
/* 1541 */         l = this.limit;
/*      */       } 
/*      */       
/* 1544 */       int c = buffer[p++];
/* 1545 */       if (c == 10) {
/* 1546 */         this.lineNumber++;
/* 1547 */         this.lineStart = p; continue;
/*      */       } 
/* 1549 */       if (c == 32 || c == 13 || c == 9) {
/*      */         continue;
/*      */       }
/*      */       
/* 1553 */       if (c == 47) {
/* 1554 */         this.pos = p;
/* 1555 */         if (p == l) {
/* 1556 */           this.pos--;
/* 1557 */           boolean charsLoaded = fillBuffer(2);
/* 1558 */           this.pos++;
/* 1559 */           if (!charsLoaded) {
/* 1560 */             return c;
/*      */           }
/*      */         } 
/*      */         
/* 1564 */         checkLenient();
/* 1565 */         char peek = buffer[this.pos];
/* 1566 */         switch (peek) {
/*      */           
/*      */           case '*':
/* 1569 */             this.pos++;
/* 1570 */             if (!skipTo("*/")) {
/* 1571 */               throw syntaxError("Unterminated comment");
/*      */             }
/* 1573 */             p = this.pos + 2;
/* 1574 */             l = this.limit;
/*      */             continue;
/*      */ 
/*      */           
/*      */           case '/':
/* 1579 */             this.pos++;
/* 1580 */             skipToEndOfLine();
/* 1581 */             p = this.pos;
/* 1582 */             l = this.limit;
/*      */             continue;
/*      */         } 
/*      */         
/* 1586 */         return c;
/*      */       } 
/* 1588 */       if (c == 35) {
/* 1589 */         this.pos = p;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1595 */         checkLenient();
/* 1596 */         skipToEndOfLine();
/* 1597 */         p = this.pos;
/* 1598 */         l = this.limit; continue;
/*      */       } 
/* 1600 */       this.pos = p;
/* 1601 */       return c;
/*      */     } 
/*      */     
/* 1604 */     if (throwOnEof) {
/* 1605 */       throw new EOFException("End of input" + locationString());
/*      */     }
/* 1607 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLenient() throws MalformedJsonException {
/* 1612 */     if (this.strictness != Strictness.LENIENT) {
/* 1613 */       throw syntaxError("Use JsonReader.setStrictness(Strictness.LENIENT) to accept malformed JSON");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipToEndOfLine() throws IOException {
/* 1623 */     while (this.pos < this.limit || fillBuffer(1)) {
/* 1624 */       char c = this.buffer[this.pos++];
/* 1625 */       if (c == '\n') {
/* 1626 */         this.lineNumber++;
/* 1627 */         this.lineStart = this.pos; break;
/*      */       } 
/* 1629 */       if (c == '\r') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipTo(String toFind) throws IOException {
/* 1639 */     int length = toFind.length();
/*      */     
/* 1641 */     for (; this.pos + length <= this.limit || fillBuffer(length); this.pos++) {
/* 1642 */       if (this.buffer[this.pos] == '\n') {
/* 1643 */         this.lineNumber++;
/* 1644 */         this.lineStart = this.pos + 1;
/*      */       } else {
/*      */         
/* 1647 */         int c = 0; while (true) { if (c < length) {
/* 1648 */             if (this.buffer[this.pos + c] != toFind.charAt(c))
/*      */               break;  c++;
/*      */             continue;
/*      */           } 
/* 1652 */           return true; } 
/*      */       } 
/* 1654 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1659 */     return getClass().getSimpleName() + locationString();
/*      */   }
/*      */   
/*      */   String locationString() {
/* 1663 */     int line = this.lineNumber + 1;
/* 1664 */     int column = this.pos - this.lineStart + 1;
/* 1665 */     return " at line " + line + " column " + column + " path " + getPath();
/*      */   }
/*      */   
/*      */   private String getPath(boolean usePreviousPath) {
/* 1669 */     StringBuilder result = (new StringBuilder()).append('$');
/* 1670 */     for (int i = 0; i < this.stackSize; i++) {
/* 1671 */       int pathIndex, scope = this.stack[i];
/* 1672 */       switch (scope) {
/*      */         case 1:
/*      */         case 2:
/* 1675 */           pathIndex = this.pathIndices[i];
/*      */           
/* 1677 */           if (usePreviousPath && pathIndex > 0 && i == this.stackSize - 1) {
/* 1678 */             pathIndex--;
/*      */           }
/* 1680 */           result.append('[').append(pathIndex).append(']');
/*      */           break;
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/* 1685 */           result.append('.');
/* 1686 */           if (this.pathNames[i] != null) {
/* 1687 */             result.append(this.pathNames[i]);
/*      */           }
/*      */           break;
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */           break;
/*      */         default:
/* 1695 */           throw new AssertionError("Unknown scope value: " + scope);
/*      */       } 
/*      */     } 
/* 1698 */     return result.toString();
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
/*      */   public String getPath() {
/* 1716 */     return getPath(false);
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
/*      */   public String getPreviousPath() {
/* 1734 */     return getPath(true);
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
/*      */     int result, i, end;
/* 1746 */     if (this.pos == this.limit && !fillBuffer(1)) {
/* 1747 */       throw syntaxError("Unterminated escape sequence");
/*      */     }
/*      */     
/* 1750 */     char escaped = this.buffer[this.pos++];
/* 1751 */     switch (escaped) {
/*      */       case 'u':
/* 1753 */         if (this.pos + 4 > this.limit && !fillBuffer(4)) {
/* 1754 */           throw syntaxError("Unterminated escape sequence");
/*      */         }
/*      */         
/* 1757 */         result = 0;
/* 1758 */         for (i = this.pos, end = i + 4; i < end; i++) {
/* 1759 */           char c = this.buffer[i];
/* 1760 */           result <<= 4;
/* 1761 */           if (c >= '0' && c <= '9') {
/* 1762 */             result += c - 48;
/* 1763 */           } else if (c >= 'a' && c <= 'f') {
/* 1764 */             result += c - 97 + 10;
/* 1765 */           } else if (c >= 'A' && c <= 'F') {
/* 1766 */             result += c - 65 + 10;
/*      */           } else {
/* 1768 */             throw syntaxError("Malformed Unicode escape \\u" + new String(this.buffer, this.pos, 4));
/*      */           } 
/*      */         } 
/* 1771 */         this.pos += 4;
/* 1772 */         return (char)result;
/*      */       
/*      */       case 't':
/* 1775 */         return '\t';
/*      */       
/*      */       case 'b':
/* 1778 */         return '\b';
/*      */       
/*      */       case 'n':
/* 1781 */         return '\n';
/*      */       
/*      */       case 'r':
/* 1784 */         return '\r';
/*      */       
/*      */       case 'f':
/* 1787 */         return '\f';
/*      */       
/*      */       case '\n':
/* 1790 */         if (this.strictness == Strictness.STRICT) {
/* 1791 */           throw syntaxError("Cannot escape a newline character in strict mode");
/*      */         }
/* 1793 */         this.lineNumber++;
/* 1794 */         this.lineStart = this.pos;
/*      */ 
/*      */       
/*      */       case '\'':
/* 1798 */         if (this.strictness == Strictness.STRICT) {
/* 1799 */           throw syntaxError("Invalid escaped character \"'\" in strict mode");
/*      */         }
/*      */       case '"':
/*      */       case '/':
/*      */       case '\\':
/* 1804 */         return escaped;
/*      */     } 
/*      */     
/* 1807 */     throw syntaxError("Invalid escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MalformedJsonException syntaxError(String message) throws MalformedJsonException {
/* 1816 */     throw new MalformedJsonException(message + 
/* 1817 */         locationString() + "\nSee " + TroubleshootingGuide.createUrl("malformed-json"));
/*      */   }
/*      */   
/*      */   private IllegalStateException unexpectedTokenError(String expected) throws IOException {
/* 1821 */     JsonToken peeked = peek();
/*      */     
/* 1823 */     String troubleshootingId = (peeked == JsonToken.NULL) ? "adapter-not-null-safe" : "unexpected-json-structure";
/* 1824 */     return new IllegalStateException("Expected " + expected + " but was " + 
/*      */ 
/*      */ 
/*      */         
/* 1828 */         peek() + 
/* 1829 */         locationString() + "\nSee " + 
/*      */         
/* 1831 */         TroubleshootingGuide.createUrl(troubleshootingId));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeNonExecutePrefix() throws IOException {
/* 1837 */     int unused = nextNonWhitespace(true);
/* 1838 */     this.pos--;
/*      */     
/* 1840 */     if (this.pos + 5 > this.limit && !fillBuffer(5)) {
/*      */       return;
/*      */     }
/*      */     
/* 1844 */     int p = this.pos;
/* 1845 */     char[] buf = this.buffer;
/* 1846 */     if (buf[p] != ')' || buf[p + 1] != ']' || buf[p + 2] != '}' || buf[p + 3] != '\'' || buf[p + 4] != '\n') {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1855 */     this.pos += 5;
/*      */   }
/*      */   
/*      */   static {
/* 1859 */     JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess()
/*      */       {
/*      */         public void promoteNameToValue(JsonReader reader) throws IOException
/*      */         {
/* 1863 */           if (reader instanceof JsonTreeReader) {
/* 1864 */             ((JsonTreeReader)reader).promoteNameToValue();
/*      */             return;
/*      */           } 
/* 1867 */           int p = reader.peeked;
/* 1868 */           if (p == 0) {
/* 1869 */             p = reader.doPeek();
/*      */           }
/* 1871 */           if (p == 13) {
/* 1872 */             reader.peeked = 9;
/* 1873 */           } else if (p == 12) {
/* 1874 */             reader.peeked = 8;
/* 1875 */           } else if (p == 14) {
/* 1876 */             reader.peeked = 10;
/*      */           } else {
/* 1878 */             throw reader.unexpectedTokenError("a name");
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\stream\JsonReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */