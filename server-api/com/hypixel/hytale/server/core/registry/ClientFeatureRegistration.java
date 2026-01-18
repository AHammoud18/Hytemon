/*    */ package com.hypixel.hytale.server.core.registry;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.setup.ClientFeature;
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import com.hypixel.hytale.server.core.client.ClientFeatureHandler;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientFeatureRegistration
/*    */   extends Registration
/*    */ {
/*    */   private final ClientFeature feature;
/*    */   
/*    */   public ClientFeatureRegistration(@Nonnull ClientFeatureRegistration registration, BooleanSupplier isEnabled, Runnable unregister) {
/* 21 */     this(registration.feature, isEnabled, unregister);
/*    */   }
/*    */   
/*    */   public ClientFeatureRegistration(ClientFeature feature) {
/* 25 */     super(() -> true, () -> ClientFeatureHandler.unregister(feature));
/* 26 */     this.feature = feature;
/*    */   }
/*    */   
/*    */   public ClientFeatureRegistration(ClientFeature feature, BooleanSupplier isEnabled, Runnable unregister) {
/* 30 */     super(isEnabled, unregister);
/* 31 */     this.feature = feature;
/*    */   }
/*    */   
/*    */   public ClientFeature getFeature() {
/* 35 */     return this.feature;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\registry\ClientFeatureRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */