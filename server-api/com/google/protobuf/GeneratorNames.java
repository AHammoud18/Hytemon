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
/*     */ public final class GeneratorNames
/*     */ {
/*     */   public static String getFileJavaPackage(DescriptorProtos.FileDescriptorProtoOrBuilder file) {
/*  24 */     return getProto2ApiDefaultJavaPackage(file.getOptions(), file.getPackage());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFileJavaPackage(Descriptors.FileDescriptor file) {
/*  29 */     return getProto2ApiDefaultJavaPackage(file.getOptions(), file.getPackage());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getDefaultJavaPackage(DescriptorProtos.FileOptions fileOptions, String filePackage) {
/*  35 */     if (fileOptions.hasJavaPackage()) {
/*  36 */       return fileOptions.getJavaPackage();
/*     */     }
/*  38 */     return filePackage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String joinPackage(String a, String b) {
/*  44 */     if (a.isEmpty())
/*  45 */       return b; 
/*  46 */     if (b.isEmpty()) {
/*  47 */       return a;
/*     */     }
/*  49 */     return a + '.' + b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getProto2ApiDefaultJavaPackage(DescriptorProtos.FileOptions fileOptions, String filePackage) {
/*  59 */     return getDefaultJavaPackage(fileOptions, filePackage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFileClassName(DescriptorProtos.FileDescriptorProtoOrBuilder file) {
/*  66 */     return getFileClassNameImpl(file, getResolvedFileFeatures(JavaFeaturesProto.java_, file));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFileClassName(Descriptors.FileDescriptor file) {
/*  71 */     return getFileClassNameImpl(file
/*  72 */         .toProto(), (JavaFeaturesProto.JavaFeatures)file.getFeatures().getExtension(JavaFeaturesProto.java_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getFileClassNameImpl(DescriptorProtos.FileDescriptorProtoOrBuilder file, JavaFeaturesProto.JavaFeatures resolvedFeatures) {
/*  78 */     if (file.getOptions().hasJavaOuterClassname()) {
/*  79 */       return file.getOptions().getJavaOuterClassname();
/*     */     }
/*     */ 
/*     */     
/*  83 */     String className = getDefaultFileClassName(file, resolvedFeatures.getUseOldOuterClassnameDefault());
/*  84 */     if (resolvedFeatures.getUseOldOuterClassnameDefault() && 
/*  85 */       hasConflictingClassName(file, className)) {
/*  86 */       return className + "OuterClass";
/*     */     }
/*  88 */     return className;
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
/*     */   static <T extends Message> T getResolvedFileFeatures(GeneratedMessage.GeneratedExtension<DescriptorProtos.FeatureSet, T> ext, DescriptorProtos.FileDescriptorProtoOrBuilder file) {
/*     */     DescriptorProtos.Edition edition;
/* 102 */     if (file.getSyntax().equals("proto3")) {
/* 103 */       edition = DescriptorProtos.Edition.EDITION_PROTO3;
/* 104 */     } else if (!file.hasEdition()) {
/* 105 */       edition = DescriptorProtos.Edition.EDITION_PROTO2;
/*     */     } else {
/* 107 */       edition = file.getEdition();
/*     */     } 
/* 109 */     DescriptorProtos.FeatureSet features = file.getOptions().getFeatures();
/* 110 */     if (features.getUnknownFields().hasField(ext.getNumber())) {
/*     */ 
/*     */       
/* 113 */       ExtensionRegistry registry = ExtensionRegistry.newInstance();
/* 114 */       registry.add(ext);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 119 */         features = DescriptorProtos.FeatureSet.newBuilder().mergeFrom(features.getUnknownFields().toByteString(), registry).build();
/* 120 */       } catch (InvalidProtocolBufferException e) {
/*     */ 
/*     */         
/* 123 */         throw new IllegalArgumentException("Failed to parse features", e);
/*     */       } 
/*     */     } 
/* 126 */     return 
/* 127 */       (T)((Message)Descriptors.getEditionDefaults(edition).getExtension(ext)).toBuilder()
/* 128 */       .mergeFrom((Message)features.getExtension(ext))
/* 129 */       .build();
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
/*     */   static String getDefaultFileClassName(DescriptorProtos.FileDescriptorProtoOrBuilder file, boolean useOldOuterClassnameDefault) {
/* 141 */     String name = file.getName();
/*     */     
/* 143 */     name = name.substring(name.lastIndexOf('/') + 1);
/* 144 */     name = underscoresToCamelCase(stripProto(name));
/* 145 */     return useOldOuterClassnameDefault ? name : (name + "Proto");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String stripProto(String filename) {
/* 150 */     if (filename.endsWith(".protodevel")) {
/* 151 */       return filename.substring(0, filename.length() - ".protodevel".length());
/*     */     }
/* 153 */     if (filename.endsWith(".proto")) {
/* 154 */       return filename.substring(0, filename.length() - ".proto".length());
/*     */     }
/*     */     
/* 157 */     return filename;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasConflictingClassName(DescriptorProtos.FileDescriptorProtoOrBuilder file, String name) {
/* 162 */     for (DescriptorProtos.EnumDescriptorProto enumDesc : file.getEnumTypeList()) {
/* 163 */       if (name.equals(enumDesc.getName())) {
/* 164 */         return true;
/*     */       }
/*     */     } 
/* 167 */     for (DescriptorProtos.ServiceDescriptorProto serviceDesc : file.getServiceList()) {
/* 168 */       if (name.equals(serviceDesc.getName())) {
/* 169 */         return true;
/*     */       }
/*     */     } 
/* 172 */     for (DescriptorProtos.DescriptorProto messageDesc : file.getMessageTypeList()) {
/* 173 */       if (hasConflictingClassName(messageDesc, name)) {
/* 174 */         return true;
/*     */       }
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasConflictingClassName(DescriptorProtos.DescriptorProto messageDesc, String name) {
/* 182 */     if (name.equals(messageDesc.getName())) {
/* 183 */       return true;
/*     */     }
/* 185 */     for (DescriptorProtos.EnumDescriptorProto enumDesc : messageDesc.getEnumTypeList()) {
/* 186 */       if (name.equals(enumDesc.getName())) {
/* 187 */         return true;
/*     */       }
/*     */     } 
/* 190 */     for (DescriptorProtos.DescriptorProto nestedMessageDesc : messageDesc.getNestedTypeList()) {
/* 191 */       if (hasConflictingClassName(nestedMessageDesc, name)) {
/* 192 */         return true;
/*     */       }
/*     */     } 
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String underscoresToCamelCase(String input, boolean capitalizeNextLetter) {
/* 206 */     StringBuilder result = new StringBuilder();
/* 207 */     for (int i = 0; i < input.length(); i++) {
/* 208 */       char ch = input.charAt(i);
/* 209 */       if ('a' <= ch && ch <= 'z') {
/* 210 */         if (capitalizeNextLetter) {
/* 211 */           result.append((char)(ch + -32));
/*     */         } else {
/* 213 */           result.append(ch);
/*     */         } 
/* 215 */         capitalizeNextLetter = false;
/* 216 */       } else if ('A' <= ch && ch <= 'Z') {
/* 217 */         if (i == 0 && !capitalizeNextLetter) {
/*     */ 
/*     */           
/* 220 */           result.append((char)(ch + 32));
/*     */         } else {
/*     */           
/* 223 */           result.append(ch);
/*     */         } 
/* 225 */         capitalizeNextLetter = false;
/* 226 */       } else if ('0' <= ch && ch <= '9') {
/* 227 */         result.append(ch);
/* 228 */         capitalizeNextLetter = true;
/*     */       } else {
/* 230 */         capitalizeNextLetter = true;
/*     */       } 
/*     */     } 
/* 233 */     return result.toString();
/*     */   }
/*     */   
/*     */   static String underscoresToCamelCase(String input) {
/* 237 */     return underscoresToCamelCase(input, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBytecodeClassName(Descriptors.Descriptor message) {
/* 247 */     return getClassFullName(
/* 248 */         getClassNameWithoutPackage(message), message.getFile(), !getNestInFileClass(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBytecodeClassName(Descriptors.EnumDescriptor enm) {
/* 258 */     return getClassFullName(
/* 259 */         getClassNameWithoutPackage(enm), enm.getFile(), !getNestInFileClass(enm));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getBytecodeClassName(Descriptors.ServiceDescriptor service) {
/* 269 */     String suffix = "";
/* 270 */     boolean isOwnFile = !getNestInFileClass(service);
/* 271 */     return getClassFullName(getClassNameWithoutPackage(service), service.getFile(), isOwnFile) + suffix;
/*     */   }
/*     */ 
/*     */   
/*     */   static String getQualifiedFromBytecodeClassName(String bytecodeClassName) {
/* 276 */     return bytecodeClassName.replace('$', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getQualifiedClassName(Descriptors.Descriptor message) {
/* 285 */     return getQualifiedFromBytecodeClassName(getBytecodeClassName(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getQualifiedClassName(Descriptors.EnumDescriptor enm) {
/* 294 */     return getQualifiedFromBytecodeClassName(getBytecodeClassName(enm));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getQualifiedClassName(Descriptors.ServiceDescriptor service) {
/* 303 */     return getQualifiedFromBytecodeClassName(getBytecodeClassName(service));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getClassFullName(String nameWithoutPackage, Descriptors.FileDescriptor file, boolean isOwnFile) {
/* 309 */     StringBuilder result = new StringBuilder();
/* 310 */     if (isOwnFile) {
/* 311 */       result.append(getFileJavaPackage(file.toProto()));
/* 312 */       if (result.length() > 0) {
/* 313 */         result.append(".");
/*     */       }
/*     */     } else {
/* 316 */       result.append(joinPackage(getFileJavaPackage(file.toProto()), getFileClassName(file)));
/* 317 */       if (result.length() > 0) {
/* 318 */         result.append("$");
/*     */       }
/*     */     } 
/* 321 */     result.append(nameWithoutPackage.replace('.', '$'));
/* 322 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getNestInFileClass(Descriptors.FileDescriptor file, JavaFeaturesProto.JavaFeatures resolvedFeatures) {
/* 329 */     switch (resolvedFeatures.getNestInFileClass()) {
/*     */       case YES:
/* 331 */         return true;
/*     */       case NO:
/* 333 */         return false;
/*     */       case LEGACY:
/* 335 */         return !file.getOptions().getJavaMultipleFiles();
/*     */     } 
/* 337 */     throw new IllegalArgumentException("Java features are not resolved");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getNestInFileClass(Descriptors.Descriptor descriptor) {
/* 342 */     return getNestInFileClass(descriptor
/* 343 */         .getFile(), (JavaFeaturesProto.JavaFeatures)descriptor.getFeatures().getExtension(JavaFeaturesProto.java_));
/*     */   }
/*     */   
/*     */   public static boolean getNestInFileClass(Descriptors.EnumDescriptor descriptor) {
/* 347 */     return getNestInFileClass(descriptor
/* 348 */         .getFile(), (JavaFeaturesProto.JavaFeatures)descriptor.getFeatures().getExtension(JavaFeaturesProto.java_));
/*     */   }
/*     */   
/*     */   private static boolean getNestInFileClass(Descriptors.ServiceDescriptor descriptor) {
/* 352 */     return getNestInFileClass(descriptor
/* 353 */         .getFile(), (JavaFeaturesProto.JavaFeatures)descriptor.getFeatures().getExtension(JavaFeaturesProto.java_));
/*     */   }
/*     */ 
/*     */   
/*     */   static String stripPackageName(String fullName, Descriptors.FileDescriptor file) {
/* 358 */     if (file.getPackage().isEmpty()) {
/* 359 */       return fullName;
/*     */     }
/* 361 */     return fullName.substring(file.getPackage().length() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getClassNameWithoutPackage(Descriptors.Descriptor message) {
/* 367 */     return stripPackageName(message.getFullName(), message.getFile());
/*     */   }
/*     */ 
/*     */   
/*     */   static String getClassNameWithoutPackage(Descriptors.EnumDescriptor enm) {
/* 372 */     Descriptors.Descriptor containingType = enm.getContainingType();
/* 373 */     if (containingType == null) {
/* 374 */       return enm.getName();
/*     */     }
/* 376 */     return joinPackage(getClassNameWithoutPackage(containingType), enm.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   static String getClassNameWithoutPackage(Descriptors.ServiceDescriptor service) {
/* 381 */     return stripPackageName(service.getFullName(), service.getFile());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratorNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */