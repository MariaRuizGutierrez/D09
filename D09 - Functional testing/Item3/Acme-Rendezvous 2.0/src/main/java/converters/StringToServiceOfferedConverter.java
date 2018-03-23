
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ServiceOfferedRepository;
import domain.ServiceOffered;

@Component
@Transactional
public class StringToServiceOfferedConverter implements Converter<String, ServiceOffered> {

	@Autowired
	private ServiceOfferedRepository	serviceOfferedRepository;


	@Override
	public ServiceOffered convert(final String text) {

		ServiceOffered result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.serviceOfferedRepository.findOne(id);

			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;

	}

}
