/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.Priority;
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
/*    */ public abstract class ScaledResponseCurve
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ScaledResponseCurve>>
/*    */ {
/*    */   public static final AssetCodecMapCodec<String, ScaledResponseCurve> CODEC;
/*    */   protected AssetExtraInfo.Data data;
/*    */   protected String id;
/*    */   
/*    */   static {
/* 27 */     CODEC = (new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data, true)).register(Priority.DEFAULT, "Default", ScaledXResponseCurve.class, ScaledXResponseCurve.CODEC);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     CODEC.register("Switch", ScaledSwitchResponseCurve.class, ScaledSwitchResponseCurve.CODEC);
/*    */   }
/*    */   
/*    */   public ScaledResponseCurve(String id) {
/*    */     this.id = id;
/*    */   }
/*    */   
/*    */   protected ScaledResponseCurve() {}
/*    */   
/*    */   public String getId() {
/*    */     return this.id;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/*    */     return "ScaledResponseCurve{data=" + String.valueOf(this.data) + ", id='" + this.id + "'}";
/*    */   }
/*    */   
/*    */   public abstract double computeY(double paramDouble);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\ScaledResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */