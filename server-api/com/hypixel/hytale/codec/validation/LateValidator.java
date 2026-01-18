package com.hypixel.hytale.codec.validation;

import com.hypixel.hytale.codec.ExtraInfo;

public interface LateValidator<T> extends Validator<T> {
  void acceptLate(T paramT, ValidationResults paramValidationResults, ExtraInfo paramExtraInfo);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\validation\LateValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */