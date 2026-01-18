/*    */ package com.google.gson.internal.sql;
/*    */ 
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.internal.bind.DefaultDateTypeAdapter;
/*    */ import java.sql.Date;
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
/*    */ public final class SqlTypesSupport
/*    */ {
/*    */   public static final boolean SUPPORTS_SQL_TYPES;
/*    */   public static final DefaultDateTypeAdapter.DateType<? extends Date> DATE_DATE_TYPE;
/*    */   public static final DefaultDateTypeAdapter.DateType<? extends Date> TIMESTAMP_DATE_TYPE;
/*    */   public static final TypeAdapterFactory DATE_FACTORY;
/*    */   public static final TypeAdapterFactory TIME_FACTORY;
/*    */   public static final TypeAdapterFactory TIMESTAMP_FACTORY;
/*    */   
/*    */   static {
/*    */     boolean sqlTypesSupport;
/*    */     try {
/* 48 */       Class.forName("java.sql.Date");
/* 49 */       sqlTypesSupport = true;
/* 50 */     } catch (ClassNotFoundException classNotFoundException) {
/* 51 */       sqlTypesSupport = false;
/*    */     } 
/* 53 */     SUPPORTS_SQL_TYPES = sqlTypesSupport;
/*    */     
/* 55 */     if (SUPPORTS_SQL_TYPES) {
/* 56 */       DATE_DATE_TYPE = (DefaultDateTypeAdapter.DateType)new DefaultDateTypeAdapter.DateType<Date>(Date.class)
/*    */         {
/*    */           protected Date deserialize(Date date)
/*    */           {
/* 60 */             return new Date(date.getTime());
/*    */           }
/*    */         };
/* 63 */       TIMESTAMP_DATE_TYPE = (DefaultDateTypeAdapter.DateType)new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class)
/*    */         {
/*    */           protected Timestamp deserialize(Date date)
/*    */           {
/* 67 */             return new Timestamp(date.getTime());
/*    */           }
/*    */         };
/*    */       
/* 71 */       DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
/* 72 */       TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
/* 73 */       TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
/*    */     } else {
/* 75 */       DATE_DATE_TYPE = null;
/* 76 */       TIMESTAMP_DATE_TYPE = null;
/*    */       
/* 78 */       DATE_FACTORY = null;
/* 79 */       TIME_FACTORY = null;
/* 80 */       TIMESTAMP_FACTORY = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\sql\SqlTypesSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */