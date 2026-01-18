/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CheckReturnValue
/*      */ public final class Descriptors
/*      */ {
/*   66 */   private static final Logger logger = Logger.getLogger(Descriptors.class.getName());
/*   67 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/*   68 */   private static final Descriptor[] EMPTY_DESCRIPTORS = new Descriptor[0];
/*   69 */   private static final FieldDescriptor[] EMPTY_FIELD_DESCRIPTORS = new FieldDescriptor[0];
/*   70 */   private static final EnumDescriptor[] EMPTY_ENUM_DESCRIPTORS = new EnumDescriptor[0];
/*   71 */   private static final ServiceDescriptor[] EMPTY_SERVICE_DESCRIPTORS = new ServiceDescriptor[0];
/*   72 */   private static final OneofDescriptor[] EMPTY_ONEOF_DESCRIPTORS = new OneofDescriptor[0];
/*   73 */   private static final ConcurrentHashMap<DescriptorProtos.FeatureSet, DescriptorProtos.FeatureSet> FEATURE_CACHE = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */   
/*   77 */   private static volatile DescriptorProtos.FeatureSetDefaults javaEditionDefaults = null;
/*      */ 
/*      */   
/*      */   static void setTestJavaEditionDefaults(DescriptorProtos.FeatureSetDefaults defaults) {
/*   81 */     javaEditionDefaults = defaults;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static DescriptorProtos.FeatureSetDefaults getJavaEditionDefaults() {
/*   90 */     Descriptor unused1 = DescriptorProtos.FeatureSetDefaults.getDescriptor();
/*   91 */     FileDescriptor unused2 = JavaFeaturesProto.getDescriptor();
/*   92 */     if (javaEditionDefaults == null) {
/*   93 */       synchronized (Descriptors.class) {
/*   94 */         if (javaEditionDefaults == null) {
/*      */           try {
/*   96 */             ExtensionRegistry registry = ExtensionRegistry.newInstance();
/*   97 */             registry.add(JavaFeaturesProto.java_);
/*   98 */             setTestJavaEditionDefaults(
/*   99 */                 DescriptorProtos.FeatureSetDefaults.parseFrom("\n'\030\007\"\003Ê>\000*\035\b\001\020\002\030\002 \003(\0010\0028\002@\001Ê>\n\b\001\020\001\030\000 \001(\003\n'\030ç\007\"\003Ê>\000*\035\b\002\020\001\030\001 \002(\0010\0018\002@\001Ê>\n\b\000\020\001\030\000 \001(\003\n'\030è\007\"\023\b\001\020\001\030\001 \002(\0010\001Ê>\004\b\000\020\001*\r8\002@\001Ê>\006\030\000 \001(\003\n'\030é\007\"\033\b\001\020\001\030\001 \002(\0010\0018\001@\002Ê>\b\b\000\020\001\030\000(\001*\005Ê>\002 \000 æ\007(é\007"
/*  100 */                   .getBytes(Internal.ISO_8859_1), registry));
/*      */           
/*      */           }
/*  103 */           catch (Exception e) {
/*  104 */             throw new AssertionError(e);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*  109 */     return javaEditionDefaults;
/*      */   }
/*      */   
/*      */   static DescriptorProtos.FeatureSet getEditionDefaults(DescriptorProtos.Edition edition) {
/*  113 */     DescriptorProtos.FeatureSetDefaults javaEditionDefaults = getJavaEditionDefaults();
/*  114 */     if (edition.getNumber() < javaEditionDefaults.getMinimumEdition().getNumber()) {
/*  115 */       throw new IllegalArgumentException("Edition " + edition + " is lower than the minimum supported edition " + javaEditionDefaults
/*      */ 
/*      */ 
/*      */           
/*  119 */           .getMinimumEdition() + "!");
/*      */     }
/*      */     
/*  122 */     if (edition.getNumber() > javaEditionDefaults.getMaximumEdition().getNumber()) {
/*  123 */       throw new IllegalArgumentException("Edition " + edition + " is greater than the maximum supported edition " + javaEditionDefaults
/*      */ 
/*      */ 
/*      */           
/*  127 */           .getMaximumEdition() + "!");
/*      */     }
/*      */     
/*  130 */     DescriptorProtos.FeatureSetDefaults.FeatureSetEditionDefault found = null;
/*  131 */     for (DescriptorProtos.FeatureSetDefaults.FeatureSetEditionDefault editionDefault : javaEditionDefaults.getDefaultsList()) {
/*  132 */       if (editionDefault.getEdition().getNumber() > edition.getNumber()) {
/*      */         break;
/*      */       }
/*  135 */       found = editionDefault;
/*      */     } 
/*  137 */     if (found == null) {
/*  138 */       throw new IllegalArgumentException("Edition " + edition + " does not have a valid default FeatureSet!");
/*      */     }
/*      */     
/*  141 */     return found.getFixedFeatures().toBuilder().mergeFrom(found.getOverridableFeatures()).build();
/*      */   }
/*      */   
/*      */   private static DescriptorProtos.FeatureSet internFeatures(DescriptorProtos.FeatureSet features) {
/*  145 */     DescriptorProtos.FeatureSet cached = FEATURE_CACHE.putIfAbsent(features, features);
/*  146 */     if (cached == null) {
/*  147 */       return features;
/*      */     }
/*  149 */     return cached;
/*      */   }
/*      */   public static final class FileDescriptor extends GenericDescriptor { private DescriptorProtos.FileDescriptorProto proto; private volatile DescriptorProtos.FileOptions options; private final Descriptors.Descriptor[] messageTypes; private final Descriptors.EnumDescriptor[] enumTypes;
/*      */     private final Descriptors.ServiceDescriptor[] services;
/*      */     private final Descriptors.FieldDescriptor[] extensions;
/*      */     private final FileDescriptor[] dependencies;
/*      */     private final FileDescriptor[] publicDependencies;
/*      */     private final Descriptors.FileDescriptorTables tables;
/*      */     private final boolean placeholder;
/*      */     private volatile boolean featuresResolved;
/*      */     
/*      */     public DescriptorProtos.FileDescriptorProto toProto() {
/*  161 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/*  167 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FileDescriptor getFile() {
/*  173 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/*  178 */       return null;
/*      */     }
/*      */     
/*      */     public boolean isPlaceholder() {
/*  182 */       return this.placeholder;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/*  188 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getPackage() {
/*  196 */       return this.proto.getPackage();
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.FileOptions getOptions() {
/*  201 */       if (this.options == null) {
/*  202 */         DescriptorProtos.FileOptions strippedOptions = this.proto.getOptions();
/*  203 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/*  207 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/*  209 */         synchronized (this) {
/*  210 */           if (this.options == null) {
/*  211 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/*  215 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.Descriptor> getMessageTypes() {
/*  220 */       return Collections.unmodifiableList(Arrays.asList(this.messageTypes));
/*      */     }
/*      */     
/*      */     public int getMessageTypeCount() {
/*  224 */       return this.messageTypes.length;
/*      */     }
/*      */     
/*      */     public Descriptors.Descriptor getMessageType(int index) {
/*  228 */       return this.messageTypes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.EnumDescriptor> getEnumTypes() {
/*  233 */       return Collections.unmodifiableList(Arrays.asList(this.enumTypes));
/*      */     }
/*      */     
/*      */     public int getEnumTypeCount() {
/*  237 */       return this.enumTypes.length;
/*      */     }
/*      */     
/*      */     public Descriptors.EnumDescriptor getEnumType(int index) {
/*  241 */       return this.enumTypes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.ServiceDescriptor> getServices() {
/*  246 */       return Collections.unmodifiableList(Arrays.asList(this.services));
/*      */     }
/*      */     
/*      */     public int getServiceCount() {
/*  250 */       return this.services.length;
/*      */     }
/*      */     
/*      */     public Descriptors.ServiceDescriptor getService(int index) {
/*  254 */       return this.services[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.FieldDescriptor> getExtensions() {
/*  259 */       return Collections.unmodifiableList(Arrays.asList(this.extensions));
/*      */     }
/*      */     
/*      */     public int getExtensionCount() {
/*  263 */       return this.extensions.length;
/*      */     }
/*      */     
/*      */     public Descriptors.FieldDescriptor getExtension(int index) {
/*  267 */       return this.extensions[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<FileDescriptor> getDependencies() {
/*  272 */       return Collections.unmodifiableList(Arrays.asList(this.dependencies));
/*      */     }
/*      */ 
/*      */     
/*      */     public List<FileDescriptor> getPublicDependencies() {
/*  277 */       return Collections.unmodifiableList(Arrays.asList(this.publicDependencies));
/*      */     }
/*      */ 
/*      */     
/*      */     DescriptorProtos.Edition getEdition() {
/*  282 */       switch (this.proto.getSyntax()) {
/*      */         case "editions":
/*  284 */           return this.proto.getEdition();
/*      */         case "proto3":
/*  286 */           return DescriptorProtos.Edition.EDITION_PROTO3;
/*      */       } 
/*  288 */       return DescriptorProtos.Edition.EDITION_PROTO2;
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyHeadingTo(DescriptorProtos.FileDescriptorProto.Builder protoBuilder) {
/*  293 */       protoBuilder.setName(getName()).setSyntax(this.proto.getSyntax());
/*  294 */       if (!getPackage().isEmpty()) {
/*  295 */         protoBuilder.setPackage(getPackage());
/*      */       }
/*  297 */       if (this.proto.getSyntax().equals("editions")) {
/*  298 */         protoBuilder.setEdition(this.proto.getEdition());
/*      */       }
/*  300 */       if (this.proto.hasOptions() && !this.proto.getOptions().equals(DescriptorProtos.FileOptions.getDefaultInstance())) {
/*  301 */         protoBuilder.setOptions(this.proto.getOptions());
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor findMessageTypeByName(String name) {
/*  314 */       if (name.indexOf('.') != -1) {
/*  315 */         return null;
/*      */       }
/*  317 */       String packageName = getPackage();
/*  318 */       if (!packageName.isEmpty()) {
/*  319 */         name = packageName + '.' + name;
/*      */       }
/*  321 */       Descriptors.GenericDescriptor result = this.tables.findSymbol(name);
/*  322 */       if (result instanceof Descriptors.Descriptor && result.getFile() == this) {
/*  323 */         return (Descriptors.Descriptor)result;
/*      */       }
/*  325 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumDescriptor findEnumTypeByName(String name) {
/*  338 */       if (name.indexOf('.') != -1) {
/*  339 */         return null;
/*      */       }
/*  341 */       String packageName = getPackage();
/*  342 */       if (!packageName.isEmpty()) {
/*  343 */         name = packageName + '.' + name;
/*      */       }
/*  345 */       Descriptors.GenericDescriptor result = this.tables.findSymbol(name);
/*  346 */       if (result instanceof Descriptors.EnumDescriptor && result.getFile() == this) {
/*  347 */         return (Descriptors.EnumDescriptor)result;
/*      */       }
/*  349 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.ServiceDescriptor findServiceByName(String name) {
/*  362 */       if (name.indexOf('.') != -1) {
/*  363 */         return null;
/*      */       }
/*  365 */       String packageName = getPackage();
/*  366 */       if (!packageName.isEmpty()) {
/*  367 */         name = packageName + '.' + name;
/*      */       }
/*  369 */       Descriptors.GenericDescriptor result = this.tables.findSymbol(name);
/*  370 */       if (result instanceof Descriptors.ServiceDescriptor && result.getFile() == this) {
/*  371 */         return (Descriptors.ServiceDescriptor)result;
/*      */       }
/*  373 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor findExtensionByName(String name) {
/*  384 */       if (name.indexOf('.') != -1) {
/*  385 */         return null;
/*      */       }
/*  387 */       String packageName = getPackage();
/*  388 */       if (!packageName.isEmpty()) {
/*  389 */         name = packageName + '.' + name;
/*      */       }
/*  391 */       Descriptors.GenericDescriptor result = this.tables.findSymbol(name);
/*  392 */       if (result instanceof Descriptors.FieldDescriptor && result.getFile() == this) {
/*  393 */         return (Descriptors.FieldDescriptor)result;
/*      */       }
/*  395 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static FileDescriptor buildFrom(DescriptorProtos.FileDescriptorProto proto, FileDescriptor[] dependencies) throws Descriptors.DescriptorValidationException {
/*  410 */       return buildFrom(proto, dependencies, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static FileDescriptor buildFrom(DescriptorProtos.FileDescriptorProto proto, FileDescriptor[] dependencies, boolean allowUnknownDependencies) throws Descriptors.DescriptorValidationException {
/*  428 */       return buildFrom(proto, dependencies, allowUnknownDependencies, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static FileDescriptor buildFrom(DescriptorProtos.FileDescriptorProto proto, FileDescriptor[] dependencies, boolean allowUnknownDependencies, boolean allowUnresolvedFeatures) throws Descriptors.DescriptorValidationException {
/*  446 */       Descriptors.FileDescriptorTables tables = new Descriptors.FileDescriptorTables(dependencies, allowUnknownDependencies);
/*      */       
/*  448 */       FileDescriptor result = new FileDescriptor(proto, dependencies, tables, allowUnknownDependencies);
/*      */       
/*  450 */       result.crossLink();
/*      */       
/*  452 */       if (!allowUnresolvedFeatures)
/*      */       {
/*      */         
/*  455 */         result.resolveAllFeaturesInternal();
/*      */       }
/*  457 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static byte[] latin1Cat(String[] strings) {
/*  470 */       if (strings.length == 1) {
/*  471 */         return strings[0].getBytes(Internal.ISO_8859_1);
/*      */       }
/*  473 */       StringBuilder descriptorData = new StringBuilder();
/*  474 */       for (String part : strings) {
/*  475 */         descriptorData.append(part);
/*      */       }
/*  477 */       return descriptorData.toString().getBytes(Internal.ISO_8859_1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static FileDescriptor[] findDescriptors(Class<?> descriptorOuterClass, String[] dependencyClassNames, String[] dependencyFileNames) {
/*  484 */       List<FileDescriptor> descriptors = new ArrayList<>();
/*  485 */       for (int i = 0; i < dependencyClassNames.length; i++) {
/*      */         try {
/*  487 */           Class<?> clazz = descriptorOuterClass.getClassLoader().loadClass(dependencyClassNames[i]);
/*  488 */           descriptors.add((FileDescriptor)clazz.getField("descriptor").get(null));
/*  489 */         } catch (Exception e) {
/*      */ 
/*      */           
/*  492 */           Descriptors.logger.warning("Descriptors for \"" + dependencyFileNames[i] + "\" can not be found.");
/*      */         } 
/*      */       } 
/*  495 */       return descriptors.<FileDescriptor>toArray(new FileDescriptor[0]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public static void internalBuildGeneratedFileFrom(String[] descriptorDataParts, FileDescriptor[] dependencies, InternalDescriptorAssigner descriptorAssigner) {
/*      */       DescriptorProtos.FileDescriptorProto proto;
/*      */       FileDescriptor result;
/*  510 */       byte[] descriptorBytes = latin1Cat(descriptorDataParts);
/*      */ 
/*      */       
/*      */       try {
/*  514 */         proto = DescriptorProtos.FileDescriptorProto.parseFrom(descriptorBytes);
/*  515 */       } catch (InvalidProtocolBufferException e) {
/*  516 */         throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  524 */         result = buildFrom(proto, dependencies, true);
/*  525 */       } catch (DescriptorValidationException e) {
/*  526 */         throw new IllegalArgumentException("Invalid embedded descriptor for \"" + proto
/*  527 */             .getName() + "\".", e);
/*      */       } 
/*      */       
/*  530 */       ExtensionRegistry registry = descriptorAssigner.assignDescriptors(result);
/*      */       
/*  532 */       if (registry != null) {
/*  533 */         throw new RuntimeException("assignDescriptors must return null");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static FileDescriptor internalBuildGeneratedFileFrom(String[] descriptorDataParts, FileDescriptor[] dependencies) {
/*      */       DescriptorProtos.FileDescriptorProto proto;
/*  543 */       byte[] descriptorBytes = latin1Cat(descriptorDataParts);
/*      */ 
/*      */       
/*      */       try {
/*  547 */         proto = DescriptorProtos.FileDescriptorProto.parseFrom(descriptorBytes);
/*  548 */       } catch (InvalidProtocolBufferException e) {
/*  549 */         throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  556 */         return buildFrom(proto, dependencies, true, true);
/*  557 */       } catch (DescriptorValidationException e) {
/*  558 */         throw new IllegalArgumentException("Invalid embedded descriptor for \"" + proto
/*  559 */             .getName() + "\".", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static FileDescriptor internalBuildGeneratedFileFrom(String[] descriptorDataParts, Class<?> descriptorOuterClass, String[] dependencyClassNames, String[] dependencyFileNames) {
/*  573 */       FileDescriptor[] dependencies = findDescriptors(descriptorOuterClass, dependencyClassNames, dependencyFileNames);
/*  574 */       return internalBuildGeneratedFileFrom(descriptorDataParts, dependencies);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static void internalUpdateFileDescriptor(FileDescriptor descriptor, ExtensionRegistry registry) {
/*  584 */       ByteString bytes = descriptor.proto.toByteString();
/*      */       try {
/*  586 */         DescriptorProtos.FileDescriptorProto proto = DescriptorProtos.FileDescriptorProto.parseFrom(bytes, registry);
/*  587 */         descriptor.setProto(proto);
/*  588 */       } catch (InvalidProtocolBufferException e) {
/*  589 */         throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FileDescriptor(DescriptorProtos.FileDescriptorProto proto, FileDescriptor[] dependencies, Descriptors.FileDescriptorTables tables, boolean allowUnknownDependencies) throws Descriptors.DescriptorValidationException {
/*  630 */       this.tables = tables;
/*  631 */       this.proto = proto;
/*  632 */       this.dependencies = (FileDescriptor[])dependencies.clone();
/*  633 */       this.featuresResolved = false;
/*  634 */       HashMap<String, FileDescriptor> nameToFileMap = new HashMap<>();
/*  635 */       for (FileDescriptor file : dependencies) {
/*  636 */         nameToFileMap.put(file.getName(), file);
/*      */       }
/*  638 */       List<FileDescriptor> publicDependencies = new ArrayList<>(); int i;
/*  639 */       for (i = 0; i < proto.getPublicDependencyCount(); i++) {
/*  640 */         int index = proto.getPublicDependency(i);
/*  641 */         if (index < 0 || index >= proto.getDependencyCount()) {
/*  642 */           throw new Descriptors.DescriptorValidationException(this, "Invalid public dependency index.");
/*      */         }
/*  644 */         String name = proto.getDependency(index);
/*  645 */         FileDescriptor file = nameToFileMap.get(name);
/*  646 */         if (file == null) {
/*  647 */           if (!allowUnknownDependencies) {
/*  648 */             throw new Descriptors.DescriptorValidationException(this, "Invalid public dependency: " + name);
/*      */           }
/*      */         } else {
/*      */           
/*  652 */           publicDependencies.add(file);
/*      */         } 
/*      */       } 
/*  655 */       this.publicDependencies = new FileDescriptor[publicDependencies.size()];
/*  656 */       publicDependencies.toArray(this.publicDependencies);
/*      */       
/*  658 */       this.placeholder = false;
/*      */       
/*  660 */       tables.addPackage(getPackage(), this);
/*      */       
/*  662 */       this
/*      */ 
/*      */         
/*  665 */         .messageTypes = (proto.getMessageTypeCount() > 0) ? new Descriptors.Descriptor[proto.getMessageTypeCount()] : Descriptors.EMPTY_DESCRIPTORS;
/*  666 */       for (i = 0; i < proto.getMessageTypeCount(); i++) {
/*  667 */         this.messageTypes[i] = new Descriptors.Descriptor(proto.getMessageType(i), this, null, i);
/*      */       }
/*      */       
/*  670 */       this
/*      */ 
/*      */         
/*  673 */         .enumTypes = (proto.getEnumTypeCount() > 0) ? new Descriptors.EnumDescriptor[proto.getEnumTypeCount()] : Descriptors.EMPTY_ENUM_DESCRIPTORS;
/*  674 */       for (i = 0; i < proto.getEnumTypeCount(); i++) {
/*  675 */         this.enumTypes[i] = new Descriptors.EnumDescriptor(proto.getEnumType(i), this, null, i);
/*      */       }
/*      */       
/*  678 */       this
/*      */ 
/*      */         
/*  681 */         .services = (proto.getServiceCount() > 0) ? new Descriptors.ServiceDescriptor[proto.getServiceCount()] : Descriptors.EMPTY_SERVICE_DESCRIPTORS;
/*  682 */       for (i = 0; i < proto.getServiceCount(); i++) {
/*  683 */         this.services[i] = new Descriptors.ServiceDescriptor(proto.getService(i), this, i);
/*      */       }
/*      */       
/*  686 */       this
/*      */ 
/*      */         
/*  689 */         .extensions = (proto.getExtensionCount() > 0) ? new Descriptors.FieldDescriptor[proto.getExtensionCount()] : Descriptors.EMPTY_FIELD_DESCRIPTORS;
/*  690 */       for (i = 0; i < proto.getExtensionCount(); i++) {
/*  691 */         this.extensions[i] = new Descriptors.FieldDescriptor(proto.getExtension(i), this, null, i, true);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     FileDescriptor(String packageName, Descriptors.Descriptor message) throws Descriptors.DescriptorValidationException {
/*  697 */       this.tables = new Descriptors.FileDescriptorTables(new FileDescriptor[0], true);
/*  698 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  703 */         .proto = DescriptorProtos.FileDescriptorProto.newBuilder().setName(message.getFullName() + ".placeholder.proto").setPackage(packageName).addMessageType(message.toProto()).build();
/*  704 */       this.dependencies = new FileDescriptor[0];
/*  705 */       this.publicDependencies = new FileDescriptor[0];
/*  706 */       this.featuresResolved = false;
/*      */       
/*  708 */       this.messageTypes = new Descriptors.Descriptor[] { message };
/*  709 */       this.enumTypes = Descriptors.EMPTY_ENUM_DESCRIPTORS;
/*  710 */       this.services = Descriptors.EMPTY_SERVICE_DESCRIPTORS;
/*  711 */       this.extensions = Descriptors.EMPTY_FIELD_DESCRIPTORS;
/*      */       
/*  713 */       this.placeholder = true;
/*      */       
/*  715 */       this.tables.addPackage(packageName, this);
/*  716 */       this.tables.addSymbol(message);
/*      */     }
/*      */     
/*      */     public void resolveAllFeaturesImmutable() {
/*      */       try {
/*  721 */         resolveAllFeaturesInternal();
/*  722 */       } catch (DescriptorValidationException e) {
/*  723 */         throw new IllegalArgumentException("Invalid features for \"" + this.proto.getName() + "\".", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void resolveAllFeaturesInternal() throws Descriptors.DescriptorValidationException {
/*  732 */       if (this.featuresResolved) {
/*      */         return;
/*      */       }
/*      */       
/*  736 */       synchronized (this) {
/*  737 */         if (this.featuresResolved) {
/*      */           return;
/*      */         }
/*  740 */         resolveFeatures(this.proto.getOptions().getFeatures());
/*      */         
/*  742 */         for (Descriptors.Descriptor messageType : this.messageTypes) {
/*  743 */           messageType.resolveAllFeatures();
/*      */         }
/*      */         
/*  746 */         for (Descriptors.EnumDescriptor enumType : this.enumTypes) {
/*  747 */           enumType.resolveAllFeatures();
/*      */         }
/*      */         
/*  750 */         for (Descriptors.ServiceDescriptor service : this.services) {
/*  751 */           service.resolveAllFeatures();
/*      */         }
/*      */         
/*  754 */         for (Descriptors.FieldDescriptor extension : this.extensions) {
/*  755 */           extension.resolveAllFeatures();
/*      */         }
/*  757 */         this.featuresResolved = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     DescriptorProtos.FeatureSet inferLegacyProtoFeatures() {
/*  763 */       if (getEdition().getNumber() >= DescriptorProtos.Edition.EDITION_2023.getNumber()) {
/*  764 */         return DescriptorProtos.FeatureSet.getDefaultInstance();
/*      */       }
/*      */       
/*  767 */       DescriptorProtos.FeatureSet.Builder features = null;
/*  768 */       if (getEdition() == DescriptorProtos.Edition.EDITION_PROTO2 && 
/*  769 */         this.proto.getOptions().getJavaStringCheckUtf8()) {
/*  770 */         features = DescriptorProtos.FeatureSet.newBuilder();
/*  771 */         features.setExtension(JavaFeaturesProto.java_, 
/*      */             
/*  773 */             JavaFeaturesProto.JavaFeatures.newBuilder()
/*  774 */             .setUtf8Validation(JavaFeaturesProto.JavaFeatures.Utf8Validation.VERIFY)
/*  775 */             .build());
/*      */       } 
/*      */ 
/*      */       
/*  779 */       return (features != null) ? features.build() : DescriptorProtos.FeatureSet.getDefaultInstance();
/*      */     }
/*      */ 
/*      */     
/*      */     private void crossLink() throws Descriptors.DescriptorValidationException {
/*  784 */       for (Descriptors.Descriptor messageType : this.messageTypes) {
/*  785 */         messageType.crossLink();
/*      */       }
/*      */       
/*  788 */       for (Descriptors.ServiceDescriptor service : this.services) {
/*  789 */         service.crossLink();
/*      */       }
/*      */       
/*  792 */       for (Descriptors.FieldDescriptor extension : this.extensions) {
/*  793 */         extension.crossLink();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private synchronized void setProto(DescriptorProtos.FileDescriptorProto proto) {
/*  806 */       this.proto = proto;
/*  807 */       this.options = null;
/*      */       try {
/*  809 */         resolveFeatures(proto.getOptions().getFeatures());
/*      */         int i;
/*  811 */         for (i = 0; i < this.messageTypes.length; i++) {
/*  812 */           this.messageTypes[i].setProto(proto.getMessageType(i));
/*      */         }
/*      */         
/*  815 */         for (i = 0; i < this.enumTypes.length; i++) {
/*  816 */           this.enumTypes[i].setProto(proto.getEnumType(i));
/*      */         }
/*      */         
/*  819 */         for (i = 0; i < this.services.length; i++) {
/*  820 */           this.services[i].setProto(proto.getService(i));
/*      */         }
/*      */         
/*  823 */         for (i = 0; i < this.extensions.length; i++) {
/*  824 */           this.extensions[i].setProto(proto.getExtension(i));
/*      */         }
/*  826 */       } catch (DescriptorValidationException e) {
/*  827 */         throw new IllegalArgumentException("Invalid features for \"" + proto.getName() + "\".", e);
/*      */       } 
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public static interface InternalDescriptorAssigner {
/*      */       ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor param2FileDescriptor); } }
/*      */   
/*      */   public static final class Descriptor extends GenericDescriptor {
/*      */     private final int index;
/*      */     private DescriptorProtos.DescriptorProto proto;
/*      */     private volatile DescriptorProtos.MessageOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.GenericDescriptor parent;
/*      */     private final Descriptor[] nestedTypes;
/*      */     private final Descriptors.EnumDescriptor[] enumTypes;
/*      */     private final Descriptors.FieldDescriptor[] fields;
/*      */     private final Descriptors.FieldDescriptor[] fieldsSortedByNumber;
/*      */     private final Descriptors.FieldDescriptor[] extensions;
/*      */     private final Descriptors.OneofDescriptor[] oneofs;
/*      */     private final int realOneofCount;
/*      */     private final int[] extensionRangeLowerBounds;
/*      */     private final int[] extensionRangeUpperBounds;
/*      */     private final boolean placeholder;
/*      */     
/*      */     public int getIndex() {
/*  853 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.DescriptorProto toProto() {
/*  859 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/*  865 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/*  882 */       return this.fullName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/*  888 */       return this.parent.getFile();
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/*  893 */       return this.parent;
/*      */     }
/*      */     
/*      */     public boolean isPlaceholder() {
/*  897 */       return this.placeholder;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptor getContainingType() {
/*  902 */       if (this.parent instanceof Descriptor) {
/*  903 */         return (Descriptor)this.parent;
/*      */       }
/*  905 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.MessageOptions getOptions() {
/*  910 */       if (this.options == null) {
/*  911 */         DescriptorProtos.MessageOptions strippedOptions = this.proto.getOptions();
/*  912 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/*  916 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/*  918 */         synchronized (this) {
/*  919 */           if (this.options == null) {
/*  920 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/*  924 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.FieldDescriptor> getFields() {
/*  929 */       return Collections.unmodifiableList(Arrays.asList(this.fields));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getFieldCount() {
/*  934 */       return this.fields.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getField(int index) {
/*  939 */       return this.fields[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.OneofDescriptor> getOneofs() {
/*  944 */       return Collections.unmodifiableList(Arrays.asList(this.oneofs));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getOneofCount() {
/*  949 */       return this.oneofs.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.OneofDescriptor getOneof(int index) {
/*  954 */       return this.oneofs[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.OneofDescriptor> getRealOneofs() {
/*  959 */       return Collections.unmodifiableList(Arrays.<Descriptors.OneofDescriptor>asList(this.oneofs).subList(0, this.realOneofCount));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRealOneofCount() {
/*  964 */       return this.realOneofCount;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.OneofDescriptor getRealOneof(int index) {
/*  969 */       if (index >= this.realOneofCount) {
/*  970 */         throw new ArrayIndexOutOfBoundsException(index);
/*      */       }
/*  972 */       return this.oneofs[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.FieldDescriptor> getExtensions() {
/*  977 */       return Collections.unmodifiableList(Arrays.asList(this.extensions));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getExtensionCount() {
/*  982 */       return this.extensions.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getExtension(int index) {
/*  987 */       return this.extensions[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptor> getNestedTypes() {
/*  992 */       return Collections.unmodifiableList(Arrays.asList(this.nestedTypes));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getNestedTypeCount() {
/*  997 */       return this.nestedTypes.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptor getNestedType(int index) {
/* 1002 */       return this.nestedTypes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.EnumDescriptor> getEnumTypes() {
/* 1007 */       return Collections.unmodifiableList(Arrays.asList(this.enumTypes));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEnumTypeCount() {
/* 1012 */       return this.enumTypes.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.EnumDescriptor getEnumType(int index) {
/* 1017 */       return this.enumTypes[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isExtensionNumber(int number) {
/* 1022 */       int index = Arrays.binarySearch(this.extensionRangeLowerBounds, number);
/* 1023 */       if (index < 0) {
/* 1024 */         index = (index ^ 0xFFFFFFFF) - 1;
/*      */       }
/*      */       
/* 1027 */       return (index >= 0 && number < this.extensionRangeUpperBounds[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isReservedNumber(int number) {
/* 1032 */       for (DescriptorProtos.DescriptorProto.ReservedRange range : this.proto.getReservedRangeList()) {
/* 1033 */         if (range.getStart() <= number && number < range.getEnd()) {
/* 1034 */           return true;
/*      */         }
/*      */       } 
/* 1037 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isReservedName(String name) {
/* 1042 */       Internal.checkNotNull(name);
/* 1043 */       for (String reservedName : this.proto.getReservedNameList()) {
/* 1044 */         if (reservedName.equals(name)) {
/* 1045 */           return true;
/*      */         }
/*      */       } 
/* 1048 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isExtendable() {
/* 1056 */       return !this.proto.getExtensionRangeList().isEmpty();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor findFieldByName(String name) {
/* 1071 */       Descriptors.GenericDescriptor result = (getFile()).tables.findSymbol(this.fullName + '.' + name);
/* 1072 */       if (result instanceof Descriptors.FieldDescriptor) {
/* 1073 */         return (Descriptors.FieldDescriptor)result;
/*      */       }
/* 1075 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor findFieldByNumber(int number) {
/* 1086 */       return (Descriptors.FieldDescriptor)Descriptors.binarySearch((T[])this.fieldsSortedByNumber, this.fieldsSortedByNumber.length, 
/* 1087 */           (ToIntFunction)Descriptors.FieldDescriptor.NUMBER_GETTER, number);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptor findNestedTypeByName(String name) {
/* 1097 */       Descriptors.GenericDescriptor result = (getFile()).tables.findSymbol(this.fullName + '.' + name);
/* 1098 */       if (result instanceof Descriptor) {
/* 1099 */         return (Descriptor)result;
/*      */       }
/* 1101 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumDescriptor findEnumTypeByName(String name) {
/* 1112 */       Descriptors.GenericDescriptor result = (getFile()).tables.findSymbol(this.fullName + '.' + name);
/* 1113 */       if (result instanceof Descriptors.EnumDescriptor) {
/* 1114 */         return (Descriptors.EnumDescriptor)result;
/*      */       }
/* 1116 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Descriptor(String fullname) throws Descriptors.DescriptorValidationException {
/* 1140 */       String name = fullname;
/* 1141 */       String packageName = "";
/* 1142 */       int pos = fullname.lastIndexOf('.');
/* 1143 */       if (pos != -1) {
/* 1144 */         name = fullname.substring(pos + 1);
/* 1145 */         packageName = fullname.substring(0, pos);
/*      */       } 
/* 1147 */       this.index = 0;
/* 1148 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1153 */         .proto = DescriptorProtos.DescriptorProto.newBuilder().setName(name).addExtensionRange(DescriptorProtos.DescriptorProto.ExtensionRange.newBuilder().setStart(1).setEnd(536870912).build()).build();
/* 1154 */       this.fullName = fullname;
/*      */       
/* 1156 */       this.nestedTypes = Descriptors.EMPTY_DESCRIPTORS;
/* 1157 */       this.enumTypes = Descriptors.EMPTY_ENUM_DESCRIPTORS;
/* 1158 */       this.fields = Descriptors.EMPTY_FIELD_DESCRIPTORS;
/* 1159 */       this.fieldsSortedByNumber = Descriptors.EMPTY_FIELD_DESCRIPTORS;
/* 1160 */       this.extensions = Descriptors.EMPTY_FIELD_DESCRIPTORS;
/* 1161 */       this.oneofs = Descriptors.EMPTY_ONEOF_DESCRIPTORS;
/* 1162 */       this.realOneofCount = 0;
/*      */ 
/*      */       
/* 1165 */       this.parent = new Descriptors.FileDescriptor(packageName, this);
/*      */       
/* 1167 */       this.extensionRangeLowerBounds = new int[] { 1 };
/* 1168 */       this.extensionRangeUpperBounds = new int[] { 536870912 };
/*      */       
/* 1170 */       this.placeholder = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Descriptor(DescriptorProtos.DescriptorProto proto, Descriptors.FileDescriptor file, Descriptor parent, int index) throws Descriptors.DescriptorValidationException {
/* 1179 */       if (parent == null) {
/* 1180 */         this.parent = file;
/*      */       } else {
/* 1182 */         this.parent = parent;
/*      */       } 
/* 1184 */       this.index = index;
/* 1185 */       this.proto = proto;
/* 1186 */       this.fullName = Descriptors.computeFullName(file, parent, proto.getName());
/*      */       
/* 1188 */       this
/*      */ 
/*      */         
/* 1191 */         .oneofs = (proto.getOneofDeclCount() > 0) ? new Descriptors.OneofDescriptor[proto.getOneofDeclCount()] : Descriptors.EMPTY_ONEOF_DESCRIPTORS; int i;
/* 1192 */       for (i = 0; i < proto.getOneofDeclCount(); i++) {
/* 1193 */         this.oneofs[i] = new Descriptors.OneofDescriptor(proto.getOneofDecl(i), this, i);
/*      */       }
/*      */       
/* 1196 */       this
/*      */ 
/*      */         
/* 1199 */         .nestedTypes = (proto.getNestedTypeCount() > 0) ? new Descriptor[proto.getNestedTypeCount()] : Descriptors.EMPTY_DESCRIPTORS;
/* 1200 */       for (i = 0; i < proto.getNestedTypeCount(); i++) {
/* 1201 */         this.nestedTypes[i] = new Descriptor(proto.getNestedType(i), file, this, i);
/*      */       }
/*      */       
/* 1204 */       this
/*      */ 
/*      */         
/* 1207 */         .enumTypes = (proto.getEnumTypeCount() > 0) ? new Descriptors.EnumDescriptor[proto.getEnumTypeCount()] : Descriptors.EMPTY_ENUM_DESCRIPTORS;
/* 1208 */       for (i = 0; i < proto.getEnumTypeCount(); i++) {
/* 1209 */         this.enumTypes[i] = new Descriptors.EnumDescriptor(proto.getEnumType(i), file, this, i);
/*      */       }
/*      */       
/* 1212 */       this
/*      */ 
/*      */         
/* 1215 */         .fields = (proto.getFieldCount() > 0) ? new Descriptors.FieldDescriptor[proto.getFieldCount()] : Descriptors.EMPTY_FIELD_DESCRIPTORS;
/* 1216 */       for (i = 0; i < proto.getFieldCount(); i++) {
/* 1217 */         this.fields[i] = new Descriptors.FieldDescriptor(proto.getField(i), file, this, i, false);
/*      */       }
/* 1219 */       this
/* 1220 */         .fieldsSortedByNumber = (proto.getFieldCount() > 0) ? (Descriptors.FieldDescriptor[])this.fields.clone() : Descriptors.EMPTY_FIELD_DESCRIPTORS;
/*      */       
/* 1222 */       this
/*      */ 
/*      */         
/* 1225 */         .extensions = (proto.getExtensionCount() > 0) ? new Descriptors.FieldDescriptor[proto.getExtensionCount()] : Descriptors.EMPTY_FIELD_DESCRIPTORS;
/* 1226 */       for (i = 0; i < proto.getExtensionCount(); i++) {
/* 1227 */         this.extensions[i] = new Descriptors.FieldDescriptor(proto.getExtension(i), file, this, i, true);
/*      */       }
/*      */       
/* 1230 */       for (i = 0; i < proto.getOneofDeclCount(); i++) {
/* 1231 */         (this.oneofs[i]).fields = new Descriptors.FieldDescriptor[this.oneofs[i].getFieldCount()];
/* 1232 */         (this.oneofs[i]).fieldCount = 0;
/*      */       } 
/* 1234 */       for (i = 0; i < proto.getFieldCount(); i++) {
/* 1235 */         Descriptors.OneofDescriptor oneofDescriptor = this.fields[i].getContainingOneof();
/* 1236 */         if (oneofDescriptor != null) {
/* 1237 */           oneofDescriptor.fields[oneofDescriptor.fieldCount++] = this.fields[i];
/*      */         }
/*      */       } 
/*      */       
/* 1241 */       int syntheticOneofCount = 0;
/* 1242 */       for (Descriptors.OneofDescriptor oneof : this.oneofs) {
/* 1243 */         if (oneof.isSynthetic()) {
/* 1244 */           syntheticOneofCount++;
/*      */         }
/* 1246 */         else if (syntheticOneofCount > 0) {
/* 1247 */           throw new Descriptors.DescriptorValidationException(this, "Synthetic oneofs must come last.");
/*      */         } 
/*      */       } 
/*      */       
/* 1251 */       this.realOneofCount = this.oneofs.length - syntheticOneofCount;
/*      */       
/* 1253 */       this.placeholder = false;
/*      */       
/* 1255 */       file.tables.addSymbol(this);
/*      */ 
/*      */       
/* 1258 */       if (proto.getExtensionRangeCount() > 0) {
/* 1259 */         this.extensionRangeLowerBounds = new int[proto.getExtensionRangeCount()];
/* 1260 */         this.extensionRangeUpperBounds = new int[proto.getExtensionRangeCount()];
/* 1261 */         int j = 0;
/* 1262 */         for (DescriptorProtos.DescriptorProto.ExtensionRange range : proto.getExtensionRangeList()) {
/* 1263 */           this.extensionRangeLowerBounds[j] = range.getStart();
/* 1264 */           this.extensionRangeUpperBounds[j] = range.getEnd();
/* 1265 */           j++;
/*      */         } 
/*      */ 
/*      */         
/* 1269 */         Arrays.sort(this.extensionRangeLowerBounds);
/* 1270 */         Arrays.sort(this.extensionRangeUpperBounds);
/*      */       } else {
/* 1272 */         this.extensionRangeLowerBounds = Descriptors.EMPTY_INT_ARRAY;
/* 1273 */         this.extensionRangeUpperBounds = Descriptors.EMPTY_INT_ARRAY;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 1279 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */       
/* 1281 */       for (Descriptor nestedType : this.nestedTypes) {
/* 1282 */         nestedType.resolveAllFeatures();
/*      */       }
/*      */       
/* 1285 */       for (Descriptors.EnumDescriptor enumType : this.enumTypes) {
/* 1286 */         enumType.resolveAllFeatures();
/*      */       }
/*      */ 
/*      */       
/* 1290 */       for (Descriptors.OneofDescriptor oneof : this.oneofs) {
/* 1291 */         oneof.resolveAllFeatures();
/*      */       }
/*      */       
/* 1294 */       for (Descriptors.FieldDescriptor field : this.fields) {
/* 1295 */         field.resolveAllFeatures();
/*      */       }
/*      */       
/* 1298 */       for (Descriptors.FieldDescriptor extension : this.extensions) {
/* 1299 */         extension.resolveAllFeatures();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void crossLink() throws Descriptors.DescriptorValidationException {
/* 1305 */       for (Descriptor nestedType : this.nestedTypes) {
/* 1306 */         nestedType.crossLink();
/*      */       }
/*      */       
/* 1309 */       for (Descriptors.FieldDescriptor field : this.fields) {
/* 1310 */         field.crossLink();
/*      */       }
/* 1312 */       Arrays.sort((Object[])this.fieldsSortedByNumber);
/* 1313 */       validateNoDuplicateFieldNumbers();
/*      */       
/* 1315 */       for (Descriptors.FieldDescriptor extension : this.extensions) {
/* 1316 */         extension.crossLink();
/*      */       }
/*      */     }
/*      */     
/*      */     private void validateNoDuplicateFieldNumbers() throws Descriptors.DescriptorValidationException {
/* 1321 */       for (int i = 0; i + 1 < this.fieldsSortedByNumber.length; i++) {
/* 1322 */         Descriptors.FieldDescriptor old = this.fieldsSortedByNumber[i];
/* 1323 */         Descriptors.FieldDescriptor field = this.fieldsSortedByNumber[i + 1];
/* 1324 */         if (old.getNumber() == field.getNumber()) {
/* 1325 */           throw new Descriptors.DescriptorValidationException(field, "Field number " + field
/*      */ 
/*      */               
/* 1328 */               .getNumber() + " has already been used in \"" + field
/*      */               
/* 1330 */               .getContainingType().getFullName() + "\" by field \"" + old
/*      */               
/* 1332 */               .getName() + "\".");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.DescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 1340 */       this.proto = proto;
/* 1341 */       this.options = null;
/* 1342 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */       int i;
/* 1344 */       for (i = 0; i < this.nestedTypes.length; i++) {
/* 1345 */         this.nestedTypes[i].setProto(proto.getNestedType(i));
/*      */       }
/*      */       
/* 1348 */       for (i = 0; i < this.oneofs.length; i++) {
/* 1349 */         this.oneofs[i].setProto(proto.getOneofDecl(i));
/*      */       }
/*      */       
/* 1352 */       for (i = 0; i < this.enumTypes.length; i++) {
/* 1353 */         this.enumTypes[i].setProto(proto.getEnumType(i));
/*      */       }
/*      */       
/* 1356 */       for (i = 0; i < this.fields.length; i++) {
/* 1357 */         this.fields[i].setProto(proto.getField(i));
/*      */       }
/*      */       
/* 1360 */       for (i = 0; i < this.extensions.length; i++) {
/* 1361 */         this.extensions[i].setProto(proto.getExtension(i));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class FieldDescriptor
/*      */     extends GenericDescriptor
/*      */     implements Comparable<FieldDescriptor>, FieldSet.FieldDescriptorLite<FieldDescriptor>
/*      */   {
/* 1371 */     private static final ToIntFunction<FieldDescriptor> NUMBER_GETTER = FieldDescriptor::getNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndex() {
/* 1379 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.FieldDescriptorProto toProto() {
/* 1385 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 1391 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNumber() {
/* 1397 */       return this.proto.getNumber();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 1407 */       return this.fullName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getJsonName() {
/* 1412 */       String result = this.jsonName;
/* 1413 */       if (result != null)
/* 1414 */         return result; 
/* 1415 */       if (this.proto.hasJsonName()) {
/* 1416 */         return this.jsonName = this.proto.getJsonName();
/*      */       }
/* 1418 */       return this.jsonName = fieldNameToJsonName(this.proto.getName());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JavaType getJavaType() {
/* 1427 */       return getType().getJavaType();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public WireFormat.JavaType getLiteJavaType() {
/* 1433 */       return getLiteType().getJavaType();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 1439 */       return this.parent.getFile();
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 1444 */       return this.parent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getType() {
/* 1452 */       if (this.type == Type.MESSAGE && (this.typeDescriptor == null || 
/*      */         
/* 1454 */         !((Descriptors.Descriptor)this.typeDescriptor).toProto().getOptions().getMapEntry()) && (this.containingType == null || 
/* 1455 */         !this.containingType.toProto().getOptions().getMapEntry()) && this.features != null && 
/*      */         
/* 1457 */         getFeatures().getMessageEncoding() == DescriptorProtos.FeatureSet.MessageEncoding.DELIMITED) {
/* 1458 */         return Type.GROUP;
/*      */       }
/* 1460 */       return this.type;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public WireFormat.FieldType getLiteType() {
/* 1466 */       return table[getType().ordinal()];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean needsUtf8Check() {
/* 1471 */       if (getType() != Type.STRING) {
/* 1472 */         return false;
/*      */       }
/* 1474 */       if (getContainingType().toProto().getOptions().getMapEntry())
/*      */       {
/* 1476 */         return true;
/*      */       }
/* 1478 */       if (((JavaFeaturesProto.JavaFeatures)getFeatures()
/* 1479 */         .getExtension(JavaFeaturesProto.java_))
/* 1480 */         .getUtf8Validation()
/* 1481 */         .equals(JavaFeaturesProto.JavaFeatures.Utf8Validation.VERIFY)) {
/* 1482 */         return true;
/*      */       }
/* 1484 */       return getFeatures().getUtf8Validation().equals(DescriptorProtos.FeatureSet.Utf8Validation.VERIFY);
/*      */     }
/*      */     
/*      */     public boolean isMapField() {
/* 1488 */       return (getType() == Type.MESSAGE && 
/* 1489 */         isRepeated() && 
/* 1490 */         getMessageType().toProto().getOptions().getMapEntry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1496 */     private static final WireFormat.FieldType[] table = WireFormat.FieldType.values(); private final int index; private DescriptorProtos.FieldDescriptorProto proto; private volatile DescriptorProtos.FieldOptions options; private final String fullName; private String jsonName; private final Descriptors.GenericDescriptor parent;
/*      */     private final Descriptors.Descriptor extensionScope;
/*      */     
/*      */     public boolean isRequired() {
/* 1500 */       return (getFeatures().getFieldPresence() == DescriptorProtos.FeatureSet.FieldPresence.LEGACY_REQUIRED);
/*      */     }
/*      */     private final boolean isProto3Optional; private volatile RedactionState redactionState;
/*      */     private Type type;
/*      */     private Descriptors.Descriptor containingType;
/*      */     private Descriptors.OneofDescriptor containingOneof;
/*      */     private Descriptors.GenericDescriptor typeDescriptor;
/*      */     private Object defaultValue;
/*      */     
/*      */     @Deprecated
/*      */     public boolean isOptional() {
/* 1511 */       return (this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_OPTIONAL && 
/* 1512 */         getFeatures().getFieldPresence() != DescriptorProtos.FeatureSet.FieldPresence.LEGACY_REQUIRED);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isRepeated() {
/* 1519 */       return (this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isPacked() {
/* 1528 */       if (!isPackable()) {
/* 1529 */         return false;
/*      */       }
/* 1531 */       return getFeatures()
/* 1532 */         .getRepeatedFieldEncoding()
/* 1533 */         .equals(DescriptorProtos.FeatureSet.RepeatedFieldEncoding.PACKED);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isPackable() {
/* 1538 */       return (isRepeated() && getLiteType().isPackable());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasDefaultValue() {
/* 1543 */       return this.proto.hasDefaultValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getDefaultValue() {
/* 1552 */       if (getJavaType() == JavaType.MESSAGE) {
/* 1553 */         throw new UnsupportedOperationException("FieldDescriptor.getDefaultValue() called on an embedded message field.");
/*      */       }
/*      */       
/* 1556 */       return this.defaultValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.FieldOptions getOptions() {
/* 1561 */       if (this.options == null) {
/* 1562 */         DescriptorProtos.FieldOptions strippedOptions = this.proto.getOptions();
/* 1563 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 1567 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 1569 */         synchronized (this) {
/* 1570 */           if (this.options == null) {
/* 1571 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 1575 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isExtension() {
/* 1580 */       return this.proto.hasExtendee();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getContainingType() {
/* 1588 */       return this.containingType;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.OneofDescriptor getContainingOneof() {
/* 1593 */       return this.containingOneof;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.OneofDescriptor getRealContainingOneof() {
/* 1598 */       return (this.containingOneof != null && !this.containingOneof.isSynthetic()) ? this.containingOneof : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean hasOptionalKeyword() {
/* 1606 */       return (this.isProto3Optional || (
/* 1607 */         getFile().getEdition() == DescriptorProtos.Edition.EDITION_PROTO2 && 
/* 1608 */         !isRequired() && 
/* 1609 */         !isRepeated() && 
/* 1610 */         getContainingOneof() == null));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasPresence() {
/* 1624 */       if (isRepeated()) {
/* 1625 */         return false;
/*      */       }
/* 1627 */       return (this.isProto3Optional || 
/* 1628 */         getType() == Type.MESSAGE || 
/* 1629 */         getType() == Type.GROUP || 
/* 1630 */         isExtension() || 
/* 1631 */         getContainingOneof() != null || 
/* 1632 */         getFeatures().getFieldPresence() != DescriptorProtos.FeatureSet.FieldPresence.IMPLICIT);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isGroupLike() {
/* 1641 */       if (getType() != Type.GROUP)
/*      */       {
/* 1643 */         return false;
/*      */       }
/*      */       
/* 1646 */       if (!getMessageType().getName().toLowerCase().equals(getName()))
/*      */       {
/* 1648 */         return false;
/*      */       }
/*      */       
/* 1651 */       if (getMessageType().getFile() != getFile())
/*      */       {
/* 1653 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1659 */       return isExtension() ? (
/* 1660 */         (getMessageType().getContainingType() == getExtensionScope())) : (
/* 1661 */         (getMessageType().getContainingType() == getContainingType()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getExtensionScope() {
/* 1686 */       if (!isExtension()) {
/* 1687 */         throw new UnsupportedOperationException(
/* 1688 */             String.format("This field is not an extension. (%s)", new Object[] { this.fullName }));
/*      */       }
/* 1690 */       return this.extensionScope;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getMessageType() {
/* 1695 */       if (getJavaType() != JavaType.MESSAGE) {
/* 1696 */         throw new UnsupportedOperationException(
/* 1697 */             String.format("This field is not of message type. (%s)", new Object[] { this.fullName }));
/*      */       }
/* 1699 */       return (Descriptors.Descriptor)this.typeDescriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumDescriptor getEnumType() {
/* 1705 */       if (getJavaType() != JavaType.ENUM) {
/* 1706 */         throw new UnsupportedOperationException(
/* 1707 */             String.format("This field is not of enum type. (%s)", new Object[] { this.fullName }));
/*      */       }
/* 1709 */       return (Descriptors.EnumDescriptor)this.typeDescriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean legacyEnumFieldTreatedAsClosed() {
/* 1737 */       if (getFile().getDependencies().isEmpty()) {
/* 1738 */         return (getType() == Type.ENUM && getEnumType().isClosed());
/*      */       }
/*      */       
/* 1741 */       return (getType() == Type.ENUM && (((JavaFeaturesProto.JavaFeatures)
/* 1742 */         getFeatures().getExtension(JavaFeaturesProto.java_)).getLegacyClosedEnum() || 
/* 1743 */         getEnumType().isClosed()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(FieldDescriptor other) {
/* 1756 */       if (other.containingType != this.containingType) {
/* 1757 */         throw new IllegalArgumentException("FieldDescriptors can only be compared to other FieldDescriptors for fields of the same message type.");
/*      */       }
/*      */ 
/*      */       
/* 1761 */       return getNumber() - other.getNumber();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1766 */       return getFullName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final class RedactionState
/*      */     {
/* 1780 */       private static final RedactionState FALSE_FALSE = new RedactionState(false, false);
/* 1781 */       private static final RedactionState FALSE_TRUE = new RedactionState(false, true);
/* 1782 */       private static final RedactionState TRUE_FALSE = new RedactionState(true, false);
/* 1783 */       private static final RedactionState TRUE_TRUE = new RedactionState(true, true);
/*      */       
/*      */       final boolean redact;
/*      */       final boolean report;
/*      */       
/*      */       private RedactionState(boolean redact, boolean report) {
/* 1789 */         this.redact = redact;
/* 1790 */         this.report = report;
/*      */       }
/*      */       
/*      */       private static RedactionState of(boolean redact) {
/* 1794 */         return of(redact, false);
/*      */       }
/*      */       
/*      */       private static RedactionState of(boolean redact, boolean report) {
/* 1798 */         if (redact) {
/* 1799 */           return report ? TRUE_TRUE : TRUE_FALSE;
/*      */         }
/* 1801 */         return report ? FALSE_TRUE : FALSE_FALSE;
/*      */       }
/*      */       
/*      */       private static RedactionState combine(RedactionState lhs, RedactionState rhs) {
/* 1805 */         return of((lhs.redact || rhs.redact), rhs.report);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public enum Type
/*      */     {
/* 1819 */       DOUBLE((String)Descriptors.FieldDescriptor.JavaType.DOUBLE),
/* 1820 */       FLOAT((String)Descriptors.FieldDescriptor.JavaType.FLOAT),
/* 1821 */       INT64((String)Descriptors.FieldDescriptor.JavaType.LONG),
/* 1822 */       UINT64((String)Descriptors.FieldDescriptor.JavaType.LONG),
/* 1823 */       INT32((String)Descriptors.FieldDescriptor.JavaType.INT),
/* 1824 */       FIXED64((String)Descriptors.FieldDescriptor.JavaType.LONG),
/* 1825 */       FIXED32((String)Descriptors.FieldDescriptor.JavaType.INT),
/* 1826 */       BOOL((String)Descriptors.FieldDescriptor.JavaType.BOOLEAN),
/* 1827 */       STRING((String)Descriptors.FieldDescriptor.JavaType.STRING),
/* 1828 */       GROUP((String)Descriptors.FieldDescriptor.JavaType.MESSAGE),
/* 1829 */       MESSAGE((String)Descriptors.FieldDescriptor.JavaType.MESSAGE),
/* 1830 */       BYTES((String)Descriptors.FieldDescriptor.JavaType.BYTE_STRING),
/* 1831 */       UINT32((String)Descriptors.FieldDescriptor.JavaType.INT),
/* 1832 */       ENUM((String)Descriptors.FieldDescriptor.JavaType.ENUM),
/* 1833 */       SFIXED32((String)Descriptors.FieldDescriptor.JavaType.INT),
/* 1834 */       SFIXED64((String)Descriptors.FieldDescriptor.JavaType.LONG),
/* 1835 */       SINT32((String)Descriptors.FieldDescriptor.JavaType.INT),
/* 1836 */       SINT64((String)Descriptors.FieldDescriptor.JavaType.LONG);
/*      */ 
/*      */       
/* 1839 */       private static final Type[] types = values(); private final Descriptors.FieldDescriptor.JavaType javaType; static {
/*      */       
/*      */       } Type(Descriptors.FieldDescriptor.JavaType javaType) {
/* 1842 */         this.javaType = javaType;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public DescriptorProtos.FieldDescriptorProto.Type toProto() {
/* 1848 */         return DescriptorProtos.FieldDescriptorProto.Type.forNumber(ordinal() + 1);
/*      */       }
/*      */       
/*      */       public Descriptors.FieldDescriptor.JavaType getJavaType() {
/* 1852 */         return this.javaType;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 1862 */       if (Type.types.length != (DescriptorProtos.FieldDescriptorProto.Type.values()).length) {
/* 1863 */         throw new RuntimeException("descriptor.proto has a new declared type but Descriptors.java wasn't updated.");
/*      */       }
/*      */     }
/*      */     
/*      */     public enum JavaType
/*      */     {
/* 1869 */       INT((String)Integer.valueOf(0)),
/* 1870 */       LONG((String)Long.valueOf(0L)),
/* 1871 */       FLOAT((String)Float.valueOf(0.0F)),
/* 1872 */       DOUBLE((String)Double.valueOf(0.0D)),
/* 1873 */       BOOLEAN((String)Boolean.valueOf(false)),
/* 1874 */       STRING(""),
/* 1875 */       BYTE_STRING((String)ByteString.EMPTY),
/* 1876 */       ENUM(null),
/* 1877 */       MESSAGE(null); private final Object defaultDefault;
/*      */       
/*      */       JavaType(Object defaultDefault) {
/* 1880 */         this.defaultDefault = defaultDefault;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static String fieldNameToJsonName(String name) {
/* 1893 */       int length = name.length();
/* 1894 */       StringBuilder result = new StringBuilder(length);
/* 1895 */       boolean isNextUpperCase = false;
/* 1896 */       for (int i = 0; i < length; i++) {
/* 1897 */         char ch = name.charAt(i);
/* 1898 */         if (ch == '_') {
/* 1899 */           isNextUpperCase = true;
/* 1900 */         } else if (isNextUpperCase) {
/*      */ 
/*      */           
/* 1903 */           if ('a' <= ch && ch <= 'z') {
/* 1904 */             ch = (char)(ch - 97 + 65);
/*      */           }
/* 1906 */           result.append(ch);
/* 1907 */           isNextUpperCase = false;
/*      */         } else {
/* 1909 */           result.append(ch);
/*      */         } 
/*      */       } 
/* 1912 */       return result.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FieldDescriptor(DescriptorProtos.FieldDescriptorProto proto, Descriptors.FileDescriptor file, Descriptors.Descriptor parent, int index, boolean isExtension) throws Descriptors.DescriptorValidationException {
/* 1922 */       this.index = index;
/* 1923 */       this.proto = proto;
/* 1924 */       this.fullName = Descriptors.computeFullName(file, parent, proto.getName());
/*      */       
/* 1926 */       if (proto.hasType()) {
/* 1927 */         this.type = Type.valueOf(proto.getType());
/*      */       }
/*      */       
/* 1930 */       this.isProto3Optional = proto.getProto3Optional();
/*      */       
/* 1932 */       if (getNumber() <= 0) {
/* 1933 */         throw new Descriptors.DescriptorValidationException(this, "Field numbers must be positive integers.");
/*      */       }
/*      */       
/* 1936 */       if (isExtension) {
/* 1937 */         if (!proto.hasExtendee()) {
/* 1938 */           throw new Descriptors.DescriptorValidationException(this, "FieldDescriptorProto.extendee not set for extension field.");
/*      */         }
/*      */         
/* 1941 */         this.containingType = null;
/* 1942 */         if (parent != null) {
/*      */           
/* 1944 */           this.extensionScope = parent;
/* 1945 */           this.parent = parent;
/*      */         } else {
/*      */           
/* 1948 */           this.extensionScope = null;
/* 1949 */           this.parent = Internal.<Descriptors.GenericDescriptor>checkNotNull(file);
/*      */         } 
/*      */         
/* 1952 */         if (proto.hasOneofIndex()) {
/* 1953 */           throw new Descriptors.DescriptorValidationException(this, "FieldDescriptorProto.oneof_index set for extension field.");
/*      */         }
/*      */         
/* 1956 */         this.containingOneof = null;
/*      */       } else {
/* 1958 */         if (proto.hasExtendee()) {
/* 1959 */           throw new Descriptors.DescriptorValidationException(this, "FieldDescriptorProto.extendee set for non-extension field.");
/*      */         }
/*      */         
/* 1962 */         this.containingType = parent;
/*      */         
/* 1964 */         if (proto.hasOneofIndex()) {
/* 1965 */           if (proto.getOneofIndex() < 0 || proto
/* 1966 */             .getOneofIndex() >= parent.toProto().getOneofDeclCount()) {
/* 1967 */             throw new Descriptors.DescriptorValidationException(this, "FieldDescriptorProto.oneof_index is out of range for type " + parent
/*      */                 
/* 1969 */                 .getName());
/*      */           }
/* 1971 */           this.containingOneof = parent.getOneofs().get(proto.getOneofIndex());
/* 1972 */           this.containingOneof.fieldCount++;
/* 1973 */           this.parent = Internal.<Descriptors.GenericDescriptor>checkNotNull(this.containingOneof);
/*      */         } else {
/* 1975 */           this.containingOneof = null;
/* 1976 */           this.parent = Internal.<Descriptors.GenericDescriptor>checkNotNull(parent);
/*      */         } 
/* 1978 */         this.extensionScope = null;
/*      */       } 
/*      */       
/* 1981 */       file.tables.addSymbol(this);
/*      */     }
/*      */ 
/*      */     
/*      */     private static RedactionState isOptionSensitive(FieldDescriptor field, Object value) {
/* 1986 */       if (field.getType() == Type.ENUM) {
/* 1987 */         if (field.isRepeated()) {
/* 1988 */           for (Descriptors.EnumValueDescriptor v : value) {
/* 1989 */             if (v.getOptions().getDebugRedact()) {
/* 1990 */               return RedactionState.of(true, false);
/*      */             }
/*      */           }
/*      */         
/* 1994 */         } else if (((Descriptors.EnumValueDescriptor)value).getOptions().getDebugRedact()) {
/* 1995 */           return RedactionState.of(true, false);
/*      */         }
/*      */       
/* 1998 */       } else if (field.getJavaType() == JavaType.MESSAGE) {
/* 1999 */         if (field.isRepeated()) {
/* 2000 */           for (Message m : value) {
/* 2001 */             for (Map.Entry<FieldDescriptor, Object> entry : m.getAllFields().entrySet()) {
/* 2002 */               RedactionState state = isOptionSensitive(entry.getKey(), entry.getValue());
/* 2003 */               if (state.redact) {
/* 2004 */                 return state;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 2010 */           for (Map.Entry<FieldDescriptor, Object> entry : ((Message)value).getAllFields().entrySet()) {
/* 2011 */             RedactionState state = isOptionSensitive(entry.getKey(), entry.getValue());
/* 2012 */             if (state.redact) {
/* 2013 */               return state;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 2018 */       return RedactionState.of(false);
/*      */     }
/*      */ 
/*      */     
/*      */     RedactionState getRedactionState() {
/* 2023 */       RedactionState state = this.redactionState;
/* 2024 */       if (state == null)
/*      */       {
/* 2026 */         synchronized (this) {
/* 2027 */           state = this.redactionState;
/* 2028 */           if (state == null) {
/* 2029 */             DescriptorProtos.FieldOptions options = getOptions();
/* 2030 */             state = RedactionState.of(options.getDebugRedact());
/*      */ 
/*      */ 
/*      */             
/* 2034 */             for (Map.Entry<FieldDescriptor, Object> entry : options.getAllFields().entrySet()) {
/*      */               
/* 2036 */               state = RedactionState.combine(state, 
/* 2037 */                   isOptionSensitive(entry.getKey(), entry.getValue()));
/* 2038 */               if (state.redact) {
/*      */                 break;
/*      */               }
/*      */             } 
/* 2042 */             this.redactionState = state;
/*      */           } 
/*      */         } 
/*      */       }
/* 2046 */       return state;
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 2051 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */     }
/*      */ 
/*      */     
/*      */     DescriptorProtos.FeatureSet inferLegacyProtoFeatures() {
/* 2056 */       if (getFile().getEdition().getNumber() >= DescriptorProtos.Edition.EDITION_2023.getNumber()) {
/* 2057 */         return DescriptorProtos.FeatureSet.getDefaultInstance();
/*      */       }
/*      */       
/* 2060 */       DescriptorProtos.FeatureSet.Builder features = null;
/*      */       
/* 2062 */       if (this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_REQUIRED) {
/* 2063 */         features = DescriptorProtos.FeatureSet.newBuilder();
/* 2064 */         features.setFieldPresence(DescriptorProtos.FeatureSet.FieldPresence.LEGACY_REQUIRED);
/*      */       } 
/*      */       
/* 2067 */       if (this.proto.getType() == DescriptorProtos.FieldDescriptorProto.Type.TYPE_GROUP) {
/* 2068 */         if (features == null) {
/* 2069 */           features = DescriptorProtos.FeatureSet.newBuilder();
/*      */         }
/* 2071 */         features.setMessageEncoding(DescriptorProtos.FeatureSet.MessageEncoding.DELIMITED);
/*      */       } 
/*      */       
/* 2074 */       if (getFile().getEdition() == DescriptorProtos.Edition.EDITION_PROTO2 && this.proto.getOptions().getPacked()) {
/* 2075 */         if (features == null) {
/* 2076 */           features = DescriptorProtos.FeatureSet.newBuilder();
/*      */         }
/* 2078 */         features.setRepeatedFieldEncoding(DescriptorProtos.FeatureSet.RepeatedFieldEncoding.PACKED);
/*      */       } 
/*      */       
/* 2081 */       if (getFile().getEdition() == DescriptorProtos.Edition.EDITION_PROTO3 && 
/* 2082 */         this.proto.getOptions().hasPacked() && !this.proto.getOptions().getPacked()) {
/* 2083 */         if (features == null) {
/* 2084 */           features = DescriptorProtos.FeatureSet.newBuilder();
/*      */         }
/* 2086 */         features.setRepeatedFieldEncoding(DescriptorProtos.FeatureSet.RepeatedFieldEncoding.EXPANDED);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2091 */       return (features != null) ? features.build() : DescriptorProtos.FeatureSet.getDefaultInstance();
/*      */     }
/*      */ 
/*      */     
/*      */     void validateFeatures() throws Descriptors.DescriptorValidationException {
/* 2096 */       if (this.containingType != null && this.containingType
/* 2097 */         .toProto().getOptions().getMessageSetWireFormat() && 
/* 2098 */         isExtension() && (
/* 2099 */         isRequired() || isRepeated() || getType() != Type.MESSAGE)) {
/* 2100 */         throw new Descriptors.DescriptorValidationException(this, "Extensions of MessageSets may not be required or repeated messages.");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void crossLink() throws Descriptors.DescriptorValidationException {
/* 2109 */       if (this.proto.hasExtendee()) {
/*      */ 
/*      */ 
/*      */         
/* 2113 */         Descriptors.GenericDescriptor extendee = (getFile()).tables.lookupSymbol(this.proto
/* 2114 */             .getExtendee(), this, Descriptors.FileDescriptorTables.SearchFilter.TYPES_ONLY);
/* 2115 */         if (!(extendee instanceof Descriptors.Descriptor)) {
/* 2116 */           throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 2117 */               .getExtendee() + "\" is not a message type.");
/*      */         }
/* 2119 */         this.containingType = (Descriptors.Descriptor)extendee;
/*      */         
/* 2121 */         if (!getContainingType().isExtensionNumber(getNumber())) {
/* 2122 */           throw new Descriptors.DescriptorValidationException(this, '"' + 
/*      */ 
/*      */               
/* 2125 */               getContainingType().getFullName() + "\" does not declare " + 
/*      */               
/* 2127 */               getNumber() + " as an extension number.");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2132 */       if (this.proto.hasTypeName()) {
/*      */ 
/*      */ 
/*      */         
/* 2136 */         Descriptors.GenericDescriptor typeDescriptor = (getFile()).tables.lookupSymbol(this.proto
/* 2137 */             .getTypeName(), this, Descriptors.FileDescriptorTables.SearchFilter.TYPES_ONLY);
/*      */         
/* 2139 */         if (!this.proto.hasType())
/*      */         {
/* 2141 */           if (typeDescriptor instanceof Descriptors.Descriptor) {
/* 2142 */             this.type = Type.MESSAGE;
/* 2143 */           } else if (typeDescriptor instanceof Descriptors.EnumDescriptor) {
/* 2144 */             this.type = Type.ENUM;
/*      */           } else {
/* 2146 */             throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 2147 */                 .getTypeName() + "\" is not a type.");
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2153 */         if (this.type.getJavaType() == JavaType.MESSAGE) {
/* 2154 */           if (!(typeDescriptor instanceof Descriptors.Descriptor)) {
/* 2155 */             throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 2156 */                 .getTypeName() + "\" is not a message type.");
/*      */           }
/* 2158 */           this.typeDescriptor = typeDescriptor;
/*      */           
/* 2160 */           if (this.proto.hasDefaultValue()) {
/* 2161 */             throw new Descriptors.DescriptorValidationException(this, "Messages can't have default values.");
/*      */           }
/* 2163 */         } else if (this.type.getJavaType() == JavaType.ENUM) {
/* 2164 */           if (!(typeDescriptor instanceof Descriptors.EnumDescriptor)) {
/* 2165 */             throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 2166 */                 .getTypeName() + "\" is not an enum type.");
/*      */           }
/* 2168 */           this.typeDescriptor = typeDescriptor;
/*      */         } else {
/* 2170 */           throw new Descriptors.DescriptorValidationException(this, "Field with primitive type has type_name.");
/*      */         }
/*      */       
/* 2173 */       } else if (this.type.getJavaType() == JavaType.MESSAGE || this.type.getJavaType() == JavaType.ENUM) {
/* 2174 */         throw new Descriptors.DescriptorValidationException(this, "Field with message or enum type missing type_name.");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2180 */       if (this.proto.getOptions().getPacked() && !isPackable()) {
/* 2181 */         throw new Descriptors.DescriptorValidationException(this, "[packed = true] can only be specified for repeated primitive fields.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2187 */       if (this.proto.hasDefaultValue()) {
/* 2188 */         if (isRepeated()) {
/* 2189 */           throw new Descriptors.DescriptorValidationException(this, "Repeated fields cannot have default values.");
/*      */         }
/*      */ 
/*      */         
/*      */         try {
/* 2194 */           switch (this.type.ordinal()) {
/*      */             case 4:
/*      */             case 14:
/*      */             case 16:
/* 2198 */               this.defaultValue = Integer.valueOf(TextFormat.parseInt32(this.proto.getDefaultValue()));
/*      */               break;
/*      */             case 6:
/*      */             case 12:
/* 2202 */               this.defaultValue = Integer.valueOf(TextFormat.parseUInt32(this.proto.getDefaultValue()));
/*      */               break;
/*      */             case 2:
/*      */             case 15:
/*      */             case 17:
/* 2207 */               this.defaultValue = Long.valueOf(TextFormat.parseInt64(this.proto.getDefaultValue()));
/*      */               break;
/*      */             case 3:
/*      */             case 5:
/* 2211 */               this.defaultValue = Long.valueOf(TextFormat.parseUInt64(this.proto.getDefaultValue()));
/*      */               break;
/*      */             case 1:
/* 2214 */               if (this.proto.getDefaultValue().equals("inf")) {
/* 2215 */                 this.defaultValue = Float.valueOf(Float.POSITIVE_INFINITY); break;
/* 2216 */               }  if (this.proto.getDefaultValue().equals("-inf")) {
/* 2217 */                 this.defaultValue = Float.valueOf(Float.NEGATIVE_INFINITY); break;
/* 2218 */               }  if (this.proto.getDefaultValue().equals("nan")) {
/* 2219 */                 this.defaultValue = Float.valueOf(Float.NaN); break;
/*      */               } 
/* 2221 */               this.defaultValue = Float.valueOf(this.proto.getDefaultValue());
/*      */               break;
/*      */             
/*      */             case 0:
/* 2225 */               if (this.proto.getDefaultValue().equals("inf")) {
/* 2226 */                 this.defaultValue = Double.valueOf(Double.POSITIVE_INFINITY); break;
/* 2227 */               }  if (this.proto.getDefaultValue().equals("-inf")) {
/* 2228 */                 this.defaultValue = Double.valueOf(Double.NEGATIVE_INFINITY); break;
/* 2229 */               }  if (this.proto.getDefaultValue().equals("nan")) {
/* 2230 */                 this.defaultValue = Double.valueOf(Double.NaN); break;
/*      */               } 
/* 2232 */               this.defaultValue = Double.valueOf(this.proto.getDefaultValue());
/*      */               break;
/*      */             
/*      */             case 7:
/* 2236 */               this.defaultValue = Boolean.valueOf(this.proto.getDefaultValue());
/*      */               break;
/*      */             case 8:
/* 2239 */               this.defaultValue = this.proto.getDefaultValue();
/*      */               break;
/*      */             case 11:
/*      */               try {
/* 2243 */                 this.defaultValue = TextFormat.unescapeBytes(this.proto.getDefaultValue());
/* 2244 */               } catch (InvalidEscapeSequenceException e) {
/* 2245 */                 throw new Descriptors.DescriptorValidationException(this, "Couldn't parse default value: " + e
/* 2246 */                     .getMessage(), e);
/*      */               } 
/*      */               break;
/*      */             case 13:
/* 2250 */               this.defaultValue = getEnumType().findValueByName(this.proto.getDefaultValue());
/* 2251 */               if (this.defaultValue == null) {
/* 2252 */                 throw new Descriptors.DescriptorValidationException(this, "Unknown enum default value: \"" + this.proto
/* 2253 */                     .getDefaultValue() + '"');
/*      */               }
/*      */               break;
/*      */             case 9:
/*      */             case 10:
/* 2258 */               throw new Descriptors.DescriptorValidationException(this, "Message type had default value.");
/*      */           } 
/* 2260 */         } catch (NumberFormatException e) {
/* 2261 */           throw new Descriptors.DescriptorValidationException(this, "Could not parse default value: \"" + this.proto
/* 2262 */               .getDefaultValue() + '"', e);
/*      */         }
/*      */       
/*      */       }
/* 2266 */       else if (isRepeated()) {
/* 2267 */         this.defaultValue = Collections.emptyList();
/*      */       } else {
/* 2269 */         switch (this.type.getJavaType().ordinal()) {
/*      */ 
/*      */           
/*      */           case 7:
/* 2273 */             this.defaultValue = getEnumType().getValue(0);
/*      */             return;
/*      */           case 8:
/* 2276 */             this.defaultValue = null;
/*      */             return;
/*      */         } 
/* 2279 */         this.defaultValue = (this.type.getJavaType()).defaultDefault;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.FieldDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 2288 */       this.proto = proto;
/* 2289 */       this.options = null;
/* 2290 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean internalMessageIsImmutable(Object message) {
/* 2295 */       return message instanceof MessageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void internalMergeFrom(Object to, Object from) {
/* 2302 */       ((Message.Builder)to).mergeFrom((Message)from);
/*      */     }
/*      */   }
/*      */   
/*      */   public static final class EnumDescriptor extends GenericDescriptor implements Internal.EnumLiteMap<EnumValueDescriptor> {
/*      */     private final int index;
/*      */     private DescriptorProtos.EnumDescriptorProto proto;
/*      */     private volatile DescriptorProtos.EnumOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.GenericDescriptor parent;
/*      */     private final Descriptors.EnumValueDescriptor[] values;
/*      */     private final Descriptors.EnumValueDescriptor[] valuesSortedByNumber;
/*      */     private final int distinctNumbers;
/*      */     
/*      */     public int getIndex() {
/* 2317 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.EnumDescriptorProto toProto() {
/* 2323 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2329 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 2339 */       return this.fullName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 2345 */       return this.parent.getFile();
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 2350 */       return this.parent;
/*      */     }
/*      */     
/*      */     public boolean isPlaceholder() {
/* 2354 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isClosed() {
/* 2382 */       return (getFeatures().getEnumType() == DescriptorProtos.FeatureSet.EnumType.CLOSED);
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getContainingType() {
/* 2387 */       if (this.parent instanceof Descriptors.Descriptor) {
/* 2388 */         return (Descriptors.Descriptor)this.parent;
/*      */       }
/* 2390 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.EnumOptions getOptions() {
/* 2395 */       if (this.options == null) {
/* 2396 */         DescriptorProtos.EnumOptions strippedOptions = this.proto.getOptions();
/* 2397 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 2401 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 2403 */         synchronized (this) {
/* 2404 */           if (this.options == null) {
/* 2405 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 2409 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.EnumValueDescriptor> getValues() {
/* 2414 */       return Collections.unmodifiableList(Arrays.asList(this.values));
/*      */     }
/*      */     
/*      */     public int getValueCount() {
/* 2418 */       return this.values.length;
/*      */     }
/*      */     
/*      */     public Descriptors.EnumValueDescriptor getValue(int index) {
/* 2422 */       return this.values[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isReservedNumber(int number) {
/* 2427 */       for (DescriptorProtos.EnumDescriptorProto.EnumReservedRange range : this.proto.getReservedRangeList()) {
/* 2428 */         if (range.getStart() <= number && number <= range.getEnd()) {
/* 2429 */           return true;
/*      */         }
/*      */       } 
/* 2432 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isReservedName(String name) {
/* 2437 */       Internal.checkNotNull(name);
/* 2438 */       for (String reservedName : this.proto.getReservedNameList()) {
/* 2439 */         if (reservedName.equals(name)) {
/* 2440 */           return true;
/*      */         }
/*      */       } 
/* 2443 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumValueDescriptor findValueByName(String name) {
/* 2453 */       Descriptors.GenericDescriptor result = (getFile()).tables.findSymbol(this.fullName + '.' + name);
/* 2454 */       if (result instanceof Descriptors.EnumValueDescriptor) {
/* 2455 */         return (Descriptors.EnumValueDescriptor)result;
/*      */       }
/* 2457 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumValueDescriptor findValueByNumber(int number) {
/* 2470 */       return (Descriptors.EnumValueDescriptor)Descriptors.binarySearch((T[])this.valuesSortedByNumber, this.distinctNumbers, (ToIntFunction)Descriptors.EnumValueDescriptor.NUMBER_GETTER, number);
/*      */     }
/*      */     
/*      */     private static class UnknownEnumValueReference
/*      */       extends WeakReference<Descriptors.EnumValueDescriptor> {
/*      */       private final int number;
/*      */       
/*      */       private UnknownEnumValueReference(int number, Descriptors.EnumValueDescriptor descriptor) {
/* 2478 */         super(descriptor);
/* 2479 */         this.number = number;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.EnumValueDescriptor findValueByNumberCreatingIfUnknown(int number) {
/* 2488 */       Descriptors.EnumValueDescriptor result = findValueByNumber(number);
/* 2489 */       if (result != null) {
/* 2490 */         return result;
/*      */       }
/*      */       
/* 2493 */       synchronized (this) {
/* 2494 */         if (this.cleanupQueue == null) {
/* 2495 */           this.cleanupQueue = new ReferenceQueue<>();
/* 2496 */           this.unknownValues = new HashMap<>();
/*      */         } else {
/*      */           while (true) {
/* 2499 */             UnknownEnumValueReference toClean = (UnknownEnumValueReference)this.cleanupQueue.poll();
/* 2500 */             if (toClean == null) {
/*      */               break;
/*      */             }
/* 2503 */             this.unknownValues.remove(Integer.valueOf(toClean.number));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2510 */         WeakReference<Descriptors.EnumValueDescriptor> reference = this.unknownValues.get(Integer.valueOf(number));
/* 2511 */         result = (reference == null) ? null : reference.get();
/*      */         
/* 2513 */         if (result == null) {
/* 2514 */           result = new Descriptors.EnumValueDescriptor(this, Integer.valueOf(number));
/* 2515 */           this.unknownValues.put(Integer.valueOf(number), new UnknownEnumValueReference(number, result));
/*      */         } 
/*      */       } 
/* 2518 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     int getUnknownEnumValueDescriptorCount() {
/* 2523 */       return this.unknownValues.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2534 */     private Map<Integer, WeakReference<Descriptors.EnumValueDescriptor>> unknownValues = null;
/* 2535 */     private ReferenceQueue<Descriptors.EnumValueDescriptor> cleanupQueue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EnumDescriptor(DescriptorProtos.EnumDescriptorProto proto, Descriptors.FileDescriptor file, Descriptors.Descriptor parent, int index) throws Descriptors.DescriptorValidationException {
/* 2543 */       if (parent == null) {
/* 2544 */         this.parent = file;
/*      */       } else {
/* 2546 */         this.parent = parent;
/*      */       } 
/* 2548 */       this.index = index;
/* 2549 */       this.proto = proto;
/* 2550 */       this.fullName = Descriptors.computeFullName(file, parent, proto.getName());
/*      */       
/* 2552 */       if (proto.getValueCount() == 0)
/*      */       {
/*      */         
/* 2555 */         throw new Descriptors.DescriptorValidationException(this, "Enums must contain at least one value.");
/*      */       }
/*      */       
/* 2558 */       this.values = new Descriptors.EnumValueDescriptor[proto.getValueCount()];
/* 2559 */       for (int i = 0; i < proto.getValueCount(); i++) {
/* 2560 */         this.values[i] = new Descriptors.EnumValueDescriptor(proto.getValue(i), this, i);
/*      */       }
/* 2562 */       this.valuesSortedByNumber = (Descriptors.EnumValueDescriptor[])this.values.clone();
/* 2563 */       Arrays.sort(this.valuesSortedByNumber, Descriptors.EnumValueDescriptor.BY_NUMBER);
/*      */       
/* 2565 */       int j = 0;
/* 2566 */       for (int k = 1; k < proto.getValueCount(); k++) {
/* 2567 */         Descriptors.EnumValueDescriptor oldValue = this.valuesSortedByNumber[j];
/* 2568 */         Descriptors.EnumValueDescriptor newValue = this.valuesSortedByNumber[k];
/* 2569 */         if (oldValue.getNumber() != newValue.getNumber()) {
/* 2570 */           this.valuesSortedByNumber[++j] = newValue;
/*      */         }
/*      */       } 
/* 2573 */       this.distinctNumbers = j + 1;
/* 2574 */       Arrays.fill((Object[])this.valuesSortedByNumber, this.distinctNumbers, proto.getValueCount(), (Object)null);
/*      */       
/* 2576 */       file.tables.addSymbol(this);
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 2581 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */       
/* 2583 */       for (Descriptors.EnumValueDescriptor value : this.values) {
/* 2584 */         value.resolveAllFeatures();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.EnumDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 2590 */       this.proto = proto;
/* 2591 */       this.options = null;
/* 2592 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */       
/* 2594 */       for (int i = 0; i < this.values.length; i++) {
/* 2595 */         this.values[i].setProto(proto.getValue(i));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class EnumValueDescriptor
/*      */     extends GenericDescriptor
/*      */     implements Internal.EnumLite
/*      */   {
/* 2610 */     static final Comparator<EnumValueDescriptor> BY_NUMBER = new Comparator<EnumValueDescriptor>()
/*      */       {
/*      */         public int compare(Descriptors.EnumValueDescriptor o1, Descriptors.EnumValueDescriptor o2)
/*      */         {
/* 2614 */           return Integer.compare(o1.getNumber(), o2.getNumber());
/*      */         }
/*      */       };
/*      */     
/* 2618 */     static final ToIntFunction<EnumValueDescriptor> NUMBER_GETTER = EnumValueDescriptor::getNumber;
/*      */     private final int index;
/*      */     private DescriptorProtos.EnumValueDescriptorProto proto;
/*      */     private volatile DescriptorProtos.EnumValueOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.EnumDescriptor type;
/*      */     
/*      */     public int getIndex() {
/* 2626 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.EnumValueDescriptorProto toProto() {
/* 2632 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2638 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNumber() {
/* 2644 */       return this.proto.getNumber();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2649 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 2659 */       return this.fullName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 2665 */       return this.type.getFile();
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 2670 */       return this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.EnumDescriptor getType() {
/* 2675 */       return this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.EnumValueOptions getOptions() {
/* 2680 */       if (this.options == null) {
/* 2681 */         DescriptorProtos.EnumValueOptions strippedOptions = this.proto.getOptions();
/* 2682 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 2686 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 2688 */         synchronized (this) {
/* 2689 */           if (this.options == null) {
/* 2690 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 2694 */       return this.options;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EnumValueDescriptor(DescriptorProtos.EnumValueDescriptorProto proto, Descriptors.EnumDescriptor parent, int index) throws Descriptors.DescriptorValidationException {
/* 2706 */       this.index = index;
/* 2707 */       this.proto = proto;
/* 2708 */       this.type = parent;
/* 2709 */       this.fullName = parent.getFullName() + '.' + proto.getName();
/* 2710 */       (this.type.getFile()).tables.addSymbol(this);
/*      */     }
/*      */ 
/*      */     
/*      */     private EnumValueDescriptor(Descriptors.EnumDescriptor parent, Integer number) {
/* 2715 */       String name = "UNKNOWN_ENUM_VALUE_" + parent.getName() + "_" + number;
/*      */       
/* 2717 */       DescriptorProtos.EnumValueDescriptorProto proto = DescriptorProtos.EnumValueDescriptorProto.newBuilder().setName(name).setNumber(number.intValue()).build();
/* 2718 */       this.index = -1;
/* 2719 */       this.proto = proto;
/* 2720 */       this.type = parent;
/* 2721 */       this.fullName = parent.getFullName() + '.' + proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 2728 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.EnumValueDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 2734 */       this.proto = proto;
/* 2735 */       this.options = null;
/* 2736 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */     }
/*      */   }
/*      */   
/*      */   public static final class ServiceDescriptor extends GenericDescriptor { private final int index;
/*      */     private DescriptorProtos.ServiceDescriptorProto proto;
/*      */     private volatile DescriptorProtos.ServiceOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.FileDescriptor file;
/*      */     private Descriptors.MethodDescriptor[] methods;
/*      */     
/*      */     public int getIndex() {
/* 2748 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.ServiceDescriptorProto toProto() {
/* 2754 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2760 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 2770 */       return this.fullName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 2776 */       return this.file;
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 2781 */       return this.file;
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.ServiceOptions getOptions() {
/* 2786 */       if (this.options == null) {
/* 2787 */         DescriptorProtos.ServiceOptions strippedOptions = this.proto.getOptions();
/* 2788 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 2792 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 2794 */         synchronized (this) {
/* 2795 */           if (this.options == null) {
/* 2796 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 2800 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.MethodDescriptor> getMethods() {
/* 2805 */       return Collections.unmodifiableList(Arrays.asList(this.methods));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMethodCount() {
/* 2810 */       return this.methods.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.MethodDescriptor getMethod(int index) {
/* 2815 */       return this.methods[index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.MethodDescriptor findMethodByName(String name) {
/* 2825 */       Descriptors.GenericDescriptor result = this.file.tables.findSymbol(this.fullName + '.' + name);
/* 2826 */       if (result instanceof Descriptors.MethodDescriptor) {
/* 2827 */         return (Descriptors.MethodDescriptor)result;
/*      */       }
/* 2829 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ServiceDescriptor(DescriptorProtos.ServiceDescriptorProto proto, Descriptors.FileDescriptor file, int index) throws Descriptors.DescriptorValidationException {
/* 2843 */       this.index = index;
/* 2844 */       this.proto = proto;
/* 2845 */       this.fullName = Descriptors.computeFullName(file, null, proto.getName());
/* 2846 */       this.file = file;
/*      */       
/* 2848 */       this.methods = new Descriptors.MethodDescriptor[proto.getMethodCount()];
/* 2849 */       for (int i = 0; i < proto.getMethodCount(); i++) {
/* 2850 */         this.methods[i] = new Descriptors.MethodDescriptor(proto.getMethod(i), this, i);
/*      */       }
/*      */       
/* 2853 */       file.tables.addSymbol(this);
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 2858 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */       
/* 2860 */       for (Descriptors.MethodDescriptor method : this.methods) {
/* 2861 */         method.resolveAllFeatures();
/*      */       }
/*      */     }
/*      */     
/*      */     private void crossLink() throws Descriptors.DescriptorValidationException {
/* 2866 */       for (Descriptors.MethodDescriptor method : this.methods) {
/* 2867 */         method.crossLink();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.ServiceDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 2873 */       this.proto = proto;
/* 2874 */       this.options = null;
/* 2875 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */       
/* 2877 */       for (int i = 0; i < this.methods.length; i++)
/* 2878 */         this.methods[i].setProto(proto.getMethod(i)); 
/*      */     } }
/*      */   
/*      */   public static final class MethodDescriptor extends GenericDescriptor {
/*      */     private final int index;
/*      */     private DescriptorProtos.MethodDescriptorProto proto;
/*      */     private volatile DescriptorProtos.MethodOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.ServiceDescriptor service;
/*      */     private Descriptors.Descriptor inputType;
/*      */     private Descriptors.Descriptor outputType;
/*      */     
/*      */     public int getIndex() {
/* 2891 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DescriptorProtos.MethodDescriptorProto toProto() {
/* 2897 */       return this.proto;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2903 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 2913 */       return this.fullName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 2919 */       return this.service.file;
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 2924 */       return this.service;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.ServiceDescriptor getService() {
/* 2929 */       return this.service;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getInputType() {
/* 2934 */       return this.inputType;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getOutputType() {
/* 2939 */       return this.outputType;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isClientStreaming() {
/* 2944 */       return this.proto.getClientStreaming();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isServerStreaming() {
/* 2949 */       return this.proto.getServerStreaming();
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.MethodOptions getOptions() {
/* 2954 */       if (this.options == null) {
/* 2955 */         DescriptorProtos.MethodOptions strippedOptions = this.proto.getOptions();
/* 2956 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 2960 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 2962 */         synchronized (this) {
/* 2963 */           if (this.options == null) {
/* 2964 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 2968 */       return this.options;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodDescriptor(DescriptorProtos.MethodDescriptorProto proto, Descriptors.ServiceDescriptor parent, int index) throws Descriptors.DescriptorValidationException {
/* 2984 */       this.index = index;
/* 2985 */       this.proto = proto;
/* 2986 */       this.service = parent;
/*      */       
/* 2988 */       this.fullName = parent.getFullName() + '.' + proto.getName();
/*      */       
/* 2990 */       this.service.file.tables.addSymbol(this);
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 2995 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void crossLink() throws Descriptors.DescriptorValidationException {
/* 3002 */       Descriptors.GenericDescriptor input = (getFile()).tables.lookupSymbol(this.proto
/* 3003 */           .getInputType(), this, Descriptors.FileDescriptorTables.SearchFilter.TYPES_ONLY);
/* 3004 */       if (!(input instanceof Descriptors.Descriptor)) {
/* 3005 */         throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 3006 */             .getInputType() + "\" is not a message type.");
/*      */       }
/* 3008 */       this.inputType = (Descriptors.Descriptor)input;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3013 */       Descriptors.GenericDescriptor output = (getFile()).tables.lookupSymbol(this.proto
/* 3014 */           .getOutputType(), this, Descriptors.FileDescriptorTables.SearchFilter.TYPES_ONLY);
/* 3015 */       if (!(output instanceof Descriptors.Descriptor)) {
/* 3016 */         throw new Descriptors.DescriptorValidationException(this, '"' + this.proto
/* 3017 */             .getOutputType() + "\" is not a message type.");
/*      */       }
/* 3019 */       this.outputType = (Descriptors.Descriptor)output;
/*      */     }
/*      */ 
/*      */     
/*      */     private void setProto(DescriptorProtos.MethodDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 3024 */       this.proto = proto;
/* 3025 */       this.options = null;
/* 3026 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String computeFullName(FileDescriptor file, Descriptor parent, String name) {
/* 3037 */     if (parent != null) {
/* 3038 */       return parent.getFullName() + '.' + name;
/*      */     }
/*      */     
/* 3041 */     String packageName = file.getPackage();
/* 3042 */     if (!packageName.isEmpty()) {
/* 3043 */       return packageName + '.' + name;
/*      */     }
/*      */     
/* 3046 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class GenericDescriptor
/*      */   {
/*      */     volatile DescriptorProtos.FeatureSet features;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private GenericDescriptor() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void resolveFeatures(DescriptorProtos.FeatureSet unresolvedFeatures) throws Descriptors.DescriptorValidationException {
/*      */       DescriptorProtos.FeatureSet.Builder features;
/* 3070 */       GenericDescriptor parent = getParent();
/* 3071 */       DescriptorProtos.FeatureSet inferredLegacyFeatures = null;
/* 3072 */       if (parent != null && unresolvedFeatures
/* 3073 */         .equals(DescriptorProtos.FeatureSet.getDefaultInstance()) && (
/* 3074 */         inferredLegacyFeatures = inferLegacyProtoFeatures())
/* 3075 */         .equals(DescriptorProtos.FeatureSet.getDefaultInstance())) {
/* 3076 */         this.features = parent.features;
/* 3077 */         validateFeatures();
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 3083 */       boolean hasPossibleCustomJavaFeature = false;
/* 3084 */       for (Descriptors.FieldDescriptor f : unresolvedFeatures.getExtensionFields().keySet()) {
/* 3085 */         if (f.getNumber() == JavaFeaturesProto.java_.getNumber() && f != JavaFeaturesProto.java_
/* 3086 */           .getDescriptor()) {
/* 3087 */           hasPossibleCustomJavaFeature = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3095 */       boolean hasPossibleUnknownJavaFeature = (!unresolvedFeatures.getUnknownFields().isEmpty() && unresolvedFeatures.getUnknownFields().hasField(JavaFeaturesProto.java_.getNumber()));
/*      */       
/* 3097 */       if (hasPossibleCustomJavaFeature || hasPossibleUnknownJavaFeature) {
/* 3098 */         ExtensionRegistry registry = ExtensionRegistry.newInstance();
/* 3099 */         registry.add(JavaFeaturesProto.java_);
/* 3100 */         ByteString bytes = unresolvedFeatures.toByteString();
/*      */         try {
/* 3102 */           unresolvedFeatures = DescriptorProtos.FeatureSet.parseFrom(bytes, registry);
/* 3103 */         } catch (InvalidProtocolBufferException e) {
/* 3104 */           throw new Descriptors.DescriptorValidationException(this, "Failed to parse features with Java feature extension registry.", e);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3110 */       if (parent == null) {
/* 3111 */         DescriptorProtos.Edition edition = getFile().getEdition();
/* 3112 */         features = Descriptors.getEditionDefaults(edition).toBuilder();
/*      */       } else {
/* 3114 */         features = parent.features.toBuilder();
/*      */       } 
/* 3116 */       if (inferredLegacyFeatures == null) {
/* 3117 */         inferredLegacyFeatures = inferLegacyProtoFeatures();
/*      */       }
/* 3119 */       features.mergeFrom(inferredLegacyFeatures);
/* 3120 */       features.mergeFrom(unresolvedFeatures);
/* 3121 */       this.features = Descriptors.internFeatures(features.build());
/* 3122 */       validateFeatures();
/*      */     }
/*      */     
/*      */     DescriptorProtos.FeatureSet inferLegacyProtoFeatures() {
/* 3126 */       return DescriptorProtos.FeatureSet.getDefaultInstance();
/*      */     }
/*      */ 
/*      */     
/*      */     void validateFeatures() throws Descriptors.DescriptorValidationException {}
/*      */ 
/*      */     
/*      */     DescriptorProtos.FeatureSet getFeatures() {
/* 3134 */       if (this.features == null && (
/* 3135 */         getFile().getEdition() == DescriptorProtos.Edition.EDITION_PROTO2 || 
/* 3136 */         getFile().getEdition() == DescriptorProtos.Edition.EDITION_PROTO3)) {
/* 3137 */         getFile().resolveAllFeaturesImmutable();
/*      */       }
/* 3139 */       if (this.features == null) {
/* 3140 */         throw new NullPointerException(
/* 3141 */             String.format("Features not yet loaded for %s.", new Object[] { getFullName() }));
/*      */       }
/* 3143 */       return this.features;
/*      */     }
/*      */     public abstract Message toProto();
/*      */     public abstract String getName();
/*      */     public abstract String getFullName();
/*      */     public abstract Descriptors.FileDescriptor getFile();
/*      */     abstract GenericDescriptor getParent(); }
/*      */   public static class DescriptorValidationException extends Exception { private static final long serialVersionUID = 5750205775490483148L; private final String name;
/*      */     private final Message proto;
/*      */     private final String description;
/*      */     
/*      */     public String getProblemSymbolName() {
/* 3155 */       return this.name;
/*      */     }
/*      */ 
/*      */     
/*      */     public Message getProblemProto() {
/* 3160 */       return this.proto;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getDescription() {
/* 3165 */       return this.description;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DescriptorValidationException(Descriptors.GenericDescriptor problemDescriptor, String description) {
/* 3174 */       super(problemDescriptor.getFullName() + ": " + description);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3179 */       this.name = problemDescriptor.getFullName();
/* 3180 */       this.proto = problemDescriptor.toProto();
/* 3181 */       this.description = description;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DescriptorValidationException(Descriptors.GenericDescriptor problemDescriptor, String description, Throwable cause) {
/* 3188 */       this(problemDescriptor, description);
/* 3189 */       initCause(cause);
/*      */     }
/*      */ 
/*      */     
/*      */     private DescriptorValidationException(Descriptors.FileDescriptor problemDescriptor, String description) {
/* 3194 */       super(problemDescriptor.getName() + ": " + description);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3199 */       this.name = problemDescriptor.getName();
/* 3200 */       this.proto = problemDescriptor.toProto();
/* 3201 */       this.description = description;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class FileDescriptorTables
/*      */   {
/*      */     private final Set<Descriptors.FileDescriptor> dependencies;
/*      */     
/*      */     private final boolean allowUnknownDependencies;
/*      */     private final Map<String, Descriptors.GenericDescriptor> descriptorsByName;
/*      */     
/*      */     enum SearchFilter
/*      */     {
/* 3215 */       TYPES_ONLY,
/* 3216 */       AGGREGATES_ONLY,
/* 3217 */       ALL_SYMBOLS;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FileDescriptorTables(Descriptors.FileDescriptor[] dependencies, boolean allowUnknownDependencies)
/*      */     {
/* 3255 */       this.descriptorsByName = new HashMap<>(); this.dependencies = Collections.newSetFromMap(new IdentityHashMap<>(dependencies.length)); this.allowUnknownDependencies = allowUnknownDependencies; for (Descriptors.FileDescriptor dependency : dependencies) { this.dependencies.add(dependency); importPublicDependencies(dependency); }
/*      */        for (Descriptors.FileDescriptor dependency : this.dependencies) { try { addPackage(dependency.getPackage(), dependency); }
/*      */         catch (DescriptorValidationException e) { throw new AssertionError(e); }
/*      */          }
/* 3259 */        } Descriptors.GenericDescriptor findSymbol(String fullName) { return findSymbol(fullName, SearchFilter.ALL_SYMBOLS); }
/*      */      private void importPublicDependencies(Descriptors.FileDescriptor file) {
/*      */       for (Descriptors.FileDescriptor dependency : file.getPublicDependencies()) {
/*      */         if (this.dependencies.add(dependency))
/*      */           importPublicDependencies(dependency); 
/*      */       } 
/*      */     }
/*      */     Descriptors.GenericDescriptor findSymbol(String fullName, SearchFilter filter) {
/* 3267 */       Descriptors.GenericDescriptor result = this.descriptorsByName.get(fullName);
/* 3268 */       if (result != null && (
/* 3269 */         filter == SearchFilter.ALL_SYMBOLS || (filter == SearchFilter.TYPES_ONLY && 
/* 3270 */         isType(result)) || (filter == SearchFilter.AGGREGATES_ONLY && 
/* 3271 */         isAggregate(result)))) {
/* 3272 */         return result;
/*      */       }
/*      */ 
/*      */       
/* 3276 */       for (Descriptors.FileDescriptor dependency : this.dependencies) {
/* 3277 */         result = dependency.tables.descriptorsByName.get(fullName);
/* 3278 */         if (result != null && (
/* 3279 */           filter == SearchFilter.ALL_SYMBOLS || (filter == SearchFilter.TYPES_ONLY && 
/* 3280 */           isType(result)) || (filter == SearchFilter.AGGREGATES_ONLY && 
/* 3281 */           isAggregate(result)))) {
/* 3282 */           return result;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 3287 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isType(Descriptors.GenericDescriptor descriptor) {
/* 3292 */       return (descriptor instanceof Descriptors.Descriptor || descriptor instanceof Descriptors.EnumDescriptor);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isAggregate(Descriptors.GenericDescriptor descriptor) {
/* 3297 */       return (descriptor instanceof Descriptors.Descriptor || descriptor instanceof Descriptors.EnumDescriptor || descriptor instanceof PackageDescriptor || descriptor instanceof Descriptors.ServiceDescriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor lookupSymbol(String name, Descriptors.GenericDescriptor relativeTo, SearchFilter filter) throws Descriptors.DescriptorValidationException {
/*      */       Descriptors.GenericDescriptor result;
/*      */       String fullname;
/* 3316 */       if (name.startsWith(".")) {
/*      */         
/* 3318 */         fullname = name.substring(1);
/* 3319 */         result = findSymbol(fullname, filter);
/*      */       } else {
/*      */         String firstPart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3334 */         int firstPartLength = name.indexOf('.');
/*      */         
/* 3336 */         if (firstPartLength == -1) {
/* 3337 */           firstPart = name;
/*      */         } else {
/* 3339 */           firstPart = name.substring(0, firstPartLength);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3344 */         StringBuilder scopeToTry = new StringBuilder(relativeTo.getFullName());
/*      */ 
/*      */         
/*      */         while (true) {
/* 3348 */           int dotpos = scopeToTry.lastIndexOf(".");
/* 3349 */           if (dotpos == -1) {
/* 3350 */             fullname = name;
/* 3351 */             Descriptors.GenericDescriptor genericDescriptor = findSymbol(name, filter);
/*      */             break;
/*      */           } 
/* 3354 */           scopeToTry.setLength(dotpos + 1);
/*      */ 
/*      */           
/* 3357 */           scopeToTry.append(firstPart);
/*      */           
/* 3359 */           result = findSymbol(scopeToTry
/* 3360 */               .toString(), SearchFilter.AGGREGATES_ONLY);
/*      */           
/* 3362 */           if (result != null) {
/* 3363 */             if (firstPartLength != -1) {
/*      */ 
/*      */ 
/*      */               
/* 3367 */               scopeToTry.setLength(dotpos + 1);
/* 3368 */               scopeToTry.append(name);
/* 3369 */               result = findSymbol(scopeToTry.toString(), filter);
/*      */             } 
/* 3371 */             fullname = scopeToTry.toString();
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 3376 */           scopeToTry.setLength(dotpos);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3381 */       if (result == null) {
/* 3382 */         if (this.allowUnknownDependencies && filter == SearchFilter.TYPES_ONLY) {
/* 3383 */           Descriptors.logger.warning("The descriptor for message type \"" + name + "\" cannot be found and a placeholder is created for it");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3393 */           result = new Descriptors.Descriptor(fullname);
/*      */ 
/*      */           
/* 3396 */           this.dependencies.add(result.getFile());
/* 3397 */           return result;
/*      */         } 
/* 3399 */         throw new Descriptors.DescriptorValidationException(relativeTo, '"' + name + "\" is not defined.");
/*      */       } 
/*      */       
/* 3402 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addSymbol(Descriptors.GenericDescriptor descriptor) throws Descriptors.DescriptorValidationException {
/* 3411 */       validateSymbolName(descriptor);
/*      */       
/* 3413 */       String fullName = descriptor.getFullName();
/*      */       
/* 3415 */       Descriptors.GenericDescriptor old = this.descriptorsByName.put(fullName, descriptor);
/* 3416 */       if (old != null) {
/* 3417 */         this.descriptorsByName.put(fullName, old);
/*      */         
/* 3419 */         if (descriptor.getFile() == old.getFile()) {
/* 3420 */           int dotpos = fullName.lastIndexOf('.');
/* 3421 */           if (dotpos == -1) {
/* 3422 */             throw new Descriptors.DescriptorValidationException(descriptor, '"' + fullName + "\" is already defined.");
/*      */           }
/*      */           
/* 3425 */           throw new Descriptors.DescriptorValidationException(descriptor, '"' + fullName
/*      */ 
/*      */               
/* 3428 */               .substring(dotpos + 1) + "\" is already defined in \"" + fullName
/*      */               
/* 3430 */               .substring(0, dotpos) + "\".");
/*      */         } 
/*      */ 
/*      */         
/* 3434 */         throw new Descriptors.DescriptorValidationException(descriptor, '"' + fullName + "\" is already defined in file \"" + old
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3439 */             .getFile().getName() + "\".");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static final class PackageDescriptor
/*      */       extends Descriptors.GenericDescriptor
/*      */     {
/*      */       private final String name;
/*      */       
/*      */       private final String fullName;
/*      */       private final Descriptors.FileDescriptor file;
/*      */       
/*      */       public Message toProto() {
/* 3453 */         return this.file.toProto();
/*      */       }
/*      */ 
/*      */       
/*      */       public String getName() {
/* 3458 */         return this.name;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getFullName() {
/* 3463 */         return this.fullName;
/*      */       }
/*      */ 
/*      */       
/*      */       Descriptors.GenericDescriptor getParent() {
/* 3468 */         return this.file;
/*      */       }
/*      */ 
/*      */       
/*      */       public Descriptors.FileDescriptor getFile() {
/* 3473 */         return this.file;
/*      */       }
/*      */       
/*      */       PackageDescriptor(String name, String fullName, Descriptors.FileDescriptor file) {
/* 3477 */         this.file = file;
/* 3478 */         this.fullName = fullName;
/* 3479 */         this.name = name;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addPackage(String fullName, Descriptors.FileDescriptor file) throws Descriptors.DescriptorValidationException {
/*      */       String name;
/* 3494 */       int dotpos = fullName.lastIndexOf('.');
/*      */       
/* 3496 */       if (dotpos == -1) {
/* 3497 */         name = fullName;
/*      */       } else {
/* 3499 */         addPackage(fullName.substring(0, dotpos), file);
/* 3500 */         name = fullName.substring(dotpos + 1);
/*      */       } 
/*      */ 
/*      */       
/* 3504 */       Descriptors.GenericDescriptor old = this.descriptorsByName.put(fullName, new PackageDescriptor(name, fullName, file));
/* 3505 */       if (old != null) {
/* 3506 */         this.descriptorsByName.put(fullName, old);
/* 3507 */         if (!(old instanceof PackageDescriptor)) {
/* 3508 */           throw new Descriptors.DescriptorValidationException(file, '"' + name + "\" is already defined (as something other than a package) in file \"" + old
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3514 */               .getFile().getName() + "\".");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void validateSymbolName(Descriptors.GenericDescriptor descriptor) throws Descriptors.DescriptorValidationException {
/* 3526 */       String name = descriptor.getName();
/* 3527 */       if (name.length() == 0) {
/* 3528 */         throw new Descriptors.DescriptorValidationException(descriptor, "Missing name.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3535 */       for (int i = 0; i < name.length(); ) {
/* 3536 */         char c = name.charAt(i);
/* 3537 */         if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == '_' || ('0' <= c && c <= '9' && i > 0)) {
/*      */           i++;
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 3544 */         throw new Descriptors.DescriptorValidationException(descriptor, '"' + name + "\" is not a valid identifier.");
/*      */       } 
/*      */     } }
/*      */   public static final class OneofDescriptor extends GenericDescriptor { private final int index; private DescriptorProtos.OneofDescriptorProto proto; private volatile DescriptorProtos.OneofOptions options;
/*      */     private final String fullName;
/*      */     private final Descriptors.Descriptor containingType;
/*      */     private int fieldCount;
/*      */     private Descriptors.FieldDescriptor[] fields;
/*      */     
/*      */     public int getIndex() {
/* 3554 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getName() {
/* 3559 */       return this.proto.getName();
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FileDescriptor getFile() {
/* 3564 */       return this.containingType.getFile();
/*      */     }
/*      */ 
/*      */     
/*      */     Descriptors.GenericDescriptor getParent() {
/* 3569 */       return this.containingType;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getFullName() {
/* 3574 */       return this.fullName;
/*      */     }
/*      */     
/*      */     public Descriptors.Descriptor getContainingType() {
/* 3578 */       return this.containingType;
/*      */     }
/*      */     
/*      */     public int getFieldCount() {
/* 3582 */       return this.fieldCount;
/*      */     }
/*      */     
/*      */     public DescriptorProtos.OneofOptions getOptions() {
/* 3586 */       if (this.options == null) {
/* 3587 */         DescriptorProtos.OneofOptions strippedOptions = this.proto.getOptions();
/* 3588 */         if (strippedOptions.hasFeatures())
/*      */         {
/*      */ 
/*      */           
/* 3592 */           strippedOptions = strippedOptions.toBuilder().clearFeatures().build();
/*      */         }
/* 3594 */         synchronized (this) {
/* 3595 */           if (this.options == null) {
/* 3596 */             this.options = strippedOptions;
/*      */           }
/*      */         } 
/*      */       } 
/* 3600 */       return this.options;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Descriptors.FieldDescriptor> getFields() {
/* 3605 */       return Collections.unmodifiableList(Arrays.asList(this.fields));
/*      */     }
/*      */     
/*      */     public Descriptors.FieldDescriptor getField(int index) {
/* 3609 */       return this.fields[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public DescriptorProtos.OneofDescriptorProto toProto() {
/* 3614 */       return this.proto;
/*      */     }
/*      */     
/*      */     boolean isSynthetic() {
/* 3618 */       return (this.fields.length == 1 && (this.fields[0]).isProto3Optional);
/*      */     }
/*      */ 
/*      */     
/*      */     private void resolveAllFeatures() throws Descriptors.DescriptorValidationException {
/* 3623 */       resolveFeatures(this.proto.getOptions().getFeatures());
/*      */     }
/*      */     
/*      */     private void setProto(DescriptorProtos.OneofDescriptorProto proto) throws Descriptors.DescriptorValidationException {
/* 3627 */       this.proto = proto;
/* 3628 */       this.options = null;
/* 3629 */       resolveFeatures(proto.getOptions().getFeatures());
/*      */     }
/*      */ 
/*      */     
/*      */     private OneofDescriptor(DescriptorProtos.OneofDescriptorProto proto, Descriptors.Descriptor parent, int index) {
/* 3634 */       this.proto = proto;
/* 3635 */       this.fullName = Descriptors.computeFullName(null, parent, proto.getName());
/* 3636 */       this.index = index;
/*      */       
/* 3638 */       this.containingType = parent;
/* 3639 */       this.fieldCount = 0;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T binarySearch(T[] array, int size, ToIntFunction<T> getter, int number) {
/* 3653 */     int left = 0;
/* 3654 */     int right = size - 1;
/*      */     
/* 3656 */     while (left <= right) {
/* 3657 */       int mid = (left + right) / 2;
/* 3658 */       T midValue = array[mid];
/* 3659 */       int midValueNumber = getter.applyAsInt(midValue);
/* 3660 */       if (number < midValueNumber) {
/* 3661 */         right = mid - 1; continue;
/* 3662 */       }  if (number > midValueNumber) {
/* 3663 */         left = mid + 1; continue;
/*      */       } 
/* 3665 */       return midValue;
/*      */     } 
/*      */     
/* 3668 */     return null;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Descriptors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */