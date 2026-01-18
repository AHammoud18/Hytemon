/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumber;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.IntValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.ObjIntConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntHolder
/*    */   extends ValueHolder
/*    */ {
/*    */   protected List<ObjIntConsumer<ExecutionContext>> relationValidators;
/*    */   protected IntValidator intValidator;
/*    */   
/*    */   public IntHolder() {
/* 25 */     super(ValueType.NUMBER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 30 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, IntValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 34 */     readJSON(requiredJsonElement, name, builderParameters);
/* 35 */     this.intValidator = validator;
/* 36 */     if (isStatic()) validate(MathUtil.floor(this.expression.getNumber(null))); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int defaultValue, IntValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 40 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticNumber(defaultValue), name, builderParameters);
/* 41 */     this.intValidator = validator;
/* 42 */     if (isStatic()) validate(MathUtil.floor(this.expression.getNumber(null))); 
/*    */   }
/*    */   
/*    */   public int get(ExecutionContext executionContext) {
/* 46 */     int value = rawGet(executionContext);
/* 47 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 51 */     return value;
/*    */   }
/*    */   
/*    */   public int rawGet(ExecutionContext executionContext) {
/* 55 */     int value = MathUtil.floor(this.expression.getNumber(executionContext));
/* 56 */     if (!isStatic()) validate(value); 
/* 57 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(int value) {
/* 61 */     if (this.intValidator != null && !this.intValidator.test(value)) {
/* 62 */       throw new IllegalStateException(this.intValidator.errorMessage(value, this.name));
/*    */     }
/*    */   }
/*    */   
/*    */   public void addRelationValidator(ObjIntConsumer<ExecutionContext> validator) {
/* 67 */     if (this.relationValidators == null) this.relationValidators = (List<ObjIntConsumer<ExecutionContext>>)new ObjectArrayList(); 
/* 68 */     this.relationValidators.add(validator);
/*    */   }
/*    */   
/*    */   protected void validateRelations(ExecutionContext executionContext, int value) {
/* 72 */     if (this.relationValidators == null)
/*    */       return; 
/* 74 */     for (ObjIntConsumer<ExecutionContext> executionContextConsumer : this.relationValidators)
/* 75 */       executionContextConsumer.accept(executionContext, value); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\IntHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */