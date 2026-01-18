/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.nimbusds.jose.shaded.gson.internal.Excluder;
/*     */ import com.nimbusds.jose.shaded.gson.internal.GsonPreconditions;
/*     */ import com.nimbusds.jose.shaded.gson.internal.bind.DefaultDateTypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.internal.bind.TreeTypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.internal.bind.TypeAdapters;
/*     */ import com.nimbusds.jose.shaded.gson.internal.sql.SqlTypesSupport;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
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
/*  94 */   private Excluder excluder = Excluder.DEFAULT;
/*  95 */   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
/*  96 */   private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
/*  97 */   private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();
/*  98 */   private final List<TypeAdapterFactory> factories = new ArrayList<>();
/*     */ 
/*     */   
/* 101 */   private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>();
/*     */   
/*     */   private boolean serializeNulls = false;
/* 104 */   private String datePattern = Gson.DEFAULT_DATE_PATTERN;
/* 105 */   private int dateStyle = 2;
/* 106 */   private int timeStyle = 2;
/*     */   private boolean complexMapKeySerialization = false;
/*     */   private boolean serializeSpecialFloatingPointValues = false;
/*     */   private boolean escapeHtmlChars = true;
/* 110 */   private FormattingStyle formattingStyle = Gson.DEFAULT_FORMATTING_STYLE;
/*     */   private boolean generateNonExecutableJson = false;
/* 112 */   private Strictness strictness = Gson.DEFAULT_STRICTNESS;
/*     */   private boolean useJdkUnsafe = true;
/* 114 */   private ToNumberStrategy objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
/* 115 */   private ToNumberStrategy numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
/* 116 */   private final ArrayDeque<ReflectionAccessFilter> reflectionFilters = new ArrayDeque<>();
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
/* 132 */     this.excluder = gson.excluder;
/* 133 */     this.fieldNamingPolicy = gson.fieldNamingStrategy;
/* 134 */     this.instanceCreators.putAll(gson.instanceCreators);
/* 135 */     this.serializeNulls = gson.serializeNulls;
/* 136 */     this.complexMapKeySerialization = gson.complexMapKeySerialization;
/* 137 */     this.generateNonExecutableJson = gson.generateNonExecutableJson;
/* 138 */     this.escapeHtmlChars = gson.htmlSafe;
/* 139 */     this.formattingStyle = gson.formattingStyle;
/* 140 */     this.strictness = gson.strictness;
/* 141 */     this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
/* 142 */     this.longSerializationPolicy = gson.longSerializationPolicy;
/* 143 */     this.datePattern = gson.datePattern;
/* 144 */     this.dateStyle = gson.dateStyle;
/* 145 */     this.timeStyle = gson.timeStyle;
/* 146 */     this.factories.addAll(gson.builderFactories);
/* 147 */     this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
/* 148 */     this.useJdkUnsafe = gson.useJdkUnsafe;
/* 149 */     this.objectToNumberStrategy = gson.objectToNumberStrategy;
/* 150 */     this.numberToNumberStrategy = gson.numberToNumberStrategy;
/* 151 */     this.reflectionFilters.addAll(gson.reflectionFilters);
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
/* 171 */     if (Double.isNaN(version) || version < 0.0D) {
/* 172 */       throw new IllegalArgumentException("Invalid version: " + version);
/*     */     }
/* 174 */     this.excluder = this.excluder.withVersion(version);
/* 175 */     return this;
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
/* 194 */     Objects.requireNonNull(modifiers);
/* 195 */     this.excluder = this.excluder.withModifiers(modifiers);
/* 196 */     return this;
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
/* 209 */     this.generateNonExecutableJson = true;
/* 210 */     return this;
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
/* 225 */     this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
/* 226 */     return this;
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
/* 238 */     this.serializeNulls = true;
/* 239 */     return this;
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
/* 326 */     this.complexMapKeySerialization = true;
/* 327 */     return this;
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
/* 353 */     this.excluder = this.excluder.disableInnerClassSerialization();
/* 354 */     return this;
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
/* 367 */     this.longSerializationPolicy = Objects.<LongSerializationPolicy>requireNonNull(serializationPolicy);
/* 368 */     return this;
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
/* 379 */     return setFieldNamingStrategy(namingConvention);
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
/* 396 */     this.fieldNamingPolicy = Objects.<FieldNamingStrategy>requireNonNull(fieldNamingStrategy);
/* 397 */     return this;
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
/* 410 */     this.objectToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(objectToNumberStrategy);
/* 411 */     return this;
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
/* 424 */     this.numberToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(numberToNumberStrategy);
/* 425 */     return this;
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
/* 455 */     Objects.requireNonNull(strategies);
/* 456 */     for (ExclusionStrategy strategy : strategies) {
/* 457 */       this.excluder = this.excluder.withExclusionStrategy(strategy, true, true);
/*     */     }
/* 459 */     return this;
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
/* 478 */     Objects.requireNonNull(strategy);
/* 479 */     this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
/* 480 */     return this;
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
/* 499 */     Objects.requireNonNull(strategy);
/* 500 */     this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
/* 501 */     return this;
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
/* 515 */     return setFormattingStyle(FormattingStyle.PRETTY);
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
/* 529 */     this.formattingStyle = Objects.<FormattingStyle>requireNonNull(formattingStyle);
/* 530 */     return this;
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
/*     */   @InlineMe(replacement = "this.setStrictness(Strictness.LENIENT)", imports = {"com.nimbusds.jose.shaded.gson.Strictness"})
/*     */   @CanIgnoreReturnValue
/*     */   public GsonBuilder setLenient() {
/* 549 */     return setStrictness(Strictness.LENIENT);
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
/* 567 */     this.strictness = Objects.<Strictness>requireNonNull(strictness);
/* 568 */     return this;
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
/* 580 */     this.escapeHtmlChars = false;
/* 581 */     return this;
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
/* 605 */     if (pattern != null) {
/*     */       try {
/* 607 */         new SimpleDateFormat(pattern);
/* 608 */       } catch (IllegalArgumentException e) {
/*     */         
/* 610 */         throw new IllegalArgumentException("The date pattern '" + pattern + "' is not valid", e);
/*     */       } 
/*     */     }
/* 613 */     this.datePattern = pattern;
/* 614 */     return this;
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
/* 640 */     this.dateStyle = checkDateFormatStyle(dateStyle);
/* 641 */     this.datePattern = null;
/* 642 */     return this;
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
/* 663 */     this.dateStyle = checkDateFormatStyle(dateStyle);
/* 664 */     this.timeStyle = checkDateFormatStyle(timeStyle);
/* 665 */     this.datePattern = null;
/* 666 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int checkDateFormatStyle(int style) {
/* 671 */     if (style < 0 || style > 3) {
/* 672 */       throw new IllegalArgumentException("Invalid style: " + style);
/*     */     }
/* 674 */     return style;
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
/* 706 */     Objects.requireNonNull(type);
/* 707 */     GsonPreconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof InstanceCreator || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 713 */     if (hasNonOverridableAdapter(type)) {
/* 714 */       throw new IllegalArgumentException("Cannot override built-in adapter for " + type);
/*     */     }
/*     */     
/* 717 */     if (typeAdapter instanceof InstanceCreator) {
/* 718 */       this.instanceCreators.put(type, (InstanceCreator)typeAdapter);
/*     */     }
/* 720 */     if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
/* 721 */       TypeToken<?> typeToken = TypeToken.get(type);
/* 722 */       this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
/*     */     } 
/* 724 */     if (typeAdapter instanceof TypeAdapter) {
/*     */ 
/*     */       
/* 727 */       TypeAdapterFactory factory = TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)typeAdapter);
/* 728 */       this.factories.add(factory);
/*     */     } 
/* 730 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasNonOverridableAdapter(Type type) {
/* 735 */     return (type == Object.class);
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
/* 757 */     Objects.requireNonNull(factory);
/* 758 */     this.factories.add(factory);
/* 759 */     return this;
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
/* 780 */     Objects.requireNonNull(baseType);
/* 781 */     GsonPreconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 786 */     if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer) {
/* 787 */       this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter));
/*     */     }
/* 789 */     if (typeAdapter instanceof TypeAdapter) {
/*     */ 
/*     */       
/* 792 */       TypeAdapterFactory factory = TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter)typeAdapter);
/* 793 */       this.factories.add(factory);
/*     */     } 
/* 795 */     return this;
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
/* 819 */     this.serializeSpecialFloatingPointValues = true;
/* 820 */     return this;
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
/* 840 */     this.useJdkUnsafe = false;
/* 841 */     return this;
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
/* 866 */     Objects.requireNonNull(filter);
/* 867 */     this.reflectionFilters.addFirst(filter);
/* 868 */     return this;
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
/* 879 */     List<TypeAdapterFactory> factories = new ArrayList<>(this.factories.size() + this.hierarchyFactories.size() + 3);
/* 880 */     factories.addAll(this.factories);
/* 881 */     Collections.reverse(factories);
/*     */     
/* 883 */     List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>(this.hierarchyFactories);
/* 884 */     Collections.reverse(hierarchyFactories);
/* 885 */     factories.addAll(hierarchyFactories);
/*     */     
/* 887 */     addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
/*     */     
/* 889 */     return new Gson(this.excluder, this.fieldNamingPolicy, new HashMap<>(this.instanceCreators), this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.formattingStyle, this.strictness, this.serializeSpecialFloatingPointValues, this.useJdkUnsafe, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, new ArrayList<>(this.factories), new ArrayList<>(this.hierarchyFactories), factories, this.objectToNumberStrategy, this.numberToNumberStrategy, new ArrayList<>(this.reflectionFilters));
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
/* 916 */     boolean sqlTypesSupported = SqlTypesSupport.SUPPORTS_SQL_TYPES;
/* 917 */     TypeAdapterFactory sqlTimestampAdapterFactory = null;
/* 918 */     TypeAdapterFactory sqlDateAdapterFactory = null;
/*     */     
/* 920 */     if (datePattern != null && !datePattern.trim().isEmpty()) {
/* 921 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(datePattern);
/*     */       
/* 923 */       if (sqlTypesSupported) {
/*     */         
/* 925 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(datePattern);
/* 926 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(datePattern);
/*     */       } 
/* 928 */     } else if (dateStyle != 2 || timeStyle != 2) {
/*     */       
/* 930 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(dateStyle, timeStyle);
/*     */       
/* 932 */       if (sqlTypesSupported) {
/*     */         
/* 934 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */         
/* 936 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */       } 
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 942 */     factories.add(dateAdapterFactory);
/* 943 */     if (sqlTypesSupported) {
/* 944 */       factories.add(sqlTimestampAdapterFactory);
/* 945 */       factories.add(sqlDateAdapterFactory);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\GsonBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */