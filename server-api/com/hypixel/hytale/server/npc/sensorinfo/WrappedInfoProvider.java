/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class WrappedInfoProvider
/*    */   implements InfoProvider
/*    */ {
/*    */   @Nonnull
/*    */   private final List<Sensor> sensors;
/*    */   @Nullable
/*    */   private IPositionProvider positionMatch;
/*    */   protected ExtraInfoProvider passedExtraInfo;
/*    */   
/*    */   public WrappedInfoProvider() {
/* 29 */     this.sensors = (List<Sensor>)new ObjectArrayList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WrappedInfoProvider(Sensor[] sensors) {
/* 38 */     this.sensors = List.of(sensors);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <E extends ExtraInfoProvider> E getExtraInfo(Class<E> clazz) {
/* 44 */     for (int i = 0; i < this.sensors.size(); i++) {
/* 45 */       InfoProvider info = ((Sensor)this.sensors.get(i)).getSensorInfo();
/* 46 */       if (info != null) {
/*    */         
/* 48 */         E specificInfo = info.getExtraInfo(clazz);
/* 49 */         if (specificInfo != null) return specificInfo; 
/*    */       } 
/* 51 */     }  return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <E extends ExtraInfoProvider> void passExtraInfo(E provider) {
/* 56 */     this.passedExtraInfo = (ExtraInfoProvider)provider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends ExtraInfoProvider> E getPassedExtraInfo(Class<E> clazz) {
/* 62 */     return (E)this.passedExtraInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPosition() {
/* 67 */     return (this.positionMatch != null && this.positionMatch.hasPosition());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IPositionProvider getPositionProvider() {
/* 73 */     return this.positionMatch;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ParameterProvider getParameterProvider(int parameter) {
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   public void clearMatches() {
/* 84 */     this.sensors.clear();
/*    */   }
/*    */   
/*    */   public void addMatch(Sensor sensor) {
/* 88 */     this.sensors.add(sensor);
/*    */   }
/*    */   
/*    */   public void clearPositionMatch() {
/* 92 */     this.positionMatch = null;
/*    */   }
/*    */   
/*    */   public void setPositionMatch(IPositionProvider provider) {
/* 96 */     this.positionMatch = provider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\WrappedInfoProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */