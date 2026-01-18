/*    */ package com.google.gson.internal.sql;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
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
/*    */ class SqlTimestampTypeAdapter
/*    */   extends TypeAdapter<Timestamp>
/*    */ {
/* 31 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*    */       {
/* 36 */         if (typeToken.getRawType() == Timestamp.class) {
/* 37 */           TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
/* 38 */           return new SqlTimestampTypeAdapter(dateTypeAdapter);
/*    */         } 
/* 40 */         return null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final TypeAdapter<Date> dateTypeAdapter;
/*    */   
/*    */   private SqlTimestampTypeAdapter(TypeAdapter<Date> dateTypeAdapter) {
/* 48 */     this.dateTypeAdapter = dateTypeAdapter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp read(JsonReader in) throws IOException {
/* 53 */     Date date = (Date)this.dateTypeAdapter.read(in);
/* 54 */     return (date != null) ? new Timestamp(date.getTime()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Timestamp value) throws IOException {
/* 59 */     this.dateTypeAdapter.write(out, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\sql\SqlTimestampTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */