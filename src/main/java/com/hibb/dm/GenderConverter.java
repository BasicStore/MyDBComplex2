package com.hibb.dm;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {
	
	@Override
    public Integer convertToDatabaseColumn(Gender gender) {
		return gender.getId();
    }
 
    @Override
    public Gender convertToEntityAttribute(Integer genderId) {
    	return Gender.fromId(genderId);
    }

}
