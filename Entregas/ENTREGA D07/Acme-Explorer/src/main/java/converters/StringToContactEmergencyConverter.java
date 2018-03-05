
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ContactEmergencyRepository;
import domain.ContactEmergency;

@Component
@Transactional
public class StringToContactEmergencyConverter implements Converter<String, ContactEmergency> {

	@Autowired
	ContactEmergencyRepository	contactEmergencyRepository;


	@Override
	public ContactEmergency convert(final String text) {
		ContactEmergency result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.contactEmergencyRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
