package com.app.app.Dao;

import java.util.ArrayList;
import com.app.app.Dto.BbsDto;
import java.util.Map;
import com.app.app.Dto.UserDto;

public interface BDao {
	public void Write(BbsDto BDto);
	public ArrayList<BbsDto> list();
	public BbsDto detail(String bbs_id);
}
