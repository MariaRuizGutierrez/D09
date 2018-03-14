package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RendezvouseRepository;
import domain.Rendezvouse;

@Component
@Transactional
public class StringToRendezvouseConverter implements Converter<String, Rendezvouse>{
	
	@Autowired
	private RendezvouseRepository	rendezvouseRepository;


	@Override
	public Rendezvouse convert(String text) {

		Rendezvouse result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.rendezvouseRepository.findOne(id);

			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
