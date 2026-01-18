/*     */ package org.bson.json;
/*     */ 
/*     */ import org.bson.BsonBinary;
/*     */ import org.bson.BsonMaxKey;
/*     */ import org.bson.BsonMinKey;
/*     */ import org.bson.BsonNull;
/*     */ import org.bson.BsonRegularExpression;
/*     */ import org.bson.BsonTimestamp;
/*     */ import org.bson.BsonUndefined;
/*     */ import org.bson.BsonWriterSettings;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.types.Decimal128;
/*     */ import org.bson.types.ObjectId;
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
/*     */ public final class JsonWriterSettings
/*     */   extends BsonWriterSettings
/*     */ {
/*  41 */   private static final JsonNullConverter JSON_NULL_CONVERTER = new JsonNullConverter();
/*  42 */   private static final JsonStringConverter JSON_STRING_CONVERTER = new JsonStringConverter();
/*  43 */   private static final JsonBooleanConverter JSON_BOOLEAN_CONVERTER = new JsonBooleanConverter();
/*  44 */   private static final JsonDoubleConverter JSON_DOUBLE_CONVERTER = new JsonDoubleConverter();
/*  45 */   private static final ExtendedJsonDoubleConverter EXTENDED_JSON_DOUBLE_CONVERTER = new ExtendedJsonDoubleConverter();
/*  46 */   private static final RelaxedExtendedJsonDoubleConverter RELAXED_EXTENDED_JSON_DOUBLE_CONVERTER = new RelaxedExtendedJsonDoubleConverter();
/*     */   
/*  48 */   private static final JsonInt32Converter JSON_INT_32_CONVERTER = new JsonInt32Converter();
/*  49 */   private static final ExtendedJsonInt32Converter EXTENDED_JSON_INT_32_CONVERTER = new ExtendedJsonInt32Converter();
/*  50 */   private static final JsonSymbolConverter JSON_SYMBOL_CONVERTER = new JsonSymbolConverter();
/*  51 */   private static final ExtendedJsonMinKeyConverter EXTENDED_JSON_MIN_KEY_CONVERTER = new ExtendedJsonMinKeyConverter();
/*  52 */   private static final ShellMinKeyConverter SHELL_MIN_KEY_CONVERTER = new ShellMinKeyConverter();
/*  53 */   private static final ExtendedJsonMaxKeyConverter EXTENDED_JSON_MAX_KEY_CONVERTER = new ExtendedJsonMaxKeyConverter();
/*  54 */   private static final ShellMaxKeyConverter SHELL_MAX_KEY_CONVERTER = new ShellMaxKeyConverter();
/*  55 */   private static final ExtendedJsonUndefinedConverter EXTENDED_JSON_UNDEFINED_CONVERTER = new ExtendedJsonUndefinedConverter();
/*  56 */   private static final ShellUndefinedConverter SHELL_UNDEFINED_CONVERTER = new ShellUndefinedConverter();
/*  57 */   private static final LegacyExtendedJsonDateTimeConverter LEGACY_EXTENDED_JSON_DATE_TIME_CONVERTER = new LegacyExtendedJsonDateTimeConverter();
/*     */   
/*  59 */   private static final ExtendedJsonDateTimeConverter EXTENDED_JSON_DATE_TIME_CONVERTER = new ExtendedJsonDateTimeConverter();
/*  60 */   private static final RelaxedExtendedJsonDateTimeConverter RELAXED_EXTENDED_JSON_DATE_TIME_CONVERTER = new RelaxedExtendedJsonDateTimeConverter();
/*     */   
/*  62 */   private static final ShellDateTimeConverter SHELL_DATE_TIME_CONVERTER = new ShellDateTimeConverter();
/*  63 */   private static final ExtendedJsonBinaryConverter EXTENDED_JSON_BINARY_CONVERTER = new ExtendedJsonBinaryConverter();
/*  64 */   private static final LegacyExtendedJsonBinaryConverter LEGACY_EXTENDED_JSON_BINARY_CONVERTER = new LegacyExtendedJsonBinaryConverter();
/*  65 */   private static final ShellBinaryConverter SHELL_BINARY_CONVERTER = new ShellBinaryConverter();
/*  66 */   private static final ExtendedJsonInt64Converter EXTENDED_JSON_INT_64_CONVERTER = new ExtendedJsonInt64Converter();
/*  67 */   private static final RelaxedExtendedJsonInt64Converter RELAXED_JSON_INT_64_CONVERTER = new RelaxedExtendedJsonInt64Converter();
/*  68 */   private static final ShellInt64Converter SHELL_INT_64_CONVERTER = new ShellInt64Converter();
/*  69 */   private static final ExtendedJsonDecimal128Converter EXTENDED_JSON_DECIMAL_128_CONVERTER = new ExtendedJsonDecimal128Converter();
/*  70 */   private static final ShellDecimal128Converter SHELL_DECIMAL_128_CONVERTER = new ShellDecimal128Converter();
/*  71 */   private static final ExtendedJsonObjectIdConverter EXTENDED_JSON_OBJECT_ID_CONVERTER = new ExtendedJsonObjectIdConverter();
/*  72 */   private static final ShellObjectIdConverter SHELL_OBJECT_ID_CONVERTER = new ShellObjectIdConverter();
/*  73 */   private static final ExtendedJsonTimestampConverter EXTENDED_JSON_TIMESTAMP_CONVERTER = new ExtendedJsonTimestampConverter();
/*  74 */   private static final ShellTimestampConverter SHELL_TIMESTAMP_CONVERTER = new ShellTimestampConverter();
/*  75 */   private static final ExtendedJsonRegularExpressionConverter EXTENDED_JSON_REGULAR_EXPRESSION_CONVERTER = new ExtendedJsonRegularExpressionConverter();
/*     */   
/*  77 */   private static final LegacyExtendedJsonRegularExpressionConverter LEGACY_EXTENDED_JSON_REGULAR_EXPRESSION_CONVERTER = new LegacyExtendedJsonRegularExpressionConverter();
/*     */   
/*  79 */   private static final ShellRegularExpressionConverter SHELL_REGULAR_EXPRESSION_CONVERTER = new ShellRegularExpressionConverter();
/*     */   
/*     */   private final boolean indent;
/*     */   
/*     */   private final String newLineCharacters;
/*     */   
/*     */   private final String indentCharacters;
/*     */   
/*     */   private final int maxLength;
/*     */   
/*     */   private final JsonMode outputMode;
/*     */   
/*     */   private final Converter<BsonNull> nullConverter;
/*     */   
/*     */   private final Converter<String> stringConverter;
/*     */   
/*     */   private final Converter<Long> dateTimeConverter;
/*     */   
/*     */   private final Converter<BsonBinary> binaryConverter;
/*     */   
/*     */   private final Converter<Boolean> booleanConverter;
/*     */   private final Converter<Double> doubleConverter;
/*     */   private final Converter<Integer> int32Converter;
/*     */   private final Converter<Long> int64Converter;
/*     */   private final Converter<Decimal128> decimal128Converter;
/*     */   private final Converter<ObjectId> objectIdConverter;
/*     */   private final Converter<BsonTimestamp> timestampConverter;
/*     */   private final Converter<BsonRegularExpression> regularExpressionConverter;
/*     */   private final Converter<String> symbolConverter;
/*     */   private final Converter<BsonUndefined> undefinedConverter;
/*     */   private final Converter<BsonMinKey> minKeyConverter;
/*     */   private final Converter<BsonMaxKey> maxKeyConverter;
/*     */   private final Converter<String> javaScriptConverter;
/*     */   
/*     */   public static Builder builder() {
/* 114 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonWriterSettings(Builder builder) {
/* 119 */     this.indent = builder.indent;
/* 120 */     this.newLineCharacters = (builder.newLineCharacters != null) ? builder.newLineCharacters : System.getProperty("line.separator");
/* 121 */     this.indentCharacters = builder.indentCharacters;
/* 122 */     this.outputMode = builder.outputMode;
/* 123 */     this.maxLength = builder.maxLength;
/*     */     
/* 125 */     if (builder.nullConverter != null) {
/* 126 */       this.nullConverter = builder.nullConverter;
/*     */     } else {
/* 128 */       this.nullConverter = JSON_NULL_CONVERTER;
/*     */     } 
/*     */     
/* 131 */     if (builder.stringConverter != null) {
/* 132 */       this.stringConverter = builder.stringConverter;
/*     */     } else {
/* 134 */       this.stringConverter = JSON_STRING_CONVERTER;
/*     */     } 
/*     */     
/* 137 */     if (builder.booleanConverter != null) {
/* 138 */       this.booleanConverter = builder.booleanConverter;
/*     */     } else {
/* 140 */       this.booleanConverter = JSON_BOOLEAN_CONVERTER;
/*     */     } 
/*     */     
/* 143 */     if (builder.doubleConverter != null) {
/* 144 */       this.doubleConverter = builder.doubleConverter;
/* 145 */     } else if (this.outputMode == JsonMode.EXTENDED) {
/* 146 */       this.doubleConverter = EXTENDED_JSON_DOUBLE_CONVERTER;
/* 147 */     } else if (this.outputMode == JsonMode.RELAXED) {
/* 148 */       this.doubleConverter = RELAXED_EXTENDED_JSON_DOUBLE_CONVERTER;
/*     */     } else {
/* 150 */       this.doubleConverter = JSON_DOUBLE_CONVERTER;
/*     */     } 
/*     */     
/* 153 */     if (builder.int32Converter != null) {
/* 154 */       this.int32Converter = builder.int32Converter;
/* 155 */     } else if (this.outputMode == JsonMode.EXTENDED) {
/* 156 */       this.int32Converter = EXTENDED_JSON_INT_32_CONVERTER;
/*     */     } else {
/*     */       
/* 159 */       this.int32Converter = JSON_INT_32_CONVERTER;
/*     */     } 
/*     */     
/* 162 */     if (builder.symbolConverter != null) {
/* 163 */       this.symbolConverter = builder.symbolConverter;
/*     */     } else {
/* 165 */       this.symbolConverter = JSON_SYMBOL_CONVERTER;
/*     */     } 
/*     */     
/* 168 */     if (builder.javaScriptConverter != null) {
/* 169 */       this.javaScriptConverter = builder.javaScriptConverter;
/*     */     } else {
/* 171 */       this.javaScriptConverter = new JsonJavaScriptConverter();
/*     */     } 
/*     */     
/* 174 */     if (builder.minKeyConverter != null) {
/* 175 */       this.minKeyConverter = builder.minKeyConverter;
/* 176 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 177 */       this.minKeyConverter = EXTENDED_JSON_MIN_KEY_CONVERTER;
/*     */     } else {
/* 179 */       this.minKeyConverter = SHELL_MIN_KEY_CONVERTER;
/*     */     } 
/*     */     
/* 182 */     if (builder.maxKeyConverter != null) {
/* 183 */       this.maxKeyConverter = builder.maxKeyConverter;
/* 184 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 185 */       this.maxKeyConverter = EXTENDED_JSON_MAX_KEY_CONVERTER;
/*     */     } else {
/* 187 */       this.maxKeyConverter = SHELL_MAX_KEY_CONVERTER;
/*     */     } 
/*     */     
/* 190 */     if (builder.undefinedConverter != null) {
/* 191 */       this.undefinedConverter = builder.undefinedConverter;
/* 192 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 193 */       this.undefinedConverter = EXTENDED_JSON_UNDEFINED_CONVERTER;
/*     */     } else {
/* 195 */       this.undefinedConverter = SHELL_UNDEFINED_CONVERTER;
/*     */     } 
/*     */     
/* 198 */     if (builder.dateTimeConverter != null) {
/* 199 */       this.dateTimeConverter = builder.dateTimeConverter;
/* 200 */     } else if (this.outputMode == JsonMode.STRICT) {
/* 201 */       this.dateTimeConverter = LEGACY_EXTENDED_JSON_DATE_TIME_CONVERTER;
/* 202 */     } else if (this.outputMode == JsonMode.EXTENDED) {
/* 203 */       this.dateTimeConverter = EXTENDED_JSON_DATE_TIME_CONVERTER;
/* 204 */     } else if (this.outputMode == JsonMode.RELAXED) {
/* 205 */       this.dateTimeConverter = RELAXED_EXTENDED_JSON_DATE_TIME_CONVERTER;
/*     */     } else {
/* 207 */       this.dateTimeConverter = SHELL_DATE_TIME_CONVERTER;
/*     */     } 
/*     */     
/* 210 */     if (builder.binaryConverter != null) {
/* 211 */       this.binaryConverter = builder.binaryConverter;
/* 212 */     } else if (this.outputMode == JsonMode.STRICT) {
/* 213 */       this.binaryConverter = LEGACY_EXTENDED_JSON_BINARY_CONVERTER;
/* 214 */     } else if (this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 215 */       this.binaryConverter = EXTENDED_JSON_BINARY_CONVERTER;
/*     */     } else {
/* 217 */       this.binaryConverter = SHELL_BINARY_CONVERTER;
/*     */     } 
/*     */     
/* 220 */     if (builder.int64Converter != null) {
/* 221 */       this.int64Converter = builder.int64Converter;
/* 222 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED) {
/* 223 */       this.int64Converter = EXTENDED_JSON_INT_64_CONVERTER;
/* 224 */     } else if (this.outputMode == JsonMode.RELAXED) {
/* 225 */       this.int64Converter = RELAXED_JSON_INT_64_CONVERTER;
/*     */     } else {
/* 227 */       this.int64Converter = SHELL_INT_64_CONVERTER;
/*     */     } 
/*     */     
/* 230 */     if (builder.decimal128Converter != null) {
/* 231 */       this.decimal128Converter = builder.decimal128Converter;
/* 232 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 233 */       this.decimal128Converter = EXTENDED_JSON_DECIMAL_128_CONVERTER;
/*     */     } else {
/* 235 */       this.decimal128Converter = SHELL_DECIMAL_128_CONVERTER;
/*     */     } 
/*     */     
/* 238 */     if (builder.objectIdConverter != null) {
/* 239 */       this.objectIdConverter = builder.objectIdConverter;
/* 240 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 241 */       this.objectIdConverter = EXTENDED_JSON_OBJECT_ID_CONVERTER;
/*     */     } else {
/* 243 */       this.objectIdConverter = SHELL_OBJECT_ID_CONVERTER;
/*     */     } 
/*     */     
/* 246 */     if (builder.timestampConverter != null) {
/* 247 */       this.timestampConverter = builder.timestampConverter;
/* 248 */     } else if (this.outputMode == JsonMode.STRICT || this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 249 */       this.timestampConverter = EXTENDED_JSON_TIMESTAMP_CONVERTER;
/*     */     } else {
/* 251 */       this.timestampConverter = SHELL_TIMESTAMP_CONVERTER;
/*     */     } 
/*     */     
/* 254 */     if (builder.regularExpressionConverter != null) {
/* 255 */       this.regularExpressionConverter = builder.regularExpressionConverter;
/* 256 */     } else if (this.outputMode == JsonMode.EXTENDED || this.outputMode == JsonMode.RELAXED) {
/* 257 */       this.regularExpressionConverter = EXTENDED_JSON_REGULAR_EXPRESSION_CONVERTER;
/* 258 */     } else if (this.outputMode == JsonMode.STRICT) {
/* 259 */       this.regularExpressionConverter = LEGACY_EXTENDED_JSON_REGULAR_EXPRESSION_CONVERTER;
/*     */     } else {
/* 261 */       this.regularExpressionConverter = SHELL_REGULAR_EXPRESSION_CONVERTER;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIndent() {
/* 272 */     return this.indent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewLineCharacters() {
/* 281 */     return this.newLineCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndentCharacters() {
/* 290 */     return this.indentCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonMode getOutputMode() {
/* 299 */     return this.outputMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLength() {
/* 309 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonNull> getNullConverter() {
/* 319 */     return this.nullConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<String> getStringConverter() {
/* 329 */     return this.stringConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonBinary> getBinaryConverter() {
/* 339 */     return this.binaryConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Boolean> getBooleanConverter() {
/* 349 */     return this.booleanConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Long> getDateTimeConverter() {
/* 359 */     return this.dateTimeConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Double> getDoubleConverter() {
/* 369 */     return this.doubleConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Integer> getInt32Converter() {
/* 379 */     return this.int32Converter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Long> getInt64Converter() {
/* 389 */     return this.int64Converter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<Decimal128> getDecimal128Converter() {
/* 399 */     return this.decimal128Converter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<ObjectId> getObjectIdConverter() {
/* 409 */     return this.objectIdConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonRegularExpression> getRegularExpressionConverter() {
/* 419 */     return this.regularExpressionConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonTimestamp> getTimestampConverter() {
/* 429 */     return this.timestampConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<String> getSymbolConverter() {
/* 439 */     return this.symbolConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonMinKey> getMinKeyConverter() {
/* 449 */     return this.minKeyConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonMaxKey> getMaxKeyConverter() {
/* 459 */     return this.maxKeyConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<BsonUndefined> getUndefinedConverter() {
/* 469 */     return this.undefinedConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter<String> getJavaScriptConverter() {
/* 479 */     return this.javaScriptConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private boolean indent;
/*     */ 
/*     */     
/* 489 */     private String newLineCharacters = System.getProperty("line.separator");
/* 490 */     private String indentCharacters = "  ";
/* 491 */     private JsonMode outputMode = JsonMode.RELAXED;
/*     */     
/*     */     private int maxLength;
/*     */     
/*     */     private Converter<BsonNull> nullConverter;
/*     */     
/*     */     private Converter<String> stringConverter;
/*     */     
/*     */     private Converter<Long> dateTimeConverter;
/*     */     
/*     */     private Converter<BsonBinary> binaryConverter;
/*     */     private Converter<Boolean> booleanConverter;
/*     */     private Converter<Double> doubleConverter;
/*     */     private Converter<Integer> int32Converter;
/*     */     private Converter<Long> int64Converter;
/*     */     private Converter<Decimal128> decimal128Converter;
/*     */     private Converter<ObjectId> objectIdConverter;
/*     */     private Converter<BsonTimestamp> timestampConverter;
/*     */     private Converter<BsonRegularExpression> regularExpressionConverter;
/*     */     private Converter<String> symbolConverter;
/*     */     private Converter<BsonUndefined> undefinedConverter;
/*     */     private Converter<BsonMinKey> minKeyConverter;
/*     */     private Converter<BsonMaxKey> maxKeyConverter;
/*     */     private Converter<String> javaScriptConverter;
/*     */     
/*     */     public JsonWriterSettings build() {
/* 517 */       return new JsonWriterSettings(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder indent(boolean indent) {
/* 527 */       this.indent = indent;
/* 528 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder newLineCharacters(String newLineCharacters) {
/* 539 */       Assertions.notNull("newLineCharacters", newLineCharacters);
/* 540 */       this.newLineCharacters = newLineCharacters;
/* 541 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder indentCharacters(String indentCharacters) {
/* 551 */       Assertions.notNull("indentCharacters", indentCharacters);
/* 552 */       this.indentCharacters = indentCharacters;
/* 553 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder outputMode(JsonMode outputMode) {
/* 563 */       Assertions.notNull("outputMode", outputMode);
/* 564 */       this.outputMode = outputMode;
/* 565 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxLength(int maxLength) {
/* 576 */       Assertions.isTrueArgument("maxLength >= 0", (maxLength >= 0));
/* 577 */       this.maxLength = maxLength;
/* 578 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder nullConverter(Converter<BsonNull> nullConverter) {
/* 588 */       this.nullConverter = nullConverter;
/* 589 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder stringConverter(Converter<String> stringConverter) {
/* 599 */       this.stringConverter = stringConverter;
/* 600 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder dateTimeConverter(Converter<Long> dateTimeConverter) {
/* 610 */       this.dateTimeConverter = dateTimeConverter;
/* 611 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder binaryConverter(Converter<BsonBinary> binaryConverter) {
/* 621 */       this.binaryConverter = binaryConverter;
/* 622 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder booleanConverter(Converter<Boolean> booleanConverter) {
/* 632 */       this.booleanConverter = booleanConverter;
/* 633 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder doubleConverter(Converter<Double> doubleConverter) {
/* 643 */       this.doubleConverter = doubleConverter;
/* 644 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder int32Converter(Converter<Integer> int32Converter) {
/* 654 */       this.int32Converter = int32Converter;
/* 655 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder int64Converter(Converter<Long> int64Converter) {
/* 665 */       this.int64Converter = int64Converter;
/* 666 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder decimal128Converter(Converter<Decimal128> decimal128Converter) {
/* 676 */       this.decimal128Converter = decimal128Converter;
/* 677 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder objectIdConverter(Converter<ObjectId> objectIdConverter) {
/* 687 */       this.objectIdConverter = objectIdConverter;
/* 688 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder timestampConverter(Converter<BsonTimestamp> timestampConverter) {
/* 698 */       this.timestampConverter = timestampConverter;
/* 699 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder regularExpressionConverter(Converter<BsonRegularExpression> regularExpressionConverter) {
/* 709 */       this.regularExpressionConverter = regularExpressionConverter;
/* 710 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder symbolConverter(Converter<String> symbolConverter) {
/* 720 */       this.symbolConverter = symbolConverter;
/* 721 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder minKeyConverter(Converter<BsonMinKey> minKeyConverter) {
/* 731 */       this.minKeyConverter = minKeyConverter;
/* 732 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxKeyConverter(Converter<BsonMaxKey> maxKeyConverter) {
/* 742 */       this.maxKeyConverter = maxKeyConverter;
/* 743 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder undefinedConverter(Converter<BsonUndefined> undefinedConverter) {
/* 753 */       this.undefinedConverter = undefinedConverter;
/* 754 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder javaScriptConverter(Converter<String> javaScriptConverter) {
/* 764 */       this.javaScriptConverter = javaScriptConverter;
/* 765 */       return this;
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonWriterSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */