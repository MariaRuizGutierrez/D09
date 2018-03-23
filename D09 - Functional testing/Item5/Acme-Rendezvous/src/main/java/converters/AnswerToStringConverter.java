package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Answer;

@Component
@Transactional
public class AnswerToStringConverter implements Converter<Answer, String>{
	
	@Override
	public String convert(Answer reply) {
		String result;

		if (reply == null)
			result = null;
		else
			result = String.valueOf(reply.getId());
		return result;
	}

}
