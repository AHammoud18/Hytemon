/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.google.gson.internal.Excluder;
/*     */ import com.google.gson.internal.bind.DefaultDateTypeAdapter;
/*     */ import com.google.gson.internal.bind.TreeTypeAdapter;
/*     */ import com.google.gson.internal.bind.TypeAdapters;
/*     */ import com.google.gson.internal.sql.SqlTypesSupport;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public final class GsonBuilder
/*     */ {
/*  93 */   private Excluder excluder = Excluder.DEFAULT;
/*  94 */   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
/*  95 */   private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
/*  96 */   private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();
/*  97 */   private final List<TypeAdapterFactory> factories = new ArrayList<>();
/*     */ 
/*     */   
/* 100 */   private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>();
/*     */   
/*     */   private boolean serializeNulls = false;
/* 103 */   private String datePattern = Gson.DEFAULT_DATE_PATTERN;
/* 104 */   private int dateStyle = 2;
/* 105 */   private int timeStyle = 2;
/*     */   private boolean complexMapKeySerialization = false;
/*     */   private boolean serializeSpecialFloatingPointValues = false;
/*     */   private boolean escapeHtmlChars = true;
/* 109 */   private FormattingStyle formattingStyle = Gson.DEFAULT_FORMATTING_STYLE;
/*     */   private boolean generateNonExecutableJson = false;
/* 111 */   private Strictness strictness = Gson.DEFAULT_STRICTNESS;
/*     */   private boolean useJdkUnsafe = true;
/* 113 */   private ToNumberStrategy objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
/* 114 */   private ToNumberStrategy numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
/* 115 */   private final ArrayDeque<ReflectionAccessFilter> reflectionFilters = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GsonBuilder(Gson gson) {
/* 131 */     this.excluder = gson.excluder;
/* 132 */     this.fieldNamingPolicy = gson.fieldNamingStrategy;
/* 133 */     this.instanceCreators.putAll(gson.instanceCreators);
/* 134 */     this.serializeNulls = gson.serializeNulls;
/* 135 */     this.complexMapKeySerialization = gson.complexMapKeySerialization;
/* 136 */     this.generateNonExecutableJson = gson.generateNonExecutableJson;
/* 137 */     this.escapeHtmlChars = gson.htmlSafe;
/* 138 */     this.formattingStyle = gson.formattingStyle;
/* 139 */     this.strictness = gson.strictness;
/* 140 */     this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
/* 141 */     this.longSerializationPolicy = gson.longSerializationPolicy;
/* 142 */     this.datePattern = gson.datePattern;
/* 143 */     this.dateStyle = gson.dateStyle;
/* 144 */     this.timeStyle = gson.timeStyle;
/* 145 */     this.factories.addAll(gson.builderFactories);
/* 146 */     this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
/* 147 */     this.useJdkUnsafe = gson.useJdkUnsafe;
/* 148 */     this.objectToNumberStrategy = gson.objectToNumberStrategy;
/* 149 */     this.numberToNumberStrategy = gson.numberToNumberStrategy;
/* 150 */     this.reflectionFilters.addAll(gson.reflectionFilters);
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setVersion(double version) {
/* 170 */     if (Double.isNaN(version) || version < 0.0D) {
/* 171 */       throw new IllegalArgumentException("Invalid version: " + version);
/*     */     }
/* 173 */     this.excluder = this.excluder.withVersion(version);
/* 174 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
/* 193 */     Objects.requireNonNull(modifiers);
/* 194 */     this.excluder = this.excluder.withModifiers(modifiers);
/* 195 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder generateNonExecutableJson() {
/* 208 */     this.generateNonExecutableJson = true;
/* 209 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
/* 224 */     this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder serializeNulls() {
/* 237 */     this.serializeNulls = true;
/* 238 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder enableComplexMapKeySerialization() {
/* 325 */     this.complexMapKeySerialization = true;
/* 326 */     return this;
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
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder disableInnerClassSerialization() {
/* 352 */     this.excluder = this.excluder.disableInnerClassSerialization();
/* 353 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
/* 366 */     this.longSerializationPolicy = Objects.<LongSerializationPolicy>requireNonNull(serializationPolicy);
/* 367 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
/* 378 */     return setFieldNamingStrategy(namingConvention);
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
/* 395 */     this.fieldNamingPolicy = Objects.<FieldNamingStrategy>requireNonNull(fieldNamingStrategy);
/* 396 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setObjectToNumberStrategy(ToNumberStrategy objectToNumberStrategy) {
/* 409 */     this.objectToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(objectToNumberStrategy);
/* 410 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setNumberToNumberStrategy(ToNumberStrategy numberToNumberStrategy) {
/* 423 */     this.numberToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(numberToNumberStrategy);
/* 424 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
/* 454 */     Objects.requireNonNull(strategies);
/* 455 */     for (ExclusionStrategy strategy : strategies) {
/* 456 */       this.excluder = this.excluder.withExclusionStrategy(strategy, true, true);
/*     */     }
/* 458 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
/* 477 */     Objects.requireNonNull(strategy);
/* 478 */     this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
/* 479 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
/* 498 */     Objects.requireNonNull(strategy);
/* 499 */     this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
/* 500 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setPrettyPrinting() {
/* 514 */     return setFormattingStyle(FormattingStyle.PRETTY);
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setFormattingStyle(FormattingStyle formattingStyle) {
/* 528 */     this.formattingStyle = Objects.<FormattingStyle>requireNonNull(formattingStyle);
/* 529 */     return this;
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
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "this.setStrictness(Strictness.LENIENT)", imports = {"com.google.gson.Strictness"})
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setLenient() {
/* 548 */     return setStrictness(Strictness.LENIENT);
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setStrictness(Strictness strictness) {
/* 566 */     this.strictness = Objects.<Strictness>requireNonNull(strictness);
/* 567 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder disableHtmlEscaping() {
/* 579 */     this.escapeHtmlChars = false;
/* 580 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setDateFormat(String pattern) {
/* 604 */     if (pattern != null) {
/*     */       try {
/* 606 */         new SimpleDateFormat(pattern);
/* 607 */       } catch (IllegalArgumentException e) {
/*     */         
/* 609 */         throw new IllegalArgumentException("The date pattern '" + pattern + "' is not valid", e);
/*     */       } 
/*     */     }
/* 612 */     this.datePattern = pattern;
/* 613 */     return this;
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
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setDateFormat(int dateStyle) {
/* 639 */     this.dateStyle = checkDateFormatStyle(dateStyle);
/* 640 */     this.datePattern = null;
/* 641 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
/* 662 */     this.dateStyle = checkDateFormatStyle(dateStyle);
/* 663 */     this.timeStyle = checkDateFormatStyle(timeStyle);
/* 664 */     this.datePattern = null;
/* 665 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int checkDateFormatStyle(int style) {
/* 670 */     if (style < 0 || style > 3) {
/* 671 */       throw new IllegalArgumentException("Invalid style: " + style);
/*     */     }
/* 673 */     return style;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
/* 705 */     Objects.requireNonNull(type);
/* 706 */     Objects.requireNonNull(typeAdapter);
/* 707 */     if (!(typeAdapter instanceof JsonSerializer) && !(typeAdapter instanceof JsonDeserializer) && !(typeAdapter instanceof InstanceCreator) && !(typeAdapter instanceof TypeAdapter))
/*     */     {
/*     */ 
/*     */       
/* 711 */       throw new IllegalArgumentException("Class " + typeAdapter
/*     */           
/* 713 */           .getClass().getName() + " does not implement any supported type adapter class or interface");
/*     */     }
/*     */ 
/*     */     
/* 717 */     if (hasNonOverridableAdapter(type)) {
/* 718 */       throw new IllegalArgumentException("Cannot override built-in adapter for " + type);
/*     */     }
/*     */     
/* 721 */     if (typeAdapter instanceof InstanceCreator) {
/* 722 */       this.instanceCreators.put(type, (InstanceCreator)typeAdapter);
/*     */     }
/* 724 */     if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
/* 725 */       TypeToken<?> typeToken = TypeToken.get(type);
/* 726 */       this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
/*     */     } 
/* 728 */     if (typeAdapter instanceof TypeAdapter) {
/*     */ 
/*     */       
/* 731 */       TypeAdapterFactory factory = TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)typeAdapter);
/* 732 */       this.factories.add(factory);
/*     */     } 
/* 734 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasNonOverridableAdapter(Type type) {
/* 739 */     return (type == Object.class);
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
/* 761 */     Objects.requireNonNull(factory);
/* 762 */     this.factories.add(factory);
/* 763 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
/* 784 */     Objects.requireNonNull(baseType);
/* 785 */     Objects.requireNonNull(typeAdapter);
/* 786 */     if (!(typeAdapter instanceof JsonSerializer) && !(typeAdapter instanceof JsonDeserializer) && !(typeAdapter instanceof TypeAdapter))
/*     */     {
/*     */       
/* 789 */       throw new IllegalArgumentException("Class " + typeAdapter
/*     */           
/* 791 */           .getClass().getName() + " does not implement any supported type adapter class or interface");
/*     */     }
/*     */ 
/*     */     
/* 795 */     if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer) {
/* 796 */       this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter));
/*     */     }
/* 798 */     if (typeAdapter instanceof TypeAdapter) {
/*     */ 
/*     */       
/* 801 */       TypeAdapterFactory factory = TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter)typeAdapter);
/* 802 */       this.factories.add(factory);
/*     */     } 
/* 804 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder serializeSpecialFloatingPointValues() {
/* 828 */     this.serializeSpecialFloatingPointValues = true;
/* 829 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder disableJdkUnsafe() {
/* 849 */     this.useJdkUnsafe = false;
/* 850 */     return this;
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
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder addReflectionAccessFilter(ReflectionAccessFilter filter) {
/* 875 */     Objects.requireNonNull(filter);
/* 876 */     this.reflectionFilters.addFirst(filter);
/* 877 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gson create() {
/* 888 */     List<TypeAdapterFactory> factories = new ArrayList<>(this.factories.size() + this.hierarchyFactories.size() + 3);
/* 889 */     factories.addAll(this.factories);
/* 890 */     Collections.reverse(factories);
/*     */     
/* 892 */     List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>(this.hierarchyFactories);
/* 893 */     Collections.reverse(hierarchyFactories);
/* 894 */     factories.addAll(hierarchyFactories);
/*     */     
/* 896 */     addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
/*     */     
/* 898 */     return new Gson(this.excluder, this.fieldNamingPolicy, new HashMap<>(this.instanceCreators), this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.formattingStyle, this.strictness, this.serializeSpecialFloatingPointValues, this.useJdkUnsafe, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, new ArrayList<>(this.factories), new ArrayList<>(this.hierarchyFactories), factories, this.objectToNumberStrategy, this.numberToNumberStrategy, new ArrayList<>(this.reflectionFilters));
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> factories) {
/*     */     TypeAdapterFactory dateAdapterFactory;
/* 925 */     boolean sqlTypesSupported = SqlTypesSupport.SUPPORTS_SQL_TYPES;
/* 926 */     TypeAdapterFactory sqlTimestampAdapterFactory = null;
/* 927 */     TypeAdapterFactory sqlDateAdapterFactory = null;
/*     */     
/* 929 */     if (datePattern != null && !datePattern.trim().isEmpty()) {
/* 930 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(datePattern);
/*     */       
/* 932 */       if (sqlTypesSupported) {
/*     */         
/* 934 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(datePattern);
/* 935 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(datePattern);
/*     */       } 
/* 937 */     } else if (dateStyle != 2 || timeStyle != 2) {
/*     */       
/* 939 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(dateStyle, timeStyle);
/*     */       
/* 941 */       if (sqlTypesSupported) {
/*     */         
/* 943 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */         
/* 945 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */       } 
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 951 */     factories.add(dateAdapterFactory);
/* 952 */     if (sqlTypesSupported) {
/* 953 */       factories.add(sqlTimestampAdapterFactory);
/* 954 */       factories.add(sqlDateAdapterFactory);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\GsonBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */