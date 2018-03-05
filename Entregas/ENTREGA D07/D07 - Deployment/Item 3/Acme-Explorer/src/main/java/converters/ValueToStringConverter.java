
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Value;

@Component
@Transactional
public class ValueToStringConverter implements Converter<Value, String> {

	@Override
	public String convert(final Value value) {
		String result;

		if (value == null)
			result = null;
		else
			result = String.valueOf(value.getId());
		return result;
	}

}
