/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum FieldType
/*     */ {
/*  19 */   DOUBLE(0, Collection.SCALAR, JavaType.DOUBLE),
/*  20 */   FLOAT(1, Collection.SCALAR, JavaType.FLOAT),
/*  21 */   INT64(2, Collection.SCALAR, JavaType.LONG),
/*  22 */   UINT64(3, Collection.SCALAR, JavaType.LONG),
/*  23 */   INT32(4, Collection.SCALAR, JavaType.INT),
/*  24 */   FIXED64(5, Collection.SCALAR, JavaType.LONG),
/*  25 */   FIXED32(6, Collection.SCALAR, JavaType.INT),
/*  26 */   BOOL(7, Collection.SCALAR, JavaType.BOOLEAN),
/*  27 */   STRING(8, Collection.SCALAR, JavaType.STRING),
/*  28 */   MESSAGE(9, Collection.SCALAR, JavaType.MESSAGE),
/*  29 */   BYTES(10, Collection.SCALAR, JavaType.BYTE_STRING),
/*  30 */   UINT32(11, Collection.SCALAR, JavaType.INT),
/*  31 */   ENUM(12, Collection.SCALAR, JavaType.ENUM),
/*  32 */   SFIXED32(13, Collection.SCALAR, JavaType.INT),
/*  33 */   SFIXED64(14, Collection.SCALAR, JavaType.LONG),
/*  34 */   SINT32(15, Collection.SCALAR, JavaType.INT),
/*  35 */   SINT64(16, Collection.SCALAR, JavaType.LONG),
/*  36 */   GROUP(17, Collection.SCALAR, JavaType.MESSAGE),
/*  37 */   DOUBLE_LIST(18, Collection.VECTOR, JavaType.DOUBLE),
/*  38 */   FLOAT_LIST(19, Collection.VECTOR, JavaType.FLOAT),
/*  39 */   INT64_LIST(20, Collection.VECTOR, JavaType.LONG),
/*  40 */   UINT64_LIST(21, Collection.VECTOR, JavaType.LONG),
/*  41 */   INT32_LIST(22, Collection.VECTOR, JavaType.INT),
/*  42 */   FIXED64_LIST(23, Collection.VECTOR, JavaType.LONG),
/*  43 */   FIXED32_LIST(24, Collection.VECTOR, JavaType.INT),
/*  44 */   BOOL_LIST(25, Collection.VECTOR, JavaType.BOOLEAN),
/*  45 */   STRING_LIST(26, Collection.VECTOR, JavaType.STRING),
/*  46 */   MESSAGE_LIST(27, Collection.VECTOR, JavaType.MESSAGE),
/*  47 */   BYTES_LIST(28, Collection.VECTOR, JavaType.BYTE_STRING),
/*  48 */   UINT32_LIST(29, Collection.VECTOR, JavaType.INT),
/*  49 */   ENUM_LIST(30, Collection.VECTOR, JavaType.ENUM),
/*  50 */   SFIXED32_LIST(31, Collection.VECTOR, JavaType.INT),
/*  51 */   SFIXED64_LIST(32, Collection.VECTOR, JavaType.LONG),
/*  52 */   SINT32_LIST(33, Collection.VECTOR, JavaType.INT),
/*  53 */   SINT64_LIST(34, Collection.VECTOR, JavaType.LONG),
/*  54 */   DOUBLE_LIST_PACKED(35, Collection.PACKED_VECTOR, JavaType.DOUBLE),
/*  55 */   FLOAT_LIST_PACKED(36, Collection.PACKED_VECTOR, JavaType.FLOAT),
/*  56 */   INT64_LIST_PACKED(37, Collection.PACKED_VECTOR, JavaType.LONG),
/*  57 */   UINT64_LIST_PACKED(38, Collection.PACKED_VECTOR, JavaType.LONG),
/*  58 */   INT32_LIST_PACKED(39, Collection.PACKED_VECTOR, JavaType.INT),
/*  59 */   FIXED64_LIST_PACKED(40, Collection.PACKED_VECTOR, JavaType.LONG),
/*  60 */   FIXED32_LIST_PACKED(41, Collection.PACKED_VECTOR, JavaType.INT),
/*  61 */   BOOL_LIST_PACKED(42, Collection.PACKED_VECTOR, JavaType.BOOLEAN),
/*  62 */   UINT32_LIST_PACKED(43, Collection.PACKED_VECTOR, JavaType.INT),
/*  63 */   ENUM_LIST_PACKED(44, Collection.PACKED_VECTOR, JavaType.ENUM),
/*  64 */   SFIXED32_LIST_PACKED(45, Collection.PACKED_VECTOR, JavaType.INT),
/*  65 */   SFIXED64_LIST_PACKED(46, Collection.PACKED_VECTOR, JavaType.LONG),
/*  66 */   SINT32_LIST_PACKED(47, Collection.PACKED_VECTOR, JavaType.INT),
/*  67 */   SINT64_LIST_PACKED(48, Collection.PACKED_VECTOR, JavaType.LONG),
/*  68 */   GROUP_LIST(49, Collection.VECTOR, JavaType.MESSAGE),
/*  69 */   MAP(50, Collection.MAP, JavaType.VOID);
/*     */   
/*     */   private final JavaType javaType;
/*     */   
/*     */   private final int id;
/*     */   
/*     */   private final Collection collection;
/*     */   
/*     */   FieldType(int id, Collection collection, JavaType javaType) {
/*  78 */     this.id = id;
/*  79 */     this.collection = collection;
/*  80 */     this.javaType = javaType;
/*     */     
/*  82 */     switch (collection.ordinal()) {
/*     */       case 3:
/*  84 */         this.elementType = javaType.getBoxedType();
/*     */         break;
/*     */       case 1:
/*  87 */         this.elementType = javaType.getBoxedType();
/*     */         break;
/*     */       
/*     */       default:
/*  91 */         this.elementType = null;
/*     */         break;
/*     */     } 
/*     */     
/*  95 */     boolean primitiveScalar = false;
/*  96 */     if (collection == Collection.SCALAR) {
/*  97 */       switch (javaType) {
/*     */         case BYTE_STRING:
/*     */         case MESSAGE:
/*     */         case STRING:
/*     */           break;
/*     */         default:
/* 103 */           primitiveScalar = true;
/*     */           break;
/*     */       } 
/*     */     }
/* 107 */     this.primitiveScalar = primitiveScalar;
/*     */   }
/*     */   private final Class<?> elementType; private final boolean primitiveScalar; private static final FieldType[] VALUES; private static final Type[] EMPTY_TYPES;
/*     */   
/*     */   public int id() {
/* 112 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getJavaType() {
/* 120 */     return this.javaType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPacked() {
/* 125 */     return Collection.PACKED_VECTOR.equals(this.collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrimitiveScalar() {
/* 133 */     return this.primitiveScalar;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScalar() {
/* 138 */     return (this.collection == Collection.SCALAR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isList() {
/* 143 */     return this.collection.isList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMap() {
/* 148 */     return (this.collection == Collection.MAP);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidForField(Field field) {
/* 153 */     if (Collection.VECTOR.equals(this.collection)) {
/* 154 */       return isValidForList(field);
/*     */     }
/* 156 */     return this.javaType.getType().isAssignableFrom(field.getType());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidForList(Field field) {
/* 161 */     Class<?> clazz = field.getType();
/* 162 */     if (!this.javaType.getType().isAssignableFrom(clazz))
/*     */     {
/* 164 */       return false;
/*     */     }
/* 166 */     Type[] types = EMPTY_TYPES;
/* 167 */     Type genericType = field.getGenericType();
/* 168 */     if (genericType instanceof ParameterizedType) {
/* 169 */       types = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
/*     */     }
/* 171 */     Type listParameter = getListParameter(clazz, types);
/* 172 */     if (!(listParameter instanceof Class))
/*     */     {
/* 174 */       return true;
/*     */     }
/* 176 */     return this.elementType.isAssignableFrom((Class)listParameter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldType forId(int id) {
/* 185 */     if (id < 0 || id >= VALUES.length) {
/* 186 */       return null;
/*     */     }
/* 188 */     return VALUES[id];
/*     */   }
/*     */   
/*     */   static {
/* 192 */     EMPTY_TYPES = new Type[0];
/*     */ 
/*     */     
/* 195 */     FieldType[] values = values();
/* 196 */     VALUES = new FieldType[values.length];
/* 197 */     for (FieldType type : values) {
/* 198 */       VALUES[type.id] = type;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getGenericSuperList(Class<?> clazz) {
/* 209 */     Type[] genericInterfaces = clazz.getGenericInterfaces();
/* 210 */     for (Type genericInterface : genericInterfaces) {
/* 211 */       if (genericInterface instanceof ParameterizedType) {
/* 212 */         ParameterizedType parameterizedType = (ParameterizedType)genericInterface;
/* 213 */         Class<?> rawType = (Class)parameterizedType.getRawType();
/* 214 */         if (List.class.isAssignableFrom(rawType)) {
/* 215 */           return genericInterface;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 221 */     Type type = clazz.getGenericSuperclass();
/* 222 */     if (type instanceof ParameterizedType) {
/* 223 */       ParameterizedType parameterizedType = (ParameterizedType)type;
/* 224 */       Class<?> rawType = (Class)parameterizedType.getRawType();
/* 225 */       if (List.class.isAssignableFrom(rawType)) {
/* 226 */         return type;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 231 */     return null;
/*     */   }
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
/*     */   private static Type getListParameter(Class<?> clazz, Type[] realTypes) {
/* 246 */     label41: while (clazz != List.class) {
/*     */       
/* 248 */       Type genericType = getGenericSuperList(clazz);
/* 249 */       if (genericType instanceof ParameterizedType) {
/*     */         
/* 251 */         ParameterizedType parameterizedType = (ParameterizedType)genericType;
/* 252 */         Type[] superArgs = parameterizedType.getActualTypeArguments();
/* 253 */         for (int i = 0; i < superArgs.length; i++) {
/* 254 */           Type superArg = superArgs[i];
/* 255 */           if (superArg instanceof TypeVariable) {
/*     */ 
/*     */             
/* 258 */             TypeVariable[] arrayOfTypeVariable = (TypeVariable[])clazz.getTypeParameters();
/* 259 */             if (realTypes.length != arrayOfTypeVariable.length) {
/* 260 */               throw new RuntimeException("Type array mismatch");
/*     */             }
/*     */ 
/*     */             
/* 264 */             boolean foundReplacement = false;
/* 265 */             for (int j = 0; j < arrayOfTypeVariable.length; j++) {
/* 266 */               if (superArg == arrayOfTypeVariable[j]) {
/* 267 */                 Type realType = realTypes[j];
/* 268 */                 superArgs[i] = realType;
/* 269 */                 foundReplacement = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 273 */             if (!foundReplacement) {
/* 274 */               throw new RuntimeException("Unable to find replacement for " + superArg);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 279 */         Class<?> parent = (Class)parameterizedType.getRawType();
/*     */         
/* 281 */         realTypes = superArgs;
/* 282 */         clazz = parent;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 288 */       realTypes = EMPTY_TYPES;
/* 289 */       for (Class<?> iface : clazz.getInterfaces()) {
/* 290 */         if (List.class.isAssignableFrom(iface)) {
/* 291 */           clazz = iface;
/*     */           continue label41;
/*     */         } 
/*     */       } 
/* 295 */       clazz = clazz.getSuperclass();
/*     */     } 
/*     */     
/* 298 */     if (realTypes.length != 1) {
/* 299 */       throw new RuntimeException("Unable to identify parameter type for List<T>");
/*     */     }
/* 301 */     return realTypes[0];
/*     */   }
/*     */   
/*     */   enum Collection {
/* 305 */     SCALAR(false),
/* 306 */     VECTOR(true),
/* 307 */     PACKED_VECTOR(true),
/* 308 */     MAP(false);
/*     */     
/*     */     private final boolean isList;
/*     */     
/*     */     Collection(boolean isList) {
/* 313 */       this.isList = isList;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isList() {
/* 318 */       return this.isList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\FieldType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */