/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorEval;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorEval
/*    */   extends SensorBase {
/*    */   protected final String expression;
/*    */   @Nonnull
/*    */   protected final CompileContext compileContext;
/*    */   protected ExecutionContext.Instruction[] instructions;
/*    */   protected boolean isValid;
/*    */   
/*    */   public SensorEval(@Nonnull BuilderSensorEval builderSensorEval, @Nonnull BuilderSupport support) {
/* 30 */     super((BuilderSensorBase)builderSensorEval);
/* 31 */     this.expression = builderSensorEval.getExpression();
/* 32 */     this.compileContext = new CompileContext();
/* 33 */     this.isValid = true;
/*    */     try {
/* 35 */       ObjectArrayList<ExecutionContext.Instruction> instructions = new ObjectArrayList();
/* 36 */       StdScope scope = support.getSensorScope();
/* 37 */       ValueType valueType = compile(this.expression, scope, (List<ExecutionContext.Instruction>)instructions);
/* 38 */       if (valueType != ValueType.BOOLEAN) {
/* 39 */         this.isValid = false;
/* 40 */         throw new IllegalStateException("Expression '" + this.expression + "' must return boolean value but is:" + String.valueOf(valueType));
/*    */       } 
/* 42 */       this.instructions = (ExecutionContext.Instruction[])instructions.toArray(x$0 -> new ExecutionContext.Instruction[x$0]);
/* 43 */     } catch (RuntimeException e) {
/* 44 */       this.isValid = false;
/* 45 */       throw new RuntimeException("Error evaluating '" + this.expression + "'", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 51 */     return (super.matches(ref, role, dt, store) && this.isValid && evalBoolean(role.getEntitySupport().getSensorScope(), this.instructions));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   protected ValueType compile(@Nonnull String expression, StdScope sensorScope, List<ExecutionContext.Instruction> instructions) {
/* 60 */     return this.compileContext.compile(expression, (Scope)sensorScope, true, instructions);
/*    */   }
/*    */   
/*    */   protected boolean evalBoolean(StdScope sensorScope, @Nonnull ExecutionContext.Instruction[] instructions) {
/* 64 */     ExecutionContext executionContext = this.compileContext.getExecutionContext();
/* 65 */     if (executionContext.execute(instructions, (Scope)sensorScope) != ValueType.BOOLEAN) {
/* 66 */       throw new IllegalStateException("Expression must return boolean value");
/*    */     }
/* 68 */     return executionContext.popBoolean();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorEval.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */