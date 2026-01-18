/*     */ package com.nimbusds.jose.shaded.gson.reflect;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.internal.GsonTypes;
/*     */ import com.nimbusds.jose.shaded.gson.internal.TroubleshootingGuide;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ public class TypeToken<T>
/*     */ {
/*     */   private final Class<? super T> rawType;
/*     */   private final Type type;
/*     */   private final int hashCode;
/*     */   
/*     */   protected TypeToken() {
/*  74 */     this.type = getTypeTokenTypeArgument();
/*  75 */     this.rawType = GsonTypes.getRawType(this.type);
/*  76 */     this.hashCode = this.type.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeToken(Type type) {
/*  82 */     this.type = GsonTypes.canonicalize(Objects.<Type>requireNonNull(type));
/*  83 */     this.rawType = GsonTypes.getRawType(this.type);
/*  84 */     this.hashCode = this.type.hashCode();
/*     */   }
/*     */   
/*     */   private static boolean isCapturingTypeVariablesForbidden() {
/*  88 */     return !Objects.equals(System.getProperty("gson.allowCapturingTypeVariables"), "true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type getTypeTokenTypeArgument() {
/*  96 */     Type superclass = getClass().getGenericSuperclass();
/*  97 */     if (superclass instanceof ParameterizedType) {
/*  98 */       ParameterizedType parameterized = (ParameterizedType)superclass;
/*  99 */       if (parameterized.getRawType() == TypeToken.class) {
/* 100 */         Type typeArgument = GsonTypes.canonicalize(parameterized.getActualTypeArguments()[0]);
/*     */         
/* 102 */         if (isCapturingTypeVariablesForbidden()) {
/* 103 */           verifyNoTypeVariable(typeArgument);
/*     */         }
/* 105 */         return typeArgument;
/*     */       }
/*     */     
/*     */     }
/* 109 */     else if (superclass == TypeToken.class) {
/* 110 */       throw new IllegalStateException("TypeToken must be created with a type argument: new TypeToken<...>() {}; When using code shrinkers (ProGuard, R8, ...) make sure that generic signatures are preserved.\nSee " + 
/*     */ 
/*     */ 
/*     */           
/* 114 */           TroubleshootingGuide.createUrl("type-token-raw"));
/*     */     } 
/*     */ 
/*     */     
/* 118 */     throw new IllegalStateException("Must only create direct subclasses of TypeToken");
/*     */   }
/*     */   
/*     */   private static void verifyNoTypeVariable(Type type) {
/* 122 */     if (type instanceof TypeVariable) {
/* 123 */       TypeVariable<?> typeVariable = (TypeVariable)type;
/* 124 */       throw new IllegalArgumentException("TypeToken type argument must not contain a type variable; captured type variable " + typeVariable
/*     */           
/* 126 */           .getName() + " declared by " + typeVariable
/*     */           
/* 128 */           .getGenericDeclaration() + "\nSee " + 
/*     */           
/* 130 */           TroubleshootingGuide.createUrl("typetoken-type-variable"));
/* 131 */     }  if (type instanceof GenericArrayType) {
/* 132 */       verifyNoTypeVariable(((GenericArrayType)type).getGenericComponentType());
/* 133 */     } else if (type instanceof ParameterizedType) {
/* 134 */       ParameterizedType parameterizedType = (ParameterizedType)type;
/* 135 */       Type ownerType = parameterizedType.getOwnerType();
/* 136 */       if (ownerType != null) {
/* 137 */         verifyNoTypeVariable(ownerType);
/*     */       }
/*     */       
/* 140 */       for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
/* 141 */         verifyNoTypeVariable(typeArgument);
/*     */       }
/* 143 */     } else if (type instanceof WildcardType) {
/* 144 */       WildcardType wildcardType = (WildcardType)type;
/* 145 */       for (Type bound : wildcardType.getLowerBounds()) {
/* 146 */         verifyNoTypeVariable(bound);
/*     */       }
/* 148 */       for (Type bound : wildcardType.getUpperBounds()) {
/* 149 */         verifyNoTypeVariable(bound);
/*     */       }
/* 151 */     } else if (type == null) {
/*     */ 
/*     */ 
/*     */       
/* 155 */       throw new IllegalArgumentException("TypeToken captured `null` as type argument; probably a compiler / runtime bug");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<? super T> getRawType() {
/* 162 */     return this.rawType;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Type getType() {
/* 167 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(Class<?> cls) {
/* 177 */     return isAssignableFrom(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(Type from) {
/* 187 */     if (from == null) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     if (this.type.equals(from)) {
/* 192 */       return true;
/*     */     }
/*     */     
/* 195 */     if (this.type instanceof Class)
/* 196 */       return this.rawType.isAssignableFrom(GsonTypes.getRawType(from)); 
/* 197 */     if (this.type instanceof ParameterizedType)
/* 198 */       return isAssignableFrom(from, (ParameterizedType)this.type, new HashMap<>()); 
/* 199 */     if (this.type instanceof GenericArrayType) {
/* 200 */       return (this.rawType.isAssignableFrom(GsonTypes.getRawType(from)) && 
/* 201 */         isAssignableFrom(from, (GenericArrayType)this.type));
/*     */     }
/* 203 */     throw buildUnsupportedTypeException(this.type, new Class[] { Class.class, ParameterizedType.class, GenericArrayType.class });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(TypeToken<?> token) {
/* 215 */     return isAssignableFrom(token.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAssignableFrom(Type<?> from, GenericArrayType to) {
/* 223 */     Type toGenericComponentType = to.getGenericComponentType();
/* 224 */     if (toGenericComponentType instanceof ParameterizedType) {
/* 225 */       Type<?> t = from;
/* 226 */       if (from instanceof GenericArrayType) {
/* 227 */         t = ((GenericArrayType)from).getGenericComponentType();
/* 228 */       } else if (from instanceof Class) {
/* 229 */         Class<?> classType = (Class)from;
/* 230 */         while (classType.isArray()) {
/* 231 */           classType = classType.getComponentType();
/*     */         }
/* 233 */         t = classType;
/*     */       } 
/* 235 */       return isAssignableFrom(t, (ParameterizedType)toGenericComponentType, new HashMap<>());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAssignableFrom(Type from, ParameterizedType to, Map<String, Type> typeVarMap) {
/* 247 */     if (from == null) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     if (to.equals(from)) {
/* 252 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 256 */     Class<?> clazz = GsonTypes.getRawType(from);
/* 257 */     ParameterizedType ptype = null;
/* 258 */     if (from instanceof ParameterizedType) {
/* 259 */       ptype = (ParameterizedType)from;
/*     */     }
/*     */ 
/*     */     
/* 263 */     if (ptype != null) {
/* 264 */       Type[] tArgs = ptype.getActualTypeArguments();
/* 265 */       TypeVariable[] arrayOfTypeVariable = (TypeVariable[])clazz.getTypeParameters();
/* 266 */       for (int i = 0; i < tArgs.length; i++) {
/* 267 */         Type arg = tArgs[i];
/* 268 */         TypeVariable<?> var = arrayOfTypeVariable[i];
/* 269 */         while (arg instanceof TypeVariable) {
/* 270 */           TypeVariable<?> v = (TypeVariable)arg;
/* 271 */           arg = typeVarMap.get(v.getName());
/*     */         } 
/* 273 */         typeVarMap.put(var.getName(), arg);
/*     */       } 
/*     */ 
/*     */       
/* 277 */       if (typeEquals(ptype, to, typeVarMap)) {
/* 278 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 282 */     for (Type itype : clazz.getGenericInterfaces()) {
/* 283 */       if (isAssignableFrom(itype, to, new HashMap<>(typeVarMap))) {
/* 284 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 289 */     Type sType = clazz.getGenericSuperclass();
/* 290 */     return isAssignableFrom(sType, to, new HashMap<>(typeVarMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean typeEquals(ParameterizedType from, ParameterizedType to, Map<String, Type> typeVarMap) {
/* 299 */     if (from.getRawType().equals(to.getRawType())) {
/* 300 */       Type[] fromArgs = from.getActualTypeArguments();
/* 301 */       Type[] toArgs = to.getActualTypeArguments();
/* 302 */       for (int i = 0; i < fromArgs.length; i++) {
/* 303 */         if (!matches(fromArgs[i], toArgs[i], typeVarMap)) {
/* 304 */           return false;
/*     */         }
/*     */       } 
/* 307 */       return true;
/*     */     } 
/* 309 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IllegalArgumentException buildUnsupportedTypeException(Type token, Class<?>... expected) {
/* 316 */     StringBuilder exceptionMessage = new StringBuilder("Unsupported type, expected one of: ");
/* 317 */     for (Class<?> clazz : expected) {
/* 318 */       exceptionMessage.append(clazz.getName()).append(", ");
/*     */     }
/* 320 */     exceptionMessage
/* 321 */       .append("but got: ")
/* 322 */       .append(token.getClass().getName())
/* 323 */       .append(", for type token: ")
/* 324 */       .append(token.toString());
/*     */     
/* 326 */     return new IllegalArgumentException(exceptionMessage.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean matches(Type from, Type to, Map<String, Type> typeMap) {
/* 334 */     return (to.equals(from) || (from instanceof TypeVariable && to
/*     */       
/* 336 */       .equals(typeMap.get(((TypeVariable)from).getName()))));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 341 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/* 346 */     return (o instanceof TypeToken && GsonTypes.equals(this.type, ((TypeToken)o).type));
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 351 */     return GsonTypes.typeToString(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TypeToken<?> get(Type type) {
/* 356 */     return new TypeToken(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> TypeToken<T> get(Class<T> type) {
/* 361 */     return new TypeToken<>(type);
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
/*     */   public static TypeToken<?> getParameterized(Type rawType, Type... typeArguments) {
/* 386 */     Objects.requireNonNull(rawType);
/* 387 */     Objects.requireNonNull(typeArguments);
/*     */ 
/*     */ 
/*     */     
/* 391 */     if (!(rawType instanceof Class))
/*     */     {
/* 393 */       throw new IllegalArgumentException("rawType must be of type Class, but was " + rawType);
/*     */     }
/* 395 */     Class<?> rawClass = (Class)rawType;
/* 396 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawClass.getTypeParameters();
/*     */     
/* 398 */     int expectedArgsCount = arrayOfTypeVariable.length;
/* 399 */     int actualArgsCount = typeArguments.length;
/* 400 */     if (actualArgsCount != expectedArgsCount) {
/* 401 */       throw new IllegalArgumentException(rawClass
/* 402 */           .getName() + " requires " + expectedArgsCount + " type arguments, but got " + actualArgsCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     if (typeArguments.length == 0) {
/* 411 */       return get(rawClass);
/*     */     }
/*     */ 
/*     */     
/* 415 */     if (GsonTypes.requiresOwnerType(rawType)) {
/* 416 */       throw new IllegalArgumentException("Raw type " + rawClass
/*     */           
/* 418 */           .getName() + " is not supported because it requires specifying an owner type");
/*     */     }
/*     */ 
/*     */     
/* 422 */     for (int i = 0; i < expectedArgsCount; i++) {
/*     */       
/* 424 */       Type typeArgument = Objects.<Type>requireNonNull(typeArguments[i], "Type argument must not be null");
/* 425 */       Class<?> rawTypeArgument = GsonTypes.getRawType(typeArgument);
/* 426 */       TypeVariable<?> typeVariable = arrayOfTypeVariable[i];
/*     */       
/* 428 */       for (Type bound : typeVariable.getBounds()) {
/* 429 */         Class<?> rawBound = GsonTypes.getRawType(bound);
/*     */         
/* 431 */         if (!rawBound.isAssignableFrom(rawTypeArgument)) {
/* 432 */           throw new IllegalArgumentException("Type argument " + typeArgument + " does not satisfy bounds for type variable " + typeVariable + " declared by " + rawType);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     return new TypeToken(GsonTypes.newParameterizedTypeWithOwner(null, rawClass, typeArguments));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeToken<?> getArray(Type componentType) {
/* 450 */     return new TypeToken(GsonTypes.arrayOf(componentType));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\reflect\TypeToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */