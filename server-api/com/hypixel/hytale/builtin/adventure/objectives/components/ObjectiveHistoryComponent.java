/*    */ package com.hypixel.hytale.builtin.adventure.objectives.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveLineHistoryData;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class ObjectiveHistoryComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<ObjectiveHistoryComponent> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveHistoryComponent.class, ObjectiveHistoryComponent::new).append(new KeyedCodec("ObjectiveHistory", (Codec)new MapCodec((Codec)ObjectiveHistoryData.CODEC, Object2ObjectOpenHashMap::new, false)), (objectiveHistoryComponent, stringObjectiveHistoryDataMap) -> objectiveHistoryComponent.objectiveHistoryMap = stringObjectiveHistoryDataMap, objectiveHistoryComponent -> objectiveHistoryComponent.objectiveHistoryMap).add()).append(new KeyedCodec("ObjectiveLineHistory", (Codec)new MapCodec((Codec)ObjectiveLineHistoryData.CODEC, Object2ObjectOpenHashMap::new, false)), (objectiveHistoryComponent, stringObjectiveLineHistoryDataMap) -> objectiveHistoryComponent.objectiveLineHistoryMap = stringObjectiveLineHistoryDataMap, objectiveHistoryComponent -> objectiveHistoryComponent.objectiveLineHistoryMap).add()).build();
/*    */   }
/* 33 */   private Map<String, ObjectiveHistoryData> objectiveHistoryMap = (Map<String, ObjectiveHistoryData>)new Object2ObjectOpenHashMap();
/*    */   
/* 35 */   private Map<String, ObjectiveLineHistoryData> objectiveLineHistoryMap = (Map<String, ObjectiveLineHistoryData>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   public Map<String, ObjectiveHistoryData> getObjectiveHistoryMap() {
/* 38 */     return this.objectiveHistoryMap;
/*    */   }
/*    */   
/*    */   public Map<String, ObjectiveLineHistoryData> getObjectiveLineHistoryMap() {
/* 42 */     return this.objectiveLineHistoryMap;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     ObjectiveHistoryComponent component = new ObjectiveHistoryComponent();
/* 49 */     component.objectiveHistoryMap.putAll(this.objectiveHistoryMap);
/* 50 */     component.objectiveLineHistoryMap.putAll(this.objectiveLineHistoryMap);
/* 51 */     return component;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\components\ObjectiveHistoryComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */