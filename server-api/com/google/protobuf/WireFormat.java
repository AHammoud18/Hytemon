/*     */ package com.google.protobuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WireFormat
/*     */ {
/*     */   static final int FIXED32_SIZE = 4;
/*     */   static final int FIXED64_SIZE = 8;
/*     */   static final int MAX_VARINT32_SIZE = 5;
/*     */   static final int MAX_VARINT64_SIZE = 10;
/*     */   static final int MAX_VARINT_SIZE = 10;
/*     */   public static final int WIRETYPE_VARINT = 0;
/*     */   public static final int WIRETYPE_FIXED64 = 1;
/*     */   public static final int WIRETYPE_LENGTH_DELIMITED = 2;
/*     */   public static final int WIRETYPE_START_GROUP = 3;
/*     */   public static final int WIRETYPE_END_GROUP = 4;
/*     */   public static final int WIRETYPE_FIXED32 = 5;
/*     */   static final int TAG_TYPE_BITS = 3;
/*     */   static final int TAG_TYPE_MASK = 7;
/*     */   static final int MESSAGE_SET_ITEM = 1;
/*     */   static final int MESSAGE_SET_TYPE_ID = 2;
/*     */   static final int MESSAGE_SET_MESSAGE = 3;
/*     */   
/*     */   public static int getTagWireType(int tag) {
/*  42 */     return tag & 0x7;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTagFieldNumber(int tag) {
/*  47 */     return tag >>> 3;
/*     */   }
/*     */ 
/*     */   
/*     */   static int makeTag(int fieldNumber, int wireType) {
/*  52 */     return fieldNumber << 3 | wireType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum JavaType
/*     */   {
/*  60 */     INT((String)Integer.valueOf(0)),
/*  61 */     LONG((String)Long.valueOf(0L)),
/*  62 */     FLOAT((String)Float.valueOf(0.0F)),
/*  63 */     DOUBLE((String)Double.valueOf(0.0D)),
/*  64 */     BOOLEAN((String)Boolean.valueOf(false)),
/*  65 */     STRING(""),
/*  66 */     BYTE_STRING((String)ByteString.EMPTY),
/*  67 */     ENUM(null),
/*  68 */     MESSAGE(null);
/*     */     
/*     */     JavaType(Object defaultDefault) {
/*  71 */       this.defaultDefault = defaultDefault;
/*     */     }
/*     */     private final Object defaultDefault;
/*     */     
/*     */     Object getDefaultDefault() {
/*  76 */       return this.defaultDefault;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum FieldType
/*     */   {
/*  87 */     DOUBLE((String)WireFormat.JavaType.DOUBLE, 1),
/*  88 */     FLOAT((String)WireFormat.JavaType.FLOAT, 5),
/*  89 */     INT64((String)WireFormat.JavaType.LONG, 0),
/*  90 */     UINT64((String)WireFormat.JavaType.LONG, 0),
/*  91 */     INT32((String)WireFormat.JavaType.INT, 0),
/*  92 */     FIXED64((String)WireFormat.JavaType.LONG, 1),
/*  93 */     FIXED32((String)WireFormat.JavaType.INT, 5),
/*  94 */     BOOL((String)WireFormat.JavaType.BOOLEAN, 0),
/*  95 */     STRING((String)WireFormat.JavaType.STRING, 2)
/*     */     {
/*     */       public boolean isPackable() {
/*  98 */         return false;
/*     */       }
/*     */     },
/* 101 */     GROUP((String)WireFormat.JavaType.MESSAGE, 3)
/*     */     {
/*     */       public boolean isPackable() {
/* 104 */         return false;
/*     */       }
/*     */     },
/* 107 */     MESSAGE((String)WireFormat.JavaType.MESSAGE, 2)
/*     */     {
/*     */       public boolean isPackable() {
/* 110 */         return false;
/*     */       }
/*     */     },
/* 113 */     BYTES((String)WireFormat.JavaType.BYTE_STRING, 2)
/*     */     {
/*     */       public boolean isPackable() {
/* 116 */         return false;
/*     */       }
/*     */     },
/* 119 */     UINT32((String)WireFormat.JavaType.INT, 0),
/* 120 */     ENUM((String)WireFormat.JavaType.ENUM, 0),
/* 121 */     SFIXED32((String)WireFormat.JavaType.INT, 5),
/* 122 */     SFIXED64((String)WireFormat.JavaType.LONG, 1),
/* 123 */     SINT32((String)WireFormat.JavaType.INT, 0),
/* 124 */     SINT64((String)WireFormat.JavaType.LONG, 0);
/*     */     
/*     */     FieldType(WireFormat.JavaType javaType, int wireType) {
/* 127 */       this.javaType = javaType;
/* 128 */       this.wireType = wireType;
/*     */     }
/*     */     
/*     */     private final WireFormat.JavaType javaType;
/*     */     private final int wireType;
/*     */     
/*     */     public WireFormat.JavaType getJavaType() {
/* 135 */       return this.javaType;
/*     */     }
/*     */     
/*     */     public int getWireType() {
/* 139 */       return this.wireType;
/*     */     }
/*     */     
/*     */     public boolean isPackable() {
/* 143 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   static final int MESSAGE_SET_ITEM_TAG = makeTag(1, 3);
/* 154 */   static final int MESSAGE_SET_ITEM_END_TAG = makeTag(1, 4);
/* 155 */   static final int MESSAGE_SET_TYPE_ID_TAG = makeTag(2, 0);
/*     */   
/* 157 */   static final int MESSAGE_SET_MESSAGE_TAG = makeTag(3, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum Utf8Validation
/*     */   {
/* 165 */     LOOSE,
/*     */     
/* 167 */     STRICT,
/*     */     
/* 169 */     LAZY;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\WireFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */