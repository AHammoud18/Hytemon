/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.QueueProp;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class QueuePropAsset
/*    */   extends PropAsset
/*    */ {
/*    */   public static final BuilderCodec<QueuePropAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(QueuePropAsset.class, QueuePropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("Queue", (Codec)new ArrayCodec((Codec)PropAsset.CODEC, x$0 -> new PropAsset[x$0]), true), (asset, v) -> asset.propAssets = v, asset -> asset.propAssets).add()).build();
/*    */   }
/* 22 */   private PropAsset[] propAssets = new PropAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Prop build(@Nonnull PropAsset.Argument argument) {
/* 27 */     if (skip()) return Prop.noProp();
/*    */     
/* 29 */     ArrayList<Prop> propsQueue = new ArrayList<>(this.propAssets.length);
/* 30 */     for (PropAsset asset : this.propAssets) {
/* 31 */       propsQueue.add(asset.build(argument));
/*    */     }
/*    */     
/* 34 */     return (Prop)new QueueProp(propsQueue);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     for (PropAsset propAsset : this.propAssets)
/* 40 */       propAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\QueuePropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */