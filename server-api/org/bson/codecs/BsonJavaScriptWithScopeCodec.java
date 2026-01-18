/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonJavaScriptWithScope;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BsonJavaScriptWithScopeCodec
/*    */   implements Codec<BsonJavaScriptWithScope>
/*    */ {
/*    */   private final Codec<BsonDocument> documentCodec;
/*    */   
/*    */   public BsonJavaScriptWithScopeCodec(Codec<BsonDocument> documentCodec) {
/* 38 */     this.documentCodec = documentCodec;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonJavaScriptWithScope decode(BsonReader bsonReader, DecoderContext decoderContext) {
/* 43 */     String code = bsonReader.readJavaScriptWithScope();
/* 44 */     BsonDocument scope = this.documentCodec.decode(bsonReader, decoderContext);
/* 45 */     return new BsonJavaScriptWithScope(code, scope);
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonJavaScriptWithScope codeWithScope, EncoderContext encoderContext) {
/* 50 */     writer.writeJavaScriptWithScope(codeWithScope.getCode());
/* 51 */     this.documentCodec.encode(writer, codeWithScope.getScope(), encoderContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonJavaScriptWithScope> getEncoderClass() {
/* 56 */     return BsonJavaScriptWithScope.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonJavaScriptWithScopeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */