/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.ComponentContext;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InstructionContextValidator
/*    */   extends Validator
/*    */ {
/*    */   private final EnumSet<InstructionType> instructionTypes;
/*    */   private final EnumSet<ComponentContext> componentContexts;
/*    */   
/*    */   private InstructionContextValidator(EnumSet<InstructionType> instructionTypes, EnumSet<ComponentContext> componentContexts) {
/* 18 */     this.instructionTypes = instructionTypes;
/* 19 */     this.componentContexts = componentContexts;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String getErrorMessage(@Nonnull String value, @Nonnull InstructionType instructionContext, boolean instructionMatched, @Nonnull ComponentContext componentContext, boolean extraMatched, String breadcrumbs) {
/* 24 */     StringBuilder sb = (new StringBuilder(value)).append(" not valid");
/* 25 */     if (!instructionMatched) sb.append(" in instruction ").append(instructionContext.get()); 
/* 26 */     if (!extraMatched) sb.append(" in context ").append(componentContext.get()); 
/* 27 */     sb.append(" at: ").append(breadcrumbs);
/* 28 */     return sb.toString();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static InstructionContextValidator inInstructions(EnumSet<InstructionType> instructionTypes, EnumSet<ComponentContext> componentContexts) {
/* 33 */     return new InstructionContextValidator(instructionTypes, componentContexts);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\InstructionContextValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */