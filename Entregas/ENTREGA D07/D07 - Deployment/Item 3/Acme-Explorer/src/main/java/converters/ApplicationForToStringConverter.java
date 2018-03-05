
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ApplicationFor;

@Component
@Transactional
public class ApplicationForToStringConverter implements Converter<ApplicationFor, String> {

	@Override
	public String convert(final ApplicationFor applicationFor) {
		String result;

		if (applicationFor == null)
			result = null;
		else
			result = String.valueOf(applicationFor.getId());
		return result;
	}

}
