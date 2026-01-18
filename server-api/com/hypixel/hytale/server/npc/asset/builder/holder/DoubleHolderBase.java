/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumber;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.ObjDoubleConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public abstract class DoubleHolderBase
/*    */   extends ValueHolder
/*    */ {
/*    */   protected List<ObjDoubleConsumer<ExecutionContext>> relationValidators;
/*    */   protected DoubleValidator doubleValidator;
/*    */   
/*    */   protected DoubleHolderBase() {
/* 23 */     super(ValueType.NUMBER);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, DoubleValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 27 */     readJSON(requiredJsonElement, name, builderParameters);
/* 28 */     this.doubleValidator = validator;
/* 29 */     if (isStatic()) validate(this.expression.getNumber(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, double defaultValue, DoubleValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 33 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticNumber(defaultValue), name, builderParameters);
/* 34 */     this.doubleValidator = validator;
/* 35 */     if (isStatic()) validate(this.expression.getNumber(null)); 
/*    */   }
/*    */   
/*    */   public void addRelationValidator(ObjDoubleConsumer<ExecutionContext> validator) {
/* 39 */     if (this.relationValidators == null) this.relationValidators = (List<ObjDoubleConsumer<ExecutionContext>>)new ObjectArrayList(); 
/* 40 */     this.relationValidators.add(validator);
/*    */   }
/*    */   
/*    */   protected void validateRelations(ExecutionContext executionContext, double value) {
/* 44 */     if (this.relationValidators == null)
/*    */       return; 
/* 46 */     for (ObjDoubleConsumer<ExecutionContext> executionContextConsumer : this.relationValidators) {
/* 47 */       executionContextConsumer.accept(executionContext, value);
/*    */     }
/*    */   }
/*    */   
/*    */   public double rawGet(ExecutionContext executionContext) {
/* 52 */     double value = this.expression.getNumber(executionContext);
/* 53 */     if (!isStatic()) validate(value); 
/* 54 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(double value) {
/* 58 */     if (this.doubleValidator != null && !this.doubleValidator.test(value))
/* 59 */       throw new IllegalStateException(this.doubleValidator.errorMessage(value, this.name)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\DoubleHolderBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */