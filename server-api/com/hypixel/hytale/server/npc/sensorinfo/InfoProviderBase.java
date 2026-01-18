/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InfoProviderBase
/*    */   implements InfoProvider
/*    */ {
/*    */   protected final ParameterProvider parameterProvider;
/*    */   @Nullable
/*    */   protected final Map<Class<? extends ExtraInfoProvider>, ExtraInfoProvider> extraProviders;
/*    */   protected ExtraInfoProvider passedExtraInfo;
/*    */   
/*    */   public InfoProviderBase() {
/* 24 */     this(null);
/*    */   }
/*    */   
/*    */   public InfoProviderBase(ParameterProvider parameterProvider) {
/* 28 */     this.parameterProvider = parameterProvider;
/* 29 */     this.extraProviders = null;
/*    */   }
/*    */   
/*    */   public InfoProviderBase(ParameterProvider parameterProvider, @Nonnull ExtraInfoProvider... providers) {
/* 33 */     this.parameterProvider = parameterProvider;
/* 34 */     this.extraProviders = new HashMap<>();
/* 35 */     for (ExtraInfoProvider provider : providers) {
/* 36 */       if (this.extraProviders.put(provider.getType(), provider) != null) {
/* 37 */         throw new IllegalArgumentException("More than one type of " + provider.getType().getSimpleName() + " provider registered!");
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ParameterProvider getParameterProvider(int parameter) {
/* 45 */     if (this.parameterProvider == null) return null; 
/* 46 */     return this.parameterProvider.getParameterProvider(parameter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <E extends ExtraInfoProvider> E getExtraInfo(Class<E> clazz) {
/* 54 */     if (this.extraProviders == null) return null; 
/* 55 */     return (E)this.extraProviders.get(clazz);
/*    */   }
/*    */ 
/*    */   
/*    */   public <E extends ExtraInfoProvider> void passExtraInfo(E provider) {
/* 60 */     this.passedExtraInfo = (ExtraInfoProvider)provider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends ExtraInfoProvider> E getPassedExtraInfo(Class<E> clazz) {
/* 66 */     return (E)this.passedExtraInfo;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\InfoProviderBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */