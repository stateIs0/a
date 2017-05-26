package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.SmsRecord;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wzx on 17/3/31.
 */
@Mapper
public interface SmsRecordRepository extends DefaultMapper<SmsRecord> {
    @Select("select code from sms_record  " +
            "where phone = #{0} and status = 0 " +
            "order by create_time desc limit 0,1")
    String findCodeByPhone(String phone);
}
