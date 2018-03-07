
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ServiceOffered;

@Component
@Transactional
public class ServiceOfferedToStringConverter implements Converter<ServiceOffered, String> {

	@Override
	public String convert(final ServiceOffered serviceOffered) {
		String result;

		if (serviceOffered == null)
			result = null;
		else
			result = String.valueOf(serviceOffered.getId());
		return result;
	}

}
