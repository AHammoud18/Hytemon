/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticString;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumHolder<E extends Enum<E>>
/*    */   extends StringHolderBase
/*    */ {
/*    */   protected List<BiConsumer<ExecutionContext, E>> enumRelationValidators;
/*    */   private E[] enumConstants;
/*    */   private E value;
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 26 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, Class<E> clazz, String name, @Nonnull BuilderParameters builderParameters) {
/* 30 */     this.enumConstants = clazz.getEnumConstants();
/* 31 */     readJSON(requiredJsonElement, name, builderParameters);
/* 32 */     if (isStatic()) {
/* 33 */       this.value = (E)BuilderBase.stringToEnum(this.expression.getString(null), (Enum[])this.enumConstants, getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, Class<E> clazz, @Nonnull E defaultValue, String name, @Nonnull BuilderParameters builderParameters) {
/* 38 */     this.enumConstants = clazz.getEnumConstants();
/* 39 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticString(defaultValue.toString()), name, builderParameters);
/* 40 */     if (isStatic()) {
/* 41 */       this.value = (E)BuilderBase.stringToEnum(this.expression.getString(null), (Enum[])this.enumConstants, getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public E get(ExecutionContext executionContext) {
/* 46 */     E value = rawGet(executionContext);
/* 47 */     validateEnumRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 51 */     return value;
/*    */   }
/*    */   
/*    */   public void addEnumRelationValidator(BiConsumer<ExecutionContext, E> validator) {
/* 55 */     if (this.enumRelationValidators == null) this.enumRelationValidators = (List<BiConsumer<ExecutionContext, E>>)new ObjectArrayList(); 
/* 56 */     this.enumRelationValidators.add(validator);
/*    */   }
/*    */   
/*    */   public E rawGet(ExecutionContext executionContext) {
/* 60 */     if (!isStatic()) {
/* 61 */       this.value = (E)BuilderBase.stringToEnum(this.expression.getString(executionContext), (Enum[])this.enumConstants, getName());
/*    */     }
/* 63 */     return this.value;
/*    */   }
/*    */   
/*    */   private void validateEnumRelations(ExecutionContext context, E value) {
/* 67 */     if (this.enumRelationValidators == null)
/*    */       return; 
/* 69 */     for (BiConsumer<ExecutionContext, E> executionContextConsumer : this.enumRelationValidators)
/* 70 */       executionContextConsumer.accept(context, value); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\EnumHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */