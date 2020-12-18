package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.dao.ContactMapper;
import com.ywkj.ktyunxiao.model.Contact;
import com.ywkj.ktyunxiao.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 16:09
 */
@Service
public class ContactServiceImpl implements ContactService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public boolean insertList(List<Contact> contactList) {
        if (checkIsMain(contactList) != contactList.size()) {
            throw new ParamException();
        }
        return contactMapper.insertList(contactList) == contactList.size();
    }

    @Override
    public boolean updateList(List<Contact> contactList) {
        if (checkIsMain(contactList) != contactList.size()) {
            throw new ParamException();
        }
        return contactMapper.updateList(contactList) == contactList.size();
    }

    @Override
    public boolean deleteListByPrimaryId(String[] contactIdArray, String customerId, String companyId) {
        return contactMapper.deleteListByPrimaryId(contactIdArray, customerId, companyId) == contactIdArray.length;
    }

    @Override
    public boolean insert(Contact contact) {
        return contactMapper.insert(contact) == SUCCESS_INT;
    }

    public long checkIsMain(List<Contact> contactList){
        return contactList.stream().filter(c -> c.getIsMain() == 0 || c.getIsMain() == 1).count();
    }

}
