/*    */ package com.hypixel.hytale.server.core.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bson.BsonArray;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonString;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenerateSchemaEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   protected final Map<String, Schema> schemas;
/*    */   protected final SchemaContext context;
/*    */   protected final BsonDocument vsCodeConfig;
/*    */   
/*    */   public GenerateSchemaEvent(Map<String, Schema> schemas, SchemaContext context, BsonDocument vsCodeConfig) {
/* 27 */     this.schemas = schemas;
/* 28 */     this.context = context;
/* 29 */     this.vsCodeConfig = vsCodeConfig;
/*    */   }
/*    */   
/*    */   public SchemaContext getContext() {
/* 33 */     return this.context;
/*    */   }
/*    */   
/*    */   public BsonDocument getVsCodeConfig() {
/* 37 */     return this.vsCodeConfig;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSchemaLink(String name, @Nonnull List<String> paths, @Nullable String extension) {
/* 42 */     BsonDocument config = new BsonDocument();
/* 43 */     config.put("fileMatch", (BsonValue)new BsonArray((List)paths.stream().map(v -> new BsonString("/Server/" + v)).collect(Collectors.toList())));
/* 44 */     config.put("url", (BsonValue)new BsonString("./Schema/" + name + ".json"));
/* 45 */     this.vsCodeConfig.getArray("json.schemas").add((BsonValue)config);
/* 46 */     if (extension != null && !extension.equals(".json")) {
/* 47 */       this.vsCodeConfig.getDocument("files.associations").put("*" + extension, (BsonValue)new BsonString("json"));
/*    */     }
/*    */   }
/*    */   
/*    */   public void addSchema(String fileName, Schema schema) {
/* 52 */     this.schemas.put(fileName, schema);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\GenerateSchemaEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */