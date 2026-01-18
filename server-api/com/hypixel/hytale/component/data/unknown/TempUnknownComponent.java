/*    */ package com.hypixel.hytale.component.data.unknown;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class TempUnknownComponent<ECS_TYPE> implements Component<ECS_TYPE> {
/*    */   static {
/* 12 */     COMPONENT_CODEC = (Codec<Component>)new FunctionCodec((Codec)Codec.BSON_DOCUMENT, TempUnknownComponent::new, component -> ((TempUnknownComponent)component).document);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<Component> COMPONENT_CODEC;
/*    */   
/*    */   private BsonDocument document;
/*    */   
/*    */   public TempUnknownComponent(BsonDocument document) {
/* 21 */     this.document = document;
/*    */   }
/*    */   
/*    */   public BsonDocument getDocument() {
/* 25 */     return this.document;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<ECS_TYPE> clone() {
/* 31 */     return new TempUnknownComponent(this.document.clone());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dat\\unknown\TempUnknownComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */