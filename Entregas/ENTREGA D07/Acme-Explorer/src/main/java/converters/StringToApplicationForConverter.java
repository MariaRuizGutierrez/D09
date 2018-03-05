
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationForRepository;
import domain.ApplicationFor;

@Component
@Transactional
public class StringToApplicationForConverter implements Converter<String, ApplicationFor> {

	@Autowired
	ApplicationForRepository	applicationForRepository;


	@Override
	public ApplicationFor convert(final String text) {
		ApplicationFor result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.applicationForRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
