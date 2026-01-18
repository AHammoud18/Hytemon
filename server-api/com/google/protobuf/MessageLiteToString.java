/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MessageLiteToString
/*     */ {
/*     */   private static final String LIST_SUFFIX = "List";
/*     */   private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
/*     */   private static final String MAP_SUFFIX = "Map";
/*     */   private static final String BYTES_SUFFIX = "Bytes";
/*  29 */   private static final char[] INDENT_BUFFER = new char[80];
/*     */   
/*     */   static {
/*  32 */     Arrays.fill(INDENT_BUFFER, ' ');
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
/*     */   static String toString(MessageLite messageLite, String commentString) {
/*  47 */     StringBuilder buffer = new StringBuilder();
/*  48 */     buffer.append("# ").append(commentString);
/*  49 */     reflectivePrintWithIndent(messageLite, buffer, 0);
/*  50 */     return buffer.toString();
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
/*     */   private static void reflectivePrintWithIndent(MessageLite messageLite, StringBuilder buffer, int indent) {
/*  64 */     Set<String> setters = new HashSet<>();
/*  65 */     Map<String, Method> hazzers = new HashMap<>();
/*  66 */     Map<String, Method> getters = new TreeMap<>();
/*  67 */     for (Method method : messageLite.getClass().getDeclaredMethods()) {
/*  68 */       if (!Modifier.isStatic(method.getModifiers()))
/*     */       {
/*     */         
/*  71 */         if (method.getName().length() >= 3)
/*     */         {
/*     */ 
/*     */           
/*  75 */           if (method.getName().startsWith("set")) {
/*  76 */             setters.add(method.getName());
/*     */ 
/*     */           
/*     */           }
/*  80 */           else if (Modifier.isPublic(method.getModifiers())) {
/*     */ 
/*     */ 
/*     */             
/*  84 */             if ((method.getParameterTypes()).length == 0)
/*     */             {
/*     */ 
/*     */               
/*  88 */               if (method.getName().startsWith("has")) {
/*  89 */                 hazzers.put(method.getName(), method);
/*  90 */               } else if (method.getName().startsWith("get")) {
/*  91 */                 getters.put(method.getName(), method);
/*     */               }  } 
/*     */           }  }  } 
/*     */     } 
/*  95 */     for (Map.Entry<String, Method> getter : getters.entrySet()) {
/*  96 */       String suffix = ((String)getter.getKey()).substring(3);
/*  97 */       if (suffix.endsWith("List") && 
/*  98 */         !suffix.endsWith("OrBuilderList") && 
/*     */         
/* 100 */         !suffix.equals("List")) {
/*     */ 
/*     */         
/* 103 */         Method listMethod = getter.getValue();
/* 104 */         if (listMethod != null && listMethod.getReturnType().equals(List.class)) {
/* 105 */           printField(buffer, indent, suffix
/*     */ 
/*     */               
/* 108 */               .substring(0, suffix.length() - "List".length()), 
/* 109 */               GeneratedMessageLite.invokeOrDie(listMethod, messageLite, new Object[0]));
/*     */           continue;
/*     */         } 
/*     */       } 
/* 113 */       if (suffix.endsWith("Map") && 
/*     */         
/* 115 */         !suffix.equals("Map")) {
/*     */ 
/*     */         
/* 118 */         Method mapMethod = getter.getValue();
/* 119 */         if (mapMethod != null && mapMethod
/* 120 */           .getReturnType().equals(Map.class) && 
/*     */ 
/*     */           
/* 123 */           !mapMethod.isAnnotationPresent((Class)Deprecated.class) && 
/*     */           
/* 125 */           Modifier.isPublic(mapMethod.getModifiers())) {
/* 126 */           printField(buffer, indent, suffix
/*     */ 
/*     */               
/* 129 */               .substring(0, suffix.length() - "Map".length()), 
/* 130 */               GeneratedMessageLite.invokeOrDie(mapMethod, messageLite, new Object[0]));
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 135 */       if (!setters.contains("set" + suffix)) {
/*     */         continue;
/*     */       }
/* 138 */       if (suffix.endsWith("Bytes") && getters
/* 139 */         .containsKey("get" + suffix.substring(0, suffix.length() - "Bytes".length()))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       Method getMethod = getter.getValue();
/* 147 */       Method hasMethod = hazzers.get("has" + suffix);
/*     */       
/* 149 */       if (getMethod != null) {
/* 150 */         Object value = GeneratedMessageLite.invokeOrDie(getMethod, messageLite, new Object[0]);
/*     */ 
/*     */ 
/*     */         
/* 154 */         boolean hasValue = (hasMethod == null) ? (!isDefaultValue(value)) : ((Boolean)GeneratedMessageLite.invokeOrDie(hasMethod, messageLite, new Object[0])).booleanValue();
/*     */         
/* 156 */         if (hasValue) {
/* 157 */           printField(buffer, indent, suffix, value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     if (messageLite instanceof GeneratedMessageLite.ExtendableMessage) {
/*     */       
/* 165 */       Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter = ((GeneratedMessageLite.ExtendableMessage)messageLite).extensions.iterator();
/* 166 */       while (iter.hasNext()) {
/* 167 */         Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> entry = iter.next();
/* 168 */         printField(buffer, indent, "[" + ((GeneratedMessageLite.ExtensionDescriptor)entry.getKey()).getNumber() + "]", entry.getValue());
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     if (((GeneratedMessageLite)messageLite).unknownFields != null) {
/* 173 */       ((GeneratedMessageLite)messageLite).unknownFields.printWithIndent(buffer, indent);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isDefaultValue(Object o) {
/* 178 */     if (o instanceof Boolean) {
/* 179 */       return !((Boolean)o).booleanValue();
/*     */     }
/* 181 */     if (o instanceof Integer) {
/* 182 */       return (((Integer)o).intValue() == 0);
/*     */     }
/* 184 */     if (o instanceof Float) {
/* 185 */       return (Float.floatToRawIntBits(((Float)o).floatValue()) == 0);
/*     */     }
/* 187 */     if (o instanceof Double) {
/* 188 */       return (Double.doubleToRawLongBits(((Double)o).doubleValue()) == 0L);
/*     */     }
/* 190 */     if (o instanceof String) {
/* 191 */       return o.equals("");
/*     */     }
/* 193 */     if (o instanceof ByteString) {
/* 194 */       return o.equals(ByteString.EMPTY);
/*     */     }
/* 196 */     if (o instanceof MessageLite) {
/* 197 */       return (o == ((MessageLite)o).getDefaultInstanceForType());
/*     */     }
/* 199 */     if (o instanceof java.lang.Enum) {
/* 200 */       return (((java.lang.Enum)o).ordinal() == 0);
/*     */     }
/*     */     
/* 203 */     return false;
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
/*     */   static void printField(StringBuilder buffer, int indent, String name, Object object) {
/* 217 */     if (object instanceof List) {
/* 218 */       List<?> list = (List)object;
/* 219 */       for (Object entry : list) {
/* 220 */         printField(buffer, indent, name, entry);
/*     */       }
/*     */       return;
/*     */     } 
/* 224 */     if (object instanceof Map) {
/* 225 */       Map<?, ?> map = (Map<?, ?>)object;
/* 226 */       for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 227 */         printField(buffer, indent, name, entry);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 232 */     buffer.append('\n');
/* 233 */     indent(indent, buffer);
/* 234 */     buffer.append(pascalCaseToSnakeCase(name));
/*     */     
/* 236 */     if (object instanceof String) {
/* 237 */       buffer.append(": \"").append(TextFormatEscaper.escapeText((String)object)).append('"');
/* 238 */     } else if (object instanceof ByteString) {
/* 239 */       buffer.append(": \"").append(TextFormatEscaper.escapeBytes((ByteString)object)).append('"');
/* 240 */     } else if (object instanceof GeneratedMessageLite) {
/* 241 */       buffer.append(" {");
/* 242 */       reflectivePrintWithIndent((GeneratedMessageLite)object, buffer, indent + 2);
/* 243 */       buffer.append("\n");
/* 244 */       indent(indent, buffer);
/* 245 */       buffer.append("}");
/* 246 */     } else if (object instanceof Map.Entry) {
/* 247 */       buffer.append(" {");
/* 248 */       Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
/* 249 */       printField(buffer, indent + 2, "key", entry.getKey());
/* 250 */       printField(buffer, indent + 2, "value", entry.getValue());
/* 251 */       buffer.append("\n");
/* 252 */       indent(indent, buffer);
/* 253 */       buffer.append("}");
/*     */     } else {
/* 255 */       buffer.append(": ").append(object);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void indent(int indent, StringBuilder buffer) {
/* 260 */     while (indent > 0) {
/* 261 */       int partialIndent = indent;
/* 262 */       if (partialIndent > INDENT_BUFFER.length) {
/* 263 */         partialIndent = INDENT_BUFFER.length;
/*     */       }
/* 265 */       buffer.append(INDENT_BUFFER, 0, partialIndent);
/* 266 */       indent -= partialIndent;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String pascalCaseToSnakeCase(String pascalCase) {
/* 271 */     if (pascalCase.isEmpty()) {
/* 272 */       return pascalCase;
/*     */     }
/*     */     
/* 275 */     StringBuilder builder = new StringBuilder();
/* 276 */     builder.append(Character.toLowerCase(pascalCase.charAt(0)));
/* 277 */     for (int i = 1; i < pascalCase.length(); i++) {
/* 278 */       char ch = pascalCase.charAt(i);
/* 279 */       if (Character.isUpperCase(ch)) {
/* 280 */         builder.append("_");
/*     */       }
/* 282 */       builder.append(Character.toLowerCase(ch));
/*     */     } 
/* 284 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageLiteToString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */