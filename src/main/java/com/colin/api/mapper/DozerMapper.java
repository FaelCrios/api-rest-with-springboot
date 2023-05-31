package com.colin.api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {
	
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <Origin, Destiny> Destiny parseObject(Origin origin, Class<Destiny> destiny) {
		return mapper.map(origin, destiny);
	}
	
	public static <Origin, Destiny> List<Destiny> parseListObjects(List<Origin> origin, Class<Destiny> destiny) {
		List<Destiny> destinationObjects = new ArrayList<Destiny>();
		for (Origin o : origin) {
			destinationObjects.add(mapper.map(o, destiny));
		}
		return destinationObjects;
		
		
	}
}
