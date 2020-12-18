package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Company;

public interface CompanyService {

    Company selectMapKeyWordByPrimary(String companyId);

    boolean updateMapKeyWord(String poi, String keyWord, String companyId);
}
