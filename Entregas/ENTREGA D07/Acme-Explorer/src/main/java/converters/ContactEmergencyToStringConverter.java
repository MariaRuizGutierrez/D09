
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ContactEmergency;

@Component
@Transactional
public class ContactEmergencyToStringConverter implements Converter<ContactEmergency, String> {

	@Override
	public String convert(final ContactEmergency contactEmergency) {
		String result;

		if (contactEmergency == null)
			result = null;
		else
			result = String.valueOf(contactEmergency.getId());
		return result;
	}

}
