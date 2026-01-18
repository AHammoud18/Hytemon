/*      */ package com.google.gson;
/*      */ 
/*      */ import com.google.gson.internal.ConstructorConstructor;
/*      */ import com.google.gson.internal.Excluder;
/*      */ import com.google.gson.internal.LazilyParsedNumber;
/*      */ import com.google.gson.internal.Primitives;
/*      */ import com.google.gson.internal.Streams;
/*      */ import com.google.gson.internal.bind.ArrayTypeAdapter;
/*      */ import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.DefaultDateTypeAdapter;
/*      */ import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.JsonTreeReader;
/*      */ import com.google.gson.internal.bind.JsonTreeWriter;
/*      */ import com.google.gson.internal.bind.MapTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.NumberTypeAdapter;
/*      */ import com.google.gson.internal.bind.ObjectTypeAdapter;
/*      */ import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.SerializationDelegatingTypeAdapter;
/*      */ import com.google.gson.internal.bind.TypeAdapters;
/*      */ import com.google.gson.internal.sql.SqlTypesSupport;
/*      */ import com.google.gson.reflect.TypeToken;
/*      */ import com.google.gson.stream.JsonReader;
/*      */ import com.google.gson.stream.JsonToken;
/*      */ import com.google.gson.stream.JsonWriter;
/*      */ import com.google.gson.stream.MalformedJsonException;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Type;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Gson
/*      */ {
/*      */   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
/*  154 */   static final Strictness DEFAULT_STRICTNESS = null;
/*  155 */   static final FormattingStyle DEFAULT_FORMATTING_STYLE = FormattingStyle.COMPACT;
/*      */   static final boolean DEFAULT_ESCAPE_HTML = true;
/*      */   static final boolean DEFAULT_SERIALIZE_NULLS = false;
/*      */   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
/*      */   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
/*      */   static final boolean DEFAULT_USE_JDK_UNSAFE = true;
/*  161 */   static final String DEFAULT_DATE_PATTERN = null;
/*  162 */   static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
/*  163 */   static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
/*  164 */   static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  180 */   private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */   
/*  184 */   private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();
/*      */ 
/*      */   
/*      */   private final ConstructorConstructor constructorConstructor;
/*      */ 
/*      */   
/*      */   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
/*      */ 
/*      */   
/*      */   final List<TypeAdapterFactory> factories;
/*      */ 
/*      */   
/*      */   final Excluder excluder;
/*      */ 
/*      */   
/*      */   final FieldNamingStrategy fieldNamingStrategy;
/*      */ 
/*      */   
/*      */   final Map<Type, InstanceCreator<?>> instanceCreators;
/*      */ 
/*      */   
/*      */   final boolean serializeNulls;
/*      */ 
/*      */   
/*      */   final boolean complexMapKeySerialization;
/*      */ 
/*      */   
/*      */   final boolean generateNonExecutableJson;
/*      */ 
/*      */   
/*      */   final boolean htmlSafe;
/*      */ 
/*      */   
/*      */   final FormattingStyle formattingStyle;
/*      */ 
/*      */   
/*      */   final Strictness strictness;
/*      */ 
/*      */   
/*      */   final boolean serializeSpecialFloatingPointValues;
/*      */ 
/*      */   
/*      */   final boolean useJdkUnsafe;
/*      */ 
/*      */   
/*      */   final String datePattern;
/*      */ 
/*      */   
/*      */   final int dateStyle;
/*      */ 
/*      */   
/*      */   final int timeStyle;
/*      */ 
/*      */   
/*      */   final LongSerializationPolicy longSerializationPolicy;
/*      */ 
/*      */   
/*      */   final List<TypeAdapterFactory> builderFactories;
/*      */   
/*      */   final List<TypeAdapterFactory> builderHierarchyFactories;
/*      */   
/*      */   final ToNumberStrategy objectToNumberStrategy;
/*      */   
/*      */   final ToNumberStrategy numberToNumberStrategy;
/*      */   
/*      */   final List<ReflectionAccessFilter> reflectionFilters;
/*      */ 
/*      */   
/*      */   public Gson() {
/*  253 */     this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, 
/*      */ 
/*      */         
/*  256 */         Collections.emptyMap(), false, false, false, true, DEFAULT_FORMATTING_STYLE, DEFAULT_STRICTNESS, false, true, LongSerializationPolicy.DEFAULT, DEFAULT_DATE_PATTERN, 2, 2, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  269 */         Collections.emptyList(), 
/*  270 */         Collections.emptyList(), 
/*  271 */         Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY, 
/*      */ 
/*      */         
/*  274 */         Collections.emptyList());
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
/*      */   Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, FormattingStyle formattingStyle, Strictness strictness, boolean serializeSpecialFloatingPointValues, boolean useJdkUnsafe, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> builderFactories, List<TypeAdapterFactory> builderHierarchyFactories, List<TypeAdapterFactory> factoriesToBeAdded, ToNumberStrategy objectToNumberStrategy, ToNumberStrategy numberToNumberStrategy, List<ReflectionAccessFilter> reflectionFilters) {
/*  299 */     this.excluder = excluder;
/*  300 */     this.fieldNamingStrategy = fieldNamingStrategy;
/*  301 */     this.instanceCreators = instanceCreators;
/*  302 */     this.constructorConstructor = new ConstructorConstructor(instanceCreators, useJdkUnsafe, reflectionFilters);
/*      */     
/*  304 */     this.serializeNulls = serializeNulls;
/*  305 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*  306 */     this.generateNonExecutableJson = generateNonExecutableGson;
/*  307 */     this.htmlSafe = htmlSafe;
/*  308 */     this.formattingStyle = formattingStyle;
/*  309 */     this.strictness = strictness;
/*  310 */     this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
/*  311 */     this.useJdkUnsafe = useJdkUnsafe;
/*  312 */     this.longSerializationPolicy = longSerializationPolicy;
/*  313 */     this.datePattern = datePattern;
/*  314 */     this.dateStyle = dateStyle;
/*  315 */     this.timeStyle = timeStyle;
/*  316 */     this.builderFactories = builderFactories;
/*  317 */     this.builderHierarchyFactories = builderHierarchyFactories;
/*  318 */     this.objectToNumberStrategy = objectToNumberStrategy;
/*  319 */     this.numberToNumberStrategy = numberToNumberStrategy;
/*  320 */     this.reflectionFilters = reflectionFilters;
/*      */     
/*  322 */     List<TypeAdapterFactory> factories = new ArrayList<>();
/*      */ 
/*      */     
/*  325 */     factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
/*  326 */     factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
/*      */ 
/*      */     
/*  329 */     factories.add(excluder);
/*      */ 
/*      */     
/*  332 */     factories.addAll(factoriesToBeAdded);
/*      */ 
/*      */     
/*  335 */     factories.add(TypeAdapters.STRING_FACTORY);
/*  336 */     factories.add(TypeAdapters.INTEGER_FACTORY);
/*  337 */     factories.add(TypeAdapters.BOOLEAN_FACTORY);
/*  338 */     factories.add(TypeAdapters.BYTE_FACTORY);
/*  339 */     factories.add(TypeAdapters.SHORT_FACTORY);
/*  340 */     TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
/*  341 */     factories.add(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
/*  342 */     factories.add(
/*  343 */         TypeAdapters.newFactory(double.class, Double.class, 
/*  344 */           doubleAdapter(serializeSpecialFloatingPointValues)));
/*  345 */     factories.add(
/*  346 */         TypeAdapters.newFactory(float.class, Float.class, 
/*  347 */           floatAdapter(serializeSpecialFloatingPointValues)));
/*  348 */     factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
/*  349 */     factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
/*  350 */     factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
/*  351 */     factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
/*  352 */     factories.add(
/*  353 */         TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
/*  354 */     factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
/*  355 */     factories.add(TypeAdapters.CHARACTER_FACTORY);
/*  356 */     factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
/*  357 */     factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
/*  358 */     factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
/*  359 */     factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
/*      */ 
/*      */     
/*  362 */     factories.add(
/*  363 */         TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
/*  364 */     factories.add(TypeAdapters.URL_FACTORY);
/*  365 */     factories.add(TypeAdapters.URI_FACTORY);
/*  366 */     factories.add(TypeAdapters.UUID_FACTORY);
/*  367 */     factories.add(TypeAdapters.CURRENCY_FACTORY);
/*  368 */     factories.add(TypeAdapters.LOCALE_FACTORY);
/*  369 */     factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
/*  370 */     factories.add(TypeAdapters.BIT_SET_FACTORY);
/*  371 */     factories.add(DefaultDateTypeAdapter.DEFAULT_STYLE_FACTORY);
/*  372 */     factories.add(TypeAdapters.CALENDAR_FACTORY);
/*      */     
/*  374 */     if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
/*  375 */       factories.add(SqlTypesSupport.TIME_FACTORY);
/*  376 */       factories.add(SqlTypesSupport.DATE_FACTORY);
/*  377 */       factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
/*      */     } 
/*      */     
/*  380 */     factories.add(ArrayTypeAdapter.FACTORY);
/*  381 */     factories.add(TypeAdapters.CLASS_FACTORY);
/*      */ 
/*      */     
/*  384 */     factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
/*  385 */     factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
/*  386 */     this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
/*  387 */     factories.add(this.jsonAdapterFactory);
/*  388 */     factories.add(TypeAdapters.ENUM_FACTORY);
/*  389 */     factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, reflectionFilters));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     this.factories = Collections.unmodifiableList(factories);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GsonBuilder newBuilder() {
/*  408 */     return new GsonBuilder(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Excluder excluder() {
/*  417 */     return this.excluder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldNamingStrategy fieldNamingStrategy() {
/*  426 */     return this.fieldNamingStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean serializeNulls() {
/*  436 */     return this.serializeNulls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean htmlSafe() {
/*  446 */     return this.htmlSafe;
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
/*  450 */     if (serializeSpecialFloatingPointValues) {
/*  451 */       return TypeAdapters.DOUBLE;
/*      */     }
/*  453 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Double read(JsonReader in) throws IOException {
/*  456 */           if (in.peek() == JsonToken.NULL) {
/*  457 */             in.nextNull();
/*  458 */             return null;
/*      */           } 
/*  460 */           return Double.valueOf(in.nextDouble());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  465 */           if (value == null) {
/*  466 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  469 */           double doubleValue = value.doubleValue();
/*  470 */           Gson.checkValidFloatingPoint(doubleValue);
/*  471 */           out.value(doubleValue);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
/*  477 */     if (serializeSpecialFloatingPointValues) {
/*  478 */       return TypeAdapters.FLOAT;
/*      */     }
/*  480 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Float read(JsonReader in) throws IOException {
/*  483 */           if (in.peek() == JsonToken.NULL) {
/*  484 */             in.nextNull();
/*  485 */             return null;
/*      */           } 
/*  487 */           return Float.valueOf((float)in.nextDouble());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  492 */           if (value == null) {
/*  493 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  496 */           float floatValue = value.floatValue();
/*  497 */           Gson.checkValidFloatingPoint(floatValue);
/*      */ 
/*      */           
/*  500 */           Number floatNumber = (value instanceof Float) ? value : Float.valueOf(floatValue);
/*  501 */           out.value(floatNumber);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   static void checkValidFloatingPoint(double value) {
/*  507 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/*  508 */       throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
/*  516 */     if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
/*  517 */       return TypeAdapters.LONG;
/*      */     }
/*  519 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Number read(JsonReader in) throws IOException {
/*  522 */           if (in.peek() == JsonToken.NULL) {
/*  523 */             in.nextNull();
/*  524 */             return null;
/*      */           } 
/*  526 */           return Long.valueOf(in.nextLong());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  531 */           if (value == null) {
/*  532 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  535 */           out.value(value.toString());
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
/*  541 */     return (new TypeAdapter<AtomicLong>()
/*      */       {
/*      */         public void write(JsonWriter out, AtomicLong value) throws IOException {
/*  544 */           longAdapter.write(out, Long.valueOf(value.get()));
/*      */         }
/*      */ 
/*      */         
/*      */         public AtomicLong read(JsonReader in) throws IOException {
/*  549 */           Number value = longAdapter.read(in);
/*  550 */           return new AtomicLong(value.longValue());
/*      */         }
/*  552 */       }).nullSafe();
/*      */   }
/*      */ 
/*      */   
/*      */   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
/*  557 */     return (new TypeAdapter<AtomicLongArray>()
/*      */       {
/*      */         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
/*  560 */           out.beginArray();
/*  561 */           for (int i = 0, length = value.length(); i < length; i++) {
/*  562 */             longAdapter.write(out, Long.valueOf(value.get(i)));
/*      */           }
/*  564 */           out.endArray();
/*      */         }
/*      */ 
/*      */         
/*      */         public AtomicLongArray read(JsonReader in) throws IOException {
/*  569 */           List<Long> list = new ArrayList<>();
/*  570 */           in.beginArray();
/*  571 */           while (in.hasNext()) {
/*  572 */             long value = ((Number)longAdapter.read(in)).longValue();
/*  573 */             list.add(Long.valueOf(value));
/*      */           } 
/*  575 */           in.endArray();
/*  576 */           int length = list.size();
/*  577 */           AtomicLongArray array = new AtomicLongArray(length);
/*  578 */           for (int i = 0; i < length; i++) {
/*  579 */             array.set(i, ((Long)list.get(i)).longValue());
/*      */           }
/*  581 */           return array;
/*      */         }
/*  583 */       }).nullSafe();
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
/*      */   public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
/*  598 */     Objects.requireNonNull(type, "type must not be null");
/*  599 */     TypeAdapter<?> cached = this.typeTokenCache.get(type);
/*  600 */     if (cached != null) {
/*      */       
/*  602 */       TypeAdapter<T> adapter = (TypeAdapter)cached;
/*  603 */       return adapter;
/*      */     } 
/*      */     
/*  606 */     Map<TypeToken<?>, TypeAdapter<?>> threadCalls = this.threadLocalAdapterResults.get();
/*  607 */     boolean isInitialAdapterRequest = false;
/*  608 */     if (threadCalls == null) {
/*  609 */       threadCalls = new HashMap<>();
/*  610 */       this.threadLocalAdapterResults.set(threadCalls);
/*  611 */       isInitialAdapterRequest = true;
/*      */     }
/*      */     else {
/*      */       
/*  615 */       TypeAdapter<T> ongoingCall = (TypeAdapter<T>)threadCalls.get(type);
/*  616 */       if (ongoingCall != null) {
/*  617 */         return ongoingCall;
/*      */       }
/*      */     } 
/*      */     
/*  621 */     TypeAdapter<T> candidate = null;
/*      */     try {
/*  623 */       FutureTypeAdapter<T> call = new FutureTypeAdapter<>();
/*  624 */       threadCalls.put(type, call);
/*      */       
/*  626 */       for (TypeAdapterFactory factory : this.factories) {
/*  627 */         candidate = factory.create(this, type);
/*  628 */         if (candidate != null) {
/*  629 */           call.setDelegate(candidate);
/*      */           
/*  631 */           threadCalls.put(type, candidate);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  636 */       if (isInitialAdapterRequest) {
/*  637 */         this.threadLocalAdapterResults.remove();
/*      */       }
/*      */     } 
/*      */     
/*  641 */     if (candidate == null) {
/*  642 */       throw new IllegalArgumentException("GSON (2.13.2) cannot handle " + type);
/*      */     }
/*      */ 
/*      */     
/*  646 */     if (isInitialAdapterRequest)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  653 */       this.typeTokenCache.putAll(threadCalls);
/*      */     }
/*  655 */     return candidate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(Class<T> type) {
/*  665 */     return getAdapter(TypeToken.get(type));
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
/*      */   
/*      */   public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
/*      */     JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory;
/*  730 */     Objects.requireNonNull(skipPast, "skipPast must not be null");
/*  731 */     Objects.requireNonNull(type, "type must not be null");
/*      */     
/*  733 */     if (this.jsonAdapterFactory.isClassJsonAdapterFactory(type, skipPast)) {
/*  734 */       jsonAdapterAnnotationTypeAdapterFactory = this.jsonAdapterFactory;
/*      */     }
/*      */     
/*  737 */     boolean skipPastFound = false;
/*  738 */     for (TypeAdapterFactory factory : this.factories) {
/*  739 */       if (!skipPastFound) {
/*  740 */         if (factory == jsonAdapterAnnotationTypeAdapterFactory) {
/*  741 */           skipPastFound = true;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  746 */       TypeAdapter<T> candidate = factory.create(this, type);
/*  747 */       if (candidate != null) {
/*  748 */         return candidate;
/*      */       }
/*      */     } 
/*      */     
/*  752 */     if (skipPastFound) {
/*  753 */       throw new IllegalArgumentException("GSON cannot serialize or deserialize " + type);
/*      */     }
/*      */     
/*  756 */     return getAdapter(type);
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
/*      */   public JsonElement toJsonTree(Object src) {
/*  775 */     if (src == null) {
/*  776 */       return JsonNull.INSTANCE;
/*      */     }
/*  778 */     return toJsonTree(src, src.getClass());
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
/*      */   public JsonElement toJsonTree(Object src, Type typeOfSrc) {
/*  800 */     JsonTreeWriter writer = new JsonTreeWriter();
/*  801 */     toJson(src, typeOfSrc, (JsonWriter)writer);
/*  802 */     return writer.get();
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
/*      */   public String toJson(Object src) {
/*  821 */     if (src == null) {
/*  822 */       return toJson(JsonNull.INSTANCE);
/*      */     }
/*  824 */     return toJson(src, src.getClass());
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
/*      */   public String toJson(Object src, Type typeOfSrc) {
/*  846 */     StringBuilder writer = new StringBuilder();
/*  847 */     toJson(src, typeOfSrc, writer);
/*  848 */     return writer.toString();
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
/*      */   public void toJson(Object src, Appendable writer) throws JsonIOException {
/*  868 */     if (src != null) {
/*  869 */       toJson(src, src.getClass(), writer);
/*      */     } else {
/*  871 */       toJson(JsonNull.INSTANCE, writer);
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
/*      */   public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
/*      */     try {
/*  897 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  898 */       toJson(src, typeOfSrc, jsonWriter);
/*  899 */     } catch (IOException e) {
/*  900 */       throw new JsonIOException(e);
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
/*      */   public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
/*  927 */     TypeAdapter<Object> adapter = getAdapter(TypeToken.get(typeOfSrc));
/*      */     
/*  929 */     Strictness oldStrictness = writer.getStrictness();
/*  930 */     if (this.strictness != null) {
/*  931 */       writer.setStrictness(this.strictness);
/*  932 */     } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/*  934 */       writer.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*  937 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  938 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*      */     
/*  940 */     writer.setHtmlSafe(this.htmlSafe);
/*  941 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  943 */       adapter.write(writer, src);
/*  944 */     } catch (IOException e) {
/*  945 */       throw new JsonIOException(e);
/*  946 */     } catch (AssertionError e) {
/*  947 */       throw new AssertionError("AssertionError (GSON 2.13.2): " + e
/*  948 */           .getMessage(), e);
/*      */     } finally {
/*  950 */       writer.setStrictness(oldStrictness);
/*  951 */       writer.setHtmlSafe(oldHtmlSafe);
/*  952 */       writer.setSerializeNulls(oldSerializeNulls);
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
/*      */   public String toJson(JsonElement jsonElement) {
/*  964 */     StringBuilder writer = new StringBuilder();
/*  965 */     toJson(jsonElement, writer);
/*  966 */     return writer.toString();
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
/*      */   public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
/*      */     try {
/*  979 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  980 */       toJson(jsonElement, jsonWriter);
/*  981 */     } catch (IOException e) {
/*  982 */       throw new JsonIOException(e);
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
/*      */   public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
/* 1007 */     Strictness oldStrictness = writer.getStrictness();
/* 1008 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/* 1009 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*      */     
/* 1011 */     writer.setHtmlSafe(this.htmlSafe);
/* 1012 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     
/* 1014 */     if (this.strictness != null) {
/* 1015 */       writer.setStrictness(this.strictness);
/* 1016 */     } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/* 1018 */       writer.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*      */     try {
/* 1022 */       Streams.write(jsonElement, writer);
/* 1023 */     } catch (IOException e) {
/* 1024 */       throw new JsonIOException(e);
/* 1025 */     } catch (AssertionError e) {
/* 1026 */       throw new AssertionError("AssertionError (GSON 2.13.2): " + e
/* 1027 */           .getMessage(), e);
/*      */     } finally {
/* 1029 */       writer.setStrictness(oldStrictness);
/* 1030 */       writer.setHtmlSafe(oldHtmlSafe);
/* 1031 */       writer.setSerializeNulls(oldSerializeNulls);
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
/*      */   public JsonWriter newJsonWriter(Writer writer) throws IOException {
/* 1053 */     if (this.generateNonExecutableJson) {
/* 1054 */       writer.write(")]}'\n");
/*      */     }
/* 1056 */     JsonWriter jsonWriter = new JsonWriter(writer);
/* 1057 */     jsonWriter.setFormattingStyle(this.formattingStyle);
/* 1058 */     jsonWriter.setHtmlSafe(this.htmlSafe);
/* 1059 */     jsonWriter.setStrictness((this.strictness == null) ? Strictness.LEGACY_STRICT : this.strictness);
/* 1060 */     jsonWriter.setSerializeNulls(this.serializeNulls);
/* 1061 */     return jsonWriter;
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
/*      */   public JsonReader newJsonReader(Reader reader) {
/* 1077 */     JsonReader jsonReader = new JsonReader(reader);
/* 1078 */     jsonReader.setStrictness((this.strictness == null) ? Strictness.LEGACY_STRICT : this.strictness);
/* 1079 */     return jsonReader;
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
/*      */   public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
/* 1106 */     return fromJson(json, TypeToken.get(classOfT));
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
/*      */   public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
/* 1135 */     return fromJson(json, TypeToken.get(typeOfT));
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
/*      */   public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
/* 1165 */     if (json == null) {
/* 1166 */       return null;
/*      */     }
/* 1168 */     StringReader reader = new StringReader(json);
/* 1169 */     return fromJson(reader, typeOfT);
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
/*      */   public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
/* 1197 */     return fromJson(json, TypeToken.get(classOfT));
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
/*      */   public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1227 */     return fromJson(json, TypeToken.get(typeOfT));
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
/*      */   public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1258 */     JsonReader jsonReader = newJsonReader(json);
/* 1259 */     T object = fromJson(jsonReader, typeOfT);
/* 1260 */     assertFullConsumption(object, jsonReader);
/* 1261 */     return object;
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
/*      */   public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1303 */     return fromJson(reader, TypeToken.get(typeOfT));
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
/*      */   public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1344 */     boolean isEmpty = true;
/* 1345 */     Strictness oldStrictness = reader.getStrictness();
/*      */     
/* 1347 */     if (this.strictness != null) {
/* 1348 */       reader.setStrictness(this.strictness);
/* 1349 */     } else if (reader.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/* 1351 */       reader.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*      */     try {
/* 1355 */       JsonToken unused = reader.peek();
/* 1356 */       isEmpty = false;
/* 1357 */       TypeAdapter<T> typeAdapter = getAdapter(typeOfT);
/* 1358 */       T object = typeAdapter.read(reader);
/* 1359 */       Class<?> expectedTypeWrapped = Primitives.wrap(typeOfT.getRawType());
/* 1360 */       if (object != null && !expectedTypeWrapped.isInstance(object)) {
/* 1361 */         throw new ClassCastException("Type adapter '" + typeAdapter + "' returned wrong type; requested " + typeOfT
/*      */ 
/*      */ 
/*      */             
/* 1365 */             .getRawType() + " but got instance of " + object
/*      */             
/* 1367 */             .getClass() + "\nVerify that the adapter was registered for the correct type.");
/*      */       }
/*      */       
/* 1370 */       return object;
/* 1371 */     } catch (EOFException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1376 */       if (isEmpty) {
/* 1377 */         return null;
/*      */       }
/* 1379 */       throw new JsonSyntaxException(e);
/* 1380 */     } catch (IllegalStateException e) {
/* 1381 */       throw new JsonSyntaxException(e);
/* 1382 */     } catch (IOException e) {
/*      */       
/* 1384 */       throw new JsonSyntaxException(e);
/* 1385 */     } catch (AssertionError e) {
/* 1386 */       throw new AssertionError("AssertionError (GSON 2.13.2): " + e
/* 1387 */           .getMessage(), e);
/*      */     } finally {
/* 1389 */       reader.setStrictness(oldStrictness);
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
/*      */   public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
/* 1415 */     return fromJson(json, TypeToken.get(classOfT));
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
/*      */   public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
/* 1442 */     return fromJson(json, TypeToken.get(typeOfT));
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
/*      */   public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
/* 1468 */     if (json == null) {
/* 1469 */       return null;
/*      */     }
/* 1471 */     return fromJson((JsonReader)new JsonTreeReader(json), typeOfT);
/*      */   }
/*      */   
/*      */   private static void assertFullConsumption(Object obj, JsonReader reader) {
/*      */     try {
/* 1476 */       if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
/* 1477 */         throw new JsonSyntaxException("JSON document was not fully consumed.");
/*      */       }
/* 1479 */     } catch (MalformedJsonException e) {
/* 1480 */       throw new JsonSyntaxException(e);
/* 1481 */     } catch (IOException e) {
/* 1482 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class FutureTypeAdapter<T>
/*      */     extends SerializationDelegatingTypeAdapter<T>
/*      */   {
/*      */     private TypeAdapter<T> delegate;
/*      */ 
/*      */     
/*      */     FutureTypeAdapter() {
/* 1495 */       this.delegate = null;
/*      */     }
/*      */     public void setDelegate(TypeAdapter<T> typeAdapter) {
/* 1498 */       if (this.delegate != null) {
/* 1499 */         throw new AssertionError("Delegate is already set");
/*      */       }
/* 1501 */       this.delegate = typeAdapter;
/*      */     }
/*      */     
/*      */     private TypeAdapter<T> delegate() {
/* 1505 */       TypeAdapter<T> delegate = this.delegate;
/* 1506 */       if (delegate == null)
/*      */       {
/*      */ 
/*      */         
/* 1510 */         throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
/*      */       }
/*      */ 
/*      */       
/* 1514 */       return delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeAdapter<T> getSerializationDelegate() {
/* 1519 */       return delegate();
/*      */     }
/*      */ 
/*      */     
/*      */     public T read(JsonReader in) throws IOException {
/* 1524 */       return delegate().read(in);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(JsonWriter out, T value) throws IOException {
/* 1529 */       delegate().write(out, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1535 */     return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\Gson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */