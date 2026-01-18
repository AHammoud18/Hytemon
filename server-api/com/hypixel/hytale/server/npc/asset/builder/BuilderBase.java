/*      */ package com.hypixel.hytale.server.npc.asset.builder;
/*      */ import com.google.gson.JsonArray;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.hypixel.hytale.codec.schema.config.ArraySchema;
/*      */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*      */ import com.hypixel.hytale.codec.schema.config.Schema;
/*      */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionDynamic;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.AssetArrayHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.AssetHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.EnumHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.IntHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.NumberArrayHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.holder.ValueHolder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ParameterType;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.AnyBooleanValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.ArrayValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.ArraysOneSetValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.AtMostOneBooleanValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleArrayValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.IntArrayValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.IntValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.OneOrNonePresentValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.OnePresentValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.RelationalOperator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.StateStringValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringArrayValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringsOneSetValidator;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.validators.Validator;
/*      */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*      */ import com.hypixel.hytale.server.npc.valuestore.ValueStoreValidator;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public abstract class BuilderBase<T> implements Builder<T> {
/*   49 */   private static final Pattern PATTERN = Pattern.compile("\\s*,\\s*");
/*      */   
/*      */   protected String fileName;
/*      */   @Nullable
/*   53 */   protected Set<String> queriedKeys = new HashSet<>();
/*      */   
/*      */   protected boolean useDefaultsOnly;
/*      */   
/*      */   protected String label;
/*      */   
/*      */   protected String typeName;
/*      */   protected FeatureEvaluatorHelper evaluatorHelper;
/*      */   protected InternalReferenceResolver internalReferenceResolver;
/*      */   protected StateMappingHelper stateHelper;
/*      */   protected InstructionContextHelper instructionContextHelper;
/*      */   protected ExtraInfo extraInfo;
/*      */   protected List<Evaluator<?>> evaluators;
/*      */   protected BuilderValidationHelper validationHelper;
/*      */   @Nullable
/*      */   protected BuilderDescriptor builderDescriptor;
/*      */   protected BuilderParameters builderParameters;
/*      */   protected BuilderManager builderManager;
/*      */   protected BuilderContext owner;
/*      */   @Nullable
/*      */   protected List<String> readErrors;
/*      */   private List<ValueHolder> dynamicHolders;
/*      */   private List<ValueStoreValidator.ValueUsage> valueStoreUsages;
/*      */   @Nullable
/*      */   protected ObjectSchema builderSchema;
/*      */   protected Schema builderSchemaRaw;
/*      */   @Nullable
/*      */   protected SchemaContext builderSchemaContext;
/*      */   
/*      */   public void setTypeName(String name) {
/*   83 */     this.typeName = name;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getTypeName() {
/*   88 */     return this.typeName;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLabel() {
/*   93 */     return this.label;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLabel(String label) {
/*   98 */     this.label = label;
/*      */   }
/*      */ 
/*      */   
/*      */   public FeatureEvaluatorHelper getEvaluatorHelper() {
/*  103 */     return this.evaluatorHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public StateMappingHelper getStateMappingHelper() {
/*  108 */     return this.stateHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public InstructionContextHelper getInstructionContextHelper() {
/*  113 */     return this.instructionContextHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public void validateReferencedProvidedFeatures(BuilderManager manager, ExecutionContext context) {
/*  118 */     if (this.evaluatorHelper == null)
/*  119 */       return;  this.evaluatorHelper.validateProviderReferences(manager, context);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canRequireFeature() {
/*  124 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean excludeFromRegularBuilding() {
/*  129 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void readConfig(BuilderContext owner, @Nonnull JsonElement data, BuilderManager builderManager, BuilderParameters builderParameters, BuilderValidationHelper builderValidationHelper) {
/*  134 */     preReadConfig(owner, builderManager, builderParameters, builderValidationHelper);
/*  135 */     readCommonConfig(data);
/*  136 */     readConfig(data);
/*  137 */     postReadConfig(data);
/*      */   }
/*      */   
/*      */   private void preReadConfig(BuilderContext owner, BuilderManager builderManager, BuilderParameters builderParameters, @Nullable BuilderValidationHelper builderValidationHelper) {
/*  141 */     this.owner = owner;
/*  142 */     this.useDefaultsOnly = false;
/*  143 */     this.builderParameters = builderParameters;
/*  144 */     this.builderManager = builderManager;
/*  145 */     this.queriedKeys.add("Comment");
/*  146 */     this.queriedKeys.add("$Title");
/*  147 */     this.queriedKeys.add("$Comment");
/*  148 */     this.queriedKeys.add("$Author");
/*  149 */     this.queriedKeys.add("$TODO");
/*  150 */     this.queriedKeys.add("$Position");
/*  151 */     this.queriedKeys.add("$FloatingFunctionNodes");
/*  152 */     this.queriedKeys.add("$Groups");
/*  153 */     this.queriedKeys.add("$WorkspaceID");
/*  154 */     this.queriedKeys.add("$NodeEditorMetadata");
/*  155 */     this.queriedKeys.add("$NodeId");
/*  156 */     if (builderValidationHelper != null) {
/*  157 */       this.validationHelper = builderValidationHelper;
/*  158 */       this.fileName = builderValidationHelper.getName();
/*  159 */       this.evaluatorHelper = builderValidationHelper.getFeatureEvaluatorHelper();
/*  160 */       this.internalReferenceResolver = builderValidationHelper.getInternalReferenceResolver();
/*  161 */       this.stateHelper = builderValidationHelper.getStateMappingHelper();
/*  162 */       this.instructionContextHelper = builderValidationHelper.getInstructionContextHelper();
/*  163 */       this.extraInfo = builderValidationHelper.getExtraInfo();
/*  164 */       this.evaluators = builderValidationHelper.getEvaluators();
/*  165 */       this.readErrors = builderValidationHelper.getReadErrors();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addQueryKey(String name) {
/*  170 */     if (!this.queriedKeys.add(name)) throw new IllegalArgumentException(String.valueOf(name));
/*      */   
/*      */   }
/*      */   
/*      */   public BuilderContext getOwner() {
/*  175 */     return this.owner;
/*      */   }
/*      */ 
/*      */   
/*      */   public void ignoreAttribute(String name) {
/*  180 */     this.queriedKeys.add(name);
/*      */   }
/*      */   
/*      */   private void postReadConfig(@Nonnull JsonElement data) {
/*  184 */     if (this.builderDescriptor == null && data.isJsonObject()) {
/*  185 */       this.queriedKeys.add("Type");
/*  186 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)data.getAsJsonObject().entrySet()) {
/*  187 */         String key = entry.getKey();
/*  188 */         if (!this.queriedKeys.contains(key)) {
/*  189 */           String string = data.toString();
/*  190 */           NPCPlugin.get().getLogger().at(Level.WARNING).log("Unknown JSON attribute '%s' found in %s: %s (JSON: %s)", key, 
/*  191 */               getBreadCrumbs(), this.builderParameters.getFileName(), (string.length() > 60) ? (string.substring(60) + "...") : string);
/*      */         } 
/*      */       } 
/*      */     } 
/*  195 */     this.queriedKeys = null;
/*  196 */     this.readErrors = null;
/*      */   }
/*      */   
/*      */   public Builder<T> readCommonConfig(JsonElement data) {
/*  200 */     return this;
/*      */   }
/*      */   
/*      */   public Builder<T> readConfig(JsonElement data) {
/*  204 */     return this;
/*      */   }
/*      */   
/*      */   public BuilderManager getBuilderManager() {
/*  208 */     return this.builderManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public BuilderParameters getBuilderParameters() {
/*  213 */     return this.builderParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JsonObject expectJsonObject(@Nonnull JsonElement data, String name) {
/*  221 */     if (data.isJsonObject()) return data.getAsJsonObject();
/*      */     
/*  223 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  225 */     throw new IllegalStateException("Expected object when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   protected JsonArray expectJsonArray(@Nonnull JsonElement data, String name) {
/*  229 */     if (data.isJsonArray()) return data.getAsJsonArray();
/*      */     
/*  231 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  233 */     throw new IllegalStateException("Expected array when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected String expectString(@Nonnull JsonElement data, String name) {
/*  238 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isString())
/*  239 */       return data.getAsJsonPrimitive().getAsString(); 
/*  240 */     if (data.isJsonNull()) {
/*  241 */       return null;
/*      */     }
/*      */     
/*  244 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  246 */     throw new IllegalStateException("Expected string when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   protected double expectDouble(@Nonnull JsonElement data, String name) {
/*  250 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isNumber()) {
/*      */       try {
/*  252 */         return data.getAsJsonPrimitive().getAsDouble();
/*  253 */       } catch (NumberFormatException e) {
/*  254 */         throw new IllegalStateException("Invalid number when looking for parameter \"" + name + "\", found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */       } 
/*      */     }
/*      */     
/*  258 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  260 */     throw new IllegalStateException("Expected number when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   protected int expectInteger(@Nonnull JsonElement data, String name) {
/*  264 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isNumber()) {
/*      */       try {
/*  266 */         return data.getAsJsonPrimitive().getAsInt();
/*  267 */       } catch (NumberFormatException e) {
/*  268 */         throw new IllegalStateException("Invalid integer number when looking for parameter \"" + name + "\", found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */       } 
/*      */     }
/*      */     
/*  272 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  274 */     throw new IllegalStateException("Expected integer number when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   protected boolean expectBoolean(@Nonnull JsonElement data, String name) {
/*  278 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isBoolean()) {
/*  279 */       return data.getAsJsonPrimitive().getAsBoolean();
/*      */     }
/*      */     
/*  282 */     checkForUnexpectedComputeObject(data, name);
/*      */     
/*  284 */     throw new IllegalStateException("Expected boolean value when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */   }
/*      */   
/*      */   protected int[] expectIntArray(@Nonnull JsonElement data, String name, int minSize, int maxSize) {
/*  288 */     JsonArray jsonArray = expectJsonArray(data, name, minSize, maxSize);
/*  289 */     int count = jsonArray.size();
/*      */     
/*  291 */     int[] array = new int[count];
/*  292 */     for (int i = 0; i < count; i++) {
/*  293 */       array[i] = expectInteger(jsonArray.get(i), name);
/*      */     }
/*      */     
/*  296 */     return array;
/*      */   }
/*      */   
/*      */   protected int[] expectIntArray(@Nonnull JsonElement data, String name, int size) {
/*  300 */     return expectIntArray(data, name, size, size);
/*      */   }
/*      */   
/*      */   protected double[] expectDoubleArray(@Nonnull JsonElement data, String name, int minSize, int maxSize) {
/*  304 */     JsonArray jsonArray = expectJsonArray(data, name, minSize, maxSize);
/*  305 */     int count = jsonArray.size();
/*      */     
/*  307 */     double[] array = new double[count];
/*  308 */     for (int i = 0; i < count; i++) {
/*  309 */       array[i] = expectDouble(jsonArray.get(i), name);
/*      */     }
/*      */     
/*  312 */     return array;
/*      */   }
/*      */   
/*      */   protected double[] expectDoubleArray(@Nonnull JsonElement data, String name, int size) {
/*  316 */     return expectDoubleArray(data, name, size, size);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected JsonArray expectJsonArray(@Nonnull JsonElement data, String name, int minSize, int maxSize) {
/*  321 */     JsonArray jsonArray = expectJsonArray(data, name);
/*  322 */     int count = jsonArray.size();
/*      */     
/*  324 */     if (count < minSize || count > maxSize) {
/*  325 */       if (maxSize == minSize) {
/*  326 */         throw new IllegalStateException("Expected array with " + maxSize + " elements when looking for parameter \"" + name + "\" but found " + count + " elements in context " + getBreadCrumbs());
/*      */       }
/*  328 */       throw new IllegalStateException("Expected array with " + minSize + " to " + maxSize + " elements when looking for parameter \"" + name + "\" but found " + count + " elements in context " + getBreadCrumbs());
/*      */     } 
/*      */     
/*  331 */     return jsonArray;
/*      */   }
/*      */   
/*      */   protected void checkForUnexpectedComputeObject(@Nonnull JsonElement data, String name) {
/*  335 */     if (data.isJsonObject() && data.getAsJsonObject().has("Compute")) {
/*  336 */       throw new IllegalStateException("Parameter \"" + name + "\" of " + category().getSimpleName() + " " + getTypeName() + " is not computable (yet) in context " + getBreadCrumbs());
/*      */     }
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected JsonElement getRequiredJsonElement(@Nonnull JsonElement data, String name, boolean addKey) {
/*  342 */     if (addKey) {
/*  343 */       addQueryKey(name);
/*      */     }
/*  345 */     JsonElement element = expectJsonObject(data, name).get(name);
/*  346 */     if (element == null) {
/*  347 */       throw new IllegalStateException("Parameter \"" + name + "\" is missing in context " + getBreadCrumbs());
/*      */     }
/*  349 */     return element;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected JsonElement getRequiredJsonElement(@Nonnull JsonElement data, String name) {
/*  354 */     return getRequiredJsonElement(data, name, true);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected JsonElement getRequiredJsonElementIfNotOverridden(@Nonnull JsonElement data, String name, @Nonnull ParameterType type, boolean addKey) {
/*  359 */     if (addKey) {
/*  360 */       addQueryKey(name);
/*      */     }
/*  362 */     JsonElement element = expectJsonObject(data, name).get(name);
/*      */     
/*  364 */     if (element != null) return element;
/*      */ 
/*      */     
/*  367 */     if (this.evaluatorHelper.belongsToFeatureRequiringComponent()) {
/*  368 */       this.evaluatorHelper.addComponentRequirementValidator((helper, executionContext) -> validateOverriddenParameter(name, type, helper));
/*  369 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  373 */     if (hasOverriddenParameter(name, type, this.evaluatorHelper)) return null;
/*      */ 
/*      */     
/*  376 */     if (this.evaluatorHelper.requiresProviderReferenceEvaluation()) {
/*  377 */       this.evaluatorHelper.addProviderReferenceValidator((manager, context) -> {
/*      */             resolveFeatureProviderReverences(manager);
/*      */             validateOverriddenParameter(name, type, this.evaluatorHelper);
/*      */           });
/*  381 */       return null;
/*      */     } 
/*      */     
/*  384 */     throw new IllegalStateException(String.format("Parameter %s is missing and either not provided by a sensor, or provided with the wrong parameter type (expected %s) in context %s", new Object[] { name, type.get(), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected JsonElement getRequiredJsonElementIfNotOverridden(@Nonnull JsonElement data, String name, @Nonnull ParameterType type) {
/*  389 */     return getRequiredJsonElementIfNotOverridden(data, name, type, true);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected JsonElement getOptionalJsonElement(@Nonnull JsonElement data, String name, boolean addKey) {
/*  394 */     JsonElement result = null;
/*  395 */     if (!this.useDefaultsOnly) {
/*  396 */       if (addKey) {
/*  397 */         addQueryKey(name);
/*      */       }
/*  399 */       result = expectJsonObject(data, name).get(name);
/*      */     } 
/*  401 */     return result;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected JsonElement getOptionalJsonElement(@Nonnull JsonElement data, String name) {
/*  406 */     return getOptionalJsonElement(data, name, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireString(@Nonnull JsonElement data, String name, @Nonnull Consumer<String> setter, StringValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  414 */     if (isCreatingSchema()) {
/*      */       
/*  416 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new StringSchema());
/*  417 */       s.setTitle(name);
/*  418 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  419 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  422 */     if (isCreatingDescriptor()) {
/*  423 */       this.builderDescriptor.addAttribute(name, String.class.getSimpleName(), state, shortDescription, longDescription)
/*  424 */         .required()
/*  425 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*      */     try {
/*  429 */       validateAndSet(expectString(getRequiredJsonElement(data, name), name), validator, setter, name);
/*  430 */     } catch (Exception e) {
/*  431 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getString(@Nonnull JsonElement data, String name, @Nonnull Consumer<String> setter, String defaultValue, StringValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  436 */     if (isCreatingSchema()) {
/*      */       
/*  438 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new StringSchema());
/*  439 */       s.setTitle(name);
/*  440 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  441 */       this.builderSchema.getProperties().put(name, s);
/*  442 */       return false;
/*      */     } 
/*  444 */     if (isCreatingDescriptor()) {
/*  445 */       this.builderDescriptor.addAttribute(name, String.class.getSimpleName(), state, shortDescription, longDescription)
/*  446 */         .optional(defaultValue)
/*  447 */         .validator((Validator)validator);
/*  448 */       return false;
/*      */     } 
/*      */     try {
/*  451 */       JsonElement element = getOptionalJsonElement(data, name);
/*  452 */       boolean haveValue = (element != null);
/*  453 */       if (haveValue) {
/*  454 */         defaultValue = expectString(element, name);
/*      */       }
/*  456 */       validateAndSet(defaultValue, validator, setter, name);
/*  457 */       return haveValue;
/*  458 */     } catch (Exception e) {
/*  459 */       addError(e);
/*  460 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireString(@Nonnull JsonElement data, String name, @Nonnull StringHolder stringHolder, StringValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  465 */     if (isCreatingSchema()) {
/*      */       
/*  467 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new StringSchema());
/*  468 */       s.setTitle(name);
/*  469 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  470 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  473 */     if (isCreatingDescriptor()) {
/*  474 */       stringHolder.setName(name);
/*  475 */       this.builderDescriptor.addAttribute(name, String.class.getSimpleName(), state, shortDescription, longDescription)
/*  476 */         .required()
/*  477 */         .computable()
/*  478 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*  481 */     Objects.requireNonNull(stringHolder, "stringHolder is null");
/*      */     try {
/*  483 */       stringHolder.readJSON(getRequiredJsonElement(data, name), validator, name, this.builderParameters);
/*  484 */       trackDynamicHolder((ValueHolder)stringHolder);
/*  485 */     } catch (Exception e) {
/*  486 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean requireStringIfNotOverridden(@Nonnull JsonElement data, String name, @Nonnull StringHolder stringHolder, StringValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  491 */     if (isCreatingSchema()) {
/*      */       
/*  493 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new StringSchema());
/*  494 */       s.setTitle(name);
/*  495 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  496 */       this.builderSchema.getProperties().put(name, s);
/*  497 */       return false;
/*      */     } 
/*  499 */     if (isCreatingDescriptor()) {
/*  500 */       stringHolder.setName(name);
/*  501 */       this.builderDescriptor.addAttribute(name, String.class.getSimpleName(), state, shortDescription, longDescription)
/*  502 */         .requiredIfNotOverridden()
/*  503 */         .computable()
/*  504 */         .validator((Validator)validator);
/*  505 */       return false;
/*      */     } 
/*  507 */     Objects.requireNonNull(stringHolder, "stringHolder is null");
/*      */     try {
/*  509 */       JsonElement element = getRequiredJsonElementIfNotOverridden(data, name, ParameterType.STRING);
/*  510 */       boolean valueProvided = (element != null);
/*  511 */       stringHolder.readJSON(element, null, !valueProvided ? null : validator, name, this.builderParameters);
/*  512 */       trackDynamicHolder((ValueHolder)stringHolder);
/*  513 */       return valueProvided;
/*  514 */     } catch (Exception e) {
/*  515 */       addError(e);
/*  516 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getString(@Nonnull JsonElement data, String name, @Nonnull StringHolder stringHolder, String defaultValue, StringValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  521 */     if (isCreatingSchema()) {
/*      */       
/*  523 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new StringSchema());
/*  524 */       s.setTitle(name);
/*  525 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  526 */       this.builderSchema.getProperties().put(name, s);
/*  527 */       return false;
/*      */     } 
/*  529 */     if (isCreatingDescriptor()) {
/*  530 */       stringHolder.setName(name);
/*  531 */       this.builderDescriptor.addAttribute(name, String.class.getSimpleName(), state, shortDescription, longDescription)
/*  532 */         .optional(defaultValue)
/*  533 */         .computable()
/*  534 */         .validator((Validator)validator);
/*  535 */       return false;
/*      */     } 
/*  537 */     Objects.requireNonNull(stringHolder, "stringHolder is null");
/*      */     try {
/*  539 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/*  540 */       stringHolder.readJSON(optionalJsonElement, defaultValue, validator, name, this.builderParameters);
/*  541 */       trackDynamicHolder((ValueHolder)stringHolder);
/*  542 */       return (optionalJsonElement != null);
/*  543 */     } catch (Exception e) {
/*  544 */       addError(e);
/*  545 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(String str, @Nullable StringValidator validator, @Nonnull Consumer<String> setter, String name) {
/*  550 */     if (validator != null && !validator.test(str)) {
/*  551 */       throw new IllegalStateException(validator.errorMessage(str, name) + " in " + validator.errorMessage(str, name));
/*      */     }
/*  553 */     setter.accept(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected String[] nonNull(@Nullable String[] array) {
/*  561 */     return (array == null) ? ArrayUtil.EMPTY_STRING_ARRAY : array;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public String[] expectStringArray(@Nonnull JsonElement data, @Nullable Function<String, String> mapper, String name, boolean warning) {
/*  566 */     if (mapper == null) mapper = Function.identity(); 
/*  567 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isString()) {
/*  568 */       if (warning) {
/*  569 */         NPCPlugin.get().getLogger().at(Level.WARNING).log("Use of strings for lists is deprecated for JSON attribute '%s' (use []) in %s: %s", name, getBreadCrumbs(), this.builderParameters.getFileName());
/*      */       }
/*  571 */       return (String[])StringListHelpers.splitToStringList(data.getAsJsonPrimitive().getAsString(), mapper).toArray(x$0 -> new String[x$0]);
/*      */     } 
/*  573 */     if (!data.isJsonArray()) {
/*  574 */       throw new IllegalStateException("Expected string or array when looking for parameter \"" + name + "\" but found '" + String.valueOf(data) + "' in context " + getBreadCrumbs());
/*      */     }
/*  576 */     JsonArray array = data.getAsJsonArray();
/*  577 */     String[] result = new String[array.size()];
/*  578 */     for (int i = 0; i < array.size(); i++) {
/*  579 */       String s = mapper.apply(expectString(array.get(i), name).trim());
/*  580 */       if (s != null && !s.isEmpty()) {
/*  581 */         result[i] = s;
/*      */       }
/*      */     } 
/*  584 */     return result;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public String[] expectStringArray(@Nonnull JsonElement data, Function<String, String> mapper, String name) {
/*  589 */     return expectStringArray(data, mapper, name, true);
/*      */   }
/*      */   
/*      */   public boolean getStringArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<String[]> setter, Function<String, String> mapper, String[] defaultValue, StringArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  593 */     if (isCreatingSchema()) {
/*  594 */       ArraySchema a = new ArraySchema();
/*  595 */       a.setItem((Schema)new StringSchema());
/*  596 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  597 */       s.setTitle(name);
/*  598 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  599 */       this.builderSchema.getProperties().put(name, s);
/*  600 */       return false;
/*      */     } 
/*  602 */     if (isCreatingDescriptor()) {
/*  603 */       this.builderDescriptor.addAttribute(name, "StringList", state, shortDescription, longDescription)
/*  604 */         .optional(defaultArrayToString(defaultValue))
/*  605 */         .validator((Validator)validator);
/*  606 */       return false;
/*      */     }  try {
/*      */       String[] array;
/*  609 */       JsonElement element = getOptionalJsonElement(data, name);
/*      */       
/*  611 */       boolean haveValue = (element != null);
/*  612 */       if (haveValue) {
/*  613 */         array = expectStringArray(element, mapper, name);
/*      */       } else {
/*  615 */         array = defaultValue;
/*      */       } 
/*  617 */       validateAndSet(array, validator, setter, name);
/*  618 */       return haveValue;
/*  619 */     } catch (Exception e) {
/*  620 */       addError(e);
/*  621 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireStringArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<String[]> setter, Function<String, String> mapper, StringArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  626 */     if (isCreatingSchema()) {
/*  627 */       ArraySchema a = new ArraySchema();
/*  628 */       a.setItem((Schema)new StringSchema());
/*  629 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  630 */       s.setTitle(name);
/*  631 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  632 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  635 */     if (isCreatingDescriptor()) {
/*  636 */       this.builderDescriptor.addAttribute(name, "StringList", state, shortDescription, longDescription)
/*  637 */         .required()
/*  638 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*      */     try {
/*  642 */       JsonElement element = getRequiredJsonElement(data, name);
/*  643 */       validateAndSet(expectStringArray(element, mapper, name), validator, setter, name);
/*  644 */     } catch (Exception e) {
/*  645 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireStringArray(@Nonnull JsonElement data, String name, @Nonnull StringArrayHolder holder, int minLength, int maxLength, StringArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  650 */     if (isCreatingSchema()) {
/*  651 */       ArraySchema a = new ArraySchema();
/*  652 */       a.setItem((Schema)new StringSchema());
/*  653 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/*  654 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/*  655 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  656 */       s.setTitle(name);
/*  657 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  658 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  661 */     if (isCreatingDescriptor()) {
/*  662 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/*  663 */         .domain("String")
/*  664 */         .length(minLength, maxLength)
/*  665 */         .validator((Validator)validator)
/*  666 */         .computable()
/*  667 */         .required();
/*      */       return;
/*      */     } 
/*  670 */     Objects.requireNonNull(holder, "string array holder is null");
/*      */     try {
/*  672 */       holder.readJSON(getRequiredJsonElement(data, name), minLength, maxLength, validator, name, this.builderParameters);
/*  673 */       trackDynamicHolder((ValueHolder)holder);
/*  674 */     } catch (Exception e) {
/*  675 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireTemporalArray(@Nonnull JsonElement data, String name, @Nonnull TemporalArrayHolder holder, int minLength, int maxLength, TemporalArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  680 */     if (isCreatingSchema()) {
/*  681 */       ArraySchema a = new ArraySchema();
/*  682 */       a.setItem((Schema)new StringSchema());
/*  683 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/*  684 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/*  685 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  686 */       s.setTitle(name);
/*  687 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  688 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  691 */     if (isCreatingDescriptor()) {
/*  692 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/*  693 */         .domain("TemporalAmount")
/*  694 */         .length(minLength, maxLength)
/*  695 */         .validator((Validator)validator)
/*  696 */         .computable()
/*  697 */         .required();
/*      */       return;
/*      */     } 
/*  700 */     Objects.requireNonNull(holder, "temporal array holder is null");
/*      */     try {
/*  702 */       holder.readJSON(getRequiredJsonElement(data, name), minLength, maxLength, validator, name, this.builderParameters);
/*  703 */       trackDynamicHolder((ValueHolder)holder);
/*  704 */     } catch (Exception e) {
/*  705 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireTemporalRange(@Nonnull JsonElement data, String name, @Nonnull TemporalArrayHolder holder, TemporalArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/*  710 */     requireTemporalArray(data, name, holder, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getStringArray(@Nonnull JsonElement data, String name, @Nonnull StringArrayHolder holder, String[] defaultValue, int minLength, int maxLength, StringArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  714 */     if (isCreatingSchema()) {
/*  715 */       ArraySchema a = new ArraySchema();
/*  716 */       a.setItem((Schema)new StringSchema());
/*  717 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/*  718 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/*  719 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  720 */       s.setTitle(name);
/*  721 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  722 */       this.builderSchema.getProperties().put(name, s);
/*  723 */       return false;
/*      */     } 
/*  725 */     if (isCreatingDescriptor()) {
/*  726 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/*  727 */         .domain("String")
/*  728 */         .length(minLength, maxLength)
/*  729 */         .validator((Validator)validator)
/*  730 */         .computable()
/*  731 */         .optional(defaultValue);
/*  732 */       return false;
/*      */     } 
/*  734 */     Objects.requireNonNull(holder, "string array holder is null");
/*      */     try {
/*  736 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/*  737 */       holder.readJSON(optionalJsonElement, minLength, maxLength, defaultValue, validator, name, this.builderParameters);
/*  738 */       trackDynamicHolder((ValueHolder)holder);
/*  739 */       return (optionalJsonElement != null);
/*  740 */     } catch (Exception e) {
/*  741 */       addError(e);
/*  742 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(String[] value, @Nullable StringArrayValidator validator, @Nonnull Consumer<String[]> setter, String name) {
/*  747 */     if (validator != null && !validator.test(value)) {
/*  748 */       throw new IllegalStateException(validator.errorMessage(name, value) + " in " + validator.errorMessage(name, value));
/*      */     }
/*  750 */     setter.accept(value);
/*      */   }
/*      */   
/*      */   private String defaultArrayToString(@Nullable String[] defaultValue) {
/*  754 */     return (defaultValue == null) ? null : Arrays.toString((Object[])defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean requireOrGetDictionary(@Nonnull JsonElement data, String name, String domain, @Nonnull BiConsumer<String, JsonElement> setter, boolean required, BuilderDescriptorState state, String shortDescription, String longDescription) {
/*  762 */     if (isCreatingDescriptor()) {
/*  763 */       this.builderDescriptor.addAttribute(name, "Dictionary", state, shortDescription, longDescription)
/*  764 */         .required()
/*  765 */         .domain(domain);
/*  766 */       return false;
/*      */     } 
/*      */     
/*      */     try {
/*  770 */       JsonElement element = required ? getRequiredJsonElement(data, name) : getOptionalJsonElement(data, name);
/*      */       
/*  772 */       boolean haveValue = (element != null);
/*  773 */       if (haveValue) {
/*  774 */         JsonObject object = expectJsonObject(element, name);
/*  775 */         object.entrySet().forEach(stringJsonElementEntry -> setter.accept((String)stringJsonElementEntry.getKey(), (JsonElement)stringJsonElementEntry.getValue()));
/*      */       } 
/*  777 */       return haveValue;
/*  778 */     } catch (Exception e) {
/*  779 */       addError(e);
/*  780 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireDictionary(@Nonnull JsonElement data, String name, String domain, @Nonnull BiConsumer<String, JsonElement> setter, BuilderDescriptorState state, String shortDescription, String longDescription) {
/*  785 */     requireOrGetDictionary(data, name, domain, setter, true, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getDictionary(@Nonnull JsonElement data, String name, String domain, @Nonnull BiConsumer<String, JsonElement> setter, BuilderDescriptorState state, String shortDescription, String longDescription) {
/*  789 */     return requireOrGetDictionary(data, name, domain, setter, false, state, shortDescription, longDescription);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireDouble(@Nonnull JsonElement data, String name, @Nonnull DoubleConsumer setter, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  797 */     if (isCreatingSchema()) {
/*  798 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/*  799 */       s.setTitle(name);
/*  800 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  801 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  804 */     if (isCreatingDescriptor()) {
/*  805 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/*  806 */         .required()
/*  807 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*      */     try {
/*  811 */       validateAndSet(expectDouble(getRequiredJsonElement(data, name), name), validator, setter, name);
/*  812 */     } catch (Exception e) {
/*  813 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getDouble(@Nonnull JsonElement data, String name, @Nonnull DoubleConsumer setter, double defaultValue, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  818 */     if (isCreatingSchema()) {
/*  819 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/*  820 */       s.setTitle(name);
/*  821 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  822 */       this.builderSchema.getProperties().put(name, s);
/*  823 */       return false;
/*      */     } 
/*  825 */     if (isCreatingDescriptor()) {
/*  826 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/*  827 */         .optional(Double.toString(defaultValue))
/*  828 */         .validator((Validator)validator);
/*  829 */       return false;
/*      */     } 
/*      */     try {
/*  832 */       JsonElement element = getOptionalJsonElement(data, name);
/*  833 */       boolean haveValue = (element != null);
/*  834 */       if (haveValue) {
/*  835 */         defaultValue = expectDouble(element, name);
/*      */       }
/*  837 */       validateAndSet(defaultValue, validator, setter, name);
/*  838 */       return haveValue;
/*  839 */     } catch (Exception e) {
/*  840 */       addError(e);
/*  841 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireDouble(@Nonnull JsonElement data, String name, @Nonnull DoubleHolder doubleHolder, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  846 */     if (isCreatingSchema()) {
/*  847 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/*  848 */       s.setTitle(name);
/*  849 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  850 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  853 */     if (isCreatingDescriptor()) {
/*  854 */       doubleHolder.setName(name);
/*  855 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/*  856 */         .required()
/*  857 */         .computable()
/*  858 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*  861 */     Objects.requireNonNull(doubleHolder, "doubleHolder is null");
/*      */     try {
/*  863 */       doubleHolder.readJSON(getRequiredJsonElement(data, name), validator, name, this.builderParameters);
/*  864 */       trackDynamicHolder((ValueHolder)doubleHolder);
/*  865 */     } catch (Exception e) {
/*  866 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean requireDoubleIfNotOverridden(@Nonnull JsonElement data, String name, @Nonnull DoubleHolder doubleHolder, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  871 */     if (isCreatingSchema()) {
/*  872 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/*  873 */       s.setTitle(name);
/*  874 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  875 */       this.builderSchema.getProperties().put(name, s);
/*  876 */       return false;
/*      */     } 
/*  878 */     if (isCreatingDescriptor()) {
/*  879 */       doubleHolder.setName(name);
/*  880 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/*  881 */         .requiredIfNotOverridden()
/*  882 */         .computable()
/*  883 */         .validator((Validator)validator);
/*  884 */       return false;
/*      */     } 
/*  886 */     Objects.requireNonNull(doubleHolder, "doubleHolder is null");
/*      */     try {
/*  888 */       JsonElement element = getRequiredJsonElementIfNotOverridden(data, name, ParameterType.DOUBLE);
/*  889 */       boolean valueProvided = (element != null);
/*  890 */       doubleHolder.readJSON(element, -1.7976931348623157E308D, !valueProvided ? null : validator, name, this.builderParameters);
/*  891 */       trackDynamicHolder((ValueHolder)doubleHolder);
/*  892 */       return valueProvided;
/*  893 */     } catch (Exception e) {
/*  894 */       addError(e);
/*  895 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getDouble(@Nonnull JsonElement data, String name, @Nonnull DoubleHolder doubleHolder, double defaultValue, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  900 */     if (isCreatingSchema()) {
/*  901 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/*  902 */       s.setTitle(name);
/*  903 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  904 */       this.builderSchema.getProperties().put(name, s);
/*  905 */       return false;
/*      */     } 
/*  907 */     if (isCreatingDescriptor()) {
/*  908 */       doubleHolder.setName(name);
/*  909 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/*  910 */         .optional(Double.toString(defaultValue))
/*  911 */         .computable()
/*  912 */         .validator((Validator)validator);
/*  913 */       return false;
/*      */     } 
/*  915 */     Objects.requireNonNull(doubleHolder, "doubleHolder is null");
/*      */     try {
/*  917 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/*  918 */       doubleHolder.readJSON(optionalJsonElement, defaultValue, validator, name, this.builderParameters);
/*  919 */       trackDynamicHolder((ValueHolder)doubleHolder);
/*  920 */       return (optionalJsonElement != null);
/*  921 */     } catch (Exception e) {
/*  922 */       addError(e);
/*  923 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void validateAndSet(double v, @Nullable DoubleValidator validator, @Nonnull DoubleConsumer setter, String name) {
/*  929 */     if (validator != null && !validator.test(v)) {
/*  930 */       throw new IllegalStateException(validator.errorMessage(v, name) + " in " + validator.errorMessage(v, name));
/*      */     }
/*  932 */     setter.accept(v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireIntArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<int[]> setter, int minLength, int maxLength, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  940 */     if (isCreatingSchema()) {
/*  941 */       ArraySchema a = new ArraySchema((Schema)new IntegerSchema());
/*  942 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/*  943 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/*  944 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  945 */       s.setTitle(name);
/*  946 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  947 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/*  950 */     if (isCreatingDescriptor()) {
/*  951 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/*  952 */         .domain("integer")
/*  953 */         .length(minLength, maxLength)
/*  954 */         .validator((Validator)validator)
/*  955 */         .required();
/*      */       return;
/*      */     } 
/*      */     try {
/*  959 */       validateAndSet(expectIntArray(getRequiredJsonElement(data, name), name, minLength, maxLength), validator, setter, name);
/*  960 */     } catch (Exception e) {
/*  961 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getIntArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<int[]> setter, int[] defaultValue, int minLength, int maxLength, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  966 */     if (isCreatingSchema()) {
/*  967 */       ArraySchema a = new ArraySchema((Schema)new IntegerSchema());
/*  968 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/*  969 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/*  970 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/*  971 */       s.setTitle(name);
/*  972 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/*  973 */       this.builderSchema.getProperties().put(name, s);
/*  974 */       return false;
/*      */     } 
/*  976 */     if (isCreatingDescriptor()) {
/*  977 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/*  978 */         .domain("integer")
/*  979 */         .length(minLength, maxLength)
/*  980 */         .validator((Validator)validator)
/*  981 */         .optional(defaultValue);
/*  982 */       return false;
/*      */     } 
/*      */     try {
/*  985 */       JsonElement element = getOptionalJsonElement(data, name);
/*  986 */       boolean haveValue = (element != null);
/*  987 */       if (haveValue) {
/*  988 */         defaultValue = expectIntArray(element, name, minLength, maxLength);
/*      */       }
/*  990 */       validateAndSet(defaultValue, validator, setter, name);
/*  991 */       return haveValue;
/*  992 */     } catch (Exception e) {
/*  993 */       addError(e);
/*  994 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireIntArray(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, int minLength, int maxLength, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/*  999 */     if (isCreatingSchema()) {
/* 1000 */       ArraySchema a = new ArraySchema((Schema)new IntegerSchema());
/* 1001 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1002 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1003 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1004 */       s.setTitle(name);
/* 1005 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1006 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1009 */     if (isCreatingDescriptor()) {
/* 1010 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1011 */         .domain("integer")
/* 1012 */         .length(minLength, maxLength)
/* 1013 */         .validator((Validator)validator)
/* 1014 */         .computable()
/* 1015 */         .required();
/*      */       return;
/*      */     } 
/* 1018 */     Objects.requireNonNull(holder, "int array holder is null");
/*      */     try {
/* 1020 */       holder.readJSON(getRequiredJsonElement(data, name), minLength, maxLength, validator, name, this.builderParameters);
/* 1021 */       trackDynamicHolder((ValueHolder)holder);
/* 1022 */     } catch (Exception e) {
/* 1023 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getIntArray(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, int[] defaultValue, int minLength, int maxLength, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1028 */     if (isCreatingSchema()) {
/* 1029 */       ArraySchema a = new ArraySchema((Schema)new IntegerSchema());
/* 1030 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1031 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1032 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1033 */       s.setTitle(name);
/* 1034 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1035 */       this.builderSchema.getProperties().put(name, s);
/* 1036 */       return false;
/*      */     } 
/* 1038 */     if (isCreatingDescriptor()) {
/* 1039 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1040 */         .domain("integer")
/* 1041 */         .length(minLength, maxLength)
/* 1042 */         .validator((Validator)validator)
/* 1043 */         .computable()
/* 1044 */         .optional(defaultValue);
/* 1045 */       return false;
/*      */     } 
/* 1047 */     Objects.requireNonNull(holder, "int array holder is null");
/*      */     try {
/* 1049 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1050 */       holder.readJSON(optionalJsonElement, minLength, maxLength, defaultValue, validator, name, this.builderParameters);
/* 1051 */       trackDynamicHolder((ValueHolder)holder);
/* 1052 */       return (optionalJsonElement != null);
/* 1053 */     } catch (Exception e) {
/* 1054 */       addError(e);
/* 1055 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireIntRange(@Nonnull JsonElement data, String name, @Nonnull Consumer<int[]> setter, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1060 */     requireIntArray(data, name, setter, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getIntRange(@Nonnull JsonElement data, String name, @Nonnull Consumer<int[]> setter, int[] defaultValue, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1064 */     return getIntArray(data, name, setter, defaultValue, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public void requireIntRange(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1068 */     requireIntArray(data, name, holder, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getIntRange(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, int[] defaultValue, IntArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1072 */     return getIntArray(data, name, holder, defaultValue, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   private void validateAndSet(int[] v, @Nullable IntArrayValidator validator, @Nonnull Consumer<int[]> setter, String name) {
/* 1076 */     if (validator != null && !validator.test(v)) {
/* 1077 */       throw new IllegalStateException(validator.errorMessage(v, name) + " in " + validator.errorMessage(v, name));
/*      */     }
/* 1079 */     setter.accept(v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireDoubleArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, int minLength, int maxLength, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1087 */     if (isCreatingSchema()) {
/* 1088 */       ArraySchema a = new ArraySchema((Schema)new NumberSchema());
/* 1089 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1090 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1091 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1092 */       s.setTitle(name);
/* 1093 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1094 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1097 */     if (isCreatingDescriptor()) {
/* 1098 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1099 */         .domain("Double")
/* 1100 */         .length(minLength, maxLength)
/* 1101 */         .validator((Validator)validator)
/* 1102 */         .required();
/*      */       return;
/*      */     } 
/*      */     try {
/* 1106 */       validateAndSet(expectDoubleArray(getRequiredJsonElement(data, name), name, minLength, maxLength), validator, setter, name);
/* 1107 */     } catch (Exception e) {
/* 1108 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getDoubleArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, double[] defaultValue, int minLength, int maxLength, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1113 */     if (isCreatingSchema()) {
/* 1114 */       ArraySchema a = new ArraySchema((Schema)new NumberSchema());
/* 1115 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1116 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1117 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1118 */       s.setTitle(name);
/* 1119 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1120 */       this.builderSchema.getProperties().put(name, s);
/* 1121 */       return false;
/*      */     } 
/* 1123 */     if (isCreatingDescriptor()) {
/* 1124 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1125 */         .domain("Double")
/* 1126 */         .length(minLength, maxLength)
/* 1127 */         .validator((Validator)validator)
/* 1128 */         .optional(defaultValue);
/* 1129 */       return false;
/*      */     } 
/*      */     try {
/* 1132 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1133 */       boolean haveValue = (element != null);
/* 1134 */       if (haveValue) {
/* 1135 */         defaultValue = expectDoubleArray(element, name, minLength, maxLength);
/*      */       }
/* 1137 */       validateAndSet(defaultValue, validator, setter, name);
/* 1138 */       return haveValue;
/* 1139 */     } catch (Exception e) {
/* 1140 */       addError(e);
/* 1141 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireDoubleArray(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, int minLength, int maxLength, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1146 */     if (isCreatingSchema()) {
/* 1147 */       ArraySchema a = new ArraySchema((Schema)new NumberSchema());
/* 1148 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1149 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1150 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1151 */       s.setTitle(name);
/* 1152 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1153 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1156 */     if (isCreatingDescriptor()) {
/* 1157 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1158 */         .domain("Double")
/* 1159 */         .length(minLength, maxLength)
/* 1160 */         .validator((Validator)validator)
/* 1161 */         .computable()
/* 1162 */         .required();
/*      */       return;
/*      */     } 
/* 1165 */     Objects.requireNonNull(holder, "double array holder is null");
/*      */     try {
/* 1167 */       holder.readJSON(getRequiredJsonElement(data, name), minLength, maxLength, validator, name, this.builderParameters);
/* 1168 */       trackDynamicHolder((ValueHolder)holder);
/* 1169 */     } catch (Exception e) {
/* 1170 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getDoubleArray(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, double[] defaultValue, int minLength, int maxLength, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1175 */     if (isCreatingSchema()) {
/* 1176 */       ArraySchema a = new ArraySchema((Schema)new NumberSchema());
/* 1177 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 1178 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 1179 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 1180 */       s.setTitle(name);
/* 1181 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1182 */       this.builderSchema.getProperties().put(name, s);
/* 1183 */       return false;
/*      */     } 
/* 1185 */     if (isCreatingDescriptor()) {
/* 1186 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 1187 */         .domain("Double")
/* 1188 */         .length(minLength, maxLength)
/* 1189 */         .validator((Validator)validator)
/* 1190 */         .computable()
/* 1191 */         .optional(defaultValue);
/* 1192 */       return false;
/*      */     } 
/* 1194 */     Objects.requireNonNull(holder, "double array holder is null");
/*      */     try {
/* 1196 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1197 */       holder.readJSON(optionalJsonElement, minLength, maxLength, defaultValue, validator, name, this.builderParameters);
/* 1198 */       trackDynamicHolder((ValueHolder)holder);
/* 1199 */       return (optionalJsonElement != null);
/* 1200 */     } catch (Exception e) {
/* 1201 */       addError(e);
/* 1202 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireDoubleRange(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1207 */     requireDoubleArray(data, name, setter, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getDoubleRange(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, double[] defaultValue, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1211 */     return getDoubleArray(data, name, setter, defaultValue, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public void requireVector3d(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1215 */     requireDoubleArray(data, name, setter, 3, 3, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getVector3d(@Nonnull JsonElement data, String name, @Nonnull Consumer<double[]> setter, double[] defaultValue, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1219 */     return getDoubleArray(data, name, setter, defaultValue, 3, 3, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public void requireDoubleRange(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1223 */     requireDoubleArray(data, name, holder, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getDoubleRange(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, double[] defaultValue, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1227 */     return getDoubleArray(data, name, holder, defaultValue, 2, 2, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public void requireVector3d(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1231 */     requireDoubleArray(data, name, holder, 3, 3, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   public boolean getVector3d(@Nonnull JsonElement data, String name, @Nonnull NumberArrayHolder holder, double[] defaultValue, DoubleArrayValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1235 */     return getDoubleArray(data, name, holder, defaultValue, 3, 3, validator, state, shortDescription, longDescription);
/*      */   }
/*      */   
/*      */   private void validateAndSet(double[] v, @Nullable DoubleArrayValidator validator, @Nonnull Consumer<double[]> setter, String name) {
/* 1239 */     if (validator != null && !validator.test(v)) {
/* 1240 */       throw new IllegalStateException(validator.errorMessage(v, name) + " in " + validator.errorMessage(v, name));
/*      */     }
/* 1242 */     setter.accept(v);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3d createVector3d(@Nonnull double[] coordinates) {
/* 1247 */     return new Vector3d(coordinates[0], coordinates[1], coordinates[2]);
/*      */   }
/*      */   
/*      */   public static Vector3d createVector3d(@Nullable double[] coordinates, @Nonnull Supplier<Vector3d> defaultSupplier) {
/* 1251 */     return (coordinates != null) ? createVector3d(coordinates) : defaultSupplier.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireFloat(@Nonnull JsonElement data, String name, @Nonnull FloatConsumer setter, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1259 */     if (isCreatingSchema()) {
/* 1260 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/* 1261 */       s.setTitle(name);
/* 1262 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1263 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1266 */     if (isCreatingDescriptor()) {
/* 1267 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/* 1268 */         .required()
/* 1269 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*      */     try {
/* 1273 */       validateAndSet((float)expectDouble(getRequiredJsonElement(data, name), name), validator, setter, name);
/* 1274 */     } catch (Exception e) {
/* 1275 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getFloat(@Nonnull JsonElement data, String name, @Nonnull FloatConsumer setter, float defaultValue, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1280 */     if (isCreatingSchema()) {
/* 1281 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/* 1282 */       s.setTitle(name);
/* 1283 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1284 */       this.builderSchema.getProperties().put(name, s);
/* 1285 */       return false;
/*      */     } 
/* 1287 */     if (isCreatingDescriptor()) {
/* 1288 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/* 1289 */         .optional(Double.toString(defaultValue))
/* 1290 */         .validator((Validator)validator);
/* 1291 */       return false;
/*      */     } 
/*      */     try {
/* 1294 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1295 */       boolean haveValue = (element != null);
/* 1296 */       if (haveValue) {
/* 1297 */         defaultValue = (float)expectDouble(element, name);
/*      */       }
/* 1299 */       validateAndSet(defaultValue, validator, setter, name);
/* 1300 */       return haveValue;
/* 1301 */     } catch (Exception e) {
/* 1302 */       addError(e);
/* 1303 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireFloat(@Nonnull JsonElement data, String name, @Nonnull FloatHolder floatHolder, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1308 */     if (isCreatingSchema()) {
/* 1309 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/* 1310 */       s.setTitle(name);
/* 1311 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1312 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1315 */     if (isCreatingDescriptor()) {
/* 1316 */       floatHolder.setName(name);
/* 1317 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/* 1318 */         .required()
/* 1319 */         .computable()
/* 1320 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/* 1323 */     Objects.requireNonNull(floatHolder, "floatHolder is null");
/*      */     try {
/* 1325 */       floatHolder.readJSON(getRequiredJsonElement(data, name), validator, name, this.builderParameters);
/* 1326 */       trackDynamicHolder((ValueHolder)floatHolder);
/* 1327 */     } catch (Exception e) {
/* 1328 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getFloat(@Nonnull JsonElement data, String name, @Nonnull FloatHolder floatHolder, double defaultValue, DoubleValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1333 */     if (isCreatingSchema()) {
/* 1334 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new NumberSchema());
/* 1335 */       s.setTitle(name);
/* 1336 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1337 */       this.builderSchema.getProperties().put(name, s);
/* 1338 */       return false;
/*      */     } 
/* 1340 */     if (isCreatingDescriptor()) {
/* 1341 */       floatHolder.setName(name);
/* 1342 */       this.builderDescriptor.addAttribute(name, Double.class.getSimpleName(), state, shortDescription, longDescription)
/* 1343 */         .optional(Double.toString(defaultValue))
/* 1344 */         .computable()
/* 1345 */         .validator((Validator)validator);
/* 1346 */       return false;
/*      */     } 
/* 1348 */     Objects.requireNonNull(floatHolder, "floatHolder is null");
/*      */     try {
/* 1350 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1351 */       floatHolder.readJSON(optionalJsonElement, defaultValue, validator, name, this.builderParameters);
/* 1352 */       trackDynamicHolder((ValueHolder)floatHolder);
/* 1353 */       return (optionalJsonElement != null);
/* 1354 */     } catch (Exception e) {
/* 1355 */       addError(e);
/* 1356 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(float v, @Nullable DoubleValidator validator, @Nonnull FloatConsumer setter, String name) {
/* 1361 */     if (validator != null && !validator.test(v)) {
/* 1362 */       throw new IllegalStateException(validator.errorMessage(v, name) + " in " + validator.errorMessage(v, name));
/*      */     }
/* 1364 */     setter.accept(v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireInt(@Nonnull JsonElement data, String name, @Nonnull IntConsumer setter, IntValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1372 */     if (isCreatingSchema()) {
/* 1373 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new IntegerSchema());
/* 1374 */       s.setTitle(name);
/* 1375 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1376 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1379 */     if (isCreatingDescriptor()) {
/* 1380 */       this.builderDescriptor.addAttribute(name, Integer.class.getSimpleName(), state, shortDescription, longDescription)
/* 1381 */         .required()
/* 1382 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/*      */     try {
/* 1386 */       validateAndSet(expectInteger(getRequiredJsonElement(data, name), name), validator, setter, name);
/* 1387 */     } catch (Exception e) {
/* 1388 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getInt(@Nonnull JsonElement data, String name, @Nonnull IntConsumer setter, int defaultValue, IntValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1393 */     if (isCreatingSchema()) {
/* 1394 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new IntegerSchema());
/* 1395 */       s.setTitle(name);
/* 1396 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1397 */       this.builderSchema.getProperties().put(name, s);
/* 1398 */       return false;
/*      */     } 
/* 1400 */     if (isCreatingDescriptor()) {
/* 1401 */       this.builderDescriptor.addAttribute(name, Integer.class.getSimpleName(), state, shortDescription, longDescription)
/* 1402 */         .optional(Integer.toString(defaultValue))
/* 1403 */         .validator((Validator)validator);
/* 1404 */       return false;
/*      */     } 
/*      */     try {
/* 1407 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1408 */       boolean haveValue = (element != null);
/* 1409 */       if (haveValue) {
/* 1410 */         defaultValue = expectInteger(element, name);
/*      */       }
/* 1412 */       validateAndSet(defaultValue, validator, setter, name);
/* 1413 */       return haveValue;
/* 1414 */     } catch (Exception e) {
/* 1415 */       addError(e);
/* 1416 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireInt(@Nonnull JsonElement data, String name, @Nonnull IntHolder intHolder, IntValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1421 */     if (isCreatingSchema()) {
/* 1422 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new IntegerSchema());
/* 1423 */       s.setTitle(name);
/* 1424 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1425 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1428 */     if (isCreatingDescriptor()) {
/* 1429 */       intHolder.setName(name);
/* 1430 */       this.builderDescriptor.addAttribute(name, Integer.class.getSimpleName(), state, shortDescription, longDescription)
/* 1431 */         .required()
/* 1432 */         .computable()
/* 1433 */         .validator((Validator)validator);
/*      */       return;
/*      */     } 
/* 1436 */     Objects.requireNonNull(intHolder, "intHolder is null");
/*      */     try {
/* 1438 */       intHolder.readJSON(getRequiredJsonElement(data, name), validator, name, this.builderParameters);
/* 1439 */       trackDynamicHolder((ValueHolder)intHolder);
/* 1440 */     } catch (Exception e) {
/* 1441 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean requireIntIfNotOverridden(@Nonnull JsonElement data, String name, @Nonnull IntHolder intHolder, IntValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1446 */     if (isCreatingSchema()) {
/* 1447 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new IntegerSchema());
/* 1448 */       s.setTitle(name);
/* 1449 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1450 */       this.builderSchema.getProperties().put(name, s);
/* 1451 */       return false;
/*      */     } 
/* 1453 */     if (isCreatingDescriptor()) {
/* 1454 */       intHolder.setName(name);
/* 1455 */       this.builderDescriptor.addAttribute(name, Integer.class.getSimpleName(), state, shortDescription, longDescription)
/* 1456 */         .requiredIfNotOverridden()
/* 1457 */         .computable()
/* 1458 */         .validator((Validator)validator);
/* 1459 */       return false;
/*      */     } 
/* 1461 */     Objects.requireNonNull(intHolder, "intHolder is null");
/*      */     try {
/* 1463 */       JsonElement element = getRequiredJsonElementIfNotOverridden(data, name, ParameterType.INTEGER);
/* 1464 */       boolean valueProvided = (element != null);
/* 1465 */       intHolder.readJSON(element, -2147483648, !valueProvided ? null : validator, name, this.builderParameters);
/* 1466 */       trackDynamicHolder((ValueHolder)intHolder);
/* 1467 */       return valueProvided;
/* 1468 */     } catch (Exception e) {
/* 1469 */       addError(e);
/* 1470 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getInt(@Nonnull JsonElement data, String name, @Nonnull IntHolder intHolder, int defaultValue, IntValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1475 */     if (isCreatingSchema()) {
/* 1476 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new IntegerSchema());
/* 1477 */       s.setTitle(name);
/* 1478 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1479 */       this.builderSchema.getProperties().put(name, s);
/* 1480 */       return false;
/*      */     } 
/* 1482 */     if (isCreatingDescriptor()) {
/* 1483 */       intHolder.setName(name);
/* 1484 */       this.builderDescriptor.addAttribute(name, Integer.class.getSimpleName(), state, shortDescription, longDescription)
/* 1485 */         .optional(Double.toString(defaultValue))
/* 1486 */         .computable()
/* 1487 */         .validator((Validator)validator);
/* 1488 */       return false;
/*      */     } 
/*      */     try {
/* 1491 */       Objects.requireNonNull(intHolder, "intHolder is null");
/* 1492 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1493 */       intHolder.readJSON(optionalJsonElement, defaultValue, validator, name, this.builderParameters);
/* 1494 */       trackDynamicHolder((ValueHolder)intHolder);
/* 1495 */       return (optionalJsonElement != null);
/* 1496 */     } catch (Exception e) {
/* 1497 */       addError(e);
/* 1498 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(int v, @Nullable IntValidator validator, @Nonnull IntConsumer setter, String name) {
/* 1503 */     if (validator != null && !validator.test(v)) {
/* 1504 */       throw new IllegalStateException(validator.errorMessage(v, name) + " in " + validator.errorMessage(v, name));
/*      */     }
/* 1506 */     setter.accept(v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireBoolean(@Nonnull JsonElement data, String name, @Nonnull BooleanHolder booleanHolder, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1514 */     if (isCreatingSchema()) {
/* 1515 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema());
/* 1516 */       s.setTitle(name);
/* 1517 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1518 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1521 */     if (isCreatingDescriptor()) {
/* 1522 */       booleanHolder.setName(name);
/* 1523 */       this.builderDescriptor.addAttribute(name, Boolean.class.getSimpleName(), state, shortDescription, longDescription)
/* 1524 */         .required()
/* 1525 */         .computable();
/*      */       return;
/*      */     } 
/* 1528 */     Objects.requireNonNull(booleanHolder, "booleanHolder is null");
/*      */     try {
/* 1530 */       booleanHolder.readJSON(getRequiredJsonElement(data, name), name, this.builderParameters);
/* 1531 */       trackDynamicHolder((ValueHolder)booleanHolder);
/* 1532 */     } catch (Exception e) {
/* 1533 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getBoolean(@Nonnull JsonElement data, String name, @Nonnull BooleanHolder booleanHolder, boolean defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1538 */     if (isCreatingSchema()) {
/* 1539 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema());
/* 1540 */       s.setTitle(name);
/* 1541 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1542 */       this.builderSchema.getProperties().put(name, s);
/* 1543 */       return false;
/*      */     } 
/* 1545 */     if (isCreatingDescriptor()) {
/* 1546 */       booleanHolder.setName(name);
/* 1547 */       this.builderDescriptor.addAttribute(name, Boolean.class.getSimpleName(), state, shortDescription, longDescription)
/* 1548 */         .optional(Boolean.toString(defaultValue))
/* 1549 */         .computable();
/* 1550 */       return false;
/*      */     } 
/* 1552 */     Objects.requireNonNull(booleanHolder, "booleanHolder is null");
/*      */     try {
/* 1554 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1555 */       booleanHolder.readJSON(optionalJsonElement, defaultValue, name, this.builderParameters);
/* 1556 */       trackDynamicHolder((ValueHolder)booleanHolder);
/* 1557 */       return (optionalJsonElement != null);
/* 1558 */     } catch (Exception e) {
/* 1559 */       addError(e);
/* 1560 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireBoolean(@Nonnull JsonElement data, String name, @Nonnull BooleanConsumer setter, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1565 */     if (isCreatingSchema()) {
/* 1566 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema());
/* 1567 */       s.setTitle(name);
/* 1568 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1569 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 1572 */     if (isCreatingDescriptor()) {
/* 1573 */       this.builderDescriptor.addAttribute(name, Boolean.class.getSimpleName(), state, shortDescription, longDescription)
/* 1574 */         .required();
/*      */       return;
/*      */     } 
/*      */     try {
/* 1578 */       setter.accept(expectBoolean(getRequiredJsonElement(data, name), name));
/* 1579 */     } catch (Exception e) {
/* 1580 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getBoolean(@Nonnull JsonElement data, String name, @Nonnull BooleanConsumer setter, boolean defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1585 */     if (isCreatingSchema()) {
/* 1586 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema());
/* 1587 */       s.setTitle(name);
/* 1588 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1589 */       this.builderSchema.getProperties().put(name, s);
/* 1590 */       return false;
/*      */     } 
/* 1592 */     if (isCreatingDescriptor()) {
/* 1593 */       this.builderDescriptor.addAttribute(name, Boolean.class.getSimpleName(), state, shortDescription, longDescription)
/* 1594 */         .optional(Boolean.toString(defaultValue));
/* 1595 */       return false;
/*      */     } 
/*      */     try {
/* 1598 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1599 */       boolean haveValue = (element != null);
/* 1600 */       if (haveValue) {
/* 1601 */         defaultValue = expectBoolean(element, name);
/*      */       }
/* 1603 */       setter.accept(defaultValue);
/* 1604 */       return haveValue;
/* 1605 */     } catch (Exception e) {
/* 1606 */       addError(e);
/* 1607 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void getParameterBlock(@Nonnull JsonElement data, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 1612 */     if (isCreatingSchema()) {
/* 1613 */       this.builderSchema.getProperties().put("Parameters", new ObjectSchema());
/*      */       return;
/*      */     } 
/* 1616 */     if (isCreatingDescriptor()) {
/* 1617 */       this.builderDescriptor.addAttribute("Parameters", "Parameters", state, shortDescription, longDescription)
/* 1618 */         .optional("");
/*      */       
/*      */       return;
/*      */     } 
/* 1622 */     if (!data.isJsonObject()) {
/* 1623 */       throw new IllegalStateException(String.format("Looking for parameter block in a JsonElement that isn't an object at %s", new Object[] { getBreadCrumbs() }));
/*      */     }
/*      */     
/* 1626 */     BuilderParameters builderParameters = new BuilderParameters(this.builderParameters);
/* 1627 */     builderParameters.readJSON(data.getAsJsonObject(), this.stateHelper);
/* 1628 */     builderParameters.validateNoDuplicateParameters(this.builderParameters);
/* 1629 */     builderParameters.addParametersToScope();
/* 1630 */     this.builderParameters = builderParameters;
/* 1631 */     addQueryKey("Parameters");
/*      */   }
/*      */   
/*      */   public void cleanupParameters() {
/* 1635 */     if (isCreatingDescriptor())
/* 1636 */       return;  this.builderParameters.disposeCompileContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected <E extends Enum<E>> E resolveValue(String txt, E[] enumConstants, String paramName) {
/*      */     try {
/* 1646 */       return stringToEnum(txt, enumConstants, paramName);
/* 1647 */     } catch (IllegalArgumentException e) {
/* 1648 */       throw new IllegalArgumentException(e.getMessage() + " in " + e.getMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static <E extends Enum<E>> E stringToEnum(@Nullable String value, E[] enumConstants, String ident) {
/* 1655 */     if (value != null && !value.isBlank()) {
/* 1656 */       String trimmed = value.trim();
/* 1657 */       for (E e : enumConstants) {
/* 1658 */         if (e.name().equalsIgnoreCase(trimmed)) {
/* 1659 */           return e;
/*      */         }
/*      */       } 
/*      */     } 
/* 1663 */     throw new IllegalArgumentException(String.format("Enum value '%s' is '%s', must be one of %s", new Object[] { ident, value, getDomain(enumConstants) }));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static <E extends Enum<E>> String getDomain(E[] enumConstants) {
/* 1668 */     return Arrays.toString((Object[])enumConstants);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private static String formatEnumCamelCase(@Nonnull String name) {
/* 1673 */     boolean isLower = Character.isLowerCase(name.charAt(0));
/* 1674 */     if (name.chars().anyMatch(v -> (Character.isLowerCase(v) != isLower))) {
/* 1675 */       return name;
/*      */     }
/*      */     
/* 1678 */     StringBuilder nameParts = new StringBuilder();
/* 1679 */     for (String part : name.split("_")) {
/* 1680 */       nameParts.append(Character.toUpperCase(part.charAt(0)))
/* 1681 */         .append(part.substring(1).toLowerCase())
/* 1682 */         .append('_');
/*      */     }
/* 1684 */     nameParts.deleteCharAt(nameParts.length() - 1);
/* 1685 */     return nameParts.toString();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private static <E extends Enum<E>> String[] getEnumValues(@Nonnull Class<E> enumClass) {
/* 1690 */     return (String[])Arrays.<Enum>stream((Enum[])enumClass.getEnumConstants())
/* 1691 */       .map(Enum::name)
/* 1692 */       .map(BuilderBase::formatEnumCamelCase)
/* 1693 */       .toArray(x$0 -> new String[x$0]);
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> void requireEnum(@Nonnull JsonElement data, String name, @Nonnull Consumer<E> setter, @Nonnull Class<E> clazz, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1697 */     if (isCreatingSchema()) {
/* 1698 */       StringSchema s = new StringSchema();
/* 1699 */       s.setEnum(getEnumValues(clazz));
/* 1700 */       s.setTitle(name);
/* 1701 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1702 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)s));
/*      */       return;
/*      */     } 
/* 1705 */     if (isCreatingDescriptor()) {
/* 1706 */       this.builderDescriptor.addAttribute(name, "Flag", state, shortDescription, longDescription)
/* 1707 */         .required()
/* 1708 */         .setEnum(clazz);
/*      */       return;
/*      */     } 
/*      */     try {
/* 1712 */       setter.accept((E)resolveValue(expectString(getRequiredJsonElement(data, name), name), (Enum[])clazz.getEnumConstants(), name));
/* 1713 */     } catch (Exception e) {
/* 1714 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> boolean getEnum(@Nonnull JsonElement data, String name, @Nonnull Consumer<E> setter, @Nonnull Class<E> clazz, @Nullable E defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1719 */     if (isCreatingSchema()) {
/* 1720 */       StringSchema s = new StringSchema();
/* 1721 */       s.setEnum(getEnumValues(clazz));
/* 1722 */       s.setTitle(name);
/* 1723 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1724 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)s));
/* 1725 */       return false;
/*      */     } 
/* 1727 */     if (isCreatingDescriptor()) {
/* 1728 */       this.builderDescriptor.addAttribute(name, "Flag", state, shortDescription, longDescription)
/* 1729 */         .optional((defaultValue != null) ? defaultValue.toString() : "<context>")
/* 1730 */         .setEnum(clazz);
/* 1731 */       return false;
/*      */     } 
/*      */     try {
/* 1734 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1735 */       boolean haveValue = (element != null);
/* 1736 */       if (haveValue) {
/* 1737 */         defaultValue = resolveValue(expectString(element, name), (Enum[])clazz.getEnumConstants(), name);
/*      */       }
/* 1739 */       setter.accept(defaultValue);
/* 1740 */       return haveValue;
/* 1741 */     } catch (Exception e) {
/* 1742 */       addError(e);
/* 1743 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> void requireEnum(@Nonnull JsonElement data, String name, @Nonnull EnumHolder<E> enumHolder, @Nonnull Class<E> clazz, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1748 */     if (isCreatingSchema()) {
/* 1749 */       StringSchema s = new StringSchema();
/* 1750 */       s.setEnum(getEnumValues(clazz));
/* 1751 */       s.setTitle(name);
/* 1752 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1753 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)s));
/*      */       return;
/*      */     } 
/* 1756 */     if (isCreatingDescriptor()) {
/* 1757 */       enumHolder.setName(name);
/* 1758 */       this.builderDescriptor.addAttribute(name, "Flag", state, shortDescription, longDescription)
/* 1759 */         .required()
/* 1760 */         .computable()
/* 1761 */         .setEnum(clazz);
/*      */       return;
/*      */     } 
/* 1764 */     Objects.requireNonNull(enumHolder, "enumHolder is null");
/*      */     try {
/* 1766 */       enumHolder.readJSON(getRequiredJsonElement(data, name), clazz, name, this.builderParameters);
/* 1767 */       trackDynamicHolder((ValueHolder)enumHolder);
/* 1768 */     } catch (Exception e) {
/* 1769 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> boolean getEnum(@Nonnull JsonElement data, String name, @Nonnull EnumHolder<E> enumHolder, @Nonnull Class<E> clazz, @Nonnull E defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1774 */     if (isCreatingSchema()) {
/* 1775 */       StringSchema s = new StringSchema();
/* 1776 */       s.setEnum(getEnumValues(clazz));
/* 1777 */       s.setTitle(name);
/* 1778 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1779 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)s));
/* 1780 */       return false;
/*      */     } 
/* 1782 */     if (isCreatingDescriptor()) {
/* 1783 */       enumHolder.setName(name);
/* 1784 */       this.builderDescriptor.addAttribute(name, "Flag", state, shortDescription, longDescription)
/* 1785 */         .optional(defaultValue.toString())
/* 1786 */         .computable()
/* 1787 */         .setEnum(clazz);
/* 1788 */       return false;
/*      */     } 
/* 1790 */     Objects.requireNonNull(enumHolder, "enumHolder is null");
/*      */     try {
/* 1792 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 1793 */       enumHolder.readJSON(optionalJsonElement, clazz, (Enum)defaultValue, name, this.builderParameters);
/* 1794 */       trackDynamicHolder((ValueHolder)enumHolder);
/* 1795 */       return (optionalJsonElement != null);
/* 1796 */     } catch (Exception e) {
/* 1797 */       addError(e);
/* 1798 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static <E extends Enum<E>> String[] enumSetToStrings(@Nonnull EnumSet<E> enumSet) {
/* 1808 */     int count = 0;
/* 1809 */     Iterator<E> it = enumSet.iterator();
/* 1810 */     while (it.hasNext()) {
/* 1811 */       count++;
/* 1812 */       it.next();
/*      */     } 
/* 1814 */     if (count == 0) return ArrayUtil.EMPTY_STRING_ARRAY;
/*      */     
/* 1816 */     String[] result = new String[count];
/* 1817 */     it = enumSet.iterator();
/* 1818 */     for (int i = 0; i < count; i++) {
/* 1819 */       result[i] = ((Enum)it.next()).toString();
/*      */     }
/* 1821 */     return result;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static <E extends Enum<E>> EnumSet<E> stringsToEnumSet(@Nullable String[] array, @Nonnull Class<E> clazz, E[] enumConstants, String ident) {
/* 1826 */     EnumSet<E> value = EnumSet.noneOf(clazz);
/* 1827 */     if (array == null) return value; 
/* 1828 */     for (String s : array) {
/* 1829 */       value.add(stringToEnum(s, enumConstants, ident));
/*      */     }
/* 1831 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static <E extends Enum<E>> E[] stringsToEnumArray(@Nullable String[] array, @Nonnull Class<E> clazz, E[] enumConstants, String ident) {
/* 1837 */     if (array == null || array.length == 0) return (E[])Array.newInstance(clazz, 0); 
/* 1838 */     Enum[] arrayOfEnum = (Enum[])Array.newInstance(clazz, array.length);
/* 1839 */     for (int i = 0; i < array.length; i++) {
/* 1840 */       arrayOfEnum[i] = stringToEnum(array[i], (Enum[])enumConstants, ident);
/*      */     }
/* 1842 */     return (E[])arrayOfEnum;
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E>> void toSet(String name, @Nonnull Class<E> clazz, @Nonnull EnumSet<E> t, @Nonnull String elementAsString) {
/* 1846 */     Enum[] arrayOfEnum = (Enum[])clazz.getEnumConstants();
/* 1847 */     for (String s : elementAsString.split(",")) {
/* 1848 */       t.add(resolveValue(s.trim(), (E[])arrayOfEnum, name));
/*      */     }
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected EnumSet<RoleDebugFlags> toDebugFlagSet(String name, @Nonnull String elementAsString) {
/*      */     try {
/* 1855 */       return RoleDebugFlags.getFlags(PATTERN.split(elementAsString.trim()));
/* 1856 */     } catch (IllegalArgumentException e) {
/* 1857 */       throw new IllegalArgumentException(e.getMessage() + " in parameter " + e.getMessage() + " at " + name, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E>> void toSet(String name, @Nonnull Class<E> clazz, @Nonnull EnumSet<E> t, @Nonnull JsonArray jsonArray) {
/* 1862 */     Enum[] arrayOfEnum = (Enum[])clazz.getEnumConstants();
/* 1863 */     for (JsonElement jsonElement : jsonArray) {
/* 1864 */       t.add(resolveValue(expectString(jsonElement, name), (E[])arrayOfEnum, name));
/*      */     }
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E>> void toSet(String name, @Nonnull Class<E> clazz, @Nonnull EnumSet<E> t, @Nonnull JsonElement jsonElement) {
/* 1869 */     if (jsonElement.isJsonArray()) {
/* 1870 */       toSet(name, clazz, t, expectJsonArray(jsonElement, name));
/* 1871 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("Use of strings for enum sets is deprecated for JSON attribute '%s' (use []) in %s: %s", name, getBreadCrumbs(), this.builderParameters.getFileName());
/*      */     } else {
/* 1873 */       toSet(name, clazz, t, expectString(jsonElement, name));
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> void requireEnumArray(@Nonnull JsonElement data, String name, @Nonnull EnumArrayHolder<E> enumArrayHolderHolder, @Nonnull Class<E> clazz, EnumArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1878 */     if (isCreatingSchema()) {
/* 1879 */       StringSchema s = new StringSchema();
/* 1880 */       s.setEnum(getEnumValues(clazz));
/* 1881 */       s.setTitle(name);
/* 1882 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1883 */       ArraySchema a = new ArraySchema();
/* 1884 */       a.setItem((Schema)s);
/* 1885 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)a));
/*      */       return;
/*      */     } 
/* 1888 */     if (isCreatingDescriptor()) {
/* 1889 */       enumArrayHolderHolder.setName(name);
/* 1890 */       this.builderDescriptor.addAttribute(name, "FlagArray", state, shortDescription, longDescription)
/* 1891 */         .required()
/* 1892 */         .computable()
/* 1893 */         .validator((Validator)validator)
/* 1894 */         .setEnum(clazz);
/*      */       return;
/*      */     } 
/* 1897 */     Objects.requireNonNull(enumArrayHolderHolder, "enumArrayHolder is null");
/*      */     try {
/* 1899 */       enumArrayHolderHolder.readJSON(getRequiredJsonElement(data, name), clazz, validator, name, this.builderParameters);
/* 1900 */       trackDynamicHolder((ValueHolder)enumArrayHolderHolder);
/* 1901 */     } catch (Exception e) {
/* 1902 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> void requireEnumSet(@Nonnull JsonElement data, String name, @Nonnull Consumer<? super EnumSet<E>> setter, @Nonnull Class<E> clazz, @Nonnull Supplier<? extends EnumSet<E>> factory, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1907 */     if (isCreatingSchema()) {
/* 1908 */       StringSchema s = new StringSchema();
/* 1909 */       s.setEnum(getEnumValues(clazz));
/* 1910 */       s.setTitle(name);
/* 1911 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1912 */       ArraySchema a = new ArraySchema();
/* 1913 */       a.setItem((Schema)s);
/* 1914 */       a.setUniqueItems(true);
/* 1915 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)a));
/*      */       return;
/*      */     } 
/* 1918 */     if (isCreatingDescriptor()) {
/* 1919 */       this.builderDescriptor.addAttribute(name, "FlagSet", state, shortDescription, longDescription)
/* 1920 */         .required()
/* 1921 */         .setEnum(clazz);
/*      */       return;
/*      */     } 
/*      */     try {
/* 1925 */       EnumSet<E> t = factory.get();
/* 1926 */       toSet(name, clazz, t, getRequiredJsonElement(data, name));
/* 1927 */       setter.accept(t);
/* 1928 */     } catch (Exception e) {
/* 1929 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> boolean getEnumSet(@Nonnull JsonElement data, String name, @Nonnull Consumer<? super EnumSet<E>> setter, @Nonnull Class<E> clazz, @Nonnull Supplier<? extends EnumSet<E>> factory, @Nonnull EnumSet<E> defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1934 */     if (isCreatingSchema()) {
/* 1935 */       StringSchema s = new StringSchema();
/* 1936 */       s.setEnum(getEnumValues(clazz));
/* 1937 */       s.setTitle(name);
/* 1938 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1939 */       ArraySchema a = new ArraySchema();
/* 1940 */       a.setItem((Schema)s);
/* 1941 */       a.setUniqueItems(true);
/* 1942 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)a));
/* 1943 */       return false;
/*      */     } 
/* 1945 */     if (isCreatingDescriptor()) {
/* 1946 */       this.builderDescriptor.addAttribute(name, "FlagSet", state, shortDescription, longDescription)
/* 1947 */         .optional(defaultValue.toString())
/* 1948 */         .setEnum(clazz);
/* 1949 */       return false;
/*      */     } 
/*      */     try {
/* 1952 */       JsonElement element = getOptionalJsonElement(data, name);
/* 1953 */       if (element != null) {
/* 1954 */         EnumSet<E> t = factory.get();
/* 1955 */         toSet(name, clazz, t, element);
/* 1956 */         setter.accept(t);
/* 1957 */         return true;
/*      */       } 
/* 1959 */       setter.accept(defaultValue);
/* 1960 */     } catch (Exception e) {
/* 1961 */       addError(e);
/*      */     } 
/* 1963 */     return false;
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> void requireEnumSet(@Nonnull JsonElement data, String name, @Nonnull EnumSetHolder<E> enumSetHolder, @Nonnull Class<E> clazz, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 1967 */     if (isCreatingSchema()) {
/* 1968 */       StringSchema s = new StringSchema();
/* 1969 */       s.setEnum(getEnumValues(clazz));
/* 1970 */       s.setTitle(name);
/* 1971 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1972 */       ArraySchema a = new ArraySchema();
/* 1973 */       a.setItem((Schema)s);
/* 1974 */       a.setUniqueItems(true);
/* 1975 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)a));
/*      */       return;
/*      */     } 
/* 1978 */     if (isCreatingSchema()) {
/* 1979 */       StringSchema s = new StringSchema();
/* 1980 */       s.setEnum(getEnumValues(clazz));
/* 1981 */       s.setTitle(name);
/* 1982 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 1983 */       ArraySchema a = new ArraySchema();
/* 1984 */       a.setItem((Schema)s);
/* 1985 */       a.setUniqueItems(true);
/* 1986 */       this.builderSchema.getProperties().put(name, a);
/*      */       return;
/*      */     } 
/* 1989 */     if (isCreatingDescriptor()) {
/* 1990 */       enumSetHolder.setName(name);
/* 1991 */       this.builderDescriptor.addAttribute(name, "FlagSet", state, shortDescription, longDescription)
/* 1992 */         .required()
/* 1993 */         .computable()
/* 1994 */         .setEnum(clazz);
/*      */       return;
/*      */     } 
/* 1997 */     Objects.requireNonNull(enumSetHolder, "enumSetHolder is null");
/*      */     try {
/* 1999 */       enumSetHolder.readJSON(getRequiredJsonElement(data, name), clazz, name, this.builderParameters);
/* 2000 */       trackDynamicHolder((ValueHolder)enumSetHolder);
/* 2001 */     } catch (Exception e) {
/* 2002 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <E extends Enum<E> & Supplier<String>> boolean getEnumSet(@Nonnull JsonElement data, String name, @Nonnull EnumSetHolder<E> enumSetHolder, @Nonnull Class<E> clazz, @Nonnull EnumSet<E> defaultValue, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2007 */     if (isCreatingSchema()) {
/* 2008 */       StringSchema s = new StringSchema();
/* 2009 */       s.setEnum(getEnumValues(clazz));
/* 2010 */       s.setTitle(name);
/* 2011 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2012 */       ArraySchema a = new ArraySchema();
/* 2013 */       a.setItem((Schema)s);
/* 2014 */       a.setUniqueItems(true);
/* 2015 */       this.builderSchema.getProperties().put(name, BuilderExpressionDynamic.computableSchema((Schema)a));
/* 2016 */       return false;
/*      */     } 
/* 2018 */     if (isCreatingDescriptor()) {
/* 2019 */       enumSetHolder.setName(name);
/* 2020 */       this.builderDescriptor.addAttribute(name, "FlagSet", state, shortDescription, longDescription)
/* 2021 */         .optional(defaultValue.toString())
/* 2022 */         .computable()
/* 2023 */         .setEnum(clazz);
/* 2024 */       return false;
/*      */     } 
/* 2026 */     Objects.requireNonNull(enumSetHolder, "enumSetHolder is null");
/*      */     try {
/* 2028 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 2029 */       enumSetHolder.readJSON(optionalJsonElement, defaultValue, clazz, name, this.builderParameters);
/* 2030 */       trackDynamicHolder((ValueHolder)enumSetHolder);
/* 2031 */       return (optionalJsonElement != null);
/* 2032 */     } catch (Exception e) {
/* 2033 */       addError(e);
/* 2034 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private Schema getObjectSchema(@Nonnull Class<?> classType) {
/* 2045 */     BuilderFactory<Object> factory = this.builderManager.getFactory(classType);
/* 2046 */     Schema subSchema = this.builderSchemaContext.refDefinition(factory);
/* 2047 */     ObjectSchema ref = new ObjectSchema();
/* 2048 */     ref.setProperties(new LinkedHashMap<>());
/* 2049 */     Map<String, Schema> props = ref.getProperties();
/* 2050 */     props.put("Reference", BuilderExpressionDynamic.computableSchema((Schema)new StringSchema()));
/* 2051 */     props.put("Local", BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema()));
/* 2052 */     props.put("$Label", BuilderExpressionDynamic.computableSchema((Schema)new StringSchema()));
/* 2053 */     props.put("Nullable", BuilderExpressionDynamic.computableSchema((Schema)new BooleanSchema()));
/* 2054 */     props.put("Interfaces", BuilderExpressionDynamic.computableSchema((Schema)new ArraySchema((Schema)new StringSchema())));
/*      */     
/* 2056 */     props.put("Modify", BuilderModifier.toSchema(this.builderSchemaContext));
/*      */     
/* 2058 */     Schema comment = new Schema();
/* 2059 */     comment.setDoNotSuggest(true);
/* 2060 */     props.put("Comment", comment);
/* 2061 */     props.put("$Title", comment);
/* 2062 */     props.put("$Comment", comment);
/* 2063 */     props.put("$TODO", comment);
/* 2064 */     props.put("$Author", comment);
/* 2065 */     props.put("$Position", comment);
/* 2066 */     props.put("$FloatingFunctionNodes", comment);
/* 2067 */     props.put("$Groups", comment);
/* 2068 */     props.put("$WorkspaceID", comment);
/* 2069 */     props.put("$NodeEditorMetadata", comment);
/* 2070 */     props.put("$NodeId", comment);
/*      */     
/* 2072 */     ref.setTitle("Object reference");
/* 2073 */     ref.setRequired(new String[] { "Reference" });
/* 2074 */     ref.setAdditionalProperties(false);
/*      */     
/* 2076 */     Schema cond = new Schema();
/* 2077 */     ObjectSchema check = new ObjectSchema();
/* 2078 */     check.setProperties(Map.of("Reference", BuilderExpressionDynamic.computableSchema((Schema)new StringSchema())));
/* 2079 */     check.setRequired(new String[] { "Reference" });
/* 2080 */     cond.setIf((Schema)check);
/* 2081 */     cond.setThen((Schema)ref);
/* 2082 */     cond.setElse(subSchema);
/*      */     
/* 2084 */     return BuilderExpressionDynamic.computableSchema(cond);
/*      */   }
/*      */   
/*      */   public boolean getObject(@Nonnull JsonElement data, String name, @Nonnull BuilderObjectReferenceHelper<?> builderObjectReferenceHelper, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2088 */     if (isCreatingSchema()) {
/* 2089 */       Schema s = getObjectSchema(builderObjectReferenceHelper.getClassType());
/* 2090 */       s.setTitle(name);
/* 2091 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2092 */       this.builderSchema.getProperties().put(name, s);
/* 2093 */       return false;
/*      */     } 
/* 2095 */     if (isCreatingDescriptor()) {
/* 2096 */       this.builderDescriptor.addAttribute(name, "ObjectRef", state, shortDescription, longDescription)
/* 2097 */         .domain(this.builderManager.getCategoryName(builderObjectReferenceHelper.getClassType()))
/* 2098 */         .optional((String)null);
/* 2099 */       return false;
/*      */     } 
/* 2101 */     addQueryKey(name);
/*      */     try {
/* 2103 */       JsonElement element = expectJsonObject(data, name).get(name);
/* 2104 */       if (element != null) {
/* 2105 */         builderObjectReferenceHelper.setLabel(name);
/* 2106 */         this.extraInfo.pushKey(name);
/* 2107 */         builderObjectReferenceHelper.readConfig(element, this.builderManager, this.builderParameters, builderValidationHelper);
/* 2108 */         this.extraInfo.popKey();
/* 2109 */         return true;
/*      */       } 
/* 2111 */     } catch (Exception e) {
/* 2112 */       addError(e);
/*      */     } 
/* 2114 */     return false;
/*      */   }
/*      */   
/*      */   public void requireObject(@Nonnull JsonElement data, String name, @Nonnull BuilderObjectReferenceHelper<?> builderObjectReferenceHelper, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2118 */     if (isCreatingSchema()) {
/* 2119 */       Schema s = getObjectSchema(builderObjectReferenceHelper.getClassType());
/* 2120 */       s.setTitle(name);
/* 2121 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2122 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2125 */     if (isCreatingDescriptor()) {
/* 2126 */       this.builderDescriptor.addAttribute(name, "ObjectRef", state, shortDescription, longDescription)
/* 2127 */         .domain(this.builderManager.getCategoryName(builderObjectReferenceHelper.getClassType()))
/* 2128 */         .required();
/*      */       return;
/*      */     } 
/*      */     try {
/* 2132 */       builderObjectReferenceHelper.setLabel(name);
/* 2133 */       this.extraInfo.pushKey(name);
/* 2134 */       builderObjectReferenceHelper.readConfig(getRequiredJsonElement(data, name), this.builderManager, this.builderParameters, builderValidationHelper);
/* 2135 */       this.extraInfo.popKey();
/* 2136 */     } catch (Exception e) {
/* 2137 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getCodecObject(@Nonnull JsonElement data, String name, @Nonnull BuilderCodecObjectHelper<?> helper, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2142 */     if (isCreatingSchema()) {
/* 2143 */       Schema s = this.builderSchemaContext.refDefinition((SchemaConvertable)helper.codec);
/*      */ 
/*      */       
/* 2146 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2147 */       this.builderSchema.getProperties().put(name, s);
/* 2148 */       return false;
/*      */     } 
/* 2150 */     if (isCreatingDescriptor()) {
/* 2151 */       this.builderDescriptor.addAttribute(name, "CodecObject", state, shortDescription, longDescription)
/* 2152 */         .domain(helper.getClassType().getSimpleName())
/* 2153 */         .optional((String)null);
/* 2154 */       return false;
/*      */     } 
/* 2156 */     addQueryKey(name);
/*      */     try {
/* 2158 */       JsonElement element = expectJsonObject(data, name).get(name);
/* 2159 */       if (element != null) {
/* 2160 */         this.extraInfo.pushKey(name);
/*      */         try {
/* 2162 */           helper.readConfig(element, this.extraInfo);
/* 2163 */         } catch (Exception e) {
/* 2164 */           addError(e);
/*      */         } 
/* 2166 */         this.extraInfo.popKey();
/* 2167 */         return true;
/*      */       } 
/* 2169 */     } catch (Exception e) {
/* 2170 */       addError(e);
/*      */     } 
/* 2172 */     return false;
/*      */   }
/*      */   
/*      */   public void requireCodecObject(@Nonnull JsonElement data, String name, @Nonnull BuilderCodecObjectHelper<?> helper, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2176 */     if (isCreatingSchema()) {
/* 2177 */       Schema s = this.builderSchemaContext.refDefinition((SchemaConvertable)helper.codec);
/* 2178 */       s.setTitle(name);
/* 2179 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2180 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2183 */     if (isCreatingDescriptor()) {
/* 2184 */       this.builderDescriptor.addAttribute(name, "CodecObject", state, shortDescription, longDescription)
/* 2185 */         .domain(helper.getClassType().getSimpleName())
/* 2186 */         .required();
/*      */       return;
/*      */     } 
/* 2189 */     this.extraInfo.pushKey(name);
/*      */     try {
/* 2191 */       helper.readConfig(getRequiredJsonElement(data, name), this.extraInfo);
/* 2192 */     } catch (Exception e) {
/* 2193 */       addError(e);
/*      */     } 
/* 2195 */     this.extraInfo.popKey();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireEmbeddableArray(@Nonnull JsonElement data, String embedTag, @Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper, @Nonnull ArrayValidator arrayValidator, BuilderDescriptorState state, String shortDescription, String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2204 */     if (isCreatingDescriptor()) {
/* 2205 */       this.builderDescriptor.addAttribute(embedTag, "EmbeddableArray", state, shortDescription, longDescription)
/* 2206 */         .domain(this.builderManager.getCategoryName(builderObjectArrayHelper.getClassType()))
/* 2207 */         .validator((Validator)arrayValidator)
/* 2208 */         .required();
/*      */       return;
/*      */     } 
/* 2211 */     if (this.useDefaultsOnly) throw new IllegalArgumentException("An embeddable array can be only used once!"); 
/*      */     try {
/* 2213 */       if (data.isJsonArray()) {
/* 2214 */         builderObjectArrayHelper.readConfig(data, this.builderManager, this.builderParameters, builderValidationHelper);
/* 2215 */         if (!arrayValidator.test(builderObjectArrayHelper)) {
/* 2216 */           throw new IllegalStateException(arrayValidator.errorMessage(builderObjectArrayHelper) + " at " + arrayValidator.errorMessage(builderObjectArrayHelper));
/*      */         }
/* 2218 */         this.useDefaultsOnly = true;
/*      */       } else {
/* 2220 */         requireArray0(data, embedTag, builderObjectArrayHelper, arrayValidator, builderValidationHelper);
/*      */       } 
/* 2222 */     } catch (Exception e) {
/* 2223 */       addError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getArray(@Nonnull JsonElement data, String name, @Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper, ArrayValidator arrayValidator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2232 */     if (isCreatingSchema()) {
/* 2233 */       ArraySchema a = new ArraySchema();
/* 2234 */       Schema s = getObjectSchema(builderObjectArrayHelper.getClassType());
/* 2235 */       a.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2236 */       a.setItem(s);
/* 2237 */       this.builderSchema.getProperties().put(name, a);
/* 2238 */       return false;
/*      */     } 
/* 2240 */     if (isCreatingDescriptor()) {
/* 2241 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 2242 */         .optional((String)null)
/* 2243 */         .validator((Validator)arrayValidator)
/* 2244 */         .domain(this.builderManager.getCategoryName(builderObjectArrayHelper.getClassType()));
/* 2245 */       return false;
/*      */     } 
/*      */     try {
/* 2248 */       JsonElement element = getOptionalJsonElement(data, name);
/* 2249 */       if (element != null) {
/* 2250 */         requireArray0(element, name, builderObjectArrayHelper, arrayValidator, builderValidationHelper);
/* 2251 */         return true;
/*      */       } 
/* 2253 */     } catch (Exception e) {
/* 2254 */       addError(e);
/*      */     } 
/* 2256 */     return false;
/*      */   }
/*      */   
/*      */   public void requireArray(@Nonnull JsonElement data, String name, @Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper, ArrayValidator arrayValidator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2260 */     if (isCreatingSchema()) {
/* 2261 */       ArraySchema a = new ArraySchema();
/* 2262 */       Schema s = getObjectSchema(builderObjectArrayHelper.getClassType());
/* 2263 */       a.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2264 */       a.setItem(s);
/* 2265 */       this.builderSchema.getProperties().put(name, a);
/*      */       return;
/*      */     } 
/* 2268 */     if (isCreatingDescriptor()) {
/* 2269 */       this.builderDescriptor.addAttribute(name, "Array", state, shortDescription, longDescription)
/* 2270 */         .required()
/* 2271 */         .validator((Validator)arrayValidator)
/* 2272 */         .domain(this.builderManager.getCategoryName(builderObjectArrayHelper.getClassType()));
/*      */       return;
/*      */     } 
/*      */     try {
/* 2276 */       requireArray0(getRequiredJsonElement(data, name), name, builderObjectArrayHelper, arrayValidator, builderValidationHelper);
/* 2277 */     } catch (Exception e) {
/* 2278 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void requireArray0(@Nonnull JsonElement data, String name, @Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper, @Nullable ArrayValidator validator, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2283 */     builderObjectArrayHelper.setLabel(name);
/* 2284 */     this.extraInfo.pushKey(name);
/* 2285 */     builderObjectArrayHelper.readConfig(data, this.builderManager, this.builderParameters, builderValidationHelper);
/* 2286 */     this.extraInfo.popKey();
/* 2287 */     if (validator != null && 
/* 2288 */       !validator.test(builderObjectArrayHelper)) {
/* 2289 */       throw new IllegalStateException(validator.errorMessage(builderObjectArrayHelper) + " at " + validator.errorMessage(builderObjectArrayHelper));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void requireArray(@Nonnull JsonElement data, @Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper, ArrayValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 2295 */     if (isCreatingSchema()) {
/* 2296 */       this.builderSchemaRaw = (Schema)new ArraySchema();
/* 2297 */       Schema s = getObjectSchema(builderObjectArrayHelper.getClassType());
/* 2298 */       this.builderSchemaRaw.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2299 */       ((ArraySchema)this.builderSchemaRaw).setItem(s);
/*      */       return;
/*      */     } 
/* 2302 */     if (isCreatingDescriptor()) {
/* 2303 */       this.builderDescriptor.addAttribute(null, "Array", state, shortDescription, longDescription)
/* 2304 */         .domain(this.builderManager.getCategoryName(builderObjectArrayHelper.getClassType()))
/* 2305 */         .validator((Validator)validator)
/* 2306 */         .required();
/*      */       return;
/*      */     } 
/*      */     try {
/* 2310 */       builderObjectArrayHelper.readConfig(data, this.builderManager, this.builderParameters, builderValidationHelper);
/* 2311 */     } catch (Exception e) {
/* 2312 */       addError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requireAsset(@Nonnull JsonElement data, String name, @Nonnull Consumer<String> setter, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2321 */     if (isCreatingSchema()) {
/* 2322 */       StringSchema assetS = new StringSchema();
/* 2323 */       validator.updateSchema(assetS);
/* 2324 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)assetS);
/* 2325 */       s.setTitle(name);
/* 2326 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2327 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2330 */     if (isCreatingDescriptor()) {
/* 2331 */       this.builderDescriptor.addAttribute(name, "Asset", state, shortDescription, longDescription)
/* 2332 */         .required()
/* 2333 */         .domain(validator.getDomain());
/*      */       return;
/*      */     } 
/*      */     try {
/* 2337 */       validateAndSet(expectString(getRequiredJsonElement(data, name), name), validator, setter, name);
/* 2338 */     } catch (Exception e) {
/* 2339 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getAsset(@Nonnull JsonElement data, String name, @Nonnull Consumer<String> setter, String defaultValue, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2344 */     if (isCreatingSchema()) {
/* 2345 */       StringSchema assetS = new StringSchema();
/* 2346 */       validator.updateSchema(assetS);
/* 2347 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)assetS);
/* 2348 */       s.setTitle(name);
/* 2349 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2350 */       this.builderSchema.getProperties().put(name, s);
/* 2351 */       return false;
/*      */     } 
/* 2353 */     if (isCreatingDescriptor()) {
/* 2354 */       this.builderDescriptor.addAttribute(name, "Asset", state, shortDescription, longDescription)
/* 2355 */         .optional(defaultValue)
/* 2356 */         .domain(validator.getDomain());
/* 2357 */       return false;
/*      */     } 
/*      */     try {
/* 2360 */       JsonElement element = getOptionalJsonElement(data, name);
/* 2361 */       boolean haveValue = (element != null);
/* 2362 */       if (haveValue) {
/* 2363 */         defaultValue = expectString(element, name);
/*      */       }
/* 2365 */       validateAndSet(defaultValue, validator, setter, name);
/* 2366 */       return haveValue;
/* 2367 */     } catch (Exception e) {
/* 2368 */       addError(e);
/* 2369 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireAsset(@Nonnull JsonElement data, String name, @Nonnull AssetHolder assetHolder, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2374 */     if (isCreatingSchema()) {
/* 2375 */       StringSchema assetS = new StringSchema();
/* 2376 */       validator.updateSchema(assetS);
/* 2377 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)assetS);
/* 2378 */       s.setTitle(name);
/* 2379 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2380 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2383 */     if (isCreatingDescriptor()) {
/* 2384 */       assetHolder.setName(name);
/* 2385 */       this.builderDescriptor.addAttribute(name, "Asset", state, shortDescription, longDescription)
/* 2386 */         .required()
/* 2387 */         .computable()
/* 2388 */         .domain(validator.getDomain());
/*      */       return;
/*      */     } 
/* 2391 */     Objects.requireNonNull(assetHolder, "assetHolder is null");
/*      */     try {
/* 2393 */       assetHolder.readJSON(getRequiredJsonElement(data, name), validator, name, this.builderParameters);
/* 2394 */       assetHolder.staticValidate();
/* 2395 */       trackDynamicHolder((ValueHolder)assetHolder);
/* 2396 */     } catch (Exception e) {
/* 2397 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getAsset(@Nonnull JsonElement data, String name, @Nonnull AssetHolder assetHolder, String defaultValue, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2402 */     if (isCreatingSchema()) {
/* 2403 */       StringSchema assetS = new StringSchema();
/* 2404 */       validator.updateSchema(assetS);
/* 2405 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)assetS);
/* 2406 */       s.setTitle(name);
/* 2407 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2408 */       this.builderSchema.getProperties().put(name, s);
/* 2409 */       return false;
/*      */     } 
/* 2411 */     if (isCreatingDescriptor()) {
/* 2412 */       assetHolder.setName(name);
/* 2413 */       this.builderDescriptor.addAttribute(name, "Asset", state, shortDescription, longDescription)
/* 2414 */         .optional(defaultValue)
/* 2415 */         .computable()
/* 2416 */         .domain(validator.getDomain());
/* 2417 */       return false;
/*      */     } 
/* 2419 */     Objects.requireNonNull(assetHolder, "assetHolder is null");
/*      */     try {
/* 2421 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 2422 */       assetHolder.readJSON(optionalJsonElement, defaultValue, validator, name, this.builderParameters);
/* 2423 */       assetHolder.staticValidate();
/* 2424 */       trackDynamicHolder((ValueHolder)assetHolder);
/* 2425 */       return (optionalJsonElement != null);
/* 2426 */     } catch (Exception e) {
/* 2427 */       addError(e);
/* 2428 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(String str, @Nullable AssetValidator validator, @Nonnull Consumer<String> setter, String name) {
/* 2433 */     if (validator != null) {
/* 2434 */       validateSingleAsset(str, validator, name);
/*      */     }
/* 2436 */     setter.accept(str);
/*      */   }
/*      */   
/*      */   public static boolean validateAssetList(@Nullable String[] assetList, @Nonnull AssetValidator validator, String attributeName, boolean testExistance) {
/* 2440 */     if (assetList == null) {
/* 2441 */       if (!validator.canListBeEmpty()) {
/* 2442 */         throw new IllegalStateException("Null is not an allowed list of " + validator.getDomain() + " value for attribute \"" + attributeName + "\"");
/*      */       }
/* 2444 */       return true;
/*      */     } 
/*      */     
/* 2447 */     if (assetList.length == 0) {
/* 2448 */       if (!validator.canListBeEmpty()) {
/* 2449 */         throw new IllegalStateException("List of " + validator.getDomain() + " value for attribute \"" + attributeName + "\" must not be empty");
/*      */       }
/* 2451 */       return true;
/*      */     } 
/*      */     
/* 2454 */     for (String asset : assetList) {
/* 2455 */       validateAsset(asset, validator, attributeName, testExistance);
/*      */     }
/* 2457 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean validateAsset(@Nullable String assetName, @Nonnull AssetValidator validator, String attributeName, boolean testExistance) {
/* 2461 */     if (assetName == null) {
/* 2462 */       if (!validator.isNullable()) {
/* 2463 */         throw new SkipSentryException(new IllegalStateException("Null is not an allowed " + validator.getDomain() + " value for attribute \"" + attributeName + "\""));
/*      */       }
/* 2465 */       return true;
/*      */     } 
/*      */     
/* 2468 */     if (assetName.isEmpty()) {
/* 2469 */       if (!validator.canBeEmpty()) {
/* 2470 */         throw new SkipSentryException(new IllegalStateException("Empty string is not an allowed " + validator.getDomain() + " value for attribute \"" + attributeName + "\""));
/*      */       }
/* 2472 */       return true;
/*      */     } 
/*      */     
/* 2475 */     if (!testExistance) return true;
/*      */     
/* 2477 */     if (validator.isMatcher() && StringUtil.isGlobPattern(assetName)) return true;
/*      */     
/* 2479 */     if (!validator.test(assetName)) {
/* 2480 */       throw new SkipSentryException(new IllegalStateException(validator.errorMessage(assetName, attributeName)));
/*      */     }
/*      */     
/* 2483 */     return true;
/*      */   }
/*      */   
/*      */   private void validateSingleAsset(String assetName, @Nonnull AssetValidator validator, String attributeName) {
/*      */     try {
/* 2488 */       if (!validateAsset(assetName, validator, attributeName, true)) {
/* 2489 */         NPCPlugin.get().getLogger().at(Level.WARNING).log("%s in %s: %s", validator.errorMessage(assetName, attributeName), getBreadCrumbs(), this.builderParameters.getFileName());
/*      */       }
/* 2491 */     } catch (IllegalStateException e) {
/* 2492 */       throw new IllegalStateException(e.getMessage() + " in " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAssetArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<String[]> setter, Function<String, String> mapper, String[] defaultValue, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2501 */     if (isCreatingSchema()) {
/* 2502 */       StringSchema assetS = new StringSchema();
/* 2503 */       validator.updateSchema(assetS);
/* 2504 */       ArraySchema a = new ArraySchema((Schema)assetS);
/* 2505 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 2506 */       s.setTitle(name);
/* 2507 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2508 */       this.builderSchema.getProperties().put(name, s);
/* 2509 */       return false;
/*      */     } 
/* 2511 */     if (isCreatingDescriptor()) {
/* 2512 */       this.builderDescriptor.addAttribute(name, "AssetArray", state, shortDescription, longDescription)
/* 2513 */         .optional(defaultArrayToString(defaultValue))
/* 2514 */         .domain(validator.getDomain());
/* 2515 */       return false;
/*      */     } 
/*      */     try {
/*      */       String[] list;
/* 2519 */       JsonElement element = getOptionalJsonElement(data, name);
/*      */       
/* 2521 */       boolean haveValue = (element != null);
/* 2522 */       if (haveValue) {
/* 2523 */         list = expectStringArray(element, mapper, name);
/*      */       } else {
/* 2525 */         list = defaultValue;
/*      */       } 
/* 2527 */       validateAndSet(list, validator, setter, name);
/* 2528 */       return haveValue;
/* 2529 */     } catch (Exception e) {
/* 2530 */       addError(e);
/* 2531 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getAssetArray(@Nonnull JsonElement data, String name, @Nonnull AssetArrayHolder assetHolder, String[] defaultValue, int minLength, int maxLength, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2536 */     if (isCreatingSchema()) {
/* 2537 */       StringSchema assetS = new StringSchema();
/* 2538 */       validator.updateSchema(assetS);
/* 2539 */       ArraySchema a = new ArraySchema((Schema)assetS);
/* 2540 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 2541 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 2542 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 2543 */       s.setTitle(name);
/* 2544 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2545 */       this.builderSchema.getProperties().put(name, s);
/* 2546 */       return false;
/*      */     } 
/* 2548 */     if (isCreatingDescriptor()) {
/* 2549 */       assetHolder.setName(name);
/* 2550 */       this.builderDescriptor.addAttribute(name, "AssetArray", state, shortDescription, longDescription)
/* 2551 */         .optional(defaultArrayToString(defaultValue))
/* 2552 */         .computable()
/* 2553 */         .domain(validator.getDomain());
/* 2554 */       return false;
/*      */     } 
/* 2556 */     Objects.requireNonNull(assetHolder, "assetHolder is null");
/*      */     try {
/* 2558 */       JsonElement optionalJsonElement = getOptionalJsonElement(data, name);
/* 2559 */       assetHolder.readJSON(optionalJsonElement, minLength, maxLength, defaultValue, validator, name, this.builderParameters);
/* 2560 */       assetHolder.staticValidate();
/* 2561 */       trackDynamicHolder((ValueHolder)assetHolder);
/* 2562 */       return (optionalJsonElement != null);
/* 2563 */     } catch (Exception e) {
/* 2564 */       addError(e);
/* 2565 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireAssetArray(@Nonnull JsonElement data, String name, @Nonnull Consumer<String[]> setter, Function<String, String> mapper, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2570 */     if (isCreatingSchema()) {
/* 2571 */       StringSchema assetS = new StringSchema();
/* 2572 */       validator.updateSchema(assetS);
/* 2573 */       ArraySchema a = new ArraySchema((Schema)assetS);
/* 2574 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 2575 */       s.setTitle(name);
/* 2576 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2577 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2580 */     if (isCreatingDescriptor()) {
/* 2581 */       this.builderDescriptor.addAttribute(name, "AssetArray", state, shortDescription, longDescription)
/* 2582 */         .required()
/* 2583 */         .domain(validator.getDomain());
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 2588 */       JsonElement element = getRequiredJsonElement(data, name);
/* 2589 */       validateAndSet(expectStringArray(element, mapper, name), validator, setter, name);
/* 2590 */     } catch (Exception e) {
/* 2591 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void requireAssetArray(@Nonnull JsonElement data, String name, @Nonnull AssetArrayHolder assetHolder, int minLength, int maxLength, @Nonnull AssetValidator validator, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 2596 */     if (isCreatingSchema()) {
/* 2597 */       StringSchema assetS = new StringSchema();
/* 2598 */       validator.updateSchema(assetS);
/* 2599 */       ArraySchema a = new ArraySchema((Schema)assetS);
/* 2600 */       if (minLength != 0) a.setMinItems(Integer.valueOf(minLength)); 
/* 2601 */       if (maxLength != Integer.MAX_VALUE) a.setMaxItems(Integer.valueOf(maxLength)); 
/* 2602 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 2603 */       s.setTitle(name);
/* 2604 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 2605 */       this.builderSchema.getProperties().put(name, s);
/*      */       return;
/*      */     } 
/* 2608 */     if (isCreatingDescriptor()) {
/* 2609 */       assetHolder.setName(name);
/* 2610 */       this.builderDescriptor.addAttribute(name, "AssetArray", state, shortDescription, longDescription)
/* 2611 */         .required()
/* 2612 */         .computable()
/* 2613 */         .domain(validator.getDomain());
/*      */       return;
/*      */     } 
/* 2616 */     Objects.requireNonNull(assetHolder, "assetHolder is null");
/*      */     try {
/* 2618 */       assetHolder.readJSON(getRequiredJsonElement(data, name), minLength, maxLength, validator, name, this.builderParameters);
/* 2619 */       assetHolder.staticValidate();
/* 2620 */       trackDynamicHolder((ValueHolder)assetHolder);
/* 2621 */     } catch (Exception e) {
/* 2622 */       addError(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAndSet(@Nullable String[] assetList, @Nullable AssetValidator validator, @Nonnull Consumer<String[]> setter, String attributeName) {
/* 2627 */     if (validator != null) {
/* 2628 */       if (assetList == null) {
/* 2629 */         if (!validator.canListBeEmpty()) {
/* 2630 */           throw new IllegalStateException("Null is not an allowed list of " + validator.getDomain() + " value for attribute \"" + attributeName + "\" in " + getBreadCrumbs());
/*      */         }
/* 2632 */       } else if (assetList.length == 0) {
/* 2633 */         if (!validator.canListBeEmpty()) {
/* 2634 */           throw new IllegalStateException("List of " + validator.getDomain() + " value for attribute \"" + attributeName + "\" must not be empty in " + getBreadCrumbs());
/*      */         }
/*      */       } else {
/* 2637 */         for (String asset : assetList) {
/* 2638 */           validateSingleAsset(asset, validator, attributeName);
/*      */         }
/*      */       } 
/*      */     }
/* 2642 */     setter.accept(assetList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BuilderDescriptor createDescriptor(@Nonnull Builder<?> builder, String builderName, String categoryName, BuilderManager builderManager, BuilderDescriptorState state, String shortDescription, String longDescription, Set<String> tags) {
/* 2650 */     this.builderDescriptor = new BuilderDescriptor(builderName, categoryName, shortDescription, longDescription, tags, state);
/*      */     try {
/* 2652 */       builder.readConfig((BuilderContext)null, (JsonElement)null, builderManager, this.builderParameters, (BuilderValidationHelper)null);
/* 2653 */       return this.builderDescriptor;
/*      */     } finally {
/* 2655 */       this.builderDescriptor = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isCreatingDescriptor() {
/* 2660 */     return (this.builderDescriptor != null);
/*      */   }
/*      */   
/*      */   protected boolean isCreatingSchema() {
/* 2664 */     return (this.builderSchema != null);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String getSchemaName() {
/* 2670 */     return (this.typeName == null) ? ("NPC:Class:" + 
/* 2671 */       getClass().getSimpleName()) : ("NPC:" + 
/*      */       
/* 2673 */       this.typeName);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Schema toSchema(@Nonnull SchemaContext context) {
/*      */     try {
/* 2680 */       this.builderSchemaContext = context;
/* 2681 */       this.builderSchemaRaw = (Schema)(this.builderSchema = new ObjectSchema());
/* 2682 */       this.builderSchema.setProperties(new LinkedHashMap<>());
/* 2683 */       this.builderSchema.setAdditionalProperties(false);
/*      */ 
/*      */       
/* 2686 */       this.builderDescriptor = new BuilderDescriptor(null, null, null, null, null, null);
/*      */       try {
/* 2688 */         readConfig((BuilderContext)null, (JsonElement)null, BuilderManager.SCHEMA_BUILDER_MANAGER, this.builderParameters, (BuilderValidationHelper)null);
/*      */       } finally {
/* 2690 */         this.builderDescriptor = null;
/*      */       } 
/*      */       
/* 2693 */       Schema comment = new Schema();
/* 2694 */       comment.setDoNotSuggest(true);
/* 2695 */       this.builderSchema.getProperties().put("Comment", comment);
/* 2696 */       this.builderSchema.getProperties().put("$Title", comment);
/* 2697 */       this.builderSchema.getProperties().put("$Comment", comment);
/* 2698 */       this.builderSchema.getProperties().put("$TODO", comment);
/* 2699 */       this.builderSchema.getProperties().put("$Author", comment);
/* 2700 */       this.builderSchema.getProperties().put("$Position", comment);
/* 2701 */       this.builderSchema.getProperties().put("$FloatingFunctionNodes", comment);
/* 2702 */       this.builderSchema.getProperties().put("$Groups", comment);
/* 2703 */       this.builderSchema.getProperties().put("$WorkspaceID", comment);
/* 2704 */       this.builderSchema.getProperties().put("$NodeEditorMetadata", comment);
/* 2705 */       this.builderSchema.getProperties().put("$NodeId", comment);
/*      */       
/* 2707 */       this.builderSchemaRaw.setTitle(this.typeName);
/* 2708 */       this.builderSchemaRaw.setDescription(getLongDescription());
/*      */       
/* 2710 */       return this.builderSchemaRaw;
/*      */     } finally {
/* 2712 */       this.builderSchema = null;
/* 2713 */       this.builderSchemaContext = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final BuilderDescriptor getDescriptor(String builderName, String categoryName, BuilderManager builderManager) {
/* 2719 */     HashSet<String> tags = new HashSet<>();
/* 2720 */     registerTags(tags);
/* 2721 */     return createDescriptor(this, builderName, categoryName, builderManager, getBuilderDescriptorState(), getShortDescription(), getLongDescription(), tags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerTags(@Nonnull Set<String> tags) {
/* 2731 */     String pkg = getClass().getPackageName().replaceFirst("\\.builders$", "");
/* 2732 */     tags.add(pkg.substring(pkg.lastIndexOf('.') + 1));
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
/*      */   protected void validateNotAllStringsEmpty(String attribute1, String string1, String attribute2, String string2) {
/* 2746 */     if (isCreatingDescriptor()) {
/* 2747 */       this.builderDescriptor.addValidator((Validator)StringsNotEmptyValidator.withAttributes(attribute1, attribute2));
/*      */       return;
/*      */     } 
/* 2750 */     if (!StringsNotEmptyValidator.test(string1, string2)) {
/* 2751 */       addError(StringsNotEmptyValidator.errorMessage(string1, attribute1, string2, attribute2, getBreadCrumbs()));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateAtMostOneString(String attribute1, String string1, String attribute2, String string2) {
/* 2756 */     if (isCreatingDescriptor()) {
/* 2757 */       this.builderDescriptor.addValidator((Validator)StringsAtMostOneValidator.withAttributes(attribute1, attribute2));
/*      */       return;
/*      */     } 
/* 2760 */     if (StringsAtMostOneValidator.test(string1, string2)) {
/* 2761 */       addError(StringsAtMostOneValidator.errorMessage(string1, attribute1, string2, attribute2, getBreadCrumbs()));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateOneSetString(String attribute1, String string1, String attribute2, String string2) {
/* 2766 */     if (isCreatingDescriptor()) {
/* 2767 */       this.builderDescriptor.addValidator((Validator)StringsOneSetValidator.withAttributes(attribute1, attribute2));
/*      */       return;
/*      */     } 
/* 2770 */     if (!StringsOneSetValidator.test(string1, string2)) {
/* 2771 */       addError(StringsOneSetValidator.formatErrorMessage(string1, attribute1, string2, attribute2, getBreadCrumbs()));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateOneSetAsset(@Nonnull AssetHolder value1, String attribute2, String string2) {
/* 2776 */     if (isCreatingDescriptor()) {
/* 2777 */       this.builderDescriptor.addValidator((Validator)StringsOneSetValidator.withAttributes(value1.getName(), string2));
/*      */       
/*      */       return;
/*      */     } 
/* 2781 */     if (value1.isStatic()) {
/* 2782 */       validateOneSetString(value1.getName(), value1.get(null), attribute2, string2);
/*      */       
/*      */       return;
/*      */     } 
/* 2786 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           if (!StringsOneSetValidator.test(v1, string2)) {
/*      */             throw new IllegalStateException(StringsOneSetValidator.formatErrorMessage(v1, value1.getName(), string2, attribute2, getBreadCrumbs()));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateOneSetAsset(@Nonnull AssetHolder value1, @Nonnull AssetHolder value2) {
/* 2794 */     if (isCreatingDescriptor()) {
/* 2795 */       this.builderDescriptor.addValidator((Validator)StringsOneSetValidator.withAttributes(value1.getName(), value2.getName()));
/*      */       return;
/*      */     } 
/* 2798 */     if (value1.isStatic()) {
/* 2799 */       validateOneSetAsset(value2, value1.getName(), value1.get(null));
/*      */       return;
/*      */     } 
/* 2802 */     if (value2.isStatic()) {
/* 2803 */       validateOneSetAsset(value1, value2.getName(), value2.get(null));
/*      */       
/*      */       return;
/*      */     } 
/* 2807 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           String s2 = value2.rawGet(executionContext);
/*      */           if (!StringsOneSetValidator.test(v1, s2)) {
/*      */             throw new IllegalStateException(StringsOneSetValidator.formatErrorMessage(v1, value1.getName(), s2, value2.getName(), getBreadCrumbs()));
/*      */           }
/*      */         });
/* 2813 */     value2.addRelationValidator((executionContext, v2) -> {
/*      */           String s1 = value1.rawGet(executionContext);
/*      */           if (!StringsOneSetValidator.test(s1, v2)) {
/*      */             throw new IllegalStateException(StringsOneSetValidator.formatErrorMessage(s1, value1.getName(), v2, value2.getName(), getBreadCrumbs()));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateOneSetAssetArray(@Nonnull AssetArrayHolder value1, String attribute2, String[] value2) {
/* 2822 */     if (isCreatingDescriptor()) {
/* 2823 */       this.builderDescriptor.addValidator((Validator)ArraysOneSetValidator.withAttributes(value1.getName(), attribute2));
/*      */       
/*      */       return;
/*      */     } 
/* 2827 */     if (value1.isStatic()) {
/* 2828 */       if (!ArraysOneSetValidator.validate(value1.get(null), value2)) {
/* 2829 */         addError(ArraysOneSetValidator.formatErrorMessage(value1.getName(), attribute2, getBreadCrumbs()));
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 2834 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           if (!ArraysOneSetValidator.validate(v1, value2)) {
/*      */             throw new IllegalStateException(ArraysOneSetValidator.formatErrorMessage(value1.getName(), attribute2, getBreadCrumbs()));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateOneSetAssetArray(@Nonnull AssetArrayHolder value1, @Nonnull AssetArrayHolder value2) {
/* 2842 */     if (isCreatingDescriptor()) {
/* 2843 */       this.builderDescriptor.addValidator((Validator)ArraysOneSetValidator.withAttributes(value1.getName(), value2.getName()));
/*      */       
/*      */       return;
/*      */     } 
/* 2847 */     if (value1.isStatic()) {
/* 2848 */       validateOneSetAssetArray(value2, value1.getName(), value1.get(null));
/*      */       return;
/*      */     } 
/* 2851 */     if (value2.isStatic()) {
/* 2852 */       validateOneSetAssetArray(value1, value2.getName(), value2.get(null));
/*      */       
/*      */       return;
/*      */     } 
/* 2856 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           String[] s2 = value2.rawGet(executionContext);
/*      */           if (!ArraysOneSetValidator.validate(v1, s2)) {
/*      */             throw new IllegalStateException(ArraysOneSetValidator.formatErrorMessage(value1.getName(), value2.getName(), getBreadCrumbs()));
/*      */           }
/*      */         });
/* 2862 */     value2.addRelationValidator((executionContext, v2) -> {
/*      */           String[] s1 = value1.rawGet(executionContext);
/*      */           if (!ArraysOneSetValidator.validate(s1, v2)) {
/*      */             throw new IllegalStateException(ArraysOneSetValidator.formatErrorMessage(value1.getName(), value2.getName(), getBreadCrumbs()));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected <K> void validateNoDuplicates(Iterable<K> list, String variableName) {
/* 2871 */     NoDuplicatesValidator<K> validator = NoDuplicatesValidator.withAttributes(list, variableName);
/* 2872 */     if (isCreatingDescriptor()) {
/* 2873 */       this.builderDescriptor.addValidator((Validator)validator);
/*      */       return;
/*      */     } 
/* 2876 */     if (!validator.test()) {
/* 2877 */       addError(validator.errorMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateDoubleRelation(String attribute1, double value1, @Nonnull RelationalOperator relation, String attribute2, double value2) {
/* 2886 */     if (isCreatingDescriptor()) {
/* 2887 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, attribute2));
/*      */       return;
/*      */     } 
/* 2890 */     if (!DoubleValidator.compare(value1, relation, value2)) {
/* 2891 */       addError(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Double.valueOf(value1), relation.asText(), attribute2, Double.valueOf(value2), getBreadCrumbs() }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateDoubleRelation(@Nonnull DoubleHolder value1, @Nonnull RelationalOperator relation, String attribute2, double value2) {
/* 2896 */     if (isCreatingDescriptor()) {
/* 2897 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, attribute2));
/*      */       return;
/*      */     } 
/* 2900 */     if (value1.isStatic()) {
/* 2901 */       validateDoubleRelation(value1.getName(), value1.get(null), relation, attribute2, value2);
/*      */     } else {
/* 2903 */       value1.addRelationValidator((executionContext, v1) -> {
/*      */             if (!DoubleValidator.compare(v1, relation, value2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), attribute2, Double.valueOf(value2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateDoubleRelation(String attribute1, double value1, @Nonnull RelationalOperator relation, @Nonnull DoubleHolder value2) {
/* 2912 */     if (isCreatingDescriptor()) {
/* 2913 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 2916 */     if (value2.isStatic()) {
/* 2917 */       validateDoubleRelation(attribute1, value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 2919 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             if (!DoubleValidator.compare(value1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Double.valueOf(value1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateDoubleRelation(@Nonnull DoubleHolder value1, @Nonnull RelationalOperator relation, @Nonnull DoubleHolder value2) {
/* 2928 */     if (isCreatingDescriptor()) {
/* 2929 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 2932 */     if (value1.isStatic()) {
/* 2933 */       validateDoubleRelation(value1.getName(), value1.get(null), relation, value2);
/* 2934 */     } else if (value2.isStatic()) {
/* 2935 */       validateDoubleRelation(value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 2937 */       value1.addRelationValidator((executionContext1, v1) -> {
/*      */             double v2 = value2.rawGet(executionContext1);
/*      */             if (!DoubleValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/* 2943 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             double v1 = value1.rawGet(executionContext);
/*      */             if (!DoubleValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateFloatRelation(String attribute1, float value1, @Nonnull RelationalOperator relation, String attribute2, float value2) {
/* 2957 */     if (isCreatingDescriptor()) {
/* 2958 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, attribute2));
/*      */       return;
/*      */     } 
/* 2961 */     if (!DoubleValidator.compare(value1, relation, value2)) {
/* 2962 */       addError(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Float.valueOf(value1), relation.asText(), attribute2, Float.valueOf(value2), getBreadCrumbs() }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateFloatRelation(@Nonnull FloatHolder value1, @Nonnull RelationalOperator relation, String attribute2, float value2) {
/* 2967 */     if (isCreatingDescriptor()) {
/* 2968 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, attribute2));
/*      */       return;
/*      */     } 
/* 2971 */     if (value1.isStatic()) {
/* 2972 */       validateFloatRelation(value1.getName(), value1.get(null), relation, attribute2, value2);
/*      */     } else {
/* 2974 */       value1.addRelationValidator((executionContext, v1) -> {
/*      */             if (!DoubleValidator.compare(v1, relation, value2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), attribute2, Float.valueOf(value2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateFloatRelation(String attribute1, float value1, @Nonnull RelationalOperator relation, @Nonnull FloatHolder value2) {
/* 2983 */     if (isCreatingDescriptor()) {
/* 2984 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 2987 */     if (value2.isStatic()) {
/* 2988 */       validateFloatRelation(attribute1, value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 2990 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             if (!DoubleValidator.compare(value1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Float.valueOf(value1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateFloatRelation(@Nonnull FloatHolder value1, @Nonnull RelationalOperator relation, @Nonnull FloatHolder value2) {
/* 2999 */     if (isCreatingDescriptor()) {
/* 3000 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 3003 */     if (value1.isStatic()) {
/* 3004 */       validateFloatRelation(value1.getName(), value1.get(null), relation, value2);
/* 3005 */     } else if (value2.isStatic()) {
/* 3006 */       validateFloatRelation(value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 3008 */       value1.addRelationValidator((executionContext1, v1) -> {
/*      */             double v2 = value2.rawGet(executionContext1);
/*      */             if (!DoubleValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/* 3014 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             double v1 = value1.rawGet(executionContext);
/*      */             if (!DoubleValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Double.valueOf(v1), relation.asText(), value2.getName(), Double.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateIntRelation(String attribute1, int value1, @Nonnull RelationalOperator relation, String attribute2, int value2) {
/* 3028 */     if (isCreatingDescriptor()) {
/* 3029 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, attribute2));
/*      */       return;
/*      */     } 
/* 3032 */     if (!IntValidator.compare(value1, relation, value2)) {
/* 3033 */       addError(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Integer.valueOf(value1), relation.asText(), attribute2, Integer.valueOf(value2), getBreadCrumbs() }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void validateIntRelation(@Nonnull IntHolder value1, @Nonnull RelationalOperator relation, String attribute2, int value2) {
/* 3038 */     if (isCreatingDescriptor()) {
/* 3039 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, attribute2));
/*      */       return;
/*      */     } 
/* 3042 */     if (value1.isStatic()) {
/* 3043 */       validateIntRelation(value1.getName(), value1.get(null), relation, attribute2, value2);
/*      */     } else {
/* 3045 */       value1.addRelationValidator((executionContext, v1) -> {
/*      */             if (!IntValidator.compare(v1, relation, value2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Integer.valueOf(v1), relation.asText(), attribute2, Integer.valueOf(value2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateIntRelation(String attribute1, int value1, @Nonnull RelationalOperator relation, @Nonnull IntHolder value2) {
/* 3054 */     if (isCreatingDescriptor()) {
/* 3055 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(attribute1, relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 3058 */     if (value2.isStatic()) {
/* 3059 */       validateIntRelation(attribute1, value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 3061 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             if (!IntValidator.compare(value1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { attribute1, Integer.valueOf(value1), relation.asText(), value2.getName(), Integer.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateIntRelation(@Nonnull IntHolder value1, @Nonnull RelationalOperator relation, @Nonnull IntHolder value2) {
/* 3070 */     if (isCreatingDescriptor()) {
/* 3071 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, value2.getName()));
/*      */       return;
/*      */     } 
/* 3074 */     if (value1.isStatic()) {
/* 3075 */       validateIntRelation(value1.getName(), value1.get(null), relation, value2);
/* 3076 */     } else if (value2.isStatic()) {
/* 3077 */       validateIntRelation(value1, relation, value2.getName(), value2.get(null));
/*      */     } else {
/* 3079 */       value1.addRelationValidator((executionContext1, v1) -> {
/*      */             int v2 = value2.rawGet(executionContext1);
/*      */             if (!IntValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Integer.valueOf(v1), relation.asText(), value2.getName(), Integer.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/* 3085 */       value2.addRelationValidator((executionContext, v2) -> {
/*      */             int v1 = value1.rawGet(executionContext);
/*      */             if (!IntValidator.compare(v1, relation, v2)) {
/*      */               throw new IllegalStateException(String.format("'%s'(=%s) should be %s '%s'(=%s) in %s", new Object[] { value1.getName(), Integer.valueOf(v1), relation.asText(), value2.getName(), Integer.valueOf(v2), getBreadCrumbs() }));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateIntRelationIfBooleanIs(boolean targetValue, boolean actualValue, @Nonnull IntHolder value1, @Nonnull RelationalOperator relation, @Nonnull IntHolder value2) {
/* 3095 */     if (isCreatingDescriptor()) {
/* 3096 */       this.builderDescriptor.addValidator((Validator)AttributeRelationValidator.withAttributes(value1.getName(), relation, value2.getName()));
/*      */       
/*      */       return;
/*      */     } 
/* 3100 */     if (actualValue != targetValue)
/*      */       return; 
/* 3102 */     validateIntRelation(value1, relation, value2);
/*      */   }
/*      */   
/*      */   protected void validateAnyPresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 3106 */     if (isCreatingDescriptor()) {
/* 3107 */       this.builderDescriptor.addValidator((Validator)AnyPresentValidator.withAttributes(attribute1, attribute2));
/*      */       return;
/*      */     } 
/* 3110 */     if (objectHelper1.isPresent() || objectHelper2.isPresent()) {
/*      */       return;
/*      */     }
/* 3113 */     addError(AnyPresentValidator.errorMessage(new String[] { attribute1, attribute2 }) + " in " + AnyPresentValidator.errorMessage(new String[] { attribute1, attribute2 }));
/*      */   }
/*      */   
/*      */   protected void validateAnyPresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2, String attribute3, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 3117 */     if (isCreatingDescriptor()) {
/* 3118 */       this.builderDescriptor.addValidator((Validator)AnyPresentValidator.withAttributes(new String[] { attribute1, attribute2, attribute3 }));
/*      */       return;
/*      */     } 
/* 3121 */     if (objectHelper1.isPresent() || objectHelper2.isPresent() || objectHelper3.isPresent()) {
/*      */       return;
/*      */     }
/* 3124 */     addError(AnyPresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }) + " in " + AnyPresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }));
/*      */   }
/*      */   
/*      */   protected void validateAnyPresent(@Nonnull String[] attributes, @Nonnull BuilderObjectHelper<?>[] objectHelpers) {
/* 3128 */     if (isCreatingDescriptor()) {
/* 3129 */       this.builderDescriptor.addValidator((Validator)AnyPresentValidator.withAttributes(attributes));
/*      */       return;
/*      */     } 
/* 3132 */     if (AnyPresentValidator.test((BuilderObjectHelper[])objectHelpers)) {
/*      */       return;
/*      */     }
/* 3135 */     addError(AnyPresentValidator.errorMessage(attributes) + " in " + AnyPresentValidator.errorMessage(attributes));
/*      */   }
/*      */   
/*      */   protected void validateOnePresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 3139 */     if (isCreatingDescriptor()) {
/* 3140 */       this.builderDescriptor.addValidator((Validator)OnePresentValidator.withAttributes(new String[] { attribute1, attribute2 }));
/*      */       
/*      */       return;
/*      */     } 
/* 3144 */     if (OnePresentValidator.test(objectHelper1, objectHelper2))
/*      */       return; 
/* 3146 */     addError(OnePresentValidator.errorMessage(new String[] { attribute1, attribute2 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2 }) + " in " + OnePresentValidator.errorMessage(new String[] { attribute1, attribute2 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2 }));
/*      */   }
/*      */   
/*      */   protected void validateOnePresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2, String attribute3, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 3150 */     if (isCreatingDescriptor()) {
/* 3151 */       this.builderDescriptor.addValidator((Validator)OnePresentValidator.withAttributes(new String[] { attribute1, attribute2, attribute3 }));
/*      */       
/*      */       return;
/*      */     } 
/* 3155 */     if (OnePresentValidator.test(objectHelper1, objectHelper2, objectHelper3))
/*      */       return; 
/* 3157 */     addError(OnePresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2, objectHelper3 }) + " in " + OnePresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2, objectHelper3 }));
/*      */   }
/*      */   
/*      */   protected void validateOnePresent(@Nonnull String[] attributes, @Nonnull BuilderObjectHelper<?>[] objectHelpers) {
/* 3161 */     if (isCreatingDescriptor()) {
/* 3162 */       this.builderDescriptor.addValidator((Validator)OnePresentValidator.withAttributes(attributes));
/*      */       
/*      */       return;
/*      */     } 
/* 3166 */     if (OnePresentValidator.test((BuilderObjectHelper[])objectHelpers))
/*      */       return; 
/* 3168 */     addError(OnePresentValidator.errorMessage(attributes, (BuilderObjectHelper[])objectHelpers) + " in " + OnePresentValidator.errorMessage(attributes, (BuilderObjectHelper[])objectHelpers));
/*      */   }
/*      */   
/*      */   protected void validateOnePresent(@Nonnull String[] attributes, @Nonnull boolean[] readStatus) {
/* 3172 */     if (isCreatingDescriptor()) {
/* 3173 */       this.builderDescriptor.addValidator((Validator)OnePresentValidator.withAttributes(attributes));
/*      */       
/*      */       return;
/*      */     } 
/* 3177 */     if (OnePresentValidator.test(readStatus))
/*      */       return; 
/* 3179 */     addError(OnePresentValidator.errorMessage(attributes, readStatus) + " in " + OnePresentValidator.errorMessage(attributes, readStatus));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void validateOneOrNonePresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 3184 */     if (isCreatingDescriptor()) {
/* 3185 */       this.builderDescriptor.addValidator((Validator)OneOrNonePresentValidator.withAttributes(new String[] { attribute1, attribute2 }));
/*      */       
/*      */       return;
/*      */     } 
/* 3189 */     if (OneOrNonePresentValidator.test(objectHelper1, objectHelper2))
/*      */       return; 
/* 3191 */     addError(OneOrNonePresentValidator.errorMessage(new String[] { attribute1, attribute2 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2 }) + " in " + OneOrNonePresentValidator.errorMessage(new String[] { attribute1, attribute2 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2 }));
/*      */   }
/*      */   
/*      */   protected void validateOneOrNonePresent(String attribute1, @Nonnull BuilderObjectHelper<?> objectHelper1, String attribute2, @Nonnull BuilderObjectHelper<?> objectHelper2, String attribute3, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 3195 */     if (isCreatingDescriptor()) {
/* 3196 */       this.builderDescriptor.addValidator((Validator)OneOrNonePresentValidator.withAttributes(new String[] { attribute1, attribute2, attribute3 }));
/*      */       
/*      */       return;
/*      */     } 
/* 3200 */     if (OneOrNonePresentValidator.test(objectHelper1, objectHelper2, objectHelper3))
/*      */       return; 
/* 3202 */     addError(OneOrNonePresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2, objectHelper3 }) + " in " + OneOrNonePresentValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }, new BuilderObjectHelper[] { objectHelper1, objectHelper2, objectHelper3 }));
/*      */   }
/*      */   
/*      */   protected void validateOneOrNonePresent(@Nonnull String[] attributes, @Nonnull BuilderObjectHelper<?>[] objectHelpers) {
/* 3206 */     if (isCreatingDescriptor()) {
/* 3207 */       this.builderDescriptor.addValidator((Validator)OneOrNonePresentValidator.withAttributes(attributes));
/*      */       return;
/*      */     } 
/* 3210 */     if (OneOrNonePresentValidator.test((BuilderObjectHelper[])objectHelpers)) {
/*      */       return;
/*      */     }
/* 3213 */     addError(OneOrNonePresentValidator.errorMessage(attributes, (BuilderObjectHelper[])objectHelpers) + " in " + OneOrNonePresentValidator.errorMessage(attributes, (BuilderObjectHelper[])objectHelpers));
/*      */   }
/*      */   
/*      */   protected void validateOneOrNonePresent(@Nonnull String[] attributes, @Nonnull boolean[] readStatus) {
/* 3217 */     if (isCreatingDescriptor()) {
/* 3218 */       this.builderDescriptor.addValidator((Validator)OneOrNonePresentValidator.withAttributes(attributes));
/*      */       return;
/*      */     } 
/* 3221 */     if (OneOrNonePresentValidator.test(readStatus)) {
/*      */       return;
/*      */     }
/* 3224 */     addError(OneOrNonePresentValidator.errorMessage(attributes, readStatus) + " in " + OneOrNonePresentValidator.errorMessage(attributes, readStatus));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void validateExistsIfParameterSet(String parameter, boolean value, String attribute, @Nonnull BuilderObjectHelper<?> objectHelper) {
/* 3229 */     if (isCreatingDescriptor()) {
/* 3230 */       this.builderDescriptor.addValidator((Validator)ExistsIfParameterSetValidator.withAttributes(parameter, attribute));
/*      */       
/*      */       return;
/*      */     } 
/* 3234 */     if (!value)
/* 3235 */       return;  if (objectHelper.isPresent())
/*      */       return; 
/* 3237 */     addError(ExistsIfParameterSetValidator.errorMessage(parameter, attribute) + " in " + ExistsIfParameterSetValidator.errorMessage(parameter, attribute));
/*      */   }
/*      */ 
/*      */   
/*      */   protected <E extends Enum<E> & Supplier<String>> void validateStringIfEnumIs(@Nonnull StringHolder parameter, @Nonnull StringValidator validator, @Nonnull EnumHolder<E> enumParameter, E targetValue) {
/* 3242 */     if (isCreatingDescriptor()) {
/* 3243 */       this.builderDescriptor.addValidator((Validator)ValidateIfEnumIsValidator.withAttributes(parameter.getName(), (Validator)validator, enumParameter.getName(), (Enum)targetValue));
/*      */       
/*      */       return;
/*      */     } 
/* 3247 */     if (enumParameter.isStatic()) {
/* 3248 */       validateStringIfEnumIs(parameter, validator, enumParameter.getName(), enumParameter.get(null), (Enum)targetValue);
/*      */       
/*      */       return;
/*      */     } 
/* 3252 */     parameter.addRelationValidator((context, s) -> {
/*      */           Enum enum_ = enumParameter.rawGet(context);
/*      */           if (enum_ != targetValue) {
/*      */             return;
/*      */           }
/*      */           if (!validator.test(s))
/*      */             throw new IllegalStateException(validator.errorMessage(s, parameter.getName()) + " if " + validator.errorMessage(s, parameter.getName()) + " is " + enumParameter.getName() + " in " + String.valueOf(targetValue)); 
/*      */         });
/* 3260 */     enumParameter.addEnumRelationValidator((context, e) -> {
/*      */           if (e != targetValue) {
/*      */             return;
/*      */           }
/*      */           String stringValue = parameter.rawGet(context);
/*      */           if (!validator.test(stringValue))
/*      */             throw new IllegalStateException(validator.errorMessage(stringValue, parameter.getName()) + " if " + validator.errorMessage(stringValue, parameter.getName()) + " is " + enumParameter.getName() + " in " + String.valueOf(targetValue)); 
/*      */         });
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E> & Supplier<String>> void validateStringIfEnumIs(@Nonnull StringHolder parameter, @Nonnull StringValidator validator, String enumName, E targetValue, E enumValue) {
/* 3271 */     if (isCreatingDescriptor()) {
/* 3272 */       this.builderDescriptor.addValidator((Validator)ValidateIfEnumIsValidator.withAttributes(parameter.getName(), (Validator)validator, enumName, (Enum)targetValue));
/*      */       
/*      */       return;
/*      */     } 
/* 3276 */     if (targetValue != enumValue)
/*      */       return; 
/* 3278 */     if (parameter.isStatic()) {
/* 3279 */       String value = parameter.get(null);
/* 3280 */       if (!validator.test(value)) {
/* 3281 */         addError(validator.errorMessage(value, parameter.getName()) + " if " + validator.errorMessage(value, parameter.getName()) + " is " + enumName + " in " + String.valueOf(targetValue));
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 3286 */     parameter.addRelationValidator((context, s) -> {
/*      */           if (!validator.test(s)) {
/*      */             throw new IllegalStateException(validator.errorMessage(s, parameter.getName()) + " if " + validator.errorMessage(s, parameter.getName()) + " is " + enumName + " in " + String.valueOf(targetValue));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E> & Supplier<String>> void validateAssetIfEnumIs(@Nonnull AssetHolder parameter, @Nonnull AssetValidator validator, @Nonnull EnumHolder<E> enumParameter, E targetValue) {
/* 3294 */     if (isCreatingDescriptor()) {
/* 3295 */       this.builderDescriptor.addValidator((Validator)ValidateAssetIfEnumIsValidator.withAttributes(parameter.getName(), validator, enumParameter.getName(), (Enum)targetValue));
/*      */       
/*      */       return;
/*      */     } 
/* 3299 */     if (enumParameter.isStatic()) {
/* 3300 */       validateAssetIfEnumIs(parameter, validator, enumParameter.getName(), enumParameter.get(null), (Enum)targetValue);
/*      */       
/*      */       return;
/*      */     } 
/* 3304 */     parameter.addRelationValidator((context, s) -> {
/*      */           Enum enum_ = enumParameter.rawGet(context);
/*      */           if (enum_ != targetValue) {
/*      */             return;
/*      */           }
/*      */           if (!validator.test(s))
/*      */             throw new IllegalStateException(validator.errorMessage(s, parameter.getName()) + " if " + validator.errorMessage(s, parameter.getName()) + " is " + enumParameter.getName() + " in " + String.valueOf(targetValue)); 
/*      */         });
/* 3312 */     enumParameter.addEnumRelationValidator((context, e) -> {
/*      */           if (e != targetValue)
/*      */             return; 
/*      */           String stringValue = parameter.rawGet(context);
/*      */           if (!validator.test(stringValue))
/*      */             throw new IllegalStateException(validator.errorMessage(stringValue, parameter.getName()) + " if " + validator.errorMessage(stringValue, parameter.getName()) + " is " + enumParameter.getName() + " in " + String.valueOf(targetValue)); 
/*      */         });
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E> & Supplier<String>> void validateAssetIfEnumIs(@Nonnull AssetHolder parameter, @Nonnull AssetValidator validator, String enumName, E targetValue, E enumValue) {
/* 3322 */     if (isCreatingDescriptor()) {
/* 3323 */       this.builderDescriptor.addValidator((Validator)ValidateAssetIfEnumIsValidator.withAttributes(parameter.getName(), validator, enumName, (Enum)targetValue));
/*      */       
/*      */       return;
/*      */     } 
/* 3327 */     if (targetValue != enumValue)
/*      */       return; 
/* 3329 */     if (parameter.isStatic()) {
/* 3330 */       String value = parameter.get(null);
/* 3331 */       if (!validator.test(value)) {
/* 3332 */         throw new IllegalStateException(validator.errorMessage(value, parameter.getName()) + " if " + validator.errorMessage(value, parameter.getName()) + " is " + enumName + " in " + String.valueOf(targetValue));
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 3337 */     parameter.addRelationValidator((context, s) -> {
/*      */           if (!validator.test(s)) {
/*      */             throw new IllegalStateException(validator.errorMessage(s, parameter.getName()) + " if " + validator.errorMessage(s, parameter.getName()) + " is " + enumName + " in " + String.valueOf(targetValue));
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   protected void validateAny(String attribute1, boolean value1, String attribute2, boolean value2) {
/* 3346 */     if (isCreatingDescriptor()) {
/* 3347 */       this.builderDescriptor.addValidator((Validator)AnyBooleanValidator.withAttributes(attribute1, attribute2));
/*      */       return;
/*      */     } 
/* 3350 */     if (value1 || value2) {
/*      */       return;
/*      */     }
/* 3353 */     addError(AnyBooleanValidator.errorMessage(new String[] { attribute1, attribute2 }) + " in " + AnyBooleanValidator.errorMessage(new String[] { attribute1, attribute2 }));
/*      */   }
/*      */   
/*      */   protected void validateAny(@Nonnull BooleanHolder value1, @Nonnull BooleanHolder value2) {
/* 3357 */     if (isCreatingDescriptor()) {
/* 3358 */       this.builderDescriptor.addValidator((Validator)AnyBooleanValidator.withAttributes(value1.getName(), value2.getName()));
/*      */       
/*      */       return;
/*      */     } 
/* 3362 */     if (value1.isStatic()) {
/* 3363 */       boolean v1 = value1.get(null);
/* 3364 */       if (!v1) validateAny(value2, value1.getName(), v1);
/*      */       
/*      */       return;
/*      */     } 
/* 3368 */     if (value2.isStatic()) {
/* 3369 */       boolean v2 = value2.get(null);
/* 3370 */       if (!v2) validateAny(value1, value2.getName(), v2);
/*      */       
/*      */       return;
/*      */     } 
/* 3374 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           boolean v2 = value2.rawGet(executionContext);
/*      */           if (!v1.booleanValue() && !v2) {
/*      */             throw new IllegalStateException(AnyBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }) + " in " + AnyBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }));
/*      */           }
/*      */         });
/* 3380 */     value2.addRelationValidator((executionContext, v2) -> {
/*      */           boolean v1 = value1.rawGet(executionContext);
/*      */           if (!v1 && !v2.booleanValue()) {
/*      */             throw new IllegalStateException(AnyBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }) + " in " + AnyBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateAny(@Nonnull BooleanHolder value1, String attribute2, boolean value2) {
/* 3389 */     if (isCreatingDescriptor()) {
/* 3390 */       this.builderDescriptor.addValidator((Validator)AnyBooleanValidator.withAttributes(value1.getName(), attribute2));
/*      */       
/*      */       return;
/*      */     } 
/* 3394 */     if (value2)
/*      */       return; 
/* 3396 */     if (value1.isStatic()) {
/* 3397 */       if (value1.get(null))
/* 3398 */         return;  addError(AnyBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }) + " in " + AnyBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }));
/*      */     } 
/*      */     
/* 3401 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           if (!v1.booleanValue()) {
/*      */             throw new IllegalStateException(AnyBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }) + " in " + AnyBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateAny(String attribute1, boolean value1, String attribute2, boolean value2, String attribute3, boolean value3) {
/* 3409 */     if (isCreatingDescriptor()) {
/* 3410 */       this.builderDescriptor.addValidator((Validator)AnyBooleanValidator.withAttributes(new String[] { attribute1, attribute2, attribute3 }));
/*      */       return;
/*      */     } 
/* 3413 */     if (value1 || value2 || value3) {
/*      */       return;
/*      */     }
/* 3416 */     addError(AnyBooleanValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }) + " in " + AnyBooleanValidator.errorMessage(new String[] { attribute1, attribute2, attribute3 }));
/*      */   }
/*      */   
/*      */   protected void validateAny(@Nonnull String[] attributes, @Nonnull boolean[] values) {
/* 3420 */     if (isCreatingDescriptor()) {
/* 3421 */       this.builderDescriptor.addValidator((Validator)AnyBooleanValidator.withAttributes(attributes));
/*      */       return;
/*      */     } 
/* 3424 */     if (AnyBooleanValidator.test(values)) {
/*      */       return;
/*      */     }
/* 3427 */     addError(AnyBooleanValidator.errorMessage(attributes) + " in " + AnyBooleanValidator.errorMessage(attributes));
/*      */   }
/*      */   
/*      */   protected void validateAtMostOne(@Nonnull BooleanHolder value1, @Nonnull BooleanHolder value2) {
/* 3431 */     if (isCreatingDescriptor()) {
/* 3432 */       this.builderDescriptor.addValidator((Validator)AtMostOneBooleanValidator.withAttributes(value1.getName(), value2.getName()));
/*      */       
/*      */       return;
/*      */     } 
/* 3436 */     if (value1.isStatic()) {
/* 3437 */       boolean v1 = value1.get(null);
/* 3438 */       if (v1) validateAtMostOne(value2, value1.getName(), v1);
/*      */       
/*      */       return;
/*      */     } 
/* 3442 */     if (value2.isStatic()) {
/* 3443 */       boolean v2 = value2.get(null);
/* 3444 */       if (v2) validateAtMostOne(value1, value2.getName(), v2);
/*      */       
/*      */       return;
/*      */     } 
/* 3448 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           boolean v2 = value2.rawGet(executionContext);
/*      */           if (v1.booleanValue() && v2) {
/*      */             throw new IllegalStateException(AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }) + " in " + AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }));
/*      */           }
/*      */         });
/* 3454 */     value2.addRelationValidator((executionContext, v2) -> {
/*      */           boolean v1 = value1.rawGet(executionContext);
/*      */           if (v1 && v2.booleanValue()) {
/*      */             throw new IllegalStateException(AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }) + " in " + AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), value2.getName() }));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateAtMostOne(@Nonnull BooleanHolder value1, String attribute2, boolean value2) {
/* 3463 */     if (isCreatingDescriptor()) {
/* 3464 */       this.builderDescriptor.addValidator((Validator)AtMostOneBooleanValidator.withAttributes(value1.getName(), attribute2));
/*      */       
/*      */       return;
/*      */     } 
/* 3468 */     if (!value2)
/*      */       return; 
/* 3470 */     if (value1.isStatic() && 
/* 3471 */       value1.get(null)) {
/* 3472 */       addError(AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }) + " in " + AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }));
/*      */     }
/*      */ 
/*      */     
/* 3476 */     value1.addRelationValidator((executionContext, v1) -> {
/*      */           if (v1.booleanValue()) {
/*      */             throw new IllegalStateException(AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }) + " in " + AtMostOneBooleanValidator.errorMessage(new String[] { value1.getName(), attribute2 }));
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void validateBooleanImplicationAnyAntecedent(String[] attributes1, @Nonnull boolean[] values1, boolean antecedentState, String[] attributes2, @Nonnull boolean[] values2, boolean consequentState) {
/* 3484 */     validateBooleanImplication(attributes1, values1, antecedentState, attributes2, values2, consequentState, true);
/*      */   }
/*      */   
/*      */   protected void validateBooleanImplicationAllAntecedents(String[] attributes1, @Nonnull boolean[] values1, boolean antecedentState, String[] attributes2, @Nonnull boolean[] values2, boolean consequentState) {
/* 3488 */     validateBooleanImplication(attributes1, values1, antecedentState, attributes2, values2, consequentState, false);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected ToIntFunction<BuilderSupport> requireStringValueStoreParameter(String parameter, ValueStoreValidator.UseType useType) {
/* 3494 */     if (this.valueStoreUsages == null) this.valueStoreUsages = (List<ValueStoreValidator.ValueUsage>)new ObjectArrayList(); 
/* 3495 */     this.valueStoreUsages.add(new ValueStoreValidator.ValueUsage(parameter, ValueStore.Type.String, useType, this));
/* 3496 */     return support -> support.getValueStoreStringSlot(parameter);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected ToIntFunction<BuilderSupport> requireIntValueStoreParameter(String parameter, ValueStoreValidator.UseType useType) {
/* 3501 */     if (this.valueStoreUsages == null) this.valueStoreUsages = (List<ValueStoreValidator.ValueUsage>)new ObjectArrayList(); 
/* 3502 */     this.valueStoreUsages.add(new ValueStoreValidator.ValueUsage(parameter, ValueStore.Type.Int, useType, this));
/* 3503 */     return support -> support.getValueStoreIntSlot(parameter);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected ToIntFunction<BuilderSupport> requireDoubleValueStoreParameter(String parameter, ValueStoreValidator.UseType useType) {
/* 3508 */     if (this.valueStoreUsages == null) this.valueStoreUsages = (List<ValueStoreValidator.ValueUsage>)new ObjectArrayList(); 
/* 3509 */     this.valueStoreUsages.add(new ValueStoreValidator.ValueUsage(parameter, ValueStore.Type.Double, useType, this));
/* 3510 */     return support -> support.getValueStoreDoubleSlot(parameter);
/*      */   }
/*      */   
/*      */   private void validateBooleanImplication(String[] attributes1, @Nonnull boolean[] values1, boolean antecedentState, String[] attributes2, @Nonnull boolean[] values2, boolean consequentState, boolean anyAntecedent) {
/* 3514 */     BooleanImplicationValidator validator = BooleanImplicationValidator.withAttributes(attributes1, antecedentState, attributes2, consequentState, anyAntecedent);
/* 3515 */     if (isCreatingDescriptor()) {
/* 3516 */       this.builderDescriptor.addValidator((Validator)validator);
/*      */       return;
/*      */     } 
/* 3519 */     if (validator.test(values1, values2)) {
/*      */       return;
/*      */     }
/* 3522 */     addError(validator.errorMessage() + " in " + validator.errorMessage());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void provideFeature(@Nonnull Feature feature) {
/* 3530 */     provideFeatureOrParameters((ProviderEvaluator)new UnconditionalFeatureProviderEvaluator(feature));
/*      */   }
/*      */   
/*      */   protected void overrideParameters(@Nonnull String[] parameters, @Nonnull ParameterType... types) {
/* 3534 */     if (!isCreatingDescriptor() && this.evaluatorHelper.isDisallowParameterProviders())
/* 3535 */       return;  provideFeatureOrParameters((ProviderEvaluator)new UnconditionalParameterProviderEvaluator(parameters, types));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preventParameterOverride() {
/* 3543 */     if (isCreatingDescriptor())
/* 3544 */       return;  this.evaluatorHelper.disallowParameterProviders();
/*      */   }
/*      */   
/*      */   private void provideFeatureOrParameters(ProviderEvaluator evaluator) {
/* 3548 */     if (isCreatingDescriptor()) {
/* 3549 */       this.builderDescriptor.addProviderEvaluator(evaluator);
/*      */       return;
/*      */     } 
/* 3552 */     this.evaluatorHelper.add(evaluator);
/*      */   }
/*      */   
/*      */   protected void provideFeature(@Nonnull EnumSet<Feature> feature) {
/* 3556 */     feature.forEach(this::provideFeature);
/*      */   }
/*      */   
/*      */   protected void requireFeature(@Nonnull EnumSet<Feature> feature) {
/* 3560 */     requireFeature((RequiredFeatureValidator)RequiresOneOfFeaturesValidator.withFeatures(feature));
/*      */   }
/*      */   
/*      */   protected <E extends Enum<E> & Supplier<String>> void requireFeatureIf(String enumName, E targetValue, E enumValue, @Nonnull EnumSet<Feature> feature) {
/* 3564 */     if (isCreatingDescriptor()) {
/* 3565 */       this.builderDescriptor.addValidator((Validator)RequiresFeatureIfEnumValidator.withAttributes(enumName, (Enum)targetValue, feature));
/*      */       
/*      */       return;
/*      */     } 
/* 3569 */     if (targetValue != enumValue)
/*      */       return; 
/* 3571 */     if (this.evaluatorHelper.belongsToFeatureRequiringComponent()) {
/* 3572 */       this.evaluatorHelper.addComponentRequirementValidator((helper, executionContext) -> validateRequiresFeatureIf(enumName, targetValue, enumValue, feature, helper));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3578 */     if (RequiresFeatureIfEnumValidator.staticValidate(this.evaluatorHelper, feature, (Enum)targetValue, (Enum)enumValue))
/*      */       return; 
/* 3580 */     if (this.evaluatorHelper.requiresProviderReferenceEvaluation()) {
/* 3581 */       this.evaluatorHelper.addProviderReferenceValidator((manager, context) -> {
/*      */             resolveFeatureProviderReverences(manager);
/*      */             
/*      */             validateRequiresFeatureIf(enumName, targetValue, enumValue, feature, this.evaluatorHelper);
/*      */           });
/*      */       return;
/*      */     } 
/* 3588 */     String[] description = getDescriptionArray(feature);
/* 3589 */     addError(String.format("If %s is %s, one of %s must be provided at %s", new Object[] { enumName, targetValue, String.join(", ", (CharSequence[])description), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   protected void requireFeatureIf(String attribute, boolean requiredValue, boolean value, @Nonnull EnumSet<Feature> feature) {
/* 3593 */     if (isCreatingDescriptor()) {
/* 3594 */       this.builderDescriptor.addValidator((Validator)RequiresFeatureIfValidator.withAttributes(attribute, requiredValue, feature));
/*      */       
/*      */       return;
/*      */     } 
/* 3598 */     if (this.evaluatorHelper.belongsToFeatureRequiringComponent()) {
/* 3599 */       this.evaluatorHelper.addComponentRequirementValidator((helper, executionContext) -> validateRequiresFeatureIf(attribute, requiredValue, value, feature, helper));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3605 */     if (RequiresFeatureIfValidator.staticValidate(this.evaluatorHelper, feature, requiredValue, value))
/*      */       return; 
/* 3607 */     if (this.evaluatorHelper.requiresProviderReferenceEvaluation()) {
/* 3608 */       this.evaluatorHelper.addProviderReferenceValidator((manager, context) -> {
/*      */             resolveFeatureProviderReverences(manager);
/*      */             
/*      */             validateRequiresFeatureIf(attribute, requiredValue, value, feature, this.evaluatorHelper);
/*      */           });
/*      */       return;
/*      */     } 
/* 3615 */     String[] description = getDescriptionArray(feature);
/* 3616 */     addError(String.format("If %s is %s, one of %s must be provided at %s", new Object[] { attribute, requiredValue ? "set" : "not set", String.join(", ", (CharSequence[])description), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   protected void requireFeatureIf(@Nonnull BooleanHolder parameter, boolean requiredValue, @Nonnull EnumSet<Feature> feature) {
/* 3620 */     if (isCreatingDescriptor()) {
/* 3621 */       this.builderDescriptor.addValidator((Validator)RequiresFeatureIfValidator.withAttributes(parameter.getName(), requiredValue, feature));
/*      */       
/*      */       return;
/*      */     } 
/* 3625 */     if (this.evaluatorHelper.belongsToFeatureRequiringComponent()) {
/* 3626 */       this.evaluatorHelper.addComponentRequirementValidator((helper, executionContext) -> validateRequiresFeatureIf(parameter.getName(), requiredValue, parameter.get(executionContext), feature, helper));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3632 */     if (parameter.isStatic() && RequiresFeatureIfValidator.staticValidate(this.evaluatorHelper, feature, requiredValue, parameter.get(null))) {
/*      */       return;
/*      */     }
/*      */     
/* 3636 */     if (this.evaluatorHelper.requiresProviderReferenceEvaluation()) {
/* 3637 */       this.evaluatorHelper.addProviderReferenceValidator((manager, context) -> {
/*      */             resolveFeatureProviderReverences(manager);
/*      */             
/*      */             validateRequiresFeatureIf(parameter.getName(), requiredValue, parameter.rawGet(context), feature, this.evaluatorHelper);
/*      */           });
/*      */       return;
/*      */     } 
/* 3644 */     parameter.addRelationValidator((context, value) -> validateRequiresFeatureIf(parameter.getName(), requiredValue, value.booleanValue(), feature, this.evaluatorHelper));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasOverriddenParameter(String parameter, ParameterType type, @Nonnull FeatureEvaluatorHelper helper) {
/* 3650 */     for (ProviderEvaluator provider : helper.getProviders()) {
/* 3651 */       if (provider instanceof ParameterProviderEvaluator && ((ParameterProviderEvaluator)provider).hasParameter(parameter, type)) return true; 
/*      */     } 
/* 3653 */     return false;
/*      */   }
/*      */   
/*      */   private void validateOverriddenParameter(String parameter, @Nonnull ParameterType type, @Nonnull FeatureEvaluatorHelper helper) {
/* 3657 */     if (hasOverriddenParameter(parameter, type, helper))
/*      */       return; 
/* 3659 */     throw new IllegalStateException(String.format("Parameter %s is missing and either not provided by a sensor, or provided with the wrong parameter type (expected %s) in context %s", new Object[] { parameter, type.get(), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   private <E extends Enum<E> & Supplier<String>> void validateRequiresFeatureIf(String attribute, E requiredValue, E value, @Nonnull EnumSet<Feature> feature, @Nonnull FeatureEvaluatorHelper helper) {
/* 3663 */     if (RequiresFeatureIfEnumValidator.staticValidate(helper, feature, (Enum)requiredValue, (Enum)value))
/*      */       return; 
/* 3665 */     String[] description = getDescriptionArray(feature);
/* 3666 */     throw new IllegalStateException(String.format("If %s is %s, one of %s must be provided at %s", new Object[] { attribute, requiredValue, String.join(", ", description), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   private void validateRequiresFeatureIf(String attribute, boolean requiredValue, boolean value, @Nonnull EnumSet<Feature> feature, @Nonnull FeatureEvaluatorHelper helper) {
/* 3670 */     if (RequiresFeatureIfValidator.staticValidate(helper, feature, requiredValue, value))
/*      */       return; 
/* 3672 */     String[] description = getDescriptionArray(feature);
/* 3673 */     throw new IllegalStateException(String.format("If %s is %s, one of %s must be provided at %s", new Object[] { attribute, requiredValue ? "set" : "not set", String.join(", ", description), getBreadCrumbs() }));
/*      */   }
/*      */   
/*      */   private void requireFeature(@Nonnull RequiredFeatureValidator validator) {
/* 3677 */     if (isCreatingDescriptor()) {
/* 3678 */       this.builderDescriptor.addValidator((Validator)validator);
/*      */       
/*      */       return;
/*      */     } 
/* 3682 */     if (this.evaluatorHelper.belongsToFeatureRequiringComponent()) {
/* 3683 */       this.evaluatorHelper.addComponentRequirementValidator((helper, executionContext) -> {
/*      */             if (validator.validate(helper)) {
/*      */               return;
/*      */             }
/*      */             throw new IllegalStateException(validator.getErrorMessage(getBreadCrumbs()));
/*      */           });
/*      */       return;
/*      */     } 
/* 3691 */     if (validator.validate(this.evaluatorHelper))
/*      */       return; 
/* 3693 */     if (this.evaluatorHelper.requiresProviderReferenceEvaluation()) {
/* 3694 */       this.evaluatorHelper.addProviderReferenceValidator((manager, context) -> {
/*      */             resolveFeatureProviderReverences(manager);
/*      */             if (validator.validate(this.evaluatorHelper)) {
/*      */               return;
/*      */             }
/*      */             throw new IllegalStateException(validator.getErrorMessage(getBreadCrumbs()));
/*      */           });
/*      */       return;
/*      */     } 
/* 3703 */     addError(validator.getErrorMessage(getBreadCrumbs()));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static String[] getDescriptionArray(@Nonnull EnumSet<Feature> feature) {
/* 3708 */     String[] description = new String[feature.size()];
/* 3709 */     Feature[] featureArray = (Feature[])feature.toArray(x$0 -> new Feature[x$0]);
/* 3710 */     for (int i = 0; i < featureArray.length; i++) {
/* 3711 */       description[i] = featureArray[i].get();
/*      */     }
/* 3713 */     return description;
/*      */   }
/*      */   
/*      */   private void resolveFeatureProviderReverences(BuilderManager manager) {
/* 3717 */     for (ProviderEvaluator providerEvaluator : this.evaluatorHelper.getProviders()) {
/* 3718 */       providerEvaluator.resolveReferences(manager);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void registerStateSensor(String name, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/* 3723 */     if (isCreatingDescriptor())
/* 3724 */       return;  this.stateHelper.getAndPutSensorIndex(name, subState, setter);
/* 3725 */     getParent().setCurrentStateName(name);
/*      */   }
/*      */   
/*      */   protected void registerStateSetter(String name, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/* 3729 */     if (isCreatingDescriptor())
/* 3730 */       return;  this.stateHelper.getAndPutSetterIndex(name, subState, setter);
/*      */   }
/*      */   
/*      */   protected void registerStateRequirer(String name, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/* 3734 */     if (isCreatingDescriptor())
/* 3735 */       return;  this.stateHelper.getAndPutStateRequirerIndex(name, subState, setter);
/*      */   }
/*      */   
/*      */   protected void validateIsComponent() {
/* 3739 */     if (isCreatingDescriptor()) {
/* 3740 */       this.builderDescriptor.addValidator((Validator)ComponentOnlyValidator.get());
/*      */       return;
/*      */     } 
/* 3743 */     if (!isComponent()) {
/* 3744 */       addError(String.format("Element not valid outside of component at: %s", new Object[] { getBreadCrumbs() }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void requireStateString(@Nonnull JsonElement data, String name, boolean componentAllowed, @Nonnull TriConsumer<String, String, Boolean> setter, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 3749 */     StateStringValidator validator = StateStringValidator.get();
/* 3750 */     requireString(data, name, v -> {  }(StringValidator)validator, state, shortDescription, longDescription);
/*      */ 
/*      */     
/* 3753 */     if (isCreatingDescriptor())
/*      */       return; 
/* 3755 */     String mainState = validator.hasMainState() ? validator.getMainState() : null;
/* 3756 */     String subState = validator.hasSubState() ? validator.getSubState() : null;
/*      */     
/* 3758 */     if (this.stateHelper.isComponent()) {
/*      */       
/* 3760 */       if (!componentAllowed) {
/* 3761 */         addError(String.format("Components not supported for state setter %s at %s", new Object[] { subState, getBreadCrumbs() }));
/*      */       }
/* 3763 */       if (!this.stateHelper.hasDefaultLocalState()) {
/* 3764 */         addError("Component with local states must define a 'DefaultState' at the top of the file");
/*      */       }
/* 3766 */       if (mainState != null) {
/* 3767 */         addError(String.format("Components must not contain references to main states (%s) at %s", new Object[] { mainState, getBreadCrumbs() }));
/*      */       }
/* 3769 */       setter.accept(subState, null, Boolean.valueOf(false));
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3774 */     if (mainState == null) mainState = this.stateHelper.getCurrentParentState();
/*      */ 
/*      */     
/* 3777 */     boolean isDefaultSubState = false;
/* 3778 */     if (subState == null) {
/* 3779 */       subState = this.stateHelper.getDefaultSubState();
/* 3780 */       isDefaultSubState = true;
/*      */     } 
/*      */ 
/*      */     
/* 3784 */     if (mainState == null) {
/* 3785 */       addError(String.format("Substate %s does not have a specified main state at %s", new Object[] { subState, getBreadCrumbs() }));
/*      */     }
/*      */     
/* 3788 */     setter.accept(mainState, subState, Boolean.valueOf(isDefaultSubState));
/*      */   }
/*      */   
/*      */   protected boolean getExistentStateSet(@Nonnull JsonElement data, String name, @Nonnull Consumer<Int2ObjectMap<IntSet>> setter, @Nonnull StateMappingHelper stateHelper, BuilderDescriptorState state, String shortDescription, @Nullable String longDescription) {
/* 3792 */     StateStringValidator validator = StateStringValidator.requireMainState();
/* 3793 */     if (isCreatingSchema()) {
/* 3794 */       ArraySchema a = new ArraySchema();
/* 3795 */       a.setItem((Schema)new StringSchema());
/* 3796 */       Schema s = BuilderExpressionDynamic.computableSchema((Schema)a);
/* 3797 */       s.setTitle(name);
/* 3798 */       s.setDescription((longDescription == null) ? shortDescription : longDescription);
/* 3799 */       this.builderSchema.getProperties().put(name, s);
/* 3800 */       return false;
/*      */     } 
/* 3802 */     if (isCreatingDescriptor()) {
/* 3803 */       this.builderDescriptor.addAttribute(name, "StringList", state, shortDescription, longDescription)
/* 3804 */         .required()
/* 3805 */         .validator((Validator)validator);
/* 3806 */       return false;
/*      */     } 
/*      */     
/*      */     try {
/* 3810 */       JsonElement element = getOptionalJsonElement(data, name);
/* 3811 */       if (element == null) {
/* 3812 */         setter.accept(null);
/* 3813 */         return false;
/*      */       } 
/*      */       
/* 3816 */       String[][] strings = new String[1][1];
/* 3817 */       validateAndSet(expectStringArray(element, (Function<String, String>)null, name), (StringArrayValidator)StringArrayNoEmptyStringsValidator.get(), s -> strings[0] = s, name);
/* 3818 */       String[] stringStates = strings[0];
/* 3819 */       Int2ObjectOpenHashMap<IntSet> stateSets = new Int2ObjectOpenHashMap();
/* 3820 */       for (String stringState : stringStates) {
/* 3821 */         if (!validator.test(stringState)) {
/* 3822 */           throw new IllegalStateException(validator.errorMessage(stringState));
/*      */         }
/*      */         
/* 3825 */         String subState = validator.hasSubState() ? validator.getSubState() : stateHelper.getDefaultSubState();
/* 3826 */         stateHelper.getAndPutStateRequirerIndex(validator.getMainState(), subState, (m, s) -> ((IntSet)stateSets.computeIfAbsent(m.intValue(), ())).add(s.intValue()));
/*      */       } 
/* 3828 */       stateSets.trim();
/* 3829 */       setter.accept(stateSets);
/* 3830 */       return true;
/* 3831 */     } catch (Exception e) {
/* 3832 */       addError(e);
/* 3833 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean getDefaultSubState(@Nonnull JsonElement data, String name, @Nonnull Consumer<String> setter, StringValidator validator, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 3838 */     String[] defaultSubState = new String[1];
/* 3839 */     boolean read = getString(data, name, v -> defaultSubState[0] = v, "Default", validator, state, shortDescription, longDescription);
/* 3840 */     if (!isCreatingDescriptor()) this.stateHelper.setDefaultSubState(defaultSubState[0]); 
/* 3841 */     setter.accept(defaultSubState[0]);
/* 3842 */     return read;
/*      */   }
/*      */   
/*      */   protected void increaseDepth() {
/* 3846 */     if (isCreatingDescriptor())
/*      */       return; 
/* 3848 */     this.stateHelper.increaseDepth();
/*      */   }
/*      */   
/*      */   protected void decreaseDepth() {
/* 3852 */     if (isCreatingDescriptor())
/*      */       return; 
/* 3854 */     this.stateHelper.decreaseDepth();
/*      */   }
/*      */   
/*      */   protected void setNotComponent() {
/* 3858 */     if (isCreatingDescriptor())
/*      */       return; 
/* 3860 */     this.stateHelper.setNotComponent();
/*      */   }
/*      */   
/*      */   protected boolean isComponent() {
/* 3864 */     if (isCreatingDescriptor()) return false;
/*      */     
/* 3866 */     return this.stateHelper.isComponent();
/*      */   }
/*      */   
/*      */   protected void requireInstructionType(@Nonnull EnumSet<InstructionType> instructionType) {
/* 3870 */     requireContext(instructionType, (EnumSet<ComponentContext>)null);
/*      */   }
/*      */   
/*      */   protected void requireContext(@Nonnull EnumSet<InstructionType> instructionType, EnumSet<ComponentContext> componentContexts) {
/* 3874 */     InstructionContextValidator validator = InstructionContextValidator.inInstructions(instructionType, componentContexts);
/* 3875 */     if (isCreatingDescriptor()) {
/* 3876 */       this.builderDescriptor.addValidator((Validator)validator);
/*      */       
/*      */       return;
/*      */     } 
/* 3880 */     if (this.instructionContextHelper.isComponent()) {
/* 3881 */       this.instructionContextHelper.addComponentContextEvaluator((type, extraContext) -> {
/*      */             boolean correctInstruction = InstructionContextHelper.isInCorrectInstruction(instructionType, type);
/*      */             
/*      */             boolean correctExtraContext = InstructionContextHelper.extraContextMatches(componentContexts, extraContext);
/*      */             if (correctInstruction && correctExtraContext) {
/*      */               return;
/*      */             }
/*      */             throw new IllegalStateException(InstructionContextValidator.getErrorMessage(getTypeName(), type, correctInstruction, extraContext, correctExtraContext, getBreadCrumbs()));
/*      */           });
/*      */       return;
/*      */     } 
/* 3892 */     boolean correctInstruction = this.instructionContextHelper.isInCorrectInstruction(instructionType);
/* 3893 */     boolean correctExtraContext = this.instructionContextHelper.extraContextMatches(componentContexts);
/* 3894 */     if (correctInstruction && correctExtraContext)
/*      */       return; 
/* 3896 */     addError(InstructionContextValidator.getErrorMessage(getTypeName(), this.instructionContextHelper.getInstructionContext(), correctInstruction, this.instructionContextHelper
/* 3897 */           .getComponentContext(), correctExtraContext, getBreadCrumbs()));
/*      */   }
/*      */ 
/*      */   
/*      */   public IntSet getDependencies() {
/* 3902 */     return this.builderParameters.getDependencies();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 3907 */     boolean result = true;
/* 3908 */     if (this.dynamicHolders != null) {
/* 3909 */       for (ValueHolder assetHolder : this.dynamicHolders) {
/* 3910 */         result &= validateDynamicHolder(configName, assetHolder, context, errors);
/*      */       }
/*      */     }
/* 3913 */     ValueStoreValidator valueStoreValidator = validationHelper.getValueStoreValidator();
/* 3914 */     if (this.valueStoreUsages != null) {
/* 3915 */       for (ValueStoreValidator.ValueUsage usage : this.valueStoreUsages) {
/* 3916 */         valueStoreValidator.registerValueUsage(usage);
/*      */       }
/*      */     }
/* 3919 */     result &= runLoadTimeValidationHelper(configName, validationHelper, context, errors);
/* 3920 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void runLoadTimeValidationHelper0(String configName, NPCLoadTimeValidationHelper loadTimeValidationHelper, ExecutionContext context, List<String> errors) {}
/*      */   
/*      */   private boolean runLoadTimeValidationHelper(String configName, NPCLoadTimeValidationHelper loadTimeValidationHelper, ExecutionContext context, @Nonnull List<String> errors) {
/*      */     try {
/* 3928 */       runLoadTimeValidationHelper0(configName, loadTimeValidationHelper, context, errors);
/* 3929 */     } catch (Exception e) {
/* 3930 */       errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 3931 */       return false;
/*      */     } 
/* 3933 */     return true;
/*      */   }
/*      */   
/*      */   private boolean validateDynamicHolder(String configName, @Nonnull ValueHolder holder, ExecutionContext context, @Nonnull List<String> errors) {
/*      */     try {
/* 3938 */       holder.validate(context);
/* 3939 */     } catch (Exception e) {
/* 3940 */       errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 3941 */       return false;
/*      */     } 
/* 3943 */     return true;
/*      */   }
/*      */   
/*      */   private void trackDynamicHolder(@Nonnull ValueHolder holder) {
/* 3947 */     if (holder.isStatic())
/* 3948 */       return;  if (this.dynamicHolders == null) this.dynamicHolders = new ArrayList<>(); 
/* 3949 */     this.dynamicHolders.add(holder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readString(@Nonnull JsonObject object, String key) {
/* 3957 */     return expectStringElement(expectKey(object, key), key);
/*      */   }
/*      */   
/*      */   public static String readString(@Nonnull JsonObject jsonObject, String key, String defaultValue) {
/* 3961 */     JsonElement value = jsonObject.get(key);
/* 3962 */     if (value == null) {
/* 3963 */       return defaultValue;
/*      */     }
/* 3965 */     return expectStringElement(value, key);
/*      */   }
/*      */   
/*      */   public static boolean readBoolean(@Nonnull JsonObject jsonObject, String key, boolean defaultValue) {
/* 3969 */     JsonElement value = jsonObject.get(key);
/* 3970 */     if (value == null) {
/* 3971 */       return defaultValue;
/*      */     }
/* 3973 */     return expectBooleanElement(value, key);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static JsonElement expectKey(@Nonnull JsonObject jsonObject, String key) {
/* 3978 */     JsonElement value = jsonObject.get(key);
/* 3979 */     if (value == null) {
/* 3980 */       throw new IllegalStateException("'" + key + "' missing in JSON object");
/*      */     }
/* 3982 */     return value;
/*      */   }
/*      */   
/*      */   public static String expectStringElement(@Nonnull JsonElement element, String key) {
/* 3986 */     if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
/* 3987 */       return element.getAsJsonPrimitive().getAsString();
/*      */     }
/* 3989 */     throw new IllegalStateException("'" + key + "' must be a string");
/*      */   }
/*      */   
/*      */   public static boolean expectBooleanElement(@Nonnull JsonElement element, String key) {
/* 3993 */     if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
/* 3994 */       return element.getAsJsonPrimitive().getAsBoolean();
/*      */     }
/* 3996 */     throw new IllegalStateException("'" + key + "' must be a boolean value");
/*      */   }
/*      */   
/*      */   public static JsonObject expectObject(@Nonnull JsonElement element) {
/* 4000 */     if (!element.isJsonObject()) {
/* 4001 */       throw new IllegalStateException("Expected a JSON object");
/*      */     }
/* 4003 */     return element.getAsJsonObject();
/*      */   }
/*      */   
/*      */   public static JsonObject expectObject(@Nonnull JsonElement element, String key) {
/* 4007 */     if (!element.isJsonObject()) {
/* 4008 */       throw new IllegalStateException("'" + key + "' must be an object: " + String.valueOf(element));
/*      */     }
/* 4010 */     return element.getAsJsonObject();
/*      */   }
/*      */   
/*      */   public static String[] readStringArray(@Nonnull JsonObject object, String key, @Nonnull StringValidator validator, String[] defaultValue) {
/* 4014 */     JsonElement value = object.get(key);
/* 4015 */     if (value == null) {
/* 4016 */       return defaultValue;
/*      */     }
/* 4018 */     return readStringArray(value, key, validator);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static String[] readStringArray(@Nonnull JsonElement element, String key, @Nonnull StringValidator validator) {
/* 4023 */     if (!element.isJsonArray()) {
/* 4024 */       throw new IllegalStateException(key + " must be an array: " + key);
/*      */     }
/*      */     
/* 4027 */     JsonArray array = element.getAsJsonArray();
/* 4028 */     String[] ret = new String[array.size()];
/* 4029 */     for (int i = 0; i < array.size(); i++) {
/* 4030 */       String string = expectStringElement(array.get(i), String.format("%s element at position %s", new Object[] { key, Integer.valueOf(i) }));
/* 4031 */       if (!validator.test(string)) {
/* 4032 */         throw new IllegalStateException(validator.errorMessage(string));
/*      */       }
/* 4034 */       ret[i] = string;
/*      */     } 
/* 4036 */     return ret;
/*      */   }
/*      */   
/*      */   protected void addError(String error) {
/* 4040 */     this.readErrors.add(this.fileName + ": " + this.fileName);
/*      */   }
/*      */   
/*      */   protected void addError(@Nonnull Exception e) {
/* 4044 */     addError(e.getMessage());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public abstract String getShortDescription();
/*      */   
/*      */   @Nullable
/*      */   public abstract String getLongDescription();
/*      */   
/*      */   @Nullable
/*      */   public abstract BuilderDescriptorState getBuilderDescriptorState();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */