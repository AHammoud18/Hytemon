/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ReferenceProviderEvaluator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringArrayNotEmptyValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderObjectReferenceHelper<T>
/*     */   extends BuilderObjectHelper<T>
/*     */ {
/*     */   public static final String KEY_REFERENCE = "Reference";
/*     */   public static final String KEY_LOCAL = "Local";
/*     */   public static final String KEY_INTERFACE_LIST = "Interfaces";
/*     */   public static final String KEY_NULLABLE = "Nullable";
/*     */   public static final String NULL_COMPONENT = "$Null";
/*     */   public static final String KEY_LABEL = "$Label";
/*     */   @Nullable
/*     */   protected Builder<T> builder;
/*  35 */   protected final StringHolder fileReference = new StringHolder();
/*     */   
/*     */   protected String[] componentInterfaces;
/*     */   protected int referenceIndex;
/*     */   protected boolean isReference;
/*     */   protected boolean isNullable;
/*     */   @Nullable
/*     */   protected BuilderModifier modifier;
/*     */   protected FeatureEvaluatorHelper evaluatorHelper;
/*     */   protected InternalReferenceResolver internalReferenceResolver;
/*     */   protected boolean isInternalReference;
/*     */   protected String label;
/*     */   
/*     */   public BuilderObjectReferenceHelper(Class<?> classType, BuilderContext owner) {
/*  49 */     super(classType, owner);
/*  50 */     this.builder = null;
/*  51 */     this.referenceIndex = Integer.MIN_VALUE;
/*  52 */     this.modifier = null;
/*     */   }
/*     */   
/*     */   public boolean excludeFromRegularBuild() {
/*  56 */     if (this.builder == null) return false; 
/*  57 */     return this.builder.excludeFromRegularBuilding();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T build(@Nonnull BuilderSupport builderSupport) {
/*  63 */     if (!isPresent()) {
/*  64 */       return null;
/*     */     }
/*     */     
/*  67 */     Builder<T> builder = getBuilder(builderSupport.getBuilderManager(), builderSupport, this.isNullable);
/*  68 */     if (builder == null)
/*     */     {
/*  70 */       return null;
/*     */     }
/*     */     
/*  73 */     StateMappingHelper mappingHelper = builder.getStateMappingHelper();
/*  74 */     boolean hasLocalComponentStates = (this.builder == null && mappingHelper != null && mappingHelper.hasComponentStates());
/*  75 */     if (hasLocalComponentStates) {
/*  76 */       mappingHelper.initialiseComponentState(builderSupport);
/*     */     }
/*     */     
/*  79 */     if (this.modifier == null) {
/*  80 */       validateRequiredFeatures(builder, builderSupport.getBuilderManager(), builderSupport.getExecutionContext());
/*  81 */       T t = builder.isEnabled(builderSupport.getExecutionContext()) ? builder.build(builderSupport) : null;
/*  82 */       if (hasLocalComponentStates) {
/*  83 */         mappingHelper.popComponentState(builderSupport);
/*     */       }
/*  85 */       return t;
/*     */     } 
/*     */ 
/*     */     
/*  89 */     Scope globalScope = null;
/*  90 */     if (this.isInternalReference) {
/*  91 */       globalScope = builderSupport.getGlobalScope();
/*  92 */       Objects.requireNonNull(globalScope, "Global scope should not be null when applying to an internal component");
/*     */     } 
/*     */     
/*  95 */     if (this.modifier.exportedStateCount() != builder.getStateMappingHelper().importedStateCount()) {
/*  96 */       throw new SkipSentryException(new IllegalStateException(String.format("Number of exported states does not match imported states in component %s", new Object[] { this.fileReference.get(builderSupport.getExecutionContext()) })));
/*     */     }
/*     */     
/*  99 */     ExecutionContext context = builderSupport.getExecutionContext();
/* 100 */     Scope newScope = this.modifier.createScope(builderSupport, builder.getBuilderParameters(), globalScope);
/* 101 */     Scope oldScope = context.setScope(newScope);
/*     */     
/* 103 */     if (this.modifier.exportedStateCount() > 0) {
/* 104 */       this.modifier.applyComponentStateMap(builderSupport);
/*     */     }
/*     */     
/* 107 */     validateRequiredFeatures(builder, builderSupport.getBuilderManager(), context);
/* 108 */     validateInstructionContext(builder, builderSupport);
/* 109 */     T instance = builder.isEnabled(builderSupport.getExecutionContext()) ? builder.build(builderSupport) : null;
/*     */     
/* 111 */     if (this.modifier.exportedStateCount() > 0) {
/* 112 */       this.modifier.popComponentStateMap(builderSupport);
/*     */     }
/*     */     
/* 115 */     if (hasLocalComponentStates) {
/* 116 */       mappingHelper.popComponentState(builderSupport);
/*     */     }
/*     */     
/* 119 */     builderSupport.getExecutionContext().setScope(oldScope);
/* 120 */     return instance;
/*     */   }
/*     */   public boolean validate(String configName, NPCLoadTimeValidationHelper loadTimeValidationHelper, @Nonnull BuilderManager manager, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/*     */     Builder<T> builder;
/*     */     Scope newScope;
/* 125 */     if (!isPresent()) return true;
/*     */ 
/*     */     
/*     */     try {
/* 129 */       builder = getBuilder(manager, context, (Builder<?>)null);
/* 130 */     } catch (Exception e) {
/* 131 */       errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 132 */       return false;
/*     */     } 
/*     */     
/* 135 */     if (builder == null) {
/* 136 */       if (this.isNullable) return true;
/*     */       
/* 138 */       errors.add(String.format("%s: %s is not a nullable component reference but a null component was passed", new Object[] { configName, this.fileReference.getExpressionString() }));
/* 139 */       return false;
/*     */     } 
/*     */     
/* 142 */     if (this.modifier == null) {
/*     */       
/* 144 */       if (!builder.isEnabled(context)) return true;
/*     */       
/* 146 */       boolean bool = true;
/*     */       try {
/* 148 */         validateRequiredFeatures(builder, manager, context);
/* 149 */       } catch (Exception e) {
/* 150 */         errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 151 */         bool = false;
/*     */       } 
/* 153 */       bool &= builder.validate(configName, loadTimeValidationHelper, context, globalScope, errors);
/* 154 */       return bool;
/*     */     } 
/*     */     
/* 157 */     boolean result = true;
/* 158 */     if (this.modifier.exportedStateCount() != builder.getStateMappingHelper().importedStateCount()) {
/* 159 */       errors.add(String.format("%s: Number of exported states does not match imported states in component %s", new Object[] { configName, this.fileReference.get(context) }));
/* 160 */       result = false;
/*     */     } 
/*     */     
/* 163 */     Scope additionalScope = this.isInternalReference ? globalScope : null;
/*     */ 
/*     */     
/*     */     try {
/* 167 */       newScope = this.modifier.createScope(context, builder.getBuilderParameters(), additionalScope);
/* 168 */     } catch (Exception e) {
/* 169 */       errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 170 */       return false;
/*     */     } 
/*     */     
/* 173 */     Scope oldScope = context.setScope(newScope);
/*     */ 
/*     */     
/* 176 */     if (builder.isEnabled(context)) {
/*     */       try {
/* 178 */         validateRequiredFeatures(builder, manager, context);
/* 179 */       } catch (Exception e) {
/* 180 */         errors.add(String.format("%s: %s", new Object[] { configName, e.getMessage() }));
/* 181 */         result = false;
/*     */       } 
/* 183 */       result &= builder.validate(configName, loadTimeValidationHelper, context, globalScope, errors);
/*     */     } 
/* 185 */     context.setScope(oldScope);
/* 186 */     return result;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Builder<T> getBuilder(@Nonnull BuilderManager builderManager, @Nonnull BuilderSupport support, boolean nullable) {
/* 191 */     Builder<T> builder = getBuilder(builderManager, support.getExecutionContext(), support.getParentSpawnable());
/* 192 */     if (!nullable && builder == null) throw new NullPointerException(String.format("ReferenceHelper failed to get builder: %s", new Object[] { getClassType().getSimpleName() })); 
/* 193 */     return builder;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Builder<T> getBuilder(@Nonnull BuilderManager builderManager, ExecutionContext context, @Nullable Builder<?> parentSpawnable) {
/* 198 */     if (this.builder != null) {
/* 199 */       return this.builder;
/*     */     }
/*     */     
/* 202 */     if (this.isInternalReference) {
/* 203 */       return this.internalReferenceResolver.getBuilder(this.referenceIndex, this.classType);
/*     */     }
/*     */     
/* 206 */     if (this.referenceIndex >= 0) {
/* 207 */       Builder<T> builder = builderManager.tryGetCachedValidBuilder(this.referenceIndex, this.classType);
/* 208 */       if (builder == null) {
/* 209 */         throw new SkipSentryException(new IllegalStateException(String.format("Builder %s exists but is not valid!", new Object[] { builderManager.lookupName(this.referenceIndex) })));
/*     */       }
/* 211 */       return builder;
/*     */     } 
/*     */     
/* 214 */     String reference = this.fileReference.get(context);
/* 215 */     if (reference.equals("$Null")) {
/* 216 */       return null;
/*     */     }
/*     */     
/* 219 */     int idx = builderManager.getIndex(reference);
/* 220 */     if (idx >= 0) {
/* 221 */       if (parentSpawnable != null) parentSpawnable.addDynamicDependency(idx); 
/* 222 */       Builder<T> builder = builderManager.getCachedBuilder(idx, this.classType);
/* 223 */       String builderInterfaceCode = builder.getBuilderParameters().getInterfaceCode();
/* 224 */       validateComponentInterfaceMatch(builderInterfaceCode);
/* 225 */       return builder;
/*     */     } 
/*     */     
/* 228 */     if (!reference.isEmpty()) {
/* 229 */       throw new SkipSentryException(new IllegalStateException("Failed to find builder for: " + reference));
/*     */     }
/*     */     
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readConfig(@Nonnull JsonElement data, @Nonnull BuilderManager builderManager, @Nonnull BuilderParameters builderParameters, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 237 */     readConfig(data, builderManager.getFactory(this.classType), builderManager, builderParameters, builderValidationHelper);
/*     */   }
/*     */   
/*     */   public void readConfig(@Nonnull JsonElement data, @Nonnull BuilderFactory<T> factory, @Nonnull BuilderManager builderManager, @Nonnull BuilderParameters builderParameters, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 241 */     super.readConfig(data, builderManager, builderParameters, builderValidationHelper);
/* 242 */     if (data.isJsonNull()) {
/* 243 */       this.builder = null;
/*     */       
/*     */       return;
/*     */     } 
/* 247 */     if (data.isJsonPrimitive() && data.getAsJsonPrimitive().isString()) {
/* 248 */       builderValidationHelper.getReadErrors().add(builderValidationHelper.getName() + ": String reference '" + builderValidationHelper.getName() + "' to a component is deprecated. Use the 'Reference' parameter instead.");
/*     */       
/*     */       return;
/*     */     } 
/* 252 */     JsonObject jsonObject = data.isJsonObject() ? data.getAsJsonObject() : null;
/* 253 */     JsonElement referenceValue = (jsonObject != null) ? jsonObject.get("Reference") : null;
/* 254 */     if (referenceValue != null) {
/*     */       try {
/* 256 */         if (BuilderBase.readBoolean(jsonObject, "Local", false)) {
/* 257 */           BuilderModifier.readModifierObject(data.getAsJsonObject(), builderParameters, this.fileReference, holder -> setInternalReference(holder, builderValidationHelper.getInternalReferenceResolver()), modifier -> this.modifier = modifier, builderValidationHelper
/*     */               
/* 259 */               .getStateMappingHelper(), builderValidationHelper.getExtraInfo());
/*     */         } else {
/* 261 */           JsonObject dataObj = data.getAsJsonObject();
/* 262 */           BuilderModifier.readModifierObject(dataObj, builderParameters, this.fileReference, holder -> setFileReference(holder, dataObj, builderManager), modifier -> this.modifier = modifier, builderValidationHelper
/*     */               
/* 264 */               .getStateMappingHelper(), builderValidationHelper.getExtraInfo());
/*     */         } 
/*     */         
/* 267 */         FeatureEvaluatorHelper evaluatorHelper = builderValidationHelper.getFeatureEvaluatorHelper();
/* 268 */         if (evaluatorHelper != null) {
/*     */           
/* 270 */           if (evaluatorHelper.canAddProvider()) {
/* 271 */             evaluatorHelper.add((ProviderEvaluator)new ReferenceProviderEvaluator(this.referenceIndex, this.classType));
/* 272 */             evaluatorHelper.setContainsReference();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 277 */           this.evaluatorHelper = evaluatorHelper;
/*     */         } 
/* 279 */       } catch (IllegalArgumentException|IllegalStateException e) {
/* 280 */         builderValidationHelper.getReadErrors().add(builderValidationHelper.getName() + ": " + builderValidationHelper.getName() + " at " + e.getMessage());
/*     */       } 
/*     */     } else {
/* 283 */       this.builder = factory.createBuilder(data);
/*     */       
/* 285 */       if (this.builder.isDeprecated()) {
/* 286 */         builderManager.checkIfDeprecated(this.builder, factory, data, builderParameters.getFileName(), getBreadCrumbs());
/*     */       }
/* 288 */       if (data.isJsonObject() && data.getAsJsonObject().has("$Label") && data.getAsJsonObject().get("$Label").isJsonPrimitive()) {
/* 289 */         this.builder.setLabel(data.getAsJsonObject().get("$Label").getAsString());
/*     */       } else {
/* 291 */         this.builder.setLabel(factory.getKeyName(data));
/*     */       } 
/* 293 */       this.builder.readConfig(this, data, builderManager, builderParameters, builderValidationHelper);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setInternalReference(@Nonnull StringHolder holder, InternalReferenceResolver referenceResolver) {
/* 298 */     this.isInternalReference = true;
/* 299 */     this.isReference = true;
/*     */     
/* 301 */     if (holder.isStatic()) {
/* 302 */       this.internalReferenceResolver = referenceResolver;
/* 303 */       this.referenceIndex = this.internalReferenceResolver.getOrCreateIndex(holder.get(null));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setFileReference(@Nonnull StringHolder holder, @Nonnull JsonObject jsonObject, @Nonnull BuilderManager builderManager) {
/* 308 */     this.isInternalReference = false;
/* 309 */     this.isReference = true;
/* 310 */     this.componentInterfaces = BuilderBase.readStringArray(jsonObject, "Interfaces", (StringValidator)StringNotEmptyValidator.get(), null);
/* 311 */     this.isNullable = BuilderBase.readBoolean(jsonObject, "Nullable", false);
/* 312 */     if (holder.isStatic()) {
/* 313 */       this.referenceIndex = builderManager.getOrCreateIndex(holder.get(null));
/* 314 */       this.builderParameters.addDependency(this.referenceIndex);
/* 315 */     } else if (!StringArrayNotEmptyValidator.get().test(this.componentInterfaces)) {
/* 316 */       throw new SkipSentryException(new IllegalStateException("Computable references must define a list of 'Interfaces' to control which components can be attached."));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateRequiredFeatures(@Nonnull Builder<T> builder, BuilderManager manager, ExecutionContext context) {
/* 321 */     builder.validateReferencedProvidedFeatures(manager, context);
/* 322 */     if (this.evaluatorHelper != null) {
/* 323 */       this.evaluatorHelper.validateProviderReferences(manager, context);
/* 324 */       builder.getEvaluatorHelper().validateComponentRequirements(this.evaluatorHelper, context);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateInstructionContext(@Nonnull Builder<T> builder, @Nonnull BuilderSupport support) {
/* 329 */     InstructionContextHelper instructionContextHelper = builder.getInstructionContextHelper();
/* 330 */     if (instructionContextHelper == null || this.isInternalReference)
/*     */       return; 
/* 332 */     instructionContextHelper.validateComponentContext(support.getCurrentInstructionContext(), support.getCurrentComponentContext());
/*     */   }
/*     */   
/*     */   private void validateComponentInterfaceMatch(String builderInterfaceCode) {
/* 336 */     for (String componentInterface : this.componentInterfaces) {
/* 337 */       if (componentInterface.equals(builderInterfaceCode)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 341 */     throw new SkipSentryException(new IllegalStateException(String.format("Component code %s does not match any of slot codes: %s.", new Object[] { builderInterfaceCode, Arrays.toString(this.componentInterfaces) })));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPresent() {
/* 346 */     return (isFinal() || this.isReference);
/*     */   }
/*     */   
/*     */   public boolean isFinal() {
/* 350 */     return (this.builder != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 355 */     return this.label;
/*     */   }
/*     */   
/*     */   public void setLabel(String label) {
/* 359 */     this.label = label;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderObjectReferenceHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */