/*     */ package org.bson.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import org.bson.BsonRegularExpression;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JsonScanner
/*     */ {
/*     */   private final JsonBuffer buffer;
/*     */   
/*     */   JsonScanner(JsonBuffer buffer) {
/*  33 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   JsonScanner(String json) {
/*  37 */     this(new JsonStringBuffer(json));
/*     */   }
/*     */   
/*     */   JsonScanner(Reader reader) {
/*  41 */     this(new JsonStreamBuffer(reader));
/*     */   }
/*     */   
/*     */   public void reset(int markPos) {
/*  45 */     this.buffer.reset(markPos);
/*     */   }
/*     */   
/*     */   public int mark() {
/*  49 */     return this.buffer.mark();
/*     */   }
/*     */   
/*     */   public void discard(int markPos) {
/*  53 */     this.buffer.discard(markPos);
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
/*     */   public JsonToken nextToken() {
/*  65 */     int c = this.buffer.read();
/*  66 */     while (c != -1 && Character.isWhitespace(c)) {
/*  67 */       c = this.buffer.read();
/*     */     }
/*  69 */     if (c == -1) {
/*  70 */       return new JsonToken(JsonTokenType.END_OF_FILE, "<eof>");
/*     */     }
/*     */     
/*  73 */     switch (c) {
/*     */       case 123:
/*  75 */         return new JsonToken(JsonTokenType.BEGIN_OBJECT, "{");
/*     */       case 125:
/*  77 */         return new JsonToken(JsonTokenType.END_OBJECT, "}");
/*     */       case 91:
/*  79 */         return new JsonToken(JsonTokenType.BEGIN_ARRAY, "[");
/*     */       case 93:
/*  81 */         return new JsonToken(JsonTokenType.END_ARRAY, "]");
/*     */       case 40:
/*  83 */         return new JsonToken(JsonTokenType.LEFT_PAREN, "(");
/*     */       case 41:
/*  85 */         return new JsonToken(JsonTokenType.RIGHT_PAREN, ")");
/*     */       case 58:
/*  87 */         return new JsonToken(JsonTokenType.COLON, ":");
/*     */       case 44:
/*  89 */         return new JsonToken(JsonTokenType.COMMA, ",");
/*     */       case 34:
/*     */       case 39:
/*  92 */         return scanString((char)c);
/*     */       case 47:
/*  94 */         return scanRegularExpression();
/*     */     } 
/*  96 */     if (c == 45 || Character.isDigit(c))
/*  97 */       return scanNumber((char)c); 
/*  98 */     if (c == 36 || c == 95 || Character.isLetter(c)) {
/*  99 */       return scanUnquotedString((char)c);
/*     */     }
/* 101 */     int position = this.buffer.getPosition();
/* 102 */     this.buffer.unread(c);
/* 103 */     throw new JsonParseException("Invalid JSON input. Position: %d. Character: '%c'.", new Object[] { Integer.valueOf(position), Integer.valueOf(c) });
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
/*     */   private JsonToken scanRegularExpression() {
/* 122 */     StringBuilder patternBuilder = new StringBuilder();
/* 123 */     StringBuilder optionsBuilder = new StringBuilder();
/* 124 */     RegularExpressionState state = RegularExpressionState.IN_PATTERN; while (true) {
/*     */       BsonRegularExpression regex;
/* 126 */       int c = this.buffer.read();
/* 127 */       switch (state) {
/*     */         case SAW_LEADING_MINUS:
/* 129 */           switch (c) {
/*     */             case -1:
/* 131 */               state = RegularExpressionState.INVALID;
/*     */               break;
/*     */             case 47:
/* 134 */               state = RegularExpressionState.IN_OPTIONS;
/*     */               break;
/*     */             case 92:
/* 137 */               state = RegularExpressionState.IN_ESCAPE_SEQUENCE;
/*     */               break;
/*     */           } 
/* 140 */           state = RegularExpressionState.IN_PATTERN;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SAW_LEADING_ZERO:
/* 145 */           state = RegularExpressionState.IN_PATTERN;
/*     */           break;
/*     */         case SAW_INTEGER_DIGITS:
/* 148 */           switch (c) {
/*     */             case 105:
/*     */             case 109:
/*     */             case 115:
/*     */             case 120:
/* 153 */               state = RegularExpressionState.IN_OPTIONS;
/*     */               break;
/*     */             case -1:
/*     */             case 41:
/*     */             case 44:
/*     */             case 93:
/*     */             case 125:
/* 160 */               state = RegularExpressionState.DONE;
/*     */               break;
/*     */           } 
/* 163 */           if (Character.isWhitespace(c)) {
/* 164 */             state = RegularExpressionState.DONE; break;
/*     */           } 
/* 166 */           state = RegularExpressionState.INVALID;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       switch (state) {
/*     */         case SAW_DECIMAL_POINT:
/* 177 */           this.buffer.unread(c);
/*     */           
/* 179 */           regex = new BsonRegularExpression(patternBuilder.toString(), optionsBuilder.toString());
/* 180 */           return new JsonToken(JsonTokenType.REGULAR_EXPRESSION, regex);
/*     */         case SAW_FRACTION_DIGITS:
/* 182 */           throw new JsonParseException("Invalid JSON regular expression. Position: %d.", new Object[] { Integer.valueOf(this.buffer.getPosition()) });
/*     */       } 
/* 184 */       switch (state) {
/*     */         case SAW_INTEGER_DIGITS:
/* 186 */           if (c != 47) {
/* 187 */             optionsBuilder.append((char)c);
/*     */           }
/*     */           continue;
/*     */       } 
/* 191 */       patternBuilder.append((char)c);
/*     */     } 
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
/*     */   private JsonToken scanUnquotedString(char firstChar) {
/* 204 */     StringBuilder sb = new StringBuilder();
/* 205 */     sb.append(firstChar);
/* 206 */     int c = this.buffer.read();
/* 207 */     while (c == 36 || c == 95 || Character.isLetterOrDigit(c)) {
/* 208 */       sb.append((char)c);
/* 209 */       c = this.buffer.read();
/*     */     } 
/* 211 */     this.buffer.unread(c);
/* 212 */     String lexeme = sb.toString();
/* 213 */     return new JsonToken(JsonTokenType.UNQUOTED_STRING, lexeme);
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
/*     */   private JsonToken scanNumber(char firstChar) {
/*     */     NumberState state;
/* 237 */     int c = firstChar;
/* 238 */     StringBuilder sb = new StringBuilder();
/* 239 */     sb.append(firstChar);
/*     */ 
/*     */ 
/*     */     
/* 243 */     switch (c) {
/*     */       case 45:
/* 245 */         state = NumberState.SAW_LEADING_MINUS;
/*     */         break;
/*     */       case 48:
/* 248 */         state = NumberState.SAW_LEADING_ZERO;
/*     */         break;
/*     */       default:
/* 251 */         state = NumberState.SAW_INTEGER_DIGITS;
/*     */         break;
/*     */     } 
/*     */     
/* 255 */     JsonTokenType type = JsonTokenType.INT64; while (true) {
/*     */       boolean sawMinusInfinity; String lexeme; char[] nfinity;
/*     */       long value;
/*     */       int i;
/* 259 */       c = this.buffer.read();
/* 260 */       switch (state) {
/*     */         case SAW_LEADING_MINUS:
/* 262 */           switch (c) {
/*     */             case 48:
/* 264 */               state = NumberState.SAW_LEADING_ZERO;
/*     */               break;
/*     */             case 73:
/* 267 */               state = NumberState.SAW_MINUS_I;
/*     */               break;
/*     */           } 
/* 270 */           if (Character.isDigit(c)) {
/* 271 */             state = NumberState.SAW_INTEGER_DIGITS; break;
/*     */           } 
/* 273 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_LEADING_ZERO:
/* 279 */           switch (c) {
/*     */             case 46:
/* 281 */               state = NumberState.SAW_DECIMAL_POINT;
/*     */               break;
/*     */             case 69:
/*     */             case 101:
/* 285 */               state = NumberState.SAW_EXPONENT_LETTER;
/*     */               break;
/*     */             case -1:
/*     */             case 41:
/*     */             case 44:
/*     */             case 93:
/*     */             case 125:
/* 292 */               state = NumberState.DONE;
/*     */               break;
/*     */           } 
/* 295 */           if (Character.isDigit(c)) {
/* 296 */             state = NumberState.SAW_INTEGER_DIGITS; break;
/* 297 */           }  if (Character.isWhitespace(c)) {
/* 298 */             state = NumberState.DONE; break;
/*     */           } 
/* 300 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_INTEGER_DIGITS:
/* 306 */           switch (c) {
/*     */             case 46:
/* 308 */               state = NumberState.SAW_DECIMAL_POINT;
/*     */               break;
/*     */             case 69:
/*     */             case 101:
/* 312 */               state = NumberState.SAW_EXPONENT_LETTER;
/*     */               break;
/*     */             case -1:
/*     */             case 41:
/*     */             case 44:
/*     */             case 93:
/*     */             case 125:
/* 319 */               state = NumberState.DONE;
/*     */               break;
/*     */           } 
/* 322 */           if (Character.isDigit(c)) {
/* 323 */             state = NumberState.SAW_INTEGER_DIGITS; break;
/* 324 */           }  if (Character.isWhitespace(c)) {
/* 325 */             state = NumberState.DONE; break;
/*     */           } 
/* 327 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_DECIMAL_POINT:
/* 333 */           type = JsonTokenType.DOUBLE;
/* 334 */           if (Character.isDigit(c)) {
/* 335 */             state = NumberState.SAW_FRACTION_DIGITS; break;
/*     */           } 
/* 337 */           state = NumberState.INVALID;
/*     */           break;
/*     */         
/*     */         case SAW_FRACTION_DIGITS:
/* 341 */           switch (c) {
/*     */             case 69:
/*     */             case 101:
/* 344 */               state = NumberState.SAW_EXPONENT_LETTER;
/*     */               break;
/*     */             case -1:
/*     */             case 41:
/*     */             case 44:
/*     */             case 93:
/*     */             case 125:
/* 351 */               state = NumberState.DONE;
/*     */               break;
/*     */           } 
/* 354 */           if (Character.isDigit(c)) {
/* 355 */             state = NumberState.SAW_FRACTION_DIGITS; break;
/* 356 */           }  if (Character.isWhitespace(c)) {
/* 357 */             state = NumberState.DONE; break;
/*     */           } 
/* 359 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_EXPONENT_LETTER:
/* 365 */           type = JsonTokenType.DOUBLE;
/* 366 */           switch (c) {
/*     */             case 43:
/*     */             case 45:
/* 369 */               state = NumberState.SAW_EXPONENT_SIGN;
/*     */               break;
/*     */           } 
/* 372 */           if (Character.isDigit(c)) {
/* 373 */             state = NumberState.SAW_EXPONENT_DIGITS; break;
/*     */           } 
/* 375 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_EXPONENT_SIGN:
/* 381 */           if (Character.isDigit(c)) {
/* 382 */             state = NumberState.SAW_EXPONENT_DIGITS; break;
/*     */           } 
/* 384 */           state = NumberState.INVALID;
/*     */           break;
/*     */         
/*     */         case SAW_EXPONENT_DIGITS:
/* 388 */           switch (c) {
/*     */             case 41:
/*     */             case 44:
/*     */             case 93:
/*     */             case 125:
/* 393 */               state = NumberState.DONE;
/*     */               break;
/*     */           } 
/* 396 */           if (Character.isDigit(c)) {
/* 397 */             state = NumberState.SAW_EXPONENT_DIGITS; break;
/* 398 */           }  if (Character.isWhitespace(c)) {
/* 399 */             state = NumberState.DONE; break;
/*     */           } 
/* 401 */           state = NumberState.INVALID;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case SAW_MINUS_I:
/* 407 */           sawMinusInfinity = true;
/* 408 */           nfinity = new char[] { 'n', 'f', 'i', 'n', 'i', 't', 'y' };
/* 409 */           for (i = 0; i < nfinity.length; i++) {
/* 410 */             if (c != nfinity[i]) {
/* 411 */               sawMinusInfinity = false;
/*     */               break;
/*     */             } 
/* 414 */             sb.append((char)c);
/* 415 */             c = this.buffer.read();
/*     */           } 
/* 417 */           if (sawMinusInfinity) {
/* 418 */             type = JsonTokenType.DOUBLE;
/* 419 */             switch (c) {
/*     */               case -1:
/*     */               case 41:
/*     */               case 44:
/*     */               case 93:
/*     */               case 125:
/* 425 */                 state = NumberState.DONE;
/*     */                 break;
/*     */             } 
/* 428 */             if (Character.isWhitespace(c)) {
/* 429 */               state = NumberState.DONE; break;
/*     */             } 
/* 431 */             state = NumberState.INVALID;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 436 */           state = NumberState.INVALID;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 442 */       switch (state) {
/*     */         case INVALID:
/* 444 */           throw new JsonParseException("Invalid JSON number");
/*     */         case DONE:
/* 446 */           this.buffer.unread(c);
/* 447 */           lexeme = sb.toString();
/* 448 */           if (type == JsonTokenType.DOUBLE) {
/* 449 */             return new JsonToken(JsonTokenType.DOUBLE, Double.valueOf(Double.parseDouble(lexeme)));
/*     */           }
/* 451 */           value = Long.parseLong(lexeme);
/* 452 */           if (value < -2147483648L || value > 2147483647L) {
/* 453 */             return new JsonToken(JsonTokenType.INT64, Long.valueOf(value));
/*     */           }
/* 455 */           return new JsonToken(JsonTokenType.INT32, Integer.valueOf((int)value));
/*     */       } 
/*     */ 
/*     */       
/* 459 */       sb.append((char)c);
/*     */     } 
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
/*     */   private JsonToken scanString(char quoteCharacter) {
/* 474 */     StringBuilder sb = new StringBuilder();
/*     */     
/*     */     while (true) {
/* 477 */       int u1, u2, u3, u4, c = this.buffer.read();
/* 478 */       switch (c) {
/*     */         case 92:
/* 480 */           c = this.buffer.read();
/* 481 */           switch (c) {
/*     */             case 39:
/* 483 */               sb.append('\'');
/*     */               break;
/*     */             case 34:
/* 486 */               sb.append('"');
/*     */               break;
/*     */             case 92:
/* 489 */               sb.append('\\');
/*     */               break;
/*     */             case 47:
/* 492 */               sb.append('/');
/*     */               break;
/*     */             case 98:
/* 495 */               sb.append('\b');
/*     */               break;
/*     */             case 102:
/* 498 */               sb.append('\f');
/*     */               break;
/*     */             case 110:
/* 501 */               sb.append('\n');
/*     */               break;
/*     */             case 114:
/* 504 */               sb.append('\r');
/*     */               break;
/*     */             case 116:
/* 507 */               sb.append('\t');
/*     */               break;
/*     */             case 117:
/* 510 */               u1 = this.buffer.read();
/* 511 */               u2 = this.buffer.read();
/* 512 */               u3 = this.buffer.read();
/* 513 */               u4 = this.buffer.read();
/* 514 */               if (u4 != -1) {
/* 515 */                 String hex = new String(new char[] { (char)u1, (char)u2, (char)u3, (char)u4 });
/* 516 */                 sb.append((char)Integer.parseInt(hex, 16));
/*     */               } 
/*     */               break;
/*     */           } 
/* 520 */           throw new JsonParseException("Invalid escape sequence in JSON string '\\%c'.", new Object[] { Integer.valueOf(c) });
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 525 */           if (c == quoteCharacter) {
/* 526 */             return new JsonToken(JsonTokenType.STRING, sb.toString());
/*     */           }
/* 528 */           if (c != -1)
/* 529 */             sb.append((char)c); 
/*     */           break;
/*     */       } 
/* 532 */       if (c == -1)
/* 533 */         throw new JsonParseException("End of file in JSON string."); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum NumberState
/*     */   {
/* 539 */     SAW_LEADING_MINUS,
/* 540 */     SAW_LEADING_ZERO,
/* 541 */     SAW_INTEGER_DIGITS,
/* 542 */     SAW_DECIMAL_POINT,
/* 543 */     SAW_FRACTION_DIGITS,
/* 544 */     SAW_EXPONENT_LETTER,
/* 545 */     SAW_EXPONENT_SIGN,
/* 546 */     SAW_EXPONENT_DIGITS,
/* 547 */     SAW_MINUS_I,
/* 548 */     DONE,
/* 549 */     INVALID;
/*     */   }
/*     */   
/*     */   private enum RegularExpressionState {
/* 553 */     IN_PATTERN,
/* 554 */     IN_ESCAPE_SEQUENCE,
/* 555 */     IN_OPTIONS,
/* 556 */     DONE,
/* 557 */     INVALID;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */