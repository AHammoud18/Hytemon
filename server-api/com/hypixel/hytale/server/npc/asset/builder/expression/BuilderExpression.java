/*     */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.hypixel.hytale.codec.schema.NamedSchema;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*     */ import com.hypixel.hytale.codec.schema.config.ArraySchema;
/*     */ import com.hypixel.hytale.codec.schema.config.BooleanSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.NumberSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
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
/*     */ public abstract class BuilderExpression
/*     */ {
/*     */   public static final String STATIC = "<STATIC>";
/*     */   
/*     */   public abstract ValueType getType();
/*     */   
/*     */   public abstract boolean isStatic();
/*     */   
/*     */   public double getNumber(ExecutionContext executionContext) {
/*  39 */     throw new IllegalStateException("BuilderExpression: Reading number is not supported");
/*     */   }
/*     */   
/*     */   public String getString(ExecutionContext executionContext) {
/*  43 */     throw new IllegalStateException("BuilderExpression: Reading string is not supported");
/*     */   }
/*     */   
/*     */   public boolean getBoolean(ExecutionContext executionContext) {
/*  47 */     throw new IllegalStateException("BuilderExpression: Reading boolean is not supported");
/*     */   }
/*     */   
/*     */   public double[] getNumberArray(ExecutionContext executionContext) {
/*  51 */     throw new IllegalStateException("BuilderExpression: Reading number array is not supported");
/*     */   }
/*     */   
/*     */   public int[] getIntegerArray(ExecutionContext executionContext) {
/*  55 */     throw new IllegalStateException("BuilderExpression: Reading integer is not supported");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String[] getStringArray(ExecutionContext executionContext) {
/*  60 */     throw new IllegalStateException("BuilderExpression: Reading string array is not supported");
/*     */   }
/*     */   
/*     */   public boolean[] getBooleanArray(ExecutionContext executionContext) {
/*  64 */     throw new IllegalStateException("BuilderExpression: Reading boolean array is not supported");
/*     */   }
/*     */   
/*     */   public void addToScope(String name, StdScope scope) {
/*  68 */     throw new IllegalStateException("This type of builder expression cannot be added to a scope");
/*     */   }
/*     */   
/*     */   public void updateScope(StdScope scope, String name, ExecutionContext executionContext) {
/*  72 */     throw new IllegalStateException("This type of builder expression cannot update a scope");
/*     */   }
/*     */   
/*     */   public String getExpression() {
/*  76 */     return "<STATIC>";
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderExpression fromOperand(@Nonnull ExecutionContext.Operand operand) {
/*  81 */     switch (operand.type) { case NUMBER: 
/*     */       case STRING: 
/*     */       case BOOLEAN: 
/*     */       case EMPTY_ARRAY: 
/*     */       case NUMBER_ARRAY: 
/*     */       case STRING_ARRAY:
/*     */       
/*     */       case BOOLEAN_ARRAY:
/*  89 */        }  throw new IllegalStateException("Operand cannot be converted to builder expression");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderExpression fromJSON(@Nonnull JsonElement jsonElement, @Nonnull BuilderParameters builderParameters, boolean constantsOnly) {
/*  95 */     BuilderExpression builderExpression = fromJSON(jsonElement, builderParameters);
/*     */     
/*  97 */     if (constantsOnly && !builderExpression.isStatic()) {
/*  98 */       throw new IllegalArgumentException("Only constant string, number or boolean or arrays allowed, found: " + String.valueOf(jsonElement));
/*     */     }
/* 100 */     return builderExpression;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderExpression fromJSON(@Nonnull JsonElement jsonElement, @Nonnull BuilderParameters builderParameters, ValueType expectedType) {
/* 105 */     BuilderExpression builderExpression = fromJSON(jsonElement, builderParameters);
/*     */     
/* 107 */     if (!ValueType.isAssignableType(builderExpression.getType(), expectedType)) {
/* 108 */       throw new IllegalStateException("Expression type mismatch. Got " + String.valueOf(builderExpression.getType()) + " but expected " + String.valueOf(expectedType) + " from: " + String.valueOf(jsonElement));
/*     */     }
/*     */     
/* 111 */     return builderExpression;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderExpression fromJSON(@Nonnull JsonElement jsonElement, @Nonnull BuilderParameters builderParameters) {
/* 116 */     if (jsonElement.isJsonObject())
/* 117 */       return BuilderExpressionDynamic.fromJSON(jsonElement, builderParameters); 
/* 118 */     if (jsonElement.isJsonPrimitive()) {
/* 119 */       BuilderExpression jsonPrimitive = readJSONPrimitive(jsonElement);
/* 120 */       if (jsonPrimitive != null) return jsonPrimitive; 
/* 121 */     } else if (jsonElement.isJsonArray()) {
/* 122 */       BuilderExpression result = readStaticArray(jsonElement);
/* 123 */       if (result != null) return result; 
/*     */     } 
/* 125 */     throw new IllegalArgumentException("Illegal JSON value for expression: " + String.valueOf(jsonElement));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BuilderExpression readJSONPrimitive(@Nonnull JsonElement jsonElement) {
/* 130 */     JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
/* 131 */     if (jsonPrimitive.isString())
/* 132 */       return new BuilderExpressionStaticString(jsonPrimitive.getAsString()); 
/* 133 */     if (jsonPrimitive.isBoolean())
/* 134 */       return new BuilderExpressionStaticBoolean(jsonPrimitive.getAsBoolean()); 
/* 135 */     if (jsonPrimitive.isNumber()) {
/* 136 */       return new BuilderExpressionStaticNumber(jsonPrimitive.getAsDouble());
/*     */     }
/* 138 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BuilderExpression readStaticArray(@Nonnull JsonElement jsonElement) {
/* 143 */     JsonArray jsonArray = jsonElement.getAsJsonArray();
/* 144 */     if (jsonArray.isEmpty()) {
/* 145 */       return BuilderExpressionStaticEmptyArray.INSTANCE;
/*     */     }
/*     */     
/* 148 */     JsonElement firstElement = jsonArray.get(0);
/* 149 */     BuilderExpression result = null;
/* 150 */     if (firstElement.isJsonPrimitive()) {
/* 151 */       JsonPrimitive jsonPrimitive = firstElement.getAsJsonPrimitive();
/*     */       
/* 153 */       if (jsonPrimitive.isString()) {
/* 154 */         result = BuilderExpressionStaticStringArray.fromJSON(jsonArray);
/* 155 */       } else if (jsonPrimitive.isBoolean()) {
/* 156 */         result = BuilderExpressionStaticBooleanArray.fromJSON(jsonArray);
/* 157 */       } else if (jsonPrimitive.isNumber()) {
/* 158 */         result = BuilderExpressionStaticNumberArray.fromJSON(jsonArray);
/*     */       } 
/*     */     } 
/* 161 */     return result;
/*     */   }
/*     */   
/*     */   public void compile(BuilderParameters builderParameters) {}
/*     */   
/*     */   @Nonnull
/*     */   public static Schema toSchema(@Nonnull SchemaContext context) {
/* 168 */     return context.refDefinition(SchemaGenerator.INSTANCE);
/*     */   }
/*     */   
/*     */   private static class SchemaGenerator implements SchemaConvertable<Void>, NamedSchema {
/*     */     @Nonnull
/* 173 */     public static SchemaGenerator INSTANCE = new SchemaGenerator();
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String getSchemaName() {
/* 178 */       return "NPC:Type:BuilderExpression";
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Schema toSchema(@Nonnull SchemaContext context) {
/* 184 */       Schema s = new Schema();
/* 185 */       s.setTitle("Expression");
/* 186 */       s.setAnyOf(new Schema[] { (Schema)new ArraySchema(), (Schema)new NumberSchema(), (Schema)new StringSchema(), (Schema)new BooleanSchema(), 
/*     */ 
/*     */ 
/*     */             
/* 190 */             BuilderExpressionDynamic.toSchema() });
/* 191 */       return s;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpression.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */