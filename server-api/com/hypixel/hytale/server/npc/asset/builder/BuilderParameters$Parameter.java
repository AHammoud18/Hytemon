/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.config.BooleanSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticBooleanArray;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumberArray;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticStringArray;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Parameter
/*     */ {
/*     */   public static final String KEY_VALUE = "Value";
/*     */   public static final String KEY_TYPE_HINT = "TypeHint";
/*     */   public static final String KEY_VALIDATE = "Validate";
/*     */   public static final String KEY_CONFINE = "Confine";
/*     */   public static final String KEY_DESCRIPTION = "Description";
/*     */   public static final String KEY_PRIVATE = "Private";
/*     */   private final BuilderExpression expression;
/*     */   private final String description;
/*     */   private final String code;
/*     */   private List<ExecutionContext.Instruction> instructionList;
/*     */   private final boolean isValidation;
/*     */   private final boolean isPrivate;
/*     */   
/*     */   public Parameter(BuilderExpression expression, String description, String code, boolean isValidation, boolean isPrivate) {
/* 179 */     this.expression = expression;
/* 180 */     this.description = description;
/* 181 */     this.code = code;
/* 182 */     this.isValidation = isValidation;
/* 183 */     this.isPrivate = isPrivate;
/*     */   }
/*     */   
/*     */   public BuilderExpression getExpression() {
/* 187 */     return this.expression;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 191 */     return this.description;
/*     */   }
/*     */   
/*     */   public boolean isValidation() {
/* 195 */     return this.isValidation;
/*     */   }
/*     */   
/*     */   public boolean isPrivate() {
/* 199 */     return this.isPrivate;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ObjectSchema toSchema(@Nonnull SchemaContext context) {
/* 204 */     ObjectSchema props = new ObjectSchema();
/* 205 */     props.setTitle("Parameter");
/* 206 */     LinkedHashMap<String, Schema> map = new LinkedHashMap<>();
/*     */     
/* 208 */     map.put("Value", BuilderExpression.toSchema(context));
/* 209 */     map.put("TypeHint", new StringSchema());
/* 210 */     map.put("Validate", new StringSchema());
/* 211 */     map.put("Confine", new StringSchema());
/* 212 */     map.put("Description", new StringSchema());
/* 213 */     map.put("Private", new BooleanSchema());
/*     */     
/* 215 */     props.setProperties(map);
/* 216 */     return props;
/*     */   }
/*     */   @Nonnull
/*     */   private static Parameter fromJSON(@Nonnull JsonElement element, @Nonnull BuilderParameters builderParameters, String parameterName) {
/*     */     BuilderExpressionStaticBooleanArray builderExpressionStaticBooleanArray;
/* 221 */     JsonObject jsonObject = BuilderBase.expectObject(element);
/* 222 */     BuilderExpression expression = BuilderExpression.fromJSON(BuilderBase.expectKey(jsonObject, "Value"), builderParameters, true);
/*     */ 
/*     */ 
/*     */     
/* 226 */     if (expression instanceof com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticEmptyArray) {
/* 227 */       if (!jsonObject.has("TypeHint")) {
/* 228 */         throw new IllegalStateException("TypeHint missing for parameter " + parameterName);
/*     */       }
/* 230 */       String type = BuilderBase.readString(jsonObject, "TypeHint");
/* 231 */       if ("STRING".equalsIgnoreCase(type)) {
/* 232 */         BuilderExpressionStaticStringArray builderExpressionStaticStringArray = BuilderExpressionStaticStringArray.INSTANCE_EMPTY;
/* 233 */       } else if ("NUMBER".equalsIgnoreCase(type)) {
/* 234 */         BuilderExpressionStaticNumberArray builderExpressionStaticNumberArray = BuilderExpressionStaticNumberArray.INSTANCE_EMPTY;
/* 235 */       } else if ("BOOLEAN".equalsIgnoreCase(type)) {
/* 236 */         builderExpressionStaticBooleanArray = BuilderExpressionStaticBooleanArray.INSTANCE_EMPTY;
/*     */       } else {
/* 238 */         throw new IllegalStateException("TypeHint must be one of STRING, NUMBER, BOOLEAN for parameter " + parameterName);
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     String validate = BuilderBase.readString(jsonObject, "Validate", null);
/* 243 */     String confine = BuilderBase.readString(jsonObject, "Confine", null);
/*     */     
/* 245 */     boolean hasValidate = (validate != null);
/*     */     
/* 247 */     if (hasValidate && confine != null) {
/* 248 */       throw new IllegalStateException("Only either 'Confine' or 'Validate' allowed for parameter " + parameterName);
/*     */     }
/*     */     
/* 251 */     String code = hasValidate ? validate : confine;
/* 252 */     return new Parameter((BuilderExpression)builderExpressionStaticBooleanArray, BuilderBase.readString(jsonObject, "Description", null), code, hasValidate, BuilderBase.readBoolean(jsonObject, "Private", false));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderParameters$Parameter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */