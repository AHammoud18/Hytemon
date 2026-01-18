/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueWrappedInfoProvider
/*    */   implements InfoProvider
/*    */ {
/*    */   @Nullable
/*    */   private final InfoProvider wrappedProvider;
/*    */   @Nonnull
/*    */   private final ParameterProvider parameterProvider;
/*    */   
/*    */   public ValueWrappedInfoProvider(@Nullable InfoProvider wrappedProvider, @Nonnull ParameterProvider parameterProvider) {
/* 18 */     this.wrappedProvider = wrappedProvider;
/* 19 */     this.parameterProvider = parameterProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IPositionProvider getPositionProvider() {
/* 25 */     return (this.wrappedProvider != null) ? this.wrappedProvider.getPositionProvider() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ParameterProvider getParameterProvider(int parameter) {
/* 31 */     ParameterProvider provider = this.parameterProvider.getParameterProvider(parameter);
/* 32 */     if (provider != null) return provider;
/*    */     
/* 34 */     return (this.wrappedProvider != null) ? this.wrappedProvider.getParameterProvider(parameter) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <E extends ExtraInfoProvider> E getExtraInfo(Class<E> clazz) {
/* 40 */     return (this.wrappedProvider != null) ? this.wrappedProvider.<E>getExtraInfo(clazz) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <E extends ExtraInfoProvider> void passExtraInfo(E provider) {
/* 45 */     if (this.wrappedProvider != null) this.wrappedProvider.passExtraInfo(provider);
/*    */   
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <E extends ExtraInfoProvider> E getPassedExtraInfo(Class<E> clazz) {
/* 51 */     return (this.wrappedProvider != null) ? this.wrappedProvider.<E>getPassedExtraInfo(clazz) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPosition() {
/* 56 */     return (this.wrappedProvider != null && this.wrappedProvider.hasPosition());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\ValueWrappedInfoProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */