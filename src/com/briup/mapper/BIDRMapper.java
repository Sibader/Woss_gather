package com.briup.mapper;


import com.briup.util.BIDR;

public interface BIDRMapper {

	public void addBIDR(Integer day,BIDR bidrs);
	public void deleteByDay(Integer day,Integer arg);
}
