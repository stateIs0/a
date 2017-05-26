package com.ipzoe.light.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Light;
import com.ipzoe.light.bean.response.PageBean;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.AdminLightRepository;
import com.ipzoe.light.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzx on 2017/3/28.
 */
@Service
@Transactional
public class AdminLightService {

    @Autowired
    private AdminLightRepository adminLightRepository;

    /**
     * 用户分页查询
     * @param page
     * @param size
     * @param key
     * @return
     */
    public Response query(Integer page, Integer size, String key) {

        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, size);

        if (!StringUtil.isEmpty(key)) {
            map.put("key", "%" + key + "%");
        }

        List<Light> platformList = adminLightRepository.selectPlatform(map);


        return Response.ok(new PageBean<>(platformList,platformList.size()));

    }
}
