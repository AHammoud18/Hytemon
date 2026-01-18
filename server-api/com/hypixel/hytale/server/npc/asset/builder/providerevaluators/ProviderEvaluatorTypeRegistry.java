/*    */ package com.hypixel.hytale.server.npc.asset.builder.providerevaluators;
/*    */ 
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.SubTypeTypeAdapterFactory;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProviderEvaluatorTypeRegistry
/*    */ {
/*    */   @Nonnull
/*    */   public static GsonBuilder registerTypes(@Nonnull GsonBuilder gsonBuilder) {
/* 15 */     SubTypeTypeAdapterFactory factory = SubTypeTypeAdapterFactory.of(ProviderEvaluator.class, "Type");
/* 16 */     factory.registerSubType(UnconditionalFeatureProviderEvaluator.class, "ProvidesFeatureUnconditionally");
/* 17 */     factory.registerSubType(UnconditionalParameterProviderEvaluator.class, "ProvidesParameterUnconditionally");
/* 18 */     gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)factory);
/* 19 */     return gsonBuilder;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\providerevaluators\ProviderEvaluatorTypeRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */