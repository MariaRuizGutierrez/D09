package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.ManagerRepository;
import domain.Manager;


public class StringToManagerConverter implements Converter<String, Manager>{
	
	@Autowired
	private ManagerRepository	managerRepository;
	
	@Override
	public Manager convert(String text) {

		Manager result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.managerRepository.findOne(id);

			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
		
	}

}
