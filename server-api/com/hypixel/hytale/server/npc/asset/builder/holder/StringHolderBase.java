/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StringHolderBase
/*    */   extends ValueHolder
/*    */ {
/*    */   protected List<BiConsumer<ExecutionContext, String>> relationValidators;
/*    */   
/*    */   protected StringHolderBase() {
/* 17 */     super(ValueType.STRING);
/*    */   }
/*    */   
/*    */   public void addRelationValidator(BiConsumer<ExecutionContext, String> validator) {
/* 21 */     if (this.relationValidators == null) this.relationValidators = (List<BiConsumer<ExecutionContext, String>>)new ObjectArrayList(); 
/* 22 */     this.relationValidators.add(validator);
/*    */   }
/*    */   
/*    */   protected void validateRelations(ExecutionContext executionContext, String value) {
/* 26 */     if (this.relationValidators == null)
/*    */       return; 
/* 28 */     for (BiConsumer<ExecutionContext, String> executionContextConsumer : this.relationValidators)
/* 29 */       executionContextConsumer.accept(executionContext, value); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\StringHolderBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */