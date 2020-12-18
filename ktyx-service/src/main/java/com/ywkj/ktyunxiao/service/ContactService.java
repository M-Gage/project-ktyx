package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 16:07
 */
@Service
public interface ContactService {

    boolean insertList(List<Contact> contactList);

    boolean updateList(List<Contact> contactList);

    boolean deleteListByPrimaryId(String[] contactIdArray, String customerId, String companyId);

    boolean insert(Contact contact);

}
