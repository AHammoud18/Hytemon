/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.schema.NamedSchema;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*     */ import com.hypixel.hytale.codec.schema.config.ArraySchema;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StateStringValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*     */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderModifier
/*     */ {
/*     */   public static final String KEY_MODIFY = "Modify";
/*     */   public static final String KEY_EXPORT_STATES = "_ExportStates";
/*     */   public static final String KEY_INTERFACE_PARAMETERS = "_InterfaceParameters";
/*     */   public static final String KEY_COMBAT_CONFIG = "_CombatConfig";
/*     */   public static final String KEY_INTERACTION_VARS = "_InteractionVars";
/*     */   private final Object2ObjectMap<String, ExpressionHolder> builderExpressionMap;
/*     */   private final StatePair[] exportedStateIndexes;
/*     */   private final StateMappingHelper stateHelper;
/*     */   private final String combatConfig;
/*     */   private final Map<String, String> interactionVars;
/*     */   
/*     */   protected BuilderModifier(Object2ObjectMap<String, ExpressionHolder> builderExpressionMap, StatePair[] exportedStateIndexes, StateMappingHelper stateHelper, String combatConfig, Map<String, String> interactionVars) {
/*  89 */     this.builderExpressionMap = builderExpressionMap;
/*  90 */     this.exportedStateIndexes = exportedStateIndexes;
/*  91 */     this.stateHelper = stateHelper;
/*  92 */     this.combatConfig = combatConfig;
/*  93 */     this.interactionVars = interactionVars;
/*     */   }
/*     */   
/*     */   public String getCombatConfig() {
/*  97 */     return this.combatConfig;
/*     */   }
/*     */   
/*     */   public Map<String, String> getInteractionVars() {
/* 101 */     return this.interactionVars;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 105 */     return this.builderExpressionMap.isEmpty();
/*     */   }
/*     */   
/*     */   public int exportedStateCount() {
/* 109 */     return this.exportedStateIndexes.length;
/*     */   }
/*     */   
/*     */   public void applyComponentStateMap(@Nonnull BuilderSupport support) {
/* 113 */     support.setModifiedStateMap(this.stateHelper, this.exportedStateIndexes);
/*     */   }
/*     */   
/*     */   public void popComponentStateMap(@Nonnull BuilderSupport support) {
/* 117 */     support.popModifiedStateMap();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Scope createScope(@Nonnull BuilderSupport builderSupport, @Nonnull BuilderParameters builderParameters, Scope globalScope) {
/* 122 */     ExecutionContext executionContext = builderSupport.getExecutionContext();
/* 123 */     return createScope(executionContext, builderParameters, globalScope);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Scope createScope(ExecutionContext executionContext, @Nonnull BuilderParameters builderParameters, @Nullable Scope globalScope) {
/* 128 */     StdScope scope = builderParameters.createScope();
/* 129 */     if (globalScope != null) {
/* 130 */       StdScope mergedScope = new StdScope(globalScope);
/* 131 */       mergedScope.merge(scope);
/* 132 */       scope = mergedScope;
/*     */     } 
/*     */     
/* 135 */     StdScope finalScope = scope;
/* 136 */     ObjectIterator<Object2ObjectMap.Entry<String, ExpressionHolder>> iterator = Object2ObjectMaps.fastIterator(this.builderExpressionMap);
/* 137 */     while (iterator.hasNext()) {
/* 138 */       Object2ObjectMap.Entry<String, ExpressionHolder> pair = (Object2ObjectMap.Entry<String, ExpressionHolder>)iterator.next();
/* 139 */       String name = (String)pair.getKey();
/* 140 */       ExpressionHolder holder = (ExpressionHolder)pair.getValue();
/* 141 */       ValueType valueType = builderParameters.getParameterType(name);
/*     */ 
/*     */       
/* 144 */       BuilderExpression expression = holder.getExpression(builderParameters.getInterfaceCode());
/* 145 */       if (expression == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 150 */       if (valueType == ValueType.VOID) {
/* 151 */         throw new SkipSentryException(new IllegalStateException("Parameter " + name + " does not exist or is private"));
/*     */       }
/*     */       
/* 154 */       if (!ValueType.isAssignableType(expression.getType(), valueType)) {
/* 155 */         throw new SkipSentryException(new IllegalStateException("Parameter " + name + " has type " + String.valueOf(expression.getType()) + " but should be " + String.valueOf(valueType)));
/*     */       }
/*     */       
/* 158 */       expression.updateScope(finalScope, name, executionContext);
/*     */     } 
/* 160 */     return (Scope)scope;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderModifier fromJSON(@Nonnull JsonObject jsonObject, @Nonnull BuilderParameters builderParameters, @Nonnull StateMappingHelper helper, @Nonnull ExtraInfo extraInfo) {
/* 165 */     JsonObject modify = null;
/* 166 */     JsonElement modifyObject = jsonObject.get("Modify");
/* 167 */     if (modifyObject != null) {
/* 168 */       modify = BuilderBase.expectObject(modifyObject, "Modify");
/*     */     }
/*     */     
/* 171 */     if (modify == null || modify.entrySet().isEmpty()) {
/* 172 */       return EmptyBuilderModifier.INSTANCE;
/*     */     }
/*     */     
/* 175 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 176 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 177 */     for (Map.Entry<String, JsonElement> stringElementPair : (Iterable<Map.Entry<String, JsonElement>>)modify.entrySet()) {
/* 178 */       String key = stringElementPair.getKey();
/* 179 */       if (object2ObjectOpenHashMap.containsKey(key)) {
/* 180 */         throw new SkipSentryException(new IllegalStateException("Duplicate entry '" + key + "' in 'Modify' block"));
/*     */       }
/* 182 */       if (key.equals("_InterfaceParameters") || key.equals("_CombatConfig") || key.equals("_InteractionVars")) {
/*     */         continue;
/*     */       }
/* 185 */       if (key.equals("_ExportStates")) {
/* 186 */         if (!((JsonElement)stringElementPair.getValue()).isJsonArray()) {
/* 187 */           throw new SkipSentryException(new IllegalStateException(String.format("%s in modifier block must be a Json Array", new Object[] { "_ExportStates" })));
/*     */         }
/* 189 */         StateStringValidator validator = StateStringValidator.requireMainState();
/* 190 */         JsonArray array = ((JsonElement)stringElementPair.getValue()).getAsJsonArray();
/* 191 */         for (int i = 0; i < array.size(); i++) {
/* 192 */           String state = array.get(i).getAsString();
/* 193 */           if (!validator.test(state)) {
/* 194 */             throw new SkipSentryException(new IllegalStateException(validator.errorMessage(state)));
/*     */           }
/* 196 */           String substate = validator.hasSubState() ? validator.getSubState() : helper.getDefaultSubState();
/* 197 */           helper.getAndPutSetterIndex(validator.getMainState(), substate, (m, s) -> exportedStateIndexes.add(new StatePair(validator.getMainState(), m.intValue(), s.intValue())));
/*     */         } 
/*     */         continue;
/*     */       } 
/* 201 */       BuilderExpression expression = BuilderExpression.fromJSON(stringElementPair.getValue(), builderParameters, false);
/* 202 */       object2ObjectOpenHashMap.put(key, new ExpressionHolder(expression));
/*     */     } 
/*     */     
/* 205 */     JsonElement interfaceValue = modify.get("_InterfaceParameters");
/* 206 */     if (interfaceValue != null) {
/* 207 */       JsonObject interfaceParameters = BuilderBase.expectObject(interfaceValue, "_InterfaceParameters");
/* 208 */       for (Map.Entry<String, JsonElement> interfaceEntry : (Iterable<Map.Entry<String, JsonElement>>)interfaceParameters.entrySet()) {
/* 209 */         String interfaceKey = interfaceEntry.getKey();
/* 210 */         JsonObject parameters = BuilderBase.expectObject(interfaceEntry.getValue());
/* 211 */         for (Map.Entry<String, JsonElement> parameterEntry : (Iterable<Map.Entry<String, JsonElement>>)parameters.entrySet()) {
/* 212 */           ExpressionHolder holder = (ExpressionHolder)object2ObjectOpenHashMap.computeIfAbsent(parameterEntry.getKey(), key -> new ExpressionHolder());
/* 213 */           if (holder.hasInterfaceMappedExpression(interfaceKey)) {
/* 214 */             throw new SkipSentryException(new IllegalStateException("Duplicate entry '" + (String)parameterEntry.getKey() + "' in 'Modify' block for interface '" + interfaceKey));
/*     */           }
/* 216 */           holder.addInterfaceMappedExpression(interfaceKey, BuilderExpression.fromJSON(parameterEntry.getValue(), builderParameters, false));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     String combatConfig = null;
/* 222 */     JsonElement combatConfigValue = modify.get("_CombatConfig");
/* 223 */     if (combatConfigValue != null) {
/* 224 */       combatConfig = combatConfigValue.getAsString();
/*     */     }
/*     */     
/* 227 */     Map<String, String> interactionVars = null;
/* 228 */     JsonElement interactionVarsValue = modify.get("_InteractionVars");
/* 229 */     if (interactionVarsValue != null) {
/* 230 */       interactionVars = RootInteraction.CHILD_ASSET_CODEC_MAP.decode(BsonUtil.translateJsonToBson(interactionVarsValue), extraInfo);
/*     */       
/* 232 */       extraInfo.getValidationResults()._processValidationResults();
/* 233 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(HytaleLogger.getLogger());
/*     */     } 
/*     */     
/* 236 */     return new BuilderModifier((Object2ObjectMap<String, ExpressionHolder>)object2ObjectOpenHashMap, (StatePair[])objectArrayList.toArray(x$0 -> new StatePair[x$0]), helper, combatConfig, interactionVars);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void readModifierObject(@Nonnull JsonObject jsonObject, @Nonnull BuilderParameters builderParameters, @Nonnull StringHolder holder, @Nonnull Consumer<StringHolder> referenceConsumer, @Nonnull Consumer<BuilderModifier> builderModifierConsumer, @Nonnull StateMappingHelper helper, @Nonnull ExtraInfo extraInfo) {
/* 242 */     holder.readJSON(BuilderBase.expectKey(jsonObject, "Reference"), (StringValidator)StringNotEmptyValidator.get(), "Reference", builderParameters);
/* 243 */     BuilderModifier modifier = fromJSON(jsonObject, builderParameters, helper, extraInfo);
/* 244 */     referenceConsumer.accept(holder);
/* 245 */     builderModifierConsumer.accept(modifier);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Schema toSchema(@Nonnull SchemaContext context) {
/* 250 */     return context.refDefinition(SchemaGenerator.INSTANCE);
/*     */   }
/*     */   
/*     */   private static class SchemaGenerator implements SchemaConvertable<Void>, NamedSchema {
/*     */     @Nonnull
/* 255 */     public static SchemaGenerator INSTANCE = new SchemaGenerator();
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String getSchemaName() {
/* 260 */       return "NPC:Type:BuilderModifier";
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Schema toSchema(@Nonnull SchemaContext context) {
/* 266 */       ObjectSchema s = new ObjectSchema();
/* 267 */       s.setTitle("BuilderModifier");
/* 268 */       LinkedHashMap<String, Schema> props = new LinkedHashMap<>();
/* 269 */       s.setProperties(props);
/*     */       
/* 271 */       props.put("_ExportStates", new ArraySchema((Schema)new StringSchema()));
/* 272 */       props.put("_InterfaceParameters", new ObjectSchema());
/* 273 */       StringSchema combatConfig = new StringSchema();
/* 274 */       combatConfig.setHytaleAssetRef(BalanceAsset.class.getSimpleName());
/* 275 */       props.put("_CombatConfig", combatConfig);
/* 276 */       ObjectSchema interactionVars = new ObjectSchema();
/* 277 */       interactionVars.setTitle("Map");
/* 278 */       Schema childSchema = context.refDefinition((SchemaConvertable)RootInteraction.CHILD_ASSET_CODEC);
/* 279 */       interactionVars.setAdditionalProperties(childSchema);
/* 280 */       props.put("_InteractionVars", interactionVars);
/*     */ 
/*     */       
/* 283 */       s.setAdditionalProperties(BuilderExpression.toSchema(context));
/* 284 */       return (Schema)s;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ExpressionHolder {
/*     */     private final BuilderExpression expression;
/*     */     private Object2ObjectMap<String, BuilderExpression> interfaceMappedExpressions;
/*     */     
/*     */     public ExpressionHolder() {
/* 293 */       this(null);
/*     */     }
/*     */     
/*     */     public ExpressionHolder(BuilderExpression expression) {
/* 297 */       this.expression = expression;
/*     */     }
/*     */     
/*     */     public boolean hasInterfaceMappedExpression(String interfaceKey) {
/* 301 */       return (this.interfaceMappedExpressions != null && this.interfaceMappedExpressions.containsKey(interfaceKey));
/*     */     }
/*     */     
/*     */     public void addInterfaceMappedExpression(String interfaceKey, BuilderExpression expression) {
/* 305 */       if (this.interfaceMappedExpressions == null) this.interfaceMappedExpressions = (Object2ObjectMap<String, BuilderExpression>)new Object2ObjectOpenHashMap(); 
/* 306 */       this.interfaceMappedExpressions.put(interfaceKey, expression);
/*     */     }
/*     */     
/*     */     public BuilderExpression getExpression(@Nullable String interfaceKey) {
/* 310 */       if (interfaceKey == null || this.interfaceMappedExpressions == null || !this.interfaceMappedExpressions.containsKey(interfaceKey)) return this.expression; 
/* 311 */       return (BuilderExpression)this.interfaceMappedExpressions.get(interfaceKey);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderModifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */