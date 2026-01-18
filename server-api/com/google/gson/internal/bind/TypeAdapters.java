/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonIOException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.LazilyParsedNumber;
/*     */ import com.google.gson.internal.NumberLimits;
/*     */ import com.google.gson.internal.TroubleshootingGuide;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Calendar;
/*     */ import java.util.Currency;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeAdapters
/*     */ {
/*     */   private TypeAdapters() {
/*  58 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*  62 */   public static final TypeAdapter<Class> CLASS = (new TypeAdapter<Class>()
/*     */     {
/*     */       public void write(JsonWriter out, Class value) throws IOException
/*     */       {
/*  66 */         throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value
/*     */             
/*  68 */             .getName() + ". Forgot to register a type adapter?\nSee " + 
/*     */ 
/*     */             
/*  71 */             TroubleshootingGuide.createUrl("java-lang-class-unsupported"));
/*     */       }
/*     */ 
/*     */       
/*     */       public Class read(JsonReader in) throws IOException {
/*  76 */         throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?\nSee " + 
/*     */ 
/*     */             
/*  79 */             TroubleshootingGuide.createUrl("java-lang-class-unsupported"));
/*     */       }
/*  81 */     }).nullSafe();
/*     */   
/*  83 */   public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
/*     */   
/*  85 */   public static final TypeAdapter<BitSet> BIT_SET = (new TypeAdapter<BitSet>()
/*     */     {
/*     */       public BitSet read(JsonReader in) throws IOException {
/*     */         // Byte code:
/*     */         //   0: new java/util/BitSet
/*     */         //   3: dup
/*     */         //   4: invokespecial <init> : ()V
/*     */         //   7: astore_2
/*     */         //   8: aload_1
/*     */         //   9: invokevirtual beginArray : ()V
/*     */         //   12: iconst_0
/*     */         //   13: istore_3
/*     */         //   14: aload_1
/*     */         //   15: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
/*     */         //   18: astore #4
/*     */         //   20: aload #4
/*     */         //   22: getstatic com/google/gson/stream/JsonToken.END_ARRAY : Lcom/google/gson/stream/JsonToken;
/*     */         //   25: if_acmpeq -> 204
/*     */         //   28: getstatic com/google/gson/internal/bind/TypeAdapters$33.$SwitchMap$com$google$gson$stream$JsonToken : [I
/*     */         //   31: aload #4
/*     */         //   33: invokevirtual ordinal : ()I
/*     */         //   36: iaload
/*     */         //   37: tableswitch default -> 142, 1 -> 64, 2 -> 64, 3 -> 133
/*     */         //   64: aload_1
/*     */         //   65: invokevirtual nextInt : ()I
/*     */         //   68: istore #6
/*     */         //   70: iload #6
/*     */         //   72: ifne -> 81
/*     */         //   75: iconst_0
/*     */         //   76: istore #5
/*     */         //   78: goto -> 182
/*     */         //   81: iload #6
/*     */         //   83: iconst_1
/*     */         //   84: if_icmpne -> 93
/*     */         //   87: iconst_1
/*     */         //   88: istore #5
/*     */         //   90: goto -> 182
/*     */         //   93: new com/google/gson/JsonSyntaxException
/*     */         //   96: dup
/*     */         //   97: new java/lang/StringBuilder
/*     */         //   100: dup
/*     */         //   101: invokespecial <init> : ()V
/*     */         //   104: ldc 'Invalid bitset value '
/*     */         //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   109: iload #6
/*     */         //   111: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */         //   114: ldc ', expected 0 or 1; at path '
/*     */         //   116: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   119: aload_1
/*     */         //   120: invokevirtual getPreviousPath : ()Ljava/lang/String;
/*     */         //   123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   126: invokevirtual toString : ()Ljava/lang/String;
/*     */         //   129: invokespecial <init> : (Ljava/lang/String;)V
/*     */         //   132: athrow
/*     */         //   133: aload_1
/*     */         //   134: invokevirtual nextBoolean : ()Z
/*     */         //   137: istore #5
/*     */         //   139: goto -> 182
/*     */         //   142: new com/google/gson/JsonSyntaxException
/*     */         //   145: dup
/*     */         //   146: new java/lang/StringBuilder
/*     */         //   149: dup
/*     */         //   150: invokespecial <init> : ()V
/*     */         //   153: ldc 'Invalid bitset value type: '
/*     */         //   155: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   158: aload #4
/*     */         //   160: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */         //   163: ldc '; at path '
/*     */         //   165: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   168: aload_1
/*     */         //   169: invokevirtual getPath : ()Ljava/lang/String;
/*     */         //   172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */         //   175: invokevirtual toString : ()Ljava/lang/String;
/*     */         //   178: invokespecial <init> : (Ljava/lang/String;)V
/*     */         //   181: athrow
/*     */         //   182: iload #5
/*     */         //   184: ifeq -> 192
/*     */         //   187: aload_2
/*     */         //   188: iload_3
/*     */         //   189: invokevirtual set : (I)V
/*     */         //   192: iinc #3, 1
/*     */         //   195: aload_1
/*     */         //   196: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
/*     */         //   199: astore #4
/*     */         //   201: goto -> 20
/*     */         //   204: aload_1
/*     */         //   205: invokevirtual endArray : ()V
/*     */         //   208: aload_2
/*     */         //   209: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #89	-> 0
/*     */         //   #90	-> 8
/*     */         //   #91	-> 12
/*     */         //   #92	-> 14
/*     */         //   #93	-> 20
/*     */         //   #95	-> 28
/*     */         //   #98	-> 64
/*     */         //   #99	-> 70
/*     */         //   #100	-> 75
/*     */         //   #101	-> 81
/*     */         //   #102	-> 87
/*     */         //   #104	-> 93
/*     */         //   #108	-> 120
/*     */         //   #112	-> 133
/*     */         //   #113	-> 139
/*     */         //   #115	-> 142
/*     */         //   #116	-> 169
/*     */         //   #118	-> 182
/*     */         //   #119	-> 187
/*     */         //   #121	-> 192
/*     */         //   #122	-> 195
/*     */         //   #123	-> 201
/*     */         //   #124	-> 204
/*     */         //   #125	-> 208
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   78	3	5	set	Z
/*     */         //   90	3	5	set	Z
/*     */         //   70	63	6	intValue	I
/*     */         //   139	3	5	set	Z
/*     */         //   182	19	5	set	Z
/*     */         //   0	210	0	this	Lcom/google/gson/internal/bind/TypeAdapters$2;
/*     */         //   0	210	1	in	Lcom/google/gson/stream/JsonReader;
/*     */         //   8	202	2	bitset	Ljava/util/BitSet;
/*     */         //   14	196	3	i	I
/*     */         //   20	190	4	tokenType	Lcom/google/gson/stream/JsonToken;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, BitSet src) throws IOException {
/* 130 */         out.beginArray();
/* 131 */         for (int i = 0, length = src.length(); i < length; i++) {
/* 132 */           int value = src.get(i) ? 1 : 0;
/* 133 */           out.value(value);
/*     */         } 
/* 135 */         out.endArray();
/*     */       }
/* 137 */     }).nullSafe();
/*     */   
/* 139 */   public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
/*     */   
/* 141 */   public static final TypeAdapter<Boolean> BOOLEAN = new TypeAdapter<Boolean>()
/*     */     {
/*     */       public Boolean read(JsonReader in) throws IOException
/*     */       {
/* 145 */         JsonToken peek = in.peek();
/* 146 */         if (peek == JsonToken.NULL) {
/* 147 */           in.nextNull();
/* 148 */           return null;
/* 149 */         }  if (peek == JsonToken.STRING)
/*     */         {
/* 151 */           return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
/*     */         }
/* 153 */         return Boolean.valueOf(in.nextBoolean());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 158 */         out.value(value);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>()
/*     */     {
/*     */       public Boolean read(JsonReader in) throws IOException
/*     */       {
/* 169 */         if (in.peek() == JsonToken.NULL) {
/* 170 */           in.nextNull();
/* 171 */           return null;
/*     */         } 
/* 173 */         return Boolean.valueOf(in.nextString());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 178 */         out.value((value == null) ? "null" : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 183 */   public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(boolean.class, (Class)Boolean.class, (TypeAdapter)BOOLEAN);
/*     */   
/* 185 */   public static final TypeAdapter<Number> BYTE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/*     */         int intValue;
/* 189 */         if (in.peek() == JsonToken.NULL) {
/* 190 */           in.nextNull();
/* 191 */           return null;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 196 */           intValue = in.nextInt();
/* 197 */         } catch (NumberFormatException e) {
/* 198 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */         
/* 201 */         if (intValue > 255 || intValue < -128) {
/* 202 */           throw new JsonSyntaxException("Lossy conversion from " + intValue + " to byte; at path " + in
/* 203 */               .getPreviousPath());
/*     */         }
/* 205 */         return Byte.valueOf((byte)intValue);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 210 */         if (value == null) {
/* 211 */           out.nullValue();
/*     */         } else {
/* 213 */           out.value(value.byteValue());
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 218 */   public static final TypeAdapterFactory BYTE_FACTORY = newFactory(byte.class, (Class)Byte.class, (TypeAdapter)BYTE);
/*     */   
/* 220 */   public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/*     */         int intValue;
/* 224 */         if (in.peek() == JsonToken.NULL) {
/* 225 */           in.nextNull();
/* 226 */           return null;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 231 */           intValue = in.nextInt();
/* 232 */         } catch (NumberFormatException e) {
/* 233 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */         
/* 236 */         if (intValue > 65535 || intValue < -32768) {
/* 237 */           throw new JsonSyntaxException("Lossy conversion from " + intValue + " to short; at path " + in
/* 238 */               .getPreviousPath());
/*     */         }
/* 240 */         return Short.valueOf((short)intValue);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 245 */         if (value == null) {
/* 246 */           out.nullValue();
/*     */         } else {
/* 248 */           out.value(value.shortValue());
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 254 */   public static final TypeAdapterFactory SHORT_FACTORY = newFactory(short.class, (Class)Short.class, (TypeAdapter)SHORT);
/*     */   
/* 256 */   public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException
/*     */       {
/* 260 */         if (in.peek() == JsonToken.NULL) {
/* 261 */           in.nextNull();
/* 262 */           return null;
/*     */         } 
/*     */         try {
/* 265 */           return Integer.valueOf(in.nextInt());
/* 266 */         } catch (NumberFormatException e) {
/* 267 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 273 */         if (value == null) {
/* 274 */           out.nullValue();
/*     */         } else {
/* 276 */           out.value(value.intValue());
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 281 */   public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(int.class, (Class)Integer.class, (TypeAdapter)INTEGER);
/*     */   
/* 283 */   public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = (new TypeAdapter<AtomicInteger>()
/*     */     {
/*     */       public AtomicInteger read(JsonReader in) throws IOException
/*     */       {
/*     */         try {
/* 288 */           return new AtomicInteger(in.nextInt());
/* 289 */         } catch (NumberFormatException e) {
/* 290 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, AtomicInteger value) throws IOException {
/* 296 */         out.value(value.get());
/*     */       }
/* 298 */     }).nullSafe();
/*     */   
/* 300 */   public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
/*     */   
/* 302 */   public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = (new TypeAdapter<AtomicBoolean>()
/*     */     {
/*     */       public AtomicBoolean read(JsonReader in) throws IOException
/*     */       {
/* 306 */         return new AtomicBoolean(in.nextBoolean());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, AtomicBoolean value) throws IOException {
/* 311 */         out.value(value.get());
/*     */       }
/* 313 */     }).nullSafe();
/*     */   
/* 315 */   public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
/*     */   
/* 317 */   public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = (new TypeAdapter<AtomicIntegerArray>()
/*     */     {
/*     */       public AtomicIntegerArray read(JsonReader in) throws IOException
/*     */       {
/* 321 */         List<Integer> list = new ArrayList<>();
/* 322 */         in.beginArray();
/* 323 */         while (in.hasNext()) {
/*     */           try {
/* 325 */             int integer = in.nextInt();
/* 326 */             list.add(Integer.valueOf(integer));
/* 327 */           } catch (NumberFormatException e) {
/* 328 */             throw new JsonSyntaxException(e);
/*     */           } 
/*     */         } 
/* 331 */         in.endArray();
/* 332 */         int length = list.size();
/* 333 */         AtomicIntegerArray array = new AtomicIntegerArray(length);
/* 334 */         for (int i = 0; i < length; i++) {
/* 335 */           array.set(i, ((Integer)list.get(i)).intValue());
/*     */         }
/* 337 */         return array;
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, AtomicIntegerArray value) throws IOException {
/* 342 */         out.beginArray();
/* 343 */         for (int i = 0, length = value.length(); i < length; i++) {
/* 344 */           out.value(value.get(i));
/*     */         }
/* 346 */         out.endArray();
/*     */       }
/* 348 */     }).nullSafe();
/*     */   
/* 350 */   public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
/*     */   
/* 352 */   public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException
/*     */       {
/* 356 */         if (in.peek() == JsonToken.NULL) {
/* 357 */           in.nextNull();
/* 358 */           return null;
/*     */         } 
/*     */         try {
/* 361 */           return Long.valueOf(in.nextLong());
/* 362 */         } catch (NumberFormatException e) {
/* 363 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 369 */         if (value == null) {
/* 370 */           out.nullValue();
/*     */         } else {
/* 372 */           out.value(value.longValue());
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 377 */   public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException
/*     */       {
/* 381 */         if (in.peek() == JsonToken.NULL) {
/* 382 */           in.nextNull();
/* 383 */           return null;
/*     */         } 
/* 385 */         return Float.valueOf((float)in.nextDouble());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 390 */         if (value == null) {
/* 391 */           out.nullValue();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 396 */           Number floatNumber = (value instanceof Float) ? value : Float.valueOf(value.floatValue());
/* 397 */           out.value(floatNumber);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 402 */   public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException
/*     */       {
/* 406 */         if (in.peek() == JsonToken.NULL) {
/* 407 */           in.nextNull();
/* 408 */           return null;
/*     */         } 
/* 410 */         return Double.valueOf(in.nextDouble());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 415 */         if (value == null) {
/* 416 */           out.nullValue();
/*     */         } else {
/* 418 */           out.value(value.doubleValue());
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 423 */   public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>()
/*     */     {
/*     */       public Character read(JsonReader in) throws IOException
/*     */       {
/* 427 */         if (in.peek() == JsonToken.NULL) {
/* 428 */           in.nextNull();
/* 429 */           return null;
/*     */         } 
/* 431 */         String str = in.nextString();
/* 432 */         if (str.length() != 1) {
/* 433 */           throw new JsonSyntaxException("Expecting character, got: " + str + "; at " + in
/* 434 */               .getPreviousPath());
/*     */         }
/* 436 */         return Character.valueOf(str.charAt(0));
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Character value) throws IOException {
/* 441 */         out.value((value == null) ? null : String.valueOf(value));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 446 */   public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(char.class, (Class)Character.class, (TypeAdapter)CHARACTER);
/*     */   
/* 448 */   public static final TypeAdapter<String> STRING = new TypeAdapter<String>()
/*     */     {
/*     */       public String read(JsonReader in) throws IOException
/*     */       {
/* 452 */         JsonToken peek = in.peek();
/* 453 */         if (peek == JsonToken.NULL) {
/* 454 */           in.nextNull();
/* 455 */           return null;
/*     */         } 
/*     */         
/* 458 */         if (peek == JsonToken.BOOLEAN) {
/* 459 */           return Boolean.toString(in.nextBoolean());
/*     */         }
/* 461 */         return in.nextString();
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, String value) throws IOException {
/* 466 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 470 */   public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>()
/*     */     {
/*     */       public BigDecimal read(JsonReader in) throws IOException
/*     */       {
/* 474 */         if (in.peek() == JsonToken.NULL) {
/* 475 */           in.nextNull();
/* 476 */           return null;
/*     */         } 
/* 478 */         String s = in.nextString();
/*     */         try {
/* 480 */           return NumberLimits.parseBigDecimal(s);
/* 481 */         } catch (NumberFormatException e) {
/* 482 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as BigDecimal; at path " + in
/* 483 */               .getPreviousPath(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, BigDecimal value) throws IOException {
/* 489 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 493 */   public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>()
/*     */     {
/*     */       public BigInteger read(JsonReader in) throws IOException
/*     */       {
/* 497 */         if (in.peek() == JsonToken.NULL) {
/* 498 */           in.nextNull();
/* 499 */           return null;
/*     */         } 
/* 501 */         String s = in.nextString();
/*     */         try {
/* 503 */           return NumberLimits.parseBigInteger(s);
/* 504 */         } catch (NumberFormatException e) {
/* 505 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as BigInteger; at path " + in
/* 506 */               .getPreviousPath(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, BigInteger value) throws IOException {
/* 512 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 516 */   public static final TypeAdapter<LazilyParsedNumber> LAZILY_PARSED_NUMBER = new TypeAdapter<LazilyParsedNumber>()
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*     */       public LazilyParsedNumber read(JsonReader in) throws IOException
/*     */       {
/* 523 */         if (in.peek() == JsonToken.NULL) {
/* 524 */           in.nextNull();
/* 525 */           return null;
/*     */         } 
/* 527 */         return new LazilyParsedNumber(in.nextString());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, LazilyParsedNumber value) throws IOException {
/* 532 */         out.value((Number)value);
/*     */       }
/*     */     };
/*     */   
/* 536 */   public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
/*     */   
/* 538 */   public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>()
/*     */     {
/*     */       public StringBuilder read(JsonReader in) throws IOException
/*     */       {
/* 542 */         if (in.peek() == JsonToken.NULL) {
/* 543 */           in.nextNull();
/* 544 */           return null;
/*     */         } 
/* 546 */         return new StringBuilder(in.nextString());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, StringBuilder value) throws IOException {
/* 551 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 556 */   public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
/*     */   
/* 558 */   public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>()
/*     */     {
/*     */       public StringBuffer read(JsonReader in) throws IOException
/*     */       {
/* 562 */         if (in.peek() == JsonToken.NULL) {
/* 563 */           in.nextNull();
/* 564 */           return null;
/*     */         } 
/* 566 */         return new StringBuffer(in.nextString());
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, StringBuffer value) throws IOException {
/* 571 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 576 */   public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
/*     */   
/* 578 */   public static final TypeAdapter<URL> URL = new TypeAdapter<URL>()
/*     */     {
/*     */       public URL read(JsonReader in) throws IOException
/*     */       {
/* 582 */         if (in.peek() == JsonToken.NULL) {
/* 583 */           in.nextNull();
/* 584 */           return null;
/*     */         } 
/* 586 */         String nextString = in.nextString();
/* 587 */         return nextString.equals("null") ? null : new URL(nextString);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, URL value) throws IOException {
/* 592 */         out.value((value == null) ? null : value.toExternalForm());
/*     */       }
/*     */     };
/*     */   
/* 596 */   public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
/*     */   
/* 598 */   public static final TypeAdapter<URI> URI = new TypeAdapter<URI>()
/*     */     {
/*     */       public URI read(JsonReader in) throws IOException
/*     */       {
/* 602 */         if (in.peek() == JsonToken.NULL) {
/* 603 */           in.nextNull();
/* 604 */           return null;
/*     */         } 
/*     */         try {
/* 607 */           String nextString = in.nextString();
/* 608 */           return nextString.equals("null") ? null : new URI(nextString);
/* 609 */         } catch (URISyntaxException e) {
/* 610 */           throw new JsonIOException(e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, URI value) throws IOException {
/* 616 */         out.value((value == null) ? null : value.toASCIIString());
/*     */       }
/*     */     };
/*     */   
/* 620 */   public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
/*     */   
/* 622 */   public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>()
/*     */     {
/*     */       public InetAddress read(JsonReader in) throws IOException
/*     */       {
/* 626 */         if (in.peek() == JsonToken.NULL) {
/* 627 */           in.nextNull();
/* 628 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 634 */         InetAddress addr = InetAddress.getByName(in.nextString());
/* 635 */         return addr;
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, InetAddress value) throws IOException {
/* 640 */         out.value((value == null) ? null : value.getHostAddress());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 645 */   public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
/*     */   
/* 647 */   public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>()
/*     */     {
/*     */       public UUID read(JsonReader in) throws IOException
/*     */       {
/* 651 */         if (in.peek() == JsonToken.NULL) {
/* 652 */           in.nextNull();
/* 653 */           return null;
/*     */         } 
/* 655 */         String s = in.nextString();
/*     */         try {
/* 657 */           return UUID.fromString(s);
/* 658 */         } catch (IllegalArgumentException e) {
/* 659 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as UUID; at path " + in
/* 660 */               .getPreviousPath(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, UUID value) throws IOException {
/* 666 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 670 */   public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);
/*     */   
/* 672 */   public static final TypeAdapter<Currency> CURRENCY = (new TypeAdapter<Currency>()
/*     */     {
/*     */       public Currency read(JsonReader in) throws IOException
/*     */       {
/* 676 */         String s = in.nextString();
/*     */         try {
/* 678 */           return Currency.getInstance(s);
/* 679 */         } catch (IllegalArgumentException e) {
/* 680 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as Currency; at path " + in
/* 681 */               .getPreviousPath(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Currency value) throws IOException {
/* 687 */         out.value(value.getCurrencyCode());
/*     */       }
/* 689 */     }).nullSafe();
/* 690 */   public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
/*     */   
/* 692 */   public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>()
/*     */     {
/*     */       private static final String YEAR = "year";
/*     */       
/*     */       private static final String MONTH = "month";
/*     */       private static final String DAY_OF_MONTH = "dayOfMonth";
/*     */       private static final String HOUR_OF_DAY = "hourOfDay";
/*     */       private static final String MINUTE = "minute";
/*     */       private static final String SECOND = "second";
/*     */       
/*     */       public Calendar read(JsonReader in) throws IOException {
/* 703 */         if (in.peek() == JsonToken.NULL) {
/* 704 */           in.nextNull();
/* 705 */           return null;
/*     */         } 
/* 707 */         in.beginObject();
/* 708 */         int year = 0;
/* 709 */         int month = 0;
/* 710 */         int dayOfMonth = 0;
/* 711 */         int hourOfDay = 0;
/* 712 */         int minute = 0;
/* 713 */         int second = 0;
/* 714 */         while (in.peek() != JsonToken.END_OBJECT) {
/* 715 */           String name = in.nextName();
/* 716 */           int value = in.nextInt();
/* 717 */           switch (name) {
/*     */             case "year":
/* 719 */               year = value;
/*     */             
/*     */             case "month":
/* 722 */               month = value;
/*     */             
/*     */             case "dayOfMonth":
/* 725 */               dayOfMonth = value;
/*     */             
/*     */             case "hourOfDay":
/* 728 */               hourOfDay = value;
/*     */             
/*     */             case "minute":
/* 731 */               minute = value;
/*     */             
/*     */             case "second":
/* 734 */               second = value;
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         } 
/* 740 */         in.endObject();
/* 741 */         return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Calendar value) throws IOException {
/* 746 */         if (value == null) {
/* 747 */           out.nullValue();
/*     */           return;
/*     */         } 
/* 750 */         out.beginObject();
/* 751 */         out.name("year");
/* 752 */         out.value(value.get(1));
/* 753 */         out.name("month");
/* 754 */         out.value(value.get(2));
/* 755 */         out.name("dayOfMonth");
/* 756 */         out.value(value.get(5));
/* 757 */         out.name("hourOfDay");
/* 758 */         out.value(value.get(11));
/* 759 */         out.name("minute");
/* 760 */         out.value(value.get(12));
/* 761 */         out.name("second");
/* 762 */         out.value(value.get(13));
/* 763 */         out.endObject();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 768 */   public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, (Class)GregorianCalendar.class, CALENDAR);
/*     */   
/* 770 */   public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>()
/*     */     {
/*     */       public Locale read(JsonReader in) throws IOException
/*     */       {
/* 774 */         if (in.peek() == JsonToken.NULL) {
/* 775 */           in.nextNull();
/* 776 */           return null;
/*     */         } 
/* 778 */         String locale = in.nextString();
/* 779 */         StringTokenizer tokenizer = new StringTokenizer(locale, "_");
/* 780 */         String language = null;
/* 781 */         String country = null;
/* 782 */         String variant = null;
/* 783 */         if (tokenizer.hasMoreElements()) {
/* 784 */           language = tokenizer.nextToken();
/*     */         }
/* 786 */         if (tokenizer.hasMoreElements()) {
/* 787 */           country = tokenizer.nextToken();
/*     */         }
/* 789 */         if (tokenizer.hasMoreElements()) {
/* 790 */           variant = tokenizer.nextToken();
/*     */         }
/* 792 */         if (country == null && variant == null)
/* 793 */           return new Locale(language); 
/* 794 */         if (variant == null) {
/* 795 */           return new Locale(language, country);
/*     */         }
/* 797 */         return new Locale(language, country, variant);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Locale value) throws IOException {
/* 803 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 807 */   public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
/*     */   
/* 809 */   public static final TypeAdapter<JsonElement> JSON_ELEMENT = JsonElementTypeAdapter.ADAPTER;
/*     */ 
/*     */   
/* 812 */   public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
/*     */   
/* 814 */   public static final TypeAdapterFactory ENUM_FACTORY = EnumTypeAdapter.FACTORY;
/*     */ 
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 819 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */         {
/* 823 */           return typeToken.equals(type) ? typeAdapter : null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 830 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */         {
/* 834 */           return (typeToken.getRawType() == type) ? typeAdapter : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 839 */           return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
/* 847 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */         {
/* 851 */           Class<? super T> rawType = typeToken.getRawType();
/* 852 */           return (rawType == unboxed || rawType == boxed) ? typeAdapter : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 857 */           return "Factory[type=" + boxed
/* 858 */             .getName() + "+" + unboxed
/*     */             
/* 860 */             .getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
/* 871 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */         {
/* 875 */           Class<? super T> rawType = typeToken.getRawType();
/* 876 */           return (rawType == base || rawType == sub) ? typeAdapter : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 881 */           return "Factory[type=" + base
/* 882 */             .getName() + "+" + sub
/*     */             
/* 884 */             .getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
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
/*     */   public static <T1> TypeAdapterFactory newTypeHierarchyFactory(final Class<T1> clazz, final TypeAdapter<T1> typeAdapter) {
/* 898 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T2> TypeAdapter<T2> create(Gson gson, TypeToken<T2> typeToken)
/*     */         {
/* 902 */           final Class<? super T2> requestedType = typeToken.getRawType();
/* 903 */           if (!clazz.isAssignableFrom(requestedType)) {
/* 904 */             return null;
/*     */           }
/* 906 */           return new TypeAdapter<T1>()
/*     */             {
/*     */               public void write(JsonWriter out, T1 value) throws IOException
/*     */               {
/* 910 */                 typeAdapter.write(out, value);
/*     */               }
/*     */ 
/*     */               
/*     */               public T1 read(JsonReader in) throws IOException {
/* 915 */                 T1 result = (T1)typeAdapter.read(in);
/* 916 */                 if (result != null && !requestedType.isInstance(result)) {
/* 917 */                   throw new JsonSyntaxException("Expected a " + requestedType
/*     */                       
/* 919 */                       .getName() + " but was " + result
/*     */                       
/* 921 */                       .getClass().getName() + "; at path " + in
/*     */                       
/* 923 */                       .getPreviousPath());
/*     */                 }
/* 925 */                 return result;
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 932 */           return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\TypeAdapters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */