/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Conventions
/*    */ {
/* 43 */   public static final Convention CLASS_AND_PROPERTY_CONVENTION = new ConventionDefaultsImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public static final Convention ANNOTATION_CONVENTION = new ConventionAnnotationImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static final Convention SET_PRIVATE_FIELDS_CONVENTION = new ConventionSetPrivateFieldImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public static final Convention USE_GETTERS_FOR_SETTERS = new ConventionUseGettersAsSettersImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public static final Convention OBJECT_ID_GENERATORS = new ConventionObjectIdGeneratorsImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   public static final List<Convention> DEFAULT_CONVENTIONS = Collections.unmodifiableList(Arrays.asList(new Convention[] { CLASS_AND_PROPERTY_CONVENTION, ANNOTATION_CONVENTION, OBJECT_ID_GENERATORS }));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   public static final List<Convention> NO_CONVENTIONS = Collections.emptyList();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\Conventions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */