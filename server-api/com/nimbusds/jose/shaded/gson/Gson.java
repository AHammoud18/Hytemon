/*      */ package com.nimbusds.jose.shaded.gson;
/*      */ 
/*      */ import com.nimbusds.jose.shaded.gson.internal.ConstructorConstructor;
/*      */ import com.nimbusds.jose.shaded.gson.internal.Excluder;
/*      */ import com.nimbusds.jose.shaded.gson.internal.LazilyParsedNumber;
/*      */ import com.nimbusds.jose.shaded.gson.internal.Primitives;
/*      */ import com.nimbusds.jose.shaded.gson.internal.Streams;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.ArrayTypeAdapter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.CollectionTypeAdapterFactory;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.DefaultDateTypeAdapter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.JsonTreeReader;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.JsonTreeWriter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.MapTypeAdapterFactory;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.NumberTypeAdapter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.ObjectTypeAdapter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.ReflectiveTypeAdapterFactory;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.SerializationDelegatingTypeAdapter;
/*      */ import com.nimbusds.jose.shaded.gson.internal.bind.TypeAdapters;
/*      */ import com.nimbusds.jose.shaded.gson.internal.sql.SqlTypesSupport;
/*      */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*      */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*      */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*      */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*      */ import com.nimbusds.jose.shaded.gson.stream.MalformedJsonException;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
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
/*  155 */   static final Strictness DEFAULT_STRICTNESS = null;
/*  156 */   static final FormattingStyle DEFAULT_FORMATTING_STYLE = FormattingStyle.COMPACT;
/*      */   static final boolean DEFAULT_ESCAPE_HTML = true;
/*      */   static final boolean DEFAULT_SERIALIZE_NULLS = false;
/*      */   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
/*      */   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
/*      */   static final boolean DEFAULT_USE_JDK_UNSAFE = true;
/*  162 */   static final String DEFAULT_DATE_PATTERN = null;
/*  163 */   static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
/*  164 */   static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
/*  165 */   static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
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
/*  181 */   private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */   
/*  185 */   private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();
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
/*  254 */     this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, 
/*      */ 
/*      */         
/*  257 */         Collections.emptyMap(), false, false, false, true, DEFAULT_FORMATTING_STYLE, DEFAULT_STRICTNESS, false, true, LongSerializationPolicy.DEFAULT, DEFAULT_DATE_PATTERN, 2, 2, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  270 */         Collections.emptyList(), 
/*  271 */         Collections.emptyList(), 
/*  272 */         Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY, 
/*      */ 
/*      */         
/*  275 */         Collections.emptyList());
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
/*  300 */     this.excluder = excluder;
/*  301 */     this.fieldNamingStrategy = fieldNamingStrategy;
/*  302 */     this.instanceCreators = instanceCreators;
/*  303 */     this.constructorConstructor = new ConstructorConstructor(instanceCreators, useJdkUnsafe, reflectionFilters);
/*      */     
/*  305 */     this.serializeNulls = serializeNulls;
/*  306 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*  307 */     this.generateNonExecutableJson = generateNonExecutableGson;
/*  308 */     this.htmlSafe = htmlSafe;
/*  309 */     this.formattingStyle = formattingStyle;
/*  310 */     this.strictness = strictness;
/*  311 */     this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
/*  312 */     this.useJdkUnsafe = useJdkUnsafe;
/*  313 */     this.longSerializationPolicy = longSerializationPolicy;
/*  314 */     this.datePattern = datePattern;
/*  315 */     this.dateStyle = dateStyle;
/*  316 */     this.timeStyle = timeStyle;
/*  317 */     this.builderFactories = builderFactories;
/*  318 */     this.builderHierarchyFactories = builderHierarchyFactories;
/*  319 */     this.objectToNumberStrategy = objectToNumberStrategy;
/*  320 */     this.numberToNumberStrategy = numberToNumberStrategy;
/*  321 */     this.reflectionFilters = reflectionFilters;
/*      */     
/*  323 */     List<TypeAdapterFactory> factories = new ArrayList<>();
/*      */ 
/*      */     
/*  326 */     factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
/*  327 */     factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
/*      */ 
/*      */     
/*  330 */     factories.add(excluder);
/*      */ 
/*      */     
/*  333 */     factories.addAll(factoriesToBeAdded);
/*      */ 
/*      */     
/*  336 */     factories.add(TypeAdapters.STRING_FACTORY);
/*  337 */     factories.add(TypeAdapters.INTEGER_FACTORY);
/*  338 */     factories.add(TypeAdapters.BOOLEAN_FACTORY);
/*  339 */     factories.add(TypeAdapters.BYTE_FACTORY);
/*  340 */     factories.add(TypeAdapters.SHORT_FACTORY);
/*  341 */     TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
/*  342 */     factories.add(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
/*  343 */     factories.add(
/*  344 */         TypeAdapters.newFactory(double.class, Double.class, 
/*  345 */           doubleAdapter(serializeSpecialFloatingPointValues)));
/*  346 */     factories.add(
/*  347 */         TypeAdapters.newFactory(float.class, Float.class, 
/*  348 */           floatAdapter(serializeSpecialFloatingPointValues)));
/*  349 */     factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
/*  350 */     factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
/*  351 */     factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
/*  352 */     factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
/*  353 */     factories.add(
/*  354 */         TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
/*  355 */     factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
/*  356 */     factories.add(TypeAdapters.CHARACTER_FACTORY);
/*  357 */     factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
/*  358 */     factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
/*  359 */     factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
/*  360 */     factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
/*      */ 
/*      */     
/*  363 */     factories.add(
/*  364 */         TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
/*  365 */     factories.add(TypeAdapters.URL_FACTORY);
/*  366 */     factories.add(TypeAdapters.URI_FACTORY);
/*  367 */     factories.add(TypeAdapters.UUID_FACTORY);
/*  368 */     factories.add(TypeAdapters.CURRENCY_FACTORY);
/*  369 */     factories.add(TypeAdapters.LOCALE_FACTORY);
/*  370 */     factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
/*  371 */     factories.add(TypeAdapters.BIT_SET_FACTORY);
/*  372 */     factories.add(DefaultDateTypeAdapter.DEFAULT_STYLE_FACTORY);
/*  373 */     factories.add(TypeAdapters.CALENDAR_FACTORY);
/*      */     
/*  375 */     if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
/*  376 */       factories.add(SqlTypesSupport.TIME_FACTORY);
/*  377 */       factories.add(SqlTypesSupport.DATE_FACTORY);
/*  378 */       factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
/*      */     } 
/*      */     
/*  381 */     factories.add(ArrayTypeAdapter.FACTORY);
/*  382 */     factories.add(TypeAdapters.CLASS_FACTORY);
/*      */ 
/*      */     
/*  385 */     factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
/*  386 */     factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
/*  387 */     this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
/*  388 */     factories.add(this.jsonAdapterFactory);
/*  389 */     factories.add(TypeAdapters.ENUM_FACTORY);
/*  390 */     factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, reflectionFilters));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  398 */     this.factories = Collections.unmodifiableList(factories);
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
/*  409 */     return new GsonBuilder(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Excluder excluder() {
/*  418 */     return this.excluder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldNamingStrategy fieldNamingStrategy() {
/*  427 */     return this.fieldNamingStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean serializeNulls() {
/*  437 */     return this.serializeNulls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean htmlSafe() {
/*  447 */     return this.htmlSafe;
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
/*  451 */     if (serializeSpecialFloatingPointValues) {
/*  452 */       return TypeAdapters.DOUBLE;
/*      */     }
/*  454 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Double read(JsonReader in) throws IOException {
/*  457 */           if (in.peek() == JsonToken.NULL) {
/*  458 */             in.nextNull();
/*  459 */             return null;
/*      */           } 
/*  461 */           return Double.valueOf(in.nextDouble());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  466 */           if (value == null) {
/*  467 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  470 */           double doubleValue = value.doubleValue();
/*  471 */           Gson.checkValidFloatingPoint(doubleValue);
/*  472 */           out.value(doubleValue);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
/*  478 */     if (serializeSpecialFloatingPointValues) {
/*  479 */       return TypeAdapters.FLOAT;
/*      */     }
/*  481 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Float read(JsonReader in) throws IOException {
/*  484 */           if (in.peek() == JsonToken.NULL) {
/*  485 */             in.nextNull();
/*  486 */             return null;
/*      */           } 
/*  488 */           return Float.valueOf((float)in.nextDouble());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  493 */           if (value == null) {
/*  494 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  497 */           float floatValue = value.floatValue();
/*  498 */           Gson.checkValidFloatingPoint(floatValue);
/*      */ 
/*      */           
/*  501 */           Number floatNumber = (value instanceof Float) ? value : Float.valueOf(floatValue);
/*  502 */           out.value(floatNumber);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   static void checkValidFloatingPoint(double value) {
/*  508 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/*  509 */       throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
/*  517 */     if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
/*  518 */       return TypeAdapters.LONG;
/*      */     }
/*  520 */     return new TypeAdapter<Number>()
/*      */       {
/*      */         public Number read(JsonReader in) throws IOException {
/*  523 */           if (in.peek() == JsonToken.NULL) {
/*  524 */             in.nextNull();
/*  525 */             return null;
/*      */           } 
/*  527 */           return Long.valueOf(in.nextLong());
/*      */         }
/*      */ 
/*      */         
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  532 */           if (value == null) {
/*  533 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  536 */           out.value(value.toString());
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
/*  542 */     return (new TypeAdapter<AtomicLong>()
/*      */       {
/*      */         public void write(JsonWriter out, AtomicLong value) throws IOException {
/*  545 */           longAdapter.write(out, Long.valueOf(value.get()));
/*      */         }
/*      */ 
/*      */         
/*      */         public AtomicLong read(JsonReader in) throws IOException {
/*  550 */           Number value = longAdapter.read(in);
/*  551 */           return new AtomicLong(value.longValue());
/*      */         }
/*  553 */       }).nullSafe();
/*      */   }
/*      */ 
/*      */   
/*      */   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
/*  558 */     return (new TypeAdapter<AtomicLongArray>()
/*      */       {
/*      */         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
/*  561 */           out.beginArray();
/*  562 */           for (int i = 0, length = value.length(); i < length; i++) {
/*  563 */             longAdapter.write(out, Long.valueOf(value.get(i)));
/*      */           }
/*  565 */           out.endArray();
/*      */         }
/*      */ 
/*      */         
/*      */         public AtomicLongArray read(JsonReader in) throws IOException {
/*  570 */           List<Long> list = new ArrayList<>();
/*  571 */           in.beginArray();
/*  572 */           while (in.hasNext()) {
/*  573 */             long value = ((Number)longAdapter.read(in)).longValue();
/*  574 */             list.add(Long.valueOf(value));
/*      */           } 
/*  576 */           in.endArray();
/*  577 */           int length = list.size();
/*  578 */           AtomicLongArray array = new AtomicLongArray(length);
/*  579 */           for (int i = 0; i < length; i++) {
/*  580 */             array.set(i, ((Long)list.get(i)).longValue());
/*      */           }
/*  582 */           return array;
/*      */         }
/*  584 */       }).nullSafe();
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
/*  599 */     Objects.requireNonNull(type, "type must not be null");
/*  600 */     TypeAdapter<?> cached = this.typeTokenCache.get(type);
/*  601 */     if (cached != null) {
/*      */       
/*  603 */       TypeAdapter<T> adapter = (TypeAdapter)cached;
/*  604 */       return adapter;
/*      */     } 
/*      */     
/*  607 */     Map<TypeToken<?>, TypeAdapter<?>> threadCalls = this.threadLocalAdapterResults.get();
/*  608 */     boolean isInitialAdapterRequest = false;
/*  609 */     if (threadCalls == null) {
/*  610 */       threadCalls = new HashMap<>();
/*  611 */       this.threadLocalAdapterResults.set(threadCalls);
/*  612 */       isInitialAdapterRequest = true;
/*      */     }
/*      */     else {
/*      */       
/*  616 */       TypeAdapter<T> ongoingCall = (TypeAdapter<T>)threadCalls.get(type);
/*  617 */       if (ongoingCall != null) {
/*  618 */         return ongoingCall;
/*      */       }
/*      */     } 
/*      */     
/*  622 */     TypeAdapter<T> candidate = null;
/*      */     try {
/*  624 */       FutureTypeAdapter<T> call = new FutureTypeAdapter<>();
/*  625 */       threadCalls.put(type, call);
/*      */       
/*  627 */       for (TypeAdapterFactory factory : this.factories) {
/*  628 */         candidate = factory.create(this, type);
/*  629 */         if (candidate != null) {
/*  630 */           call.setDelegate(candidate);
/*      */           
/*  632 */           threadCalls.put(type, candidate);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  637 */       if (isInitialAdapterRequest) {
/*  638 */         this.threadLocalAdapterResults.remove();
/*      */       }
/*      */     } 
/*      */     
/*  642 */     if (candidate == null) {
/*  643 */       throw new IllegalArgumentException("GSON (2.13.1) cannot handle " + type);
/*      */     }
/*      */ 
/*      */     
/*  647 */     if (isInitialAdapterRequest)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  654 */       this.typeTokenCache.putAll(threadCalls);
/*      */     }
/*  656 */     return candidate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(Class<T> type) {
/*  666 */     return getAdapter(TypeToken.get(type));
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
/*  731 */     Objects.requireNonNull(skipPast, "skipPast must not be null");
/*  732 */     Objects.requireNonNull(type, "type must not be null");
/*      */     
/*  734 */     if (this.jsonAdapterFactory.isClassJsonAdapterFactory(type, skipPast)) {
/*  735 */       jsonAdapterAnnotationTypeAdapterFactory = this.jsonAdapterFactory;
/*      */     }
/*      */     
/*  738 */     boolean skipPastFound = false;
/*  739 */     for (TypeAdapterFactory factory : this.factories) {
/*  740 */       if (!skipPastFound) {
/*  741 */         if (factory == jsonAdapterAnnotationTypeAdapterFactory) {
/*  742 */           skipPastFound = true;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  747 */       TypeAdapter<T> candidate = factory.create(this, type);
/*  748 */       if (candidate != null) {
/*  749 */         return candidate;
/*      */       }
/*      */     } 
/*      */     
/*  753 */     if (skipPastFound) {
/*  754 */       throw new IllegalArgumentException("GSON cannot serialize or deserialize " + type);
/*      */     }
/*      */     
/*  757 */     return getAdapter(type);
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
/*  776 */     if (src == null) {
/*  777 */       return JsonNull.INSTANCE;
/*      */     }
/*  779 */     return toJsonTree(src, src.getClass());
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
/*  801 */     JsonTreeWriter writer = new JsonTreeWriter();
/*  802 */     toJson(src, typeOfSrc, (JsonWriter)writer);
/*  803 */     return writer.get();
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
/*  822 */     if (src == null) {
/*  823 */       return toJson(JsonNull.INSTANCE);
/*      */     }
/*  825 */     return toJson(src, src.getClass());
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
/*  847 */     StringWriter writer = new StringWriter();
/*  848 */     toJson(src, typeOfSrc, writer);
/*  849 */     return writer.toString();
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
/*  869 */     if (src != null) {
/*  870 */       toJson(src, src.getClass(), writer);
/*      */     } else {
/*  872 */       toJson(JsonNull.INSTANCE, writer);
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
/*  898 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  899 */       toJson(src, typeOfSrc, jsonWriter);
/*  900 */     } catch (IOException e) {
/*  901 */       throw new JsonIOException(e);
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
/*  928 */     TypeAdapter<Object> adapter = getAdapter(TypeToken.get(typeOfSrc));
/*      */     
/*  930 */     Strictness oldStrictness = writer.getStrictness();
/*  931 */     if (this.strictness != null) {
/*  932 */       writer.setStrictness(this.strictness);
/*  933 */     } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/*  935 */       writer.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*  938 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  939 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*      */     
/*  941 */     writer.setHtmlSafe(this.htmlSafe);
/*  942 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  944 */       adapter.write(writer, src);
/*  945 */     } catch (IOException e) {
/*  946 */       throw new JsonIOException(e);
/*  947 */     } catch (AssertionError e) {
/*  948 */       throw new AssertionError("AssertionError (GSON 2.13.1): " + e
/*  949 */           .getMessage(), e);
/*      */     } finally {
/*  951 */       writer.setStrictness(oldStrictness);
/*  952 */       writer.setHtmlSafe(oldHtmlSafe);
/*  953 */       writer.setSerializeNulls(oldSerializeNulls);
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
/*  965 */     StringWriter writer = new StringWriter();
/*  966 */     toJson(jsonElement, writer);
/*  967 */     return writer.toString();
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
/*  980 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  981 */       toJson(jsonElement, jsonWriter);
/*  982 */     } catch (IOException e) {
/*  983 */       throw new JsonIOException(e);
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
/* 1008 */     Strictness oldStrictness = writer.getStrictness();
/* 1009 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/* 1010 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*      */     
/* 1012 */     writer.setHtmlSafe(this.htmlSafe);
/* 1013 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     
/* 1015 */     if (this.strictness != null) {
/* 1016 */       writer.setStrictness(this.strictness);
/* 1017 */     } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/* 1019 */       writer.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*      */     try {
/* 1023 */       Streams.write(jsonElement, writer);
/* 1024 */     } catch (IOException e) {
/* 1025 */       throw new JsonIOException(e);
/* 1026 */     } catch (AssertionError e) {
/* 1027 */       throw new AssertionError("AssertionError (GSON 2.13.1): " + e
/* 1028 */           .getMessage(), e);
/*      */     } finally {
/* 1030 */       writer.setStrictness(oldStrictness);
/* 1031 */       writer.setHtmlSafe(oldHtmlSafe);
/* 1032 */       writer.setSerializeNulls(oldSerializeNulls);
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
/* 1054 */     if (this.generateNonExecutableJson) {
/* 1055 */       writer.write(")]}'\n");
/*      */     }
/* 1057 */     JsonWriter jsonWriter = new JsonWriter(writer);
/* 1058 */     jsonWriter.setFormattingStyle(this.formattingStyle);
/* 1059 */     jsonWriter.setHtmlSafe(this.htmlSafe);
/* 1060 */     jsonWriter.setStrictness((this.strictness == null) ? Strictness.LEGACY_STRICT : this.strictness);
/* 1061 */     jsonWriter.setSerializeNulls(this.serializeNulls);
/* 1062 */     return jsonWriter;
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
/* 1078 */     JsonReader jsonReader = new JsonReader(reader);
/* 1079 */     jsonReader.setStrictness((this.strictness == null) ? Strictness.LEGACY_STRICT : this.strictness);
/* 1080 */     return jsonReader;
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
/* 1107 */     return fromJson(json, TypeToken.get(classOfT));
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
/* 1136 */     return fromJson(json, TypeToken.get(typeOfT));
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
/* 1166 */     if (json == null) {
/* 1167 */       return null;
/*      */     }
/* 1169 */     StringReader reader = new StringReader(json);
/* 1170 */     return fromJson(reader, typeOfT);
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
/* 1198 */     return fromJson(json, TypeToken.get(classOfT));
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
/* 1228 */     return fromJson(json, TypeToken.get(typeOfT));
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
/* 1259 */     JsonReader jsonReader = newJsonReader(json);
/* 1260 */     T object = fromJson(jsonReader, typeOfT);
/* 1261 */     assertFullConsumption(object, jsonReader);
/* 1262 */     return object;
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
/* 1304 */     return fromJson(reader, TypeToken.get(typeOfT));
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
/* 1345 */     boolean isEmpty = true;
/* 1346 */     Strictness oldStrictness = reader.getStrictness();
/*      */     
/* 1348 */     if (this.strictness != null) {
/* 1349 */       reader.setStrictness(this.strictness);
/* 1350 */     } else if (reader.getStrictness() == Strictness.LEGACY_STRICT) {
/*      */       
/* 1352 */       reader.setStrictness(Strictness.LENIENT);
/*      */     } 
/*      */     
/*      */     try {
/* 1356 */       JsonToken unused = reader.peek();
/* 1357 */       isEmpty = false;
/* 1358 */       TypeAdapter<T> typeAdapter = getAdapter(typeOfT);
/* 1359 */       T object = typeAdapter.read(reader);
/* 1360 */       Class<?> expectedTypeWrapped = Primitives.wrap(typeOfT.getRawType());
/* 1361 */       if (object != null && !expectedTypeWrapped.isInstance(object)) {
/* 1362 */         throw new ClassCastException("Type adapter '" + typeAdapter + "' returned wrong type; requested " + typeOfT
/*      */ 
/*      */ 
/*      */             
/* 1366 */             .getRawType() + " but got instance of " + object
/*      */             
/* 1368 */             .getClass() + "\nVerify that the adapter was registered for the correct type.");
/*      */       }
/*      */       
/* 1371 */       return object;
/* 1372 */     } catch (EOFException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       if (isEmpty) {
/* 1378 */         return null;
/*      */       }
/* 1380 */       throw new JsonSyntaxException(e);
/* 1381 */     } catch (IllegalStateException e) {
/* 1382 */       throw new JsonSyntaxException(e);
/* 1383 */     } catch (IOException e) {
/*      */       
/* 1385 */       throw new JsonSyntaxException(e);
/* 1386 */     } catch (AssertionError e) {
/* 1387 */       throw new AssertionError("AssertionError (GSON 2.13.1): " + e
/* 1388 */           .getMessage(), e);
/*      */     } finally {
/* 1390 */       reader.setStrictness(oldStrictness);
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
/* 1416 */     return fromJson(json, TypeToken.get(classOfT));
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
/* 1443 */     return fromJson(json, TypeToken.get(typeOfT));
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
/* 1469 */     if (json == null) {
/* 1470 */       return null;
/*      */     }
/* 1472 */     return fromJson((JsonReader)new JsonTreeReader(json), typeOfT);
/*      */   }
/*      */   
/*      */   private static void assertFullConsumption(Object obj, JsonReader reader) {
/*      */     try {
/* 1477 */       if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
/* 1478 */         throw new JsonSyntaxException("JSON document was not fully consumed.");
/*      */       }
/* 1480 */     } catch (MalformedJsonException e) {
/* 1481 */       throw new JsonSyntaxException(e);
/* 1482 */     } catch (IOException e) {
/* 1483 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class FutureTypeAdapter<T>
/*      */     extends SerializationDelegatingTypeAdapter<T>
/*      */   {
/* 1496 */     private TypeAdapter<T> delegate = null;
/*      */     
/*      */     public void setDelegate(TypeAdapter<T> typeAdapter) {
/* 1499 */       if (this.delegate != null) {
/* 1500 */         throw new AssertionError("Delegate is already set");
/*      */       }
/* 1502 */       this.delegate = typeAdapter;
/*      */     }
/*      */     
/*      */     private TypeAdapter<T> delegate() {
/* 1506 */       TypeAdapter<T> delegate = this.delegate;
/* 1507 */       if (delegate == null)
/*      */       {
/*      */ 
/*      */         
/* 1511 */         throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
/*      */       }
/*      */ 
/*      */       
/* 1515 */       return delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeAdapter<T> getSerializationDelegate() {
/* 1520 */       return delegate();
/*      */     }
/*      */ 
/*      */     
/*      */     public T read(JsonReader in) throws IOException {
/* 1525 */       return delegate().read(in);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(JsonWriter out, T value) throws IOException {
/* 1530 */       delegate().write(out, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1536 */     return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\Gson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */