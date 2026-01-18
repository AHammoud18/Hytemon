/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonJavaScript;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ public class BsonJavaScriptCodec
/*    */   implements Codec<BsonJavaScript>
/*    */ {
/*    */   public BsonJavaScript decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonJavaScript(reader.readJavaScript());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonJavaScript value, EncoderContext encoderContext) {
/* 36 */     writer.writeJavaScript(value.getCode());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonJavaScript> getEncoderClass() {
/* 41 */     return BsonJavaScript.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonJavaScriptCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */