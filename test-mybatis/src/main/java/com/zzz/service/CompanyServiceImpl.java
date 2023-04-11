package com.zzz.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.IDUtil;
import com.zzz.entity.Company;
import com.zzz.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> {
    public void testBatchInsert(Integer times) {
        List<Company> companyList = genCompanyList(times);
        this.saveBatch(companyList);
    }

    private List<Company> genCompanyList(Integer times) {
        List<Company> companyList = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            Company company = Company.builder()
                    .name("测试公司" + IDUtil.getUUID())
                    .alias("我是别名")
                    .email("771668756@qq.com")
                    .companyType(1)
                    .intro("我是公司简介")
                    .isTop(1)
                    .phone("1523568723")
                    .scale("22~232")
                    .status("1")
                    .registeredCapital("141412")
                    .website("http://www.bailain-ai.com")
                    .address("北京市昌平区阜成门外大街")
                    .unifiedSocialNumber("79792352532")
                    .createTime(LocalDateTime.now())
                    .build();
            companyList.add(company);
        }
        return companyList;
    }
}
