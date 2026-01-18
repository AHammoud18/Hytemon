/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.Document;
/*    */ import org.bson.types.CodeWithScope;
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
/*    */ public class CodeWithScopeCodec
/*    */   implements Codec<CodeWithScope>
/*    */ {
/*    */   private final Codec<Document> documentCodec;
/*    */   
/*    */   public CodeWithScopeCodec(Codec<Document> documentCodec) {
/* 38 */     this.documentCodec = documentCodec;
/*    */   }
/*    */ 
/*    */   
/*    */   public CodeWithScope decode(BsonReader bsonReader, DecoderContext decoderContext) {
/* 43 */     String code = bsonReader.readJavaScriptWithScope();
/* 44 */     Document scope = this.documentCodec.decode(bsonReader, decoderContext);
/* 45 */     return new CodeWithScope(code, scope);
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, CodeWithScope codeWithScope, EncoderContext encoderContext) {
/* 50 */     writer.writeJavaScriptWithScope(codeWithScope.getCode());
/* 51 */     this.documentCodec.encode(writer, codeWithScope.getScope(), encoderContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<CodeWithScope> getEncoderClass() {
/* 56 */     return CodeWithScope.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\CodeWithScopeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */