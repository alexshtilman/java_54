package telran.spring.jpa.dto;

import org.springframework.beans.factory.annotation.Value;

public interface IntervalMarks {

	@Value("#{target.interval*target.intervalNum}")
	int getMin();

	@Value("#{target.interval*target.intervalNum + target.interval-1 }")
	int getMax();

	long getCountOfOccurencies();

}
