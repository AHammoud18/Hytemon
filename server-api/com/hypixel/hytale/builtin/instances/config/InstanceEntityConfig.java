/*    */ package com.hypixel.hytale.builtin.instances.config;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class InstanceEntityConfig
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final String ID = "Instance";
/*    */   public static final BuilderCodec<InstanceEntityConfig> CODEC;
/*    */   private WorldReturnPoint returnPoint;
/*    */   private transient WorldReturnPoint returnPointOverride;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(InstanceEntityConfig.class, InstanceEntityConfig::new).appendInherited(new KeyedCodec("ReturnPoint", (Codec)WorldReturnPoint.CODEC), (o, i) -> o.returnPoint = i, o -> o.returnPoint, (o, p) -> o.returnPoint = p.returnPoint).add()).build();
/*    */   }
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, InstanceEntityConfig> getComponentType() {
/* 28 */     return InstancesPlugin.get().getInstanceEntityConfigComponentType();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static InstanceEntityConfig ensureAndGet(@Nonnull Holder<EntityStore> holder) {
/* 33 */     ComponentType<EntityStore, InstanceEntityConfig> type = getComponentType();
/* 34 */     return (InstanceEntityConfig)holder.ensureAndGetComponent(type);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static InstanceEntityConfig removeAndGet(@Nonnull Holder<EntityStore> holder) {
/* 39 */     ComponentType<EntityStore, InstanceEntityConfig> type = getComponentType();
/* 40 */     InstanceEntityConfig component = (InstanceEntityConfig)holder.getComponent(type);
/* 41 */     holder.removeComponent(type);
/* 42 */     return component;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldReturnPoint getReturnPoint() {
/* 55 */     return this.returnPoint;
/*    */   }
/*    */   
/*    */   public void setReturnPoint(WorldReturnPoint returnPoint) {
/* 59 */     this.returnPoint = returnPoint;
/*    */   }
/*    */   
/*    */   public WorldReturnPoint getReturnPointOverride() {
/* 63 */     return this.returnPointOverride;
/*    */   }
/*    */   
/*    */   public void setReturnPointOverride(WorldReturnPoint returnPointOverride) {
/* 67 */     this.returnPointOverride = returnPointOverride;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public InstanceEntityConfig clone() {
/* 73 */     InstanceEntityConfig v = new InstanceEntityConfig();
/* 74 */     v.returnPoint = v.returnPoint.clone();
/* 75 */     v.returnPointOverride = v.returnPointOverride.clone();
/* 76 */     return v;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\config\InstanceEntityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */