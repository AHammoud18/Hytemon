/*    */ package com.hypixel.hytale.server.npc.asset.builder.providerevaluators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnconditionalParameterProviderEvaluator
/*    */   implements ParameterProviderEvaluator
/*    */ {
/* 13 */   private final Map<String, ParameterType> parameters = new HashMap<>();
/*    */   
/*    */   public UnconditionalParameterProviderEvaluator(@Nonnull String[] parameters, @Nonnull ParameterType[] types) {
/* 16 */     if (parameters.length != types.length) throw new IllegalArgumentException("Different number of parameters to types"); 
/* 17 */     for (int i = 0; i < parameters.length; i++) {
/* 18 */       this.parameters.put(parameters[i], types[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasParameter(String parameter, ParameterType type) {
/* 24 */     return (this.parameters.get(parameter) == type);
/*    */   }
/*    */   
/*    */   public void resolveReferences(BuilderManager builderManager) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\providerevaluators\UnconditionalParameterProviderEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */