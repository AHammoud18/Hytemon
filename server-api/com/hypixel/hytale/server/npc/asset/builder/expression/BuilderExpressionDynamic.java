/*    */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderExpressionDynamic
/*    */   extends BuilderExpression
/*    */ {
/*    */   public static final String KEY_COMPUTE = "Compute";
/*    */   private final String expression;
/*    */   private final ExecutionContext.Instruction[] instructionSequence;
/*    */   
/*    */   public BuilderExpressionDynamic(String expression, ExecutionContext.Instruction[] instructionSequence) {
/* 28 */     this.expression = expression;
/* 29 */     this.instructionSequence = instructionSequence;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getExpression() {
/* 39 */     return this.expression;
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull ExecutionContext executionContext) {
/* 43 */     Objects.requireNonNull(executionContext, "ExecutionContext not initialised");
/* 44 */     if (executionContext.execute(this.instructionSequence) != getType()) {
/* 45 */       throw new IllegalStateException("Expression returned wrong type " + String.valueOf(executionContext.getType()) + " but expected " + 
/* 46 */           String.valueOf(getType()) + ": " + this.expression);
/*    */     }
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderExpression fromJSON(@Nonnull JsonElement jsonElement, @Nonnull BuilderParameters builderParameters) {
/* 52 */     JsonObject jsonObject = jsonElement.getAsJsonObject();
/* 53 */     JsonElement computeValue = jsonObject.get("Compute");
/* 54 */     if (computeValue == null) {
/* 55 */       throw new IllegalArgumentException("JSON expression missing 'Compute' member: " + String.valueOf(jsonElement));
/*    */     }
/*    */     
/* 58 */     String expression = BuilderBase.expectStringElement(computeValue, "Compute");
/* 59 */     ValueType type = builderParameters.compile(expression);
/*    */ 
/*    */     
/* 62 */     ExecutionContext.Operand operand = builderParameters.getConstantOperand();
/* 63 */     if (operand != null) {
/* 64 */       return BuilderExpression.fromOperand(operand);
/*    */     }
/*    */     
/* 67 */     ExecutionContext.Instruction[] instructionSequence = (ExecutionContext.Instruction[])builderParameters.getInstructions().toArray(x$0 -> new ExecutionContext.Instruction[x$0]);
/* 68 */     switch (type) { case NUMBER: 
/*    */       case STRING: 
/*    */       case BOOLEAN: 
/*    */       case NUMBER_ARRAY: 
/*    */       case STRING_ARRAY:
/*    */       
/*    */       case BOOLEAN_ARRAY:
/* 75 */        }  throw new IllegalStateException("Unable to create dynamic expression from type " + String.valueOf(type));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Schema toSchema() {
/* 81 */     ObjectSchema s = new ObjectSchema();
/* 82 */     s.setTitle("ExpressionDynamic");
/* 83 */     s.setProperties(Map.of("Compute", new StringSchema()));
/* 84 */     s.setRequired(new String[] { "Compute" });
/* 85 */     s.setAdditionalProperties(false);
/* 86 */     return (Schema)s;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Schema computableSchema(Schema toWrap) {
/* 91 */     return Schema.anyOf(new Schema[] { toWrap, toSchema() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionDynamic.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */