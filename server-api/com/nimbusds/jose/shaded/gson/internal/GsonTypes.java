/*     */ package com.nimbusds.jose.shaded.gson.internal;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GsonTypes
/*     */ {
/*  47 */   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/*     */   
/*     */   private GsonTypes() {
/*  50 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Class<?> rawType, Type... typeArguments) {
/*  61 */     return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GenericArrayType arrayOf(Type componentType) {
/*  70 */     return new GenericArrayTypeImpl(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WildcardType subtypeOf(Type bound) {
/*     */     Type[] upperBounds;
/*  81 */     if (bound instanceof WildcardType) {
/*  82 */       upperBounds = ((WildcardType)bound).getUpperBounds();
/*     */     } else {
/*  84 */       upperBounds = new Type[] { bound };
/*     */     } 
/*  86 */     return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WildcardType supertypeOf(Type bound) {
/*     */     Type[] lowerBounds;
/*  95 */     if (bound instanceof WildcardType) {
/*  96 */       lowerBounds = ((WildcardType)bound).getLowerBounds();
/*     */     } else {
/*  98 */       lowerBounds = new Type[] { bound };
/*     */     } 
/* 100 */     return new WildcardTypeImpl(new Type[] { Object.class }, lowerBounds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type canonicalize(Type type) {
/* 108 */     if (type instanceof Class) {
/* 109 */       Class<?> c = (Class)type;
/* 110 */       return c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c;
/*     */     } 
/* 112 */     if (type instanceof ParameterizedType) {
/* 113 */       ParameterizedType p = (ParameterizedType)type;
/* 114 */       return new ParameterizedTypeImpl(p
/* 115 */           .getOwnerType(), (Class)p.getRawType(), p.getActualTypeArguments());
/*     */     } 
/* 117 */     if (type instanceof GenericArrayType) {
/* 118 */       GenericArrayType g = (GenericArrayType)type;
/* 119 */       return new GenericArrayTypeImpl(g.getGenericComponentType());
/*     */     } 
/* 121 */     if (type instanceof WildcardType) {
/* 122 */       WildcardType w = (WildcardType)type;
/* 123 */       return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<?> getRawType(Type type) {
/* 132 */     if (type instanceof Class)
/*     */     {
/* 134 */       return (Class)type;
/*     */     }
/* 136 */     if (type instanceof ParameterizedType) {
/* 137 */       ParameterizedType parameterizedType = (ParameterizedType)type;
/*     */ 
/*     */ 
/*     */       
/* 141 */       Type rawType = parameterizedType.getRawType();
/* 142 */       GsonPreconditions.checkArgument(rawType instanceof Class);
/* 143 */       return (Class)rawType;
/*     */     } 
/* 145 */     if (type instanceof GenericArrayType) {
/* 146 */       Type componentType = ((GenericArrayType)type).getGenericComponentType();
/* 147 */       return Array.newInstance(getRawType(componentType), 0).getClass();
/*     */     } 
/* 149 */     if (type instanceof TypeVariable)
/*     */     {
/*     */       
/* 152 */       return Object.class;
/*     */     }
/* 154 */     if (type instanceof WildcardType) {
/* 155 */       Type[] bounds = ((WildcardType)type).getUpperBounds();
/*     */       
/* 157 */       assert bounds.length == 1;
/* 158 */       return getRawType(bounds[0]);
/*     */     } 
/*     */     
/* 161 */     String className = (type == null) ? "null" : type.getClass().getName();
/* 162 */     throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equal(Object a, Object b) {
/* 171 */     return Objects.equals(a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equals(Type a, Type b) {
/* 176 */     if (a == b)
/*     */     {
/* 178 */       return true;
/*     */     }
/* 180 */     if (a instanceof Class)
/*     */     {
/* 182 */       return a.equals(b);
/*     */     }
/* 184 */     if (a instanceof ParameterizedType) {
/* 185 */       if (!(b instanceof ParameterizedType)) {
/* 186 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 190 */       ParameterizedType pa = (ParameterizedType)a;
/* 191 */       ParameterizedType pb = (ParameterizedType)b;
/* 192 */       return (equal(pa.getOwnerType(), pb.getOwnerType()) && pa
/* 193 */         .getRawType().equals(pb.getRawType()) && 
/* 194 */         Arrays.equals((Object[])pa.getActualTypeArguments(), (Object[])pb.getActualTypeArguments()));
/*     */     } 
/* 196 */     if (a instanceof GenericArrayType) {
/* 197 */       if (!(b instanceof GenericArrayType)) {
/* 198 */         return false;
/*     */       }
/*     */       
/* 201 */       GenericArrayType ga = (GenericArrayType)a;
/* 202 */       GenericArrayType gb = (GenericArrayType)b;
/* 203 */       return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
/*     */     } 
/* 205 */     if (a instanceof WildcardType) {
/* 206 */       if (!(b instanceof WildcardType)) {
/* 207 */         return false;
/*     */       }
/*     */       
/* 210 */       WildcardType wa = (WildcardType)a;
/* 211 */       WildcardType wb = (WildcardType)b;
/* 212 */       return (Arrays.equals((Object[])wa.getUpperBounds(), (Object[])wb.getUpperBounds()) && 
/* 213 */         Arrays.equals((Object[])wa.getLowerBounds(), (Object[])wb.getLowerBounds()));
/*     */     } 
/* 215 */     if (a instanceof TypeVariable) {
/* 216 */       if (!(b instanceof TypeVariable)) {
/* 217 */         return false;
/*     */       }
/* 219 */       TypeVariable<?> va = (TypeVariable)a;
/* 220 */       TypeVariable<?> vb = (TypeVariable)b;
/* 221 */       return (Objects.equals(va.getGenericDeclaration(), vb.getGenericDeclaration()) && va
/* 222 */         .getName().equals(vb.getName()));
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String typeToString(Type type) {
/* 231 */     return (type instanceof Class) ? ((Class)type).getName() : type.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> supertype) {
/* 240 */     if (supertype == rawType) {
/* 241 */       return context;
/*     */     }
/*     */ 
/*     */     
/* 245 */     if (supertype.isInterface()) {
/* 246 */       Class<?>[] interfaces = rawType.getInterfaces();
/* 247 */       for (int i = 0, length = interfaces.length; i < length; i++) {
/* 248 */         if (interfaces[i] == supertype)
/* 249 */           return rawType.getGenericInterfaces()[i]; 
/* 250 */         if (supertype.isAssignableFrom(interfaces[i])) {
/* 251 */           return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], supertype);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 257 */     if (!rawType.isInterface()) {
/* 258 */       while (rawType != Object.class) {
/* 259 */         Class<?> rawSupertype = rawType.getSuperclass();
/* 260 */         if (rawSupertype == supertype)
/* 261 */           return rawType.getGenericSuperclass(); 
/* 262 */         if (supertype.isAssignableFrom(rawSupertype)) {
/* 263 */           return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, supertype);
/*     */         }
/* 265 */         rawType = rawSupertype;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 270 */     return supertype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
/* 281 */     if (context instanceof WildcardType) {
/*     */ 
/*     */       
/* 284 */       Type[] bounds = ((WildcardType)context).getUpperBounds();
/*     */       
/* 286 */       assert bounds.length == 1;
/* 287 */       context = bounds[0];
/*     */     } 
/* 289 */     GsonPreconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
/* 290 */     return resolve(context, contextRawType, 
/* 291 */         getGenericSupertype(context, contextRawType, supertype));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getArrayComponentType(Type array) {
/* 300 */     return (array instanceof GenericArrayType) ? (
/* 301 */       (GenericArrayType)array).getGenericComponentType() : (
/* 302 */       (Class)array).getComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
/* 311 */     Type collectionType = getSupertype(context, contextRawType, Collection.class);
/*     */     
/* 313 */     if (collectionType instanceof ParameterizedType) {
/* 314 */       return ((ParameterizedType)collectionType).getActualTypeArguments()[0];
/*     */     }
/* 316 */     return Object.class;
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
/*     */   public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
/* 329 */     if (Properties.class.isAssignableFrom(contextRawType)) {
/* 330 */       return new Type[] { String.class, String.class };
/*     */     }
/*     */     
/* 333 */     Type mapType = getSupertype(context, contextRawType, Map.class);
/*     */     
/* 335 */     if (mapType instanceof ParameterizedType) {
/* 336 */       ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
/* 337 */       return mapParameterizedType.getActualTypeArguments();
/*     */     } 
/* 339 */     return new Type[] { Object.class, Object.class };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
/* 344 */     return resolve(context, contextRawType, toResolve, new HashMap<>());
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
/*     */   private static Type resolve(Type context, Class<?> contextRawType, Type toResolve, Map<TypeVariable<?>, Type> visitedTypeVariables) {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore #4
/*     */     //   3: aload_2
/*     */     //   4: instanceof java/lang/reflect/TypeVariable
/*     */     //   7: ifeq -> 90
/*     */     //   10: aload_2
/*     */     //   11: checkcast java/lang/reflect/TypeVariable
/*     */     //   14: astore #5
/*     */     //   16: aload_3
/*     */     //   17: aload #5
/*     */     //   19: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: checkcast java/lang/reflect/Type
/*     */     //   27: astore #6
/*     */     //   29: aload #6
/*     */     //   31: ifnull -> 49
/*     */     //   34: aload #6
/*     */     //   36: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
/*     */     //   39: if_acmpne -> 46
/*     */     //   42: aload_2
/*     */     //   43: goto -> 48
/*     */     //   46: aload #6
/*     */     //   48: areturn
/*     */     //   49: aload_3
/*     */     //   50: aload #5
/*     */     //   52: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
/*     */     //   55: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   60: pop
/*     */     //   61: aload #4
/*     */     //   63: ifnonnull -> 70
/*     */     //   66: aload #5
/*     */     //   68: astore #4
/*     */     //   70: aload_0
/*     */     //   71: aload_1
/*     */     //   72: aload #5
/*     */     //   74: invokestatic resolveTypeVariable : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/TypeVariable;)Ljava/lang/reflect/Type;
/*     */     //   77: astore_2
/*     */     //   78: aload_2
/*     */     //   79: aload #5
/*     */     //   81: if_acmpne -> 87
/*     */     //   84: goto -> 493
/*     */     //   87: goto -> 3
/*     */     //   90: aload_2
/*     */     //   91: instanceof java/lang/Class
/*     */     //   94: ifeq -> 154
/*     */     //   97: aload_2
/*     */     //   98: checkcast java/lang/Class
/*     */     //   101: invokevirtual isArray : ()Z
/*     */     //   104: ifeq -> 154
/*     */     //   107: aload_2
/*     */     //   108: checkcast java/lang/Class
/*     */     //   111: astore #5
/*     */     //   113: aload #5
/*     */     //   115: invokevirtual getComponentType : ()Ljava/lang/Class;
/*     */     //   118: astore #6
/*     */     //   120: aload_0
/*     */     //   121: aload_1
/*     */     //   122: aload #6
/*     */     //   124: aload_3
/*     */     //   125: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   128: astore #7
/*     */     //   130: aload #6
/*     */     //   132: aload #7
/*     */     //   134: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   137: ifeq -> 145
/*     */     //   140: aload #5
/*     */     //   142: goto -> 150
/*     */     //   145: aload #7
/*     */     //   147: invokestatic arrayOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/GenericArrayType;
/*     */     //   150: astore_2
/*     */     //   151: goto -> 493
/*     */     //   154: aload_2
/*     */     //   155: instanceof java/lang/reflect/GenericArrayType
/*     */     //   158: ifeq -> 210
/*     */     //   161: aload_2
/*     */     //   162: checkcast java/lang/reflect/GenericArrayType
/*     */     //   165: astore #5
/*     */     //   167: aload #5
/*     */     //   169: invokeinterface getGenericComponentType : ()Ljava/lang/reflect/Type;
/*     */     //   174: astore #6
/*     */     //   176: aload_0
/*     */     //   177: aload_1
/*     */     //   178: aload #6
/*     */     //   180: aload_3
/*     */     //   181: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   184: astore #7
/*     */     //   186: aload #6
/*     */     //   188: aload #7
/*     */     //   190: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   193: ifeq -> 201
/*     */     //   196: aload #5
/*     */     //   198: goto -> 206
/*     */     //   201: aload #7
/*     */     //   203: invokestatic arrayOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/GenericArrayType;
/*     */     //   206: astore_2
/*     */     //   207: goto -> 493
/*     */     //   210: aload_2
/*     */     //   211: instanceof java/lang/reflect/ParameterizedType
/*     */     //   214: ifeq -> 379
/*     */     //   217: aload_2
/*     */     //   218: checkcast java/lang/reflect/ParameterizedType
/*     */     //   221: astore #5
/*     */     //   223: aload #5
/*     */     //   225: invokeinterface getOwnerType : ()Ljava/lang/reflect/Type;
/*     */     //   230: astore #6
/*     */     //   232: aload_0
/*     */     //   233: aload_1
/*     */     //   234: aload #6
/*     */     //   236: aload_3
/*     */     //   237: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   240: astore #7
/*     */     //   242: aload #7
/*     */     //   244: aload #6
/*     */     //   246: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   249: ifne -> 256
/*     */     //   252: iconst_1
/*     */     //   253: goto -> 257
/*     */     //   256: iconst_0
/*     */     //   257: istore #8
/*     */     //   259: aload #5
/*     */     //   261: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
/*     */     //   266: astore #9
/*     */     //   268: iconst_0
/*     */     //   269: istore #10
/*     */     //   271: iconst_0
/*     */     //   272: istore #11
/*     */     //   274: aload #9
/*     */     //   276: arraylength
/*     */     //   277: istore #12
/*     */     //   279: iload #11
/*     */     //   281: iload #12
/*     */     //   283: if_icmpge -> 343
/*     */     //   286: aload_0
/*     */     //   287: aload_1
/*     */     //   288: aload #9
/*     */     //   290: iload #11
/*     */     //   292: aaload
/*     */     //   293: aload_3
/*     */     //   294: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   297: astore #13
/*     */     //   299: aload #13
/*     */     //   301: aload #9
/*     */     //   303: iload #11
/*     */     //   305: aaload
/*     */     //   306: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   309: ifne -> 337
/*     */     //   312: iload #10
/*     */     //   314: ifne -> 330
/*     */     //   317: aload #9
/*     */     //   319: invokevirtual clone : ()Ljava/lang/Object;
/*     */     //   322: checkcast [Ljava/lang/reflect/Type;
/*     */     //   325: astore #9
/*     */     //   327: iconst_1
/*     */     //   328: istore #10
/*     */     //   330: aload #9
/*     */     //   332: iload #11
/*     */     //   334: aload #13
/*     */     //   336: aastore
/*     */     //   337: iinc #11, 1
/*     */     //   340: goto -> 279
/*     */     //   343: iload #8
/*     */     //   345: ifne -> 353
/*     */     //   348: iload #10
/*     */     //   350: ifeq -> 373
/*     */     //   353: aload #7
/*     */     //   355: aload #5
/*     */     //   357: invokeinterface getRawType : ()Ljava/lang/reflect/Type;
/*     */     //   362: checkcast java/lang/Class
/*     */     //   365: aload #9
/*     */     //   367: invokestatic newParameterizedTypeWithOwner : (Ljava/lang/reflect/Type;Ljava/lang/Class;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/ParameterizedType;
/*     */     //   370: goto -> 375
/*     */     //   373: aload #5
/*     */     //   375: astore_2
/*     */     //   376: goto -> 493
/*     */     //   379: aload_2
/*     */     //   380: instanceof java/lang/reflect/WildcardType
/*     */     //   383: ifeq -> 493
/*     */     //   386: aload_2
/*     */     //   387: checkcast java/lang/reflect/WildcardType
/*     */     //   390: astore #5
/*     */     //   392: aload #5
/*     */     //   394: invokeinterface getLowerBounds : ()[Ljava/lang/reflect/Type;
/*     */     //   399: astore #6
/*     */     //   401: aload #5
/*     */     //   403: invokeinterface getUpperBounds : ()[Ljava/lang/reflect/Type;
/*     */     //   408: astore #7
/*     */     //   410: aload #6
/*     */     //   412: arraylength
/*     */     //   413: iconst_1
/*     */     //   414: if_icmpne -> 450
/*     */     //   417: aload_0
/*     */     //   418: aload_1
/*     */     //   419: aload #6
/*     */     //   421: iconst_0
/*     */     //   422: aaload
/*     */     //   423: aload_3
/*     */     //   424: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   427: astore #8
/*     */     //   429: aload #8
/*     */     //   431: aload #6
/*     */     //   433: iconst_0
/*     */     //   434: aaload
/*     */     //   435: if_acmpeq -> 447
/*     */     //   438: aload #8
/*     */     //   440: invokestatic supertypeOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/WildcardType;
/*     */     //   443: astore_2
/*     */     //   444: goto -> 493
/*     */     //   447: goto -> 487
/*     */     //   450: aload #7
/*     */     //   452: arraylength
/*     */     //   453: iconst_1
/*     */     //   454: if_icmpne -> 487
/*     */     //   457: aload_0
/*     */     //   458: aload_1
/*     */     //   459: aload #7
/*     */     //   461: iconst_0
/*     */     //   462: aaload
/*     */     //   463: aload_3
/*     */     //   464: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   467: astore #8
/*     */     //   469: aload #8
/*     */     //   471: aload #7
/*     */     //   473: iconst_0
/*     */     //   474: aaload
/*     */     //   475: if_acmpeq -> 487
/*     */     //   478: aload #8
/*     */     //   480: invokestatic subtypeOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/WildcardType;
/*     */     //   483: astore_2
/*     */     //   484: goto -> 493
/*     */     //   487: aload #5
/*     */     //   489: astore_2
/*     */     //   490: goto -> 493
/*     */     //   493: aload #4
/*     */     //   495: ifnull -> 508
/*     */     //   498: aload_3
/*     */     //   499: aload #4
/*     */     //   501: aload_2
/*     */     //   502: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   507: pop
/*     */     //   508: aload_2
/*     */     //   509: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #353	-> 0
/*     */     //   #355	-> 3
/*     */     //   #356	-> 10
/*     */     //   #357	-> 16
/*     */     //   #358	-> 29
/*     */     //   #360	-> 34
/*     */     //   #364	-> 49
/*     */     //   #365	-> 61
/*     */     //   #366	-> 66
/*     */     //   #369	-> 70
/*     */     //   #370	-> 78
/*     */     //   #371	-> 84
/*     */     //   #374	-> 87
/*     */     //   #375	-> 107
/*     */     //   #376	-> 113
/*     */     //   #377	-> 120
/*     */     //   #378	-> 125
/*     */     //   #379	-> 130
/*     */     //   #380	-> 151
/*     */     //   #382	-> 154
/*     */     //   #383	-> 161
/*     */     //   #384	-> 167
/*     */     //   #385	-> 176
/*     */     //   #386	-> 181
/*     */     //   #387	-> 186
/*     */     //   #388	-> 207
/*     */     //   #390	-> 210
/*     */     //   #391	-> 217
/*     */     //   #392	-> 223
/*     */     //   #393	-> 232
/*     */     //   #394	-> 242
/*     */     //   #396	-> 259
/*     */     //   #397	-> 268
/*     */     //   #398	-> 271
/*     */     //   #399	-> 286
/*     */     //   #400	-> 294
/*     */     //   #401	-> 299
/*     */     //   #402	-> 312
/*     */     //   #403	-> 317
/*     */     //   #404	-> 327
/*     */     //   #406	-> 330
/*     */     //   #398	-> 337
/*     */     //   #411	-> 343
/*     */     //   #412	-> 353
/*     */     //   #413	-> 357
/*     */     //   #412	-> 367
/*     */     //   #414	-> 373
/*     */     //   #415	-> 376
/*     */     //   #417	-> 379
/*     */     //   #418	-> 386
/*     */     //   #419	-> 392
/*     */     //   #420	-> 401
/*     */     //   #422	-> 410
/*     */     //   #423	-> 417
/*     */     //   #424	-> 424
/*     */     //   #425	-> 429
/*     */     //   #426	-> 438
/*     */     //   #427	-> 444
/*     */     //   #429	-> 447
/*     */     //   #430	-> 457
/*     */     //   #431	-> 464
/*     */     //   #432	-> 469
/*     */     //   #433	-> 478
/*     */     //   #434	-> 484
/*     */     //   #437	-> 487
/*     */     //   #438	-> 490
/*     */     //   #445	-> 493
/*     */     //   #446	-> 498
/*     */     //   #448	-> 508
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   16	71	5	typeVariable	Ljava/lang/reflect/TypeVariable;
/*     */     //   29	58	6	previouslyResolved	Ljava/lang/reflect/Type;
/*     */     //   113	41	5	original	Ljava/lang/Class;
/*     */     //   120	34	6	componentType	Ljava/lang/reflect/Type;
/*     */     //   130	24	7	newComponentType	Ljava/lang/reflect/Type;
/*     */     //   167	43	5	original	Ljava/lang/reflect/GenericArrayType;
/*     */     //   176	34	6	componentType	Ljava/lang/reflect/Type;
/*     */     //   186	24	7	newComponentType	Ljava/lang/reflect/Type;
/*     */     //   299	38	13	resolvedTypeArgument	Ljava/lang/reflect/Type;
/*     */     //   274	69	11	t	I
/*     */     //   279	64	12	length	I
/*     */     //   223	156	5	original	Ljava/lang/reflect/ParameterizedType;
/*     */     //   232	147	6	ownerType	Ljava/lang/reflect/Type;
/*     */     //   242	137	7	newOwnerType	Ljava/lang/reflect/Type;
/*     */     //   259	120	8	ownerChanged	Z
/*     */     //   268	111	9	args	[Ljava/lang/reflect/Type;
/*     */     //   271	108	10	argsChanged	Z
/*     */     //   429	18	8	lowerBound	Ljava/lang/reflect/Type;
/*     */     //   469	18	8	upperBound	Ljava/lang/reflect/Type;
/*     */     //   392	101	5	original	Ljava/lang/reflect/WildcardType;
/*     */     //   401	92	6	originalLowerBound	[Ljava/lang/reflect/Type;
/*     */     //   410	83	7	originalUpperBound	[Ljava/lang/reflect/Type;
/*     */     //   0	510	0	context	Ljava/lang/reflect/Type;
/*     */     //   0	510	1	contextRawType	Ljava/lang/Class;
/*     */     //   0	510	2	toResolve	Ljava/lang/reflect/Type;
/*     */     //   0	510	3	visitedTypeVariables	Ljava/util/Map;
/*     */     //   3	507	4	resolving	Ljava/lang/reflect/TypeVariable;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   16	71	5	typeVariable	Ljava/lang/reflect/TypeVariable<*>;
/*     */     //   113	41	5	original	Ljava/lang/Class<*>;
/*     */     //   0	510	1	contextRawType	Ljava/lang/Class<*>;
/*     */     //   0	510	3	visitedTypeVariables	Ljava/util/Map<Ljava/lang/reflect/TypeVariable<*>;Ljava/lang/reflect/Type;>;
/*     */     //   3	507	4	resolving	Ljava/lang/reflect/TypeVariable<*>;
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
/*     */   private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
/* 453 */     Class<?> declaredByRaw = declaringClassOf(unknown);
/*     */ 
/*     */     
/* 456 */     if (declaredByRaw == null) {
/* 457 */       return unknown;
/*     */     }
/*     */     
/* 460 */     Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
/* 461 */     if (declaredBy instanceof ParameterizedType) {
/* 462 */       int index = indexOf((Object[])declaredByRaw.getTypeParameters(), unknown);
/* 463 */       return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
/*     */     } 
/*     */     
/* 466 */     return unknown;
/*     */   }
/*     */   
/*     */   private static int indexOf(Object[] array, Object toFind) {
/* 470 */     for (int i = 0, length = array.length; i < length; i++) {
/* 471 */       if (toFind.equals(array[i])) {
/* 472 */         return i;
/*     */       }
/*     */     } 
/* 475 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
/* 483 */     GenericDeclaration genericDeclaration = (GenericDeclaration)typeVariable.getGenericDeclaration();
/* 484 */     return (genericDeclaration instanceof Class) ? (Class)genericDeclaration : null;
/*     */   }
/*     */   
/*     */   static void checkNotPrimitive(Type type) {
/* 488 */     GsonPreconditions.checkArgument((!(type instanceof Class) || !((Class)type).isPrimitive()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean requiresOwnerType(Type rawType) {
/* 499 */     if (rawType instanceof Class) {
/* 500 */       Class<?> rawTypeAsClass = (Class)rawType;
/* 501 */       return (!Modifier.isStatic(rawTypeAsClass.getModifiers()) && rawTypeAsClass
/* 502 */         .getDeclaringClass() != null);
/*     */     } 
/* 504 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ParameterizedTypeImpl
/*     */     implements ParameterizedType, Serializable
/*     */   {
/*     */     private final Type ownerType;
/*     */     
/*     */     private final Type rawType;
/*     */     
/*     */     private final Type[] typeArguments;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     public ParameterizedTypeImpl(Type ownerType, Class<?> rawType, Type... typeArguments) {
/* 522 */       Objects.requireNonNull(rawType);
/*     */       
/* 524 */       if (ownerType == null && GsonTypes.requiresOwnerType(rawType)) {
/* 525 */         throw new IllegalArgumentException("Must specify owner type for " + rawType);
/*     */       }
/*     */       
/* 528 */       this.ownerType = (ownerType == null) ? null : GsonTypes.canonicalize(ownerType);
/* 529 */       this.rawType = GsonTypes.canonicalize(rawType);
/* 530 */       this.typeArguments = (Type[])typeArguments.clone();
/* 531 */       for (int t = 0, length = this.typeArguments.length; t < length; t++) {
/* 532 */         Objects.requireNonNull(this.typeArguments[t]);
/* 533 */         GsonTypes.checkNotPrimitive(this.typeArguments[t]);
/* 534 */         this.typeArguments[t] = GsonTypes.canonicalize(this.typeArguments[t]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getActualTypeArguments() {
/* 540 */       return (Type[])this.typeArguments.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getRawType() {
/* 545 */       return this.rawType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getOwnerType() {
/* 550 */       return this.ownerType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 555 */       return (other instanceof ParameterizedType && 
/* 556 */         GsonTypes.equals(this, (ParameterizedType)other));
/*     */     }
/*     */     
/*     */     private static int hashCodeOrZero(Object o) {
/* 560 */       return (o != null) ? o.hashCode() : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 565 */       return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType.hashCode() ^ hashCodeOrZero(this.ownerType);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 570 */       int length = this.typeArguments.length;
/* 571 */       if (length == 0) {
/* 572 */         return GsonTypes.typeToString(this.rawType);
/*     */       }
/*     */       
/* 575 */       StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
/* 576 */       stringBuilder
/* 577 */         .append(GsonTypes.typeToString(this.rawType))
/* 578 */         .append("<")
/* 579 */         .append(GsonTypes.typeToString(this.typeArguments[0]));
/* 580 */       for (int i = 1; i < length; i++) {
/* 581 */         stringBuilder.append(", ").append(GsonTypes.typeToString(this.typeArguments[i]));
/*     */       }
/* 583 */       return stringBuilder.append(">").toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class GenericArrayTypeImpl
/*     */     implements GenericArrayType, Serializable
/*     */   {
/*     */     private final Type componentType;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public GenericArrayTypeImpl(Type componentType) {
/* 594 */       Objects.requireNonNull(componentType);
/* 595 */       this.componentType = GsonTypes.canonicalize(componentType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getGenericComponentType() {
/* 600 */       return this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 605 */       return (o instanceof GenericArrayType && GsonTypes.equals(this, (GenericArrayType)o));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 610 */       return this.componentType.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 615 */       return GsonTypes.typeToString(this.componentType) + "[]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class WildcardTypeImpl
/*     */     implements WildcardType, Serializable
/*     */   {
/*     */     private final Type upperBound;
/*     */ 
/*     */     
/*     */     private final Type lowerBound;
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */     
/*     */     public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
/* 635 */       GsonPreconditions.checkArgument((lowerBounds.length <= 1));
/* 636 */       GsonPreconditions.checkArgument((upperBounds.length == 1));
/*     */       
/* 638 */       if (lowerBounds.length == 1) {
/* 639 */         Objects.requireNonNull(lowerBounds[0]);
/* 640 */         GsonTypes.checkNotPrimitive(lowerBounds[0]);
/* 641 */         GsonPreconditions.checkArgument((upperBounds[0] == Object.class));
/* 642 */         this.lowerBound = GsonTypes.canonicalize(lowerBounds[0]);
/* 643 */         this.upperBound = Object.class;
/*     */       } else {
/*     */         
/* 646 */         Objects.requireNonNull(upperBounds[0]);
/* 647 */         GsonTypes.checkNotPrimitive(upperBounds[0]);
/* 648 */         this.lowerBound = null;
/* 649 */         this.upperBound = GsonTypes.canonicalize(upperBounds[0]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getUpperBounds() {
/* 655 */       return new Type[] { this.upperBound };
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getLowerBounds() {
/* 660 */       (new Type[1])[0] = this.lowerBound; return (this.lowerBound != null) ? new Type[1] : GsonTypes.EMPTY_TYPE_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 665 */       return (other instanceof WildcardType && GsonTypes.equals(this, (WildcardType)other));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 671 */       return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 676 */       if (this.lowerBound != null)
/* 677 */         return "? super " + GsonTypes.typeToString(this.lowerBound); 
/* 678 */       if (this.upperBound == Object.class) {
/* 679 */         return "?";
/*     */       }
/* 681 */       return "? extends " + GsonTypes.typeToString(this.upperBound);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\GsonTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */