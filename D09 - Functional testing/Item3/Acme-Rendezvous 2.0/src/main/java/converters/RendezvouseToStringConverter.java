package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rendezvouse;

@Component
@Transactional
public class RendezvouseToStringConverter implements Converter<Rendezvouse, String>{
	
	@Override
	public String convert(Rendezvouse rendezvouse) {
		String result;

		if (rendezvouse == null)
			result = null;
		else
			result = String.valueOf(rendezvouse.getId());
		return result;
	}

}
