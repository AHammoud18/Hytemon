/*    */ package com.hypixel.hytale.component.data.unknown;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ public class UnknownComponents<ECS_TYPE> implements Component<ECS_TYPE> {
/* 19 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */ 
/*    */   
/*    */   public static final String ID = "Unknown";
/*    */   
/*    */   public static final BuilderCodec<UnknownComponents> CODEC;
/*    */   
/*    */   private Map<String, BsonDocument> unknownComponents;
/*    */ 
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UnknownComponents.class, UnknownComponents::new).addField(new KeyedCodec("Components", (Codec)new MapCodec((Codec)Codec.BSON_DOCUMENT, Object2ObjectOpenHashMap::new, false)), (o, map) -> o.unknownComponents = map, o -> o.unknownComponents)).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public UnknownComponents() {
/* 35 */     this.unknownComponents = (Map<String, BsonDocument>)new Object2ObjectOpenHashMap();
/*    */   }
/*    */   
/*    */   public UnknownComponents(Map<String, BsonDocument> unknownComponents) {
/* 39 */     this.unknownComponents = unknownComponents;
/*    */   }
/*    */   
/*    */   public void addComponent(String componentId, Component<ECS_TYPE> component, @Nonnull Codec<Component<ECS_TYPE>> codec) {
/* 43 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 44 */     BsonValue bsonValue = codec.encode(component, extraInfo);
/* 45 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*    */     
/* 47 */     this.unknownComponents.put(componentId, bsonValue.asDocument());
/*    */   }
/*    */   
/*    */   public void addComponent(String componentId, @Nonnull TempUnknownComponent<ECS_TYPE> component) {
/* 51 */     this.unknownComponents.put(componentId, component.getDocument());
/*    */   }
/*    */   
/*    */   public boolean contains(String componentId) {
/* 55 */     return this.unknownComponents.containsKey(componentId);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends Component<ECS_TYPE>> T removeComponent(String componentId, @Nonnull Codec<T> codec) {
/* 60 */     BsonDocument bsonDocument = this.unknownComponents.remove(componentId);
/* 61 */     if (bsonDocument == null) return null;
/*    */     
/* 63 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 64 */     Component component = (Component)codec.decode((BsonValue)bsonDocument, extraInfo);
/* 65 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/* 66 */     return (T)component;
/*    */   }
/*    */   
/*    */   public Map<String, BsonDocument> getUnknownComponents() {
/* 70 */     return this.unknownComponents;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<ECS_TYPE> clone() {
/* 76 */     return new UnknownComponents((Map<String, BsonDocument>)new Object2ObjectOpenHashMap(this.unknownComponents));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dat\\unknown\UnknownComponents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */