package org.bson;

public interface FieldNameValidator {
  boolean validate(String paramString);
  
  FieldNameValidator getValidatorForField(String paramString);
  
  default void start() {}
  
  default void end() {}
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\FieldNameValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */