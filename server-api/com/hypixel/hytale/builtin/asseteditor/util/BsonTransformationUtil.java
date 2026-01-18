/*    */ package com.hypixel.hytale.builtin.asseteditor.util;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonArray;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ public class BsonTransformationUtil
/*    */ {
/*    */   private static void actionOnProperty(BsonDocument entity, @Nonnull String[] propertyPath, @Nonnull BiConsumer<BsonValue, String> biConsumer, boolean create) {
/* 14 */     BsonDocument bsonDocument = entity;
/* 15 */     for (int i = 0; i < propertyPath.length - 1; i++) {
/*    */       BsonDocument bsonDocument1;
/* 17 */       if (bsonDocument instanceof BsonDocument) {
/* 18 */         BsonValue jsonElement = bsonDocument.get(propertyPath[i]);
/* 19 */         if (jsonElement == null || jsonElement instanceof org.bson.BsonNull) {
/* 20 */           if (create) {
/* 21 */             if (StringUtil.isNumericString(propertyPath[i + 1])) {
/* 22 */               BsonArray bsonArray = new BsonArray();
/*    */             } else {
/* 24 */               bsonDocument1 = new BsonDocument();
/*    */             } 
/* 26 */             bsonDocument.put(propertyPath[i], (BsonValue)bsonDocument1);
/*    */           } else {
/*    */             return;
/*    */           } 
/*    */         }
/* 31 */       } else if (bsonDocument instanceof BsonArray && StringUtil.isNumericString(propertyPath[i])) {
/* 32 */         int index = Integer.parseInt(propertyPath[i]);
/* 33 */         BsonValue jsonElement = ((BsonArray)bsonDocument).get(index);
/* 34 */         if (jsonElement == null || jsonElement instanceof org.bson.BsonNull) {
/* 35 */           if (create) {
/* 36 */             if (StringUtil.isNumericString(propertyPath[i + 1])) {
/* 37 */               BsonArray bsonArray = new BsonArray();
/*    */             } else {
/* 39 */               bsonDocument1 = new BsonDocument();
/*    */             } 
/* 41 */             ((BsonArray)bsonDocument).set(index, (BsonValue)bsonDocument1);
/*    */           } else {
/*    */             return;
/*    */           } 
/*    */         }
/*    */       } else {
/* 47 */         throw new IllegalArgumentException("Element is not Object or (Array or invalid index)! " + String.join(".", propertyPath) + ", " + propertyPath[i] + ", " + String.valueOf(bsonDocument));
/*    */       } 
/* 49 */       bsonDocument = bsonDocument1;
/*    */     } 
/* 51 */     biConsumer.accept(bsonDocument, propertyPath[propertyPath.length - 1]);
/*    */   }
/*    */   
/*    */   public static void removeProperty(BsonDocument entity, @Nonnull String[] propertyPath) {
/* 55 */     actionOnProperty(entity, propertyPath, (parent, key) -> { if (parent instanceof BsonDocument) { ((BsonDocument)parent).remove(key); } else if (parent instanceof BsonArray && StringUtil.isNumericString(key)) { ((BsonArray)parent).remove(Integer.parseInt(key)); } else { throw new IllegalArgumentException("Element is not Object or (Array or invalid index)! " + key + ", " + key + ", " + String.valueOf(parent)); }  }false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setProperty(BsonDocument entity, @Nonnull String[] pathElements, BsonValue value) {
/* 67 */     actionOnProperty(entity, pathElements, (parent, key) -> { if (parent instanceof BsonDocument) { ((BsonDocument)parent).put(key, value); } else if (parent instanceof BsonArray && StringUtil.isNumericString(key)) { ((BsonArray)parent).set(Integer.parseInt(key), value); } else { throw new IllegalArgumentException("Element is not Object or (Array or invalid index)! " + key + ", " + key + ", " + String.valueOf(parent)); }  }true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insertProperty(BsonDocument entity, @Nonnull String[] pathElements, BsonValue value) {
/* 79 */     actionOnProperty(entity, pathElements, (parent, key) -> { if (parent instanceof BsonDocument) { ((BsonDocument)parent).put(key, value); } else if (parent instanceof BsonArray && StringUtil.isNumericString(key)) { ((BsonArray)parent).add(Integer.parseInt(key), value); } else { throw new IllegalArgumentException("Element is not Object or (Array or invalid index)! " + key + ", " + key + ", " + String.valueOf(parent)); }  }true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\assetedito\\util\BsonTransformationUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */