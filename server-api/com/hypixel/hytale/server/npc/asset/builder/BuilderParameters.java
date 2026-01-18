/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.config.ArraySchema;
/*     */ import com.hypixel.hytale.codec.schema.config.BooleanSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticBooleanArray;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumberArray;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticStringArray;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderParameters
/*     */ {
/*     */   public static final String KEY_PARAMETERS = "Parameters";
/*     */   public static final String KEY_IMPORT_STATES = "_ImportStates";
/*     */   public static final String KEY_INTERFACE = "Interface";
/*     */   @Nonnull
/*     */   protected final Map<String, Parameter> parameters;
/*     */   
/*     */   protected BuilderParameters(StdScope scope, String fileName, String interfaceCode) {
/*  40 */     this(scope, fileName, interfaceCode, (IntSet)new IntOpenHashSet());
/*     */   } protected StdScope scope; @Nullable
/*     */   protected CompileContext compileContext; protected final String fileName; protected final IntSet dependencies; protected final String interfaceCode;
/*     */   protected BuilderParameters(StdScope scope, String fileName, String interfaceCode, IntSet dependencies) {
/*  44 */     this.scope = new StdScope((Scope)scope);
/*  45 */     this.compileContext = new CompileContext((Scope)this.scope);
/*  46 */     this.parameters = new HashMap<>();
/*  47 */     this.fileName = fileName;
/*  48 */     this.dependencies = dependencies;
/*  49 */     this.interfaceCode = interfaceCode;
/*     */   }
/*     */   
/*     */   protected BuilderParameters(@Nonnull BuilderParameters other) {
/*  53 */     this(other.scope, other.fileName, other.interfaceCode, other.dependencies);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  57 */     return this.parameters.isEmpty();
/*     */   }
/*     */   
/*     */   public void addParametersToScope() {
/*  61 */     this.parameters.forEach((name, parameter) -> parameter.expression.addToScope(name, this.scope));
/*     */   }
/*     */   
/*     */   public ValueType getParameterType(String name) {
/*  65 */     Parameter p = this.parameters.get(name);
/*     */     
/*  67 */     if (p == null || p.isPrivate()) return ValueType.VOID;
/*     */     
/*  69 */     return p.getExpression().getType();
/*     */   }
/*     */   
/*     */   public void readJSON(@Nonnull JsonObject jsonObject, @Nonnull StateMappingHelper stateHelper) {
/*  73 */     JsonElement parameterValue = jsonObject.get("Parameters");
/*  74 */     if (parameterValue == null)
/*     */       return; 
/*  76 */     JsonObject modify = BuilderBase.expectObject(parameterValue, "Parameters");
/*     */     
/*  78 */     modify.entrySet().forEach(stringElementPair -> {
/*     */           String key = (String)stringElementPair.getKey();
/*     */           if (this.parameters.containsKey(key)) {
/*     */             throw new IllegalStateException("Duplicate entry '" + key + "' in 'Parameters' block");
/*     */           }
/*     */           if (key.equals("_ImportStates")) {
/*     */             if (!((JsonElement)stringElementPair.getValue()).isJsonArray()) {
/*     */               throw new IllegalStateException(String.format("%s in parameter block must be a Json Array", new Object[] { "_ImportStates" }));
/*     */             }
/*     */             stateHelper.setComponentImportStateMappings(((JsonElement)stringElementPair.getValue()).getAsJsonArray());
/*     */             return;
/*     */           } 
/*     */           this.parameters.put(key, Parameter.fromJSON((JsonElement)stringElementPair.getValue(), this, key));
/*     */         });
/*     */   }
/*     */   
/*     */   public void createCompileContext() {
/*  95 */     this.compileContext = new CompileContext();
/*     */   }
/*     */   
/*     */   public void disposeCompileContext() {
/*  99 */     this.compileContext = null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CompileContext getCompileContext() {
/* 104 */     return this.compileContext;
/*     */   }
/*     */   
/*     */   public ValueType compile(@Nonnull String expression) {
/* 108 */     return getCompileContext().compile(expression, (Scope)getScope(), false);
/*     */   }
/*     */   
/*     */   public List<ExecutionContext.Instruction> getInstructions() {
/* 112 */     return getCompileContext().getInstructions();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ExecutionContext.Operand getConstantOperand() {
/* 117 */     return getCompileContext().getAsOperand();
/*     */   }
/*     */   
/*     */   public StdScope getScope() {
/* 121 */     return this.scope;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public StdScope createScope() {
/* 126 */     return StdScope.copyOf(this.scope);
/*     */   }
/*     */   
/*     */   public void validateNoDuplicateParameters(@Nonnull BuilderParameters other) {
/* 130 */     other.parameters.keySet().forEach(key -> {
/*     */           if (this.parameters.containsKey(key)) {
/*     */             throw new IllegalStateException("Parameter '" + key + "' in 'Parameters' block hides parameter from parent scope");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 138 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public IntSet getDependencies() {
/* 142 */     return this.dependencies;
/*     */   }
/*     */   
/*     */   public String getInterfaceCode() {
/* 146 */     return this.interfaceCode;
/*     */   }
/*     */   
/*     */   public void addDependency(int d) {
/* 150 */     this.dependencies.add(d);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ObjectSchema toSchema(@Nonnull SchemaContext context) {
/* 155 */     ObjectSchema schema = new ObjectSchema();
/* 156 */     schema.setTitle("Parameters");
/* 157 */     schema.setProperties(Map.of("_ImportStates", new ArraySchema((Schema)new StringSchema())));
/* 158 */     schema.setAdditionalProperties((Schema)Parameter.toSchema(context));
/* 159 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Parameter
/*     */   {
/*     */     public static final String KEY_VALUE = "Value";
/*     */     public static final String KEY_TYPE_HINT = "TypeHint";
/*     */     public static final String KEY_VALIDATE = "Validate";
/*     */     public static final String KEY_CONFINE = "Confine";
/*     */     public static final String KEY_DESCRIPTION = "Description";
/*     */     public static final String KEY_PRIVATE = "Private";
/*     */     private final BuilderExpression expression;
/*     */     private final String description;
/*     */     private final String code;
/*     */     private List<ExecutionContext.Instruction> instructionList;
/*     */     private final boolean isValidation;
/*     */     private final boolean isPrivate;
/*     */     
/*     */     public Parameter(BuilderExpression expression, String description, String code, boolean isValidation, boolean isPrivate) {
/* 179 */       this.expression = expression;
/* 180 */       this.description = description;
/* 181 */       this.code = code;
/* 182 */       this.isValidation = isValidation;
/* 183 */       this.isPrivate = isPrivate;
/*     */     }
/*     */     
/*     */     public BuilderExpression getExpression() {
/* 187 */       return this.expression;
/*     */     }
/*     */     
/*     */     public String getDescription() {
/* 191 */       return this.description;
/*     */     }
/*     */     
/*     */     public boolean isValidation() {
/* 195 */       return this.isValidation;
/*     */     }
/*     */     
/*     */     public boolean isPrivate() {
/* 199 */       return this.isPrivate;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static ObjectSchema toSchema(@Nonnull SchemaContext context) {
/* 204 */       ObjectSchema props = new ObjectSchema();
/* 205 */       props.setTitle("Parameter");
/* 206 */       LinkedHashMap<String, Schema> map = new LinkedHashMap<>();
/*     */       
/* 208 */       map.put("Value", BuilderExpression.toSchema(context));
/* 209 */       map.put("TypeHint", new StringSchema());
/* 210 */       map.put("Validate", new StringSchema());
/* 211 */       map.put("Confine", new StringSchema());
/* 212 */       map.put("Description", new StringSchema());
/* 213 */       map.put("Private", new BooleanSchema());
/*     */       
/* 215 */       props.setProperties(map);
/* 216 */       return props;
/*     */     }
/*     */     @Nonnull
/*     */     private static Parameter fromJSON(@Nonnull JsonElement element, @Nonnull BuilderParameters builderParameters, String parameterName) {
/*     */       BuilderExpressionStaticBooleanArray builderExpressionStaticBooleanArray;
/* 221 */       JsonObject jsonObject = BuilderBase.expectObject(element);
/* 222 */       BuilderExpression expression = BuilderExpression.fromJSON(BuilderBase.expectKey(jsonObject, "Value"), builderParameters, true);
/*     */ 
/*     */ 
/*     */       
/* 226 */       if (expression instanceof com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticEmptyArray) {
/* 227 */         if (!jsonObject.has("TypeHint")) {
/* 228 */           throw new IllegalStateException("TypeHint missing for parameter " + parameterName);
/*     */         }
/* 230 */         String type = BuilderBase.readString(jsonObject, "TypeHint");
/* 231 */         if ("STRING".equalsIgnoreCase(type)) {
/* 232 */           BuilderExpressionStaticStringArray builderExpressionStaticStringArray = BuilderExpressionStaticStringArray.INSTANCE_EMPTY;
/* 233 */         } else if ("NUMBER".equalsIgnoreCase(type)) {
/* 234 */           BuilderExpressionStaticNumberArray builderExpressionStaticNumberArray = BuilderExpressionStaticNumberArray.INSTANCE_EMPTY;
/* 235 */         } else if ("BOOLEAN".equalsIgnoreCase(type)) {
/* 236 */           builderExpressionStaticBooleanArray = BuilderExpressionStaticBooleanArray.INSTANCE_EMPTY;
/*     */         } else {
/* 238 */           throw new IllegalStateException("TypeHint must be one of STRING, NUMBER, BOOLEAN for parameter " + parameterName);
/*     */         } 
/*     */       } 
/*     */       
/* 242 */       String validate = BuilderBase.readString(jsonObject, "Validate", null);
/* 243 */       String confine = BuilderBase.readString(jsonObject, "Confine", null);
/*     */       
/* 245 */       boolean hasValidate = (validate != null);
/*     */       
/* 247 */       if (hasValidate && confine != null) {
/* 248 */         throw new IllegalStateException("Only either 'Confine' or 'Validate' allowed for parameter " + parameterName);
/*     */       }
/*     */       
/* 251 */       String code = hasValidate ? validate : confine;
/* 252 */       return new Parameter((BuilderExpression)builderExpressionStaticBooleanArray, BuilderBase.readString(jsonObject, "Description", null), code, hasValidate, BuilderBase.readBoolean(jsonObject, "Private", false));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderParameters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */