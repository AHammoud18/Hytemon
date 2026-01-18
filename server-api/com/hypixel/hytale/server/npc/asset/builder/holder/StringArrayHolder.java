/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringArrayHolder
/*    */   extends ArrayHolder
/*    */ {
/*    */   protected StringArrayValidator stringArrayValidator;
/*    */   protected List<BiConsumer<ExecutionContext, String[]>> relationValidators;
/*    */   
/*    */   public StringArrayHolder() {
/* 25 */     super(ValueType.STRING_ARRAY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 30 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, StringArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 34 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 35 */     this.stringArrayValidator = validator;
/* 36 */     if (isStatic()) validate(this.expression.getStringArray(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, String[] defaultValue, StringArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 40 */     readJSON(optionalJsonElement, minLength, maxLength, defaultValue, name, builderParameters);
/* 41 */     this.stringArrayValidator = validator;
/* 42 */     if (isStatic()) validate(this.expression.getStringArray(null)); 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String[] get(ExecutionContext executionContext) {
/* 47 */     String[] value = rawGet(executionContext);
/*    */     
/* 49 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 53 */     return value;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String[] rawGet(ExecutionContext executionContext) {
/* 58 */     String[] value = this.expression.getStringArray(executionContext);
/* 59 */     if (!isStatic()) validate(value); 
/* 60 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(@Nullable String[] value) {
/* 64 */     if (value != null) validateLength(value.length); 
/* 65 */     if (this.stringArrayValidator != null && !this.stringArrayValidator.test(value)) {
/* 66 */       throw new IllegalStateException(this.stringArrayValidator.errorMessage(this.name, value));
/*    */     }
/*    */   }
/*    */   
/*    */   public void addRelationValidator(BiConsumer<ExecutionContext, String[]> validator) {
/* 71 */     if (this.relationValidators == null) this.relationValidators = (List<BiConsumer<ExecutionContext, String[]>>)new ObjectArrayList(); 
/* 72 */     this.relationValidators.add(validator);
/*    */   }
/*    */   
/*    */   protected void validateRelations(ExecutionContext executionContext, String[] value) {
/* 76 */     if (this.relationValidators == null)
/*    */       return; 
/* 78 */     for (BiConsumer<ExecutionContext, String[]> executionContextConsumer : this.relationValidators)
/* 79 */       executionContextConsumer.accept(executionContext, value); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\StringArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */