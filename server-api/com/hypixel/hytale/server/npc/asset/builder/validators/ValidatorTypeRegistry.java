/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.google.gson.GsonBuilder;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidatorTypeRegistry
/*    */ {
/*    */   @Nonnull
/*    */   public static GsonBuilder registerTypes(@Nonnull GsonBuilder gsonBuilder) {
/* 14 */     SubTypeTypeAdapterFactory factory = SubTypeTypeAdapterFactory.of(Validator.class, "Type");
/* 15 */     factory.registerSubType(StringNotEmptyValidator.class, "StringNotEmpty");
/* 16 */     factory.registerSubType(StringNullOrNotEmptyValidator.class, "StringNullOrNotEmpty");
/* 17 */     factory.registerSubType(StringsAtMostOneValidator.class, "StringsAtMostOne");
/* 18 */     factory.registerSubType(StringsOneSetValidator.class, "StringsOneSet");
/* 19 */     factory.registerSubType(StringsNotEmptyValidator.class, "NotAllStringsEmpty");
/* 20 */     factory.registerSubType(IntSingleValidator.class, "Int");
/* 21 */     factory.registerSubType(IntOrValidator.class, "IntOr");
/* 22 */     factory.registerSubType(IntRangeValidator.class, "IntRange");
/* 23 */     factory.registerSubType(DoubleSingleValidator.class, "Double");
/* 24 */     factory.registerSubType(DoubleOrValidator.class, "DoubleOr");
/* 25 */     factory.registerSubType(DoubleRangeValidator.class, "DoubleRange");
/* 26 */     factory.registerSubType(AttributeRelationValidator.class, "NumericRelation");
/* 27 */     factory.registerSubType(ArrayNotEmptyValidator.class, "ArrayNotEmpty");
/* 28 */     factory.registerSubType(AnyPresentValidator.class, "AnyPresent");
/* 29 */     factory.registerSubType(OnePresentValidator.class, "OnePresent");
/* 30 */     factory.registerSubType(OneOrNonePresentValidator.class, "OneOrNonePresent");
/* 31 */     factory.registerSubType(AnyBooleanValidator.class, "AnyTrue");
/* 32 */     factory.registerSubType(StringArrayNotEmptyValidator.class, "StringListNotEmpty");
/* 33 */     factory.registerSubType(StringArrayNoEmptyStringsValidator.class, "StringListNoEmptyStrings");
/* 34 */     factory.registerSubType(DoubleSequenceValidator.class, "DoubleSequenceValidator");
/* 35 */     factory.registerSubType(IntSequenceValidator.class, "IntSequenceValidator");
/* 36 */     factory.registerSubType(ExistsIfParameterSetValidator.class, "ExistsIfParameterSet");
/* 37 */     factory.registerSubType(TemporalSequenceValidator.class, "TemporalSequenceValidator");
/* 38 */     factory.registerSubType(RequiresFeatureIfValidator.class, "RequiresFeatureIf");
/* 39 */     factory.registerSubType(RequiresOneOfFeaturesValidator.class, "RequiresOneOfFeatures");
/* 40 */     factory.registerSubType(StateStringValidator.class, "StateString");
/* 41 */     factory.registerSubType(ValidateIfEnumIsValidator.class, "ValidateIfEnumIs");
/* 42 */     factory.registerSubType(ValidateAssetIfEnumIsValidator.class, "ValidateAssetIfEnumIs");
/* 43 */     factory.registerSubType(ComponentOnlyValidator.class, "ComponentOnly");
/* 44 */     factory.registerSubType(RequiresFeatureIfEnumValidator.class, "RequiresFeatureIfEnum");
/* 45 */     factory.registerSubType(EnumArrayNoDuplicatesValidator.class, "EnumArrayNoDuplicates");
/* 46 */     factory.registerSubType(ArraysOneSetValidator.class, "ArraysOneSet");
/* 47 */     factory.registerSubType(BooleanImplicationValidator.class, "BooleanImplication");
/* 48 */     factory.registerSubType(InstructionContextValidator.class, "InstructionContext");
/* 49 */     factory.registerSubType(AtMostOneBooleanValidator.class, "AtMostOneBoolean");
/* 50 */     gsonBuilder.registerTypeAdapterFactory(factory);
/* 51 */     return gsonBuilder;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ValidatorTypeRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */