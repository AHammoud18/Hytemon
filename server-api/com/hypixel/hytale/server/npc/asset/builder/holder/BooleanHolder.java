/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticBoolean;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BooleanHolder
/*    */   extends ValueHolder
/*    */ {
/*    */   protected List<BiConsumer<ExecutionContext, Boolean>> relationValidators;
/*    */   
/*    */   public BooleanHolder() {
/* 22 */     super(ValueType.BOOLEAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, String name, @Nonnull BuilderParameters builderParameters) {
/* 27 */     super.readJSON(requiredJsonElement, name, builderParameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 32 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, boolean defaultValue, String name, @Nonnull BuilderParameters builderParameters) {
/* 36 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticBoolean(defaultValue), name, builderParameters);
/*    */   }
/*    */   
/*    */   public boolean get(ExecutionContext executionContext) {
/* 40 */     boolean value = rawGet(executionContext);
/* 41 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 45 */     return value;
/*    */   }
/*    */   
/*    */   public boolean rawGet(ExecutionContext executionContext) {
/* 49 */     return this.expression.getBoolean(executionContext);
/*    */   }
/*    */   
/*    */   public void addRelationValidator(BiConsumer<ExecutionContext, Boolean> validator) {
/* 53 */     if (this.relationValidators == null) this.relationValidators = (List<BiConsumer<ExecutionContext, Boolean>>)new ObjectArrayList(); 
/* 54 */     this.relationValidators.add(validator);
/*    */   }
/*    */   
/*    */   protected void validateRelations(ExecutionContext executionContext, boolean value) {
/* 58 */     if (this.relationValidators == null)
/*    */       return; 
/* 60 */     for (BiConsumer<ExecutionContext, Boolean> executionContextConsumer : this.relationValidators)
/* 61 */       executionContextConsumer.accept(executionContext, Boolean.valueOf(value)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\BooleanHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */