package dto;

import lombok.Data;

@Data
public abstract class BaseDto<T> {
	protected T id;
}
