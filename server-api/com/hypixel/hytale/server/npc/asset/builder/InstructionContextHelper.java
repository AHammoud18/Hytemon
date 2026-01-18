/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InstructionContextHelper
/*    */ {
/*    */   private final InstructionType context;
/*    */   private ComponentContext componentContext;
/*    */   private List<BiConsumer<InstructionType, ComponentContext>> componentContextEvaluators;
/*    */   
/*    */   public InstructionContextHelper(InstructionType context) {
/* 22 */     this.context = context;
/*    */   }
/*    */   
/*    */   public boolean isComponent() {
/* 26 */     return (this.context == InstructionType.Component);
/*    */   }
/*    */   
/*    */   public void setComponentContext(ComponentContext context) {
/* 30 */     this.componentContext = context;
/*    */   }
/*    */   
/*    */   public boolean isInCorrectInstruction(@Nonnull EnumSet<InstructionType> validTypes) {
/* 34 */     return validTypes.contains(this.context);
/*    */   }
/*    */   
/*    */   public static boolean isInCorrectInstruction(@Nonnull EnumSet<InstructionType> validTypes, InstructionType instructionContext) {
/* 38 */     return validTypes.contains(instructionContext);
/*    */   }
/*    */   
/*    */   public boolean extraContextMatches(@Nullable EnumSet<ComponentContext> contexts) {
/* 42 */     return (contexts == null || contexts.contains(this.componentContext));
/*    */   }
/*    */   
/*    */   public static boolean extraContextMatches(@Nullable EnumSet<ComponentContext> validContexts, ComponentContext context) {
/* 46 */     return (validContexts == null || validContexts.contains(context));
/*    */   }
/*    */   
/*    */   public void addComponentContextEvaluator(BiConsumer<InstructionType, ComponentContext> evaluator) {
/* 50 */     if (this.componentContextEvaluators == null) this.componentContextEvaluators = (List<BiConsumer<InstructionType, ComponentContext>>)new ObjectArrayList();
/*    */     
/* 52 */     this.componentContextEvaluators.add(evaluator);
/*    */   }
/*    */   
/*    */   public void validateComponentContext(InstructionType instructionContext, ComponentContext componentContext) {
/* 56 */     if (!isComponent()) throw new IllegalStateException("Calling validateComponentContext on a InstructionContextHelper that is not part of a component!"); 
/* 57 */     if (this.componentContextEvaluators == null)
/*    */       return; 
/* 59 */     for (BiConsumer<InstructionType, ComponentContext> evaluator : this.componentContextEvaluators) {
/* 60 */       evaluator.accept(instructionContext, componentContext);
/*    */     }
/*    */   }
/*    */   
/*    */   public InstructionType getInstructionContext() {
/* 65 */     return this.context;
/*    */   }
/*    */   
/*    */   public ComponentContext getComponentContext() {
/* 69 */     return this.componentContext;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\InstructionContextHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */