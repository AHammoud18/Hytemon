/*     */ package com.google.gson.internal;
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
/*     */ public final class GsonTypes
/*     */ {
/*  45 */   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/*     */   
/*     */   private GsonTypes() {
/*  48 */     throw new UnsupportedOperationException();
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
/*  59 */     return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GenericArrayType arrayOf(Type componentType) {
/*  68 */     return new GenericArrayTypeImpl(componentType);
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
/*  79 */     if (bound instanceof WildcardType) {
/*  80 */       upperBounds = ((WildcardType)bound).getUpperBounds();
/*     */     } else {
/*  82 */       upperBounds = new Type[] { bound };
/*     */     } 
/*  84 */     return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WildcardType supertypeOf(Type bound) {
/*     */     Type[] lowerBounds;
/*  93 */     if (bound instanceof WildcardType) {
/*  94 */       lowerBounds = ((WildcardType)bound).getLowerBounds();
/*     */     } else {
/*  96 */       lowerBounds = new Type[] { bound };
/*     */     } 
/*  98 */     return new WildcardTypeImpl(new Type[] { Object.class }, lowerBounds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type canonicalize(Type type) {
/* 106 */     if (type instanceof Class) {
/* 107 */       Class<?> c = (Class)type;
/* 108 */       return c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c;
/*     */     } 
/* 110 */     if (type instanceof ParameterizedType) {
/* 111 */       ParameterizedType p = (ParameterizedType)type;
/* 112 */       return new ParameterizedTypeImpl(p
/* 113 */           .getOwnerType(), (Class)p.getRawType(), p.getActualTypeArguments());
/*     */     } 
/* 115 */     if (type instanceof GenericArrayType) {
/* 116 */       GenericArrayType g = (GenericArrayType)type;
/* 117 */       return new GenericArrayTypeImpl(g.getGenericComponentType());
/*     */     } 
/* 119 */     if (type instanceof WildcardType) {
/* 120 */       WildcardType w = (WildcardType)type;
/* 121 */       return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
/*     */     } 
/*     */ 
/*     */     
/* 125 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<?> getRawType(Type type) {
/* 130 */     if (type instanceof Class)
/*     */     {
/* 132 */       return (Class)type;
/*     */     }
/* 134 */     if (type instanceof ParameterizedType) {
/* 135 */       ParameterizedType parameterizedType = (ParameterizedType)type;
/*     */ 
/*     */ 
/*     */       
/* 139 */       Type rawType = parameterizedType.getRawType();
/* 140 */       return (Class)rawType;
/*     */     } 
/* 142 */     if (type instanceof GenericArrayType) {
/* 143 */       Type componentType = ((GenericArrayType)type).getGenericComponentType();
/* 144 */       return Array.newInstance(getRawType(componentType), 0).getClass();
/*     */     } 
/* 146 */     if (type instanceof TypeVariable)
/*     */     {
/*     */       
/* 149 */       return Object.class;
/*     */     }
/* 151 */     if (type instanceof WildcardType) {
/* 152 */       Type[] bounds = ((WildcardType)type).getUpperBounds();
/*     */       
/* 154 */       assert bounds.length == 1;
/* 155 */       return getRawType(bounds[0]);
/*     */     } 
/*     */     
/* 158 */     String className = (type == null) ? "null" : type.getClass().getName();
/* 159 */     throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equal(Object a, Object b) {
/* 168 */     return Objects.equals(a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equals(Type a, Type b) {
/* 173 */     if (a == b)
/*     */     {
/* 175 */       return true;
/*     */     }
/* 177 */     if (a instanceof Class)
/*     */     {
/* 179 */       return a.equals(b);
/*     */     }
/* 181 */     if (a instanceof ParameterizedType) {
/* 182 */       if (!(b instanceof ParameterizedType)) {
/* 183 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 187 */       ParameterizedType pa = (ParameterizedType)a;
/* 188 */       ParameterizedType pb = (ParameterizedType)b;
/* 189 */       return (equal(pa.getOwnerType(), pb.getOwnerType()) && pa
/* 190 */         .getRawType().equals(pb.getRawType()) && 
/* 191 */         Arrays.equals((Object[])pa.getActualTypeArguments(), (Object[])pb.getActualTypeArguments()));
/*     */     } 
/* 193 */     if (a instanceof GenericArrayType) {
/* 194 */       if (!(b instanceof GenericArrayType)) {
/* 195 */         return false;
/*     */       }
/*     */       
/* 198 */       GenericArrayType ga = (GenericArrayType)a;
/* 199 */       GenericArrayType gb = (GenericArrayType)b;
/* 200 */       return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
/*     */     } 
/* 202 */     if (a instanceof WildcardType) {
/* 203 */       if (!(b instanceof WildcardType)) {
/* 204 */         return false;
/*     */       }
/*     */       
/* 207 */       WildcardType wa = (WildcardType)a;
/* 208 */       WildcardType wb = (WildcardType)b;
/* 209 */       return (Arrays.equals((Object[])wa.getUpperBounds(), (Object[])wb.getUpperBounds()) && 
/* 210 */         Arrays.equals((Object[])wa.getLowerBounds(), (Object[])wb.getLowerBounds()));
/*     */     } 
/* 212 */     if (a instanceof TypeVariable) {
/* 213 */       if (!(b instanceof TypeVariable)) {
/* 214 */         return false;
/*     */       }
/* 216 */       TypeVariable<?> va = (TypeVariable)a;
/* 217 */       TypeVariable<?> vb = (TypeVariable)b;
/* 218 */       return (Objects.equals(va.getGenericDeclaration(), vb.getGenericDeclaration()) && va
/* 219 */         .getName().equals(vb.getName()));
/*     */     } 
/*     */ 
/*     */     
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String typeToString(Type type) {
/* 228 */     return (type instanceof Class) ? ((Class)type).getName() : type.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> supertype) {
/* 237 */     if (supertype == rawType) {
/* 238 */       return context;
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (supertype.isInterface()) {
/* 243 */       Class<?>[] interfaces = rawType.getInterfaces();
/* 244 */       for (int i = 0, length = interfaces.length; i < length; i++) {
/* 245 */         if (interfaces[i] == supertype)
/* 246 */           return rawType.getGenericInterfaces()[i]; 
/* 247 */         if (supertype.isAssignableFrom(interfaces[i])) {
/* 248 */           return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], supertype);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 254 */     if (!rawType.isInterface()) {
/* 255 */       while (rawType != Object.class) {
/* 256 */         Class<?> rawSupertype = rawType.getSuperclass();
/* 257 */         if (rawSupertype == supertype)
/* 258 */           return rawType.getGenericSuperclass(); 
/* 259 */         if (supertype.isAssignableFrom(rawSupertype)) {
/* 260 */           return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, supertype);
/*     */         }
/* 262 */         rawType = rawSupertype;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 267 */     return supertype;
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
/* 278 */     if (context instanceof WildcardType) {
/*     */ 
/*     */       
/* 281 */       Type[] bounds = ((WildcardType)context).getUpperBounds();
/*     */       
/* 283 */       assert bounds.length == 1;
/* 284 */       context = bounds[0];
/*     */     } 
/* 286 */     if (!supertype.isAssignableFrom(contextRawType)) {
/* 287 */       throw new IllegalArgumentException(contextRawType + " is not the same as or a subtype of " + supertype);
/*     */     }
/*     */     
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
/* 488 */     if (type instanceof Class && ((Class)type).isPrimitive()) {
/* 489 */       throw new IllegalArgumentException("Primitive type is not allowed");
/*     */     }
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
/* 501 */     if (rawType instanceof Class) {
/* 502 */       Class<?> rawTypeAsClass = (Class)rawType;
/* 503 */       return (!Modifier.isStatic(rawTypeAsClass.getModifiers()) && rawTypeAsClass
/* 504 */         .getDeclaringClass() != null);
/*     */     } 
/* 506 */     return false;
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
/*     */     ParameterizedTypeImpl(Type ownerType, Class<?> rawType, Type... typeArguments) {
/* 524 */       Objects.requireNonNull(rawType);
/*     */       
/* 526 */       if (ownerType == null && GsonTypes.requiresOwnerType(rawType)) {
/* 527 */         throw new IllegalArgumentException("Must specify owner type for " + rawType);
/*     */       }
/*     */       
/* 530 */       this.ownerType = (ownerType == null) ? null : GsonTypes.canonicalize(ownerType);
/* 531 */       this.rawType = GsonTypes.canonicalize(rawType);
/* 532 */       this.typeArguments = (Type[])typeArguments.clone();
/* 533 */       for (int t = 0, length = this.typeArguments.length; t < length; t++) {
/* 534 */         Objects.requireNonNull(this.typeArguments[t]);
/* 535 */         GsonTypes.checkNotPrimitive(this.typeArguments[t]);
/* 536 */         this.typeArguments[t] = GsonTypes.canonicalize(this.typeArguments[t]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getActualTypeArguments() {
/* 542 */       return (Type[])this.typeArguments.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getRawType() {
/* 547 */       return this.rawType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getOwnerType() {
/* 552 */       return this.ownerType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 557 */       return (other instanceof ParameterizedType && 
/* 558 */         GsonTypes.equals(this, (ParameterizedType)other));
/*     */     }
/*     */     
/*     */     private static int hashCodeOrZero(Object o) {
/* 562 */       return (o != null) ? o.hashCode() : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 567 */       return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType.hashCode() ^ hashCodeOrZero(this.ownerType);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 572 */       int length = this.typeArguments.length;
/* 573 */       if (length == 0) {
/* 574 */         return GsonTypes.typeToString(this.rawType);
/*     */       }
/*     */       
/* 577 */       StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
/* 578 */       stringBuilder
/* 579 */         .append(GsonTypes.typeToString(this.rawType))
/* 580 */         .append("<")
/* 581 */         .append(GsonTypes.typeToString(this.typeArguments[0]));
/* 582 */       for (int i = 1; i < length; i++) {
/* 583 */         stringBuilder.append(", ").append(GsonTypes.typeToString(this.typeArguments[i]));
/*     */       }
/* 585 */       return stringBuilder.append(">").toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class GenericArrayTypeImpl
/*     */     implements GenericArrayType, Serializable
/*     */   {
/*     */     private final Type componentType;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     GenericArrayTypeImpl(Type componentType) {
/* 596 */       Objects.requireNonNull(componentType);
/* 597 */       this.componentType = GsonTypes.canonicalize(componentType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getGenericComponentType() {
/* 602 */       return this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 607 */       return (o instanceof GenericArrayType && GsonTypes.equals(this, (GenericArrayType)o));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 612 */       return this.componentType.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 617 */       return GsonTypes.typeToString(this.componentType) + "[]";
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
/*     */     WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
/* 637 */       if (lowerBounds.length > 1) {
/* 638 */         throw new IllegalArgumentException("At most one lower bound is supported");
/*     */       }
/* 640 */       if (upperBounds.length != 1) {
/* 641 */         throw new IllegalArgumentException("Exactly one upper bound must be specified");
/*     */       }
/*     */       
/* 644 */       if (lowerBounds.length == 1) {
/* 645 */         Objects.requireNonNull(lowerBounds[0]);
/* 646 */         GsonTypes.checkNotPrimitive(lowerBounds[0]);
/* 647 */         if (upperBounds[0] != Object.class) {
/* 648 */           throw new IllegalArgumentException("When lower bound is specified, upper bound must be Object");
/*     */         }
/*     */         
/* 651 */         this.lowerBound = GsonTypes.canonicalize(lowerBounds[0]);
/* 652 */         this.upperBound = Object.class;
/*     */       } else {
/*     */         
/* 655 */         Objects.requireNonNull(upperBounds[0]);
/* 656 */         GsonTypes.checkNotPrimitive(upperBounds[0]);
/* 657 */         this.lowerBound = null;
/* 658 */         this.upperBound = GsonTypes.canonicalize(upperBounds[0]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getUpperBounds() {
/* 664 */       return new Type[] { this.upperBound };
/*     */     }
/*     */ 
/*     */     
/*     */     public Type[] getLowerBounds() {
/* 669 */       (new Type[1])[0] = this.lowerBound; return (this.lowerBound != null) ? new Type[1] : GsonTypes.EMPTY_TYPE_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 674 */       return (other instanceof WildcardType && GsonTypes.equals(this, (WildcardType)other));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 680 */       return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 685 */       if (this.lowerBound != null)
/* 686 */         return "? super " + GsonTypes.typeToString(this.lowerBound); 
/* 687 */       if (this.upperBound == Object.class) {
/* 688 */         return "?";
/*     */       }
/* 690 */       return "? extends " + GsonTypes.typeToString(this.upperBound);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\GsonTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */